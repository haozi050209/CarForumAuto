package com.yonyou.friendsandaargang.info.activity.modifypass;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.yonyou.friendsandaargang.R;
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
 * 设置支付密码
 */

public class PlayPassWordActivity extends BaseActivity {


    @BindView(R.id.new_paly_password)
    EditText new_paly_password;
    @BindView(R.id.repeat_paly_password)
    EditText repeat_paly_password;

    @BindView(R.id.show_paly_pwd)
    ImageView show_paly_pwd;
    @BindView(R.id.repeat_paly_pwd)
    ImageView repeat_paly_pwd;

    @BindView(R.id.current_paly_password)
    EditText current_paly_password;
    @BindView(R.id.current_paly_pwd)
    ImageView current_paly_pwd;


    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paly_password);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("设置支付密码");
        userid = SPTool.getContent(mContext, Constants.USER_ID);
    }


    @OnClick({R.id.btn_modify_paly, R.id.show_paly_pwd, R.id.repeat_paly_pwd})
    public void OnClick(View view) {
        switch (view.getId()) {
            //确认修改
            case R.id.btn_modify_paly:
                break;
            //新密码  隐藏
            case R.id.show_paly_pwd:
                break;
            //重复密码 隐藏
            case R.id.repeat_paly_pwd:
                break;
        }
    }


    @OnClick({R.id.show_paly_pwd, R.id.repeat_paly_pwd, R.id.btn_modify_paly})
    public void OnClisk(View view) {
        switch (view.getId()) {

            case R.id.show_paly_pwd:
                break;
            case R.id.repeat_paly_pwd:
                break;
            //设置支付密码
            case R.id.btn_modify_paly:
                break;
        }
    }


    private void getupdateUserPayPassword(String tv_play, String new_play) {

        showProgressDialog();
        Call<HttpResult> call = communityService().getupdateUserPayPassword(userid, tv_play, new_play);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {

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
