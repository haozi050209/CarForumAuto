package com.yonyou.friendsandaargang.homepage.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.yonyou.friendsandaargang.view.dialog.DraftDialog;

import org.json.JSONArray;
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

/**
 * Created by shibing on 18/2/28.
 * 发帖
 */

public class PublishPostActivity extends BaseActivity implements View.OnClickListener, OssService.picResultCallback {
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    @BindView(R.id.tizi_title)
    EditText tizi_title;  //发表的标题
    @BindView(R.id.title_text)
    TextView title_text;  //title
    @BindView(R.id.richeditor)
    CustomRichEditor richtexteditor;  //内容
    @BindView(R.id.tizi_immage_lay)
    TextView tizi_immage_lay;  //添加图片
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


    private DraftDialog draftDialog;    //交易或者保存草稿弹窗
    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";
    private ArrayList<ImageItem> images;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private JSONArray jsonArray;
    private JSONObject requestData;
    private RequestBody body;


    private String forumName, forumId, postId, userId, type, brandId, strTitles, draftContent, path, objectname,  //图片地址，图片上传oss名称
            brandName, DrafTtitle, typeName, picContent;    //有图片内容，没有图片内容


    private Map<String, String> map;            //添加到map  遍历map
    private List<String> listobjectname;        //上传到oss key 值
    private List<String> draftListPic;          //草稿图片

