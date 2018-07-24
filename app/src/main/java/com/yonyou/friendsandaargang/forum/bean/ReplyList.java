package com.yonyou.friendsandaargang.forum.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/23.
 */

public class ReplyList {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"replyId":3,"replierId":3,"content":"dcvbbnjj的风格更好","nickname":"ycb222988","avatar":"https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/null?Expires=1521801948&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=eAcD9Kwhywnb9TP6bwOxV0PcWcc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","replyNumber":2,"thumbupNumber":0,"realNameAuth":10081001,"thumbuped":0,"replyList":[{"replyId":5,"replierId":3,"content":"大概会斤斤计较司法改革后","nickname":"ycb222988"},{"replyId":4,"replierId":3,"content":"对方还好结局","nickname":"ycb222988"}],"replyDate":"2018-03-23 23:30:18"}]
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
         * replyId : 3
         * replierId : 3
         * content : dcvbbnjj的风格更好
         * nickname : ycb222988
         * avatar : https://yonyouqiche-community-app-images.oss-cn-shanghai.aliyuncs.com/null?Expires=1521801948&OSSAccessKeyId=LTAIovCX7SX3Z3Lt&Signature=eAcD9Kwhywnb9TP6bwOxV0PcWcc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * replyNumber : 2
         * thumbupNumber : 0
         * realNameAuth : 10081001
         * thumbuped : 0
         * replyList : [{"replyId":5,"replierId":3,"content":"大概会斤斤计较司法改革后","nickname":"ycb222988"},{"replyId":4,"replierId":3,"content":"对方还好结局","nickname":"ycb222988"}]
         * replyDate : 2018-03-23 23:30:18
         */
        private String replyId;
        private String replierId;
        private String content;
        private String nickname;
        private String avatar;
        private int replyNumber;
        private String thumbupNumber;
        private int realNameAuth;
        private int thumbuped;
        private String replyDate;
        private List<ReplyListBean> replyList;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getReplyNumber() {
            return replyNumber;
        }

        public void setReplyNumber(int replyNumber) {
            this.replyNumber = replyNumber;
        }

        public String getThumbupNumber() {
            return thumbupNumber;
        }

        public void setThumbupNumber(String thumbupNumber) {
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
             * replyId : 456
             * replierId : 71
             * content : 我来回复下
             * nickname : ycb
             * directRepliedUserId : 9
             * directRepliedNickname : Zerowin
             * directRepliedContent : 啊啊啊
             * replyDate : 2018-05-28 16:23:28
             */

            private int replyId;
            private String replierId;
            private String content;
            private String nickname;
            private String directRepliedUserId;
            private String directRepliedNickname;
            private String directRepliedContent;
            private String replyDate;

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
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

            public String getReplyDate() {
                return replyDate;
            }

            public void setReplyDate(String replyDate) {
                this.replyDate = replyDate;
            }

        }
    }
}
