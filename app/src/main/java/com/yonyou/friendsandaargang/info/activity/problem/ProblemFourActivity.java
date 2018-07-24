package com.yonyou.friendsandaargang.info.activity.problem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.SolutionActivity;
import com.yonyou.friendsandaargang.info.adapter.ProblemFourAdapter;
import com.yonyou.friendsandaargang.info.adapter.ProblemTherrAdapter;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/5/15.
 */

public class ProblemFourActivity extends BaseActivity {


    private String title;

    @BindView(R.id.prolem_list_four)
    PinnedHeaderListView prolem_list;
    private ProblemFourAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolemfour);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
        }
        setTitleText(title);


        adapter = new ProblemFourAdapter(mContext);
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
                            intent.putExtra("title", "如何发帖？");
                            intent.putExtra("content", "在首页、论坛页面均可找到“+”，点击便可进行发帖。");
                            startActivity(intent);
                            break;

                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么帖子发布不成功？");
                            intent.putExtra("content", "如果帖子必填项没有填写完整/帖子中出现敏感词汇将发布不成功。");
                            startActivity(intent);
                            break;

                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么我的帖子会被删除？");
                            intent.putExtra("content", "如出现以下情况帖子将会被删除：\n" +
                                    "1、严禁一贴多发，刷帖等恶意灌水现象；\n" +
                                    "2、发帖请至正确板块，确保内容无误；\n" +
                                    "3、请勿使用辱骂等词汇进行人身攻击；\n" +
                                    "4、话题秉承言论自由，但请勿泄露他人隐私；\n" +
                                    "5、严禁发表危害国家安全、损害国家利益、破坏民族团结、破坏国家宗教政策、破坏社会稳定、侮辱、诽谤、淫秽、色情等内容；\n" +
                                    "6、不允许在内容发布任何广告性质的内容；");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何成为精华帖/置顶帖？");
                            intent.putExtra("content", "精华帖/置顶帖至少需达到以下要求：\n" +
                                    "1、标题达意清晰，没有错别字；\n" +
                                    "2、正文内容字数超过200字、图片超过5张；\n" +
                                    "3、图片需要高清可辨，不可出现模糊、虚像的情况；\n" +
                                    "4、内容原创，观点客观，逻辑清楚；\n" +
                                    "若达到以上要求，将有机会成为精华帖/置顶帖。");
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何删除帖子？");
                            intent.putExtra("content", "“我的”—“我的发布”侧滑可删除自己发布的内容，如发现恶意内容可联系客服进行举报。");
                            startActivity(intent);
                            break;

                        case 5:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何删除帖子的评论？");
                            intent.putExtra("content", "“帖子的评论无法删除，仅可通过删除帖子主体进行删除。");
                            startActivity(intent);
                            break;
                    }
                }

                if (section == 1) {
                    switch (position) {
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何发布话题？");
                            intent.putExtra("content", "在首页、论坛页面均可找到“+”，点击便可发布话题。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "话题和帖子的区别是什么？");
                            intent.putExtra("content", "话题更注重于讨论，着重于对于内容的评论。");
                            startActivity(intent);
                            break;

                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么话题发布不成功？");
                            intent.putExtra("content", "如果话题必填项没有填写完整/话题中出现敏感词汇将发布不成功。");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么我的话题会被删除？");
                            intent.putExtra("content", "如出现以下情况话题将会被删除：\n" +
                                    "1、严禁一贴多发，刷帖等恶意灌水现象；\n" +
                                    "2、发话题请至正确板块，确保内容无误；\n" +
                                    "3、请勿使用辱骂等词汇进行人身攻击；\n" +
                                    "4、话题秉承言论自由，但请勿泄露他人隐私；\n" +
                                    "5、严禁发表危害国家安全、损害国家利益、破坏民族团结、破坏国家宗教政策、破坏社会稳定、侮辱、诽谤、淫秽、色情等内容；\n" +
                                    "6、不允许在内容发布任何广告性质的内容；");
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何成为热议话题？");
                            intent.putExtra("content", "热议话题均由后台逻辑进行判断生成。");
                            startActivity(intent);
                            break;

                    }
                }


                if (section == 2) {
                    switch (position) {
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何删除我的评论？");
                            intent.putExtra("content", "评论无法删除，烦请评论时确认后发布。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么我的评论不见了？");
                            intent.putExtra("content", "如出现以下情况评论将被删除：\n" +
                                    "1、出现恶意灌水现象；\n" +
                                    "2、使用辱骂等词汇进行人身攻击；\n" +
                                    "3、评论秉承言论自由，但请勿泄露他人隐私；\n" +
                                    "4、严禁发表危害国家安全、损害国家利益、破坏民族团结、破坏国家宗教政策、破坏社会稳定、侮辱、诽谤、淫秽、色情等内容；\n" +
                                    "5、在评论发布广告性质内容，如推广、二维码等；");
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
