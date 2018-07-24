package com.yonyou.friendsandaargang.login.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.security.Security;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/27.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, TextWatcher, View.OnTouchListener {


    @BindView(R.id.user_name)
    EditText name;
    @BindView(R.id.user_code)
    EditText user_code;
    @BindView(R.id.user_password)
    EditText password;
    @BindView(R.id.user_passworetwo)
    EditText passworetwo;
    @BindView(R.id.user_phone)
    EditText phone;
    @BindView(R.id.user_sendcode)
    TextView sendcode;
    @BindView(R.id.regiter_tv)
    Button regiter;

    @BindView(R.id.rehister_scrollView)
    NestedScrollView scrollView;

    //清除密码
    @BindView(R.id.user_clean_password)
    ImageView user_clean_password;

    //再次清除密码
    @BindView(R.id.clean_password_two)
    ImageView clean_password_two;


    //显示 or隐藏密码

    @BindView(R.id.user_iv_show_pwd)
    ImageView user_iv_show_pwd;

    @BindView(R.id.iv_show_pwd_two)
    ImageView iv_show_pwd_two;
    @BindView(R.id.checkbox)
    CheckBox checkBox;


    private String username_text;
    private String mobile_text;
    private String password_text;
    private String password_text_two;
    private String verifyCode_text;

    private TimeCount time;
    private DialogSureCancel SureCancel;

    private boolean isCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initviews();
    }


    /**
     * 一些基本数据处理
     */
    private void initviews() {
        getTitleBar();
        setTitleText("注册");
        time = new TimeCount(60000, 1000);
        isCheckBox = false;
        initEvnts();
        initChekbox();
    }

    /**
     * 密码监听
     */
    private void initEvnts() {
        password.addTextChangedListener(this);
        //确认密码监听
        passworetwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 8) {
                    regiter.setEnabled(false);
                } else {
                    regiter.setEnabled(true);
                    regiter.setBackgroundResource(R.drawable.shape_follow_red);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password_two.getVisibility() == View.GONE) {
                    clean_password_two.setVisibility(View.VISIBLE);

                } else if (TextUtils.isEmpty(s)) {
                    clean_password_two.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    ToastUtils.normal(mContext, "请输入数字或字母").show();
                    s.delete(temp.length() - 1, temp.length());
                    passworetwo.setSelection(s.length());
                }
            }
        });
        scrollView.setOnTouchListener(this);
    }


    /**
     * 协议单选框
     */
    private void initChekbox() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckBox = isChecked;
                if (isChecked) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        });

    }


    /**
     * 监听事件
     *
     * @param v
     */
    @SuppressLint("NewApi")
    @OnClick({R.id.regiter_tv, R.id.user_sendcode, R.id.user_clean_password
            , R.id.clean_password_two, R.id.user_iv_show_pwd, R.id.iv_show_pwd_two
            , R.id.tv_html})
    public void onClick(View v) {
        username_text = name.getText().toString();
        mobile_text = phone.getText().toString();
        password_text = password.getText().toString();
        verifyCode_text = user_code.getText().toString();
        password_text_two = passworetwo.getText().toString();
        switch (v.getId()) {
            case R.id.regiter_tv:
                if (TextUtils.isEmpty(username_text)) {
                    ToastUtils.normal(mContext, "用户名不能为空").show();
                    return;
                }
                if (TextUtils.isEmpty(mobile_text)) {
                    ToastUtils.normal(mContext, "请输入手机号码").show();
                    return;
                }
                if (TextUtils.isEmpty(verifyCode_text)) {
                    ToastUtils.normal(mContext, "验证码为空").show();
                    return;
                }
                if (TextUtils.isEmpty(password_text)) {
                    ToastUtils.normal(mContext, "请设置登陆密码").show();
                    return;
                }
                if (TextUtils.isEmpty(password_text_two)) {
                    ToastUtils.normal(mContext, "请再次确认登陆密码").show();
                    return;
                }
                if (!Objects.equals(password_text, password_text_two)) {
                    ToastUtils.normal(mContext, "俩次密码不一致").show();
                    return;
                }
                if (!isCheckBox) {
                    ToastUtils.normal(mContext, "请先勾选同意").show();
                    return;
                }
                getRegistData(username_text, mobile_text, password_text, verifyCode_text);
                break;
            case R.id.user_sendcode:
                if (TextUtils.isEmpty(mobile_text)) {
                    ToastUtils.normal(mContext, "手机号码为空").show();
                    return;
                }
                if (!com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(mobile_text)) {
                    ToastUtils.normal(mContext, "手机号码有误").show();
                    return;
                }
                getverifyCode(mobile_text);
                break;
            //设置密码  清除
            case R.id.user_clean_password:
                password.setText("");
                break;
            //确认密码     清除
            case R.id.clean_password_two:
                password.setText("");
                break;
            //设置密码  显示OR  隐藏
            case R.id.user_iv_show_pwd:
                if (password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    user_iv_show_pwd.setImageResource(R.drawable.kj);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    user_iv_show_pwd.setImageResource(R.drawable.bkj);
                }
                //光标
                if (!TextUtils.isEmpty(password_text))
                    password.setSelection(password_text.length());
                break;
            //确认密码  显示
            case R.id.iv_show_pwd_two:
                if (passworetwo.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    passworetwo.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_show_pwd_two.setImageResource(R.drawable.kj);
                } else {
                    passworetwo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_show_pwd_two.setImageResource(R.drawable.bkj);
                }
                //光标
                if (!TextUtils.isEmpty(password_text_two))
                    passworetwo.setSelection(password_text_two.length());
                break;

            case R.id.tv_html:
                ActivityManger.skipActivity(mContext, RegisterLisenceActivity.class);
                break;


        }
    }


    private void getverifyCode(String phone) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getSendCode(phone);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                time.start();
                sendcode.setBackgroundResource(R.drawable.shape_code_garly);
                ToastUtils.normal(mContext, "验证码已发送，请注意查收").show();
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.error(mContext, "服务器异常，请稍后重试！").show();
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

    /**
     * 注册
     *
     * @param userName
     * @param mobile
     * @param password
     * @param verifyCode
     */
    private void getRegistData(String userName, final String mobile, String password, String verifyCode) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getRegiSter(userName, mobile, password, verifyCode);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                try {
                    if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                        showDiaglo();
                    } else if (model.getReturnCode() == 10011001) {
                        ToastUtils.normal(mContext, "验证码错误").show();
                    } else if (model.getReturnCode() == 10011002) {
                        ToastUtils.normal(mContext, "验证码过期").show();
                    } else if (model.getReturnCode() == 10011003) {
                        ToastUtils.normal(mContext, "用户名已存在").show();
                    } else if (model.getReturnCode() == 10011004) {
                        ToastUtils.normal(mContext, "手机号码已存在").show();
                    } else if (model.getReturnCode() == 10011005) {
                        ToastUtils.normal(mContext, "手机号码非法").show();
                    } else if (model.getReturnCode() == 10011006) {
                        ToastUtils.normal(mContext, "注册失败，数据库操作错误").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器异常请，请稍后重试！").show();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s) && user_clean_password.getVisibility() == View.GONE) {
            user_clean_password.setVisibility(View.VISIBLE);

        } else if (TextUtils.isEmpty(s)) {
            user_clean_password.setVisibility(View.GONE);
        }
        if (s.toString().isEmpty())
            return;
        if (!s.toString().matches("[A-Za-z0-9]+")) {
            String temp = s.toString();
            ToastUtils.normal(mContext, "请输入数字或字母").show();
            s.delete(temp.length() - 1, temp.length());
            password.setSelection(s.length());
        }
    }


    /**
     * 禁止滑动
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }


    /*60秒倒计时*/
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sendcode.setClickable(false);
            sendcode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            sendcode.setText("获取验证码");
            sendcode.setClickable(true);
            sendcode.setBackgroundResource(R.drawable.shape_follow_red);
        }

    }


    private void showDiaglo() {
        SureCancel = new DialogSureCancel(mContext);//提示弹窗
        SureCancel.setCanceledOnTouchOutside(false);
        SureCancel.getTitleView().setText("提示");
        SureCancel.getContentView().setText("注册成功，需跳转至登陆页面登陆？");
        SureCancel.getCancelView().setVisibility(View.GONE);
        SureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SureCancel.dismiss();
                ActivityManger.skipActivityAndFinish(RegisterActivity.this, ActivityLogin.class);
            }
        });
        SureCancel.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SureCancel != null) {
            SureCancel.dismiss();
        }
    }
}
