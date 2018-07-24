package com.yonyou.friendsandaargang.info.activity.message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.jaeger.library.StatusBarUtil;
import com.suke.widget.SwitchButton;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.MessagePushAdapter;
import com.yonyou.friendsandaargang.info.bean.MessagePush;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 消息提醒设置
 */

public class MessageRemindingActivity extends BaseActivity implements SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.list_push)
    ListView list_push;
    //系统推送
    @BindView(R.id.switchButton1)
    SwitchButton switchButton1;
    //钱包推送
    @BindView(R.id.switchButton2)
    SwitchButton switchButton2;
    //粉丝推送
    @BindView(R.id.switchButton3)
    SwitchButton switchButton3;
    //论坛推送
    @BindView(R.id.switchButton4)
    SwitchButton switchButton4;
    //问答推送
    @BindView(R.id.switchButton5)
    SwitchButton switchButton5;
    @BindView(R.id.switchButton6)
    SwitchButton switchButton6;

    private String userId;
    private MessagePush messagePush;
    private String values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_reminding);
        ButterKnife.bind(this);
        initviews();
        getUserMessagePushSetting();
    }


    private void initviews() {
        getTitleBar();
        setTitleText("消息提醒设置");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        switchButton1.setOnCheckedChangeListener(this);
        switchButton2.setOnCheckedChangeListener(this);
        switchButton3.setOnCheckedChangeListener(this);
        switchButton4.setOnCheckedChangeListener(this);
        switchButton5.setOnCheckedChangeListener(this);
        switchButton6.setOnCheckedChangeListener(this);

    }

    //判断状态是打开还是关闭状态
    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.switchButton1:
                Map<String, String> map = new HashMap<>();
                values = (isChecked) ? "1" : "0";
                map.put("userId", userId);
                map.put("systemMessagePush", values);
                getupdateMessagePushSetting(map);
                break;
            case R.id.switchButton2:
                Map<String, String> map1 = new HashMap<>();
                values = (isChecked) ? "1" : "0";
                map1.put("userId", userId);
                map1.put("fundMessagePush", values);
                getupdateMessagePushSetting(map1);
                break;
            case R.id.switchButton3:
                Map<String, String> map2 = new HashMap<>();
                values = (isChecked) ? "1" : "0";
                map2.put("userId", userId);
                map2.put("fansMessagePush", values);
                getupdateMessagePushSetting(map2);
                break;
            case R.id.switchButton4:
                Map<String, String> map3 = new HashMap<>();
                values = (isChecked) ? "1" : "0";
                map3.put("userId", userId);
                map3.put("forumMessagePush", values);
                getupdateMessagePushSetting(map3);
                break;
            case R.id.switchButton5:
                Map<String, String> map4 = new HashMap<>();
                values = (isChecked) ? "1" : "0";
                map4.put("userId", userId);
                map4.put("answerMessagePush", values);
                getupdateMessagePushSetting(map4);
                break;
            case R.id.switchButton6:
                Map<String, String> map5 = new HashMap<>();
                values = (isChecked) ? "1" : "0";
                map5.put("userId", userId);
                map5.put("channelMessagePush", values);
                getupdateMessagePushSetting(map5);
                break;
        }

    }


    /**
     * 获取消息推送设置状态
     */

    private void getUserMessagePushSetting() {
        Call<HttpResult<MessagePush>> call = communityService().getUserMessagePushSetting(userId);
        NetUtils<HttpResult<MessagePush>> netUtils = new NetUtils<HttpResult<MessagePush>>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<MessagePush>>() {
            @Override
            public void onResponseCallback(HttpResult<MessagePush> model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                messagePush = model.getContent();
                if (messagePush.getSystemMessagePush().equals("1")) {
                    switchButton1.toggle(true);
                }
                if (messagePush.getFundMessagePush().equals("1")) {
                    switchButton2.toggle(true);
                }
                if (messagePush.getFansMessagePush().equals("1")) {
                    switchButton3.toggle(true);
                }
                if (messagePush.getForumMessagePush().equals("1")) {
                    switchButton4.toggle(true);
                }
                if (messagePush.getAnswerMessagePush().equals("1")) {
                    switchButton5.toggle(true);
                }
                if (messagePush.getChannelMessagePush().equals("1")) {
                    switchButton6.toggle(true);
                }
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }


    /**
     * 设置消息推送
     * map  请求
     */

    private void getupdateMessagePushSetting(Map<String, String> map) {
        Call<HttpResult> call = communityService().updateMessagePushSetting(map);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }

            }
        }));
    }
}
