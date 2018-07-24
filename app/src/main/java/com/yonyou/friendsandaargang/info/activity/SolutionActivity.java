package com.yonyou.friendsandaargang.info.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/4/11.
 */

public class SolutionActivity extends BaseActivity {


    private String title;
    private String content;


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("查看解决方案");
        initviews();
    }

    private void initviews() {

        if (getIntent().getStringExtra("title") != null) {
            tv_title.setText(getIntent().getStringExtra("title"));
        }

        if (getIntent().getStringExtra("content") != null) {
            tv_content.setText(getIntent().getStringExtra("content"));
        }
    }
}
