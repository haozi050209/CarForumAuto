package com.yonyou.friendsandaargang.info.activity.message;

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
import com.yonyou.friendsandaargang.info.adapter.PurseNewsAdapter;
import com.yonyou.friendsandaargang.info.bean.SyatemMessage;
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
 * 钱包消息
 */

public class PurseNewsActivity extends BaseActivity implements
        OnRefreshListener, AdapterView.OnItemClickListener, OnLoadMoreListener {
    @BindView(R.id.list_puresenews)
    ListView listView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    private String userId;
    private List<SyatemMessage.ContentBean> list;
    private int pageNo = 1;
    private int pageSize = 8;
    private PurseNewsAdapter purseNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pursenews);
        ButterKnife.bind(this);

        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("钱包消息");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        initRefresh();
        getPursenews();
    }


    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        list = new ArrayList<>();
        listView.setOnItemClickListener(this);
    }


    private void getPursenews() {
        Call<SyatemMessage> call = communityService().getWalletMessageByUserId(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<SyatemMessage>(mContext, new HttpCallBackImpl<SyatemMessage>() {
            @Override
            public void onResponseCallback(SyatemMessage model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initDatas(model.getContent());
            }
        }));
    }


    private void initDatas(List<SyatemMessage.ContentBean> contentBeans) {

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
            purseNewsAdapter = new PurseNewsAdapter(PurseNewsActivity.this, list);
            listView.setAdapter(purseNewsAdapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            purseNewsAdapter.notifyDataSetChanged(list);
        }

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getPursenews();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getPursenews();
    }
}
