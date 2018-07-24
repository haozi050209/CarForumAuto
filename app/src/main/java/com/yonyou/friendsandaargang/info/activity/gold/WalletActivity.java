package com.yonyou.friendsandaargang.info.activity.gold;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.RecordActivity;
import com.yonyou.friendsandaargang.info.adapter.WalletAdapter;
import com.yonyou.friendsandaargang.info.bean.ExchangeBean;
import com.yonyou.friendsandaargang.info.bean.UserFortuneBean;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的钱包
 */

public class WalletActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private String userId;
    @BindView(R.id.wallet_jb_tv)
    TextView tvJb;
    @BindView(R.id.wallet_cfz_tv)
    TextView tvCfz;
    @BindView(R.id.wallet_jl_tv)
    TextView tvJl;
    @BindView(R.id.wallet_gridview)
    GridView gridView;
    @BindView(R.id.no_text_tv)
    TextView no_text_tv;

    private WalletAdapter adapter;
    private List<ExchangeBean.ContentBean> list;
    private ExchangeBean.ContentBean exchangeBean;

    private String coin;
    private String fortune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        initviews();
        getUserFortuneAndCoin();
        getExchangeItemList();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的钱包");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        gridView.setOnItemClickListener(this);
    }


    @OnClick({R.id.wallet_jb_ray, R.id.wallet_cfz_ray, R.id.wallet_jl_ray})
    public void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.wallet_jb_ray://金币
                intent = new Intent(mContext, GoldCoinActivity.class);
                intent.putExtra("coin", coin);
                startActivity(intent);
                break;
            case R.id.wallet_cfz_ray://财富值
                intent = new Intent(mContext, WealthValueActivity.class);
                intent.putExtra("fortune", fortune);
                startActivity(intent);
                break;
            case R.id.wallet_jl_ray://兑换记录
                ActivityManger.skipActivity(mContext, RecordActivity.class);
                break;
        }
    }


    /**
     * 获取用户财富值和金币值
     */
    private void getUserFortuneAndCoin() {
        Call<HttpResult<UserFortuneBean>> call = communityService().getUserFortuneAndCoin(userId);
        NetUtils<HttpResult<UserFortuneBean>> utils = new NetUtils<HttpResult<UserFortuneBean>>(this);
        utils.enqueue(call, new ResponseCallBack<HttpResult<UserFortuneBean>>() {
            @Override
            public void onResponseCallback(HttpResult<UserFortuneBean> userFortune) {
                if (!userFortune.getReturnMsg().equals("success") && userFortune.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, userFortune.getReturnMsg()).show();
                    return;
                }
                coin = userFortune.getContent().getCoin();
                fortune = userFortune.getContent().getFortune();
                //保存金币值
                SPTool.putContent(mContext, Constants.GOLD, coin);
                tvJb.setText(coin);
                tvCfz.setText(fortune);
                tvJl.setText("我的财富值：" + fortune);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    /**
     * 获取可兑换的物品列表
     */

    private void getExchangeItemList() {
        Call<ExchangeBean> call = communityService().getExchangeItemList();
        NetUtils<ExchangeBean> netUtils = new NetUtils<ExchangeBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<ExchangeBean>() {
            @Override
            public void onResponseCallback(ExchangeBean exchangeBean) {
                if (!exchangeBean.getReturnMsg().equals("success") && exchangeBean.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, exchangeBean.getReturnMsg()).show();
                    no_text_tv.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);
                    return;
                }
                setData(exchangeBean);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    private void setData(ExchangeBean exchangeBean) {
        list = exchangeBean.getContent();
        if (list.size() == 0) {
            no_text_tv.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            return;
        }
        gridView.setVisibility(View.VISIBLE);
        adapter = new WalletAdapter(mContext, list);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        exchangeBean = list.get(position);
        Intent intent = new Intent(mContext, ProductDetalisActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) exchangeBean);
        intent.putExtra("bundle", bundle);
        startActivity(intent);

    }
}
