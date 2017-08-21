package com.cy.core.enterpriseJob.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.enterpriseJob.dao.EnterpriseJobMapper;
import com.cy.core.enterpriseJob.entity.EnterpriseJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
@Service("EnterpriseJobService")
public class EnterpriseJobServiceImpl implements EnterpriseJobService {

    @Autowired
    private EnterpriseJobMapper enterpriseJobMapper;


    @Override
    public List<EnterpriseJob> findList(Map<String, Object> map) {
        List<EnterpriseJob> list = enterpriseJobMapper.selectEnterpriseJob(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    @Override
    public DataGrid<EnterpriseJob> dataGrid(Map<String, Object> map) {
        DataGrid<EnterpriseJob> dataGrid = new DataGrid<EnterpriseJob>();
        long total = enterpriseJobMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<EnterpriseJob> list = enterpriseJobMapper.selectEnterpriseJob(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public List<EnterpriseJob> enterpriseJobList(){
        Map<String, Object> map = new HashMap<>();
        map.put("isNoLimit", "1");
        List<EnterpriseJob> list = enterpriseJobMapper.selectEnterpriseJob(map);
        return list;
    }

    @Override
    public EnterpriseJob save(EnterpriseJob enterpriseJob) {
        enterpriseJob.preInsert();
        enterpriseJobMapper.insert(enterpriseJob);
        return enterpriseJob;
    }
    @Override
    public EnterpriseJob update(EnterpriseJob enterpriseJob) {
        enterpriseJob.preUpdate();
        enterpriseJobMapper.update(enterpriseJob);
        return enterpriseJob;
    }

    @Override
    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        enterpriseJobMapper.delete(list);
    }

    public EnterpriseJob getById(String id) {
        EnterpriseJob enterpriseJob = enterpriseJobMapper.getById(id) ;
        return enterpriseJob;
    }


    /***********************************************************************
     * 【校友企业】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/

    /**
     * 查詢招聘岗位列表
     * @param message
     * @param content
     */
    public void findEnterpriseJobList(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        String enterpriseId = (String)map.get("enterpriseId");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        }else{
            map.put("isNoLimit", "1");
        }
        if(StringUtils.isNotBlank(enterpriseId)){
            EnterpriseJob enterpriseJob = enterpriseJobMapper.getById(enterpriseId);
            if( enterpriseJob == null ){
                message.init(false, "不存在的企业ID", null);
            }
        }

        List<EnterpriseJob> list = enterpriseJobMapper.selectEnterpriseJob(map);
        long total = enterpriseJobMapper.count(map);
        DataGrid<EnterpriseJob> dataGrid = new DataGrid<>();
        dataGrid.setTotal(total);
        dataGrid.setRows(list);
        message.init(true, "查询成功", dataGrid);
    }


    /**
     * 查詢招聘岗位详情
     * @param message
     * @param content
     */
    public void findEnterpriseJob(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String id = map.get("id");
        String accountNum = map.get("accountNum");
        String enterpriseId = (String)map.get("enterpriseId");
        if(StringUtils.isBlank(map.get("id"))){
            message.setMsg("职位Id为空");
            message.setSuccess(false);
            return;
        }

        map.put("id", id );
        map.put("isNoLimit", "1");

        EnterpriseJob enterpriseJob = enterpriseJobMapper.getById(id);

        if(enterpriseJob == null){
            message.setMsg("招聘岗位不存在");
            message.setSuccess(false);
            return;

        }else{
            message.init(true, "查询成功", enterpriseJob);
        }
    }
}
