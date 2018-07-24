package com.yonyou.friendsandaargang.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yonyou.friendsandaargang.utils.ToastUtils;

/**
 * 作者:huangshuang
 * 事件 2016/12/16.
 * 邮箱： 15378412400@163.com
 */

public class NetHttpUtil {
    public static boolean isNetworkStateed(Context context) {
        try {
            // 创建并初始化连接对象
            ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 判断初始化是否成功并作出相应处理
            if (connMan != null) {
                // 调用getActiveNetworkInfo方法创建对象,如果不为空则表明网络连通，否则没连通
                NetworkInfo info = connMan.getActiveNetworkInfo();
                if (info != null) {
                    return info.isAvailable();
                } else {
                    ToastUtils.normal(context, "网络异常，请检查后重试！").show();
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

}
