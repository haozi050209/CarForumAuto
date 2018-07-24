package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
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
import com.yonyou.friendsandaargang.homepage.adapter.QaNavigationbarAdapter;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.homepage.modle.QaMainPageBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.HotListAdapter;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/2/8.
 * <p>
 * <p>
 * 问答专区
 */

public class QaActivity extends BaseActivity implements
        OnRefreshListener
        , AdapterView.OnItemClickListener
        , OnItemClickListener, OnLoadMoreListener {

    @BindView(R.id.qa_refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.recylerview)
    RecyclerView headRecyler;


    @BindView(R.id.recylerview_list)
    RecyclerView recyclerView;
    @BindView(R.id.no_tv)
    TextView textView;

    @BindView(R.id.search_text_qa)
    TextView tvSearch;


    //布局管理器
    private LinearLayoutManager manager, manager1;
    private int pageSize = 8;
    private int pageNo = 1;
    private String uesrId;
    private boolean isLogin;
    //导航栏适配器
    private QaNavigationbarAdapter adapter;
    private List<QaMainPageBean.ContentBean> qaList;
    private List<HotListBean.ContentBean> hotList;
    private HotListAdapter hotListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        ButterKnife.bind(this);
        initviews();
        getForumOnMainPage();


    }

    private void initviews() {
        tvSearch.setText("请输入搜索内容");
        isLogin = SPTool.getBoolean(mContext, Constants.ISLOGIN);
        uesrId = SPTool.getContent(this, Constants.USER_ID);

        initHeadRecyler();
        initRecyler();
        initRefresh();
    }


    private void initHeadRecyler() {
        manager1 = new LinearLayoutManager(mContext);
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        headRecyler.setLayoutManager(manager1);
    }


    /**
     * recyler和加载更多
     */
    private void initRecyler() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        hotList = new ArrayList<>();

    }


    /**
     * 加载更多
     */
    private void initRefresh() {
        refreshlayout.setRefreshHeader(new ClassicsHeader(QaActivity.this).setSpinnerStyle(FixedBehind));
        refreshlayout.setRefreshFooter(new ClassicsFooter(QaActivity.this).setSpinnerStyle(FixedBehind));
        refreshlayout.setOnRefreshListener(this);
        refreshlayout.setOnLoadMoreListener(this);
    }


    @OnClick({R.id.back_lay, R.id.rl_rewards, R.id.rl_askexperts, R.id.search_text_qa})
    public void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_lay:
                finish();
                break;
            //发布悬赏问答
            case R.id.rl_rewards:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                ActivityManger.skipActivity(mContext, PutToQaActivity.class);
                break;
            //咨询专家
            case R.id.rl_askexperts:
                intent = new Intent(mContext, MavinActivity.class);
                intent.putExtra("form", "");
                startActivity(intent);
                break;
            case R.id.search_text_qa:
                intent = new Intent(mContext, QaSearchActivity.class);
                intent.putExtra("form", "QaMain");
                startActivity(intent);
                break;

        }
    }


    /**
     * 获取问答专区首页专区列表(中间的3个)
     */
    private void getForumOnMainPage() {
        Call<QaMainPageBean> call = communityService().getForumOnMainPage(uesrId);
        NetUtils<QaMainPageBean> netUtils = new NetUtils<QaMainPageBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<QaMainPageBean>() {
            @Override
            public void onResponseCallback(QaMainPageBean qaMainPageBean) {
                if (qaMainPageBean.getReturnCode() != 0 && !qaMainPageBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, qaMainPageBean.getReturnMsg()).show();
                    return;
                }
                getNavigationBarData(qaMainPageBean);
                getListHot();
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    /**
     * 添加导航栏
     */
    private void getNavigationBarData(QaMainPageBean qaMainPageBean) {
        qaList = new ArrayList<>();
        qaList.add(new QaMainPageBean.ContentBean(""
                , "0"
                , "大咖专区"
                , R.drawable.bigshot + ""
                , 0));
        qaList.addAll(qaMainPageBean.getContent());
        qaList.add(new QaMainPageBean.ContentBean(""
                , "0"
                , "全部专区"
                , R.drawable.all + ""
                , 0));

        adapter = new QaNavigationbarAdapter(mContext, qaList);
        headRecyler.setAdapter(adapter);
        adapter.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
                switch (position) {
                    case 0:
                        ActivityManger.skipActivity(mContext, BigShotActivity.class);
                        break;
                    case 1:
                    case 2:
                    case 3:
                        intent = new Intent(mContext, SpecialAreaDetalisActivity.class);
                        intent.putExtra(Constants.TITLE, qaList.get(position).getForumName());
                        intent.putExtra(Constants.FORUMID, qaList.get(position).getForumId());
                        startActivity(intent);
                        break;
                    //全部专区
                    case 4:
                        intent = new Intent(mContext, AllActivity.class);
                        intent.putExtra(Constants.TITLE, "全部专区");
                        startActivity(intent);
                        break;
                }
            }
        });

    }


    /**
     * 添加热门回答
     */
    private void getListHot() {
        Call<HotListBean> call = communityService().getListOnQAMainPage(uesrId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<HotListBean>(mContext, new HttpCallBackImpl<HotListBean>() {
            @Override
            public void onResponseCallback(HotListBean hotListBean) {
                if (hotListBean.getReturnCode() != 0 && !hotListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, hotListBean.getReturnMsg()).show();
                    return;
                }
                getHotData(hotListBean.getContent());
            }
        }));
    }


    /**
     * 数据处理
     */
    private void getHotData(List<HotListBean.ContentBean> hotListBean) {

        if (hotListBean.size() == 0) {
            if (pageNo == 1) {
                textView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                refreshlayout.setEnableLoadMore(false);
            } else {
                refreshlayout.finishLoadMoreWithNoMoreData();
            }
            refreshlayout.finishLoadMore();
            refreshlayout.finishRefresh();
            return;
        }
        if (pageNo == 1) {
            hotList.clear();
            hotList.addAll(hotListBean);
            refreshlayout.finishRefresh();
            refreshlayout.setEnableLoadMore(true);
            hotListAdapter = new HotListAdapter(mContext, hotList);
            recyclerView.setAdapter(hotListAdapter);
        } else {
            hotList.addAll(hotListBean);
            refreshlayout.finishLoadMore();
            hotListAdapter.notifyDataSetChanged();
        }
        hotListAdapter.addOnItemClickListener(this);

    }


    /**
     * item  监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                ActivityManger.skipActivity(mContext, BigShotActivity.class);
                break;
            case 1:
            case 2:
            case 3:
                intent = new Intent(mContext, SpecialAreaDetalisActivity.class);
                intent.putExtra(Constants.TITLE, qaList.get(position).getForumName());
                intent.putExtra(Constants.FORUMID, qaList.get(position).getForumId());
                startActivity(intent);
                break;
            //全部专区
            case 4:
                intent = new Intent(mContext, AllActivity.class);
                intent.putExtra(Constants.TITLE, "全部专区");
                startActivity(intent);
                break;
        }
    }


    /**
     * recylerview  监听
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, QaDetalisActivity.class);
        intent.putExtra(Constants.POSTID, hotList.get(position).getPostId());
        startActivity(intent);
    }


    //下啦加载
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getListHot();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getListHot();
    }
}
