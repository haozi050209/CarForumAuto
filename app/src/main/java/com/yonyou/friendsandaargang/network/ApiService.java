package com.yonyou.friendsandaargang.network;

import com.yonyou.friendsandaargang.forum.bean.EssenceList;
import com.yonyou.friendsandaargang.forum.bean.Follow;
import com.yonyou.friendsandaargang.forum.bean.FollowForum;
import com.yonyou.friendsandaargang.forum.bean.ForumDetalis;
import com.yonyou.friendsandaargang.forum.bean.ForumState;
import com.yonyou.friendsandaargang.forum.bean.FullReply;
import com.yonyou.friendsandaargang.forum.bean.HuaTi;
import com.yonyou.friendsandaargang.forum.bean.MyForumList;
import com.yonyou.friendsandaargang.forum.bean.ReplyList;
import com.yonyou.friendsandaargang.guide.bean.BucketList;
import com.yonyou.friendsandaargang.guide.bean.Roll;
import com.yonyou.friendsandaargang.guide.bean.UserInfo;
import com.yonyou.friendsandaargang.guide.bean.VersionCodeBean;
import com.yonyou.friendsandaargang.homepage.modle.AllBrand;
import com.yonyou.friendsandaargang.homepage.modle.AllForums;
import com.yonyou.friendsandaargang.homepage.modle.AllSearch;
import com.yonyou.friendsandaargang.homepage.modle.AnswerListBean;
import com.yonyou.friendsandaargang.homepage.modle.BannerBean;
import com.yonyou.friendsandaargang.homepage.modle.BigForumBean;
import com.yonyou.friendsandaargang.homepage.modle.BigShotInfoBean;
import com.yonyou.friendsandaargang.homepage.modle.BigshotAnwserListBean;
import com.yonyou.friendsandaargang.homepage.modle.FollowPople;
import com.yonyou.friendsandaargang.homepage.modle.HotListBean;
import com.yonyou.friendsandaargang.homepage.modle.HotSearch;
import com.yonyou.friendsandaargang.homepage.modle.MavinListBean;
import com.yonyou.friendsandaargang.homepage.modle.QAForumListBean;
import com.yonyou.friendsandaargang.homepage.modle.QAReplyLisBean;
import com.yonyou.friendsandaargang.homepage.modle.QaDetalisBean;
import com.yonyou.friendsandaargang.homepage.modle.QaMainPageBean;
import com.yonyou.friendsandaargang.homepage.modle.QuestionDetalisBean;
import com.yonyou.friendsandaargang.homepage.modle.QuestionInfoBean;
import com.yonyou.friendsandaargang.homepage.modle.QuestionListBean;
import com.yonyou.friendsandaargang.homepage.modle.SearchPostBean;
import com.yonyou.friendsandaargang.homepage.modle.SearchQa;
import com.yonyou.friendsandaargang.homepage.modle.SearchUser;
import com.yonyou.friendsandaargang.info.bean.Area;
import com.yonyou.friendsandaargang.info.bean.AuthStatus;
import com.yonyou.friendsandaargang.info.bean.City;
import com.yonyou.friendsandaargang.info.bean.CoinDetailBean;
import com.yonyou.friendsandaargang.info.bean.Country;
import com.yonyou.friendsandaargang.info.bean.CustBean;
import com.yonyou.friendsandaargang.info.bean.CustomerByStr;
import com.yonyou.friendsandaargang.info.bean.DataBankBean;
import com.yonyou.friendsandaargang.info.bean.DataBankDetailBean;
import com.yonyou.friendsandaargang.info.bean.Draft;
import com.yonyou.friendsandaargang.info.bean.ExchangeBean;
import com.yonyou.friendsandaargang.info.bean.ExchangeRecordBean;
import com.yonyou.friendsandaargang.info.bean.FAQByKeyWord;
import com.yonyou.friendsandaargang.info.bean.Fans;
import com.yonyou.friendsandaargang.info.bean.FortuneDetailBean;
import com.yonyou.friendsandaargang.info.bean.ForumMessage;
import com.yonyou.friendsandaargang.info.bean.IdCardBean;
import com.yonyou.friendsandaargang.info.bean.JobList;
import com.yonyou.friendsandaargang.info.bean.MessAge;
import com.yonyou.friendsandaargang.info.bean.MessagePush;
import com.yonyou.friendsandaargang.info.bean.MyFans;
import com.yonyou.friendsandaargang.info.bean.People;
import com.yonyou.friendsandaargang.info.bean.Province;
import com.yonyou.friendsandaargang.info.bean.QaToMeBean;
import com.yonyou.friendsandaargang.info.bean.QaUserAnswerBean;
import com.yonyou.friendsandaargang.info.bean.QaUserIfonBean;
import com.yonyou.friendsandaargang.info.bean.QaUserToMeBean;
import com.yonyou.friendsandaargang.info.bean.RecordBean;
import com.yonyou.friendsandaargang.info.bean.Sign;
import com.yonyou.friendsandaargang.info.bean.SyatemMessage;
import com.yonyou.friendsandaargang.info.bean.SystemBean;
import com.yonyou.friendsandaargang.info.bean.Tel;
import com.yonyou.friendsandaargang.info.bean.UserFortuneBean;
import com.yonyou.friendsandaargang.info.bean.UserLevel;
import com.yonyou.friendsandaargang.info.bean.UserLevelog;
import com.yonyou.friendsandaargang.login.modle.Login;
import com.yonyou.friendsandaargang.login.modle.LoginUserInfo;
import com.yonyou.friendsandaargang.login.modle.ThirdLogin;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by shibing on 18/2/27.
 */

