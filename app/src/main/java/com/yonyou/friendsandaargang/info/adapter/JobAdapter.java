package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.JobList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class JobAdapter extends BaseAdapter {
    private Context context;
    private List<JobList.ContentBean> list;

    public JobAdapter(Context context, List<JobList.ContentBean> list) {
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_job_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.brand_item_name.setText(list.get(position).getJob());
        return convertView;
    }


    public class ViewHodler {

        @BindView(R.id.job_item_name)
        TextView brand_item_name;
        @BindView(R.id.job_item_off)
        ImageView brand_item_off;


        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
