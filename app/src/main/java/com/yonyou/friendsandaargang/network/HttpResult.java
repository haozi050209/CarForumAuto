package com.yonyou.friendsandaargang.network;

/**
 * Created by shibing on 18/3/15.
 */

public class HttpResult<T> {

    private int returnCode;
    private String returnMsg;
    private T content;

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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
