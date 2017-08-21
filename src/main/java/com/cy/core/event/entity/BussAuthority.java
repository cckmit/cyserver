package com.cy.core.event.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by Cha0res on 2016/8/20.
 */
public class BussAuthority extends DataEntity<BussAuthority> {
    private static final long serialVersionUID = 1L;

    private String bussId;      //业务编号
    private String bussType;    //业务类型（10：活动）
    private String alumniId;    //分会组织编号
    private String status;      //审核状态（10：审核中；20：审核通过；30：审核不通过）

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

    public String getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(String alumniId) {
        this.alumniId = alumniId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
