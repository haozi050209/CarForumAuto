package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.activirty.ShowBigImageActivity;
import com.yonyou.friendsandaargang.homepage.modle.QAReplyLisBean;
import com.yonyou.friendsandaargang.homepage.modle.QaDetalisBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.QAReplyListAdapter;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyListView;
import com.yonyou.friendsandaargang.view.NoScrollWebView;
import com.yonyou.friendsandaargang.view.dialog.DialogShowImage;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;
import com.yonyou.friendsandaargang.view.dialog.ShardDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/6/1.
 */

public class QaDetalisActivity extends BaseActivity
        implements UMShareListener
        , OnItemClickListener {


    @BindView(R.id.qa_detalis_image)
    CircleImageView imageView;
    @BindView(R.id.qa_detalis_name)
    TextView tvName;
    @BindView(R.id.qa_detalis_tiem)
    TextView tvTime;
    @BindView(R.id.qa_detalis_money)
    TextView tvMoney;
    @BindView(R.id.qa_detalis_content)
    TextView tvContent;
    @BindView(R.id.qa_detalis_webview)
    NoScrollWebView webView;
    @BindView(R.id.qa_detalis_list)
    MyListView listView;
    @BindView(R.id.qedetalis_nohuida)
    TextView tvNohuidTitle;
    @BindView(R.id.qadetalis_no_reply)
    TextView tvNoreply;
    @BindView(R.id.qa_detalis_answer_ray)
    RelativeLayout rayAnswer;
    @BindView(R.id.qa_detalis_answer_ed)
    EditText edAnswer;              //回答问题


    private int thumbsCall;
    private String userId;
    private String postId;
    private String webPath;
    private ShardDialog shardDialog;
    private String shareUrl;
    private UMShareAPI shareAPI;
    private QaDetalisBean.ContentBean detalisBean;
    private List<QAReplyLisBean.ContentBean> listReply;
    private QAReplyListAdapter replyListAdapter;
    private String publisherId;   //发布者id
    private String AnswerName;
    private String isCanAnswer;
    private String content;
    private int IsSelected;      //是否被采纳回答
    private DialogSureCancel dialogSureCancel;
    private DialogShowImage dialogShowImage;
    private int hours, minute;
    private boolean isLogin;
    private Long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qadetalis);
        ButterKnife.bind(this);
        initviews();
        getQaDetalisInfo();

    }

    private void initviews() {
        shareAPI = UMShareAPI.get(mContext);
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        postId = getIntent().getStringExtra(Constants.POSTID);
        isLogin = SPTool.getBoolean(mContext, Constants.ISLOGIN);

        getTitleBar();
        putEdAnswer();
    }


    private void putEdAnswer() {
        if (!isLogin) {
            edAnswer.setFocusableInTouchMode(false);
            edAnswer.setFocusable(false);
        }

        edAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    content = edAnswer.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.normal(mContext, "回复内容不能为空").show();
                        return false;
                    }
                    getAnswerQuestion(content);
                    edAnswer.setText("");
                    return false;
                }
                return false;
            }
        });
    }


    @OnClick({R.id.titleBar_right_text, R.id.qa_detalis_answer_ed})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.titleBar_right_text:
                getQaDetalisShard();
                break;
            case R.id.qa_detalis_answer_ed:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                if (isCanAnswer.equals("0")) {
                    ToastUtils.normal(mContext, "需本专区所属职位认证才可回答问题哦").show();
                    return;
                }
                break;
        }
    }


    //获取问答详情
    private void getQaDetalisInfo() {
        Call<QaDetalisBean> call = communityService().getQuestionInfo(userId, postId);
        NetUtils<QaDetalisBean> netUtils = new NetUtils<QaDetalisBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<QaDetalisBean>() {
            @Override
            public void onResponseCallback(QaDetalisBean qaDetalisBean) {
                if (qaDetalisBean.getReturnCode() != 0 && !qaDetalisBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, qaDetalisBean.getReturnMsg()).show();
                    return;
                }
                getQaData(qaDetalisBean);


            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }

    //用户信息处理
    private void getQaData(QaDetalisBean qaDetalisBean) {
        detalisBean = qaDetalisBean.getContent();

        setTitleText(detalisBean.getForumName()).rightImageRes(R.drawable.shard);
        GlideManager.loadImage(mContext, qaDetalisBean.getContent().getAvatar(), R.drawable.user, imageView);
        tvName.setText(qaDetalisBean.getContent().getViewerNickname());
        //超过回答时间了
        if (qaDetalisBean.getContent().getHours() == 0 || qaDetalisBean.getContent().getMinute() < 0) {
            time = TimeUtil.timeStrToSecond(qaDetalisBean.getContent().getPostDate());
            tvTime.setText(TimeUtil.getSpaceTime(time));
        } else {
            tvTime.setText("还剩" + qaDetalisBean.getContent().getHours() + "小时");
        }
        tvContent.setText(qaDetalisBean.getContent().getTitle());

        //剩余时间
        hours = qaDetalisBean.getContent().getHours();
        minute = qaDetalisBean.getContent().getMinute();
        //发布者id
        publisherId = detalisBean.getAuthor();
        //是否可以回答


        //是本人发布的  且超过时间了  隐藏掉回答布局
        if (userId.equals(detalisBean.getAuthor())
                || detalisBean.getHours() == 0
                || detalisBean.getMinute() < 0) {
            rayAnswer.setVisibility(View.GONE);
        } else {
            rayAnswer.setVisibility(View.VISIBLE);
        }

        isCanAnswer = detalisBean.getCanAnswer();
        if (isCanAnswer.equals("0")) {
            edAnswer.setFocusableInTouchMode(false);
            edAnswer.setFocusable(false);
        }


        if (qaDetalisBean.getContent().getHaveContent() == 0) {
            webView.setVisibility(View.GONE);
        } else {
            webView.setVisibility(View.VISIBLE);
            // TODO: 18/6/6   获取到首页信息后在获取htmel与回答列表
            getWebData();
        }
        getAnswerList();
    }


    //设置webUrl
    private void getWebData() {
        webPath = ApiService.BaseUrl + "getPostContent?" + "postId=" + postId + "&os=android";
        WebSettings webSettings = webView.getSettings();
        //加载缓存否则网络
        /*if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }*/
        // 设置支持javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //Js调用android  设置¬
        webView.addJavascriptInterface(new JSCallAndroid(this), "JSCallAndroid");
        webSettings.setBuiltInZoomControls(false);
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
                return true;
            }
        });
        webView.loadUrl(webPath);
        Logger.e("---webPath----", webPath);

    }


    //查看大图 与js交互
    public class JSCallAndroid {
        Context context;

        JSCallAndroid(Context c) {
            context = c;
        }

        @JavascriptInterface
        public void getBigImage(String url) {
            showDialogIamge(url);
        }

    }


    public void showDialogIamge(String url) {
        Intent intent = new Intent(mContext, ShowBigImageActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


    //获取回答列表
    private void getAnswerList() {
        Call<QAReplyLisBean> call = communityService().getQAReplyList(userId, postId, 1);
        call.enqueue(new NetRetrofitCallback<QAReplyLisBean>(mContext, new HttpCallBackImpl<QAReplyLisBean>() {
            @Override
            public void onResponseCallback(QAReplyLisBean qaReplyLisBean) {
                if (qaReplyLisBean.getReturnCode() != 0 && !qaReplyLisBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, qaReplyLisBean.getReturnMsg()).show();
                    return;
                }
                ReplyData(qaReplyLisBean);
            }
        }));
    }


    /**
     * 数据处理
     *
     * @param qaReplyLisBean
     */
    private void ReplyData(QAReplyLisBean qaReplyLisBean) {
        listReply = qaReplyLisBean.getContent();
        if (listReply.size() == 0) {
            tvNohuidTitle.setText("暂无回答");
            tvNoreply.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }
        tvNohuidTitle.setText("全部回答");
        tvNoreply.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        replyListAdapter = new QAReplyListAdapter(mContext, listReply, publisherId, hours, minute);
        listView.setAdapter(replyListAdapter);
        replyListAdapter.addOnItemClickListener(this);
    }


    //回答问题
    private void getAnswerQuestion(String content) {
        Call<HttpResult> call = communityService().answerQuestion(postId, content, userId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getAnswerList();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 回复点赞
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        thumbsCall = listReply.get(position).getThumbuped();
        IsSelected = listReply.get(position).getIsSelected();
        AnswerName = listReply.get(position).getNickname();
        if (userId.equals(publisherId) && IsSelected == 0 && (hours != 0 || minute > 0)) {
            if (!isLogin) {
                ActivityManger.skipActivity(mContext, ActivityLogin.class);
                return;
            }
            showAnswerDialog(AnswerName, listReply.get(position).getReplyId());
        } else {
            if (thumbsCall == 1) {
                QaReplyThumbs(listReply.get(position).getReplyId(), -1);
            } else {
                QaReplyThumbs(listReply.get(position).getReplyId(), 1);
            }
        }
    }


    /**
     * 点赞or取消点赞
     */
    private void QaReplyThumbs(String replyId, final int thumbs) {
        Call<HttpResult> call = communityService().thumbUpOrDown4Answer(userId, replyId, thumbs);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getAnswerList();
            }
        }));
    }


    //采纳回答dialog
    private void showAnswerDialog(String name, final String replyId) {
        dialogSureCancel = new DialogSureCancel(mContext);
        dialogSureCancel.setCancelable(true);
        dialogSureCancel.setTitle("您确定要采纳" + name + "的答案吗");
        dialogSureCancel.setContent("采纳后您的悬赏将自动进入对方的户");
        dialogSureCancel.getTitleView().setTextColor(getResources().getColor(R.color.color_balank));
        dialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnswer(replyId);
                dialogSureCancel.dismiss();

            }
        });
        dialogSureCancel.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.show();
    }


    //采纳回答
    private void selectAnswer(String replyId) {
        Call<HttpResult> call = communityService().selectAnswer(replyId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getAnswerList();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    //分享问答详情
    private void getQaDetalisShard() {
        shareUrl = ApiService.BaseUrl + "getPostContent?" + "postId=" + postId + "&os=share";
        final UMWeb web = new UMWeb(shareUrl);         //分享出去url
        web.setTitle(detalisBean.getViewerNickname());                     //标题
        web.setDescription(detalisBean.getTitle());            //描述
        shardDialog = new ShardDialog(mContext);
        shardDialog.setCancelable(false);
        //取消监听事件
        shardDialog.setExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shardDialog.dismiss();
            }
        });
        //微信好友监听事件
        shardDialog.setWeChatListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shareAPI.isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                    ToastUtils.normal(mContext, "请检查是否安装微信").show();
                    shardDialog.dismiss();
                    return;
                }
                new ShareAction(mContext)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withMedia(web)
                        .setCallback(QaDetalisActivity.this)//回调监听器
                        .share();
                shardDialog.dismiss();
            }
        });
        //微信朋友圈监听事件
        shardDialog.setWeChatCircleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shareAPI.isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                    ToastUtils.normal(mContext, "请检查是否安装微信").show();
                    shardDialog.dismiss();
                    return;
                }
                new ShareAction(mContext)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                        .withMedia(web)
                        .setCallback(QaDetalisActivity.this)//回调监听器
                        .share();
                shardDialog.dismiss();
            }
        });

        //QQ空间
        shardDialog.setQQZoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shareAPI.isInstall(mContext, SHARE_MEDIA.QQ)) {
                    ToastUtils.normal(mContext, "请检查是否安装QQ空间").show();
                    shardDialog.dismiss();
                    return;
                }
                new ShareAction(mContext)
                        .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                        .withMedia(web)
                        .setCallback(QaDetalisActivity.this)//回调监听器
                        .share();
                shardDialog.dismiss();
            }
        });

        //qq
        shardDialog.setQQfriendsListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shareAPI.isInstall(mContext, SHARE_MEDIA.QQ)) {
                    ToastUtils.normal(mContext, "请检查是否安装QQ").show();
                    shardDialog.dismiss();
                    return;
                }
                new ShareAction(mContext)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withMedia(web)
                        .setCallback(QaDetalisActivity.this)//回调监听器
                        .share();

                shardDialog.dismiss();
            }
        });

        //复制链接
        shardDialog.setCopylinkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shardDialog.dismiss();
            }
        });
        shardDialog.show();


    }


    /**
     * QQ分享的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param share_media 平台类型
     * @descrption 分享开始的回调
     */
    @Override
    public void onStart(SHARE_MEDIA share_media) {
    }

    /**
     * 分享成功
     *
     * @param share_media
     */
    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtils.normal(mContext, "分享成功").show();
    }

    /**
     * 分享失败
     *
     * @param share_media
     * @param throwable
     */
    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtils.normal(mContext, "分享失败").show();
    }


    /**
     * 取消分享
     *
     * @param share_media
     */
    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        ToastUtils.normal(mContext, "您取消了分享").show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shardDialog != null && dialogSureCancel != null) {
            shardDialog.dismiss();
            dialogSureCancel.dismiss();

        }
    }


}
