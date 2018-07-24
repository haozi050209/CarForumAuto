package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.guide.activity.MainActivity;
import com.yonyou.friendsandaargang.homepage.modle.BigshotAnwserListBean;
import com.yonyou.friendsandaargang.homepage.modle.MavinListBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.BigshotAnwserListAdapter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.MavinAdapter;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/6/6.
 */

public class BigshotDetalisActivity extends BaseActivity implements AdapterView.OnItemClickListener
        , OnItemClickListener, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.onbigshot_no_dazhu_tv)
    TextView tvNoDazhu;
    @BindView(R.id.onbigshot_wend_tv)
    TextView tvNoWend;
    @BindView(R.id.onbigshot_dazhu_list)
    MyListView dazhuList;
    @BindView(R.id.onbigshot_wend_list)
    RecyclerView wendList;
    @BindView(R.id.smartrefresh)
    SmartRefreshLayout refreshLayout;

    private String title;
    private String userId;
    private String forumId;
    private LinearLayoutManager manager;
    private int pageNo = 1;
    private int pageSize = 8;


    private MavinAdapter mavinAdapter;
    private List<MavinListBean.ContentBean> list;
    private List<BigshotAnwserListBean.ContentBean> beanList;
    private BigshotAnwserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigshotdetalis);
        ButterKnife.bind(this);
        initviews();
        initRefresh();
        initRecyler();
        getPopleAnswer();
    }

    private void initviews() {
        title = getIntent().getStringExtra(Constants.TITLE);
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        forumId = getIntent().getStringExtra(Constants.FORUMID);
        getTitleBar();
        setTitleText(title);
    }


    private void initRecyler() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        wendList.setLayoutManager(manager);
        wendList.setNestedScrollingEnabled(false);
    }


    private void initRefresh() {
        beanList = new ArrayList<>();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }


    /**
     * 获取单个大咖页人气答主
     */
    private void getPopleAnswer() {
        Call<MavinListBean> call = communityService().getHotBigshotListOnArea(userId, forumId
                , 1, 100);
        NetUtils<MavinListBean> netUtils = new NetUtils<MavinListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<MavinListBean>() {
            @Override
            public void onResponseCallback(MavinListBean mavinListBean) {
                if (mavinListBean.getReturnCode() != 0 && !mavinListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, mavinListBean.getReturnMsg()).show();
                    return;
                }
                PopleAnswerData(mavinListBean);

            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    private void PopleAnswerData(MavinListBean mavinListBean) {
        list = mavinListBean.getContent();
        if (list.size() == 0) {
            tvNoDazhu.setVisibility(View.VISIBLE);
            dazhuList.setVisibility(View.GONE);
            return;
        }
        dazhuList.setVisibility(View.VISIBLE);
        mavinAdapter = new MavinAdapter(mContext, list, "", "onbigshot");
        dazhuList.setAdapter(mavinAdapter);
        dazhuList.setOnItemClickListener(this);
        View view = LayoutInflater.from(mContext).inflate(R.layout.qa_footer, null);
        TextView textView = view.findViewById(R.id.qa_footer_tv);
        LinearLayout layout = view.findViewById(R.id.lay_qa_footer);
        textView.setText("更多答主");
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_jiantou, 0);
        if (list.size() > 2) {
            dazhuList.addFooterView(view);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MavinActivity.class);
                    intent.putExtra(Constants.FORUMID, forumId);
                    intent.putExtra("form", "moreOnArea");
                    startActivity(intent);
                }
            });
        }

        getOnBigshotArea();
    }


    @OnClick(R.id.search_lay)
    public void OnClick() {
        //单个大咖内搜索问答 答主
        Intent intent = new Intent(mContext, QaSearchActivity.class);
        intent.putExtra("form", "OnBigshotArea");
        intent.putExtra(Constants.FORUMID, forumId);
        startActivity(intent);
    }


    /**
     * 单个大咖专区热门问答
     */
    private void getOnBigshotArea() {
        Call<BigshotAnwserListBean> call = communityService().getHotListOnBigshotArea(userId, forumId
                , pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<BigshotAnwserListBean>(mContext, new HttpCallBackImpl<BigshotAnwserListBean>() {
            @Override
            public void onResponseCallback(BigshotAnwserListBean listBean) {
                if (listBean.getReturnCode() != 0 && !listBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, listBean.getReturnMsg()).show();
                    return;
                }
                getOnBigshotAreaData(listBean.getContent());
            }
        }));

    }

    private void getOnBigshotAreaData(List<BigshotAnwserListBean.ContentBean> contentBeans) {
        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                tvNoWend.setVisibility(View.VISIBLE);
                wendList.setVisibility(View.GONE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            return;
        }

        if (pageNo == 1) {
            beanList.clear();
            beanList.addAll(contentBeans);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            adapter = new BigshotAnwserListAdapter(mContext, beanList, "bigshotDetalis");
            wendList.setAdapter(adapter);
        } else {
            beanList.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
        adapter.addOnItemClickListener(this);

    }

    /**
     * 答主监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, BigShotPutToActivity.class);
        intent.putExtra(Constants.BIGSHOTID, list.get(position).getUserId());
        startActivity(intent);

    }

    /**
     * 问答监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, BigshotQuestionActivity.class);
        intent.putExtra(Constants.POSTID, beanList.get(position).getPostId());
        startActivity(intent);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getOnBigshotArea();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getOnBigshotArea();
    }
}
