package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
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
import com.yonyou.friendsandaargang.homepage.modle.BigForumBean;
import com.yonyou.friendsandaargang.homepage.modle.BigshotAnwserListBean;
import com.yonyou.friendsandaargang.homepage.modle.MavinListBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.BigHeadAdapter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.BigshotAnwserListAdapter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.MavinAdapter;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
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

public class BigShotActivity extends BaseActivity implements OnItemClickListener, AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.big_recyler)
    RecyclerView recyclerView;
    @BindView(R.id.search_text_qa)
    TextView tvSearch;
    @BindView(R.id.bigshot_no_dazhu)
    TextView tvNoDazhu;
    @BindView(R.id.bigshot_list)
    MyListView dazhuList;
    @BindView(R.id.bigshot_no_wend)
    TextView tvNowenda;
    @BindView(R.id.bigshot_recler)
    RecyclerView wendaRecy;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;


    //导航栏
    private LinearLayoutManager manager;
    private BigHeadAdapter adapter;
    private List<BigForumBean.ContentBean> list;
    //用户id
    private String userId;
    //新晋答主
    private MavinAdapter mavinAdapter;
    private List<MavinListBean.ContentBean> listDazhu;
    //热门回答
    private LinearLayoutManager layoutManager;
    private List<BigshotAnwserListBean.ContentBean> listHot;
    private BigshotAnwserListAdapter hotAdapter;


    private int pageNo = 1;
    private int pageSize = 8;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_shot);
        ButterKnife.bind(this);
        initviews();
        initRecyler();
        initRefresh();
        getBigNavigation();
    }

    //导航栏
    private void initviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        tvSearch.setText("请输入搜索内容");
        isLogin = SPTool.getBoolean(mContext, Constants.ISLOGIN);

    }


    /**
     * recyler
     */
    private void initRecyler() {
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
    }


    /**
     * 加载更多
     */
    private void initRefresh() {
        listHot = new ArrayList<>();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }


    @OnClick({R.id.search_text_qa, R.id.back_lay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.search_text_qa:
                Intent intent = new Intent(mContext, QaSearchActivity.class);
                intent.putExtra("form", "bigShot");
                startActivity(intent);
                break;
            case R.id.back_lay:
                finish();
                break;
        }
    }

    //获取导航栏数据
    private void getBigNavigation() {
        Call<BigForumBean> call = communityService().getForumOnBSMainPage(userId);
        NetUtils<BigForumBean> netUtils = new NetUtils<BigForumBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<BigForumBean>() {
            @Override
            public void onResponseCallback(BigForumBean bigForumBean) {
                if (bigForumBean.getReturnCode() != 0 && !bigForumBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, bigForumBean.getReturnMsg()).show();
                    return;
                }
                getBigFroumData(bigForumBean);
                //获取新晋答主
                getAnswerLord();
                //获取热门回答
                initHotRecy();
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    private void getBigFroumData(BigForumBean bigForumBean) {
        list = bigForumBean.getContent();
        adapter = new BigHeadAdapter(mContext, list);
        recyclerView.setAdapter(adapter);
        adapter.addOnItemClickListener(this);

    }

    //获取新晋答主
    private void getAnswerLord() {
        Call<MavinListBean> call = communityService().getNewBigshotList();
        call.enqueue(new NetRetrofitCallback<MavinListBean>(mContext, new HttpCallBackImpl<MavinListBean>() {
            @Override
            public void onResponseCallback(MavinListBean mavinListBean) {
                if (mavinListBean.getReturnCode() != 0 && !mavinListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, mavinListBean.getReturnMsg()).show();
                    return;
                }
                getAnswerLordData(mavinListBean);
            }
        }));
    }

    private void getAnswerLordData(MavinListBean mavinListBean) {
        listDazhu = mavinListBean.getContent();
        if (listDazhu.size() == 0) {
            tvNoDazhu.setVisibility(View.VISIBLE);
            dazhuList.setVisibility(View.GONE);
            return;
        }
        dazhuList.setVisibility(View.VISIBLE);
        mavinAdapter = new MavinAdapter(mContext, listDazhu, "", "");
        dazhuList.setAdapter(mavinAdapter);
        dazhuList.setOnItemClickListener(this);
    }


    //热门回答 recylerview
    private void initHotRecy() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wendaRecy.setLayoutManager(layoutManager);
        wendaRecy.setNestedScrollingEnabled(false);
        getHotAnswer();
    }


    //获取热门回答

    private void getHotAnswer() {
        Call<BigshotAnwserListBean> call = communityService().getHotListOnBigshotMainPage(userId
                , pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<BigshotAnwserListBean>(mContext, new HttpCallBackImpl<BigshotAnwserListBean>() {
            @Override
            public void onResponseCallback(BigshotAnwserListBean anwserListBean) {
                if (anwserListBean.getReturnCode() != 0 && !anwserListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, anwserListBean.getReturnMsg()).show();
                    return;
                }
                getHotAnswerData(anwserListBean.getContent());
            }
        }));
    }


    private void getHotAnswerData(List<BigshotAnwserListBean.ContentBean> anwserListBean) {
        if (anwserListBean.size() == 0) {
            if (pageNo == 1) {
                tvNowenda.setVisibility(View.VISIBLE);
                wendaRecy.setVisibility(View.GONE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            return;
        }

        if (pageNo == 1) {
            listHot.clear();
            listHot.addAll(anwserListBean);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            hotAdapter = new BigshotAnwserListAdapter(mContext, listHot, "bigshot");
            wendaRecy.setAdapter(hotAdapter);
        } else {
            listHot.addAll(anwserListBean);
            refreshLayout.finishLoadMore();
            hotAdapter.notifyDataSetChanged();
        }


        wendaRecy.setVisibility(View.VISIBLE);
        hotAdapter.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(mContext, BigshotQuestionActivity.class);
                intent.putExtra(Constants.POSTID, listHot.get(position).getPostId());
                startActivity(intent);
            }
        });
    }

    /**
     * 导航栏监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, BigshotDetalisActivity.class);
        intent.putExtra(Constants.TITLE, list.get(position).getForumName());
        intent.putExtra(Constants.FORUMID, list.get(position).getForumId());
        startActivity(intent);
    }

    //答主监听事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (!isLogin) {
            ActivityManger.skipActivity(mContext, ActivityLogin.class);
            return;
        }

        Intent intent = new Intent(mContext, BigShotPutToActivity.class);
        intent.putExtra(Constants.BIGSHOTID, listDazhu.get(position).getUserId());
        startActivity(intent);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getHotAnswer();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getHotAnswer();
    }
}
