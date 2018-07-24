package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/3/22.
 *
 *
 * 签到  dialog
 */

public class SignDialog extends DialogUtils {


    private TextView sign_day;
    private TextView sign_exp;
    private TextView sign_fortune;
    private ImageView sign_tuichu;
    private FrameLayout sign_ray_1;
    private FrameLayout sign_ray_2;
    private FrameLayout sign_ray_3;
    private FrameLayout sign_ray_4;
    private FrameLayout sign_ray_5;
    private FrameLayout sign_ray_6;
    private FrameLayout sign_ray_7;

    private ImageView sign_cg_1;
    private ImageView sign_cg_2;
    private ImageView sign_cg_3;
    private ImageView sign_cg_4;
    private ImageView sign_cg_5;
    private ImageView sign_cg_6;
    private ImageView sign_cg_7;

    public FrameLayout getSign_ray_1() {
        return sign_ray_1;
    }

    public void setSign_ray_1(FrameLayout sign_ray_1) {
        this.sign_ray_1 = sign_ray_1;
    }

    public FrameLayout getSign_ray_2() {
        return sign_ray_2;
    }

    public void setSign_ray_2(FrameLayout sign_ray_2) {
        this.sign_ray_2 = sign_ray_2;
    }

    public FrameLayout getSign_ray_3() {
        return sign_ray_3;
    }

    public void setSign_ray_3(FrameLayout sign_ray_3) {
        this.sign_ray_3 = sign_ray_3;
    }

    public FrameLayout getSign_ray_4() {
        return sign_ray_4;
    }

    public void setSign_ray_4(FrameLayout sign_ray_4) {
        this.sign_ray_4 = sign_ray_4;
    }

    public FrameLayout getSign_ray_5() {
        return sign_ray_5;
    }

    public void setSign_ray_5(FrameLayout sign_ray_5) {
        this.sign_ray_5 = sign_ray_5;
    }

    public FrameLayout getSign_ray_6() {
        return sign_ray_6;
    }

    public void setSign_ray_6(FrameLayout sign_ray_6) {
        this.sign_ray_6 = sign_ray_6;
    }

    public FrameLayout getSign_ray_7() {
        return sign_ray_7;
    }

    public void setSign_ray_7(FrameLayout sign_ray_7) {
        this.sign_ray_7 = sign_ray_7;
    }

    public ImageView getSign_cg_1() {
        return sign_cg_1;
    }

    public void setSign_cg_1(ImageView sign_cg_1) {
        this.sign_cg_1 = sign_cg_1;
    }

    public ImageView getSign_cg_2() {
        return sign_cg_2;
    }

    public void setSign_cg_2(ImageView sign_cg_2) {
        this.sign_cg_2 = sign_cg_2;
    }

    public ImageView getSign_cg_3() {
        return sign_cg_3;
    }

    public void setSign_cg_3(ImageView sign_cg_3) {
        this.sign_cg_3 = sign_cg_3;
    }

    public ImageView getSign_cg_4() {
        return sign_cg_4;
    }

    public void setSign_cg_4(ImageView sign_cg_4) {
        this.sign_cg_4 = sign_cg_4;
    }

    public ImageView getSign_cg_5() {
        return sign_cg_5;
    }

    public void setSign_cg_5(ImageView sign_cg_5) {
        this.sign_cg_5 = sign_cg_5;
    }

    public ImageView getSign_cg_6() {
        return sign_cg_6;
    }

    public void setSign_cg_6(ImageView sign_cg_6) {
        this.sign_cg_6 = sign_cg_6;
    }

    public ImageView getSign_cg_7() {
        return sign_cg_7;
    }

    public void setSign_cg_7(ImageView sign_cg_7) {
        this.sign_cg_7 = sign_cg_7;
    }

    public ImageView getSign_tuichu() {
        return sign_tuichu;
    }

    public void setSign_tuichu(ImageView sign_tuichu) {
        this.sign_tuichu = sign_tuichu;
    }

    public SignDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public SignDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public SignDialog(Context context) {
        super(context);
        initView();
    }

    public SignDialog(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }


    public TextView getSign_day() {
        return sign_day;
    }

    public void setSign_day(TextView sign_day) {
        this.sign_day = sign_day;
    }

    public TextView getSign_exp() {
        return sign_exp;
    }

    public void setSign_exp(TextView sign_exp) {
        this.sign_exp = sign_exp;
    }

    public TextView getSign_fortune() {
        return sign_fortune;
    }

    public void setSign_fortune(TextView sign_fortune) {
        this.sign_fortune = sign_fortune;
    }

    //退出监听事件
    public void setExitListener(View.OnClickListener cancelListener) {
        sign_tuichu.setOnClickListener(cancelListener);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sign, null);
        sign_tuichu=(ImageView) dialogView.findViewById(R.id.sign_tuichu);
        sign_day = (TextView) dialogView.findViewById(R.id.sign_day);
        sign_exp = (TextView) dialogView.findViewById(R.id.sign_exp);

        sign_fortune = (TextView) dialogView.findViewById(R.id.sign_fortune);
        sign_ray_1 = (FrameLayout) dialogView.findViewById(R.id.sign_ray_1);
        sign_ray_2 = (FrameLayout) dialogView.findViewById(R.id.sign_ray_2);
        sign_ray_3 = (FrameLayout) dialogView.findViewById(R.id.sign_ray_3);
        sign_ray_4 = (FrameLayout) dialogView.findViewById(R.id.sign_ray_4);
        sign_ray_5 = (FrameLayout) dialogView.findViewById(R.id.sign_ray_5);
        sign_ray_6 = (FrameLayout) dialogView.findViewById(R.id.sign_ray_6);
        sign_ray_7 = (FrameLayout) dialogView.findViewById(R.id.sign_ray_7);

        sign_cg_1 = (ImageView) dialogView.findViewById(R.id.sign_cg_1);
        sign_cg_2 = (ImageView) dialogView.findViewById(R.id.sign_cg_2);
        sign_cg_3 = (ImageView) dialogView.findViewById(R.id.sign_cg_3);
        sign_cg_4 = (ImageView) dialogView.findViewById(R.id.sign_cg_4);
        sign_cg_5 = (ImageView) dialogView.findViewById(R.id.sign_cg_5);
        sign_cg_6 = (ImageView) dialogView.findViewById(R.id.sign_cg_6);
        sign_cg_7 = (ImageView) dialogView.findViewById(R.id.sign_cg_7);

        setContentView(dialogView);
    }
}
