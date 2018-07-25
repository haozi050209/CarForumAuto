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
import com.yonyou.friendsandaargang.homepage.activity.PublishPostActivity;
import com.yonyou.friendsandaargang.info.adapter.DraftAdapter;
import com.yonyou.friendsandaargang.info.bean.Draft;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;
import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedFront;
import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.Translate;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的草稿
 */

public class DraftActivity extends BaseActivity implements OnItemClickListener, OnRefreshListener, OnLoadMoreListener {


    private String userId;

    @BindView(R.id.list_drafit)
    SwipeMenuRecyclerView list_drafit;
    @BindView(R.id.draft_tv)
    TextView draft_tv;


    private List<Draft.ContentBean> list;
    private DraftAdapter draftAdapter;

    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        ButterKnife.bind(this);
        initviews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDraftList();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的草稿");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        list_drafit.setLayoutManager(new LinearLayoutManager(DraftActivity.this));
        list_drafit.setItemViewSwipeEnabled(false);
        list_drafit.setSwipeMenuCreator(swipeMenuCreator);
        list_drafit.setSwipeMenuItemClickListener(setSwipeMenuItemClickListener);
    }


    /**
     * 接口调试
     */
    private void getDraftList() {
        Call<Draft> call = communityService().getDraftList(userId, pageNo, pageSize);
        NetUtils<Draft> netUtils = new NetUtils<Draft>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<Draft>() {
            @Override
            public void onResponseCallback(Draft draft) {
                if (draft.getReturnCode() != 0 && !draft.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, draft.getReturnMsg()).show();
                    return;
                }
                setDraftData(draft.getContent());
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();

            }
        });

    }


    private void setDraftData(List<Draft.ContentBean> contentBeans) {
        list = contentBeans;
        if (contentBeans.size() == 0) {
            draft_tv.setVisibility(View.VISIBLE);
            list_drafit.setVisibility(View.GONE);
            return;
        }
        draftAdapter = new DraftAdapter(mContext, contentBeans);
        list_drafit.setAdapter(draftAdapter);
        draftAdapter.addOnItemClickListener(this);
    }


    /**
     * 删除草稿
     */
    private void getDeleteDraft(String postId, final int position) {
        Call<HttpResult> call = communityService().getdeleteThePost(postId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg().toString()).show();
                    return;
                }
                list.remove(position);
                draftAdapter.notifyDataSetChanged();
            }
        }));
    }


    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.album_dp_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(DraftActivity.this)
                    .setText("删除")
                    .setBackground(R.color.color_red)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height)
                    .setTextSize(14);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };


    private SwipeMenuItemClickListener setSwipeMenuItemClickListener = new SwipeMenuItemClickListener() {
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

    @Override
    public void onItemClick(int position) {


        Logger.e("---getContent---", list.get(position).getContent());


        Intent intent = new Intent(mContext, PublishPostActivity.class);
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        intent.putExtra("type", "10051002");
        intent.putExtra(Constants.TITLE, "帖子");
        intent.putExtra("content", list.get(position).getContent() + "");
        intent.putExtra("forumName", list.get(position).getForumName());
        intent.putExtra(Constants.FORUMID, list.get(position).getForumId());
        intent.putExtra("brandName", list.get(position).getBrandName());
        intent.putExtra("DrafTtitle", list.get(position).getTitle());
        startActivity(intent);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getDraftList();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getDraftList();
    }
}
