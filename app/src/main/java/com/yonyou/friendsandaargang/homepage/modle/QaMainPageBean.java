package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/5/30.
 */

public class QaMainPageBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","forumId":1014,"forumName":"售后服务","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/shfw%402x.png?Expires=1527676587&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=a6zf2e69vxc%2BO18AoPRwZoOlFIg%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":2460},{"digitalSignature":"","forumId":1013,"forumName":"财务专区","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/cw%402x.png?Expires=1527676587&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=UoXN%2Fp2LV4EE5JwHCl8FjDEcgZ0%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":1224},{"digitalSignature":"","forumId":1012,"forumName":"人事行政","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/rsxz%402x.png?Expires=1527676587&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=UKls3E64%2BRC5G9VSv96xFmM1uAs%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":1001}]
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
        public ContentBean(String digitalSignature, String forumId, String forumName, String icon, int postNumber) {
            this.digitalSignature = digitalSignature;
            this.forumId = forumId;
            this.forumName = forumName;
            this.icon = icon;
            this.postNumber = postNumber;
        }

        /**
         * digitalSignature :
         * forumId : 1014
         * forumName : 售后服务
         * icon : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/shfw%402x.png?Expires=1527676587&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=a6zf2e69vxc%2BO18AoPRwZoOlFIg%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * postNumber : 2460
         */



        private String digitalSignature;
        private String forumId;
        private String forumName;
        private String icon;
        private int postNumber;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getPostNumber() {
            return postNumber;
        }

        public void setPostNumber(int postNumber) {
            this.postNumber = postNumber;
        }
    }
}
