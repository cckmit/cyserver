package com.cy.core.activityWinning.entity;

import com.cy.base.entity.DataEntity;

/**
 * 中奖人实体类
 *
 * @author niu
 * @create 2017-06-06 下午 22:45
 **/

public class ActivityWinning extends DataEntity<ActivityWinning> {
    private static final long serialVersionUID = 1L;

    private String activityId  ; //   活动编号
    private String applicantId ; //   报名人编号
    private String awardsId    ; //   奖项编号
    private String sort        ; //   排序
    private String activityName ; //活动名称
    private String applicantName ; //获奖人名称
    private String headSrc;        //获奖人头像
    private String telephone;      //获奖人电话
    private String awardsName ; //奖项名称
    private String prizeName ; //奖品内容
    private String prizeSrc;   //奖品图片



    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getAwardsId() {
        return awardsId;
    }

    public void setAwardsId(String awardsId) {
        this.awardsId = awardsId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
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

    public String getHeadSrc() {
        return headSrc;
    }

    public void setHeadSrc(String headSrc) {
        this.headSrc = headSrc;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPrizeSrc() {
        return prizeSrc;
    }

    public void setPrizeSrc(String prizeSrc) {
        this.prizeSrc = prizeSrc;
    }
}
