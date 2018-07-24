package com.yonyou.friendsandaargang.login.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.login.modle.CodeBean;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/3/6.
 */

public class ForgetPassWordActivity extends BaseActivity implements TextWatcher {


    @BindView(R.id.forget_phone)
    EditText forget_phone;
    @BindView(R.id.forget_put_code)
    EditText forget_put_code;
    @BindView(R.id.forget_obtain_code)
    TextView forget_obtain_code;
    @BindView(R.id.button)
    Button button_regiter;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("忘记密码");
        time = new TimeCount(60000, 1000);
        forget_put_code.addTextChangedListener(this);
    }


    @OnClick({R.id.forget_obtain_code, R.id.button})
    public void Onclick(View view) {
        String mobile = forget_phone.getText().toString();
        String code = forget_put_code.getText().toString();
        switch (view.getId()) {
            //发送验证码
            case R.id.forget_obtain_code:

                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.normal(mContext, "请输入手机号码").show();
                    return;
                }
                if (com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(mobile)) {
                    ToastUtils.normal(mContext, "手机号码有误").show();
                    return;
                }
                getCode(mobile);
                break;
            //重置密码
            case R.id.button:
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.normal(mContext, "请输入手机号码").show();
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.normal(mContext, "请输入验证码").show();
                    return;
                }
                getresetPasswordData(mobile, code);
                break;
        }
    }

    /**
     * 发送验证码
     */
    /**
     * 发送验证码
     */
    private void getCode(String phone) {
        Call<HttpResult> call = communityService().getSendCode(phone);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                time.start();
                forget_obtain_code.setBackgroundResource(R.drawable.shape_code_garly);
                ToastUtils.normal(mContext, "验证码已发送，请注意查收").show();
            }

            @Override
            public void onFailureCallback() {

            }
        });

    }


    /**
     * 重置密码
     */

    private void getresetPasswordData(final String mobile, String verifyCode) {
        Call<HttpResult> call = communityService().getresetPassword(mobile, verifyCode);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }

                ToastUtils.normal(mContext, "新密码已发送至您手机，请注意查收").show();
                ActivityManger.skipActivityAndFinish(ForgetPassWordActivity.this, ActivityLogin.class);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty() && s.toString().length() < 6) {
            button_regiter.setEnabled(false);
        } else {
            button_regiter.setEnabled(true);
            button_regiter.setBackgroundResource(R.drawable.shape_follow_red);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /*60秒倒计时*/
    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            forget_obtain_code.setClickable(false);
            forget_obtain_code.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            forget_obtain_code.setText("获取验证码");
            forget_obtain_code.setClickable(true);
            forget_obtain_code.setBackgroundResource(R.drawable.shape_follow_red);
        }
    }
}
