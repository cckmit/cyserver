package com.cy.mobileInterface.baseinfo.entity;

import java.io.Serializable;
/**
 * Created by Cha0res on 2016/8/3.
 */
public class ChangePhoneNum implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNum;
    private String phoneNum;
    private String checkCode;

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
