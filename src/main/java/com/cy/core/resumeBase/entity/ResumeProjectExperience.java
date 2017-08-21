package com.cy.core.resumeBase.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 6/6/17.
 */
public class ResumeProjectExperience extends DataEntity<ResumeProjectExperience> {

    private String resumeBaseId;        // 基础简历编号
    private String projectName;         // 项目名称
    private String desc;                // 项目描述
    private String startDate;           // 开始日期
    private String endDate;             // 结束日期（为空表示还在进行）

    public String getResumeBaseId() {
        return resumeBaseId;
    }

    public void setResumeBaseId(String resumeBaseId) {
        this.resumeBaseId = resumeBaseId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
