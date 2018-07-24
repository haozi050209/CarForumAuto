package com.yonyou.friendsandaargang.info.bean;

import java.util.List;

/**
 * Created by shibing on 18/3/15.
 */

public class Area {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : [{"digitalSignature":"","areaId":210101,"area":"市辖区"},{"digitalSignature":"","areaId":210102,"area":"和平区"},{"digitalSignature":"","areaId":210103,"area":"沈河区"},{"digitalSignature":"","areaId":210104,"area":"大东区"},{"digitalSignature":"","areaId":210105,"area":"皇姑区"},{"digitalSignature":"","areaId":210106,"area":"铁西区"},{"digitalSignature":"","areaId":210111,"area":"苏家屯区"},{"digitalSignature":"","areaId":210112,"area":"东陵区"},{"digitalSignature":"","areaId":210113,"area":"新城子区"},{"digitalSignature":"","areaId":210114,"area":"于洪区"},{"digitalSignature":"","areaId":210122,"area":"辽中县"},{"digitalSignature":"","areaId":210123,"area":"康平县"},{"digitalSignature":"","areaId":210124,"area":"法库县"},{"digitalSignature":"","areaId":210181,"area":"新民市"}]
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
         * areaId : 210101
         * area : 市辖区
         */

        private String digitalSignature;
        private String areaId;
        private String area;

        public String getDigitalSignature() {
            return digitalSignature;
        }

        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }
}
