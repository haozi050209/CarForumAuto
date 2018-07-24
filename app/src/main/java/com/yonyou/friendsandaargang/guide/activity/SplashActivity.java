package com.yonyou.friendsandaargang.guide.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.jaeger.library.StatusBarUtil;
import com.yonyou.friendsandaargang.base.ActivityManger;

/**
 * Created by shibing on 18/2/7.
 * <p>
 * <p>
 * 欢迎闪频页  梁浩
 */

public class SplashActivity extends Activity {

    private static final int TIME = 500;
    private static final int GO_MAIN = 100;
    private static final int GO_GUIDE = 101;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_MAIN:
                    goMain();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        ActivityManger.addActivity(this);
        //设置全透明状态栏
        StatusBarUtil.setTranslucent(this, 0);
        init();
    }

    private void init() {
        SharedPreferences sf = getSharedPreferences("data", MODE_PRIVATE);//判断是否是第一次进入
        boolean isFirstIn = sf.getBoolean("isFirstIn", true);
        SharedPreferences.Editor editor = sf.edit();
        //若为true，则是第一次进入
        if(isFirstIn) {
            editor.putBoolean("isFirstIn", false);
            //将欢迎页停留0.5秒，并且将message设置为跳转到引导页SplashActivity，跳转在goGuide中实现
            mhandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
        } else {
            //将欢迎页停留0.5秒，并且将message设置文跳转到MainActivity，跳转功能在goMain中实现
            mhandler.sendEmptyMessageDelayed(GO_MAIN, TIME);
        }
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); 	不要调用父类的方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void goMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }
}
