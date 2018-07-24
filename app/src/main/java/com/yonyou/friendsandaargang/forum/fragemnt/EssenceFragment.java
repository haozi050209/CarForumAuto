package com.yonyou.friendsandaargang.forum.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.yonyou.friendsandaargang.forum.activirty.MoreHautiActivity;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.forum.adapter.EssenceAdapter;
import com.yonyou.friendsandaargang.forum.bean.EssenceList;
import com.yonyou.friendsandaargang.forum.bean.HuaTi;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.TextViewVerticalMore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/3/5.
 * 精华  fragemnt
 */

public class EssenceFragment extends Fragment implements
        View.OnClickListener,
        OnLoadMoreListener
        , OnRefreshListener, OnItemClickListener {
    View view = null;
    @BindView(R.id.no_huati_text)
    TextView no_huati;
    @BindView(R.id.essence_recylerview)
    RecyclerView recyclerView;
    @BindView(R.id.verticalmore)
    TextViewVerticalMore verticalmore;

    //精华adapter
    private EssenceAdapter essenceAdapter;

    private List<EssenceList.ContentBean> list;
    private List<HuaTi.ContentBean> contentBeans;
    private List<View> viewList;

    //第一个
    @BindView(R.id.ray1)
    RelativeLayout ray1;
    //第二个
    @BindView(R.id.ray2)
    RelativeLayout ray2;
    //第三个
    @BindView(R.id.ray3)
    RelativeLayout ray3;

    //第一个
    @BindView(R.id.huati_one_content)
    TextView huati_one_content;
    @BindView(R.id.huati_one_number)
    TextView huati_one_number;


    //第二个
    @BindView(R.id.huati_two_content)
    TextView huati_two_content;
    @BindView(R.id.huati_two_number)
    TextView huati_two_number;

    //第三个
    @BindView(R.id.huati_therr_content)
    TextView huati_therr_content;
    @BindView(R.id.huati_therr_number)
    TextView huati_therr_number;

    //只有3条信息的时候 显示这个布局
    @BindView(R.id.roll_layout)
    LinearLayout roll_layout;

    @BindView(R.id.no_tv)
    TextView no_tv;

    @BindView(R.id.essence_freshlayout)
    SmartRefreshLayout refreshLayout;


    private int pageSize = 8;
    private int pageNo = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_essence, null);
        ButterKnife.bind(this, view);
        initViews();
        initRrfesh();

        getEssenceList();

    }


    @Override
    public void onResume() {
        super.onResume();
        getHuatiData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }


    //recylerview
    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
    }


    //加载更多
    private void initRrfesh() {
        list = new ArrayList<>();
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()).setSpinnerStyle(FixedBehind));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setSpinnerStyle(FixedBehind));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }


    /**
     * 监听事件
     *
     * @param v
     */
    @Override
    @OnClick({R.id.huati_ray, R.id.ray1, R.id.ray2, R.id.ray3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.huati_ray:
                ActivityManger.skipActivity(getActivity(), MoreHautiActivity.class);
                break;
            case R.id.ray1:
                Intent intent1 = new Intent(getActivity(), PostDetailsActivity.class);
                intent1.putExtra(Constants.POSTID, contentBeans.get(0).getPostId());
                intent1.putExtra(Constants.TITLE, "话题");
                startActivity(intent1);
                break;
            case R.id.ray2:
                Intent intent2 = new Intent(getActivity(), PostDetailsActivity.class);
                intent2.putExtra(Constants.POSTID, contentBeans.get(1).getPostId());
                intent2.putExtra(Constants.TITLE, "话题");
                startActivity(intent2);
                break;
            case R.id.ray3:
                Intent intent3 = new Intent(getActivity(), PostDetailsActivity.class);
                intent3.putExtra(Constants.POSTID, contentBeans.get(2).getPostId());
                intent3.putExtra(Constants.TITLE, "话题");
                startActivity(intent3);
                break;
        }
    }


    /**
     * 获取热门话题
     */

    private void getHuatiData() {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<HuaTi> call = apiService.getHotTopicList();
        call.enqueue(new NetRetrofitCallback<HuaTi>(getActivity(), new HttpCallBackImpl<HuaTi>() {
            @Override
            public void onResponseCallback(HuaTi model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(model.getReturnMsg());
                    return;
                }
                initdatas(model);
                refreshLayout.finishRefresh();
            }
        }));
    }


    private void initdatas(HuaTi model) {
        contentBeans = model.getContent();
        if (contentBeans.size() == 0) {
            roll_layout.setVisibility(View.GONE);
            verticalmore.setVisibility(View.GONE);
            no_huati.setVisibility(View.VISIBLE);
            return;
        }
        if (contentBeans.size() <= 3) {
            roll_layout.setVisibility(View.VISIBLE);
            verticalmore.setVisibility(View.GONE);
            no_huati.setVisibility(View.GONE);
            if (contentBeans.size() == 1) {
                ray2.setVisibility(View.GONE);
                ray3.setVisibility(View.GONE);
                huati_one_content.setText(contentBeans.get(0).getTitle());
                huati_one_number.setText(contentBeans.get(0).getReplyNumber() + "人参与");
            }
            //等于2个
            else if (contentBeans.size() == 2) {
                ray3.setVisibility(View.GONE);
                huati_one_content.setText(contentBeans.get(0).getTitle());
                huati_one_number.setText(contentBeans.get(0).getReplyNumber() + "人参与");
                huati_two_content.setText(contentBeans.get(1).getTitle());
                huati_two_number.setText(contentBeans.get(1).getReplyNumber() + "人参与");
            }

            //等于3个
            else if (contentBeans.size() == 3) {
                ray2.setVisibility(View.VISIBLE);
                ray1.setVisibility(View.VISIBLE);
                ray3.setVisibility(View.VISIBLE);
                huati_one_content.setText(contentBeans.get(0).getTitle());
                huati_one_number.setText(contentBeans.get(0).getReplyNumber() + "人参与");
                huati_two_content.setText(contentBeans.get(1).getTitle());
                huati_two_number.setText(contentBeans.get(1).getReplyNumber() + "人参与");
                huati_therr_content.setText(contentBeans.get(2).getTitle());
                huati_therr_number.setText(contentBeans.get(2).getReplyNumber() + "人参与");
            }
            return;
        }

        if (contentBeans.size() > 3) {
            verticalmore.setVisibility(View.VISIBLE);
            roll_layout.setVisibility(View.GONE);
            no_huati.setVisibility(View.GONE);
            roll_layout.setVisibility(View.GONE);
            verticalmore.setVisibility(View.VISIBLE);
            List<View> viewList = new ArrayList<>();
            setUPMarqueeView(viewList);
            verticalmore.setViews(viewList);
            return;
        }
    }

    private void setUPMarqueeView(List<View> views) {

        int i;
        for (i = 0; i < contentBeans.size(); i = i + 3) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_essence_roll, null);
            //初始化布局控件
            //第一个
            TextView huati_one_content = (TextView) linearLayout.findViewById(R.id.huati_one_content);
            TextView huati_one_number = (TextView) linearLayout.findViewById(R.id.huati_one_number);
            //第2个
            TextView huati_two_content = (TextView) linearLayout.findViewById(R.id.huati_two_content);
            TextView huati_two_number = (TextView) linearLayout.findViewById(R.id.huati_two_number);
            //第3个
            TextView huati_therr_content = (TextView) linearLayout.findViewById(R.id.huati_therr_content);
            TextView huati_therr_number = (TextView) linearLayout.findViewById(R.id.huati_therr_number);

            RelativeLayout ray1 = (RelativeLayout) linearLayout.findViewById(R.id.ray1);
            RelativeLayout ray2 = (RelativeLayout) linearLayout.findViewById(R.id.ray2);
            RelativeLayout ray3 = (RelativeLayout) linearLayout.findViewById(R.id.ray3);

            //对控件进行填充数据
            huati_one_content.setText(contentBeans.get(i).getTitle());
            huati_one_number.setText(contentBeans.get(i).getReplyNumber() + "人参与");

            if (contentBeans.size() > i + 1) {
                huati_two_content.setText(contentBeans.get(i + 1).getTitle());
                huati_two_number.setText(contentBeans.get(i + 1).getReplyNumber() + "人参与");
            } else {
                ray2.setVisibility(View.GONE);
            }

            if (contentBeans.size() > i + 2) {
                huati_therr_content.setText(contentBeans.get(i + 2).getTitle());
                huati_therr_number.setText(contentBeans.get(i + 2).getReplyNumber() + "人参与");
            } else {
                ray3.setVisibility(View.GONE);
            }


            final int finalI = i;
            ray1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getActivity(), PostDetailsActivity.class);
                    intent1.putExtra(Constants.POSTID, contentBeans.get(finalI).getPostId());
                    intent1.putExtra(Constants.TITLE, "话题");
                    startActivity(intent1);
                }
            });
            ray2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(getActivity(), PostDetailsActivity.class);
                    intent2.putExtra(Constants.POSTID, contentBeans.get(finalI + 1).getPostId());
                    intent2.putExtra(Constants.TITLE, "话题");
                    startActivity(intent2);
                }
            });

            ray3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(getActivity(), PostDetailsActivity.class);
                    intent3.putExtra(Constants.POSTID, contentBeans.get(finalI + 2).getPostId());
                    intent3.putExtra(Constants.TITLE, "话题");
                    startActivity(intent3);
                }
            });
            views.add(linearLayout);
        }
    }


    /**
     * 获取精华帖子列表
     */
    private void getEssenceList() {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<EssenceList> call = apiService.getTheBestPostList(pageNo, pageSize);
        call.enqueue(new NetRetrofitCallback<EssenceList>(getActivity(), new HttpCallBackImpl<EssenceList>() {
            @Override
            public void onResponseCallback(EssenceList model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    return;
                }
                initEssenceDatas(model.getContent());
            }
        }));
    }


    /**
     * 精华列表
     */
    private void initEssenceDatas(List<EssenceList.ContentBean> contentBeanList) {
        if (contentBeanList.size() == 0) {
            if (pageNo == 1) {
                no_tv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
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
            list.addAll(contentBeanList);
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
            essenceAdapter = new EssenceAdapter(getActivity(), list);
            recyclerView.setAdapter(essenceAdapter);
        } else {
            list.addAll(contentBeanList);
            refreshLayout.finishLoadMore();
            essenceAdapter.notifyDataSetChanged(list);
        }
        essenceAdapter.addOnItemClickListener(this);
    }

    //上啦加载
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getEssenceList();
    }

    //下啦刷新
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getHuatiData();
        getEssenceList();

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
        intent.putExtra(Constants.TITLE, "帖子");
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        startActivity(intent);
    }
}
