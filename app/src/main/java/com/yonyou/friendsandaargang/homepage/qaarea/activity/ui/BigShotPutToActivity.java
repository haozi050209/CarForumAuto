package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.modle.BigShotInfoBean;
import com.yonyou.friendsandaargang.homepage.modle.BigshotAnwserListBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.BigshotAnwserListAdapter;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/6/5.
 */

public class BigShotPutToActivity extends BaseActivity implements OnItemClickListener {


    @BindView(R.id.bigputto_img)
    CircleImageView imageView;
    @BindView(R.id.bigputto_name)
    TextView tvName;
    @BindView(R.id.bigputto_content)
    TextView tvContent;
    @BindView(R.id.bigputto_smzx)
    TextView tvSmzx;
    @BindView(R.id.bigputto_smzx_xb)
    TextView tvSmzxXb;
    @BindView(R.id.bigputto_gktw)
    TextView tvGktw;
    @BindView(R.id.bigputto_gktw_xb)
    TextView tvGktwXb;
    @BindView(R.id.bigputto_edit)
    EditText editText;
    @BindView(R.id.bigputto_recylerview)
    RecyclerView recyclerView;
    @BindView(R.id.bigputto_noanwers)
    TextView tvNoAnwers;

    private LinearLayoutManager manager;

    private String userId;
    private String bigshotId;
    private int isPrivate;     //公开提问还是私密咨询
    private String forumId;

    private List<BigshotAnwserListBean.ContentBean> list;
    private BigshotAnwserListAdapter adapter;
    private String cold;    //金币值
    private int rewardCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigshotputto);
        ButterKnife.bind(this);
        initviews();
        defaultLabel();
        getBiaShotInfo();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("大咖提问");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        bigshotId = getIntent().getStringExtra(Constants.BIGSHOTID);
        cold = SPTool.getContent(mContext, Constants.GOLD);
    }

    /**
     * 默认标签
     */
    private void defaultLabel() {
        //默认是公开提问
        isPrivate = 0;
        tvGktwXb.setVisibility(View.VISIBLE);
        editText.setHint(R.string.gktw);
        tvGktw.setTextColor(getResources().getColor(R.color.color_red));
        tvSmzx.setTextColor(getResources().getColor(R.color.color_title_main));
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @OnClick({R.id.bigputto_gktw, R.id.bigputto_smzx, R.id.bigputto_tiwen})
    public void OnClisk(View view) {
        switch (view.getId()) {
            //公开提问
            case R.id.bigputto_gktw:
                isPrivate = 0;
                tvGktwXb.setVisibility(View.VISIBLE);
                tvSmzxXb.setVisibility(View.GONE);
                editText.setHint(R.string.gktw);
                recyclerView.setVisibility(View.VISIBLE);
                tvSmzx.setTextColor(getResources().getColor(R.color.color_title_main));
                tvGktw.setTextColor(getResources().getColor(R.color.color_red));
                break;
            //私密咨询
            case R.id.bigputto_smzx:
                isPrivate = 1;
                tvGktwXb.setVisibility(View.GONE);
                tvSmzxXb.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                editText.setHint(R.string.smzx);
                editText.setText("");
                tvSmzx.setTextColor(getResources().getColor(R.color.color_red));
                tvGktw.setTextColor(getResources().getColor(R.color.color_title_main));
                break;
            //提问
            case R.id.bigputto_tiwen:
                String title = editText.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.normal(mContext, "请填写问题在提问").show();
                    return;
                }
               /* if (cold < rewardCoin) {
                    ToastUtils.normal(mContext, "您的钱包余额不足，请去充值").show();
                    return;
                }*/
                if (userId.equals(bigshotId)) {
                    ToastUtils.normal(mContext, "自己不能向自己提问哦").show();
                    return;
                }
                if (TextUtils.isEmpty(forumId)) {
                    ToastUtils.normal(mContext, "提问失败 请重试").show();
                    return;
                }
                getRewardToBS(title);
                break;
        }
    }


    /**
     * 大咖简介
     */
    private void getBiaShotInfo() {
        Call<BigShotInfoBean> call = communityService().getBigShotInfo(bigshotId);
        NetUtils<BigShotInfoBean> netUtils = new NetUtils<BigShotInfoBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<BigShotInfoBean>() {
            @Override
            public void onResponseCallback(BigShotInfoBean bigShotInfoBean) {
                if (bigShotInfoBean.getReturnCode() != 0 && !bigShotInfoBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, bigShotInfoBean.getReturnMsg()).show();
                    return;
                }
                Glide.with(mContext)
                        .load(bigShotInfoBean.getContent().getAvatar())
                        .error(R.drawable.user)
                        .into(imageView);
                tvName.setText(bigShotInfoBean.getContent().getNickname());
                tvContent.setText(bigShotInfoBean.getContent().getBigshotDesc());
                forumId = bigShotInfoBean.getContent().getForumId();
                rewardCoin = bigShotInfoBean.getContent().getRewardCoin();
                //先获取到提问信息后在获取列表信息


                getBigshotAnwserList();

            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    /**
     * 向大咖提问
     */
    private void getRewardToBS(String title) {
        Call<HttpResult> call = communityService().postRewardToBS(userId
                , bigshotId
                , "10051003"
                , title
                , forumId
                , "0"
                , isPrivate);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {

                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "已提问").show();
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    private void getBigshotAnwserList() {
        Call<BigshotAnwserListBean> call = communityService().getBigshotAnwserList(bigshotId
                , userId
                , 1
                , 100);
        call.enqueue(new NetRetrofitCallback<BigshotAnwserListBean>(mContext, new HttpCallBackImpl<BigshotAnwserListBean>() {
            @Override
            public void onResponseCallback(BigshotAnwserListBean bigshotListBean) {
                if (bigshotListBean.getReturnCode() != 0 && !bigshotListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, bigshotListBean.getReturnMsg()).show();
                    return;
                }
                BigShotAnwserData(bigshotListBean);
            }
        }));
    }


    private void BigShotAnwserData(BigshotAnwserListBean bigshotListBean) {
        list = bigshotListBean.getContent();
        if (list.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            tvNoAnwers.setVisibility(View.VISIBLE);
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new BigshotAnwserListAdapter(mContext, list, "bigshotPutTo");
        adapter.addOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }


    /**
     * 列表监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mContext, BigshotQuestionActivity.class);
        intent.putExtra(Constants.POSTID, list.get(position).getPostId());
        startActivity(intent);
    }
}
