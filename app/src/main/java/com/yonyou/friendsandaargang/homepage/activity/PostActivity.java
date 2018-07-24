package com.yonyou.friendsandaargang.homepage.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;

import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.MyApplication;
import com.yonyou.friendsandaargang.forum.activirty.ImageGridActivityTwo;
import com.yonyou.friendsandaargang.forum.bean.attachmentList;
import com.yonyou.friendsandaargang.guide.bean.BucketList;
import com.yonyou.friendsandaargang.homepage.adapter.ImagePickerAdapter;
import com.yonyou.friendsandaargang.info.bean.Draft;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.CommonUtil;
import com.yonyou.friendsandaargang.utils.GlideImageLoader;
import com.yonyou.friendsandaargang.utils.ImageUtils;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SDCardUtil;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.StringUtils;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.utils.Tool;
import com.yonyou.friendsandaargang.view.MyRecylerview;
import com.yonyou.friendsandaargang.view.RichTextEditor;
import com.yonyou.friendsandaargang.view.dialog.DialogUploadPicture;
import com.yonyou.friendsandaargang.view.dialog.DraftDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shibing on 18/2/28.
 * 发帖
 */

public class PostActivity extends BaseActivity implements
        View.OnClickListener,
        OssService.picResultCallback, TextWatcher {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    @BindView(R.id.tizi_title)
    EditText tizi_title;  //发表的标题
    @BindView(R.id.title_text)
    TextView title_text;  //title
    @BindView(R.id.richtexteditor)
    RichTextEditor richtexteditor;  //内容
    @BindView(R.id.tizi_immage_lay)
    TextView tizi_immage_lay;  //添加图片

    //类型
    @BindView(R.id.tizi_type_ray)
    RelativeLayout rayTpye;     //交易市场 or新车交付   类型
    @BindView(R.id.tizi_type_text)
    TextView tvTypetext;        //tv
    @BindView(R.id.type_view)
    TextView tvtypeView;     //分割线
    //论坛
    @BindView(R.id.tizi_luntan_text)
    TextView tizi_luntan_text;
    @BindView(R.id.tizi_luntan_ray)
    RelativeLayout rayForum; //帖子论坛
    @BindView(R.id.froum_view)
    TextView tvFroumview;
    //品牌
    @BindView(R.id.tizi_pipai_ray)
    RelativeLayout rayPinpai;  //品牌
    @BindView(R.id.tizi_pipai_text)
    TextView tizi_pipai_text;
    @BindView(R.id.tizi_pinpai_title_tv)
    TextView tvPinpaiTitle;  //品牌title


    private DraftDialog draftDialog;
    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";
    private ArrayList<ImageItem> images;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    private JSONArray jsonArray;
    private JSONObject requestData;
    private String path;
    private String objectname;
    private List<String> times;
    private Subscription subsInsert;
    private Subscription subsLoading;
    private String postId, userId, type, brandId, strTitles, draftContent;

    //论坛id
    private String forumName, forumId, brandName, DrafTtitle, typeName;
    private List<RichTextEditor.EditData> editList;
    private List<String> listPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        showDraftContent();
        initviews();
        setMarket();
        initImagePicker();
        ossService = initOSS(endpoint, bucketName);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (draftContent != null) {
            draftContent = getEditData();
        }
    }

    /**
     * 初始化
     */
    private void initviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        //标题
        if (getIntent().getStringExtra("title") != null) {
            strTitles = getIntent().getStringExtra("title");
            title_text.setText(strTitles);
        }
        //帖子还是话题
        if (getIntent().getStringExtra("type") != null) {
            type = getIntent().getStringExtra("type");
        }
        //帖子有添加图片  话题没有添加图片
        if (type.equals("10051001")) {
            tizi_immage_lay.setVisibility(View.GONE);
        } else {
            tizi_immage_lay.setVisibility(View.VISIBLE);
        }
        selImageList = new ArrayList<>();
        requestData = new JSONObject();
        jsonArray = new JSONArray();
        listPath = new ArrayList<>();
        times = new ArrayList<>();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    /**
     * 新车交易 or配件市场
     */
    private void setMarket() {
        if (forumId.equals("1025") || forumId.equals("1026")) {
            rayTpye.setVisibility(View.VISIBLE);
            tvtypeView.setVisibility(View.VISIBLE);
            rayForum.setVisibility(View.GONE);
            tvFroumview.setVisibility(View.GONE);
            tvPinpaiTitle.setText("品牌");
        } else {
            rayTpye.setVisibility(View.GONE);
            tvtypeView.setVisibility(View.GONE);
            rayForum.setVisibility(View.VISIBLE);
            tvFroumview.setVisibility(View.VISIBLE);
            tvPinpaiTitle.setText("品牌（选填）");
        }


    }


    /**
     * 草稿页传来的bunlde    显示草稿内容
     */
    private void showDraftContent() {
        //草稿id
        if (getIntent().getStringExtra(Constants.POSTID) != null) {
            postId = getIntent().getStringExtra(Constants.POSTID);
        }
        if (getIntent().getStringExtra("content") != null && !getIntent().getStringExtra("content").equals("")) {
            draftContent = getIntent().getStringExtra("content");
            richtexteditor.clearAllLayout();
            showDataSync(draftContent);
        }
        if (getIntent().getStringExtra("forumName") != null) {
            forumName = getIntent().getStringExtra("forumName");
            tizi_luntan_text.setText(forumName);
        }
        if (getIntent().getStringExtra("forumId") != null) {
            forumId = getIntent().getStringExtra("forumId");
        }
        if (getIntent().getStringExtra("brandName") != null) {
            brandName = getIntent().getStringExtra("brandName");
            tizi_pipai_text.setText(brandName);
        }
        if (getIntent().getStringExtra("DrafTtitle") != null) {
            DrafTtitle = getIntent().getStringExtra("DrafTtitle");
            tizi_title.setText(DrafTtitle);
        }
        tizi_title.addTextChangedListener(this);

    }


    /**
     * 异步方式显示数据
     * <p>
     * <p>
     * 草稿过来显示的图片
     *
     * @param html
     */
    private void showDataSync(final String html) {
        subsLoading = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                showEditData(subscriber, html);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressDialog();
                        //在图片全部插入完毕后，再插入一个EditText，防止最后一张图片后无法插入文字
                        richtexteditor.addEditTextAtIndex(richtexteditor.getLastIndex(), "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressDialog();
                        ToastUtils.normal(mContext, "解析错误：图片不存在或已损坏").show();
                    }

                    @Override
                    public void onNext(String text) {
                        if (text.contains("<img") && text.contains("src=")) {
                            //imagePath可能是本地路径，也可能是网络地址
                            String imagePath = StringUtils.getImgSrc(text);
                            richtexteditor.addImageViewAtIndex(richtexteditor.getLastIndex(), imagePath);
                        } else {
                            richtexteditor.addEditTextAtIndex(richtexteditor.getLastIndex(), text);
                        }
                    }
                });
    }


    /**
     * 负责处理编辑数据提交等事宜  传给草稿页面的content
     */
    private String getDraftEditData() {
        times.clear();
        listPath.clear();
        editList = richtexteditor.buildEditData();
        StringBuffer content1 = new StringBuffer();
        for (int i = 0; i < editList.size(); i++) {
            if (editList.get(i).inputStr != null) {
                content1.append(editList.get(i).inputStr);
            } else if (editList.get(i).imagePath != null) {
                //发帖图片
                objectname = userId + "-"
                        + TimeUtil.getDateTimeFromMil(System.currentTimeMillis())
                        + i
                        + ".png";
                times.add(objectname);
                //把图片路径添加至 一个list中
                listPath.add(editList.get(i).imagePath);
                content1.append("<img src=\"").append(editList.get(i).imagePath).append("\"/>");
            }
        }
        return content1.toString();
    }


    /**
     * 显示数据
     */
    protected void showEditData(Subscriber<? super String> subscriber, String html) {
        try {
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                subscriber.onNext(text);
            }
            subscriber.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }


    /**
     * 发帖的编辑数据提交等事宜
     */
    private String getEditData() {
        times.clear();
        listPath.clear();
        editList = richtexteditor.buildEditData();
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < editList.size(); i++) {
            if (editList.get(i).inputStr != null) {
                content.append(editList.get(i).inputStr);
            } else if (editList.get(i).imagePath != null) {
                //发帖图片
                objectname = userId + "-"
                        + TimeUtil.getDateTimeFromMil(System.currentTimeMillis())
                        + i
                        + ".png";
                times.add(objectname);
                listPath.add(editList.get(i).imagePath);
                content.append("[#post-attachment/").append(objectname).append("#]");
            }
        }
        return content.toString();
    }


    /**
     * 发帖时异步处理图片
     */
    private void insertImagesSync() {
        showProgressDialog();
        subsInsert = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    richtexteditor.measure(0, 0);
                    int width = CommonUtil.getScreenWidth(PostActivity.this);
                    int height = CommonUtil.getScreenHeight(PostActivity.this);
                    //可以同时插入多张图片
                    for (int i = 0; i < images.size(); i++) {
                        String imagePath = images.get(i).path;
                        Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath, width, height);//压缩图片
                        imagePath = SDCardUtil.saveToSdCard(bitmap);
                        subscriber.onNext(imagePath);
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        dismissProgressDialog();
                        richtexteditor.addEditTextAtIndex(richtexteditor.getLastIndex(), " ");
                        ToastUtils.normal(mContext, "图片添加成功:").show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissProgressDialog();
                        ToastUtils.normal(mContext, "图片添加失败:").show();
                    }

                    @Override
                    public void onNext(String imagePath) {
                        richtexteditor.insertImage(imagePath, richtexteditor.getMeasuredWidth());
                    }
                });
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.tizi_commit_lay, R.id.back_lay, R.id.tizi_luntan_ray, R.id.tizi_pipai_ray, R.id.tizi_immage_lay, R.id.tizi_type_ray})
    public void onClick(View v) {
        Intent intent = null;
        draftContent = getEditData();
        switch (v.getId()) {
            case R.id.back_lay:
                //话题 or   没有保存草稿
                if (type.equals("10051001") || type.equals("10051004") || type.equals("10051005")) {
                    finish();
                    return;
                }
                if (TextUtils.isEmpty(DrafTtitle) && TextUtils.isEmpty(draftContent) && forumId == null) {
                    finish();
                    return;
                }
                showDraftDialog(1);
                break;
            case R.id.tizi_commit_lay:
                commitPost();
                break;
            case R.id.tizi_luntan_ray:
                intent = new Intent(mContext, AllForumsActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.tizi_pipai_ray:
                intent = new Intent(mContext, AllBrandActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.tizi_immage_lay:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(PostActivity.this, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            case R.id.tizi_type_ray:
                showDraftDialog(2);
                break;
        }
    }


    /**
     * 发布帖子时 区分是帖子 话题 还是交易市场  出售
     */
    private void commitPost() {
        //如果是新车交易 or 配件市场
        if (type.equals("10051004") || type.equals("10051005")) {
            if (TextUtils.isEmpty(DrafTtitle)) {
                ToastUtils.normal(mContext, "请输入标题").show();
                return;
            }
            if (TextUtils.isEmpty(brandName)) {
                ToastUtils.normal(mContext, "请选择品牌").show();
                return;
            }

            if (TextUtils.isEmpty(typeName)) {
                ToastUtils.normal(mContext, "请选择类型").show();
                return;
            }
            if (TextUtils.isEmpty(draftContent)) {
                ToastUtils.normal(mContext, "请输入内容").show();
                return;
            }
            getHairPost(DrafTtitle, draftContent, 0);
        } else {

            if (TextUtils.isEmpty(DrafTtitle)) {
                ToastUtils.normal(mContext, "请输入标题").show();
                return;
            }
            if (TextUtils.isEmpty(forumName)) {
                ToastUtils.normal(mContext, "请选择论坛").show();
                return;
            }
            if (TextUtils.isEmpty(draftContent)) {
                ToastUtils.normal(mContext, "请输入内容").show();
                return;
            }
            getHairPost(DrafTtitle, draftContent, 0);
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
            case 100:
                if (data.getStringExtra("ForumName") != null) {
                    forumName = data.getStringExtra("ForumName");
                    tizi_luntan_text.setText(forumName);
                    forumId = data.getStringExtra("ForumId");
                }
                break;
            case 200:
                if (data.getStringExtra("BrandName") != null) {

                    tizi_pipai_text.setText(data.getStringExtra("BrandName"));
                    brandName = data.getStringExtra("BrandName");
                    brandId = data.getStringExtra("BrandId");
                }
                break;
            case ImagePicker.RESULT_CODE_ITEMS:
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null) {
                        selImageList.addAll(images);
                        insertImagesSync();
                    }
                }
                break;
            case ImagePicker.RESULT_CODE_BACK:
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        selImageList.clear();
                        selImageList.addAll(images);
                    }
                }
                break;
        }
    }

    /**
     * 判断是否为第一次发帖或者是是否在草稿中进入这个页面  需要传入postid；
     */
    private void getHairPost(String title, String content, final int draft) {
        try {
            //发表帖子的参数
            requestData.put("authorId", userId);
            requestData.put("postId", postId);
            requestData.put("isDraft", draft);
            requestData.put("type", type);
            requestData.put("title", title);
            requestData.put("content", content);
            requestData.put("draft", "draft");
            requestData.put("forumId", forumId);
            requestData.put("brandId", brandId);
            for (int i = 0; i < listPath.size(); i++) {
                objectname = times.get(i);
                JSONObject root = new JSONObject();
                root.put("type", "10121001");
                root.put("attachmentName", "post-attachment/" + objectname);
                jsonArray.put(root);
            }
            requestData.put("attachmentList", jsonArray);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestData.toString());
            Call<HttpResult> call = communityService().getSavePost(body);
            NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
            netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
                @Override
                public void onResponseCallback(HttpResult httpResult) {
                    if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                        ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                        return;
                    }
                    putPostSuccess(draft);
                }

                @Override
                public void onFailureCallback() {
                    ToastUtils.normal(mContext, "服务器加载异常").show();
                }
            });
        } catch (Exception e) {
        }
    }


    /**
     * 发帖成功后的处理工作
     */
    private void putPostSuccess(int draft) {
        if (draft == 1) {
            ToastUtils.normal(mContext, "已保存至草稿箱").show();
            finish();
            return;
        }
        //没有图片就不上传图片到服务器  信息已发布成功
        if (listPath.size() == 0) {

            switch (type) {
                case "10051002":
                    ToastUtils.normal(mContext, "帖子发表成功").show();
                    break;
                case "10051001":
                    ToastUtils.normal(mContext, "话题发表成功").show();
                    break;
                case "10051004":
                case "10051005":
                    ToastUtils.normal(mContext, "信息已发布成功").show();
                    break;
            }

            finish();
            return;
        }
        for (int i = 0; i < listPath.size(); i++) {
            objectname = times.get(i);
            path = listPath.get(i);
            int width = CommonUtil.getScreenWidth(PostActivity.this);
            int height = CommonUtil.getScreenHeight(PostActivity.this);
            Bitmap bitmap = ImageUtils.getSmallBitmap(path, width, height);//压缩图片
            path = SDCardUtil.saveToSdCard(bitmap);
            ossService.asyncPutImage("post-attachment/" + objectname, path);
        }
        switch (type) {
            case "10051002":
                ToastUtils.normal(mContext, "帖子发表成功").show();
                break;
            case "10051004":
            case "10051005":
                ToastUtils.normal(mContext, "信息已发布成功").show();
                break;
        }
        finish();
    }


    /**
     * 保存草稿弹窗
     */
    private void showDraftDialog(final int falg) {
        draftDialog = new DraftDialog(PostActivity.this);
        if (falg == 1) {
            draftDialog.getSave_draft().setText("保存草稿");
            draftDialog.getCancel_draf().setText("不保存");
            draftDialog.getCancel_draf().setText("取消");
        } else {
            draftDialog.getSave_draft().setText("求购");
            draftDialog.getNo_draft().setText("出售");
            draftDialog.getCancel_draf().setText("取消");
        }
        //取消
        draftDialog.setCancelDrafListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draftDialog.dismiss();
            }
        });
        //不保存
        draftDialog.setNoDraftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (falg == 1) {
                    draftDialog.dismiss();
                    finish();
                } else {
                    type = "10051005";
                    tvTypetext.setText("出售");
                    typeName = "出售";
                    draftDialog.dismiss();
                }
            }
        });
        //保存草稿
        draftDialog.setSaveDraftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (falg == 1) {
                    String title = tizi_title.getText().toString();
                    String content = getDraftEditData();
                    getHairPost(title, content, 1);
                    draftDialog.dismiss();
                } else {
                    type = "10051004";
                    tvTypetext.setText("求购");
                    typeName = "求购";
                    draftDialog.dismiss();
                }
            }
        });
        draftDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (draftDialog != null) {
            draftDialog.dismiss();
        }
    }


    /**
     * 上传回来 回调
     *
     * @param data
     */
    @Override
    public void getPicData(PutObjectRequest data) {

        Logger.e("----1010101---", SPTool.getContent(mContext, Constants.TOKEN));
        Logger.e("----1111111---", data.getBucketName());
        Logger.e("----2222222---", data.getObjectKey());
        Logger.e("----4444444---", data.getUploadFilePath());

        Logger.e("----5555555---", data.getMetadata().getCacheControl());
        Logger.e("----6666666---", data.getMetadata().getContentDisposition());
        Logger.e("----7777777---", data.getMetadata().getContentEncoding());
        Logger.e("----8888888---", data.getMetadata().getContentType());
        Logger.e("----9999999---", data.getMetadata().getObjectType());


    }


    //失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        DrafTtitle = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
