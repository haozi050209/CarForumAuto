package com.yonyou.friendsandaargang.info.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;
import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.Translate;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的收藏
 * <p>
 * <p>
 * 多种item布局
 * <p>
 * 滑动取消收藏
 */

public class CollectionActivity extends BaseActivity implements OnRefreshListener, OnItemClickListener, OnLoadMoreListener {


    @BindView(R.id.collection_recylerview)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.zanwu_shoucan)
    TextView zanwu_shoucan;
    @BindView(R.id.collection_refresh)
    SmartRefreshLayout refreshLayout;
    private String userId;
    private List<Follow.ContentBean> list;
    private LinearLayoutManager layoutManager;
    private int pageNo = 1;
    private int pageSize = 8;

    private ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initviews();
        initRefresh();
        getCollectionList();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化
     */
    private void initviews() {
        getTitleBar();
        setTitleText("我的收藏");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        layoutManager = new LinearLayoutManager(CollectionActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewSwipeEnabled(false);   //开启滑动删除
        recyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }


    /**
     * 加载更多
     */
    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(Translate));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(Translate));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        list = new ArrayList<>();
    }


    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.album_dp_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(CollectionActivity.this)
                    .setText("取消收藏")
                    .setBackground(R.color.color_red)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height)
                    .setTextSize(13);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };


    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition();
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                String postid = list.get(adapterPosition).getPostId();
                getDeleteFromFavorites(postid, adapterPosition);
            }

        }
    };


    /**
     * 删除收藏
     */

    private void getDeleteFromFavorites(String postId, final int position) {
        Call<HttpResult> call = communityService().getdeleteFromFavorites(userId, postId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                list.remove(position);
                productAdapter.notifyDataSetChanged(list);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, " 服务器加载异常").show();
            }
        });
    }


    /**
     * 收藏列表
     */
    private void getCollectionList() {
        Call<Follow> call = communityService().getFavoritePostList(userId,
                pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<Follow>(mContext, new HttpCallBackImpl<Follow>() {
            @Override
            public void onResponseCallback(Follow model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getData(model.getContent());
            }
        }));
    }


    /**
     * 列表数据
     *
     * @param contentBeans
     */
    private void getData(List<Follow.ContentBean> contentBeans) {
        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                recyclerView.setVisibility(View.GONE);
                zanwu_shoucan.setVisibility(View.VISIBLE);
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
            productAdapter = new ProductAdapter(list, CollectionActivity.this);
            recyclerView.setAdapter(productAdapter);
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
        getCollectionList();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getCollectionList();
    }

    /**
     * 监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CollectionActivity.this, PostDetailsActivity.class);
        intent.putExtra(Constants.TITLE, list.get(position).getTypeDesc());
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        startActivity(intent);
    }


}
