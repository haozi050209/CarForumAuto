package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.homepage.modle.QAReplyLisBean;
import com.yonyou.friendsandaargang.homepage.modle.QaDetalisBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.QAReplyListAdapter;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.MyListView;
import com.yonyou.friendsandaargang.view.NoScrollWebView;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;
import com.yonyou.friendsandaargang.view.dialog.ShareBottomDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/6/1.
 */

public class QaDetalisActivity extends BaseActivity implements OnItemClickListener, TextView.OnEditorActionListener {
    @BindView(R.id.qa_detalis_image)
    CircleImageView imageView;
    @BindView(R.id.qa_detalis_name)
    TextView tvName;
    @BindView(R.id.qa_detalis_tiem)
    TextView tvTime;
    @BindView(R.id.qa_detalis_money)
    TextView tvMoney;
    @BindView(R.id.qa_detalis_content)
    TextView tvContent;
    @BindView(R.id.qa_detalis_webview)
    NoScrollWebView webView;
    @BindView(R.id.qa_detalis_list)
    MyListView listView;
    @BindView(R.id.qedetalis_nohuida)
    TextView tvNohuidTitle;
    @BindView(R.id.qadetalis_no_reply)
    TextView tvNoreply;
    @BindView(R.id.qa_detalis_answer_ray)
    RelativeLayout rayAnswer;
    @BindView(R.id.qa_detalis_answer_ed)
    EditText edAnswer;              //回答问题


    private String webPath, shareUrl, postId, userId, content, isCanAnswer, AnswerName, publisherId;
    private QaDetalisBean.ContentBean detalisBean;
    private List<QAReplyLisBean.ContentBean> listReply;
    private QAReplyListAdapter replyListAdapter;

