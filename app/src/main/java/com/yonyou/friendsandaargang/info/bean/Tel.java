package com.yonyou.friendsandaargang.info.bean;

/**
 * Created by shibing on 18/4/11.
 */

public class Tel {

   /* {
        "returnCode": 0,
            "returnMsg": "success",
            "content": "666666666666"
    }*/


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
