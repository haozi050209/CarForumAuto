package com.yonyou.friendsandaargang.info.activity.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.guide.adapter.ViewPagerAdapter;
import com.yonyou.friendsandaargang.info.bean.QaUserIfonBean;
import com.yonyou.friendsandaargang.info.fragment.QaBigAnswerFragemnt;
import com.yonyou.friendsandaargang.info.fragment.QaBigTomeFragemnt;
import com.yonyou.friendsandaargang.info.fragment.QaUserAnswerFragemnt;
import com.yonyou.friendsandaargang.info.fragment.QaUserSeeFragemnt;
import com.yonyou.friendsandaargang.info.fragment.QaUserToMeFragemnt;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.MyViewPager;
import com.yonyou.friendsandaargang.view.dialog.DialogEditSureCancel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的问答
 */

public class MyQAActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MyQAActivity";
    private String userId;
    @BindView(R.id.myqa_img)
    CircleImageView imageView;
    @BindView(R.id.myqa_name)
    TextView tvName;
    @BindView(R.id.myqa_beizhu)
    TextView tvBeiz;
    @BindView(R.id.myqa_sy)
    TextView tvShouru;
    @BindView(R.id.myqa_jiage)
    TextView tvJiage;
    @BindView(R.id.myqa_tablay)
    XTabLayout tabLayout;
    @BindView(R.id.myqa_recyl)
    RecyclerView recycl;
    @BindView(R.id.myqa_tv)
    TextView textView;
    @BindView(R.id.titleBar_right_text)
    TextView tvRight;
    @BindView(R.id.myqa_viewpage)
    ViewPager viewPager;
    @BindView(R.id.myqa_viewpage_big)
    ViewPager bigViewpage;

    private int falg;
    private String identityType;
    private String identityInfo;
    private DialogEditSureCancel dialog;
    private String edContent;    //内容
    private List<String> bigshotTab;
    private List<String> qUserTab;
    private List<String> qBigshotTab;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;


    private QaBigTomeFragemnt bigTomeFragemnt;//大咖身份 问我的问题
    private QaBigAnswerFragemnt bigAnswerFragemnt; //大咖身份  回答
    private QaUserToMeFragemnt userToMeFragemnt;   //我的提问
    private QaUserAnswerFragemnt userAnswerFragemnt; //我的回答
    private QaUserSeeFragemnt userSeeFragemnt;       //我的查看


    private List<Fragment> defaultList;
    private List<Fragment> userList;
    private List<Fragment> bigList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myqa);
        ButterKnife.bind(this);
        initviews();
        getQaMainInfo();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initviews() {
        getTitleBar();
        identityType = getIntent().getStringExtra("identityType");
        identityInfo = getIntent().getStringExtra("identityInfo");
        if (identityInfo.equals("3")) {
            setTitleText("我的问答").rightTv("切换身份");
        } else {
            setTitleText("我的问答");
        }
        userId = SPTool.getContent(mContext, Constants.USER_ID);

        qBigshotTab = new ArrayList<>();
        bigshotTab = new ArrayList<>();
        qUserTab = new ArrayList<>();


        //fragenmt
        defaultList = new ArrayList<>();
        userList = new ArrayList<>();
        bigList = new ArrayList<>();

        bigTomeFragemnt = new QaBigTomeFragemnt();
        bigAnswerFragemnt = new QaBigAnswerFragemnt();
        userToMeFragemnt = new QaUserToMeFragemnt();
        userAnswerFragemnt = new QaUserAnswerFragemnt();
        userSeeFragemnt = new QaUserSeeFragemnt();
    }


    @OnClick({R.id.titleBar_right_text, R.id.myqa_jiage, R.id.myqa_beizhu})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.titleBar_right_text:
                if (identityType.equals("10211002")) {
                    switchIdentity("10211001");    // 点击切换到个人
                } else {
                    switchIdentity("10211002");    // 点击切换到大咖
                }
                break;
            case R.id.myqa_beizhu:
                falg = 1;
                showDialog();
                break;
            case R.id.myqa_jiage:
                falg = 2;
                showDialog();
                break;

        }

    }


    //问答个人主页（上方）用户信息
    private void getQaMainInfo() {
        Call<QaUserIfonBean> call = communityService().getUserInfoOnSelfMainPage(userId, identityType);
        NetUtils<QaUserIfonBean> netUtils = new NetUtils<QaUserIfonBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<QaUserIfonBean>() {
            @Override
            public void onResponseCallback(QaUserIfonBean qaUserIfonBean) {
                if (qaUserIfonBean.getReturnCode() != 0 &&
                        !qaUserIfonBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, qaUserIfonBean.getReturnMsg()).show();
                    return;
                }
                getQaMainInfoData(qaUserIfonBean);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    //获取个人信息数据处理
    private void getQaMainInfoData(QaUserIfonBean qaUserIfonBean) {
        GlideManager.loadImage(mContext, qaUserIfonBean.getContent().getAvatar()
                , R.drawable.user, imageView);

        tvName.setText(qaUserIfonBean.getContent().getNickname());
        if (TextUtils.isEmpty(qaUserIfonBean.getContent().getBigshotDesc())) {
            tvBeiz.setText("暂无简介");
        } else {
            tvBeiz.setText(qaUserIfonBean.getContent().getBigshotDesc());
        }

        /*tvShouru.setText("总收入 ¥" + qaUserIfonBean.getContent().getCoin()
                + "        回答" + qaUserIfonBean.getContent().getPublicNum() + "");*/
        tvShouru.setText("回答 " + qaUserIfonBean.getContent().getPublicNum() + "");
        tvJiage.setText("提问价格¥" + qaUserIfonBean.getContent().getRewardCoin());

        //如果是个人身份
        if (identityType.equals("10211001")) {
            tvBeiz.setVisibility(View.GONE);
            tvJiage.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
        if (bigshotTab.size() > 0) {
            bigshotTab.clear();
        }
        if (qUserTab.size() > 0) {
            qUserTab.clear();
        }
        if (qBigshotTab.size() > 0) {
            qBigshotTab.clear();
        }

        if (identityType.equals("10211002")) {
            bigshotTab.add("问我的问题");
            bigshotTab.add("我的回答");
            initTab(bigshotTab, identityType);
        } else {
            bigshotTab.add("我的提问");
            bigshotTab.add("我的回答");
            bigshotTab.add("我的查看");
            initTab(bigshotTab, identityType);
        }

    }


    private void initTab(List<String> userTab, String type) {
        fragmentList = new ArrayList<>();

        tabLayout.removeAllTabs();
        tabLayout.setxTabDisplayNum(userTab.size());
        for (int i = 0; i < userTab.size(); i++) {
            this.tabLayout.addTab(tabLayout.newTab().setText(userTab.get(i)));
        }
        if (type.equals("10211002")) {
            viewPager.setVisibility(View.GONE);
            bigViewpage.setVisibility(View.VISIBLE);
            fragmentList.add(bigTomeFragemnt);
            fragmentList.add(bigAnswerFragemnt);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, userTab);
            bigViewpage.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(bigViewpage);

        } else {
            bigViewpage.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            fragmentList.add(userToMeFragemnt);
            fragmentList.add(userAnswerFragemnt);
            fragmentList.add(userSeeFragemnt);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, userTab);
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
        //  viewPager.addOnPageChangeListener(this);


    }


    private void showDialog() {
        dialog = new DialogEditSureCancel(mContext);
        dialog.setCancelable(false);
        if (falg == 1) {
            dialog.setTitle("修改简介");
            dialog.setContent("最多只能输入15个字符哦");
            dialog.setEditText("");
        } else {
            dialog.setTitle("修改提问价格");
            dialog.setContent("最高只能修改100哦");
            dialog.setEditText("请输入提问价格");
        }
        dialog.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = dialog.getEditText().getText().toString().trim();

                if (falg == 1) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.normal(mContext, "简介不能为空哦").show();
                        return;
                    }
                    modifyBigshotDesc(content);
                } else {
                    modifyBSRewardCoin(Integer.parseInt(dialog.getEditText().getText().toString()));
                }
                dialog.dismiss();
            }
        });


        //取消
        dialog.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 修改大咖简介
     *
     * @param desc
     */
    private void modifyBigshotDesc(String desc) {
        Call<HttpResult> call = communityService().saveBigshotDesc(userId, desc);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getQaMainInfo();
            }
        }));
    }


    /**
     * 修改提问价格
     *
     * @param desc
     */
    private void modifyBSRewardCoin(int desc) {
        Call<HttpResult> call = communityService().updateBSRewardCoin(userId, desc);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getQaMainInfo();
            }
        }));
    }


    /**
     * 切换身份
     */

    private void switchIdentity(final String type) {
        Call<HttpResult> call = communityService().switchIdentity(userId, type);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (type.equals("10211001")) {   //大咖 切换到个人
                    identityType = "10211001";
                    tvBeiz.setVisibility(View.GONE);
                    tvJiage.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    //添加tab
                    if (bigshotTab.size() > 0) {
                        bigshotTab.clear();
                    }
                    if (qBigshotTab.size() > 0) {
                        qBigshotTab.clear();
                    }

                    if (defaultList.size() > 0) {
                        defaultList.clear();
                    }
                    if (bigList.size() > 0) {
                        bigList.clear();
                    }

                    qUserTab.add("我的提问");
                    qUserTab.add("我的回答");
                    qUserTab.add("我的查看");

                    userList.add(userToMeFragemnt);
                    userList.add(userAnswerFragemnt);
                    userList.add(userSeeFragemnt);

                    initTab(qUserTab, identityType);

                } else {

                    identityType = "10211002";   // 个人切换到大咖
                    tvBeiz.setVisibility(View.VISIBLE);
                    tvJiage.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    tvJiage.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    //添加tab
                    if (qUserTab.size() > 0) {
                        qUserTab.clear();

                    }
                    if (bigshotTab.size() > 0) {
                        bigshotTab.clear();
                    }

                    if (defaultList.size() > 0) {
                        defaultList.clear();
                    }

                    if (userList.size() > 0) {
                        userList.clear();
                    }

                    qBigshotTab.add("问我的问题");
                    qBigshotTab.add("我的回答");
                    bigList.add(bigTomeFragemnt);
                    bigList.add(bigAnswerFragemnt);
                    initTab(qBigshotTab, identityType);
                }
            }
        }));

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
