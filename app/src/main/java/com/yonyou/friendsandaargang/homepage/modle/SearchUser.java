package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/4/17.
 */

public class SearchUser {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","userId":1000008,"userName":"用户名","signature":"签名","nickname":"昵称","avatar":"https://yonyouqiche-community-app-imag","realNameAuth":10081001,"realNameAuthDesc":"未认证","level":0,"followed":"0 ，1是，0否"}]
     */

    private int returnCode;
    private String returnMsg;
    private List<ContentBean> content;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * digitalSignature :
         * userId : 1000008
         * userName : 用户名
         * signature : 签名
         * nickname : 昵称
         * avatar : https://yonyouqiche-community-app-imag
         * realNameAuth : 10081001
         * realNameAuthDesc : 未认证
         * level : 0
         * followed : 0 ，1是，0否
         */

        private String digitalSignature;
        private String userId;
        private String userName;
        private String signature;
        private String nickname;
        private String avatar;
        private int realNameAuth;
        private String realNameAuthDesc;
        private int level;
        private String followed;

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getFollowed() {
            return followed;
        }

        public void setFollowed(String followed) {
            this.followed = followed;
        }
    }
}
