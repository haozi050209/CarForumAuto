package com.yonyou.friendsandaargang.info.bean;

/**
 * Created by shibing on 18/5/30.
 */

public class UserFortuneBean {


    /**
     * digitalSignature :
     * fortune : 12
     * coin : 0.26
     */

    private String digitalSignature;
    private String fortune;
    private String coin;

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public String getFortune() {
        return fortune;
    }

    public void setFortune(String fortune) {
        this.fortune = fortune;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
