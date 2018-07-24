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

public class ProblemFiveAdapter extends SectionedBaseAdapter {


    private Context context;
    private List<String> titleList;
    private List<String> contentList;
    private List<String> typeList;


    private List<String> titleList1;
    private List<String> contentList1;


    public ProblemFiveAdapter(Context context) {
        this.context = context;
        initList();
    }


    private void initList() {
        typeList = Arrays.asList("悬赏相关", "专家相关");
        titleList = Arrays.asList("为什么我无法回答悬赏问题？"
//                , "我没有选中答案为什么赏金没有退回？"
//                , "为什么我无法回答悬赏问题？"

        );
        contentList = Arrays.asList(
//                "如果无人回答，赏金将在24小时内返回账户。"
//                , "已经有认证领域用户进行回答，若没有选中其中的答案，赏金将平分给参与回答的用户。"
//                ,
                "悬赏问题需认证为该领域用户才可进行回答，可至“我的”—“个人中心”对身份进行认证。"
        );


        titleList1 = Arrays.asList(
//                "问题已经回答了，查看的时候为什么需要支付金币？"
//                , "为什么每个专家的金额不一样？"
//                , "专家回答的不满意怎么办？"
//                ,
                "公开和私密提问的区别在哪里？");

        contentList1 = Arrays.asList(
//                "专家回答的问题，如需查看需支付1元费用，该费用将由专家和提问者平分。"
//                , "专家的提问价格为自行设定，烦请确认后进行支付提问，一旦专家进行回答，则费用无法退还。"
//                , "一旦向专家提问，若专家进行回答，则费用无法退还，还请在提问前确认后进行支付。"
//                ,
                "公开提问后别的用户可查看大咖的答案；私密提问后别的用户无法进行查看");


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
