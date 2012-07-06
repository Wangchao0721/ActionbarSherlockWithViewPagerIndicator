
package de.wangchao.abswithvpi;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListNavigation extends SherlockActivity implements
        ActionBar.OnNavigationListener {
    private TextView mSelected;
    private String[] mLocations;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        int theme = R.style.Theme_Sherlock_Light_ForceOverflow;
        setTheme(theme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_navigation);
        mSelected = (TextView) findViewById(R.id.text);

        mLocations = getResources().getStringArray(R.array.locations);

        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context,
                R.array.locations, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
        getSupportActionBar().setTitle(getString(R.string.list_navigation));
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        mSelected.setText("Selected: " + mLocations[itemPosition]);
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

}
