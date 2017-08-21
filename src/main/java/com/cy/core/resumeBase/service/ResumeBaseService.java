package com.cy.core.resumeBase.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.resumeBase.entity.ResumeBase;

import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 简历基本信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
public interface ResumeBaseService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<ResumeBase> findList(Map<String,Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String,Object> map) ;

    DataGrid<ResumeBase> dataGrid(Map<String, Object> map);

    List<ResumeBase> resumeBaseList();

    ResumeBase getById(String id);
    ResumeBase save(ResumeBase resumeBase);
    ResumeBase update(ResumeBase resumeBase);
    void delete(String ids);

    /**
     *
     *创建/修改简历基本信息接口
     *
     */

    void saveResumeBase(Message message, String content);
    /**
     *
     *删除简历基本信息接口
     *
     */

    void deleteResumeBase(Message message, String content);


    /**
     * 查询简历信息的接口
     * @param message
     * @param content
     */
    void getResumeByAccount(Message message, String content);

    /**
     * 简历拓展类型的增删改
     * @param message
     * @param content
     */
    void operateResumeExtends(Message message, String content);
}
