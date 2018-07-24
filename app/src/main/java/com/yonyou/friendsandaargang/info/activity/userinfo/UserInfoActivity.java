package com.yonyou.friendsandaargang.info.activity.userinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.bigkoo.pickerview.TimePickerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.guide.activity.MainActivity;
import com.yonyou.friendsandaargang.info.activity.MyGradeActivity;
import com.yonyou.friendsandaargang.info.activity.city.CountryActivity;
import com.yonyou.friendsandaargang.info.activity.seeting.AuthenticationActivity;
import com.yonyou.friendsandaargang.info.activity.seeting.AutographActivity;
import com.yonyou.friendsandaargang.info.activity.seeting.NickNameActivity;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.utils.GlideImageLoader;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.dialog.DialogUploadPicture;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;
import static com.yonyou.friendsandaargang.homepage.activity.PostActivity.REQUEST_CODE_SELECT;
import static com.yonyou.friendsandaargang.utils.TimeUtil.getTime;


/**
 * Created by shibing on 18/2/28.
 * <p>
 * 个人信息
 */

public class UserInfoActivity extends BaseActivity implements View.OnClickListener
        , OssService.picResultCallback {
    //头像
    @BindView(R.id.user_head_img)
    CircleImageView head_img;
    //昵称
    @BindView(R.id.user_nikename_text)
    TextView nikename_text;
    @BindView(R.id.user_username_text)
    TextView user_username_text;
    //性别
    @BindView(R.id.user_sex_text)
    TextView sex_text;
    //生日
    @BindView(R.id.user_birthday_text)
    TextView birthday_text;
    //地区
    @BindView(R.id.user_region_text)
    TextView region_text;
    //签名
    @BindView(R.id.user_autograph_text)
    TextView autograph_text;
    //等级
    @BindView(R.id.user_grade_text)
    TextView grade_text;
    //认证
    @BindView(R.id.user_authentication_text)
    TextView authentication_text;
    private DialogUploadPicture dialogUploadPicture;
    //日期选择器
    private TimePickerView pvTime;
    private String userId;
    private int gender;
    private String birthday;
    private String region;
    private String signature;
    private String nickname;
    private String nickNames;
    private String avatar;
    private String areaId;
    private String authDesc;
    private ArrayList<ImageItem> images = null;
    //上传到oss服务器上
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private OSS oss;
    private String bucketName = "yonyou-community-app-images";
    //OSS的上传下载
    private OssService ossService;

    private Long time;
    private String objectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        getTitleBar();
        setTitleText("个人信息");
        intiviews();
        ossService = initOSS(endpoint, bucketName);
        initImagePicker();
    }

    //初始化
    private void intiviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        //头像
        if (getIntent().getStringExtra(Constants.HEADIMAGE) != null
                && !equals(getIntent().getStringExtra(Constants.HEADIMAGE))) {
            GlideManager.loadImage(mContext, getIntent().getStringExtra(Constants.HEADIMAGE), R.drawable.user, head_img);
        }
        //用户名
        if (getIntent().getStringExtra(Constants.USER_NAME) != null
                && !equals(getIntent().getStringExtra(Constants.USER_NAME))) {
            user_username_text.setText(getIntent().getStringExtra(Constants.USER_NAME));
        }
        //昵称
        if (getIntent().getStringExtra(Constants.NIKE_NAME) != null
                && !equals(getIntent().getStringExtra(Constants.NIKE_NAME))) {
            nikename_text.setText(getIntent().getStringExtra(Constants.NIKE_NAME));
            nickname = getIntent().getStringExtra(Constants.NIKE_NAME);
        }

        //性别
        if (getIntent().getStringExtra(Constants.GENDER) != null
                && !equals(getIntent().getStringExtra(Constants.GENDER))) {
            sex_text.setText(getIntent().getStringExtra(Constants.GENDER));
        }
        //生日
        if (getIntent().getStringExtra(Constants.BIRTHDAY) != null
                && !equals(getIntent().getStringExtra(Constants.BIRTHDAY))) {
            birthday_text.setText(getIntent().getStringExtra(Constants.BIRTHDAY));
        } else {
            birthday_text.setText("请完善生日");
        }
        //地区
        if (getIntent().getStringExtra(Constants.REGINON) != null
                && !equals(getIntent().getStringExtra(Constants.REGINON))) {
            region_text.setText(getIntent().getStringExtra(Constants.REGINON));
        } else {
            region_text.setText("请选择地区");
        }
        //签名
        if (getIntent().getStringExtra(Constants.AUTOGRAPH) != null
                && !equals(getIntent().getStringExtra(Constants.AUTOGRAPH))) {
            autograph_text.setText(getIntent().getStringExtra(Constants.AUTOGRAPH));
            signature = getIntent().getStringExtra(Constants.AUTOGRAPH);
        } else {
            autograph_text.setText("请设置签名");
        }

        if (getIntent().getStringExtra(Constants.AUTHDESC) != null) {
            authDesc = getIntent().getStringExtra(Constants.AUTHDESC);
            if (authDesc.equals("已认证") || authDesc.equals("认证中")) {
                authentication_text.setText(authDesc);
            } else {
                authentication_text.setText("您还未认证，快去认证吧");
            }
        }

        //弹窗
        dialogUploadPicture = new DialogUploadPicture(this);


    }

    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.back_lay, R.id.fly_user_head, R.id.fly_user_nickname, R.id.fly_uesr_sex, R.id.fly_uesr_birthday
            , R.id.fly_uesr_region, R.id.fly_uesr_autograph, R.id.fly_user_grade, R.id.fly_user_authentication
    })
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_lay:
                ActivityManger.skipActivityAndFinish(mContext, MainActivity.class);
                break;
            //头像
            case R.id.fly_user_head:
                DialogChooseImage();
                break;
            //昵称
            case R.id.fly_user_nickname:
                intent = new Intent(mContext, NickNameActivity.class);
                intent.putExtra(Constants.NIKE_NAME, nikename_text.getText().toString());
                startActivityForResult(intent, 100);
                break;
            //性别
            case R.id.fly_uesr_sex:
                showSexDialog();
                break;
            //生日
            case R.id.fly_uesr_birthday:
                showBirth();
                break;
            //地区
            case R.id.fly_uesr_region:
                intent = new Intent(this, CountryActivity.class);
                startActivityForResult(intent, 1);
                break;
            //签名
            case R.id.fly_uesr_autograph:
                intent = new Intent(mContext, AutographActivity.class);
                intent.putExtra(Constants.AUTOGRAPH, signature);
                startActivityForResult(intent, 300);
                break;
            //等级
            case R.id.fly_user_grade:
                ActivityManger.skipActivity(this, MyGradeActivity.class);
                break;
            //认证
            case R.id.fly_user_authentication:
                ActivityManger.skipActivity(this, AuthenticationActivity.class);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            //昵称
            case 100:
                if (data.getStringExtra("nickname") != null) {
                    nikename_text.setText(data.getStringExtra("nickname"));
                    nickNames = data.getStringExtra("nickname");
                }
                getupdateUserNikeName(nickNames);
                break;
            //地区
            case 9527:
            case 400:
                if (data.getStringExtra(Constants.REGINON) != null) {
                    region_text.setText(data.getStringExtra(Constants.REGINON));
                    region = data.getStringExtra(Constants.REGINON);
                }
                if (data.getStringExtra("areaId") != null) {
                    areaId = data.getStringExtra("areaId");
                }
                getupdateUser("");
                break;
            //签名
            case 300:
                if (data.getStringExtra("autograph") != null) {
                    autograph_text.setText(data.getStringExtra("autograph"));
                    signature = data.getStringExtra("autograph");
                    getupdateUser("");
                }
                break;
            case ImagePicker.RESULT_CODE_ITEMS:
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null) {
                        GlideManager.loadImage(mContext, images.get(0).path, R.drawable.user, head_img);
                        time = new Date().getTime();
                        objectName = "avatar_" + TimeUtil.getDateTimeFromMil(time) + ".png";
                        if (images.get(0).path != null) {
                            ossService.asyncPutImage("user-avatar/" + objectName, images.get(0).path);
                        }
                    }
                }
                break;
            case ImagePicker.RESULT_CODE_BACK:
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        GlideManager.loadImage(mContext, images.get(0).path, R.drawable.user, head_img);
                        time = new Date().getTime();
                        objectName = "avatar_" + TimeUtil.getDateTimeFromMil(time) + ".png";
                        if (images.get(0).path != null) {
                            ossService.asyncPutImage("user-avatar/" + objectName, images.get(0).path);
                        }
                    }

                }
                break;
        }
    }

    /**
     * 性别选择
     */


    private void showSexDialog() {
        dialogUploadPicture.setCancelable(false);
        dialogUploadPicture.getCancel().setText("取消");
        dialogUploadPicture.getAlbum().setText("女");
        dialogUploadPicture.getPhotograph().setText("男");
        dialogUploadPicture.setPhotographListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex_text.setText("男");
                dialogUploadPicture.dismiss();
                gender = 10041001;
                getupdateUser("");
            }
        });
        dialogUploadPicture.setAlbumListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex_text.setText("女");
                dialogUploadPicture.dismiss();
                gender = 10041002;
                getupdateUser("");
            }
        });

        dialogUploadPicture.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUploadPicture.dismiss();
            }
        });

        dialogUploadPicture.show();
    }


    private void DialogChooseImage() {
        dialogUploadPicture = new DialogUploadPicture(this);
        dialogUploadPicture.getCancel().setText("取消");
        dialogUploadPicture.getAlbum().setText("选择相册");
        dialogUploadPicture.getPhotograph().setText("相机");
        dialogUploadPicture.setCancelable(false);
        dialogUploadPicture.setPhotographListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent = new Intent(UserInfoActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                dialogUploadPicture.dismiss();
            }
        });
        dialogUploadPicture.setAlbumListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent1 = new Intent(UserInfoActivity.this, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                dialogUploadPicture.dismiss();
            }
        });

        dialogUploadPicture.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUploadPicture.dismiss();
            }
        });
        dialogUploadPicture.show();
    }


    public void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);                        //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);     //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private void showBirth() {

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                birthday_text.setText(getTime(date));
                birthday = getTime(date);
                getupdateUser("");
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(18)
                .setDecorView(null)
                .build();
        pvTime.show();
    }


    /**
     * 更新用户信息
     */

    private void getupdateUser(String avatar) {
        Call<HttpResult> call = communityService().getupdateUserInfo(userId, "", "", gender, "", "", "",
                birthday, areaId, signature, "", avatar);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    return;
                }
                ToastUtils.normal(mContext, "修改成功").show();
            }
        }));

    }


    private void getupdateUserNikeName(String nickNames) {
        Call<HttpResult> call = communityService().getupdateUserInfo(userId,
                "",
                "", gender,
                "", "",
                "",
                birthday, areaId,
                signature, nickNames,
                avatar);
        call.enqueue(new NetRetrofitCallback<HttpResult>(mContext, new HttpCallBackImpl<HttpResult>() {
            @Override
            public void onResponseCallback(HttpResult model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg()).show();
                    nikename_text.setText(nickname);
                    return;
                }
                ToastUtils.normal(mContext, "修改成功").show();
            }
        }));

    }


    /**
     * @param data
     */
    @Override
    public void getPicData(PutObjectRequest data) {
        getupdateUser(data.getObjectKey());

    }


    //失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {
        if (clientException != null || serviceException != null) {
            ToastUtils.normal(mContext, "图片上传失败").show();
        }
    }
}
