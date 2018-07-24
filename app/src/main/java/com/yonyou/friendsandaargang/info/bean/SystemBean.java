package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */

public class SystemBean {

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

        private String digitalSignature;
        private String custId;
        private String productId;
        private String prdName;
        private String prdCode;
        private String sort;

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

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPrdName() {
            return prdName;
        }

        public void setPrdName(String prdName) {
            this.prdName = prdName;
        }

        public String getPrdCode() {
            return prdCode;
        }

        public void setPrdCode(String prdCode) {
            this.prdCode = prdCode;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
