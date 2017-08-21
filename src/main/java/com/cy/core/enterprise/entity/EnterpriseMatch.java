package com.cy.core.enterprise.entity;

import com.cy.base.entity.DataEntity;
import jdk.internal.dynalink.beans.StaticClass;

import java.util.Date;

/**
 * 云平台校友企业匹配实体类
 *
 * @author niu
 * @create 2017-07-29 上午 10:51
 **/

public class EnterpriseMatch extends DataEntity<EnterpriseMatch> {

    public static String TYPE_ENTERPRISE ="10";
    public static String TYPE_PRODUCT = "20";
    public static String TYPE_TEAM = "30";
    public static String TYPE_POSITION = "40";

    private String bussId ; //业务编号
    private String bussType; //业务类型
    private Date lastUpdateDate; // 最后更新时间
    private String pushResult; //认证推送结果（0：未推送；1：已推送）

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

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getPushResult() {
        return pushResult;
    }

    public void setPushResult(String pushResult) {
        this.pushResult = pushResult;
    }
}
