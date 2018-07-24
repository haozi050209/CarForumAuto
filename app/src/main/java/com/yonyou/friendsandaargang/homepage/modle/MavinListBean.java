package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/6/1.
 */

public class MavinListBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","userId":57,"nickname":"浠曦1号🥈🥈🥈","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180524153225.png?Expires=1527834975&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=Ai7wBcCTzqC0ZtQx5Vu1lT9WtCs%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","bigshotDesc":"哈哈哈哈大牛一个","answerNum":50,"forumName":"销售专区","forumId":1018,"rewardCoin":25},{"digitalSignature":"","userId":4,"nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1527835189&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=MCJXYI7pRsLdZK0YCUenAlAFPB0%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","bigshotDesc":"买鞋","answerNum":32,"forumName":"销售专区","forumId":1018,"rewardCoin":20},{"digitalSignature":"","userId":38,"nickname":"1","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180403121043.png?Expires=1527835189&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=5IOhIO8jCvbqE8RBDR%2BnrXbyIgg%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","bigshotDesc":"砖家","answerNum":5,"forumName":"销售专区","forumId":1018,"rewardCoin":10},{"digitalSignature":"","userId":48,"nickname":"1","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1},{"digitalSignature":"","userId":41,"nickname":"1","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":2},{"digitalSignature":"","userId":46,"nickname":"1","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1},{"digitalSignature":"","userId":39,"nickname":"1","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180403121043.png?Expires=1527835189&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=5IOhIO8jCvbqE8RBDR%2BnrXbyIgg%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1},{"digitalSignature":"","userId":44,"nickname":"1","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1},{"digitalSignature":"","userId":42,"nickname":"2","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":3},{"digitalSignature":"","userId":47,"nickname":"1","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1},{"digitalSignature":"","userId":40,"nickname":"1","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1},{"digitalSignature":"","userId":45,"nickname":"1","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1},{"digitalSignature":"","userId":43,"nickname":"4","avatar":"","bigshotDesc":"砖家","answerNum":0,"forumName":"销售专区","forumId":1018,"rewardCoin":1}]
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
         * userId : 57
         * nickname : 浠曦1号🥈🥈🥈
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180524153225.png?Expires=1527834975&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=Ai7wBcCTzqC0ZtQx5Vu1lT9WtCs%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * bigshotDesc : 哈哈哈哈大牛一个
         * answerNum : 50
         * forumName : 销售专区
         * forumId : 1018
         * rewardCoin : 25
         */

        private String digitalSignature;
        private String userId;
        private String nickname;
        private String avatar;
        private String bigshotDesc;
        private int answerNum;
        private String forumName;
        private int forumId;
        private int rewardCoin;

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

        public int getAnswerNum() {
            return answerNum;
        }

        public void setAnswerNum(int answerNum) {
            this.answerNum = answerNum;
        }

        public String getForumName() {
            return forumName;
        }

        public void setForumName(String forumName) {
            this.forumName = forumName;
        }

        public int getForumId() {
            return forumId;
        }

        public void setForumId(int forumId) {
            this.forumId = forumId;
        }

        public int getRewardCoin() {
            return rewardCoin;
        }

        public void setRewardCoin(int rewardCoin) {
            this.rewardCoin = rewardCoin;
        }
    }
}
