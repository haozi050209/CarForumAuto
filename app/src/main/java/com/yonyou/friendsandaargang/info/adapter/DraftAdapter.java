package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.Area;
import com.yonyou.friendsandaargang.info.bean.Draft;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyRecylerview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class DraftAdapter extends MyRecylerview.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    private List<Draft.ContentBean> list;

    public DraftAdapter(Context context, List<Draft.ContentBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_draft_item, parent, false);
        return new ViewHodler(view, viewType);
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
        viewHodler.draft_time.setText(list.get(position).getPostDate() + "");
        viewHodler.draft_title.setText("标题：" + list.get(position).getTitle() + "");
        if (!TextUtils.isEmpty(list.get(position).getContent())) {
            String reg = "[^\u4e00-\u9fa5]";
            viewHodler.draft_content.setText(list.get(position).getContent().replaceAll(reg, "") + "[图片]");
        } else {
            viewHodler.draft_content.setText("暂未填写内容");
        }
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


    /**
     * 添加item监听
     *
     * @param listener
     */
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.draft_time)
        TextView draft_time;
        @BindView(R.id.draft_title)
        TextView draft_title;
        @BindView(R.id.draft_content)
        TextView draft_content;

        public ViewHodler(View view, final int viewType) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }


}
