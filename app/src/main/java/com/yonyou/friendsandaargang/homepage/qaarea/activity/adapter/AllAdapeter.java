package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.homepage.modle.QAForumListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 */

public class AllAdapeter extends BaseAdapter {


    private Context mContext;
    private List<QAForumListBean.ContentBean> list;

    public AllAdapeter(Context mContext, List<QAForumListBean.ContentBean> list) {
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
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_all_zq, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        Glide.with(mContext)
                .load(list.get(position).getIcon())
                .error(R.drawable.user)
                .into(viewHodler.imageView);
        viewHodler.tvTitle.setText(list.get(position).getForumName());
        viewHodler.tvContent.setText(list.get(position).getBriefDesc());

        return convertView;
    }


    public class ViewHodler {

        @BindView(R.id.all_item_image)
        CircleImageView imageView;
        @BindView(R.id.all_item_title)
        TextView tvTitle;
        @BindView(R.id.all_item_content)
        TextView tvContent;

        public ViewHodler(View view) {

            ButterKnife.bind(this, view);
        }
    }
}
