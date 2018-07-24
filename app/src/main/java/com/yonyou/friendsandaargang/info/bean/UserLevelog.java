package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/4/12.
 */

public class UserLevelog {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","expChange":10,"actionContent":"完成第一次收藏任务获得","createDate":"2018-04-11 19:45:52"},{"digitalSignature":"","expChange":10,"actionContent":"提问奖励-亚洲博鳌论坛开幕式，习近平出席大会，宣布降低进口车关税？","createDate":"2018-04-11 17:31:19"},{"digitalSignature":"","expChange":0,"actionContent":"提出悬赏扣除金币-亚洲博鳌论坛开幕式，习近平出席大会，宣布降低进口车关税？","createDate":"2018-04-11 17:31:19"},{"digitalSignature":"","expChange":10,"actionContent":"提问奖励-今日头条被封大家怎么看？","createDate":"2018-04-11 17:08:58"},{"digitalSignature":"","expChange":0,"actionContent":"提出悬赏扣除金币-今日头条被封大家怎么看？","createDate":"2018-04-11 17:08:58"},{"digitalSignature":"","expChange":10,"actionContent":"提问奖励-中美贸易战大家怎么看？","createDate":"2018-04-10 14:20:42"},{"digitalSignature":"","expChange":0,"actionContent":"提出悬赏扣除金币-中美贸易战大家怎么看？","createDate":"2018-04-10 14:20:42"},{"digitalSignature":"","expChange":0,"actionContent":"悬赏超时未解决返还金币-提问13","createDate":"2018-04-05 14:22:16"},{"digitalSignature":"","expChange":0,"actionContent":"悬赏超时未解决返还金币-提问14","createDate":"2018-04-05 14:22:16"},{"digitalSignature":"","expChange":0,"actionContent":"悬赏超时未解决返还金币-发布第一个问答","createDate":"2018-04-04 17:32:58"}]
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
         * expChange : 10
         * actionContent : 完成第一次收藏任务获得
         * createDate : 2018-04-11 19:45:52
         */

        private String digitalSignature;
        private int expChange;
        private String actionContent;
        private String createDate;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public int getExpChange() {
            return expChange;
        }

        public void setExpChange(int expChange) {
            this.expChange = expChange;
        }

        public String getActionContent() {
            return actionContent;
        }

        public void setActionContent(String actionContent) {
            this.actionContent = actionContent;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
