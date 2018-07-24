package com.yonyou.friendsandaargang.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by shibing on 18/5/7.
 */

public class NetworkConnected {


    public NetworkConnected(Context context) {

    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断wifi网络是否可用
     *
     * @param context
     * @return
     */
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     * @param context
     * @return
     */
    public boolean isMobileConnected(Context context) {
             if (context != null) {
                     ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                             .getSystemService(Context.CONNECTIVITY_SERVICE);
                     NetworkInfo mMobileNetworkInfo = mConnectivityManager
                             .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                     if (mMobileNetworkInfo != null) {
                             return mMobileNetworkInfo.isAvailable();
                         }
                 }
             return false;
         }


    private boolean isOpenNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            int networkType = networkInfo.getType();
            if (ConnectivityManager.TYPE_WIFI == networkType) {
                //当前为wifi网络
            } else if (ConnectivityManager.TYPE_MOBILE == networkType) {
                //当前为mobile网络
            }
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }
}
