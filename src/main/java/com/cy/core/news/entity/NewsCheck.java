package com.cy.core.news.entity;


import com.cy.base.entity.DataEntity;

public class NewsCheck extends DataEntity<NewsCheck> {

    private static final long serialVersionUID = 1L;

    private String bussId;
    private String bussType;
    private String bussDeptId;
    private String status;
    private String userId;
    private String handleDate;
    private String handleOpinion;

    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public String getBussDeptId() {
        return bussDeptId;
    }

    public void setBussDeptId(String bussDeptId) {
        this.bussDeptId = bussDeptId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getHandleOpinion() {
        return handleOpinion;
    }

    public void setHandleOpinion(String handleOpinion) {
        this.handleOpinion = handleOpinion;
    }
}
