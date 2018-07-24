package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.modle.AllBrand;
import com.yonyou.friendsandaargang.homepage.modle.AllForums;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class AllBrandAdapter extends BaseAdapter {


    private Context context;
    private List<AllBrand.ContentBean> list;


    public AllBrandAdapter(Context context, List<AllBrand.ContentBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_all_brand, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        GlideManager.loadImage(context, list.get(position).getIcon(), R.drawable.logo1, viewHodler.all_forums_image);
        viewHodler.all_forums_name.setText(list.get(position).getBrandName());
        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.all_forums_image)
        ImageView all_forums_image;
        @BindView(R.id.all_forums_name)
        TextView all_forums_name;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
