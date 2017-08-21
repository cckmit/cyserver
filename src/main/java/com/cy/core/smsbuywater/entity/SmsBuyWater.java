package com.cy.core.smsbuywater.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/17.
 */
public class SmsBuyWater  extends DataEntity<SmsBuyWater> {
    private static final long serialVersionUID = 1L;

    private String account;         // 應用帳號
    private String accountId;		// 应用账号编号
    private String orderNo;			// 订单号
    private String buyTime;			// 购买时间
    private String buyNum;			// 购买条数
    private String buyPrice;		// 购买金额
    private String surplusNum;		// 剩余条数
    private String surplusPrice;	// 剩余金额
    private String singlePrice;		// 单条金额
    private String startTime;		// 开始使用时间
    private String endTime;			// 短信用完时间
    private String payStatus ;		// 支付状态(0:未支付;1:已支付)

    private String payTime ;		// 支付时间
    private String payType ;		// 支付方式(10:支付宝;20:微信)
    private String bizNo ;			// 业务号(支付宝业务号)
    private String appBussId ;		// 应用业务编号
    private String notifyUrl ;		// 回调通知地址
    private String resultUrl ;		// 回调地址

    private String flowPacketDetailId; //流量包明细编号
    private String startime;			//开始的购买时间
    private String endtime;				//结束时间
    private String status;              //短信使用状态
    private String sign;				//学校签名

    private String name ;			// 列表描述（购买时间-购买条数）
    private String filter ;			// 查询（0：查询尚未使用；1：查询尚未用完；2：查询已使用完）

    private String textField;// "日期 - 剩余条数 - 订单号"

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(String surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getSurplusPrice() {
        return surplusPrice;
    }

    public void setSurplusPrice(String surplusPrice) {
        this.surplusPrice = surplusPrice;
    }

    public String getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
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

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getAppBussId() {
        return appBussId;
    }

    public void setAppBussId(String appBussId) {
        this.appBussId = appBussId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getFlowPacketDetailId() {
        return flowPacketDetailId;
    }

    public void setFlowPacketDetailId(String flowPacketDetailId) {
        this.flowPacketDetailId = flowPacketDetailId;
    }

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTextField() {
        String str = "";
        if (StringUtils.isNotBlank(this.buyTime)) {
            str = this.buyTime.replaceAll("-","");
        }
        str  += " - " + this.surplusNum + " - " + this.orderNo;
        return str;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }
}
