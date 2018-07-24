package com.yonyou.friendsandaargang.forum.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/20.
 */

public class Follow {


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
         * postId : 1000000461
         * type : 10051002
         * typeDesc : 帖子
         * authorId : 1000007
         * forumId : 1002
         * forumName : 金融论坛
         * brandName : 现代
         * title : 帖子我也要上头条！！！
         * content : 哈哈哈哈哈哈哈哈哈哈哈哈
         * 发张图片就想上头条，想多了吧
         * [图片]
         * <p>
         * readNumber : 9
         * isDigestPost : 0
         * isTop : 1
         * itemType : 1
         * postDate : 2018-03-16 11:19:53
         * attachmentList : [{"attachmentId":1416,"attachmentName":"1000007-20180316111830.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000007-20180316111830.png?Expires=1521548129&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=YYHeqq5jrxDxSJ6BxjLYEsLNiTY%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000461,"type":10121001,"uploadDate":"2018-03-16"}]
         */

        private String digitalSignature;
        private String postId;
        private int type;
        private String typeDesc;
        private int authorId;
        private int forumId;
        private String forumName;
        private String title;
        private String content;
        private String readNumber;
        private int isDigestPost;
        private int isTop;
        private int itemType;
        private String postDate;
        private String brandName;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        private List<AttachmentListBean> attachmentList;


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

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }

        public int getForumId() {
            return forumId;
        }

        public void setForumId(int forumId) {
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

        public String getReadNumber() {
            return readNumber;
        }

        public void setReadNumber(String readNumber) {
            this.readNumber = readNumber;
        }

        public int getIsDigestPost() {
            return isDigestPost;
        }

        public void setIsDigestPost(int isDigestPost) {
            this.isDigestPost = isDigestPost;
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

        public static class AttachmentListBean {
            /**
             * attachmentId : 1416
             * attachmentName : 1000007-20180316111830.png
             * attachmentUrl : https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000007-20180316111830.png?Expires=1521548129&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=YYHeqq5jrxDxSJ6BxjLYEsLNiTY%3D&x-oss-process=image%2Fresize%2Cw_200
             * postId : 1000000461
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
}
