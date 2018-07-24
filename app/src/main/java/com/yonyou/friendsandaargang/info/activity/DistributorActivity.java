package com.yonyou.friendsandaargang.info.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.info.adapter.DistrAdapter;
import com.yonyou.friendsandaargang.info.adapter.JobAdapter;
import com.yonyou.friendsandaargang.info.bean.CustomerByStr;
import com.yonyou.friendsandaargang.info.bean.JobList;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 经销商选择
 */

public class DistributorActivity extends BaseActivity {


    private static final String TAG = "DistributorActivity";

    @BindView(R.id.distributor_text)
    EditText distributor;
    @BindView(R.id.distr_list)
    MyListView distr_list;
    @BindView(R.id.search_qingchu_lya)
    LinearLayout search_qingchu_lya;

    private String brandId;
    private String searchContent;
    private DistrAdapter distrAdapter;
    private List<CustomerByStr.ContentBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        ButterKnife.bind(this);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("经销商选择");
        brandId = getIntent().getStringExtra("brandId");


        distributor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchContent = s.toString();

                if (!TextUtils.isEmpty(searchContent)) {
                    search_qingchu_lya.setVisibility(View.VISIBLE);
                } else {
                    search_qingchu_lya.setVisibility(View.GONE);
                }
                if (s.length() < 3) {
                    ToastUtils.normal(mContext, "请至少输入3个字符").show();
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        distributor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                searchContent = distributor.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(searchContent)) {
                        ToastUtils.normal(mContext, "请输入搜索内容").show();
                        return false;
                    }
                    getCustomerBy(searchContent);
                }
                return false;
            }
        });
    }


    private void getCustomerBy(String string) {
        showProgressDialog();
        Call<CustomerByStr> call = communityService().getCustomerByStr(string, brandId);
        call.enqueue(new RetrofitCallback<CustomerByStr>() {
            @Override
            public void onSuccess(CustomerByStr model) {

                if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }

                list = model.getContent();
                if (list != null) {
                    distrAdapter = new DistrAdapter(mContext, list);
                    distr_list.setAdapter(distrAdapter);
                    distr_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.putExtra("CompName", list.get(position).getCompName());
                            intent.putExtra("comId", list.get(position).getCustId());
                            setResult(300, intent);
                            finish();
                        }
                    });
                } else {
                    ToastUtils.normal(mContext, "暂无搜索结果").show();
                    distr_list.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }


    @OnClick(R.id.search_qingchu_lya)
    public void OnClick() {
        distributor.setText("");
    }

}
