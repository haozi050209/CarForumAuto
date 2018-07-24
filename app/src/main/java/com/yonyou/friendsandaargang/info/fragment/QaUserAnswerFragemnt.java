package com.yonyou.friendsandaargang.info.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.QaDetalisActivity;
import com.yonyou.friendsandaargang.info.adapter.QaAnswerAdapter;
import com.yonyou.friendsandaargang.info.adapter.QaUserToMeAdapter;
import com.yonyou.friendsandaargang.info.bean.QaUserAnswerBean;
import com.yonyou.friendsandaargang.info.bean.QaUserToMeBean;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;
import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.Translate;

/**
 * Created by shibing on 18/6/12.
 */

public class QaUserAnswerFragemnt extends Fragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    View view;
    private String userId;

    @BindView(R.id.tv_no_anwers)
    TextView textView;
    @BindView(R.id.qa_anwesr_list)
    ListView listView;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;

    private List<QaUserAnswerBean.ContentBean> list;
    private QaAnswerAdapter adapter;
    private int pageNo = 1;
    private int pageSize = 8;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragenmt_user_answer, null);
        ButterKnife.bind(this, view);
        initviews();
        getQaAnswerList();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }


    private void initviews() {
        userId = SPTool.getContent(getActivity(), Constants.USER_ID);
        list = new ArrayList<>();
        listView.setOnItemClickListener(this);

        initRefresh();
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void initRefresh() {
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(Translate));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(Translate));
        refreshLayout.autoRefresh();
        //加载更多


    }


    private void getQaAnswerList() {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<QaUserAnswerBean> call = apiService.getMyAnswer(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<QaUserAnswerBean>(getActivity(), new HttpCallBackImpl<QaUserAnswerBean>() {
            @Override
            public void onResponseCallback(QaUserAnswerBean model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(model.getReturnMsg());
                }
                getQaUserToMeData(model.getContent());
            }
        }));
    }

    private void getQaUserToMeData(List<QaUserAnswerBean.ContentBean> model) {
        if (model.size() == 0) {
            if (pageNo == 1) {
                listView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            return;
        }
        if (pageNo == 1) {
            list.clear();
            list.addAll(model);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            adapter = new QaAnswerAdapter(getActivity(), list);
            listView.setAdapter(adapter);
        } else {
            list.addAll(model);
            refreshLayout.finishLoadMore();
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), QaDetalisActivity.class);
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        startActivity(intent);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getQaAnswerList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getQaAnswerList();
    }
}
