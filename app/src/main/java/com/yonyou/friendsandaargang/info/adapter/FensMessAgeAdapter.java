package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.MyFans;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class FensMessAgeAdapter extends BaseAdapter {


    private Context context;
    private List<MyFans.ContentBean> list;

    public FensMessAgeAdapter(Context context, List<MyFans.ContentBean> list) {

        this.list = list;
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_fens_message, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        Glide.with(context)
                .load(list.get(position).getRelatedAvatar())
                .error(R.drawable.user)
                .into(viewHodler.fens_message_img);

        viewHodler.fens_hg_text.setText(list.get(position).getLevelId() + "");
        viewHodler.fens_message_titel.setText(list.get(position).getMessageTitle());
        Long time = TimeUtil.timeStrToSecond(list.get(position).getCreateDate());
        viewHodler.fens_message_time.setText(TimeUtil.getSpaceTime(time));
        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.fens_message_img_item)
        CircleImageView fens_message_img;
        @BindView(R.id.fens_message_titel_item)
        TextView fens_message_titel;
        @BindView(R.id.fens_hg_text)
        TextView fens_hg_text;
        @BindView(R.id.fens_message_time_item)
        TextView fens_message_time;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
