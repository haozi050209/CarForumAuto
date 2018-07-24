package com.yonyou.friendsandaargang.guide.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {


    //按键返回
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    /**
     * ==========================================
     */


    @BindView(R.id.navigation)
    BottomNavigationView navigationView;


    //viewpager
    @BindView(R.id.home_viewpager)
    MyViewPager viewPager;

    @BindView(R.id.buttom_img_home)
    ImageView home_image;
    @BindView(R.id.buttom_text_home)
    TextView home_text;

    @BindView(R.id.buttom_img_forum)
    ImageView forum_image;
    @BindView(R.id.buttom_text_forum)
    TextView forum_text;

    @BindView(R.id.buttom_img_info)
    ImageView info_image;
    @BindView(R.id.buttom_text_info)
    TextView info_text;
    private boolean islogin;
    private String userId;
    private String registrationId;

    private MenuItem menuItem;


    private String ak;
    private String token;
    private String sk;
    private String expiration;
    private String endpoint;
    private String bucketName;
    private String objectKey;
    private String verName;
    private int verCode;
    private DialogSureCancel dialog;


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

        //保存极光id
        SPTool.putContent(mContext, Constants.REGISTRATIONID, JPushInterface.getRegistrationID(this));

        userId = SPTool.getContent(mContext, Constants.USER_ID);
        registrationId = SPTool.getContent(mContext, Constants.REGISTRATIONID);


        verCode = APKVersionCodeUtils.getVersionCode(mContext);
        verName = APKVersionCodeUtils.getVerName(mContext);


        //是否登陆
        islogin = SPTool.getBoolean(MainActivity.this, Constants.ISLOGIN);
        navigationView.setOnNavigationItemSelectedListener(this);

        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        if (islogin) {
            getVersionCode();
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(fragmentManager);
        adapter.addFragment(new HomePagerFragemnt());
        adapter.addFragment(new ForumFragment());
        adapter.addFragment(new InfoFragemnt());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);//参数为预加载数量，系统最小值为1。慎用！预加载数量过多低端机子受不了
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
        endpoint = bucketList.getContent().getBucketList().get(0).getEndPoint() + "";
        bucketName = bucketList.getContent().getBucketList().get(0).getBucketName() + "";
        objectKey = bucketList.getContent().getBucketList().get(0).getRemark() + "";
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
     * viewpage  监听事件
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        navigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
                viewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_dashboard:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_notifications:
                viewPager.setCurrentItem(2);
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
