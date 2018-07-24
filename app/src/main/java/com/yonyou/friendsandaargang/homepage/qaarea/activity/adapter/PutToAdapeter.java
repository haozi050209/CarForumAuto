package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.modle.QAForumListBean;
import com.yonyou.friendsandaargang.homepage.modle.QuestionListBean;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 * <p>
 * 提问适配器
 */

public class PutToAdapeter extends BaseAdapter {


    private Context mContext;
    private List<QuestionListBean.ContentBean> list;
    private OnItemClickListener listener;
    private String keyWord;

    public PutToAdapeter(Context mContext, List<QuestionListBean.ContentBean> list, String keyWord) {
        this.mContext = mContext;
        this.list = list;
        this.keyWord = keyWord;
    }

    @Override
    public int getCount() {
        if (android.text.TextUtils.isEmpty(keyWord)) {
            if (list != null && list.size() > 3) {
                return 3;
            } else if (list != null) {
                return list.size();
            }
        } else {
            if (list != null) {
                return list.size();
            }
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_putto, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        if (android.text.TextUtils.isEmpty(keyWord)) {
            viewHodler.tvContent.setText(list.get(position).getTitle());
        } else {
            viewHodler.tvContent.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), keyWord));
        }
        Glide.with(mContext)
                .load(list.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.imageView);
        viewHodler.tvName.setText(list.get(position).getViewerNickname());
        //viewHodler.tvMoney.setText("¥" + list.get(position).getRewardCoin());
        viewHodler.tvTime.setText("还剩" + list.get(position).getHours() + "小时");
        viewHodler.tvNum.setText(list.get(position).getReplyNumber() + "人已抢答");

        // TODO: 18/6/4   去抢答按钮该怎么显示？？？
        if (!android.text.TextUtils.isEmpty(list.get(position).getAuthor())) {
            if (list.get(position).getAuthor().equals(SPTool.getContent(mContext, Constants.USER_ID))) {
                viewHodler.tvQd.setVisibility(View.GONE);
            } else {
                viewHodler.tvQd.setVisibility(View.VISIBLE);
                viewHodler.tvQd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(position);
                    }
                });
            }
        }

        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.tiwen_item_image)
        CircleImageView imageView;
        @BindView(R.id.tiwen_item_name)
        TextView tvName;
        @BindView(R.id.tiwen_item_money)
        TextView tvMoney;
        @BindView(R.id.tiwen_item_content)
        TextView tvContent;
        @BindView(R.id.tiwen_item_tiem)
        TextView tvTime;
        @BindView(R.id.tiwen_item_num)
        TextView tvNum;
        @BindView(R.id.tiwen_item_qd)
        TextView tvQd;

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
