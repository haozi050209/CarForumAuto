package com.yonyou.friendsandaargang.info.activity.seeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.bean.AuthStatus;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/3/8.
 * <p>
 * 认证
 */

public class AuthenticationActivity extends BaseActivity {


    private String userId;
    private String authStatus;    //认证状态 10191001未认证,10191002认证中,10191003未通过,10191004已认证
    private String identityType;  // 10211001用户认证，10211002大咖认证，10211003经销商认证
    @BindView(R.id.user_rz_text)
    TextView user_rz_text;
    @BindView(R.id.dakai_rz_text)
    TextView dakai_rz_text;
    @BindView(R.id.qiye_rz_text)
    TextView qiye_rz_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        initviews();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAuthStatus();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("认证");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
    }

    @OnClick({R.id.user_rz_ray, R.id.dakai_rz_ray, R.id.qiye_rz_ray})
    public void OnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //用户认证
            case R.id.user_rz_ray:

                intent = new Intent(mContext, UserAuthenticationActivity.class);
                intent.putExtra("identityType", "10211001");
                intent.putExtra("forum", "userAu");
                startActivity(intent);
                break;
            //大咖认证
            case R.id.dakai_rz_ray:
                intent = new Intent(mContext, UserAuthenticationActivity.class);
                intent.putExtra("identityType", "10211002");
                intent.putExtra("forum", "daKaiAu");
                startActivity(intent);
                break;
            // 企业认证
            case R.id.qiye_rz_ray:
                ActivityManger.skipActivity(mContext, EnterpriseAuthenticationActivity.class);
                break;
        }
    }


    private void getAuthStatus() {
        showProgressDialog();
        Call<AuthStatus> call = communityService().getIdentityAuthStatus(userId);
        call.enqueue(new RetrofitCallback<AuthStatus>() {
            @Override
            public void onSuccess(AuthStatus model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                for (int i = 0; i < model.getContent().size(); i++) {
                    authStatus = model.getContent().get(i).getAuthStatus();
                    identityType = model.getContent().get(i).getIdentityType();
                    //用户认证
                    if (identityType.equals("10211001")) {
                        switch (authStatus) {
                            case "10191001":
                                user_rz_text.setText("未认证");
                                break;
                            case "10191002":
                                user_rz_text.setText("认证中");
                                break;
                            case "10191003":
                                user_rz_text.setText("未通过");
                                break;
                            case "10191004":
                                user_rz_text.setText("已认证");
                                break;
                        }

                    }

                    //大咖认证
                    if (identityType.equals("10211002")) {
                        switch (authStatus) {
                            case "10191001":
                                dakai_rz_text.setText("未认证");
                                break;
                            case "10191002":
                                dakai_rz_text.setText("认证中");
                                break;
                            case "10191003":
                                dakai_rz_text.setText("未通过");
                                break;
                            case "10191004":
                                dakai_rz_text.setText("已认证");
                                break;
                        }

                    }


                    //经销商认证
                    if (identityType.equals("10211003")) {
                        switch (authStatus) {
                            case "10191001":
                                qiye_rz_text.setText("未认证");
                                break;
                            case "10191002":
                                qiye_rz_text.setText("认证中");
                                break;
                            case "10191003":
                                qiye_rz_text.setText("未通过");
                                break;
                            case "10191004":
                                qiye_rz_text.setText("已认证");
                                break;
                        }

                    }
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
