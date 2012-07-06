
package de.wangchao.abswithvpi;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;

public class ListNavigationWithVPI extends SherlockFragmentActivity implements
        ActionBar.OnNavigationListener {
    private static final String[] CONTENT = new String[] {
            "Recent", "Artists", "Albums", "Songs", "Playlists", "Genres"
    };

    private String[] mLocations;

    TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        int theme = R.style.Theme_Sherlock_Light_ForceOverflow;
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_navigation_vpi);
        // List Navigation
        mLocations = getResources().getStringArray(R.array.locations);

        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context,
                R.array.locations, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
        getSupportActionBar().setTitle(getString(R.string.list_navigation));

        // Tab ViewPagerIndicator
        mAdapter = new GoogleMusicAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Used to put dark icons on light action bar
        // boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
        boolean isLight = true;

        menu.add("Save").setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Search").setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.ic_search)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Refresh")
                .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Save").setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Search").setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.ic_search)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add("Refresh")
                .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    class GoogleMusicAdapter extends TestFragmentAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return TestFragment.newInstance(ListNavigationWithVPI.CONTENT[position
                    % ListNavigationWithVPI.CONTENT.length]);
        }

        @Override
        public int getCount() {

            return ListNavigationWithVPI.CONTENT.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return ListNavigationWithVPI.CONTENT[position % ListNavigationWithVPI.CONTENT.length]
                    .toUpperCase();
        }
    }
}
