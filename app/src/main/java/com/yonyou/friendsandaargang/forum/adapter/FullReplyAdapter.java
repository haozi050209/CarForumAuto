package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.bean.FullReply;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.utils.TextTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class FullReplyAdapter extends BaseAdapter {


    private Context context;
    private List<FullReply.ContentBean.ReplyListBean> list;
    private ClickableSpan span = null;
    private ClickableSpan spanTwo;
    private String mainUserId;
    private String directUserId;

    public FullReplyAdapter(Context context, List<FullReply.ContentBean.ReplyListBean> list) {
        this.list = list;
        this.context = context;

    }

    public void notifyDataSetChanged(List<FullReply.ContentBean.ReplyListBean> list) {
        super.notifyDataSetChanged();
        this.list = list;
        initviews();
    }


    private void initviews() {
        span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, mainUserId);
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(context.getResources().getColor(R.color.color_blue));
                ds.setUnderlineText(false);
            }
        };

        spanTwo = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, directUserId);
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(context.getResources().getColor(R.color.color_blue));
                ds.setUnderlineText(false);
            }
        };
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_relpy_item_two, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        if (list != null) {
            if (list.get(position).getDirectRepliedNickname() == null
                    && list.get(position).getDirectRepliedContent() == null) {

                mainUserId = list.get(position).getReplierId();

                TextTool.getBuilder("")
                        .setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                        .append(list.get(position).getNickname() + ":  ")
                        .setForegroundColor(context.getResources().getColor(R.color.color_blue))
                        .setClickSpan(span)
                        .append(list.get(position).getContent())
                        .into(viewHodler.list_relpy_two_title);


            } else {
                directUserId = list.get(position).getDirectRepliedUserId();
                TextTool.getBuilder("")
                        .setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                        .append(list.get(position).getNickname())
                        .setForegroundColor(context.getResources().getColor(R.color.color_blue))
                        .setClickSpan(span)
                        .append("回复")
                        .append(list.get(position).getDirectRepliedNickname() + ":  ")
                        .setClickSpan(spanTwo)
                        .setForegroundColor(context.getResources().getColor(R.color.color_blue))
                        .append(list.get(position).getContent())
                        .into(viewHodler.list_relpy_two_title);
            }



           /* viewHodler.list_relpy_two_title.setText(list.get(position).getNickname() + " :  ");
            viewHodler.list_relpy_two_content.setText(list.get(position).getContent() + "");*/
        }
        return convertView;
    }


    public class ViewHodler {


        @BindView(R.id.list_relpy_two_title)
        TextView list_relpy_two_title;
        @BindView(R.id.list_relpy_two_content)
        TextView list_relpy_two_content;


        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
