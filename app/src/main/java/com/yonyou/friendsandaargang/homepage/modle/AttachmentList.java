package com.yonyou.friendsandaargang.homepage.modle;

/**
 * Created by shibing on 18/4/9.
 */

public class AttachmentList {


    private String type;
    private String attachmentName;


    public AttachmentList(String type, String attachmentName) {
        this.type = type;
        this.attachmentName = attachmentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
}
