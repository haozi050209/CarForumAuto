package com.yonyou.friendsandaargang.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yonyou.friendsandaargang.utils.Tool;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by shibing on 18/2/27.
 */

public class MyApplication extends Application {

    private static Application instance;
    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Tool.init(this);
        initJPush();
        initUM();
    }


    /**
     * 初始化极光
     */
    private void initJPush() {
        JPushInterface.setDebugMode(true);     //打印log
        JPushInterface.init(this);    //初始化 极光
    }


    /**
     * 初始化友盟
     */
    private void initUM() {
        UMShareAPI.get(this);
        UMConfigure.init(this, "5ae027fef29d9876cf00003f", "有车帮", UMConfigure.DEVICE_TYPE_PHONE, "");
        {
            //初始化友盟
            // 微信
            PlatformConfig.setWeixin("wx0e630ca5a7e5fbf9", "9a88eea18ccbd84c91b2ea176a5870eb");
            //qq
            PlatformConfig.setQQZone("1106865544", "WuB4SMMxKR7W8vg6");
        }
    }


    //分割Dex
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
