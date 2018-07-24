package com.yonyou.friendsandaargang.info.activity.city;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.AreaAdapter;
import com.yonyou.friendsandaargang.info.bean.Area;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 选择区
 */

public class AreaActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.area_listview)
    ListView area_listview;
    private AreaAdapter areaAdapter;
    private String City;
    private String CityID;
    private String province;
    private String region;
    private List<Area.ContentBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择地区");
        if (getIntent().getStringExtra(Constants.CITYID) != null) {
            CityID = getIntent().getStringExtra(Constants.CITYID);
        }
        if (getIntent().getStringExtra(Constants.CITY) != null) {
            City = getIntent().getStringExtra(Constants.CITY);
        }
        if (getIntent().getStringExtra(Constants.PROVINCE) != null) {
            province = getIntent().getStringExtra(Constants.PROVINCE);
        }

        getArea();
    }


    private void getArea() {
        Call<Area> call = communityService().getAreaList(CityID);
        call.enqueue(new NetRetrofitCallback<Area>(mContext, new HttpCallBackImpl<Area>() {
            @Override
            public void onResponseCallback(Area model) {
                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    return;
                }
                initDatas(model);
            }
        }));
    }


    private void initDatas(Area model) {
        list = model.getContent();
        areaAdapter = new AreaAdapter(AreaActivity.this, list);
        area_listview.setAdapter(areaAdapter);
        area_listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        region = province + City + list.get(position).getArea();
        Intent intent = new Intent();
        intent.putExtra(Constants.REGINON, region);
        intent.putExtra("areaId", list.get(position).getAreaId());
        Log.e("onItemClick", "onItemClick: " + list.get(position).getAreaId());
        setResult(3000, intent);
        finish();
    }
}
