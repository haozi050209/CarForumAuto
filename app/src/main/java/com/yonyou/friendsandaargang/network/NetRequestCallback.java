package com.yonyou.friendsandaargang.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


import com.yonyou.friendsandaargang.view.dialog.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class NetRequestCallback<T> implements Callback<T> {


    private LoadingDialog loadingDialog;

    private NetUtils netUtils;
    private Context context;
    private HttpCallBackImpl httpCallBack;


    public abstract void onResponseCallback(T t);

    public abstract void onFailureCallback();


    public NetRequestCallback(NetUtils utils, Context context) {
        this.netUtils = utils;
        this.context = context;
    }


    /**
     * 请求成功
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        hideProgress();
        if (response.body() == null) {
            return;
        }
        onResponseCallback(response.body());

    }


    /**
     * 请求失败
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        hideProgress();
        //httpCallBack.onError(t, context);
        onFailureCallback();


    }


    private void hideProgress() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();

        }
    }
}
