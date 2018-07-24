package com.yonyou.friendsandaargang.info.activity;

import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/28.
 *
 * 车商贷
 */

public class CarMerChantLoanActivity extends BaseActivity {


    private int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmerchantloan);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("车商贷");
    }
}
