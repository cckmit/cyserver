package com.cy.core.share.entity;

import com.cy.base.entity.DataEntity;

import java.util.List;

/**
 * @description 分享配置实体类
 * @author niu
 * @date 2016/12/22.
 */
public class Share extends DataEntity<Share>{

    private String schoolName;     //学校名称
    private String iosUrl;         //ios下载地址
    private String androidUrl;     // 安卓下载地址
    private String iosPackageSize;           //ios安装包大小
    private String iosDownloads;      //ios下载量
    private String androidPackageSize;           //android安装包大小
    private String androidDownloads;      //android下载量
    private String pagePictures;   //页面图片
    private String introduce;      //产品介绍

    private String pictureUrls;    //页面图片地址

    private List<File> fileList;  // 页面图片列表

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosPackageSize() {
        return iosPackageSize;
    }

    public void setIosPackageSize(String iosPackageSize) {
        this.iosPackageSize = iosPackageSize;
    }

    public String getIosDownloads() {
        return iosDownloads;
    }

    public void setIosDownloads(String iosDownloads) {
        this.iosDownloads = iosDownloads;
    }

    public String getAndroidPackageSize() {
        return androidPackageSize;
    }

    public void setAndroidPackageSize(String androidPackageSize) {
        this.androidPackageSize = androidPackageSize;
    }

    public String getAndroidDownloads() {
        return androidDownloads;
    }

    public void setAndroidDownloads(String androidDownloads) {
        this.androidDownloads = androidDownloads;
    }

    public String getPagePictures() {
        return pagePictures;
    }

    public void setPagePictures(String pagePictures) {
        this.pagePictures = pagePictures;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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
}
