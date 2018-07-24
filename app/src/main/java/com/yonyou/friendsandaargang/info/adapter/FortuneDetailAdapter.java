package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.FortuneDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/11.
 */

public class FortuneDetailAdapter extends BaseAdapter {

    private Context context;
    private List<FortuneDetailBean.ContentBean> list;

    public FortuneDetailAdapter(Context context, List<FortuneDetailBean.ContentBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_fortunedetail, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        float before = Float.parseFloat(list.get(position).getFortuneBeforeChange());
        float after = Float.parseFloat(list.get(position).getFortuneAfterChange());
        if (before < after) {
            holder.tvFortunechange.setText("+" + list.get(position).getFortuneChange());
        } else {
            holder.tvFortunechange.setText(list.get(position).getFortuneChange());
        }
        holder.tvActioncontent.setText(list.get(position).getActionContent());
        holder.tvCreatedate.setText(list.get(position).getCreateDate());
        holder.tvFortuneafterchange.setText(list.get(position).getFortuneAfterChange());

        return convertView;
    }


    public class ViewHolder {

        @BindView(R.id.tv_actioncontent)
        TextView tvActioncontent;
        @BindView(R.id.tv_fortunechange)
        TextView tvFortunechange;
        @BindView(R.id.tv_createdate)
        TextView tvCreatedate;
        @BindView(R.id.tv_fortuneafterchange)
        TextView tvFortuneafterchange;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
