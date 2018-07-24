package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.view.pageview.PageIndicatorView;
import com.yonyou.friendsandaargang.view.pageview.PageRecyclerView;

/**
 * Created by Administrator on 2018/6/5.
 */

public class InterestFieldDialog extends DialogUtils {

    private PageRecyclerView pageview;
    private Button btnsure;
    private ImageView ivexit;

    public InterestFieldDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public InterestFieldDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public InterestFieldDialog(Context context) {
        super(context);
        initView();
    }

    public InterestFieldDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public PageRecyclerView getPageview() {
        return pageview;
    }

    public void setPageview(PageRecyclerView pageview) {
        this.pageview = pageview;
    }

    public Button getBtnsure() {
        return btnsure;
    }

    public void setBtnsure(Button btnsure) {
        this.btnsure = btnsure;
    }

    public ImageView getIvexit() {
        return ivexit;
    }

    public void setIvexit(ImageView ivexit) {
        this.ivexit = ivexit;
    }


    public void setBtnSrueListener(View.OnClickListener onClickListener) {
        btnsure.setOnClickListener(onClickListener);
    }

    public void setExitListener(View.OnClickListener onClickListener) {
        ivexit.setOnClickListener(onClickListener);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_interestfield, null);
        pageview = view.findViewById(R.id.pageview);
        btnsure = view.findViewById(R.id.btn_sure);
        ivexit = view.findViewById(R.id.iv_exit);

        pageview.setIndicator((PageIndicatorView) view.findViewById(R.id.indicator));
        pageview.setPageSize(3, 3);
        pageview.setPageMargin(0);

        setContentView(view);
    }
}
