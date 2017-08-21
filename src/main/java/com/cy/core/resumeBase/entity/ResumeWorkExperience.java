package com.cy.core.resumeBase.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 6/6/17.
 */
public class ResumeWorkExperience extends DataEntity<ResumeWorkExperience>{

    private String resumeBaseId;        //基础简历编号
    private String companyName;         //公司名称
    private String positionName;        //职位名称
    private String desc;                //经历描述
    private String startDate;           //入职日期
    private String endDate;             //离职日期（为空表示在职）

    public String getResumeBaseId() {
        return resumeBaseId;
    }

    public void setResumeBaseId(String resumeBaseId) {
        this.resumeBaseId = resumeBaseId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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