    private boolean mHasAddImg = false;         //默认编辑器没有图片


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishpost);
        ButterKnife.bind(this);
        ossService = initOSS(endpoint, bucketName);
        initviews();
        setMarket();
        initImagePicker();
        Richtexteditor();
        showDraftContent();
    }


    /**
     * 初始化
     */
    private void initviews() {
        userId = SPTool.getContent(mContext, Constants.USER_ID);
        forumId = getIntent().getStringExtra(Constants.FORUMID);
        strTitles = getIntent().getStringExtra("title");  //标题
        title_text.setText(strTitles);
        type = getIntent().getStringExtra("type");  //帖子还是话题
        if (type.equals("10051001")) {                 //帖子有添加图片  话题没有添加图片
            tizi_immage_lay.setVisibility(View.GONE);
        } else {
            tizi_immage_lay.setVisibility(View.VISIBLE);
        }

        selImageList = new ArrayList<>();           //图片list
        requestData = new JSONObject();             //上传图片json
        jsonArray = new JSONArray();
        listobjectname = new ArrayList<>();     //oss key 值
        draftListPic = new ArrayList<>();       //草稿页进来的获取图片路径
        map = new HashMap<>();
    }


    /**
     * 编辑内容
     */
    private void Richtexteditor() {
        richtexteditor.setEditorHeight(300);
        richtexteditor.setEditorFontSize(16);
        richtexteditor.setEditorFontColor(getResources().getColor(R.color.color_balank));
        richtexteditor.setPadding(10, 10, 10, 20);
        richtexteditor.setPlaceholder("请填写内容......");
        richtexteditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                if (mHasAddImg) {
                    mHasAddImg = false;
                    richtexteditor.setNewLine();
                }
            }
        });
    }


    /**
     * 草稿页传来的bunlde    显示草稿内容
     */
    private void showDraftContent() {
        //草稿id
        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.POSTID))) {
            postId = getIntent().getStringExtra(Constants.POSTID);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))) {
            draftContent = getIntent().getStringExtra("content");
            richtexteditor.setHtml(draftContent);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("forumName"))) {
            forumName = getIntent().getStringExtra("forumName");
            tizi_luntan_text.setText(forumName);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("brandName"))) {
            brandName = getIntent().getStringExtra("brandName");
            tizi_pipai_text.setText(brandName);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("DrafTtitle"))) {
            DrafTtitle = getIntent().getStringExtra("DrafTtitle");
            tizi_title.setText(DrafTtitle);
        }
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
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.tizi_commit_lay, R.id.back_lay, R.id.tizi_luntan_ray, R.id.tizi_pipai_ray, R.id.tizi_immage_lay, R.id.tizi_type_ray})
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_lay:
                //话题 or   没有保存草稿
                if (type.equals("10051001") || type.equals("10051004") || type.equals("10051005")) {
                    finish();
                    return;
                }
                if (TextUtils.isEmpty(tizi_title.getText().toString()) && TextUtils.isEmpty(richtexteditor.getHtml()) && forumId == null) {
                    finish();
                    return;
                }
                showDraftDialog(1);
                break;
            case R.id.tizi_commit_lay:
                commitPost(richtexteditor.getHtml(), tizi_title.getText().toString());          // 编辑内容
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
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());  //打开选择,本次允许选择的数量
                Intent intent1 = new Intent(PublishPostActivity.this, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            case R.id.tizi_type_ray:
                showDraftDialog(2);
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
                        selImageList.clear();
                        for (int i = 0; i < images.size(); i++) {
                            mHasAddImg = true;
                            richtexteditor.insertImage(images.get(i).path, "picvision\" style=\"max-width:100%");
                        }
                    }
                }
                break;
            case ImagePicker.RESULT_CODE_BACK:
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    selImageList.clear();
                    for (int i = 0; i < images.size(); i++) {
                        mHasAddImg = true;
                        richtexteditor.insertImage(images.get(i).path, "picvision\" style=\"max-width:100% ");
                    }
                }
                break;
        }
    }


    /**
     * 发布帖子时 区分是帖子 话题 还是交易市场  出售
     */
    private void commitPost(String content, String title) {
        draftListPic.clear();
        listobjectname.clear();


        draftListPic = StringUtils.getHtmlImgSrc(content);
        if (draftListPic.size() > 0) {                       //大于0 就是有图片的内容
            for (int i = 0; i < draftListPic.size(); i++) {
                Logger.e("draftListPic", draftListPic.get(i) + "");
                objectname = userId + "-"
                        + TimeUtil.getDateTimeFromMil(System.currentTimeMillis())
                        + i
                        + ".png";
                listobjectname.add(objectname);
                map.put(objectname, draftListPic.get(i));
                int width = CommonUtil.getScreenWidth(mContext);
                int height = CommonUtil.getScreenHeight(mContext);
                Bitmap bitmap = ImageUtils.getSmallBitmap(draftListPic.get(i), width, height);//压缩图片
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

        //如果是新车交易 or 配件市场
        if (type.equals("10051004") || type.equals("10051005")) {
            if (TextUtils.isEmpty(title)) {
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
            if (TextUtils.isEmpty(content)) {
                ToastUtils.normal(mContext, "请输入内容").show();
                return;
            }

        } else {

            if (TextUtils.isEmpty(title)) {
                ToastUtils.normal(mContext, "请输入标题").show();
                return;
            }
            if (TextUtils.isEmpty(forumId)) {
                ToastUtils.normal(mContext, "请选择论坛").show();
                return;
            }
            if (TextUtils.isEmpty(content)) {
                ToastUtils.normal(mContext, "请输入内容").show();
                return;
            }

        }
        //不为空 就是有图片，
        if (!TextUtils.isEmpty(picContent)) {
            getHairPost(title, picContent, 0);
            return;
        }
        getHairPost(title, content, 0);

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
            for (int i = 0; i < listobjectname.size(); i++) {
                String objectNamePath = listobjectname.get(i);
                JSONObject root = new JSONObject();
                root.put("type", "10121001");
                root.put("attachmentName", "post-attachment/" + objectNamePath);
                jsonArray.put(root);
            }
            requestData.put("attachmentList", jsonArray);
            String string = requestData.toString().replaceAll("\\\\\"", "");     //去掉/"
            if (draft == 1) {     //草稿就不去掉 引号
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestData.toString());
            } else {
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string);
            }
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
            ToastUtils.normal(mContext, e.getMessage()).show();
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
        if (draftListPic.size() == 0) {
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
     * 保存草稿or  交易信息类型
     */
    private void showDraftDialog(final int falg) {
        draftDialog = new DraftDialog(PublishPostActivity.this);
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
                    getHairPost(title, richtexteditor.getHtml(), 1);
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


    /**
     * 上传回来 回调
     *
     * @param data
     */
    @Override
    public void getPicData(PutObjectRequest data) {
    }


    //失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {
        ToastUtils.normal(mContext, "图片上传失败,请重新上传").show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (draftDialog != null) {
            draftDialog.dismiss();
        }
    }
}
