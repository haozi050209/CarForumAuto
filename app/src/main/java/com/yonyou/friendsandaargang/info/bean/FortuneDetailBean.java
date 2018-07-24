package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */

public class FortuneDetailBean {

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
        private String fortuneBeforeChange;
        private String fortuneAfterChange;
        private String fortuneChange;
        private String actionCode;
        private String actionContent;
        private String createDate;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getFortuneBeforeChange() {
            return fortuneBeforeChange;
        }

        public void setFortuneBeforeChange(String fortuneBeforeChange) {
            this.fortuneBeforeChange = fortuneBeforeChange;
        }

        public String getFortuneAfterChange() {
            return fortuneAfterChange;
        }

        public void setFortuneAfterChange(String fortuneAfterChange) {
            this.fortuneAfterChange = fortuneAfterChange;
        }

        public String getFortuneChange() {
            return fortuneChange;
        }

        public void setFortuneChange(String fortuneChange) {
            this.fortuneChange = fortuneChange;
        }

        public String getActionCode() {
            return actionCode;
        }

        public void setActionCode(String actionCode) {
            this.actionCode = actionCode;
        }

        public String getActionContent() {
            return actionContent;
        }

        public void setActionContent(String actionContent) {
            this.actionContent = actionContent;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
