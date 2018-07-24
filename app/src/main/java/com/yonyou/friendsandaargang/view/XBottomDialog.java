package com.yonyou.friendsandaargang.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;

import static com.yonyou.friendsandaargang.utils.DeviceTool.getScreenHeight;
import static com.yonyou.friendsandaargang.utils.StatusUtil.getStatusBarHeight;


/**
 * @author huangshuang
 */

public abstract class XBottomDialog extends BottomSheetDialog implements View.OnClickListener {
    public View view;
    protected Context mContext;

    public XBottomDialog(@NonNull Context mContext) {
        super(mContext);
        this.mContext = mContext;

        view = View.inflate(mContext, getLayoutId(), null);
        setContentView(view);

        initView();
        setListener();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void setListener();


    @Override
    protected void onStart() {
        super.onStart();
        setPeekHeight();
    }

    /**
     * 设置展开的默认的高度
     */
    private void setPeekHeight() {
        BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * 处理在某些手机上面存在状态栏不透明的问题
     */
    private void init() {
        int screenHeight = getScreenHeight(mContext);
        int statusBarHeight = getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0
                    ? ViewGroup.LayoutParams.MATCH_PARENT
                    : dialogHeight);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
