package com.yonyou.friendsandaargang.info.activity.operationmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.QueryQuestionAdapter;
import com.yonyou.friendsandaargang.info.bean.RecordBean;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

public class QueryQuestionActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.et_queryquestion)
    EditText etQueryquestion;
    @BindView(R.id.tl_queryquestion)
    TabLayout tlQueryquestion;
    @BindView(R.id.lv_queryquestion)
    ListView lvQueryquestion;
    @BindView(R.id.srl_queryquestion)
    SmartRefreshLayout srlQueryquestion;
    @BindView(R.id.no_text_tv)
    TextView no_text_tv;

    private String userId;
    private List<RecordBean.ContentBean> list;
    private QueryQuestionAdapter adapter;
    private String infoccRdid;
    private String status;
    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_question);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        getTitleBar();
        setTitleText("问题查询");
        userId = SPTool.getContent(mContext, Constants.USER_ID);

        list = new ArrayList<>();
        adapter = new QueryQuestionAdapter(mContext, list, userId);
        lvQueryquestion.setAdapter(adapter);

        initRefresh();
        initTab();
        getRecord(infoccRdid, status);
    }


    /**
     * tab layout
     */
    private void initTab() {
        tlQueryquestion.addTab(tlQueryquestion.newTab().setText("全部"));
        tlQueryquestion.addTab(tlQueryquestion.newTab().setText("处理中"));
        tlQueryquestion.addTab(tlQueryquestion.newTab().setText("已处理"));
        tlQueryquestion.addTab(tlQueryquestion.newTab().setText("待评价"));
    }


    /**
     * 加载更多
     */
    private void initRefresh() {
        srlQueryquestion.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        srlQueryquestion.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        srlQueryquestion.setOnRefreshListener(this);
        srlQueryquestion.setOnLoadMoreListener(this);
    }


    private void initListener() {
        etQueryquestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                infoccRdid = s.toString();
                getRecord(infoccRdid, status);
                adapter.notifyDataSetChanged();
            }
        });

        tlQueryquestion.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //10331001暂存 10331002未处理 10331003已处理 10331004处理中 10331005待评价 10331006已关闭
                switch (tab.getText().toString()) {
                    case "全部":
                        status = "";
                        break;
                    case "处理中":
                        status = "10331004";
                        break;
                    case "已处理":
                        status = "10331003";
                        break;
                    case "待评价":
                        status = "10331005";
                        break;
                }
                getRecord(infoccRdid, status);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        lvQueryquestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    private void getRecord(String infoccRdid, String status) {
        showProgressDialog();
        Call<RecordBean> call = communityService().getRecord(userId, infoccRdid, status, pageNo, pageSize);
        call.enqueue(new RetrofitCallback<RecordBean>() {
            @Override
            public void onSuccess(RecordBean model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    no_text_tv.setVisibility(View.VISIBLE);
                    lvQueryquestion.setVisibility(View.GONE);
                    return;
                }
                setData(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                srlQueryquestion.finishRefresh();
                srlQueryquestion.finishLoadMore();
                ToastUtils.normal(mContext, "服务器异常").show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }

    private void setData(RecordBean model) {
        if (model.getContent().size() == 0) {
            if (pageNo == 1) {
                list.clear();
                adapter.notifyDataSetChanged();
                no_text_tv.setVisibility(View.VISIBLE);
                lvQueryquestion.setVisibility(View.GONE);
            } else {
                srlQueryquestion.finishLoadMoreWithNoMoreData();
            }
            srlQueryquestion.finishRefresh();
            srlQueryquestion.finishLoadMore();
            pageNo = 1;
            return;
        }

        if (pageNo == 1) {
            list.clear();
            list.addAll(model.getContent());
            no_text_tv.setVisibility(View.GONE);
            lvQueryquestion.setVisibility(View.VISIBLE);
            srlQueryquestion.finishRefresh();
            srlQueryquestion.setEnableLoadMore(true);
            adapter.notifyDataSetChanged();
        } else {
            list.addAll(model.getContent());
            srlQueryquestion.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getRecord(infoccRdid, status);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getRecord(infoccRdid, status);
    }
}
