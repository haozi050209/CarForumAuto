package com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.bean.ForumState;
import com.yonyou.friendsandaargang.guide.adapter.SectionedBaseAdapter;
import com.yonyou.friendsandaargang.homepage.modle.MavinListBean;
import com.yonyou.friendsandaargang.utils.TextUtils;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/2/8.
 */

public class MavinAdapter extends BaseAdapter {


    private Context context;
    private List<MavinListBean.ContentBean> mavinList;
    private String keyWord;
    private String form;

    public MavinAdapter(Context context, List<MavinListBean.ContentBean> mavinList
            , String keyWord
            , String form) {
        this.context = context;
        this.keyWord = keyWord;
        this.mavinList = mavinList;
        this.form = form;
    }

    @Override
    public int getCount() {
        if (android.text.TextUtils.isEmpty(form)) {
            if (mavinList != null) {
                return mavinList.size();
            }
        } else {
            if (mavinList.size() > 2) {
                return 3;
            }
            return mavinList.size();
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

        ViewHodler viewHodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_mavin_item, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        GlideManager.loadImage(context, mavinList.get(position).getAvatar(), R.drawable.user, viewHodler.imageView);

        if (!android.text.TextUtils.isEmpty(keyWord)) {
            viewHodler.tvName.setText(TextUtils.getHighLightKeyWord(Color.RED, mavinList.get(position).getNickname(), keyWord));
            viewHodler.tvContent.setText(TextUtils.getHighLightKeyWord(Color.RED, mavinList.get(position).getBigshotDesc(), keyWord));
        } else {
            viewHodler.tvName.setText(mavinList.get(position).getNickname());

            if (android.text.TextUtils.isEmpty(mavinList.get(position).getBigshotDesc())) {
                viewHodler.tvContent.setText("暂无大咖简介");
            } else {
                viewHodler.tvContent.setText(mavinList.get(position).getBigshotDesc());
            }

        }
        viewHodler.tvForum.setText(mavinList.get(position).getForumName());
        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.macin_item_image)
        CircleImageView imageView;
        @BindView(R.id.mavin_item_name)
        TextView tvName;
        @BindView(R.id.mavin_item_content)
        TextView tvContent;
        @BindView(R.id.mavin_item_forum)
        TextView tvForum;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }

    }


}
