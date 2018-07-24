package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/6/12.
 */

public class QaUserAnswerBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":514,"authorId":62,"author":"浠曦2号","viewerNickname":"用友汽车皮尺部","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1528800135&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=%2B16MZhyyqgg2GpViOqQbaA2B%2BVc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1011,"title":"如何找到更多潜客？","content":"测试回答1","itemType":3,"postDate":"2018-06-07 15:14:54","rewardCoin":0,"isSelected":0},{"digitalSignature":"","postId":514,"authorId":62,"author":"浠曦2号","viewerNickname":"用友汽车皮尺部","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1528800135&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=%2B16MZhyyqgg2GpViOqQbaA2B%2BVc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1011,"title":"如何找到更多潜客？","content":"测试回答","itemType":3,"postDate":"2018-06-07 15:14:54","rewardCoin":0,"isSelected":0},{"digitalSignature":"","postId":514,"authorId":62,"author":"浠曦2号","viewerNickname":"用友汽车皮尺部","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1528800135&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=%2B16MZhyyqgg2GpViOqQbaA2B%2BVc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1011,"title":"如何找到更多潜客？","content":"回答问题","itemType":3,"postDate":"2018-06-07 15:14:54","rewardCoin":0,"isSelected":0},{"digitalSignature":"","postId":514,"authorId":62,"author":"浠曦2号","viewerNickname":"用友汽车皮尺部","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1528800135&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=%2B16MZhyyqgg2GpViOqQbaA2B%2BVc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1011,"title":"如何找到更多潜客？","content":"的风格很好","itemType":3,"postDate":"2018-06-07 15:14:54","rewardCoin":0,"isSelected":0},{"digitalSignature":"","postId":514,"authorId":62,"author":"浠曦2号","viewerNickname":"用友汽车皮尺部","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1528800135&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=%2B16MZhyyqgg2GpViOqQbaA2B%2BVc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","forumId":1011,"title":"如何找到更多潜客？","content":"繁华的风格很久","itemType":3,"postDate":"2018-06-07 15:14:54","rewardCoin":0,"isSelected":0}]
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
         * postId : 514
         * authorId : 62
         * author : 浠曦2号
         * viewerNickname : 用友汽车皮尺部
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180529181527.png?Expires=1528800135&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=%2B16MZhyyqgg2GpViOqQbaA2B%2BVc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * forumId : 1011
         * title : 如何找到更多潜客？
         * content : 测试回答1
         * itemType : 3
         * postDate : 2018-06-07 15:14:54
         * rewardCoin : 0
         * isSelected : 0
         */

        private String digitalSignature;
        private String postId;
        private int authorId;
        private String author;
        private String viewerNickname;
        private String avatar;
        private int forumId;
        private String title;
        private String content;
        private int itemType;
        private String postDate;
        private int rewardCoin;
        private int isSelected;

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

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
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

        public int getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(int isSelected) {
            this.isSelected = isSelected;
        }
    }
}
