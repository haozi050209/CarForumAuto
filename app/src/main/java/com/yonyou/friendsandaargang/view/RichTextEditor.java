package com.yonyou.friendsandaargang.view;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.yonyou.friendsandaargang.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sendtion on 2016/6/24.
 * <p>
 * 编辑富文本
 */
@SuppressLint({"NewApi", "InflateParams"})
public class RichTextEditor extends ScrollView {
    private Activity activity;
    private static final int EDIT_PADDING = 10; // edittext常规padding是10dp
    //private static final int EDIT_FIRST_PADDING_TOP = 10; // 第一个EditText的paddingTop值
    private int viewTagIndex = 1; // 新生的view都会打一个tag，对每个view来说，这个tag是唯一的。
    private LinearLayout allLayout; // 这个是所有子view的容器，scrollView内部的唯一一个ViewGroup
    private LayoutInflater inflater;
    private OnKeyListener keyListener; // 所有EditText的软键盘监听器
    private OnClickListener btnListener; // 图片右上角红叉按钮监听器
    private OnFocusChangeListener focusListener; // 所有EditText的焦点监听listener
    private EditText lastFocusEdit; // 最近被聚焦的EditText
    private LayoutTransition mTransitioner; // 只在图片View添加或remove时，触发transition动画
    private int editNormalPadding = 0; //
    private int disappearingImageIndex = 0;
    private Bitmap bmp;
    private ArrayList<String> imagePaths;//图片地址集合

    //删除图片的接口
    private OnDeleteImageListener onDeleteImageListener;

    public RichTextEditor(Context context) {
        this(context, null);
    }

    public RichTextEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        activity = (Activity) context;
        //onDeleteImageListener = (OnDeleteImageListener) context;
        //this.onDeleteImageListener = onDeleteImageListener;

        imagePaths = new ArrayList<>();

        inflater = LayoutInflater.from(context);

        // 1. 初始化allLayout
        allLayout = new LinearLayout(context);
        allLayout.setOrientation(LinearLayout.VERTICAL);
        //allLayout.setBackgroundColor(Color.WHITE);
        setupLayoutTransitions();//禁止载入动画
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        allLayout.setPadding(0, 10, 0, 0);//设置间距，防止生成图片时文字太靠边，不能用margin，否则有黑边
        addView(allLayout, layoutParams);

