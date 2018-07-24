package com.yonyou.friendsandaargang.info.activity.problem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.SolutionActivity;
import com.yonyou.friendsandaargang.info.adapter.ProblemAdapter;
import com.yonyou.friendsandaargang.info.adapter.ProblemTwoAdapter;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/5/15.
 */

public class ProblemTwoActivity extends BaseActivity {


    private String title;

    @BindView(R.id.prolem_list_two)
    PinnedHeaderListView prolem_list;
    private ProblemTwoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolemtwo);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
        }
        setTitleText(title);


        adapter = new ProblemTwoAdapter(mContext);
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
                            intent.putExtra("title", "如何获得财富值？");
                            intent.putExtra("content", "进入“我的”—“我的钱包”—“我的财富值”可查阅财富值的关联行为，点击右上方明细可查看财富值的具体变化。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "财富值有什么用处？");
                            intent.putExtra("content", "财富值可进行金币兑换、实物兑换等。");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么财富值会有扣除的情况？");
                            intent.putExtra("content", "当存在删帖、恶意灌水等行为时，财富值将进行对应的扣除，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。。");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么财富值没有到账？");
                            intent.putExtra("content", "完成任务后经验值会自动到账，可能会有一定的延迟，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通");
                            startActivity(intent);
                            break;
                    }
                }
/*
                if (section == 1) {
                    switch (position) {
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么部分金币无法提现？");
                            intent.putExtra("content", "财富值兑换金币无法进行提现，仅可进行消费。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "金币如何提现？");
                            intent.putExtra("content", "进入“我的”—“我的钱包”—“我的金币”—“提现”可进行提现，提现将直接返回原账户，预计15个工作日到账。");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "金币如何充值？");
                            intent.putExtra("content", "进入“我的”—“我的钱包”—“我的金币”—“充值”可通过微信、支付宝等渠道进行充值。");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "金币有什么用处？");
                            intent.putExtra("content", "金币作为站内的流通货币进行使用，可进行问答悬赏、购买增值服务等。");
                            startActivity(intent);
                            break;
                    }
                }*/
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
                switch (section) {
                }
            }
        });
    }
}
