package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.yonyou.friendsandaargang.homepage.modle.AnswerListBean;
import com.yonyou.friendsandaargang.homepage.modle.QuestionListBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.AdoptAdapeter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.PutToAdapeter;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;


/**
 * 专区详情
 */
public class SpecialAreaDetalisActivity extends BaseActivity
        implements AdapterView.OnItemClickListener, OnItemClickListener
        , OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.no_tiwen_tv)
    TextView tvNoTiwen;
    @BindView(R.id.no_caina_tv)
    TextView tvNoCaina;
    @BindView(R.id.tiwen_list)
    MyListView listViewTiwen;
    @BindView(R.id.cainai_list)
    MyListView listviewCaina;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;


    private String forumName;
    private String userId;
    private String forumId;

    //提问
    private List<QuestionListBean.ContentBean> listQues;
    private PutToAdapeter putToAdapeter;
    //采纳
    private List<AnswerListBean.ContentBean> listAnswer;
    private AdoptAdapeter adoptAdapeter;
    private View view;
    private int pageNo = 1;
    private int pageSize = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);
        initviews();
        initRefresh();
        //采纳
        getAreaAnswerList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //提问
        getAreaQuestionList();
    }

    private void initviews() {
        getTitleBar();
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        forumName = getIntent().getStringExtra(Constants.TITLE);
        forumId = getIntent().getStringExtra(Constants.FORUMID);
        setTitleText(forumName).rightImageRes(R.drawable.add3x);
        getFooter();

    }


    private void initRefresh() {
        listAnswer = new ArrayList<>();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }


    private void getFooter() {
        view = LayoutInflater.from(mContext).inflate(R.layout.qa_footer, null);
        LinearLayout layout = view.findViewById(R.id.lay_qa_footer);
        TextView textView = view.findViewById(R.id.qa_footer_tv);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hyh, 0, 0, 0);
        textView.setText("换一换");
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAreaQuestionList();
            }
        });
    }

    /**
     * 发布提问按钮
     */
    @OnClick({R.id.titleBar_right_text, R.id.search_lay})
    public void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.titleBar_right_text:
                intent = new Intent(mContext, PutToQaActivity.class);
                intent.putExtra(Constants.FORUMID, forumId);
                intent.putExtra(Constants.TITLE, forumName);
                startActivity(intent);
                break;
            case R.id.search_lay:
                intent = new Intent(mContext, QaSearchActivity.class);
                intent.putExtra("form", "Area");
                intent.putExtra("forumName", forumName);
                intent.putExtra(Constants.FORUMID, forumId);
                startActivity(intent);
                break;
        }

    }


    /**
     * 普通专区最新提问列表
     */

    private void getAreaQuestionList() {
        Call<QuestionListBean> call = communityService().getAreaQuestionList(userId, forumId, 1, 100);
        NetUtils<QuestionListBean> netUtils = new NetUtils<QuestionListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<QuestionListBean>() {
            @Override
            public void onResponseCallback(QuestionListBean questionListBean) {
                try {
                    if (questionListBean.getReturnCode() != 0 && !questionListBean.getReturnMsg().equals("success")) {
                        ToastUtils.normal(mContext, questionListBean.getReturnMsg()).show();
                        return;
                    }
                    getQuestionListData(questionListBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器异常").show();
            }
        });
    }


    /**
     * 提问数据处理
     */
    private void getQuestionListData(QuestionListBean questionListBean) {
        listQues = questionListBean.getContent();
        if (listQues.size() == 0) {
            listViewTiwen.setVisibility(View.GONE);
            tvNoTiwen.setVisibility(View.VISIBLE);
            return;
        }
        listViewTiwen.setVisibility(View.VISIBLE);
        tvNoTiwen.setVisibility(View.GONE);
        putToAdapeter = new PutToAdapeter(mContext, listQues, "");
        listViewTiwen.setAdapter(putToAdapeter);
        listViewTiwen.setOnItemClickListener(this);
        putToAdapeter.addOnItemClickListener(this);
        if (listQues.size() > 2) {
            listViewTiwen.removeFooterView(view);
            listViewTiwen.addFooterView(view);
        }
    }


    /**
     * 普通专区最新采纳列表
     */
    private void getAreaAnswerList() {
        Call<AnswerListBean> call = communityService().getAreaAnswerList(userId, forumId, pageNo, pageSize);
        call.enqueue(new RetrofitCallback<AnswerListBean>() {
            @Override
            public void onSuccess(AnswerListBean answerListBean) {
                if (answerListBean.getReturnCode() != 0 && !answerListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, answerListBean.getReturnMsg()).show();
                    return;
                }

                AnswerListData(answerListBean.getContent());
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    //采纳数据处理
    private void AnswerListData(List<AnswerListBean.ContentBean> answerListBean) {
        if (answerListBean.size() == 0) {
            if (pageNo == 1) {
                tvNoCaina.setVisibility(View.VISIBLE);
                listviewCaina.setVisibility(View.GONE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.setNoMoreData(false);
                ToastUtils.normal(mContext, "没有更多数据了哦").show();
            }
            refreshLayout.finishLoadMore();
            refreshLayout.finishRefresh();
            return;
        }

        if (pageNo == 1) {
            listAnswer.clear();
            listAnswer.addAll(answerListBean);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            adoptAdapeter = new AdoptAdapeter(mContext, listAnswer);
            listviewCaina.setAdapter(adoptAdapeter);
        } else {
            listAnswer.addAll(answerListBean);
            refreshLayout.finishLoadMore();
            adoptAdapeter.notifyDataSetChanged();
        }

        listviewCaina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, QaDetalisActivity.class);
                intent.putExtra(Constants.POSTID, listAnswer.get(position).getPostId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, QaDetalisActivity.class);
        intent.putExtra(Constants.POSTID, listQues.get(position).getPostId());
        startActivity(intent);
    }


    //去回答监听事件
    @Override
    public void onItemClick(int position) {
        //   ToastUtils.normal(mContext, "去问答监听事件").show();
    }


    //刷新加载
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getAreaAnswerList();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getAreaAnswerList();
    }
}
