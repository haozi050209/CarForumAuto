package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.modle.MavinListBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.MavinAdapter;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;


/**
 * 人气专家
 */
public class MavinActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.headerlistview)
    ListView headerListView;
    @BindView(R.id.macin_hade_ray)
    RelativeLayout rayHead;
    @BindView(R.id.search_text_qa)
    TextView tvSearch;

    private MavinAdapter mavinAdapter;
    private List<MavinListBean.ContentBean> list;
    private String userId;
    private String keyWord;
    private String form;
    private String forumId;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        ButterKnife.bind(this);
        initviews();

    }

    //初始化
    private void initviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        forumId = getIntent().getStringExtra(Constants.FORUMID);
        form = getIntent().getStringExtra("form");
        isLogin = SPTool.getBoolean(mContext, Constants.ISLOGIN);
        if (TextUtils.isEmpty(form)) {
            tvSearch.setText("搜索大咖");
            rayHead.setVisibility(View.VISIBLE);
            getBigshotList();
        } else {
            tvSearch.setText("搜索答主");
            rayHead.setVisibility(View.GONE);
            getPopleAnswer();
        }

    }

    @OnClick({R.id.macin_hade_ray, R.id.search_text_qa, R.id.back_lay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back_lay:
                finish();
                break;
            case R.id.macin_hade_ray:
                ActivityManger.skipActivity(mContext, BigShotActivity.class);
                break;
            case R.id.search_text_qa:
                Intent intent = new Intent(mContext, QaSearchActivity.class);
                intent.putExtra(Constants.FORUMID, forumId);
                if (form.equals("moreOnArea")) {
                    intent.putExtra("form", "OnBigAnswerLord");
                } else {
                    intent.putExtra("form", "expert");
                }
                startActivity(intent);
                break;
        }

    }


    // 获取人气专家列表
    private void getBigshotList() {
        Call<MavinListBean> call = communityService().getBigshotList(userId, keyWord, 1, 100);
        NetUtils<MavinListBean> netUtils = new NetUtils<MavinListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<MavinListBean>() {
            @Override
            public void onResponseCallback(MavinListBean mavinListBean) {
                if (mavinListBean.getReturnCode() != 0 && !mavinListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, mavinListBean.getReturnMsg()).show();
                    return;
                }
                getHotData(mavinListBean);

            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    private void getHotData(MavinListBean mavinListBean) {
        list = mavinListBean.getContent();
        mavinAdapter = new MavinAdapter(mContext, list, "", "");
        headerListView.setAdapter(mavinAdapter);
        headerListView.setOnItemClickListener(this);
    }


    /**
     * 获取单个大咖页人气答主
     */
    private void getPopleAnswer() {
        Call<MavinListBean> call = communityService().getHotBigshotListOnArea(userId, forumId
                , 1, 100);
        NetUtils<MavinListBean> netUtils = new NetUtils<MavinListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<MavinListBean>() {
            @Override
            public void onResponseCallback(MavinListBean mavinListBean) {
                if (mavinListBean.getReturnCode() != 0 && !mavinListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, mavinListBean.getReturnMsg()).show();
                    return;
                }
                PopleAnswerData(mavinListBean);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    private void PopleAnswerData(MavinListBean mavinListBean) {
        list = mavinListBean.getContent();
        mavinAdapter = new MavinAdapter(mContext, list, "", "");
        headerListView.setAdapter(mavinAdapter);
        headerListView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!isLogin) {
            ActivityManger.skipActivity(mContext, ActivityLogin.class);
            return;
        }
        Intent intent = new Intent(mContext, BigShotPutToActivity.class);
        intent.putExtra(Constants.BIGSHOTID, list.get(position).getUserId());
        startActivity(intent);
    }
}
