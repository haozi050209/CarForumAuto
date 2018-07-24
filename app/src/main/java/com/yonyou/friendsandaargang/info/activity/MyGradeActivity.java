package com.yonyou.friendsandaargang.info.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.adapter.NavigationbarAdapter;
import com.yonyou.friendsandaargang.homepage.modle.NavigationBean;
import com.yonyou.friendsandaargang.info.activity.grade.GradeQuanYiActivity;
import com.yonyou.friendsandaargang.info.activity.grade.GradeQuanYiTherrActivity;
import com.yonyou.friendsandaargang.info.activity.grade.GradeQuanYiTwoActivity;
import com.yonyou.friendsandaargang.info.activity.grade.GradeQuanYiforeActivity;
import com.yonyou.friendsandaargang.info.bean.UserLevel;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.GrowthValueProgress;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 */

public class MyGradeActivity extends BaseActivity {

    @BindView(R.id.grade_recyler)
    RecyclerView grade_recyler;
    @BindView(R.id.grade_user_head)
    CircleImageView grade_user_head;
    @BindView(R.id.grade_name)
    TextView grade_name;
    @BindView(R.id.grade_text)
    TextView grade_text;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.grade_hg_fay)
    FrameLayout frameLayout;


    @BindView(R.id.tv0)
    TextView tv_v0;
    @BindView(R.id.tv_v1)
    TextView tv_v1;
    @BindView(R.id.tv_v2)
    TextView tv_v2;
    @BindView(R.id.tv_v3)
    TextView tv_v3;
    @BindView(R.id.tv_v4)
    TextView tv_v4;


    @BindView(R.id.dj_image1)
    ImageView imageView1;
    @BindView(R.id.dj_image2)
    ImageView imageView2;
    @BindView(R.id.dj_image3)
    ImageView imageView3;


    private ArrayList list;
    private NavigationbarAdapter navigationbarAdapter;


    private String uesrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grade);
        ButterKnife.bind(this);
        initviews();
        getUserLevel();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("我的等级").rightTv("明细");
        uesrId = SPTool.getContent(mContext, Constants.USER_ID);
        //getNavigationBarData();
    }

    @OnClick({R.id.titleBar_right_text, R.id.tv_lay1, R.id.tv_lay2, R.id.tv_lay3})
    public void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.titleBar_right_text:

                ActivityManger.skipActivity(mContext, RewardDetailsActivity.class);
                break;
            case R.id.tv_lay1:

                intent = new Intent(mContext, GradeQuanYiActivity.class);
                intent.putExtra("from", "quanyi1");
                startActivity(intent);
                break;
            case R.id.tv_lay2:
                intent = new Intent(mContext, GradeQuanYiActivity.class);
                intent.putExtra("from", "quanyi2");
                startActivity(intent);

                break;
            case R.id.tv_lay3:
                intent = new Intent(mContext, GradeQuanYiActivity.class);
                intent.putExtra("from", "quanyi3");
                startActivity(intent);
                break;
        }

    }


    private void getNavigationBarData() {
        list = new ArrayList<>();
        list.add(new NavigationBean("财富值兑换\nV1可享", R.drawable.cfz, GradeQuanYiActivity.class));
        list.add(new NavigationBean("专属活动\nV2可享", R.drawable.ashd, GradeQuanYiActivity.class));
        list.add(new NavigationBean("生日特权\nV2可享", R.drawable.srtq, GradeQuanYiTwoActivity.class));
        list.add(new NavigationBean("客服特权\nV3可享", R.drawable.kftq, GradeQuanYiTherrActivity.class));
        list.add(new NavigationBean("财富值翻倍\nV3可享", R.drawable.fb, GradeQuanYiforeActivity.class));
        grade_recyler.setLayoutManager(new GridLayoutManager(MyGradeActivity.this, 5));
        navigationbarAdapter = new NavigationbarAdapter(list, "");
        grade_recyler.setAdapter(navigationbarAdapter);

    }

    /**
     * 获取用户等级
     */

    private void getUserLevel() {
        Call<UserLevel> call = communityService().getlevelgetUserLevel(uesrId);
        NetUtils<UserLevel> netUtils = new NetUtils<UserLevel>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<UserLevel>() {
            @Override
            public void onResponseCallback(UserLevel userLevel) {
                if (userLevel.getReturnCode() != 0 && !userLevel.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, userLevel.getReturnMsg()).show();
                    return;
                }
                GradeData(userLevel);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    private void GradeData(UserLevel userLevel) {
        GlideManager.loadImage(mContext, userLevel.getContent().getAvatar(), R.drawable.user, grade_user_head);
        grade_name.setText(userLevel.getContent().getNickname());
        tv_text.setText("还需" + userLevel.getContent().getToNext() + "分可升级");
        progress.setProgress(userLevel.getContent().getExp());
        if (userLevel.getContent().getLevelId().equals("0")) {
            frameLayout.setVisibility(View.GONE);
        } else {
            grade_text.setText(userLevel.getContent().getLevelId());
        }


        switch (userLevel.getContent().getLevelId()) {
            case "1":
                imageView1.setImageResource(R.drawable.cfz);
                break;
            case "2":
                imageView2.setImageResource(R.drawable.ic_zshd);
                break;
            case "3":
                imageView3.setImageResource(R.drawable.ic_group);
                break;
            case "4":
                break;
        }


        if (userLevel.getContent().getExp() > 0 && userLevel.getContent().getExp() < 300) {
            tv_v0.setTextColor(getResources().getColor(R.color.color_white));
            tv_v0.setBackgroundResource(R.drawable.oval);
            return;
        }

        if (userLevel.getContent().getExp() > 300 && userLevel.getContent().getExp() < 1075) {
            tv_v0.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v0.setBackgroundResource(0);
            tv_v1.setTextColor(getResources().getColor(R.color.color_white));
            tv_v1.setBackgroundResource(R.drawable.oval);
            return;
        }


        if (userLevel.getContent().getExp() > 1075 && userLevel.getContent().getExp() < 4000) {
            tv_v0.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v0.setBackgroundResource(0);
            tv_v1.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v1.setBackgroundResource(0);
            tv_v2.setTextColor(getResources().getColor(R.color.color_white));
            tv_v2.setBackgroundResource(R.drawable.oval);
            return;
        }


        if (userLevel.getContent().getExp() > 6000 && userLevel.getContent().getExp() < 25560) {
            tv_v0.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v0.setBackgroundResource(0);
            tv_v1.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v1.setBackgroundResource(0);
            tv_v2.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v2.setBackgroundResource(0);
            tv_v3.setTextColor(getResources().getColor(R.color.color_white));
            tv_v3.setBackgroundResource(R.drawable.oval);
            return;
        }


        if (userLevel.getContent().getExp() > 25560) {
            tv_v0.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v0.setBackgroundResource(0);
            tv_v1.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v1.setBackgroundResource(0);
            tv_v2.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v2.setBackgroundResource(0);
            tv_v3.setTextColor(getResources().getColor(R.color.color_balank));
            tv_v3.setBackgroundResource(0);
            tv_v4.setTextColor(getResources().getColor(R.color.color_white));
            tv_v4.setBackgroundResource(R.drawable.oval);
            return;
        }









       /* switch (userLevel.getContent().getExp()) {
                        *//*v1Values = 300;//v1会员成长值
                        v2Values = 10742;//v2会员成长值
                        v3Values = 15556;//v3会员成长值
                        v4Values = 25556;//v4会员成长值*//*


            case 100:
                tv_v1.setTextColor(getResources().getColor(R.color.color_white));
                tv_v1.setBackgroundResource(R.drawable.oval);
                break;
            case 1075:
                tv_v2.setTextColor(getResources().getColor(R.color.color_white));
                tv_v2.setBackgroundResource(R.drawable.oval);
                break;
            case 15559:
                tv_v3.setTextColor(getResources().getColor(R.color.color_white));
                tv_v3.setBackgroundResource(R.drawable.oval);
                break;
            case 26560:
                tv_v4.setTextColor(getResources().getColor(R.color.color_white));
                tv_v4.setBackgroundResource(R.drawable.oval);
                break;
        }*/
    }

}
