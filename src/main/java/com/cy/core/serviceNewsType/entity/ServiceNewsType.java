package com.cy.core.serviceNewsType.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.news.entity.News;

import java.util.List;


public class ServiceNewsType  extends DataEntity<ServiceNewsType> {

    //父id
    private String parentId;

    //类型名称
    private String name;

    //1.新闻类别,2.链接，3.单页面
    private int type;

    //新闻的来源,1.总会新闻，2校友会新闻
    private int origin;

    //栏目访问地址
    private String url;

    //排列顺序
    private int orderNum;

    //所在地,市
    private String cityName;

    //是否上首页
    private int isMain;

    //是否上导航
    private int isNavigation;

    //服务Id
    private String serviceId;

    //服务名
    private String serviceName;

    //组织Id
    private String deptId;

    //类别
    private String channel;


    /**--该栏目下所有新闻集合--**/
    private List<News> newsList;
    /**--取相应的新闻web用到--**/
    private String json_news_url;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getIsMain() {
        return isMain;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public int getIsNavigation() {
        return isNavigation;
    }

    public void setIsNavigation(int isNavigation) {
        this.isNavigation = isNavigation;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public String getJson_news_url() {
        return json_news_url;
    }

    public void setJson_news_url(String json_news_url) {
        this.json_news_url = json_news_url;
    }
}


