package com.yonyou.friendsandaargang.info.activity.wechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class BindingQQActivity extends BaseActivity {

    @BindView(R.id.bdqq_but)
    Button bdqqBut;

    private static final String TAG = "BindingQQActivity";
    private String userId;
    private String openid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdqq);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("绑定QQ");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        if (!SPTool.getBoolean(mContext, "qqhadbind")) {
            bdqqBut.setText("绑定QQ");
        } else {
            bdqqBut.setText("解绑QQ");
        }
    }

    @OnClick({R.id.back_lay, R.id.bdqq_but})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_lay:
                finish();
                break;
            case R.id.bdqq_but://绑定解绑QQ
                if (!SPTool.getBoolean(mContext, "qqhadbind")) {
                    umengDeleteOauth(SHARE_MEDIA.QQ);
                    shareLoginUmeng(this, SHARE_MEDIA.QQ);
                } else {
                    getUnBindQQ(userId);
                }
                break;
        }
    }

    public void getBindQQ(String userId, String openId) {
        Call<HttpResult> call = communityService().getBindQQ(userId, openId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                SPTool.putBoolean(mContext, "qqhadbind", true);
                ToastUtils.normal(mContext, "QQ绑定成功").show();
                bdqqBut.setText("解绑QQ");
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    private void getUnBindQQ(String userId) {
        Call<HttpResult> call = communityService().getUnBindQQ(userId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                SPTool.putBoolean(mContext, "qqhadbind", false);
                ToastUtils.normal(mContext, "QQ解绑成功").show();
                bdqqBut.setText("绑定QQ");
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, msg).show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

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
                openid = map.get("openid");
                Log.e(TAG, "onStart授权完成: openid = " + openid + "  " + share_media_type);
                getBindQQ(userId, openid);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(activity, "授权失败", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onError: " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(activity, "授权取消", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onError: " + "授权取消");
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
                Log.e(TAG, "onComplete: 取消授权成功");
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
}
