package com.yonyou.friendsandaargang.info.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import com.yonyou.friendsandaargang.info.adapter.ForumNewsAdapter;
import com.yonyou.friendsandaargang.info.bean.ForumMessage;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 论坛消息
 */

public class ForumNewsActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {


    private static final String TAG = "ForumNewsActivity";
    @BindView(R.id.list_forunews)
    ListView listView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;

    private String userId;

    private List<ForumMessage.ContentBean> list;
    private ForumNewsAdapter forumNewsAdapter;
    private int pageNo = 1;
    private int pageSize = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forunnews);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("论坛消息");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        initRefresh();
        getForumNews();
    }


    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        list = new ArrayList<>();
        listView.setOnItemClickListener(this);
    }


    /**
     * 论坛消息
     */
    private void getForumNews() {
        Call<ForumMessage> call = communityService().getForumMessageByUserId(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<ForumMessage>(mContext, new HttpCallBackImpl<ForumMessage>() {
            @Override
            public void onResponseCallback(ForumMessage model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initDatas(model.getContent());
            }
        }));
    }


    /**
     * 数据处理
     *
     * @param contentBeans
     */
    private void initDatas(List<ForumMessage.ContentBean> contentBeans) {
        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            } else {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            return;
        }
        if (pageNo == 1) {
            list.clear();
            list.addAll(contentBeans);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            forumNewsAdapter = new ForumNewsAdapter(mContext, list);
            listView.setAdapter(forumNewsAdapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            forumNewsAdapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ForumNewsActivity.this, PostDetailsActivity.class);
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        intent.putExtra(Constants.TITLE, "帖子");
        startActivity(intent);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getForumNews();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getForumNews();
    }
}
