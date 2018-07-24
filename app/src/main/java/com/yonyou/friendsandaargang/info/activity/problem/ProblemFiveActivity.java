package com.yonyou.friendsandaargang.info.activity.problem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.SolutionActivity;
import com.yonyou.friendsandaargang.info.adapter.ProblemFiveAdapter;
import com.yonyou.friendsandaargang.info.adapter.ProblemFourAdapter;
import com.yonyou.friendsandaargang.info.adapter.ProblemTwoAdapter;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/5/15.
 */

public class ProblemFiveActivity extends BaseActivity {


    private String title;

    @BindView(R.id.prolem_list_five)
    PinnedHeaderListView prolem_list;
    private ProblemFiveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolemfive);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
        }
        setTitleText(title);


        adapter = new ProblemFiveAdapter(mContext);
        prolem_list.setAdapter(adapter);
        prolem_list.setPinHeaders(true);

        prolem_list.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                Intent intent;

                if (section == 0) {
                    switch (position) {
                        /*case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如果没人回答赏金如何处理？");
                            intent.putExtra("content", "如果无人回答，赏金将在24小时内返回账户。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "我没有选中答案为什么赏金没有退回？");
                            intent.putExtra("content", "已经有认证领域用户进行回答，若没有选中其中的答案，赏金将平分给参与回答的用户。");
                            startActivity(intent);
                            break;*/
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么我无法回答悬赏问题？");
                            intent.putExtra("content", "悬赏问题需认证为该领域用户才可进行回答，可至“我的”—“个人中心”对身份进行认证。");
                            startActivity(intent);
                            break;
                    }
                }

                if (section == 1) {
                    switch (position) {
                        /*case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "问题已经回答了，查看的时候为什么需要支付金币？");
                            intent.putExtra("content", "专家回答的问题，如需查看需支付1元费用，该费用将由专家和提问者平分。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么每个专家的金额不一样？");
                            intent.putExtra("content", "专家的提问价格为自行设定，烦请确认后进行支付提问，一旦专家进行回答，则费用无法退还。");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "专家回答的不满意怎么办？");
                            intent.putExtra("content", "一旦向专家提问，若专家进行回答，则费用无法退还，还请在提问前确认后进行支付。");
                            startActivity(intent);
                            break;*/
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "公开和私密提问的区别在哪里？");
                            intent.putExtra("content", "公开提问后别的用户可查看大咖的答案；私密提问后别的用户无法进行查看");
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
