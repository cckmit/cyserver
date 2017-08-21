package com.cy.core.classHandle.entity;
import com.cy.base.entity.DataEntity;

import java.util.Map;

public class ClassHandle  extends DataEntity<ClassHandle> {
    private static final long serialVersionUID = 1L;
    private String classBaseInfoId;
    private String baseInfoId;
    private String deptId;
    private String name;
    private String nameOld;
    private String telephone;
    private String telephoneOld;
    private String className;
    private String majorName;
    private String classAdmin;
    private String status;
    private String type;
    private String sex;

    public String getClassBaseInfoId() {
        return classBaseInfoId;
    }

    public void setClassBaseInfoId(String classBaseInfoId) {
        this.classBaseInfoId = classBaseInfoId;
    }

    public String getBaseInfoId() {
        return baseInfoId;
    }

    public void setBaseInfoId(String baseInfoId) {
        this.baseInfoId = baseInfoId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNameOld() {
        return nameOld;
    }

    public void setNameOld(String nameOld) {
        this.nameOld = nameOld;
    }

    public String getTelephoneOld() {
        return telephoneOld;
    }

    public void setTelephoneOld(String telephoneOld) {
        this.telephoneOld = telephoneOld;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getClassAdmin() {
        return classAdmin;
    }

    public void setClassAdmin(String classAdmin) {
        this.classAdmin = classAdmin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
