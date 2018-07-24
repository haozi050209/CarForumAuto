package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/4/9.
 */

public class AllForums {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","forumId":1001,"forumName":"销售论坛","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1002,"forumName":"人事行政","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1003,"forumName":"财务论坛","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1004,"forumName":"售后服务","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1005,"forumName":"二手车","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1006,"forumName":"管理论坛","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1007,"forumName":"保险理赔","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1008,"forumName":"新车测评","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1009,"forumName":"爱车杂谈","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"},{"digitalSignature":"","forumId":1010,"forumName":"车辆改装","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000"}]
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
         * icon : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/logo%402x.png?Expires=1523258999&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=e%2BNFEJMQwVYQj3lR7EToP3eOq9E%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         */

        private String digitalSignature;
        private String forumId;
        private String forumName;
        private String icon;

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
    }
}
