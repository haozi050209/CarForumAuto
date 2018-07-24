package com.yonyou.friendsandaargang.info.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.SyatemMessage;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class PurseNewsAdapter extends BaseAdapter {


    private Context context;

    private List<SyatemMessage.ContentBean> beanList;

    public PurseNewsAdapter(Context context, List<SyatemMessage.ContentBean> beanList) {

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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_fens_item_forum, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.forum_item_head.setImageResource(R.drawable.qbxxfb);
        viewHodler.forum_item_name.setText(beanList.get(position).getMessageTitle());
        viewHodler.forum_item_content.setText(beanList.get(position).getMessageContent());

        Long time = TimeUtil.timeStrToSecond(beanList.get(position).getCreateDate());
        viewHodler.forum_item_time.setText(TimeUtil.getSpaceTime(time));
        return convertView;
    }


    public class ViewHodler {


        @BindView(R.id.forum_item_head)
        CircleImageView forum_item_head;
        @BindView(R.id.forum_item_name)
        TextView forum_item_name;
        @BindView(R.id.forum_item_time)
        TextView forum_item_time;
        @BindView(R.id.forum_item_content)
        TextView forum_item_content;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
