package com.yonyou.friendsandaargang.info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.guide.adapter.SectionedBaseAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/8.
 */

public class ProblemTwoAdapter extends SectionedBaseAdapter {


    private Context context;
    private List<String> titleList;
    private List<String> contentList;
    private List<String> typeList;


    private List<String> titleList1;
    private List<String> contentList1;


    public ProblemTwoAdapter(Context context) {
        this.context = context;
        initList();
    }


    private void initList() {
        typeList = Arrays.asList("财富值相关");
        titleList = Arrays.asList("如何获得财富值？"
                , "财富值有什么用处？"
                , "为什么财富值会有扣除的情况？"
                , "为什么财富值没有到账？"

        );
        contentList = Arrays.asList("进入“我的”—“我的钱包”—“我的财富值”可查阅财富值的关联行为，点击右上方明细可查看财富值的具体变化。"
                , "财富值可进行金币兑换、实物兑换等。"
                , "当存在删帖、恶意灌水等行为时，财富值将进行对应的扣除，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。"
                , "完成任务后经验值会自动到账，可能会有一定的延迟，如有疑问可通过“设置”—“求助反馈”-“问题求助”寻找客服进行沟通。"
        );

       /* titleList1 = Arrays.asList(
                "为什么部分金币无法提现？"
                , "金币如何提现？"
                , "金币如何充值？"
                , "金币有什么用处？");

        contentList1 = Arrays.asList(
                "财富值兑换金币无法进行提现，仅可进行消费。"
                , "进入“我的”—“我的钱包”—“我的金币”—“提现”可进行提现，提现将直接返回原账户，预计15个工作日到账。"
                , "进入“我的”—“我的钱包”—“我的金币”—“充值”可通过微信、支付宝等渠道进行充值。"
                , "金币作为站内的流通货币进行使用，可进行问答悬赏、购买增值服务等。");*/


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
