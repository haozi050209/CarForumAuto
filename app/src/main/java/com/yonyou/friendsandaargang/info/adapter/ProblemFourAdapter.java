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

public class ProblemFourAdapter extends SectionedBaseAdapter {


    private Context context;
    private List<String> titleList;
    private List<String> contentList;
    private List<String> typeList;


    private List<String> titleList1;
    private List<String> contentList1;


    private List<String> titleList2;
    private List<String> contentList2;

    public ProblemFourAdapter(Context context) {
        this.context = context;
        initList();
    }


    private void initList() {
        typeList = Arrays.asList("帖子内容", "话题内容", "评论相关");
        titleList = Arrays.asList(
                "如何发帖？"
                , "为什么帖子发布不成功？"
                , "为什么我的帖子会被删除？"
                , "如何成为精华帖/置顶帖？"
                , "如何删除帖子？"
                , "如何删除帖子的评论？"

        );
        contentList = Arrays.asList(
                "在首页、论坛页面均可找到“+”，点击便可进行发帖。"
                , "如果帖子必填项没有填写完整/帖子中出现敏感词汇将发布不成功。"
                , "如出现以下情况帖子将会被删除：\n" +
                        "1、严禁一贴多发，刷帖等恶意灌水现象；\n" +
                        "2、发帖请至正确板块，确保内容无误；\n" +
                        "3、请勿使用辱骂等词汇进行人身攻击；\n" +
                        "4、话题秉承言论自由，但请勿泄露他人隐私；\n" +
                        "5、严禁发表危害国家安全、损害国家利益、破坏民族团结、破坏国家宗教政策、破坏社会稳定、侮辱、诽谤、淫秽、色情等内容；\n" +
                        "6、不允许在内容发布任何广告性质的内容；"
                , "精华帖/置顶帖至少需达到以下要求：\n" +
                        "1、标题达意清晰，没有错别字；\n" +
                        "2、正文内容字数超过200字、图片超过5张；\n" +
                        "3、图片需要高清可辨，不可出现模糊、虚像的情况；\n" +
                        "4、内容原创，观点客观，逻辑清楚；\n" +
                        "若达到以上要求，将有机会成为精华帖/置顶帖。"
                , "“我的”—“我的发布”侧滑可删除自己发布的内容，如发现恶意内容可联系客服进行举报。"
                , "“帖子的评论无法删除，仅可通过删除帖子主体进行删除。"
        );


        titleList1 = Arrays.asList(
                "如何发布话题？"
                ,"话题和帖子的区别是什么？"
                ,"为什么话题发布不成功？"
                , "为什么我的话题会被删除？"
                , "如何成为热议话题？"
        );

        contentList1 = Arrays.asList(
                   "在首页、论坛页面均可找到“+”，点击便可发布话题。"
                ,"话题更注重于讨论，着重于对于内容的评论。"
                ,"如果话题必填项没有填写完整/话题中出现敏感词汇将发布不成功。"
                ,"如出现以下情况话题将会被删除：\n" +
                        "1、严禁一贴多发，刷帖等恶意灌水现象；\n" +
                        "2、发话题请至正确板块，确保内容无误；\n" +
                        "3、请勿使用辱骂等词汇进行人身攻击；\n" +
                        "4、话题秉承言论自由，但请勿泄露他人隐私；\n" +
                        "5、严禁发表危害国家安全、损害国家利益、破坏民族团结、破坏国家宗教政策、破坏社会稳定、侮辱、诽谤、淫秽、色情等内容；\n" +
                        "6、不允许在内容发布任何广告性质的内容；"
                , "热议话题均由后台逻辑进行判断生成。"
        );


        titleList2 = Arrays.asList("如何删除我的评论？"
                , "为什么我的评论不见了？");

        contentList2 = Arrays.asList(
                "评论无法删除，烦请评论时确认后发布。"
                , "如出现以下情况评论将被删除：\n" +
                        "1、出现恶意灌水现象；\n" +
                        "2、使用辱骂等词汇进行人身攻击；\n" +
                        "3、评论秉承言论自由，但请勿泄露他人隐私；\n" +
                        "4、严禁发表危害国家安全、损害国家利益、破坏民族团结、破坏国家宗教政策、破坏社会稳定、侮辱、诽谤、淫秽、色情等内容；\n" +
                        "5、在评论发布广告性质内容，如推广、二维码等；"
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
