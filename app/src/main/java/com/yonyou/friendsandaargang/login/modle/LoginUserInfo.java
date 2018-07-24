package com.yonyou.friendsandaargang.login.modle;

/**
 * Created by shibing on 18/3/13.
 */

public class LoginUserInfo {


    /**
     * returnCode : 0
     * returnMsg : success
     * content : {"digitalSignature":"","userId":1000018,"userName":"111","password":"aa123456","gender":10041001,"registerDate":"2018-02-06","mobile":"18207182435","birthday":"2018-03-08"}
     */
    /**
     * digitalSignature :
     * userId : 1000018
     * userName : 111
     * password : aa123456
     * gender : 10041001
     * registerDate : 2018-02-06
     * mobile : 18207182435
     * birthday : 2018-03-08
     */

    private String digitalSignature;
    private String userId;
    private String userName;
    private String password;
    private String gender;
    private String registerDate;
    private String mobile;
    private String birthday;
    private int firstTimeLogon;

    public int getFirstTimeLogon() {
        return firstTimeLogon;
    }

    public void setFirstTimeLogon(int firstTimeLogon) {
        this.firstTimeLogon = firstTimeLogon;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    // }
}
