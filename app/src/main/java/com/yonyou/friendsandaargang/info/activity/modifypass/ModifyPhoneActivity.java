package com.yonyou.friendsandaargang.info.activity.modifypass;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 验证手机号
 */

public class ModifyPhoneActivity extends BaseActivity {
    private int color;


    @BindView(R.id.yanzhen_phone)
    TextView phone;
    @BindView(R.id.yanzhen_code)
    EditText code;

    @BindView(R.id.button_yanzhen)
    Button button_yanzhen;

    @BindView(R.id.yanzhen_obtain_code)
    Button yanzhen_obtain_code;


    private String telPhone;

    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("修改手机");
        time = new TimeCount(60000, 1000);
        telPhone = SPTool.getContent(mContext, Constants.MOBILE);
        phone.setText(telPhone);

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = s.toString();
                if (s.toString().isEmpty() || s.length() < 6) {
                    button_yanzhen.setBackgroundResource(R.drawable.shape_phone_garly);
                    button_yanzhen.setEnabled(false);
                } else {
                    button_yanzhen.setBackgroundResource(R.drawable.shape_follow_red);
                    button_yanzhen.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @OnClick({R.id.button_yanzhen, R.id.yanzhen_obtain_code})
    public void getOnClick(View view) {
        switch (view.getId()) {
            //发送验证码
            case R.id.yanzhen_obtain_code:
                if (android.text.TextUtils.isEmpty(telPhone)) {
                    ToastUtils.normal(mContext, "手机号码为空").show();
                    return;
                }
                if (!com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(telPhone)) {
                    ToastUtils.normal(mContext, "手机号码有误").show();
                    return;
                }
                time.start();
                getCode(telPhone);
                break;
            //确认修改
            case R.id.button_yanzhen:
                String strcode = code.getText().toString().trim();
                getCheckVeryfyCode(telPhone, strcode);
                break;

        }
    }


    /**
     * 发送验证码
     */
    private void getCode(String phone) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getSendCode(phone);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, "验证码已发送，请注意查收").show();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onThrowable(Throwable t) {
                ToastUtils.error(t.toString());
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }


    /*60秒倒计时*/
    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            yanzhen_obtain_code.setClickable(false);
            yanzhen_obtain_code.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            yanzhen_obtain_code.setText("获取验证码");
            yanzhen_obtain_code.setClickable(true);
        }
    }


    /**
     * 验证手机号码
     */


    private void getCheckVeryfyCode(String phone, String telcode) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getcheckVeryfyCode(phone, telcode);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, "验证成功").show();
                    ActivityManger.skipActivity(ModifyPhoneActivity.this, ModifyPhoneTwoActivity.class);
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
