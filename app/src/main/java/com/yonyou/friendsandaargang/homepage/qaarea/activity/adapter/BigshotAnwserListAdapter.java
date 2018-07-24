package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.homepage.modle.BigshotAnwserListBean;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 */

public class BigshotAnwserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BigshotAnwserListBean.ContentBean> list;
    private OnItemClickListener listener;
    private String from;

    public BigshotAnwserListAdapter(Context mContext, List<BigshotAnwserListBean.ContentBean> list, String from) {
        this.list = list;
        this.mContext = mContext;
        this.from = from;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.list_item_bigshotanwser, null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }

        Glide.with(mContext)
                .load(list.get(position)
                        .getAvatar())
                .error(R.drawable.user)
                .into(((ViewHodler) holder).imageView);
        ((ViewHodler) holder).tvName.setText(list.get(position).getViewerNickname() );
        ((ViewHodler) holder).tvContent.setText(list.get(position).getTitle());
        ((ViewHodler) holder).tvZhifu.setText("立即查看");

        if (from.equals("bigshot")) {
            viewHodler.tvLook.setVisibility(View.VISIBLE);
            viewHodler.tvLook.setText(list.get(position).getReadNumber());
        }




       /* if (list.get(position).getIsPay() == 1) {
            ((ViewHodler) holder).tvZhifu.setText("立即查看");
        } else {
            ((ViewHodler) holder).tvZhifu.setText("支付1元学习下");
        }*/

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
        @BindView(R.id.big_anwers_img_item)
        CircleImageView imageView;
        @BindView(R.id.big_anwers_name_item)
        TextView tvName;
        @BindView(R.id.big_anwers_title_item)
        TextView tvContent;
        @BindView(R.id.big_anwers_zhifu_item)
        TextView tvZhifu;
        @BindView(R.id.big_anwers_look_item)
        TextView tvLook;

        public ViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
