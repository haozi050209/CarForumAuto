package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/5/31.
 */

public class AnswerListBean  {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":593,"author":"212","viewerNickname":"ycb189128","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180629150812.png?Expires=1530267475&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=oWrEI6Xc2VPU7yxFZUk8JMbvLTc=&x-oss-process=image/format,jpg/circle,r_3000","forumId":1011,"title":"怎么留住销售呀","replyNumber":1,"itemType":3,"rewardCoin":0,"groupType":2,"haveContent":1,"canAnswer":1},{"digitalSignature":"","postId":592,"author":"71","viewerNickname":"用友汽车运行","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180629132550.png?Expires=1530267475&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=oZBHcm+FIFUIK9WntcXSWFRBCHE=&x-oss-process=image/format,jpg/circle,r_3000","forumId":1011,"title":"家居车哪个比较好","replyNumber":2,"itemType":3,"rewardCoin":0,"groupType":2,"haveContent":1,"canAnswer":1},{"digitalSignature":"","postId":592,"author":"71","viewerNickname":"用友汽车运行","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180629132550.png?Expires=1530267475&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=oZBHcm+FIFUIK9WntcXSWFRBCHE=&x-oss-process=image/format,jpg/circle,r_3000","forumId":1011,"title":"家居车哪个比较好","replyNumber":2,"itemType":3,"rewardCoin":0,"groupType":2,"haveContent":1,"canAnswer":1}]
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
         * postId : 593
         * author : 212
         * viewerNickname : ycb189128
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180629150812.png?Expires=1530267475&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=oWrEI6Xc2VPU7yxFZUk8JMbvLTc=&x-oss-process=image/format,jpg/circle,r_3000
         * forumId : 1011
         * title : 怎么留住销售呀
         * replyNumber : 1
         * itemType : 3
         * rewardCoin : 0.0
         * groupType : 2
         * haveContent : 1
         * canAnswer : 1
         */

        private String digitalSignature;
        private String postId;
        private String author;
        private String viewerNickname;
        private String avatar;
        private int forumId;
        private String title;
        private int replyNumber;
        private int itemType;
        private double rewardCoin;
        private int groupType;
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

        public int getReplyNumber() {
            return replyNumber;
        }

        public void setReplyNumber(int replyNumber) {
            this.replyNumber = replyNumber;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public double getRewardCoin() {
            return rewardCoin;
        }

        public void setRewardCoin(double rewardCoin) {
            this.rewardCoin = rewardCoin;
        }

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
            this.groupType = groupType;
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
