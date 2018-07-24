package com.yonyou.friendsandaargang.login.modle;

/**
 * Created by Administrator on 2018/5/31.
 */

public class ThirdLogin {

    /**
     * "userId":"1001",
     "userName":"user's name",
     "gender":"1",
     "registerDate":"2017-11-10",
     "email":"abc@163.com",
     "yonyouId":"1001",
     "mobile":"15618781723",
     "birthday":"2017-11-10",
     "firstTimeLogon": 0 //是否首次登录，1是，0否
     */

    private String userId;
    private String userName;
    private int gender;
    private String registerDate;
    private String email;
    private String yonyouId;
    private String mobile;
    private String birthday;
    private int firstTimeLogon;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYonyouId() {
        return yonyouId;
    }

    public void setYonyouId(String yonyouId) {
        this.yonyouId = yonyouId;
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

    public int getFirstTimeLogon() {
        return firstTimeLogon;
    }

    public void setFirstTimeLogon(int firstTimeLogon) {
        this.firstTimeLogon = firstTimeLogon;
    }
}
