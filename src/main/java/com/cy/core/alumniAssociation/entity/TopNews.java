package com.cy.core.alumniAssociation.entity;

import com.cy.core.news.entity.News;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/4/26.
 */
public class TopNews implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    * title 标题
     */
    private String title;

    /*
    * newsList 新闻列表
    */
    private List<News> newsList;

    /*
    * donationList 捐赠列表
    */
    private List<Project> donationList;

    /*
    * fxjhList 捐赠列表
    */
    private List<Fxjh> fxjhList;

    /*
    * styleList 风采列表
    */
    private List<News> styleList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<com.cy.core.news.entity.News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<com.cy.core.news.entity.News> newsList) {
        this.newsList = newsList;
    }

    public List<Project> getDonationList() {
        return donationList;
    }

    public void setDonationList(List<Project> donationList) {
        this.donationList = donationList;
    }

    public List<com.cy.core.news.entity.News> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<com.cy.core.news.entity.News> styleList) {
        this.styleList = styleList;
    }

    public List<Fxjh> getFxjhList() {
        return fxjhList;
    }

    public void setFxjhList(List<Fxjh> fxjhList) {
        this.fxjhList = fxjhList;
    }
}
