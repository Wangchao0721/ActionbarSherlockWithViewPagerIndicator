
package de.wangchao.abswithvpi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class TabFragmentAdapter extends FragmentPagerAdapter {
    protected static final String[] CONTENT = new String[] {
            "This", "Is", "A", "Test",
    };

    private int mCount = CONTENT.length;

    public TabFragmentAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        // return TabItemFragment.newInstance(CONTENT[position %
        // CONTENT.length]);
        return null;
    }

    @Override
    public int getCount() {

        return mCount;
    }

    public void setCount(int count) {

        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}
