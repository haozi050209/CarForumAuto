package com.yonyou.friendsandaargang.view.dialog;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shibing on 18/5/29.
 */

public class DialogShowImage extends BottomSheetDialog implements View.OnClickListener {


    private ImageView imageView;
    private PhotoView photoView;
    private PhotoViewAttacher photoViewAttacher;

    private String imagePath;
    private Context mContext;

    public DialogShowImage(@NonNull Context mContext, String url) {
        super(mContext);
        this.mContext = mContext;
        this.imagePath = url;
        initviews();
    }


    private void initviews() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_showbigimage, null);
        imageView = view.findViewById(R.id.shoucan_img);
        photoView = view.findViewById(R.id.photoview);
        photoView.setOnClickListener(this);
        photoViewAttacher = new PhotoViewAttacher(photoView);
        GlideManager.loadImage(mContext, imagePath, R.drawable.ic_default_image, photoView);
        setContentView(view);
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
