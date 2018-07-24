package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.QaUserToMeBean;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/6/12.
 */

public class QaUserToMeAdapter extends BaseAdapter {


    private Context mContext;
    List<QaUserToMeBean.ContentBean> list;

    public QaUserToMeAdapter(Context mContext, List<QaUserToMeBean.ContentBean> list) {
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
        Viewodler viewodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_usertome_item, null);
            viewodler = new Viewodler(convertView);
            convertView.setTag(viewodler);
        } else {
            viewodler = (Viewodler) convertView.getTag();
        }

        viewodler.tvName.setText(list.get(position).getTitle());

        Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
        viewodler.tvTime.setText(TimeUtil.getSpaceTime(time));

        viewodler.tvSee.setText("看过   " + list.get(position).getReadNumber());
        if (list.get(position).getReplyNumber() == 0) {
            viewodler.tvAnswer.setText("回答   " + list.get(position).getReplyNumber());
            viewodler.tvJieDa.setText("未解决");
        } else {
            viewodler.tvAnswer.setText("回答   " + list.get(position).getReplyNumber());
            viewodler.tvJieDa.setText("已解答");
        }

        return convertView;
    }


    public class Viewodler {

        @BindView(R.id.qauser_item_name)
        TextView tvName;
        @BindView(R.id.qauser_item_time)
        TextView tvTime;
        @BindView(R.id.qauser_item_see)
        TextView tvSee;
        @BindView(R.id.qauser_item_answer)
        TextView tvAnswer;
        @BindView(R.id.qauser_item_jieda)
        TextView tvJieDa;

        public Viewodler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
