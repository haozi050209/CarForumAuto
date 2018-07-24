package com.yonyou.friendsandaargang.homepage.modle;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class MyFollowPopleAdapter extends BaseAdapter {
    private Context context;
    private List<FollowPople.ContentBean> list;
    private String title;
    private OnItemClickListener listener;

    public MyFollowPopleAdapter(Context context, List<FollowPople.ContentBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_my_followpople_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        if (list != null && list.size() > 0) {

            GlideManager.loadImage(context, list.get(position).getAvatar(), R.drawable.user, viewHodler.pople_head);
            viewHodler.pople_name.setText(list.get(position).getNickname());
            viewHodler.pople_hg_text.setText(list.get(position).getLevel() + "");
            Long time = TimeUtil.timeStrToSecond(list.get(position).getTraceDate());
            viewHodler.pople_time.setText(TimeUtil.getSpaceTime(time));
            if (!TextUtils.isEmpty(list.get(position).getAttachment())) {
                GlideManager.loadImage(context, list.get(position).getAttachment(), R.drawable.ic_zan, viewHodler.pople_content_image);
            } else {
                viewHodler.pople_content_image.setVisibility(View.GONE);
            }
            viewHodler.pople_content.setText(list.get(position).getAction());
            viewHodler.pople_kj_text.setText(list.get(position).getReadNumber() + "");

            switch (list.get(position).getActionType()) {
                case 1001:
                    title = "帖子";
                    break;
                case 1002:
                    title = "话题";
                    break;
                case 1003:
                    title = " 问答";
                    break;
            }
        }
        //点击跳转至信息详情
        viewHodler.pople_ray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, list.get(position).getUserId());
                context.startActivity(intent);
            }
        });

        //头像点击跳转至 信息详情
        viewHodler.pople_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, list.get(position).getUserId());
                context.startActivity(intent);
            }
        });

        //点击跳转至帖子详情
        viewHodler.pople_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra(Constants.POSTID, list.get(position).getPostId());
                intent.putExtra(Constants.TITLE, title);
                context.startActivity(intent);
            }
        });


        if (list.get(position).getThumbuped() == 1) {
            viewHodler.pople_dz_image.setImageResource(R.drawable.comment_zan_high);
        } else {
            viewHodler.pople_dz_image.setImageResource(R.drawable.ico_dz);
        }

        //点赞
        viewHodler.pople_dz_lay.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.pople_head)
        CircleImageView pople_head;
        @BindView(R.id.pople_name)
        TextView pople_name;
        @BindView(R.id.pople_hg_text)
        TextView pople_hg_text;
        @BindView(R.id.pople_time)
        TextView pople_time;
        @BindView(R.id.pople_content_image)
        ImageView pople_content_image;
        @BindView(R.id.pople_content)
        TextView pople_content;
        @BindView(R.id.pople_kj_text)
        TextView pople_kj_text;
        @BindView(R.id.pople_dz_lay)
        LinearLayout pople_dz_lay;
        @BindView(R.id.pople_dz_image)
        ImageView pople_dz_image;
        @BindView(R.id.pople_dz_text)
        TextView pople_dz_text;
        @BindView(R.id.pople_ray)
        RelativeLayout pople_ray;
        @BindView(R.id.pople_lay)
        LinearLayout pople_lay;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }

    }


    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
