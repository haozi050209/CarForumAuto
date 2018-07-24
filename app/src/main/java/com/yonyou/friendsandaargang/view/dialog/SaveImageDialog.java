package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/7/12.
 * <p>
 * <p>
 * 保存图片
 */

public class SaveImageDialog extends BottomSheetDialog implements View.OnClickListener {


    private Context mContext;
    private TextView tvSave, tvCancel;


    public TextView getTvSave() {
        return tvSave;
    }

    public void setTvSave(TextView tvSave) {
        this.tvSave = tvSave;
    }

    public TextView getTvCancel() {
        return tvCancel;
    }

    public void setTvCancel(TextView tvCancel) {
        this.tvCancel = tvCancel;
    }

    public SaveImageDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initveiws();
    }


    private void initveiws() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_savepicture, null);
        tvSave = view.findViewById(R.id.save_phone);
        tvCancel = view.findViewById(R.id.save_cancel);
        setContentView(view);

    }


    //拍照
    public void setSavePicListener(View.OnClickListener Listener) {
        tvSave.setOnClickListener(Listener);
    }

    //选择相册
    public void setCancelPicListener(View.OnClickListener listener) {
        tvCancel.setOnClickListener(listener);
    }


    @Override
    public void onClick(View v) {

    }
}
