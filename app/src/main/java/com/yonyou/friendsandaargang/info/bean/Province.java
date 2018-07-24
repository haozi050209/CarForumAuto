package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/15.
 */

public class Province {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","provinceId":110000,"province":"北京市"},{"digitalSignature":"","provinceId":120000,"province":"天津市"},{"digitalSignature":"","provinceId":130000,"province":"河北省"},{"digitalSignature":"","provinceId":140000,"province":"山西省"},{"digitalSignature":"","provinceId":150000,"province":"内蒙古自治区"},{"digitalSignature":"","provinceId":210000,"province":"辽宁省"},{"digitalSignature":"","provinceId":220000,"province":"吉林省"},{"digitalSignature":"","provinceId":230000,"province":"黑龙江省"},{"digitalSignature":"","provinceId":310000,"province":"上海市"},{"digitalSignature":"","provinceId":320000,"province":"江苏省"},{"digitalSignature":"","provinceId":330000,"province":"浙江省"},{"digitalSignature":"","provinceId":340000,"province":"安徽省"},{"digitalSignature":"","provinceId":350000,"province":"福建省"},{"digitalSignature":"","provinceId":360000,"province":"江西省"},{"digitalSignature":"","provinceId":370000,"province":"山东省"},{"digitalSignature":"","provinceId":410000,"province":"河南省"},{"digitalSignature":"","provinceId":420000,"province":"湖北省"},{"digitalSignature":"","provinceId":430000,"province":"湖南省"},{"digitalSignature":"","provinceId":440000,"province":"广东省"},{"digitalSignature":"","provinceId":450000,"province":"广西壮族自治区"},{"digitalSignature":"","provinceId":460000,"province":"海南省"},{"digitalSignature":"","provinceId":500000,"province":"重庆市"},{"digitalSignature":"","provinceId":510000,"province":"四川省"},{"digitalSignature":"","provinceId":520000,"province":"贵州省"},{"digitalSignature":"","provinceId":530000,"province":"云南省"},{"digitalSignature":"","provinceId":540000,"province":"西藏自治区"},{"digitalSignature":"","provinceId":610000,"province":"陕西省"},{"digitalSignature":"","provinceId":620000,"province":"甘肃省"},{"digitalSignature":"","provinceId":630000,"province":"青海省"},{"digitalSignature":"","provinceId":640000,"province":"宁夏回族自治区"},{"digitalSignature":"","provinceId":650000,"province":"新疆维吾尔自治区"},{"digitalSignature":"","provinceId":710000,"province":"台湾省"},{"digitalSignature":"","provinceId":810000,"province":"香港特别行政区"},{"digitalSignature":"","provinceId":820000,"province":"澳门特别行政区"}]
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
         * provinceId : 110000
         * province : 北京市
         */

        private String digitalSignature;
        private String provinceId;
        private String province;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}
