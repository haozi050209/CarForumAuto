package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/6/1.
 */

public class BigForumBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","forumId":1021,"forumName":"售后服务","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/shfw%402x.png?Expires=1527826131&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=kHbfAw8MQRrCPAaZNEMQ6qdrRQU%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":2455},{"digitalSignature":"","forumId":1018,"forumName":"销售专区","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/xs%402x.png?Expires=1527826133&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=JHmeFbhME3r%2B5iE%2BA6yAOF%2FbYO0%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":2024},{"digitalSignature":"","forumId":1020,"forumName":"财务专区","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/cw%402x.png?Expires=1527826131&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=2CBrfBPOOrxwzQSS%2B5piYpGLd7k%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":1222},{"digitalSignature":"","forumId":1019,"forumName":"人事行政","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/rsxz%402x.png?Expires=1527826131&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=ag6N%2BNSHcuGYmecGF84%2Ft2znSK0%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":1000},{"digitalSignature":"","forumId":1024,"forumName":"保险理赔","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/bxlp%402x.png?Expires=1527826133&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=mGNjNZfwTWsBkEdoSwa4blAKzCU%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":504},{"digitalSignature":"","forumId":1023,"forumName":"管理专区","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/gl%402x.png?Expires=1527826133&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=BGTwZWdY29hE%2B%2FZgpbXI9ESKYw8%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":287},{"digitalSignature":"","forumId":1022,"forumName":"二手车","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/esc%20copy%402x.png?Expires=1527826133&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=41EwJeUp2YtIZdL5a4V9uSvDXH4%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","postNumber":159}]
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
         * forumId : 1021
         * forumName : 售后服务
         * icon : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/shfw%402x.png?Expires=1527826131&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=kHbfAw8MQRrCPAaZNEMQ6qdrRQU%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * postNumber : 2455
         */

        private String digitalSignature;
        private String forumId;
        private String forumName;
        private String icon;
        private String postNumber;

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

        public String getPostNumber() {
            return postNumber;
        }

        public void setPostNumber(String postNumber) {
            this.postNumber = postNumber;
        }
    }
}
