package com.yonyou.friendsandaargang.forum.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.activirty.FollowForumActivity;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.forum.adapter.ProductAdapter;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyRecylerview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/3/5.
 * <p>
 * <p>
 * 论坛关注模块
 */

public class FollowFragment extends Fragment implements View.OnClickListener
        , OnLoadMoreListener
        , OnRefreshListener, OnItemClickListener {
    View view = null;
    @BindView(R.id.follow_recyler)
    MyRecylerview follow_recyler;
    @BindView(R.id.no_follow)
    TextView no_follow;
    @BindView(R.id.follow_refreshlayout)
    SmartRefreshLayout follow_refreshlayout;
    @BindView(R.id.follow_ray)
    RelativeLayout follow_ray;


    private List<Follow.ContentBean> list;
    private String userId;
    private int pageSize = 8;
    private int pageNo = 1;

    private ProductAdapter productAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_follow, null);
        ButterKnife.bind(this, view);
        initviews();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userId = SPTool.getContent(getActivity(), Constants.USER_ID);
        getFolloewList();
    }

    private void initviews() {
        //布局顶部显示
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        follow_recyler.setLayoutManager(layoutManager);
        follow_recyler.setNestedScrollingEnabled(false);

        follow_refreshlayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(FixedBehind));
        follow_refreshlayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(FixedBehind));
        follow_refreshlayout.setOnRefreshListener(this);
        follow_refreshlayout.setOnLoadMoreListener(this);
        list = new ArrayList<>();

    }


    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick(R.id.follow_ray)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.follow_ray:
                ActivityManger.skipActivity(getActivity(), FollowForumActivity.class);
                break;
        }
    }


    private void getFolloewList() {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<Follow> call = apiService.getUserPostListOfAllFollowedForums(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<Follow>(getActivity(), new HttpCallBackImpl<Follow>() {
            @Override
            public void onResponseCallback(Follow model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    return;
                }
                initDatas(model.getContent());
            }
        }));
    }

    /**
     * 添加recylervier数据
     */
    private void initDatas(List<Follow.ContentBean> contentList) {
        try {
            if (contentList.size() == 0) {
                if (pageNo == 1) {
                    no_follow.setVisibility(View.VISIBLE);
                    follow_recyler.setVisibility(View.GONE);
                    follow_refreshlayout.setEnableLoadMore(false);
                } else {
                    follow_refreshlayout.finishLoadMoreWithNoMoreData();
                }
                follow_refreshlayout.finishRefresh();
                follow_refreshlayout.finishLoadMore();
                return;
            }
            follow_recyler.setVisibility(View.VISIBLE);
            no_follow.setVisibility(View.GONE);
            if (pageNo == 1) {
                list.clear();
                list.addAll(contentList);
                follow_refreshlayout.finishRefresh();
                follow_refreshlayout.setEnableLoadMore(true);
                productAdapter = new ProductAdapter(list, getActivity());
                follow_recyler.setAdapter(productAdapter);
            } else {
                list.addAll(contentList);
                follow_refreshlayout.finishLoadMore();
                productAdapter.notifyDataSetChanged(list);
            }
            productAdapter.addOnItemClickListener(this);
        } catch (Exception e) {

        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
        intent.putExtra(Constants.TITLE, list.get(position).getTypeDesc());
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        startActivity(intent);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getFolloewList();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getFolloewList();
    }
}
