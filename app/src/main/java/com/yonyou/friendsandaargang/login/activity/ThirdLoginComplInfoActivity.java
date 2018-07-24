package com.yonyou.friendsandaargang.login.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.guide.activity.MainActivity;
import com.yonyou.friendsandaargang.login.modle.ThirdLogin;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class ThirdLoginComplInfoActivity extends BaseActivity implements View.OnClickListener {

    //手机号码
    @BindView(R.id.phone_mobile)
    EditText phone_EtMobile;
    //验证码
    @BindView(R.id.phone_password)
    EditText mEtPassword;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.phone_code)
    TextView code;

    @BindView(R.id.phone_login)
    Button phone_login;

    @BindView(R.id.tv_loginstyle)
    TextView tv_loginstyle;
    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;

    private String openid;
    private String registrationId;
    private String iconurl;
    private String thirdname;
    private String avatarname;
    private String loginstyle;
    private String phoneText;
    private String codeText;
    private Bundle bundle;

    private ThirdLoginComplInfoActivity.TimeCount time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdlogin_complinfo);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {
        getTitleBar();
        setTitleText("登录");
        registrationId = SPTool.getContent(mContext, Constants.REGISTRATIONID);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            openid = bundle.getString("openid");
            iconurl = bundle.getString("iconurl");
            thirdname = bundle.getString("name");
            loginstyle = bundle.getString("loginstyle");
            avatarname = bundle.getString("avatarname");
        }
        tv_loginstyle.setText("请完善友车帮资料，完善资料后可使用" + loginstyle + "进行登录");
        GlideManager.loadImage(mContext, iconurl, R.drawable.user, iv_avatar);
        tv_name.setText(thirdname);
    }

    private void initEvent() {

        code.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
        /**
         * Edit手机号码监听
         */
        /**
         * 密码监听
         */
        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    phone_login.setEnabled(false);
                } else {

                    phone_login.setEnabled(true);
                    phone_login.setBackgroundResource(R.drawable.shape_follow_red);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(mContext, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    mEtPassword.setSelection(s.length());
                }
            }
        });
        /**
         * 禁止键盘弹起的时候可以滚动
         */
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }


    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.phone_login, R.id.phone_code})
    public void onClick(View v) {
        phoneText = phone_EtMobile.getText().toString().trim();
        codeText = mEtPassword.getText().toString().trim();
        switch (v.getId()) {
            //登陆按钮
            case R.id.phone_login:
                if (TextUtils.isEmpty(phoneText)) {
                    ToastUtils.normal(mContext, "手机号码不能为空").show();
                    return;
                }
                if (!com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(phoneText)) {
                    ToastUtils.normal(mContext, "手机号码有误").show();
                    return;
                }
                if (TextUtils.isEmpty(codeText)) {
                    ToastUtils.normal(mContext, "验证码为空").show();
                    return;
                }
                getThirdLogin(phoneText, codeText);
                break;
            //发送验证码
            case R.id.phone_code:
                if (TextUtils.isEmpty(phoneText)) {
                    ToastUtils.normal(mContext, "手机号码为空").show();
                    return;
                }
                if (!com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(phoneText)) {
                    ToastUtils.normal(mContext, "手机号码有误").show();
                    return;
                }

                getCode(phoneText);
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void getCode(String phone) {
        final Call<HttpResult> call = communityService().getSendCode(phone);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                time.start();
                code.setBackgroundResource(R.drawable.shape_code_garly);
                ToastUtils.normal(mContext, "验证码已发送，请注意查收").show();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }

    private void getThirdLogin(String phone, String code) {

        if (TextUtils.isEmpty(registrationId)) {
            registrationId = JPushInterface.getRegistrationID(mContext);
            if (TextUtils.isEmpty(registrationId)) {
                ToastUtils.normal(mContext, "请退出应用并检查网络后重试").show();
                return;
            }
        }

        if ("微信".equals(loginstyle)) {
            getWeChatLogin(openid, phone, code, registrationId, avatarname, thirdname);
        } else if ("QQ".equals(loginstyle)) {
            getQQLogin(openid, phone, code, registrationId, avatarname, thirdname);
        }
    }


    /*60秒倒计时*/
    public class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            code.setClickable(false);
            code.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            code.setText("获取验证码");
            code.setClickable(true);
            code.setBackgroundResource(R.drawable.shape_follow_red);
        }
    }


    /**
     * qq登录
     */
    private void getQQLogin(String openid, String phoneText, String codeText, String registrationId, String avatarname, String thirdname) {
        Call<HttpResult<ThirdLogin>> call = communityService().getQQLogin(openid, phoneText, codeText,
                "10241002", registrationId, avatarname, thirdname);
        NetUtils<HttpResult<ThirdLogin>> netUtils = new NetUtils<>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<ThirdLogin>>() {
            @Override
            public void onResponseCallback(HttpResult<ThirdLogin> model) {
                try {
                    if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                        ToastUtils.normal(mContext, model.getReturnMsg()).show();
                        return;
                    }

                    SPTool.putContent(mContext, Constants.GENDER, model.getContent().getGender() + "");
                    SPTool.putContent(mContext, Constants.USER_ID, model.getContent().getUserId());
                    SPTool.putContent(mContext, Constants.USER_NAME, model.getContent().getUserName());
                    SPTool.putContent(mContext, Constants.MOBILE, model.getContent().getMobile());
                    SPTool.putInt(mContext, Constants.FIRSTTIMELOGON, model.getContent().getFirstTimeLogon());
                    SPTool.putBoolean(mContext, Constants.ISLOGIN, true);
                    SPTool.putBoolean(mContext, "qqhadbind", true);
                    ActivityManger.skipActivityAndFinish(mContext, MainActivity.class);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    /**
     * 微信登录
     */
    private void getWeChatLogin(String openid, String phoneText, String codeText, String registrationId, String avatarname, String thirdname) {
        Call<HttpResult<ThirdLogin>> call = communityService().getWeChatLogin(openid, phoneText, codeText,
                "10241002", registrationId, avatarname, thirdname);
        NetUtils<HttpResult<ThirdLogin>> netUtils = new NetUtils<>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<ThirdLogin>>() {
            @Override
            public void onResponseCallback(HttpResult<ThirdLogin> model) {
                try {
                    if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                        ToastUtils.normal(mContext, model.getReturnMsg()).show();
                        return;
                    }

                    SPTool.putContent(mContext, Constants.GENDER, model.getContent().getGender() + "");
                    SPTool.putContent(mContext, Constants.USER_ID, model.getContent().getUserId());
                    SPTool.putContent(mContext, Constants.USER_NAME, model.getContent().getUserName());
                    SPTool.putContent(mContext, Constants.MOBILE, model.getContent().getMobile());
                    SPTool.putInt(mContext, Constants.FIRSTTIMELOGON, model.getContent().getFirstTimeLogon());
                    SPTool.putBoolean(mContext, Constants.ISLOGIN, true);
                    SPTool.putBoolean(mContext, "wechathadbind", true);
                    ActivityManger.skipActivityAndFinish(mContext, MainActivity.class);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }
}
