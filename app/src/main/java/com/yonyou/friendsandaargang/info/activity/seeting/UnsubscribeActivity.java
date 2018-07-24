package com.yonyou.friendsandaargang.info.activity.seeting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.guide.activity.MainActivity;
import com.yonyou.friendsandaargang.info.adapter.UnsubscribeAdapter;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.MyListView;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/4/10.
 */

public class UnsubscribeActivity extends BaseActivity {

    private String userId;

    @BindView(R.id.cancellation_list)
    MyListView cancellation_list;
    @BindView(R.id.cancellation_but)
    Button cancellation_but;
    private UnsubscribeAdapter unsubscribeAdapter;
    private int closeReason;
    private String code;
    private DialogSureCancel rxDialogSureCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsubscribe);
        ButterKnife.bind(this);
        initviews();
        Reason();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("账户注销");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        cancellation_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogOut();
            }
        });


    }


    /**
     * 原因列表
     */
    private void Reason() {
        unsubscribeAdapter = new UnsubscribeAdapter(mContext);
        cancellation_list.setAdapter(unsubscribeAdapter);
        cancellation_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                closeReason = position;
                unsubscribeAdapter.ChangedImage(position);
                unsubscribeAdapter.notifyDataSetChanged();
                switch (position) {
                    case 0:
                        code = "10131001";
                        break;
                    case 1:
                        code = "10131002";
                        break;
                    case 2:
                        code = "10131003";
                        break;
                    case 3:
                        code = "10131004";
                        break;
                }
            }
        });

    }

    /**
     * 是否注销弹窗
     */
    private void ShowDialogOut() {
        rxDialogSureCancel = new DialogSureCancel(mContext);//提示弹窗
        rxDialogSureCancel.getTitleView().setText("提示");
        rxDialogSureCancel.getContentView().setText("确定要注销账户，账户注销后将清除所有信息？");
        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(code)) {
                    ToastUtils.normal(mContext, "烦请选择注销原因").show();
                    return;
                }

                getCloseAccount(code);
                rxDialogSureCancel.dismiss();
            }
        });
        rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSureCancel.dismiss();
            }
        });

        rxDialogSureCancel.show();
    }


    private void getCloseAccount(String code) {
        Call<HttpResult> call = communityService().getCloseAccount(userId, code);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "注销成功").show();
                //清除所有保存信息
                SPTool.putBoolean(mContext, Constants.ISLOGIN, false);
                SPTool.putBoolean(mContext, "qqhadbind", false);
                SPTool.putBoolean(mContext, "wechathadbind", false);
                SPTool.clearPreference(mContext, Constants.USER_ID, null);
                ActivityManger.skipActivityAndFinish(mContext, MainActivity.class);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器异常加载异常").show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
