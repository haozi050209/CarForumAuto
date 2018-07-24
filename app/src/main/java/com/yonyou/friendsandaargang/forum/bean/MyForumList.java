package com.yonyou.friendsandaargang.forum.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/20.
 */

public class MyForumList {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","forumId":1004,"forumName":"二手车论坛","icon":"icon address 3","briefDesc":"forum4 desc"},{"digitalSignature":"","forumId":1005,"forumName":"forum5","icon":"icon address 5","briefDesc":"forum5 desc"},{"digitalSignature":"","forumId":1002,"forumName":"金融论坛","icon":"icon address 2","briefDesc":"forum2 desc"},{"digitalSignature":"","forumId":1003,"forumName":"库存管理论坛","icon":"icon address 4","briefDesc":"forum3 desc"}]
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
         * forumId : 1004
         * forumName : 二手车论坛
         * icon : icon address 3
         * briefDesc : forum4 desc
         */

        private String digitalSignature;
        private String forumId;
        private String forumName;
        private String icon;
        private String briefDesc;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getForumId() {
            return forumId;
        }

        public void setForumId(String forumId) {
            this.forumId = forumId;
        }

        public String getForumName() {
            return forumName;
        }

        public void setForumName(String forumName) {
            this.forumName = forumName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getBriefDesc() {
            return briefDesc;
        }

        public void setBriefDesc(String briefDesc) {
            this.briefDesc = briefDesc;
        }
    }
}
