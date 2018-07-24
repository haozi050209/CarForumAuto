package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/21.
 */

public class MessAge {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","messageId":1004,"messageType":10141001,"messagePreview":"【等级】您已经从LV4升级到LV5","createDate":"2018-01-22 12:00:00","count":0},{"digitalSignature":"","messageId":1005,"messageType":10141002,"messagePreview":"【消费】您已消费108.00元","createDate":"2018-01-23 12:12:00","count":0},{"digitalSignature":"","messageId":10783,"messageType":10141003,"messagePreview":"\u201cycb584645\u201d开始关注您了","createDate":"2018-03-19 18:51:24","count":0},{"digitalSignature":"","messageId":10793,"messageType":10141004,"messagePreview":"\u201cycb584645\u201d在楼中楼回复了您","createDate":"2018-03-19 18:59:08","count":0},{"digitalSignature":"","messageId":1003,"messageType":10141005,"messagePreview":"\u201cliqiang\u201d回答了您的问题","createDate":"2018-01-22 11:02:00","count":0}]
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
         * messageId : 1004
         * messageType : 10141001
         * messagePreview : 【等级】您已经从LV4升级到LV5
         * createDate : 2018-01-22 12:00:00
         * count : 0
         */

        private String digitalSignature;
        private int messageId;
        private int messageType;
        private String messagePreview;
        private String createDate;
        private int count;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public String getMessagePreview() {
            return messagePreview;
        }

        public void setMessagePreview(String messagePreview) {
            this.messagePreview = messagePreview;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
