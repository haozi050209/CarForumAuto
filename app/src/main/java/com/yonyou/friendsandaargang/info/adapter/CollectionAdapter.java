package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/3/2.
 */

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;


    public CollectionAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyler_collection_item_one, parent,false);

        return new ViewHodlerOne(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHodlerOne){
//            ((ViewHodlerOne) holder).title.setText("");


        }
    }


    @Override
    public int getItemCount() {
        return 5;
    }


    public class ViewHodlerOne extends RecyclerView.ViewHolder {
        @BindView(R.id.collection_lable_one)
        TextView lable;
        @BindView(R.id.collection_source_one)
        TextView source;
        @BindView(R.id.collection_title_one)
        TextView title;
        @BindView(R.id.collection_tiem_one)
        TextView time;
        @BindView(R.id.collection_number_one)
        TextView number;

        public ViewHodlerOne(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class ViewHodlerTwo extends RecyclerView.ViewHolder {

        public ViewHodlerTwo(View itemView) {
            super(itemView);
        }
    }

    public class ViewHodlerTherr extends RecyclerView.ViewHolder {

        public ViewHodlerTherr(View itemView) {
            super(itemView);
        }
    }
}
