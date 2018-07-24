package com.yonyou.friendsandaargang.network;

import android.content.Context;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shibing on 18/2/27.
 */

public class NetRetrofitCallback<T> implements Callback<T> {


    private Context mContext;
    private HttpCallBackImpl httpCallBack;

    public NetRetrofitCallback(Context mContext, HttpCallBackImpl httpCallBack) {
        this.mContext = mContext;
        this.httpCallBack = httpCallBack;

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        try {
            if (NetHttpUtil.isNetworkStateed(mContext)) {
                if (response == null) {
                    ToastUtils.normal(mContext, "服务器加载异常").show();
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                httpCallBack.onResponseCallback(response.body());

            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.normal(mContext, "服务器加载异常").show();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        httpCallBack.onError(t, mContext);
    }


}