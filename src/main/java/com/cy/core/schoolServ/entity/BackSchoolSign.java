package com.cy.core.schoolServ.entity;

import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

import java.io.Serializable;

/**
 * Created by niu on 2016/12/19.
 */
public class BackSchoolSign implements Serializable {
    private static final long serialVersionUID = 1L;

    private String	id;
    private String	backSchoolId;		//	活动ID
    private String	accountNum;		    //   报名用户
    private String	signUpTime;		    //	报名时间
    private String	viewNotification ;  //是否已查看通知（0=未查看， 1=已查看）
    private String	isSignIn;		    //   是否签到（0=未签到， 1=已签到）
    private String	signInTime;		    //	签到时间
    private String  picture;            //用户头像
    private String pictureUrl;         //用户头像相对地址
    private String  name;
    private String  phoneNum;
    private String  sex;
    private String  email;
    private String  groupName;
    private String  isOwner;            //是否当前用户
    private String  services;            //用户所需返校计划服务
    private String  servicesRemarks;    //返校计划服务备注信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackSchoolId() {
        return backSchoolId;
    }

    public void setBackSchoolId(String backSchoolId) {
        this.backSchoolId = backSchoolId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(String signUpTime) {
        this.signUpTime = signUpTime;
    }

    public String getViewNotification() {
        return viewNotification;
    }

    public void setViewNotification(String viewNotification) {
        this.viewNotification = viewNotification;
    }

    public String getIsSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(String isSignIn) {
        this.isSignIn = isSignIn;
    }

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPicture() {
        if(StringUtils.isBlank(picture) && StringUtils.isNotBlank(pictureUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(pictureUrl.indexOf("http") < 0) {
                if (!pictureUrl.trim().startsWith("/")){
                    pictureUrl="/"+pictureUrl;
                }
                picture = Global.URL_DOMAIN + pictureUrl ;
            }else{
                picture=pictureUrl;
            }
        }
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public String getPictureUrl() {
        if(StringUtils.isBlank(pictureUrl) && StringUtils.isNotBlank(picture) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(picture.indexOf("http") < 0) {
                if (!picture.trim().startsWith("/")){
                    picture="/"+picture;
                }
                pictureUrl = Global.URL_DOMAIN + picture ;
            }else{
                pictureUrl=picture;
            }
        }
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getServicesRemarks() {
        return servicesRemarks;
    }

    public void setServicesRemarks(String servicesRemarks) {
        this.servicesRemarks = servicesRemarks;
    }
}
