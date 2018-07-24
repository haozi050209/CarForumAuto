package com.yonyou.friendsandaargang.forum.activirty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.adapter.MoreHuaTiAdapter;
import com.yonyou.friendsandaargang.forum.bean.HuaTi;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/3/6.
 */

public class MoreHautiActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.morehuati_list)
    ListView myListView;
    private MoreHuaTiAdapter moreHuaTiAdapter;
    List<HuaTi.ContentBean> contentBeans;
    @BindView(R.id.no_huati)
    TextView no_huati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morehauti);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("热门话题");
        getHuatiData();
    }


    /**
     * 获取热门话题
     */

    private void getHuatiData() {
        Call<HuaTi> call = communityService().getHotTopicList();
        call.enqueue(new NetRetrofitCallback<HuaTi>(mContext, new HttpCallBackImpl<HuaTi>() {
            @Override
            public void onResponseCallback(HuaTi model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initdatas(model);
            }
        }));
    }


    private void initdatas(HuaTi model) {
        contentBeans = model.getContent();
        if (contentBeans.size() == 0) {
            no_huati.setVisibility(View.VISIBLE);
            myListView.setVisibility(View.GONE);
            return;
        }
        moreHuaTiAdapter = new MoreHuaTiAdapter(this, contentBeans);
        myListView.setAdapter(moreHuaTiAdapter);
        myListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MoreHautiActivity.this, PostDetailsActivity.class);
        intent.putExtra(Constants.TITLE, "话题");
        intent.putExtra(Constants.POSTID, contentBeans.get(position).getPostId());
        startActivity(intent);
    }
}
