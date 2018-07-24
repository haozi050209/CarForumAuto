package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.bean.MyForumList;
import com.yonyou.friendsandaargang.info.adapter.FollowPeopleAdapter;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class MyForumAdapter extends BaseAdapter {


    private Context context;
    private List<MyForumList.ContentBean> list;
    private OnItemClickListener listener;


    public MyForumAdapter(Context context, List<MyForumList.ContentBean> list) {
        this.context = context;
        this.list = list;
    }


    public void setList(List<MyForumList.ContentBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_forum, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.my_forum_item_title.setText(list.get(position).getForumName());
        GlideManager.loadImage(context, list.get(position).getIcon(), R.drawable.logo1, viewHodler.my_forum_item_img);
        viewHodler.my_forum_item_content.setText(list.get(position).getBriefDesc());
        viewHodler.my_forum_item_foollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.my_forum_item_img)
        ImageView my_forum_item_img;
        @BindView(R.id.my_forum_item_title)
        TextView my_forum_item_title;
        @BindView(R.id.my_forum_item_content)
        TextView my_forum_item_content;
        @BindView(R.id.my_forum_item_foollow)
        TextView my_forum_item_foollow;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
