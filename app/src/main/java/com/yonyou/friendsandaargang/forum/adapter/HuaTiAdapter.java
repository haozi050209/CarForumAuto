package com.yonyou.friendsandaargang.forum.adapter;

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

public class HuaTiAdapter extends BaseAdapter {


    private Context context;

    public HuaTiAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return 3;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_iten_morehuati, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        return convertView;
    }


    public class ViewHodler {

        @BindView(R.id.morehuati_item_content)
        TextView morehuati_item_content;
        @BindView(R.id.morehuati_item_number)
        TextView morehuati_item_number;


        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
