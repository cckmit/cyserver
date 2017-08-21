package com.cy.core.enterprise.service;

/**
 * Created by cha0res on 1/6/17.
 */

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.enterprise.dao.EnterpriseMapper;
import com.cy.core.enterprise.dao.EnterpriseProductMapper;
import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.share.dao.FileMapper;
import com.cy.core.share.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("enterpriseProductService")
public class EnterpriseProductServiceImpl implements EnterpriseProductService {

    @Autowired
    private EnterpriseProductMapper enterpriseProductMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private FileMapper fileMapper;

    public DataGrid<EnterpriseProduct> dataGridProduct(Map<String, Object> map){
        long count = enterpriseProductMapper.count(map);
        DataGrid<EnterpriseProduct> dataGrid = new DataGrid<>();
        dataGrid.setTotal(count);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<EnterpriseProduct> list = enterpriseProductMapper.findEnterPriseProductList(map);
        if (list != null && list.size() > 0) {
            for (EnterpriseProduct enterpriseProduct : list) {
                //关联分享图片
                if (enterpriseProduct != null && StringUtils.isNotBlank(enterpriseProduct.getId())) {
                    Map<String, Object> tmp = new HashMap<String, Object>();
                    tmp.put("fileGroup", enterpriseProduct.getId());
                    tmp.put("bussType","50");
                    List<File> fileList = fileMapper.selectList(tmp);
                    enterpriseProduct.setFileList(fileList);
                }

            }
        }
        dataGrid.setRows(list);
        return dataGrid;
    }

    public EnterpriseProduct getById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("isNotLimit", "1");
        List<EnterpriseProduct> list = enterpriseProductMapper.findEnterPriseProductList(map);
        EnterpriseProduct enterpriseProduct = new EnterpriseProduct();

        if(enterpriseProduct != null && org.apache.commons.lang3.StringUtils.isNotBlank(enterpriseProduct.getDescription())) {
            enterpriseProduct.setDescription(EditorUtils.changeSrcFromRelativeToAbsolute(enterpriseProduct.getDescription()));
        }
        if(list != null && list.size()>0){
            enterpriseProduct = list.get(0);
        }
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("fileGroup", enterpriseProduct.getId());
        map2.put("bussType","50");
        List<File> fileList = fileMapper.selectList(map2);
        enterpriseProduct.setFileList(fileList);
        return enterpriseProduct;
    }

    public int saveProduct(EnterpriseProduct enterpriseProduct){
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId",enterpriseProduct.getEnterpriseId());
        long count = enterpriseProductMapper.count(map);
        if(count > 5){
            return 1;
        }
        enterpriseProduct.preInsert();
        enterpriseProductMapper.save(enterpriseProduct);
        if (StringUtils.isNotBlank(enterpriseProduct.getPosterPicUrl())) {
            //删除该产品关联的图片
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("fileGroup",enterpriseProduct.getId());
            map1.put("bussType","50");
            fileMapper.delete(map1);
            //保存该产品所关联的图片
            String[] urls = enterpriseProduct.getPosterPicUrl().split(",");
            for (String url : urls) {
                File file = new File();
                file.setFileGroup(enterpriseProduct.getId());
                file.setBussType("50");
                file.setPicUrl(url);
                file.preInsert();
                fileMapper.insert(file);
            }
        }
        return 0;
    }

    public int updateProduct(EnterpriseProduct enterpriseProduct){
        enterpriseProduct.preUpdate();
        enterpriseProductMapper.update(enterpriseProduct);
        if (StringUtils.isNotBlank(enterpriseProduct.getPosterPicUrl())) {
            //删除该产品关联的图片
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("fileGroup",enterpriseProduct.getId());
            map1.put("bussType","50");
            fileMapper.delete(map1);
            //修改改产品所关联的图片
            String[] urls = enterpriseProduct.getPosterPicUrl().split(",");
            for (String url : urls) {
                File file = new File();
                file.setFileGroup(enterpriseProduct.getId());
                file.setBussType("50");
                file.setPicUrl(url);
                file.preInsert();
                fileMapper.insert(file);
            }
        }
        return 0;
    }

    public int delete(String ids){
        String[] array = ids.split(",");
        for(String id : array){
            EnterpriseProduct enterpriseProduct = getById(id);
            enterpriseProduct.preUpdate();
            enterpriseProduct.setDelFlag("1");
            enterpriseProductMapper.update(enterpriseProduct);
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
     * 查询校友企业产品列表
     * @param message
     * @param content
     */
    public void findEnterpriseProductList(Message message, String content){
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
        List<EnterpriseProduct> productList = enterpriseProductMapper.findEnterPriseProductList(map);
        long total = enterpriseProductMapper.count(map);
        DataGrid<EnterpriseProduct> dataGrid = new DataGrid<>();
        dataGrid.setTotal(total);
        dataGrid.setRows(productList);
        message.init(true, "查询成功", dataGrid);
    }

    /**
     * 查询产品详情
     * @param message
     * @param content
     */
    public void findEnterpriseProduct(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String productId = (String)map.get("productId");
        String accountNum = (String)map.get("accountNum");
        if(StringUtils.isBlank(productId)){
            message.setMsg("未传入产品ID");
            message.setSuccess(false);
            return;
        }
        map.put("id", productId);
        map.put("isNotLimit", "1");
        List<EnterpriseProduct> list = enterpriseProductMapper.findEnterPriseProductList(map);
        if(list == null || list.size()<=0){
            message.init(false,"未知的产品ID", null);
        }else{
            EnterpriseProduct enterpriseProduct = list.get(0);
            long clickNumber = (StringUtils.isNotBlank(enterpriseProduct.getClickNumber()))?Long.parseLong(enterpriseProduct.getClickNumber()):0;
            clickNumber++;
            enterpriseProduct.setClickNumber(String.valueOf(clickNumber));
            /*enterpriseProduct.preUpdate();*/
            enterpriseProductMapper.update(enterpriseProduct);

            if (enterpriseProduct !=null && enterpriseProduct.getFileList()!=null && enterpriseProduct.getFileList().size()>0){
                List<File> fileList = new ArrayList<>();
                for (File file :enterpriseProduct.getFileList()){
                    File f = new File();
                    if(file != null && StringUtils.isNotBlank(file.getPicUrl())){
                        f.setPicUrl(file.getPicUrl());
                        fileList.add(f);
                    }
                }
                enterpriseProduct.setFileList(fileList);
            }
            message.init(true, "查询成功", enterpriseProduct);
        }
    }
}
