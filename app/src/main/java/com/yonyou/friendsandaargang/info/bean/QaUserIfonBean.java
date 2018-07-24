package com.yonyou.friendsandaargang.info.bean;

/**
 * Created by shibing on 18/6/8.
 */

public class QaUserIfonBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","nickname":"大嘴猴🐵","userId":4,"avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1524196749&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=6BG9blFOhmixLcmce1or3u15m2U%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","coin":100,"publicNum":14,"rewardCoin":0,"bigshotDesc":"简介"}
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
         * nickname : 大嘴猴🐵
         * userId : 4
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1524196749&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=6BG9blFOhmixLcmce1or3u15m2U%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * coin : 100
         * publicNum : 14
         * rewardCoin : 0
         * bigshotDesc : 简介
         */

        private String digitalSignature;
        private String nickname;
        private int userId;
        private String avatar;
        private int coin;
        private int publicNum;
        private String rewardCoin;
        private String bigshotDesc;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getPublicNum() {
            return publicNum;
        }

        public void setPublicNum(int publicNum) {
            this.publicNum = publicNum;
        }

        public String getRewardCoin() {
            return rewardCoin;
        }

        public void setRewardCoin(String rewardCoin) {
            this.rewardCoin = rewardCoin;
        }

        public String getBigshotDesc() {
            return bigshotDesc;
        }

        public void setBigshotDesc(String bigshotDesc) {
            this.bigshotDesc = bigshotDesc;
        }
    }
}
