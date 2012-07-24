
package de.wangchao.abswithvpi;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class AbsWithViewPagerActivity extends SherlockFragmentActivity implements
        OnPageChangeListener, TabListener {

    private static final String TAG = "de.wangchao.abswithviewpager";

    private AppFragment mAppFragment;
    private PhoneNumsFragment mPhonesFragment;

    private ViewPager mViewPager;

    /************************************************************************/
    /* METHODS - core lifecycle methods */
    /************************************************************************/
    @Override
    protected void onCreate(Bundle arg0) {

        super.onCreate(arg0);
        setTheme(R.style.Theme_Sherlock_Light_ForceOverflow);
        setContentView(R.layout.activity_abswithpager);

        FragmentManager fm = this.getSupportFragmentManager();

        mViewPager = (ViewPager) this.findViewById(R.id.pager);

        if (mViewPager != null) {
            mViewPager.setAdapter(new HomePagerAdapter(fm));
            mViewPager.setOnPageChangeListener(this);
            mViewPager.setPageMarginDrawable(R.drawable.grey_border_inset_lr);
            mViewPager.setPageMargin(this.getResources().getDimensionPixelSize(
                    R.dimen.page_margin_width));

            final ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.addTab(actionBar.newTab().setText(R.string.tab1).setTabListener(this));
            actionBar.addTab(actionBar.newTab().setText(R.string.tab2).setTabListener(this));
        } else {
            // TODO Apply all Fragment to Layout when XLarge
        }
    }

    /************************************************************************/
    /* METHODS - UI Interactive */
    /************************************************************************/
    @Override
    public void onPageScrollStateChanged(int arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {

        getSupportActionBar().setSelectedNavigationItem(position);

        int titleId = -1;
        switch (position) {
            case 0:
                titleId = R.string.tab1;
                break;
            case 1:
                titleId = R.string.tab2;
                break;
        }
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {

        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

        // TODO Auto-generated method stub

    }

    /************************************************************************/
    /* WIDGETS - widgets to adapter views */
    /************************************************************************/
    private class HomePagerAdapter extends FragmentPagerAdapter {
        public HomePagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new PhoneNumsFragment();
                case 1:
                    return new AppFragment();
                default:
                    return new PhoneNumsFragment();
            }
        }

        @Override
        public int getCount() {

            return 2;
        }
    }

}
