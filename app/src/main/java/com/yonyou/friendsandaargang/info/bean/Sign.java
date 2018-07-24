package com.yonyou.friendsandaargang.info.bean;

/**
 * Created by shibing on 18/3/26.
 */

public class Sign {


    /**
     * digitalSignature :
     * times : 1
     * exp : 1
     * fortune : 1
     * result : 0
     */

    private String digitalSignature;
    private int times;
    private String exp;
    private String fortune;
    private String result;

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getFortune() {
        return fortune;
    }

    public void setFortune(String fortune) {
        this.fortune = fortune;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
