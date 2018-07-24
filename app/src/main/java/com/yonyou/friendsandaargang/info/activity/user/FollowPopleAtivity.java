package com.yonyou.friendsandaargang.info.activity.user;

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
import com.yonyou.friendsandaargang.homepage.modle.FollowPople;
import com.yonyou.friendsandaargang.homepage.modle.MyFollowPopleAdapter;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
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

/**
 * Created by shibing on 18/3/29.
 */

public class FollowPopleAtivity extends BaseActivity implements OnItemClickListener

        , OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.follow_pople_list)
    ListView follow_pople_list;
    @BindView(R.id.no_follow_pople)
    TextView no_follow_pople;
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout refreshLayout;

    private String userId;
    private List<FollowPople.ContentBean> list;
    private MyFollowPopleAdapter adapter;
    private int pageNo = 1;
    private int pageSize = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_pople);
        ButterKnife.bind(this);
        initviews();
        initRefresh();
        getFollowPeople();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我关注的人");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
    }

    /**
     * 加载更多
     */
    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);

        list = new ArrayList<>();
    }


    private void getFollowPeople() {
        Call<FollowPople> call = communityService().getFollowedUserTraceInDetail(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<FollowPople>(mContext, new HttpCallBackImpl<FollowPople>() {
            @Override
            public void onResponseCallback(FollowPople followPople) {
                if (followPople.getReturnCode() != 0 && !followPople.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, followPople.getReturnMsg()).show();
                    return;
                }
                initData(followPople.getContent());
            }
        }));
    }


    private void initData(List<FollowPople.ContentBean> contentBeans) {


        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                no_follow_pople.setVisibility(View.VISIBLE);
                follow_pople_list.setVisibility(View.GONE);
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
            adapter = new MyFollowPopleAdapter(FollowPopleAtivity.this, list);
            follow_pople_list.setAdapter(adapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            adapter.notifyDataSetChanged();
        }

        adapter.addOnItemClickListener(this);

    }


    /**
     * 监听事件 点赞
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        String postid = list.get(position).getPostId();
        int dianzan = list.get(position).getThumbuped();
        String replyId;
        if (list.get(position).getThumbuped() == 1) {
            dianzan = -1;
        } else if (list.get(position).getThumbuped() == 0) {
            dianzan = 1;
        }
        switch (list.get(position).getItemCode()) {
            case "10171001":
            case "10171004":
            case "10171005":
            case "10171007":
            case "10171008":
                getThumbUpOrDown4Post(postid, dianzan);
                break;
            case "10171002":
            case "10171009":
                thumbUpOrDown4Reply(dianzan, list.get(position).getReplyId());
                break;
            case "10171006":
                QaReplyThumbs(list.get(position).getReplyId(), dianzan);
                break;
        }


    }


    /**
     * 对帖子点赞/取消点赞
     *
     * @param postId
     * @param dianzan
     */
    private void getThumbUpOrDown4Post(String postId, int dianzan) {
        Call<HttpResult> call = communityService().getThumbUpOrDown4Post(userId, postId, dianzan);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getFollowPeople();
            }
        }));
    }


    /**
     * 对帖子回复点赞/取消点赞
     */
    private void thumbUpOrDown4Reply(int dianzan, String replyId) {
        Call<HttpResult> call = communityService().getThumbUpOrDown4Reply(userId, replyId, dianzan);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {

                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getFollowPeople();
            }
        }));
    }


    /**
     * 对问答 回答的点赞
     */
    private void QaReplyThumbs(String replyId, final int thumbs) {
        Call<HttpResult> call = communityService().thumbUpOrDown4Answer(userId, replyId, thumbs);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getFollowPeople();
            }
        }));
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getFollowPeople();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getFollowPeople();
    }
}

