package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.utils.ImageUtils;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/8.
 */

public class UserDetalisProductAdapter extends BaseAdapter {
    private List<Follow.ContentBean> list;
    private Context context;
    private List<Follow.ContentBean.AttachmentListBean> listBeans;
    private OnItemClickListener listener;
    private Long time;
    private Bitmap bitmap;
    private ImageUtils imageUtils;

    public UserDetalisProductAdapter(List<Follow.ContentBean> list, Context context) {
        this.list = list;
        this.context = context;
        imageUtils = new ImageUtils(context, null);
    }

    public void notifyDataSetChanged(List<Follow.ContentBean> list) {
        this.list = list;
        super.notifyDataSetChanged();
    }


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
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        try {
            switch (list.get(position).getItemType()) {
                case 1:
                    viewHodler.linearLayout.setVisibility(View.GONE);
                    viewHodler.iamgeLable.setVisibility(View.VISIBLE);
                    viewHodler.imageProduct.setVisibility(View.VISIBLE);
                    if (list.get(position).getAttachmentList().size() > 0) {
                        GlideManager.loadImage(context
                                , list.get(position).getAttachmentList().get(0).getAttachmentUrl()
                                , R.drawable.ic_zan
                                , viewHodler.imageProduct);
                    }
                    viewHodler.imageProduct.setImageResource(R.drawable.ic_zan);
                    viewHodler.tvContent.setText("");
                    viewHodler.iamgeLable.setImageResource(R.drawable.zdx);
                    viewHodler.tvTitle.setText(list.get(position).getTitle());
                    viewHodler.tvDesc.setText(list.get(position).getTypeDesc());
                    viewHodler.tvNumer.setText(list.get(position).getReadNumber());
                    time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                    viewHodler.tvTime.setText(TimeUtil.getSpaceTime(time));

                    if (!TextUtils.isEmpty(list.get(position).getBrandName())) {
                        viewHodler.tvbrandName.setVisibility(View.GONE);
                        viewHodler.tvbrandName.setText(list.get(position).getBrandName());
                    }
                    break;
                case 2:
                    viewHodler.imageProduct.setVisibility(View.GONE);   //图片隐藏
                    viewHodler.tvContent.setVisibility(View.GONE);     //不显示内容
                    viewHodler.linearLayout.setVisibility(View.VISIBLE);
                    viewHodler.iamgeLable.setVisibility(View.VISIBLE);
                    viewHodler.iamgeLable.setImageResource(R.drawable.jhx);
                    viewHodler.tvTitle.setText(list.get(position).getTitle());
                    viewHodler.tvDesc.setText(list.get(position).getTypeDesc());
                    viewHodler.tvNumer.setText(list.get(position).getReadNumber());
                    time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                    viewHodler.tvTime.setText(TimeUtil.getSpaceTime(time));
                    listBeans = list.get(position).getAttachmentList();


                    if (list.get(position).getAttachmentList().size() == 2) {
                        GlideManager.loadImage(context, list.get(position).getAttachmentList().get(0).getAttachmentUrl()
                                , R.drawable.ic_zan, viewHodler.imageView1);

                        GlideManager.loadImage(context, list.get(position).getAttachmentList().get(1).getAttachmentUrl()
                                , R.drawable.ic_zan, viewHodler.imageView2);

                        viewHodler.imageView3.setImageResource(R.drawable.ic_zan);
                    }

                    if (list.get(position).getAttachmentList().size() == 1) {
                        GlideManager.loadImage(context, list.get(position).getAttachmentList().get(0).getAttachmentUrl()
                                , R.drawable.ic_zan, viewHodler.imageView1);
                        viewHodler.imageView2.setImageResource(R.drawable.ic_zan);
                        viewHodler.imageView3.setImageResource(R.drawable.ic_zan);
                    }
                    if (list.get(position).getAttachmentList().size() == 0) {
                        viewHodler.imageView1.setImageResource(R.drawable.ic_zan);
                        viewHodler.imageView2.setImageResource(R.drawable.ic_zan);
                        viewHodler.imageView3.setImageResource(R.drawable.ic_zan);
                    }


                    if (!TextUtils.isEmpty(list.get(position).getBrandName())) {
                        viewHodler.tvbrandName.setVisibility(View.GONE);
                        viewHodler.tvbrandName.setText(list.get(position).getBrandName());
                    }
                    break;
                case 3:
                    if ((list.get(position).getAttachmentList().size() > 0 && list.get(position).getTypeDesc().equals("帖子"))
                            ||
                            (list.get(position).getAttachmentList().size() > 0 &&
                                    (list.get(position).getTypeDesc().equals("出售") || list.get(position).getTypeDesc().equals("求购")))
                            ) {
                        viewHodler.imageProduct.setVisibility(View.VISIBLE);
                        viewHodler.iamgeLable.setVisibility(View.GONE);
                        viewHodler.linearLayout.setVisibility(View.GONE);
                        GlideManager.loadImage(context
                                , list.get(position).getAttachmentList().get(0).getAttachmentUrl()
                                , R.drawable.ic_zan
                                , viewHodler.imageProduct);
                        viewHodler.tvContent.setText("");
                        viewHodler.tvTitle.setText(list.get(position).getTitle());
                        viewHodler.tvDesc.setText(list.get(position).getTypeDesc());
                        viewHodler.tvNumer.setText(list.get(position).getReadNumber());
                        time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                        viewHodler.tvTime.setText(TimeUtil.getSpaceTime(time));
                        if (!TextUtils.isEmpty(list.get(position).getBrandName())) {
                            viewHodler.tvbrandName.setVisibility(View.GONE);
                            viewHodler.tvbrandName.setText(list.get(position).getBrandName());
                        }
                    }
                    //话题
                    else if (list.get(position).getIsDigestPost() == 1) {
                        viewHodler.iamgeLable.setVisibility(View.VISIBLE);
                        viewHodler.imageProduct.setVisibility(View.GONE);
                        viewHodler.linearLayout.setVisibility(View.GONE);
                        viewHodler.iamgeLable.setImageResource(R.drawable.ry);
                        viewHodler.tvTitle.setText(list.get(position).getTitle());
                        viewHodler.tvDesc.setText(list.get(position).getTypeDesc());
                        viewHodler.tvContent.setText(list.get(position).getContent());
                        viewHodler.tvNumer.setText(list.get(position).getReadNumber());
                        time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                        viewHodler.tvTime.setText(TimeUtil.getSpaceTime(time));

                        if (!TextUtils.isEmpty(list.get(position).getBrandName())) {
                            viewHodler.tvbrandName.setVisibility(View.GONE);
                            viewHodler.tvbrandName.setText(list.get(position).getBrandName());
                        }
                    } else {
                        viewHodler.iamgeLable.setVisibility(View.GONE);
                        viewHodler.imageProduct.setVisibility(View.GONE);
                        viewHodler.linearLayout.setVisibility(View.GONE);
                        String content = null;
                        if (!TextUtils.isEmpty(list.get(position).getContent())) {
                            content = list.get(position).getContent().trim();
                        }
                        viewHodler.tvContent.setText(content);
                        viewHodler.tvTitle.setText(list.get(position).getTitle());
                        viewHodler.tvDesc.setText(list.get(position).getTypeDesc());
                        viewHodler.tvNumer.setText(list.get(position).getReadNumber());
                        time = TimeUtil.timeStrToSecond(list.get(position).getPostDate());
                        viewHodler.tvTime.setText(TimeUtil.getSpaceTime(time));
                        if (!TextUtils.isEmpty(list.get(position).getBrandName())) {
                            viewHodler.tvbrandName.setVisibility(View.GONE);
                            viewHodler.tvbrandName.setText(list.get(position).getBrandName());
                        }

                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    /**
     * 单张  置顶
     */
    public class ViewHodler extends RecyclerView.ViewHolder {
        @BindView(R.id.product_lable)
        ImageView iamgeLable;
        @BindView(R.id.product_title)
        TextView tvTitle;
        @BindView(R.id.product_content)
        TextView tvContent;
        @BindView(R.id.product_desc)
        TextView tvDesc;
        @BindView(R.id.product_time)
        TextView tvTime;
        @BindView(R.id.product_numer)
        TextView tvNumer;
        @BindView(R.id.product_image)
        ImageView imageProduct;
        @BindView(R.id.product_brandName)
        TextView tvbrandName;

        @BindView(R.id.img_lay)
        LinearLayout linearLayout;
        @BindView(R.id.image1)
        ImageView imageView1;
        @BindView(R.id.image2)
        ImageView imageView2;
        @BindView(R.id.image3)
        ImageView imageView3;


        public ViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
