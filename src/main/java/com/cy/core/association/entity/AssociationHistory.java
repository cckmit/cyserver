package com.cy.core.association.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;

/**
 * Created by cha0res on 12/13/16.
 */
public class AssociationHistory extends DataEntity<AssociationHistory> {
    private static final long serialVersionUID = 1L;
    private String associationId;       //当前社团编号
    private String type;                //变更类型（10：社名变更；20：社团合并；30：社团解散；）
    private String associationOldId;    //原社团编号
    private String oldName;             //原社团名称
    private String newName;             //新社团名称
    private String oldTypeId;           //原社团类型编号
    private String newTypeId;           //新社团类型编号
    private String startTime;           //开始时间
    private String endTime;             //结束时间
    private String status;              //变更请求状态（10：申请变更；20：已变更；30：变更未通过）
    private String oldTypeName;         //旧社团类型名称
    private String newTypeName;         //新社团类型名称

    public String getAssociationId() {
        if(StringUtils.isBlank(associationId) && StringUtils.isNotBlank(associationOldId)){
            associationId = associationOldId;
        }
        return associationId;
    }

    public void setAssociationId(String associationId) {
        this.associationId = associationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssociationOldId() {
        return associationOldId;
    }

    public void setAssociationOldId(String associationOldId) {
        this.associationOldId = associationOldId;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getOldTypeId() {
        return oldTypeId;
    }

    public void setOldTypeId(String oldTypeId) {
        this.oldTypeId = oldTypeId;
    }

    public String getNewTypeId() {
        return newTypeId;
    }

    public void setNewTypeId(String newTypeId) {
        this.newTypeId = newTypeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOldTypeName() {
        return oldTypeName;
    }

    public void setOldTypeName(String oldTypeName) {
        this.oldTypeName = oldTypeName;
    }

    public String getNewTypeName() {
        return newTypeName;
    }

    public void setNewTypeName(String newTypeName) {
        this.newTypeName = newTypeName;
    }
}
