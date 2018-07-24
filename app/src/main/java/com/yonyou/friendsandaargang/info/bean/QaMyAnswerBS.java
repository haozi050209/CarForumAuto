package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/6/12.
 */

public class QaMyAnswerBS {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":145,"author":"大嘴猴🐵","viewerNickname":"1","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1524045725&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=c2zjOwqiFCWUdnBpi9KtdsShdEU%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","title":"大咖提问3","content":"111","itemType":3,"postDate":"2018-04-02 13:22:16","rewardCoin":10}]
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
         * postId : 145
         * author : 大嘴猴🐵
         * viewerNickname : 1
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1524045725&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=c2zjOwqiFCWUdnBpi9KtdsShdEU%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * title : 大咖提问3
         * content : 111
         * itemType : 3
         * postDate : 2018-04-02 13:22:16
         * rewardCoin : 10
         */

        private String digitalSignature;
        private int postId;
        private String author;
        private String viewerNickname;
        private String avatar;
        private String title;
        private String content;
        private int itemType;
        private String postDate;
        private int rewardCoin;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getViewerNickname() {
            return viewerNickname;
        }

        public void setViewerNickname(String viewerNickname) {
            this.viewerNickname = viewerNickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public int getRewardCoin() {
            return rewardCoin;
        }

        public void setRewardCoin(int rewardCoin) {
            this.rewardCoin = rewardCoin;
        }
    }
}
