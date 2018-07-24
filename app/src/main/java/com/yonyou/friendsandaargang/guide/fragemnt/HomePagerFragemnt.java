package com.yonyou.friendsandaargang.guide.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.forum.activirty.ForumDetailsActivity;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.forum.adapter.ProductAdapter;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.guide.adapter.GlideImageLoader;
import com.yonyou.friendsandaargang.guide.bean.Roll;
import com.yonyou.friendsandaargang.homepage.activity.BannerDetailsActivity;
import com.yonyou.friendsandaargang.homepage.activity.PostActivity;
import com.yonyou.friendsandaargang.homepage.activity.PublishPostActivity;
import com.yonyou.friendsandaargang.homepage.activity.SearchActivity;
import com.yonyou.friendsandaargang.homepage.adapter.NavigationbarAdapter;
import com.yonyou.friendsandaargang.homepage.modle.AllForums;
import com.yonyou.friendsandaargang.homepage.modle.BannerBean;
import com.yonyou.friendsandaargang.homepage.modle.NavigationBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.QaActivity;
import com.yonyou.friendsandaargang.info.activity.seeting.AuthenticationActivity;
import com.yonyou.friendsandaargang.info.activity.user.FollowPopleAtivity;
import com.yonyou.friendsandaargang.info.activity.operationmanager.OperationManagerActivity;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ImageTool;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.CircleImageView;
import com.yonyou.friendsandaargang.view.RecycleViewDivider;
import com.yonyou.friendsandaargang.view.TextViewVertical;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;
import com.yonyou.friendsandaargang.view.dialog.ForumDialog;
import com.yonyou.friendsandaargang.view.dialog.InterestFieldDialog;
import com.yonyou.friendsandaargang.view.pageview.PageRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/2/7.
 */

