package com.cy.core.discover.dao;

import com.cy.core.discover.entity.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.wu on 2017/3/7.
 */
public interface DiscoverContentMapper {
    void save(DiscoverContent discoverContent);
    int deleteDiscoverContent(Map<String, Object> map);
    List<DiscoverContent> showAllList(Map<String, Object> map);
    DiscoverContent getDiscoverPersonalInfo(Map<String, Object> map);
    long showAllListCount(Map<String, Object> map);
    String getDiscoverCurrentCommentId(DiscoverComment discoverComment);
    DiscoverComment getDiscoverCurrentComment(String currentCommentId);
    void insertDiscoverComment(DiscoverComment discoverComment);
    String getDiscoverCurrentReplyId(DiscoverReply discoverReply);
    DiscoverReply getDiscoverCurrentReply(String currentReplyId);
    void insertDiscoverReply(DiscoverReply discoverReply);
    void insertDiscoverPraise(DiscoverPraise discoverPraise);
    void deleteDiscoverComment(DiscoverComment discoverComment);
    void deleteDiscoverReply(DiscoverReply discoverReply);
    void deleteDiscoverPraise(DiscoverPraise discoverPraise);
    long getPraise(String id);
    long getPraiseExist(Map<String, Object> map);
    List<DiscoverComment> getDiscoverCommentList(String id);
    List<DiscoverReply> getDiscoverReplyList(String id);
    List<DiscoverReplyComment> getDiscoverReplyReplyList(String id);
    int selectDiscoverWallPhoto(Map<String, Object> map);
    void insertDiscoverWallPhoto(Map<String, Object> map);
    void updateDiscoverWallPhoto(Map<String, Object> map);
    String getDiscoverWallPhoto(Map<String, Object> map);
    List<DiscoverNews> listDiscoverNews(Map<String, Object> map);
    long listDiscoverNewsCount(Map<String, Object> map);
    List<DiscoverRecruitment> listDiscoverRecruitment(Map<String, Object> map);
    long listDiscoverRecruitmentCount(Map<String, Object> map);
    void insertDiscoverMsgPush(DiscoverMsgPush discoverMsgPush);
    void updateDiscoverMsgPush(String pushId);
    List<DiscoverMsgPush> getDiscoverPushMsg(Map<String, Object> map);
    long getDiscoverPushMsgCount(Map<String, Object> map);
    long getDiscoverPushMsgNotReadCount(Map<String, Object> map);
    void updateDiscoverPushMsgIsRead(Map<String, Object> map);
    void deleteDiscoverPushMsg(Map<String, Object> map);
    String getPushId(DiscoverMsgPush discoverMsgPush);
    int getTotalDiscoverComplaint(Map<String, Object> map);
    void updateDiscoverContentDelFlag(Map<String, Object> map);
    int getDiscoverComplaint(Map<String, Object> map);
    void insertDiscoverComplaint(Map<String, Object> map);
    void setAllDiscoverPushMsgRead(Map<String, Object> map);

}
