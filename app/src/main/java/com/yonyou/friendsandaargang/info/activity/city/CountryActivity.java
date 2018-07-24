package com.yonyou.friendsandaargang.info.activity.city;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.CountryAdapter;
import com.yonyou.friendsandaargang.info.bean.Country;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 选择国家
 */

public class CountryActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.country_listview)
    ListView country_listview;
    private CountryAdapter countryAdapter;
    private List<Country.ContentBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择国家");
        getCountry();
    }

    private void getCountry() {
        Call<Country> call = communityService().getCountryList();
        call.enqueue(new NetRetrofitCallback<Country>(mContext, new HttpCallBackImpl<Country>() {
            @Override
            public void onResponseCallback(Country model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                initDatas(model);

            }
        }));
    }


    private void initDatas(Country model) {
        list = model.getContent();
        countryAdapter = new CountryAdapter(CountryActivity.this, list);
        country_listview.setAdapter(countryAdapter);
        country_listview.setOnItemClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 1000) {
            //把之前传回来的值返回给最外面一个activity
            setResult(9527, data);
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (list.get(position).getCountryId().equals("1")) {
            Intent intent = new Intent(CountryActivity.this, ProvinceActivity.class);
            intent.putExtra(Constants.COUNTRYID, list.get(position).getCountryId());
            startActivityForResult(intent, 2);
        } else {
            Intent intent = new Intent();
            intent.putExtra("areaId", list.get(position).getCountryId());
            intent.putExtra(Constants.REGINON, list.get(position).getCountry());
            setResult(400, intent);
            finish();
        }

    }
}