public interface ApiService {


    //    /*测试环境   外网*/
    String BaseUrl = "https://210.13.111.26:10210/";


    //String BaseUrl = "https://youchebang.yonyouauto.com/";

    String hostUrl = "http://aliyunverifyidcard.haoservice.com/";

    /**
     * 身份证实名认证接口
     *
     * @param cardNo
     * @param realName
     * @return
     */
    @Headers("Authorization:APPCODE 927383e6583d4b6a94b71e57f6345c90")
    @GET("idcard/VerifyIdcardv2")
    Call<IdCardBean> getIdcard(@Query("cardNo") String cardNo, @Query("realName") String realName);


    /**
     * @param policyName
     * @param userId
     * @return 获取oss图片上传
     */
    @FormUrlEncoded
    @POST("getSTSAccessInfo")
    Call<BucketList> getSTSAccessInfo(@Field("policyName") String policyName, @Field("userId") String userId);


    /**
     * @param policyName
     * @param userId
     * @return 上传极光id至服务器
     */
    @FormUrlEncoded
    @POST("saveRegistrationId")
    Call<HttpResult> getsaveRegistrationId(@Field("userId") String policyName, @Field("registrationId") String userId);


    /**
     * 上传极光id  用于统计消息
     *
     * @param uuid
     * @param msgId
     * @return
     */
    @FormUrlEncoded
    @POST("updatePushTask")
    Call<HttpResult> updatePushTask(@Field("uuid") String uuid
            , @Field("msgId") String msgId);


    /**
     * 获取版本号
     *
     * @return
     */
    @POST("getVersion")
    Call<VersionCodeBean> getVersionCode();


    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("sendVerificationCode")
    Call<HttpResult> getSendCode(@Field("mobile") String mobile);

    /**
     * 验证码登陆
     */
    @FormUrlEncoded
    @POST("logonByVerifyCode")
    Call<HttpResult<LoginUserInfo>> getCodeLogin(@Field("mobile") String mobile,
                                                 @Field("verifyCode") String verifyCode,
                                                 @Field("systemType") String systemType,
                                                 @Field("registrationId") String registrationId);


    /**
     * 注册账号
     *
     * @param userName
     * @param mobile
     * @param password
     * @param verifyCode
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Call<HttpResult> getRegiSter(@Field("userName") String userName,
                                 @Field("mobile") String mobile,
                                 @Field("password") String password,
                                 @Field("verifyCode") String verifyCode);

    /**
     * 密码登录
     *
     * @param userName
     * @param mobile
     * @param password
     * @return
     */

    @FormUrlEncoded
    @POST("logonByPassword")
    Call<HttpResult<Login>> getLogin(@Field("userName") String userName,
                                     @Field("mobile") String mobile,
                                     @Field("password") String password,
                                     @Field("systemType") String systemType,
                                     @Field("registrationId") String registrationId);

    /**
     * QQ登录
     */
    @FormUrlEncoded
    @POST("logonByQQ")
    Call<HttpResult<ThirdLogin>> getQQLogin(@Field("qqOpenId") String qqOpenId,
                                            @Field("mobile") String mobile,
                                            @Field("verifyCode") String verifyCode,
                                            @Field("systemType") String systemType,
                                            @Field("registrationId") String registrationId,
                                            @Field("qqAvatar") String qqAvatar,
                                            @Field("qqNickname") String qqNickname);

    /**
     * 微信登录
     */
    @FormUrlEncoded
    @POST("logonByWechat")
    Call<HttpResult<ThirdLogin>> getWeChatLogin(@Field("wechatOpenId") String wechatOpenId,
                                                @Field("mobile") String mobile,
                                                @Field("verifyCode") String verifyCode,
                                                @Field("systemType") String systemType,
                                                @Field("registrationId") String registrationId,
                                                @Field("wechatAvatar") String wechatAvatar,
                                                @Field("wechatNickname") String wechatNickname);

    /**
     * 绑定QQ账号
     */
    @FormUrlEncoded
    @POST("bindQQAccount")
    Call<HttpResult> getBindQQ(@Field("userId") String userId, @Field("openId") String openId);

    /**
     * 绑定微信账号
     */
    @FormUrlEncoded
    @POST("bindWechatAccount")
    Call<HttpResult> getBindWX(@Field("userId") String userId, @Field("openId") String openId);

    /**
     * 解绑QQ账号
     */
    @FormUrlEncoded
    @POST("unbindQQAccount")
    Call<HttpResult> getUnBindQQ(@Field("userId") String userId);

    /**
     * 解绑微信账号
     */
    @FormUrlEncoded
    @POST("unbindWechatAccount")
    Call<HttpResult> getUnBindWX(@Field("userId") String userId);

    /**
     * 重置密码
     */
    @FormUrlEncoded
    @POST("resetPassword")
    Call<HttpResult> getresetPassword(@Field("mobile") String mobile, @Field("verifyCode") String verifyCode);


