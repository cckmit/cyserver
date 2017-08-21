package com.cy.core.live.dao;

import com.cy.core.live.entity.*;
import com.cy.core.schoolServ.entity.SchoolServ;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public interface LiveMapper {
    //是否已经创建直播间
    String getLiveRoom(String accountNum);

    //创建直播间
    void liveRoomCreate(LiveRoom liveRoom);

    //修改直播间
    void liveRoomUpdate(LiveRoom liveRoom);

    //获取当前直播间ID
    String getCurrentLiveRoomId(LiveRoom liveRoom);

    //获取当前直播间信息
    LiveRoom getCurrentLiveRoomInfo(String currentLiveRoomId);

    //创建直播间话题
    void liveTopicCreate(LiveTopic liveTopic);

    //修改直播间话题
    void liveTopicUpdate(LiveTopic liveTopic);

    //直播间展示
    List<LiveRoomTopic> showLiveRoomList(Map map);

    //直播间总数
    long showLiveRoomListCount();

    //个人直播间展示
    LiveRoomTopic showLiveRoom(Map map);

    //个人直播间关注人数
    long getLiveRoomAttentionNum(Map map);

    //是否关注
    long getAttentionExist(Map map);

    //个人直播间话题展示
    List<LiveTopic> showLiveRoomTopic(Map map);

    //个人话题总数
    long showLiveTopicCount(Map map);

    //直播间关注
    void liveRoomAttention(Map map);

    //取消直播间关注
    void liveRoomAttentionCancel(Map map);

    //直播间话题详情
    LiveTopic showLiveTopicInfo(Map map);

    //直播间留言
    void insertLiveRoomComment(Map map);

    //获取当前留言ID
    String getCurrentLiveCommentId(Map map);

    //获取当前留言内容
    LiveComment getCurrentLiveComment(String currentLiveCommentId);

    //删除直播间留言
    void deleteLiveRoomComment(Map map);

    //直播间留言板展示
    List<LiveComment> showLiveCommentList(Map map);

    //直播间留言板总数
    long showLiveCommentListCount(Map map);

    //直播间留言回复
    void insertLiveRoomReply(Map map);

    //获取当前回复ID
    String getCurrentLiveReplyId(Map map);

    //获取当前回复内容
    LiveReply getCurrentLiveReply(String currentLiveReplyId);

    //删除直播间回复
    void deleteLiveRoomReply(Map map);

    //直播间当前留言
    LiveComment showLiveComment(Map map);

    //回复列表
    List<LiveReply> getLiveReplyList(Map map);

    //我关注的直播间列表
    List<LiveRoomAttention> getLiveRoomAttentionList(Map map);

    //我关注的直播间列表个数
    long getLiveRoomAttentionCount(Map map);

    //参与话题
    void liveTopicAttention(Map map);

    //参与人数更新
    void updateLiveTopicNumOfPeople(Map map);

    //我参与的话题列表
    List<LiveTopicAttention> getLiveTopicAttentionList(Map map);

    //我参与的话题列表个数
    long getLiveTopicAttentionCount(Map map);

    //直播状态变更
    void updateLiveIn(Map map);

    //直播间内容保存
    void insertLiveRoomContent(Map map);

    //直播间内容获取
    List<LiveRoomContent> getLiveRoomContent(Map map);

    //直播间内容总数
    long getLiveRoomContentCount(Map map);

    //直播间是否参与话题
    long getLiveTopicAttentionExist(Map map);
}
