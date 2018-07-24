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
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.info.adapter.FollowPeopleAdapter;
import com.yonyou.friendsandaargang.info.bean.People;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
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
 * 关注的人
 */

public class FollowPeopleFragment extends Fragment implements AdapterView.OnItemClickListener
        ,  OnItemClickListener {
    View view;
    @BindView(R.id.list_followpeople)
    ListView list_followpeople;
    @BindView(R.id.zanwu_people)
    TextView zanwu_people;
    private String userId;
    List<People.ContentBean> list;
    private FollowPeopleAdapter followPeopleAdapter;
    private String viewerId;
    private FollowDialog followDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_followpeople, null);
        ButterKnife.bind(this, view);
        userId = SPTool.getContent(getActivity(), Constants.USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFollowPeople();
    }


    /**
     * 我关注的人
     */
    private void getFollowPeople() {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<People> call = apiService.getFollowUserList(userId, viewerId);
        call.enqueue(new NetRetrofitCallback<People>(getActivity(), new HttpCallBackImpl<People>() {
            @Override
            public void onResponseCallback(People model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg());
                    return;
                }
                initData(model);
            }
        }));
    }


    private void initData(People model) {
        list = model.getContent();
        if (list.size() == 0) {
            list_followpeople.setVisibility(View.GONE);
            zanwu_people.setVisibility(View.VISIBLE);
            return;
        }
        followPeopleAdapter = new FollowPeopleAdapter(getActivity(), list, "userfollow");
        list_followpeople.setAdapter(followPeopleAdapter);
        list_followpeople.setOnItemClickListener(this);
        followPeopleAdapter.addOnItemClickListener(this);
    }


    /**
     * 取消关注
     *
     * @param followId
     */
    private void getUnFollowPople(String followId) {
        ApiService apiService = ApiClient.retrofit().create(ApiService.class);
        Call<HttpResult> call = apiService.getunFollowUser(userId, followId);
        call.enqueue(new NetRetrofitCallback<HttpResult>(getActivity(), new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg());
                    return;
                }
                getFollowPeople();
                ToastUtils.normal("取消成功");
            }
        }));
    }


    /**
     * 取消dialog
     *
     * @param postion
     */
    private void showDialog(final int postion) {
        followDialog = new FollowDialog(getActivity());
        followDialog.setCancelable(false);
        followDialog.getDialog_forum_title().setText("确定不再关注此人？");
        followDialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUnFollowPople(list.get(postion).getUserId());
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
     * 监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), FensDetailsActivity.class);
        intent.putExtra(Constants.USER_ID, list.get(position).getUserId());
        startActivity(intent);
    }


    /**
     * 取消关注监听事件
     * @param postion
     */
    @Override
    public void onItemClick(int postion) {
        showDialog(postion);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (followDialog != null) {
            followDialog.dismiss();
        }
    }
}
