package com.yonyou.friendsandaargang.info.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.user.HuiDaAnwserActivity;
import com.yonyou.friendsandaargang.info.adapter.QaBigToMeAdapter;
import com.yonyou.friendsandaargang.info.bean.QaToMeBean;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.Translate;

/**
 * Created by shibing on 18/6/12.
 */

public class QaBigTomeFragemnt extends Fragment implements
        OnRefreshListener
        , OnItemClickListener, OnLoadMoreListener {
    View view;

    @BindView(R.id.tv_no_bigtome)
    TextView textView;
    @BindView(R.id.big_tome_list)
    ListView listView;
    @BindView(R.id.qabig_refreshlayout)
    SmartRefreshLayout refreshLayout;

    private String userId;
    private List<QaToMeBean.ContentBean> list;
    private QaBigToMeAdapter adapter;
    private ApiService apiService;

    private int pageNo = 1;
    private int pageSize = 8;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragenmt_qa_bigtome, null);
        ButterKnife.bind(this, view);
        apiService = ApiClient.retrofit().create(ApiService.class);
        initviews();

    }


    @Override
    public void onResume() {
        super.onResume();
        getQaBigTomeList();

    }


    private void initviews() {
        userId = SPTool.getContent(getActivity(), Constants.USER_ID);
        list = new ArrayList<>();
        initRefresh();
    }


    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(Translate));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(Translate));
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    private void getQaBigTomeList() {
        Call<QaToMeBean> call = apiService.getQuestionAskToMe(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<QaToMeBean>(getActivity(), new HttpCallBackImpl<QaToMeBean>() {
            @Override
            public void onResponseCallback(QaToMeBean qaToMeBean) {

                if (!qaToMeBean.getReturnMsg().equals("success") && qaToMeBean.getReturnCode() != 0) {
                    ToastUtils.normal(qaToMeBean.getReturnMsg());
                }
                getQaBigTomeData(qaToMeBean.getContent());

            }
        }));
    }

    private void getQaBigTomeData(List<QaToMeBean.ContentBean> contentBeans) {
        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                listView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            return;
        }
        listView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        if (pageNo == 1) {
            refreshLayout.setEnableLoadMore(true);
            list.clear();
            list.addAll(contentBeans);
            refreshLayout.finishRefresh();

            adapter = new QaBigToMeAdapter(getActivity(), list);
            listView.setAdapter(adapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            adapter.notifyDataSetChanged(list);
        }
        adapter.addOnItemClickListener(this);
        adapter.addRefuseOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                refuseToAnswer(list.get(position).getPostId());
                ToastUtils.normal(getActivity(), "您拒绝回答该问题").show();
            }
        });
    }


    /**
     * 拒绝回答问题
     *
     * @param postId
     */
    private void refuseToAnswer(String postId) {
        Call<HttpResult> call = apiService.refuseToAnswer(postId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(getActivity());
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(getActivity(), httpResult.getReturnMsg()).show();
                    return;
                }
                getQaBigTomeList();
            }

            @Override
            public void onFailureCallback() {

            }
        });

    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getQaBigTomeList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getQaBigTomeList();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), HuiDaAnwserActivity.class);
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        intent.putExtra("image", list.get(position).getAvatar());
        intent.putExtra("name", list.get(position).getAuthor());
        intent.putExtra("content", list.get(position).getTitle());
        startActivity(intent);
    }


}