public class HomePagerFragemnt extends Fragment implements View.OnClickListener
        , OnRefreshListener
        , OnBannerListener, OnItemClickListener, OnLoadMoreListener {
    private View view = null;
    //banner 图
    @BindView(R.id.banner)
    Banner banner;
    //导航栏
    @BindView(R.id.recylerview)
    RecyclerView recyclerView;
    // 没有登录 滚动条信息
    @BindView(R.id.roll_text)
    TextView roll_text;
    @BindView(R.id.recylerview_list)
    RecyclerView recycler;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    @BindView(R.id.no_nrirong)
    TextView no_nrirong;
    @BindView(R.id.text_roll_two)
    TextViewVertical text_roll_two;


    //导航栏适配器
    private NavigationbarAdapter navigationbarAdapter;
    //滚动信息
    private List<Roll.ContentBean> textList;
    //导航栏数据
    private List<NavigationBean> list;

    private List<Follow.ContentBean> contentList;
    private ArrayList<String> arrayList;

    //布局管理器
    private LinearLayoutManager manager;
    private String userId;
    private int mColumnCount = 4;
    private ForumDialog forumDialog;
    private int pageSize = 8;
    private int pageNo = 1;
    private boolean isLogin;
    private List<Integer> checklist;
    private InterestFieldDialog fieldDialog;
    private DialogSureCancel dialogSureCancel;
    private List<AllForums.ContentBean> contentBeanList;
    private PageRecyclerView pageview;
    private PageRecyclerView.PageAdapter pageAdapter;
    private int follownum = 0;
    private ProductAdapter productAdapter;
    private ApiService communityService;
    private List<String> listBanner;
    private List<BannerBean.ContentBean> listBannerBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_homepager, null);
        ButterKnife.bind(this, view);
        initviews();
        getBanner();
        getNavigationBarData();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }


    private void initviews() {
        communityService = ApiClient.retrofit().create(ApiService.class);
        initRecyer();

        refreshlayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(FixedBehind));
        refreshlayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(FixedBehind));
        refreshlayout.setOnRefreshListener(this);
        refreshlayout.setOnLoadMoreListener(this);
        contentList = new ArrayList<>();
        listBanner = new ArrayList<>();
    }


    private void initRecyer() {
        manager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(manager);
        recycler.setNestedScrollingEnabled(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        showText();

    }


    private void showText() {
        isLogin = SPTool.getBoolean(getActivity(), Constants.ISLOGIN);
        userId = SPTool.getContent(getActivity(), Constants.USER_ID);
        getContentList();
        if (!isLogin) {
            roll_text.setVisibility(View.VISIBLE);
            roll_text.setText("好友动态一点便知，点击跳转至登录...");
            text_roll_two.setVisibility(View.GONE);
            return;
        }
        getRollText();
        if (SPTool.getInt(getActivity(), Constants.FIRSTTIMELOGON) == 1) {
            showInterestFieldDialog();
            SPTool.putInt(getActivity(), Constants.FIRSTTIMELOGON, 0);
        }
    }


    private void getBanner() {
        Call<BannerBean> call = communityService.getBanner();
        call.enqueue(new NetRetrofitCallback<BannerBean>(getActivity(), new HttpCallBackImpl<BannerBean>() {
            @Override
            public void onResponseCallback(BannerBean bannerBean) {
                if (bannerBean == null) {
                    return;
                }
                getBannerData(bannerBean.getContent());

            }
        }));
    }


    /**
     * 添加banner图
     *
     * @param contentBeans
     */
    private void getBannerData(List<BannerBean.ContentBean> contentBeans) {
        listBannerBean = contentBeans;
        for (int i = 0; i < contentBeans.size(); i++) {
            listBanner.add(contentBeans.get(i).getBannerUrl());
        }
        banner.setBannerStyle(1);
        banner.setImageLoader(new GlideImageLoader());  //设置图片加载器
        banner.setImages(listBanner);  //设置图片集合
        banner.isAutoPlay(true);  //设置自动轮播，默认为true
        banner.setDelayTime(3000);   //设置轮播时间
        banner.setIndicatorGravity(BannerConfig.RIGHT);//设置指示器位置（当banner模式中有指示器时）
        banner.setOnBannerListener(this);  //监听事件
        banner.start();//banner设置方法全部调用完毕时最后调用

    }


    /**
     * banner 图监听事件
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), BannerDetailsActivity.class);
        intent.putExtra(Constants.POSTID, listBannerBean.get(position).getBannerId());
        intent.putExtra(Constants.TITLE, listBannerBean.get(position).getTitle());
        getActivity().startActivity(intent);
    }


    /**
     * 添加导航栏
     */

    private void getNavigationBarData() {
        list = new ArrayList<>();
        list.add(new NavigationBean("服务管家", R.drawable.ic_jxsgl));
        list.add(new NavigationBean("新车交易", R.drawable.ic_xcjy));
        list.add(new NavigationBean("配件市场", R.drawable.ic_pjjy));
        list.add(new NavigationBean("问答专区", R.drawable.ic_wezq));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
        recyclerView.addItemDecoration(new RecycleViewDivider(ImageTool.dp2px(5f)));
        navigationbarAdapter = new NavigationbarAdapter(list, "homepage");
        recyclerView.setAdapter(navigationbarAdapter);
        navigationbarAdapter.addOnItemClickListener(this);
    }


    @Override
    public void onItemClick(int position) {
        Intent intent;
        switch (position) {
            case 0:
                if (!SPTool.getBoolean(getActivity(), Constants.ISLOGIN)) {
                    ActivityManger.skipActivity(getActivity(), ActivityLogin.class);
                    return;
                }
                String identityType = SPTool.getContent(getActivity(), Constants.IDENTITYTYPE);
                if ("10211003".equals(identityType)) {
                    ActivityManger.skipActivity(getActivity(), OperationManagerActivity.class);
                } else {
                    showAuthDialog();
                }
                break;
            case 1:
                intent = new Intent(getActivity(), ForumDetailsActivity.class);
                intent.putExtra(Constants.FORUMID, "1025");
                intent.putExtra(Constants.TITLE, "新车交易");
                intent.putExtra("from", "homepage");
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getActivity(), ForumDetailsActivity.class);
                intent.putExtra(Constants.FORUMID, "1026");
                intent.putExtra("from", "homepage");
                intent.putExtra(Constants.TITLE, "配件市场");
                startActivity(intent);
                break;
            case 3:
                ActivityManger.skipActivity(getActivity(), QaActivity.class);
                break;
        }
    }

    /**
     * 服务管家dialog
     */
    private void showAuthDialog() {
        dialogSureCancel = new DialogSureCancel(getActivity());
        dialogSureCancel.setCanceledOnTouchOutside(false);
        dialogSureCancel.setTitle("提示");
        dialogSureCancel.setContent("认证用友经销商才可使用该功能,如需认证点击确认即可");
        dialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManger.skipActivity(getActivity(), AuthenticationActivity.class);
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.show();
    }


    /**
     * 滚动信息条
     */
    private void getRollText() {
        Call<Roll> call = communityService.getFollowedUserTrace(userId);
        call.enqueue(new NetRetrofitCallback<Roll>(getActivity(), new HttpCallBackImpl<Roll>() {
            @Override
            public void onResponseCallback(Roll roll) {
                try {
                    if (roll.getReturnCode() != 0 && !roll.getReturnMsg().equals("success")) {
                        ToastUtils.normal(getActivity(), roll.getReturnMsg()).show();
                        return;
                    }
                    getRollData(roll);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    /**
     * 滚动条数据处理
     */
    private void getRollData(Roll model) {
        textList = model.getContent();
        arrayList = new ArrayList<>();
        roll_text.setVisibility(View.GONE);
        text_roll_two.setVisibility(View.VISIBLE);


        if (textList.size() == 0) {
            roll_text.setVisibility(View.VISIBLE);
            roll_text.setText("暂无好友动态哦");
            text_roll_two.setVisibility(View.GONE);
            return;
        }
        text_roll_two.setVisibility(View.VISIBLE);
        roll_text.setVisibility(View.GONE);
        for (int i = 0; i < textList.size(); i++) {
            Long time = TimeUtil.timeStrToSecond(textList.get(i).getTraceDate());
            arrayList.add(textList.get(i).getAction() + "" + "  " + TimeUtil.getSpaceTime(time));
        }
        text_roll_two.setTextList(arrayList);
        text_roll_two.setAnimTime(300);
        text_roll_two.setTextStillTime(3000);
        text_roll_two.setText(16, 5, getResources().getColor(R.color.color_lightbalank));
        text_roll_two.startAutoScroll();

        text_roll_two.setOnItemClickListener(new TextViewVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), FollowPopleAtivity.class);
                intent.putExtra(Constants.USER_ID, userId);
                startActivity(intent);
            }
        });

    }


    /**
     * 首页数据列表
     */
    private void getContentList() {
        Call<Follow> call = communityService.getPostListOnMainPage(userId, pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<Follow>(getActivity(), new HttpCallBackImpl<Follow>() {
            @Override
            public void onResponseCallback(Follow follow) {
                if (follow.getReturnCode() != 0 && !follow.getReturnMsg().equals("success")) {
                    ToastUtils.normal(follow.getReturnMsg());
                    return;
                }
                getContentData(follow.getContent());
            }
        }));
    }


    /**
     * 数据处理
     *
     * @param beanList
     */
    private void getContentData(List<Follow.ContentBean> beanList) {
        if (beanList.size() == 0) {
            if (pageNo == 1) {
                no_nrirong.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);

            } else {
                refreshlayout.finishLoadMoreWithNoMoreData();
            }
            refreshlayout.finishRefresh();
            refreshlayout.finishLoadMore();
            return;
        }
        recycler.setVisibility(View.VISIBLE);
        no_nrirong.setVisibility(View.GONE);
        if (pageNo == 1) {
            contentList.clear();
            contentList.addAll(beanList);
            refreshlayout.finishRefresh(true);
            productAdapter = new ProductAdapter(contentList, getActivity());
            recycler.setAdapter(productAdapter);

        } else {
            contentList.addAll(beanList);
            refreshlayout.finishLoadMore(true);
            productAdapter.notifyDataSetChanged();
        }

        productAdapter.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
                intent.putExtra(Constants.TITLE, contentList.get(position).getTypeDesc());
                intent.putExtra(Constants.POSTID, contentList.get(position).getPostId());
                startActivity(intent);
            }
        });
    }


    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.search_ray, R.id.add_image_lay, R.id.roll_text})
    public void onClick(View v) {
        if (!SPTool.getBoolean(getActivity(), Constants.ISLOGIN)) {
            ActivityManger.skipActivity(getActivity(), ActivityLogin.class);
            return;
        }
        Intent intent = null;
        switch (v.getId()) {
            case R.id.add_image_lay:
                showDiaglo();
                break;
            case R.id.search_ray:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type", "homepage");
                startActivity(intent);
                break;
            case R.id.roll_text:
                intent = new Intent(getActivity(), FollowPopleAtivity.class);
                intent.putExtra(Constants.USER_ID, userId);
                startActivity(intent);
                break;
        }
    }


    /**
     * 发帖 or发布话题
     */
    private void showDiaglo() {
        forumDialog = new ForumDialog(getActivity());
        forumDialog.setCanceledOnTouchOutside(false);
        forumDialog.setForunListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishPostActivity.class);
                intent.putExtra("title", "帖子");
                intent.putExtra("type", "10051002");        //帖子
                intent.putExtra(Constants.FORUMID, "");
                startActivity(intent);
                forumDialog.dismiss();
            }
        });

        forumDialog.setHuaTiListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PublishPostActivity.class);
                intent.putExtra("title", "话题");
                intent.putExtra("type", "10051001");        //话题
                intent.putExtra(Constants.FORUMID, "");
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


    /**
     * 兴趣选择
     */
    private void showInterestFieldDialog() {
        checklist = new ArrayList<>();
        follownum = 0;
        userId = SPTool.getContent(getActivity(), Constants.USER_ID);
        fieldDialog = new InterestFieldDialog(getActivity());
        fieldDialog.setCanceledOnTouchOutside(false);
        setAllForumsData();
        fieldDialog.setBtnSrueListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follownum == 0) {
                    ToastUtils.normal("请至少选择一个兴趣领域");
                    return;
                }
                fieldDialog.dismiss();
            }
        });
        fieldDialog.setExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follownum == 0) {
                    ToastUtils.normal("请至少选择一个兴趣领域");
                    return;
                }
                fieldDialog.dismiss();
            }
        });

        fieldDialog.show();
    }


    /**
     * 加载更多
     *
     * @param
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getContentList();
    }


    /**
     * 下拉刷新
     *
     * @param refreshlayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getContentList();
        if (isLogin) {
            getRollText();
        }
    }

    private void setAllForumsData() {
        checklist.clear();
        follownum = 0;
        Call<AllForums> call = communityService.getForumList();
        call.enqueue(new RetrofitCallback<AllForums>() {
            @Override
            public void onSuccess(AllForums model) {
                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    contentBeanList = model.getContent();
                    pageview = fieldDialog.getPageview();
                    pageAdapter = pageview.new PageAdapter(contentBeanList, new PageRecyclerView.CallBack() {
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_item_interestfield, parent, false);
                            RViewHolder holder = new RViewHolder(view);
                            return holder;
                        }

                        @Override
                        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                            RViewHolder viewHolder = (RViewHolder) holder;
                            GlideManager.loadImage(getActivity()
                                    , contentBeanList.get(position).getIcon()
                                    , R.drawable.logo1
                                    , viewHolder.iv_field);
                            viewHolder.tv_field.setText(contentBeanList.get(position).getForumName());
                            if (checklist.contains(position)) {
                                viewHolder.iv_checked.setVisibility(View.VISIBLE);
                            } else {
                                viewHolder.iv_checked.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onItemClickListener(View view, int position) {
                            ImageView iv = view.findViewById(R.id.iv_checked);
                            if (iv.getVisibility() == View.GONE) {
                                iv.setVisibility(View.VISIBLE);
                                getFollowForum(contentBeanList.get(position).getForumId());
                                follownum++;
                                checklist.add(position);
                            } else if (iv.getVisibility() == View.VISIBLE) {
                                iv.setVisibility(View.GONE);
                                getUnFollowForum(contentBeanList.get(position).getForumId());
                                follownum--;
                                checklist.remove(Integer.valueOf(position));
                            }
                        }

                        @Override
                        public void onItemLongClickListener(View view, int position) {

                        }
                    });
                    pageview.setAdapter(pageAdapter);
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {
                ToastUtils.normal(getActivity(), "服务器异常").show();
            }

            @Override
            public void onFinish() {

            }
        });
    }


    public class RViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_field)
        CircleImageView iv_field;
        @BindView(R.id.iv_checked)
        ImageView iv_checked;
        @BindView(R.id.tv_field)
        TextView tv_field;

        public RViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void getFollowForum(String forumId) {
        Call<HttpResult> call = communityService.getfollowForumr(userId, forumId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg().toString());
                    return;
                }
                ToastUtils.normal("已关注");
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void getUnFollowForum(String forumId) {
        Call<HttpResult> call = communityService.getunFollowForum(userId, forumId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg().toString());
                    return;
                }
                ToastUtils.normal("取消关注");
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
            }
        });
    }


    /**
     * 开始轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
        banner.stopAutoPlay();
        if (arrayList != null && arrayList.size() > 0) {
            text_roll_two.startAutoScroll();
        }

    }

    /**
     * 结束轮播
     */
    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        if (arrayList != null && arrayList.size() > 0) {
            text_roll_two.stopAutoScroll();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (forumDialog != null) {
            forumDialog.dismiss();
        }
    }
}
