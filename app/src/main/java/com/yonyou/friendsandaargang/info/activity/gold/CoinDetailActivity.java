package com.yonyou.friendsandaargang.info.activity.gold;

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
import com.yonyou.friendsandaargang.info.adapter.CoinDetailAdapter;
import com.yonyou.friendsandaargang.info.bean.CoinDetailBean;
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

public class CoinDetailActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.lv_coindetail)
    ListView lvCoindetail;
    @BindView(R.id.srl_coindetail)
    SmartRefreshLayout srlCoindetail;
    @BindView(R.id.no_text_tv)
    TextView no_text_tv;

    private String userId;
    private int pageNo = 1;
    private List<CoinDetailBean.ContentBean> list;
    private CoinDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
        getUserCoinDetail();
    }

    private void initView() {
        getTitleBar();
        setTitleText("金币明细");
        list = new ArrayList<>();
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        srlCoindetail.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        srlCoindetail.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
    }

    private void initListener() {
        srlCoindetail.setOnRefreshListener(this);
        srlCoindetail.setOnLoadMoreListener(this);
    }

    private void getUserCoinDetail() {
        Call<CoinDetailBean> call = communityService().getUserCoinDetail(userId, pageNo);
        NetUtils<CoinDetailBean> netUtils = new NetUtils<CoinDetailBean>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<CoinDetailBean>() {
            @Override
            public void onResponseCallback(CoinDetailBean coinDetailBean) {
                if (coinDetailBean.getReturnCode() != 0 && !coinDetailBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(coinDetailBean.getReturnMsg());
                    no_text_tv.setVisibility(View.VISIBLE);
                    lvCoindetail.setVisibility(View.GONE);
                    return;
                }
                setData(coinDetailBean);
            }

            @Override
            public void onFailureCallback() {
                srlCoindetail.finishRefresh();
                srlCoindetail.finishLoadMore();
            }
        });
    }

    private void setData(CoinDetailBean coinDetailBean) {
        if (coinDetailBean.getContent().size() == 0) {
            if (pageNo == 1) {
                no_text_tv.setVisibility(View.VISIBLE);
                lvCoindetail.setVisibility(View.GONE);
                srlCoindetail.setEnableLoadMore(false);
            } else {
                srlCoindetail.finishLoadMoreWithNoMoreData();
            }
            srlCoindetail.finishRefresh();
            srlCoindetail.finishLoadMore();
            return;
        }

        if (pageNo == 1) {
            list.clear();
            list.addAll(coinDetailBean.getContent());
            srlCoindetail.finishRefresh();
            srlCoindetail.setEnableLoadMore(true);
            adapter = new CoinDetailAdapter(mContext, list);
            lvCoindetail.setAdapter(adapter);
        } else {
            list.addAll(coinDetailBean.getContent());
            srlCoindetail.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getUserCoinDetail();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getUserCoinDetail();
    }
}
