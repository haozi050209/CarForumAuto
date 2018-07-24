package com.yonyou.friendsandaargang.forum.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/28.
 */

public class FullReply {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"replierId":8,"content":"测试","nickname":"浠曦2号","viewerNickname":"ycb","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/null?Expires=1527425565&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=5qhvrPL86CvC2l6o9IfgRwdcw0o=&x-oss-process=image/format,jpg/circle,r_3000","thumbupNumber":0,"realNameAuth":10081001,"thumbuped":0,"replyList":[{"replyId":97,"replierId":8,"content":"测试","nickname":"浠曦2号","replyDate":"2018-03-27 15:01:01"},{"replyId":98,"replierId":7,"content":"测试","nickname":"浠曦1号","replyDate":"2018-03-27 15:01:09"},{"replyId":422,"replierId":71,"content":"回复","nickname":"ycb","directRepliedUserId":8,"directRepliedNickname":"浠曦2号","directRepliedContent":"测试","replyDate":"2018-05-27 19:57:05"},{"replyId":423,"replierId":71,"content":"回复","nickname":"ycb","directRepliedUserId":8,"directRepliedNickname":"浠曦2号","directRepliedContent":"测试","replyDate":"2018-05-27 19:57:16"},{"replyId":424,"replierId":71,"content":"回复","nickname":"ycb","directRepliedUserId":7,"directRepliedNickname":"浠曦1号","directRepliedContent":"测试","replyDate":"2018-05-27 19:57:26"}],"replyDate":"2018-03-27 11:25:51"}
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
         * replierId : 8
         * content : 测试
         * nickname : 浠曦2号
         * viewerNickname : ycb
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/null?Expires=1527425565&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=5qhvrPL86CvC2l6o9IfgRwdcw0o=&x-oss-process=image/format,jpg/circle,r_3000
         * thumbupNumber : 0
         * realNameAuth : 10081001
         * thumbuped : 0
         * replyList : [{"replyId":97,"replierId":8,"content":"测试","nickname":"浠曦2号","replyDate":"2018-03-27 15:01:01"},{"replyId":98,"replierId":7,"content":"测试","nickname":"浠曦1号","replyDate":"2018-03-27 15:01:09"},{"replyId":422,"replierId":71,"content":"回复","nickname":"ycb","directRepliedUserId":8,"directRepliedNickname":"浠曦2号","directRepliedContent":"测试","replyDate":"2018-05-27 19:57:05"},{"replyId":423,"replierId":71,"content":"回复","nickname":"ycb","directRepliedUserId":8,"directRepliedNickname":"浠曦2号","directRepliedContent":"测试","replyDate":"2018-05-27 19:57:16"},{"replyId":424,"replierId":71,"content":"回复","nickname":"ycb","directRepliedUserId":7,"directRepliedNickname":"浠曦1号","directRepliedContent":"测试","replyDate":"2018-05-27 19:57:26"}]
         * replyDate : 2018-03-27 11:25:51
         */

        private int replierId;
        private String content;
        private String nickname;
        private String viewerNickname;
        private String avatar;
        private int thumbupNumber;
        private int realNameAuth;
        private int thumbuped;
        private String replyDate;
        private List<ReplyListBean> replyList;

        public int getReplierId() {
            return replierId;
        }

        public void setReplierId(int replierId) {
            this.replierId = replierId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getViewerNickname() {
            return viewerNickname;
        }

        public void setViewerNickname(String viewerNickname) {
            this.viewerNickname = viewerNickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getThumbupNumber() {
            return thumbupNumber;
        }

        public void setThumbupNumber(int thumbupNumber) {
            this.thumbupNumber = thumbupNumber;
        }

        public int getRealNameAuth() {
            return realNameAuth;
        }

        public void setRealNameAuth(int realNameAuth) {
            this.realNameAuth = realNameAuth;
        }

        public int getThumbuped() {
            return thumbuped;
        }

        public void setThumbuped(int thumbuped) {
            this.thumbuped = thumbuped;
        }

        public String getReplyDate() {
            return replyDate;
        }

        public void setReplyDate(String replyDate) {
            this.replyDate = replyDate;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class ReplyListBean {
            /**
             * replyId : 97
             * replierId : 8
             * content : 测试
             * nickname : 浠曦2号
             * replyDate : 2018-03-27 15:01:01
             * directRepliedUserId : 8
             * directRepliedNickname : 浠曦2号
             * directRepliedContent : 测试
             */

            private String replyId;
            private String replierId;
            private String content;
            private String nickname;
            private String replyDate;
            private String directRepliedUserId;
            private String directRepliedNickname;
            private String directRepliedContent;

            public String getReplyId() {
                return replyId;
            }

            public void setReplyId(String replyId) {
                this.replyId = replyId;
            }

            public String getReplierId() {
                return replierId;
            }

            public void setReplierId(String replierId) {
                this.replierId = replierId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getReplyDate() {
                return replyDate;
            }

            public void setReplyDate(String replyDate) {
                this.replyDate = replyDate;
            }

            public String getDirectRepliedUserId() {
                return directRepliedUserId;
            }

            public void setDirectRepliedUserId(String directRepliedUserId) {
                this.directRepliedUserId = directRepliedUserId;
            }

            public String getDirectRepliedNickname() {
                return directRepliedNickname;
            }

            public void setDirectRepliedNickname(String directRepliedNickname) {
                this.directRepliedNickname = directRepliedNickname;
            }

            public String getDirectRepliedContent() {
                return directRepliedContent;
            }

            public void setDirectRepliedContent(String directRepliedContent) {
                this.directRepliedContent = directRepliedContent;
            }
        }
    }
}
