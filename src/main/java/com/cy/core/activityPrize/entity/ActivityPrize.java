package com.cy.core.activityPrize.entity;

import com.cy.base.entity.DataEntity;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

/**
 * 奖品实体类
 *
 * @author niu
 * @create 2017-06-06 上午 10:44
 **/

public class ActivityPrize extends DataEntity {
    private static final long serialVersionUID = 1L;

    private String activityId;   // 活动编号
    private String name ;         // 奖项名称
    private String num ;          // 奖项数量
    private String prizeName;    // 奖品名称
    private String prizeSrc ;    // 奖品图片路径（相对路径）
    private String isRepeat;     // 是否允许中间人与其他奖项重复（0：否；1：是）
    private String surplusNum;   // 剩余数量（默认为奖品数量，为0表示奖品已抽完）
    private String sort  ;        // 排序

    private String prizeSrcPic;  //奖品图片路径（绝对路径）

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }
    //相对路径
    public String getPrizeSrc() {
        if(StringUtils.isBlank(prizeSrc) && StringUtils.isNotBlank(prizeSrcPic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(prizeSrcPic.indexOf(Global.URL_DOMAIN) == 0) {
                prizeSrc = prizeSrcPic.substring(Global.URL_DOMAIN.length()) ;
                if (!prizeSrc.trim().startsWith("/")){
                    prizeSrc="/"+prizeSrc;
                }
            }else{
                prizeSrc=prizeSrcPic;
            }
        }
        return prizeSrc;
    }

    public String getPrizeSrcPic() {
        if(StringUtils.isBlank(prizeSrcPic) && StringUtils.isNotBlank(prizeSrc) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(prizeSrc.indexOf("http") < 0) {
                prizeSrcPic = Global.URL_DOMAIN + prizeSrc ;
            }else{
                prizeSrcPic=prizeSrc;
            }
        }
        return prizeSrcPic;
    }

    public void setPrizeSrcPic(String prizeSrcPic) {
        this.prizeSrcPic = prizeSrcPic;
    }

    public void setPrizeSrc(String prizeSrc) {
        this.prizeSrc = prizeSrc;
    }

    public String getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(String surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


}
