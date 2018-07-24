package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class ReplyAdapter extends BaseAdapter {


    private Context context;

    public ReplyAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_reply_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        return convertView;
    }


    public class ViewHodler {


        @BindView(R.id.reply_item_name)
        TextView reply_name;
        @BindView(R.id.reply_item_content)
        TextView reply_content;
        @BindView(R.id.reply_item_type)
        TextView reply_type;
        @BindView(R.id.reply_item_numer)
        TextView reply_numer;
        @BindView(R.id.reply_item_img)
        ImageView reply_img;


        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
