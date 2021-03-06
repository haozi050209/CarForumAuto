package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.info.bean.Area;
import com.yonyou.friendsandaargang.info.bean.FAQByKeyWord;
import com.yonyou.friendsandaargang.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class SearchResultAdapter extends BaseAdapter {


    private Context context;
    private List<FAQByKeyWord.ContentBean> list;
    private String keyword;

    public SearchResultAdapter(Context context, List<FAQByKeyWord.ContentBean> list, String keyword) {
        this.context = context;
        this.list = list;
        this.keyword = keyword;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_searchresul_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        viewHodler.tv.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), keyword));

        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.tv)
        TextView tv;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
