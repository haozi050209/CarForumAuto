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
import com.yonyou.friendsandaargang.homepage.adapter.ImagePickerAdapter;
import com.yonyou.friendsandaargang.network.OssService;
import com.yonyou.friendsandaargang.view.dialog.DialogUploadPicture;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shibing on 18/3/8.
 * <p>
 * <p>
 * 大咖认证
 */

public class BigShotAuthenticationActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener
        , OssService.picResultCallback {


    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    //姓名
    @BindView(R.id.dk_rz_name)
    EditText dk_rz_name;
    //身份证
    @BindView(R.id.dk_rz_id)
    EditText dk_rz_id;
    //所在行业
    @BindView(R.id.dk_rz_industry)
    EditText dk_rz_industry;
    //公司 学校
    @BindView(R.id.dk_rz_school)
    EditText dk_rz_school;
    //职位
    @BindView(R.id.dk_rz_job)
    TextView dk_rz_job;

    @BindView(R.id.dk_photo_grid)
    RecyclerView dk_photo_grid;


    private String job_id;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> images = null;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private Long time;

    private DialogUploadPicture dialog;
    //OSS的上传下载
    private OssService ossService;
    private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private String bucketName = "yonyou-community-app-images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_bigshot);
        ButterKnife.bind(this);
        initviews();
    }


    private void initviews() {
        getTitleBar();
        setTitleText("大咖认证");
        initImagePicker();
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        dk_photo_grid.setLayoutManager(new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        dk_photo_grid.setHasFixedSize(true);
        dk_photo_grid.setAdapter(adapter);
    }

    @OnClick({R.id.dk_rz_but, R.id.dk_rz_sfz_img, R.id.dk_rz_job})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.dk_rz_but:
                break;
            case R.id.dk_rz_sfz_img:
                break;
            case R.id.dk_rz_job:
                Intent intent = new Intent(mContext, JobActivity.class);
                intent.putExtra("form", "job_dakai");
                startActivityForResult(intent, 300);
                break;
        }
    }


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
                Intent intent = new Intent(mContext, ImageGridActivity.class);
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
                Intent intent1 = new Intent(mContext, ImageGridActivity.class);
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

            case 40:
                if (data.getStringExtra("jobname") != null && data.getStringExtra("jobid") != null) {
                    dk_rz_job.setText(data.getStringExtra("jobname"));
                    job_id = data.getStringExtra("jobid");
                }
                break;
        }
    }

    @Override
    public void getPicData(PutObjectRequest data) {

    }


    //失败回调
    @Override
    public void Failure(ClientException clientException, ServiceException serviceException) {

    }
}
