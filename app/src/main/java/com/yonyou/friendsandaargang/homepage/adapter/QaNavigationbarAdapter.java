package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.modle.NavigationBean;
import com.yonyou.friendsandaargang.homepage.modle.QaMainPageBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.BigHeadAdapter;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/2/8.
 */

public class QaNavigationbarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<QaMainPageBean.ContentBean> list;
    private Context context;
    private OnItemClickListener listener;

    public QaNavigationbarAdapter(Context context, List<QaMainPageBean.ContentBean> list) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_bignavigationbar, null);
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
        if (position == 0) {
            ((ViewHodler) holder).navigation_image.setImageResource(R.drawable.bigshot);
        } else if (position == list.size() - 1) {
            viewHodler.navigation_image.setImageResource(R.drawable.all);
        } else {
            Glide.with(context)
                    .load(list.get(position).getIcon())
                    .error(R.drawable.logo1)
                    .into(viewHodler.navigation_image);

        }
        viewHodler.navigation_text.setText(list.get(position).getForumName());
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

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bignavigationbar, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if (position == 0) {
            viewHodler.navigation_image.setImageResource(R.drawable.bigshot);
        } else if (position == list.size() - 1) {
            viewHodler.navigation_image.setImageResource(R.drawable.all);
        } else {
            Glide.with(context)
                    .load(list.get(position).getIcon())
                    .error(R.drawable.logo1)
                    .into(viewHodler.navigation_image);

        }
        viewHodler.navigation_text.setText(list.get(position).getForumName());
        return convertView;
    }*/


    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.navigation_bigimage)
        CircleImageView navigation_image;
        @BindView(R.id.navigationbig_text)
        TextView navigation_text;

        public ViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

