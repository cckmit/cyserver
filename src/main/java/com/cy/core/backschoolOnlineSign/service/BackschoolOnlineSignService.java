package com.cy.core.backschoolOnlineSign.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.backschoolOnlineSign.entity.BackschoolOnlineSign;

import java.util.Map;


public interface BackschoolOnlineSignService {

    /**
     * 存储
     * @return true，成功；false，失败；
     *
     */
    boolean save(BackschoolOnlineSign backschoolOnlineSign);


    /**
     * 更新
     *
     * @return true，成功；false，失败；
     *
     */
    boolean update(BackschoolOnlineSign backschoolOnlineSign);

    /**
     * 获取总条数
     *
     * @return long
     */
    long count(Map<String, Object> map);

    /**
     * 所有信息列表(带分页)
     *
     * @return DataGrid<AlumniCard>
     */
    DataGrid<BackschoolOnlineSign> dataGrid(Map<String, Object> map);

    /**
     * 删除
     *
     */
    void delete(String ids);


    /**
     * 逻辑删除
     *
     */
    void deletion(String ids);



    /**
     * 获取详情
     * @return List<AlumniCard>
     */
    BackschoolOnlineSign selectById(String id);




    /**
     * 申请修改校友卡接口
     * @param message
     * @param content
     */

    void saveOnlineSign(Message message, String content);

    /*
     * 查看报名详情接口
     * @param message
     * @param content
     */

    void getOnlineSign(Message message, String content);

    /*
     * 查看报名人列表接口
     * @param message
     * @param content
     */

    void getSignList(Message message, String content);
}
