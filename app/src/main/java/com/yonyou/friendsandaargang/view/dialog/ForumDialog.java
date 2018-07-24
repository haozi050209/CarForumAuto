package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.view.dialog.DialogUtils;

/**
 * Created by shibing on 18/3/22.
 * <p>
 * <p>
 * 论坛
 */

public class ForumDialog extends DialogUtils {


    private LinearLayout forumDialog;
    private ImageView exitDialog;
    private LinearLayout huatiDialog;


    public ForumDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public ForumDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public ForumDialog(Context context) {
        super(context);
        initView();
    }

    public ForumDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }


    public LinearLayout getForumDialog() {
        return forumDialog;
    }

    public void setForumDialog(LinearLayout forumDialog) {
        this.forumDialog = forumDialog;
    }

    public ImageView getExitDialog() {
        return exitDialog;
    }

    public void setExitDialog(ImageView exitDialog) {
        this.exitDialog = exitDialog;
    }

    public LinearLayout getHuatiDialog() {
        return huatiDialog;
    }

    public void setHuatiDialog(LinearLayout huatiDialog) {
        this.huatiDialog = huatiDialog;
    }


    //帖子监听事件
    public void setForunListener(View.OnClickListener sureListener) {
        forumDialog.setOnClickListener(sureListener);
    }

    //话题监听事件
    public void setHuaTiListener(View.OnClickListener cancelListener) {
        huatiDialog.setOnClickListener(cancelListener);
    }

    //退出监听事件
    public void setExitListener(View.OnClickListener cancelListener) {
        exitDialog.setOnClickListener(cancelListener);
    }


    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_forum, null);
        forumDialog = (LinearLayout) dialogView.findViewById(R.id.tizi_layout);
        exitDialog = (ImageView) dialogView.findViewById(R.id.tuichu);
        huatiDialog = (LinearLayout) dialogView.findViewById(R.id.huati_layout);
        setContentView(dialogView);
    }
}
