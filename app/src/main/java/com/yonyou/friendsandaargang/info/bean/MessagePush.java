package com.yonyou.friendsandaargang.info.bean;

/**
 * Created by shibing on 18/4/23.
 */

public class MessagePush {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"answerMessagePush":"1","digitalSignature":"","fansMessagePush":"1","forumMessagePush":"1","fundMessagePush":"1","systemMessagePush":"1"}
     */

    /*private int returnCode;
    private String returnMsg;
    private ContentBean content;*/

    /*public int getReturnCode() {
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
    }*/

   /* public void setContent(ContentBean content) {
        this.content = content;
    }*/

//    public static class ContentBean {
    /**
     * answerMessagePush : 1
     * digitalSignature :
     * fansMessagePush : 1
     * forumMessagePush : 1
     * fundMessagePush : 1
     * systemMessagePush : 1
     */

    private String answerMessagePush;
    private String digitalSignature;
    private String fansMessagePush;
    private String forumMessagePush;
    private String fundMessagePush;
    private String systemMessagePush;
    private String channelMessagePush;

    public String getChannelMessagePush() {
        return channelMessagePush;
    }

    public void setChannelMessagePush(String channelMessagePush) {
        this.channelMessagePush = channelMessagePush;
    }

    public String getAnswerMessagePush() {
        return answerMessagePush;
    }

    public void setAnswerMessagePush(String answerMessagePush) {
        this.answerMessagePush = answerMessagePush;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public String getFansMessagePush() {
        return fansMessagePush;
    }

    public void setFansMessagePush(String fansMessagePush) {
        this.fansMessagePush = fansMessagePush;
    }

    public String getForumMessagePush() {
        return forumMessagePush;
    }

    public void setForumMessagePush(String forumMessagePush) {
        this.forumMessagePush = forumMessagePush;
    }

    public String getFundMessagePush() {
        return fundMessagePush;
    }

    public void setFundMessagePush(String fundMessagePush) {
        this.fundMessagePush = fundMessagePush;
    }

    public String getSystemMessagePush() {
        return systemMessagePush;
    }

    public void setSystemMessagePush(String systemMessagePush) {
        this.systemMessagePush = systemMessagePush;
    }
}
//}
