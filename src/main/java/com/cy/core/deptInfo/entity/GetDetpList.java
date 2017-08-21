package com.cy.core.deptInfo.entity;

import java.io.Serializable;

public class GetDetpList implements Serializable {
    private static final long serialVersionUID = 1L;

    private String level;

    private String schoolId;

    private String collegeId;

    private String gradeId;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
}
