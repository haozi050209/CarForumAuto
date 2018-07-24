package com.yonyou.friendsandaargang.info.activity.grade;

import android.os.Bundle;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 等级权益
 */

public class GradeQuanYiTherrActivity extends BaseActivity {


    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_quanyi);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("等级权益");
    }


}
