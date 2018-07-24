package com.yonyou.friendsandaargang.info.activity.problem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.activity.SolutionActivity;
import com.yonyou.friendsandaargang.info.adapter.ProblemAdapter;
import com.yonyou.friendsandaargang.view.PinnedHeaderListView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/5/15.
 */

public class ProblemActivity extends BaseActivity {


    private String title;

    @BindView(R.id.prolem_list)
    PinnedHeaderListView prolem_list;
    private ProblemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolem);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        if (getIntent().getStringExtra("title") != null) {
            title = getIntent().getStringExtra("title");
        }
        setTitleText(title);


        adapter = new ProblemAdapter(mContext);
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
                            intent.putExtra("title", "如何进行登录");
                            intent.putExtra("content", "可使用用户名+密码/手机号+验证码的形式登录，也可关联QQ/微信进行快捷登录。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何进行注册");
                            intent.putExtra("content", "设置用户名，输入手机号及验证码，确认密码无误即可进行注册。");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "忘记密码怎么办");
                            intent.putExtra("content", "可通过手机号验证码确认重置密码。");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何修改密码");
                            intent.putExtra("content", "进入“我的”—“设置”—“账号与安全”—“登录密码”可对密码进行修改。");
                            startActivity(intent);
                            break;
                    }
                }

                if (section == 1) {
                    switch (position) {
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何更改手机号绑定?");
                            intent.putExtra("content", "通过旧手机号绑定输入验证码即可重新绑定新手机号，若手机号遗失可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何更改微信绑定?");
                            intent.putExtra("content", "进入“我的”—“设置”—“账号与安全”对微信进行修改绑定。");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何更改QQ绑定？");
                            intent.putExtra("content", "进入“我的”—“设置”—“账号与安全”对QQ进行修改绑定。");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "是否可以解绑手机号？");
                            intent.putExtra("content", "为了账号安全，目前无法进行解绑，只能更换绑定手机号。");
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何修改用户名？");
                            intent.putExtra("content", "用户名无法进行修改，可进入“我的”—“个人中心”对昵称进行修改。");
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何修改个人资料？");
                            intent.putExtra("content", "进入“我的”—“个人中心”可对个人资料进行修改。");
                            startActivity(intent);
                            break;
                    }
                }
                if (section == 2) {
                    switch (position) {
                        case 0:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "等级如何判定？");
                            intent.putExtra("content", "进入“我的”—“个人中心”—“我的等级”可查阅等级的关联行为，点击右上方明细可查看等级分数的具体变化。");
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "等级有什么用处？");
                            intent.putExtra("content", "不同等级将享有不同服务，LV1将获得财富值兑换服务，LV2将享有专属活动，LV3及以上可不定期获赠升级的福利礼包，更多权益敬请期待。");
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何获得经验值？");
                            intent.putExtra("content", "身份认证、上传头像、新人任务、内容产出、签到均可获得对应经验值，具体可见“我的”—“个人中心”—“我的等级”。");
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么经验值会有扣除的情况？");
                            intent.putExtra("content", "当存在删帖、恶意灌水等行为时，经验值将进行对应的扣除，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。");
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么经验值没有到账？");
                            intent.putExtra("content", "完成任务后经验值会自动到账，可能会有一定的延迟，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。");
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何进行认证？");
                            intent.putExtra("content", "进入“我的”—“个人中心”—“认证中心”可进行不同身份的认证，按照身份完成内容录入即可。");
                            startActivity(intent);
                            break;
                        case 6:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "认证审核时间多久？");
                            intent.putExtra("content", "认证将在1个工作日内审核完毕，烦请耐心等待。");
                            startActivity(intent);
                            break;
                        case 7:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "认证有什么用处？");
                            intent.putExtra("content", "个人认证后将可参与悬赏问答中的对应领域问题，将有机会获得悬赏金额。专家认证后将成为友车帮专家问答专区一份子，可接受用户的单独提问。用友经销商认证后可使用相关企业服务。");
                            startActivity(intent);
                            break;
                        case 8:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如何能成为问答大咖？");
                            intent.putExtra("content", "专家需在垂直领域有一定影响力，如已认证“个人认证”及“用友经销商认证”的用户也可申请成为问答专家。如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。");
                            startActivity(intent);
                            break;
                        case 9:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "为什么会认证不成功？");
                            intent.putExtra("content", "烦请检查提交的资料，确保资料准确无误，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。");
                            startActivity(intent);
                            break;
                        case 10:
                            intent = new Intent(mContext, SolutionActivity.class);
                            intent.putExtra("title", "如果认证的资料需要变更怎么办？");
                            intent.putExtra("content", "进入“我的”—“个人中心”—“认证中心”进行身份解绑后重新认证即可。");
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
