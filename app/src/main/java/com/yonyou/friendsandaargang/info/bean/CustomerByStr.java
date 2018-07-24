package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/5/15.
 */

public class CustomerByStr {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","custId":10000992,"compName":"陕西荣爵汽车销售服务有限公司","entityCode":"2100326","shortName":"陕西荣爵"},{"digitalSignature":"","custId":10001371,"compName":"榆林市荣渡商贸有限公司","entityCode":"2100478","shortName":"陕西荣渡"}]
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
         * custId : 10000992
         * compName : 陕西荣爵汽车销售服务有限公司
         * entityCode : 2100326
         * shortName : 陕西荣爵
         */

        private String digitalSignature;
        private String custId;
        private String compName;
        private String entityCode;
        private String shortName;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getCompName() {
            return compName;
        }

        public void setCompName(String compName) {
            this.compName = compName;
        }

        public String getEntityCode() {
            return entityCode;
        }

        public void setEntityCode(String entityCode) {
            this.entityCode = entityCode;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }
}
