package com.yonyou.friendsandaargang.forum.bean;

import java.util.List;

/**
 * Created by shibing on 18/5/6.
 */

public class ForumState {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","forumId":1001,"forumName":"销售论坛","icon":"forum-icon/logo@2x.png","briefDesc":"forum1 desc","followed":1},{"digitalSignature":"","forumId":1003,"forumName":"财务论坛","icon":"forum-icon/logo@2x.png","briefDesc":"forum3 desc","followed":1},{"digitalSignature":"","forumId":1005,"forumName":"二手车","icon":"forum-icon/logo@2x.png","briefDesc":"forum5 desc","followed":1},{"digitalSignature":"","forumId":1007,"forumName":"保险理赔","icon":"forum-icon/logo@2x.png","briefDesc":"forum7 desc","followed":1},{"digitalSignature":"","forumId":1009,"forumName":"爱车杂谈","icon":"forum-icon/logo@2x.png","briefDesc":"forum7 desc","followed":1},{"digitalSignature":"","forumId":1010,"forumName":"车辆改装","icon":"forum-icon/logo@2x.png","briefDesc":"forum7 desc","followed":1},{"digitalSignature":"","forumId":1002,"forumName":"人事行政","icon":"forum-icon/logo@2x.png","briefDesc":"forum2 desc","followed":0},{"digitalSignature":"","forumId":1004,"forumName":"售后服务","icon":"forum-icon/logo@2x.png","briefDesc":"forum4 desc","followed":0},{"digitalSignature":"","forumId":1006,"forumName":"管理论坛","icon":"forum-icon/logo@2x.png","briefDesc":"forum6 desc","followed":0},{"digitalSignature":"","forumId":1008,"forumName":"新车测评","icon":"forum-icon/logo@2x.png","briefDesc":"forum7 desc","followed":0}]
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
         * forumId : 1001
         * forumName : 销售论坛
         * icon : forum-icon/logo@2x.png
         * briefDesc : forum1 desc
         * followed : 1
         */

        private String digitalSignature;
        private String forumId;
        private String forumName;
        private String icon;
        private String briefDesc;
        private int followed;

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

        public int getFollowed() {
            return followed;
        }

        public void setFollowed(int followed) {
            this.followed = followed;
        }
    }
}