    /**
     * 退出登陆
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("logOut")
    Call<HttpResult> getlogOut(@Field("userId") String userId);


    @POST("getBanner")
    Call<BannerBean> getBanner();


    /**
     * 获取滚动条信息
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getFollowedUserTrace")
    Call<Roll> getFollowedUserTrace(@Field("userId") String userId);


    /**
     * 页面-我关注的人
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getFollowedUserTraceInDetail")
    Call<FollowPople> getFollowedUserTraceInDetail(@Field("userId") String userId,
                                                   @Field("pageNo") int pageNo,
                                                   @Field("pageSize") int pageSize);


    /**
     * 获取用户个人信息
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getUserInfo")
    Call<HttpResult<UserInfo>> getUserInfo(@Field("userId") String userId, @Field("viewerId") String viewerId);


    /**
     * 签到
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("userSignIn")
    Call<HttpResult<Sign>> getUserSignIn(@Field("userId") String userId);


    /**
     * 获取国家列表
     *
     * @return
     */
    @POST("getCountryList")
    Call<Country> getCountryList();


    /**
     * 选择省份
     *
     * @param countryId
     * @return
     */
    @FormUrlEncoded
    @POST("getProvinceList")
    Call<Province> getProvinceList(@Field("countryId") String countryId);

    /**
     * 选择城市
     *
     * @param provinceId
     * @return
     */
    @FormUrlEncoded
    @POST("getCityList")
    Call<City> getCityList(@Field("provinceId") String provinceId);


    /**
     * 选择地区
     *
     * @param cityId
     * @return
     */
    @FormUrlEncoded
    @POST("getAreaList")
    Call<Area> getAreaList(@Field("cityId") String cityId);


    /**
     * 更新用户个人信息
     *
     * @param userId
     * @param userName
     * @param gender
     * @param email
     * @param qq
     * @param mobile
     * @param birthday
     * @param region
     * @param signature
     * @param nickname
     * @param avatar
     * @return
     */
    @FormUrlEncoded
    @POST("updateUserInfo")
    Call<HttpResult> getupdateUserInfo(@Field("userId") String userId,             //id
                                       @Field("userName") String userName,         //名字
                                       @Field("password") String password,         //密码
                                       @Field("gender") int gender,                //性别编码
                                       @Field("email") String email,               //邮箱
                                       @Field("qq") String qq,                     //qq
                                       @Field("mobile") String mobile,             //电话
                                       @Field("birthday") String birthday,         //生日
                                       @Field("region") String region,             //地区
                                       @Field("signature") String signature,       //签名
                                       @Field("nickname") String nickname,         //昵称
                                       @Field("avatar") String avatar);            //头像


    /**
     * 获取关注的用户列表
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getFollowUserList")
    Call<People> getFollowUserList(@Field("userId") String userId, @Field("viewerId") String viewerId);

    /**
     * 获取关注的论坛列表
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getFollowForumList")
    Call<MyForumList> getFollowForumList(@Field("userId") String userId);


    /**
     * 获取粉丝
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getFanList")
    Call<Fans> getFanList(@Field("userId") String userId,
                          @Field("viewerId") String viewerId);


    /**
     * 获取我的收藏列表
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getFavoritePostList")
    Call<Follow> getFavoritePostList(@Field("userId") String userId,
                                     @Field("pageNo") int pageNo,
                                     @Field("pageSize") int pageSize);


    /**
     * 添加到我的收藏列表
     *
     * @param userId
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("addToFavorites")
    Call<HttpResult> getaddToFavorites(@Field("userId") String userId, @Field("postId") String postId);


    /**
     * 从我的收藏删除
     *
     * @param userId
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("deleteFromFavorites")
    Call<HttpResult> getdeleteFromFavorites(@Field("userId") String userId, @Field("postId") String postId);


    /**
     * 获取我的(指定用户的)内容
     *
     * @param userId
     * @param postType
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getUserPostList")
    Call<Follow> getUserPostList(@Field("userId") String userId,
                                 @Field("postType") String postType,
                                 @Field("pageNo") int pageNo,
                                 @Field("pageSize") int pageSize);


    @FormUrlEncoded
    @POST("getUserPostList")
    Call<Follow> getUserPostListAll(@Field("userId") String userId,
                                    @Field("postType") String postType,
                                    @Field("currentUser") String currentUser,
                                    @Field("pageNo") int pageNo,
                                    @Field("pageSize") int pageSize);


    /**
     * 获取我的回复
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getUserRepliedPostList")
    Call<Follow> getUserRepliedPostList(@Field("userId") String userId,
                                        @Field("pageNo") int pageNo,
                                        @Field("pageSize") int pageSize);


    /**
     * 获取我的草稿
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getDraftList")
    Call<Draft> getDraftList(@Field("userId") String userId,
                             @Field("pageNo") int pageNo,
                             @Field("pageSize") int pageSize);

    /**
     * 获取我的资料库列表
     */
    @FormUrlEncoded
    @POST("databank/getbanklist")
    Call<DataBankBean> getBankList(@Field("userId") String userId,
                                   @Field("pageNo") int pageNo,
                                   @Field("pageSize") int pageSize,
                                   @Field("orderBy") String orderBy,
                                   @Field("fileId") String fileId);

