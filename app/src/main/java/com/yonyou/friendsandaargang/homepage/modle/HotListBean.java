package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/5/31.
 */

public class HotListBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":326,"type":10051003,"author":"62","viewerNickname":"浠曦2号","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180530140145.png?Expires=1527742192&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=MooE7A68MWI618xPwWN8olRwLDg%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1014,"forumName":"售后服务","title":"提升配件流转率？","content":"提升配件流转率？","replyNumber":2,"readNumber":115,"thumbupNumber":0,"isDigestPost":0,"isDraft":0,"isTop":0,"itemType":3,"isDeleted":0,"isMainpageTop":0,"postDate":"2018-05-17 16:49:09","rewardCoin":0,"hours":0,"groupType":2,"minute":-16989,"isClosed":1,"haveContent":1,"isPrivate":0,"canAnswer":0,"qaStatus":2},{"digitalSignature":"","postId":388,"type":10051003,"author":"57","viewerNickname":"浠曦1号🥈🥈🥈","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180524153225.png?Expires=1527742190&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=dv7%2BH9DAU7%2Bq0W3XG6Te8gSjZuc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1011,"forumName":"销售专区","title":"如果提高配件周转","content":"配件周转","replyNumber":3,"readNumber":69,"thumbupNumber":0,"isDigestPost":0,"isDraft":0,"isTop":0,"itemType":3,"isDeleted":0,"isMainpageTop":0,"postDate":"2018-05-24 16:46:00","rewardCoin":0,"hours":0,"groupType":2,"minute":-6912,"isClosed":1,"haveContent":1,"isPrivate":0,"canAnswer":1,"qaStatus":2}]
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
         * postId : 326
         * type : 10051003
         * author : 62
         * viewerNickname : 浠曦2号
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180530140145.png?Expires=1527742192&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=MooE7A68MWI618xPwWN8olRwLDg%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * forumId : 1014
         * forumName : 售后服务
         * title : 提升配件流转率？
         * content : 提升配件流转率？
         * replyNumber : 2
         * readNumber : 115
         * thumbupNumber : 0
         * isDigestPost : 0
         * isDraft : 0
         * isTop : 0
         * itemType : 3
         * isDeleted : 0
         * isMainpageTop : 0
         * postDate : 2018-05-17 16:49:09
         * rewardCoin : 0
         * hours : 0
         * groupType : 2
         * minute : -16989
         * isClosed : 1
         * haveContent : 1
         * isPrivate : 0
         * canAnswer : 0
         * qaStatus : 2
         *
         *
         *
         *
         *
         *
         *
         *
         "digitalSignature": "",
         "postId": 9,
         "viewerNickname": "大嘴猴🐵",
         "avatar": "https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1528105790&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=76gGiMTJGr4JmwnYYIWotyf6GfI=&x-oss-process=image/format,jpg/circle,r_3000",
         "forumId": 1011,
         "title": "专区猜测1",
         "replyNumber": 1,
         "itemType": 3,
         "postDate": "2018-03-30 15:36:31",
         "rewardCoin": 0.0,
         "hours": 0,
         "groupType": 2,
         "bigshotDesc": "买鞋",
         "minute": -92269,
         "isPay": 0,
         "haveContent": 1,
         "canAnswer": 0
         */

        private String digitalSignature;
        private String postId;
        private int type;
        private String author;
        private String viewerNickname;
        private String avatar;
        private String forumId;
        private String forumName;
        private String title;
        private String content;
        private String replyNumber;
        private String readNumber;
        private int thumbupNumber;
        private int isDigestPost;
        private int isDraft;
        private int isTop;
        private int itemType;
        private int isDeleted;
        private int isMainpageTop;
        private String postDate;
        private int rewardCoin;
        private int hours;
        private int groupType;
        private int minute;
        private int isClosed;
        private int haveContent;
        private int isPrivate;
        private int canAnswer;
        private int qaStatus;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getForumId() {
            return forumId;
        }

        public void setForumId(String forumId) {
            this.forumId = forumId;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getThumbupNumber() {
            return thumbupNumber;
        }

        public void setThumbupNumber(int thumbupNumber) {
            this.thumbupNumber = thumbupNumber;
        }

        public int getIsDigestPost() {
            return isDigestPost;
        }

        public void setIsDigestPost(int isDigestPost) {
            this.isDigestPost = isDigestPost;
        }

        public int getIsDraft() {
            return isDraft;
        }

        public void setIsDraft(int isDraft) {
            this.isDraft = isDraft;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
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

        public int getIsMainpageTop() {
            return isMainpageTop;
        }

        public void setIsMainpageTop(int isMainpageTop) {
            this.isMainpageTop = isMainpageTop;
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

        public int getIsClosed() {
            return isClosed;
        }

        public void setIsClosed(int isClosed) {
            this.isClosed = isClosed;
        }

        public int getHaveContent() {
            return haveContent;
        }

        public void setHaveContent(int haveContent) {
            this.haveContent = haveContent;
        }

        public int getIsPrivate() {
            return isPrivate;
        }

        public void setIsPrivate(int isPrivate) {
            this.isPrivate = isPrivate;
        }

        public int getCanAnswer() {
            return canAnswer;
        }

        public void setCanAnswer(int canAnswer) {
            this.canAnswer = canAnswer;
        }

        public int getQaStatus() {
            return qaStatus;
        }

        public void setQaStatus(int qaStatus) {
            this.qaStatus = qaStatus;
        }
    }
}
