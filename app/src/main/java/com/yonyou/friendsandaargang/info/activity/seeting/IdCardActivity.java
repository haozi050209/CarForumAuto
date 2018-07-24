package com.yonyou.friendsandaargang.info.activity.seeting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.ClientException;

import com.alibaba.sdk.android.oss.ServiceException;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.idcard.CameraActivity;
import com.yonyou.friendsandaargang.info.bean.IdCardBean;
import com.yonyou.friendsandaargang.network.ApiService;

import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;


import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.TimeUnit;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shibing on 18/5/8.
 */

public class IdCardActivity extends BaseActivity
        implements OssService.picResultCallback
        , View.OnClickListener {


    private static final String TAG = "IdCardActivity";
    OkHttpClient mOkHttpClient;
    private Button mRecognize;   //确认上传
    private EditText mPath, mName, mType;
    private ImageView mPhoto, photo_fan;
    private String mPhotoPath;

    private List<String> list;
    private List<String> listName;
    private String userId;

    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";
    private String objectname;
    private String name;
    private String idCard;
    private String realName;
    private String idCardNum;
    private IdCardBean.ResultBean resultBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        getTitleBar();
        setTitleText("上传身份证");
        initClient();
        initView();
        initClickListener();
        ossService = initOSS(endpoint, bucketName);
    }


    private void initView() {
        list = new ArrayList<>();
        listName = new ArrayList<>();
        mRecognize = (Button) findViewById(R.id.recognize);
        mPhoto = (ImageView) findViewById(R.id.photo);
        photo_fan = findViewById(R.id.photo_fan);
        userId = SPTool.getContent(mContext, Constants.USER_ID);
    }


    /**
     * 监听事件
     */
    private void initClickListener() {
        mPhoto.setOnClickListener(this);
        photo_fan.setOnClickListener(this);
        mRecognize.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_lay:
                finish();
                break;
            case R.id.photo:
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra("forum", "zheng");
                startActivityForResult(intent, 100);
                break;
            case R.id.photo_fan:
                intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra("forum", "fang");
                startActivityForResult(intent, 200);
                break;
            case R.id.recognize:
                // 识别成功后上传身份证图片
                if (list.size() == 1) {
                    ToastUtils.normal(mContext, "请上传完整的身份证信息").show();
                    return;
                }
                if (resultBean == null) {
                    ToastUtils.normal(mContext, "身份证识别失败，请检查网络是否连接正常").show();
                    return;
                }
                if (resultBean != null && !resultBean.isIsok()) {
                    ToastUtils.normal(mContext, "身份证识别失败，请重新上传身份证").show();
                    return;
                }
                //上传到服务器
                for (int i = 0; i < list.size(); i++) {
                    objectname = userId + "-" + TimeUtil.getDateTimeFromMil(System.currentTimeMillis()) + i;
                    listName.add(objectname);
                    ossService.asyncPutImage("user-identity/" + objectname, list.get(i));
                }
                break;
        }
    }


    private void initClient() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MINUTES)
                .readTimeout(1000, TimeUnit.MINUTES)
                .writeTimeout(1000, TimeUnit.MINUTES)
                .build();
    }


    /**
     * 上传身份证 识别身份证信息
     */
    private void uploadAndRecognize() {
        for (int i = 0; i < list.size(); i++) {
            mPhotoPath = list.get(i);
        }
        if (!TextUtils.isEmpty(mPhotoPath)) {
            File file = new File(mPhotoPath);
            //构造上传请求，类似web表单
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"callbackurl\""), RequestBody.create(null, "/idcard/"))
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"action\""), RequestBody.create(null, "idcard"))
                    .addPart(Headers.of("Content-Disposition", "form-data; name=\"img\"; filename=\"idcardFront_user.jpg\""), RequestBody.create(MediaType.parse("image/jpeg"), file))
                    .build();
            //这个是ui线程回调，可直接操作UI
            RequestBody progressRequestBody = ProgressHelper.withProgress(requestBody, new ProgressUIListener() {
                @Override
                public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                    //ui层回调
                }
            });
            //进行包装，使其支持进度回调
            final Request request = new Request.Builder()
                    .header("Host", "ocr.ccyunmai.com:8080")
                    .header("Origin", "http://ocr.ccyunmai.com:8080")
                    .header("Referer", "http://ocr.ccyunmai.com:8080/idcard/")
                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2398.0 Safari/537.36")
                    .url("http://ocr.ccyunmai.com:8080/UploadImage.action")
                    .post(progressRequestBody)
                    .build();
            //开始请求
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    try {
                        if (call.isExecuted()) {
                            ToastUtils.normal(mContext, "身份证信息读取失败，请检查网络是否正常").show();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = null;
                    try {
                        result = response.body().string();
                        Document parse = Jsoup.parse(result);
                        Elements select = parse.select("div#ocrresult");


                        name = select.text().substring(select.text().indexOf("姓名: "), select.text().indexOf("性别:")).substring(4);
                        idCard = select.text().substring(select.text().indexOf("公民身份号码: "), select.text().indexOf("签发机关:")).substring(8);


                        Logger.e("---select----", select.text());
                        Logger.e("---name----", name);
                        Logger.e("---idCard----", idCard);

                        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(idCard)) {
                            ToastUtils.normal(mContext, "身份证信息读取失败，请重新上传").show();
                            return;
                        }
                        //获取到身份证信息后 识别身份证是否正确
                        IdCard(idCard.trim(), name.trim());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 100:
                try {
                    Bundle extras = data.getExtras();
                    String path = extras.getString("path");
                    String type = extras.getString("type");
                    list.add(path);
                    File file = new File(path);
                    FileInputStream inStream = null;
                    inStream = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(inStream);
                    mPhoto.setImageBitmap(bitmap);
                    inStream.close();
                    //上传成功后上传识别
                    uploadAndRecognize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 200:
                try {
                    Bundle extras = data.getExtras();
                    String path = extras.getString("path");
                    String type = extras.getString("type");
                    list.add(path);
                    File file = new File(path);
                    FileInputStream inStream = null;
                    inStream = new FileInputStream(file);
                    Bitmap bitmap1 = BitmapFactory.decodeStream(inStream);
                    photo_fan.setImageBitmap(bitmap1);
                    inStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 验证身份证信息
     */
    public void IdCard(String IdCard, String UserName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.hostUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        retrofit2.Call<IdCardBean> call = apiService.getIdcard(IdCard, UserName);
        call.enqueue(new RetrofitCallback<IdCardBean>() {
            @Override
            public void onSuccess(IdCardBean idCardBean) {
                try {
                    //识别成功
                    if (idCardBean.getError_code() == 0) {
                        resultBean = idCardBean.getResult();

                        if (!idCardBean.getResult().isIsok()) {
                            ToastUtils.normal(mContext, "身份证不匹配,请重新上传身份证").show();
                            return;
                        }
                        ToastUtils.normal(mContext, "身份证验证成功").show();
                        realName = idCardBean.getResult().getRealname();
                        idCardNum = idCardBean.getResult().getIdcard();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器异常，请检查网络后重试！").show();
                mRecognize.setFocusable(false);
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
            }
        });

    }


    //上传成功后回调
    @Override
    public void getPicData(PutObjectRequest data) {

        if (TextUtils.isEmpty(realName) && TextUtils.isEmpty(idCardNum)) {
            ToastUtils.normal(mContext, "身份证识别失败，请重试").show();
            return;
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra("nameList", (ArrayList<String>) listName);
        intent.putExtra("realName", realName);
        intent.putExtra("idNumber", idCardNum);
        setResult(100, intent);
        finish();

    }

    //失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {

    }

}
