package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/21.
 */

public class People {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","userId":1000021,"userName":"liqiangtest","nickname":"ycb223232","avatar":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/avatar_20180318073442.png?Expires=1521625939&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=D45GfJAZRwt3pNxuu6jdiv5BV9U%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"NO","level":0,"viewerFollowed":0},{"digitalSignature":"","userId":1000017,"userName":"兵","signature":"sigbbb","nickname":"ycb229933","avatar":"","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"NO","viewerFollowed":0},{"digitalSignature":"","userId":1000034,"userName":"浠曦","signature":"签名","nickname":"ycb492124","avatar":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/avatar_20180316170540.png?Expires=1521625939&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=HzP1O0rQ3f28qrOwkioq4L8yHf8%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"NO","level":0,"viewerFollowed":0},{"digitalSignature":"","userId":1000013,"userName":"zkz","signature":"上善若水","nickname":"胤翔","avatar":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000007-20180118163745.png?Expires=1521625939&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=wmEF9Tpw3t5BUgGXIgWJdfS5hPA%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"YES","level":0,"viewerFollowed":0},{"digitalSignature":"","userId":1000019,"userName":"zero win","signature":"sigaaa","nickname":"assdff","avatar":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/avatar_20180312163622.png?Expires=1521625939&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=yOUE9sbJwDtZlgxAIgTdg8a1j%2Bk%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"YES","level":1,"viewerFollowed":0}]
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
         * userId : 1000021
         * userName : liqiangtest
         * nickname : ycb223232
         * avatar : https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/avatar_20180318073442.png?Expires=1521625939&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=D45GfJAZRwt3pNxuu6jdiv5BV9U%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * realNameAuth : 10081001
         * realNameAuthDesc : 未认证
         * friend : NO
         * level : 0
         * viewerFollowed : 0
         * signature : sigbbb
         */

        private String digitalSignature;
        private String userId;
        private String userName;
        private String nickname;
        private String avatar;
        private int realNameAuth;
        private String realNameAuthDesc;
        private String friend;
        private String level;
        private int viewerFollowed;
        private String signature;

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

        public String getFriend() {
            return friend;
        }

        public void setFriend(String friend) {
            this.friend = friend;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getViewerFollowed() {
            return viewerFollowed;
        }

        public void setViewerFollowed(int viewerFollowed) {
            this.viewerFollowed = viewerFollowed;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
