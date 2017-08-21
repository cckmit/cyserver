package com.cy.core.operation.dao;

import com.cy.core.operation.entity.Favorite;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/27.
 */
public interface FavoriteMapper {

    long count(Favorite favorite);

    void insert(Favorite favorite);

    void update(Favorite favorite);

    void delete(Favorite favorite);

    Favorite selectById(String id);

    List<Favorite> selectList(Favorite favorite);

}
