package com.yonyou.friendsandaargang.info.activity.operationmanager;

import android.os.Bundle;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;

public class SMSRechargeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsrecharge);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getTitleBar();
        setTitleText("短信充值");
    }
}
