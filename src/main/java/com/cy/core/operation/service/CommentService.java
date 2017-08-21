package com.cy.core.operation.service;

import com.cy.base.entity.Message;

/**
 * Created by cha0res on 12/27/16.
 */
public interface CommentService {
    void saveComment(Message message, String content);
    void findCommentList(Message message, String content);
}
