package com.yonyou.friendsandaargang.info.activity.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.forum.adapter.ProductAdapter;
import com.yonyou.friendsandaargang.forum.adapter.UserDetalisProductAdapter;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.guide.bean.UserInfo;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.QaDetalisActivity;
import com.yonyou.friendsandaargang.info.activity.user.FansActivity;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.dialog.FollowDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/2/28.
 * <p>
 */

public class FensDetailsActivity extends BaseActivity
        implements OnItemClickListener, AdapterView.OnItemClickListener
        , XTabLayout.OnTabSelectedListener, OnRefreshListener
        , View.OnTouchListener, OnLoadMoreListener {

    @BindView(R.id.fens_details_image)
    CircleImageView fens_details_image;
    @BindView(R.id.fens_details_name)
    TextView fens_details_name;
    @BindView(R.id.fens_details_content)
    TextView fens_details_content;
    @BindView(R.id.fens_detalis_number)
    TextView fens_detalis_number;
    @BindView(R.id.fens_details_follow_number)
    TextView fens_details_follow_number;
    @BindView(R.id.fens_details_hg_text)
    TextView fens_details_hg_text;
    @BindView(R.id.fens_details_foollow)
    TextView fens_details_foollow;
    @BindView(R.id.no_text)
    TextView no_text;
    //tablay
    @BindView(R.id.fens_details_tablay)
    XTabLayout tabLayout;
    @BindView(R.id.fens_details_recylerview)
    RecyclerView listView;
    @BindView(R.id.fens_details_hg)
    ImageView imageView;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;

    private List<Follow.ContentBean> list;
    private String userId;
    private String titles;
    private String viewerId;
    private String postType;
    private String id;
    private int Isfollow;
    private FollowDialog followDialog;
    private UserDetalisProductAdapter adapter;
    private ProductAdapter productAdapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private List<String> tablayList;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fens_details);
        ButterKnife.bind(this);
        initviews();
        getUserInfo();

    }


    private void initviews() {
        getTitleBar();
        userId = getIntent().getStringExtra(Constants.USER_ID);
        id = SPTool.getContent(mContext, Constants.USER_ID);
        // initRecyler();
        imageView.setImageResource(R.drawable.hg);
        initRecyler();
        initRefresh();


    }


    private void initRecyler() {
        manager = new LinearLayoutManager(mContext);
        listView.setLayoutManager(manager);

    }


    private void initRefresh() {
        list = new ArrayList<>();
        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        Call<HttpResult<UserInfo>> call = communityService().getUserInfo(userId, id);
        NetUtils<HttpResult<UserInfo>> netUtils = new NetUtils<HttpResult<UserInfo>>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult<UserInfo>>() {
            @Override
            public void onResponseCallback(HttpResult<UserInfo> model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getData(model);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }

    private void getData(HttpResult<UserInfo> model) {
        if (model != null) {
            GlideManager.loadImage(mContext, model.getContent().getAvatar(), R.drawable.user, fens_details_image);
            fens_details_name.setText(model.getContent().getNickname());
            fens_details_hg_text.setText(model.getContent().getLevel());
            if (TextUtils.isEmpty(model.getContent().getSignature())) {
                fens_details_content.setText("暂无签名");
            } else {
                fens_details_content.setText(model.getContent().getSignature());
            }
            fens_detalis_number.setText(model.getContent().getFollowerNumber() + "   粉丝");
            fens_details_follow_number.setText(model.getContent().getFollowNumber() + "   关注");


            //自己不能关注自己
            if (userId.equals(id)) {
                fens_details_foollow.setVisibility(View.GONE);
            } else {
                fens_details_foollow.setVisibility(View.VISIBLE);
                if (model.getContent().getFollowed() == 1) {
                    fens_details_foollow.setText("已关注");
                    fens_details_foollow.setBackgroundResource(R.drawable.shape_follow_garly);
                    fens_details_foollow.setTextColor(getResources().getColor(R.color.color_gray3));
                } else {
                    fens_details_foollow.setText("关注");
                    fens_details_foollow.setBackgroundResource(R.drawable.shape_follow_garly);
                    fens_details_foollow.setTextColor(getResources().getColor(R.color.color_gray3));
                }
            }


            Isfollow = model.getContent().getFollowed();//是否关注
            titles = model.getContent().getNickname();
            viewerId = model.getContent().getUserId();  //浏览者id
            //请求成功后添加tab
            initTab();
            //第一次进来默认加载全部
            getListData(postType);

        }
    }


    /**
     * 添加tab
     */
    private void initTab() {
        tablayList = Arrays.asList("全 部", "帖 子", "话 题", "问 答");
        tabLayout.setxTabDisplayNum(tablayList.size());
        for (int i = 0; i < tablayList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tablayList.get(i)));
        }
        tabLayout.setOnTabSelectedListener(this);
    }

    /**
     * 调用接口
     */
    private void getListData(String postType) {
        Call<Follow> call = communityService().getUserPostListAll(userId,
                postType,
                userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<Follow>(mContext, new HttpCallBackImpl<Follow>() {
            @Override
            public void onResponseCallback(Follow model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getData(model.getContent());
            }
        }));
    }

    /**
     * @param contentBeans
     */
    private void getData(List<Follow.ContentBean> contentBeans) {
        if (contentBeans.size() == 0) {
            if (pageNo == 1) {
                list.clear();
                listView.setVisibility(View.GONE);
                no_text.setVisibility(View.VISIBLE);
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            return;
        }
        listView.setVisibility(View.VISIBLE);
        no_text.setVisibility(View.GONE);
        if (pageNo == 1) {
            list.clear();
            list.addAll(contentBeans);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            productAdapter = new ProductAdapter(list, mContext);
            listView.setAdapter(productAdapter);
        } else {
            list.addAll(contentBeans);
            refreshLayout.finishLoadMore();
            productAdapter.notifyDataSetChanged(list);
        }
        productAdapter.notifyDataSetChanged(list);
        productAdapter.addOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    /**
     * 监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent;
        if (list.get(position).getTypeDesc().equals("提问")) {
            intent = new Intent(FensDetailsActivity.this, QaDetalisActivity.class);
            intent.putExtra(Constants.POSTID, list.get(position).getPostId());
            startActivity(intent);
        } else {
            intent = new Intent(FensDetailsActivity.this, PostDetailsActivity.class);
            intent.putExtra(Constants.TITLE, list.get(position).getTypeDesc());
            intent.putExtra(Constants.POSTID, list.get(position).getPostId());
            startActivity(intent);
        }
    }


    @OnClick({R.id.fens_detalis_number, R.id.fens_details_follow_number, R.id.fens_details_foollow})
    public void OnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.fens_detalis_number:
                intent = new Intent(FensDetailsActivity.this, FansActivity.class);
                intent.putExtra(Constants.TITLE, titles);
                intent.putExtra(Constants.VIEWERID, viewerId);
                startActivity(intent);
                break;
            case R.id.fens_details_follow_number:
                intent = new Intent(FensDetailsActivity.this, UserFollowActivity.class);
                intent.putExtra(Constants.TITLE, titles);
                intent.putExtra(Constants.VIEWERID, viewerId);
                startActivity(intent);
                break;
            //关注or取消关注
            case R.id.fens_details_foollow:
                if (Isfollow == 1) {
                    showUnFollowDialog();
                } else {
                    getfollowUser();
                }
                break;
        }
    }


    private void showUnFollowDialog() {
        followDialog = new FollowDialog(mContext);
        followDialog.setCancelable(false);
        followDialog.getDialog_forum_title().setText("确定不再关注此人？");
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUnfollowUser();
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

    /**
     * 关注
     */
    private void getfollowUser() {
        Call<HttpResult> call = communityService().getFollowUser(id, userId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    return;
                }
                Isfollow = 1;
                fens_details_foollow.setText("已关注");
                fens_details_foollow.setBackgroundResource(R.drawable.shape_follow_garly);
                fens_details_foollow.setTextColor(getResources().getColor(R.color.color_gray3));
            }
        }));
    }


    /**
     * 取消关注
     */

    private void getUnfollowUser() {
        Call<HttpResult> call = communityService().getUnFollowUser(id, userId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    return;
                }
                Isfollow = 0;
                fens_details_foollow.setText("关注");
                fens_details_foollow.setBackgroundResource(R.drawable.shape_follow_garly);
                fens_details_foollow.setTextColor(getResources().getColor(R.color.color_gray3));
            }
        }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (followDialog != null) {
            followDialog.dismiss();
        }
    }


    //切换标签栏
    @Override
    public void onTabSelected(XTabLayout.Tab tab) {
        list.clear();
        switch (tab.getPosition()) {
            case 0:
                postType = "";
                pageNo = 1;
                getListData(postType);
                break;
            case 1:
                postType = "10051002";
                pageNo = 1;
                getListData(postType);
                break;
            case 2:
                postType = "10051001";
                pageNo = 1;
                getListData(postType);
                break;
            case 3:
                postType = "10051003";
                pageNo = 1;
                getListData(postType);
                break;
        }
    }

    @Override
    public void onTabUnselected(XTabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(XTabLayout.Tab tab) {

    }


    /**
     * 下拉刷新
     *
     * @param refreshlayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getListData(postType);
    }


    /**
     * list  焦点在listview中滑动 listview  在refreshLayout中滑动  refreshLayout
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    /**
     * 上啦加载
     *
     * @param refreshLayout
     */

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getListData(postType);
    }
}
