package com.cy.core.express.service;

import com.cy.base.entity.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by Mr.wu on 2017/6/7.
 */
public interface ExpressService {
    /**
     * 表白墙发布接口
     * @param message
     * @param content
     */
    void sendExpressContent(Message message, String content);

    /**
     * 表白墙获取接口
     * @param message
     * @param content
     */
    void getExpressContentList(Message message, String content);
    /**
     * 表白墙评论接口
     * @param message
     * @param content
     */
    void saveExpressCommentOrPraise(Message message, String content);
    /**
     * 删除表白墙评论接口
     * @param message
     * @param content
    */
//    void delExpressComment(Message message, String content);
    /**
     * 表白墙详情获取接口
     * @param message
     * @param content
     */
    void getExpressInfo(Message message, String content);


}
