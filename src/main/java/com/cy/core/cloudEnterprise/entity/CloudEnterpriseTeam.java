package com.cy.core.cloudEnterprise.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

/**
 * Created by Administrator on 2017/5/15.
 */
public class CloudEnterpriseTeam extends DataEntity<CloudEnterpriseTeam> {
    private static final long serialVersionUID = 1L;

    private String fullName;            // 姓名
    private String enterpriseId;    // 所属企业编号
    private String pic;             // 头像
    private String isAlumni;        // 是否为校友
    private String position;        // 职位
    private String classinfo;       // 班级信息
    private String enterpriseName;  // 企业名称
    private String description;    //个人简介
    private String isShow;         //是否在团队风采展示(0:是 ；1：否)
    private String cloudTeamId; //云平台成员编号
    private String userId;

    private String type;   //企业成员在该校的校友类型（10：正式校友；20：名誉校友）
    private String status; //企业成员在该校的审核状态（10 待审核；20:认证成功，正式校友；25：认证通过，名誉校友；30: 未通过审核）



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIsAlumni() {
        return isAlumni;
    }

    public void setIsAlumni(String isAlumni) {
        this.isAlumni = isAlumni;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(String classinfo) {
        this.classinfo = classinfo;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getCloudTeamId() {
        return cloudTeamId;
    }

    public void setCloudTeamId(String cloudTeamId) {
        this.cloudTeamId = cloudTeamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
