package com.yonyou.friendsandaargang.guide.fragemnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.friendsandaargang.R;
import com.yonyou.friendsandaargang.base.ActivityManger;
import com.yonyou.friendsandaargang.base.Constants;
import com.yonyou.friendsandaargang.base.GlideManager;
import com.yonyou.friendsandaargang.guide.bean.UserInfo;
import com.yonyou.friendsandaargang.info.activity.seeting.AuthenticationActivity;
import com.yonyou.friendsandaargang.info.activity.user.CollectionActivity;
import com.yonyou.friendsandaargang.info.activity.DatabaseActivity;
import com.yonyou.friendsandaargang.info.activity.user.DraftActivity;
import com.yonyou.friendsandaargang.info.activity.user.FansActivity;
import com.yonyou.friendsandaargang.info.activity.user.FollowActivity;
import com.yonyou.friendsandaargang.info.activity.message.MessageActivity;
import com.yonyou.friendsandaargang.info.activity.user.MyQAActivity;
import com.yonyou.friendsandaargang.info.activity.user.PublishActivity;
import com.yonyou.friendsandaargang.info.activity.user.ReplyActivity;
import com.yonyou.friendsandaargang.info.activity.seeting.SeetingActivity;
import com.yonyou.friendsandaargang.info.activity.userinfo.UserInfoActivity;
import com.yonyou.friendsandaargang.info.activity.gold.WalletActivity;
import com.yonyou.friendsandaargang.info.activity.operationmanager.OperationManagerActivity;
import com.yonyou.friendsandaargang.info.bean.Sign;
import com.yonyou.friendsandaargang.login.activity.ActivityLogin;
import com.yonyou.friendsandaargang.network.ApiClient;
import com.yonyou.friendsandaargang.network.ApiService;
import com.yonyou.friendsandaargang.network.HttpCallBackImpl;
import com.yonyou.friendsandaargang.network.HttpResult;
import com.yonyou.friendsandaargang.network.NetRetrofitCallback;
import com.yonyou.friendsandaargang.network.RetrofitCallback;
import com.yonyou.friendsandaargang.utils.SPTool;
import com.yonyou.friendsandaargang.utils.ToastUtils;
import com.yonyou.friendsandaargang.view.dialog.DialogSureCancel;
import com.yonyou.friendsandaargang.view.dialog.SignDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by shibing on 18/2/7.
 */

public class InfoFragemnt extends Fragment implements View.OnClickListener {
    private View view;
    //是否认证
    @BindView(R.id.auto_text)
    TextView auto_text;
    //个性签名
    @BindView(R.id.explain_text)
    TextView explain_text;
    //等级
    //有没有登陆
    @BindView(R.id.loging_text)
    TextView loging_text;
    //头像
    @BindView(R.id.user_img)
    CircleImageView user_img;
    //等级
    @BindView(R.id.detalis_hg_text)
    TextView detalis_hg_text;
    @BindView(R.id.info_hg_fay)
    FrameLayout info_hg_fay;
    @BindView(R.id.info_mess_coun)
    TextView info_mess_coun;
    @BindView(R.id.user_rz_iamge)
    ImageView iamge_rz;

