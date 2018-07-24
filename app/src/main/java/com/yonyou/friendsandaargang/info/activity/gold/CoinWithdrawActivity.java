package com.yonyou.friendsandaargang.info.activity.gold;

import android.os.Bundle;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;

public class CoinWithdrawActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_withdraw);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getTitleBar();
        setTitleText("金币提现");
    }
}
