package com.cy.core.resumeBase.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 6/6/17.
 */
public class ResumeRewardAtSchool extends DataEntity<ResumeRewardAtSchool> {

    private String resumeBaseId;        // 基础简历编号
    private String projectName;         // 奖项名称
    private String level;               // 奖项等级
    private String time;                // 获奖时间
    private String desc;                // 奖项描述

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
