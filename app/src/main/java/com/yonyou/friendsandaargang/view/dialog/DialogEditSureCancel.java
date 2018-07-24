package com.yonyou.friendsandaargang.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;


/**
 * Created by vondear on 2016/7/19.
 * Mainly used for confirmation and cancel.
 */
public class DialogEditSureCancel extends DialogUtils {

    private TextView mTvContent;


    private TextView mTvSure;
    private TextView mTvCancel;
    private EditText editText;
    private TextView mTvTitle;

    public DialogEditSureCancel(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public DialogEditSureCancel(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public DialogEditSureCancel(Context context) {
        super(context);
        initView();
    }

    public DialogEditSureCancel(Activity context) {
        super(context);
        initView();
    }


    public void getEditTexts(String string) {

        string = editText.getText().toString();
    }


    public void setContent(String content) {
        mTvContent.setText(content);
    }

    public TextView getmTvContent() {
        return mTvContent;
    }


    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public TextView getTitleView() {
        return mTvTitle;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(String hint) {
        editText.setHint(hint);
    }

    public TextView getSureView() {
        return mTvSure;
    }

    public void setSure(String strSure) {
        this.mTvSure.setText(strSure);
    }

    public TextView getCancelView() {
        return mTvCancel;
    }

    public void setCancel(String strCancel) {
        this.mTvCancel.setText(strCancel);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edittext_sure_false, null);
        mTvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        mTvContent = dialogView.findViewById(R.id.tv_content);
        mTvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
        mTvCancel = (TextView) dialogView.findViewById(R.id.tv_cancle);
        editText = (EditText) dialogView.findViewById(R.id.editText);
        setContentView(dialogView);
    }
}
