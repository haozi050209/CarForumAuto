package com.yonyou.friendsandaargang.info.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.yonyou.friendsandaargang.info.adapter.ExchangeRecordAdapter;
import com.yonyou.friendsandaargang.info.bean.ExchangeRecordBean;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/5/30.
 */

public class RecordActivity extends BaseActivity implements  OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.lv_record)
    ListView lvRecord;
    @BindView(R.id.srl_record)
    SmartRefreshLayout srlRecord;
    @BindView(R.id.no_text_tv)
    TextView no_text_tv;

    private String userId;
    private int pageNo = 1;
    private List<ExchangeRecordBean.ContentBean> list;
    private ExchangeRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        initviews();
        initListener();
        getExchangeRecord();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("记录");
        list = new ArrayList<>();
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        srlRecord.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        srlRecord.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
    }

    private void initListener() {
        srlRecord.setOnRefreshListener(this);
        srlRecord.setOnLoadMoreListener(this);
    }

    private void getExchangeRecord() {
        Call<ExchangeRecordBean> call = communityService().getExchangeRecord(userId, pageNo);
        NetUtils<ExchangeRecordBean> netUtils = new NetUtils<ExchangeRecordBean>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<ExchangeRecordBean>() {
            @Override
            public void onResponseCallback(ExchangeRecordBean exchangeRecordBean) {
                if (exchangeRecordBean.getReturnCode() != 0 && !exchangeRecordBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(exchangeRecordBean.getReturnMsg());
                    no_text_tv.setVisibility(View.VISIBLE);
                    lvRecord.setVisibility(View.GONE);
                    return;
                }
                setData(exchangeRecordBean);
            }

            @Override
            public void onFailureCallback() {
                srlRecord.finishRefresh();
                srlRecord.finishLoadMore();
            }
        });
    }

    private void setData(ExchangeRecordBean exchangeRecordBean) {
        if (exchangeRecordBean.getContent().size() == 0) {
            if (pageNo == 1) {
                no_text_tv.setVisibility(View.VISIBLE);
                lvRecord.setVisibility(View.GONE);
            } else {
                srlRecord.finishLoadMoreWithNoMoreData();
            }
            srlRecord.finishRefresh();
            srlRecord.finishLoadMore();
            return;
        }

        if (pageNo == 1) {
            list.clear();
            list.addAll(exchangeRecordBean.getContent());
            srlRecord.finishRefresh();
            srlRecord.setEnableLoadMore(true);
            adapter = new ExchangeRecordAdapter(mContext, list);
            lvRecord.setAdapter(adapter);
        } else {
            list.addAll(exchangeRecordBean.getContent());
            srlRecord.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getExchangeRecord();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getExchangeRecord();
    }
}
