package com.yonyou.friendsandaargang.login.modle;

/**
 * Created by shibing on 18/3/15.
 */

public class Login {


    /*
    *
    * returnCode":"0",
"returnMsg":"success",
"content":{
"userId":"1001",
"userName":"user's name",
"gender":"1",
"registerDate":"2017-11-10",
"email":"abc@163.com",
"qq":"123456789",
"openId":"asdfasdfasdfasdf",
"yonyouId":"1001",
"mobile":"15618781723",
"birthday":"2017-11-10",
"type":"1001",
"firstTimeLogon": 0 //是否首次登录，1是，0否
    *
    * */


    private String digitalSignature;
    private String userId;
    private String userName;
    private String password;
    private int gender;
    private String registerDate;
    private String mobile;
    private int firstTimeLogon;
    private String type;
    private String yonyouId;
    private String openId;

    public int getFirstTimeLogon() {
        return firstTimeLogon;
    }

    public void setFirstTimeLogon(int firstTimeLogon) {
        this.firstTimeLogon = firstTimeLogon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYonyouId() {
        return yonyouId;
    }

    public void setYonyouId(String yonyouId) {
        this.yonyouId = yonyouId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
