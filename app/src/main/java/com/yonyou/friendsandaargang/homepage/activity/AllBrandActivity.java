package com.yonyou.friendsandaargang.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.homepage.adapter.AllBrandAdapter;
import com.yonyou.friendsandaargang.homepage.modle.AllBrand;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/4/9.
 */

public class AllBrandActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.allbrand_list)
    ListView allbrand_list;

    private List<AllBrand.ContentBean> beanList;
    private AllBrandAdapter allBrandAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allbrand_activity);
        ButterKnife.bind(this);
        initviews();
        setAllbrandData();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择品牌");
    }


    /**
     * 获取所有得品牌
     */
    private void setAllbrandData() {
        Call<AllBrand> call = communityService().getgetBrandAll();
        NetUtils<AllBrand> netUtils = new NetUtils<AllBrand>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<AllBrand>() {
            @Override
            public void onResponseCallback(AllBrand allBrand) {
                if (allBrand.getReturnCode() != 0 && !allBrand.getReturnMsg().equals("success")) {
                    return;
                }
                initData(allBrand);
            }

            @Override
            public void onFailureCallback() {
                ToastUtils.normal(mContext, "服务器加载异常").show();
            }
        });

    }


    private void initData(AllBrand model) {
        beanList = model.getContent();
        allBrandAdapter = new AllBrandAdapter(mContext, beanList);
        allbrand_list.setAdapter(allBrandAdapter);
        allbrand_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("BrandName", beanList.get(position).getBrandName());
        intent.putExtra("BrandId", beanList.get(position).getBrandId());
        setResult(200, intent);
        finish();
    }
}
