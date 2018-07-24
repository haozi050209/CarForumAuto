package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/11.
 */

public class ExchangeRecordBean {

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

        private String digitalSignature;
        private String exchangeRecordId;
        private String userId;
        private String exchangeItemId;
        private String itemName;
        private String itemPhoto;//物品图片URL
        private String fortuneConsumed;//消耗的财富值
        private String exchangeStatus;//兑换状态代码
        private String exchangeStatusDesc;//兑换状态（已完成、待寄出、已寄）
        private String createDate;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getExchangeRecordId() {
            return exchangeRecordId;
        }

        public void setExchangeRecordId(String exchangeRecordId) {
            this.exchangeRecordId = exchangeRecordId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getExchangeItemId() {
            return exchangeItemId;
        }

        public void setExchangeItemId(String exchangeItemId) {
            this.exchangeItemId = exchangeItemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemPhoto() {
            return itemPhoto;
        }

        public void setItemPhoto(String itemPhoto) {
            this.itemPhoto = itemPhoto;
        }

        public String getFortuneConsumed() {
            return fortuneConsumed;
        }

        public void setFortuneConsumed(String fortuneConsumed) {
            this.fortuneConsumed = fortuneConsumed;
        }

        public String getExchangeStatus() {
            return exchangeStatus;
        }

        public void setExchangeStatus(String exchangeStatus) {
            this.exchangeStatus = exchangeStatus;
        }

        public String getExchangeStatusDesc() {
            return exchangeStatusDesc;
        }

        public void setExchangeStatusDesc(String exchangeStatusDesc) {
            this.exchangeStatusDesc = exchangeStatusDesc;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
