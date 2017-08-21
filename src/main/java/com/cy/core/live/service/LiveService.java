package com.cy.core.live.service;

import com.cy.base.entity.Message;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by Mr.wu on 2017/4/7.
 */
public interface LiveService {
    /**
     * 判断是否已经创建直播间接口
     * @param message
     * @param content
     */
    void liveRoomCreateOrNot(Message message, String content);

    /**
     * 直播间创建接口
     * @param message
     * @param content
     * @param upload
     * @param uploadFileName
     */
    void liveRoomCreate(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;

    /**
     * 直播间修改接口
     * @param message
     * @param content
     * @param upload
     * @param uploadFileName
     */
    void liveRoomUpdate(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;

    /**
     * 直播间语音上传接口
     * @param message
     * @param content
     * @param upload
     * @param uploadFileName
     */
    void liveRoomUploadVoice(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;

    /**
     * 直播间话题创建接口
     * @param message
     * @param content
     * @param upload
     * @param uploadFileName
     */
    void liveTopicCreate(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;

    /**
     * 直播间话题修改接口
     * @param message
     * @param content
     * @param upload
     * @param uploadFileName
     */
    void liveTopicUpdate(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;

    /**
     * 直播间展示接口
     * @param message
     * @param content
     */
   void liveRoomListShow(Message message, String content);

    /**
     * 个人直播间展示接口
     * @param message
     * @param content
     */
    void liveRoomShow(Message message, String content);

    /**
     * 直播间关注接口
     * @param message
     * @param content
     */
    void liveRoomAttention(Message message, String content);


    /**
     * 直播间话题详情接口
     * @param message
     * @param content
     */
    void liveTopicInfo(Message message, String content);

    /**
     * 直播间留言板接口
     * @param message
     * @param content
     */
    void liveRoomComment(Message message, String content);

    /**
     * 直播间留言板列表
     * @param message
     * @param content
     */
    void liveRoomCommentList(Message message, String content);

    /**
     * 直播间回复留言接口
     * @param message
     * @param content
     */
    void liveRoomReply(Message message, String content);

    /**
     * 直播间留言详情接口
     * @param message
     * @param content
     */
    void liveRoomCommentInfo(Message message, String content);

    /**
     * 我关注的直播间列表接口
     * @param message
     * @param content
     */
    void liveRoomAttentionList(Message message, String content);

    /**
     * 话题参与接口
     * @param message
     * @param content
     */
    void liveTopicAttention(Message message, String content);

    /**
     * 我参与的话题列表接口
     * @param message
     * @param content
     */
    void liveTopicAttentionList(Message message, String content);

    /**
     * 直播结束接口
     * @param message
     * @param content
     */
    void liveTopicEnd(Message message, String content);

    /**
     * 直播内容保存接口
     * @param message
     * @param content
     */
    void saveLiveRoomContent(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException;

    /**
     * 直播内容获取接口
     * @param message
     * @param content
     */
    void getLiveRoomContent(Message message, String content);

}
