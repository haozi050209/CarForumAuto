package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */

public class RecordBean {

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
        private String recordId;
        private String productId;
        private String custId;
        private String jobId;
        private String acceptDate;
        private String status;
        private String recordContent;
        private String lastSolution;
        private String lastUpdateDate;
        private String infoccRdid;
        private String recordRemind;
        private String source;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getAcceptDate() {
            return acceptDate;
        }

        public void setAcceptDate(String acceptDate) {
            this.acceptDate = acceptDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRecordContent() {
            return recordContent;
        }

        public void setRecordContent(String recordContent) {
            this.recordContent = recordContent;
        }

        public String getLastSolution() {
            return lastSolution;
        }

        public void setLastSolution(String lastSolution) {
            this.lastSolution = lastSolution;
        }

        public String getLastUpdateDate() {
            return lastUpdateDate;
        }

        public void setLastUpdateDate(String lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
        }

        public String getInfoccRdid() {
            return infoccRdid;
        }

        public void setInfoccRdid(String infoccRdid) {
            this.infoccRdid = infoccRdid;
        }

        public String getRecordRemind() {
            return recordRemind;
        }

        public void setRecordRemind(String recordRemind) {
            this.recordRemind = recordRemind;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
