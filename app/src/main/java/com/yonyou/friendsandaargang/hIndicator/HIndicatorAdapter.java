package com.yonyou.friendsandaargang.hIndicator;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;


import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.hIndicator.base.BaseIndicatorAdapter;
import com.yonyou.friendsandaargang.hIndicator.base.BaseViewHolder;
import com.yonyou.friendsandaargang.hIndicator.listener.OnItemClickListener;

import java.util.List;

/**
 * @author Huangshuang  2018/4/8 0008
 * @describtion 提供默认的数据类型，若是需要其他的数据类型，则需要实现BaseIndicatorAdapter自己实现
 */

public class HIndicatorAdapter extends BaseIndicatorAdapter {

    private List<String> list;
    private Context context;
    private OnItemClickListener listener;
    private SparseArray<TextView> viewArray;

    public HIndicatorAdapter(List<String> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        viewArray = new SparseArray<>();
    }

    public HIndicatorAdapter() {
    }

    @Override
    public int getLayoutID(int position) {
        return R.layout.hindicator_item_layout;
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
        viewArray.put(position, tv);
        //使用该方法来实现item分割线
        if (position == list.size() - 1) {
            holder.setVisibility(R.id.item_line, BaseViewHolder.GONE);
        } else {
            holder.setVisibility(R.id.item_line, BaseViewHolder.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void changeColor(int position) {
        for (int i = 0; i < viewArray.size(); i++) {
            viewArray.get(i).setTextColor(Color.GRAY);
        }
        viewArray.get(position).setTextColor(Color.RED);
    }
}
