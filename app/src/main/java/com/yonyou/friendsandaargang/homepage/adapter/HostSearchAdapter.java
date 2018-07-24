package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class HostSearchAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    public HostSearchAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_host, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if (position == 0) {
            viewHodler.hotsearch_name.setText(list.get(0));
            viewHodler.hotsearch_position.setText(position + 1+"");
            viewHodler.hotsearch_position.setBackgroundResource(R.color.rect);
        } else if (position == 1) {
            viewHodler.hotsearch_name.setText(list.get(1));
            viewHodler.hotsearch_position.setText(position + 1+"");
            viewHodler.hotsearch_position.setBackgroundResource(R.color.shadowPink50);
        } else if (position == 2) {
            viewHodler.hotsearch_name.setText(list.get(2));
            viewHodler.hotsearch_position.setText(position + 1+"");
            viewHodler.hotsearch_position.setBackgroundResource(R.color.lemonchiffon);
        } else {
            viewHodler.hotsearch_name.setText(list.get(position));
            viewHodler.hotsearch_position.setText(position + 1+"");
        }
        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.hotsearch_name)
        TextView hotsearch_name;
        @BindView(R.id.hotsearch_position)
        TextView hotsearch_position;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
