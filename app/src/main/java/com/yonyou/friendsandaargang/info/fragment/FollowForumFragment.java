package com.yonyou.friendsandaargang.info.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.activirty.ForumDetailsActivity;
import com.yonyou.friendsandaargang.forum.adapter.MyForumAdapter;
import com.yonyou.friendsandaargang.forum.bean.MyForumList;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
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
 * Created by shibing on 18/3/21.
 * <p>
 * <p>
 * 关注的论坛
 */

public class FollowForumFragment extends Fragment implements
        AdapterView.OnItemClickListener, OnItemClickListener {

    @BindView(R.id.list_followforum)
    ListView list_followforum;
    @BindView(R.id.zanwu_luntan)
    TextView zanwu_luntan;

    private View view;
    private String userId;
    private List<MyForumList.ContentBean> list;
    private MyForumAdapter myForumAdapter;
    private FollowDialog followDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_followforum, null);
        ButterKnife.bind(this, view);
        userId = SPTool.getContent(getActivity(), Constants.USER_ID);
        getFollowFourmList();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }


    /**
     * 获取我关注的论坛
     */
    private void getFollowFourmList() {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<MyForumList> call = apiService.getFollowForumList(userId);
        call.enqueue(new NetRetrofitCallback<MyForumList>(getActivity(), new HttpCallBackImpl<MyForumList>() {
            @Override
            public void onResponseCallback(MyForumList model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(model.getReturnMsg());
                }
                getMyFourmListData(model);
            }
        }));
    }


    /**
     * 数据处理
     *
     * @param model
     */

    private void getMyFourmListData(MyForumList model) {
        list = model.getContent();
        //我关注的论坛
        if (list.size() == 0) {
            list_followforum.setVisibility(View.GONE);
            zanwu_luntan.setVisibility(View.VISIBLE);
            return;
        }
        myForumAdapter = new MyForumAdapter(getActivity(), list);
        list_followforum.setAdapter(myForumAdapter);
        list_followforum.setOnItemClickListener(this);
        myForumAdapter.addOnItemClickListener(this);
    }


    /**
     * 弹窗
     *
     * @param forumID
     */
    private void showDialog(final String forumID) {
        followDialog = new FollowDialog(getActivity());
        followDialog.setCancelable(false);
        followDialog.getDialog_forum_title().setText("确定不再关注此论坛？");
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUnFollowForum(forumID);
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
     * 取消关注
     *
     * @param forumId
     */
    private void getUnFollowForum(String forumId) {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<HttpResult> call = apiService.getunFollowForum(userId, forumId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(getActivity(), new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg());
                    return;
                }
                ToastUtils.normal("取消成功");
                getFollowFourmList();
            }
        }));

    }


    /**
     * item  关注监听事件
     *
     * @param postion
     */
    @Override
    public void onItemClick(int postion) {
        showDialog(list.get(postion).getForumId());
    }


    /**
     * item  监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ForumDetailsActivity.class);
        intent.putExtra(Constants.FORUMID, list.get(position).getForumId());
        intent.putExtra("from", "");
        intent.putExtra(Constants.TITLE, "");
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (followDialog != null) {
            followDialog.dismiss();
        }
    }
}
