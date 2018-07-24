package com.yonyou.friendsandaargang.info.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.adapter.MyForumAdapter;
import com.yonyou.friendsandaargang.info.bean.People;
import com.yonyou.friendsandaargang.info.bean.SyatemMessage;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class FollowPeopleAdapter extends BaseAdapter {


    private Context context;
    private List<People.ContentBean> beanList;
    private String forum;
    private OnItemClickListener listener;

    public FollowPeopleAdapter(Context context, List<People.ContentBean> beanList, String forum) {
        this.context = context;
        this.forum = forum;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        if (beanList != null) {
            return beanList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_people, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        Glide.with(context)
                .load(beanList.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.my_people_item_img);
        viewHodler.my_people_item_title.setText(beanList.get(position).getNickname());
        if (beanList.get(position).getSignature() == null) {
            viewHodler.my_people_item_content.setText("暂无签名");
        }
        viewHodler.my_people_item_content.setText(beanList.get(position).getSignature());


        if (forum.equals("userfollow")) {
            if (beanList.get(position).getFriend().equals("YES")) {
                viewHodler.my_people_item_foollow.setText("互相关注");
            } else if (beanList.get(position).getFriend().equals("NO")) {
                viewHodler.my_people_item_foollow.setText("已关注");
            }
        } else if (forum.equals("viewerfollow")) {
            if (beanList.get(position).getViewerFollowed() == 1) {
                viewHodler.my_people_item_foollow.setText("已关注");
                viewHodler.my_people_item_foollow.setTextColor(context.getResources().getColor(R.color.color_gray3));
            } else {
                viewHodler.my_people_item_foollow.setTextColor(context.getResources().getColor(R.color.color_white));
                viewHodler.my_people_item_foollow.setBackgroundResource(R.drawable.shape_follow_red);
                viewHodler.my_people_item_foollow.setText("关注");
            }
        }


        viewHodler.people_hg_text.setText(beanList.get(position).getLevel());

        viewHodler.my_people_item_foollow.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.my_people_item_img)
        ImageView my_people_item_img;
        @BindView(R.id.my_people_item_title)
        TextView my_people_item_title;
        @BindView(R.id.my_people_item_foollow)
        TextView my_people_item_foollow;
        @BindView(R.id.my_people_item_content)
        TextView my_people_item_content;
        @BindView(R.id.people_hg_text)
        TextView people_hg_text;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
