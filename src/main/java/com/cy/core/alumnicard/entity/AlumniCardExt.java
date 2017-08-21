package com.cy.core.alumnicard.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 2/24/17.
 */
public class AlumniCardExt extends DataEntity<AlumniCardExt> {
    private String alumniCardId;
    private String startTime;
    private String endTime;
    private String studentNumber;
    private String depart;
    private String clazz;
    private String major;
    private String degree;
    private String graduationCertificate;

    private String signCardId;

    public String getSignCardId() {
        return signCardId;
    }

    public void setSignCardId(String signCardId) {
        this.signCardId = signCardId;
    }

    public String getAlumniCardId() {
        return alumniCardId;
    }

    public void setAlumniCardId(String alumniCardId) {
        this.alumniCardId = alumniCardId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGraduationCertificate() {
        return graduationCertificate;
    }

    public void setGraduationCertificate(String graduationCertificate) {
        this.graduationCertificate = graduationCertificate;
    }
}
