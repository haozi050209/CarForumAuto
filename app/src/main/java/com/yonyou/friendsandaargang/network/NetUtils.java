package com.yonyou.friendsandaargang.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;


import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.dialog.LoadingDialog;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.adapter.rxjava.HttpException;


public class NetUtils<T> implements DialogInterface.OnCancelListener {
    private LoadingDialog loadingDialog;
    private ResponseCallBack<T> callBack;
    private boolean hasNet;
    private Call<T> call;
    private Context mContext;

    public NetUtils(Context context) {
        this(context, true);
    }


    /**
     * 是否显示加载dialog
     *
     * @return
     */
    public boolean isProgress() {
        return true;
    }


    public NetUtils(Context context, boolean isProgress) {
        hasNet = isOpenNetwork(context);
        try {
            if (hasNet) {
                LoadingDialog.Builder builder1 = new LoadingDialog.Builder(context)
                        .setMessage("加载中...")
                        .setCancelable(true)
                        .setCancelOutside(true);
                loadingDialog = builder1.create();
                loadingDialog.show();
            } else {
                ToastUtils.normal(context, "网络连接错误，请检查网络是否连接正常").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadingDialog.dismiss();
        }

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


    public void enqueue(Call call, final ResponseCallBack<T> callBack) {
        this.call = call.clone();
        this.callBack = callBack;

        call.enqueue(new NetRequestCallback<T>(this, mContext) {
            @Override
            public void onResponseCallback(T t) {
                hideProgress();
                callBack.onResponseCallback(t);

            }

            @Override
            public void onFailureCallback() {
                hideProgress();
                callBack.onFailureCallback();
            }
        });
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (loadingDialog != null && call != null) {
            call.cancel();
        }
    }

    private void hideProgress() {

        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                loadingDialog.dismiss();
            }
        }

        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
                loadingDialog.dismiss();
            }
        }

    }


}
