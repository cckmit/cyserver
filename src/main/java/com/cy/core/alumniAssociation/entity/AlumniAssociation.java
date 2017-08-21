package com.cy.core.alumniAssociation.entity;

import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.live.entity.LiveTopic;
import com.cy.core.news.entity.News;
import com.cy.core.schoolServ.entity.SchoolServ;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/4/26.
 */
public class AlumniAssociation implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * cycleList 轮播列表
    */
    private List<News> cycleNewsList;
    /*
    * newsList 新闻列表
    */
    private TopNews topNews;

    /*
    * listRecruitment 求职招聘列表
    */
    private List<Recruitment> listRecruitment;

    /*
    * listRecruitmentCount 求职招聘列表个数
    */
    private long listRecruitmentCount;

    /*
    * liveTopicList 直播间话题列表
    */
    private List<LiveTopic> liveTopicList;

    /*
    * liveTopicListCount 直播间话题列表个数
    */
    private long liveTopicListCount;

    /*
    * enterpriseList 校友企业列表
    */
    private List<Enterprise> enterpriseList;

    /*
    * enterpriseListCount 校友企业列表个数
    */
    private long enterpriseListCount;

    /*
    * enterpriseProductList 校友产品列表
    */
    private List<EnterpriseProduct> enterpriseProductList;

    /*
    * enterpriseProductListCount 校友产品列表个数
    */
    private long enterpriseProductListCount;

    /*
    * smallTitleList 导航栏列表
    */
    private List<SchoolServ> smallTitleList;

    /*
    * smallTitleCount 导航栏个数
    */
    private int smallTitleCount;

    /*
    * campusLifeTitleList 校园生活栏目列表
    */
    private List<SchoolServ> campusLifeTitleList;

    /*
    * campusLifeTitleList 校园生活栏目个数
    */
    private int campusLifeTitleCount;

    public List<News> getCycleNewsList() {
        return cycleNewsList;
    }

    public void setCycleNewsList(List<News> cycleNewsList) {
        this.cycleNewsList = cycleNewsList;
    }

    public int getSmallTitleCount() {
        return smallTitleCount;
    }

    public void setSmallTitleCount(int smallTitleCount) {
        this.smallTitleCount = smallTitleCount;
    }

    public List<SchoolServ> getSmallTitleList() {
        return smallTitleList;
    }

    public void setSmallTitleList(List<SchoolServ> smallTitleList) {
        this.smallTitleList = smallTitleList;
    }

    public TopNews getTopNews() {
        return topNews;
    }

    public void setTopNews(TopNews topNews) {
        this.topNews = topNews;
    }

    public List<LiveTopic> getLiveTopicList() {
        return liveTopicList;
    }

    public void setLiveTopicList(List<LiveTopic> liveTopicList) {
        this.liveTopicList = liveTopicList;
    }

    public List<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public void setEnterpriseList(List<Enterprise> enterpriseList) {
        this.enterpriseList = enterpriseList;
    }


    public long getEnterpriseListCount() {
        return enterpriseListCount;
    }

    public void setEnterpriseListCount(long enterpriseListCount) {
        this.enterpriseListCount = enterpriseListCount;
    }

    public long getEnterpriseProductListCount() {
        return enterpriseProductListCount;
    }

    public void setEnterpriseProductListCount(long enterpriseProductListCount) {
        this.enterpriseProductListCount = enterpriseProductListCount;
    }

    public List<EnterpriseProduct> getEnterpriseProductList() {
        return enterpriseProductList;
    }

    public void setEnterpriseProductList(List<EnterpriseProduct> enterpriseProductList) {
        this.enterpriseProductList = enterpriseProductList;
    }

    public long getLiveTopicListCount() {
        return liveTopicListCount;
    }

    public void setLiveTopicListCount(long liveTopicListCount) {
        this.liveTopicListCount = liveTopicListCount;
    }

    public List<Recruitment> getListRecruitment() {
        return listRecruitment;
    }

    public void setListRecruitment(List<Recruitment> listRecruitment) {
        this.listRecruitment = listRecruitment;
    }

    public long getListRecruitmentCount() {
        return listRecruitmentCount;
    }

    public void setListRecruitmentCount(long listRecruitmentCount) {
        this.listRecruitmentCount = listRecruitmentCount;
    }

    public List<SchoolServ> getCampusLifeTitleList() {
        return campusLifeTitleList;
    }

    public void setCampusLifeTitleList(List<SchoolServ> campusLifeTitleList) {
        this.campusLifeTitleList = campusLifeTitleList;
    }

    public int getCampusLifeTitleCount() {
        return campusLifeTitleCount;
    }

    public void setCampusLifeTitleCount(int campusLifeTitleCount) {
        this.campusLifeTitleCount = campusLifeTitleCount;
    }
}
