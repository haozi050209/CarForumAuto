package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.homepage.modle.NavigationBean;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/2/8.
 */

public class NavigationbarAdapter extends RecyclerView.Adapter<NavigationbarAdapter.ViewHodler> {
    private List<NavigationBean> list;
    private Context context;
    private String from;
    private OnItemClickListener listener;

    public NavigationbarAdapter(List<NavigationBean> list, String from) {
        this.list = list;
        this.from = from;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigationbar, parent, false);
        context = view.getContext();
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(final ViewHodler holder, final int position) {
        holder.mItem = list.get(position);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }


        holder.navigation_text.setText(holder.mItem.getName());
        Glide.with(context).
                load(holder.mItem.getImage()).
//                diskCacheStrategy(DiskCacheStrategy.RESULT).
                //thumbnail(1f).
//                priority(Priority.HIGH).
               // override(75, 75).
                into(holder.navigation_image);

        if (TextUtils.isEmpty(from)) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManger.skipActivity(context, holder.mItem.getActivity());
                }
            });
            return;
        }
    }


    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }


    //头布局
    public void setHeaderView(View header) {

    }

    //尾部布局
    public void setFooterView(View footer) {

    }


    public class ViewHodler extends RecyclerView.ViewHolder {
        public View mView;
        public NavigationBean mItem;
        @BindView(R.id.navigation_image)
        CircleImageView navigation_image;
        @BindView(R.id.navigation_text)
        TextView navigation_text;

        public ViewHodler(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
