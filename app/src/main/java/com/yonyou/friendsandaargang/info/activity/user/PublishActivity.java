package com.yonyou.friendsandaargang.info.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.forum.adapter.ProductAdapter;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
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
 * 我的发表
 */

public class PublishActivity extends BaseActivity implements OnItemClickListener
        , OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.publish_list)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.zanwu_fabiao)
    TextView textView;
    @BindView(R.id.publish_refresh)
    SmartRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;
    private ProductAdapter adapter;
    private List<Follow.ContentBean> list;
    private String userId;
    private String postType;
    private int pageNo = 1;
    private int pageSize = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        initviews();
        initRecyler();
        initRefresh();
        getPublishList();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的发表");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
    }

    private void initRecyler() {
        layoutManager = new LinearLayoutManager(PublishActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewSwipeEnabled(false);
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
    }
    private void initRefresh() {
        list = new ArrayList<>();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }
    /**
     * 调用接口
     */
    private void getPublishList() {
        Call<Follow> call = communityService().getUserPostList(userId, postType,
                pageNo, pageSize);
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
                ToastUtils.normal(mContext, "服务器异常").show();
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
            }
        });
    }


    private void getData(List<Follow.ContentBean> contentBeans) {

        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                recyclerView.setVisibility(View.GONE);
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
            adapter = new ProductAdapter(list, PublishActivity.this);
            recyclerView.setAdapter(adapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
        adapter.addOnItemClickListener(this);

    }


    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.album_dp_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(PublishActivity.this)
                    .setText("删除")
                    .setBackground(R.color.color_red)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height)
                    .setTextSize(14);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    private SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition();
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                String postid = list.get(adapterPosition).getPostId();
                getDeleteDraft(postid, adapterPosition);
            }
        }
    };


    /**
     * 删除我的发表
     */
    private void getDeleteDraft(String postId, final int position) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getdeleteThePost(postId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                list.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器异常").show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(PublishActivity.this, PostDetailsActivity.class);
        intent.putExtra(Constants.TITLE, list.get(position).getTypeDesc());
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        startActivity(intent);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getPublishList();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getPublishList();
    }
}
