package com.yonyou.friendsandaargang.info.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.SolutionActivity;
import com.yonyou.friendsandaargang.info.adapter.SearchResultAdapter;
import com.yonyou.friendsandaargang.info.bean.FAQByKeyWord;
import com.yonyou.friendsandaargang.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/4/11.
 */

public class SearchResultActivity extends BaseActivity {


    private List<FAQByKeyWord.ContentBean> contentBeanList;


    @BindView(R.id.search_result_list)
    MyListView search_result_list;

    private SearchResultAdapter searchResultAdapter;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("搜索结果");
        initviews();
    }

    private void initviews() {
        if (getIntent().getSerializableExtra("data") != null) {
            contentBeanList = (List<FAQByKeyWord.ContentBean>) getIntent().getSerializableExtra("data");
        }

        if (getIntent().getStringExtra("keyword") != null) {
            keyword = getIntent().getStringExtra("keyword");
        }

        searchResultAdapter = new SearchResultAdapter(SearchResultActivity.this, contentBeanList, keyword);
        search_result_list.setAdapter(searchResultAdapter);
        search_result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultActivity.this, SolutionActivity.class);
                intent.putExtra("title", contentBeanList.get(position).getTitle());
                intent.putExtra("content", contentBeanList.get(position).getContent());
                startActivity(intent);

            }
        });
    }
}
