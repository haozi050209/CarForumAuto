package com.yonyou.friendsandaargang.forum.activirty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.adapter.ForumRecylerAdapter;
import com.yonyou.friendsandaargang.forum.bean.ForumState;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;
import com.yonyou.friendsandaargang.view.dialog.FollowDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/3/5.、
 * <p>
 * <p>
 * <p>
 * 我的论坛
 */

public class FollowForumActivity extends BaseActivity implements OnItemClickListener, OnRefreshListener {


    @BindView(R.id.headerlistView)
    PinnedHeaderListView headerListView;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;


    private ForumRecylerAdapter forumRecylerAdapter;
    private List<ForumState.ContentBean> stateList;
    private String userId;
    private FollowDialog followDialog;
    private List<String> titleList;

    private List<ForumState.ContentBean> FollowList;
    private List<ForumState.ContentBean> NoFollowList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followforum);
        ButterKnife.bind(this);

        initviews();
        initRefresh();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的论坛");
        userId = SPTool.getContent(this, Constants.USER_ID);
        FollowList = new ArrayList<>();
        NoFollowList = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getForumFollowStatusList();
    }


    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableLoadMore(false);

    }


    /**
     * 获取可能感兴趣的论坛
     */
    private void getForumFollowStatusList() {
        Call<ForumState> call = communityService().getForumFollowStatusList(userId);

        call.enqueue(new NetRetrofitCallback<ForumState>(mContext, new HttpCallBackImpl<ForumState>() {
            @Override
            public void onResponseCallback(ForumState model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    return;
                }
                refreshLayout.finishRefresh(true);    //结束刷新
                getFourmStateData(model);

            }
        }));
    }


    /**
     * 数据处理
     *
     * @param forumState
     */
    private void getFourmStateData(ForumState forumState) {

        stateList = forumState.getContent();
        FollowList.clear();
        NoFollowList.clear();
        for (int i = 0; i < stateList.size(); i++) {
            if (stateList.get(i).getFollowed() == 1) {
                FollowList.add(stateList.get(i));
            } else {
                NoFollowList.add(stateList.get(i));
            }
        }


        //添加title
        initTitles();

        forumRecylerAdapter = new ForumRecylerAdapter(this, FollowList, NoFollowList, titleList);
        headerListView.setAdapter(forumRecylerAdapter);
        headerListView.setPinHeaders(true);
        headerListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

                switch (section) {
                    case 0:
                        if (titleList.get(0).equals("我关注的论坛")) {
                            Intent intent = new Intent(FollowForumActivity.this, ForumDetailsActivity.class);
                            intent.putExtra("from", "");
                            intent.putExtra(Constants.TITLE, "");
                            intent.putExtra(Constants.FORUMID, FollowList.get(position).getForumId());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(FollowForumActivity.this, ForumDetailsActivity.class);
                            intent.putExtra("from", "");
                            intent.putExtra(Constants.TITLE, "");
                            intent.putExtra(Constants.FORUMID, NoFollowList.get(position).getForumId());
                            startActivity(intent);
                        }
                        break;

                    case 1:
                        Intent intent = new Intent(FollowForumActivity.this, ForumDetailsActivity.class);
                        intent.putExtra("from", "");
                        intent.putExtra(Constants.TITLE, "");
                        intent.putExtra(Constants.FORUMID, NoFollowList.get(position).getForumId());
                        startActivity(intent);
                        break;
                }


            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
        //取消关注
        forumRecylerAdapter.addOnItemClickListener(this);
        //关注
        forumRecylerAdapter.addFollowOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getFollowForum(NoFollowList.get(position).getForumId());
            }
        });
    }


    //添加分类title
    private void initTitles() {
        if (FollowList.size() > 0 && NoFollowList.size() > 0) {
            titleList = Arrays.asList("我关注的论坛", "可能感兴趣的论坛");
            return;
        }
        if (FollowList.size() > 0 && NoFollowList.size() == 0) {
            titleList = Arrays.asList("我关注的论坛");
            return;
        }
        if (FollowList.size() == 0 && NoFollowList.size() > 0) {
            titleList = Arrays.asList("可能感兴趣的论坛");
            return;
        }


    }


    /**
     * 取消关注
     *
     * @param postion
     */
    @Override
    public void onItemClick(int postion) {
        showDialog(FollowList.get(postion).getForumId());
    }


    /**
     * 弹窗
     *
     * @param forumID
     */
    private void showDialog(final String forumID) {
        followDialog = new FollowDialog(mContext);
        followDialog.setCancelable(false);
        followDialog.getDialog_forum_title().setText("确定不再关注此论坛？");
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUnFollowForum(forumID);
                followDialog.dismiss();
            }
        });
        followDialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followDialog.dismiss();
            }
        });
        followDialog.show();
    }

    //取消关注
    private void getUnFollowForum(String forumId) {
        Call<HttpResult> call = communityService().getunFollowForum(userId, forumId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg().toString()).show();
                    return;
                }
                getForumFollowStatusList();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }


    /**
     * 关注
     *
     * @param forumId
     */
    private void getFollowForum(String forumId) {
        Call<HttpResult> call = communityService().getfollowForumr(userId, forumId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(httpResult.getReturnMsg().toString());
                    return;
                }
                getForumFollowStatusList();
                ToastUtils.normal(mContext, "已关注").show();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getForumFollowStatusList();
    }
}
