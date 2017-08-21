package com.cy.core.activityApplicant.entity;

import com.cy.base.entity.DataEntity;

/**
 * 抽奖活动报名人实体类
 *
 * @author niu
 * @create 2017-06-06 上午 10:32
 **/

public class ActivityApplicant extends DataEntity {
    private static final long serialVersionUID = 1L;

    private String activityId;   // 活动编号
    private String weixinAppId;  // 微信公众号APPID
    private String openId ;      // 微信OpenId
    private String headSrc ;     // 报名人头像（相对路径）
    private String name ;        // 报名人姓名
    private String telephone ;   // 报名人手机号
    private String isWinning ;   // 是否已中奖（0：否；1：是）

    private String awardsName ; //奖项名称
    private String prizeName ; //奖品内容
    private String prizeSrc;   //奖品图片


    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getWeixinAppId() {
        return weixinAppId;
    }

    public void setWeixinAppId(String weixinAppId) {
        this.weixinAppId = weixinAppId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getHeadSrc() {
        return headSrc;
    }

    public void setHeadSrc(String headSrc) {
        this.headSrc = headSrc;
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

    public String getIsWinning() {
        return isWinning;
    }

    public void setIsWinning(String isWinning) {
        this.isWinning = isWinning;
    }

    public String getAwardsName() {
        return awardsName;
    }

    public void setAwardsName(String awardsName) {
        this.awardsName = awardsName;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getPrizeSrc() {
        return prizeSrc;
    }

    public void setPrizeSrc(String prizeSrc) {
        this.prizeSrc = prizeSrc;
    }
}
