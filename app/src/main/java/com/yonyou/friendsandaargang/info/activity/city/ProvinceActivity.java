package com.yonyou.friendsandaargang.info.activity.city;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.ProvinceAdapter;
import com.yonyou.friendsandaargang.info.bean.Province;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 选择省
 */

public class ProvinceActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.province_listview)
    ListView province_listview;
    private ProvinceAdapter provinceAdapter;
    private String countryID;
    private List<Province.ContentBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("选择省份");
        if (getIntent().getStringExtra(Constants.COUNTRYID) != null) {
            countryID = getIntent().getStringExtra(Constants.COUNTRYID);
        }
        getProvince();
    }


    private void getProvince() {
        Call<Province> call = communityService().getProvinceList(countryID);
        call.enqueue(new NetRetrofitCallback<Province>(mContext, new HttpCallBackImpl<Province>() {
            @Override
            public void onResponseCallback(Province model) {

                    if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                        ToastUtils.normal(mContext, model.getReturnMsg()).show();
                        return;
                    }

                    initDatas(model);

            }
        }));
    }

    /**
     * 数据处理
     *
     * @param model
     */
    private void initDatas(Province model) {
        list = model.getContent();
        provinceAdapter = new ProvinceAdapter(ProvinceActivity.this, list);
        province_listview.setAdapter(provinceAdapter);
        province_listview.setOnItemClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 2000) {
            setResult(1000, data);
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
        intent.putExtra(Constants.PROVINCEID, list.get(position).getProvinceId());
        intent.putExtra(Constants.PROVINCE, list.get(position).getProvince());
        startActivityForResult(intent, 3);
    }
}
