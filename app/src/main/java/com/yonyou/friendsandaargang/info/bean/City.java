package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/15.
 */

public class City {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","cityId":130100,"city":"石家庄市"},{"digitalSignature":"","cityId":130200,"city":"唐山市"},{"digitalSignature":"","cityId":130300,"city":"秦皇岛市"},{"digitalSignature":"","cityId":130400,"city":"邯郸市"},{"digitalSignature":"","cityId":130500,"city":"邢台市"},{"digitalSignature":"","cityId":130600,"city":"保定市"},{"digitalSignature":"","cityId":130700,"city":"张家口市"},{"digitalSignature":"","cityId":130800,"city":"承德市"},{"digitalSignature":"","cityId":130900,"city":"沧州市"},{"digitalSignature":"","cityId":131000,"city":"廊坊市"},{"digitalSignature":"","cityId":131100,"city":"衡水市"}]
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
         * cityId : 130100
         * city : 石家庄市
         */

        private String digitalSignature;
        private String cityId;
        private String city;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
