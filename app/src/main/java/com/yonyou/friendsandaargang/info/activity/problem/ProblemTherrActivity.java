package com.yonyou.friendsandaargang.info.activity.problem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.SolutionActivity;
import com.yonyou.friendsandaargang.info.adapter.ProblemTherrAdapter;
import com.yonyou.friendsandaargang.info.adapter.ProblemTwoAdapter;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/5/15.
 */

public class ProblemTherrActivity extends BaseActivity {


    private String title;

    @BindView(R.id.prolem_list_therr)
    PinnedHeaderListView prolem_list;
    private ProblemTherrAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolemtherr);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
        }
        setTitleText(title);


        adapter = new ProblemTherrAdapter(mContext);
        prolem_list.setAdapter(adapter);
        prolem_list.setPinHeaders(true);

        prolem_list.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                Intent intent;

                if (section == 0) {
                    switch (position) {
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "资料库的内容从哪里来？");
                            intent.putExtra("content", "资料库的内容来源为两部分，一部分为车厂下发内容，一部分为认证用户上传内容，以上内容均有查看权限设置，如认证用户上传的内容仅存在同品牌认证用户资料库中。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么资料库的使用需要认证？");
                            intent.putExtra("content", "资料库暂时仅开放给企业用户使用，所以需要先认证后才可进行使用，后续敬请期待。");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "资料库的内容如何上传？");
                            intent.putExtra("content", "烦请发送用户名、手机号、共享文档至youchebang@yonyou.com；\n" +
                                    "3个工作日内将完成审核,请耐心等待；");
                            startActivity(intent);
                            break;
                    }
                }

                if (section == 1) {
                    switch (position) {
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为用友的经销商提供了哪些服务？");
                            intent.putExtra("content", "可提报问题、查询问题处理进度、确认问题解决情况、短信充值等服务。");
                            startActivity(intent);
                            break;
                      /*  case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何使用短信充值？");
                            intent.putExtra("content", "进入“我的”—“ 用友DMS运营管家”—“短信”通过金币可购买短信充值。");
                            startActivity(intent);
                            break;*/
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如果我不是用友经销商，要怎么使用资料库等服务？");
                            intent.putExtra("content", "资料库功能暂时仅对用友经销商开放，如有加入想法可联系客服进行沟通。（客服入口为“设置”—“求助反馈”-“问题求助。");
                            startActivity(intent);
                            break;
                    }
                }
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
                switch (section) {
                }
            }
        });
    }
}
