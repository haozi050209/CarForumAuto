package com.yonyou.friendsandaargang.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.adapter.AllQaAdapter;
import com.yonyou.friendsandaargang.homepage.adapter.SearchPostAdapter;
import com.yonyou.friendsandaargang.homepage.modle.SearchQa;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.BigshotQuestionActivity;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.QaDetalisActivity;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/5/18.
 */

public class MoreQaActivity extends BaseActivity implements OnRefreshListener, AdapterView.OnItemClickListener, OnLoadMoreListener {


    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.moreqa_list)
    ListView listView;


    private List<SearchQa.ContentBean> listQa;
    private String string;
    private String userId;
    private int pageNo = 1;
    private AllQaAdapter adapter;
    private String typePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreqa);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("查看更多问答");

        string = getIntent().getStringExtra("content");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        typePost = getIntent().getStringExtra("typePost");
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        //加载更多
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        listQa = new ArrayList<>();

        getSearchResult4Qa(string);
    }


    /**
     * 搜索问答
     */
    private void getSearchResult4Qa(final String content) {
        Call<SearchQa> call = communityService().getSearchResult4QA(content, userId, pageNo);
        NetUtils<SearchQa> netUtils = new NetUtils<SearchQa>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<SearchQa>() {
            @Override
            public void onResponseCallback(SearchQa searchQa) {
                if (searchQa.getReturnCode() != 0 && !searchQa.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, searchQa.getReturnMsg()).show();
                    return;
                }
                SearchQaData(searchQa.getContent());
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    private void SearchQaData(List<SearchQa.ContentBean> searchQa) {


        if (searchQa.size() == 0) {
            if (pageNo != 1) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            return;
        }
        if (pageNo == 1) {
            listQa.clear();
            listQa.addAll(searchQa);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            adapter = new AllQaAdapter(mContext, searchQa, string, typePost);
            listView.setAdapter(adapter);
        } else {
            listQa.addAll(searchQa);
            refreshLayout.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getSearchResult4Qa(string);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getSearchResult4Qa(string);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (listQa.get(position).getGroupType()) {
            case 1:
                intent = new Intent(mContext, BigshotQuestionActivity.class);
                intent.putExtra(Constants.POSTID, listQa.get(position).getPostId());
                intent.putExtra("from", "search");
                startActivity(intent);
                break;
            case 2:
            case 3:
                intent = new Intent(mContext, QaDetalisActivity.class);
                intent.putExtra(Constants.POSTID, listQa.get(position).getPostId());
                startActivity(intent);
                break;
        }

    }


}
