package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.modle.QuestionDetalisBean;
import com.yonyou.friendsandaargang.homepage.modle.QuestionInfoBean;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by shibing on 18/6/5.
 */

public class BigshotQuestionActivity extends BaseActivity {


    //提问者
    @BindView(R.id.bigquset_user_img)
    CircleImageView userImg;
    @BindView(R.id.bigquset_user_name)
    TextView userName;
    @BindView(R.id.bigquset_user_money)
    TextView userMoney;
    @BindView(R.id.bigquset_user_content)
    TextView userContent;
    @BindView(R.id.bigquset_user_tiem)
    TextView userTime;
    //回答者
    @BindView(R.id.bigquset_biger_img)
    CircleImageView bigerImg;
    @BindView(R.id.bigquset_biger_name)
    TextView bigerName;
    @BindView(R.id.bigquset_biger_forum)
    TextView bigerForum;
    @BindView(R.id.bigquset_biger_sm)
    TextView bigerSinger;
    @BindView(R.id.bigquset_biger_anwser)
    TextView bigerAnwser;
    @BindView(R.id.bigquset_biger_zhifu)
    TextView bigerZhifu;

    @BindView(R.id.biger_lay)
    LinearLayout layNum;
    @BindView(R.id.biger_num_tv)
    TextView tvNum;

    private String bigshotId;
    private String userId;
    private String postId;
    private String content;
    private String form;
    private Long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biashotquest);
        ButterKnife.bind(this);
        initviews();

    }

    private void initviews() {
        getTitleBar();
        setTitleText("问答详情");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        postId = getIntent().getStringExtra(Constants.POSTID);
        form = getIntent().getStringExtra("from");
        if (TextUtils.isEmpty(form)) {
            layNum.setVisibility(View.VISIBLE);
        } else {
            layNum.setVisibility(View.VISIBLE);
        }
        getQuestionUserInfo();

    }


    @OnClick({R.id.bigquset_biger_zhifu, R.id.biger_lay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bigquset_biger_zhifu:
                payForBigshotQuestion();
                break;
            case R.id.biger_lay:
                Intent intent = new Intent(mContext, BigShotPutToActivity.class);
                intent.putExtra(Constants.BIGSHOTID, bigshotId);
                startActivity(intent);
                break;
        }

    }


    private void getQuestionUserInfo() {
        Call<QuestionInfoBean> call = communityService().getQuestionUserInfo(userId, postId);
        NetUtils<QuestionInfoBean> netUtils = new NetUtils<QuestionInfoBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<QuestionInfoBean>() {
            @Override
            public void onResponseCallback(QuestionInfoBean infoBean) {
                if (infoBean.getReturnCode() != 0 && !infoBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, infoBean.getReturnMsg()).show();
                    return;
                }
                Glide.with(mContext)
                        .load(infoBean.getContent().getAvatar())
                        .error(R.drawable.user)
                        .into(userImg);
                userName.setText(infoBean.getContent().getViewerNickname());
                userContent.setText(infoBean.getContent().getTitle());
                time = TimeUtil.timeStrToSecond(infoBean.getContent().getPostDate());
                userTime.setText(TimeUtil.getSpaceTime(time));

                BigshotQuestionDetalis();
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    /**
     * 回答者详情
     */
    private void BigshotQuestionDetalis() {
        Call<QuestionDetalisBean> call = communityService().getBigshotInfoOnQuestionDetail(userId, postId);
        call.enqueue(new NetRetrofitCallback<QuestionDetalisBean>(mContext, new HttpCallBackImpl<QuestionDetalisBean>() {
            @Override
            public void onResponseCallback(QuestionDetalisBean detalisBean) {
                try {
                    if (detalisBean.getReturnCode() != 0 && !detalisBean.getReturnMsg().equals("success")) {
                        ToastUtils.normal(mContext, detalisBean.getReturnMsg()).show();
                        return;
                    }
                    //回答者的信息
                    GlideManager.loadImage(mContext, detalisBean.getContent().get(0).getAvatar()
                            , R.drawable.user, bigerImg);


                    bigerName.setText(detalisBean.getContent().get(0).getNickname());
                    bigerForum.setText(detalisBean.getContent().get(0).getForumName());
                    bigerSinger.setText(detalisBean.getContent().get(0).getBigshotDesc());
                    content = detalisBean.getContent().get(0).getContent();
                    bigerAnwser.setText(detalisBean.getContent().get(0).getContent());
                    tvNum.setText(detalisBean.getContent().get(0).getNickname() + "的全部"
                            + detalisBean.getContent().get(0).getAnswerNum() + "个回答");
                    tvNum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_jiantou, 0);
                    bigshotId = detalisBean.getContent().get(0).getUserId();
                    if (detalisBean.getContent().get(0).getIsPay() == 1) {
                        bigerAnwser.setVisibility(View.VISIBLE);
                        bigerAnwser.setText(detalisBean.getContent().get(0).getContent());
                        bigerZhifu.setVisibility(View.GONE);
                    } else {
                        bigerZhifu.setVisibility(View.VISIBLE);
                        bigerAnwser.setVisibility(View.GONE);
                        bigerZhifu.setText("立即查看");
                        bigerZhifu.setBackgroundResource(R.drawable.qabj);
                        bigerZhifu.setCompoundDrawablesWithIntrinsicBounds(R.drawable.qa, 0, 0, 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }


    /**
     * 支付学习一下
     */

    private void payForBigshotQuestion() {
        Call<HttpResult> call = communityService().payForBigshotQuestion(userId, postId, 0);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    bigerZhifu.setVisibility(View.VISIBLE);
                    bigerAnwser.setVisibility(View.GONE);
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                bigerZhifu.setVisibility(View.GONE);
                bigerAnwser.setVisibility(View.VISIBLE);
                bigerAnwser.setText(content);


            }

            @Override
            public void onFailureCallback() {

            }
        });
    }
}
