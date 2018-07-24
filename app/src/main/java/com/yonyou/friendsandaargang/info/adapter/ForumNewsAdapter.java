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
import com.yonyou.friendsandaargang.info.bean.ForumMessage;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class ForumNewsAdapter extends BaseAdapter {


    private Context context;

    private List<ForumMessage.ContentBean> beanList;

    public ForumNewsAdapter(Context context, List<ForumMessage.ContentBean> beanList) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_fens_item_forum, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        Glide.with(context)
                .load(beanList.get(position).getRelatedAvatar())
                .error(R.drawable.user)
                .into(viewHodler.forum_item_head);
        viewHodler.forum_item_name.setText(beanList.get(position).getMessageTitle());
        viewHodler.forum_item_content.setText(beanList.get(position).getMessageContent());
        Long time = TimeUtil.timeStrToSecond(beanList.get(position).getCreateDate());
        viewHodler.forum_item_time.setText(TimeUtil.getSpaceTime(time));

        return convertView;
    }


    public class ViewHodler {


        @BindView(R.id.forum_item_head)
        ImageView forum_item_head;
        @BindView(R.id.forum_item_name)
        TextView forum_item_name;
        @BindView(R.id.forum_item_time)
        TextView forum_item_time;
        @BindView(R.id.forum_item_content)
        TextView forum_item_content;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
