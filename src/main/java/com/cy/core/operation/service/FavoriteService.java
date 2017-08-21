package com.cy.core.operation.service;

import com.cy.base.entity.Message;
import com.cy.core.operation.entity.Favorite;

import java.util.List;

/**
 * Created by niu on 2016/12/27.
 */
public interface FavoriteService {

    /**
     * 获取我的收藏列表
     * @param message
     * @param content
     */
    void findMyFavoriteList(Message message,String content);

    /**
     * 取消收藏
     * @param message
     * @param content
     */
    void saveOrCancelFavorite(Message message,String content);

}
