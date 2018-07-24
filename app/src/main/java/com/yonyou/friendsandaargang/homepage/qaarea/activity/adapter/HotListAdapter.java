package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 */

public class HotListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<HotListBean.ContentBean> list;
    private OnItemClickListener listener;

    public HotListAdapter(Context mContext, List<HotListBean.ContentBean> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.list_item_hot, null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

        GlideManager.loadImage(mContext
                , list.get(position).getAvatar()
                , R.drawable.user
                , ((ViewHodler) holder).imageView);

        ((ViewHodler) holder).tvName.setText(list.get(position).getViewerNickname());
        ((ViewHodler) holder).tvContent.setText(list.get(position).getTitle());
        ((ViewHodler) holder).tvforum.setText(list.get(position).getForumName());
        ((ViewHodler) holder).tvforum.setBackgroundResource(R.drawable.shape_follow_garly);
        ((ViewHodler) holder).tvNum.setText(list.get(position).getReadNumber());
        ((ViewHodler) holder).Tvda.setText("已解答" + list.get(position).getReplyNumber() + "个答案");
        ((ViewHodler) holder).tvContent1.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    /**
     * 添加item监听
     *
     * @param listener
     */
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public class ViewHodler extends RecyclerView.ViewHolder {


        @BindView(R.id.hot_item_image)
        CircleImageView imageView;
        @BindView(R.id.hot_item_name)
        TextView tvName;
        @BindView(R.id.hot_item_content)
        TextView tvContent;
        @BindView(R.id.hot_item_forum)
        TextView tvforum;
        @BindView(R.id.hot_item_num)
        TextView tvNum;
        @BindView(R.id.hot_item_daan)
        TextView Tvda;
        @BindView(R.id.hot_item_content_tv)
        TextView tvContent1;

        public ViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
