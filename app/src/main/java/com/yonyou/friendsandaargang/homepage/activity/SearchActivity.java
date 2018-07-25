package com.yonyou.friendsandaargang.homepage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.forum.activirty.PostDetailsActivity;
import com.yonyou.friendsandaargang.hIndicator.HIndicatorAdapter;
import com.yonyou.friendsandaargang.hIndicator.HIndicatorBuilder;
import com.yonyou.friendsandaargang.hIndicator.HIndicatorDialog;
import com.yonyou.friendsandaargang.hIndicator.listener.OnItemClickListener;
import com.yonyou.friendsandaargang.homepage.adapter.AllQaAdapter;
import com.yonyou.friendsandaargang.homepage.adapter.AllUserAdapter;
import com.yonyou.friendsandaargang.homepage.adapter.HostSearchAdapter;
import com.yonyou.friendsandaargang.homepage.adapter.SearchAdapter;
import com.yonyou.friendsandaargang.homepage.adapter.SearchPostAdapter;
import com.yonyou.friendsandaargang.homepage.modle.AllSearch;
import com.yonyou.friendsandaargang.homepage.modle.HotSearch;
import com.yonyou.friendsandaargang.homepage.modle.SearchPostBean;
import com.yonyou.friendsandaargang.homepage.modle.SearchQa;
import com.yonyou.friendsandaargang.homepage.modle.SearchUser;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.BigshotQuestionActivity;
import com.yonyou.friendsandaargang.homepage.qaarea.activity.ui.QaDetalisActivity;
import com.yonyou.friendsandaargang.info.activity.userinfo.FensDetailsActivity;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.MyListView;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/8.
 */

