package com.yonyou.friendsandaargang.homepage.qaarea.activity.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.BaseActivity;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetUtils;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.network.ResponseCallBack;
import com.yonyou.friendsandaargang.utils.CommonUtil;
import com.yonyou.friendsandaargang.utils.ImageUtils;
import com.yonyou.friendsandaargang.utils.Logger;
import com.yonyou.friendsandaargang.utils.SDCardUtil;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.StringUtils;
import com.yonyou.friendsandaargang.utils.TimeUtil;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.CustomRichEditor;
import com.yonyou.friendsandaargang.view.RichTextEditor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class PutToQaActivity extends BaseActivity implements OssService.picResultCallback {


    @BindView(R.id.putto_title)
    EditText edTitle;
    @BindView(R.id.putto_zhuanqu_tv)
    TextView tvZhuanqu;
    @BindView(R.id.putto_sj_edit)
    EditText edSj;
    @BindView(R.id.putto_richtexteditor)
    CustomRichEditor textEditor;


    //添加图片模块
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> images;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private JSONArray jsonArray;
    private JSONObject requestData;


    //图片处理
    private String path,objectname;

    private List<String> listPath,listobjectname;
    private Map<String, String> map;            //添加到map  遍历map
    private String userId,forumName,forumId,picContent;


    //阿里元图片上传
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";
    private boolean mHasAddImg = false;         //默认编辑器没有图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale);
        ButterKnife.bind(this);
        initviews();
        Richtexteditor();
    }


    /**
     * 初始化¬
     */
    private void initviews() {
        ossService = initOSS(endpoint, bucketName);   //初始化  oss
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        getTitleBar();
        setTitleText("提问").rightTv("图片");
        initImagePicker();

        forumName = getIntent().getStringExtra(Constants.TITLE);
        forumId = getIntent().getStringExtra(Constants.FORUMID);
        if (forumName != null) {
            tvZhuanqu.setText(forumName);
        }
        selImageList = new ArrayList<>();
        requestData = new JSONObject();
        jsonArray = new JSONArray();
        listPath = new ArrayList<>();
        listobjectname = new ArrayList<>();
        map = new HashMap<>();
    }


    /**
     * 编辑内容
     */
    private void Richtexteditor() {
        textEditor.setEditorHeight(300);
        textEditor.setEditorFontSize(16);
        textEditor.setEditorFontColor(getResources().getColor(R.color.color_balank));
        textEditor.setPadding(10, 10, 10, 20);
        textEditor.setPlaceholder("请填写内容......");
        textEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                if (mHasAddImg) {
                    mHasAddImg = false;
                    textEditor.setNewLine();
                }
            }
        });
    }


    @OnClick({R.id.titleBar_right_text, R.id.putto_zq_ray, R.id.putto_commit})
    public void OnClick(View view) {
        switch (view.getId()) {
            //添加图片
            case R.id.titleBar_right_text:
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            //选择专区
            case R.id.putto_zq_ray:
                Intent intent = new Intent(mContext, AllActivity.class);
                intent.putExtra(Constants.TITLE, "选择专区");
                startActivityForResult(intent, 100);
                break;
            //提问
            case R.id.putto_commit:
                putToCommit(edTitle.getText().toString(), textEditor.getHtml());
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 100:
                forumName = data.getStringExtra(Constants.TITLE);
                forumId = data.getStringExtra(Constants.FORUMID);
                if (forumName != null) {
                    tvZhuanqu.setText(forumName);
                }
                break;
            case ImagePicker.RESULT_CODE_ITEMS:
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null) {
                        for (int i = 0; i < images.size(); i++) {
                            mHasAddImg = true;
                            textEditor.insertImage(images.get(i).path, "picvision\" style=\"max-width:100%");
                        }
                    }
                }
                break;
            case ImagePicker.RESULT_CODE_BACK:
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        for (int i = 0; i < images.size(); i++) {
                            mHasAddImg = true;
                            textEditor.insertImage(images.get(i).path, "picvision\" style=\"max-width:100%");
                        }
                    }
                }
                break;
        }
    }


    /**
     * 提问问题
     */
    private void putToCommit(String title, String content) {
        listPath.clear();
        listobjectname.clear();
        listPath = StringUtils.getHtmlImgSrc(content);

        if (listPath.size() > 0) {                       //大于0 就是有图片的内容
            for (int i = 0; i < listPath.size(); i++) {
                Logger.e("draftListPic", listPath.get(i) + "");
                objectname = userId + "-"
                        + TimeUtil.getDateTimeFromMil(System.currentTimeMillis())
                        + i
                        + ".png";
                listobjectname.add(objectname);
                map.put(objectname, listPath.get(i));
                int width = CommonUtil.getScreenWidth(mContext);
                int height = CommonUtil.getScreenHeight(mContext);
                Bitmap bitmap = ImageUtils.getSmallBitmap(listPath.get(i), width, height);//压缩图片
                path = SDCardUtil.saveToSdCard(bitmap);
                ossService.asyncPutImage("post-attachment/" + objectname, path);
            }

            picContent = content
                    .replaceAll("<img src=", "")
                    .replaceAll("alt=\"picvision\" style=\"max-width:100%\">", "");
            //遍历map   替换
            for (Map.Entry<String, String> entry : map.entrySet()) {
                picContent = picContent.replace(entry.getValue(), "[#post-attachment/" + entry.getKey() + "#]");
            }
        }


        if (TextUtils.isEmpty(title)) {
            ToastUtils.normal(mContext, "标题未填写").show();
            return;
        }
        if (TextUtils.isEmpty(forumName)) {
            ToastUtils.normal(mContext, "专区未选择").show();
            return;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.normal(mContext, "内容未填写").show();
            return;
        }

        if (TextUtils.isEmpty(picContent)) {
            getPutToQa(title, content);
            return;
        }

        getPutToQa(title, picContent);

    }


    //发布问答
    private void getPutToQa(String title, String content) {
        try {
            requestData.put("authorId", userId);
            requestData.put("type", "10051003");
            requestData.put("title", title);
            requestData.put("content", content);
            requestData.put("forumId", forumId);
            requestData.put("rewardCoin", "0");
            for (int i = 0; i < listobjectname.size(); i++) {
                objectname = listobjectname.get(i);
                JSONObject root = new JSONObject();
                root.put("type", "10121001");
                root.put("attachmentName", "post-attachment/" + objectname);
                jsonArray.put(root);
            }
            requestData.put("attachmentList", jsonArray);

            String string = requestData.toString().replaceAll("\\\\\"", "");     //去掉/"
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string);
            Call<HttpResult> call = communityService().getpostReward(body);
            NetUtils<HttpResult> netUtils = new NetUtils<HttpResult>(mContext);
            netUtils.enqueue(call, new ResponseCallBack<HttpResult>() {
                @Override
                public void onResponseCallback(HttpResult httpResult) {
                    if (httpResult.getReturnCode() != 0 && !httpResult.getReturnMsg().equals("success")) {
                        ToastUtils.normal(mContext, httpResult.getReturnMsg()).show();
                        return;
                    }
                    ToastUtils.normal(mContext, "提问发表成功").show();
                    finish();


                }

                @Override
                public void onFailureCallback() {
                    ToastUtils.normal(mContext, "服务器加载异常").show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //图片上传成功
    @Override
    public void getPicData(PutObjectRequest data) {

    }

    //图片上传失败
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {

    }
}
