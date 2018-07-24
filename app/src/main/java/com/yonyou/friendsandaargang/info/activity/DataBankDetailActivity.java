package com.yonyou.friendsandaargang.info.activity;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.bean.DataBankDetailBean;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class DataBankDetailActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webView;

    private int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bank_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        getTitleBar();
        dataId = getIntent().getExtras().getInt("dataId");
        getDataBankDetail();
    }

    public void getDataBankDetail() {
        showProgressDialog();
        Call<HttpResult<DataBankDetailBean>> call = communityService().getDataBankDetail(dataId);
        call.enqueue(new RetrofitCallback<HttpResult<DataBankDetailBean>>() {
            @Override
            public void onSuccess(HttpResult<DataBankDetailBean> model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg());
                    return;
                }
                initData(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }

    private void initData(HttpResult<DataBankDetailBean> model) {

        String fileId = model.getContent().getFileId();
        String fileExt = model.getContent().getFileExt();
        String fileUrl = model.getContent().getFileUrl();

        setTitleText(fileId.substring(0, fileId.indexOf(".")));

        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//加载缓存否则网络
        }
        webSettings.setDefaultTextEncodingName("GBK");
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setBuiltInZoomControls(true);// 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 在APP内部打开链接，不要调用系统浏览器
                view.loadUrl(url);
                return true;
            }
        });
        Log.e("~~~~~", fileId + "  " + fileUrl);

        switch (fileExt) {
            case "jpg":
            case "jpeg":
            case "png":
            case "bmp":
            case "gif":
            case "pdf":
                webView.loadDataWithBaseURL(null, "<img src=" + fileUrl + ">", "text/html", "charset=UTF-8", null);
                break;
            case "doc":
            case "docx":
            case "xls":
            case "xlsx":
            case "ppt":
            case "pptx":
                String encodeurl = fileUrl;
                try {
                    encodeurl = URLEncoder.encode(fileUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                webView.loadUrl("https://view.officeapps.live.com/op/view.aspx?src=" + encodeurl);
                break;
            case "txt":
            case "mp4":
                webView.loadUrl(fileUrl);
                break;
        }
    }
}
