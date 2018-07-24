package com.yonyou.friendsandaargang.info.activity.operationmanager;

import android.os.Bundle;
import android.view.View;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperationManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_manager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getTitleBar();
        setTitleText("运营管家");
    }

    @OnClick({R.id.rl_newproblem, R.id.rl_queryquestion, R.id.rl_smsrecharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_newproblem://新建问题
                ActivityManger.skipActivity(mContext, NewProblemActivity.class);
                break;
            case R.id.rl_queryquestion://查询问题
                ActivityManger.skipActivity(mContext, QueryQuestionActivity.class);
                break;
            case R.id.rl_smsrecharge://短信充值
                ActivityManger.skipActivity(mContext, SMSRechargeActivity.class);
                break;
        }
    }
}
