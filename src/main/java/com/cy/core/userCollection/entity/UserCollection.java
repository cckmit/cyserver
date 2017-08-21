package com.cy.core.userCollection.entity;

import java.io.Serializable;

public class UserCollection implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
     * collectionId 用户收集ID
     */
    private String collectionId;
    /*
     * 用户ID
     */
    private String accountNum;
    /*
     * address 地址
     */
    private String address;
    /*
     * createTime 创建时间
     */
    private String createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
