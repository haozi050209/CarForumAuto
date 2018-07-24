package com.yonyou.friendsandaargang.info.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shibing on 18/6/12.
 */

public class QaToMeBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":524,"author":"ycb950348","avatar":"","forumId":1021,"title":"可大咖提问1","itemType":3,"postDate":"2018-06-13 14:18:29","rewardCoin":0},{"digitalSignature":"","postId":525,"author":"ycb950348","avatar":"","forumId":1021,"title":"阿是风风光光","itemType":3,"postDate":"2018-06-13 14:19:29","rewardCoin":0}]
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

    public static class ContentBean implements Serializable {
        /**
         * digitalSignature :
         * postId : 524
         * author : ycb950348
         * avatar :
         * forumId : 1021
         * title : 可大咖提问1
         * itemType : 3
         * postDate : 2018-06-13 14:18:29
         * rewardCoin : 0
         */

        private String digitalSignature;
        private String postId;
        private String author;
        private String avatar;
        private int forumId;
        private String title;
        private int itemType;
        private String postDate;
        private int rewardCoin;

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
