package com.yonyou.friendsandaargang.homepage.modle;

/**
 * Created by shibing on 18/6/1.
 */

public class QaDetalisBean {

    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","postId":388,"author":"57","viewerNickname":"浠曦1号🥈🥈🥈","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180524153225.png?Expires=1527849332&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=IOTXWu0QLbM%2FR6AYh04fhU%2FpK8A%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"forumId":1011,"level":0,"forumName":"销售专区","title":"如果提高配件周转","replyNumber":3,"readNumber":92,"thumbupNumber":0,"itemType":3,"isDeleted":0,"isAdminDeleted":0,"postDate":"2018-05-24 16:46:00","rewardCoin":0,"hours":0,"groupType":2,"minute":-8700,"haveContent":1,"canAnswer":0}
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
         * postId : 388
         * author : 57
         * viewerNickname : 浠曦1号🥈🥈🥈
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180524153225.png?Expires=1527849332&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=IOTXWu0QLbM%2FR6AYh04fhU%2FpK8A%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * realNameAuth : 10081001
         * forumId : 1011
         * level : 0
         * forumName : 销售专区
         * title : 如果提高配件周转
         * replyNumber : 3
         * readNumber : 92
         * thumbupNumber : 0
         * itemType : 3
         * isDeleted : 0
         * isAdminDeleted : 0
         * postDate : 2018-05-24 16:46:00
         * rewardCoin : 0
         * hours : 0
         * groupType : 2
         * minute : -8700
         * haveContent : 1
         * canAnswer : 0
         */

        private String digitalSignature;
        private String postId;
        private String author;
        private String viewerNickname;
        private String avatar;
        private String realNameAuth;
        private String forumId;
        private String level;
        private String forumName;
        private String title;
        private String replyNumber;
        private String readNumber;
        private String thumbupNumber;
        private int itemType;
        private int isDeleted;
        private int isAdminDeleted;
        private String postDate;
        private int rewardCoin;
        private int hours;
        private String groupType;
        private int minute;
        private int haveContent;
        private String canAnswer;

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

        public String getRealNameAuth() {
            return realNameAuth;
        }

        public void setRealNameAuth(String realNameAuth) {
            this.realNameAuth = realNameAuth;
        }

        public String getForumId() {
            return forumId;
        }

        public void setForumId(String forumId) {
            this.forumId = forumId;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getForumName() {
            return forumName;
        }

        public void setForumName(String forumName) {
            this.forumName = forumName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReplyNumber() {
            return replyNumber;
        }

        public void setReplyNumber(String replyNumber) {
            this.replyNumber = replyNumber;
        }

        public String getReadNumber() {
            return readNumber;
        }

        public void setReadNumber(String readNumber) {
            this.readNumber = readNumber;
        }

        public String getThumbupNumber() {
            return thumbupNumber;
        }

        public void setThumbupNumber(String thumbupNumber) {
            this.thumbupNumber = thumbupNumber;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }

        public int getIsAdminDeleted() {
            return isAdminDeleted;
        }

        public void setIsAdminDeleted(int isAdminDeleted) {
            this.isAdminDeleted = isAdminDeleted;
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

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public String getGroupType() {
            return groupType;
        }

        public void setGroupType(String groupType) {
            this.groupType = groupType;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getHaveContent() {
            return haveContent;
        }

        public void setHaveContent(int haveContent) {
            this.haveContent = haveContent;
        }

        public String getCanAnswer() {
            return canAnswer;
        }

        public void setCanAnswer(String canAnswer) {
            this.canAnswer = canAnswer;
        }
    }
}
