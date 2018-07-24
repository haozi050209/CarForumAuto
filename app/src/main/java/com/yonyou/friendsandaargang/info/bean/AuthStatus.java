package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/5/15.
 */

public class AuthStatus {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","authStatus":10191002,"identityType":10211001},{"digitalSignature":"","authStatus":10191002,"identityType":10211002},{"digitalSignature":"","authStatus":10191001,"identityType":10211003}]
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
         * authStatus : 10191002
         * identityType : 10211001
         */

        private String digitalSignature;
        private String authStatus;
        private String identityType;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(String authStatus) {
            this.authStatus = authStatus;
        }

        public String getIdentityType() {
            return identityType;
        }

        public void setIdentityType(String identityType) {
            this.identityType = identityType;
        }
    }
}
