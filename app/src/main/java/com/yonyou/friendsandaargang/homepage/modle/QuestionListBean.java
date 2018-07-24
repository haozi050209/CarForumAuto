package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/5/31.
 */

public class QuestionListBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":477,"author":"71","viewerNickname":"用友汽车皮尺部","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1527760036&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=yphNWg9W%2Ft2xVU9YvAKNg9LqdP8%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1011,"title":"提问1","replyNumber":0,"itemType":3,"rewardCoin":100,"hours":46,"groupType":3,"minute":2779,"haveContent":1,"canAnswer":0}]
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
         * postId : 477
         * author : 71
         * viewerNickname : 用友汽车皮尺部
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1527760036&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=yphNWg9W%2Ft2xVU9YvAKNg9LqdP8%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * forumId : 1011
         * title : 提问1
         * replyNumber : 0
         * itemType : 3
         * rewardCoin : 100
         * hours : 46
         * groupType : 3
         * minute : 2779
         * haveContent : 1
         * canAnswer : 0
         */

        private String digitalSignature;
        private String postId;
        private String author;
        private String viewerNickname;
        private String avatar;
        private int forumId;
        private String title;
        private String replyNumber;
        private int itemType;
        private String rewardCoin;
        private String hours;
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

        public String getReplyNumber() {
            return replyNumber;
        }

        public void setReplyNumber(String replyNumber) {
            this.replyNumber = replyNumber;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getRewardCoin() {
            return rewardCoin;
        }

        public void setRewardCoin(String rewardCoin) {
            this.rewardCoin = rewardCoin;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
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
