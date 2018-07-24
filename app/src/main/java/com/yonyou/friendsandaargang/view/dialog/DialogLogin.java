package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/3/14.
 */

public class DialogLogin extends DialogUtils {

    private TextView tv_celan;
    private TextView tv_sure;

    public TextView getTv_celan() {
        return tv_celan;
    }

    public void setTv_celan(TextView tv_celan) {
        this.tv_celan = tv_celan;
    }

    public TextView getTv_sure() {
        return tv_sure;
    }

    public void setTv_sure(TextView tv_sure) {
        this.tv_sure = tv_sure;
    }

    public DialogLogin(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DialogLogin(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DialogLogin(Context context) {
        super(context);
        initView();
    }

    public DialogLogin(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }


    //确认
    public void setSureListener(View.OnClickListener listener) {
        tv_sure.setOnClickListener(listener);
    }


    //确认
    public void setCleanListener(View.OnClickListener listener) {
        tv_celan.setOnClickListener(listener);
    }




    /*public void setContent(String str) {
        if (RxRegTool.isURL(str)) {
            // 响应点击事件的话必须设置以下属性
            mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
            mTvContent.setText(RxTextTool.getBuilder("").setBold().append(str).setUrl(str).create());//当内容为网址的时候，内容变为可点击
        } else {
            mTvContent.setText(str);
        }

    }*/

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_login, null);
        tv_sure = (TextView) dialogView.findViewById(R.id.tv_sure);
        tv_celan = (TextView) dialogView.findViewById(R.id.tv_celan);
        setContentView(dialogView);
    }
}
