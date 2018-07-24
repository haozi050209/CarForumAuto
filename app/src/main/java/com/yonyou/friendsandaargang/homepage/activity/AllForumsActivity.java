package com.yonyou.friendsandaargang.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.homepage.adapter.AllForumAdapter;
import com.yonyou.friendsandaargang.homepage.modle.AllForums;
import com.yonyou.friendsandaargang.network.NetHttpUtil;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/4/9.
 */

public class AllForumsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.allforums_list)
    ListView allforums_list;
    private AllForumAdapter allForumAdapter;
    private List<AllForums.ContentBean> contentBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allforums_activity);
        ButterKnife.bind(this);
        initviews();
        setAllForumsData();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择论坛");
    }

    private void setAllForumsData() {
        Call<AllForums> call = communityService().getForumList();
        NetUtils<AllForums> netUtils = new NetUtils<AllForums>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<AllForums>() {
            @Override
            public void onResponseCallback(AllForums allForums) {
                if (allForums.getReturnCode() != 0 && !allForums.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, allForums.getReturnMsg()).show();
                    return;
                }
                AllForumData(allForums);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    private void AllForumData(AllForums allForums) {
        contentBeanList = allForums.getContent();
        allForumAdapter = new AllForumAdapter(mContext, contentBeanList);
        allforums_list.setAdapter(allForumAdapter);
        allforums_list.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("ForumName", contentBeanList.get(position).getForumName());
        intent.putExtra("ForumId", contentBeanList.get(position).getForumId());
        setResult(100, intent);
        finish();
    }
}
