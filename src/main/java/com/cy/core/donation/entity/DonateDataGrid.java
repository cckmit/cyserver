package com.cy.core.donation.entity;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.entity.Dict;

import java.util.List;

/**
 * Created by cha0res on 2/15/17.
 */
public class DonateDataGrid<T> extends DataGrid<T> {

    private String totalMoney;
    private String totalPeople;
    private String userName;
    private String userSex;
    private String userTel;
    private String underWay;
    private String complete;
    private String userPic;
    private String userAuth;
    private String isActivate;
    private List<Dict> unitDictList; //物品单位字典列表
    private List<Dict> goodsDictList; //物品种类字典

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(String totalPeople) {
        this.totalPeople = totalPeople;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUnderWay() {
        return underWay;
    }

    public void setUnderWay(String underWay) {
        this.underWay = underWay;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth;
    }

    public String getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(String isActivate) {
        this.isActivate = isActivate;
    }

    public List<Dict> getUnitDictList() {
        return unitDictList;
    }

    public void setUnitDictList(List<Dict> unitDictList) {
        this.unitDictList = unitDictList;
    }

    public List<Dict> getGoodsDictList() {
        return goodsDictList;
    }

    public void setGoodsDictList(List<Dict> goodsDictList) {
        this.goodsDictList = goodsDictList;
    }
}
