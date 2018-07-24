package com.yonyou.friendsandaargang.info.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.ReWardDetailsAdapter;
import com.yonyou.friendsandaargang.info.bean.UserLevelog;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;
import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.Scale;

/**
 * Created by shibing on 18/4/12.
 */

public class RewardDetailsActivity extends BaseActivity implements
        OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.rewardetails_list)
    ListView rewardetails_list;
    @BindView(R.id.refrshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.no_text_tv)
    TextView no_text_tv;

    private String userId;
    private List<UserLevelog.ContentBean> list;
    private ReWardDetailsAdapter reWardDetailsAdapter;

    private int pageSize = 8;
    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarddetails);
        ButterKnife.bind(this);
        initviews();
        initRefresh();
        getChangeLogList();

    }

    private void initviews() {
        getTitleBar();
        setTitleText("经验值明细");
        userId = SPTool.getContent(mContext, Constants.USER_ID);

    }


    private void initRefresh() {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(Scale));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(Scale));
        smartRefreshLayout.setOnLoadMoreListener(this);
        list = new ArrayList<>();
    }


    private void getChangeLogList() {
        Call<UserLevelog> call = communityService().getChangeLog(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<UserLevelog>(mContext, new HttpCallBackImpl<UserLevelog>() {
            @Override
            public void onResponseCallback(UserLevelog userLevelog) {
                if (userLevelog.getReturnCode() != 0 && !userLevelog.getReturnMsg().equals("success")) {
                    return;
                }
                getData(userLevelog.getContent());
            }
        }));
    }


    private void getData(List<UserLevelog.ContentBean> contentBeans) {

        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                no_text_tv.setVisibility(View.VISIBLE);
                rewardetails_list.setVisibility(View.GONE);
                smartRefreshLayout.setEnableLoadMore(false);
            } else {
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.finishLoadMore();
            return;
        }

        if (pageNo == 1) {
            list.clear();
            list.addAll(contentBeans);
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.setEnableLoadMore(true);
            reWardDetailsAdapter = new ReWardDetailsAdapter(mContext, list);
            rewardetails_list.setAdapter(reWardDetailsAdapter);
        } else {
            list.addAll(contentBeans);
            smartRefreshLayout.finishLoadMore();
            smartRefreshLayout.setNoMoreData(false);
            reWardDetailsAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getChangeLogList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getChangeLogList();
    }
}
