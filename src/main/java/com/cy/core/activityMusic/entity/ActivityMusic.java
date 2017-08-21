package com.cy.core.activityMusic.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.actActivity.entity.ActActivity;
import com.cy.system.Global;

/**
 * 活动音乐实体类
 *
 * @author niu
 * @create 2017-06-06 上午 10:36
 **/

public class ActivityMusic extends DataEntity<ActActivity> {

    private static final long serialVersionUID = 1L;

    private String activityId; //活动id
    private String type; //类型（10：活动开始前音乐；20：活动进行中音乐；23：中奖音乐；30：活动结束后音乐）
    private String isRepeatPlay;//是否重复播放（0：否；1：是）
    private String filePath;//音乐文件路径（相对路径）
    private String filePathSyc;//音乐文件绝对路径
    private String activityName;//活动名称

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsRepeatPlay() {
        return isRepeatPlay;
    }

    public void setIsRepeatPlay(String isRepeatPlay) {
        this.isRepeatPlay = isRepeatPlay;
    }

    public String getFilePath() {
        if(org.apache.commons.lang3.StringUtils.isBlank(filePath) && org.apache.commons.lang3.StringUtils.isNotBlank(filePathSyc) && org.apache.commons.lang3.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(filePathSyc.indexOf(Global.URL_DOMAIN) == 0) {
                filePath = filePathSyc.substring(Global.URL_DOMAIN.length()) ;
                if (!filePath.trim().startsWith("/")){
                    filePath="/"+filePath;
                }
            }else{
                filePath=filePathSyc;
            }
        }
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePathSyc() {
        if(org.apache.commons.lang3.StringUtils.isBlank(filePathSyc) && org.apache.commons.lang3.StringUtils.isNotBlank(filePath) && org.apache.commons.lang3.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(filePath.indexOf("http") < 0) {
                filePathSyc = Global.URL_DOMAIN + filePath ;
            }else{
                filePathSyc=filePath;
            }
        }
        return filePathSyc;
    }

    public void setFilePathSyc(String filePathSyc) {
        this.filePathSyc = filePathSyc;
    }
}
