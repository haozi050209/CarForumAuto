package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.Area;
import com.yonyou.friendsandaargang.info.bean.UserLevelog;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class ReWardDetailsAdapter extends BaseAdapter {


    private Context context;
    private List<UserLevelog.ContentBean> list;

    public ReWardDetailsAdapter(Context context, List<UserLevelog.ContentBean> list) {
        this.context = context;
        this.list = list;
    }


    public void notifyDataSetChanged(List<UserLevelog.ContentBean> list) {
        super.notifyDataSetChanged();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_rewarddetails_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv_content_txet.setText(list.get(position).getActionContent() + "");
        Long time = TimeUtil.timeStrToSecond(list.get(position).getCreateDate());
        viewHodler.tv_time.setText(TimeUtil.getSpaceTime(time));


        viewHodler.tv_number.setText("+" + list.get(position).getExpChange() + "");
        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_content_txet)
        TextView tv_content_txet;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