    private int hours, minute, IsSelected, thumbsCall;
    private boolean isLogin;
    private Long time;
    private ShareBottomDialog bottomDialog;
    private DialogSureCancel dialogSureCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qadetalis);
        ButterKnife.bind(this);
        initviews();
        getQaDetalisInfo();

    }

    private void initviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        postId = getIntent().getStringExtra(Constants.POSTID);
        isLogin = SPTool.getBoolean(mContext, Constants.ISLOGIN);
        getTitleBar();
        putEdAnswer();
    }


    private void putEdAnswer() {
        if (!isLogin) {
            edAnswer.setFocusableInTouchMode(false);
            edAnswer.setFocusable(false);
        }
        edAnswer.setOnEditorActionListener(this);
    }

    /**
     * 回复
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            content = edAnswer.getText().toString();
            if (TextUtils.isEmpty(content)) {
                ToastUtils.normal(mContext, "回复内容不能为空").show();
                return false;
            }
            getAnswerQuestion(content);
            edAnswer.setText("");
            return false;
        }
        return false;
    }


    /**
     * 监听事件
     *
     * @param view
     */
    @OnClick({R.id.titleBar_right_text, R.id.qa_detalis_answer_ed})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.titleBar_right_text:
                shareUrl = ApiService.BaseUrl + "getPostContent?" + "postId=" + postId + "&os=share";
                bottomDialog = new ShareBottomDialog(mContext, detalisBean.getViewerNickname(), shareUrl, detalisBean.getTitle());
                bottomDialog.show();
                break;
            case R.id.qa_detalis_answer_ed:
                if (!isLogin) {
                    ActivityManger.skipActivity(mContext, ActivityLogin.class);
                    return;
                }
                if (isCanAnswer.equals("0")) {
                    ToastUtils.normal(mContext, "需本专区所属职位认证才可回答问题哦").show();
                    return;
                }
                break;
        }
    }


    /**
     * 获取问答详情
     */
    private void getQaDetalisInfo() {
        Call<QaDetalisBean> call = communityService().getQuestionInfo(userId, postId);
        NetUtils<QaDetalisBean> netUtils = new NetUtils<QaDetalisBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<QaDetalisBean>() {
            @Override
            public void onResponseCallback(QaDetalisBean qaDetalisBean) {
                if (qaDetalisBean.getReturnCode() != 0 && !qaDetalisBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, qaDetalisBean.getReturnMsg()).show();
                    return;
                }
                getQaData(qaDetalisBean);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 获取问答详情 处理
     *
     * @param qaDetalisBean
     */
    private void getQaData(QaDetalisBean qaDetalisBean) {
        detalisBean = qaDetalisBean.getContent();
        setTitleText(detalisBean.getForumName()).rightImageRes(R.drawable.shard);
        GlideManager.loadImage(mContext, qaDetalisBean.getContent().getAvatar(), R.drawable.user, imageView);
        tvName.setText(qaDetalisBean.getContent().getViewerNickname());
        //超过回答时间了
        if (qaDetalisBean.getContent().getHours() == 0 || qaDetalisBean.getContent().getMinute() < 0) {
            time = TimeUtil.timeStrToSecond(qaDetalisBean.getContent().getPostDate());
            tvTime.setText(TimeUtil.getSpaceTime(time));
        } else {
            tvTime.setText("还剩" + qaDetalisBean.getContent().getHours() + "小时");
        }
        tvContent.setText(qaDetalisBean.getContent().getTitle());

        //剩余时间
        hours = qaDetalisBean.getContent().getHours();
        minute = qaDetalisBean.getContent().getMinute();
        //发布者id
        publisherId = detalisBean.getAuthor();
        //是否可以回答


        //是本人发布的  且超过时间了  隐藏掉回答布局
        if (userId.equals(detalisBean.getAuthor())
                || detalisBean.getHours() == 0
                || detalisBean.getMinute() < 0) {
            rayAnswer.setVisibility(View.GONE);
        } else {
            rayAnswer.setVisibility(View.VISIBLE);
        }

        isCanAnswer = detalisBean.getCanAnswer();
        if (isCanAnswer.equals("0")) {
            edAnswer.setFocusableInTouchMode(false);
            edAnswer.setFocusable(false);
        }


        if (qaDetalisBean.getContent().getHaveContent() == 0) {
            webView.setVisibility(View.GONE);
        } else {
            webView.setVisibility(View.VISIBLE);
            // TODO: 18/6/6   获取到首页信息后在获取htmel与回答列表
            webPath = ApiService.BaseUrl + "getPostContent?" + "postId=" + postId + "&os=android";
            SeetingWebView(webView, webPath);
        }
        getAnswerList();
    }


    //获取回答列表
    private void getAnswerList() {
        Call<QAReplyLisBean> call = communityService().getQAReplyList(userId, postId, 1);
        call.enqueue(new NetRetrofitCallback<QAReplyLisBean>(mContext, new HttpCallBackImpl<QAReplyLisBean>() {
            @Override
            public void onResponseCallback(QAReplyLisBean qaReplyLisBean) {
                if (qaReplyLisBean.getReturnCode() != 0 && !qaReplyLisBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, qaReplyLisBean.getReturnMsg()).show();
                    return;
                }
                ReplyData(qaReplyLisBean);
            }
        }));
    }


    /**
     * 数据处理
     *
     * @param qaReplyLisBean
     */
    private void ReplyData(QAReplyLisBean qaReplyLisBean) {
        listReply = qaReplyLisBean.getContent();
        if (listReply.size() == 0) {
            tvNohuidTitle.setText("暂无回答");
            tvNoreply.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }
        tvNohuidTitle.setText("全部回答");
        tvNoreply.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        replyListAdapter = new QAReplyListAdapter(mContext, listReply, publisherId, hours, minute);
        listView.setAdapter(replyListAdapter);
        replyListAdapter.addOnItemClickListener(this);
    }


    //回答问题
    private void getAnswerQuestion(String content) {
        Call<HttpResult> call = communityService().answerQuestion(postId, content, userId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getAnswerList();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 回复点赞
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        thumbsCall = listReply.get(position).getThumbuped();
        IsSelected = listReply.get(position).getIsSelected();
        AnswerName = listReply.get(position).getNickname();
        if (userId.equals(publisherId) && IsSelected == 0 && (hours != 0 || minute > 0)) {
            if (!isLogin) {
                ActivityManger.skipActivity(mContext, ActivityLogin.class);
                return;
            }
            showAnswerDialog(AnswerName, listReply.get(position).getReplyId());
        } else {
            if (thumbsCall == 1) {
                QaReplyThumbs(listReply.get(position).getReplyId(), -1);
            } else {
                QaReplyThumbs(listReply.get(position).getReplyId(), 1);
            }
        }
    }


    /**
     * 点赞or取消点赞
     */
    private void QaReplyThumbs(String replyId, final int thumbs) {
        Call<HttpResult> call = communityService().thumbUpOrDown4Answer(userId, replyId, thumbs);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getAnswerList();
            }
        }));
    }


    //采纳回答dialog
    private void showAnswerDialog(String name, final String replyId) {
        dialogSureCancel = new DialogSureCancel(mContext);
        dialogSureCancel.setCancelable(true);
        dialogSureCancel.setTitle("您确定要采纳" + name + "的答案吗");
        dialogSureCancel.setContent("采纳后您的悬赏将自动进入对方的户");
        dialogSureCancel.getTitleView().setTextColor(getResources().getColor(R.color.color_balank));
        dialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnswer(replyId);
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


    //采纳回答
    private void selectAnswer(String replyId) {
        Call<HttpResult> call = communityService().selectAnswer(replyId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                    return;
                }
                getAnswerList();
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * QQ分享的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bottomDialog != null && dialogSureCancel != null) {
            bottomDialog.dismiss();
            dialogSureCancel.dismiss();

        }
    }


}
