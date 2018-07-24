package com.yonyou.friendsandaargang.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yonyou.friendsandaargang.R;

/**
 * Created by shibing on 18/5/22.
 */

public class GlideManager {

    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, 0, imageView);
    }

    public static void loadImage(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).into(imageView);
    }

    public static void loadImage(Fragment fragment, int resId, ImageView imageView) {
        Glide.with(fragment)
                .load(resId)
                .error(R.drawable.ic_zan)
                .into(imageView);
    }

    public static void loadImage(Context context, String url, int image, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(image)
                .into(imageView);
    }


    public static void loadIntoUseFitWidth(Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }


    


}
