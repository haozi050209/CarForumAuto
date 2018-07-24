package com.yonyou.friendsandaargang.info.activity.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.androidkun.xtablayout.XTabLayout;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.guide.adapter.ViewPagerAdapter;
import com.yonyou.friendsandaargang.info.fragment.FollowForumFragment;
import com.yonyou.friendsandaargang.info.fragment.FollowPeopleFragment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的关注
 */
public class FollowActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.follow_tablay)
    XTabLayout tabLayout;
    //关注的人
    private FollowPeopleFragment followPeople;
    //关注的论坛
    private FollowForumFragment followForum;
    private List<Fragment> listFragmnet;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        ButterKnife.bind(this);
        initviews();
        initTab();
    }

    /**
     * 初始化
     */
    private void initviews() {
        getTitleBar();
        title.setText("我的关注");
    }


    private void initTab() {
        List<String> userTab = Arrays.asList("关注的论坛", "关注的人");
        tabLayout.removeAllTabs();
        tabLayout.setxTabDisplayNum(userTab.size());
        for (int i = 0; i < userTab.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(userTab.get(i)));
        }
        listFragmnet = new ArrayList<Fragment>();
        followForum = new FollowForumFragment();
        listFragmnet.add(followForum);
        followPeople = new FollowPeopleFragment();
        listFragmnet.add(followPeople);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), listFragmnet, userTab);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
