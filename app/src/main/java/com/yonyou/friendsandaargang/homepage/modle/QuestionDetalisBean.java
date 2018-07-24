package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/6/5.
 */

public class QuestionDetalisBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","userId":57,"nickname":"浠曦1号🥈🥈🥈","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180524153225.png?Expires=1528189211&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=XUdj8dXL80foEE1TVGdehi+3BXg=&x-oss-process=image/format,jpg/circle,r_3000","bigshotDesc":"哈哈哈哈大牛一个","answerNum":3,"forumName":"销售专区","payNum":2,"forumId":1018,"rewardCoin":25,"isPay":1,"content":"这个是一个有趣的问题，我对此表示赞同，希望以后可以早点下班。"}]
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
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180524153225.png?Expires=1528189211&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=XUdj8dXL80foEE1TVGdehi+3BXg=&x-oss-process=image/format,jpg/circle,r_3000
         * bigshotDesc : 哈哈哈哈大牛一个
         * answerNum : 3
         * forumName : 销售专区
         * payNum : 2
         * forumId : 1018
         * rewardCoin : 25
         * isPay : 1
         * content : 这个是一个有趣的问题，我对此表示赞同，希望以后可以早点下班。
         */

        private String digitalSignature;
        private String userId;
        private String nickname;
        private String avatar;
        private String bigshotDesc;
        private String answerNum;
        private String forumName;
        private String payNum;
        private int forumId;
        private int rewardCoin;
        private int isPay;
        private String content;

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

        public String getAnswerNum() {
            return answerNum;
        }

        public void setAnswerNum(String answerNum) {
            this.answerNum = answerNum;
        }

        public String getForumName() {
            return forumName;
        }

        public void setForumName(String forumName) {
            this.forumName = forumName;
        }

        public String getPayNum() {
            return payNum;
        }

        public void setPayNum(String payNum) {
            this.payNum = payNum;
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

        public int getIsPay() {
            return isPay;
        }

        public void setIsPay(int isPay) {
            this.isPay = isPay;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
