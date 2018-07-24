package com.yonyou.friendsandaargang.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.lzy.imagepicker.bean.ImageItem;
import com.yonyou.friendsandaargang.utils.Logger;

import java.io.File;

import java.io.InputStream;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oss on 2015/12/7 0007.
 * 支持普通上传，普通下载和断点上传
 */
public class OssService {
    private OSS oss;
    private String bucket;
    //回调地址
    private picResultCallback Callback;


    public OssService(OSS oss, String bucket, picResultCallback Callback) {
        this.oss = oss;
        this.bucket = bucket;
        this.Callback = Callback;
    }


    /**
     * @param object
     * @param userCallback object字段为图片的上传地址（具体地址的前缀后端给，这个是拼起来的一个路径）
     *                     localFile图片的本地地址
     *                     mProgress 进度条
     *                     img 显示图片的控件
     *                     type 类型
     */

    //下载
    public void asyncGetImage(String object, @NonNull final OSSCompletedCallback<GetObjectRequest, GetObjectResult> userCallback) {
        if ((object == null) || object.equals("")) {
            Log.w("AsyncGetImage", "ObjectNull");
            return;
        }

        Log.d("GetImage", "Start");
        GetObjectRequest get = new GetObjectRequest(bucket, object);
        //OSSAsyncTask task = oss.asyncGetObject(get, userCallback);
    }

    /**
     * 上传图片
     * <p>
     * object表示上传图片名称
     * <p>
     * localFile 表示上传图片路径
     *
     * @param object
     * @param localFile
     */
    public void asyncPutImage(final String object, final String localFile) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }
        File file = null;
        file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", file.getPath());
            return;
        }
        // 构造上传请求
        final PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);
        put.setCallbackParam(new HashMap<String, String>() {
            {
                put("callbackBodyType", "application/json");
                put("callbackBody", "{\"type\":${x:type},\"attachmentName\":${x:var2}}");
            }
        });
        put.setCallbackVars(new HashMap<String, String>() {
            {
                put("x:var1", "10121001");
                put("x:var2", object);
            }
        });
        OSSAsyncTask ossAsyncTask = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("PutObject", "上传成功");
                //请求成功后的回调
                Callback.getPicData(request);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("PutObject", "上传失败");
                }

                Callback.Failure(clientException, serviceException);

            }
        });
    }

    //成功的回调接口
    public interface picResultCallback {

        void getPicData(PutObjectRequest data);

        void Failure(ClientException clientException, ServiceException serviceException);
    }


}
