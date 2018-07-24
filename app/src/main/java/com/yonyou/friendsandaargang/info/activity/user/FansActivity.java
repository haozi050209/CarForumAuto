package com.yonyou.friendsandaargang.info.activity.user;

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
import com.yonyou.friendsandaargang.info.adapter.FensAdapter;
import com.yonyou.friendsandaargang.info.bean.Fans;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.view.dialog.FollowDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的粉丝
 */

public class FansActivity extends BaseActivity
        implements OnItemClickListener
        , AdapterView.OnItemClickListener {
    private static final String TAG = "FansActivity";
    @BindView(R.id.fens_list)
    ListView fens_list;
    @BindView(R.id.zanwu_fansi)
    TextView zanwu_fansi;


    private FensAdapter fensAdapter;
    private String userId;
    private String viewerId;
    private List<Fans.ContentBean> list;
    private String title;
    private FollowDialog followDialog;

    private String MyuserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);
        ButterKnife.bind(this);
        initviews();
        getFenslist();
    }


    /*初始化工作*/
    private void initviews() {
        getTitleBar();
        if (getIntent().getStringExtra(Constants.TITLE) != null) {
            title = getIntent().getStringExtra(Constants.TITLE);
            setTitleText(title + "的粉丝");
        } else {
            setTitleText("我的粉丝");
        }

        if (getIntent().getStringExtra(Constants.VIEWERID) != null) {
            userId = getIntent().getStringExtra(Constants.VIEWERID);   //关注者的用户id
            viewerId = SPTool.getContent(mContext, Constants.USER_ID); //浏览者的id
        } else {
            userId = SPTool.getContent(mContext, Constants.USER_ID);
        }

    }


    /**
     * 获取粉丝 列表
     */
    private void getFenslist() {
        Call<Fans> call = communityService().getFanList(userId, viewerId);
        NetUtils<Fans> netUtils = new NetUtils<Fans>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<Fans>() {
            @Override
            public void onResponseCallback(Fans fans) {
                if (!fans.getReturnMsg().equals("success") && fans.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, fans.getReturnMsg()).show();
                    return;
                }
                getData(fans);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }

    /*数据处理*/
    private void getData(Fans model) {
        list = model.getContent();
        if (list.size() == 0) {
            fens_list.setVisibility(View.GONE);
            zanwu_fansi.setVisibility(View.VISIBLE);
            return;
        }
        fens_list.setVisibility(View.VISIBLE);
        fensAdapter = new FensAdapter(FansActivity.this, list, this);
        fens_list.setAdapter(fensAdapter);
        fens_list.setOnItemClickListener(this);
    }


    /*关注or取消关注*/
    private void showFenDialog(final int postion, final int follow, final String Friend) {
        followDialog = new FollowDialog(mContext);
        if (follow == 1) {
            followDialog.getDialog_forum_title().setText("确定不再关注此人？");
        } else if (Friend.equals("YES")) {
            followDialog.getDialog_forum_title().setText("确定不再关注此人？");
        } else {
            followDialog.getDialog_forum_title().setText("确定关注此人？");
        }
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewerId.equals(list.get(postion).getUserId())) {
                    ToastUtils.normal(mContext, "自己不能关注自己哦").show();
                    followDialog.dismiss();
                    return;
                }
                //已关注
                if (follow == 1) {
                    getUnFollowPople(list.get(postion).getUserId());
                }
                //互相关注的也是掉用取消关注接口
                else if (Friend.equals("YES")) {
                    getUnFollowPople(list.get(postion).getUserId());
                }
                //没有关注的掉用 关注接口
                else if (follow == 0) {
                    getfollowUser(list.get(postion).getUserId());
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
     * 关注
     */
    private void getfollowUser(String followId) {
        Call<HttpResult> call = communityService().getFollowUser(userId, followId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getFenslist();
            }
        }));
    }


    //取消关注
    private void getUnFollowPople(String followId) {
        Call<HttpResult> call = communityService().getunFollowUser(userId, followId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                getFenslist();
            }
        }));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (followDialog != null) {
            followDialog.dismiss();
        }
    }


    /**
     * item  控件监听
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        //关注or取消关注
        showFenDialog(position,
                list.get(position).getViewerFollowed(),
                list.get(position).getFriend());
    }

    /**
     * item  监听
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


}
