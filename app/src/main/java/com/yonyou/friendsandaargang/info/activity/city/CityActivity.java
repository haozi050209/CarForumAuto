package com.yonyou.friendsandaargang.info.activity.city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.CityAdapter;
import com.yonyou.friendsandaargang.info.bean.City;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 选择市
 */

public class CityActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.city_listview)
    ListView city_listview;
    private CityAdapter cityAdapter;

    private String ProvinceId;
    private String Province;
    private List<City.ContentBean> list;

    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择城市");
        if (getIntent().getStringExtra(Constants.PROVINCEID) != null) {
            ProvinceId = getIntent().getStringExtra(Constants.PROVINCEID);
        }
        if (getIntent().getStringExtra(Constants.PROVINCE) != null) {
            Province = getIntent().getStringExtra(Constants.PROVINCE);
        }
        getCity();

    }


    private void getCity() {
        Call<City> call = communityService().getCityList(ProvinceId);
        call.enqueue(new NetRetrofitCallback<City>(mContext, new HttpCallBackImpl<City>() {
            @Override
            public void onResponseCallback(City model) {

                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    return;
                }

                initDatas(model);
            }
        }));
    }


    private void initDatas(City model) {
        list = model.getContent();
        cityAdapter = new CityAdapter(CityActivity.this, list);
        city_listview.setAdapter(cityAdapter);
        city_listview.setOnItemClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == 3000) {
            setResult(2000, data);
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(CityActivity.this, AreaActivity.class);
        intent.putExtra(Constants.CITY, list.get(position).getCity());
        intent.putExtra(Constants.CITYID, list.get(position).getCityId());
        intent.putExtra(Constants.PROVINCE, Province);
        startActivityForResult(intent, 4);
    }
}
