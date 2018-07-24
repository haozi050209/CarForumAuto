package com.yonyou.friendsandaargang.info.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.MessAge;
import com.yonyou.friendsandaargang.info.bean.SyatemMessage;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class SystemMessAgeAdapter extends BaseAdapter {


    private Context context;
    private List<SyatemMessage.ContentBean> beanList;

    public SystemMessAgeAdapter(Context context, List<SyatemMessage.ContentBean> beanList) {
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


    public void notifyDataSetChanged(List<SyatemMessage.ContentBean> beanList) {
        super.notifyDataSetChanged();
        this.beanList = beanList;
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

        viewHodler.message_img.setImageResource(R.mipmap.logo);
        viewHodler.message_title.setText(beanList.get(position).getMessageTitle());
        viewHodler.message_content.setText(beanList.get(position).getMessageContent());

        Long time = TimeUtil.timeStrToSecond(beanList.get(position).getCreateDate());
        viewHodler.message_time.setText(TimeUtil.getSpaceTime(time));
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

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
