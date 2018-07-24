package com.yonyou.friendsandaargang.info.activity.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.info.adapter.FollowPeopleAdapter;
import com.yonyou.friendsandaargang.info.bean.People;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.dialog.FollowDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/4/19.
 */

public class UserFollowActivity extends BaseActivity implements OnItemClickListener, AdapterView.OnItemClickListener {


    @BindView(R.id.list_userfollow)
    ListView list_followpeople;
    @BindView(R.id.userfollow_people)
    TextView zanwu_people;
    private String UserId;
    List<People.ContentBean> list;
    private FollowPeopleAdapter followPeopleAdapter;
    private String viewerId;
    private String followId;
    private int isFollow;
    private FollowDialog followDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfollow);
        ButterKnife.bind(this);
        initviews();
        getFollowPeople();
    }

    private void initviews() {
        getTitleBar();
        if (getIntent().getStringExtra(Constants.TITLE) != null) {
            setTitleText(getIntent().getStringExtra(Constants.TITLE) + "关注的人");
        }
        //关注者的用户id
        if (getIntent().getStringExtra(Constants.VIEWERID) != null) {
            UserId = getIntent().getStringExtra(Constants.VIEWERID);
        }
        //浏览者的id
        viewerId = SPTool.getContent(mContext, Constants.USER_ID);


    }

    /**
     * 查看他人关注的列表
     */
    private void getFollowPeople() {
        Call<People> call = communityService().getFollowUserList(UserId, viewerId);
        NetUtils<People> netUtils = new NetUtils<People>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<People>() {
            @Override
            public void onResponseCallback(People people) {
                if (people.getReturnCode() != 0 && !people.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, people.getReturnMsg()).show();
                    return;
                }
                initData(people);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    private void initData(People model) {
        list = model.getContent();
        if (list.size() == 0) {
            list_followpeople.setVisibility(View.GONE);
            zanwu_people.setVisibility(View.VISIBLE);
            return;
        }
        followPeopleAdapter = new FollowPeopleAdapter(mContext, list, "viewerfollow");
        list_followpeople.setAdapter(followPeopleAdapter);
        list_followpeople.setOnItemClickListener(this);
        followPeopleAdapter.addOnItemClickListener(this);
    }


    //取消关注
    private void getUnFollowPople(final String followId) {
        Call<HttpResult> call = communityService().getunFollowUser(UserId, followId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    // 刷新ui
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                isFollow = 0;
                getFollowPeople();
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
     * 关注
     */
    private void getfollowUser(String FollowId) {
        Call<HttpResult> call = communityService().getFollowUser(viewerId, FollowId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    return;
                }
                ToastUtils.normal(mContext, "已关注").show();
                isFollow = 1;
                getFollowPeople();
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


    private void showUnFollowDialog(final String userId, String text) {
        followDialog = new FollowDialog(mContext);
        followDialog.setCancelable(false);
        followDialog.getDialog_forum_title().setText(text);
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow == 1) {
                    getUnFollowPople(userId);
                } else {
                    if (viewerId.equals(userId)) {
                        ToastUtils.normal(mContext, "不能关注本人哦！").show();
                        followDialog.dismiss();
                        return;
                    }
                    getfollowUser(userId);
                }
                followDialog.dismiss();
            }
        });
        followDialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followDialog.dismiss();
            }
        });
        followDialog.show();
    }


    /**
     * 关注监听事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        isFollow = list.get(position).getViewerFollowed();
        if (isFollow == 0) {
            showUnFollowDialog(list.get(position).getUserId(), "确定关注此人？");
        } else {
            showUnFollowDialog(list.get(position).getUserId(), "确定不在关注此人？");
        }
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
        Intent intent = new Intent(mContext, FensDetailsActivity.class);
        intent.putExtra(Constants.USER_ID, list.get(position).getUserId());
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (followDialog != null) {
            followDialog.dismiss();
        }
    }
}


