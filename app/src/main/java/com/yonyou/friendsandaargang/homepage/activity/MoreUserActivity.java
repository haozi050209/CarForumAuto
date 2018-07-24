package com.yonyou.friendsandaargang.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.adapter.AllUserAdapter;
import com.yonyou.friendsandaargang.homepage.modle.SearchUser;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/5/18.
 */

public class MoreUserActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "MoreUserActivity";
    @BindView(R.id.list_moreuser)
    MyListView list;
    @BindView(R.id.reshlayout)
    SmartRefreshLayout refreshLayout;


    private String type;

    private List<SearchUser.ContentBean> beanList;

    private String userId;
    private String string;
    private AllUserAdapter adapter;
    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreuser);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("查看更多用户");
        if (getIntent().getStringExtra("homeType") != null) {
            type = getIntent().getStringExtra("homeType");
        }
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        if (getIntent().getStringExtra("content") != null) {
            string = getIntent().getStringExtra("content");
        }
        initRefresh();
        getSearchResult4User();
    }


    /**
     * 加载更多
     */
    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        beanList = new ArrayList<>();
    }


    /**
     * 查看更多问答
     */
    private void getSearchResult4User() {
        Call<SearchUser> call = communityService().getSearchResult4User(string, userId, pageNo);
        NetUtils<SearchUser> netUtils = new NetUtils<SearchUser>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<SearchUser>() {
            @Override
            public void onResponseCallback(SearchUser searchUser) {
                if (searchUser.getReturnCode() != 0 && !searchUser.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, searchUser.getReturnMsg().toString()).show();
                    return;
                }
                getListUser(searchUser.getContent());
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    public void getListUser(List<SearchUser.ContentBean> userList) {

        if (userList.size() == 0) {
            if (pageNo != 1) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishLoadMore(true);
            refreshLayout.finishRefresh(true);
            return;
        }

        if (pageNo == 1) {
            beanList.clear();
            beanList.addAll(userList);
            refreshLayout.finishRefresh(true);
            refreshLayout.setEnableLoadMore(true);
            adapter = new AllUserAdapter(mContext, beanList, string, "type");
            list.setAdapter(adapter);
        } else {
            beanList.addAll(userList);
            refreshLayout.finishLoadMore(true);
            adapter.notifyDataSetChanged(beanList);


        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, beanList.get(position).getUserId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getSearchResult4User();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getSearchResult4User();
    }
}
