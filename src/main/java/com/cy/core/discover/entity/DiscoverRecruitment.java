package com.cy.core.discover.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverRecruitment implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String accountNum;//发布招聘的ID
    private String name;//发布招聘的人
    private String picture;//发布招聘的人
    private String jobType;//职业类型
    private String requirement;//岗位要求
    private String description;//岗位描述
    private String userId;//发布招聘的组织ID
    private String content;//内容
    private String region;//地域
    private int type;//性质（0=官方 ，5=校友会，9=个人）
    private int auditStatus;//审核状态（0=未审核，1=通过，2=不通过）
    private String auditOpinion;//审核意见
    private long auditUserId;//审核人（对应user.userId）
    private Date auditTime;//审核时间
    private int status;//状态（0=正常，1=投诉处理-信息正常，2=投诉处理-信息违规，3=用户自己删除，4=管理员删除）
    private Date createTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
