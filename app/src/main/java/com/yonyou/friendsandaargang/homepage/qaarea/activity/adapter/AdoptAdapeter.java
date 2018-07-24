package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.homepage.modle.AnswerListBean;
import com.yonyou.friendsandaargang.homepage.modle.QAForumListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 * <p>
 * 采纳适配器
 */

public class AdoptAdapeter extends BaseAdapter {


    private Context mContext;
    private List<AnswerListBean.ContentBean> list;

    public AdoptAdapeter(Context mContext, List<AnswerListBean.ContentBean> list) {
        this.mContext = mContext;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_adopt, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        Glide.with(mContext)
                .load(list.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.imageView);
        viewHodler.tvName.setText(list.get(position).getViewerNickname());
        viewHodler.tvContent.setText(list.get(position).getTitle());
        viewHodler.tvMoney.setText("¥" + list.get(position).getRewardCoin());
        viewHodler.tvAnswer.setText("已解答" + list.get(position).getReplyNumber() + "个答案");
        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.adopt_item_image)
        CircleImageView imageView;
        @BindView(R.id.adopt_item_name)
        TextView tvName;
        @BindView(R.id.adopt_item_money)
        TextView tvMoney;
        @BindView(R.id.adopt_item_content)
        TextView tvContent;
        @BindView(R.id.adopt_item_daan)
        TextView tvAnswer;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
