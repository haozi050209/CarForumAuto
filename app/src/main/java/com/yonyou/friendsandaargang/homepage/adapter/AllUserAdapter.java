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
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.activity.MoreUserActivity;
import com.yonyou.friendsandaargang.homepage.modle.AllBrand;
import com.yonyou.friendsandaargang.homepage.modle.AllSearch;
import com.yonyou.friendsandaargang.homepage.modle.SearchUser;
import com.yonyou.friendsandaargang.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class AllUserAdapter extends BaseAdapter {

    private Context context;
    private List<SearchUser.ContentBean> list;
    private String contents;
    private String homeUser;


    public AllUserAdapter(Context context, List<SearchUser.ContentBean> list, String contents, String homeUser) {
        this.context = context;
        this.list = list;
        this.homeUser = homeUser;
        this.contents = contents;
    }


    public void notifyDataSetChanged(List<SearchUser.ContentBean> list) {
        super.notifyDataSetChanged();
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            if (homeUser.equals("homeUser")) {
                if (list.size() == 8) {
                    return 7;
                } else {
                    return list.size();
                }
            }
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_user, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        Glide.with(context)
                .load(list.get(position).getAvatar())
                .error(R.drawable.user)
                .into(viewHodler.search_user_img);

        GlideManager.loadImage(context, list.get(position).getAvatar(), R.drawable.user, viewHodler.search_user_img);


        viewHodler.search_user_name.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getNickname(), contents));
        viewHodler.search_user_leave.setText(list.get(position).getLevel() + "");
        if (list.get(position).getSignature() != null) {
            viewHodler.search_user_content.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getSignature(), contents));
        } else {
            viewHodler.search_user_content.setText("暂无签名");
        }


        //如果是在首页搜索进来的
        if (homeUser.equals("homeUser")) {
            if (position == 6) {
                viewHodler.user_ck_lay.setVisibility(View.VISIBLE);
                viewHodler.user_ck_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MoreUserActivity.class);
                        intent.putExtra("content", contents);
                        intent.putExtra("homeType", "homeType");
                        context.startActivity(intent);
                    }
                });
            }
        } else {
            viewHodler.user_ck_lay.setVisibility(View.GONE);
        }

        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.search_user_img)
        CircleImageView search_user_img;
        @BindView(R.id.search_user_name)
        TextView search_user_name;
        @BindView(R.id.search_user_leave)
        TextView search_user_leave;
        @BindView(R.id.search_user_content)
        TextView search_user_content;
        @BindView(R.id.user_ck_lay)
        RelativeLayout user_ck_lay;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
