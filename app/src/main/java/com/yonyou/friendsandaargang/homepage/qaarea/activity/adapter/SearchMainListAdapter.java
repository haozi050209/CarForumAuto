package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 */

public class SearchMainListAdapter extends BaseAdapter {


    private Context mContext;
    private List<HotListBean.ContentBean> list;
    private String keyWord;

    public SearchMainListAdapter(Context mContext, List<HotListBean.ContentBean> list, String keyWord) {
        this.list = list;
        this.mContext = mContext;
        this.keyWord = keyWord;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_hot, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if (list != null) {
            Glide.with(mContext)
                    .load(list.get(position).getAvatar())
                    .into(viewHodler.imageView);
            viewHodler.tvName.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getViewerNickname(), keyWord));
            viewHodler.tvContent.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), keyWord));
            viewHodler.tvContent1.setVisibility(View.VISIBLE);
            viewHodler.tvContent1.setText("已解答" + list.get(position).getReplyNumber() + "个答案免费查看");
            viewHodler.ray.setVisibility(View.GONE);
        }
        return convertView;
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
        @BindView(R.id.hot_item_content_ray)
        RelativeLayout ray;

        public ViewHodler(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
