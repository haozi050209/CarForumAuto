package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/4/25.
 * <p>
 * <p>
 * 分享dialog
 */

public class ShardDialog extends DialogUtils {


    private LinearLayout wx_lay;
    private LinearLayout pyq_lay;
    private LinearLayout qqkj_lay;
    private LinearLayout qq_lay;
    private LinearLayout fzlj_lay;
    private TextView share_cancel_tv;

    public LinearLayout getFzlj_lay() {
        return fzlj_lay;
    }

    public void setFzlj_lay(LinearLayout fzlj_lay) {
        this.fzlj_lay = fzlj_lay;
    }

    public LinearLayout getWx_lay() {
        return wx_lay;
    }

    public void setWx_lay(LinearLayout wx_lay) {
        this.wx_lay = wx_lay;
    }

    public LinearLayout getPyq_lay() {
        return pyq_lay;
    }

    public void setPyq_lay(LinearLayout pyq_lay) {
        this.pyq_lay = pyq_lay;
    }

    public LinearLayout getQqkj_lay() {
        return qqkj_lay;
    }

    public void setQqkj_lay(LinearLayout qqkj_lay) {
        this.qqkj_lay = qqkj_lay;
    }

    public LinearLayout getQq_lay() {
        return qq_lay;
    }

    public void setQq_lay(LinearLayout qq_lay) {
        this.qq_lay = qq_lay;
    }

    public TextView getShare_cancel_tv() {
        return share_cancel_tv;
    }

    public void setShare_cancel_tv(TextView share_cancel_tv) {
        this.share_cancel_tv = share_cancel_tv;
    }

    public ShardDialog(Context context, int themeResId) {
        super(context, themeResId);
        initview();
    }

    public ShardDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initview();
    }

    public ShardDialog(Context context) {
        super(context);
        initview();
    }

    public ShardDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initview();
    }


    //取消监听事件

    public void setExitListener(View.OnClickListener cancelListener) {
        share_cancel_tv.setOnClickListener(cancelListener);
    }

    //微信监听事件
    public void setWeChatListener(View.OnClickListener cancelListener) {
        wx_lay.setOnClickListener(cancelListener);
    }

    //微信朋友圈监听事件
    public void setWeChatCircleListener(View.OnClickListener cancelListener) {
        pyq_lay.setOnClickListener(cancelListener);
    }

    //QQ空间监听事件
    public void setQQZoneListener(View.OnClickListener cancelListener) {
        qqkj_lay.setOnClickListener(cancelListener);
    }

    //QQ监听事件
    public void setQQfriendsListener(View.OnClickListener cancelListener) {
        qq_lay.setOnClickListener(cancelListener);
    }

    //QQ监听事件
    public void setCopylinkListener(View.OnClickListener cancelListener) {
        fzlj_lay.setOnClickListener(cancelListener);
    }

    private void initview() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shard, null);
        share_cancel_tv = (TextView) view.findViewById(R.id.share_cancel);
        wx_lay = (LinearLayout) view.findViewById(R.id.share_wx_lay);
        pyq_lay = (LinearLayout) view.findViewById(R.id.share_pyq_lay);
        qqkj_lay = (LinearLayout) view.findViewById(R.id.share_qqkj_lay);
        qq_lay = (LinearLayout) view.findViewById(R.id.share_qq_lay);
        fzlj_lay = (LinearLayout) view.findViewById(R.id.share_fzlj_lay);
        setContentView(view);
        mLayoutParams.gravity = Gravity.BOTTOM;
        setFullScreenWidth();

    }
}
