package com.yonyou.friendsandaargang.info.bean;

/**
 * Created by shibing on 18/4/12.
 */

public class UserLevel {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","levelId":1,"exp":367,"fortune":617,"nickname":"大嘴猴🐵","levelName":"LV1","fortuneExchange":12781001,"exclusiveActivity":12781002,"birthdayRights":12781002,"customerService":12781002,"fortuneMore":12781002,"avatar":"https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523504177&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=IlMeSg6RyLTeUPZLgFgR2gxenmA%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000","coin":230,"fortuneRate":1,"toNext":709}
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
         * digitalSignature :
         * levelId : 1
         * exp : 367
         * fortune : 617
         * nickname : 大嘴猴🐵
         * levelName : LV1
         * fortuneExchange : 12781001
         * exclusiveActivity : 12781002
         * birthdayRights : 12781002
         * customerService : 12781002
         * fortuneMore : 12781002
         * avatar : https://yonyou-community-app-images.oss-cn-hangzhou.aliyuncs.com/user-avatar/avatar_20180327172025.png?Expires=1523504177&OSSAccessKeyId=LTAI52LTgcv7nVw6&Signature=IlMeSg6RyLTeUPZLgFgR2gxenmA%3D&x-oss-process=image%2Fformat%2Cjpg%2Fcircle%2Cr_3000
         * coin : 230
         * fortuneRate : 1
         * toNext : 709
         */

        private String digitalSignature;
        private String levelId;
        private int exp;
        private int fortune;
        private String nickname;
        private String levelName;
        private int fortuneExchange;
        private int exclusiveActivity;
        private int birthdayRights;
        private int customerService;
        private int fortuneMore;
        private String avatar;
        private int coin;
        private int fortuneRate;
        private int toNext;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public int getFortune() {
            return fortune;
        }

        public void setFortune(int fortune) {
            this.fortune = fortune;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getFortuneExchange() {
            return fortuneExchange;
        }

        public void setFortuneExchange(int fortuneExchange) {
            this.fortuneExchange = fortuneExchange;
        }

        public int getExclusiveActivity() {
            return exclusiveActivity;
        }

        public void setExclusiveActivity(int exclusiveActivity) {
            this.exclusiveActivity = exclusiveActivity;
        }

        public int getBirthdayRights() {
            return birthdayRights;
        }

        public void setBirthdayRights(int birthdayRights) {
            this.birthdayRights = birthdayRights;
        }

        public int getCustomerService() {
            return customerService;
        }

        public void setCustomerService(int customerService) {
            this.customerService = customerService;
        }

        public int getFortuneMore() {
            return fortuneMore;
        }

        public void setFortuneMore(int fortuneMore) {
            this.fortuneMore = fortuneMore;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getFortuneRate() {
            return fortuneRate;
        }

        public void setFortuneRate(int fortuneRate) {
            this.fortuneRate = fortuneRate;
        }

        public int getToNext() {
            return toNext;
        }

        public void setToNext(int toNext) {
            this.toNext = toNext;
        }
    }
}
