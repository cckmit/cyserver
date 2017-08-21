package com.cy.core.cloudEnterprise.entity;

import com.cy.base.entity.DataEntity;

import java.util.PriorityQueue;

/**
 * 企业家实体类
 *
 * @author niu
 * @create 2017-08-11 上午 11:11
 **/

public class CloudEntrepreneur extends DataEntity<CloudEntrepreneur> {
    private static final long serialVersionUID = 1L;

    private String enterpriseId;  // 企业编号
    private String teamId      ;  // 团队成员编号
    private String telephone   ;  // 联系电话
    private String college     ;  // 学院
    private String grade       ;  // 年级
    private String clbum       ;  // 班级
    private String sysName     ;  // 管理员名称
    private String sysPhone    ;  // 管理员手机号
    private String accountNum  ;  // 用户编号
    private String type        ;  // 企业家类型(10:正式校友；20:名誉校友)
    private String syncStatus  ;  // 同步状态（10:来自云平台同步；20:审核处理同步到云平台；）
    private String status      ;  // 校企状态（10 待审核；20:认证成功，正式校友；25：认证通过，名誉校友；30: 未通过审核）
    private String opinion     ;  // 审核意见

    private String isNullAccountNum ; //accountNum 值空
    private String teamName    ;  // 企业家名称
    private String sex         ;  // 性别（0：男；1：女；）
    private String position    ;  // 职位
    private String enterpriseName;// 所属公司名称
    private String relationUserInfoId ;   // 关联校友编号

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClbum() {
        return clbum;
    }

    public void setClbum(String clbum) {
        this.clbum = clbum;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysPhone() {
        return sysPhone;
    }

    public void setSysPhone(String sysPhone) {
        this.sysPhone = sysPhone;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRelationUserInfoId() {
        return relationUserInfoId;
    }

    public void setRelationUserInfoId(String relationUserInfoId) {
        this.relationUserInfoId = relationUserInfoId;
    }

    public String getIsNullAccountNum() {
        return isNullAccountNum;
    }

    public void setIsNullAccountNum(String isNullAccountNum) {
        this.isNullAccountNum = isNullAccountNum;
    }
}
