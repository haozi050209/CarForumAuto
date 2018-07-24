package com.yonyou.friendsandaargang.network;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;


public abstract class HttpCallBackImpl<T> {

    public abstract void onResponseCallback(T t);

    /**
     * 统一处理 异常
     *
     * @param e
     */
    public void onError(Throwable e, Context context) {

        if (e instanceof NetworkErrorException) {
            ToastUtils.normal(context, "网络异常，请检查后重试！").show();
        }

        if (e instanceof ConnectException) {
            ToastUtils.normal(context, "服务器连接超时，请检查后重试！").show();

        }
        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            int code = exception.code();

            ToastUtils.normal(context, "服务器异常，请稍后重试！").show();
        }
    }
}
