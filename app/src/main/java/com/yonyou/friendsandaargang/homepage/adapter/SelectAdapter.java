package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.hIndicator.HIndicatorAdapter;
import com.yonyou.friendsandaargang.hIndicator.base.BaseViewHolder;
import com.yonyou.friendsandaargang.hIndicator.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by shibing on 18/5/21.
 */

public class SelectAdapter extends HIndicatorAdapter {

    private List<String> list;
    private Context context;
    private OnItemClickListener listener;

    private int selectItem = -1;


    public SelectAdapter(List<String> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getLayoutID(int position) {
        return R.layout.hindicator_item_layout;
    }

    public void ChangedImage(int selectItem) {
        this.selectItem = selectItem;
    }


    @Override
    public void onItemClick(View v, int position) {
        if (listener != null) {
            listener.OnItemClick(position);
        }
    }


    @Override
    public void onBindView(BaseViewHolder holder, int position) {
        TextView tv = holder.getView(R.id.item_tv);
        tv.setText(list.get(position));
        tv.setGravity(Gravity.LEFT);

        //使用该方法来实现item分割线
        if (position == list.size() - 1) {
            holder.setVisibility(R.id.item_line, BaseViewHolder.GONE);
        } else {
            holder.setVisibility(R.id.item_line, BaseViewHolder.VISIBLE);
        }
        if (position == 0) {
            tv.setTextColor(context.getResources().getColor(R.color.color_red));
        }

        if (selectItem == position) {
            tv.setTextColor(context.getResources().getColor(R.color.color_red));
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.cardview_dark_background));
        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


}
