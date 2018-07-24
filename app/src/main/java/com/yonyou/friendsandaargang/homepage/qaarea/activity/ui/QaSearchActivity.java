package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.modle.BigshotAnwserListBean;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.homepage.modle.MavinListBean;
import com.yonyou.friendsandaargang.homepage.modle.QuestionListBean;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.BigSearchMainListAdapter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.HotListAdapter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.MavinAdapter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.PutToAdapeter;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.adapter.SearchMainListAdapter;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/6/4.
 */

public class QaSearchActivity extends BaseActivity implements
        AdapterView.OnItemClickListener
        , OnItemClickListener {

    @BindView(R.id.qa_search_edit)
    EditText searchEdit;
    @BindView(R.id.qa_search_qingchu)
    LinearLayout layoutQinchu;
    @BindView(R.id.qa_search_list)
    ListView listView;
    @BindView(R.id.qa_search_text)
    TextView tvNoSearch;


    private String searchContent;
    private String form;
    private String userId;
    private String forumId;
    private String forumName;


    //首页搜索
    private List<HotListBean.ContentBean> hotList;
    private SearchMainListAdapter mainListAdapter;


    //专家搜索
    private MavinAdapter mavinAdapter;
    private List<MavinListBean.ContentBean> list;

    // 问答首页搜索数据处理
    private List<QuestionListBean.ContentBean> listQues;
    private PutToAdapeter putToAdapeter;

    //大咖首页搜索问答
    private List<BigshotAnwserListBean.ContentBean> bigAnwserlist;
    private BigSearchMainListAdapter bigSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qasearch);
        ButterKnife.bind(this);
        initviews();
    }


    private void initviews() {

        userId = SPTool.getContent(mContext, Constants.USER_ID);
        forumId = getIntent().getStringExtra(Constants.FORUMID);
        forumName = getIntent().getStringExtra("forumName");
        form = getIntent().getStringExtra("form");
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    layoutQinchu.setVisibility(View.GONE);
                } else {
                    layoutQinchu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //搜索按钮
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchContent = searchEdit.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(searchContent)) {
                        ToastUtils.normal(mContext, "搜索内容不能为空").show();
                        return true;
                    }
                    switch (form) {
                        //首页搜索入口
                        case "QaMain":
                            SearchQaMain(searchContent);
                            break;
                        //专家搜索入口
                        case "expert":
                            getBigshotList(searchContent);
                            break;
                        //大咖搜索入口
                        case "bigShot":
                            getSearchBigShot(searchContent);
                            break;
                        //单个大咖专区搜索问答
                        case "OnBigshotArea":
                            OnBigSearchArea(searchContent);
                            break;
                        case "OnBigAnswerLord":
                            OnBigSearchAnswerLord(searchContent);
                            break;
                        //专区搜索入口
                        case "Area":
                            getSearchResultOnQAArea(searchContent);
                            break;
                    }
                    return false;
                }
                return false;
            }
        });
    }


    @OnClick({R.id.qa_search_cancel, R.id.qa_search_qingchu})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.qa_search_cancel:
                finish();
                break;
            //清除搜索内容
            case R.id.qa_search_qingchu:
                searchEdit.setText("");
                listView.setVisibility(View.GONE);
                tvNoSearch.setVisibility(View.VISIBLE);
                break;
        }
    }

    //搜索问答首页
    private void SearchQaMain(final String keyWord) {
        Call<HotListBean> call = communityService().getSearchResultOnQAMainPage(userId
                , keyWord, 1, 100);
        NetUtils<HotListBean> netUtils = new NetUtils<HotListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<HotListBean>() {
            @Override
            public void onResponseCallback(HotListBean hotListBean) {
                if (hotListBean.getReturnCode() != 0 && !hotListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, hotListBean.getReturnMsg()).show();
                    return;
                }
                getMainData(hotListBean, keyWord);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    //问答首页搜索数据处理
    private void getMainData(HotListBean hotListBean, String keyWord) {
        hotList = hotListBean.getContent();
        if (hotList.size() == 0) {
            tvNoSearch.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }
        listView.setVisibility(View.VISIBLE);
        mainListAdapter = new SearchMainListAdapter(mContext, hotList, keyWord);
        listView.setAdapter(mainListAdapter);
        listView.setOnItemClickListener(this);
    }

    // 搜索人气专家列表
    private void getBigshotList(final String keyWord) {
        Call<MavinListBean> call = communityService().getBigshotList(userId, keyWord, 1, 100);
        NetUtils<MavinListBean> netUtils = new NetUtils<MavinListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<MavinListBean>() {
            @Override
            public void onResponseCallback(MavinListBean mavinListBean) {
                if (mavinListBean.getReturnCode() != 0 && !mavinListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, mavinListBean.getReturnMsg()).show();
                    return;
                }
                getHotData(mavinListBean, keyWord);

            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    //专家数据处理
    private void getHotData(MavinListBean mavinListBean, String keyWord) {
        list = mavinListBean.getContent();
        mavinAdapter = new MavinAdapter(mContext, list, keyWord, "");
        listView.setAdapter(mavinAdapter);
        listView.setOnItemClickListener(this);
    }

    // 单个问答专区内的搜索
    private void getSearchResultOnQAArea(final String keyWord) {
        Call<QuestionListBean> call = communityService().getSearchResultOnQAArea(userId
                , keyWord
                , forumId
                , 1
                , 100);
        NetUtils<QuestionListBean> netUtils = new NetUtils<QuestionListBean>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<QuestionListBean>() {
            @Override
            public void onResponseCallback(QuestionListBean questionListBean) {
                if (questionListBean.getReturnCode() != 0 && !questionListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, questionListBean.getReturnMsg()).show();
                    return;
                }
                getQuestionListData(questionListBean, keyWord);
            }

            @Override
            public void onFailureCallback() {

            }
        });

    }

    //单个问答专区内的搜索数据处理
    private void getQuestionListData(QuestionListBean questionListBean, String keyWord) {
        listQues = questionListBean.getContent();
        if (listQues.size() == 0) {
            listView.setVisibility(View.GONE);
            tvNoSearch.setVisibility(View.VISIBLE);
            return;
        }
        listView.setVisibility(View.VISIBLE);
        putToAdapeter = new PutToAdapeter(mContext, listQues, keyWord);
        listView.setAdapter(putToAdapeter);
        listView.setOnItemClickListener(this);
        putToAdapeter.addOnItemClickListener(this);
    }

    //大咖首页搜索问答
    private void getSearchBigShot(final String keyWord) {
        Call<BigshotAnwserListBean> call = communityService().getSearchResultOnBigshotMainPage(
                userId, keyWord
                , 1, 100);
        NetUtils<BigshotAnwserListBean> netUtils = new NetUtils<BigshotAnwserListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<BigshotAnwserListBean>() {
            @Override
            public void onResponseCallback(BigshotAnwserListBean anwserListBean) {
                if (anwserListBean.getReturnCode() != 0 && !anwserListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, anwserListBean.getReturnMsg()).show();
                    return;
                }
                BigShotSearchData(anwserListBean, keyWord);
            }

            @Override
            public void onFailureCallback() {

            }
        });

    }

    //单个大咖搜索问答
    private void OnBigSearchArea(final String keyWord) {
        Call<BigshotAnwserListBean> call = communityService().getSearchResultOnBigshotArea(
                userId, keyWord, forumId, 1, 100);
        NetUtils<BigshotAnwserListBean> netUtils = new NetUtils<BigshotAnwserListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<BigshotAnwserListBean>() {
            @Override
            public void onResponseCallback(BigshotAnwserListBean anwserListBean) {
                if (anwserListBean.getReturnCode() != 0 && !anwserListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, anwserListBean.getReturnMsg()).show();
                    return;
                }
                BigShotSearchData(anwserListBean, keyWord);
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }


    //单个大咖搜索答主
    private void OnBigSearchAnswerLord(final String keyWord) {
        Call<MavinListBean> call = communityService().SearchBigshotListOnArea(
                userId, keyWord, forumId, 1, 100);
        NetUtils<MavinListBean> netUtils = new NetUtils<MavinListBean>(this);
        netUtils.enqueue(call, new ResponseCallBack<MavinListBean>() {
            @Override
            public void onResponseCallback(MavinListBean mavinListBean) {
                if (mavinListBean.getReturnCode() != 0 && !mavinListBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, mavinListBean.getReturnMsg()).show();
                    return;
                }
                getHotData(mavinListBean, keyWord);

            }

            @Override
            public void onFailureCallback() {

            }
        });
    }

    //大咖首页搜索问答与单个大咖搜索问答数据处理
    private void BigShotSearchData(BigshotAnwserListBean anwserListBean, String keyWord) {
        bigAnwserlist = anwserListBean.getContent();
        if (bigAnwserlist.size() == 0) {
            listView.setVisibility(View.GONE);
            tvNoSearch.setVisibility(View.VISIBLE);
            return;
        }
        listView.setVisibility(View.VISIBLE);
        bigSearchAdapter = new BigSearchMainListAdapter(mContext, bigAnwserlist, keyWord);
        listView.setAdapter(bigSearchAdapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (form) {
            //首页搜索监听
            case "QaMain":
                intent = new Intent(mContext, QaDetalisActivity.class);
                intent.putExtra(Constants.TITLE, hotList.get(position).getForumName());
                intent.putExtra(Constants.POSTID, hotList.get(position).getPostId());
                startActivity(intent);
                break;
            //专家搜索监听
            case "expert":
            case "OnBigAnswerLord":
                intent = new Intent(mContext, BigShotPutToActivity.class);
                intent.putExtra(Constants.BIGSHOTID, list.get(position).getUserId());
                startActivity(intent);
                break;
            //大咖搜索监听
            case "bigShot":
            case "OnBigshotArea":
                intent = new Intent(mContext, BigshotQuestionActivity.class);
                intent.putExtra(Constants.POSTID, bigAnwserlist.get(position).getPostId());
                intent.putExtra("from", "Bigsearch");
                startActivity(intent);
                break;
            //专区搜索监听
            case "Area":
                intent = new Intent(mContext, QaDetalisActivity.class);
                //intent.putExtra(Constants.TITLE, forumName);
                intent.putExtra(Constants.POSTID, listQues.get(position).getPostId());
                startActivity(intent);
                break;
        }
    }


    //适配器item监听事件
    @Override
    public void onItemClick(int position) {

    }
}
