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

public class PublishAdapter extends BaseAdapter {


    private Context context;

    public PublishAdapter(Context context) {

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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_publish_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        return convertView;
    }


    public class ViewHodler {


        @BindView(R.id.publish_item_name)
        TextView publish_name;
        @BindView(R.id.publish_item_content)
        TextView publish_content;
        @BindView(R.id.publish_item_type)
        TextView publish_type;
        @BindView(R.id.publish_item_numer)
        TextView publish_numer;


        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
