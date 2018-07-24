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
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.info.adapter.FensMessAgeAdapter;
import com.yonyou.friendsandaargang.info.bean.MyFans;
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
 * 个人信息
 */

public class FensMessageActivity extends BaseActivity implements
        AdapterView.OnItemClickListener
        , OnRefreshListener, OnLoadMoreListener {


    private static final String TAG = "FensMessageActivity";
    @BindView(R.id.list_fens_message)
    ListView listView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;


    private FensMessAgeAdapter fensMessAgeAdapter;
    private String userId;
    private List<MyFans.ContentBean> list;
    private int pageNo = 1;
    private int pageSize = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fens_message);
        ButterKnife.bind(this);

        initviews();


    }

    private void initviews() {
        getTitleBar();
        setTitleText("粉丝消息");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        initRefresh();
        getFensList();
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
     * 接口调试
     */
    private void getFensList() {
        Call<MyFans> call = communityService().getFollowerMessageByUserId(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<MyFans>(mContext, new HttpCallBackImpl<MyFans>() {
            @Override
            public void onResponseCallback(MyFans model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initDatas(model.getContent());
            }
        }));
    }


    /**
     * @param contentBeans contentBeans  数据
     */
    private void initDatas(List<MyFans.ContentBean> contentBeans) {
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
            refreshLayout.setEnableLoadMore(true);
            fensMessAgeAdapter = new FensMessAgeAdapter(FensMessageActivity.this, list);
            listView.setAdapter(fensMessAgeAdapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            fensMessAgeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, FensDetailsActivity.class);
        intent.putExtra(Constants.USER_ID, list.get(position).getRelatedUserId());
        startActivity(intent);

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getFensList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getFensList();
    }
}
