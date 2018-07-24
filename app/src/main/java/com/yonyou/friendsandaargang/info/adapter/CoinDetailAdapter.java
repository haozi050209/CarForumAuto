package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.CoinDetailBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/11.
 */

public class CoinDetailAdapter extends BaseAdapter {

    private Context context;
    private List<CoinDetailBean.ContentBean> list;

    public CoinDetailAdapter(Context context, List<CoinDetailBean.ContentBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_coindetail, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        float before = Float.parseFloat(list.get(position).getCoinBeforeChange());
        float after = Float.parseFloat(list.get(position).getCoinAfterChange());
        if (before < after) {
            holder.tvCoinchange.setText("+" + list.get(position).getCoinChange());
        } else {
            holder.tvCoinchange.setText(list.get(position).getCoinChange());
        }
        holder.tvActioncontent.setText(list.get(position).getActionContent());
        holder.tvCreatedate.setText(list.get(position).getCreateDate());
        holder.tvCoinafterchange.setText(list.get(position).getCoinAfterChange());

        return convertView;
    }

    public class ViewHolder {

        @BindView(R.id.tv_actioncontent)
        TextView tvActioncontent;
        @BindView(R.id.tv_coinchange)
        TextView tvCoinchange;
        @BindView(R.id.tv_createdate)
        TextView tvCreatedate;
        @BindView(R.id.tv_coinafterchange)
        TextView tvCoinafterchange;

         public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
}
