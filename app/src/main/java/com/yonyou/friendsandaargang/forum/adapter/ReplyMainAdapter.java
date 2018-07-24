package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.bean.ReplyList;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class ReplyMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ReplyList.ContentBean> list;
    private List<ReplyList.ContentBean.ReplyListBean> listBeans;
    private String postId;
    private OnItemClickListener listener, listener1;

    public ReplyMainAdapter(Context context, List<ReplyList.ContentBean> list, String postId) {
        this.context = context;
        this.list = list;
        this.postId = postId;
        listBeans = new ArrayList<>();

    }


    public void notifyDataSetChanged(List<ReplyList.ContentBean> list) {
        this.list = list;
        super.notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_detalisrelpy_item, null, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
        ViewHodler viewHodler = (ViewHodler) holder;
        Glide.with(context)
                .load(list.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.relpy_avae_item);
        viewHodler.relpy_name_item.setText(list.get(position).getNickname());

        Long time = TimeUtil.timeStrToSecond(list.get(position).getReplyDate());
        viewHodler.relpy_item_tiem.setText(TimeUtil.getSpaceTime(time));


        viewHodler.relpy_dianza_item_text.setText(list.get(position).getThumbupNumber());
        viewHodler.relpy_content_item_text.setText(list.get(position).getContent());


        //二级评论列表
        if (list.get(position).getReplyList() != null && list.get(position).getReplyList().size() > 0) {
            listBeans = list.get(position).getReplyList();
            viewHodler.huifu_lay.setVisibility(View.VISIBLE);
            viewHodler.qaunbu_huifu.setVisibility(View.GONE);


            ReplyListTwoAdapter replyListTwoAdapter = new ReplyListTwoAdapter(context, listBeans);
            viewHodler.relpy_content_item_list.setAdapter(replyListTwoAdapter);


            viewHodler.relpy_content_item_list.setClickable(false);
            viewHodler.relpy_content_item_list.setPressed(false);
            viewHodler.relpy_content_item_list.setEnabled(false);

        }
        if (list.get(position).getReplyNumber() > 1) {
            viewHodler.qaunbu_huifu.setVisibility(View.VISIBLE);
            viewHodler.qaunbu_huifu.setText("查看全部" + list.get(position).getReplyNumber() + "条回复");
        }

        if (list.get(position).getThumbuped() == 1) {
            viewHodler.dianza_item_image.setImageResource(R.drawable.dianzan_on);
        } else {
            viewHodler.dianza_item_image.setImageResource(R.drawable.ico_dz);
        }

        viewHodler.relpy_avae_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, list.get(position).getReplierId());
                context.startActivity(intent);

            }
        });

        viewHodler.relpy_dianza_item_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener1 != null) {
                    listener1.onItemClick(position);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.relpy_avae_item)
        ImageView relpy_avae_item;
        @BindView(R.id.relpy_name_item)
        TextView relpy_name_item;
        @BindView(R.id.relpy_item_tiem)
        TextView relpy_item_tiem;
        @BindView(R.id.relpy_dianza_item_text)
        TextView relpy_dianza_item_text;
        @BindView(R.id.relpy_dianza_item_image)
        ImageView dianza_item_image;
        @BindView(R.id.relpy_dianza_item_lay)
        LinearLayout relpy_dianza_item_lay;

        @BindView(R.id.relpy_content_item_text)
        TextView relpy_content_item_text;
        @BindView(R.id.relpy_content_item_list)
        MyListView relpy_content_item_list;

        @BindView(R.id.qaunbu_huifu)
        TextView qaunbu_huifu;
        @BindView(R.id.huifu_lay)
        LinearLayout huifu_lay;

        public ViewHodler(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    /**
     * 定义 子控件监听事件
     */

    public void setChildClick(OnItemClickListener listener) {
        this.listener1 = listener;
    }

}
