package com.yonyou.friendsandaargang.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

import java.util.List;

/**
 * Created by shibing on 18/3/27.
 */

public class SpinerPopWindow<T> extends PopupWindow {


    private LayoutInflater inflater;
    private ListView mListView;
    private List<T> list;

    private PopWindowAdapter popWindowAdapter;
    private Context context;

    public SpinerPopWindow(Context context, List<T> list, AdapterView.OnItemClickListener clickListener) {
        super(context);
        this.list = list;
        this.context = context;
        init(clickListener);
    }


    private void init(AdapterView.OnItemClickListener clickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setAdapter(popWindowAdapter = new PopWindowAdapter());
        mListView.setOnItemClickListener(clickListener);
    }

    public class PopWindowAdapter extends BaseAdapter {
        int positions;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }


        public void setTexeCloro(int position) {
            this.positions = position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.list_popwindow_item, null);
                holder.tvName = (TextView) convertView.findViewById(R.id.popwindow_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(getItem(position).toString());

//            if (position == 0) {
//                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_red));
//            } else {
//                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_gray3));
//            }
//
//            if (positions==position){
//                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_red));
//            }else {
//                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_gray3));
//            }

            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tvName;
    }
}
