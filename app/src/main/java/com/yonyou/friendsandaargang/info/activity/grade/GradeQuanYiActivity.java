package com.yonyou.friendsandaargang.info.activity.grade;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 等级权益
 */

public class GradeQuanYiActivity extends BaseActivity {


    @BindView(R.id.quanyi_imgae)
    ImageView imageView;
    @BindView(R.id.quanyi_tv1)
    TextView textView1;
    @BindView(R.id.quanyi_tv2)
    TextView textView2;
    @BindView(R.id.quanyi_tv3)
    TextView textView3;


    private Bundle bundle;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_quanyi);
        ButterKnife.bind(this);
        initviews();
    }


    private void initviews() {
        getTitleBar();
        setTitleText("等级权益");
        from = getIntent().getStringExtra("from");
        Logger.e("-------", from + "=======");


        switch (from) {
            case "quanyi1":
                imageView.setImageResource(R.drawable.ic_dj_v1);
                textView1.setText("财富值特权");
                textView2.setText("可通过累计的财富值进行等值的兑换");
                textView3.setText("财富值具体累计项目可进入“我的钱包进行查看”");
                break;
            case "quanyi2":
                imageView.setImageResource(R.drawable.ashd);
                textView1.setText("专属活动特权");
                textView2.setText("可参与LV2的专属活动");
                textView3.setText("专属活动将不定期召开，敬请期待");
                break;
            case "quanyi3":
                imageView.setImageResource(R.drawable.srtq);
                textView1.setText("生日特权");
                textView2.setText("会员升级可不定期获赠送福利礼包");
                textView3.setText("福利礼包将不定期发放，敬请期待");
                break;
        }


    }

}
