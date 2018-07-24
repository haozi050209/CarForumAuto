package com.yonyou.friendsandaargang.info.activity.gold;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.gold.CoinDetailActivity;
import com.yonyou.friendsandaargang.info.activity.gold.CoinRechargeActivity;
import com.yonyou.friendsandaargang.info.activity.gold.CoinWithdrawActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/5/30.
 * <p>
 * 我的金币
 */

public class GoldCoinActivity extends BaseActivity {

    @BindView(R.id.tv_leftcoin)
    TextView tvLeftcoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goldcoin);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的金币");
        rightTv("明细");
        tvLeftcoin.setText(getIntent().getStringExtra("coin"));
    }

    @OnClick({R.id.titleBar_right_text, R.id.rl_recharge, R.id.rl_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titleBar_right_text://明细
                ActivityManger.skipActivity(mContext, CoinDetailActivity.class);
                break;
            case R.id.rl_recharge://充值
                ActivityManger.skipActivity(mContext, CoinRechargeActivity.class);
                break;
            case R.id.rl_withdraw://提现
                ActivityManger.skipActivity(mContext, CoinWithdrawActivity.class);
                break;
        }
    }
}
