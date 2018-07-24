package com.yonyou.friendsandaargang.info.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.info.bean.QaToMeBean;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/6/13.
 */

public class HuiDaAnwserActivity extends BaseActivity {


    @BindView(R.id.huida_image)
    CircleImageView imageView;
    @BindView(R.id.huida_name)
    TextView tvName;
    @BindView(R.id.huida_title)
    TextView tvTitle;
    @BindView(R.id.huida_content)
    EditText edContent;
    @BindView(R.id.huida_commit)
    TextView tvSure;

    private String replierId;
    private String postId;
    private QaToMeBean.ContentBean contentBean;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huidaanwser);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("回答问题");
        replierId = SPTool.getContent(mContext, Constants.USER_ID);
        GlideManager.loadImage(mContext
                , getIntent().getStringExtra("image")
                , R.drawable.user
                , imageView);
        tvName.setText(getIntent().getStringExtra("name"));
        tvTitle.setText(getIntent().getStringExtra("content"));
        postId = getIntent().getStringExtra(Constants.POSTID);
    }


    @OnClick(R.id.huida_commit)
    public void OnClick() {
        content = edContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.normal(mContext, "回答内容不能为空").show();
            return;
        }
        HuiDaAnswer(content);
    }


    /**
     * 回答问题
     *
     * @param string
     */
    private void HuiDaAnswer(String string) {
        Call<HttpResult> call = communityService().answerQuestionBS(
                string, replierId, postId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(this);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                finish();
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }
}
