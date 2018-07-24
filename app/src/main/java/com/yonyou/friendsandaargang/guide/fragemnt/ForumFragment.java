package com.yonyou.friendsandaargang.guide.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.adapter.SimpleFragmentPagerAdapter;
import com.yonyou.friendsandaargang.forum.fragemnt.EssenceFragment;
import com.yonyou.friendsandaargang.forum.fragemnt.FollowFragment;
import com.yonyou.friendsandaargang.homepage.activity.PostActivity;
import com.yonyou.friendsandaargang.homepage.activity.PublishPostActivity;
import com.yonyou.friendsandaargang.homepage.activity.SearchActivity;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.view.dialog.DialogLogin;
import com.yonyou.friendsandaargang.view.dialog.ForumDialog;
import com.yonyou.friendsandaargang.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/2/7.
 */

public class ForumFragment extends Fragment {
    private View view;
    @BindView(R.id.fourm_viewpager)
    MyViewPager viewPager;
    @BindView(R.id.fourm_tablayout)
    TabLayout tabLayout;

    private List<Pair<String, Fragment>> items;
    private ForumDialog forumDialog;
    private DialogLogin dialogLogin;
    private boolean isLogin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_fourm, null);
        ButterKnife.bind(this, view);
        initViewPage();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        isLogin = SPTool.getBoolean(getActivity(), Constants.ISLOGIN);
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.fourm_add_lay, R.id.fourm_search_lay})
    public void onClick(View v) {
        if (!isLogin) {
            ActivityManger.skipActivity(getActivity(), ActivityLogin.class);
            return;
        }

        switch (v.getId()) {
            case R.id.fourm_add_lay:
                showDiaglo();
                break;
            case R.id.fourm_search_lay:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", "forum");
                startActivity(intent);
                break;

        }
    }

    private void initViewPage() {
        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>(" 精华", new EssenceFragment()));
        items.add(new Pair<String, Fragment>("关注", new FollowFragment()));
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getChildFragmentManager(), items));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1 && !isLogin) {
                    // viewPager.setVisibility(View.GONE);
                    showLoginDialog();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void showDiaglo() {
        forumDialog = new ForumDialog(getActivity());
        forumDialog.setCanceledOnTouchOutside(false);
        forumDialog.setForunListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishPostActivity.class);
                intent.putExtra("title", "帖子");
                intent.putExtra("type", "10051002");        //帖子
                intent.putExtra(Constants.FORUMID, "");
                startActivity(intent);
                forumDialog.dismiss();

            }
        });

        forumDialog.setHuaTiListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishPostActivity.class);
                intent.putExtra("title", "话题");
                intent.putExtra("type", "10051001");        //话题
                intent.putExtra(Constants.FORUMID, "");
                startActivity(intent);
                forumDialog.dismiss();
            }
        });


        forumDialog.setExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumDialog.dismiss();

            }
        });
        forumDialog.show();
    }


    private void showLoginDialog() {
        dialogLogin = new DialogLogin(getActivity());
        dialogLogin.setCancelable(false);
        dialogLogin.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intent);
                tabLayout.setScrollPosition(0, 0, true);
                tabLayout.setSelected(true);
                viewPager.setCurrentItem(0);
                dialogLogin.dismiss();
            }
        });
        dialogLogin.setCleanListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout.setScrollPosition(0, 0, true);
                tabLayout.setSelected(true);
                viewPager.setCurrentItem(0);
                dialogLogin.dismiss();
            }
        });

        dialogLogin.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (forumDialog != null) {
            forumDialog.dismiss();
        }
    }
}
