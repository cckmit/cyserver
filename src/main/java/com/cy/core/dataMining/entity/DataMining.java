package com.cy.core.dataMining.entity;

import com.cy.base.entity.DataEntity;

import java.io.Serializable;

/**
 * Created by jiangling on 8/4/16.
 */
public class DataMining extends DataEntity<DataMining> {

    private static final long serialVersionUID = 1L;

    private String miningUserId;    //挖掘用户编号

    private String minedUserId;     //被挖掘用户编号
    private String phone;           // 挖掘手机号

    private String miningUserName;  //挖掘人姓名

    public String getMiningUserName() {
        return miningUserName;
    }

    public void setMiningUserName(String miningUserName) {
        this.miningUserName = miningUserName;
    }

    public String getMiningUserId() {
        return miningUserId;
    }

    public void setMiningUserId(String miningUserId) {
        this.miningUserId = miningUserId;
    }

    public String getMinedUserId() {
        return minedUserId;
    }

    public void setMinedUserId(String minedUserId) {
        this.minedUserId = minedUserId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}
