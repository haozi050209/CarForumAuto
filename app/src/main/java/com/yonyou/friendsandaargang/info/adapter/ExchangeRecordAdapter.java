package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.ExchangeRecordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/6/11.
 */

public class ExchangeRecordAdapter extends BaseAdapter {

    private Context context;
    private List<ExchangeRecordBean.ContentBean> list;

    public ExchangeRecordAdapter(Context context, List<ExchangeRecordBean.ContentBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_exchangerecord, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(list.get(position).getItemPhoto()).error(R.drawable.user).into(holder.prodetalisImg);
        holder.prodetalisJb.setText(list.get(position).getItemName());
        holder.prodetalisCfz.setText(list.get(position).getFortuneConsumed());
        holder.tvCreatedate.setText("兑换时间：" + list.get(position).getCreateDate());
        holder.tvState.setText(list.get(position).getExchangeStatusDesc());

        return convertView;
    }


    public class ViewHolder {

        @BindView(R.id.prodetalis_img)
        CircleImageView prodetalisImg;
        @BindView(R.id.prodetalis_jb)
        TextView prodetalisJb;
        @BindView(R.id.prodetalis_cfz)
        TextView prodetalisCfz;
        @BindView(R.id.tv_createdate)
        TextView tvCreatedate;
        @BindView(R.id.tv_state)
        TextView tvState;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
