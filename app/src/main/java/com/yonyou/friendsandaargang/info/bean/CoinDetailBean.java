package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */

public class CoinDetailBean {

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
        private String coinBeforeChange;
        private String coinAfterChange; //变动后金币值
        private String coinChange;//此次变动金币值
        private String actionCode;
        private String actionContent;
        private String createDate;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getCoinBeforeChange() {
            return coinBeforeChange;
        }

        public void setCoinBeforeChange(String coinBeforeChange) {
            this.coinBeforeChange = coinBeforeChange;
        }

        public String getCoinAfterChange() {
            return coinAfterChange;
        }

        public void setCoinAfterChange(String coinAfterChange) {
            this.coinAfterChange = coinAfterChange;
        }

        public String getCoinChange() {
            return coinChange;
        }

        public void setCoinChange(String coinChange) {
            this.coinChange = coinChange;
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