    /**
     * 更新资料浏览次数
     */
    @FormUrlEncoded
    @POST("databank/updateDataBankPV")
    Call<HttpResult> getupdateDataBankPV(@Field("dataId") int dataId);

    /**
     * 获取资料明细
     */
    @FormUrlEncoded
    @POST("databank/getDataBankDetail")
    Call<HttpResult<DataBankDetailBean>> getDataBankDetail(@Field("dataId") int dataId);

    /**
     * 删除帖子/话题/草稿
     *
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("deleteThePost")
    Call<HttpResult> getdeleteThePost(@Field("postId") String postId);


    /**
     * 获取热门话题
     *
     * @return
     */
    @POST("getHotTopicList")
    Call<HuaTi> getHotTopicList();


    /**
     * 获取精华帖列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getTheBestPostList")
    Call<EssenceList> getTheBestPostList(@Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 获取所有关注的论坛的帖子列表
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getUserPostListOfAllFollowedForums")
    Call<Follow> getUserPostListOfAllFollowedForums(@Field("userId") String userId, @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 19.获取论坛关注状态
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getForumFollowStatusList")
    Call<ForumState> getForumFollowStatusList(@Field("userId") String userId);


    /**
     * 取消关注论坛
     *
     * @param userId
     * @param forumId
     * @return
     */
    @FormUrlEncoded
    @POST("unFollowForum")
    Call<HttpResult> getunFollowForum(@Field("userId") String userId, @Field("forumId") String forumId);


    /**
     * 取消关注用户
     *
     * @param userId
     * @param followId
     * @return
     */
    @FormUrlEncoded
    @POST("unFollowUser")
    Call<HttpResult> getunFollowUser(@Field("userId") String userId, @Field("followId") String followId);

    /**
     * 20.获取论坛信息
     *
     * @param userId
     * @param forumId
     * @return
     */
    @FormUrlEncoded
    @POST("getForumInfoWithUserBrand")
    Call<FollowForum> getForumInfoWithUserBrand(@Field("userId") String userId,
                                                @Field("forumId") String forumId);


    /**
     * 21.获取论坛推荐内容
     *
     * @param forumId
     * @return
     */
    @FormUrlEncoded
    @POST("getRecommendedPostList")
    Call<Follow> getRecommendedPostList(@Field("forumId") String forumId,
                                        @Field("pageNo") int pageNo,
                                        @Field("pageSize") int pageSize);


    /**
     * 获取论坛中指定品牌的帖子列表
     *
     * @param forumId
     * @param brandId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getPostListByForumAndBrand")
    Call<Follow> getPostListByForumAndBrand(@Field("forumId") String forumId,
                                            @Field("brandId") String brandId,
                                            @Field("pageNo") int pageNo,
                                            @Field("pageSize") int pageSize);


    /**
     * 修改手机号码前，先验证原号码。
     *
     * @param mobile
     * @param verifyCode
     * @return
     */
    @FormUrlEncoded
    @POST("checkVeryfyCode")
    Call<HttpResult> getcheckVeryfyCode(@Field("mobile") String mobile,
                                        @Field("verifyCode") String verifyCode);


    /**
     * 修改手机号码
     *
     * @param userId
     * @param mobile
     * @param verifyCode
     * @return
     */
    @FormUrlEncoded
    @POST("updateUserPhoneNumber")
    Call<HttpResult> getupdateUserPhoneNumber(@Field("userId") String userId,
                                              @Field("mobile") String mobile,
                                              @Field("verifyCode") String verifyCode);


    /**
     * 25.修改用户登录密码
     *
     * @param userId
     * @param password
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST("updateUserPassword")
    Call<HttpResult> getupdateUserPassword(@Field("userId") String userId,
                                           @Field("password") String password,
                                           @Field("newPassword") String newPassword);


    /**
     * 设置/修改用户支付密码
     *
     * @param userId
     * @param password
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST("updateUserPayPassword")
    Call<HttpResult> getupdateUserPayPassword(@Field("userId") String userId,
                                              @Field("payPassword") String password,
                                              @Field("newPayPassword") String newPassword);


    /**
     * 提交意见
     *
     * @param userId
     * @param closeReason
     * @return
     */
    @FormUrlEncoded
    @POST("saveUserAdvice")
    Call<HttpResult> getsaveUserAdvice(@Field("userId") String userId,
                                       @Field("advice") String closeReason);


    /**
     * 客服电话
     *
     * @return
     */
    @POST("getCustServTel")
    Call<Tel> getCustServTel();


    /**
     * 搜索帮助中心问题
     *
     * @param keyword
     * @return
     */
    @FormUrlEncoded
    @POST("getFAQByKeyWord")
    Call<FAQByKeyWord> getFAQByKeyWord(@Field("keyword") String keyword);


    /**
     * 注销账户
     *
     * @param userId
     * @param closeReason
     * @return
     */
    @FormUrlEncoded
    @POST("closeAccount")
    Call<HttpResult> getCloseAccount(@Field("userId") String userId,
                                     @Field("closeReason") String closeReason);


