package com.yonyou.friendsandaargang.guide.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/15.
 */

public class UserInfo {


    /**
     * digitalSignature :
     * userId : 4
     * userName : zzc
     * password : z12345678
     * gender : 10041001
     * genderDesc : 男
     * registerDate : 2018-03-22
     * mobile : 18672339087
     * birthday : 2016-03-31
     * region : 310109
     * regionDesc : 上海市 虹口区
     * signature : 我就就是我，是颜色不一样的烟火🎆！
     * nickname : 大嘴猴🐵
     * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1527044670&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e88enyXmuiQ1FKNE6sInRnJnLtQ=&x-oss-process=image/format,jpg/circle,r_3000
     * realNameAuth : 10081002
     * realNameAuthDesc : 已认证
     * level : 1
     * followed : 1
     * followerNumber : 1
     * followNumber : 3
     * identityType : 10211002
     * unreadCount : 39
     * signTimes : 0
     * isLogon : 0
     * fortune : 746
     * coin : 268.5
     * bigshotDesc : 买鞋
     * identityInfo : 3
     * jobId : 1
     * job2Id : 0
     * job3Id : 0
     * custName :
     * custId : 0
     * forumIdList : [{"digitalSignature":"","forumId":1018}]
     */

    private String digitalSignature;
    private String userId;
    private String userName;
    private String password;
    private int gender;
    private String genderDesc;
    private String registerDate;
    private String mobile;
    private String birthday;
    private String region;
    private String regionDesc;
    private String signature;
    private String nickname;
    private String avatar;
    private int realNameAuth;
    private String realNameAuthDesc;
    private String level;
    private int followed;
    private int followerNumber;
    private int followNumber;
    private String identityType;
    private int unreadCount;
    private int signTimes;
    private int isLogon;
    private int fortune;
    private double coin;
    private String bigshotDesc;
    private String identityInfo;
    private int jobId;
    private int job2Id;
    private int job3Id;
    private String custName;
    private String custId;
    private List<ForumIdListBean> forumIdList;

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderDesc() {
        return genderDesc;
    }

    public void setGenderDesc(String genderDesc) {
        this.genderDesc = genderDesc;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRealNameAuth() {
        return realNameAuth;
    }

    public void setRealNameAuth(int realNameAuth) {
        this.realNameAuth = realNameAuth;
    }

    public String getRealNameAuthDesc() {
        return realNameAuthDesc;
    }

    public void setRealNameAuthDesc(String realNameAuthDesc) {
        this.realNameAuthDesc = realNameAuthDesc;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(int followerNumber) {
        this.followerNumber = followerNumber;
    }

    public int getFollowNumber() {
        return followNumber;
    }

    public void setFollowNumber(int followNumber) {
        this.followNumber = followNumber;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getSignTimes() {
        return signTimes;
    }

    public void setSignTimes(int signTimes) {
        this.signTimes = signTimes;
    }

    public int getIsLogon() {
        return isLogon;
    }

    public void setIsLogon(int isLogon) {
        this.isLogon = isLogon;
    }

    public int getFortune() {
        return fortune;
    }

    public void setFortune(int fortune) {
        this.fortune = fortune;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public String getBigshotDesc() {
        return bigshotDesc;
    }

    public void setBigshotDesc(String bigshotDesc) {
        this.bigshotDesc = bigshotDesc;
    }

    public String getIdentityInfo() {
        return identityInfo;
    }

    public void setIdentityInfo(String identityInfo) {
        this.identityInfo = identityInfo;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getJob2Id() {
        return job2Id;
    }

    public void setJob2Id(int job2Id) {
        this.job2Id = job2Id;
    }

    public int getJob3Id() {
        return job3Id;
    }

    public void setJob3Id(int job3Id) {
        this.job3Id = job3Id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public List<ForumIdListBean> getForumIdList() {
        return forumIdList;
    }

    public void setForumIdList(List<ForumIdListBean> forumIdList) {
        this.forumIdList = forumIdList;
    }

    public static class ForumIdListBean {
        /**
         * digitalSignature :
         * forumId : 1018
         */

        private String digitalSignature;
        private int forumId;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public int getForumId() {
            return forumId;
        }

        public void setForumId(int forumId) {
            this.forumId = forumId;
        }
    }
}
