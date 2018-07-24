package com.yonyou.friendsandaargang.info.bean;

/**
 * Created by shibing on 18/5/27.
 */

public class IdCardBean {


    /**
     * error_code : 0
     * reason : Success
     * result : {"realname":"张三","idcard":"330329199001020022","isok":false,"IdCardInfor":{"area":"山西省太原市清徐县","sex":"男","birthday":"1985-4-10"}}
     */

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * realname : 张三
         * idcard : 330329199001020022
         * isok : false
         * IdCardInfor : {"area":"山西省太原市清徐县","sex":"男","birthday":"1985-4-10"}
         */

        private String realname;
        private String idcard;
        private boolean isok;
        private IdCardInforBean IdCardInfor;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public boolean isIsok() {
            return isok;
        }

        public void setIsok(boolean isok) {
            this.isok = isok;
        }

        public IdCardInforBean getIdCardInfor() {
            return IdCardInfor;
        }

        public void setIdCardInfor(IdCardInforBean IdCardInfor) {
            this.IdCardInfor = IdCardInfor;
        }

        public static class IdCardInforBean {
            /**
             * area : 山西省太原市清徐县
             * sex : 男
             * birthday : 1985-4-10
             */

            private String area;
            private String sex;
            private String birthday;

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }
        }
    }
}
