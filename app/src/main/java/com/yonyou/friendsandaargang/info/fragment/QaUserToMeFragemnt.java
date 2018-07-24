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
import com.yonyou.friendsandaargang.forum.bean.MyForumList;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.BigshotQuestionActivity;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.QaDetalisActivity;
import com.yonyou.friendsandaargang.info.adapter.QaUserToMeAdapter;
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

public class QaUserToMeFragemnt extends Fragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.qa_usertome_list)
    ListView listView;
    @BindView(R.id.tv_no)
    TextView textView;
    @BindView(R.id.qauser_tome_refreshlayout)
    SmartRefreshLayout refreshLayout;

    View view;
    private String userId;
    private QaUserToMeAdapter adapter;
    private List<QaUserToMeBean.ContentBean> list;
    private int pageNo = 1;
    private int pageSize = 8;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragenmt_user_tome, null);
        ButterKnife.bind(this, view);
        initviews();
        getQaUserToMeList();

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
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(Translate));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(Translate));
        //加载更多
        refreshLayout.setEnableScrollContentWhenLoaded(true);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);


    }


    /**
     * 获取我关注的论坛
     */
    private void getQaUserToMeList() {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<QaUserToMeBean> call = apiService.getMyQuestion(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<QaUserToMeBean>(getActivity(), new HttpCallBackImpl<QaUserToMeBean>() {
            @Override
            public void onResponseCallback(QaUserToMeBean model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(model.getReturnMsg());
                }
                getQaUserToMeData(model.getContent());
            }
        }));

    }

    private void getQaUserToMeData(List<QaUserToMeBean.ContentBean> model) {

        if (model.size() == 0) {
            if (pageNo == 1) {
                listView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishLoadMore();
            refreshLayout.finishLoadMore();
            return;
        }
        if (pageNo == 1) {
            list.clear();
            list.addAll(model);
            refreshLayout.setEnableLoadMore(true);
            refreshLayout.finishRefresh();
            adapter = new QaUserToMeAdapter(getActivity(), list);
            listView.setAdapter(adapter);
        } else {
            list.addAll(model);
            refreshLayout.finishLoadMore();
            refreshLayout.setNoMoreData(false);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (list.get(position).getForumType()) {
            case "10241002":
                intent = new Intent(getActivity(), QaDetalisActivity.class);
                intent.putExtra(Constants.POSTID, list.get(position).getPostId());
                startActivity(intent);
                break;
            case "10241003":
                intent = new Intent(getActivity(), BigshotQuestionActivity.class);
                intent.putExtra(Constants.POSTID, list.get(position).getPostId());
                startActivity(intent);
                break;
        }


    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getQaUserToMeList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getQaUserToMeList();
    }
}
