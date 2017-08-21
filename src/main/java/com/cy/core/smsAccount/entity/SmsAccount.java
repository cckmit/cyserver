package com.cy.core.smsAccount.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.smsbuywater.entity.SmsBuyWater;

import java.util.List;

/**
 * Created by cha0res on 11/17/16.
 */
public class SmsAccount extends DataEntity<SmsAccount>
{
    private static final long serialVersionUID = 1L;

    private String alumniId;            // 組織ID
    private String name;                // 名稱
    private String accountFronts;       // 前綴
    private String account;             // 帳號
    private String password;            // 密碼
    private String sign;                // 簽名
    private String appUserId;           // 應用聯繫人
    private String surplusNum;			// 剩余条数
    private String surplusPrice;		// 剩余金额
    private String nextSurplusNum;		// 下次流量包剩余条数
    private String nextSurplusPrice;	// 下次流量包剩余金额
    private String ifUse;				// 是否使用（0：否；1：是）
    private String currBuyWaterId ;		// 当前使用流水编号
    private String nextBuyWaterId ;		// 下次使用流水编号
    private AppUser appUser;            // 應用聯繫人信息
    private List<SmsBuyWater> appBuyWaterList;// 购买流水列表

    private String appUserName;

    private boolean isSync = true;//是否同步短信云平台（默认true同步）

    public String getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(String alumniId) {
        this.alumniId = alumniId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(String surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getSurplusPrice() {
        return surplusPrice;
    }

    public void setSurplusPrice(String surplusPrice) {
        this.surplusPrice = surplusPrice;
    }

    public String getNextSurplusNum() {
        return nextSurplusNum;
    }

    public void setNextSurplusNum(String nextSurplusNum) {
        this.nextSurplusNum = nextSurplusNum;
    }

    public String getNextSurplusPrice() {
        return nextSurplusPrice;
    }

    public void setNextSurplusPrice(String nextSurplusPrice) {
        this.nextSurplusPrice = nextSurplusPrice;
    }

    public String getIfUse() {
        return ifUse;
    }

    public void setIfUse(String ifUse) {
        this.ifUse = ifUse;
    }

    public String getCurrBuyWaterId() {
        return currBuyWaterId;
    }

    public void setCurrBuyWaterId(String currBuyWaterId) {
        this.currBuyWaterId = currBuyWaterId;
    }

    public String getNextBuyWaterId() {
        return nextBuyWaterId;
    }

    public void setNextBuyWaterId(String nextBuyWaterId) {
        this.nextBuyWaterId = nextBuyWaterId;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getAccountFronts() {
        return accountFronts;
    }

    public void setAccountFronts(String accountFronts) {
        this.accountFronts = accountFronts;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public List<SmsBuyWater> getAppBuyWaterList() {
        return appBuyWaterList;
    }

    public void setAppBuyWaterList(List<SmsBuyWater> appBuyWaterList) {
        this.appBuyWaterList = appBuyWaterList;
    }
}
