package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/6/12.
 */

public class QaUserToMeBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":502,"avatar":"","forumId":1011,"title":"奥迪q8","replyNumber":1,"readNumber":123,"itemType":3,"postDate":"2018-06-06 16:57:28","rewardCoin":0,"isSelected":1},{"digitalSignature":"","postId":500,"avatar":"","forumId":1011,"title":"讨论下","replyNumber":1,"readNumber":89,"itemType":3,"postDate":"2018-06-06 16:46:06","rewardCoin":0,"isSelected":1}]
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
         * postId : 502
         * avatar :
         * forumId : 1011
         * title : 奥迪q8
         * replyNumber : 1
         * readNumber : 123
         * itemType : 3
         * postDate : 2018-06-06 16:57:28
         * rewardCoin : 0
         * isSelected : 1
         */

        private String digitalSignature;
        private String postId;
        private String avatar;
        private int forumId;
        private String title;
        private int replyNumber;
        private int readNumber;
        private int itemType;
        private String postDate;
        private int rewardCoin;
        private int isSelected;
        private String forumType;

        public String getForumType() {
            return forumType;
        }

        public void setForumType(String forumType) {
            this.forumType = forumType;
        }

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getForumId() {
            return forumId;
        }

        public void setForumId(int forumId) {
            this.forumId = forumId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getReplyNumber() {
            return replyNumber;
        }

        public void setReplyNumber(int replyNumber) {
            this.replyNumber = replyNumber;
        }

        public int getReadNumber() {
            return readNumber;
        }

        public void setReadNumber(int readNumber) {
            this.readNumber = readNumber;
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

        public int getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(int isSelected) {
            this.isSelected = isSelected;
        }
    }
}
