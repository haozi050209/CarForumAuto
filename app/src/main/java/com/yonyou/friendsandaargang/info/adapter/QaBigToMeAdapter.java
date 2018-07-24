package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.info.bean.QaToMeBean;
import com.yonyou.friendsandaargang.info.bean.QaUserAnswerBean;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/6/12.
 */

public class QaBigToMeAdapter extends BaseAdapter {


    private Context mContext;
    private List<QaToMeBean.ContentBean> list;
    private OnItemClickListener listener, listener1;

    public QaBigToMeAdapter(Context mContext, List<QaToMeBean.ContentBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    public void notifyDataSetChanged(List<QaToMeBean.ContentBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewodler viewodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_bigtome_item, null);
            viewodler = new Viewodler(convertView);
            convertView.setTag(viewodler);
        } else {
            viewodler = (Viewodler) convertView.getTag();
        }

        GlideManager.loadImage(mContext
                , list.get(position).getAvatar()
                , R.drawable.user
                , viewodler.imageView);
        viewodler.tvName.setText(list.get(position).getAuthor() + "  的提问");
        viewodler.tvTitle.setText(list.get(position).getTitle());
        viewodler.layout.setVisibility(View.VISIBLE);

        //回答
        viewodler.tvAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        //拒绝回答
        viewodler.tvjujAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener1 != null) {
                    listener1.onItemClick(position);
                }
            }
        });

        return convertView;
    }


    //回答
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void addRefuseOnItemClickListener(OnItemClickListener listener) {
        this.listener1 = listener;
    }


    public class Viewodler {
        @BindView(R.id.qabig_answer_image_item)
        CircleImageView imageView;

        @BindView(R.id.qabig_answer_name_item)
        TextView tvName;
        @BindView(R.id.qabig_answer_name_title)
        TextView tvTitle;
        @BindView(R.id.qabig_answer_tv)
        TextView tvAnswer;
        @BindView(R.id.qabig_jujanswer_tv)
        TextView tvjujAnswer;
        @BindView(R.id.qabig_anwser_lay)
        LinearLayout layout;

        public Viewodler(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