public class SearchActivity extends BaseActivity implements OnItemClickListener
        , TextWatcher, TextView.OnEditorActionListener, AdapterView.OnItemClickListener {
    @BindView(R.id.search_xuanze_text)
    TextView search_xuanze_text;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_qingchu)
    LinearLayout search_qingchu;
    @BindView(R.id.lately_search_listview)
    MyListView lately_search_listview;
    @BindView(R.id.search_recyler)
    ListView search_recyler;
    @BindView(R.id.search_scrollview)
    ScrollView search_scrollview;
    @BindView(R.id.search_clea_lay)
    LinearLayout search_clea_lay;
    @BindView(R.id.suos_xuanzhe)
    LinearLayout suos_xuanzhe;
    @BindView(R.id.no_search)
    TextView no_search;
    @BindView(R.id.search_pinlistview)
    PinnedHeaderListView pinnListview;
    @BindView(R.id.tagflow)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.search_root)
    LinearLayout searchRoot;
    @BindView(R.id.search_jilu_lay)
    LinearLayout jilulayout;

    private List<String> list;
    private String[] hotlist;
    private List arrayList;
    private HostSearchAdapter hostSearchAdapter;
    private String userid,string,editcotent,content;

    private HIndicatorDialog hIndicatorDialog;
    private HIndicatorAdapter hIndicatorAdapter;


    //搜索帖子
    private SearchPostAdapter adapter;
    private List<SearchPostBean.ContentBean> beanList;
    //搜索问答
    private List<SearchQa.ContentBean> qabean;
    private AllQaAdapter qaAdapter;
    //搜索用户
    private List<SearchUser.ContentBean> userList;
    private AllUserAdapter searchUserAdapter;

    // 搜索全部
    private SearchAdapter searchAdapter;
    private List<AllSearch.ContentBean.PostBean> listPost;
    private List<AllSearch.ContentBean.QaBean> listQa;
    private List<AllSearch.ContentBean.UserBean> listUser;
    private List<String> listTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initviews();
        getHotSearch();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getTagFolw();
    }

    private void initviews() {
        userid = SPTool.getContent(mContext, Constants.USER_ID);
        listTitle = Arrays.asList("论坛", "问答", "用户");
        list = Arrays.asList("全部", "论坛", "问答", "用户");

        //默认是全部
        search_xuanze_text.setText("全部");
        if (getIntent().getStringExtra("type").equals("homepage")) {
            search_xuanze_text.setText(list.get(0));
        } else if (getIntent().getStringExtra("type").equals("forum")) {
            search_xuanze_text.setText(list.get(1));
            suos_xuanzhe.setClickable(false);
        }
        search_edit.addTextChangedListener(this);
        search_edit.setOnEditorActionListener(this);
    }


    @OnClick({R.id.back_lay, R.id.search_xuanze_lay, R.id.search_text_lay, R.id.search_qingchu, R.id.search_clea_lay})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back_lay:
                finish();
                break;
            case R.id.search_xuanze_lay:
                showPopuDiow();
                break;
            case R.id.search_text_lay:
                String content = search_edit.getText().toString();
                String text = search_xuanze_text.getText().toString();
                if (content.isEmpty()) {
                    ToastUtils.normal(mContext, "搜索内容不能为空").show();
                    return;
                }
                switch (text) {
                    case "全部":
                        getSearchResult4All(content);
                        break;
                    case "论坛":
                        getSearchResult4Post(content);
                        break;
                    case "问答":
                        getSearchResult4Qa(content);
                        break;
                    case "用户":
                        getSearchResult4User(content);
                        break;
                    default:
                        getSearchResult4All(content);
                        break;
                }
                break;
            case R.id.search_qingchu:
                search_edit.setText("");
                no_search.setVisibility(View.GONE);
                search_recyler.setVisibility(View.GONE);
                pinnListview.setVisibility(View.GONE);
                search_scrollview.setVisibility(View.VISIBLE);
                break;
            case R.id.search_clea_lay:
                getDetaletSearch();
                break;
        }
    }


    /**
     * 获取搜索记录
     */
    private void getTagFolw() {
        Call<HotSearch> call = communityService().getUserSearchKeyword(userid);
        call.enqueue(new NetRetrofitCallback<HotSearch>(mContext, new HttpCallBackImpl<HotSearch>() {
            @Override
            public void onResponseCallback(HotSearch model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                if (model.getContent().length == 0) {
                    tagFlowLayout.setVisibility(View.GONE);
                    return;
                }
                jilulayout.setVisibility(View.VISIBLE);
                setTagFlow(model);
            }
        }));
    }


    /**
     * 搜索记录  数据处理
     *
     * @param model
     */
    private void setTagFlow(final HotSearch model) {
        tagFlowLayout.setMaxSelectCount(5);
        tagFlowLayout.setAdapter(new TagAdapter<String>(model.getContent()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tag_item,
                        tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }

        });


        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                search_edit.setText(model.getContent()[position]);
                content = model.getContent()[position];
                string = search_xuanze_text.getText().toString();
                switch (string) {
                    case "全部":
                        getSearchResult4All(content);
                        break;
                    case "论坛":
                        getSearchResult4Post(content);
                        break;
                    case "问答":
                        getSearchResult4Qa(content);
                        break;
                    case "用户":
                        getSearchResult4User(content);
                        break;
                    default:
                        getSearchResult4All(content);
                        break;
                }

                return true;
            }
        });
    }


    /**
     * 删除搜索记录
     */
    private void getDetaletSearch() {
        Call<HttpResult> call = communityService().deleteUserSearch(userid);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, httpResult.getReturnMsg().toString()).show();
                    return;
                }
                getTagFolw();
                jilulayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 获取热门搜索词条
     */

    private void getHotSearch() {
        Call<HotSearch> call = communityService().getHotSearchKeyword();
        NetUtils<HotSearch> netUtils = new NetUtils<HotSearch>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HotSearch>() {
            @Override
            public void onResponseCallback(HotSearch hotSearch) {
                if (hotSearch.getReturnCode() != 0 && !hotSearch.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, hotSearch.getReturnMsg()).show();
                    return;
                }
                HotListData(hotSearch);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 热门词条数据处理
     */

    private void HotListData(HotSearch hotSearch) {
        hotlist = hotSearch.getContent();
        arrayList = Arrays.asList(hotlist);
        hostSearchAdapter = new HostSearchAdapter(SearchActivity.this, arrayList);
        lately_search_listview.setAdapter(hostSearchAdapter);
        lately_search_listview.setOnItemClickListener(this);
    }


    /**
     * 搜索全部
     */

    private void getSearchResult4All(final String content) {
        Call<AllSearch> call = communityService().getSearchResult4All(userid, content);
        NetUtils<AllSearch> netUtils = new NetUtils<AllSearch>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<AllSearch>() {
            @Override
            public void onResponseCallback(AllSearch allSearch) {
                if (allSearch.getReturnCode() != 0 && allSearch.getReturnMsg() != "success") {
                    ToastUtils.normal(mContext, allSearch.getReturnMsg()).show();
                    return;
                }
                AllSearchData(allSearch);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 添加  hade  title
     */
    private void initviewsTitle() {
        //都不为空时
        if (listPost.size() > 0 && listUser.size() > 0 && listQa.size() > 0) {
            listTitle = Arrays.asList("论坛", "问答", "用户");
            return;
        }
        //当论坛与问答不为空时 ，显示帖子与问答
        if (listPost.size() > 0 && listQa.size() > 0 && listUser.size() == 0) {
            listTitle = Arrays.asList("论坛", "问答");
            return;
        }
        //当论坛与用户不为空时 ，显示论坛与用户
        if (listPost.size() > 0 && listUser.size() > 0 && listQa.size() == 0) {
            listTitle = Arrays.asList("论坛", "用户");
            return;
        }
        //当用户与问答不为空时 ，显示用户与问答
        if (listQa.size() > 0 && listUser.size() > 0 && listPost.size() == 0) {
            listTitle = Arrays.asList("问答", "用户");
            return;
        }
        //当问答 与用户都为空时  只显示论坛
        if (listPost.size() > 0 && listUser.size() == 0 && listQa.size() == 0) {
            listTitle = Arrays.asList("论坛");
            return;
        }
        //帖子 与用户都为空时  只显示问答
        if (listQa.size() > 0 && listPost.size() == 0 && listUser.size() == 0) {
            listTitle = Arrays.asList("问答");
            return;
        }
        //帖子与问答都为空时  只显示用户
        if (listUser.size() > 0 && listPost.size() == 0 && listQa.size() == 0) {
            listTitle = Arrays.asList("用户");
            return;
        }

    }


    /**
     * 搜索全部 数据处理
     */

    private void AllSearchData(AllSearch model) {
        listPost = model.getContent().getPost();
        listQa = model.getContent().getQa();
        listUser = model.getContent().getUser();
        if (listPost.size() == 0 && listQa.size() == 0 && listUser.size() == 0) {
            no_search.setVisibility(View.VISIBLE);
            pinnListview.setVisibility(View.GONE);      //隐藏分组的listview
            search_recyler.setVisibility(View.GONE);    // 分别的listview
            search_scrollview.setVisibility(View.GONE); //最层的view
            return;
        }
        initviewsTitle();
        pinnListview.setVisibility(View.VISIBLE);  //全部的listview
        search_recyler.setVisibility(View.GONE);    //各自的listview
        search_scrollview.setVisibility(View.GONE);  //最外层的布局
        no_search.setVisibility(View.GONE);          //都为空时显示view
        searchAdapter = new SearchAdapter(listPost, listQa, listUser, mContext, content, listTitle);
        pinnListview.setAdapter(searchAdapter);
        pinnListview.setPinHeaders(true);
        pinnListview.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                Intent intent;
                if (listTitle.get(section).equals("论坛")) {
                    intent = new Intent(mContext, PostDetailsActivity.class);
                    intent.putExtra(Constants.TITLE, listPost.get(position).getTypeDesc());
                    intent.putExtra(Constants.POSTID, listPost.get(position).getPostId());
                    startActivity(intent);
                } else if (listTitle.get(section).equals("问答")) {
                    switch (listQa.get(position).getGroupType()) {
                        case 1:
                            intent = new Intent(mContext, BigshotQuestionActivity.class);
                            intent.putExtra(Constants.POSTID, listQa.get(position).getPostId());
                            intent.putExtra("from", "search");
                            startActivity(intent);
                            break;
                        case 2:
                        case 3:
                            intent = new Intent(mContext, QaDetalisActivity.class);
                            intent.putExtra(Constants.POSTID, listQa.get(position).getPostId());
                            startActivity(intent);
                            break;
                    }

                } else if (listTitle.get(section).equals("用户")) {
                    intent = new Intent(mContext, FensDetailsActivity.class);
                    intent.putExtra(Constants.USER_ID, listUser.get(position).getUserId());
                    startActivity(intent);
                }
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });

    }


    /**
     * 搜索帖子
     */

    private void getSearchResult4Post(final String content) {
        Call<SearchPostBean> call = communityService().getSearchResult4Post(content, userid, 1);
        NetUtils<SearchPostBean> netUtils = new NetUtils<SearchPostBean>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<SearchPostBean>() {
            @Override
            public void onResponseCallback(SearchPostBean searchPostBean) {
                if (searchPostBean.getReturnCode() != 0 && !searchPostBean.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, searchPostBean.getReturnMsg()).show();
                    return;
                }
                SearchPostData(searchPostBean);

            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 搜索论坛数据处理
     */
    private void SearchPostData(SearchPostBean model) {
        beanList = model.getContent();

        if (beanList != null && beanList.size() > 0) {
            search_recyler.setVisibility(View.VISIBLE);
            search_scrollview.setVisibility(View.GONE);
            //单独搜索 论坛 隐藏分组的listview
            pinnListview.setVisibility(View.GONE);
            no_search.setVisibility(View.GONE);
            adapter = new SearchPostAdapter(mContext, beanList, content, "homeType");
            search_recyler.setAdapter(adapter);
            search_recyler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchActivity.this, PostDetailsActivity.class);
                    intent.putExtra(Constants.TITLE, beanList.get(position).getTypeDesc());
                    intent.putExtra(Constants.POSTID, beanList.get(position).getPostId());
                    startActivity(intent);
                }
            });


        } else {
            no_search.setVisibility(View.VISIBLE);
            search_recyler.setVisibility(View.GONE);
            search_scrollview.setVisibility(View.GONE);
            pinnListview.setVisibility(View.GONE);
        }
    }


    /**
     * 搜索问答
     */
    private void getSearchResult4Qa(final String content) {
        Call<SearchQa> call = communityService().getSearchResult4QA(content, userid, 1);
        NetUtils<SearchQa> netUtils = new NetUtils<SearchQa>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<SearchQa>() {
            @Override
            public void onResponseCallback(SearchQa searchQa) {
                if (searchQa.getReturnCode() != 0 && !searchQa.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, searchQa.getReturnMsg()).show();
                    return;
                }
                SearchQaData(searchQa);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });
    }


    /**
     * 搜索问答数据处理
     *
     * @param searchQa
     */
    private void SearchQaData(SearchQa searchQa) {
        qabean = searchQa.getContent();
        if (qabean.size() == 0) {
            search_recyler.setVisibility(View.GONE);
            search_scrollview.setVisibility(View.GONE);
            pinnListview.setVisibility(View.GONE);
            no_search.setVisibility(View.VISIBLE);
            return;
        }
        search_scrollview.setVisibility(View.GONE);
        no_search.setVisibility(View.GONE);
        pinnListview.setVisibility(View.GONE);

        search_recyler.setVisibility(View.VISIBLE);
        qaAdapter = new AllQaAdapter(mContext, qabean, content, "homeQa");
        search_recyler.setAdapter(qaAdapter);
        search_recyler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (qabean.get(position).getGroupType()) {
                    case 1:
                        intent = new Intent(mContext, BigshotQuestionActivity.class);
                        intent.putExtra(Constants.POSTID, qabean.get(position).getPostId());
                        intent.putExtra("from", "search");
                        startActivity(intent);
                        break;
                    case 2:
                    case 3:
                        intent = new Intent(mContext, QaDetalisActivity.class);
                        intent.putExtra(Constants.POSTID, qabean.get(position).getPostId());
                        startActivity(intent);
                        break;
                }
            }
        });
        qaAdapter.notifyDataSetChanged();
    }


    /**
     * 搜索用户
     */

    private void getSearchResult4User(final String content) {
        Call<SearchUser> call = communityService().getSearchResult4User(content, userid, 1);
        NetUtils<SearchUser> netUtils = new NetUtils<SearchUser>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<SearchUser>() {
            @Override
            public void onResponseCallback(SearchUser searchUser) {
                if (searchUser.getReturnCode() != 0 && !searchUser.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, searchUser.getReturnMsg()).show();
                    return;
                }
                SearchUserData(searchUser);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }


    /**
     * 搜索用户数据处理
     *
     * @param searchUser
     */
    private void SearchUserData(SearchUser searchUser) {
        userList = searchUser.getContent();
        if (userList.size() == 0) {
            no_search.setVisibility(View.VISIBLE);
            search_recyler.setVisibility(View.GONE);
            search_scrollview.setVisibility(View.GONE);
            pinnListview.setVisibility(View.GONE);
            return;
        }
        search_recyler.setVisibility(View.VISIBLE);
        search_scrollview.setVisibility(View.GONE);
        //单独搜索 论坛 隐藏分组的listview
        pinnListview.setVisibility(View.GONE);
        no_search.setVisibility(View.GONE);

        searchUserAdapter = new AllUserAdapter(mContext, userList, content, "homeUser");
        search_recyler.setAdapter(searchUserAdapter);
        search_recyler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, FensDetailsActivity.class);
                intent.putExtra(Constants.USER_ID, userList.get(position).getUserId());
                startActivity(intent);
            }
        });
    }


    /**
     * 下拉框
     */
    private void showPopuDiow() {
        hIndicatorDialog = new HIndicatorBuilder(mContext)
                .width(180)
                .height(-1)
                .arrowDirection(HIndicatorBuilder.TOP)
                .bgColor(Color.parseColor("#cacacb"))
                .gravity(HIndicatorBuilder.GRAVITY_LEFT)
                .radius(8)
                .arrowWidth(1)
                .arrowRectage(2.15f)
                .data(list)
                .dimAmount(0.2f)
                .clickListener(this)
                .enableTouchOutside(true)
                .create();

        hIndicatorDialog.show(searchRoot);
    }


    //下啦控件监听事件
    @Override
    public void OnItemClick(int position) {
        search_xuanze_text.setText(list.get(position));
        string = search_xuanze_text.getText().toString();
        hIndicatorDialog.dismiss();
        String str = search_edit.getText().toString();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        hIndicatorAdapter = hIndicatorDialog.getAdapter();
        if (hIndicatorAdapter != null) {
            hIndicatorAdapter.changeColor(position);
        }
        switch (string) {
            case "全部":
                getSearchResult4All(str);
                break;
            case "论坛":
                getSearchResult4Post(str);
                break;
            case "问答":
                getSearchResult4Qa(str);
                break;
            case "用户":
                getSearchResult4User(str);
                break;
        }
    }


    /**
     * etdi 监听事件
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editcotent = search_edit.getText().toString();
        search_edit.setSelection(editcotent.length());
        if (!editcotent.isEmpty()) {
            search_qingchu.setVisibility(View.VISIBLE);
        } else {
            search_qingchu.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    //搜索按钮
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        content = search_edit.getText().toString();
        String text = search_xuanze_text.getText().toString();
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (TextUtils.isEmpty(content)) {
                ToastUtils.normal(mContext, "请输入搜索内容").show();
                return false;
            }
            switch (text) {
                case "全部":
                    getSearchResult4All(content);
                    break;
                case "论坛":
                    getSearchResult4Post(content);
                    break;
                case "问答":
                    getSearchResult4Qa(content);
                    break;
                case "用户":
                    getSearchResult4User(content);
                    break;
                default:
                    getSearchResult4All(content);
                    break;
            }
            return false;
        }
        return false;
    }


    /**
     * list 监听事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        search_edit.setText(arrayList.get(position) + "");
        content = arrayList.get(position) + "";
        string = search_xuanze_text.getText().toString();
        switch (string) {
            case "全部":
                getSearchResult4All(content);
                break;
            case "论坛":
                getSearchResult4Post(content);
                break;
            case "问答":
                getSearchResult4Qa(content);
                break;
            case "用户":
                getSearchResult4User(content);
                break;
            default:
                getSearchResult4All(content);
                break;
        }
    }
}
