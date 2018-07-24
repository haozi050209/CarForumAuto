package com.yonyou.friendsandaargang.guide.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/16.
 */

public class PostBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","postId":1000000322,"type":10051002,"typeDesc":"帖子","forumName":"forum5","title":"不想说话💬。。。。","content":"还是发个图吧\n[图片]\n","readNumber":21,"isDigestPost":0,"isTop":1,"itemType":1,"postDate":"2018-03-13 14:40:47","attachmentList":[{"attachmentId":1267,"attachmentName":"1000007-20180313144037.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000007-20180313144037.png?Expires=1521442011&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=BpF1Ds%2FM9WJQ6HsE23qHB%2FO65Vk%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000322,"type":10121001,"uploadDate":"2018-03-13"}]},{"digitalSignature":"","postId":1000000431,"type":10051002,"typeDesc":"帖子","forumName":"零配件论坛","title":"要制定","content":"置顶啦\n[图片]\n[图片]\n[图片]\n","readNumber":55,"isDigestPost":0,"isTop":1,"itemType":1,"postDate":"2018-03-15 16:01:44","attachmentList":[{"attachmentId":1385,"attachmentName":"1000023-20180315160129.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000023-20180315160129.png?Expires=1521442011&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=QsDAwDaL6th5d%2FFm92GC8v2Zvik%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000431,"type":10121001,"uploadDate":"2018-03-15"}]},{"digitalSignature":"","postId":1000000320,"type":10051002,"typeDesc":"帖子","forumName":"库存管理论坛","title":"今天天气贼好","content":"今天天气贼好，温度也舒服，有了春天的味道。。。。\n[图片]\n","readNumber":6,"isDigestPost":0,"isTop":1,"itemType":1,"postDate":"2018-03-13 14:31:24","attachmentList":[{"attachmentId":1265,"attachmentName":"1000007-20180313143047.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000007-20180313143047.png?Expires=1521442011&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=HFppOD0TBG4EiJMYgsnWwhAvf88%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000320,"type":10121001,"uploadDate":"2018-03-13"}]},{"digitalSignature":"","postId":1000000480,"type":10051001,"typeDesc":"话题","forumName":"零配件论坛","title":"洗漱","content":"洗漱","readNumber":18,"isDigestPost":0,"isTop":0,"itemType":3,"postDate":"2018-03-16 15:17:40","attachmentList":[]}]
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
         * postId : 1000000322
         * type : 10051002
         * typeDesc : 帖子
         * forumName : forum5
         * title : 不想说话💬。。。。
         * content : 还是发个图吧
         [图片]

         * readNumber : 21
         * isDigestPost : 0
         * isTop : 1
         * itemType : 1
         * postDate : 2018-03-13 14:40:47
         * attachmentList : [{"attachmentId":1267,"attachmentName":"1000007-20180313144037.png","attachmentUrl":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000007-20180313144037.png?Expires=1521442011&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=BpF1Ds%2FM9WJQ6HsE23qHB%2FO65Vk%3D&x-oss-process=image%2Fresize%2Cw_200","postId":1000000322,"type":10121001,"uploadDate":"2018-03-13"}]
         */

        private String digitalSignature;
        private String postId;
        private int type;
        private String typeDesc;
        private String forumName;
        private String title;
        private String content;
        private int readNumber;
        private int isDigestPost;
        private int isTop;
        private int itemType;
        private String postDate;
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
             * attachmentId : 1267
             * attachmentName : 1000007-20180313144037.png
             * attachmentUrl : https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/1000007-20180313144037.png?Expires=1521442011&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=BpF1Ds%2FM9WJQ6HsE23qHB%2FO65Vk%3D&x-oss-process=image%2Fresize%2Cw_200
             * postId : 1000000322
             * type : 10121001
             * uploadDate : 2018-03-13
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
