package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/5/18.
 */

public class SearchQa {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":9,"viewerNickname":"大嘴猴🐵","avatar":"user-avatar/avatar_20180327172025.png","forumId":1011,"title":"专区猜测1","replyNumber":1,"itemType":3,"postDate":"2018-03-30 15:36:31","attachmentList":[],"rewardCoin":0,"hours":0,"groupType":2,"bigshotDesc":"买鞋","minute":-67820,"isPay":0,"haveContent":1},{"digitalSignature":"","postId":41,"viewerNickname":"大嘴猴🐵","avatar":"user-avatar/avatar_20180327172025.png","forumId":1011,"title":"悬赏10","replyNumber":1,"itemType":3,"postDate":"2018-03-30 14:15:44","attachmentList":[],"rewardCoin":10,"hours":0,"groupType":2,"bigshotDesc":"买鞋","minute":-67901,"isPay":0,"haveContent":1},{"digitalSignature":"","postId":126,"viewerNickname":"大嘴猴🐵","avatar":"user-avatar/avatar_20180327172025.png","forumId":1011,"title":"111111111111提问","replyNumber":2,"itemType":3,"postDate":"2018-04-02 13:22:16","attachmentList":[],"rewardCoin":10,"hours":0,"groupType":2,"bigshotDesc":"买鞋","minute":-63635,"isPay":0,"haveContent":1},{"digitalSignature":"","postId":127,"viewerNickname":"大嘴猴🐵","avatar":"user-avatar/avatar_20180327172025.png","forumId":1011,"title":"提问1","replyNumber":1,"itemType":3,"postDate":"2018-04-02 13:22:16","attachmentList":[{"attachmentId":1480,"attachmentName":"post-attachment/1-20180329220518.png","attachmentUrl":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/1-20180329220518.png?Expires=1526639868&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=j7uyJWtamEXzlmW1IxENDpM5I%2FE%3D&x-oss-process=image%2Fresize%2Cw_200","postId":127,"type":10121001,"uploadDate":"2018-03-29"}],"rewardCoin":10,"hours":0,"groupType":2,"bigshotDesc":"买鞋","minute":-63635,"isPay":0,"haveContent":1},{"digitalSignature":"","postId":136,"viewerNickname":"aaaaaaaaa","avatar":"aaaaaaaaa","forumId":1011,"title":"提问10","replyNumber":1,"itemType":3,"postDate":"2018-04-02 13:22:16","attachmentList":[],"rewardCoin":10,"hours":0,"groupType":2,"bigshotDesc":"","minute":-63635,"isPay":0,"haveContent":1},{"digitalSignature":"","postId":141,"viewerNickname":"Zerowin","avatar":"user-avatar/avatar_20180329175104.png","forumId":1011,"title":"提问15","replyNumber":1,"itemType":3,"postDate":"2018-03-30 13:22:16","attachmentList":[],"rewardCoin":10,"hours":0,"groupType":2,"bigshotDesc":"","minute":-67955,"isPay":0,"haveContent":1},{"digitalSignature":"","postId":140,"viewerNickname":"Zerowin","avatar":"user-avatar/avatar_20180329175104.png","forumId":1011,"title":"提问14","replyNumber":3,"itemType":3,"postDate":"2018-04-09 13:22:16","attachmentList":[],"rewardCoin":10,"hours":0,"groupType":2,"bigshotDesc":"","minute":-53555,"isPay":0,"haveContent":1},{"digitalSignature":"","postId":148,"viewerNickname":"Zerowin","avatar":"user-avatar/avatar_20180329175104.png","forumId":1011,"title":"提问0411标题","replyNumber":5,"itemType":3,"postDate":"2018-04-11 13:22:16","attachmentList":[],"rewardCoin":10,"hours":0,"groupType":2,"bigshotDesc":"","minute":-50675,"isPay":0,"haveContent":1}]
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
         * postId : 9
         * viewerNickname : 大嘴猴🐵
         * avatar : user-avatar/avatar_20180327172025.png
         * forumId : 1011
         * title : 专区猜测1
         * replyNumber : 1
         * itemType : 3
         * postDate : 2018-03-30 15:36:31
         * attachmentList : []
         * rewardCoin : 0
         * hours : 0
         * groupType : 2
         * bigshotDesc : 买鞋
         * minute : -67820
         * isPay : 0
         * haveContent : 1
         */

        private String digitalSignature;
        private String postId;
        private String viewerNickname;
        private String avatar;
        private int forumId;
        private String title;
        private String replyNumber;
        private int itemType;
        private String postDate;
        private int rewardCoin;
        private int hours;
        private int groupType;
        private String bigshotDesc;
        private int minute;
        private int isPay;
        private int haveContent;
        private List<?> attachmentList;

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

        public String getBigshotDesc() {
            return bigshotDesc;
        }

        public void setBigshotDesc(String bigshotDesc) {
            this.bigshotDesc = bigshotDesc;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getIsPay() {
            return isPay;
        }

        public void setIsPay(int isPay) {
            this.isPay = isPay;
        }

        public int getHaveContent() {
            return haveContent;
        }

        public void setHaveContent(int haveContent) {
            this.haveContent = haveContent;
        }

        public List<?> getAttachmentList() {
            return attachmentList;
        }

        public void setAttachmentList(List<?> attachmentList) {
            this.attachmentList = attachmentList;
        }
    }
}
