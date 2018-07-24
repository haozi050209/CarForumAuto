package com.yonyou.friendsandaargang.info.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.adapter.DataBankAdapter;
import com.yonyou.friendsandaargang.info.bean.DataBankBean;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.SpinerPopWindow;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.scwang.smartrefresh.layout.constant.SpinnerStyle.FixedBehind;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 我的资料库
 */

public class DatabaseActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.lv_file)
    ListView lv_file;
    @BindView(R.id.iv_sort)
    ImageView iv_sort;
    @BindView(R.id.database_freshlayout)
    SmartRefreshLayout database_freshlayout;
    @BindView(R.id.no_text_tv)
    TextView no_text_tv;

    private List<DataBankBean.ContentBean> list;
    private DataBankAdapter adapter;
    private DialogSureCancel dialogSureCancel;
    private SpinerPopWindow popWindow;
    private String[] sortkind = {"按时间排序", "按名称排序"};

    private String userId;
    private int pageNo = 1;
    private int pageSize = 10;
    private String orderBy;
    private String fileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        getTitleBar();
        setTitleText("我的资料库");
        userId = SPTool.getContent(mContext, Constants.USER_ID);

        list = new ArrayList<>();
        adapter = new DataBankAdapter(mContext, list);
        lv_file.setAdapter(adapter);

        database_freshlayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(FixedBehind));
        database_freshlayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(FixedBehind));
        getDataBank(orderBy, fileId);
    }

    private void getDataBank(String orderBy, String fileId) {
        Call<DataBankBean> call = communityService().getBankList(userId, pageNo, pageSize, orderBy, fileId);
        NetUtils<DataBankBean> netUtils = new NetUtils<DataBankBean>(mContext);
        netUtils.enqueue(call, new ResponseCallBack<DataBankBean>() {
            @Override
            public void onResponseCallback(DataBankBean model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg());
                    no_text_tv.setVisibility(View.VISIBLE);
                    lv_file.setVisibility(View.GONE);
                    return;
                }
                setData(model);
            }

            @Override
            public void onFailureCallback() {
                database_freshlayout.finishRefresh();
                database_freshlayout.finishLoadMore();
            }
        });
    }

    private void setData(DataBankBean model) {
        if (model.getContent().size() == 0) {
            if (pageNo == 1) {
                list.clear();
                adapter.notifyDataSetChanged();
                no_text_tv.setVisibility(View.VISIBLE);
                lv_file.setVisibility(View.GONE);
            } else {
                database_freshlayout.finishLoadMoreWithNoMoreData();
            }
            database_freshlayout.finishRefresh();
            database_freshlayout.finishLoadMore();
            pageNo = 1;
            return;
        }

        if (pageNo == 1) {
            list.clear();
            list.addAll(model.getContent());
            no_text_tv.setVisibility(View.GONE);
            lv_file.setVisibility(View.VISIBLE);
            database_freshlayout.finishRefresh();
            database_freshlayout.setEnableLoadMore(true);
            adapter.notifyDataSetChanged();
        } else {
            list.addAll(model.getContent());
            database_freshlayout.finishLoadMore();
            adapter.notifyDataSetChanged();
        }
    }

    private void initListener() {
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fileId = s.toString();
                getDataBank(orderBy, fileId);
                adapter.notifyDataSetChanged();
            }
        });

        lv_file.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int dataId = list.get(position).getDataId();
                Bundle bundle = new Bundle();
                bundle.putInt("dataId", dataId);
                ActivityManger.skipActivity(mContext, DataBankDetailActivity.class, bundle);
            }
        });

        database_freshlayout.setOnRefreshListener(this);
        database_freshlayout.setOnLoadMoreListener(this);
    }

    @OnClick({R.id.back_lay, R.id.add_lay, R.id.iv_sort})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_lay:
                finish();
                break;
            case R.id.add_lay:
                showUploadDialog();
                break;
            case R.id.iv_sort:
                showSortWindow();
                break;
        }
    }

    private void showUploadDialog() {
        dialogSureCancel = new DialogSureCancel(mContext);
        dialogSureCancel.setCanceledOnTouchOutside(false);
        dialogSureCancel.setTitle("提示");
        dialogSureCancel.setContent("烦请发送用户名、手机号、共享文档至youchebang@yonyou.com;3个工作日内将完成审核，请耐心等待");
        dialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.show();
    }

    private void showSortWindow() {
        popWindow = new SpinerPopWindow(mContext, Arrays.asList(sortkind), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getDataBank(orderBy, fileId);
                        break;
                    case 1:
                        getDataBank("fileId", fileId);
                }
                popWindow.dismiss();
            }
        });
        popWindow.setWidth(280);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_round_white));
        backgroundAlpha(0.5f);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.showAsDropDown(iv_sort, 10, 40, Gravity.END);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }



    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNo = 1;
        getDataBank(orderBy, fileId);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getDataBank(orderBy, fileId);
    }
}
