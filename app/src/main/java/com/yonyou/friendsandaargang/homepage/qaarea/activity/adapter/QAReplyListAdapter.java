package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.homepage.modle.QAReplyLisBean;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/31.
 */

public class QAReplyListAdapter extends BaseAdapter {


    private Context mContext;
    private List<QAReplyLisBean.ContentBean> list;
    private OnItemClickListener listener;
    private String publisherId;
    private String userId;
    private int mHours, mMinute;
    private Long time;

    public QAReplyListAdapter(Context mContext, List<QAReplyLisBean.ContentBean> list, String publisherId
            , int hours, int minute) {
        this.list = list;
        this.mContext = mContext;
        this.publisherId = publisherId;
        this.mHours = hours;
        this.mMinute = minute;
        userId = SPTool.getContent(mContext, Constants.USER_ID);
    }


    public void setListData(List<QAReplyLisBean.ContentBean> list) {
        this.list = list;
    }

    /**
     * 添加item监听
     *
     * @param listener
     */
    public void addOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
        ViewHodler viewHodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_reply, null);
            viewHodler = new ViewHodler(convertView, position);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        Glide.with(mContext)
                .load(list.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.imageView);
        viewHodler.tvName.setText(list.get(position).getNickname());
        viewHodler.tvContent.setText(list.get(position).getContent());


        time = TimeUtil.timeStrToSecond(list.get(position).getReplyDate());
        viewHodler.tvTime.setText(TimeUtil.getSpaceTime(time));

        viewHodler.tvNum.setText(list.get(position).getThumbupNumber());


        //已采纳的回答
        if (list.get(position).getIsSelected() == 1) {
            viewHodler.tvCAina.setVisibility(View.VISIBLE);
            viewHodler.tvCAina.setText("已采纳");
            viewHodler.tvNum.setBackgroundResource(0);
            viewHodler.tvNum.setTextColor(mContext.getResources().getColor(R.color.color_balank));
            if (list.get(position).getThumbuped() == 1) {
                viewHodler.tvNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dianzan_on, 0);
            } else {
                viewHodler.tvNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dianzan, 0);
            }
        }
        //等于0 没有被采纳
        else {
            //把采纳按钮隐藏
            viewHodler.tvCAina.setVisibility(View.GONE);
            //如果说问答时间没有过期
            if (userId.equals(publisherId) && (mHours > 0 || mMinute > 0)) {

                viewHodler.tvCAina.setVisibility(View.GONE);
                viewHodler.tvNum.setText("采纳回答");
                viewHodler.tvNum.setBackgroundResource(R.drawable.shape_follow_red);
                viewHodler.tvNum.setTextColor(mContext.getResources().getColor(R.color.color_white));
            } else {
                viewHodler.tvNum.setBackgroundResource(0);
                viewHodler.tvNum.setTextColor(mContext.getResources().getColor(R.color.color_balank));
                if (list.get(position).getThumbuped() == 1) {
                    viewHodler.tvNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dianzan_on, 0);
                } else {
                    viewHodler.tvNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dianzan, 0);
                }
            }


        }



        viewHodler.tvNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.qareply_item_image)
        CircleImageView imageView;
        @BindView(R.id.qareply_item_name)
        TextView tvName;
        @BindView(R.id.qareply_item_canai)
        TextView tvCAina;
        @BindView(R.id.qareply_item_content)
        TextView tvContent;
        @BindView(R.id.qareply_item_time)
        TextView tvTime;
        @BindView(R.id.qareply_item_dianz)
        TextView tvNum;

        public ViewHodler(View itemView, final int viewType) {
            ButterKnife.bind(this, itemView);
        }
    }
}
