package com.yonyou.friendsandaargang.info.activity.seeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.yonyou.friendsandaargang.homepage.activity.AllBrandActivity;
import com.yonyou.friendsandaargang.homepage.adapter.ImagePickerAdapter;
import com.yonyou.friendsandaargang.info.activity.DistributorActivity;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
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
 * <p>
 * <p>
 * 企业认证
 */

public class EnterpriseAuthenticationActivity extends BaseActivity implements
        ImagePickerAdapter.OnRecyclerViewItemClickListener
        , OssService.picResultCallback {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    @BindView(R.id.qy_rz_name)
    TextView qy_rz_name;
    @BindView(R.id.qy_rz_id)
    TextView qy_rz_id;
    @BindView(R.id.qy_rz_pp)
    TextView qy_rz_pp;
    @BindView(R.id.qy_rz_distributor)
    TextView qy_rz_jxs;
    @BindView(R.id.qy_rz_job)
    TextView qy_rz_job;
    @BindView(R.id.qy_rz_job_two)
    TextView qy_rz_job_two;
    @BindView(R.id.qy_rz_job_therr)
    TextView qy_rz_job_therr;
    @BindView(R.id.qy_photo_grid)
    RecyclerView qy_photo_grid;


    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> images = null;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private List<String> listTime;
    private int maxImgCount = 9;               //允许选择图片最大数
    private Long time;

    private DialogUploadPicture dialog;
    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";


    private String jobid_one;
    private String jobid_two;
    private String jobid_therr;
    private String brandId;
    private String job_text_one;
    private String job_text_two;
    private String job_text_therr;
    private String custId;


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
    private ArrayList<String> arrayList;      //身份证的图片


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_enterprise);
        ButterKnife.bind(this);
        ossService = initOSS(endpoint, bucketName);
        initviews();
    }

    private void initviews() {
        getTitleBar();
        setTitleText("经销商认证");

        userId = SPTool.getContent(mContext, Constants.USER_ID);

        //初始化相机
        initImagePicker();
        listTime = new ArrayList<>();
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        qy_photo_grid.setLayoutManager(new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        qy_photo_grid.setHasFixedSize(true);
        qy_photo_grid.setAdapter(adapter);
    }


    @OnClick({R.id.qy_rz_pp_ray, R.id.qy_rz_job_ray,
            R.id.qy_rz_job_two_ray, R.id.qy_rz_job_therr_ray
            , R.id.qy_rz_sfz_img, R.id.qy_rz_but,
            R.id.distributor_ray
            , R.id.qy_rz_name
            , R.id.qy_rz_id})
    public void OnClick(View view) {

        Intent intent;
        switch (view.getId()) {
            // 品牌
            case R.id.qy_rz_pp_ray:
                intent = new Intent(mContext, AllBrandActivity.class);
                startActivityForResult(intent, 100);
                break;
            //职位
            case R.id.qy_rz_job_ray:
                intent = new Intent(mContext, JobActivity.class);
                intent.putExtra("form", "job_one");
                startActivityForResult(intent, 200);
                break;
            case R.id.qy_rz_job_two_ray:
                intent = new Intent(mContext, JobActivity.class);
                intent.putExtra("form", "job_two");
                startActivityForResult(intent, 300);
                break;
            case R.id.qy_rz_job_therr_ray:
                intent = new Intent(mContext, JobActivity.class);
                intent.putExtra("form", "job_therr");
                startActivityForResult(intent, 400);
                break;
            //经销商
            case R.id.distributor_ray:
                if (qy_rz_pp.getText().toString().isEmpty()) {
                    ToastUtils.normal(mContext, "请先选择品牌").show();
                    return;
                }
                intent = new Intent(mContext, DistributorActivity.class);
                intent.putExtra("brandId", brandId);
                startActivityForResult(intent, 500);
                break;
            //身份证认证
            case R.id.qy_rz_sfz_img:
            case R.id.qy_rz_name:
            case R.id.qy_rz_id:
                Intent intent1 = new Intent(mContext, IdCardActivity.class);
                startActivityForResult(intent1, 100);
                break;
            //提交认证
            case R.id.qy_rz_but:
                getUserIdentity();
                break;
        }
    }


    //监听选择图片监听事件
    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                showDialog();
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


    private void showDialog() {
        dialog = new DialogUploadPicture(this);
        dialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        dialog.getCancel().setText("取消");
        dialog.getAlbum().setText("选择相册");
        dialog.getPhotograph().setText("相机");
        dialog.setPhotographListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(EnterpriseAuthenticationActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                dialog.dismiss();
            }
        });
        dialog.setAlbumListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(EnterpriseAuthenticationActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                dialog.dismiss();
            }
        });

        dialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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

            case 10:
                if (data.getStringExtra("jobname") != null && data.getStringExtra("jobid") != null) {
                    qy_rz_job.setText(data.getStringExtra("jobname"));
                    jobid_one = data.getStringExtra("jobid");
                    job_text_one = data.getStringExtra("jobname");
                }
                break;

            case 20:
                if (data.getStringExtra("jobname") != null && data.getStringExtra("jobid") != null) {
                    if (job_text_one == null) {
                        ToastUtils.normal(mContext, "职位一不能为空").show();
                        return;
                    }
                    if (job_text_one.equals(data.getStringExtra("jobname"))) {
                        ToastUtils.normal(mContext, "不能选择相同的职位").show();
                        return;
                    }
                    qy_rz_job_two.setText(data.getStringExtra("jobname"));
                    jobid_two = data.getStringExtra("jobid");
                    job_text_two = data.getStringExtra("jobname");
                }
                break;
            case 30:
                if (data.getStringExtra("jobname") != null && data.getStringExtra("jobid") != null) {

                    if (job_text_two == null) {
                        ToastUtils.normal(mContext, "职位二不能为空").show();
                        return;
                    }

                    if (job_text_two.equals(data.getStringExtra("jobname"))) {
                        ToastUtils.normal(mContext, "不能选择相同的职位").show();
                        return;
                    }
                    qy_rz_job_therr.setText(data.getStringExtra("jobname"));
                    jobid_therr = data.getStringExtra("jobid");
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
                    qy_rz_name.setText(realName);
                    qy_rz_id.setText(idNumber);
                }
                break;
            case 200:
                if (data.getStringExtra("BrandName") != null) {
                    qy_rz_pp.setText(data.getStringExtra("BrandName"));
                    brandId = data.getStringExtra("BrandId");
                }

            case 300:
                if (data.getStringExtra("CompName") != null) {
                    qy_rz_jxs.setText(data.getStringExtra("CompName"));

                }
                if (data.getStringExtra("comId") != null) {
                    custId = data.getStringExtra("comId");
                }
                break;


        }
    }


    //上传图片回调
    @Override
    public void getPicData(PutObjectRequest data) {

    }

    //失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {

    }


    /*//初始化一个OssService用来上传下载
    public OssService initOSS(String endpoint, String bucket) {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        //OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        //使用自己的获取STSToken的类
        String ak = SPTool.getContent(mContext, Constants.AK);
        String sk = SPTool.getContent(mContext, Constants.SK);
        String token = SPTool.getContent(mContext, Constants.TOKEN);
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ak, sk, token);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        return new OssService(oss, bucket, this);
    }

*/

    /**
     * 经销商认证
     */

    private void getUserIdentity() {
        requestData = new JSONObject();
        jsonArray = new JSONArray();
        idJsonArray = new JSONArray();
        try {
            //最外层 的参数
            requestData.put("userId", userId);
            requestData.put("jobId", jobid_one);
            requestData.put("job2Id", jobid_two);
            requestData.put("job2Id", jobid_therr);
            requestData.put("brandId", brandId);
            requestData.put("identityType", "10211003");
            requestData.put("custId", custId);
            for (int i = 0; i < selImageList.size(); i++) {
                objectname = userId + "-" + TimeUtil.getDateTimeFromMil(System.currentTimeMillis()) + i + ".png";
                listTime.add(objectname);
                JSONObject root = new JSONObject();
                root.put("attachmentName", "user-identity/" + objectname);
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
            showProgressDialog();
            Call<HttpResult> call = communityService().saveDealerIdentity(body);
            call.enqueue(new RetrofitCallback<HttpResult>() {
                @Override
                public void onSuccess(HttpResult model) {
                    if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                        ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                        return;
                    }
                    //上传认证图片
                    for (int i = 0; i < selImageList.size(); i++) {
                        path = selImageList.get(i).path;
                        ossService.asyncPutImage("user-identity/" + listTime.get(i), path);
                    }
                    SPTool.putContent(mContext, Constants.IDENTITYTYPE, "10211003");
                    ToastUtils.normal(mContext, "认证成功").show();
                    finish();
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
        } catch (Exception e) {
        }
    }

}
