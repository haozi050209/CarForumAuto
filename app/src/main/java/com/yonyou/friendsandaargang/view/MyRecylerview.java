package com.yonyou.friendsandaargang.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by shibing on 18/3/27.
 */

public class MyRecylerview  extends RecyclerView {



    public MyRecylerview(Context context) {
        super(context);
    }

    public MyRecylerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecylerview(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
