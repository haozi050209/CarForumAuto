package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.homepage.activity.MorePostActivity;
import com.yonyou.friendsandaargang.homepage.modle.SearchPostBean;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class SearchPostAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<SearchPostBean.ContentBean> postBeans;
    private String contents;
    private String typePost;

    public SearchPostAdapter(Context context, List<SearchPostBean.ContentBean> postBeans, String contents, String typePost) {
        this.context = context;
        this.postBeans = postBeans;
        this.contents = contents;
        this.typePost = typePost;
    }


    public void notifyDataSetChanged(List<SearchPostBean.ContentBean> postBeans) {
        super.notifyDataSetChanged();
        this.postBeans = postBeans;
    }

    @Override
    public int getCount() {
        if (typePost.equals("homeType")) {
            if (postBeans.size() == 8) {
                return 7;
            } else {
                return postBeans.size();
            }
        }else {
            return postBeans.size();
        }
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
        ViewHodlerPost viewHodlerPost;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_post, null, false);
            viewHodlerPost = new ViewHodlerPost(convertView);
            convertView.setTag(viewHodlerPost);
        } else {
            viewHodlerPost = (ViewHodlerPost) convertView.getTag();
        }

        //话题
        if (postBeans.get(position).getTypeDesc().equals("话题")) {
            viewHodlerPost.imageView.setVisibility(View.GONE);
            viewHodlerPost.content.setVisibility(View.VISIBLE);
            viewHodlerPost.title.setVisibility(View.VISIBLE);
            viewHodlerPost.content.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getContent(), contents));
            viewHodlerPost.title.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getTitle(), contents));

        }
        //是帖子
        else if (postBeans.get(position).getTypeDesc().equals("帖子")) {
            //没有图片就隐藏图片 显示标题与内容
            if (postBeans.get(position).getAttachmentList().size() == 0) {
                viewHodlerPost.imageView.setVisibility(View.GONE);
                viewHodlerPost.title.setVisibility(View.VISIBLE);
                viewHodlerPost.content.setVisibility(View.VISIBLE);
                viewHodlerPost.content.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getContent(), contents));
                viewHodlerPost.title.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getTitle(), contents));

            } else {
                viewHodlerPost.imageView.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(postBeans.get(position).getAttachmentList().get(0).getAttachmentUrl())
                        .error(R.drawable.ic_zan)
                        .into(viewHodlerPost.imageView);
                viewHodlerPost.title.setVisibility(View.VISIBLE);
                viewHodlerPost.content.setVisibility(View.VISIBLE);
                viewHodlerPost.content.setText("");
                viewHodlerPost.title.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getTitle(), contents));
            }
        }

        //是否置顶
        if (postBeans.get(position).getIsTop() == 1) {
            viewHodlerPost.lable.setVisibility(View.VISIBLE);
            viewHodlerPost.lable.setImageResource(R.drawable.zdx);
        } else {
            viewHodlerPost.lable.setVisibility(View.GONE);
        }


        viewHodlerPost.type.setText(postBeans.get(position).getTypeDesc());
        viewHodlerPost.numer.setText(postBeans.get(position).getReadNumber() + "");
        Long time = TimeUtil.timeStrToSecond(postBeans.get(position).getPostDate());
        viewHodlerPost.time.setText(TimeUtil.getSpaceTime(time));

        if (typePost.equals("homeType") && position == 6) {
            viewHodlerPost.luntab_lay.setVisibility(View.VISIBLE);
            viewHodlerPost.luntab_lay.setOnClickListener(this);
        } else {
            viewHodlerPost.luntab_lay.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, MorePostActivity.class);
        intent.putExtra("typePost", "typePost");
        intent.putExtra("content", contents);
        context.startActivity(intent);
    }


    public class ViewHodlerPost {

        @BindView(R.id.essence_image_one)
        ImageView imageView;
        @BindView(R.id.search_lable_post)
        ImageView lable;
        @BindView(R.id.search_title_post)
        TextView title;
        @BindView(R.id.search_content_post)
        TextView content;
        @BindView(R.id.search_type_post)
        TextView type;
        @BindView(R.id.search_time_post)
        TextView time;
        @BindView(R.id.search_numer_post)
        TextView numer;

        @BindView(R.id.luntan_ck_lay)
        RelativeLayout luntab_lay;

        public ViewHodlerPost(View view) {
            ButterKnife.bind(this, view);
        }


    }


}
