package com.yonyou.friendsandaargang.info.activity.seeting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/2/28.
 * <p>
 * 签名
 */

public class AutographActivity extends BaseActivity implements TextWatcher {


    @BindView(R.id.text_lenght)
    TextView text_lenght;
    @BindView(R.id.edit_autograph)
    EditText edit_autograph;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_autograph);
        ButterKnife.bind(this);
        initviews();
    }


    @SuppressLint("SetTextI18n")
    private void initviews() {
        getTitleBar();
        setTitleText("签名").rightTv("保存");
        string = getIntent().getStringExtra(Constants.AUTOGRAPH);
        //签名
        edit_autograph.setText(string);
        if (TextUtils.isEmpty(string)) {
            text_lenght.setText("0/30");
        } else {
            text_lenght.setText(string.length() + "/30");

        }
        edit_autograph.addTextChangedListener(this);

    }


    @OnClick(R.id.titleBar_right_text)
    public void OnClick() {
        Intent intent = new Intent();
        intent.putExtra("autograph", edit_autograph.getText().toString());
        setResult(300, intent);
        finish();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String text = s.toString();
        text_lenght.setText(text.length() + "/30");
        if (text.length() > 30) {
            ToastUtils.normal(mContext, "超过最大的字数了哦").show();
            return;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
