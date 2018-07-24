package com.yonyou.friendsandaargang.info.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.modifypass.ModifyPassWordActivity;
import com.yonyou.friendsandaargang.info.activity.modifypass.ModifyPhoneActivity;
import com.yonyou.friendsandaargang.info.activity.modifypass.PlayPassWordActivity;
import com.yonyou.friendsandaargang.info.activity.seeting.UnsubscribeActivity;
import com.yonyou.friendsandaargang.info.activity.wechat.BindingQQActivity;
import com.yonyou.friendsandaargang.info.activity.wechat.BindingWeChatActivity;
import com.yonyou.friendsandaargang.utils.SPTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 账号与安全
 */

public class SecurityActivity extends BaseActivity {

    @BindView(R.id.wx_bd_text)
    TextView wxBdText;
    @BindView(R.id.qq_bd_text)
    TextView qqBdText;
    @BindView(R.id.paly_set_text)
    TextView palySetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("账号与安全");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SPTool.getBoolean(mContext, "wechathadbind")) {
            wxBdText.setText("已绑定");
        } else {
            wxBdText.setText("未绑定");
        }
        if (SPTool.getBoolean(mContext, "qqhadbind")) {
            qqBdText.setText("已绑定");
        } else {
            qqBdText.setText("未绑定");
        }
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.back_lay, R.id.fly_bdwx, R.id.fly_bdqq, R.id.fly_phone, R.id.fly_password, R.id.fly_paly, R.id.fly_zx})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_lay:
                finish();
                break;
            //绑定微信
            case R.id.fly_bdwx:
                ActivityManger.skipActivity(this, BindingWeChatActivity.class);
                break;
            //绑定QQ
            case R.id.fly_bdqq:
                ActivityManger.skipActivity(this, BindingQQActivity.class);
                break;
            /*绑定手机号*/
            case R.id.fly_phone:
                ActivityManger.skipActivity(this, ModifyPhoneActivity.class);
                break;
            /*修改密码*/
            case R.id.fly_password:
                ActivityManger.skipActivity(this, ModifyPassWordActivity.class);
                break;
            /*设置支付密码*/
            case R.id.fly_paly:
                ActivityManger.skipActivity(this, PlayPassWordActivity.class);
                break;
            /*注销账号*/
            case R.id.fly_zx:
                ActivityManger.skipActivity(this, UnsubscribeActivity.class);
                break;
        }
    }
}
