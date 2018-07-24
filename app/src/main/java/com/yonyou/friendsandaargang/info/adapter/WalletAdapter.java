package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.ExchangeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/5/30.
 */

public class WalletAdapter extends BaseAdapter {


    private Context mContext;
    private List<ExchangeBean.ContentBean> list;

    public WalletAdapter(Context mContext, List<ExchangeBean.ContentBean> list) {
        this.mContext = mContext;
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
        ViewHodler viewHodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_wallet_item, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        Glide.with(mContext)
                .load(list.get(position).getExchangeItemPhoto())
                .error(R.drawable.user)
                .into(viewHodler.imageView);
        viewHodler.tvName.setText(list.get(position).getExchangeItemName());
        viewHodler.tvPic.setText(list.get(position).getFortuneNeeded() + "财富值");

        return convertView;
    }


    public class ViewHodler {

        @BindView(R.id.por_iamge)
        ImageView imageView;
        @BindView(R.id.por_name)
        TextView tvName;
        @BindView(R.id.por_pic)
        TextView tvPic;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
