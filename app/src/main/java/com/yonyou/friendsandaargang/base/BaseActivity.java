package com.yonyou.friendsandaargang.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.jaeger.library.StatusBarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.analytics.MobclickAgent;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.activirty.ShowBigImageActivity;
import com.yonyou.friendsandaargang.jpush.ExampleUtil;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.utils.GlideImageLoader;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.view.dialog.LoadingDialog;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shibing on 18/2/7.
 */

public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public BaseActivity mContext;
    private LinearLayout imageView;   //左边返回按钮
    private TextView title, texttitle, tvRight;        //title
    private int color;
    private LoadingDialog dialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManger.addActivity(this);
        color = getResources().getColor(R.color.color_red);
        StatusBarUtil.setColorNoTranslucent(this, color);
        //初始化消息
        init();
        //接收消息
        registerMessageReceiver();
    }


    //友盟Session统计
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    // 返回建
    public void getTitleBar() {
        title = findViewById(R.id.title);
        imageView = (LinearLayout) findViewById(R.id.back_lay);
        tvRight = findViewById(R.id.titleBar_right_text);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //title
    public BaseActivity setTitleText(String titleText) {
        title.setText(titleText);
        return this;
    }


    //添加图片
    public BaseActivity rightImageRes(int drawableRes) {
        tvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0);
        return this;
    }

    //添加右侧文字
    public BaseActivity rightTv(String titleText) {
        tvRight.setText(titleText);
        return this;
    }


    /**
     * 右侧图片的点击事件
     */
    public void onRightClick() {

    }


    @Override
    public void onClick(View v) {
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public ApiService communityService() {
        return ApiClient.retrofit().create(ApiService.class);
    }


    /**
     * 显示  图片加字
     *
     * @return
     */
    public LoadingDialog showProgressDialog() {
        LoadingDialog.Builder builder1 = new LoadingDialog.Builder(mContext)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog1 = builder1.create();
        dialog1.show();
        return dialog1;
    }


    /**
     * 结束加载dialog
     */
    public void dismissProgressDialog() {
        if (dialog1 != null && dialog1.isShowing()) {
            dialog1.dismiss();
        }
    }


    /**
     * 图片裁减
     */
    public void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(9);                        //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }


    //如果已经初始化，但没有登录的  重新登录初始化
    private void init() {
        JPushInterface.init(mContext);
    }


    //从JPISH服务器接收客户MSG

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";


    //接收消息
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }


    /**
     * 设置webview
     *
     * @param webView
     * @param url
     */
    public void SeetingWebView(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        //加载缓存否则网络
        /*if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }*/
        webSettings.setJavaScriptEnabled(true);   // 设置支持javascript脚本
        webView.addJavascriptInterface(new BaseActivity.JSCallAndroid(this), "JSCallAndroid");  //Js调用android  设置
        webSettings.setBuiltInZoomControls(false);  // 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                handler.proceed();// 接受所有网站的证书
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 在APP内部打开链接，不要调用系统浏览器
                view.loadUrl(url);
                return false;
            }
        });
        webView.loadUrl(url);
    }


    //查看大图 与js交互
    public class JSCallAndroid {
        Context context;

        JSCallAndroid(Context c) {
            context = c;
        }

        @JavascriptInterface
        public void getBigImage(String url) {
            Intent intent = new Intent(mContext, ShowBigImageActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
            //overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        }
    }


    /**
     * 设置极光消息
     */
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                }
            } catch (Exception e) {
            }
        }
    }


    /**
     * 弹出软键盘
     *
     * @param view
     */
    public void showInPutKeybord(final View view) {
        view.requestFocus();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(view, 0);
                }
            }
        }, 100);

    }

    //初始化一个OssService用来上传下载
    public OssService initOSS(String endpoint, String bucket) {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        //OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        //使用自己的获取STSToken的类
        String ak = SPTool.getContent(mContext, Constants.AK);
        String sk = SPTool.getContent(mContext, Constants.SK);
        String token = SPTool.getContent(mContext, Constants.TOKEN);
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ak, sk, token);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        return new OssService(oss, bucket, (OssService.picResultCallback) this);
    }


}
