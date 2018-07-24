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
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.BigshotQuestionActivity;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.QaDetalisActivity;
import com.yonyou.friendsandaargang.info.adapter.WenDaMessageAdapter;
import com.yonyou.friendsandaargang.info.bean.ForumMessage;
import com.yonyou.friendsandaargang.info.activity.user.HuiDaAnwserActivity;
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

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 问答消息
 */

public class WenDaMessAgeActivity extends BaseActivity
        implements AdapterView.OnItemClickListener
        , OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.list_wendanews)
    ListView list_wendanews;
    @BindView(R.id.smartlayout)
    SmartRefreshLayout refreshLayout;

    private String userId;
    private List<ForumMessage.ContentBean> list;
    private WenDaMessageAdapter wenDaMessageAdapter;
    private int pageNo = 1;
    private int pageSize = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wendanews);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        getTitleBar();
        setTitleText("问答消息");
        initRefresh();
        getWenDaMessage();
    }


    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        list = new ArrayList<>();
    }


    private void getWenDaMessage() {
        Call<ForumMessage> call = communityService().getQAMessageByUserId(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<ForumMessage>(mContext, new HttpCallBackImpl<ForumMessage>() {
            @Override
            public void onResponseCallback(ForumMessage model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initDatas(model.getContent());
            }
        }));
    }


    private void initDatas(List<ForumMessage.ContentBean> contentBeans) {
        if (contentBeans.size() == 0) {
            if (pageNo != 1) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            return;
        }
        if (pageNo == 1) {
            list.clear();
            list.addAll(contentBeans);
            refreshLayout.finishRefresh();
            wenDaMessageAdapter = new WenDaMessageAdapter(mContext, list);
            list_wendanews.setAdapter(wenDaMessageAdapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            wenDaMessageAdapter.notifyDataSetChanged(list);

        }
        list_wendanews.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (list.get(position).getQaStatus()) {
            case 1:
            case 3:
                if (list.get(position).getTimeOut() == 1) {
                    ToastUtils.normal(mContext, "该提问已经超过48小时,无法回答").show();
                    return;
                }
                intent = new Intent(mContext, HuiDaAnwserActivity.class);
                intent.putExtra(Constants.POSTID, list.get(position).getPostId());
                intent.putExtra("image", list.get(position).getRelatedAvatar());
                intent.putExtra("name", list.get(position).getMessageTitle());
                intent.putExtra("content", list.get(position).getPostTitle());
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(mContext, QaDetalisActivity.class);
                intent.putExtra(Constants.POSTID, list.get(position).getPostId());
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(mContext, BigshotQuestionActivity.class);
                intent.putExtra(Constants.POSTID, list.get(position).getPostId());
                startActivity(intent);
                break;
        }


    }

    //加载更多
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getWenDaMessage();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getWenDaMessage();
    }
}
