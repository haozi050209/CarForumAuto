package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 * <p>
 * 消息推送适配器
 */

public class MessagePushAdapter extends BaseAdapter {
    private Context context;

    public MessagePushAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_message_push, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.list_item_titie)
        TextView title;
        @BindView(R.id.list_item_content)
        TextView content;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
