package com.yonyou.friendsandaargang.info.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.MessAge;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class MessAgeAdapter extends BaseAdapter {


    private Context context;
    private List<MessAge.ContentBean> beanList;

    public MessAgeAdapter(Context context, List<MessAge.ContentBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        if (beanList != null) {
            return beanList.size();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_message, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        switch (beanList.get(position).getMessageType()) {
            case 10141001:
                viewHodler.message_title.setText("系统通知");
                viewHodler.message_img.setImageResource(R.drawable.ltxx);
                break;
            case 10141002:
                viewHodler.message_title.setText("钱包消息");
                viewHodler.message_img.setImageResource(R.drawable.qbxx);
                break;
            case 10141003:
                viewHodler.message_title.setText("我的粉丝");
                viewHodler.message_img.setImageResource(R.drawable.wdfs);
                break;
            case 10141004:
                viewHodler.message_title.setText("论坛消息");
                viewHodler.message_img.setImageResource(R.drawable.ltxx);
                break;
            case 10141005:
                viewHodler.message_title.setText("问答消息");
                viewHodler.message_img.setImageResource(R.drawable.wdxx);
                break;
            //todo 交易消息  跳转至帖子详情   ic还没更换
            case 10141006:
                viewHodler.message_title.setText("频道消息");
                viewHodler.message_img.setImageResource(R.drawable.wdxx);
                break;
        }

        viewHodler.message_content.setText(beanList.get(position).getMessagePreview());
        Long time = TimeUtil.timeStrToSecond(beanList.get(position).getCreateDate());
        viewHodler.message_time.setText(TimeUtil.getSpaceTime(time));
        if (beanList.get(position).getCount() == 0) {
            viewHodler.message_count.setVisibility(View.GONE);
        } else {
            viewHodler.message_count.setVisibility(View.VISIBLE);
            viewHodler.message_count.setText(beanList.get(position).getCount() + "");
        }


        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.message_img_item)
        CircleImageView message_img;
        @BindView(R.id.message_titel_item)
        TextView message_title;
        @BindView(R.id.message_time_item)
        TextView message_time;
        @BindView(R.id.message_content_item)
        TextView message_content;
        @BindView(R.id.message_count)
        TextView message_count;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
