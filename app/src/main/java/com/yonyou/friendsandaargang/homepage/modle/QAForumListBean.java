package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/5/31.
 */

public class QAForumListBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","forumId":1011,"forumName":"销售专区","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/xs%402x.png?Expires=1527749289&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=ko0U4WZTeUt4VRUrJ1ZmKWxvXqc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","briefDesc":"forum1 desc","type":10241002,"postNumber":9,"followNumber":1},{"digitalSignature":"","forumId":1012,"forumName":"人事行政","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/rsxz%402x.png?Expires=1527749200&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=eqOJEjqiwuCLJMfjPUTh9KiQ6Qg%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","briefDesc":"forum2 desc","type":10241002,"postNumber":1001,"followNumber":111},{"digitalSignature":"","forumId":1013,"forumName":"财务专区","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/cw%402x.png?Expires=1527749200&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=GyZU64U2xDCrF%2BBOdyLsNa6lXHU%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","briefDesc":"forum3 desc","type":10241002,"postNumber":1224,"followNumber":111},{"digitalSignature":"","forumId":1014,"forumName":"售后服务","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/shfw%402x.png?Expires=1527749200&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=WjrALohzs4oOvLKL9NdKt1HMlWA%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","briefDesc":"forum4 desc","type":10241002,"postNumber":2460,"followNumber":106},{"digitalSignature":"","forumId":1015,"forumName":"二手车","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/esc%20copy%402x.png?Expires=1527749289&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=hoAFRSoq%2BkiO5U7kBmL%2Bs2%2BBu20%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","briefDesc":"forum5 desc","type":10241002,"postNumber":163,"followNumber":111},{"digitalSignature":"","forumId":1016,"forumName":"管理专区","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/gl%402x.png?Expires=1527749289&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=l873wXOaTIhKTiEWV1uhYYpltCs%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","briefDesc":"forum6 desc","type":10241002,"postNumber":287,"followNumber":106},{"digitalSignature":"","forumId":1017,"forumName":"保险理赔","icon":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/bxlp%402x.png?Expires=1527749289&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=neFawNn0C354KdrLwotmxAOtCEo%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","briefDesc":"forum7 desc","type":10241002,"postNumber":506,"followNumber":109}]
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
         * forumId : 1011
         * forumName : 销售专区
         * icon : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/forum-icon/xs%402x.png?Expires=1527749289&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=ko0U4WZTeUt4VRUrJ1ZmKWxvXqc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * briefDesc : forum1 desc
         * type : 10241002
         * postNumber : 9
         * followNumber : 1
         */

        private String digitalSignature;
        private String forumId;
        private String forumName;
        private String icon;
        private String briefDesc;
        private int type;
        private int postNumber;
        private int followNumber;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPostNumber() {
            return postNumber;
        }

        public void setPostNumber(int postNumber) {
            this.postNumber = postNumber;
        }

        public int getFollowNumber() {
            return followNumber;
        }

        public void setFollowNumber(int followNumber) {
            this.followNumber = followNumber;
        }
    }
}
