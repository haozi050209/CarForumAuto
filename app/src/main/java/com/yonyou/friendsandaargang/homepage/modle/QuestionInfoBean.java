package com.yonyou.friendsandaargang.homepage.modle;

/**
 * Created by shibing on 18/6/5.
 */

public class QuestionInfoBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","postId":343,"author":"62","viewerNickname":"浠曦2号","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180530140145.png?Expires=1528190293&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=mAtQJOR%2BnGPfaTRTvKsYJ9AJwco%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"forumId":1018,"level":0,"forumName":"销售专区","title":"测试第三个问题","replyNumber":1,"readNumber":149,"thumbupNumber":0,"itemType":3,"isDeleted":0,"isAdminDeleted":0,"postDate":"2018-05-21 17:11:48","rewardCoin":0,"hours":0,"groupType":2,"minute":-18673,"haveContent":0,"canAnswer":0}
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
         * postId : 343
         * author : 62
         * viewerNickname : 浠曦2号
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180530140145.png?Expires=1528190293&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=mAtQJOR%2BnGPfaTRTvKsYJ9AJwco%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * realNameAuth : 10081001
         * forumId : 1018
         * level : 0
         * forumName : 销售专区
         * title : 测试第三个问题
         * replyNumber : 1
         * readNumber : 149
         * thumbupNumber : 0
         * itemType : 3
         * isDeleted : 0
         * isAdminDeleted : 0
         * postDate : 2018-05-21 17:11:48
         * rewardCoin : 0
         * hours : 0
         * groupType : 2
         * minute : -18673
         * haveContent : 0
         * canAnswer : 0
         */

        private String digitalSignature;
        private String postId;
        private String author;
        private String viewerNickname;
        private String avatar;
        private int realNameAuth;
        private int forumId;
        private int level;
        private String forumName;
        private String title;
        private int replyNumber;
        private int readNumber;
        private int thumbupNumber;
        private int itemType;
        private int isDeleted;
        private int isAdminDeleted;
        private String postDate;
        private int rewardCoin;
        private int hours;
        private int groupType;
        private int minute;
        private int haveContent;
        private int canAnswer;

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

        public int getRealNameAuth() {
            return realNameAuth;
        }

        public void setRealNameAuth(int realNameAuth) {
            this.realNameAuth = realNameAuth;
        }

        public int getForumId() {
            return forumId;
        }

        public void setForumId(int forumId) {
            this.forumId = forumId;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
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

        public int getThumbupNumber() {
            return thumbupNumber;
        }

        public void setThumbupNumber(int thumbupNumber) {
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

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
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

        public int getCanAnswer() {
            return canAnswer;
        }

        public void setCanAnswer(int canAnswer) {
            this.canAnswer = canAnswer;
        }
    }
}
