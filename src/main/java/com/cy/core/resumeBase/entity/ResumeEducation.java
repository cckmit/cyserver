package com.cy.core.resumeBase.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 6/6/17.
 */
public class ResumeEducation extends DataEntity<ResumeEducation> {

    private String resumeBaseId;        //基础简历编号
    private String schoolName;          //学校名称
    private String education;           //学历
    private String profession;          //专业
    private String startDate;           //入学年月
    private String endDate;             //毕业年月

    public String getResumeBaseId() {
        return resumeBaseId;
    }

    public void setResumeBaseId(String resumeBaseId) {
        this.resumeBaseId = resumeBaseId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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
