package com.cy.core.enterprise.service;


import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;

import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.enterprise.dao.EnterpriseMapper;

import com.cy.core.enterprise.dao.EnterpriseTeamMapper;

import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.enterprise.entity.EnterpriseTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
@Service("enterpriseTeamService")
public class EnterpriseTeamServiceImpl implements EnterpriseTeamService{

    @Autowired
    private EnterpriseTeamMapper enterpriseTeamMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    public DataGrid<EnterpriseTeam> dataGridProduct(Map<String, Object> map){
        long count = enterpriseTeamMapper.count(map);
        DataGrid<EnterpriseTeam> dataGrid = new DataGrid<>();
        dataGrid.setTotal(count);
        int page = (Integer) map.get("page");
        int rows = (Integer) map.get("rows");
        if(page >0 && rows > 0)
        {
            int start = ( (page-1)*rows);
            map.put("start", start);
        }
        else
            map.put("isNotLimit", "1");

        List<EnterpriseTeam> list = enterpriseTeamMapper.findEnterpriseTeamList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public EnterpriseTeam getById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("isNotLimit", "1");
        List<EnterpriseTeam> list = enterpriseTeamMapper.findEnterpriseTeamList(map);
        EnterpriseTeam enterpriseTeam = new EnterpriseTeam();

       /* if(enterpriseTeam != null && org.apache.commons.lang3.StringUtils.isNotBlank(enterpriseTeam.getDescription())) {
            enterpriseTeam.setDescription(EditorUtils.changeSrcFromRelativeToAbsolute(enterpriseTeam.getDescription()));
        }*/
        if(list != null && list.size()>0){
            enterpriseTeam = list.get(0);
        }
        return enterpriseTeam;
    }

    public int saveProduct(EnterpriseTeam enterpriseTeam){
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId",enterpriseTeam.getEnterpriseId());
        long count = enterpriseTeamMapper.count(map);
        if(count > 5){
            return 1;
        }
        enterpriseTeam.preInsert();
        enterpriseTeamMapper.save(enterpriseTeam);
        return 0;
    }

    public int updateProduct(EnterpriseTeam enterpriseTeam){
        enterpriseTeam.preUpdate();
        enterpriseTeamMapper.update(enterpriseTeam);
        return 0;
    }

    public int delete(String ids){
        String[] array = ids.split(",");
        for(String id : array){
            EnterpriseTeam enterpriseTeam = getById(id);
            enterpriseTeam.preUpdate();
            enterpriseTeam.setDelFlag("1");
            enterpriseTeamMapper.update(enterpriseTeam);
        }
        return 0;
    }
/***********************************************************************
 * 【校友企业產品】相关API（以下区域）
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
     * 查询校友企业成员列表
     * @param message
     * @param content
     */
    public void findEnterpriseTeamList(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        String enterpriseId = (String)map.get("enterpriseId");
        if(StringUtils.isNotBlank(enterpriseId)){
            Enterprise enterprise = enterpriseMapper.getById(enterpriseId);
            if( enterprise == null ){
                message.init(false, "不存在的企业ID", null);
            }
        }

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        }else{
            map.put("isNotLimit", "1");
        }
        List<EnterpriseTeam> teamList = enterpriseTeamMapper.findEnterpriseTeamList(map);
        long total = enterpriseTeamMapper.count(map);
        DataGrid<EnterpriseTeam> dataGrid = new DataGrid<>();
        dataGrid.setTotal(total);
        dataGrid.setRows(teamList);
        message.init(true, "查询成功", dataGrid);
    }

}
