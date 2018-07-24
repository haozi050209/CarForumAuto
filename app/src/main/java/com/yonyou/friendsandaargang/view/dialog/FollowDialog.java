package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/4/26.
 * <p>
 * 关注
 */

public class FollowDialog extends DialogUtils {


    TextView dialog_forum_title;
    TextView sure;
    TextView cancel;


    public TextView getDialog_forum_title() {
        return dialog_forum_title;
    }

    public void setDialog_forum_title(TextView dialog_forum_title) {
        this.dialog_forum_title = dialog_forum_title;
    }

    public TextView getSure() {
        return sure;
    }

    public void setSure(TextView sure) {
        this.sure = sure;
    }

    public TextView getCancel() {
        return cancel;
    }

    public void setCancel(TextView cancel) {
        this.cancel = cancel;

    }

    public FollowDialog(Context context, int themeResId) {
        super(context, themeResId);
        initview();
    }

    public FollowDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initview();
    }

    public FollowDialog(Context context) {
        super(context);
        initview();
    }

    public FollowDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
    }


    public void setSureListener(View.OnClickListener listener) {
        sure.setOnClickListener(listener);
    }


    public void setCancelListener(View.OnClickListener listener) {
        cancel.setOnClickListener(listener);
    }


    private void initview() {

        View view;
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_follow, null);
        dialog_forum_title = (TextView) view.findViewById(R.id.dialog_forum_title);
        sure = (TextView) view.findViewById(R.id.sure);
        cancel = (TextView) view.findViewById(R.id.cancel);
        setContentView(view);

    }
}
