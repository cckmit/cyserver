package com.cy.core.actActivity.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityMusic.entity.ActivityMusic;
import com.cy.core.activityPrize.entity.ActivityPrize;
import com.cy.core.share.entity.File;
import com.cy.system.Global;

import java.util.Date;
import java.util.List;


/**
 * 抽奖活动实体类
 *
 * @author niu
 * @create 2017-06-06 上午 10:17
 **/

public class ActActivity extends DataEntity<ActActivity> {

    private static final long serialVersionUID = 1L;

    private String name;//活动名称
    private String type;//活动类型（10抽奖活动）
    private String startTime;//活动开始名称
    private String endTime;//活动结束名称
    private String signUpEndTime;//活动报名结束时间
    private String signUpStartTime;//活动报名开始时间
    private String introduction;//活动介绍
    private String pic;//活动图片绝对路径
    private String picSny;//活动图片绝对路径
    private String organizer;//主办单位
    private Long sort;
    private String pictureUrls;//关联图片
    private List<File> fileList;  // 页面图片列表
    private Long applicantCount;//报名人数
    private Long winningCount;//中奖人数
    private String status;//是否进行
    private List<ActivityMusic> musicList;//音乐数组

    private List<ActivityMusic> activityMusicList; //活动音乐集合
    private List<ActivityPrize> activityPrizeList; //活动奖项集合

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSignUpEndTime() {
        return signUpEndTime;
    }

    public void setSignUpEndTime(String signUpEndTime) {
        this.signUpEndTime = signUpEndTime;
    }

    public String getSignUpStartTime() {
        return signUpStartTime;
    }

    public void setSignUpStartTime(String signUpStartTime) {
        this.signUpStartTime = signUpStartTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPic() {
        if(org.apache.commons.lang3.StringUtils.isBlank(pic) && org.apache.commons.lang3.StringUtils.isNotBlank(picSny) && org.apache.commons.lang3.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(picSny.indexOf(Global.URL_DOMAIN) == 0) {
                pic = picSny.substring(Global.URL_DOMAIN.length()) ;
                if (!pic.trim().startsWith("/")){
                    pic="/"+pic;
                }
            }else{
                pic=picSny;
            }
        }
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicSny() {
        if(org.apache.commons.lang3.StringUtils.isBlank(picSny) && org.apache.commons.lang3.StringUtils.isNotBlank(pic) && org.apache.commons.lang3.StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(pic.indexOf("http") < 0) {
                picSny = Global.URL_DOMAIN + pic ;
            }else{
                picSny=pic;
            }
        }
        return picSny;
    }

    public void setPicSny(String picSny) {
        this.picSny = picSny;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(String pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public List<ActivityMusic> getActivityMusicList() {
        return activityMusicList;
    }

    public void setActivityMusicList(List<ActivityMusic> activityMusicList) {
        this.activityMusicList = activityMusicList;
    }

    public List<ActivityPrize> getActivityPrizeList() {
        return activityPrizeList;
    }

    public void setActivityPrizeList(List<ActivityPrize> activityPrizeList) {
        this.activityPrizeList = activityPrizeList;
    }

    public Long getApplicantCount() {
        return applicantCount;
    }

    public void setApplicantCount(Long applicantCount) {
        this.applicantCount = applicantCount;
    }

    public Long getWinningCount() {
        return winningCount;
    }

    public void setWinningCount(Long winningCount) {
        this.winningCount = winningCount;
    }

    public List<ActivityMusic> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<ActivityMusic> musicList) {
        this.musicList = musicList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
