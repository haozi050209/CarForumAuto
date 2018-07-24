package com.yonyou.friendsandaargang.forum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.bean.EssenceList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/1.
 */

public class EssenceFigureAdapter extends BaseAdapter {
    private Context context;
    private List<EssenceList.ContentBean.AttachmentListBean> list;

    private String url;

    public EssenceFigureAdapter(Context context, List<EssenceList.ContentBean.AttachmentListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return 3;
        }
        return 3;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_figure_item, null, false);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }


        if (list.size() > 2) {
            GlideManager.loadImage(context
                    , list.get(position).getAttachmentUrl()
                    , R.drawable.ic_zan
                    , viewHodler.figure_image);
        } else if (list.size() > 1) {
            GlideManager.loadImage(context
                    , list.get(position).getAttachmentUrl()
                    , R.drawable.ic_zan
                    , viewHodler.figure_image);
        }


        return convertView;
    }


    public class ViewHodler {
        @BindView(R.id.figure_image)
        ImageView figure_image;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
