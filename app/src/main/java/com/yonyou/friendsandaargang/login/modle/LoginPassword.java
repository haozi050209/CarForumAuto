package com.yonyou.friendsandaargang.login.modle;

/**
 * Created by shibing on 18/3/14.
 */

public class LoginPassword {


    /**
     * returnCode : 10021001
     * returnMsg : 用户名或手机号码不存在
     * content : {}
     */

    private int returnCode;
    private String returnMsg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
    }
}
