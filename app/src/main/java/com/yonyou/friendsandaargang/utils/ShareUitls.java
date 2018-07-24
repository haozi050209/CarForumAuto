package com.yonyou.friendsandaargang.utils;

import android.content.Context;
import android.view.View;

import com.yonyou.friendsandaargang.view.dialog.ShardDialog;

/**
 * Created by shibing on 18/4/25.
 */

public class ShareUitls {


    private ShardDialog shardDialog;


    public ShareUitls(Context context) {
        shareDialog(context);
    }


    public void shareDialog(Context context) {
        shardDialog = new ShardDialog(context);
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
                shardDialog.dismiss();
            }
        });
        //微信朋友圈监听事件
        shardDialog.setWeChatCircleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shardDialog.dismiss();
            }
        });

        //QQ空间
        shardDialog.setQQZoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shardDialog.dismiss();
            }
        });

        //qq
        shardDialog.setQQfriendsListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
