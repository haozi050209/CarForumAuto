package com.yonyou.friendsandaargang.info.activity.operationmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.bigkoo.pickerview.OptionsPickerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.homepage.adapter.ImagePickerAdapter;
import com.yonyou.friendsandaargang.info.bean.CustBean;
import com.yonyou.friendsandaargang.info.bean.JobList;
import com.yonyou.friendsandaargang.info.bean.SystemBean;
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

public class NewProblemActivity extends BaseActivity implements
        ImagePickerAdapter.OnRecyclerViewItemClickListener, OssService.picResultCallback {

    @BindView(R.id.tv_custname)
    TextView tvCustname;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_prdname)
    TextView tvPrdname;
    @BindView(R.id.et_question)
    EditText etQuestion;
    @BindView(R.id.rv_question)
    RecyclerView rvQuestion;

    private String userId;
    private String custId;          //站点id
    private String productId;       //系统id
    private String jobId;           //职位id
    private String recordContent;   //问题内容
    private String objectname;      //附件名称
    private String path;            //图片地址
    private JSONArray jsonArray;    //上传图片的json数据
    private JSONObject requestData; //最外层的数据
    private List<CustBean.ContentBean> custlist;
    private List<JobList.ContentBean> joblist;
    private List<SystemBean.ContentBean> systemlist;
    private List<String> custnamelist, jobnamelist, systemnamelist;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> images = null;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private List<String> items;

    private DialogUploadPicture dialogUploadPicture;
    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_problem);
        ButterKnife.bind(this);
        ossService = initOSS(endpoint, bucketName);
        initView();
        getCustByUserId();
        getUserJobs();
        getSystemByUserId();
    }

    private void initView() {
        getTitleBar();
        setTitleText("新建问题");
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        initImagePicker();
        selImageList = new ArrayList<>();
        items = new ArrayList<>();
        adapter = new ImagePickerAdapter(mContext, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        rvQuestion.setLayoutManager(new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvQuestion.setHasFixedSize(true);
        rvQuestion.setAdapter(adapter);
    }

    @OnClick({R.id.tv_switchcust, R.id.tv_switchjob, R.id.tv_switchsystem, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_switchcust://切换站点
                if (custnamelist != null && custnamelist.size() > 0 && custlist != null && custlist.size() > 0) {
                    showPickerView(custnamelist, "cust");
                } else {
                    ToastUtils.normal("当前无站点可供选择");
                }
                break;
            case R.id.tv_switchjob://切换职位
                if (jobnamelist != null && jobnamelist.size() > 0 && joblist != null && joblist.size() > 0) {
                    showPickerView(jobnamelist, "job");
                } else {
                    ToastUtils.normal("当前无职位可供选择");
                }
                break;
            case R.id.tv_switchsystem://切换系统
                if (systemnamelist != null && systemnamelist.size() > 0 && systemlist != null && systemlist.size() > 0) {
                    showPickerView(systemnamelist, "system");
                } else {
                    ToastUtils.normal("当前无系统可供选择");
                }
                break;
            case R.id.btn_submit:
                recordContent = etQuestion.getText().toString().trim();
                if (custId == null) {
                    ToastUtils.normal("站点不能为空");
                    return;
                }
                if (jobId == null) {
                    ToastUtils.normal("职位不能为空");
                    return;
                }
                if (productId == null) {
                    ToastUtils.normal("系统不能为空");
                    return;
                }
                if (recordContent.isEmpty()) {
                    ToastUtils.normal("问题内容不能为空");
                    return;
                }
                if (selImageList.isEmpty()) {
                    ToastUtils.normal("请上传附件");
                    return;
                }
                submitQuestion();
                break;
        }
    }

    private void getCustByUserId() {
        Call<CustBean> call = communityService().getCustByUserId(userId);
        call.enqueue(new RetrofitCallback<CustBean>() {
            @Override
            public void onSuccess(CustBean model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                custlist = model.getContent();
                custnamelist = new ArrayList<>();
                for (int i = 0; i < custlist.size(); i++) {
                    custnamelist.add(custlist.get(i).getCustName());
                }
                tvCustname.setText(custnamelist.get(0));
                custId = custlist.get(0).getCustId();
                Log.e("~~~~~ custlist.size()=", custlist.size() + "");
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal("服务器异常");
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void getUserJobs() {
        Call<JobList> call = communityService().getUserJobs(userId);
        call.enqueue(new RetrofitCallback<JobList>() {
            @Override
            public void onSuccess(JobList model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                joblist = model.getContent();
                jobnamelist = new ArrayList<>();
                for (int i = 0; i < joblist.size(); i++) {
                    jobnamelist.add(joblist.get(i).getJob());
                }
                tvJob.setText(jobnamelist.get(0));
                jobId = joblist.get(0).getJobId();
                Log.e("~~~~~ joblist.size()=", joblist.size() + "");
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal("服务器异常");
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void getSystemByUserId() {
        Call<SystemBean> call = communityService().getSystemByUserId(userId);
        call.enqueue(new RetrofitCallback<SystemBean>() {
            @Override
            public void onSuccess(SystemBean model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(mContext, model.getReturnMsg().toString()).show();
                    return;
                }
                systemlist = model.getContent();
                systemnamelist = new ArrayList<>();
                for (int i = 0; i < systemlist.size(); i++) {
                    systemnamelist.add(systemlist.get(i).getPrdName());
                }
                tvPrdname.setText(systemnamelist.get(0));
                productId = systemlist.get(0).getProductId();
                Log.e("~~~~~systemlist.size()=", systemlist.size() + "");
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.normal("服务器异常");
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void showPickerView(final List<String> list, final String type) {

        OptionsPickerView opv = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                switch (type) {
                    case "cust":
                        tvCustname.setText(list.get(options1));
                        custId = custlist.get(options1).getCustId();
                        break;
                    case "job":
                        tvJob.setText(list.get(options1));
                        jobId = joblist.get(options1).getJobId();
                        break;
                    case "system":
                        tvPrdname.setText(list.get(options1));
                        productId = systemlist.get(options1).getProductId();
                        break;
                }
            }
        }).build();
        opv.setPicker(list);
        opv.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                dialogUploadPicture = new DialogUploadPicture(mContext);
                dialogUploadPicture.getCancel().setText("取消");
                dialogUploadPicture.getAlbum().setText("选择相册");
                dialogUploadPicture.getPhotograph().setText("相机");
                dialogUploadPicture.setCancelable(false);
                dialogUploadPicture.setPhotographListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent = new Intent(NewProblemActivity.this, ImageGridActivity.class);
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
                        Intent intent1 = new Intent(NewProblemActivity.this, ImageGridActivity.class);
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
                        items.clear();
                        selImageList.clear();
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                    }
                }
                break;
        }
    }

    @Override
    public void getPicData(PutObjectRequest data) {
        items.add(data.getObjectKey());
        Log.e("-----items.size() = ", items.size() +"");
        Log.e("------ObjectKey", data.getObjectKey());
        Log.e("-----FilePath", data.getUploadFilePath());
        Log.e("*****", data.getUploadData().toString());
    }

    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {

    }

    private void submitQuestion() {
        OssPic();
        requestData = new JSONObject();
        jsonArray = new JSONArray();
        try {
            requestData.put("userId", userId);
            requestData.put("custId", custId);
            requestData.put("productId", productId);
            requestData.put("jobId", jobId);
            requestData.put("recordContent", recordContent);
            for (int i = 0; i < items.size(); i++) {
                objectname = items.get(i);
                JSONObject root = new JSONObject();
                root.put("fileName", objectname);
                jsonArray.put(root);
            }
            requestData.put("attachmentList", jsonArray);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestData.toString());
            showProgressDialog();
            Call<HttpResult> call = communityService().newRecord(body);
            call.enqueue(new RetrofitCallback<HttpResult>() {
                @Override
                public void onSuccess(HttpResult model) {
                    if (!model.getReturnMsg().equals("success") && model.getReturnCode() != 0) {
                        ToastUtils.normal(mContext, model.getReturnCode() + model.getReturnMsg()).show();
                        Log.e("~~~~~ requestData ", requestData.toString());
                        return;
                    }
                    Log.e("~~~~~ success", requestData.toString());
                    //上传附件图片
                   /* for (int i = 0; i < selImageList.size(); i++) {
                        objectname = userId + "-" + TimeUtil.getDateTimeFromMil(System.currentTimeMillis()) + i + ".png";
                        path = selImageList.get(i).path;
                        ossService.asyncPutImage(objectname, path);
                    }*/
                    ToastUtils.normal(mContext, "提交问题成功").show();
                    finish();
                }

                @Override
                public void onFailure(int code, String msg) {
                    ToastUtils.normal(mContext, msg).show();
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
            e.printStackTrace();
        }
    }

    private void OssPic() {
        for (int i = 0; i < selImageList.size(); i++) {
            objectname = userId + "-" + TimeUtil.getDateTimeFromMil(System.currentTimeMillis()) + i + ".png";
            path = selImageList.get(i).path;
            Log.e("-----objectname", objectname);
            Log.e("-----path", path);
            ossService.asyncPutImage(objectname, path);
        }
    }
}

