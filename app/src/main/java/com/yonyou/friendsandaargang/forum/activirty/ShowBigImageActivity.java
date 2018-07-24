package com.yonyou.friendsandaargang.forum.activirty;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.utils.ImageUtils;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SDCardUtil;
import com.yonyou.friendsandaargang.utils.StatusUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.dialog.DialogShowImage;
import com.yonyou.friendsandaargang.view.dialog.SaveImageDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shibing on 18/5/23.
 */

public class ShowBigImageActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener, ImageUtils.SavePicCallback {
    @BindView(R.id.show_iamge)
    ImageView show_iamge;

    @BindView(R.id.photoview)
    PhotoView photoView;

    private PhotoViewAttacher photoViewAttacher;
    private Bitmap bitmap;
    private SaveImageDialog dialog;
    private String url;
    private ImageUtils imageUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.color_transparent));
        setContentView(R.layout.activity_showbigimage);
        ButterKnife.bind(this);
        initviews();
        initDialog();
    }

    private void initviews() {
        photoViewAttacher = new PhotoViewAttacher(photoView);
        imageUtils = new ImageUtils(mContext, this);
        url = getIntent().getStringExtra("url");
        GlideManager.loadImage(mContext, url, R.drawable.ic_default_image, photoView);
        photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                mContext.finish();
            }
        });

        photoViewAttacher.setOnLongClickListener(this);
    }


    private void initDialog() {
        dialog = new SaveImageDialog(mContext);

        //解决透明度问题
        dialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);


        //设置背景透明的dialog
        dialog.setSavePicListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //在子线程中把url转换成bitmap

                        bitmap = imageUtils.returnBitMap(url);
                        imageUtils.saveImageToGallery(mContext, bitmap);

                    }
                }).start();

            }
        });

        dialog.setCancelPicListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public boolean onLongClick(View v) {
        dialog.show();
        return false;


    }


    /**
     * 添加成功
     *
     * @param success
     */
    @Override
    public void showSuccess(String success) {
        Looper.prepare();
        ToastUtils.normal(mContext, success).show();
        dialog.dismiss();
        Looper.loop();
    }

    /**
     * 添加失败
     *
     * @param err
     */
    @Override
    public void showFailure(String err) {
        Looper.prepare();
        ToastUtils.normal(mContext, err).show();
        Looper.loop();
    }
}
