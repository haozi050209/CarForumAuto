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

public class ProblemTherrAdapter extends SectionedBaseAdapter {


    private Context context;
    private List<String> titleList;
    private List<String> contentList;
    private List<String> typeList;


    private List<String> titleList1;
    private List<String> contentList1;


    public ProblemTherrAdapter(Context context) {
        this.context = context;
        initList();
    }


    private void initList() {
        typeList = Arrays.asList("资料库相关", "用友经销商");
        titleList = Arrays.asList("资料库的内容从哪里来？"
                , "为什么资料库的使用需要认证？"
                , "资料库的内容如何上传？"

        );
        contentList = Arrays.asList("资料库的内容来源为两部分，一部分为车厂下发内容，一部分为认证用户上传内容，以上内容均有查看权限设置，如认证用户上传的内容仅存在同品牌认证用户资料库中。"
                , "资料库暂时仅开放给企业用户使用，所以需要先认证后才可进行使用，后续敬请期待。"
                , "烦请发送用户名、手机号、共享文档至youchebang@yonyou.com；\n" +
                        "3个工作日内将完成审核,请耐心等待；"
        );


        titleList1 = Arrays.asList(
                "为用友的经销商提供了哪些服务？"
                //, "如何使用短信充值？"
                , "如果我不是用友经销商，要怎么使用资料库等服务？"
        );

        contentList1 = Arrays.asList(
                "可提报问题、查询问题处理进度、确认问题解决情况、短信充值等服务。"
                //, "进入“我的”—“ 用友DMS运营管家”—“短信”通过金币可购买短信充值。"
                , "资料库功能暂时仅对用友经销商开放，如有加入想法可联系客服进行沟通。（客服入口为“设置”—“求助反馈”-“问题求助”）"
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
