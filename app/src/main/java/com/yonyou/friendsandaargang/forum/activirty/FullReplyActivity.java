package com.yonyou.friendsandaargang.forum.activirty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.adapter.FullReplyAdapter;
import com.yonyou.friendsandaargang.forum.bean.FullReply;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/3/28.
 */

public class FullReplyActivity extends BaseActivity implements AdapterView.OnItemClickListener, TextView.OnEditorActionListener {

    @BindView(R.id.fullraply_avae)
    CircleImageView fullraply_avae;
    @BindView(R.id.fullraply_name)
    TextView fullraply_name;
    @BindView(R.id.fullraply__tiem)
    TextView fullraply__tiem;
    @BindView(R.id.ullraply_dianzan_text)
    TextView fullraply_dianzan_text;
    @BindView(R.id.ullraply_dianzan_image)
    ImageView ullraply_dianzan_image;
    @BindView(R.id.ullraply_dianzan_lay)
    LinearLayout ullraply_dianzan_lay;
    @BindView(R.id.ullraply_content_text)
    TextView ullraply_content_text;
    @BindView(R.id.ullraply_list)
    MyListView ullraply_list;

    @BindView(R.id.fullraply_edit)
    EditText fullraply_edit;

    private Long time;
    private String postId, userId, mainRepliedId;


    private List<FullReply.ContentBean.ReplyListBean> contentBeanList;
    private FullReplyAdapter fullReplyAdapter;


    private int thumbupNumber, isdianzan;
    private String directRepliedId, replierId, Replycontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullreply);
        ButterKnife.bind(this);
        initviews();
        getPostReplyMainListData();
    }


    private void initviews() {
        getTitleBar();
        setTitleText("全部回复");
        if (getIntent().getStringExtra(Constants.POSTID) != null) {
            postId = getIntent().getStringExtra(Constants.POSTID);
        }
        if (getIntent().getStringExtra(Constants.REPLYTD) != null) {
            mainRepliedId = getIntent().getStringExtra(Constants.REPLYTD);
        }

        if (getIntent().getStringExtra("replierId") != null) {
            replierId = getIntent().getStringExtra("replierId");
        }
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        fullraply_edit.setOnEditorActionListener(this);
        ullraply_list.setOnItemClickListener(this);
    }


    @OnClick({R.id.fullraply_avae, R.id.ullraply_dianzan_lay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ullraply_dianzan_lay:
                if (thumbupNumber == 1) {
                    isdianzan = -1;
                    getThumbUpOrDown4Reply(isdianzan);
                } else if (thumbupNumber == 0) {
                    isdianzan = 1;
                    getThumbUpOrDown4Reply(isdianzan);
                }
                break;
            case R.id.fullraply_avae:
                Intent intent = new Intent(mContext, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, replierId);
                startActivity(intent);
                break;
        }
    }


    /**
     * 获取帖子主页下的评论列表
     */
    private void getPostReplyMainListData() {
        Call<FullReply> call = communityService().getTheSpecificReply(postId, mainRepliedId, userId);
        call.enqueue(new NetRetrofitCallback<FullReply>(mContext, new HttpCallBackImpl<FullReply>() {
            @Override
            public void onResponseCallback(FullReply model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getRelpyData(model);
            }
        }));
    }

    private void getRelpyData(FullReply model) {
        Glide.with(FullReplyActivity.this)
                .load(model.getContent().getAvatar())
                .error(R.drawable.user)
                .into(fullraply_avae);
        fullraply_name.setText(model.getContent().getNickname());
        time = TimeUtil.timeStrToSecond(model.getContent().getReplyDate());
        fullraply__tiem.setText(TimeUtil.getSpaceTime(time));
        fullraply_dianzan_text.setText(model.getContent().getThumbupNumber() + "");
        ullraply_content_text.setText(model.getContent().getContent() + "");
        thumbupNumber = model.getContent().getThumbuped();

        if (model.getContent().getThumbuped() == 1) {
            ullraply_dianzan_image.setImageResource(R.drawable.comment_zan_high);
        } else {
            ullraply_dianzan_image.setImageResource(R.drawable.ico_dz);
        }

        if (model.getContent().getReplyList() != null) {
            contentBeanList = model.getContent().getReplyList();
            fullReplyAdapter = new FullReplyAdapter(FullReplyActivity.this, contentBeanList);
            ullraply_list.setAdapter(fullReplyAdapter);
            fullReplyAdapter.notifyDataSetChanged(contentBeanList);
        }
    }


    /**
     * 对评论进行一级回复
     */
    private void getReplyMessage(String RepleContent) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getSaveReplyMessage(
                postId,         //   帖子id
                userId,         //   回复者的id
                RepleContent,   //   回复内容
                mainRepliedId,  //   帖子评论的id
                directRepliedId //   二级评论的id
        );
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {

                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, "已评论").show();
                    getPostReplyMainListData();
                } else {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                }

            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }


    /**
     * 对回复帖子点赞
     */

    private void getThumbUpOrDown4Reply(int dianzan) {
        showProgressDialog();
        Call<HttpResult> call = communityService().getThumbUpOrDown4Reply(userId, mainRepliedId, dianzan);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                getPostReplyMainListData();
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });


    }


    /**
     * 监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        directRepliedId = contentBeanList.get(position).getReplyId();
        showInPutKeybord(parent);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            Replycontent = fullraply_edit.getText().toString();
            if (TextUtils.isEmpty(Replycontent)) {
                ToastUtils.normal(mContext, "回复内容不能为空").show();
                return false;
            }
            getReplyMessage(Replycontent);
            fullraply_edit.setText("");
            return false;
        }
        return false;
    }
}
