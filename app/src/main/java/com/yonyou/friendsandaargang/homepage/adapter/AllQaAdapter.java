package com.yonyou.friendsandaargang.homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.activity.MoreQaActivity;
import com.yonyou.friendsandaargang.homepage.modle.SearchQa;
import com.yonyou.friendsandaargang.homepage.modle.SearchUser;
import com.yonyou.friendsandaargang.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shibing on 18/3/1.
 */

public class AllQaAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<SearchQa.ContentBean> list;
    private String contents;
    private String type;


    public AllQaAdapter(Context context, List<SearchQa.ContentBean> list, String contents, String type) {
        this.context = context;
        this.list = list;
        this.contents = contents;
        this.type = type;
    }

    @Override
    public int getCount() {
        if (list != null) {

            if (type.equals("homeQa")) {
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
        ViewHodlerQa viewHodlerQa;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_qa, null, false);
            viewHodlerQa = new ViewHodlerQa(convertView);
            convertView.setTag(viewHodlerQa);
        } else {
            viewHodlerQa = (ViewHodlerQa) convertView.getTag();
        }


        GlideManager.loadImage(context, list.get(position).getAvatar()
                , R.drawable.user, viewHodlerQa.headImage);
        switch (list.get(position).getGroupType()) {
            case 1:
                viewHodlerQa.tvDan.setVisibility(View.GONE);
                viewHodlerQa.rayFufei.setVisibility(View.VISIBLE);
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
        viewHodlerQa.tvContent.setText(list.get(position).getTitle());
        viewHodlerQa.tvNumer.setText(list.get(position).getReplyNumber());
        viewHodlerQa.tvTitle.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getViewerNickname(), contents));
        viewHodlerQa.tvZy.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getBigshotDesc(), contents));
        viewHodlerQa.tvContent.setText(TextUtils.getHighLightKeyWord(Color.RED, list.get(position).getTitle(), contents));
        viewHodlerQa.tvDan.setText("已解决" + list.get(position).getReplyNumber() + "个答案");

        if (type.equals("homeQa") && position == 6) {
            viewHodlerQa.rayQa.setVisibility(View.VISIBLE);
            viewHodlerQa.rayQa.setOnClickListener(this);
        } else {
            viewHodlerQa.rayQa.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, MoreQaActivity.class);
        intent.putExtra("content", contents);
        intent.putExtra("typePost", "typePost");
        context.startActivity(intent);
    }


    /**
     * 问答
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


}
