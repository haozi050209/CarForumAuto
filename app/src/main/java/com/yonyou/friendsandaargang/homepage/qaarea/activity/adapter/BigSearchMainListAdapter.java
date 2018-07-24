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
import com.yonyou.friendsandaargang.homepage.modle.BigshotAnwserListBean;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 */

public class BigSearchMainListAdapter extends BaseAdapter {


    private Context mContext;
    private List<BigshotAnwserListBean.ContentBean> list;
    private String keyWord;

    public BigSearchMainListAdapter(Context mContext, List<BigshotAnwserListBean.ContentBean> list, String keyWord) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_seachanwser, null);
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
        viewHodler.tvDesc.setText(list.get(position).getBigshotDesc());
        viewHodler.getTvContent.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), keyWord));
        /*if (list.get(position).getIsPay()==1){
            viewHodler.tvRed.setText("立即查看");
        }else {
            viewHodler.tvRed.setText(list.get(position).);
        }*/
        viewHodler.tvRed.setText("立即查看");
        viewHodler.TvNum.setText(list.get(position).getReadNumber());
        viewHodler.TvNum.setCompoundDrawablesWithIntrinsicBounds(R.drawable.kj3x, 0, 0, 0);
        return convertView;
    }


    public class ViewHodler extends RecyclerView.ViewHolder {


        @BindView(R.id.bigserch_anwers_img_item)
        CircleImageView imageView;
        @BindView(R.id.bigserch_anwers_name_item)
        TextView tvName;
        @BindView(R.id.bigserch_anwers_desc_item)
        TextView tvDesc;
        @BindView(R.id.bigserch_anwers_title_item)
        TextView getTvContent;
        @BindView(R.id.bigserch_anwers_zhifu_item)
        TextView tvRed;
        @BindView(R.id.bigserch_num_tv)
        TextView TvNum;

        public ViewHodler(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
