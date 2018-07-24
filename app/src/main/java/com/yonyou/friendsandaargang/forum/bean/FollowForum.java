package com.yonyou.friendsandaargang.forum.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/29.
 */

public class FollowForum {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","forumId":1001,"forumName":"零配件论坛","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo@2x.png?Expires=1522314665&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=pdfNcBUI/jl8hrnsrHmOebRIKKU=&x-oss-process=image/format,jpg/circle,r_3000","briefDesc":"forum1 desc","followed":1,"postNumber":2000,"followNumber":111,"brandList":[{"digitalSignature":"","brandId":1001,"brandName":"长安铃木","postNum":2}],"notice":{"noticeId":1001,"title":"最新通知"}}
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
         * forumId : 1001
         * forumName : 零配件论坛
         * icon : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo@2x.png?Expires=1522314665&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=pdfNcBUI/jl8hrnsrHmOebRIKKU=&x-oss-process=image/format,jpg/circle,r_3000
         * briefDesc : forum1 desc
         * followed : 1
         * postNumber : 2000
         * followNumber : 111
         * brandList : [{"digitalSignature":"","brandId":1001,"brandName":"长安铃木","postNum":2}]
         * notice : {"noticeId":1001,"title":"最新通知"}
         */

        private String digitalSignature;
        private int forumId;
        private String forumName;
        private String icon;
        private String briefDesc;
        private int followed;
        private int postNumber;
        private int followNumber;
        private NoticeBean notice;
        private List<BrandListBean> brandList;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getBriefDesc() {
            return briefDesc;
        }

        public void setBriefDesc(String briefDesc) {
            this.briefDesc = briefDesc;
        }

        public int getFollowed() {
            return followed;
        }

        public void setFollowed(int followed) {
            this.followed = followed;
        }

        public int getPostNumber() {
            return postNumber;
        }

        public void setPostNumber(int postNumber) {
            this.postNumber = postNumber;
        }

        public int getFollowNumber() {
            return followNumber;
        }

        public void setFollowNumber(int followNumber) {
            this.followNumber = followNumber;
        }

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public List<BrandListBean> getBrandList() {
            return brandList;
        }

        public void setBrandList(List<BrandListBean> brandList) {
            this.brandList = brandList;
        }

        public static class NoticeBean {
            /**
             * noticeId : 1001
             * title : 最新通知
             */

            private int noticeId;
            private String title;

            public int getNoticeId() {
                return noticeId;
            }

            public void setNoticeId(int noticeId) {
                this.noticeId = noticeId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class BrandListBean {
            /**
             * digitalSignature :
             * brandId : 1001
             * brandName : 长安铃木
             * postNum : 2
             */

            private String digitalSignature;
            private String brandId;
            private String brandName;
            private String postNum;

            public String getDigitalSignature() {
                return digitalSignature;
            }

            public void setDigitalSignature(String digitalSignature) {
                this.digitalSignature = digitalSignature;
            }

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getPostNum() {
                return postNum;
            }

            public void setPostNum(String postNum) {
                this.postNum = postNum;
            }
        }
    }
}
