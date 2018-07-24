package com.yonyou.friendsandaargang.info.activity.gold;

import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 有金所
 */

public class GoldActivity extends BaseActivity {


    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold);

        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("有金所");
    }
}
