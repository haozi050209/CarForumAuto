package com.yonyou.friendsandaargang.hIndicator.drawable;

import android.graphics.drawable.Drawable;

import com.yonyou.friendsandaargang.hIndicator.HIndicatorBuilder;


public abstract class BaseDrawable extends Drawable {

    public final int arrowDirection;

    public BaseDrawable(@HIndicatorBuilder.ARROWDIRECTION int arrowDirection) {
        this.arrowDirection = arrowDirection;
    }
}
