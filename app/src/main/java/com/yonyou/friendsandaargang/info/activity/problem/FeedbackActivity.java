package com.yonyou.friendsandaargang.info.activity.problem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/4/11.
 */

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.yifk_edit)
    EditText yifk_edit;
    @BindView(R.id.yjfk_but)
    Button yjfk_but;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("意见反馈");
        initviews();
    }

    private void initviews() {
        userid = SPTool.getContent(mContext, Constants.USER_ID);
        yjfk_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String advice = yifk_edit.getText().toString();
                if (android.text.TextUtils.isEmpty(advice)) {
                    ToastUtils.normal(mContext, "请输入反馈内容").show();
                    return;
                }
                getCommitFeedback(advice);
            }
        });
    }

    private void getCommitFeedback(String advice) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getsaveUserAdvice(userid, advice);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, "提交成功").show();
                    finish();
                } else {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器异常").show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });

    }
}
