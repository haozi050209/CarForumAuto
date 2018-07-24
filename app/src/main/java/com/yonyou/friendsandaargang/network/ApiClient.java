

package com.yonyou.friendsandaargang.network;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yonyou.friendsandaargang.BuildConfig;
import com.yonyou.friendsandaargang.gson.HttpResultGsonDeserializer;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {


        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             * 设置缓存
             */

          /*  File cachfile = new File(MyApplication.getContent)

            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!AppUtils.networkIsAvailable(DemoApplication.getContext())) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    if (AppUtils.networkIsAvailable(DemoApplication.getContext())) {
                        int maxAge = 0;
                        // 有网络时 设置缓存超时时间0个小时
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .build();
                    } else {
                        // 无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader("nyn")
                                .build();
                    }
                    return response;
                }
            };
            builder.cache(cache).addInterceptor(cacheInterceptor);*/


            //公共参数
           /* Interceptor addQueryParameterInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request request;
                    String method = originalRequest.method();
                    Headers headers = originalRequest.headers();
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            // Provide your custom parameter here
                            .addQueryParameter("platform", "android")
                            .addQueryParameter("version", "1.0.0")
                            .build();
                    request = originalRequest.newBuilder().url(modifiedUrl).build();
                    return chain.proceed(request);
                }
            };

            builder.addInterceptor(addQueryParameterInterceptor);*/
            /**
             * 设置信任所有的ssl证书
             */
            builder.hostnameVerifier(new SSLSocketClient.TrustAllHostnameVerifier());
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
            /**log用拦截器*/
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }

            /**
             *  设置cookie
             */
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            builder.cookieJar(new JavaNetCookieJar(cookieManager));


           /* 超时时间设置，默认60秒*/
            //全局的读取超时时间
            builder.readTimeout(6000, TimeUnit.MILLISECONDS);
            //全局的写入超时时间
            builder.writeTimeout(6000, TimeUnit.MILLISECONDS);
            //全局的连接超时时间
            builder.connectTimeout(6000, TimeUnit.MILLISECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            /**
             * 配置 mRetrofit
             */
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(buildGson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(builder.build())
                    .build();
        }

        return mRetrofit;
    }



    public static Gson buildGson() {
        Gson gson = null;
        if (gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(HttpResult.class, new HttpResultGsonDeserializer())
                    .create();
        }
        return gson;
    }

}
