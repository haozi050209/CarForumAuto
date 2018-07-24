package com.yonyou.friendsandaargang.info.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.forum.adapter.ProductAdapter;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的回复
 */

public class ReplyActivity extends BaseActivity implements OnRefreshListener, OnItemClickListener, OnLoadMoreListener {

    private String userId;
    @BindView(R.id.reply_list)
    RecyclerView reply_list;
    @BindView(R.id.reply_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.zanwu_reply)
    TextView textView;

    private List<Follow.ContentBean> list;
    private ProductAdapter productAdapter;


    private LinearLayoutManager layoutManager;

    private int pageNo = 1;
    private int pageSize = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_reply);
        ButterKnife.bind(this);
        initviews();
        initRecyler();
        initRefresh();
        getReplyList();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的回复");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
    }

    private void initRecyler() {
        layoutManager = new LinearLayoutManager(ReplyActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reply_list.setLayoutManager(layoutManager);
    }


    private void initRefresh() {
        list = new ArrayList<>();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }


    private void getReplyList() {
        Call<Follow> call = communityService().getUserRepliedPostList(userId, pageNo, pageSize);
        NetUtils<Follow> netUtils = new NetUtils<Follow>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<Follow>() {
            @Override
            public void onResponseCallback(Follow follow) {
                if (follow.getReturnCode() != 0 && !follow.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, follow.getReturnMsg()).show();
                    return;
                }
                getData(follow.getContent());
            }

            @Override
            public void onFailureCallback() {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
            }
        });
    }


    private void getData(List<Follow.ContentBean> contentBeans) {

        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                reply_list.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            return;
        }
        if (pageNo == 1) {
            list.clear();
            list.addAll(contentBeans);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            productAdapter = new ProductAdapter(list, ReplyActivity.this);
            reply_list.setAdapter(productAdapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            productAdapter.notifyDataSetChanged(list);
        }

        productAdapter.addOnItemClickListener(this);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getReplyList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getReplyList();
    }


    /**
     * 监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ReplyActivity.this, PostDetailsActivity.class);
        intent.putExtra(Constants.TITLE, list.get(position).getTypeDesc());
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        startActivity(intent);
    }
}
