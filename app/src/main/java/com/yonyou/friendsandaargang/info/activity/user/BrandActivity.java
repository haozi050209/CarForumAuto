package com.yonyou.friendsandaargang.info.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.adapter.BrandAdapter;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 选择品牌
 */

public class BrandActivity extends BaseActivity {

    @BindView(R.id.brand_list)
    ListView brand_list;
    private BrandAdapter brandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择品牌");
        initDatas();
    }

    private void initDatas() {
        brandAdapter = new BrandAdapter(mContext);
        brand_list.setAdapter(brandAdapter);
        brand_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.error(mContext, "点击事件");
            }
        });
    }
}
