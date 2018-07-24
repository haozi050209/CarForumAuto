package com.yonyou.friendsandaargang.info.activity.gold;

import android.os.Bundle;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.gold.FortuneDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/5/30.
 */

public class WealthValueActivity extends BaseActivity {

    @BindView(R.id.tv_leftfortune)
    TextView tvLeftfortune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletvalue);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的财富值");
        rightTv("明细");
        tvLeftfortune.setText(getIntent().getStringExtra("fortune"));
    }

    @OnClick(R.id.titleBar_right_text)
    public void onViewClicked() {
        ActivityManger.skipActivity(mContext, FortuneDetailActivity.class);
    }
}
