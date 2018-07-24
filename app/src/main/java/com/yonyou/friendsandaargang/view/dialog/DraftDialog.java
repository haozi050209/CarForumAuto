package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/4/4.
 * <p>
 * 草稿
 */

public class DraftDialog extends DialogUtils {


    private TextView save_draft;
    private TextView no_draft;
    private TextView cancel_draf;


    public TextView getSave_draft() {
        return save_draft;
    }

    public void setSave_draft(TextView save_draft) {
        this.save_draft = save_draft;
    }

    public TextView getNo_draft() {
        return no_draft;
    }

    public void setNo_draft(TextView no_draft) {
        this.no_draft = no_draft;
    }

    public TextView getCancel_draf() {
        return cancel_draf;
    }

    public void setCancel_draf(TextView cancel_draf) {
        this.cancel_draf = cancel_draf;
    }

    public DraftDialog(Context context, int themeResId) {
        super(context, themeResId);
        getView();
    }

    public DraftDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        getView();
    }

    public DraftDialog(Context context) {
        super(context);
        getView();
    }

    public DraftDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        getView();
    }


    //不保存
    public void setNoDraftListener(View.OnClickListener Listener) {
        no_draft.setOnClickListener(Listener);
    }

    //保存草稿
    public void setSaveDraftListener(View.OnClickListener savelListener) {
        save_draft.setOnClickListener(savelListener);
    }

    //取消
    public void setCancelDrafListener(View.OnClickListener cancelListener) {
        cancel_draf.setOnClickListener(cancelListener);
    }

    private void getView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_draft, null);
        save_draft = (TextView) view.findViewById(R.id.save_draft);
        no_draft = (TextView) view.findViewById(R.id.no_draft);
        cancel_draf = (TextView) view.findViewById(R.id.cancel_draf);
        setContentView(view);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }
}
