package com.yonyou.friendsandaargang.jpush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.message.MessageActivity;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver＋自定义接收器";
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }


        try {
            Bundle bundle = intent.getExtras();
            //  SDK 向 JPush Server 注册所得到的注册 ID 。
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            }
            //收到了自定义消息 Push 。
            else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.e("接收到推送下来的自定义消息: ", bundle.getString(JPushInterface.EXTRA_MESSAGE));
            }
            //接收下来通知消息
            else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                //通知的id
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                //保存服务器推送下来的附加字段。这是个 JSON 字符串。
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
                JSONObject jsonObject = new JSONObject(extras);
                String uuid = jsonObject.getString("uuid");
                if (extras != null && extras.contains("uuid")) {
                    updatePushTask(uuid, bundle.getString(JPushInterface.EXTRA_MSG_ID));
                }

            }


            //用户打开了通知
            else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                //对应 API 通知内容的 title 字段。
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                //对应 API 通知内容的alert字段。
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                //对应 API 消息内容的 extras 字段。 这是个 JSON 字符串。
                String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
                JSONObject object = new JSONObject(type);
                //打开自定义的Activity
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            }
            //JPush 服务的连接状态发生变化
            else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);

            } else {

            }
        } catch (Exception e) {

        }

    }


    private void updatePushTask(String uuid, String msgId) {
        ApiService service = ApiClient.retrofit().create(ApiService.class);
        Call<HttpResult> call = service.updatePushTask(uuid, msgId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onThrowable(Throwable t) {
            }

            @Override
            public void onFinish() {
            }
        });
    }

}
