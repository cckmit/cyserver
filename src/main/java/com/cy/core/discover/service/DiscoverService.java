package com.cy.core.discover.service;

import com.cy.base.entity.Message;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Mr.wu on 2017/3/7.
 */
public interface DiscoverService {
    /**
     * 发现发布消息
     * @param message
     * @param content
     */
    void save(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;;
    /**
     * 朋友圈展示接口
     * @param message
     * @param content
     */
    void showAllList(Message message, String content);
    /**
     * 评论和点赞接口
     * @param message
     * @param content
     */
    void saveDiscoverCommentOrPraise(Message message, String content);
    /**
     * 删除接口
     * @param message
     * @param content
     */
    void deleteDiscoverContent(Message message, String content);

    /**
     * 背景墙图片上传
     *
     * @param message
     * @param content
     * @param upload
     * @param uploadFileName
     */
    void uploadDiscoverWallPhoto(Message message, String content, File[] upload, String[] uploadFileName);

    /**
     * 背景墙图片获取
     *
     * @param message
     * @param content
     */
    void getDiscoverWallPhoto(Message message, String content);

    /**
     * 新闻获取
     *
     * @param message
     * @param content
     */
    void getDiscoverNewsList(Message message, String content);

    /**
     * 求职招聘获取
     *
     * @param message
     * @param content
     */
    void getDiscoverRecruitmentList(Message message, String content);

    /**
     * 回复评论接口
     * @param message
     * @param content
     */
    void saveDiscoverReply(Message message, String content);

    /**
     * 朋友圈展示个人详情接口
     * @param message
     * @param content
     */
    void getDiscoverPersonalInfo(Message message, String content);

    /**
     * 获取朋友圈消息推送接口
     * @param message
     * @param content
     */
    void getDiscoverGetPushMsg(Message message, String content);

    /**
     * 设置朋友圈消息推送已读接口
     * @param message
     * @param content
     */
    void discoverGetPushMsgIsRead(Message message, String content);

    /**
     * 朋友圈消息推送清空接口
     * @param message
     * @param content
     */
    void discoverGetPushMsgDelete(Message message, String content);

    /**
     * 朋友圈举报接口
     * @param message
     * @param content
     */
    void discoverContentComplaint(Message message, String content);

}
