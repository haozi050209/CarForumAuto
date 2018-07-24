package com.yonyou.friendsandaargang.homepage.modle;

/**
 * Created by shibing on 18/2/8.
 */

public class NavigationBean {

    private String name;

    private int image;

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    private Class activity;

    private String form;

    public NavigationBean(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public NavigationBean(String name, int image, Class activity) {
        this.name = name;
        this.image = image;
        this.activity = activity;
        this.form = form;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
