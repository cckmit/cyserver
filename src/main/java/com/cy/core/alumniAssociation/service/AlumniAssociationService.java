package com.cy.core.alumniAssociation.service;

import com.cy.base.entity.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by Mr.wu on 2017/4/26.
 */
public interface AlumniAssociationService {
    /**
     * 校友会首页信息获取
     * @param message
     * @param content
     */
    void getTopAlumniInfo(Message message, String content);

    /**
     * 惠校友首页信息获取
     * @param message
     * @param content
     */
    void getTopFavourAlumniInfo(Message message, String content);

    /**
     * 惠校友动态栏目获取
     * @param message
     * @param content
     */
    void getServiceColumns(Message message, String content);

    /**
     * 惠校友动态栏目变更
     * @param message
     * @param content
     */
    void updateServiceColumns(Message message, String content);

    /**
     * 根据分会组织ID获取新闻列表接口
     * @param message
     * @param content
     */
    void getNewsByAlumniId(Message message, String content);

    /**
     * 根据分会组织ID获取活动列表接口
     * @param message
     * @param content
     */
    void getEventsByAlumniId(Message message, String content);

    /**
     * 根据分会组织ID获取成员列表
     * @param message
     * @param content
     */
    void getMemberListByAlumniId(Message message, String content);

    /**
     * 统计网站点击量
     * @param message
     * @param content
     */
    void getWebClickNum(Message message, String content);

}
