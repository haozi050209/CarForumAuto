package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/21.
 */

public class SyatemMessage {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","messageId":1004,"messageType":10141001,"messageContent":"您已经从LV4升级到LV5；","messageTitle":"等级消息","userId":1000007,"messagePreview":"【等级】您已经从LV4升级到LV5","createDate":"2018-01-22 12:00:00","readStatus":10151001,"messageSubtype":10161001},{"digitalSignature":"","messageId":1001,"messageType":10141001,"messageContent":"您已经从LV3升级到LV4；","messageTitle":"等级消息","userId":1000007,"messagePreview":"【等级】您已经从LV3升级到LV4","createDate":"2018-01-22 11:00:00","readStatus":10151001,"messageSubtype":10161001}]
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
         * messageContent : 您已经从LV4升级到LV5；
         * messageTitle : 等级消息
         * userId : 1000007
         * messagePreview : 【等级】您已经从LV4升级到LV5
         * createDate : 2018-01-22 12:00:00
         * readStatus : 10151001
         * messageSubtype : 10161001
         */

        private String digitalSignature;
        private int messageId;
        private int messageType;
        private String messageContent;
        private String messageTitle;
        private int userId;
        private String messagePreview;
        private String createDate;
        private int readStatus;
        private int messageSubtype;

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

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }

        public String getMessageTitle() {
            return messageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public int getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(int readStatus) {
            this.readStatus = readStatus;
        }

        public int getMessageSubtype() {
            return messageSubtype;
        }

        public void setMessageSubtype(int messageSubtype) {
            this.messageSubtype = messageSubtype;
        }
    }
}
