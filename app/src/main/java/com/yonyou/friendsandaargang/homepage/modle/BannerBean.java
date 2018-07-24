package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/7/10.
 */

public class BannerBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","bannerId":1,"bannerUrl":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/banner/%E6%9C%80%E5%85%A8%E7%9A%84%E2%80%9C%E5%8F%8B%E8%BD%A6%E5%B8%AE%E2%80%9D%E7%A7%98%E7%B1%8D%20%282%29.png?Expires=1531214587&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KZ3cTgktsNL%2B8DcEieX0CcCHd2E%3D","title":"最全的\u201c友车帮\u201d秘籍"}]
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
         * bannerId : 1
         * bannerUrl : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/banner/%E6%9C%80%E5%85%A8%E7%9A%84%E2%80%9C%E5%8F%8B%E8%BD%A6%E5%B8%AE%E2%80%9D%E7%A7%98%E7%B1%8D%20%282%29.png?Expires=1531214587&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KZ3cTgktsNL%2B8DcEieX0CcCHd2E%3D
         * title : 最全的“友车帮”秘籍
         */

        private String digitalSignature;
        private String bannerId;
        private String bannerUrl;
        private String title;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getBannerId() {
            return bannerId;
        }

        public void setBannerId(String bannerId) {
            this.bannerId = bannerId;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
