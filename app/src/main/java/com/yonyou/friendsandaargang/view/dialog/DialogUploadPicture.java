package com.yonyou.friendsandaargang.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/4/8.
 */

public class DialogUploadPicture extends DialogUtils {


    private TextView photograph;
    private TextView album;
    private TextView cancel;

    public TextView getPhotograph() {
        return photograph;
    }

    public void setPhotograph(TextView photograph) {
        this.photograph = photograph;
    }

    public TextView getAlbum() {
        return album;
    }

    public void setAlbum(TextView album) {
        this.album = album;
    }

    public TextView getCancel() {
        return cancel;
    }

    public void setCancel(TextView cancel) {
        this.cancel = cancel;
    }

    public DialogUploadPicture(Context context, int themeResId) {
        super(context, themeResId);
        initviews();
    }

    public DialogUploadPicture(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initviews();
    }

    public DialogUploadPicture(Context context) {
        super(context);
        initviews();
    }

    public DialogUploadPicture(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initviews();
    }


    //拍照
    public void setPhotographListener(View.OnClickListener Listener) {
        photograph.setOnClickListener(Listener);
    }

    //选择相册
    public void setAlbumListener(View.OnClickListener setAlbumListener) {
        album.setOnClickListener(setAlbumListener);
    }

    //取消
    public void setCancelListener(View.OnClickListener cancelListener) {
        cancel.setOnClickListener(cancelListener);
    }

    private void initviews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_uploadpicture, null);
        photograph = view.findViewById(R.id.photograph);
        album = view.findViewById(R.id.album);
        cancel = view.findViewById(R.id.cancel);
        setContentView(view);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }
}
