package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/27.
 */

public class Fans {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","userId":8,"userName":"浠曦2号","signature":"为了测试！","nickname":"浠曦2号","avatar":"","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"YES","level":0,"viewerFollowed":0},{"digitalSignature":"","userId":4,"userName":"zzc","signature":"我就就是我，是颜色不一样的烟火🎆！","nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327160537.png?Expires=1522141396&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=Y9JGcud0IAmvdYWPmxuMBMi6wcY%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"YES","level":0,"viewerFollowed":0},{"digitalSignature":"","userId":7,"userName":"浠曦","nickname":"浠曦1号","avatar":"","realNameAuth":10081001,"realNameAuthDesc":"未认证","friend":"YES","level":0,"viewerFollowed":0}]
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
         * userId : 8
         * userName : 浠曦2号
         * signature : 为了测试！
         * nickname : 浠曦2号
         * avatar :
         * realNameAuth : 10081001
         * realNameAuthDesc : 未认证
         * friend : YES
         * level : 0
         * viewerFollowed : 0
         */

        private String digitalSignature;
        private String userId;
        private String userName;
        private String signature;
        private String nickname;
        private String avatar;
        private int realNameAuth;
        private String realNameAuthDesc;
        private String friend;
        private String level;
        private int viewerFollowed;

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
    }
}
