package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.adapter.FollowFigureAdapter;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/8.
 */

public class FollowForumDetalisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //置顶
    private final int IsTop = 1;
    //精华
    private final int IsEssence = 2;
    //其他
    private final int IsOther = 3;

    private List<Follow.ContentBean> list;
    private Context context;

    private List<Follow.ContentBean.AttachmentListBean> listBeans;

    public FollowForumDetalisAdapter(List<Follow.ContentBean> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == IsTop) {
            view = LayoutInflater.from(context).inflate(R.layout.recylerview_item_essence_one, parent, false);
            return new ViewHodlerOne(view);
        } else if (viewType == IsEssence) {
            view = LayoutInflater.from(context).inflate(R.layout.recylerview_item_essence_two, parent, false);
            return new ViewHodlerTwo(view);
        } else if (viewType == IsOther) {
            view = LayoutInflater.from(context).inflate(R.layout.recylerview_item_essence_therr, parent, false);
            return new ViewHodlerTherr(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        int type = list.get(position).getItemType();

        if (onItemOnClick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemOnClick.onItemOnClick(v, holder.getPosition());
                }
            });
        }


        //类型一 单张图片的item
        if (type == IsTop) {
            ViewHodlerOne hodlerOne = (ViewHodlerOne) holder;
            if (list.get(position).getIsTop() == 1) {
                //如果说是置顶的帖子  必须要有一张图片显示   也有可能后台出错没有图片则显示一张默认图片
                if (!list.get(position).getAttachmentList().equals("") && list.get(position).getAttachmentList().size() > 0) {
                    Glide.with(context)
                            .load(list.get(position).getAttachmentList().get(0).getAttachmentUrl())
                            .into(hodlerOne.essence_image_one);
                } else {
                    hodlerOne.essence_image_one.setImageResource(R.drawable.user);
                }
                hodlerOne.essence_lable_one.setImageResource(R.drawable.zdx);
                hodlerOne.essence_title_one.setText(list.get(position).getTitle());
                hodlerOne.essence_numer_one.setText(list.get(position).getReadNumber() + "");
                Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                hodlerOne.essence_time_one.setText(TimeUtil.getSpaceTime(time));


                hodlerOne.essence_type_one.setText(list.get(position).getTypeDesc());
                hodlerOne.essence_content_one.setVisibility(View.GONE);
                //后台在有图片的类型的帖子  可能也不是置顶的   普通的帖子  有图片则显示图片  隐藏标签
            }

        } else if (type == IsEssence) {
            //多张图片的只有是精华类型
            ViewHodlerTwo hodlerTwo = (ViewHodlerTwo) holder;
            hodlerTwo.essence_lable_two.setImageResource(R.drawable.jhx);
            hodlerTwo.essence_content_two.setText(list.get(position).getTitle());
            hodlerTwo.essence_type_two.setText(list.get(position).getTypeDesc());

            Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
            hodlerTwo.essence_time_two.setText(TimeUtil.getSpaceTime(time));


            hodlerTwo.essence_numer_two.setText(list.get(position).getReadNumber());
            if (list.get(position).getAttachmentList() != null &&
                    !list.get(position).getAttachmentList().equals("")) {
                listBeans = list.get(position).getAttachmentList();
                FollowFigureAdapter followFigureAdapter = new FollowFigureAdapter(context, listBeans);
                hodlerTwo.essence_gridview_two.setAdapter(followFigureAdapter);
            }

        }

        /**
         * 其他类型 不是置顶也不是精华
         */
        else if (type == IsOther) {
            ViewHodlerTherr hodlerTherr = (ViewHodlerTherr) holder;
            //后台返回类型3  无图片类型的item 的   不是置顶的帖子  也不是话题的
            if (list.get(position).getTypeDesc().equals("帖子")) {
                //不是置顶 也没有图片的帖子
                if (list.get(position).getAttachmentList().size() > 0 && !list.get(position).getAttachmentList().equals("")) {
                    Glide.with(context)
                            .load(list.get(position).getAttachmentList().get(0).getAttachmentUrl())
                            .error(R.drawable.user)
                            .into(hodlerTherr.essence_wutu_image);
                    hodlerTherr.essence_wutu_leable_image.setVisibility(View.GONE);
                } else {
                    hodlerTherr.essence_wutu_leable_image.setVisibility(View.GONE);
                    hodlerTherr.essence_wutu_cardview.setVisibility(View.GONE);
                }
                hodlerTherr.essence_wutu_title.setText(list.get(position).getTitle());
                hodlerTherr.essence_wutu_content.setText(list.get(position).getContent());
                hodlerTherr.essence_wutu_numer.setText(list.get(position).getReadNumber() + "");


                Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                hodlerTherr.essence_wutu_time.setText(TimeUtil.getSpaceTime(time));


                hodlerTherr.essence_wutu_type.setText(list.get(position).getTypeDesc());
            } else if (list.get(position).getTypeDesc().equals("话题")) {
                //热议的话题  只显示2行内容   需要加个标签  没有图片
                if (list.get(position).getIsDigestPost() == 1) {
                    hodlerTherr.essence_wutu_leable_image.setImageResource(R.drawable.ry);
                } else {
                    hodlerTherr.essence_wutu_leable_image.setVisibility(View.GONE);
                }
                hodlerTherr.essence_wutu_title.setText(list.get(position).getTitle());
                hodlerTherr.essence_wutu_content.setText(list.get(position).getContent());
                hodlerTherr.essence_wutu_numer.setText(list.get(position).getReadNumber() + "");

                Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                hodlerTherr.essence_wutu_time.setText(TimeUtil.getSpaceTime(time));

                hodlerTherr.essence_wutu_type.setText(list.get(position).getTypeDesc());
                hodlerTherr.essence_wutu_cardview.setVisibility(View.GONE);

            }

        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getTypeDesc().equals("帖子")) {
            return IsTop;
        } else if (list.get(position).getTypeDesc().equals("话题")) {
            return IsEssence;
        }
        return super.getItemViewType(position);
    }

    /**
     * 单张  置顶
     */
    public class ViewHodlerOne extends RecyclerView.ViewHolder {
        @BindView(R.id.essence_lable_one)
        ImageView essence_lable_one;
        @BindView(R.id.essence_title_one)
        TextView essence_title_one;
        @BindView(R.id.essence_type_one)
        TextView essence_type_one;
        @BindView(R.id.essence_time_one)
        TextView essence_time_one;
        @BindView(R.id.essence_numer_one)
        TextView essence_numer_one;
        @BindView(R.id.essence_image_one)
        ImageView essence_image_one;
        @BindView(R.id.essence_content_one)
        TextView essence_content_one;

        public ViewHodlerOne(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 多张item  精华
     */
    public class ViewHodlerTwo extends RecyclerView.ViewHolder {
        @BindView(R.id.essence_lable_two)
        ImageView essence_lable_two;
        @BindView(R.id.essence_content_two)
        TextView essence_content_two;
        @BindView(R.id.essence_gridview_two)
        MyGridView essence_gridview_two;
        @BindView(R.id.essence_type_two)
        TextView essence_type_two;
        @BindView(R.id.essence_time_two)
        TextView essence_time_two;
        @BindView(R.id.essence_numer_two)
        TextView essence_numer_two;

        public ViewHodlerTwo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * 无图item
     */
    public class ViewHodlerTherr extends RecyclerView.ViewHolder {
        @BindView(R.id.essence_wutu_title)
        TextView essence_wutu_title;
        @BindView(R.id.essence_wutu_content)
        TextView essence_wutu_content;
        @BindView(R.id.essence_wutu_type)
        TextView essence_wutu_type;
        @BindView(R.id.essence_wutu_time)
        TextView essence_wutu_time;
        @BindView(R.id.essence_wutu_numer)
        TextView essence_wutu_numer;
        @BindView(R.id.essence_wutu_image)
        ImageView essence_wutu_image;
        @BindView(R.id.essence_wutu_leable_image)
        ImageView essence_wutu_leable_image;
        @BindView(R.id.essence_wutu_cardview)
        CardView essence_wutu_cardview;

        public ViewHodlerTherr(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private OnItemOnClick onItemOnClick;


    //自定义一个接口

    public interface OnItemOnClick {

        void onItemOnClick(View view, int position);
    }


    //提供个接口暴露给外部使用

    public void setOnItemOnClick(OnItemOnClick onItemOnClick) {
        this.onItemOnClick = onItemOnClick;
    }


}
