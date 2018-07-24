package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.bean.HuaTi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class MoreHuaTiAdapter extends BaseAdapter {



    private Context context;

    private List<HuaTi.ContentBean> list;

    public MoreHuaTiAdapter(Context context, List<HuaTi.ContentBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_iten_morehuati, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.morehuati_item_content.setText(list.get(position).getTitle());
        viewHodler.morehuati_item_number.setText(list.get(position).getReplyNumber() + "人参与");
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
