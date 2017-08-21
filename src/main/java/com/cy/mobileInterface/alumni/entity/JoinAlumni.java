package com.cy.mobileInterface.alumni.entity;

import java.io.Serializable;

/**
 * Created by Cha0res on 2016/8/14.
 */
public class JoinAlumni implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String accountNum;
    private String password;
    private String alumniId;
    private String alumniIds;
    private String joinTime;
    private String leaveTime;
    private String status;
    private String delFlag;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(String alumniId) {
        this.alumniId = alumniId;
    }

    public String getAlumniIds() {
        return alumniIds;
    }

    public void setAlumniIds(String alumniIds) {
        this.alumniIds = alumniIds;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
