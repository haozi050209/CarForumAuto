package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/4/2.
 */

public class HotSearch {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : ["年会","大","浠曦","哈哈","大嘴猴","头条新闻","ze","z","zer","四轮"]
     */

    private int returnCode;
    private String returnMsg;

    private String[] content;

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

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }
}
