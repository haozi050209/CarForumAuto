package com.yonyou.friendsandaargang.info.activity.seeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.adapter.ImagePickerAdapter;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.dialog.DialogUploadPicture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by shibing on 18/3/8.
 */

public class UserAuthenticationActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener
        , OssService.picResultCallback {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    @BindView(R.id.user_rz_name)
    TextView user_rz_name;
    @BindView(R.id.user_rz_id)
    TextView user_rz_id;
    @BindView(R.id.user_rz_industry)
    EditText user_rz_industry;
    @BindView(R.id.user_rz_school)
    EditText user_rz_school;
    @BindView(R.id.user_rz_job)
    TextView user_rz_job;
    @BindView(R.id.user_rz_photo_grid)
    RecyclerView user_rz_photo_grid;
    @BindView(R.id.mediaurl_ed)
    EditText mediaurl_ed;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> images = null;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private Long time;
    private ArrayList<String> arrayList;
    private List<String> listTime;

    private DialogUploadPicture dialogUploadPicture;
    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";

    private String forum;                      // 是用户还是大咖
    private String userId;                    //  id必填
    private String realName;                  //  身份证姓名 必填
    private String idNumber;                  //  身份证号码 必填
    private String industry;                  //  行业 必填
    private String organization;              //  单位/组织 必填
    private String jobId;                     //  职位 必填
    private String identityType;              //  认证类型 必填 10211001用户认证，10211002大咖认证，10211003经销商认证
    private String mediaUrl;                  //  自媒体连接 （用于大咖）
    private String attachmentList;            //  必填 附件名称，即文件在OSS上的KEY值
    private String idCardList;                //  必填 身份证照片名称
    private String objectname;                //  附件名称
    private String path;                      //  图片地址
    private JSONArray jsonArray;              //  上传图片的json数据
    private JSONObject requestData;           //  最外层的数据
    private JSONArray idJsonArray;            //  身份证的json数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userauthentication);
        ButterKnife.bind(this);
        ossService = initOSS(endpoint, bucketName);
        initviews();
    }


    private void initviews() {
        getTitleBar();
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        if (getIntent().getStringExtra("identityType") != null) {
            identityType = getIntent().getStringExtra("identityType");
            switch (identityType) {
                case "10211001":
                    setTitleText("用户认证");
                    break;
                case "10211002":
                    setTitleText("大咖认证");
                    break;
            }
        }
        forum = getIntent().getStringExtra("forum");
        if (forum.equals("userAu")) {
            mediaurl_ed.setVisibility(View.GONE);
        } else {
            mediaurl_ed.setVisibility(View.VISIBLE);
            mediaUrl = mediaurl_ed.getText().toString();
        }


        user_rz_industry.setCursorVisible(false);
        user_rz_school.setCursorVisible(false);


        initImagePicker();
        selImageList = new ArrayList<>();
        listTime = new ArrayList<>();   //上传服务器时间

        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        user_rz_photo_grid.setLayoutManager(new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        user_rz_photo_grid.setHasFixedSize(true);
        user_rz_photo_grid.setAdapter(adapter);


    }


    @OnClick({R.id.user_rz_sfz_img, R.id.user_rz_but, R.id.user_rz_job, R.id.user_rz_name, R.id.user_rz_id})
    public void OnClick(View view) {
        switch (view.getId()) {
            //身份证认证
            case R.id.user_rz_sfz_img:
            case R.id.user_rz_name:
            case R.id.user_rz_id:
                Intent intent1 = new Intent(mContext, IdCardActivity.class);
                startActivityForResult(intent1, 100);
                break;
            // 立即认证
            case R.id.user_rz_but:
                industry = user_rz_industry.getText().toString();
                organization = user_rz_school.getText().toString();
                if (realName == null) {
                    ToastUtils.normal(mContext, "姓名不能为空").show();
                    return;
                }
                if (idNumber == null) {
                    ToastUtils.normal(mContext, "身份证号码不能为空").show();
                    return;
                }
                if (industry.isEmpty()) {
                    ToastUtils.normal(mContext, "所在行业不能为空").show();
                    return;
                }
                if (organization.isEmpty()) {
                    ToastUtils.normal(mContext, "单位/组织").show();
                    return;
                }
                if (jobId == null) {
                    ToastUtils.normal(mContext, "职位不能为空").show();
                    return;
                }
                if (selImageList == null) {
                    ToastUtils.normal(mContext, "请上传职业证明").show();
                    return;
                }
                getUserIdentity();
                break;
            case R.id.user_rz_job:
                Intent intent = new Intent(mContext, JobActivity.class);
                intent.putExtra("form", "job_user");
                startActivityForResult(intent, 300);
                break;

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                dialogUploadPicture = new DialogUploadPicture(this);
                dialogUploadPicture.getCancel().setText("取消");
                dialogUploadPicture.getAlbum().setText("选择相册");
                dialogUploadPicture.getPhotograph().setText("相机");
                dialogUploadPicture.setCancelable(false);
                dialogUploadPicture.setPhotographListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent = new Intent(UserAuthenticationActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                        dialogUploadPicture.dismiss();
                    }
                });
                dialogUploadPicture.setAlbumListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent1 = new Intent(UserAuthenticationActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
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
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }


    /**
     * 处理选择回来时图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ImagePicker.RESULT_CODE_ITEMS:
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null) {
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                    }
                }
                break;
            case ImagePicker.RESULT_CODE_BACK:
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        selImageList.clear();
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                    }
                }
                break;

            case 50:
                if (data.getStringExtra("jobname") != null && data.getStringExtra("jobid") != null) {
                    jobId = data.getStringExtra("jobid");
                    user_rz_job.setText(data.getStringExtra("jobname"));
                }
                break;

            case 100:
                if (data.getStringArrayListExtra("nameList") != null) {
                    //上传到服务器身份证的 namelist
                    arrayList = data.getStringArrayListExtra("nameList");
                    //身份证姓名
                    realName = data.getStringExtra("realName");
                    //身份证号码
                    idNumber = data.getStringExtra("idNumber");
                    user_rz_name.setText(realName);
                    user_rz_id.setText(idNumber);
                }
                break;
        }
    }


    /**
     * 用户认证
     */
    private void getUserIdentity() {
        requestData = new JSONObject();
        jsonArray = new JSONArray();
        idJsonArray = new JSONArray();
        listTime.clear();
        try {
            //最外层 的参数
            requestData.put("userId", userId);
            requestData.put("realName", realName);
            requestData.put("idNumber", idNumber);
            requestData.put("industry", industry);
            requestData.put("organization", organization);
            requestData.put("jobId", jobId);
            requestData.put("identityType", identityType);
            requestData.put("mediaUrl", mediaUrl);
            for (int i = 0; i < selImageList.size(); i++) {
                objectname = userId + "-" + TimeUtil.getDateTimeFromMil(System.currentTimeMillis()) + i + ".png";
                JSONObject root = new JSONObject();
                root.put("attachmentName", "user-identity/" + objectname);
                listTime.add(objectname);
                jsonArray.put(root);
            }
            //身份证
            for (int i = 0; i < arrayList.size(); i++) {
                JSONObject idRoot = new JSONObject();
                idRoot.put("attachmentName", "user-identity/" + arrayList.get(i));
                idJsonArray.put(idRoot);
            }
            requestData.put("attachmentList", jsonArray);
            requestData.put("idCardList", idJsonArray);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestData.toString());
            Call<HttpResult> call = communityService().saveUserIdentity(body);
            NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
            netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
                @Override
                public void onResponseCallback(HttpResult httpResult) {
                    if (!httpResult.getReturnMsg().equals("success") && httpResult.getReturnCode() != 0) {
                        ToastUtils.normal(mContext, httpResult.getReturnMsg().toString()).show();
                        return;
                    }
                    //上传认证图片
                    for (int i = 0; i < selImageList.size(); i++) {
                        //objectname = userId + "-" + TimeUtil.getDateTimeFromMil(System.currentTimeMillis()) + i;
                        path = selImageList.get(i).path;
                        ossService.asyncPutImage("user-identity/" + listTime.get(i), path);
                    }
                    SPTool.putContent(mContext, Constants.IDENTITYTYPE, identityType);
                    ToastUtils.normal(mContext, "认证成功").show();
                    finish();
                }

                @Override
                public void onFailureCallback() {
                    ToastUtils.normal(mContext, "服务器加载异常").show();
                }
            });
        } catch (Exception e) {
        }
    }


    @Override
    public void getPicData(PutObjectRequest data) {
        Logger.e("-------", data.getObjectKey());
        Logger.e("-------", data.getUploadFilePath());
    }

    //失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {

    }
}
