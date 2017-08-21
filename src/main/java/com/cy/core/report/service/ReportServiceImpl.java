package com.cy.core.report.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;

import com.cy.common.utils.EditorUtils;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.report.dao.ReportMapper;
import com.cy.core.report.entity.Report;
import com.cy.core.share.dao.FileMapper;
import com.cy.core.share.entity.File;
import com.cy.system.Global;
import com.cy.util.IdGen;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private FileMapper fileMapper;

    public void report(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            //获取参数中举报人的id
            String userId = map.get("userId");
            if (StringUtils.isBlank(userId)) {
                message.setMsg("用户编号不能为空");
                message.setSuccess(false);
                return;
            }
            //获取参数中业务的id 包括活动的Id或者花絮的ID
            String bussId = map.get("bussId");
            if (StringUtils.isBlank(bussId)) {
                message.setMsg("业务编号不能为空");
                message.setSuccess(false);
                return;
            }

            //获取参数中举报的内容
            String content1 = map.get("content");
            if (StringUtils.isBlank(content1)) {
                message.setMsg("举报内容不能为空");
                message.setSuccess(false);
                return;
            }

            //获取参数中据据举报的类型 10活动；15：花絮；
            String bussType = map.get("bussType");
            if (StringUtils.isBlank(bussType)) {
                message.setMsg("举报类型不能为空");
                message.setSuccess(false);
                return;
            }
            Report report =  new Report();
            String reportId=IdGen.uuid();
            report.setId(reportId);
            report.setUserId(userId);
            report.setCreateBy(userId);
            report.setBussId(bussId);
            report.setBussType(bussType);
            report.setContent(content1);
            reportMapper.savereportActivityOrHuaxu(report);
            String reportPics = map.get("reportPics");
            if (StringUtils.isNotBlank(reportPics)) {
                String[] urls = reportPics.split(",");
                for (String url : urls) {
                    File file = new File();
                    file.setId(IdGen.uuid());
                    file.setCreateBy(userId);
                    file.setBussType("50");
                    file.setPictureUrl(url);
                    file.setFileGroup(reportId);
                    Date createDate = new Date();
                    file.setCreateDate(createDate);
                    file.setUpdateDate(createDate);
                    fileMapper.insert(file);
                }
            }
            message.setMsg("已提交举报申请");
            message.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
   public  void viewReport(Message message, String content){

       Map<String, String> map = JSON.parseObject(content,Map.class);
        //某条举报信息的id
       String reportId = map.get("reportId");
       //获取参数中举报人的id
       String userId = map.get("userId");
      //取参数中业务的id 包括活动的Id或者花絮的ID
       String bussId = map.get("bussId");
       //获取参数中据据举报的类型 10活动；15：花絮；
       String bussType = map.get("bussType");
       if (StringUtils.isNotBlank(bussId) && StringUtils.isBlank(bussType)  ) {
           message.setMsg("举报业务编号不为空时，举报类型也不能为空");
           message.setSuccess(false);
           return;
       }
       Map<String, String> map1 = new HashMap<String, String>();
       map1.put("reportId",reportId);
       map1.put("userId",userId);
       map1.put("bussId",bussId);
       map1.put("bussType",bussType);
       List<Report> reportList = reportMapper.selectReportList(map1);
       message.setMsg("查询成功!");
       message.setObj(reportList);
       message.setSuccess(true);
       return;
    }




    public DataGrid<Report> dataGridReport(Map<String, Object> map){
        long count = reportMapper.count(map);
        DataGrid<Report> dataGrid = new DataGrid<>();
        dataGrid.setTotal(count);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Report> list = reportMapper.findReportList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public Report getById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("isNoLimit", "1");
        List<Report> list = reportMapper.findReportList(map);
        Report report = new Report();
        if(list != null && list.size()>0){
            report = list.get(0);
            String handleStatus=report.getHandleStatus();
            String fileGroup = report.getBussId();//获取业务的编号，例如活动或者花絮的编号
            String bussType = report.getBussType();//获取业务的类型，例如 10 ：活动 15：花絮
            map.put("fileGroup", fileGroup);
            map.put("bussType", bussType);
            Map<String, Object> map1 = new HashMap<>();
            List<File> fileList = fileMapper.selectList(map1);
            String str ="";
            if(fileList != null ){
                for(File file:fileList){
                    String pictureUrl = file.getPictureUrl();
                    str += pictureUrl + ",";
                }
                str = str.substring(0,str.length()-1);
            }
           report.setPictureUrls(str);
            if(handleStatus != null && handleStatus.equals("20")){
                report.setIsTrue("1");
            }
            if(handleStatus != null && handleStatus.equals("30")){
                report.setIsTrue("0");
            }
        }
        return report;
    }
    public int updateReport(Report report){
        report.preUpdate();
        reportMapper.update(report);
        return 0;
    }


}