    // 用户id
    private String UserId;  //用户id
    private String viewerId;//浏览者id
    private SignDialog signDialog;
    //是否登陆
    private boolean isLogin;
    private UserInfo userInfo;
    private DialogSureCancel dialogSureCancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initviews();
        //登录后在请求个人信息接口
        if (isLogin) {
            getUserInfo();
        }
    }

    private void initviews() {
        UserId = SPTool.getContent(getActivity(), Constants.USER_ID);
        isLogin = SPTool.getBoolean(getActivity(), Constants.ISLOGIN);
        if (!isLogin) {
            /**
             * 没有登陆就隐藏 等级与个性签名
             */
            info_hg_fay.setVisibility(View.GONE);
            user_img.setImageResource(R.drawable.user);
            explain_text.setText("签名是一种态度！");
            loging_text.setText("登录/注册");
            auto_text.setText("签到");
            iamge_rz.setVisibility(View.GONE);
            info_mess_coun.setVisibility(View.GONE);  //没有登录 显示消息隐藏
            return;
        }
    }

    /**
     * 监听事件
     *
     * @param v
     */
    @OnClick({R.id.loging_text, R.id.user_img, R.id.message_lay, R.id.setting_lay, R.id.wdqb_lay
            , R.id.gz_lay, R.id.sc_lay, R.id.fs_lay
            , R.id.fb_lay, R.id.hf_lay, R.id.cg_lay, R.id.wd_lay
            , R.id.zlk_lay, R.id.yygj_lay
            , R.id.auto_text
            , R.id.explain_text})
    public void onClick(View v) {
        if (!isLogin || userInfo == null) {
            ActivityManger.skipActivity(getActivity(), ActivityLogin.class);
            return;
        }
        Intent intent = null;
        String identityType = userInfo.getIdentityType();
        switch (v.getId()) {
            //注册登陆   //个人信息
            case R.id.loging_text:
            case R.id.user_img:
            case R.id.explain_text:
                //没有登陆就跳转登陆
                intent = new Intent(getActivity(), UserInfoActivity.class);
                intent.putExtra(Constants.USER_NAME, userInfo.getUserName());
                intent.putExtra(Constants.HEADIMAGE, userInfo.getAvatar());
                intent.putExtra(Constants.NIKE_NAME, userInfo.getNickname());
                intent.putExtra(Constants.GENDER, userInfo.getGenderDesc());
                intent.putExtra(Constants.BIRTHDAY, userInfo.getBirthday());
                intent.putExtra(Constants.REGINON, userInfo.getRegionDesc());
                intent.putExtra(Constants.AUTOGRAPH, userInfo.getSignature());
                intent.putExtra(Constants.AUTHDESC, userInfo.getRealNameAuthDesc());
                startActivity(intent);
                break;
            //消息
            case R.id.message_lay:
                ActivityManger.skipActivity(getActivity(), MessageActivity.class);
                break;
            //设置
            case R.id.setting_lay:
                ActivityManger.skipActivity(getActivity(), SeetingActivity.class);
                break;
            //我的钱包
            case R.id.wdqb_lay:
                ActivityManger.skipActivity(getActivity(), WalletActivity.class);
                break;
            //我的关注
            case R.id.gz_lay:
                ActivityManger.skipActivity(getActivity(), FollowActivity.class);
                break;
            //我的收藏
            case R.id.sc_lay:
                ActivityManger.skipActivity(getActivity(), CollectionActivity.class);
                break;
            //我的粉丝
            case R.id.fs_lay:
                ActivityManger.skipActivity(getActivity(), FansActivity.class);
                break;
            //我的发表
            case R.id.fb_lay:
                ActivityManger.skipActivity(getActivity(), PublishActivity.class);
                break;
            //我的回复
            case R.id.hf_lay:
                ActivityManger.skipActivity(getActivity(), ReplyActivity.class);
                break;
            //我的草稿
            case R.id.cg_lay:
                ActivityManger.skipActivity(getActivity(), DraftActivity.class);
                break;
            //我的问答
            case R.id.wd_lay:
                intent = new Intent(getActivity(), MyQAActivity.class);
                intent.putExtra("identityType", userInfo.getIdentityType());
                intent.putExtra("identityInfo", userInfo.getIdentityInfo());
                startActivity(intent);
                break;
            //我的资料库
            case R.id.zlk_lay:
                //当前的认证身份类型 0无认证 10211001个人 10211002大咖 10211003经销商
                if ("10211003".equals(identityType)) {
                    ActivityManger.skipActivity(getActivity(), DatabaseActivity.class);
                } else {
                    showAuthDialog();
                }
                break;
            //签到
            case R.id.auto_text:
                getUserSign();
                break;
            //运营管家
            case R.id.yygj_lay:
                if ("10211003".equals(identityType)) {
                    ActivityManger.skipActivity(getActivity(), OperationManagerActivity.class);
                } else {
                    showAuthDialog();
                }
                break;
        }
    }


    private void showAuthDialog() {
        dialogSureCancel = new DialogSureCancel(getActivity());
        dialogSureCancel.setCanceledOnTouchOutside(false);
        dialogSureCancel.setTitle("提示");
        dialogSureCancel.setContent("认证用友经销商才可使用该功能,如需认证点击确认即可");
        dialogSureCancel.setSureListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManger.skipActivity(getActivity(), AuthenticationActivity.class);
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.dismiss();
            }
        });
        dialogSureCancel.show();
    }


    /**
     * 获取用户个人信息
     */

    private void getUserInfo() {
        ApiService communityService = ApiClient.retrofit().create(ApiService.class);
        Call<HttpResult<UserInfo>> call = communityService.getUserInfo(UserId, viewerId);
        call.enqueue(new NetRetrofitCallback<HttpResult<UserInfo>>(getActivity(), new HttpCallBackImpl<HttpResult<UserInfo>>() {
            @Override
            public void onResponseCallback(HttpResult<UserInfo> model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg());
                    return;
                }
                getInfoData(model);

            }
        }));
    }

    /**
     * 用户个人信息
     *
     * @param model
     */
    private void getInfoData(HttpResult<UserInfo> model) {

        //登陆注册隐藏
        userInfo = model.getContent();
        info_hg_fay.setVisibility(View.VISIBLE);
        SPTool.putContent(getActivity(), Constants.IDENTITYTYPE, userInfo.getIdentityType());
        GlideManager.loadImage(getActivity(), model.getContent().getAvatar(), R.drawable.user, user_img);
        loging_text.setText(model.getContent().getNickname());
        detalis_hg_text.setText(model.getContent().getLevel());
        auto_text.setText("签到" + model.getContent().getSignTimes() + "天");
        if (model.getContent().getSignature() != null) {
            explain_text.setText(model.getContent().getSignature());
        } else {
            explain_text.setText("请设置个性签名");

        }
        if (model.getContent().getUnreadCount() == 0) {
            info_mess_coun.setVisibility(View.GONE);
        } else {
            info_mess_coun.setVisibility(View.VISIBLE);
            info_mess_coun.setText(model.getContent().getUnreadCount() + "");
        }

        if (model.getContent().getRealNameAuth() == 10081002) {
            iamge_rz.setVisibility(View.VISIBLE);
        } else {
            iamge_rz.setVisibility(View.GONE);
        }

    }


    /**
     * 签到接口
     */
    private void getUserSign() {
        ApiService communityService = ApiClient.retrofit().create(ApiService.class);
        Call<HttpResult<Sign>> call = communityService.getUserSignIn(UserId);
        call.enqueue(new RetrofitCallback<HttpResult<Sign>>() {
            @Override
            public void onSuccess(HttpResult<Sign> model) {
                if (model.getReturnCode() != 0 && !model.getReturnMsg().equals("success")) {
                    ToastUtils.normal(model.getReturnMsg());
                    return;
                }
                showSignDialog(model);

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


    /**
     * 用户签到  dialog
     */
    private void showSignDialog(HttpResult<Sign> modle) {
        if (modle.getContent().getResult().equals("1")) {
            ToastUtils.normal("您今天已签到,明天在来哦");
            return;
        }
        signDialog = new SignDialog(getActivity());
        signDialog.setCancelable(false);
        signDialog.getSign_day().setText("已签到" + modle.getContent().getTimes() + "天");
        switch (modle.getContent().getTimes()) {
            case 1:
                signDialog.getSign_cg_1().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_1().setBackgroundResource(R.color.color_gray3);
                break;
            case 2:
                signDialog.getSign_cg_1().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_1().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_2().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_2().setBackgroundResource(R.color.color_gray3);
                break;
            case 3:
                signDialog.getSign_cg_1().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_1().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_2().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_2().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_3().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_3().setBackgroundResource(R.color.color_gray3);

                break;
            case 4:
                signDialog.getSign_cg_1().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_1().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_2().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_2().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_3().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_3().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_4().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_4().setBackgroundResource(R.color.color_gray3);
                break;
            case 5:
                signDialog.getSign_cg_1().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_1().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_2().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_2().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_3().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_3().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_4().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_4().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_5().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_5().setBackgroundResource(R.color.color_gray3);
                break;
            case 6:
                signDialog.getSign_cg_1().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_1().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_2().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_2().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_3().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_3().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_4().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_4().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_5().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_5().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_6().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_6().setBackgroundResource(R.color.color_gray3);
                break;
            case 7:
                signDialog.getSign_cg_1().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_1().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_2().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_2().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_3().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_3().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_4().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_4().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_5().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_5().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_6().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_6().setBackgroundResource(R.color.color_gray3);
                signDialog.getSign_cg_7().setVisibility(View.VISIBLE);
                signDialog.getSign_ray_7().setBackgroundResource(R.color.color_gray3);
                break;
        }

        signDialog.getSign_exp().setText("经验值＋" + modle.getContent().getExp() + "");
        signDialog.getSign_fortune().setText("财富值＋" + modle.getContent().getFortune() + "");
        signDialog.setExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
                signDialog.dismiss();
            }
        });
        signDialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (signDialog != null) {
            signDialog.dismiss();
        }
    }
}