    /**
     * 获取用户等级
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("level/getUserLevel")
    Call<UserLevel> getlevelgetUserLevel(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("level/getChangeLog")
    Call<UserLevelog> getChangeLog(@Field("userId") String userId,
                                   @Field("pageNo") int pageNo,
                                   @Field("pageSize") int pageSize);


    /**
     * 获取所有的消息列表
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("message/getAllMessageByUserId")
    Call<MessAge> getAllMessageByUserId(@Field("userId") String userId);


    /**
     * 获取系统消息列表
     *
     * @param userId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("message/getSystemMessageByUserId")
    Call<SyatemMessage> getSystemMessageByUserId(@Field("userId") String userId,
                                                 @Field("pageNo") int pageNo,
                                                 @Field("pageSize") int pageSize);


    /**
     * 获取我的钱包消息列表
     *
     * @param userId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("message/getWalletMessageByUserId")
    Call<SyatemMessage> getWalletMessageByUserId(@Field("userId") String userId,
                                                 @Field("pageNo") int pageNo,
                                                 @Field("pageSize") int pageSize);


    /**
     * 获取我的粉丝消息列表
     *
     * @param userId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("message/getFollowerMessageByUserId")
    Call<MyFans> getFollowerMessageByUserId(@Field("userId") String userId,
                                            @Field("pageNo") int pageNo,
                                            @Field("pageSize") int pageSize);


    /**
     * 获取我的论坛消息列表
     *
     * @param userId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("message/getForumMessageByUserId")
    Call<ForumMessage> getForumMessageByUserId(@Field("userId") String userId,
                                               @Field("pageNo") int pageNo,
                                               @Field("pageSize") int pageSize);


    /**
     * 交易消息
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("message/getDealMessageByUserId")
    Call<ForumMessage> getDealMessageByUserId(@Field("userId") String userId,
                                              @Field("pageNo") int pageNo,
                                              @Field("pageSize") int pageSize);


    /**
     * 获取问答消息列表
     *
     * @param userId
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("message/getQAMessageByUserId")
    Call<ForumMessage> getQAMessageByUserId(@Field("userId") String userId,
                                            @Field("pageNo") int pageNo,
                                            @Field("pageSize") int pageSize);


    /**
     * 获取首页帖子列表
     */
    @FormUrlEncoded
    @POST("getPostListOnMainPage")
    Call<Follow> getPostListOnMainPage(@Field("userId") String userId, @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 获取帖子内容
     *
     * @param userId
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("getPostInfo")
    Call<HttpResult<ForumDetalis>> getPostInfo(@Field("userId") String userId, @Field("postId") String postId);


    /**
     * 获取帖子评论主列表
     *
     * @param userId
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("getPostReplyMainList")
    Call<ReplyList> getPostReplyMainList(@Field("userId") String userId, @Field("postId") String postId);


    /**
     * 回复页获取回复列表
     *
     * @param postId
     * @param replyId
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getTheSpecificReply")
    Call<FullReply> getTheSpecificReply(@Field("postId") String postId
            , @Field("replyId") String replyId
            , @Field("userId") String userId);


    /**
     * 回复帖子 评论提帖子
     *
     * @param postId
     * @param replierId
     * @param content
     * @param mainRepliedId
     * @return
     */
    @FormUrlEncoded
    @POST("saveReplyMessage")
    Call<HttpResult> getSaveReplyMessage(@Field("postId") String postId,
                                         @Field("replierId") String replierId,
                                         @Field("content") String content,
                                         @Field("mainRepliedId") String mainRepliedId,
                                         @Field("directRepliedId") String directRepliedId);


    /**
     * 对帖子点赞/取消点赞
     */

    @FormUrlEncoded
    @POST("thumbUpOrDown4Post")
    Call<HttpResult> getThumbUpOrDown4Post(@Field("userId") String userId,
                                           @Field("postId") String replyId,
                                           @Field("thumbupNumber") int thumbupNumber);

    /**
     * 对回复进行点赞／取消点赞
     *
     * @param userId
     * @param replyId
     * @param thumbupNumber
     * @return
     */
    @FormUrlEncoded
    @POST("thumbUpOrDown4Reply")
    Call<HttpResult> getThumbUpOrDown4Reply(@Field("userId") String userId,
                                            @Field("replyId") String replyId,
                                            @Field("thumbupNumber") int thumbupNumber);


    /**
     * 关注用户
     *
     * @param userId
     * @param followId
     * @return
     */
    @FormUrlEncoded
    @POST("followUser")
    Call<HttpResult> getFollowUser(@Field("userId") String userId, @Field("followId") String followId);

    /**
     * 取消关注用户
     */
    @FormUrlEncoded
    @POST("unFollowUser")
    Call<HttpResult> getUnFollowUser(@Field("userId") String userId, @Field("followId") String followId);


    /**
     * 关注论坛
     *
     * @param userId
     * @param forumId
     * @return
     */
    @FormUrlEncoded
    @POST("followForum")
    Call<HttpResult> getfollowForumr(@Field("userId") String userId, @Field("forumId") String forumId);


    /**
     * getHotSearchKeyword  热门搜索
     */

    @POST("getHotSearchKeyword")
    Call<HotSearch> getHotSearchKeyword();


    /**
     * 获取所有论坛
     *
     * @return
     */
    @POST("getForumList")
    Call<AllForums> getForumList();

    /**
     * 获取所有品牌
     */
    @POST("getBrandAll")
    Call<AllBrand> getgetBrandAll();


    @POST("saveNewPost")
    Call<HttpResult> getSavePost(@Body RequestBody requestBody);


    /**
     * 搜索全部
     */
    @FormUrlEncoded
    @POST("getSearchResult4All")
    Call<AllSearch> getSearchResult4All(@Field("userId") String userId, @Field("keyword") String keyword);


    /**
     * 42.3获取帖子搜索结果
     */

    @FormUrlEncoded
    @POST("getSearchResult4Post")
    Call<SearchPostBean> getSearchResult4Post(@Field("keyword") String keyword
            , @Field("userId") String userId
            , @Field("pageNo") int pageNo);


    /**
     * 获取用户搜索结果
     */

    @FormUrlEncoded
    @POST("getSearchResult4User")
    Call<SearchUser> getSearchResult4User(@Field("keyword") String keyword
            , @Field("userId") String userId
            , @Field("pageNo") int pageNo);


    @FormUrlEncoded
    @POST("getSearchResult4QA")
    Call<SearchQa> getSearchResult4QA(@Field("keyword") String keyword, @Field("userId") String userId, @Field("pageNo") int pageNo);


    /**
     * 获取职位
     */

    @POST("getJobList")
    Call<JobList> getJobList();

    /**
     * 获取消息推送设置状态
     */
    @FormUrlEncoded
    @POST("getUserMessagePushSetting")
    Call<HttpResult<MessagePush>> getUserMessagePushSetting(@Field("userId") String userId);


    /**
     * 设置消息推送
     * <p>
     * updateMessagePushSetting
     */
    @FormUrlEncoded
    @POST("updateMessagePushSetting")
    Call<HttpResult> updateMessagePushSetting(@FieldMap Map<String, String> map);


    /**
     * 获取认证状态列表
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getIdentityAuthStatus")
    Call<AuthStatus> getIdentityAuthStatus(@Field("userId") String userId);


    /**
     * 提交用户认证/大咖认证信息
     */

    @POST("saveUserIdentity")
    Call<HttpResult> saveUserIdentity(@Body RequestBody requestBody);


    /**
     * 提交用户认证/大咖认证信息
     */

    @POST("saveDealerIdentity")
    Call<HttpResult> saveDealerIdentity(@Body RequestBody requestBody);


    /**
     * 模糊匹配经销商
     *
     * @param brandId
     * @return
     */
    @FormUrlEncoded
    @POST("getCustomerByStr")
    Call<CustomerByStr> getCustomerByStr(@Field("str") String str, @Field("brandId") String brandId);


    /**
     * 搜索问答
     */


    /**
     * 获取用户最近的搜索词条，按时间倒序排列，最多10条
     */
    @FormUrlEncoded
    @POST("getUserSearchKeyword")
    Call<HotSearch> getUserSearchKeyword(@Field("userId") String str);


    /**
     * deleteUserSearchKeyword  删除搜索记录
     */
    @FormUrlEncoded
    @POST("deleteUserSearchKeyword")
    Call<HttpResult> deleteUserSearch(@Field("userId") String str);


    /**
     * 获取用户财富值和金币值
     *
     * @param str
     * @return
     */
    @FormUrlEncoded
    @POST("getUserFortuneAndCoin")
    Call<HttpResult<UserFortuneBean>> getUserFortuneAndCoin(@Field("userId") String str);


    /**
     * 获取用户金币值明细
     */
    @FormUrlEncoded
    @POST("getUserCoinDetail")
    Call<CoinDetailBean> getUserCoinDetail(@Field("userId") String userId, @Field("pageNo") int pageNo);


    /**
     * 获取用户财富值明细
     */
    @FormUrlEncoded
    @POST("getUserFortuneDetail")
    Call<FortuneDetailBean> getUserFortuneDetail(@Field("userId") String userId, @Field("pageNo") int pageNo);


    /**
     * 获取可兑换的物品列表
     *
     * @return
     */
    @POST("getExchangeItemList")
    Call<ExchangeBean> getExchangeItemList();


    /**
     * 使用财富值兑换物品/金币
     */
    @FormUrlEncoded
    @POST("exchangeUserFortune4Item")
    Call<HttpResult> exchangeUserFortune4Item(@Field("userId") String userId, @Field("exchangeItemId") String exchangeItemId);


    /**
     * 获取用户兑换记录
     */
    @FormUrlEncoded
    @POST("getExchangeRecord")
    Call<ExchangeRecordBean> getExchangeRecord(@Field("userId") String userId, @Field("pageNo") int pageNo);


    /**
     * 获取问答专区首页专区列表(中间的3个)
     */
    @FormUrlEncoded
    @POST("getForumOnMainPage")
    Call<QaMainPageBean> getForumOnMainPage(@Field("userId") String userId);


    /**
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getListOnQAMainPage")
    Call<HotListBean> getListOnQAMainPage(@Field("userId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 问答详情页
     *
     * @param userId
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("getQuestionInfo")
    Call<QaDetalisBean> getQuestionInfo(@Field("userId") String userId, @Field("postId") String postId);


    /**
     * 回答问题
     *
     * @param postId
     * @param content
     * @param replierId
     * @return
     */
    @FormUrlEncoded
    @POST("answerQuestion")
    Call<HttpResult> answerQuestion(@Field("postId") String postId
            , @Field("content") String content
            , @Field("replierId") String replierId);


    /**
     * 采纳问答
     *
     * @param replyId
     * @return
     */
    @FormUrlEncoded
    @POST("selectAnswer")
    Call<HttpResult> selectAnswer(@Field("replyId") String replyId);


    /**
     * 获取问答详情 全部问答
     *
     * @param userId
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("getQAReplyList")
    Call<QAReplyLisBean> getQAReplyList(@Field("userId") String userId, @Field("postId") String postId, @Field("count") int count);


    /**
     * 获取所有的专区
     */
    @POST("getQAForumList")
    Call<QAForumListBean> getQAForumList();


    /**
     * 获取咨询专家列表/搜索咨询专家
     *
     * @param userId
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getBigshotList")
    Call<MavinListBean> getBigshotList(@Field("userId") String userId
            , @Field("keyWord") String keyWord
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 普通专区最新提问列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getAreaQuestionList")
    Call<QuestionListBean> getAreaQuestionList(@Field("userId") String userId
            , @Field("forumId") String forumId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 普通专区最新采纳列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("getAreaAnswerList")
    Call<AnswerListBean> getAreaAnswerList(@Field("userId") String userId
            , @Field("forumId") String forumId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 发布问答
     *
     * @param requestBody
     * @return
     */
    @POST("postReward")
    Call<HttpResult> getpostReward(@Body RequestBody requestBody);


    /**
     * 问答主页搜索
     *
     * @param userId
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getSearchResultOnQAMainPage")
    Call<HotListBean> getSearchResultOnQAMainPage(@Field("userId") String userId
            , @Field("keyWord") String keyWord
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 单个问答专区内的搜索
     *
     * @param userId
     * @param keyWord
     * @param forumId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getSearchResultOnQAArea")
    Call<QuestionListBean> getSearchResultOnQAArea(@Field("userId") String userId
            , @Field("keyWord") String keyWord
            , @Field("forumId") String forumId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 获取大咖信息(向大咖提问界面)
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getBigShotInfo")
    Call<BigShotInfoBean> getBigShotInfo(@Field("userId") String userId);


    /**
     * 向大咖提问
     *
     * @param authorId
     * @param bigshotId
     * @param type
     * @param title
     * @param forumId
     * @param rewardCoin
     * @param isPrivate
     * @return
     */
    @FormUrlEncoded
    @POST("postRewardToBS")
    Call<HttpResult> postRewardToBS(@Field("authorId") String authorId
            , @Field("bigshotId") String bigshotId
            , @Field("type") String type
            , @Field("title") String title
            , @Field("forumId") String forumId
            , @Field("rewardCoin") String rewardCoin
            , @Field("isPrivate") int isPrivate);


    /**
     * 获取大咖回答过的提问列表(向大咖提问界面)
     *
     * @param bigshotId
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getBigshotAnwserList")
    Call<BigshotAnwserListBean> getBigshotAnwserList(@Field("bigshotId") String bigshotId
            , @Field("authorId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 获取大咖相关信息(大咖单个问题详情界面)
     *
     * @param userId
     * @param postId
     * @return
     */

    @FormUrlEncoded
    @POST("getBigshotInfoOnQuestionDetail")
    Call<QuestionDetalisBean> getBigshotInfoOnQuestionDetail(@Field("userId") String userId
            , @Field("postId") String postId);


    @FormUrlEncoded
    @POST("getQuestionInfo")
    Call<QuestionInfoBean> getQuestionUserInfo(@Field("userId") String userId, @Field("postId") String postId);

    /**
     * 偷看大咖答案
     *
     * @return
     */
    @FormUrlEncoded
    @POST("payForBigshotQuestion")
    Call<HttpResult> payForBigshotQuestion(@Field("userId") String userId
            , @Field("postId") String postId
            , @Field("coin") int coin);


    /**
     * 获取大咖 导航栏列表
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("getForumOnBSMainPage")
    Call<BigForumBean> getForumOnBSMainPage(@Field("userId") String userId);


    /**
     * 获取新晋答主
     *
     * @return
     */
    @POST("getNewBigshotList")
    Call<MavinListBean> getNewBigshotList();


    /**
     * 大咖首页热门问答
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getHotListOnBigshotMainPage")
    Call<BigshotAnwserListBean> getHotListOnBigshotMainPage(@Field("userId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 获取大咖单个专区 答主列表
     *
     * @param userId
     * @param forumId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getHotBigshotListOnArea")
    Call<MavinListBean> getHotBigshotListOnArea(@Field("userId") String userId
            , @Field("forumId") String forumId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 获取大咖单个专区 热门回答
     *
     * @param userId
     * @param forumId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getHotListOnBigshotArea")
    Call<BigshotAnwserListBean> getHotListOnBigshotArea(@Field("userId") String userId
            , @Field("forumId") String forumId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 大咖首页搜索问答
     *
     * @param userId
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getSearchResultOnBigshotMainPage")
    Call<BigshotAnwserListBean> getSearchResultOnBigshotMainPage(
            @Field("userId") String userId
            , @Field("keyWord") String keyWord
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 单个大咖专区搜索问答
     *
     * @param userId
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getSearchResultOnBigshotArea")
    Call<BigshotAnwserListBean> getSearchResultOnBigshotArea(
            @Field("userId") String userId
            , @Field("keyWord") String keyWord
            , @Field("forumId") String forumId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 单个大咖专区搜索答主
     *
     * @param userId
     * @param keyWord
     * @param forumId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("SearchBigshotListOnArea")
    Call<MavinListBean> SearchBigshotListOnArea(@Field("userId") String userId
            , @Field("keyWord") String keyWord
            , @Field("forumId") String forumId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 对回答点赞/取消点赞
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("thumbUpOrDown4Answer")
    Call<HttpResult> thumbUpOrDown4Answer(@Field("userId") String userId
            , @Field("replyId") String replyId
            , @Field("thumbupNumber") int thumbupNumber
    );


    /**
     * 问答个人主页（上方）用户信息
     */


    @FormUrlEncoded
    @POST("getUserInfoOnSelfMainPage")
    Call<QaUserIfonBean> getUserInfoOnSelfMainPage(@Field("userId") String userId
            , @Field("identityType") String replyId
    );


    /**
     * 修改大咖简介
     *
     * @param userId
     * @param bigshotDesc
     * @return
     */
    @FormUrlEncoded
    @POST("saveBigshotDesc")
    Call<HttpResult> saveBigshotDesc(@Field("userId") String userId
            , @Field("bigshotDesc") String bigshotDesc);


    /**
     * 修改提问价格
     *
     * @param userId
     * @param rewardCoin
     * @return
     */
    @FormUrlEncoded
    @POST("updateBSRewardCoin")
    Call<HttpResult> updateBSRewardCoin(@Field("userId") String userId
            , @Field("rewardCoin") int rewardCoin
    );


    /**
     * 切换身份
     *
     * @param userId
     * @param identityType
     * @return
     */
    @FormUrlEncoded
    @POST("switchIdentity")
    Call<HttpResult> switchIdentity(@Field("userId") String userId
            , @Field("identityType") String identityType);


    /**
     * 问我的问题
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getQuestionAskToMe")
    Call<QaToMeBean> getQuestionAskToMe(@Field("userId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 回答问题
     *
     * @param content
     * @param replierId
     * @param postId
     * @return
     */
    @FormUrlEncoded
    @POST("answerQuestionBS")
    Call<HttpResult> answerQuestionBS(@Field("content") String content
            , @Field("replierId") String replierId
            , @Field("postId") String postId);


    @FormUrlEncoded
    @POST("refuseToAnswer")
    Call<HttpResult> refuseToAnswer(@Field("postId") String postId);

    /**
     * 大咖  我回答的问题
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getMyAnswerBS")
    Call<QaUserAnswerBean> getMyAnswerBS(@Field("userId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 我的提问
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getMyQuestion")
    Call<QaUserToMeBean> getMyQuestion(@Field("userId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 我的回答
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getMyAnswer")
    Call<QaUserAnswerBean> getMyAnswer(@Field("userId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);

    /**
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @FormUrlEncoded
    @POST("getPaidQuestionList")
    Call<QaUserAnswerBean> getPaidQuestionList(@Field("userId") String userId
            , @Field("pageNo") int pageNo
            , @Field("pageSize") int pageSize);


    /**
     * 新建问题（运营管家）
     */
    @POST("record/newRecord")
    Call<HttpResult> newRecord(@Body RequestBody requestBody);


    /**
     * 新建问题-获取用户站点（运营管家）
     */
    @FormUrlEncoded
    @POST("record/getCustByUserId")
    Call<CustBean> getCustByUserId(@Field("userId") String userId);


    /**
     * .新建问题-获取用户职位列表（运营管家）
     */
    @FormUrlEncoded
    @POST("record/getUserJobs")
    Call<JobList> getUserJobs(@Field("userId") String userId);


    /**
     * 新建问题-获取系统列表（运营管家）
     */
    @FormUrlEncoded
    @POST("record/getSystemByUserId")
    Call<SystemBean> getSystemByUserId(@Field("userId") String userId);


    /**
     * 搜索问题/问题列表（运营管家）
     */
    @FormUrlEncoded
    @POST("record/getRecord")
    Call<RecordBean> getRecord(@Field("userId") String userId,
                               @Field("infoccRdid") String infoccRdid,
                               @Field("status") String status,
                               @Field("pageNo") int pageNo,
                               @Field("pageSize") int pageSize);


    /**
     * 催单（运营管家）
     */
    @FormUrlEncoded
    @POST("record/remindrec")
    Call<HttpResult> remindrec(@Field("userId") String userId,
                               @Field("recordId") String recordId);


    /**
     * 问题确认解决（运营管家）
     */
    @FormUrlEncoded
    @POST("record/confirm2evaluate")
    Call<HttpResult> confirm2evaluate(@Field("userId") String userId,
                                      @Field("recordId") String recordId);


    /**
     * 评价（运营管家）
     */
    @FormUrlEncoded
    @POST("record/saveSatisfactionDegree")
    Call<HttpResult> saveSatisfactionDegree(@Field("score") String score,
                                            @Field("spscore") String spscore,
                                            @Field("quscore") String quscore,
                                            @Field("srscore") String srscore,
                                            @Field("infoccRdid") String infoccRdid,
                                            @Field("supplementaryDescription") String supplementaryDescription);
}


