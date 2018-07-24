package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/4/23.
 */

public class JobList {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","jobId":1,"job":"销售助理"},{"digitalSignature":"","jobId":2,"job":"销售顾问"},{"digitalSignature":"","jobId":3,"job":"销售经理"},{"digitalSignature":"","jobId":4,"job":"服务顾问"},{"digitalSignature":"","jobId":5,"job":"前台主管"},{"digitalSignature":"","jobId":6,"job":"服务经理"},{"digitalSignature":"","jobId":7,"job":"车间主管"},{"digitalSignature":"","jobId":8,"job":"配件专员"},{"digitalSignature":"","jobId":9,"job":"配件经理"},{"digitalSignature":"","jobId":10,"job":"客服经理"},{"digitalSignature":"","jobId":11,"job":"财务经理"},{"digitalSignature":"","jobId":12,"job":"总经理"},{"digitalSignature":"","jobId":13,"job":"区域经理"},{"digitalSignature":"","jobId":14,"job":"车厂人员"},{"digitalSignature":"","jobId":15,"job":"其他"},{"digitalSignature":"","jobId":35,"job":"索赔专员"},{"digitalSignature":"","jobId":36,"job":"技术人员"},{"digitalSignature":"","jobId":37,"job":"钣喷人员"},{"digitalSignature":"","jobId":38,"job":"质检人员"},{"digitalSignature":"","jobId":39,"job":"二手车经理"},{"digitalSignature":"","jobId":40,"job":"二手车专员"},{"digitalSignature":"","jobId":41,"job":"理赔专员"},{"digitalSignature":"","jobId":42,"job":"保险专员"}]
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
         * jobId : 1
         * job : 销售助理
         */

        private String digitalSignature;
        private String jobId;
        private String job;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }
    }
}
