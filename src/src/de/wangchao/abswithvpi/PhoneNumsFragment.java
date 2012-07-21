
package de.wangchao.abswithvpi;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import de.wangchao.abswithvpi.tools.Tools;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PhoneNumsFragment extends SherlockListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "de.wangchao.abswithvpi.PhoneNumsFragement";
    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            People._ID, People.DISPLAY_NAME
    };

    SimpleCursorAdapter mAdapter;

    String mCurFilter;

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    /************************************************************************/
    /* FRAGMENT METHOD - Life Cycle */
    /************************************************************************/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        this.setEmptyText(getString(R.string.empty_phonelist));

        this.setHasOptionsMenu(true);

        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_expandable_list_item_1, null, new String[] {
                    People.DISPLAY_NAME
                }, new int[] {
                    android.R.id.text1
                }, 0);
        setListAdapter(mAdapter);

        setListShown(false);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /************************************************************************/
    /* FRAGMENT LOADER */
    /************************************************************************/
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        Tools.debugLog(TAG, "onCreateLoader");
        Uri baseUri;
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(People.CONTENT_FILTER_URI, Uri.encode(mCurFilter));
        } else {
            baseUri = People.CONTENT_URI;
        }

        String select = " ((" + People.DISPLAY_NAME + " NOTNULL) AND (" + People.DISPLAY_NAME
                + " != '' ))";

        return new CursorLoader(getActivity(), baseUri, CONTACTS_SUMMARY_PROJECTION, select, null,
                People.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Tools.debugLog(TAG, "onLoadFinished");
        mAdapter.swapCursor(data);

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> data) {

        Tools.debugLog(TAG, "onLoaderReset");
        mAdapter.swapCursor(null);

    }

    /************************************************************************/
    /* FRAGMENT METHOD - UI Interaction */
    /************************************************************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.ic_search_inverse);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        View searchView = SearchViewCompat.newSearchView(getActivity());
        if (searchView != null) {
            SearchViewCompat.setOnQueryTextListener(searchView, new OnQueryTextListenerCompat() {
                @Override
                public boolean onQueryTextChange(String newText) {

                    // Called when the action bar search text has
                    // changed. Update
                    // the search filter, and restart the loader to
                    // do a new query
                    // with this filter.
                    mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
                    getLoaderManager().restartLoader(0, null, PhoneNumsFragment.this);
                    return true;
                }
            });
            item.setActionView(searchView);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }
}
