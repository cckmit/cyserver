package com.cy.core.share.dao;

import com.cy.core.share.entity.File;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/23.
 */
public interface FileMapper {

    void insert(File file);

    void update(File file);

    void delete(Map<String,Object> map);

    List<File> selectList(Map<String, Object> map);

    List<File> selectPicList(Map<String, Object> map);


}
