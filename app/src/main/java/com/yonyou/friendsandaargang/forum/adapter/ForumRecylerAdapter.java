package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.bean.EssenceList;
import com.yonyou.friendsandaargang.forum.bean.ForumState;
import com.yonyou.friendsandaargang.guide.adapter.SectionedBaseAdapter;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/8.
 */

public class ForumRecylerAdapter extends SectionedBaseAdapter {


    private Context context;
    private List<ForumState.ContentBean> stateList;
    private List<String> list;
    private List<ForumState.ContentBean> NoFollowList;
    private OnItemClickListener listener, listener1;


    public ForumRecylerAdapter(Context context, List<ForumState.ContentBean> stateList
            , List<ForumState.ContentBean> NoFollowList
            , List<String> list) {
        this.context = context;
        this.stateList = stateList;    //已关注
        this.NoFollowList = NoFollowList;
        this.list = list;

    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }


    public void notifyDataSetChanged(List<ForumState.ContentBean> stateList) {
        super.notifyDataSetChanged();
        this.stateList = stateList;
    }

    public void setList(List<ForumState.ContentBean> NoFollowList) {
        this.NoFollowList = NoFollowList;
        notifyDataSetChanged();
    }


    @Override
    public int getSectionCount() {
        return list.size();
    }


    //这个是只栏目的数据
    @Override
    public int getCountForSection(int section) {
        if (section == 0) {
            if (list.get(0).equals("我关注的论坛")) {
                return stateList.size();
            } else {
                return NoFollowList.size();
            }
        } else if (section == 1) {
            return NoFollowList.size();

        }
        return 0;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            //convertView inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_forum, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);

        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        switch (section) {
            case 0:
                if (list.get(0).equals("我关注的论坛")) {
                    viewHodler.my_forum_item_title.setText(stateList.get(position).getForumName());
                    GlideManager.loadImage(context, stateList.get(position).getIcon(), R.drawable.logo1, viewHodler.my_forum_item_img);
                    viewHodler.my_forum_item_content.setText(stateList.get(position).getBriefDesc());
                    viewHodler.my_forum_item_foollow.setText("已关注");
                    viewHodler.my_forum_item_foollow.setTextColor(context.getResources().getColor(R.color.color_gray3));
                    viewHodler.my_forum_item_foollow.setBackgroundResource(R.drawable.shape_follow_garly);
                    setUnFollowOnClick(viewHodler.my_forum_item_foollow, position);
                } else {
                    GlideManager.loadImage(context, NoFollowList.get(position).getIcon(), R.drawable.logo1, viewHodler.my_forum_item_img);
                    viewHodler.my_forum_item_title.setText(NoFollowList.get(position).getForumName());
                    viewHodler.my_forum_item_content.setText(NoFollowList.get(position).getBriefDesc());
                    viewHodler.my_forum_item_foollow.setText("关注");
                    viewHodler.my_forum_item_foollow.setTextColor(context.getResources().getColor(R.color.color_white));
                    viewHodler.my_forum_item_foollow.setBackgroundResource(R.drawable.shape_follow_red);
                    setFollowOnClick(viewHodler.my_forum_item_foollow, position);
                }
                break;

            case 1:
                viewHodler.my_forum_item_title.setText(NoFollowList.get(position).getForumName());
                GlideManager.loadImage(context, NoFollowList.get(position).getIcon(), R.drawable.logo1, viewHodler.my_forum_item_img);
                viewHodler.my_forum_item_content.setText(NoFollowList.get(position).getBriefDesc());
                viewHodler.my_forum_item_foollow.setText("关注");
                viewHodler.my_forum_item_foollow.setTextColor(context.getResources().getColor(R.color.color_white));
                viewHodler.my_forum_item_foollow.setBackgroundResource(R.drawable.shape_follow_red);
                setFollowOnClick(viewHodler.my_forum_item_foollow, position);
                break;
        }
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.listview_head, null);
        } else {
            layout = (LinearLayout) convertView;
        }

        ((TextView) layout.findViewById(R.id.xingqu_text)).setText(list.get(section));


        return layout;
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


    //暴露接口供activity 调用
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    //暴露接口供activity 调用
    public void addFollowOnItemClickListener(OnItemClickListener listener1) {
        this.listener1 = listener1;
    }

    //取消关注
    public void setUnFollowOnClick(final View view, final int postion) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(postion);
                }
            }
        });

    }


    //关注
    public void setFollowOnClick(View view, final int postion) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener1 != null) {
                    listener1.onItemClick(postion);
                }
            }
        });
    }

}
