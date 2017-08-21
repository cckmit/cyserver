package com.cy.core.userProfile.entity;

/**
 * Created by cha0res on 3/27/17.
 */
public class UserProfileRegist extends UserProfile {
    private String registCode;
    private String openId;
    private String appId;
   
    
    public String getRegistCode() {
        return registCode;
    }

    public void setRegistCode(String registCode) {
        this.registCode = registCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
