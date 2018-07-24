package com.yonyou.friendsandaargang.forum.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/20.
 */

public class EssenceListTwo {


    private List<ContentBean> contentBeans;

    public List<ContentBean> getContentBeans() {
        return contentBeans;
    }

    public void setContentBeans(List<ContentBean> contentBeans) {
        this.contentBeans = contentBeans;
    }


    public static class ContentBean{
        /**
         * digitalSignature :
         * postId : 1000000491
         * forumName : 库存管理论坛
         * title : 哦你好好来咯
         * content : [图片]
         * [图片]
         * [图片]
         * <p>
         * readNumber : 11
         * isTop : 1
         * postDate : 2018-03-16 21:59:35
         * attachmentList : [{"attachmentId":1436,"attachmentName":"1000021-20180316215850.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000021-20180316215850.png?Expires=1521458713&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=a%2FSHN5DYyd1C6ptuBjCL%2F0vprCk%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000491,"type":10121001,"uploadDate":"2018-03-16"},{"attachmentId":1437,"attachmentName":"1000021-20180316215906.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000021-20180316215906.png?Expires=1521459820&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=Dga%2FLOjweHJuOc5kUxXhp7R6Nt8%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000491,"type":10121001,"uploadDate":"2018-03-16"},{"attachmentId":1438,"attachmentName":"1000021-20180316215932.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000021-20180316215932.png?Expires=1521459820&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=fixiZE0Ase3gDH4X%2FrY43Z%2Bx1mY%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000491,"type":10121001,"uploadDate":"2018-03-16"}]
         */

        private String digitalSignature;
        private int postId;
        private String forumName;
        private String title;
        private String content;
        private int readNumber;
        private int isTop;
        private String postDate;
        private List<AttachmentListBean> attachmentList;

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

        public int getReadNumber() {
            return readNumber;
        }

        public void setReadNumber(int readNumber) {
            this.readNumber = readNumber;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public List<AttachmentListBean> getAttachmentList() {
            return attachmentList;
        }

        public void setAttachmentList(List<AttachmentListBean> attachmentList) {
            this.attachmentList = attachmentList;
        }
    }


    public static class AttachmentListBean {
        /**
         * attachmentId : 1436
         * attachmentName : 1000021-20180316215850.png
         * attachmentUrl : https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000021-20180316215850.png?Expires=1521458713&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=a%2FSHN5DYyd1C6ptuBjCL%2F0vprCk%3D&x-oss-process=image%2Fresize%2Cw_200
         * postId : 1000000491
         * type : 10121001
         * uploadDate : 2018-03-16
         */

        private int attachmentId;
        private String attachmentName;
        private String attachmentUrl;
        private int postId;
        private int type;
        private String uploadDate;

        public int getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(int attachmentId) {
            this.attachmentId = attachmentId;
        }

        public String getAttachmentName() {
            return attachmentName;
        }

        public void setAttachmentName(String attachmentName) {
            this.attachmentName = attachmentName;
        }

        public String getAttachmentUrl() {
            return attachmentUrl;
        }

        public void setAttachmentUrl(String attachmentUrl) {
            this.attachmentUrl = attachmentUrl;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUploadDate() {
            return uploadDate;
        }

        public void setUploadDate(String uploadDate) {
            this.uploadDate = uploadDate;
        }
    }
}
