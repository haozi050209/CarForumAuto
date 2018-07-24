package com.yonyou.friendsandaargang.info.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.info.bean.QaUserAnswerBean;
import com.yonyou.friendsandaargang.info.bean.QaUserToMeBean;
import com.yonyou.friendsandaargang.utils.TextTool;
import com.yonyou.friendsandaargang.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/6/12.
 */

public class QaAnswerAdapter extends BaseAdapter {


    private Context mContext;
    List<QaUserAnswerBean.ContentBean> list;


    public QaAnswerAdapter(Context mContext, List<QaUserAnswerBean.ContentBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    public void notifyDataSetChanged(List<QaUserAnswerBean.ContentBean> list) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewodler viewodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_useranswer_item, null);
            viewodler = new Viewodler(convertView);
            convertView.setTag(viewodler);
        } else {
            viewodler = (Viewodler) convertView.getTag();
        }

        GlideManager.loadImage(mContext
                , list.get(position).getAvatar()
                , R.drawable.user
                , viewodler.imageView);

        viewodler.tvName.setText(list.get(position).getViewerNickname() + "  的回答");
        viewodler.tvTitle.setText(list.get(position).getContent());
        String title = list.get(position).getTitle();
        TextTool.getBuilder("")
                .setBold()
                .setAlign(Layout.Alignment.ALIGN_CENTER)
                .append(list.get(position).getAuthor())
                .setForegroundColor(mContext.getResources().getColor(R.color.color_red))
                .append(":" + title.trim())
                .into(viewodler.tvVewName);
        //是否被采纳
        if (list.get(position).getIsSelected() == 1) {
            viewodler.tvCainai.setVisibility(View.VISIBLE);
        } else {
            viewodler.tvCainai.setVisibility(View.GONE);
        }
        return convertView;
    }


    public class Viewodler {


        @BindView(R.id.qaanswer_image_item)
        CircleImageView imageView;

        @BindView(R.id.qaanswer_name_item)
        TextView tvName;
        @BindView(R.id.qaanswer_name_title)
        TextView tvTitle;
        @BindView(R.id.qaanswer_vername_item)
        TextView tvVewName;
        @BindView(R.id.qaanswer_cainai_item)
        TextView tvCainai;

        public Viewodler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
