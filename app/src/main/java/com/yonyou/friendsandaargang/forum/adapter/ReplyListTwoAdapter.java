package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.bean.ReplyList;
import com.yonyou.friendsandaargang.utils.TextTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class ReplyListTwoAdapter extends BaseAdapter {
    private Context context;
    private List<ReplyList.ContentBean.ReplyListBean> list;
    private String mainUserId;
    private String directUserId;

    public ReplyListTwoAdapter(Context context, List<ReplyList.ContentBean.ReplyListBean> list) {
        this.list = list;
        this.context = context;
    }


    protected void notifyDataSetChanged(List<ReplyList.ContentBean.ReplyListBean> list) {
        super.notifyDataSetChanged();
        this.list = list;
    }

    @Override
    public int getCount() {
       /* if (list != null) {
            return list.size();
        }*/
        return 1;
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
        /*if (list != null) {
            TextTool.getBuilder("")
                    .setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                    .append(list.get(position).getNickname() + ":  ")
                    .setForegroundColor(context.getResources().getColor(R.color.color_blue))
                    .append(list.get(position).getContent())
                    .into(viewHodler.list_relpy_two_title);
        }*/
        if (list != null) {
            if (list.get(position).getDirectRepliedNickname() == null
                    && list.get(position).getDirectRepliedContent() == null) {
                mainUserId = list.get(position).getReplierId();
                TextTool.getBuilder("")
                        .setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                        .append(list.get(position).getNickname() + ":  ")
                        .setForegroundColor(context.getResources().getColor(R.color.color_blue))
                        .append(list.get(position).getContent())
                        .into(viewHodler.list_relpy_two_title);


            } else {
                directUserId = list.get(position).getDirectRepliedUserId();
                TextTool.getBuilder("")
                        .setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                        .append(list.get(position).getNickname())
                        .setForegroundColor(context.getResources().getColor(R.color.color_blue))
                        .append("回复")
                        .append(list.get(position).getDirectRepliedNickname() + ":  ")
                        .setForegroundColor(context.getResources().getColor(R.color.color_blue))
                        .append(list.get(position).getContent())
                        .into(viewHodler.list_relpy_two_title);
            }
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
