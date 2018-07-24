package com.yonyou.friendsandaargang.forum.activirty;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yonyou.friendsandaargang.forum.adapter.ReplyMainAdapter;
import com.yonyou.friendsandaargang.forum.bean.ForumDetalis;
import com.yonyou.friendsandaargang.forum.bean.ReplyList;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.FileTool;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyRecylerview;
import com.yonyou.friendsandaargang.view.dialog.FollowDialog;
import com.yonyou.friendsandaargang.view.dialog.ShardDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/3/7.
 */

public class PostDetailsActivity extends BaseActivity
        implements UMShareListener, TextView.OnEditorActionListener, OnItemClickListener {
    @BindView(R.id.forumdetails_webview)
    WebView webView;
    //回复
    @BindView(R.id.forum_details_reply_edit_two)
    EditText reply_edit;
    //点赞
    @BindView(R.id.dianzan_img)
    ImageView dianzan_img;
    // 收藏
    @BindView(R.id.shoucan_lay)
    LinearLayout shoucan_lay;
    @BindView(R.id.shoucan_img)
    ImageView shoucan_img;
    //title
    @BindView(R.id.detalis_title)
    TextView detalis_title;
    //头像
    @BindView(R.id.detalis_avaer)
    CircleImageView detalis_avaer;
    //皇冠
    @BindView(R.id.detalis_hg)
    ImageView detalis_hg;
    //皇冠等级
    @BindView(R.id.detalis_hg_text)
    TextView detalis_hg_text;
    //姓名
    @BindView(R.id.detalis_name)
    TextView detalis_name;
    //职称
    @BindView(R.id.detalis_identity)
    TextView detalis_identity;
    //时间
    @BindView(R.id.detalis_time)
    TextView detalis_time;
    //是否收藏
    @BindView(R.id.detalis_follow)
    TextView detalis_follow;
    // 来自哪个论坛
    @BindView(R.id.detalis_forum)
    TextView detalis_forum;
    //来源
    @BindView(R.id.detalis_source)
    TextView detalis_source;
    //浏览次数
    @BindView(R.id.detalis_liulan)
    TextView detalis_liulan;
    //评论次数
    @BindView(R.id.detalis_pinlun)
    TextView detalis_pinlun;
    //是否有评论
    @BindView(R.id.detalis_pinlun_text)
    TextView detalis_pinlun_text;
    //评论列表
    @BindView(R.id.detalis_pinlun_list)
    MyRecylerview recylerview;
    //关注
    @BindView(R.id.dianzan_noucm)
    TextView dianzan_noucm;


    private LinearLayoutManager manager;

    private ReplyMainAdapter replyAdapter;
    private String followId;       // 作者id
    private int Isfollow;          //是否关注
    private int IsCollection;      //是否收藏
    private int Isthumbuped;       //是否点赞


    private String webPath, title, shareUrl;
    //帖子id
    private String postId;
    private String userId;
    //回复内容
    private String RepleContent;
    private String replierId;
    //mainRepliedId 一级回复的者的id
    private String mainRepliedId;
    //directRepliedId 二级回复者的Id，
    private String directRepliedId;


    private List<ReplyList.ContentBean> list;
    //分享的dialog
    private ShardDialog shardDialog;
    private int dianzan;
    private int replyDianzan;
    private String forumId;
    private String sharetitle;
    private String sharecontent;
    private boolean isLogin;
    private UMShareAPI shareAPI;
    private FollowDialog followDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articledetails);
        ButterKnife.bind(this);
        initviews();
        //帖子信息
        getPostInfoData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //评论列表
        getPostReplyMainListData();
    }

    private void initviews() {
        getTitleBar();

        shareAPI = UMShareAPI.get(mContext);
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        isLogin = SPTool.getBoolean(mContext, Constants.ISLOGIN);
        if (!isLogin) {
            reply_edit.setFocusableInTouchMode(false);
            reply_edit.setFocusable(false);
        }
        if (getIntent().getStringExtra(Constants.TITLE) != null) {
            title = getIntent().getStringExtra(Constants.TITLE);
            setTitleText(title).rightImageRes(R.drawable.shard);
        }
        if (getIntent().getStringExtra(Constants.POSTID) != null) {
            postId = getIntent().getStringExtra(Constants.POSTID);
        }


        initrecyler();
        reply_edit.setOnEditorActionListener(this);
    }


    private void initrecyler() {

        detalis_hg.setImageResource(R.drawable.hg);
        detalis_identity.setText("楼主");
        manager = new LinearLayoutManager(mContext);
        recylerview.setLayoutManager(manager);
        recylerview.setNestedScrollingEnabled(false);
    }


    /**
     * 评论
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            RepleContent = reply_edit.getText().toString();
            if (TextUtils.isEmpty(RepleContent)) {
                ToastUtils.normal(mContext, "回复内容不能为空").show();
                return false;
            }
            getReplyMessage(RepleContent);
            reply_edit.setText("");
            return false;
        }
        return false;
    }


    /**
     * 监听事件
     */

    @OnClick({R.id.dianzan_lay, R.id.shoucan_lay, R.id.detalis_follow, R.id.titleBar_right_text, R.id.detalis_source, R.id.title_ray_artivl, R.id.forum_details_reply_edit_two, R.id.detalis_avaer})
    public void OnClick(View view) {
        switch (view.getId()) {
            //点赞  1是已经点赞  －1 是没有点赞
            case R.id.dianzan_lay:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                if (Isthumbuped == 1) {
                    dianzan = -1;
                } else {
                    dianzan = 1;
                }
                getThumbUpOrDown4Post(dianzan);
                break;
            //收藏
            case R.id.shoucan_lay:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                if (IsCollection == 0) {
                    addToFavorites();
                } else if (IsCollection == 1) {
                    getDeleteFromFavorites();
                }
                break;
            //1嗲表已经关注了  调用取消关注
            case R.id.detalis_follow:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                if (Isfollow == 1) {
                    showUnFollowDialog();
                } else {
                    getfollowUser();
                }
                break;
            //分享
            case R.id.titleBar_right_text:
                shareDialog();
                break;
            case R.id.forum_details_reply_edit_two:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                break;
            //帖子来源  跳转至 来源详情
            case R.id.detalis_source:
                Intent intent = new Intent(mContext, ForumDetailsActivity.class);
                if (forumId.equals("1025") || forumId.equals("1026")) {
                    intent.putExtra(Constants.FORUMID, forumId);
                    intent.putExtra("from", "homepage");
                    intent.putExtra(Constants.TITLE, "");
                } else {
                    intent.putExtra(Constants.FORUMID, forumId);
                    intent.putExtra("from", "");
                    intent.putExtra(Constants.TITLE, "");
                }
                startActivity(intent);
                break;
            case R.id.title_ray_artivl:
            case R.id.detalis_avaer:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                Intent intent1 = new Intent(mContext, FensDetailsActivity.class);
                intent1.putExtra(Constants.USER_ID, followId);
                startActivity(intent1);
                break;
        }
    }

    private void showUnFollowDialog() {
        followDialog = new FollowDialog(mContext);
        followDialog.setCancelable(false);
        followDialog.getDialog_forum_title().setText("确定不再关注此人？");
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUnfollowUser();
                followDialog.dismiss();
            }
        });
        followDialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followDialog.dismiss();
            }
        });
        followDialog.show();
    }


    /**
     * 获取帖子信息
     */

    private void getPostInfoData() {
        Call<HttpResult<ForumDetalis>> call = communityService().getPostInfo(userId, postId);
        call.enqueue(new NetRetrofitCallback<HttpResult<ForumDetalis>>(mContext, new HttpCallBackImpl<HttpResult<ForumDetalis>>() {
            @Override
            public void onResponseCallback(HttpResult<ForumDetalis> forumDetalis) {
                if (forumDetalis.getReturnCode() != 0 && !forumDetalis.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, forumDetalis.getReturnMsg()).show();
                    return;
                }
                initInfoData(forumDetalis);
                //中间html
                initDatas();

            }
        }));
    }


    private void initInfoData(HttpResult<ForumDetalis> model) {
        sharetitle = model.getContent().getTitle();
        sharecontent = model.getContent().getBrandName();
        detalis_title.setText(model.getContent().getTitle());
        GlideManager.loadImage(mContext, model.getContent().getAvatar(), R.drawable.user, detalis_avaer);
        detalis_hg_text.setText(model.getContent().getLevel());
        detalis_name.setText(model.getContent().getAuthor());
        Long time = TimeUtil.timeStrToSecond(model.getContent().getPostDate());
        detalis_time.setText(TimeUtil.getSpaceTime(time));


        detalis_source.setText(model.getContent().getForumName());
        detalis_liulan.setText(model.getContent().getReadNumber());
        detalis_pinlun.setText(model.getContent().getReplyNumber());
        dianzan_noucm.setText(model.getContent().getThumbupNumber());
        //作者id
        forumId = model.getContent().getForumId();    //帖子id
        followId = model.getContent().getAuthorId(); // 作者id
        Isfollow = model.getContent().getFollowed(); //是否关注
        IsCollection = model.getContent().getFavorite(); //是否收藏
        Isthumbuped = model.getContent().getThumbuped();   //是否点赞

        //如果是自己发的帖子，不需要关注按钮
        if (userId.equals(model.getContent().getAuthorId())) {
            detalis_follow.setVisibility(View.GONE);
        } else {
            detalis_follow.setVisibility(View.VISIBLE);
            if (model.getContent().getFollowed() == 0) {
                detalis_follow.setText("关注");
                detalis_follow.setTextColor(getResources().getColor(R.color.color_white));
                detalis_follow.setBackgroundResource(R.drawable.shape_follow_red);
            } else {
                detalis_follow.setText("已关注");
                detalis_follow.setBackgroundResource(R.drawable.shape_follow_garly);
                detalis_follow.setTextColor(getResources().getColor(R.color.gray));
            }
        }
        //点赞按钮
        if (model.getContent().getThumbuped() == 1) {
            dianzan_img.setImageResource(R.drawable.comment_zan_high);
        } else {
            dianzan_img.setImageResource(R.drawable.ico_dz);
        }

        //收藏按钮
        if (model.getContent().getFavorite() == 1) {
            shoucan_img.setImageResource(R.drawable.wdsc22x);
        } else {
            shoucan_img.setImageResource(R.drawable.wdsc);
        }


        if (!TextUtils.isEmpty(model.getContent().getBrandName())) {
            detalis_forum.setVisibility(View.VISIBLE);
            detalis_forum.setText(model.getContent().getBrandName());
        } else {
            detalis_forum.setVisibility(View.GONE);
        }
    }


    /**
     * 加载url
     */
    private void initDatas() {
        webPath = ApiService.BaseUrl + "getPostContent?" + "postId=" + postId + "&os=android";
        WebSettings webSettings = webView.getSettings();
        //加载缓存否则网络
        /*if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }*/
        webSettings.setJavaScriptEnabled(true);   // 设置支持javascript脚本
        webView.addJavascriptInterface(new JSCallAndroid(this), "JSCallAndroid");  //Js调用android  设置
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
        webView.loadUrl(webPath);
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
        }
    }


    /**
     * 获取帖子主页下的评论列表
     */

    private void getPostReplyMainListData() {
        Call<ReplyList> call = communityService().getPostReplyMainList(userId, postId);
        call.enqueue(new NetRetrofitCallback<ReplyList>(mContext, new HttpCallBackImpl<ReplyList>() {
            @Override
            public void onResponseCallback(ReplyList model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                RelpyData(model);
            }
        }));
    }

    private void RelpyData(ReplyList model) {
        list = model.getContent();
        if (list.size() == 0) {
            detalis_pinlun_text.setText("暂无讨论");
            recylerview.setVisibility(View.GONE);
            return;
        }
        recylerview.setVisibility(View.VISIBLE);
        detalis_pinlun_text.setText("全部讨论");
        replyAdapter = new ReplyMainAdapter(mContext, list, postId);
        recylerview.setAdapter(replyAdapter);
        replyAdapter.notifyDataSetChanged();
        replyAdapter.addOnItemClickListener(this);
        replyAdapter.setChildClick(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                //点赞  1是已经点赞  －1 是没有点赞
                if (list.get(position).getThumbuped() == 1) {
                    replyDianzan = -1;
                } else {
                    replyDianzan = 1;
                }
                thumbUpOrDown4Reply(replyDianzan, list.get(position).getReplyId());
            }
        });
    }


    /**
     * 评论点击监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, FullReplyActivity.class);
        intent.putExtra(Constants.POSTID, postId);
        intent.putExtra(Constants.REPLYTD, list.get(position).getReplyId());
        intent.putExtra("replierId", list.get(position).getReplierId());
        startActivity(intent);
    }


    /**
     * 关注
     */
    private void getfollowUser() {
        Call<HttpResult> call = communityService().getFollowUser(userId, followId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    return;
                }
                Isfollow = 1;
                ToastUtils.normal(mContext, "已关注").show();
                detalis_follow.setText("已关注");
                detalis_follow.setBackgroundResource(R.drawable.shape_follow_garly);
                detalis_follow.setTextColor(getResources().getColor(R.color.gray));
            }
        }));

    }


    /**
     * 取消关注
     */

    private void getUnfollowUser() {
        Call<HttpResult> call = communityService().getUnFollowUser(userId, followId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    return;
                }
                Isfollow = 0;
                ToastUtils.normal(mContext, "取消关注").show();
                detalis_follow.setText("关注");
                detalis_follow.setBackgroundResource(R.drawable.shape_follow_red);
                detalis_follow.setTextColor(getResources().getColor(R.color.color_white));
            }
        }));
    }


    /**
     * 对帖子 进行评论
     */
    private void getReplyMessage(String RepleContent) {
        Call<HttpResult> call = communityService().getSaveReplyMessage(postId, userId  //回复者id
                , RepleContent      //回复内容
                , mainRepliedId     //一级评论的回复id
                , directRepliedId   //二级评论的id
        );
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {

                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "评论成功").show();
                getPostReplyMainListData();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }


    /**
     * 对帖子点赞/取消点赞
     */
    private void getThumbUpOrDown4Post(final int dianzan) {
        Call<HttpResult> call = communityService().getThumbUpOrDown4Post(userId, postId, dianzan);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getPostInfoData();
            }
        }));
    }


    /**
     * 对回复点赞/取消点赞
     */
    private void thumbUpOrDown4Reply(int dianzan, String replyId) {
        Call<HttpResult> call = communityService().getThumbUpOrDown4Reply(userId, replyId, dianzan);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {

                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getPostReplyMainListData();
            }
        }));
    }


    /**
     * 对帖子进行收藏
     */
    private void addToFavorites() {
        Call<HttpResult> call = communityService().getaddToFavorites(userId, postId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "已收藏").show();
                IsCollection = 1;
                shoucan_img.setImageResource(R.drawable.wdsc22x);
            }
        }));
    }


    /**
     * 对帖子取消收藏
     */

    private void getDeleteFromFavorites() {
        Call<HttpResult> call = communityService().getdeleteFromFavorites(userId, postId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "取消收藏").show();
                IsCollection = 0;
                shoucan_img.setImageResource(R.drawable.wdsc);
            }
        }));
    }


    //分享dialog
    public void shareDialog() {
        shareUrl = ApiService.BaseUrl + "getPostContent?" + "postId=" + postId + "&os=share";
        final UMWeb web = new UMWeb(shareUrl);         //分享出去url
        web.setTitle(sharetitle);                     //标题
        web.setDescription(sharecontent);            //描述
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
                        .setCallback(PostDetailsActivity.this)//回调监听器
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
                        .setCallback(PostDetailsActivity.this)//回调监听器
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
                        .setCallback(PostDetailsActivity.this)//回调监听器
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
                        .setCallback(PostDetailsActivity.this)//回调监听器
                        .share();

                shardDialog.dismiss();
            }
        });

        //复制链接
        shardDialog.setCopylinkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // FileTool.copy(shareUrl, shareUrl);
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
        if (shardDialog != null && followDialog != null) {
            shardDialog.dismiss();
            followDialog.dismiss();
        }
    }
}
