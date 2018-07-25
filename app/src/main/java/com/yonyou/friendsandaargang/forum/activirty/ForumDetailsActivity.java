package com.yonyou.friendsandaargang.forum.activirty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.adapter.ProductAdapter;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.forum.bean.FollowForum;
import com.yonyou.friendsandaargang.homepage.activity.PublishPostActivity;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.dialog.FollowDialog;
import com.yonyou.friendsandaargang.view.dialog.ForumDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/3/7.
 */

public class ForumDetailsActivity extends BaseActivity
        implements View.OnClickListener, TabLayout.OnTabSelectedListener
        , OnRefreshListener, OnLoadMoreListener, OnItemClickListener {
    @BindView(R.id.forum_head_img)
    CircleImageView forum_head_img;
    @BindView(R.id.forum_name_text)
    TextView forum_name_text;
    @BindView(R.id.forum_guanzhu_text)
    TextView forum_guanzhu_text;
    @BindView(R.id.forum_fatie_text)
    TextView forum_fatie_text;
    @BindView(R.id.forum_content_text)
    TextView forum_content_text;
    @BindView(R.id.forum_ggao_text)
    TextView forum_ggao_text;
    @BindView(R.id.forum_follow_text)
    TextView forum_follow_text;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.forum_recylerview)
    RecyclerView recyclerView;
    @BindView(R.id.no_tv)
    TextView tvNo;
    @BindView(R.id.gongg_lay)
    LinearLayout layoutGg;


    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;


    private List<Follow.ContentBean> listforum;
    private List<FollowForum.ContentBean.BrandListBean> listBrand;
    private String userId;
    private String forumId;
    private String forumName;
    private String brandId;
    private int followed;
    private ForumDialog forumDialog;
    private boolean IsLogin;
    private FollowDialog followDialog;
    private LinearLayoutManager manager;
    private int pageNo = 1;
    private int pageSize = 10;
    private ProductAdapter productAdapter;

    private String from, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_details);
        ButterKnife.bind(this);
        initviews();
        getFollowDetalis();

    }

    private void initviews() {
        getTitleBar();

        IsLogin = SPTool.getBoolean(mContext, Constants.ISLOGIN);
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        forumId = getIntent().getStringExtra(Constants.FORUMID);
        from = getIntent().getStringExtra("from");
        title = getIntent().getStringExtra(Constants.TITLE);
        if (TextUtils.isEmpty(title)) {
            setTitleText("论坛详情").rightImageRes(R.drawable.add3x);
        } else {
            setTitleText(title).rightImageRes(R.drawable.add3x);
        }
        initRecy();
        initRefresh();
    }

    //recylerview
    private void initRecy() {
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
    }

    //加载更多
    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        listforum = new ArrayList<>();

    }


    //论坛用户详情  用户信息
    private void getFollowDetalis() {
        Call<FollowForum> call = communityService().getForumInfoWithUserBrand(userId, forumId);
        NetUtils<FollowForum> netUtils = new NetUtils<FollowForum>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<FollowForum>() {
            @Override
            public void onResponseCallback(FollowForum followForum) {
                if (!followForum.getReturnMsg().equals("success") && followForum.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, followForum.getReturnMsg()).show();
                    return;
                }
                initDatas(followForum);
                //请求成功后添加tab信息
                initTab();
                getRecommendData();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    private void initDatas(FollowForum model) {
        //新车交易&配件市场
        if (from.equals("homepage")) {
            forum_follow_text.setVisibility(View.GONE);
            forum_guanzhu_text.setVisibility(View.GONE);
            forum_fatie_text.setVisibility(View.GONE);
            forum_content_text.setText("发帖 " + model.getContent().getPostNumber() + "");
        } else {
            forum_fatie_text.setText("发帖 " + model.getContent().getPostNumber() + "");
            forum_guanzhu_text.setText("关注 " + model.getContent().getFollowNumber() + "");
            forum_content_text.setText(model.getContent().getBriefDesc());
            if (model.getContent().getFollowed() == 0) {
                forum_follow_text.setText("关注");
                forum_follow_text.setBackgroundResource(R.drawable.shape_follow_red);
                forum_follow_text.setTextColor(getResources().getColor(R.color.color_white));
            } else {
                forum_follow_text.setText("已关注");
                forum_follow_text.setTextColor(getResources().getColor(R.color.color_gray3));
                forum_follow_text.setBackgroundResource(R.drawable.shape_follow_garly);
            }
        }

        GlideManager.loadImage(mContext, model.getContent().getIcon(), R.drawable.user, forum_head_img);
        forum_name_text.setText(model.getContent().getForumName());
        forumName = model.getContent().getForumName();
        if (model.getContent().getNotice() == null) {
            forum_ggao_text.setText("暂无最新公告");
        } else {
            forum_ggao_text.setText(model.getContent().getNotice().getTitle());
        }
        listBrand = model.getContent().getBrandList();
        followed = model.getContent().getFollowed();
    }


    /**
     * tablayout   监听事件
     */
    private void initTab() {
        tablayout.addTab(tablayout.newTab().setText("推荐"), 0, true);
        for (int i = 0; i < listBrand.size(); i++) {
            tablayout.addTab(tablayout.newTab().setText(listBrand.get(i).getBrandName()));
        }
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.addOnTabSelectedListener(this);
    }


    /**
     * tab  监听事件
     *
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //获取论坛推荐内容（推荐Tab）
        if (tab.getPosition() == 0) {
            brandId = "";
            getRecommendData();
        }
        //获取论坛中指定品牌的帖子列表
        else {
            brandId = listBrand.get(tab.getPosition() - 1).getBrandId();
            pageNo = 1;
            getAppointPost(brandId);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    /**
     * 21.获取论坛推荐内容（推荐Tab）
     */
    private void getRecommendData() {
        Call<Follow> call = communityService().getRecommendedPostList(forumId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<Follow>(mContext, new HttpCallBackImpl<Follow>() {
            @Override
            public void onResponseCallback(Follow model) {

                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initApptionData(model.getContent());
            }
        }));
    }


    /**
     * 获取论坛中指定品牌的帖子列表
     */
    private void getAppointPost(String brandId) {
        Call<Follow> call = communityService().getPostListByForumAndBrand(forumId, brandId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<Follow>(mContext, new HttpCallBackImpl<Follow>() {
            @Override
            public void onResponseCallback(Follow model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initApptionData(model.getContent());
            }
        }));
    }


    private void initApptionData(List<Follow.ContentBean> contentBeans) {
        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                tvNo.setVisibility(View.VISIBLE);
                tvNo.setText("暂时还没有数据哦");
                recyclerView.setVisibility(View.GONE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);
        tvNo.setVisibility(View.GONE);


        if (pageNo == 1) {
            listforum.clear();
            listforum.addAll(contentBeans);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            productAdapter = new ProductAdapter(listforum, mContext);
            recyclerView.setAdapter(productAdapter);
        } else {
            listforum.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            productAdapter.notifyDataSetChanged(listforum);
        }
        productAdapter.notifyDataSetChanged(listforum);
        productAdapter.addOnItemClickListener(this);
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, PostDetailsActivity.class);
        intent.putExtra(Constants.TITLE, listforum.get(position).getTypeDesc());
        intent.putExtra(Constants.POSTID, listforum.get(position).getPostId());
        startActivity(intent);
    }


    //关注or取消关注
    @OnClick({R.id.back_lay, R.id.forum_follow_text, R.id.titleBar_right_text})
    public void onClick(View v) {
        if (!IsLogin) {
            ActivityManger.skipActivity(mContext, ActivityLogin.class);
            return;
        }
        switch (v.getId()) {
            case R.id.back_lay:
                finish();
                break;
            case R.id.forum_follow_text:
                if (followed == 1) {
                    showUnFollowDialog();
                } else {
                    getFollowForum(forumId);
                }
                break;
            case R.id.titleBar_right_text:
                if (forumId.equals("1025")) {
                    Intent intent = new Intent(mContext, PublishPostActivity.class);
                    intent.putExtra("title", "新车交易");
                    intent.putExtra("type", "10051005");        //新车交易  出售
                    intent.putExtra("forumId", forumId);
                    startActivity(intent);
                    return;
                }
                if (forumId.equals("1026")) {
                    Intent intent = new Intent(mContext, PublishPostActivity.class);
                    intent.putExtra("title", "配件市场");
                    intent.putExtra("type", "10051004");        //配件市场
                    intent.putExtra("forumId", forumId);
                    startActivity(intent);
                    return;
                }
                showDiaglo();
                break;
        }


    }


    private void showDiaglo() {
        forumDialog = new ForumDialog(mContext);
        forumDialog.setCanceledOnTouchOutside(false);
        forumDialog.setForunListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PublishPostActivity.class);
                intent.putExtra("title", "帖子");
                intent.putExtra("type", "10051002");        //帖子
                intent.putExtra("forumName", forumName);
                intent.putExtra("forumId", forumId);
                startActivity(intent);
                forumDialog.dismiss();
            }
        });

        forumDialog.setHuaTiListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PublishPostActivity.class);
                intent.putExtra("title", "话题");
                intent.putExtra("type", "10051001");        //话题
                intent.putExtra("forumName", forumName);
                intent.putExtra("forumId", forumId);
                startActivity(intent);
                forumDialog.dismiss();
            }
        });


        forumDialog.setExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumDialog.dismiss();

            }
        });
        forumDialog.show();
    }


    private void showUnFollowDialog() {
        followDialog = new FollowDialog(mContext);
        followDialog.setCancelable(false);
        followDialog.getDialog_forum_title().setText("确定不再关注此论坛？");
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUnFollowForum(forumId);
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
    private void getUnFollowForum(final String forumId) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getunFollowForum(userId, forumId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                followed = 0;
                forum_follow_text.setText("关注");
                forum_follow_text.setBackgroundResource(R.drawable.shape_follow_red);
                forum_follow_text.setTextColor(getResources().getColor(R.color.color_white));
                ToastUtils.normal(mContext, "取消关注").show();
            }

            @Override
            public void onFailure(int code, String msg) {
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


    /**
     * 关注
     *
     * @param forumId
     */
    private void getFollowForum(String forumId) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getfollowForumr(userId, forumId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                followed = 1;
                forum_follow_text.setText("已关注");
                forum_follow_text.setTextColor(getResources().getColor(R.color.color_gray3));
                forum_follow_text.setBackgroundResource(R.drawable.shape_follow_garly);
                ToastUtils.normal(mContext, "已关注").show();
            }

            @Override
            public void onFailure(int code, String msg) {
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


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        if (TextUtils.isEmpty(brandId)) {
            getRecommendData();
        } else {
            getAppointPost(brandId);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (followDialog != null && forumDialog != null) {
            followDialog.dismiss();
            forumDialog.dismiss();
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (TextUtils.isEmpty(brandId)) {
            pageNo++;
            getRecommendData();
        } else {
            pageNo++;
            getAppointPost(brandId);
        }
    }
}
