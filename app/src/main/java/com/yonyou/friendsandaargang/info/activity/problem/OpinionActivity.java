package com.yonyou.friendsandaargang.info.activity.problem;

import android.os.Bundle;
import android.view.View;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 意见反馈
 */

public class OpinionActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("求助反馈");
    }


    //提交  监听事件
    @OnClick({R.id.back_lay, R.id.wtqz_ray, R.id.yjfk_ray})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_lay:
                finish();
                break;
            case R.id.wtqz_ray:
                ActivityManger.skipActivity(mContext, SeekHelpActivity.class);
                break;
            case R.id.yjfk_ray:
                ActivityManger.skipActivity(mContext, FeedbackActivity.class);
                break;
        }
    }
}
