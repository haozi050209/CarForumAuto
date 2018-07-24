package com.yonyou.friendsandaargang.homepage.modle;

import com.yonyou.friendsandaargang.utils.SPTool;

import java.util.List;

/**
 * Created by shibing on 18/4/10.
 */

public class FollowPople {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","action":"Ta赞了帖子你好世纪","actionType":1001,"traceDate":"2018-04-09 19:39:40","userId":4,"nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523342398&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KpHMVmm%2B8%2FBzOUfYV6WpzvFuAvc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":1,"postId":146,"readNumber":17,"attachment":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/2-20180409143204.png?Expires=1523344294&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=YkOsj7Q77leCWugWmIAT5AD08To%3D&x-oss-process=image%2Fresize%2Cw_200","thumbuped":0},{"digitalSignature":"","action":"Ta赞了帖子你好世纪","actionType":1001,"traceDate":"2018-04-09 19:39:37","userId":4,"nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523342398&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KpHMVmm%2B8%2FBzOUfYV6WpzvFuAvc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":1,"postId":146,"readNumber":17,"attachment":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/2-20180409143204.png?Expires=1523344294&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=YkOsj7Q77leCWugWmIAT5AD08To%3D&x-oss-process=image%2Fresize%2Cw_200","thumbuped":0},{"digitalSignature":"","action":"Ta赞了帖子你好世纪","actionType":1001,"traceDate":"2018-04-09 19:39:35","userId":4,"nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523342398&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KpHMVmm%2B8%2FBzOUfYV6WpzvFuAvc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":1,"postId":146,"readNumber":17,"attachment":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/2-20180409143204.png?Expires=1523344294&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=YkOsj7Q77leCWugWmIAT5AD08To%3D&x-oss-process=image%2Fresize%2Cw_200","thumbuped":0},{"digitalSignature":"","action":"Ta赞了帖子你好世纪","actionType":1001,"traceDate":"2018-04-09 19:39:33","userId":4,"nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523342398&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KpHMVmm%2B8%2FBzOUfYV6WpzvFuAvc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":1,"postId":146,"readNumber":17,"attachment":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/2-20180409143204.png?Expires=1523344294&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=YkOsj7Q77leCWugWmIAT5AD08To%3D&x-oss-process=image%2Fresize%2Cw_200","thumbuped":0},{"digitalSignature":"","action":"Ta赞了帖子him哦考虑我的","actionType":1001,"traceDate":"2018-04-09 19:38:42","userId":4,"nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523342398&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KpHMVmm%2B8%2FBzOUfYV6WpzvFuAvc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":1,"postId":125,"readNumber":36,"thumbuped":0},{"digitalSignature":"","action":"Ta赞了帖子him哦考虑我的","actionType":1001,"traceDate":"2018-04-09 19:38:37","userId":4,"nickname":"大嘴猴🐵","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523342398&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KpHMVmm%2B8%2FBzOUfYV6WpzvFuAvc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":1,"postId":125,"readNumber":36,"thumbuped":0},{"digitalSignature":"","action":"Ta回复了帖子你好世纪","actionType":1001,"traceDate":"2018-04-09 14:33:06","userId":2,"nickname":"ycb880272","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180410102507.png?Expires=1523343424&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=re0y7bETYyi5tIlc9uVbrmato9s%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":0,"postId":146,"readNumber":17,"attachment":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/2-20180409143204.png?Expires=1523344294&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=YkOsj7Q77leCWugWmIAT5AD08To%3D&x-oss-process=image%2Fresize%2Cw_200","thumbuped":0},{"digitalSignature":"","action":"[帖子]你好世纪","actionType":1001,"traceDate":"2018-04-09 14:32:17","userId":2,"nickname":"ycb880272","avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180410102507.png?Expires=1523343424&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=re0y7bETYyi5tIlc9uVbrmato9s%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","realNameAuth":10081001,"level":0,"postId":146,"readNumber":17,"attachment":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/2-20180409143204.png?Expires=1523344294&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=YkOsj7Q77leCWugWmIAT5AD08To%3D&x-oss-process=image%2Fresize%2Cw_200","thumbuped":0}]
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
         * action : Ta赞了帖子你好世纪
         * actionType : 1001
         * traceDate : 2018-04-09 19:39:40
         * userId : 4
         * nickname : 大嘴猴🐵
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523342398&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=KpHMVmm%2B8%2FBzOUfYV6WpzvFuAvc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * realNameAuth : 10081001
         * level : 1
         * postId : 146
         * readNumber : 17
         * attachment : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/post-attachment/2-20180409143204.png?Expires=1523344294&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=YkOsj7Q77leCWugWmIAT5AD08To%3D&x-oss-process=image%2Fresize%2Cw_200
         * thumbuped : 0
         */

        private String digitalSignature;
        private String action;
        private int actionType;
        private String traceDate;
        private String userId;
        private String nickname;
        private String avatar;
        private int realNameAuth;
        private int level;
        private String postId;
        private int readNumber;
        private String attachment;
        private int thumbuped;
        private String replyId;
        private String itemCode;

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getActionType() {
            return actionType;
        }

        public void setActionType(int actionType) {
            this.actionType = actionType;
        }

        public String getTraceDate() {
            return traceDate;
        }

        public void setTraceDate(String traceDate) {
            this.traceDate = traceDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public int getRealNameAuth() {
            return realNameAuth;
        }

        public void setRealNameAuth(int realNameAuth) {
            this.realNameAuth = realNameAuth;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public int getReadNumber() {
            return readNumber;
        }

        public void setReadNumber(int readNumber) {
            this.readNumber = readNumber;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public int getThumbuped() {
            return thumbuped;
        }

        public void setThumbuped(int thumbuped) {
            this.thumbuped = thumbuped;
        }
    }
}
