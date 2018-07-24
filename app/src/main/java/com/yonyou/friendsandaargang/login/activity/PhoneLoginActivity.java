package com.yonyou.friendsandaargang.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.guide.activity.MainActivity;
import com.yonyou.friendsandaargang.login.modle.LoginUserInfo;
import com.yonyou.friendsandaargang.login.modle.ThirdLogin;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SDCardUtil;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.io.File;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;


public class PhoneLoginActivity extends BaseActivity implements
        View.OnClickListener, TextWatcher, View.OnTouchListener, OssService.picResultCallback {

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

    private static final String TAG = "PhoneLoginActivity";
    private String phoneText;
    private String codeText;
    private TimeCount time;
    private String registrationId;
    private String openid;
    private String thirdname;
    private String iconurl;
    private String avatarname;
    private String mobile;
    private String verifyCode;
    private Bundle bundle;
    private String path;

    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        ButterKnife.bind(this);
        ossService = initOSS(endpoint, bucketName);
        initEvent();
    }


    private void initEvent() {
        getTitleBar();
        setTitleText("登录").rightTv("注册");
        registrationId = SPTool.getContent(mContext, Constants.REGISTRATIONID);
        code.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
        /**
         * 密码监听
         */
        mEtPassword.addTextChangedListener(this);
        mScrollView.setOnTouchListener(this);
    }


    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.phone_login, R.id.username_login, R.id.titleBar_right_text, R.id.back_lay, R.id.ll_wechat, R.id.ll_qq})
    public void onClick(View v) {
        phoneText = phone_EtMobile.getText().toString();
        codeText = mEtPassword.getText().toString();
        switch (v.getId()) {
            //登陆按钮
            case R.id.phone_login:
                if (TextUtils.isEmpty(phoneText) || !com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(phoneText)) {
                    ToastUtils.normal(mContext, "手机号码有误").show();
                    return;
                }
                if (TextUtils.isEmpty(codeText)) {
                    ToastUtils.normal(mContext, "验证码为空").show();
                    return;
                }
                getPhonelogin(phoneText, codeText);
                break;
            //用户名与密码切换登陆
            case R.id.username_login:
                ActivityManger.skipActivityAndFinish(PhoneLoginActivity.this, ActivityLogin.class);
                break;
            //发送验证码
            case R.id.phone_code:
                if (TextUtils.isEmpty(phoneText) || !com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(phoneText)) {
                    ToastUtils.normal(mContext, "手机号码有误").show();
                    return;
                }

                getCode(phoneText);
                break;
            //注册
            case R.id.titleBar_right_text:
                Intent intent = new Intent(PhoneLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            //微信登录
            case R.id.ll_wechat:
                //之所以要删除授权，是因为每次都拉取对应授权页
                //否则是直接授权并不能切换其他账号登入
                umengDeleteOauth(SHARE_MEDIA.WEIXIN);
                //开始授权
                shareLoginUmeng(this, SHARE_MEDIA.WEIXIN);
                break;
            //QQ登录
            case R.id.ll_qq:
                umengDeleteOauth(SHARE_MEDIA.QQ);
                shareLoginUmeng(this, SHARE_MEDIA.QQ);
                break;
        }
    }

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
                code.setBackgroundResource(R.drawable.shape_code_garly);
                ToastUtils.normal(mContext, "验证码已发送，请注意查收").show();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }


    /**
     * 登陆  进入首页
     */
    private void getPhonelogin(String phone, String code) {
        Call<HttpResult<LoginUserInfo>> call = communityService().getCodeLogin(phone, code,
                "10241002", registrationId);
        NetUtils<HttpResult<LoginUserInfo>> netUtils = new NetUtils<HttpResult<LoginUserInfo>>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<LoginUserInfo>>() {
            @Override
            public void onResponseCallback(HttpResult<LoginUserInfo> httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                //登陆成功  保存登陆状态   保存登陆信息
                SPTool.putContent(mContext, Constants.BIRTHDAY, httpResult.getContent().getBirthday());
                SPTool.putContent(mContext, Constants.GENDER, httpResult.getContent().getGender() + "");
                SPTool.putContent(mContext, Constants.USER_ID, httpResult.getContent().getUserId());
                SPTool.putContent(mContext, Constants.USER_NAME, httpResult.getContent().toString());
                SPTool.putContent(mContext, Constants.MOBILE, httpResult.getContent().getMobile());
                SPTool.putInt(mContext, Constants.FIRSTTIMELOGON, httpResult.getContent().getFirstTimeLogon());
                SPTool.putBoolean(mContext, Constants.ISLOGIN, true);
                ActivityManger.skipActivityAndFinish(PhoneLoginActivity.this, MainActivity.class);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();

            }
        });
    }


    /**
     * 友盟开始授权(登入)
     */
    public void shareLoginUmeng(final Activity activity, final SHARE_MEDIA share_media_type) {
        UMShareAPI.get(activity).getPlatformInfo(activity, share_media_type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.e(TAG, "onStart授权开始: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //sdk是6.4.5的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                //微博没有
                openid = map.get("openid");
                //名称
                thirdname = map.get("name");
                //头像地址
                iconurl = map.get("iconurl");
                //头像文件名 OSS key
                avatarname = "user-avatar/avatar_" + TimeUtil.getDateTimeFromMil(System.currentTimeMillis()) + ".png";

                if (TextUtils.isEmpty(openid) || TextUtils.isEmpty(iconurl) || TextUtils.isEmpty(thirdname)) {
                    ToastUtils.normal(mContext, "授权失败，请检查网络后重试").show();
                    return;
                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //下载头像保存在本地
                        path = SDCardUtil.saveToSdCard(SDCardUtil.returnBitMap(iconurl));

                        Logger.e("---path----", path);

                        //头像上传到OSS
                        ossService.asyncPutImage(avatarname, path);
                    }
                }).start();

                bundle = new Bundle();
                bundle.putString("openid", openid);
                bundle.putString("name", thirdname);
                bundle.putString("iconurl", iconurl);
                bundle.putString("avatarname", avatarname);

                if (TextUtils.isEmpty(registrationId)) {
                    registrationId = JPushInterface.getRegistrationID(mContext);
                    if (TextUtils.isEmpty(registrationId)) {
                        ToastUtils.normal(mContext, "请退出应用并检查网络后重试").show();
                        return;
                    }
                }

                if (share_media_type.equals(SHARE_MEDIA.WEIXIN)) {
                    bundle.putString("loginstyle", "微信");
                    getWeChatLogin(openid);
                } else if (share_media_type.equals(SHARE_MEDIA.QQ)) {
                    bundle.putString("loginstyle", "QQ");
                    getQQLogin(openid);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(activity, "授权失败", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(activity, "授权取消", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * 友盟取消授权（登入）
     */
    private void umengDeleteOauth(SHARE_MEDIA share_media_type) {

        UMShareAPI.get(this).deleteOauth(this, share_media_type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                //开始授权
                Log.e(TAG, "onStart: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //取消授权成功 i=1
                Log.e(TAG, "onComplete: ");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                //授权出错
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                //取消授权
                Log.e(TAG, "onCancel: ");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 微信登录
     *
     * @param openid
     */
    private void getWeChatLogin(String openid) {
        Call<HttpResult<ThirdLogin>> call = communityService().getWeChatLogin(openid, mobile, verifyCode, "10241002", registrationId, "", "");
        NetUtils<HttpResult<ThirdLogin>> netUtils = new NetUtils<>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<ThirdLogin>>() {
            @Override
            public void onResponseCallback(HttpResult<ThirdLogin> model) {
                try {
                    if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                        SPTool.putContent(mContext, Constants.GENDER, model.getContent().getGender() + "");
                        SPTool.putContent(mContext, Constants.USER_ID, model.getContent().getUserId());
                        SPTool.putContent(mContext, Constants.USER_NAME, model.getContent().getUserName());
                        SPTool.putContent(mContext, Constants.MOBILE, model.getContent().getMobile());
                        SPTool.putInt(mContext, Constants.FIRSTTIMELOGON, model.getContent().getFirstTimeLogon());
                        SPTool.putBoolean(mContext, Constants.ISLOGIN, true);
                        SPTool.putBoolean(mContext, "wechathadbind", true);
                        ActivityManger.skipActivityAndFinish(mContext, MainActivity.class);

                    } else if (model.getReturnCode() == 10000018) { //第一次登录 跳到验证页面完善资料绑定
                        ActivityManger.skipActivityAndFinish(mContext, ThirdLoginComplInfoActivity.class, bundle);

                    } else {
                        ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();

            }
        });
    }


    /**
     * qq登录
     *
     * @param openid
     */
    private void getQQLogin(String openid) {
        Call<HttpResult<ThirdLogin>> call = communityService().getQQLogin(openid, mobile, verifyCode, "10241002", registrationId, "", "");
        NetUtils<HttpResult<ThirdLogin>> netUtils = new NetUtils<>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<ThirdLogin>>() {
            @Override
            public void onResponseCallback(HttpResult<ThirdLogin> model) {
                try {
                    if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                        SPTool.putContent(mContext, Constants.GENDER, model.getContent().getGender() + "");
                        SPTool.putContent(mContext, Constants.USER_ID, model.getContent().getUserId());
                        SPTool.putContent(mContext, Constants.USER_NAME, model.getContent().getUserName());
                        SPTool.putContent(mContext, Constants.MOBILE, model.getContent().getMobile());
                        SPTool.putInt(mContext, Constants.FIRSTTIMELOGON, model.getContent().getFirstTimeLogon());
                        SPTool.putBoolean(mContext, Constants.ISLOGIN, true);
                        SPTool.putBoolean(mContext, "qqhadbind", true);
                        ActivityManger.skipActivityAndFinish(mContext, MainActivity.class);

                    } else if (model.getReturnCode() == 10000018) { //第一次登录 跳到验证页面完善资料绑定
                        ActivityManger.skipActivityAndFinish(mContext, ThirdLoginComplInfoActivity.class, bundle);

                    } else {
                        ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    @Override
    public void getPicData(PutObjectRequest data) {
        SDCardUtil.deleteFile(path);
    }

    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {
//        ToastUtils.normal(mContext, "头像上传失败").show();
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


}
