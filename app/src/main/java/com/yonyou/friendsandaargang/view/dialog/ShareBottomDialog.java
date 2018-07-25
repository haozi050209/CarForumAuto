package com.yonyou.friendsandaargang.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.utils.ToastUtils;

/**
 * Created by shibing on 18/7/25.
 */

public class ShareBottomDialog extends XBottomDialog implements UMShareListener {


    private String title, content, url;
    private LinearLayout wx_lay;
    private LinearLayout pyq_lay;
    private LinearLayout qqkj_lay;
    private LinearLayout qq_lay;
    private LinearLayout fzlj_lay;
    private TextView share_cancel_tv;
    private UMShareAPI shareAPI;
    private UMWeb umWeb;
    private Activity activity;
    private String sarePlatform;
    private ShareAction shareAction;

    public ShareBottomDialog(@NonNull Activity activity, String shareTitle
            , String shareUrl, String shareContent) {
        super(activity);
        this.activity = activity;
        this.title = shareTitle;
        this.url = shareUrl;
        this.content = shareContent;
        shareAPI = UMShareAPI.get(activity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_shard;
    }

    @Override
    protected void initView() {
        share_cancel_tv = findViewById(R.id.share_cancel);
        wx_lay = findViewById(R.id.share_wx_lay);
        pyq_lay = findViewById(R.id.share_pyq_lay);
        qqkj_lay = findViewById(R.id.share_qqkj_lay);
        qq_lay = findViewById(R.id.share_qq_lay);
        fzlj_lay = findViewById(R.id.share_fzlj_lay);
    }

    @Override
    protected void setListener() {
        wx_lay.setOnClickListener(this);
        pyq_lay.setOnClickListener(this);
        qqkj_lay.setOnClickListener(this);
        qq_lay.setOnClickListener(this);
        fzlj_lay.setOnClickListener(this);
        share_cancel_tv.setOnClickListener(this);  //取消
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_cancel:   //取消
                dismiss();
                break;
            case R.id.share_wx_lay:  //微信
                if (!shareAPI.isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                    ToastUtils.normal(activity, "请检查是否安装微信").show();
                    dismiss();
                    return;
                }
                UMShard("WeChat");
                break;
            case R.id.share_pyq_lay: //朋友圈
                if (!shareAPI.isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                    ToastUtils.normal(activity, "请检查是否安装微信").show();
                    dismiss();
                    return;
                }
                UMShard("WeChatfriends");
                break;
            case R.id.share_qqkj_lay: //qq空间
                if (!shareAPI.isInstall( activity, SHARE_MEDIA.QQ)) {
                    ToastUtils.normal(activity, "请检查是否安装QQ空间").show();
                    dismiss();
                    return;
                }
                UMShard("QQZone");
                break;
            case R.id.share_qq_lay:   //qq
                if (!shareAPI.isInstall(activity, SHARE_MEDIA.QQ)) {
                    ToastUtils.normal(activity, "请检查是否安装QQ").show();
                    dismiss();
                    return;
                }
                UMShard("QQ");
                break;
            case R.id.share_fzlj_lay:  //复制链接
                break;
        }
    }


    /**
     * 分享
     *
     * @param platform
     */
    private void UMShard(String platform) {
        shareAction = new ShareAction(activity);
        umWeb = new UMWeb(url);
        umWeb.setTitle(title);                     //标题
        umWeb.setDescription(content);            //描述
        switch (platform) {
            case "WeChat":
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN);//传入平台
                break;
            case "WeChatfriends":
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);//传入平台
                break;
            case "QQZone":
                shareAction.setPlatform(SHARE_MEDIA.QZONE);//传入平台
                break;
            case "QQ":
                shareAction.setPlatform(SHARE_MEDIA.QQ);//传入平台
                break;
        }
        shareAction.withMedia(umWeb);
        shareAction.setCallback(this);//回调监听器
        shareAction.share();
        dismiss();
    }

    /**
     * @param share_media 平台类型
     * @descrption 分享开始的回调
     */
    @Override
    public void onStart(SHARE_MEDIA share_media) {
        ToastUtils.normal(activity, "正在初始化分享").show();
    }

    /**
     * 分享成功
     *
     * @param share_media
     */
    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtils.normal(activity, "分享成功").show();
    }


    /**
     * 分享失败
     *
     * @param share_media
     * @param throwable
     */
    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtils.normal(activity, "分享失败").show();
    }


    /**
     * 取消分享
     *
     * @param share_media
     */
    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        ToastUtils.normal(activity, "您取消了分享").show();

    }
}
