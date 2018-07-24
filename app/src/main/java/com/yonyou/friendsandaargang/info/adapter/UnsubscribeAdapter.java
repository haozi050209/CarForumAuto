package com.yonyou.friendsandaargang.info.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.Area;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class UnsubscribeAdapter extends BaseAdapter {


    private Context context;
    private List<String> list;

    private ViewHodler viewHodler;

    private int selectItem = -1;

    public UnsubscribeAdapter(Context context) {
        this.context = context;
        initviews();
    }


    private void initviews() {
        list = Arrays.asList("安全/隐私顾虑"
                ,"这是多余的账户"
                ,"不再需要使用"
                ,"其他");
    }


    public void ChangedImage(int selectItem) {
        this.selectItem = selectItem;
    }


    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_unsubscribe_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.unsubscribe_item_name.setText(list.get(position));
        viewHodler.unsubscribe_item_xz.setImageResource(R.drawable.off_xz);

        if (selectItem == position) {
            viewHodler.unsubscribe_item_xz.setImageResource(R.drawable.xuanzhong);
        } else {
            viewHodler.unsubscribe_item_xz.setImageResource(R.drawable.off_xz);
        }


        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.unsubscribe_item_name)
        TextView unsubscribe_item_name;

        @BindView(R.id.unsubscribe_item_xz)
        ImageView unsubscribe_item_xz;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
