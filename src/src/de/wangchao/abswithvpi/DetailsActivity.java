
package de.wangchao.abswithvpi;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import de.wangchao.abswithvpi.tools.Tools;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * This is a secondary activity, to show what the user has selected when the
 * screen is not large enough to show it all in one activity.
 */

public class DetailsActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_Sherlock_Light_ForceOverflow);
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            DetailsFragment details = new DetailsFragment();
            details.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details)
                    .commit();
        }
    }

    /**
     * This is the secondary fragment, displaying the details of a particular
     * item.
     */

    public static class DetailsFragment extends SherlockFragment {
        public static final String TAG = "DetailsFragment";

        /**
         * Create a new instance of DetailsFragment, initialized to show the
         * text at 'index'.
         */
        public static DetailsFragment newInstance(int index) {

            DetailsFragment f = new DetailsFragment();

            // Supply index input as an argument.
            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);

            return f;
        }

        public int getShownIndex() {

            return getArguments().getInt("index", 0);
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

            if (container == null) {
                // We have different layouts, and in one of them this
                // fragment's containing frame doesn't exist. The fragment
                // may still be created from its saved state, but there is
                // no reason to try to create its view hierarchy because it
                // won't be displayed. Note this is not needed -- we could
                // just run the code below, where we would create and return
                // the view hierarchy; it would just never be used.
                return null;
            }

            ScrollView scroller = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                    getActivity().getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scroller.addView(text);
            text.setText(Shakespeare.DIALOGUE[getShownIndex()]);
            return scroller;
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

    }

}
