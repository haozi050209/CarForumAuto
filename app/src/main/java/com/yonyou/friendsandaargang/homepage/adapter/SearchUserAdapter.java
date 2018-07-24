package com.yonyou.friendsandaargang.homepage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.homepage.modle.SearchUser;
import com.yonyou.friendsandaargang.info.bean.People;
import com.yonyou.friendsandaargang.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class SearchUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;

    private List<SearchUser.ContentBean> beanList;

    private String content;


    public SearchUserAdapter(Context context, List<SearchUser.ContentBean> beanList, String content) {
        this.context = context;
        this.beanList = beanList;
        this.content = content;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_item_user, null, false);
        return new ViewHodler(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        Glide.with(context)
                .load(beanList.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.search_head_user);
        viewHodler.search_name_user.setText(TextUtils.getHighLightKeyWord(Color.RED, beanList.get(position).getNickname(), content));
        viewHodler.search_hg_user.setText(beanList.get(position).getLevel() + "");
        //关键字颜色
        viewHodler.search_follow_content.setText(TextUtils.getHighLightKeyWord(Color.RED, beanList.get(position).getSignature(), content));
        if (beanList.get(position).getFollowed().equals("1")) {
            viewHodler.search_follow_content.setText("已关注");
        } else {
            viewHodler.search_follow_content.setText("关注");
            viewHodler.search_follow_content.setBackgroundResource(R.drawable.shape_follow_red);
            viewHodler.search_follow_content.setTextColor(R.color.color_white);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (beanList != null) {
            return beanList.size();
        }
        return 0;
    }


    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.search_head_user)
        ImageView search_head_user;
        @BindView(R.id.search_name_user)
        TextView search_name_user;

        @BindView(R.id.search_hg_user)
        TextView search_hg_user;
        @BindView(R.id.search_follow_content)
        TextView search_follow_content;
        @BindView(R.id.search_follow_user)
        TextView search_follow_user;

        public ViewHodler(View view) {
            super(view);
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
