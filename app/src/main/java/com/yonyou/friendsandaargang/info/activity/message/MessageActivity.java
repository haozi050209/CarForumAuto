package com.yonyou.friendsandaargang.info.activity.message;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.MessAgeAdapter;
import com.yonyou.friendsandaargang.info.bean.MessAge;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 个人信息
 */

public class MessageActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MessageActivity";
    @BindView(R.id.list_message)
    ListView list_message;
    @BindView(R.id.no_message)
    TextView no_message;
    private MessAgeAdapter messAgeAdapter;
    private String userId;
    private List<MessAge.ContentBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        initviews();
    }

    private void initviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        getTitleBar();
        setTitleText("消息中心");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserMessage(userId);
    }

    /**
     * 获取消息列表
     *
     * @param userId
     */
    private void getUserMessage(String userId) {
        Call<MessAge> call = communityService().getAllMessageByUserId(userId);
        NetUtils<MessAge> netUtils = new NetUtils<MessAge>(this);
        netUtils.enqueue(call, new ResponseCallBack<MessAge>() {
            @Override
            public void onResponseCallback(MessAge messAge) {
                if (messAge.getReturnCode() != 0 && !messAge.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, messAge.getReturnMsg()).show();
                    return;
                }
                getMessAgeData(messAge);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }

    /**
     * 数据处理
     *
     * @param model
     */
    private void getMessAgeData(MessAge model) {
        list = model.getContent();
        if (list.size() == 0) {
            no_message.setVisibility(View.VISIBLE);
            list_message.setVisibility(View.GONE);
            return;
        }
        list_message.setVisibility(View.VISIBLE);
        no_message.setVisibility(View.GONE);

        messAgeAdapter = new MessAgeAdapter(mContext, list);
        list_message.setAdapter(messAgeAdapter);
        messAgeAdapter.notifyDataSetChanged();
        list_message.setOnItemClickListener(this);
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
        switch (list.get(position).getMessageType()) {
            case 10141001:
                ActivityManger.skipActivity(MessageActivity.this, SystemMessageActivity.class);
                break;
            case 10141002:
                ActivityManger.skipActivity(MessageActivity.this, PurseNewsActivity.class);
                break;
            case 10141003:
                ActivityManger.skipActivity(MessageActivity.this, FensMessageActivity.class);
                break;
            case 10141004:
                ActivityManger.skipActivity(MessageActivity.this, ForumNewsActivity.class);
                break;
            case 10141005:
                ActivityManger.skipActivity(MessageActivity.this, WenDaMessAgeActivity.class);
                break;
            case 10141006:
                ActivityManger.skipActivity(MessageActivity.this, JiaoYiMessAgeActivity.class);
                break;
        }
    }

}
