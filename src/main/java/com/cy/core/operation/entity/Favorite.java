package com.cy.core.operation.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by niu on 2016/12/27.
 */
public class Favorite extends DataEntity<Favorite> {

    private String userId; //收藏人id
    private String bussId; //业务编号
    private String bussType; //收藏业务类型10活动20社团30返校计划40校友企业50校友产品


    private Object buss; //业务对象

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public Object getBuss() {
        return buss;
    }

    public void setBuss(Object buss) {
        this.buss = buss;
    }
}
