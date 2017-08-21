package com.cy.core.operation.dao;

import com.cy.core.operation.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/27/16.
 */
public interface CommentMapper {
    void save(Comment comment);
    void delete(Comment comment);
    List<Comment> getCommentList(Map<String,Object> map);
    long count(Map<String,Object> map);
    Comment getById(String id);
}
