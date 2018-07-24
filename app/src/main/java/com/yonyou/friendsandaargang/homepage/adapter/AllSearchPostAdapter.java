package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.homepage.modle.AllSearch;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/8.
 */

public class AllSearchPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //置顶
    private final int IsTop = 1;
    //精华
    private final int IsEssence = 2;
    //其他
    private final int IsOther = 3;


    //
    private List<AllSearch.ContentBean.PostBean> list;
    private Context context;

    private List<AllSearch.ContentBean.PostBean.AttachmentListBean> listBeans;
    private String contents;

    public AllSearchPostAdapter(List<AllSearch.ContentBean.PostBean> list, Context context, String contents) {
        this.list = list;
        this.context = context;
        this.contents = contents;
        listBeans = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //单图
        if (viewType == IsTop) {
            view = LayoutInflater.from(context).inflate(R.layout.recylerview_item_essence_one, parent, false);
            return new ViewHodlerOne(view);
        }
        //多图
        else if (viewType == IsEssence) {
            view = LayoutInflater.from(context).inflate(R.layout.recylerview_item_essence_two, parent, false);
            return new ViewHodlerTwo(view);
        }
        //无图
        else if (viewType == IsOther) {
            view = LayoutInflater.from(context).inflate(R.layout.recylerview_item_essence_therr, parent, false);
            return new ViewHodlerTherr(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int type = list.get(position).getItemType();
        if (onItemOnclick != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemOnclick.OnItemOnclick(v, holder.getPosition());
                }
            });
        }
        //单图  置顶
        if (type == IsTop) {
            ViewHodlerOne hodlerOne = (ViewHodlerOne) holder;
            //置顶帖子
            if (list.get(position).getIsTop() == 1) {
                if (list.get(position).getAttachmentList().size() > 0 && !list.get(position).getAttachmentList().equals("")) {
                    Glide.with(context).load(list.get(position).getAttachmentList().get(0).getAttachmentUrl())
                            .error(R.drawable.user)
                            .into(hodlerOne.essence_image_one);
                } else {
                    //图片隐藏
                    hodlerOne.essence_image_one.setVisibility(View.GONE);
                    hodlerOne.essence_lable_one.setVisibility(View.GONE);
                }
                //标题
                hodlerOne.essence_title_one.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), contents));
                //类型
                hodlerOne.essence_type_one.setText(list.get(position).getTypeDesc());
                //时间
                Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                hodlerOne.essence_time_one.setText(TimeUtil.getSpaceTime(time));
                //浏览次数
                hodlerOne.essence_numer_one.setText(list.get(position).getReadNumber() + "");
                //内容
                hodlerOne.essence_content_one.setVisibility(View.GONE);
                //标签
                hodlerOne.essence_lable_one.setImageResource(R.drawable.zdx);
                //图片

            }
        }
        //多图  精华
        else if (type == IsEssence) {
            ViewHodlerTwo hodlerTwo = (ViewHodlerTwo) holder;
            if (list.get(position).getAttachmentList().size() > 0 && !list.get(position).getAttachmentList().equals("")) {
                listBeans = list.get(position).getAttachmentList();
                AllPostAdapter figureAdapter = new AllPostAdapter(context, listBeans);
                hodlerTwo.essence_gridview_two.setAdapter(figureAdapter);
            }
            hodlerTwo.essence_lable_two.setImageResource(R.drawable.jhx);
            hodlerTwo.essence_type_two.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTypeDesc(), contents));
            hodlerTwo.essence_numer_two.setText(list.get(position).getReadNumber() + "");
            Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
            hodlerTwo.essence_time_two.setText(TimeUtil.getSpaceTime(time));
            hodlerTwo.essence_content_two.setText(list.get(position).getTitle());

        }
        //无图  其他
        else if (type == IsOther) {
            ViewHodlerTherr hodlerTherr = (ViewHodlerTherr) holder;
            //后台返回类型3  无图片类型的item 的   不是置顶的帖子  也不是话题的
            if (list.get(position).getTypeDesc().equals("帖子")) {
                //普通帖子有图片 就显示图片
                if (list.get(position).getAttachmentList().size() > 0 && !list.get(position).getAttachmentList().equals("")) {
                    Glide.with(context)
                            .load(list.get(position).getAttachmentList().get(0).getAttachmentUrl())
                            .error(R.drawable.user)
                            .into(hodlerTherr.essence_wutu_image);
                    hodlerTherr.essence_wutu_leable_image.setVisibility(View.GONE);
                    //没有图片就隐藏图片
                } else {
                    hodlerTherr.essence_wutu_leable_image.setVisibility(View.GONE);
                    hodlerTherr.essence_wutu_image.setVisibility(View.GONE);
                }
                hodlerTherr.essence_wutu_title.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), contents));
                hodlerTherr.essence_wutu_content.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getContent(), contents));
                hodlerTherr.essence_wutu_numer.setText(list.get(position).getReadNumber() + "");
                Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                hodlerTherr.essence_wutu_time.setText(TimeUtil.getSpaceTime(time));
                hodlerTherr.essence_wutu_type.setText(list.get(position).getTypeDesc());
                //如果是话题
            } else if (list.get(position).getTypeDesc().equals("话题")) {
                //热议的话题  只显示2行内容   需要加个标签  没有图片
                /*if (list.get(position).getIsDigestPost() == 1) {
                    hodlerTherr.essence_wutu_leable_image.setImageResource(R.drawable.ry);
                } else {
                    hodlerTherr.essence_wutu_leable_image.setVisibility(View.GONE);
                }*/
                hodlerTherr.essence_wutu_title.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), contents));
                hodlerTherr.essence_wutu_content.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getContent(), contents));
                hodlerTherr.essence_wutu_numer.setText(list.get(position).getReadNumber() + "");

                Long time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                hodlerTherr.essence_wutu_time.setText(TimeUtil.getSpaceTime(time));
                hodlerTherr.essence_wutu_type.setText(list.get(position).getTypeDesc());
                hodlerTherr.essence_wutu_image.setVisibility(View.GONE);

            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    /**
     * 单张  置顶
     */

    public class ViewHodlerOne extends RecyclerView.ViewHolder {

        private OnItemOnclick onItemOnclick;
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


    //自定义接口
    private OnItemOnclick onItemOnclick;

    /**
     * 定义一个接口
     */

    public interface OnItemOnclick {
        void OnItemOnclick(View view, int position);
    }

    //暴露接口供外部使用
    public void setOnItenOnclick(OnItemOnclick onItenOnclick) {
        this.onItemOnclick = onItenOnclick;
    }

}
