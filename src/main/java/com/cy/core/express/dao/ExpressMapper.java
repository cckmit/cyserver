package com.cy.core.express.dao;

import com.cy.core.express.entity.ExpressComment;
import com.cy.core.express.entity.ExpressContent;
import com.cy.core.express.entity.ExpressUpvote;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public interface ExpressMapper {
    //表白表插入
    void insertExpressContent(Map<String, Object> map);
    //表白墙获取
    List<ExpressContent> getExpressContentList(Map<String, Object> map);
    //表白墙个数获取
    long getExpressContentCount();
    //表白评论表插入
    void insertExpressComment(Map<String, Object> map);
    //表白评论列表
    List<ExpressComment> getExpressCommentList(Map<String, Object> map);
    //删除表白墙评论
    void deleteExpressComment(Map<String, Object> map);
    //获取表白墙评论数量
    Long getExpressCommentCount(Map<String, Object> map);
    //表白墙点赞
    void insertExpressUpvote(Map<String, Object> map);
    //表白墙取消点赞
    void cancelExpressUpvote(Map<String, Object> map);
    //表白墙点赞信息
    ExpressUpvote getExpressUpvote(Map<String, Object> map);
    //表白墙点赞数量
    Long getExpressUpvoteCount(Map<String, Object> map);
    //当前表白内容
    ExpressContent getExpressContentById(Map<String, Object> map);
    //当前表白墙评论内容ID获取
    String getCurrentExpressCommentId(String expressId);
    //当前表白墙评论内容获取
    ExpressComment getCurrentExpressComment(String currentCommentId);
    //当前表白墙点赞ID获取
    String getCurrentExpressUpvoteId(String expressId);
    //当前表白墙点赞内容获取
    ExpressUpvote getCurrentExpressUpvote(String currentUpvoteId);
}
