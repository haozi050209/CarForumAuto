package com.yonyou.friendsandaargang.guide.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.guide.adapter.FragmentAdapter;
import com.yonyou.friendsandaargang.guide.bean.BucketList;
import com.yonyou.friendsandaargang.guide.bean.VersionCodeBean;
import com.yonyou.friendsandaargang.guide.fragemnt.ForumFragment;
import com.yonyou.friendsandaargang.guide.fragemnt.HomePagerFragemnt;
import com.yonyou.friendsandaargang.guide.fragemnt.InfoFragemnt;
import com.yonyou.friendsandaargang.jpush.MyReceiver;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.APKVersionCodeUtils;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.PermissionsTool;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.MyViewPager;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;

public class MainActivity extends BaseActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {


    //按键返回
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    /**
     * ==========================================
     */

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    private boolean islogin;
    private String userId,registrationId;

    private String ak,token,sk,expiration,verName;
    private int verCode;
    private DialogSureCancel dialog;
    private List<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initviews();
        getPermissions();
        getToKen(userId);
        seavregistrationId();
    }


    /**
     * 权限管理
     */
    private void getPermissions() {
        PermissionsTool.with(MainActivity.this).
                addPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                addPermission(Manifest.permission.ACCESS_COARSE_LOCATION).
                addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
                addPermission(Manifest.permission.CAMERA).
                addPermission(Manifest.permission.CALL_PHONE).
                addPermission(Manifest.permission.READ_PHONE_STATE).
                initPermission();
    }

    /**
     * 序列化
     */
    private void initviews() {
        fragments = new ArrayList<>();
        SPTool.putContent(mContext, Constants.REGISTRATIONID, JPushInterface.getRegistrationID(this));  //保存极光id
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        registrationId = SPTool.getContent(mContext, Constants.REGISTRATIONID);
        verCode = APKVersionCodeUtils.getVersionCode(mContext);
        verName = APKVersionCodeUtils.getVerName(mContext);
        islogin = SPTool.getBoolean(MainActivity.this, Constants.ISLOGIN);   //是否登陆
        navigationView.setOnNavigationItemSelectedListener(this);
        showFragment(HomePagerFragemnt.newInstance(""));       //默认第一个是首页的fragenmt
        if (islogin) {
            getVersionCode();
        }

    }


    /**
     * 显示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        if (!fragments.contains(fragment)) {
            fragments.add(fragment);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!fragment.isAdded()) {
            transaction.add(R.id.main_container, fragment);
        }
        hideFragment(transaction);
        transaction.show(fragment);
        transaction.commit();
    }


    /**
     * 隐藏所有的fragment
     */
    private void hideFragment(FragmentTransaction transaction) {
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) != null) {
                transaction.hide(fragments.get(i));
            }
        }
    }

    /**
     * 双击俩次返回  返回至桌面
     */

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            ToastUtils.normal(mContext, "再次点击返回键退出").show();
        }
        mBackPressed = System.currentTimeMillis();
    }


    /**
     * 上传照片接口  对接oss
     */
    private void getToKen(String userId) {
        Call<BucketList> call = communityService().getSTSAccessInfo("write", userId);
        call.enqueue(new NetRetrofitCallback<BucketList>(mContext, new HttpCallBackImpl<BucketList>() {
            @Override
            public void onResponseCallback(BucketList bucketList) {
                if (bucketList.getReturnCode() != 0 && !bucketList.getReturnMsg().equals("success")) {
                    return;
                }
                saveOssToken(bucketList);
            }
        }));
    }


    /**
     * 保存阿里云token
     *
     * @param bucketList
     */
    private void saveOssToken(BucketList bucketList) {
        ak = bucketList.getContent().getAccessKeyId() + "";
        sk = bucketList.getContent().getAccessKeySecret();
        token = bucketList.getContent().getSecurityToken() + "";
        expiration = bucketList.getContent().getExpiration() + "";
        SPTool.putContent(getApplicationContext(), Constants.AK, ak);
        SPTool.putContent(getApplicationContext(), Constants.SK, sk);
        SPTool.putContent(getApplicationContext(), Constants.TOKEN, token);
        SPTool.putContent(getApplicationContext(), Constants.EXPIRATION, expiration);
    }


    /**
     * 保存极光registrationId
     */

    private void seavregistrationId() {
        Call<HttpResult> call = communityService().getsaveRegistrationId(userId, registrationId);
        call.enqueue(new RetrofitCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });

    }



    /**
     * navigationView  监听事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                showFragment(HomePagerFragemnt.newInstance(""));
                return true;
            case R.id.navigation_dashboard:
                showFragment(ForumFragment.newInstance(""));
                return true;
            case R.id.navigation_notifications:
                showFragment(InfoFragemnt.newInstance(""));
                return true;
        }
        return false;
    }


    /**
     * 获取版本号
     */
    private void getVersionCode() {
        Call<VersionCodeBean> call = communityService().getVersionCode();
        call.enqueue(new NetRetrofitCallback<VersionCodeBean>(mContext, new HttpCallBackImpl<VersionCodeBean>() {
            @Override
            public void onResponseCallback(VersionCodeBean versionCodeBean) {
                if (versionCodeBean == null) {
                    return;
                }

                if (verCode == versionCodeBean.getContent().getVersionCode()
                        && verName.equals(versionCodeBean.getContent().getVersionName())) {
                    return;
                }

                Logger.e("-------", versionCodeBean.getContent().getVersionCode() + "");
                Logger.e("-------", versionCodeBean.getContent().getVersionName() + "");

                showVersionCodeDialog();
            }
        }));
    }


    private void showVersionCodeDialog() {
        dialog = new DialogSureCancel(mContext);
        dialog.getTitleView().setText("提示");
        dialog.getContentView().setText("发现新版本，是否更新？");
        //取消更新
        dialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.baidu.com/");//此处填链接
                intent.setData(content_url);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
