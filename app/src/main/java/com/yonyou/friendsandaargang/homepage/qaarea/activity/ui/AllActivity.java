package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.modle.QAForumListBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.AllAdapeter;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class AllActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.all_list)
    ListView listView;

    private AllAdapeter adapeter;
    private List<QAForumListBean.ContentBean> list;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        ButterKnife.bind(this);
        initviews();

    }

    private void initviews() {
        title = getIntent().getStringExtra(Constants.TITLE);
        getTitleBar();
        setTitleText(title);
        getQAForumList();
    }

    /**
     * 获取悬赏专区列表
     */
    private void getQAForumList() {
        Call<QAForumListBean> call = communityService().getQAForumList();
        NetUtils<QAForumListBean> netUtils = new NetUtils<QAForumListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<QAForumListBean>() {
            @Override
            public void onResponseCallback(QAForumListBean qaForumListBean) {
                if (qaForumListBean.getReturnCode() != 0 && !qaForumListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, qaForumListBean.getReturnMsg()).show();
                    return;
                }
                listData(qaForumListBean);
            }

            @Override
            public void onFailureCallback() {
            }
        });
    }

    /**
     * 数据处理
     *
     * @param qaForumListBean
     */
    private void listData(QAForumListBean qaForumListBean) {
        list = qaForumListBean.getContent();
        adapeter = new AllAdapeter(mContext, list);
        listView.setAdapter(adapeter);
        listView.setOnItemClickListener(this);
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
        Intent intent;
        if (title.equals("全部专区")) {
            intent = new Intent(mContext, SpecialAreaDetalisActivity.class);
            intent.putExtra(Constants.FORUMID, list.get(position).getForumId());
            intent.putExtra(Constants.TITLE, list.get(position).getForumName());
            startActivity(intent);
        } else {
            intent = new Intent();
            intent.putExtra(Constants.FORUMID, list.get(position).getForumId());
            intent.putExtra(Constants.TITLE, list.get(position).getForumName());
            setResult(100, intent);
            finish();
        }

    }
}
