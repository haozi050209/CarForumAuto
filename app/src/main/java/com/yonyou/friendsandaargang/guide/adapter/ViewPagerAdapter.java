package com.yonyou.friendsandaargang.guide.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by shibing on 18/2/7.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragments;
    private List<String> titles;
    private int mChildCount = 0;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }


    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }




    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments.size() != 0) {
            return fragments.size();
        }

        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
