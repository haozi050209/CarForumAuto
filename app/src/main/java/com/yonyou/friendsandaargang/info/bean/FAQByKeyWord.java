package com.yonyou.friendsandaargang.info.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shibing on 18/4/11.
 */

public class FAQByKeyWord {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"title":"为什么部分金币无法提现？","content":"财富值兑换金币无法进行提现，仅可进行消费。"},{"title":"金币如何提现？","content":"进入\u201c我的\u201d\u2014\u201c我的钱包\u201d\u2014\u201c我的金币\u201d\u2014\u201c提现\u201d可进行提现，提现将直接返回原账户，预计15个工作日到账。"},{"title":"金币如何充值？","content":"进入\u201c我的\u201d\u2014\u201c我的钱包\u201d\u2014\u201c我的金币\u201d\u2014\u201c充值\u201d可通过微信、支付宝等渠道进行充值。"},{"title":"金币有什么用处？","content":"金币作为站内的流通货币进行使用，可进行问答悬赏、购买增值服务等。"},{"title":"问题已经回答了，查看的时候为什么需要支付金币？","content":"专家回答的问题，如需查看需支付1元费用，该费用将由专家和提问者平分。"}]
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

    public static class ContentBean implements Serializable {
        /**
         * title : 为什么部分金币无法提现？
         * content : 财富值兑换金币无法进行提现，仅可进行消费。
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
