package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.forum.bean.ForumState;
import com.yonyou.friendsandaargang.guide.adapter.SectionedBaseAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/8.
 */

public class ProblemAdapter extends SectionedBaseAdapter {


    private Context context;
    private List<String> titleList;
    private List<String> contentList;
    private List<String> typeList;


    private List<String> titleList1;
    private List<String> contentList1;

    private List<String> titleList2;
    private List<String> contentList2;


    public ProblemAdapter(Context context) {
        this.context = context;
        initList();
    }


    private void initList() {
        typeList = Arrays.asList("注册登录", "个人资料", "等级认证");
        titleList = Arrays.asList("如何进行登录？"
                , "如何进行注册？"
                , "忘记密码怎么办？"
                , "如何修改密码？"

        );
        contentList = Arrays.asList("可使用用户名+密码/手机号+验证码的形式登录，后续将开通第三方登录，敬请期待。"
                , "设置用户名，输入手机号及验证码，确认密码无误即可进行注册。"
                , "可通过手机号验证码确认重置密码。"
                , "进入“我的”—“设置”—“账号与安全”—“登录密码”可对密码进行修改。"
        );


        titleList1 = Arrays.asList(
                "如何更改手机号绑定？"
                , "如何更改微信绑定？"
                , "如何更改QQ绑定？"
                , "是否可以解绑手机号？"
                , "如何修改用户名？"
                , "如何修改个人资料？");

        contentList1 = Arrays.asList(
                "通过旧手机号绑定输入验证码即可重新绑定新手机号，若手机号遗失可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。"
                , "进入“我的”—“设置”—“账号与安全”对微信进行修改绑定。"
                , "进入“我的”—“设置”—“账号与安全”对QQ进行修改绑定。"
                , "为了账号安全，目前无法进行解绑，只能更换绑定手机号。"
                , "用户名无法进行修改，可进入“我的”—“个人中心”对昵称进行修改。"
                , "进入“我的”—“个人中心”可对个人资料进行修改。");

        titleList2 = Arrays.asList("等级如何判定？"
                , "等级有什么用处？"
                , "如何获得经验值？"
                , "为什么经验值会有扣除的情况？"
                , "为什么经验值没有到账？"
                , "如何进行认证？"
                , "认证审核时间多久？"
                , "认证有什么用处？"
                , "如何能成为问答大咖？"
                , "为什么会认证不成功？"
                , "如果认证的资料需要变更怎么办？"

        );

        contentList2 = Arrays.asList(
                "进入“我的”—“个人中心”—“我的等级”可查阅等级的关联行为，点击右上方明细可查看等级分数的具体变化。"
                , "不同等级将享有不同服务，LV1将获得财富值兑换服务，LV2将享有专属活动，LV3及以上可不定期获赠升级的福利礼包，更多权益敬请期待。"
                , "身份认证、上传头像、新人任务、内容产出、签到均可获得对应经验值，具体可见“我的”—“个人中心”—“我的等级”。"
                , "当存在删帖、恶意灌水等行为时，经验值将进行对应的扣除，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。"
                , "完成任务后经验值会自动到账，可能会有一定的延迟，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。"
                , "进入“我的”—“个人中心”—“认证中心”可进行不同身份的认证，按照身份完成内容录入即可。"
                , "认证将在1个工作日内审核完毕，烦请耐心等待。"
                , "个人认证后将可参与悬赏问答中的对应领域问题，将有机会获得悬赏金额。专家认证后将成为友车帮专家问答专区一份子，可接受用户的单独提问。用友经销商认证后可使用相关企业服务。"
                , "专家需在垂直领域有一定影响力，如已认证“个人认证”及“用友经销商认证”的用户也可申请成为问答专家。如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。"
                , "烦请检查提交的资料，确保资料准确无误，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。"
                , "进入“我的”—“个人中心”—“认证中心”进行身份解绑后重新认证即可。"

        );

    }


    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }


    //
    @Override    //头部数量
    public int getSectionCount() {
        return typeList.size();
    }


    //这个是只栏目的数据
    @Override
    public int getCountForSection(int section) {
        if (section == 0) {
            return titleList.size();
        } else if (section == 1) {
            return titleList1.size();

        } else if (section == 2) {
            return titleList2.size();
        }
        return 0;
    }


    //每个item
    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        ViewHodler viewHodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_problem_forum, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);

        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }


        switch (section) {
            case 0:
                viewHodler.problem_tv1.setText(titleList.get(position));
                viewHodler.problem_tv2.setText(contentList.get(position));
                break;
            case 1:
                viewHodler.problem_tv1.setText(titleList1.get(position));
                viewHodler.problem_tv2.setText(contentList1.get(position));

                break;
            case 2:
                viewHodler.problem_tv1.setText(titleList2.get(position));
                viewHodler.problem_tv2.setText(contentList2.get(position));
                break;
        }

        return convertView;
    }


    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.listview_head, null);
        } else {
            layout = (LinearLayout) convertView;
        }

        ((TextView) layout.findViewById(R.id.xingqu_text)).setText(typeList.get(section));


        return layout;
    }


    public class ViewHodler {
        //title
        @BindView(R.id.problem_tv1)
        TextView problem_tv1;
        //内容
        @BindView(R.id.problem_tv2)
        TextView problem_tv2;

        public ViewHodler(View view) {
            ButterKnife.bind(this, view);
        }

    }


}
