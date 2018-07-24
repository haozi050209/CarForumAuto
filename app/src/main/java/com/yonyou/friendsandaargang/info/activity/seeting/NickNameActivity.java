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
 * 修改昵称
 */

public class NickNameActivity extends BaseActivity implements TextWatcher {


    @BindView(R.id.edit_nickname)
    EditText nickname;
    @BindView(R.id.text_lenght)
    TextView text_lenght;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_nickname);
        ButterKnife.bind(this);
        initviews();
    }

    @SuppressLint("SetTextI18n")
    private void initviews() {
        getTitleBar();
        setTitleText("昵称").rightTv("保存");
        string = getIntent().getStringExtra(Constants.NIKE_NAME);
        nickname.setText(string);
        if (TextUtils.isEmpty(string)) {
            text_lenght.setText("0/10");
        } else {
            text_lenght.setText(string.length() + "/10");
        }
        nickname.addTextChangedListener(this);
    }


    @OnClick(R.id.titleBar_right_text)
    public void OnClick() {
        Intent intent = new Intent();
        intent.putExtra("nickname", nickname.getText().toString());
        setResult(100, intent);
        finish();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String temp = s.toString();
        text_lenght.setText(temp.length() + "/10");
        if (temp.length() > 10) {
            ToastUtils.normal(mContext, "超过最大的字数了哦").show();
            return;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
