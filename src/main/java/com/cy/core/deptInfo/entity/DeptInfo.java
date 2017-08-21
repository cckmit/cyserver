package com.cy.core.deptInfo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangling on 7/14/16.
 */
public class DeptInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String deptId;
    private String deptName; //机构名称
    private String parentId; //上级机构
    private String fullName; //院系机构路径
    private int level; // 机构层级
    private String aliasName; // 将学校历史中原有院系归属为现有某个管理机构

    /*private List<DeptInfo> deptInfos; //???

    public List<DeptInfo> getDeptInfos() {
        return deptInfos;
    }

    public void setDeptInfos(List<DeptInfo> deptInfos) {
        this.deptInfos = deptInfos;
    }*/

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }
}
