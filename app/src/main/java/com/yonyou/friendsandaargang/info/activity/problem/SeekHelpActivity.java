package com.yonyou.friendsandaargang.info.activity.problem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.info.activity.problem.ProblemActivity;
import com.yonyou.friendsandaargang.info.activity.problem.ProblemFiveActivity;
import com.yonyou.friendsandaargang.info.activity.problem.ProblemFourActivity;
import com.yonyou.friendsandaargang.info.activity.problem.ProblemTherrActivity;
import com.yonyou.friendsandaargang.info.activity.problem.ProblemTwoActivity;
import com.yonyou.friendsandaargang.info.activity.user.SearchResultActivity;
import com.yonyou.friendsandaargang.info.bean.FAQByKeyWord;
import com.yonyou.friendsandaargang.info.bean.Tel;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by shibing on 18/4/11.
 */

public class SeekHelpActivity extends BaseActivity {

    @BindView(R.id.search_text)
    EditText search_text;

    private String tel;

    private DialogSureCancel SureCancel;

    private List<FAQByKeyWord.ContentBean> list;

    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekhelp);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("问题求助");
        getCustServTel();
        initviews();
    }

    private void initviews() {
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = search_text.getText().toString();
                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtils.normal(mContext, "请输入搜索内容").show();
                        return false;
                    }
                    getFAQByKeyWord(keyword);
                    //search_text.setText("");
                }
                return false;
            }
        });
    }


    @OnClick({R.id.zhanhao_ray, R.id.cfz_ray, R.id.qyfu_ray, R.id.ltxg_ray, R.id.wdxg_ray, R.id.lxkf_lay})
    public void OnClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.zhanhao_ray:
                intent = new Intent(mContext, ProblemActivity.class);
                intent.putExtra(Constants.TITLE, "账号问题");
                startActivity(intent);
                break;
            case R.id.cfz_ray:
                intent = new Intent(mContext, ProblemTwoActivity.class);
                intent.putExtra(Constants.TITLE, "我的钱包");
                startActivity(intent);
                break;
            case R.id.qyfu_ray:
                intent = new Intent(mContext, ProblemTherrActivity.class);
                intent.putExtra(Constants.TITLE, "企业服务");
                startActivity(intent);
                break;
            case R.id.ltxg_ray:
                intent = new Intent(mContext, ProblemFourActivity.class);
                intent.putExtra(Constants.TITLE, "论坛相关");
                startActivity(intent);
                break;
            case R.id.wdxg_ray:
                intent = new Intent(mContext, ProblemFiveActivity.class);
                intent.putExtra(Constants.TITLE, "问答相关");
                startActivity(intent);
                break;
            case R.id.lxkf_lay:
                ShowDialog();
                break;
        }
    }


    /**
     * 拨打电话
     */
    private void ShowDialog() {
        SureCancel = new DialogSureCancel(mContext);
        SureCancel.setCancelable(false);
        SureCancel.getTitleView().setText("提示");
        SureCancel.getContentView().setText("是否联系有车帮客服（工作时间：周一至周五9:00-18:00）" + "（" + tel + ")");
        SureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + tel);
                intent.setData(data);
                startActivity(intent);
                SureCancel.dismiss();
            }
        });
        SureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SureCancel.dismiss();
            }
        });
        SureCancel.show();
    }


    private void getCustServTel() {
        Call<Tel> call = communityService().getCustServTel();
        call.enqueue(new RetrofitCallback<Tel>() {
            @Override
            public void onSuccess(Tel model) {
                if (model.getReturnMsg().equals("success") && model.getReturnCode() == 0) {
                    tel = model.getContent().toString();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器异常").show();

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SureCancel != null) {
            SureCancel.dismiss();
        }
    }


    /**
     * 搜索帮助中心问题
     */

    private void getFAQByKeyWord(final String keyword) {
        showProgressDialog();
        Call<FAQByKeyWord> call = communityService().getFAQByKeyWord(keyword);
        call.enqueue(new RetrofitCallback<FAQByKeyWord>() {
            @Override
            public void onSuccess(FAQByKeyWord model) {
                if (model.getReturnCode() == 0 && model.getReturnMsg().equals("success")) {
                    list = model.getContent();
                    if (list != null && list.size() > 0) {
                        Intent intent = new Intent(mContext, SearchResultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", (Serializable) list);
                        intent.putExtra("keyword", keyword);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        ToastUtils.normal(mContext, "暂无搜索结果").show();
                    }
                }

            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal(mContext, "服务器异常").show();
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


}
