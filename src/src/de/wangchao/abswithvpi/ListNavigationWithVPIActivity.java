
package de.wangchao.abswithvpi;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TabPageIndicator;

import de.wangchao.abswithvpi.DetailsActivity.DetailsFragment;
import de.wangchao.abswithvpi.tools.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListNavigationWithVPIActivity extends SherlockFragmentActivity implements
        ActionBar.OnNavigationListener {
    private static final String TAG = "ListNavigationWithV";

    private static String[] CONTENT = new String[] {
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
                R.array.navigation, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
        // getSupportActionBar().setDisplayShowTitleEnabled(true);
        // getSupportActionBar().setTitle(getString(R.string.list_navigation));

        CONTENT = getResources().getStringArray(R.array.job_tabs);

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

        menu.add("Refresh")
                .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        /*
         * menu.add("Search").setIcon(isLight ? R.drawable.ic_search_inverse :
         * R.drawable.ic_search)
         * .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
         */
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
    public static class TitlesFragment extends SherlockFragment {
        public static final String TAG = "TitlesFragment";

        String[] mTitles = {};

        boolean mDualPane;
        int mCurCheckPosition = 0;

        ListView mLv;
        View mDetailFrame;

        public static TitlesFragment newInstance(String[] content) {

            Tools.debugLog(TAG, "newInstance");

            TitlesFragment fragment = new TitlesFragment();
            fragment.mTitles = content;

            return fragment;
        }

        /*
         * @Override public void onSaveInstanceState(Bundle outState) {
         * super.onSaveInstanceState(outState); outState.putInt("curChoice",
         * mCurCheckPosition); outState.putSerializable("titles", mTitles); }
         */

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

            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            Tools.debugLog(TAG, "onCreateView");
            View v = inflater.inflate(R.layout.fragment_titles, container, false);
            mLv = (ListView) (v.findViewById(R.id.titles_lv));

            // Check to see if we have a frame in which to embed the details
            // fragment directly in the containing UI.
            mDetailFrame = v.findViewById(R.id.titles_details);
            mDualPane = mDetailFrame != null && mDetailFrame.getVisibility() == View.VISIBLE;

            return v;
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

            /*
             * if (savedInstanceState != null) { // Restore last state for
             * checked position. mCurCheckPosition =
             * savedInstanceState.getInt("curChoice", 0); mTitles = (String[])
             * savedInstanceState.getSerializable("titles"); }
             */

            mLv.setAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_list_item_checkable_1, android.R.id.text1, mTitles));
            mLv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    showDetails(position);

                }

            });

            if (mDualPane) {
                // In dual-pane mode, the list view highlights the selected
                // item.
                mLv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Make sure our UI is in the correct state.
                showDetails(mCurCheckPosition);
            }
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

        void showDetails(int index) {

            mCurCheckPosition = index;

            if (mDualPane) {
                mLv.setItemChecked(index, true);

                DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(
                        R.id.titles_details);
                if (details == null || details.getShownIndex() != index) {
                    // Make new fragment to show this selection.
                    details = DetailsFragment.newInstance(index);

                    // Execute a transaction, replacing any existing fragment
                    // with this one inside the frame.
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.titles_details, details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
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

            switch (position) {
                case 0:
                    return TitlesFragment.newInstance(mLists.get(position));
                case 1:
                    return new PhoneNumsFragment();
                case 2:
                    return new AppFragment();
                default:
                    return TabItemFragment
                            .newInstance(ListNavigationWithVPIActivity.CONTENT[position
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
