package com.cy.core.enterprise.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.JsonUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.clientrelease.entity.Json;
import com.cy.core.enterprise.dao.EnterpriseMapper;
import com.cy.core.enterprise.dao.EnterpriseMatchMapper;
import com.cy.core.enterprise.dao.EnterpriseProductMapper;
import com.cy.core.enterprise.dao.EnterpriseTeamMapper;
import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseMatch;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.enterprise.entity.EnterpriseTeam;
import com.cy.core.enterpriseJob.dao.EnterpriseJobMapper;
import com.cy.core.enterpriseJob.entity.EnterpriseJob;
import com.cy.system.Global;
import com.cy.util.CloudPlatformUtils;
import com.cy.util.Collections3;
import com.cy.util.DateUtils;
import com.cy.util.PairUtil;
import com.google.common.collect.Maps;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.sun.tools.javac.comp.Enter;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.quartz.jobs.ee.ejb.EJBInvokerJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.ObjectName;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.cy.common.utils.BdMapUtils.getDistance;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 企业信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2016-1-6
 */

@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService{

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private EnterpriseProductMapper enterpriseProductMapper;

    @Resource
    private EnterpriseMatchMapper enterpriseMatchMapper;

    @Autowired
    private EnterpriseTeamMapper enterpriseTeamMapper;

    @Autowired
    private EnterpriseJobMapper enterpriseJobMapper;

    @Override
    public List<Enterprise> findList(Map<String, Object> map) {
        List<Enterprise> list = enterpriseMapper.selectEnterprise(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    @Override
    public DataGrid<Enterprise> dataGrid(Map<String, Object> map) {
        DataGrid<Enterprise> dataGrid = new DataGrid<Enterprise>();
        long total = enterpriseMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Enterprise> list = enterpriseMapper.selectEnterprise(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public List<Enterprise> enterprisesList(){
        Map<String, Object> map = new HashMap<>();
        map.put("isNotLimit", "1");
        List<Enterprise> list = enterpriseMapper.selectEnterprise(map);
        return list;
    }

    @Override
    public Enterprise save(Enterprise enterprise) {
        enterprise.preInsert();
        enterpriseMapper.insert(enterprise);
        return enterprise;
    }
    @Override
    public Enterprise update(Enterprise enterprise) {
        enterprise.preUpdate();
        enterpriseMapper.update(enterprise);
        return enterprise;
    }

    @Override
    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        enterpriseMapper.delete(list);
    }

    public Enterprise getById(String id) {
        Enterprise enterprise = enterpriseMapper.getById(id) ;
        if(enterprise != null && org.apache.commons.lang3.StringUtils.isNotBlank(enterprise.getDescription())) {
            enterprise.setDescription(EditorUtils.changeSrcFromRelativeToAbsolute(enterprise.getDescription()));
        }

        return enterprise;
    }

    public void updateAuditResult(Enterprise enterprise,String flag){
        Map<String,Object> param = Maps.newHashMap();
        param.put("bussId",enterprise.getCloudId());
        param.put("bussType",EnterpriseMatch.TYPE_ENTERPRISE);
        EnterpriseMatch enterpriseMatch = new EnterpriseMatch();
        List<EnterpriseMatch> enterpriseMatchList =enterpriseMatchMapper.findList(param);
        if (enterpriseMatchList!=null && !enterpriseMatchList.isEmpty()){
            enterpriseMatch = enterpriseMatchList.get(0);
            enterpriseMatch.setPushResult(flag);
            enterpriseMatchMapper.update(enterpriseMatch);
        }else {
            enterpriseMatch.setBussId(enterprise.getCloudId());
            enterpriseMatch.setBussType(EnterpriseMatch.TYPE_ENTERPRISE);
            enterpriseMatch.setLastUpdateDate(enterprise.getUpdateDate());
            enterpriseMatch.setPushResult(flag);
            enterpriseMatch.preInsert();
            enterpriseMatchMapper.insert(enterpriseMatch);
        }
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
     * 查詢企業列表
     * @param message
     * @param content
     */
    public void findEnterpriseList(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        }else{
            map.put("isNotLimit", "1");
        }

        List<Enterprise> list = enterpriseMapper.selectEnterprise(map);
        long total = enterpriseMapper.count(map);
        DataGrid<Enterprise> dataGrid = new DataGrid<>();
        dataGrid.setTotal(total);
        dataGrid.setRows(list);
        message.init(true, "查询成功", dataGrid);
    }

    /**
     * 查詢企業詳情包括產品列表
     * @param message
     * @param content
     */
    public void findEnterprise(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String enterpriseId = (String)map.get("enterpriseId");
        if(StringUtils.isBlank(enterpriseId)){
            message.setMsg("未传入企业ID");
            message.setSuccess(false);
            return;
        }

        map.put("id", enterpriseId );
        map.put("isNotLimit", "1");
        List<Enterprise> list = enterpriseMapper.selectEnterprise(map);
        if(list != null && list.size()>0){
            Enterprise enterprise = list.get(0);
            long clickNumber = (StringUtils.isNotBlank(enterprise.getClickNumber()))?Long.parseLong(enterprise.getClickNumber()):0;
            clickNumber++;
            enterprise.setClickNumber(String.valueOf(clickNumber));
            /*enterprise.preUpdate();*/
            enterpriseMapper.update(enterprise);
            map.put("id", "");
            map.put("enterpriseId", enterpriseId);
            List<EnterpriseProduct> productList = enterpriseProductMapper.findEnterPriseProductList(map);
            enterprise.setProductList(productList);
            message.init(true, "查询成功", enterprise);
        }else{
            message.init(false, "企业不存在", null);
        }
    }


    public void findEnterpriseListAndOrderByDistenceDesc(Message message, String content){

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //获取参数中的经度
        String startLongitude =(String)map.get("longitude");
        //获取参数中的纬度
        String startLatitude =(String)map.get("latitude");

        //获取参数中的经度
        double startLongitude1 =Double.parseDouble(startLongitude);
        //获取参数中的纬度
        double startLatitude2 =Double.parseDouble(startLatitude);

        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        }else{
            map.put("isNotLimit", "1");
        }
        List<Enterprise> list = enterpriseMapper.selectEnterprise(map);
        List<Enterprise> list1 = new ArrayList<>();
        for(int i = 0; i < list.size(); i++ ){
            double endLongitude =Double.parseDouble(list.get(i).getLongitude()) ;
            double endLatitude = Double.parseDouble(list.get(i).getLatitude());
            PairUtil<Double,Double> starPoint = new PairUtil<Double,Double>(startLongitude1,startLatitude2);
            PairUtil<Double,Double> endPoint = new PairUtil<Double,Double>(endLongitude,endLatitude);
            double distance = getDistance(starPoint,endPoint);
            list.get(i).setDistance(distance);
            list1.add(list.get(i));
        }
        List<Enterprise> list2 = new ArrayList<>();
        for(int i = 0; i < list1.size(); i++){
                for (int j = i; j < list1.size(); j++)
                {
                    Enterprise e1 = list1.get(i);
                    Enterprise e2 = list1.get(j);
                    if (e1.getDistance() > e2.getDistance())
                    {
                        Enterprise temp = e1;
                        list1.set(i,e2);
                        list1.set(j,temp);
                    }
                }
        }
        long total = enterpriseMapper.count(map);
        DataGrid<Enterprise> dataGrid = new DataGrid<>();
        dataGrid.setTotal(total);
        dataGrid.setRows(list1);
        message.init(true, "查询成功", dataGrid);
    }


}
