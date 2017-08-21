package com.cy.core.alumniAssociation.dao;

import com.cy.core.alumniAssociation.entity.Fxjh;
import com.cy.core.alumniAssociation.entity.Project;
import com.cy.core.alumniAssociation.entity.Recruitment;
import com.cy.core.alumniAssociation.entity.UserService;
import com.cy.core.event.entity.Event;
import com.cy.core.live.entity.LiveTopic;
import com.cy.core.news.entity.News;
import com.cy.core.schoolServ.entity.SchoolServ;
import com.cy.core.userProfile.entity.UserProfile;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.wu on 2017/4/26.
 */
public interface AlumniAssociationMapper {

    //校友会栏目获取
    List<SchoolServ> getServiceList();

    //校友会栏目个数获取
    int getServiceListCount();

    //校园生活栏目获取
    List<SchoolServ> getCampusLifeTitleList();

    //校园生活栏目个数获取
    int getCampusLifeTitleCount();

    //惠校友栏目获取
    List<SchoolServ> getFavourAlumniServiceList();

    //惠校友栏目个数获取
    int getFavourAlumniServiceListCount();

    //爱心捐赠项目取得
    List<Project> selectDonateList(Map<String, Object> map);

    /**
     * 获取返校计划列表
     *
     */
    List<Fxjh> findFxjhList(Map<String, Object> map);

    /**
     * 获取直播间话题列表
     *
     */
    List<LiveTopic> getTopicList(Map<String, Object> map);

    //直播间话题列表个数
    long getTopicListCount();

    //求职招聘列表
    List<Recruitment> listRecruitment(Map<String, Object> map);

    //求职招聘列表个数
    long listRecruitmentCount(Map<String, Object> map);

    //常用服务栏目获取
    UserService getUserServiceList(String accountNum);

    //更新常用服务
    void updateUserServiceList(Map<String, Object> map);

    //插入常用服务
    void insertUserServiceList(Map<String, Object> map);

    //惠校友首页常用栏目获取
    List<SchoolServ> selectUserFavourAlumniServiceList(Map<String, Object> map);

    //惠校友更多栏目获取
    UserService getMoreServiceColumns(String accountNum);

    //惠校友更多常用栏目获取
    List<SchoolServ> selectNormalServiceList(Map<String, Object> map);

    //惠校友更多不常用栏目获取
    List<SchoolServ> selectUnNormalServiceList(Map<String, Object> map);

    //惠校友动态栏目变更
    void updateMoreServiceColumns(Map<String, Object> map);

    //根据分会组织ID获取新闻列表
    List<News> getNewsByAlumniId(Map<String, Object> map);

    //根据分会组织ID获取新闻列表数量
    long getNewsByAlumniIdCount(Map<String, Object> map);

    //根据分会组织ID获取活动列表
    List<Event> getEventsByAlumniId(Map<String, Object> map);

    //根据分会组织ID获取活动列表数量
    long getEventsByAlumniIdCount(Map<String, Object> map);

    //根据分会组织ID获取活动列表数量
    List<UserProfile> getMemberListByAlumniId(Map<String, Object> map);

    //获取当前用户在该会中的状态
    String getMyStatus(Map<String, Object> map);

    //分会总人数
    long getMemberCount(Map<String, Object> map);

    //网站点击量取得
    String getClickNumCnt();

    //网站点击量插入
    void insertClickNumCnt(long pageView);

    //网站点击量更新
    void updateClickNumCnt(long pageView);

}
