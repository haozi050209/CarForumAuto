package com.yonyou.friendsandaargang.info.activity.operationmanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ServiceRatingActivity extends BaseActivity {

    @BindView(R.id.tv_infoccRdid)
    TextView tvInfoccRdid;
    @BindView(R.id.tv_recordContent)
    TextView tvRecordContent;
    @BindView(R.id.rb_total)
    RatingBar rbTotal;
    @BindView(R.id.rb_speed)
    RatingBar rbSpeed;
    @BindView(R.id.rb_quality)
    RatingBar rbQuality;
    @BindView(R.id.rb_service)
    RatingBar rbService;
    @BindView(R.id.et_opinion)
    EditText etOpinion;

    private String score;
    private String spscore;
    private String quscore;
    private String srscore;
    private String infoccRdid;
    private String opinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_rating);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        getTitleBar();
        setTitleText("服务评价");
        infoccRdid = getIntent().getStringExtra("infoccRdid");
        tvInfoccRdid.setText("问题编号：" + infoccRdid);
        tvRecordContent.setText("问题描述：" + getIntent().getStringExtra("recordContent"));
    }

    private void initListener() {
        rbTotal.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = (int)rating + "";
            }
        });
        rbSpeed.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                spscore = (int)rating + "";
            }
        });
        rbQuality.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                quscore = (int)rating + "";
            }
        });
        rbService.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                srscore = (int)rating + "";
            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        opinion = etOpinion.getText().toString().trim();
        if (infoccRdid == null) {
            ToastUtils.normal("问题编号不能为空");
            return;
        }
        if (score == null || spscore == null || quscore == null || srscore == null) {
            ToastUtils.normal("请进行评分");
            return;
        }
        saveSatisfactionDegree();
    }

    private void saveSatisfactionDegree() {
        Call<HttpResult> call = communityService().saveSatisfactionDegree(score, spscore, quscore, srscore, infoccRdid, opinion);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "评价成功").show();
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

            }
        });
    }
}
