package com.yonyou.friendsandaargang.login.modle;

/**
 * Created by shibing on 18/2/26.
 */

public class CodeBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content :
     */

    private int returnCode;
    private String returnMsg;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
