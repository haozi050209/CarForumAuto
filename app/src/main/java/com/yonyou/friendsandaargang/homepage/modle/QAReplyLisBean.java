package com.yonyou.friendsandaargang.homepage.modle;

import java.util.List;

/**
 * Created by shibing on 18/6/1.
 */

public class QAReplyLisBean {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"replyId":389,"replierId":62,"content":"回复第一下","nickname":"浠曦2号","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180530140145.png?Expires=1527849332&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=zLv9tGMncc3AGyMTtnhpf8sGjoo%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","thumbupNumber":3,"thumbuped":1,"isSelected":1,"replyDate":"2018-05-24 16:53:15"},{"replyId":390,"replierId":62,"content":"回复第二下","nickname":"浠曦2号","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180530140145.png?Expires=1527849332&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=zLv9tGMncc3AGyMTtnhpf8sGjoo%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","thumbupNumber":0,"thumbuped":0,"isSelected":0,"replyDate":"2018-05-24 16:53:21"},{"replyId":395,"replierId":9,"content":"啊啊啊哈哈哈","nickname":"Zerowin","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180329175104.png?Expires=1527850005&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=I3Ny49Es24MSNV%2B8AgXkP6AOeIA%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","thumbupNumber":0,"thumbuped":0,"isSelected":0,"replyDate":"2018-05-25 11:37:56"}]
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
         * replyId : 389
         * replierId : 62
         * content : 回复第一下
         * nickname : 浠曦2号
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180530140145.png?Expires=1527849332&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=zLv9tGMncc3AGyMTtnhpf8sGjoo%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * thumbupNumber : 3
         * thumbuped : 1
         * isSelected : 1
         * replyDate : 2018-05-24 16:53:15
         */

        private String replyId;
        private int replierId;
        private String content;
        private String nickname;
        private String avatar;
        private String thumbupNumber;
        private int thumbuped;
        private int isSelected;
        private String replyDate;

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getThumbupNumber() {
            return thumbupNumber;
        }

        public void setThumbupNumber(String thumbupNumber) {
            this.thumbupNumber = thumbupNumber;
        }

        public int getThumbuped() {
            return thumbuped;
        }

        public void setThumbuped(int thumbuped) {
            this.thumbuped = thumbuped;
        }

        public int getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(int isSelected) {
            this.isSelected = isSelected;
        }

        public String getReplyDate() {
            return replyDate;
        }

        public void setReplyDate(String replyDate) {
            this.replyDate = replyDate;
        }
    }
}
