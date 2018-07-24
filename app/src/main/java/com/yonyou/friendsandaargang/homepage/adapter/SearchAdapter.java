package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.guide.adapter.SectionedBaseAdapter;
import com.yonyou.friendsandaargang.homepage.activity.MorePostActivity;
import com.yonyou.friendsandaargang.homepage.activity.MoreQaActivity;
import com.yonyou.friendsandaargang.homepage.activity.MoreUserActivity;
import com.yonyou.friendsandaargang.homepage.modle.AllSearch;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.TimeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/5/17.
 */

public class SearchAdapter extends SectionedBaseAdapter implements View.OnClickListener {


    private List<String> titlelist;
    private List<AllSearch.ContentBean.PostBean> postBeans;
    private List<AllSearch.ContentBean.QaBean> qaBeans;
    private List<AllSearch.ContentBean.UserBean> userBeans;
    private Context context;
    private String contents;

    private SparseArray<View> viewArray;


    public SearchAdapter(List<AllSearch.ContentBean.PostBean> postBeans
            , List<AllSearch.ContentBean.QaBean> qaBeans
            , List<AllSearch.ContentBean.UserBean> userBeans
            , Context context
            , String contents
            , List<String> titlelist) {
        this.postBeans = postBeans;
        this.qaBeans = qaBeans;
        this.userBeans = userBeans;
        this.context = context;
        this.contents = contents;
        this.titlelist = titlelist;
        viewArray = new SparseArray<>();
        //initviews();
    }


    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        if (titlelist.size() > 0) {
            return titlelist.size();
        }
        return 0;
    }

    @Override
    public int getCountForSection(int section) {
        switch (section) {
            case 0:
                if (titlelist.get(0).equals("论坛")) {
                    if (postBeans.size() > 0) {
                        if (postBeans.size() > 3) {
                            return 3;
                        }
                        return postBeans.size();
                    }
                } else if (titlelist.get(0).equals("问答")) {
                    if (qaBeans.size() > 0) {
                        if (qaBeans.size() > 3) {
                            return 3;
                        }
                        return qaBeans.size();
                    }
                } else if (titlelist.get(0).equals("用户")) {
                    if (userBeans.size() > 0) {
                        if (userBeans.size() > 3) {
                            return 3;
                        }
                        return userBeans.size();
                    }
                }
                break;
            case 1:
                if (titlelist.get(1).equals("问答")) {
                    if (qaBeans.size() > 0) {
                        if (qaBeans.size() > 3) {
                            return 3;
                        }
                        return qaBeans.size();
                    }
                } else if (titlelist.get(1).equals("用户")) {
                    if (userBeans.size() > 0) {
                        if (userBeans.size() > 3) {
                            return 3;
                        }
                        return userBeans.size();
                    }
                }
                break;
            case 2:
                if (userBeans.size() > 0) {
                    if (userBeans.size() > 3) {
                        return 3;
                    }
                    return userBeans.size();
                }
                break;
        }
        return 0;

    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ViewHodlerPost viewHodlerPost = null;
        ViewHodlerQa viewHodlerQa = null;
        ViewHodlerUser viewHodlerUser = null;

        switch (section) {
            //论坛
            case 0:
                //判断是论坛   问答  还是用户
                if (titlelist.get(0).equals("论坛")) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_post, null);
                    viewHodlerPost = new ViewHodlerPost(convertView);
                    convertView.setTag(viewHodlerPost);
                    viewArray.put(section, convertView);
                    //没有图片  与是话题时  不显示图片
                    if (postBeans.get(position).getTypeDesc().equals("话题")) {
                        viewHodlerPost.imageView.setVisibility(View.GONE);
                        viewHodlerPost.content.setVisibility(View.VISIBLE);
                        viewHodlerPost.title.setVisibility(View.VISIBLE);
                        viewHodlerPost.title.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getTitle(), contents));
                        viewHodlerPost.content.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getContent(), contents));
                    }
                    //是帖子
                    else if (postBeans.get(position).getTypeDesc().equals("帖子")) {
                        //没有图片就隐藏图片 显示标题与内容
                        if (postBeans.get(position).getAttachmentList().size() == 0) {
                            viewHodlerPost.imageView.setVisibility(View.GONE);
                            viewHodlerPost.title.setVisibility(View.VISIBLE);
                            viewHodlerPost.content.setVisibility(View.VISIBLE);

                            viewHodlerPost.title.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getTitle(), contents));
                            viewHodlerPost.content.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getContent(), contents));
                            viewHodlerPost.content.setTextColor(context.getResources().getColor(R.color.color_gray3));
                        } else {
                            viewHodlerPost.imageView.setVisibility(View.VISIBLE);
                            GlideManager.loadImage(context
                                    , postBeans.get(position).getAttachmentList().get(0).getAttachmentUrl()
                                    , R.drawable.ic_zan
                                    , viewHodlerPost.imageView);
                            viewHodlerPost.title.setVisibility(View.VISIBLE);
                            viewHodlerPost.title.setText(TextUtils.getHighLightKeyWord(Color.RED, postBeans.get(position).getTitle(), contents));
                            viewHodlerPost.content.setVisibility(View.VISIBLE);
                            viewHodlerPost.content.setText("");

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
                    viewHodlerPost.numer.setText(postBeans.get(position).getReadNumber());

                    Long time = TimeUtil.timeStrToSecond(postBeans.get(position).getPostDate());
                    viewHodlerPost.time.setText(TimeUtil.getSpaceTime(time));
                    if (position == 2) {
                        viewHodlerPost.luntab_lay.setVisibility(View.VISIBLE);
                        viewHodlerPost.luntab_lay.setOnClickListener(this);
                    }


                } else if (titlelist.get(0).equals("问答")) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_qa, null);
                    viewHodlerQa = new ViewHodlerQa(convertView);
                    convertView.setTag(viewHodlerQa);
                    viewArray.put(section, convertView);
                    GlideManager.loadImage(context, qaBeans.get(position).getAvatar()
                            , R.drawable.user, viewHodlerQa.headImage);
                    switch (qaBeans.get(position).getGroupType()) {
                        case 1:
                            viewHodlerQa.rayFufei.setVisibility(View.VISIBLE);
                            viewHodlerQa.tvDan.setVisibility(View.GONE);
                            break;
                        case 2:
                        case 3:
                            viewHodlerQa.rayFufei.setVisibility(View.GONE);
                            viewHodlerQa.tvDan.setVisibility(View.VISIBLE);
                            viewHodlerQa.tvZy.setVisibility(View.GONE);
                            viewHodlerQa.tvNumer.setVisibility(View.GONE);
                            break;
                    }

                    viewHodlerQa.tvXx.setText("立即查看");
                    viewHodlerQa.tvContent.setText(qaBeans.get(position).getTitle());
                    viewHodlerQa.tvNumer.setText(qaBeans.get(position).getReplyNumber());
                    viewHodlerQa.tvTitle.setText(TextUtils.getHighLightKeyWord(Color.RED, qaBeans.get(position).getViewerNickname(), contents));
                    viewHodlerQa.tvZy.setText(TextUtils.getHighLightKeyWord(Color.RED, qaBeans.get(position).getBigshotDesc(), contents));
                    viewHodlerQa.tvContent.setText(TextUtils.getHighLightKeyWord(Color.RED, qaBeans.get(position).getTitle(), contents));
                    viewHodlerQa.tvDan.setText("已解决" + qaBeans.get(position).getReplyNumber() + "个答案");

                    if (position == 2) {
                        viewHodlerQa.rayQa.setVisibility(View.VISIBLE);
                        viewHodlerQa.rayQa.setOnClickListener(this);
                    }
                } else if (titlelist.get(0).equals("用户")) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, null);
                    viewHodlerUser = new ViewHodlerUser(convertView);
                    convertView.setTag(viewHodlerUser);
                    viewArray.put(section, convertView);


                    //用户头像
                    GlideManager.loadImage(context, userBeans.get(position).getAvatar(), R.drawable.logo1, viewHodlerUser.userImg);
                    //标题栏与签名
                    viewHodlerUser.userName.setText(TextUtils.getHighLightKeyWord(Color.RED, userBeans.get(position).getNickname(), contents));
                    viewHodlerUser.userLeave.setText(userBeans.get(position).getLevel() + "");
                    if (userBeans.get(position).getSignature() != null) {
                        viewHodlerUser.userContent.setText(TextUtils.getHighLightKeyWord(Color.RED, userBeans.get(position).getSignature(), contents));
                    } else {
                        viewHodlerUser.userContent.setText("暂无签名");
                    }

                    if (position == 2) {
                        viewHodlerUser.userlay.setVisibility(View.VISIBLE);
                        viewHodlerUser.userlay.setOnClickListener(this);
                    }
                }


                break;
            //问答
            case 1:
                if (titlelist.get(1).equals("问答")) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_qa, null);
                    viewHodlerQa = new ViewHodlerQa(convertView);
                    convertView.setTag(viewHodlerQa);
                    viewArray.put(section, convertView);
                    GlideManager.loadImage(context, qaBeans.get(position).getAvatar(), R.drawable.logo1, viewHodlerQa.headImage);

                    switch (qaBeans.get(position).getGroupType()) {
                        case 1:
                            viewHodlerQa.rayFufei.setVisibility(View.VISIBLE);
                            viewHodlerQa.tvDan.setVisibility(View.GONE);
                            break;
                        case 2:
                            viewHodlerQa.rayFufei.setVisibility(View.GONE);
                            viewHodlerQa.tvDan.setVisibility(View.VISIBLE);
                            viewHodlerQa.tvZy.setVisibility(View.GONE);
                            viewHodlerQa.tvNumer.setVisibility(View.GONE);
                        case 3:
                            break;
                    }
                    viewHodlerQa.tvXx.setText("立即查看");
                    viewHodlerQa.tvContent.setText(qaBeans.get(position).getTitle());
                    viewHodlerQa.tvNumer.setText(qaBeans.get(position).getReplyNumber());
                    viewHodlerQa.tvTitle.setText(TextUtils.getHighLightKeyWord(Color.RED, qaBeans.get(position).getViewerNickname(), contents));
                    viewHodlerQa.tvZy.setText(TextUtils.getHighLightKeyWord(Color.RED, qaBeans.get(position).getBigshotDesc(), contents));
                    viewHodlerQa.tvContent.setText(TextUtils.getHighLightKeyWord(Color.RED, qaBeans.get(position).getTitle(), contents));
                    viewHodlerQa.tvDan.setText("已解决" + qaBeans.get(position).getReplyNumber() + "个答案");

                    if (position == 2) {
                        viewHodlerQa.rayQa.setVisibility(View.VISIBLE);
                        viewHodlerQa.rayQa.setOnClickListener(this);
                    }
                } else if (titlelist.get(1).equals("用户")) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, null);
                    viewHodlerUser = new ViewHodlerUser(convertView);
                    convertView.setTag(viewHodlerUser);
                    viewArray.put(section, convertView);


                    GlideManager.loadImage(context, userBeans.get(position).getAvatar(), R.drawable.user, viewHodlerUser.userImg);
                    viewHodlerUser.userName.setText(TextUtils.getHighLightKeyWord(Color.RED, userBeans.get(position).getNickname(), contents));
                    viewHodlerUser.userLeave.setText(userBeans.get(position).getLevel() + "");
                    if (android.text.TextUtils.isEmpty(userBeans.get(position).getSignature())) {
                        viewHodlerUser.userContent.setText("暂无签名");
                    } else {
                        viewHodlerUser.userContent.setText(TextUtils.getHighLightKeyWord(Color.RED, userBeans.get(position).getSignature(), contents));
                    }

                    if (position == 2) {
                        viewHodlerUser.userlay.setVisibility(View.VISIBLE);
                        viewHodlerUser.userlay.setOnClickListener(this);
                    }
                }
                break;
            //用户
            case 2:
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, null);
                viewHodlerUser = new ViewHodlerUser(convertView);
                convertView.setTag(viewHodlerUser);
                viewArray.put(section, convertView);
                GlideManager.loadImage(context, userBeans.get(position).getAvatar(), R.drawable.logo1, viewHodlerUser.userImg);
                viewHodlerUser.userName.setText(TextUtils.getHighLightKeyWord(Color.RED, userBeans.get(position).getNickname(), contents));
                viewHodlerUser.userLeave.setText(userBeans.get(position).getLevel() + "");


                if (android.text.TextUtils.isEmpty(userBeans.get(position).getSignature())) {
                    viewHodlerUser.userContent.setText("暂无签名");
                } else {
                    viewHodlerUser.userContent.setText(TextUtils.getHighLightKeyWord(Color.RED, userBeans.get(position).getSignature(), contents));
                }


                if (position == 2) {
                    viewHodlerUser.userlay.setVisibility(View.VISIBLE);
                    viewHodlerUser.userlay.setOnClickListener(this);
                }
                break;
        }
        return convertView;
    }


    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.listview_head, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.xingqu_text)).setText(titlelist.get(section));
        return layout;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.luntan_ck_lay:
                intent = new Intent(context, MorePostActivity.class);
                intent.putExtra("content", contents);
                //在这边进去不需要显示查看更多
                intent.putExtra("typePost", "typePost");
                intent.putExtra("", "");
                context.startActivity(intent);

                break;
            case R.id.qa_ck_lay:
                intent = new Intent(context, MoreQaActivity.class);
                intent.putExtra("content", contents);
                intent.putExtra("typePost", "typePost");
                context.startActivity(intent);
                break;
            case R.id.user_ck_lay:
                intent = new Intent(context, MoreUserActivity.class);
                //在这边进去不需要显示查看更多
                intent.putExtra("content", contents);
                intent.putExtra("typePost", "typePost");
                context.startActivity(intent);
                break;
        }
    }


    /**
     * 单张  置顶
     */

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


    /**
     * 搜索问答
     */
    public class ViewHodlerQa {
        @BindView(R.id.qa_image)
        CircleImageView headImage;
        @BindView(R.id.qa_title_tv)
        TextView tvTitle;
        @BindView(R.id.qa_zy_tv)
        TextView tvZy;
        @BindView(R.id.qa_content_tv)
        TextView tvContent;
        @BindView(R.id.qa_xx_lay)
        LinearLayout layXx;
        @BindView(R.id.qa_xx_tv)
        TextView tvXx;
        @BindView(R.id.qa_numer_tv)
        TextView tvNumer;
        @BindView(R.id.qa_ck_lay)
        RelativeLayout rayQa;
        @BindView(R.id.qa_money_tv)
        TextView tvMoney;
        @BindView(R.id.qa_daan_tv)
        TextView tvDan;
        @BindView(R.id.dakai_fufei_ray)
        RelativeLayout rayFufei;

        public ViewHodlerQa(View itemView) {
            ButterKnife.bind(this, itemView);
        }


    }


    /**
     * 搜索用户item
     */
    public class ViewHodlerUser {
        @BindView(R.id.search_user_img)
        CircleImageView userImg;
        @BindView(R.id.search_user_name)
        TextView userName;
        @BindView(R.id.search_user_leave)
        TextView userLeave;
        @BindView(R.id.search_user_content)
        TextView userContent;
        @BindView(R.id.user_ck_lay)
        RelativeLayout userlay;


        public ViewHodlerUser(View itemView) {
            ButterKnife.bind(this, itemView);
        }


    }

}
