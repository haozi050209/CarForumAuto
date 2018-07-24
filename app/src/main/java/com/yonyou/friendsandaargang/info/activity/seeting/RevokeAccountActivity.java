package com.yonyou.friendsandaargang.info.activity.seeting;

import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 注销账户
 */

public class RevokeAccountActivity extends BaseActivity {
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revoke_accoun);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("账户注销");
    }
}
