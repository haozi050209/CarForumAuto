package com.yonyou.friendsandaargang.info.activity.seeting;

import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 关于我们
 */

public class AboutActivity extends BaseActivity {


    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_about);

        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("关于我们");
        initviews();
    }

    private void initviews() {
    }

}
