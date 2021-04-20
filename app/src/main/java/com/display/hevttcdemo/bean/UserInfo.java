package com.display.hevttcdemo.bean;

import cn.bmob.v3.BmobObject;

public class UserInfo extends BmobObject {
    private String stuno;
    private String sex;
    private String yuan;
    private String clazz;
    private String name;
    private String phoneNum;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYuan() {
        return yuan;
    }

    public void setYuan(String yuan) {
        this.yuan = yuan;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
