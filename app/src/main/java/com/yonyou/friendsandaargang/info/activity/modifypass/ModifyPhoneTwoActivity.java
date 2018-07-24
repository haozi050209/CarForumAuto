package com.yonyou.friendsandaargang.info.activity.modifypass;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.SecurityActivity;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 修改手机号
 */

public class ModifyPhoneTwoActivity extends BaseActivity {

    @BindView(R.id.modify_phone)
    EditText modify_phone;
    @BindView(R.id.modify_code)
    EditText modify_code;
    @BindView(R.id.modify_obtain_code)
    Button modify_obtain_code;
    @BindView(R.id.modify_button)
    Button modify_button;


    private TimeCount time;

    private String phone;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone_two);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("修改手机");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        time = new TimeCount(60000, 1000);
        modify_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().isEmpty() || s.length() < 6) {
                    modify_button.setBackgroundResource(R.drawable.shape_phone_garly);
                    modify_button.setEnabled(false);
                } else {
                    modify_button.setBackgroundResource(R.drawable.shape_follow_red);
                    modify_button.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.modify_obtain_code, R.id.modify_button})
    public void getOnClick(View view) {
        switch (view.getId()) {
            case R.id.modify_obtain_code:
                phone = modify_phone.getText().toString();

                if (phone.isEmpty()) {
                    ToastUtils.normal(mContext, "手机号码不能为空").show();
                    return;
                }
                if (!TextUtils.isChinaPhoneLegal(phone)) {
                    ToastUtils.normal(mContext, "请输入正确手机号码").show();
                    return;
                }
                time.start();
                getCode(phone);
                break;
            case R.id.modify_button:
                String code = modify_code.getText().toString();
                getupdateUserPhoneNumber(phone, code);
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
                ToastUtils.error(msg.toString());
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
            modify_obtain_code.setClickable(false);
            modify_obtain_code.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish() {
            modify_obtain_code.setText("重新获取验证码");
            modify_obtain_code.setClickable(true);
        }
    }


    /**
     * 修改手机号码
     */

    private void getupdateUserPhoneNumber(String tel, String telcode) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getupdateUserPhoneNumber(userId, tel, telcode);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {

                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, "绑定成功").show();
                    ActivityManger.skipActivityAndFinish(mContext, SecurityActivity.class);
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
