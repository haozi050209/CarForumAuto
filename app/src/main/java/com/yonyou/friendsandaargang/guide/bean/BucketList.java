package com.yonyou.friendsandaargang.guide.bean;

import java.util.List;

/**
 * Created by shibing on 18/4/16.
 */

public class BucketList {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"bucketList":[{"bucketId":1,"attachmentType":10121001,"bucketName":"yonyou-community-app-images","endPoint":"https://oss-cn-hangzhou.aliyuncs.com","remark":"保存图片"},{"bucketId":2,"attachmentType":10121002,"bucketName":"yonyou-community-app-videos","endPoint":"https://oss-cn-hangzhou.aliyuncs.com","remark":"保存视频"},{"bucketId":3,"attachmentType":10121003,"bucketName":"yonyou-community-app-databank","endPoint":"https://oss-cn-hangzhou.aliyuncs.com","remark":"资料库"}],"expiration":"2018-04-16T04:04:00Z","accessKeyId":"STS.Dqb9dG2MaNzwRjD1BoPLDkReU","accessKeySecret":"4F9upk8WZsiFaqUNRUY1CkKHUsWryqi4hRtmUGYkafMo","securityToken":"CAIS1gN1q6Ft5B2yfSjIpbLXct7z35JA+bicUEz11UI6XMNohJfOtzz2IHxPdXBgBeEWt/Q1mWFV6/4ZlrlpTJtIfkHfdsp36M0PqVv5OyBLLUBxqe5qsoasPETOIdORvqaLERKTLr70fvOqdCri9Etayqf7cjOPRkGsNYbz57dsctUQWHvmD1pBH8wECQZ+6q17MmDKZ9KsKQKo4BLXF1E6lgd4hH9yzq69z8aAiH/Zl0ao8vIJgI7zL5O4FWQFXvFFXsyywfZ9e4fYzSdU8GIqzqpti7Fe8jq1frj/TFBa+EeBKPGGsI8tfh5+YLk9ButPq/35iPt5pv2WnpzvjhdINO5YSEaeTYu7kszfA7HrZJRrZLH9KnPQl9uIM5zotQI4cDcSLBsPet8oJXJrTl5OBTjRMf2g40uYIFXhGbmC1rAxzN9+yFDt4NyLO0nKSq2BlDgZNpI7dCFpPhUNj2v6af1EIU4eYlRhFrGICI50aRVDr6L0rEjbTjUl0HBbsvrlILH0wvlDON+jDskbiNVNPskW6DcQIg6pG+70uCA9b3d4RLta6q7pNKKk5aWNqOfpOrKdW6lX5gQKKGCL8nvQFCsLN072/cYtdF6c5t/c1rwOHVynhs+VjBqAAUXd3q8nK53FNAQEzadGMrJ3hVqwZraGhnqJtVMpBtVCl3gpTcANKTcZSqdfMNE6heOOsZFhjNpsD0rvd4kd0Pj4qUiAaVh3Za4APDZ+0JRJ4sjwXEXjpICeAVqKEo6xyxYG86ABBahz6lyyRAOZF1UT4hTmc7mcvZSFSsy0n/O4"}
     */

    private int returnCode;
    private String returnMsg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * bucketList : [{"bucketId":1,"attachmentType":10121001,"bucketName":"yonyou-community-app-images","endPoint":"https://oss-cn-hangzhou.aliyuncs.com","remark":"保存图片"},{"bucketId":2,"attachmentType":10121002,"bucketName":"yonyou-community-app-videos","endPoint":"https://oss-cn-hangzhou.aliyuncs.com","remark":"保存视频"},{"bucketId":3,"attachmentType":10121003,"bucketName":"yonyou-community-app-databank","endPoint":"https://oss-cn-hangzhou.aliyuncs.com","remark":"资料库"}]
         * expiration : 2018-04-16T04:04:00Z
         * accessKeyId : STS.Dqb9dG2MaNzwRjD1BoPLDkReU
         * accessKeySecret : 4F9upk8WZsiFaqUNRUY1CkKHUsWryqi4hRtmUGYkafMo
         * securityToken : CAIS1gN1q6Ft5B2yfSjIpbLXct7z35JA+bicUEz11UI6XMNohJfOtzz2IHxPdXBgBeEWt/Q1mWFV6/4ZlrlpTJtIfkHfdsp36M0PqVv5OyBLLUBxqe5qsoasPETOIdORvqaLERKTLr70fvOqdCri9Etayqf7cjOPRkGsNYbz57dsctUQWHvmD1pBH8wECQZ+6q17MmDKZ9KsKQKo4BLXF1E6lgd4hH9yzq69z8aAiH/Zl0ao8vIJgI7zL5O4FWQFXvFFXsyywfZ9e4fYzSdU8GIqzqpti7Fe8jq1frj/TFBa+EeBKPGGsI8tfh5+YLk9ButPq/35iPt5pv2WnpzvjhdINO5YSEaeTYu7kszfA7HrZJRrZLH9KnPQl9uIM5zotQI4cDcSLBsPet8oJXJrTl5OBTjRMf2g40uYIFXhGbmC1rAxzN9+yFDt4NyLO0nKSq2BlDgZNpI7dCFpPhUNj2v6af1EIU4eYlRhFrGICI50aRVDr6L0rEjbTjUl0HBbsvrlILH0wvlDON+jDskbiNVNPskW6DcQIg6pG+70uCA9b3d4RLta6q7pNKKk5aWNqOfpOrKdW6lX5gQKKGCL8nvQFCsLN072/cYtdF6c5t/c1rwOHVynhs+VjBqAAUXd3q8nK53FNAQEzadGMrJ3hVqwZraGhnqJtVMpBtVCl3gpTcANKTcZSqdfMNE6heOOsZFhjNpsD0rvd4kd0Pj4qUiAaVh3Za4APDZ+0JRJ4sjwXEXjpICeAVqKEo6xyxYG86ABBahz6lyyRAOZF1UT4hTmc7mcvZSFSsy0n/O4
         */

        private String expiration;
        private String accessKeyId;
        private String accessKeySecret;
        private String securityToken;
        private List<BucketListBean> bucketList;

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }

        public List<BucketListBean> getBucketList() {
            return bucketList;
        }

        public void setBucketList(List<BucketListBean> bucketList) {
            this.bucketList = bucketList;
        }

        public static class BucketListBean {
            /**
             * bucketId : 1
             * attachmentType : 10121001
             * bucketName : yonyou-community-app-images
             * endPoint : https://oss-cn-hangzhou.aliyuncs.com
             * remark : 保存图片
             */

            private int bucketId;
            private int attachmentType;
            private String bucketName;
            private String endPoint;
            private String remark;

            public int getBucketId() {
                return bucketId;
            }

            public void setBucketId(int bucketId) {
                this.bucketId = bucketId;
            }

            public int getAttachmentType() {
                return attachmentType;
            }

            public void setAttachmentType(int attachmentType) {
                this.attachmentType = attachmentType;
            }

            public String getBucketName() {
                return bucketName;
            }

            public void setBucketName(String bucketName) {
                this.bucketName = bucketName;
            }

            public String getEndPoint() {
                return endPoint;
            }

            public void setEndPoint(String endPoint) {
                this.endPoint = endPoint;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
