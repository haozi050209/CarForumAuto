package com.yonyou.friendsandaargang.info.activity.seeting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.SecurityActivity;
import com.yonyou.friendsandaargang.info.activity.message.MessageRemindingActivity;
import com.yonyou.friendsandaargang.info.activity.problem.OpinionActivity;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.CacheUtil;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 个人信息
 */

public class SeetingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.text_hc)
    TextView text_hc;
    private String cache;
    private String userId;

    private DialogSureCancel Outdialog, Cachedialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeting);
        ButterKnife.bind(this);
        initviews();
    }


    private void initviews() {
        getTitleBar();
        setTitleText("设置");
        userId = SPTool.getContent(SeetingActivity.this, Constants.USER_ID);
        try {
            cache = CacheUtil.getTotalCacheSize(this);
            text_hc.setText(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.back_lay, R.id.fly_an, R.id.fly_hc, R.id.fly_tx, R.id.fly_fk, R.id.fly_gy, R.id.but_out})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_lay:
                finish();
                break;
            //账号与安全
            case R.id.fly_an:
                ActivityManger.skipActivity(this, SecurityActivity.class);
                break;
            //清除缓存
            case R.id.fly_hc:
                CacheShowDialog();
                break;
            //消息提醒与设置
            case R.id.fly_tx:
                ActivityManger.skipActivity(this, MessageRemindingActivity.class);
                break;
            // 意见反馈
            case R.id.fly_fk:
                ActivityManger.skipActivity(this, OpinionActivity.class);
                break;
            // 关于我们
            case R.id.fly_gy:
                ActivityManger.skipActivity(this, AboutActivity.class);
                break;
            // 安全退出
            case R.id.but_out:
                ShowDialogOut();
                break;
        }
    }

    /**
     * 显示 清除缓存的diaglo
     */
    private void CacheShowDialog() {
        Cachedialog = new DialogSureCancel(mContext);//提示弹窗
        Cachedialog.getTitleView().setText("提示");
        Cachedialog.getContentView().setText("清理缓存（" + cache + ")");
        Cachedialog.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtil.clearAllCache(mContext);
                Logger.e("------", cache + "");

                cache = "0.00MB";
                text_hc.setText(cache);
                Cachedialog.dismiss();
            }
        });
        Cachedialog.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cachedialog.dismiss();
            }
        });
        Cachedialog.show();
    }

    /**
     * 退出
     */
    private void ShowDialogOut() {
        Outdialog = new DialogSureCancel(mContext);//提示弹窗
        Outdialog.getTitleView().setText("提示");
        Outdialog.getContentView().setText("确定退出登录？");
        Outdialog.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogout();
                Outdialog.dismiss();
            }
        });
        Outdialog.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Outdialog.dismiss();
            }
        });
        Outdialog.show();
    }

    /**
     * 退出登陆
     */
    private void getLogout() {
        showProgressDialog();
        Call<HttpResult> call = communityService().getlogOut(userId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                ToastUtils.normal(mContext, "成功退出").show();
                SPTool.putBoolean(mContext, Constants.ISLOGIN, false);
                ActivityManger.skipActivityAndFinish(mContext, ActivityLogin.class);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Outdialog != null && Cachedialog != null) {
            Outdialog.dismiss();
            Cachedialog.dismiss();
        }
    }
}
