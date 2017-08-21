package com.cy.core.project.entity;

import com.cy.base.entity.DataEntity;

/**
 * Created by cha0res on 2/14/17.
 */
public class ProjectCost extends DataEntity<ProjectCost> {
    private static final long serialVersionUID = 1L;

    private String projectId;
    private String costTime;
    private String costMoney;
    private String description;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(String costMoney) {
        this.costMoney = costMoney;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
