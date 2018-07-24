package com.yonyou.friendsandaargang.info.activity.modifypass;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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
 * 修改登录密码
 */

public class ModifyPassWordActivity extends BaseActivity {


    @BindView(R.id.current_password)
    EditText current_password;
    @BindView(R.id.new_password)
    EditText new_password;
    @BindView(R.id.repeat_password)
    EditText repeat_password;

    @BindView(R.id.current_show_pwd)
    ImageView current_show_pwd;
    @BindView(R.id.new_show_pwd)
    ImageView new_show_pwd;
    @BindView(R.id.repeat_show_pwd)
    ImageView repeat_show_pwd;

    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("登录密码修改");
        userid = SPTool.getContent(mContext, Constants.USER_ID);
    }


    @OnClick({R.id.btn_modify_password, R.id.repeat_show_pwd, R.id.new_show_pwd, R.id.current_show_pwd})
    public void OnClick(View view) {
        switch (view.getId()) {
            /*确定修改*/
            case R.id.btn_modify_password:
                String currentPassword;
                String newPassword;
                String repeatPassword;
                currentPassword = current_password.getText().toString();
                newPassword = new_password.getText().toString();
                repeatPassword = repeat_password.getText().toString();
                if (TextUtils.isEmpty(currentPassword)) {
                    ToastUtils.normal(mContext, "当前密码不能为空").show();
                    return;
                }
                if (TextUtils.isEmpty(newPassword)) {
                    ToastUtils.normal(mContext, "新密码不能为空").show();
                    return;
                }
                if (TextUtils.isEmpty(repeatPassword)) {
                    ToastUtils.normal(mContext, "重复密码不能为空").show();
                    return;
                }

                if (!newPassword.equals(repeatPassword)){
                    ToastUtils.normal(mContext, "密码不一致，请重新输入").show();
                    return;
                }

                getupdateUserPassword(currentPassword, newPassword);

                break;
             /*重复密码 显示or隐藏*/
            case R.id.repeat_show_pwd:
                if (repeat_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    repeat_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    repeat_show_pwd.setImageResource(R.drawable.kj3x);
                } else {
                    repeat_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    repeat_show_pwd.setImageResource(R.drawable.off3x);
                }
                break;
            /*新密码 显示or隐藏*/
            case R.id.new_show_pwd:
                if (new_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    new_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    new_show_pwd.setImageResource(R.drawable.kj3x);
                } else {
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    new_show_pwd.setImageResource(R.drawable.off3x);
                }
                break;
            /*当前密码 显示or隐藏*/
            case R.id.current_show_pwd:
                if (current_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    current_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    current_show_pwd.setImageResource(R.drawable.kj3x);
                } else {
                    current_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    current_show_pwd.setImageResource(R.drawable.off3x);
                }
                break;
        }
    }


    /**
     * 修改密码
     */


    private void getupdateUserPassword(String password, String newpassword) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getupdateUserPassword(userid, password, newpassword);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnMsg().equals("success") && model.getReturnCode() == 0) {
                    ToastUtils.normal(mContext, "修改成功").show();
                    finish();
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
