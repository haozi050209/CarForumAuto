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
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.homepage.adapter.SearchPostAdapter;
import com.yonyou.friendsandaargang.homepage.modle.SearchPostBean;
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
import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.Scale;

/**
 * Created by shibing on 18/5/18.
 */

public class MorePostActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.moreuser_post)
    MyListView listView;
    @BindView(R.id.reshlayout)
    SmartRefreshLayout reshlayout;


    private String string;
    private String userId;

    private List<SearchPostBean.ContentBean> listPost;
    private List<SearchPostBean.ContentBean> listbean;
    private SearchPostAdapter adapter;
    private int pageNo = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morepost);
        ButterKnife.bind(this);
        initviews();
        getSearchResult4Post();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("查看更多论坛");
        string = getIntent().getStringExtra("content");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        reshlayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(Scale));
        reshlayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(Scale));
        reshlayout.setOnLoadMoreListener(this);
        reshlayout.setOnRefreshListener(this);
        listPost = new ArrayList<>();

    }


    /**
     * 搜索帖子
     */

    private void getSearchResult4Post() {
        Call<SearchPostBean> call = communityService().getSearchResult4Post(string, userId, pageNo);
        NetUtils<SearchPostBean> netUtils = new NetUtils<SearchPostBean>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<SearchPostBean>() {
            @Override
            public void onResponseCallback(SearchPostBean searchPostBean) {
                if (searchPostBean.getReturnCode() != 0 && !searchPostBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, searchPostBean.getReturnMsg().toString()).show();
                    return;
                }
                getlistPost(searchPostBean.getContent());
            }

            @Override
            public void onFailureCallback() {
                reshlayout.finishLoadMore();
                reshlayout.finishRefresh();
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }

    public void getlistPost(List<SearchPostBean.ContentBean> listbean) {


        if (listbean.size() == 0) {
            if (pageNo != 1) {
                reshlayout.finishLoadMoreWithNoMoreData();
            }
            return;
        }
        if (pageNo == 1) {
            listPost.clear();
            listPost.addAll(listbean);
            reshlayout.finishRefresh();
            adapter = new SearchPostAdapter(mContext, listPost, string, "typepost");
            listView.setAdapter(adapter);
        } else {
            listPost.addAll(listbean);
            reshlayout.finishLoadMore();
            adapter.notifyDataSetChanged(listPost);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, PostDetailsActivity.class);
                intent.putExtra(Constants.TITLE, listPost.get(position).getTypeDesc());
                intent.putExtra(Constants.POSTID, listPost.get(position).getPostId());
                startActivity(intent);
            }
        });
    }

    /**
     * 加载更多
     *
     * @param refreshlayout
     */


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getSearchResult4Post();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getSearchResult4Post();
    }
}
