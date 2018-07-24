package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.DataBankBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/6.
 */

public class DataBankAdapter extends BaseAdapter {

    private Context context;
    private List<DataBankBean.ContentBean> list;

    public DataBankAdapter(Context context, List<DataBankBean.ContentBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_file, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String fileExt = list.get(position).getFileExt();
        switch (fileExt) {
            case "doc":
            case "docx":
                holder.iv_file.setImageResource(R.drawable.ico_word);
                break;
            case "xls":
                holder.iv_file.setImageResource(R.drawable.ico_excel);
                break;
            case "txt":
                holder.iv_file.setImageResource(R.drawable.txt);
                break;
            case "gif":
                holder.iv_file.setImageResource(R.drawable.gif);
                break;
            case "mp4":
                holder.iv_file.setImageResource(R.drawable.mp4);
                break;
        }
        String fileId = list.get(position).getFileId();
        holder.tv_filename.setText(fileId.substring(0, fileId.indexOf(".")));
        holder.tv_createdate.setText(list.get(position).getCreateDate());
        holder.tv_displaysize.setText(list.get(position).getDisplaySize());
        return convertView;
    }

    public class ViewHolder {

        @BindView(R.id.iv_file)
        ImageView iv_file;
        @BindView(R.id.tv_filename)
        TextView tv_filename;
        @BindView(R.id.tv_createdate)
        TextView tv_createdate;
        @BindView(R.id.tv_displaysize)
        TextView tv_displaysize;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
