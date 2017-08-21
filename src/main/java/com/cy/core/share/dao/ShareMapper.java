package com.cy.core.share.dao;

import com.cy.core.share.entity.Share;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/22.
 */
public interface ShareMapper {

    void insert(Share share);

    void update(Share share);

    Share selectById(String id);

    List<Share> selectAll();

    List<Share> selectList(Share share);

}
