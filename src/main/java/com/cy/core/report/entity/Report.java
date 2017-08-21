package com.cy.core.report.entity;

import com.cy.base.entity.DataEntity;


/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class Report extends DataEntity<Report> {
    private String id;  //主键
    private String userId;  //用户的编号（举报人的id）
    private String bussId;   //业务的id 包括活动的Id或者花絮的ID
    private String content;  //中举报的内容
    private String bussType; //据据举报的类型 10活动；15：花絮；
    private String handleUserId;//处理人编号
    private String handleStatus;//处理结果(10处理中[默认]；20：已处理；30：举报不实)
    private String handleDesc;//处理结果描述
    private String reportPerson;//举报人名子
    private String bussName;    //
    private String pictureUrls;    //页面图片地址
    private String isTrue;          //举报是否属实
    private String handerName;  //处理此举报信息的处理人的名称

    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleDesc() {
        return handleDesc;
    }

    public void setHandleDesc(String handleDesc) {
        this.handleDesc = handleDesc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public String getReportPerson() {
        return reportPerson;
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }

    public String getBussName() {
        return bussName;
    }

    public void setBussName(String bussName) {
        this.bussName = bussName;
    }

    public String getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(String isTrue) {
        this.isTrue = isTrue;
    }

    public String getHanderName() {
        return handerName;
    }

    public void setHanderName(String handerName) {
        this.handerName = handerName;
    }


}
