package com.cy.core.share.entity;

import com.cy.base.entity.DataEntity;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by niu on 2016/12/22.
 */
public class File extends DataEntity<File>{

    private String  name;         //文件名称
    private String  bussType;   //业务类型（10：分享；20：捐赠订单；30：活动；40：评论；50产品）
    private String  fileType;    //文件类型
    private String  fileSize;    //文件大小
    private String  fileSort;    //文件排序
    private String  pictureUrl;  //图片地址
    private String  small;       //小图ID
    private String  large;       //大图ID
    private String  fileGroup;   //文件分组ID


    private String picUrl ;// 图片地址绝对路径

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {

        if(StringUtils.isBlank(pictureUrl) && StringUtils.isNotBlank(picUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(picUrl.trim().indexOf(Global.URL_DOMAIN) == 0) {
                pictureUrl = picUrl.trim().substring(Global.URL_DOMAIN.length()) ;
                if (!pictureUrl.trim().startsWith("/")){
                    pictureUrl="/"+pictureUrl;
                }
            }else{
                pictureUrl=picUrl;
            }
        }
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPicUrl() {
        if(StringUtils.isBlank(picUrl) && StringUtils.isNotBlank(pictureUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(pictureUrl.indexOf("http") < 0) {
                if (!pictureUrl.trim().startsWith("/")){
                  pictureUrl="/"+pictureUrl;
                }
                picUrl = Global.URL_DOMAIN + pictureUrl ;
            }else{
                picUrl=pictureUrl;
            }
        }
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSort() {
        return fileSort;
    }

    public void setFileSort(String fileSort) {
        this.fileSort = fileSort;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }
}