        // 2. 初始化键盘退格监听
        // 主要用来处理点击回删按钮时，view的一些列合并操作
        keyListener = new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    EditText edit = (EditText) v;
                    onBackspacePress(edit);
                }
                return false;
            }
        };

        focusListener = new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lastFocusEdit = (EditText) v;
                }
            }
        };


        LinearLayout.LayoutParams firstEditParam = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //editNormalPadding = dip2px(EDIT_PADDING);
        EditText firstEdit = createEditText("请填写内容", dip2px(context, EDIT_PADDING));

        allLayout.addView(firstEdit, firstEditParam);
        lastFocusEdit = firstEdit;
    }


    private int dip2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    /**
     * 处理软键盘backSpace回退事件
     *
     * @param editTxt 光标所在的文本输入框
     */
    private void onBackspacePress(EditText editTxt) {
        int startSelection = editTxt.getSelectionStart();
        // 只有在光标已经顶到文本输入框的最前方，在判定是否删除之前的图片，或两个View合并
        if (startSelection == 0) {
            int editIndex = allLayout.indexOfChild(editTxt);
            View preView = allLayout.getChildAt(editIndex - 1); // 如果editIndex-1<0,
            // 则返回的是null
            if (null != preView) {
                if (preView instanceof RelativeLayout) {
                    // 光标EditText的上一个view对应的是图片
                    onImageCloseClick(preView);
                } else if (preView instanceof EditText) {
                    // 光标EditText的上一个view对应的还是文本框EditText
                    String str1 = editTxt.getText().toString();
                    EditText preEdit = (EditText) preView;
                    String str2 = preEdit.getText().toString();

                    // 合并文本view时，不需要transition动画
                    //allLayout.setLayoutTransition(null);
                    allLayout.removeView(editTxt);
                    allLayout.setLayoutTransition(mTransitioner); // 恢复transition动画

                    // 文本合并
                    preEdit.setText(str2 + str1);
                    preEdit.requestFocus();
                    preEdit.setSelection(str2.length(), str2.length());
                    lastFocusEdit = preEdit;
                }
            }
        }
    }

    public interface OnDeleteImageListener {
        void onDeleteImage(String imagePath);
    }

    /**
     * 处理图片叉掉的点击事件
     *
     * @param view 整个image对应的relativeLayout view
     * @type 删除类型 0代表backspace删除 1代表按红叉按钮删除
     */
    private void onImageCloseClick(View view) {
        //if (!mTransitioner.isRunning()) {
        disappearingImageIndex = allLayout.indexOfChild(view);
        //删除文件夹里的图片
        List<EditData> dataList = buildEditData();
        EditData editData = dataList.get(disappearingImageIndex);
        //Log.i("", "###editData: "+editData);
        if (editData.imagePath != null) {
            if (onDeleteImageListener != null) {
                //TODO 通过接口回调，在笔记编辑界面处理图片的删除操作
                onDeleteImageListener.onDeleteImage(editData.imagePath);
            }
            //SDCardUtil.deleteFile(editData.imagePath);
            imagePaths.remove(editData.imagePath);
        }
        allLayout.removeView(view);
        mergeEditText();//合并上下EditText内容
        //}
    }

    /**
     * 处理图片点击事件
     *
     * @param view
     */
    private void onImageClick(View view) {
        int currentItem = 0;
        disappearingImageIndex = allLayout.indexOfChild(view);
        //根据点击的view所在位置获取到图片地址
        List<EditData> dataList = buildEditData();
        EditData editData = dataList.get(disappearingImageIndex);
        //Log.i("", "###editData: "+editData);
        if (editData.imagePath != null) {
            currentItem = imagePaths.indexOf(editData.imagePath);
        }


	/*	//点击图片预览
        PhotoPreview.builder()
				.setPhotos(imagePaths)
				.setCurrentItem(currentItem)
				.setShowDeleteButton(false)
				.start(activity);*/
    }

    /**
     * 清空所有布局
     */
    public void clearAllLayout() {
        allLayout.removeAllViews();
    }

    /**
     * 获取索引位置
     *
     * @return
     */
    public int getLastIndex() {
        int lastEditIndex = allLayout.getChildCount();
        return lastEditIndex;
    }

    public EditText createEditText(String hint, int paddingTop) {
        EditText editText = (EditText) inflater.inflate(R.layout.rich_edittext, null);
        editText.setOnKeyListener(keyListener);
        editText.setTag(viewTagIndex++);
        editText.setPadding(editNormalPadding, paddingTop, editNormalPadding, paddingTop);
        editText.setHint(hint);
        editText.setOnFocusChangeListener(focusListener);
        return editText;
    }

    /**
     * 生成图片View
     */
    private RelativeLayout createImageLayout() {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.edit_imageview, null);
        layout.setTag(viewTagIndex++);
        View closeView = layout.findViewById(R.id.image_close);
        //closeView.setVisibility(GONE);
        closeView.setTag(layout.getTag());
        closeView.setOnClickListener(btnListener);
        View imageView = layout.findViewById(R.id.edit_imageView);
        imageView.setOnClickListener(btnListener);
        return layout;
    }

    /**
     * 根据绝对路径添加view
     *
     * @param imagePath
     */
    public void insertImage(String imagePath, int width) {
        Bitmap bmp = getScaledBitmap(imagePath, width);
        insertImage(bmp, imagePath);
    }

    /**
     * 插入一张图片
     */
    public void insertImage(Bitmap bitmap, String imagePath) {
        //lastFocusEdit获取焦点的EditText
        String lastEditStr = lastFocusEdit.getText().toString();
        int cursorIndex = lastFocusEdit.getSelectionStart();//获取光标所在位置
        String editStr1 = lastEditStr.substring(0, cursorIndex).trim();//获取光标前面的字符串
        String editStr2 = lastEditStr.substring(cursorIndex).trim();//获取光标后的字符串
        int lastEditIndex = allLayout.indexOfChild(lastFocusEdit);//获取焦点的EditText所在位置

        if (lastEditStr.length() == 0) {
            //如果当前获取焦点的EditText为空，直接在EditText下方插入图片，并且插入空的EditText
            addEditTextAtIndex(lastEditIndex + 1, "");
            addImageViewAtIndex(lastEditIndex + 1, bitmap, imagePath);
        } else if (editStr1.length() == 0) {
            //如果光标已经顶在了editText的最前面，则直接插入图片，并且EditText下移即可
            addImageViewAtIndex(lastEditIndex, bitmap, imagePath);
            //同时插入一个空的EditText，防止插入多张图片无法写文字
            addEditTextAtIndex(lastEditIndex + 1, "");
        } else if (editStr2.length() == 0) {
            // 如果光标已经顶在了editText的最末端，则需要添加新的imageView和EditText
            addEditTextAtIndex(lastEditIndex + 1, "");
            addImageViewAtIndex(lastEditIndex + 1, bitmap, imagePath);
        } else {
            //如果光标已经顶在了editText的最中间，则需要分割字符串，分割成两个EditText，并在两个EditText中间插入图片
            //把光标前面的字符串保留，设置给当前获得焦点的EditText（此为分割出来的第一个EditText）
            lastFocusEdit.setText(editStr1);
            //把光标后面的字符串放在新创建的EditText中（此为分割出来的第二个EditText）
            addEditTextAtIndex(lastEditIndex + 1, editStr2);
            //在第二个EditText的位置插入一个空的EditText，以便连续插入多张图片时，有空间写文字，第二个EditText下移
            addEditTextAtIndex(lastEditIndex + 1, "");
            //在空的EditText的位置插入图片布局，空的EditText下移
            addImageViewAtIndex(lastEditIndex + 1, bitmap, imagePath);
        }
        hideKeyBoard();
    }

    /**
     * 隐藏小键盘
     */
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(lastFocusEdit.getWindowToken(), 0);
    }

    /**
     * 在特定位置插入EditText
     *
     * @param index   位置
     * @param editStr EditText显示的文字
     */
    public void addEditTextAtIndex(final int index, CharSequence editStr) {
        EditText editText2 = createEditText("", 0);
        //判断插入的字符串是否为空，如果没有内容则显示hint提示信息
        if (editStr != null && editStr.length() > 0) {
            editText2.setText(editStr);
        }
        editText2.setOnFocusChangeListener(focusListener);

        // 请注意此处，EditText添加、或删除不触动Transition动画
        allLayout.setLayoutTransition(null);
        allLayout.addView(editText2, index);
        //allLayout.setLayoutTransition(mTransitioner); // remove之后恢复transition动画
        //插入新的EditText之后，修改lastFocusEdit的指向
        lastFocusEdit = editText2;
        lastFocusEdit.requestFocus();
        lastFocusEdit.setSelection(editStr.length(), editStr.length());
    }

    /**
     * 在特定位置添加ImageView
     */
    public void addImageViewAtIndex(final int index, Bitmap bmp, String imagePath) {
        imagePaths.add(imagePath);
        RelativeLayout imageLayout = createImageLayout();
        DataImageView imageView = (DataImageView) imageLayout.findViewById(R.id.edit_imageView);
        Glide.with(getContext()).load(imagePath).crossFade().centerCrop().into(imageView);
        imageView.setAbsolutePath(imagePath);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//裁剪剧中

        // 调整imageView的高度，根据宽度等比获得高度
        //int imageHeight = allLayout.getWidth() * bmp.getHeight() / bmp.getWidth();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 600);//TODO 固定图片高度500，考虑自定义属性
        lp.bottomMargin = 5;
        imageView.setLayoutParams(lp);

        // onActivityResult无法触发动画，此处post处理
        allLayout.addView(imageLayout, index);
//		allLayout.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				allLayout.addView(imageLayout, index);
//			}
//		}, 200);
    }

    /**
     * 在特定位置添加ImageView
     */
    public void addImageViewAtIndex(final int index, final String imagePath) {
        imagePaths.add(imagePath);
        RelativeLayout imageLayout = createImageLayout();
        final DataImageView imageView = (DataImageView) imageLayout.findViewById(R.id.edit_imageView);
        imageView.setAbsolutePath(imagePath);

        //如果是网络图片
        if (imagePath.startsWith("https")) {
            Glide.with(getContext()).load(imagePath).asBitmap().dontAnimate()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            // 调整imageView的高度，根据宽度等比获得高度
                            //int imageHeight = allLayout.getWidth() * resource.getHeight() / resource.getWidth();
                            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                    LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//固定图片高度，记得设置裁剪剧中
                            lp.bottomMargin = 5;
                            imageView.setLayoutParams(lp);
                            Glide.with(getContext()).load(imagePath).crossFade().centerCrop()
                                    .placeholder(R.drawable.img_load_fail).error(R.drawable.img_load_fail)
                                    .override(Target.SIZE_ORIGINAL, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .into(imageView);
                        }
                    });
        } else { //如果是本地图片
            Bitmap bmp = BitmapFactory.decodeFile(imagePath);
            // 调整imageView的高度，根据宽度等比获得高度
            int imageHeight = allLayout.getWidth() * bmp.getHeight() / bmp.getWidth();
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, 600);//固定图片高度，记得设置裁剪剧中
            lp.bottomMargin = 5;
            imageView.setLayoutParams(lp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getContext()).load(imagePath)
                    .crossFade()
                    .centerCrop()
                    .placeholder(R.drawable.img_load_fail)
                    .error(R.drawable.img_load_fail)
                    .into(imageView);
        }

        // onActivityResult无法触发动画，此处post处理
        allLayout.addView(imageLayout, index);

    }

    /**
     * 根据view的宽度，动态缩放bitmap尺寸
     *
     * @param width view的宽度
     */
    public Bitmap getScaledBitmap(String filePath, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int sampleSize = options.outWidth > width ? options.outWidth / width
                + 1 : 1;
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 初始化transition动画
     */
    private void setupLayoutTransitions() {
        mTransitioner = new LayoutTransition();
        //allLayout.setLayoutTransition(mTransitioner);
//		mTransitioner.addTransitionListener(new TransitionListener() {
//
//			@Override
//			public void startTransition(LayoutTransition transition,
//					ViewGroup container, View view, int transitionType) {
//
//			}
//
//			@Override
//			public void endTransition(LayoutTransition transition,
//					ViewGroup container, View view, int transitionType) {
//				if (!transition.isRunning()
//						&& transitionType == LayoutTransition.CHANGE_DISAPPEARING) {
//					// transition动画结束，合并EditText
//					// mergeEditText();
//				}
//			}
//		});
//		mTransitioner.setDuration(300);
    }

    /**
     * 图片删除的时候，如果上下方都是EditText，则合并处理
     */
    private void mergeEditText() {
        View preView = allLayout.getChildAt(disappearingImageIndex - 1);
        View nextView = allLayout.getChildAt(disappearingImageIndex);
        if (preView != null && preView instanceof EditText && null != nextView
                && nextView instanceof EditText) {
            Log.d("LeiTest", "合并EditText");
            EditText preEdit = (EditText) preView;
            EditText nextEdit = (EditText) nextView;
            String str1 = preEdit.getText().toString();
            String str2 = nextEdit.getText().toString();
            String mergeText = "";
            if (str2.length() > 0) {
                mergeText = str1 + "\n" + str2;
            } else {
                mergeText = str1;
            }

            allLayout.setLayoutTransition(null);
            allLayout.removeView(nextEdit);
            preEdit.setText(mergeText);
            preEdit.requestFocus();
            preEdit.setSelection(str1.length(), str1.length());
            allLayout.setLayoutTransition(mTransitioner);
        }
    }

    /**
     * 对外提供的接口, 生成编辑数据上传
     */
    public List<EditData> buildEditData() {
        List<EditData> dataList = new ArrayList<EditData>();
        int num = allLayout.getChildCount();
        for (int index = 0; index < num; index++) {
            View itemView = allLayout.getChildAt(index);
            EditData itemData = new EditData();
            if (itemView instanceof EditText) {
                EditText item = (EditText) itemView;
                itemData.inputStr = item.getText().toString();
            } else if (itemView instanceof RelativeLayout) {
                DataImageView item = (DataImageView) itemView.findViewById(R.id.edit_imageView);
                itemData.imagePath = item.getAbsolutePath();
                //itemData.bitmap = item.getBitmap();//去掉这个防止bitmap一直被占用，导致内存溢出
            }
            dataList.add(itemData);
        }

        return dataList;
    }

    public class EditData {
        public String inputStr;
        public String imagePath;
        public Bitmap bitmap;
        public String name;
    }


}