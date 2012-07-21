
package de.wangchao.abswithvpi;

import de.wangchao.abswithvpi.tools.Tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.pm.ActivityInfoCompat;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppListLoader extends AsyncTaskLoader<List<AppEntry>> {
    private static final String TAG = "de.wangchao.abswithvpi.AppListLoader";

    final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
    final PackageManager mPm;

    List<AppEntry> mApps;
    PackageIntentReceiver mPackageObserver;

    public AppListLoader(Context context) {

        super(context);

        mPm = getContext().getPackageManager();
    }

    @Override
    protected void onStartLoading() {

        Tools.debugLog(TAG, "onStartLoading");
        if (mApps != null) {
            deliverResult(mApps);
        }

        if (mPackageObserver == null) {
            mPackageObserver = new PackageIntentReceiver(this);
        }

        boolean configChange = mLastConfig.applyNewConfig(getContext().getResources());

        if (takeContentChanged() || mApps == null || configChange) {
            forceLoad();
        }
    }

    /**
     * This is where the bulk of our work is done. This function is called in a
     * background thread and should generate a new set of data to be published
     * by the loader.
     */
    @Override
    public List<AppEntry> loadInBackground() {

        Tools.debugLog(TAG, "loadInBackground");
        List<ApplicationInfo> apps = mPm
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
                        | PackageManager.GET_DISABLED_COMPONENTS);
        if (apps == null) {
            apps = new ArrayList<ApplicationInfo>();
        }

        final Context context = getContext();

        List<AppEntry> entries = new ArrayList<AppEntry>(apps.size());
        for (int i = 0; i < apps.size(); i++) {
            AppEntry entry = new AppEntry(this, apps.get(i));
            entry.loadLabel(context);
            entries.add(entry);
        }

        Collections.sort(entries, ALPHA_COMPARATOR);
        return entries;
    }

    @Override
    public void deliverResult(List<AppEntry> apps) {

        Tools.debugLog(TAG, "deliverResult");

        if (isReset()) {
            if (apps != null) {
                onReleaseResources(apps);
            }
        }

        List<AppEntry> oldApps = apps;
        mApps = apps;

        if (isStarted()) {
            super.deliverResult(apps);
        }

        if (oldApps != null) {
            onReleaseResources(oldApps);
        }

    }

    @Override
    protected void onStopLoading() {

        Tools.debugLog(TAG, "onStopLoading");
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(List<AppEntry> apps) {

        Tools.debugLog(TAG, "onCanceled");
        super.onCanceled(apps);

        onReleaseResources(apps);
    }

    @Override
    protected void onReset() {

        Tools.debugLog(TAG, "onReset");
        super.onReset();

        onStopLoading();

        if (mApps != null) {
            onReleaseResources(mApps);
            mApps = null;
        }

        if (mPackageObserver != null) {
            getContext().unregisterReceiver(mPackageObserver);
            mPackageObserver = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated with an
     * actively loaded data set.
     */
    protected void onReleaseResources(List<AppEntry> apps) {

        // For a simple List<> there is nothing to do. For something
        // like a Cursor, we would close it here.
    }

    public static class InterestingConfigChanges {
        final Configuration mLastConfiguration = new Configuration();
        int mLastDensity;

        /**
         * Helper for determining if the configuration has changed in an
         * interesting way so we need to rebuild the app list.
         */
        boolean applyNewConfig(Resources res) {

            int configChanges = mLastConfiguration.updateFrom(res.getConfiguration());
            boolean densityChanged = mLastDensity != res.getDisplayMetrics().densityDpi;
            if (densityChanged
                    || (configChanges & (ActivityInfo.CONFIG_LOCALE
                            | ActivityInfoCompat.CONFIG_UI_MODE | ActivityInfo.CONFIG_SCREEN_LAYOUT)) != 0) {
                mLastDensity = res.getDisplayMetrics().densityDpi;
                return true;
            }
            return false;
        }
    }

    /**
     * Helper class to look for interesting changes to the installed apps so
     * that the loader can be updated.
     */
    public static class PackageIntentReceiver extends BroadcastReceiver {
        final AppListLoader mLoader;

        public PackageIntentReceiver(AppListLoader loader) {

            mLoader = loader;
            IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
            filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
            filter.addDataScheme("package");
            mLoader.getContext().registerReceiver(this, filter);
            // Register for events related to sdcard installation.
            IntentFilter sdFilter = new IntentFilter();
            sdFilter.addAction(IntentCompat.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
            sdFilter.addAction(IntentCompat.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
            mLoader.getContext().registerReceiver(this, sdFilter);
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            // Tell the loader about the change.
            mLoader.onContentChanged();
        }
    }

    /**
     * Perform alphabetical comparison of application entry objects.
     */
    public static final Comparator<AppEntry> ALPHA_COMPARATOR = new Comparator<AppEntry>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(AppEntry object1, AppEntry object2) {

            return sCollator.compare(object1.getLabel(), object2.getLabel());
        }
    };
}
