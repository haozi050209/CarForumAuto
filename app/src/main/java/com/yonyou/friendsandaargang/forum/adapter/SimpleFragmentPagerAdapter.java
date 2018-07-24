package com.yonyou.friendsandaargang.forum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Pair;

import com.yonyou.friendsandaargang.forum.fragemnt.EssenceFragment;
import com.yonyou.friendsandaargang.forum.fragemnt.FollowFragment;
import com.yonyou.friendsandaargang.guide.adapter.ViewPagerAdapter;

import java.util.List;

/**
 * Created by shibing on 18/3/5.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Pair<String, Fragment>> list;
    private String title[] = {"精华，关注"};

    public SimpleFragmentPagerAdapter(FragmentManager fm,List<Pair<String, Fragment>> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

        return list.get(position).second;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).first;
    }
}
