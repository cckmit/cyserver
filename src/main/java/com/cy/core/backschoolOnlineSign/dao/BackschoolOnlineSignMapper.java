package com.cy.core.backschoolOnlineSign.dao;


import com.cy.core.backschoolOnlineSign.entity.BackschoolOnlineSign;

import java.util.List;
import java.util.Map;

public interface BackschoolOnlineSignMapper {


    /**
     * 保存
     *
     *
     * @return true，成功；false，失败；
     *
     */
    boolean save(BackschoolOnlineSign backschoolOnlineSign);


    /**
     * 更新
     *
     *
     * @return true，成功；false，失败；
     *
     */
    boolean update(BackschoolOnlineSign backschoolOnlineSign);

    /**
     * 获取总条数
     *
     *
     * @return long
     */
    long count(Map<String, Object> map);

    /**
     * 获取列表
     *
     * @return List<AlumniCard>
     */
    List<BackschoolOnlineSign> list(Map<String, Object> map);


    /**
     * 删除
     *
     */
    void delete(List<String> list);


    /**
     * 逻辑删除
     *
     */
    void deletion(List<String> list);

    /**
     * 获取详情
     *
     * @param
     * @return List<AlumniCard>
     */
    BackschoolOnlineSign selectById(String id);


}
