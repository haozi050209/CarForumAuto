package com.yonyou.friendsandaargang.info.activity.gold;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.bean.ExchangeBean;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TextTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/5/30.
 */

public class ProductDetalisActivity extends BaseActivity {


    @BindView(R.id.prodetalis_img)
    CircleImageView imageView;
    @BindView(R.id.prodetalis_jb)
    TextView tvJb;
    @BindView(R.id.prodetalis_jb1)
    TextView tvJb1;
    @BindView(R.id.prodetalis_cfz)
    TextView tvCfz;
    @BindView(R.id.prodetalis_sm)
    TextView tvSm;
    @BindView(R.id.prodetalis_lc)
    TextView tvLc;
    @BindView(R.id.prodetalis_sx)
    TextView tvSx;

    private String userId;
    private String exchangeItemId;
    private ExchangeBean.ContentBean list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodetalis);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        list = (ExchangeBean.ContentBean) getIntent().getBundleExtra("bundle").getSerializable("list");
        setTitleText(list.getExchangeItemName());
        exchangeItemId = list.getExchangeItemId();

        Glide.with(mContext)
                .load(list.getExchangeItemPhoto())
                .error(R.drawable.user)
                .placeholder(R.drawable.user)
                .into(imageView);
        tvJb.setText(list.getExchangeItemName());
        TextTool.getBuilder("")
                .append(list.getRealPrice())
                .setStrikethrough()
                .into(tvJb1);
        tvCfz.setText(list.getFortuneNeeded());
        tvSm.setText("详细说明:\n" + list.getExchangeItemDesc());
        tvLc.setText("兑换流程:\n" + list.getExchangeProcess());
        tvSx.setText("注意事项:\n" + list.getExchangeTip());
    }

    @OnClick(R.id.prodetalis_dh)
    public void onViewClicked() {
        Call<HttpResult> call = communityService().exchangeUserFortune4Item(userId, exchangeItemId);
        NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult httpResult) {
                ToastUtils.normal(httpResult.getReturnMsg());
            }

            @Override
            public void onFailureCallback() {

            }
        });
    }
}
