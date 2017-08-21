package com.cy.core.serviceNews.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.serviceNewsType.entity.ServiceNewsType;

/**
 * 服务新闻实体类
 */
public class ServiceNews extends DataEntity<ServiceNewsType> {
    private String title;           // 标题
    private String pic;             // 新闻图片
    private String content;         // 新闻内容
    private String introduction;    // 简介
    private Long serviceId;         // 所属服务编号
    private String type;            // 类型: 资讯、新闻
    private String newsUrl;         // 新闻访问地址
    private String topnews;         // 推荐新闻(轮播图)
    private String topnewsWeb;      // 推荐新闻(web)
    private String cityName;        // 城市名称
    private String alumniId;        // 创建校友组织编号
    private String status;          // 状态（10：新增；20：审核通过；30：审核不通过；40：下线）
    private String channel;         // 栏目
    private String browseNum;       // 浏览数

    private String serviceName;
    private String deptName;
    private String mainName;
    private String name;
    private String channelName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getTopnews() {
        return topnews;
    }

    public void setTopnews(String topnews) {
        this.topnews = topnews;
    }

    public String getTopnewsWeb() {
        return topnewsWeb;
    }

    public void setTopnewsWeb(String topnewsWeb) {
        this.topnewsWeb = topnewsWeb;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(String alumniId) {
        this.alumniId = alumniId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(String browseNum) {
        this.browseNum = browseNum;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
