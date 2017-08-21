package com.cy.core.weiXin.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by WangAoHui on 2017/1/3.
 */
public class WeiXinUser extends DataEntity<WeiXinUser> {
    private static final long serialVersionUID = 1L;

    private String accountNum;      //手机用户编号
    private String openid;          //用户微信号
    private String nickname;        //微信昵称
    private String sex;             //性别（1：男；2：女）
    private String language;        //语言
    private String city;            //城市
    private String province;        //省份
    private String country;         //国家
    private String headimgurl;      //头像路径
    private String isFollow;        //是否关注（0：未关注；1：已关注）
    private String accountId;       //微信公共号信息编号
    private String accountAppId;    //微信公共号信息的appId
    private String longitude;        //位置经度
    private String latitude;       //位置纬度
    private String positionDesc;   //位置信息描述

    private String accountName;   //微信公众号名称
    private String accountType;   //微信公众号类型
    private String userName;      //手机用户姓名
    private String allPath;       // 班级全称
    private String isRemoveBinding; // 解除绑定标志 1 是
    private String localHeadImage; //微信头像本地路径


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getAccountAppId() {
        return accountAppId;
    }

    public void setAccountAppId(String accountAppId) {
        this.accountAppId = accountAppId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAllPath() {
        return allPath;
    }

    public void setAllPath(String allPath) {
        this.allPath = allPath;
    }

    public String getIsRemoveBinding() {
        return isRemoveBinding;
    }

    public void setIsRemoveBinding(String isRemoveBinding) {
        this.isRemoveBinding = isRemoveBinding;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLocalHeadImage() {
        return localHeadImage;
    }

    public void setLocalHeadImage(String localHeadImage) {
        this.localHeadImage = localHeadImage;
    }
}
