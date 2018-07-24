package com.yonyou.friendsandaargang.network;

import android.content.Context;

import com.yonyou.friendsandaargang.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by shibing on 18/2/27.
 */

public abstract class RetrofitCallback<M> implements Callback<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(int code, String msg);

    public abstract void onThrowable(Throwable t);

    public abstract void onFinish();

    @Override
    public void onResponse(Call<M> call, Response<M> response) {
        try {
            if (response.isSuccessful()) {
                onSuccess(response.body());
            } else {
                onFailure(response.code(), response.errorBody().toString());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        onFinish();

    }

    @Override
    public void onFailure(Call<M> call, Throwable t) {
        onThrowable(t);
        onFinish();
    }

}