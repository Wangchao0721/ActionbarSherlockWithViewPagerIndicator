
package de.wangchao.abswithvpi;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

import de.wangchao.abswithvpi.tools.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListNavigationWithVPIActivity extends SherlockFragmentActivity implements
        ActionBar.OnNavigationListener {
    private static final String TAG = "ListNavigationWithV";

    private static final String[] CONTENT = new String[] {
            "Recent", "Artists", "Albums", "Songs", "Playlists", "Genres"
    };

    String[] mSongs = {};
    String[] mArtists = {};
    ArrayList<String[]> mLists = new ArrayList<String[]>();

    TabFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

    /************************************************************************/
    /* METHODS - core lifecycle methods */
    /************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_Sherlock_Light_ForceOverflow);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_navigation_vpi);
        // List Navigation
        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context,
                R.array.locations, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
        getSupportActionBar().setTitle(getString(R.string.list_navigation));

        // Song List
        mSongs = getResources().getStringArray(R.array.songs_recent);
        mArtists = getResources().getStringArray(R.array.artists);
        mLists.add(mSongs);
        mLists.add(mArtists);

        // Tab ViewPagerIndicator
        mAdapter = new GoogleMusicAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }

    /************************************************************************/
    /* METHODS - UI Interaction Methods */
    /************************************************************************/
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

    /************************************************************************/
    /* FRAGMENT */
    /************************************************************************/
    public static class TitlesFragment extends SherlockListFragment {
        public static final String TAG = "TitlesFragment";

        String[] mTitles = {};

        boolean mDualPane;
        int mCurCheckPosition = 0;

        public static TitlesFragment newInstance(String[] content) {

            Tools.debugLog(TAG, "newInstance");

            TitlesFragment fragment = new TitlesFragment();
            fragment.mTitles = content;

            return fragment;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {

            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
            outState.putSerializable("titles", mTitles);

        }

        // Created
        @Override
        public void onAttach(Activity activity) {

            super.onAttach(activity);

            Tools.debugLog(TAG, "onAttach");
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            Tools.debugLog(TAG, "onCreate");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            Tools.debugLog(TAG, "onCreateView");
            // TODO Auto-generated method stub
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);

            Tools.debugLog(TAG, "onViewCreated");
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);

            Tools.debugLog(TAG, "onActivityCreated");

            if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
                mTitles = (String[]) savedInstanceState.getSerializable("titles");
            }

            this.setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_list_item_checkable_1, android.R.id.text1, mTitles));

            // TODO: Deal with Double Panel for detail
        }

        // Started
        @Override
        public void onStart() {

            super.onStart();

            Tools.debugLog(TAG, "onStart");
        }

        // Resumed
        @Override
        public void onResume() {

            super.onResume();

            Tools.debugLog(TAG, "onResume");
        }

        // Paused
        @Override
        public void onPause() {

            super.onPause();

            Tools.debugLog(TAG, "onPause");
        }

        // Stopped
        @Override
        public void onStop() {

            super.onStop();

            Tools.debugLog(TAG, "onStop");
        }

        // Destoryed
        @Override
        public void onDestroyView() {

            super.onDestroyView();

            Tools.debugLog(TAG, "onDestroyView");
        }

        @Override
        public void onDestroy() {

            super.onDestroy();

            Tools.debugLog(TAG, "onCreate");
        }

        @Override
        public void onDetach() {

            super.onDetach();

            Tools.debugLog(TAG, "onDetach");
        }

        // UI Interactive Methods
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {

            showDetails(position);
        }

        void showDetails(int index) {

            mCurCheckPosition = index;

            if (mDualPane) {
                // TODO: Deal with Double Panel for detail
            } else {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailsActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        }

    }

    class GoogleMusicAdapter extends TabFragmentAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // TODO: Transfer String Array to TabItemFragment
            if (position < 2) {
                return TitlesFragment.newInstance(mLists.get(position));
            } else {
                return TabItemFragment.newInstance(ListNavigationWithVPIActivity.CONTENT[position
                        % ListNavigationWithVPIActivity.CONTENT.length]);
            }

        }

        @Override
        public int getCount() {

            return ListNavigationWithVPIActivity.CONTENT.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return ListNavigationWithVPIActivity.CONTENT[position
                    % ListNavigationWithVPIActivity.CONTENT.length].toUpperCase();
        }
    }
}
