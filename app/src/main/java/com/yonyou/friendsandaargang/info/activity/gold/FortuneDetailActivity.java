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
import com.yonyou.friendsandaargang.info.adapter.FortuneDetailAdapter;
import com.yonyou.friendsandaargang.info.bean.FortuneDetailBean;
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

public class FortuneDetailActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.lv_fortunedetail)
    ListView lvFortunedetail;
    @BindView(R.id.srl_fortunedetail)
    SmartRefreshLayout srlFortunedetail;
    @BindView(R.id.no_text_tv)
    TextView no_text_tv;

    private String userId;
    private int pageNo = 1;
    private List<FortuneDetailBean.ContentBean> list;
    private FortuneDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortune_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
        getUserFortuneDetail();
    }

    private void initView() {
        getTitleBar();
        setTitleText("财富值明细");
        list = new ArrayList<>();
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        srlFortunedetail.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        srlFortunedetail.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
    }

    private void initListener() {
        srlFortunedetail.setOnRefreshListener(this);
        srlFortunedetail.setOnLoadMoreListener(this);
    }

    private void getUserFortuneDetail() {
        Call<FortuneDetailBean> call = communityService().getUserFortuneDetail(userId, pageNo);
        NetUtils<FortuneDetailBean> netUtils = new NetUtils<FortuneDetailBean>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<FortuneDetailBean>() {
            @Override
            public void onResponseCallback(FortuneDetailBean fortuneDetailBean) {
                if (fortuneDetailBean.getReturnCode() != 0 && !fortuneDetailBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(fortuneDetailBean.getReturnMsg());
                    no_text_tv.setVisibility(View.VISIBLE);
                    lvFortunedetail.setVisibility(View.GONE);
                    return;
                }
                setData(fortuneDetailBean);
            }

            @Override
            public void onFailureCallback() {
                srlFortunedetail.finishRefresh();
                srlFortunedetail.finishLoadMore();
            }
        });
    }

    private void setData(FortuneDetailBean fortuneDetailBean) {
        if (fortuneDetailBean.getContent().size() == 0) {
            if (pageNo == 1) {
                no_text_tv.setVisibility(View.VISIBLE);
                lvFortunedetail.setVisibility(View.GONE);
                srlFortunedetail.setEnableLoadMore(false);
            } else {
                srlFortunedetail.finishLoadMoreWithNoMoreData();
            }
            srlFortunedetail.finishRefresh();
            srlFortunedetail.finishLoadMore();
            return;
        }

        if (pageNo == 1) {
            list.clear();
            list.addAll(fortuneDetailBean.getContent());
            srlFortunedetail.finishRefresh();
            srlFortunedetail.setEnableLoadMore(true);
            srlFortunedetail.setEnableRefresh(true);
            adapter = new FortuneDetailAdapter(mContext, list);
            lvFortunedetail.setAdapter(adapter);
        } else {
            list.addAll(fortuneDetailBean.getContent());
            srlFortunedetail.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getUserFortuneDetail();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getUserFortuneDetail();
    }
}
