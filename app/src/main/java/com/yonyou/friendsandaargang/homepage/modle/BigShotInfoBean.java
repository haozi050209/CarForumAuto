package com.yonyou.friendsandaargang.homepage.modle;

/**
 * Created by shibing on 18/6/5.
 */

public class BigShotInfoBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","userId":38,"nickname":"1","avatar":"","bigshotDesc":"砖家","forumName":"销售专区","forumId":1018,"rewardCoin":10,"privateNum":2,"publicNum":3}
     */

    private int returnCode;
    private String returnMsg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * digitalSignature :
         * userId : 38
         * nickname : 1
         * avatar :
         * bigshotDesc : 砖家
         * forumName : 销售专区
         * forumId : 1018
         * rewardCoin : 10
         * privateNum : 2
         * publicNum : 3
         */

        private String digitalSignature;
        private int userId;
        private String nickname;
        private String avatar;
        private String bigshotDesc;
        private String forumName;
        private String forumId;
        private int rewardCoin;
        private int privateNum;
        private int publicNum;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getBigshotDesc() {
            return bigshotDesc;
        }

        public void setBigshotDesc(String bigshotDesc) {
            this.bigshotDesc = bigshotDesc;
        }

        public String getForumName() {
            return forumName;
        }

        public void setForumName(String forumName) {
            this.forumName = forumName;
        }

        public String getForumId() {
            return forumId;
        }

        public void setForumId(String forumId) {
            this.forumId = forumId;
        }

        public int getRewardCoin() {
            return rewardCoin;
        }

        public void setRewardCoin(int rewardCoin) {
            this.rewardCoin = rewardCoin;
        }

        public int getPrivateNum() {
            return privateNum;
        }

        public void setPrivateNum(int privateNum) {
            this.privateNum = privateNum;
        }

        public int getPublicNum() {
            return publicNum;
        }

        public void setPublicNum(int publicNum) {
            this.publicNum = publicNum;
        }
    }
}
