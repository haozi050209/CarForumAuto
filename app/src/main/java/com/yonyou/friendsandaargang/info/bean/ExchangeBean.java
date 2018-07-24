package com.yonyou.friendsandaargang.info.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shibing on 18/5/30.
 */

public class ExchangeBean implements Serializable {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","exchangeItemId":1,"exchangeItemName":"10元金币","exchangeItemDesc":"金币可用于悬赏及付费服务支付；不可进行提现","exchangeProcess":"点击\u201c马上兑换\u201d，金币即可发放至兑换账户；金币明细可在\u201c我的钱包\u201d中查看","exchangeTip":"每天仅允许兑换一次金币；如有疑问请联系客服。","exchangeItemPhoto":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/exchange-item/coin.png?Expires=1527661816&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=eQExPQhNp2mRPoqyk1bf23eqnY0%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","fortuneNeeded":1000,"dailyQuantity":0,"realPrice":10,"isCoin":1},{"digitalSignature":"","exchangeItemId":2,"exchangeItemName":"小米蓝牙音箱","exchangeItemDesc":"小米蓝牙音箱，能装进口袋的好音箱。","exchangeProcess":"点击\u201c马上兑换\u201d，我们会按照兑换时间分批发货，请大家耐心等待","exchangeTip":"werwe","exchangeItemPhoto":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/exchange-item/xiaomibluetooth.png?Expires=1527661816&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=kETN937uM4B4HTWh7dJUuhveYsc%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","fortuneNeeded":13000,"dailyQuantity":10,"realPrice":1000,"isCoin":0}]
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
         * digitalSignature :
         * exchangeItemId : 1
         * exchangeItemName : 10元金币
         * exchangeItemDesc : 金币可用于悬赏及付费服务支付；不可进行提现
         * exchangeProcess : 点击“马上兑换”，金币即可发放至兑换账户；金币明细可在“我的钱包”中查看
         * exchangeTip : 每天仅允许兑换一次金币；如有疑问请联系客服。
         * exchangeItemPhoto : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/exchange-item/coin.png?Expires=1527661816&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=eQExPQhNp2mRPoqyk1bf23eqnY0%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * fortuneNeeded : 1000
         * dailyQuantity : 0
         * realPrice : 10
         * isCoin : 1
         */

        private String digitalSignature;
        private String exchangeItemId;
        private String exchangeItemName;
        private String exchangeItemDesc;
        private String exchangeProcess;
        private String exchangeTip;
        private String exchangeItemPhoto;
        private String fortuneNeeded;
        private int dailyQuantity;
        private String realPrice;
        private int isCoin;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getExchangeItemId() {
            return exchangeItemId;
        }

        public void setExchangeItemId(String exchangeItemId) {
            this.exchangeItemId = exchangeItemId;
        }

        public String getExchangeItemName() {
            return exchangeItemName;
        }

        public void setExchangeItemName(String exchangeItemName) {
            this.exchangeItemName = exchangeItemName;
        }

        public String getExchangeItemDesc() {
            return exchangeItemDesc;
        }

        public void setExchangeItemDesc(String exchangeItemDesc) {
            this.exchangeItemDesc = exchangeItemDesc;
        }

        public String getExchangeProcess() {
            return exchangeProcess;
        }

        public void setExchangeProcess(String exchangeProcess) {
            this.exchangeProcess = exchangeProcess;
        }

        public String getExchangeTip() {
            return exchangeTip;
        }

        public void setExchangeTip(String exchangeTip) {
            this.exchangeTip = exchangeTip;
        }

        public String getExchangeItemPhoto() {
            return exchangeItemPhoto;
        }

        public void setExchangeItemPhoto(String exchangeItemPhoto) {
            this.exchangeItemPhoto = exchangeItemPhoto;
        }

        public String getFortuneNeeded() {
            return fortuneNeeded;
        }

        public void setFortuneNeeded(String fortuneNeeded) {
            this.fortuneNeeded = fortuneNeeded;
        }

        public int getDailyQuantity() {
            return dailyQuantity;
        }

        public void setDailyQuantity(int dailyQuantity) {
            this.dailyQuantity = dailyQuantity;
        }

        public String getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(String realPrice) {
            this.realPrice = realPrice;
        }

        public int getIsCoin() {
            return isCoin;
        }

        public void setIsCoin(int isCoin) {
            this.isCoin = isCoin;
        }
    }
}
