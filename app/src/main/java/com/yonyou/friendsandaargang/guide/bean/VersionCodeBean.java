package com.yonyou.friendsandaargang.guide.bean;

/**
 * Created by shibing on 18/7/16.
 */

public class VersionCodeBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","versionCode":1,"versionName":"1.0"}
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
         * versionCode : 1
         * versionName : 1.0
         */

        private String digitalSignature;
        private int versionCode;
        private String versionName;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }
    }
}
