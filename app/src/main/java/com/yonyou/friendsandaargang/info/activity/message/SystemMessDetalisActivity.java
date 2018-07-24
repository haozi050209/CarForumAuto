package com.yonyou.friendsandaargang.info.activity.message;

import android.os.Bundle;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/7/9.
 */

public class SystemMessDetalisActivity extends BaseActivity {


    @BindView(R.id.systemdetals_title)
    TextView tvTitle;
    @BindView(R.id.systemdetals_content)
    TextView tvContext;


    private String title, context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemdetalis);
        ButterKnife.bind(this);
        initviews();
    }

    //初始化
    private void initviews() {
        getTitleBar();
        setTitleText("系统消息");
        title = getIntent().getStringExtra(Constants.TITLE);
        context = getIntent().getStringExtra(Constants.CONTEXT);
        tvTitle.setText(title);
        tvContext.setText(context);

    }
}
