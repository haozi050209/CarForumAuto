package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.bean.ForumState;
import com.yonyou.friendsandaargang.forum.bean.MyForumList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class MyForumStateAdapter extends BaseAdapter {


    private Context context;
    private List<ForumState.ContentBean> list;
    private int falg;

    public MyForumStateAdapter(Context context, List<ForumState.ContentBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_forum, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if (list.get(position).getFollowed() == 0) {
            viewHodler.my_forum_item_title.setText(list.get(position).getForumName());
            Glide.with(context)
                    .load(list.get(position).getIcon())
                    .error(R.drawable.user)
                    .into(viewHodler.my_forum_item_img);
            viewHodler.my_forum_item_content.setText(list.get(position).getBriefDesc());
            viewHodler.my_forum_item_foollow.setText("关注");
            viewHodler.my_forum_item_foollow.setBackgroundResource(R.drawable.shape_follow_red);
            viewHodler.my_forum_item_foollow.setTextColor(context.getResources().getColor(R.color.color_white));
        }
        viewHodler.my_forum_item_foollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setFollowOnClick(viewHodler.my_forum_item_foollow, position);
        return convertView;
    }


    public class ViewHodler {
        //头像
        @BindView(R.id.my_forum_item_img)
        ImageView my_forum_item_img;
        //title
        @BindView(R.id.my_forum_item_title)
        TextView my_forum_item_title;
        //内容
        @BindView(R.id.my_forum_item_content)
        TextView my_forum_item_content;
        //是否关注
        @BindView(R.id.my_forum_item_foollow)
        TextView my_forum_item_foollow;

        public ViewHodler(View view) {

            ButterKnife.bind(this, view);
        }

    }


    //声明自定义接口
    private OnItemClickListener onItemClickListener;


    //自定义监听事件接口
    public interface OnItemClickListener {
        public void onItemClick(int postion);
    }

    //暴露接口供activity 调用
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setFollowOnClick(final View view, final int postion) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(postion);
                }
            }
        });

    }


}
