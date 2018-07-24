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
import com.yonyou.friendsandaargang.info.bean.Fans;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class FensAdapter extends BaseAdapter {


    private Context context;
    private List<Fans.ContentBean> list;
    private OnItemClickListener listener;

    public FensAdapter(Context context, List<Fans.ContentBean> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_fens_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.fens_head);
        viewHodler.fens_name.setText(list.get(position).getNickname());
        viewHodler.fans_hg_text.setText(list.get(position).getLevel());
        if (list.get(position).getViewerFollowed() == 1) {
            viewHodler.fens_follow.setText("已关注");
        } else if (list.get(position).getFriend().equals("YES")) {
            viewHodler.fens_follow.setText("互相关注");
            viewHodler.fens_follow.setBackgroundResource(R.drawable.shape_follow_garly);
            viewHodler.fens_follow.setTextColor(context.getResources().getColor(R.color.color_gray3));
        } else if (list.get(position).getViewerFollowed() == 0) {
            viewHodler.fens_follow.setText("关注");
            viewHodler.fens_follow.setBackgroundResource(R.drawable.shape_follow_red);
            viewHodler.fens_follow.setTextColor(context.getResources().getColor(R.color.color_white));
        }
        if (list.get(position).getSignature() == null) {
            viewHodler.fens_content.setText("暂无签名");
        } else {
            viewHodler.fens_content.setText(list.get(position).getSignature());
        }
        viewHodler.fens_follow.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.fens_item_head)
        CircleImageView fens_head;
        @BindView(R.id.fens_item_name)
        TextView fens_name;
        @BindView(R.id.fens_item_follow)
        TextView fens_follow;
        @BindView(R.id.fens_item_content)
        TextView fens_content;
        @BindView(R.id.fans_hg_text)
        TextView fans_hg_text;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 添加item监听
     *
     * @param listener
     */
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
