package com.yonyou.friendsandaargang.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.guide.activity.MainActivity;
import com.yonyou.friendsandaargang.login.modle.Login;
import com.yonyou.friendsandaargang.login.modle.LoginUserInfo;
import com.yonyou.friendsandaargang.login.modle.ThirdLogin;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SDCardUtil;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.Tool;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;


public class ActivityLogin extends BaseActivity implements
        View.OnClickListener, View.OnTouchListener, TextWatcher, OssService.picResultCallback {
    //用户名
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    //清除手机号
    @BindView(R.id.iv_clean_phone)
    ImageView mIvCleanPhone;
    //密码
    @BindView(R.id.et_password)
    EditText mEtPassword;
    //清除密码
    @BindView(R.id.clean_password)
    ImageView mCleanPassword;
    //密码是否可见
    @BindView(R.id.iv_show_pwd)
    ImageView mIvShowPwd;
    //登陆
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    //微信登录
    @BindView(R.id.ll_wechat)
    LinearLayout mLlWechat;
    //QQ登录
    @BindView(R.id.ll_qq)
    LinearLayout mLlQQ;
    //切换按钮
    @BindView(R.id.code_login)
    TextView tvLoginswitch;
    //发送验证码
    @BindView(R.id.phone_code)
    TextView tvSendCode;

    @BindView(R.id.forget_regist)
    TextView tvForget;


    @BindView(R.id.sc_lay)
    LinearLayout lay_qingchu;


    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;

    private String registrationId;
    private String openid, thirdname, iconurl, avatarname, username, password, mobile, verifyCode, path;
    private Bundle bundle;
    private SHARE_MEDIA shareMedia;

    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";
    private int falg = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_act);
        ButterKnife.bind(this);
        initviews();

    }

    /**
     * 初始化
     */
    private void initviews() {
        getTitleBar();
        setTitleText("登录").rightTv("注册");
        registrationId = SPTool.getContent(mContext, Constants.REGISTRATIONID);
        ossService = initOSS(endpoint, bucketName);
        initEvent();
        DefaultLogin();
    }


    /**
     * 默认登录方式
     */
    private void DefaultLogin() {
        tvLoginswitch.setText("验证码登录");
        mEtMobile.setHint("请输入用户名/手机号");
        mEtPassword.setHint("请输入密码");
        tvSendCode.setVisibility(View.GONE);    //隐藏发送验证码
        lay_qingchu.setVisibility(View.GONE);
        tvForget.setVisibility(View.VISIBLE);
        mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        falg = 1;
    }


    /**
     * Edit 监听事件/**
     * 禁止键盘弹起的时候可以滚动
     */
    private void initEvent() {
        mEtPassword.addTextChangedListener(this);
        mScrollView.setOnTouchListener(this);
    }


    /**
     * 按钮 控件监听事件
     *
     * @param v
     */
    @OnClick({R.id.iv_clean_phone, R.id.sc_lay, R.id.kj_lay, R.id.btn_login, R.id.code_login, R.id.forget_regist,
            R.id.titleBar_right_text, R.id.ll_wechat, R.id.ll_qq, R.id.phone_code})
    public void onClick(View v) {
        password = mEtPassword.getText().toString();
        switch (v.getId()) {
            //清空用户名
            case R.id.iv_clean_phone:
                mEtMobile.setText("");
                break;
            //清空密码
            case R.id.sc_lay:
                mEtPassword.setText("");
                break;
            //显示 or  隐藏密码
            case R.id.kj_lay:
                if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.kj3x);
                } else {
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.off3x);
                }
                String pwd = mEtPassword.getText().toString();
                //光标
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;
            //用户名与密码切换登陆
            case R.id.code_login:
                Loginswitch();
                // ActivityManger.skipActivityAndFinish(ActivityLogin.this, PhoneLoginActivity.class);
                break;

            //发送验证码
            case R.id.phone_code:
                senCode();
                break;
            //登陆按钮
            case R.id.btn_login:
                MainLogin();
                break;
            //忘记密码
            case R.id.forget_regist:
                ActivityManger.skipActivity(this, ForgetPassWordActivity.class);
                break;
            //注册
            case R.id.titleBar_right_text:
                Intent intent = new Intent(ActivityLogin.this, RegisterActivity.class);
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
     * 登录切换
     */

    private void Loginswitch() {
        if (tvLoginswitch.getText().equals("验证码登录")) {
            falg = 2;
            tvLoginswitch.setText("用户名/密码登录");
            mEtMobile.setHint("请输入手机号码");
            mEtMobile.setInputType(InputType.TYPE_CLASS_NUMBER);   //数字类型键盘
            mEtPassword.setHint("请输入验证码");
            mEtMobile.setText("");              //切换 清空
            mEtPassword.setText("");
            mEtPassword.setInputType(InputType.TYPE_CLASS_NUMBER); //数字类型 键盘
            mEtPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});   //最大字体长度
            tvSendCode.setVisibility(View.VISIBLE);
            lay_qingchu.setVisibility(View.GONE);
            tvForget.setVisibility(View.GONE);

        } else {
            falg = 1;
            tvLoginswitch.setText("验证码登录");
            mEtMobile.setHint("请输入用户名/手机号");
            mEtMobile.setInputType(InputType.TYPE_CLASS_TEXT);   //普通键盘
            mEtPassword.setHint("请输入密码");
            mEtMobile.setText("");         //切换 清空
            mEtPassword.setText("");
            mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEtPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});   //最大字体长度
            tvSendCode.setVisibility(View.GONE);
            lay_qingchu.setVisibility(View.GONE);
            tvForget.setVisibility(View.VISIBLE);

        }
    }


    /**
     * 发送验证码判断处理
     */
    private void senCode() {
        username = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.normal(mContext, "手机号码不能为空").show();
            return;
        }
        if (!com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(username)) {
            ToastUtils.normal(mContext, "手机号码有误").show();
            return;
        }
        getPhoneCode(username);
    }


    /**
     * 发送验证码
     */
    private void getPhoneCode(String phone) {
        Call<HttpResult> call = communityService().getSendCode(phone);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "验证码已发送，请注意查收").show();
                Tool.countDown(tvSendCode, 60000, 1000, "获取验证码");
                tvSendCode.setBackgroundResource(R.drawable.shape_code_garly);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }


    /**
     * 登录  判断处理
     */
    private void MainLogin() {
        password = mEtPassword.getText().toString();
        username = mEtMobile.getText().toString();
        switch (falg) {
            case 1:
                if (TextUtils.isEmpty(username)) {
                    ToastUtils.normal(mContext, "用户名不能为空").show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.normal(mContext, "密码不能为空").show();
                    return;
                }
                //判断用户名还是手机号码登录
                if (!com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(username)) {
                    getLogin(username, "", password);
                } else {
                    getLogin("", username, password);
                }
                break;
            case 2:
                if (TextUtils.isEmpty(username)) {
                    ToastUtils.normal(mContext, "手机号码不能为空").show();
                    return;
                }
                if (!com.yonyou.friendsandaargang.utils.TextUtils.isChinaPhoneLegal(username)) {
                    ToastUtils.normal(mContext, "手机号码错误").show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.normal(mContext, "验证码不能为空").show();
                    return;
                }
                getPhonelogin(username, password);
                break;
        }
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
                ActivityManger.skipActivityAndFinish(mContext, MainActivity.class);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();

            }
        });
    }


    /**
     * 普通登录
     *
     * @param userName
     * @param mobile
     * @param password
     */
    private void getLogin(String userName, final String mobile, String password) {
        Call<HttpResult<Login>> call = communityService().getLogin(userName, mobile,
                password, "10241002", registrationId);
        NetUtils<HttpResult<Login>> netUtils = new NetUtils<>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<Login>>() {
            @Override
            public void onResponseCallback(HttpResult<Login> loginHttpResult) {
                if (loginHttpResult.getReturnCode() != 0 && !loginHttpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, loginHttpResult.getReturnMsg()).show();
                    return;
                }
                //登陆成功  保存登陆状态   保存登陆信息
                LogonSuccess(loginHttpResult);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 用户名或者是手机号码登录成功
     *
     * @param loginHttpResult
     */
    private void LogonSuccess(HttpResult<Login> loginHttpResult) {
        SPTool.putContent(mContext, Constants.GENDER, loginHttpResult.getContent().getGender() + "");
        SPTool.putContent(mContext, Constants.USER_ID, loginHttpResult.getContent().getUserId());
        SPTool.putContent(mContext, Constants.USER_NAME, loginHttpResult.getContent().getUserName());
        SPTool.putContent(mContext, Constants.MOBILE, loginHttpResult.getContent().getMobile());
        SPTool.putInt(mContext, Constants.FIRSTTIMELOGON, loginHttpResult.getContent().getFirstTimeLogon());
        SPTool.putBoolean(mContext, Constants.ISLOGIN, true);
        ActivityManger.skipActivityAndFinish(ActivityLogin.this, MainActivity.class);
    }


    /**
     * 友盟开始授权(登入)
     */
    public void shareLoginUmeng(final Activity activity, final SHARE_MEDIA share_media_type) {
        UMShareAPI.get(activity).getPlatformInfo(activity, share_media_type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //sdk是6.4.5的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                shareMedia = share_media;
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

                if (shareMedia.equals(SHARE_MEDIA.WEIXIN)) {
                    bundle.putString("loginstyle", "微信");
                    getWeChatLogin(openid);
                } else if (shareMedia.equals(SHARE_MEDIA.QQ)) {
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
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //取消授权成功 i=1
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                //授权出错
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                //取消授权
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

                QQLoginSuccess(model);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * qq 登录  成功后处理
     *
     * @return
     */
    private void QQLoginSuccess(HttpResult<ThirdLogin> model) {
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


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    /**
     * 监听密码
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        switch (falg) {
            case 1:
                if (s.length() < 8) {
                    mBtnLogin.setBackgroundResource(R.drawable.shape_dark);
                    mBtnLogin.setEnabled(false);
                } else {
                    mBtnLogin.setBackgroundResource(R.drawable.shape_follow_red);
                    mBtnLogin.setEnabled(true);
                }
                break;
            case 2:
                if (s.length() < 6) {
                    mBtnLogin.setBackgroundResource(R.drawable.shape_dark);
                    mBtnLogin.setEnabled(false);
                } else {
                    mBtnLogin.setBackgroundResource(R.drawable.shape_follow_red);
                    mBtnLogin.setEnabled(true);
                }
                break;
        }
    }


    /**
     * 密码监听
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (falg == 1) {
            mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEtPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});   //最大字体长度
        } else {
            mEtPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
            mEtPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});   //最大字体长度
        }

        if (!TextUtils.isEmpty(s) && lay_qingchu.getVisibility() == View.GONE) {
            lay_qingchu.setVisibility(View.VISIBLE);

        } else if (TextUtils.isEmpty(s)) {
            lay_qingchu.setVisibility(View.GONE);
        }
        if (s.toString().isEmpty())
            return;
        if (!s.toString().matches("[A-Za-z0-9]+")) {
            String temp = s.toString();
            ToastUtils.normal(mContext, "请输入数字或字母").show();
            s.delete(temp.length() - 1, temp.length());
            mEtPassword.setSelection(s.length());
        }
    }

    //头像上传成功回调
    @Override
    public void getPicData(PutObjectRequest data) {
        SDCardUtil.deleteFile(path);
    }

    //头像上传失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {
//        ToastUtils.normal(mContext, "头像上传失败").show();
    }
}
