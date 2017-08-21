package com.cy.core.cloudEnterprise.service;


import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.cy.base.entity.DataGrid;


import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.JsonUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.dao.*;
import com.cy.core.cloudEnterprise.entity.*;


import com.cy.core.enterprise.dao.EnterpriseMatchMapper;
import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseMatch;
import com.cy.core.enterprise.entity.EnterpriseTeam;
import com.cy.system.Global;
import com.cy.util.CloudPlatformUtils;
import com.cy.util.Collections3;
import com.cy.util.DateUtils;
import com.google.common.collect.Maps;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 企业信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2016-1-6
 */

@Service("cloudEnterpriseService")
public class CloudEnterpriseServiceImpl implements CloudEnterpriseService{

    @Autowired
    private CloudEnterpriseMapper cloudEnterpriseMapper;
   @Autowired
    private CloudEnterpriseProductMapper cloudEnterpriseProductMapper;

    @Resource
    private EnterpriseMatchMapper enterpriseMatchMapper;

    @Resource
    private CloudEnterpriseTeamMapper cloudEnterpriseTeamMapper;

    @Resource
    private CloudEnterprisePositionMapper cloudEnterprisePositionMapper;

    @Resource
    private CloudEntrepreneurMapper cloudEntrepreneurMapper;


    @Override
    public List<CloudEnterprise> findList(Map<String, Object> map) {
        List<CloudEnterprise> list = cloudEnterpriseMapper.selectEnterprise(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return cloudEnterpriseMapper.count(map);
    }

    @Override
    public DataGrid<CloudEnterprise> dataGrid(Map<String, Object> map) {
        DataGrid<CloudEnterprise> dataGrid = new DataGrid<CloudEnterprise>();
        long total = cloudEnterpriseMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<CloudEnterprise> list = cloudEnterpriseMapper.selectEnterprise(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public List<CloudEnterprise> enterprisesList(){
        Map<String, Object> map = new HashMap<>();
        map.put("isNotLimit", "1");
        List<CloudEnterprise> list = cloudEnterpriseMapper.selectEnterprise(map);
        return list;
    }

    @Override
    public CloudEnterprise save(CloudEnterprise cloudEnterprise) {
        cloudEnterprise.preInsert();
        cloudEnterpriseMapper.insert(cloudEnterprise);
        return cloudEnterprise;
    }
    @Override
    public CloudEnterprise update(CloudEnterprise cloudEnterprise) {
        cloudEnterprise.preUpdate();
        cloudEnterpriseMapper.update(cloudEnterprise);
        return cloudEnterprise;
    }

    public Integer updateByCloudId(CloudEnterprise cloudEnterprise){
        cloudEnterprise.preUpdate();
        return cloudEnterpriseMapper.updateByCloudId(cloudEnterprise);
    }

    @Override
    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        cloudEnterpriseMapper.delete(list);
    }

    public CloudEnterprise getByCloudId(String id) {
        CloudEnterprise cloudEnterprise = cloudEnterpriseMapper.getByCloudId(id) ;
        return cloudEnterprise;
    }

    /**
     * 推送企业审核结果
     * @param enterpriseId
     */
    public void auditEnterprise(String enterpriseId){

        CloudEnterprise enterprise = getByCloudId(enterpriseId);
        if (enterprise!=null && StringUtils.isNotBlank(enterprise.getCloudId())){
            //推送之前将推送结果为未推送
            Map<String,Object> param = Maps.newHashMap();
            param.put("bussId",enterprise.getCloudId());
            param.put("bussType",EnterpriseMatch.TYPE_ENTERPRISE);
            List<EnterpriseMatch> enterpriseMatchList =enterpriseMatchMapper.findList(param);
            EnterpriseMatch enterpriseMatch = new EnterpriseMatch();
            if (enterpriseMatchList!=null && !enterpriseMatchList.isEmpty()){
                enterpriseMatch = enterpriseMatchList.get(0);
                enterpriseMatch.setPushResult("0");
                enterpriseMatchMapper.update(enterpriseMatch);
            }else {
                enterpriseMatch.setBussId(enterprise.getCloudId());
                enterpriseMatch.setBussType(EnterpriseMatch.TYPE_ENTERPRISE);
                enterpriseMatch.setLastUpdateDate(enterprise.getUpdateDate());
                enterpriseMatch.setPushResult("0");
                enterpriseMatch.preInsert();
                enterpriseMatchMapper.insert(enterpriseMatch);
            }

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("enterpriseId",enterprise.getCloudId()));
            nameValuePairs.add(new BasicNameValuePair("schoolCode",Global.deptNo));
            nameValuePairs.add(new BasicNameValuePair("status",enterprise.getStatus()));
            nameValuePairs.add(new BasicNameValuePair("auditTime",DateUtils.formatDateTime(enterprise.getUpdateDate())));
            Map<String,Object> map = CloudPlatformUtils.post(Global.cloud_server_url+"/school_audit_enterprise/certification",nameValuePairs,0);
            try {
                if (map!=null && !map.isEmpty() && map.get("code")!=null && (Integer)map.get("code") ==201){
                    enterpriseMatch.setPushResult("0");
                    enterpriseMatchMapper.update(enterpriseMatch);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步校友团队申请到学校
     */
    public void syncTeamToSchoolByQuartz(){
        Map<String,Object> dataMap = CloudPlatformUtils.get(Global.cloud_server_url + "/enterprise_team/syncTeamToSchool/"+Global.deptNo,0);
       try {
            if (dataMap!=null && !dataMap.isEmpty() && dataMap.get("code")!=null && ((Integer)dataMap.get("code")) ==200){
                Map<String,Object> map = (Map<String, Object>) dataMap.get("data");

                List<Map<String,Object>> enterpriseList = (List<Map<String, Object>>) map.get("enterprises");
                if (enterpriseList!=null&& !enterpriseList.isEmpty()){
                    enterpriseMatch(enterpriseList);
                }
                List<Map<String,Object>> entrepreneursList = (List<Map<String, Object>>) map.get("entrepreneurs");
                if (entrepreneursList!=null && !entrepreneursList.isEmpty()){
                    entrepreneurMatch(entrepreneursList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void entrepreneurMatch(List<Map<String,Object>> cloudEntrepreneurList){

        //获取本地已同步过来的校友申请
        Map<String,Object> param = Maps.newHashMap();
        param.put("status","10");
        param.put("isNotLimit","1");
        List<CloudEntrepreneur> entrepreneurList = cloudEntrepreneurMapper.findList(param);

        String teamIds ="";
        if (entrepreneurList !=null && !entrepreneurList.isEmpty()){
            teamIds = Collections3.extractToString(entrepreneurList,"teamId",",");
        }
        List<CloudEntrepreneur> tempList = entrepreneurList;

        if (cloudEntrepreneurList!=null && !cloudEntrepreneurList.isEmpty()) {
            for (Map<String, Object> map : cloudEntrepreneurList) {
                String bussId = (String) map.get("buss_id");
                String updateTime = (String) map.get("update_time");
                if (StringUtils.isNotBlank(bussId)) {
                    if (entrepreneurList != null && !entrepreneurList.isEmpty() && teamIds.indexOf(bussId) > -1) {
                        for (CloudEntrepreneur entrepreneur : entrepreneurList) {
                            if (bussId.equals(entrepreneur.getTeamId())) {
                                //更新信息
                                if (StringUtils.isNotBlank(updateTime)&& DateUtils.parseDate(updateTime).after(entrepreneur.getUpdateDate())) {
                                    updateEntrepreneur(map,entrepreneur.getId());
                                }
                               tempList.remove(entrepreneur);
                                break;
                            }
                        }
                    } else {
                        updateEntrepreneur(map,null);
                    }
                }
            }
        }
    }

    /**
     * 更新企业家
     * @param map
     * @param id
     */
    public void updateEntrepreneur(Map<String,Object> map,String id){
        CloudEntrepreneur cloudEntrepreneur = new CloudEntrepreneur();
        cloudEntrepreneur.setType(map.get("type")!=null?(String) map.get("type"):null);
        cloudEntrepreneur.setTeamId(map.get("buss_id")!=null?(String) map.get("buss_id"):null);
        cloudEntrepreneur.setTelephone(map.get("tel")!=null?(String)map.get("tel"):null);
        cloudEntrepreneur.setEnterpriseId(map.get("enterprise_id")!=null?(String) map.get("enterprise_id"):null);
        cloudEntrepreneur.setClbum(map.get("clbum")!=null?(String) map.get("clbum"):null);
        cloudEntrepreneur.setCollege(map.get("college")!=null?(String) map.get("college"):null);
        cloudEntrepreneur.setGrade(map.get("grade")!=null?(String) map.get("grade"):null);
        cloudEntrepreneur.setStatus(map.get("status")!=null?(String)map.get("status"):null);
        cloudEntrepreneur.setSyncStatus(map.get("sync_status")!=null?(String)map.get("sync_status"):null);
        cloudEntrepreneur.setSysName(map.get("sys_name")!=null?(String)map.get("sys_name"):null);
        cloudEntrepreneur.setSysPhone(map.get("sys_phone")!=null?(String)map.get("sys_phone"):null);
        Integer integer = -1;
        if (StringUtils.isNotBlank(id)){
            cloudEntrepreneur.setId(id);
            cloudEntrepreneur.preUpdate();
            integer= cloudEntrepreneurMapper.update(cloudEntrepreneur);
        }else {
            cloudEntrepreneur.preInsert();
            integer= cloudEntrepreneurMapper.insert(cloudEntrepreneur);
        }

        if (integer>0){
            //反馈给云平台获取企业家结果
            updateCloudResult(map.get("id")!=null?(String)map.get("id"):null);
        }
    }

    /**
     * 获取云平台企业团队信息
     * @param schoolId
     * @param enterpriseId
     */
    public void findCloudTeam(String schoolId,String enterpriseId){
        if (StringUtils.isNotBlank(schoolId)&& StringUtils.isNotBlank(enterpriseId)){
            Map<String,Object> dataMap = CloudPlatformUtils.get(Global.cloud_server_url + "/enterprise_team/findSchoolTeam?schoolId="+schoolId+"&enterpriseId="+enterpriseId,0);
            try {
                if (dataMap !=null && !dataMap.isEmpty() && dataMap.get("code")!=null && ((Integer)dataMap.get("code")) ==200){
                    List<Map<String,Object>> cloudTeamList = (List<Map<String, Object>>) dataMap.get("data");
                   teamMatch(cloudTeamList,enterpriseId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String updateCloudResult(String id){
        if (StringUtils.isNotBlank(id)){
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("syncStatus","20"));
            Map<String,Object> dataMap = CloudPlatformUtils.post(Global.cloud_server_url+"/enterprise_school/saveSync",nameValuePairs,0);
            try {
                if (dataMap !=null && !dataMap.isEmpty() && dataMap.get("code")!=null && ((Integer)dataMap.get("code")) ==200){
                    return "1";
                }else {
                    return "0";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "0";
            }
        }else {
            return "0";
        }
    }

    /**
     * 定时推送企业认证推送失败记录
     */
    public void pushAuditFailByQuartz(){
        //判断学校ID是否存在，否重新获取
        if (StringUtils.isBlank((String) CacheUtils.get("cloudSchoolId"))){
            CloudPlatformUtils.getSchoolByToken();
        }
        //再次确认学校ID是否存在
        if (StringUtils.isNotBlank((String)CacheUtils.get("cloudSchoolId"))) {
            //获取认证推送失败列表
            Map<String, Object> param = Maps.newHashMap();
            param.put("bussType", EnterpriseMatch.TYPE_ENTERPRISE);
            param.put("pushResult", "0");
            List<EnterpriseMatch> enterpriseMatchList = enterpriseMatchMapper.findList(param);
            if (enterpriseMatchList != null && !enterpriseMatchList.isEmpty()) {
                for (EnterpriseMatch enterpriseMatch : enterpriseMatchList) {
                    //重新推送
                    List<NameValuePair> nameValuePairs = new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("enterpriseId", enterpriseMatch.getBussId()));
                    nameValuePairs.add(new BasicNameValuePair("schoolId", (String) CacheUtils.get("cloudSchoolId")));
                    nameValuePairs.add(new BasicNameValuePair("status", "20"));
                    nameValuePairs.add(new BasicNameValuePair("auditTime", DateUtils.getDateTime()));
                    Map<String, Object> map = CloudPlatformUtils.post(Global.cloud_server_url + "/school_audit_enterprise/certification", nameValuePairs,0);
                    try {
                        if (map != null && !map.isEmpty() && map.get("code") != null && (Integer) map.get("code") == 201) {
                            enterpriseMatch.setPushResult("1");
                            enterpriseMatchMapper.update(enterpriseMatch);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * 定时更新云平台企业认证申请
     */
    public void findApplyEnterpriseByQuartz(){
        //判断学校ID是否存在，否重新获取
        if (StringUtils.isBlank((String) CacheUtils.get("cloudSchoolId"))){
            CloudPlatformUtils.getSchoolByToken();
        }
        //再次确认学校ID是否存在
        if (StringUtils.isNotBlank((String)CacheUtils.get("cloudSchoolId"))){
            //获取云平台申请认证企业
            Map<String,Object> map = CloudPlatformUtils.get(Global.cloud_server_url+"/enterprise/enterpriseData/"+(String)CacheUtils.get("cloudSchoolId")+"/10",0);
            try {
                if (map !=null && !map.isEmpty() && map.get("code")!=null && ((Integer)map.get("code")) ==200){
                    List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get("data");
                    if (!mapList.isEmpty()){
                        //获取本地已同步过的企业
                        Map<String,Object> param = Maps.newHashMap();
                        param.put("status","10");
                        param.put("isNotLimit","1");
                        List<CloudEnterprise> cloudEnterpriseList = cloudEnterpriseMapper.selectEnterprise(param);
                        //获取已同步过来的企业编号集合，用于最快比较云平台企业是否存在本地
                        String cloudIds ="";
                        if (cloudEnterpriseList!=null && !cloudEnterpriseList.isEmpty()) {
                            cloudIds = Collections3.extractToString(cloudEnterpriseList, "cloudId", ",");
                        }
                        List<CloudEnterprise> temp = cloudEnterpriseList;
                        for (Map<String,Object> map1 :mapList){
                            String enterpriseId = (String)map1.get("id");
                            String updateTime = (String) map1.get("update_time");
                            CloudEnterprise enterprise = new CloudEnterprise();
                            if (StringUtils.isNotBlank(enterpriseId)){
                                if (cloudEnterpriseList!=null && !cloudEnterpriseList.isEmpty() && cloudIds.indexOf(enterpriseId)>-1){
                                    for (CloudEnterprise cloudEnterprise:cloudEnterpriseList){
                                        if (enterpriseId.equals(cloudEnterprise.getCloudId())){
                                            Map<String, Object> paramTeam = Maps.newHashMap();
                                            paramTeam.put("bussId", enterpriseId);
                                            paramTeam.put("bussType", EnterpriseMatch.TYPE_ENTERPRISE);
                                            List<EnterpriseMatch> enterpriseMatchList = enterpriseMatchMapper.findList(paramTeam);
                                            if (enterpriseMatchList != null && !enterpriseMatchList.isEmpty()) {
                                                EnterpriseMatch enterpriseMatch = enterpriseMatchList.get(0);
                                                if (enterpriseMatch != null && DateUtils.parseDate(updateTime).after(enterpriseMatch.getLastUpdateDate())) {
                                                    //更新企业
                                                    enterprise = updateEnterprise(map1, enterpriseMatch.getId(), cloudEnterprise.getId(),"10");
                                                }
                                            }
                                            temp.remove(cloudEnterprise);
                                            break;
                                        }
                                    }
                                }else{
                                    //新增企业信息
                                    enterprise = updateEnterprise(map1,null,null,"10");
                                }
                                //校友-团队成员
                                List<Map<String,Object>> teamList = (List<Map<String, Object>>) map.get("team_list");
                                if (teamList !=null && !teamList.isEmpty()){
                                    //匹配团队成员
                                    teamMatch(teamList,enterpriseId);
                                }
                            }
                        }
                        //本地多余的刪除
                        if (temp !=null&& !temp.isEmpty()){
                            List<String> list = new ArrayList<>();
                            for (CloudEnterprise cloudEnterprise :temp){
                                Map<String,Object> paramMap = Maps.newHashMap();
                                paramMap.put("bussId",cloudEnterprise.getCloudId());
                                paramMap.put("bussType",EnterpriseMatch.TYPE_ENTERPRISE);
                                enterpriseMatchMapper.deleteByBussIdAndType(paramMap);
                                list.add(cloudEnterprise.getId());
                            }
                            cloudEnterpriseMapper.delete(list);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 定时更新已认证企业信息
     */
    public void findAuditEnterpriseByQuartz(){

        //判断学校ID是否存在，否重新获取
        if (StringUtils.isBlank((String)CacheUtils.get("cloudSchoolId"))){
            CloudPlatformUtils.getSchoolByToken();
        }
        //再次确认学校ID是否存在
        if (StringUtils.isNotBlank((String)CacheUtils.get("cloudSchoolId"))){
            //获取云平台企业申请信息
//            String data = CloudPlatformUtils.get(Global.cloud_server_url+"/school_audit_enterprise?del_flag=0&schoolId=0183dd339ec8406a8632967d5d300f47");
            Map<String,Object> map = CloudPlatformUtils.get(Global.cloud_server_url+"/enterprise/enterpriseData/"+CacheUtils.get("cloudSchoolId")+"/20",0);
            try {
                if (map!=null && !map.isEmpty() && map.get("code")!=null && (Integer)map.get("code") ==200){
                    List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get("data");
                    if (!mapList.isEmpty()){
                        //获取本地已同步过的企业
                        Map<String,Object> param = Maps.newHashMap();
                        param.put("status","20");
                        param.put("isNotLimit","1");
                        List<CloudEnterprise> cloudEnterpriseList = cloudEnterpriseMapper.selectEnterprise(param);
                       //获取已同步过来的企业编号集合，用于最快比较云平台企业是否存在本地
                        String cloudIds ="";
                        if (cloudEnterpriseList!=null && !cloudEnterpriseList.isEmpty()) {
                            cloudIds = Collections3.extractToString(cloudEnterpriseList, "cloudId", ",");
                        }
                        //本地同步过来的企业集合，用于判断云平台是否有删除本地已存在的企业
                        List<CloudEnterprise> temp = cloudEnterpriseList;
                        for (Map<String,Object> map1 :mapList){
                            CloudEnterprise enterprise = new CloudEnterprise();
                            String enterpriseId = (String)map1.get("id");
                            String updateTime = (String) map1.get("update_time");
                            if (StringUtils.isNotBlank(enterpriseId)){
                                if (cloudEnterpriseList!=null && !cloudEnterpriseList.isEmpty() && cloudIds.indexOf(enterpriseId)>-1){
                                    for (CloudEnterprise cloudEnterprise:cloudEnterpriseList){
                                        if (enterpriseId.equals(cloudEnterprise.getCloudId())){

                                            Map<String, Object> paramTeam = Maps.newHashMap();
                                            paramTeam.put("bussId", enterpriseId);
                                            paramTeam.put("bussType", EnterpriseMatch.TYPE_ENTERPRISE);
                                            List<EnterpriseMatch> enterpriseMatchList = enterpriseMatchMapper.findList(paramTeam);
                                            if (enterpriseMatchList != null && !enterpriseMatchList.isEmpty()) {
                                                EnterpriseMatch enterpriseMatch = enterpriseMatchList.get(0);
                                                if (enterpriseMatch != null && DateUtils.parseDate(updateTime).after(enterpriseMatch.getLastUpdateDate())) {
                                                    //更新企业
                                                   enterprise = updateEnterprise(map1, enterpriseMatch.getId(), cloudEnterprise.getId(),"20");
                                                }
                                            }
                                            temp.remove(cloudEnterprise);
                                            break;
                                        }
                                    }
                                }else{
                                    //新增企业信息
                                    enterprise = updateEnterprise(map1,null,null,"20");
                                }
                                //团队成员-校友
                                List<Map<String,Object>> teamList = (List<Map<String, Object>>) map1.get("team_list");
                                if (teamList !=null && !teamList.isEmpty()){
                                    //匹配团队成员
//                                    teamMatch(teamList,enterprise);
                                }
                                //企业产品
                                List<Map<String,Object>> productList = (List<Map<String, Object>>) map1.get("product_list");
                                if (productList!=null && !productList.isEmpty()){
                                    //匹配产品
                                    productMatch(productList,enterprise);
                                }
                                //企业职位招聘
                                List<Map<String,Object>> positionList = (List<Map<String, Object>>) map1.get("position_list");
                                if (positionList!=null && !positionList.isEmpty()){
                                    positionMatch(positionList,enterprise);
                                }
                            }
                        }
                        //本地多余的刪除
                        if (temp !=null&& !temp.isEmpty()){
                            List<String> list = new ArrayList<>();
                            for (CloudEnterprise cloudEnterprise :temp){
                                Map<String,Object> paramMap = Maps.newHashMap();
                                paramMap.put("bussId",cloudEnterprise.getCloudId());
                                paramMap.put("status","20");
                                enterpriseMatchMapper.deleteByBussIdAndType(paramMap);
                                list.add(cloudEnterprise.getId());
                            }
                            cloudEnterpriseMapper.delete(list);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void enterpriseMatch(List<Map<String,Object>> cloudEnterpriseMap){
        //获取本地已同步过的企业
        Map<String,Object> param = Maps.newHashMap();
        param.put("isNotLimit","1");
        List<CloudEnterprise> cloudEnterpriseList = cloudEnterpriseMapper.selectEnterprise(param);
        //获取已同步过来的企业编号集合，用于最快比较云平台企业是否存在本地
        String cloudIds ="";
        if (cloudEnterpriseList!=null && !cloudEnterpriseList.isEmpty()) {
            cloudIds = Collections3.extractToString(cloudEnterpriseList, "cloudId", ",");
        }
        List<CloudEnterprise> temp = cloudEnterpriseList;
        for (Map<String,Object> map :cloudEnterpriseMap){
            String enterpriseId = (String)map.get("id");
            String updateTime = (String) map.get("update_time");
            CloudEnterprise enterprise = new CloudEnterprise();
            if (StringUtils.isNotBlank(enterpriseId)){
                if (cloudEnterpriseList!=null && !cloudEnterpriseList.isEmpty() && cloudIds.indexOf(enterpriseId)>-1){
                    for (CloudEnterprise cloudEnterprise:cloudEnterpriseList){
                        if (enterpriseId.equals(cloudEnterprise.getCloudId())){
                            Map<String, Object> paramTeam = Maps.newHashMap();
                            paramTeam.put("bussId", enterpriseId);
                            paramTeam.put("bussType", EnterpriseMatch.TYPE_ENTERPRISE);
                            List<EnterpriseMatch> enterpriseMatchList = enterpriseMatchMapper.findList(paramTeam);
                            if (enterpriseMatchList != null && !enterpriseMatchList.isEmpty()) {
                                EnterpriseMatch enterpriseMatch = enterpriseMatchList.get(0);
                                if (enterpriseMatch != null && DateUtils.parseDate(updateTime).after(enterpriseMatch.getLastUpdateDate())) {
                                    //更新企业
                                    enterprise = updateEnterprise(map, enterpriseMatch.getId(), cloudEnterprise.getId(),"10");
                                }
                            }
                            temp.remove(cloudEnterprise);
                            break;
                        }
                    }
                }else{
                    //新增企业信息
                    enterprise = updateEnterprise(map,null,null,"10");
                }
                //校友-团队成员
                List<Map<String,Object>> teamList = (List<Map<String, Object>>) map.get("team_list");
                if (teamList !=null && !teamList.isEmpty()){
                    //匹配团队成员
                    teamMatch(teamList,enterpriseId);
                }
            }
        }
        //本地多余的刪除
        if (temp !=null&& !temp.isEmpty()){
            List<String> list = new ArrayList<>();
            for (CloudEnterprise cloudEnterprise :temp){
                Map<String,Object> paramMap = Maps.newHashMap();
                paramMap.put("bussId",cloudEnterprise.getCloudId());
                paramMap.put("bussType",EnterpriseMatch.TYPE_ENTERPRISE);
                enterpriseMatchMapper.deleteByBussIdAndType(paramMap);
                list.add(cloudEnterprise.getId());
            }
            cloudEnterpriseMapper.delete(list);
        }
    }

    /**
     * 更新本地企业信息
     * @param cloudEnterprise   云平台企业信息
     * @param enterpriseMatchId 匹配记录ID
     * @param enterpriseId  本地企业编号
     * @param status  企业状态
     * @return
     */
    public CloudEnterprise updateEnterprise(Map<String,Object> cloudEnterprise,String enterpriseMatchId,String enterpriseId,String status){

        CloudEnterprise enterprise = new CloudEnterprise();
        enterprise.setAddress((String)cloudEnterprise.get("address"));
        enterprise.setCity((String)cloudEnterprise.get("city_name"));
        enterprise.setProvince((String)cloudEnterprise.get("province_name"));
        enterprise.setContactNumber((String)cloudEnterprise.get("contact_number"));
        enterprise.setIndustry((String)cloudEnterprise.get("industry_name"));
        enterprise.setLatitude((String)cloudEnterprise.get("latitude"));
        enterprise.setLinkman((String)cloudEnterprise.get("linkman"));
        enterprise.setLogo(cloudEnterprise.get("logo")!=null?Global.cloud_file_url+(String)cloudEnterprise.get("logo"):null);
        enterprise.setLongitude((String)cloudEnterprise.get("longitude"));
        enterprise.setMainBusiness((String)cloudEnterprise.get("main_business"));
        enterprise.setName((String)cloudEnterprise.get("name"));
        enterprise.setRecruitEmail((String)cloudEnterprise.get("recruit_email"));

        enterprise.setWebsite((String)cloudEnterprise.get("website"));
        enterprise.setSlogan((String)cloudEnterprise.get("slogan"));
        enterprise.setSummary((String)cloudEnterprise.get("summary"));
        enterprise.setFinancingStage((String)cloudEnterprise.get("financing_stage"));
        enterprise.setLocationDesc((String)cloudEnterprise.get("location_desc"));
        enterprise.setPosterPic((String)cloudEnterprise.get("poster_pic"));
        enterprise.setDescription((String)cloudEnterprise.get("description"));
        enterprise.setBusLicenseUrl(cloudEnterprise.get("bus_license_url")!=null?Global.cloud_file_url+(String)cloudEnterprise.get("bus_license_url"):null);
        enterprise.setCloudId((String)cloudEnterprise.get("id"));
        enterprise.setStatus(status);
        EnterpriseMatch enterpriseMatch = new EnterpriseMatch();
        //企业编号和匹配编号都为空时为新增、都不为空时为更新
        if (StringUtils.isNotBlank(enterpriseId)&&StringUtils.isNotBlank(enterpriseMatchId)){
            enterprise.preUpdate();
            enterprise.setId(enterpriseId);
            cloudEnterpriseMapper.update(enterprise);
            //企业同步记录
            enterpriseMatch.preUpdate();
            enterpriseMatch.setId(enterpriseMatchId);
            enterpriseMatch.setLastUpdateDate(DateUtils.parseDate((String)cloudEnterprise.get("update_time")));
            enterpriseMatchMapper.update(enterpriseMatch);
        }else {
            enterprise.preInsert();
            cloudEnterpriseMapper.insert(enterprise);
            enterpriseMatch.setBussId((String)cloudEnterprise.get("id"));
            enterpriseMatch.setBussType(EnterpriseMatch.TYPE_ENTERPRISE);
            enterpriseMatch.setPushResult("1");
            enterpriseMatch.setLastUpdateDate(DateUtils.parseDate(cloudEnterprise.get("update_time")));
            enterpriseMatch.preInsert();
            enterpriseMatchMapper.insert(enterpriseMatch);

        }
        return enterprise;

    }


    /**
     * 本地与云平台企业团队数据匹配
     * @param cloudEnterpriseTeamList
     */
    public void teamMatch(List<Map<String,Object>> cloudEnterpriseTeamList,String  enterpriseId){

        //获取本地所属该企业下的团队列表
        Map<String,Object> param = Maps.newHashMap();
        param.put("enterpriseId",enterpriseId);
        param.put("isNotLimit","1");
        List<CloudEnterpriseTeam> enterpriseTeamList = cloudEnterpriseTeamMapper.findEnterPriseTeamList(param);
        String cloudTeamIds = "";
        if (enterpriseTeamList !=null && !enterpriseTeamList.isEmpty()){
            cloudTeamIds = Collections3.extractToString(enterpriseTeamList,"cloudTeamId",",");
        }
        List<CloudEnterpriseTeam> tempList = enterpriseTeamList;
        for (Map<String,Object> map:cloudEnterpriseTeamList){
            String cloudTeamId = (String)map.get("id");
            String updateTime = (String) map.get("update_time");
            if (StringUtils.isNotBlank(cloudTeamId)) {
                if (enterpriseTeamList != null && !enterpriseTeamList.isEmpty() && cloudTeamIds.indexOf(cloudTeamId) > -1) {
                    for (CloudEnterpriseTeam enterpriseTeam:enterpriseTeamList){
                        if (cloudTeamId.equals(enterpriseTeam.getCloudTeamId())) {
                            //更新成员
                            Map<String, Object> paramTeam = Maps.newHashMap();
                            paramTeam.put("bussId", cloudTeamId);
                            paramTeam.put("bussType", EnterpriseMatch.TYPE_TEAM);
                            List<EnterpriseMatch> enterpriseMatchList = enterpriseMatchMapper.findList(paramTeam);
                            if (enterpriseMatchList != null && !enterpriseMatchList.isEmpty()) {
                                EnterpriseMatch enterpriseMatch = enterpriseMatchList.get(0);
                                if (enterpriseMatch != null &&(StringUtils.isNotBlank(updateTime)|| DateUtils.parseDate(updateTime).after(enterpriseMatch.getLastUpdateDate()))) {
                                    updateTeam(map, enterpriseMatch,enterpriseId);
                                }
                            }
                            tempList.remove(enterpriseTeam);
                            break;
                        }
                    }
                } else {
                    updateTeam(map,null,enterpriseId);
                }
            }
        }
        if (tempList!=null&& !tempList.isEmpty()){
            for (CloudEnterpriseTeam enterpriseTeam:tempList){
                Map<String,Object> paramMap = Maps.newHashMap();
                paramMap.put("bussId",enterpriseId);
                paramMap.put("bussType",EnterpriseMatch.TYPE_TEAM);
                enterpriseMatchMapper.deleteByBussIdAndType(paramMap);
                cloudEnterpriseTeamMapper.delete(enterpriseTeam.getId());
            }
        }

    }


    /**
     * 更新团队成员信息
     * @param cloudTeam
     * @param enterpriseMatch
     * @param enterpriseId
     */
    public void updateTeam(Map<String,Object> cloudTeam,EnterpriseMatch enterpriseMatch,String enterpriseId){
        CloudEnterpriseTeam enterpriseTeam = new CloudEnterpriseTeam();
        enterpriseTeam.setEnterpriseId(enterpriseId);
        enterpriseTeam.setFullName((String)cloudTeam.get("full_name"));
        enterpriseTeam.setIsShow((String)cloudTeam.get("is_show"));
        enterpriseTeam.setDescription((String)cloudTeam.get("description"));
        enterpriseTeam.setPosition((String)cloudTeam.get("position"));
        enterpriseTeam.setPic(Global.cloud_file_url+(String)cloudTeam.get("pic"));
        enterpriseTeam.setCloudTeamId((String) cloudTeam.get("id"));
        if (enterpriseMatch !=null){
            enterpriseTeam.preUpdate();
            cloudEnterpriseTeamMapper.updateByCloudTeamId(enterpriseTeam);
            //企业同步记录
            enterpriseMatch.setLastUpdateDate(DateUtils.parseDate((String)cloudTeam.get("update_time")));
            enterpriseMatch.setPushResult("1");
            enterpriseMatchMapper.update(enterpriseMatch);
        }else {
            enterpriseTeam.preInsert();
            cloudEnterpriseTeamMapper.save(enterpriseTeam);
            EnterpriseMatch enterpriseMatch1 = new EnterpriseMatch();
            enterpriseMatch1.setBussId((String)cloudTeam.get("id"));
            enterpriseMatch1.setBussType(EnterpriseMatch.TYPE_TEAM);
            enterpriseMatch1.setPushResult("1");
            enterpriseMatch1.setLastUpdateDate(DateUtils.parseDate(cloudTeam.get("update_time")));
            enterpriseMatch1.preInsert();
            enterpriseMatchMapper.insert(enterpriseMatch1);
        }
    }

    /**
     * 本地数据与云平台企业产品数据匹配
     * @param cloudEnterpriseProductList
     * @param enterprise
     */
    public void productMatch(List<Map<String,Object>> cloudEnterpriseProductList,CloudEnterprise enterprise){
        Map<String,Object> param = Maps.newHashMap();
        param.put("enterpriseId",enterprise.getId());
//        param.put("isCloud","1");
        param.put("isNotLimit","1");
        List<CloudEnterpriseProduct> enterpriseProductList = cloudEnterpriseProductMapper.findEnterPriseProductList(param);
        String cloudProductIds = "";
        if (enterpriseProductList !=null && !enterpriseProductList.isEmpty()){
            cloudProductIds = Collections3.extractToString(enterpriseProductList,"cloudProductId",",");
        }
        List<CloudEnterpriseProduct> tempList = enterpriseProductList;
        for (Map<String,Object> map:cloudEnterpriseProductList){
            String cloudProductId = (String)map.get("id");
            String updateTime = (String) map.get("update_time");
            if (StringUtils.isNotBlank(cloudProductId)) {
                if (enterpriseProductList != null && !enterpriseProductList.isEmpty() && cloudProductIds.indexOf(cloudProductId) > -1) {
                    for (CloudEnterpriseProduct enterpriseProduct:enterpriseProductList){
                        if (cloudProductId.equals(enterpriseProduct.getCloudProductId())) {
                            //更新产品
                            Map<String, Object> paramTeam = Maps.newHashMap();
                            paramTeam.put("bussId", cloudProductId);
                            paramTeam.put("bussType", EnterpriseMatch.TYPE_PRODUCT);
                            List<EnterpriseMatch> enterpriseMatchList = enterpriseMatchMapper.findList(paramTeam);
                            if (enterpriseMatchList != null && !enterpriseMatchList.isEmpty()) {
                                EnterpriseMatch enterpriseMatch = enterpriseMatchList.get(0);
                                if (enterpriseMatch != null && DateUtils.parseDate(updateTime).after(enterpriseMatch.getLastUpdateDate())) {
                                    updateProduct(map, enterpriseMatch, enterprise.getId());
                                }
                            }
                            tempList.remove(enterpriseProduct);
                            break;
                        }
                    }
                } else {
                    updateProduct(map,null,enterprise.getId());
                }
            }
        }
        if (tempList!=null&& !tempList.isEmpty()){
            for (CloudEnterpriseProduct enterpriseProduct:tempList){
                Map<String,Object> paramMap = Maps.newHashMap();
                paramMap.put("bussId",enterpriseProduct.getCloudProductId());
                paramMap.put("bussType",EnterpriseMatch.TYPE_PRODUCT);
                enterpriseMatchMapper.deleteByBussIdAndType(paramMap);
                cloudEnterpriseProductMapper.delete(enterpriseProduct.getId());
            }
        }
    }

    /**
     * 更新产品信息
     * @param cloudProduct
     * @param enterpriseMatch
     * @param enterpriseId
     */
    public void updateProduct(Map<String,Object> cloudProduct,EnterpriseMatch enterpriseMatch,String enterpriseId){
        CloudEnterpriseProduct enterpriseProduct = new CloudEnterpriseProduct();
        enterpriseProduct.setEnterpriseId(enterpriseId);
        enterpriseProduct.setPosterPic(Global.cloud_file_url+(String)cloudProduct.get("poster_pic"));
        enterpriseProduct.setDescription((String)cloudProduct.get("description"));
        enterpriseProduct.setName((String)cloudProduct.get("name"));
        enterpriseProduct.setSlogan((String)cloudProduct.get("slogan"));
        enterpriseProduct.setType((String)cloudProduct.get("type_name"));
        enterpriseProduct.setStatus((String)cloudProduct.get("status"));
        enterpriseProduct.setSummary((String)cloudProduct.get("summary"));
        enterpriseProduct.setCloudProductId((String) cloudProduct.get("id"));
        List<Map<String,String>> mapList = (List<Map<String, String>>) cloudProduct.get("poster_list");
        if (mapList !=null && !mapList.isEmpty()){
            String posters ="";
            for (Map<String,String> map :mapList){
                String src = map.get("src");
                if (StringUtils.isNotBlank(src)){
                    if (StringUtils.isNotBlank(posters)){
                        posters = ","+Global.cloud_file_url+src;
                    }else {
                        posters = Global.cloud_file_url+src;
                    }
                }
            }
            enterpriseProduct.setPosterIds(posters);
        }
        if (enterpriseMatch !=null){
            enterpriseProduct.preUpdate();
            cloudEnterpriseProductMapper.updateByCloudId(enterpriseProduct);
            //企业同步记录
            enterpriseMatch.setLastUpdateDate(DateUtils.parseDate((String)cloudProduct.get("update_time")));
            enterpriseMatch.setPushResult("1");
            enterpriseMatchMapper.update(enterpriseMatch);
        }else {
            enterpriseProduct.preInsert();
            cloudEnterpriseProductMapper.save(enterpriseProduct);
            EnterpriseMatch enterpriseMatch1 = new EnterpriseMatch();
            enterpriseMatch1.setBussId((String)cloudProduct.get("id"));
            enterpriseMatch1.setBussType(EnterpriseMatch.TYPE_PRODUCT);
            enterpriseMatch1.setPushResult("1");
            enterpriseMatch1.setLastUpdateDate(DateUtils.parseDate(cloudProduct.get("update_time")));
            enterpriseMatch1.preInsert();
            enterpriseMatchMapper.insert(enterpriseMatch1);
        }
    }

    /**
     * 本地数据与云平台职位招聘数据匹配
     * @param cloudEnterpriseJobList
     * @param enterprise
     */
    public void positionMatch(List<Map<String,Object>> cloudEnterpriseJobList,CloudEnterprise enterprise){
        Map<String,Object> param = Maps.newHashMap();
        param.put("enterpriseId",enterprise.getId());
        param.put("isNotLimit","1");
        List<CloudEnterprisePosition> enterpriseJobList = cloudEnterprisePositionMapper.selectEnterpriseJob(param);
        String cloudPositionIds = "";
        if (enterpriseJobList !=null && !enterpriseJobList.isEmpty()){
            cloudPositionIds = Collections3.extractToString(enterpriseJobList,"cloudPositionId",",");
        }
        List<CloudEnterprisePosition> tempList = enterpriseJobList;
        for (Map<String,Object> map:cloudEnterpriseJobList){
            String cloudPositionId = (String)map.get("id");
            String updateTime = (String) map.get("update_time");
            if (StringUtils.isNotBlank(cloudPositionId)) {
                if (enterpriseJobList != null && !enterpriseJobList.isEmpty() && cloudPositionIds.indexOf(cloudPositionId) > -1) {
                    for (CloudEnterprisePosition enterpriseJob:enterpriseJobList){
                        if (cloudPositionId.equals(enterpriseJob.getCloudPositionId())) {
                            //更新产品
                            Map<String, Object> paramTeam = Maps.newHashMap();
                            paramTeam.put("bussId", cloudPositionId);
                            paramTeam.put("bussType", EnterpriseMatch.TYPE_POSITION);
                            List<EnterpriseMatch> enterpriseMatchList = enterpriseMatchMapper.findList(paramTeam);
                            if (enterpriseMatchList != null && !enterpriseMatchList.isEmpty()) {
                                EnterpriseMatch enterpriseMatch = enterpriseMatchList.get(0);
                                if (enterpriseMatch != null && DateUtils.parseDate(updateTime).after(enterpriseMatch.getLastUpdateDate())) {
                                    updatePosition(map, enterpriseMatch, enterprise.getId());
                                }
                            }
                            tempList.remove(enterpriseJob);
                            break;
                        }
                    }
                } else {
                    updatePosition(map,null,enterprise.getId());
                }
            }
        }
        //删除
        if (tempList!=null&& !tempList.isEmpty()){
            List<String> list = new ArrayList<>();
            for (CloudEnterprisePosition cloudEnterprisePosition:tempList){
                Map<String,Object> paramMap = Maps.newHashMap();
                paramMap.put("bussId",cloudEnterprisePosition.getCloudPositionId());
                paramMap.put("bussType",EnterpriseMatch.TYPE_POSITION);
                enterpriseMatchMapper.deleteByBussIdAndType(paramMap);
                list.add(cloudEnterprisePosition.getId());
            }
            cloudEnterprisePositionMapper.delete(list);
        }
    }

    /**
     * 更新职位招聘信息
     * @param cloudPosition
     * @param enterpriseMatch
     * @param enterpriseId
     */
    public void updatePosition(Map<String,Object> cloudPosition,EnterpriseMatch enterpriseMatch,String enterpriseId){

        CloudEnterprisePosition enterpriseJob = new CloudEnterprisePosition();
        enterpriseJob.setEnterpriseId(enterpriseId);
        enterpriseJob.setAddress((String)cloudPosition.get("address"));
        enterpriseJob.setCity((String)cloudPosition.get("city_name"));
        enterpriseJob.setDepartment((String)cloudPosition.get("department"));
        enterpriseJob.setDescription((String)cloudPosition.get("description"));
        enterpriseJob.setEducation((String)cloudPosition.get("education_name"));
        enterpriseJob.setName((String)cloudPosition.get("name"));
        enterpriseJob.setJobType((String)cloudPosition.get("job_type"));
        enterpriseJob.setPositionType((String)cloudPosition.get("position_type_name"));
        enterpriseJob.setRecruitNumber((String)cloudPosition.get("recruit_number"));
        enterpriseJob.setSalaryMax((String)cloudPosition.get("salary_max"));
        enterpriseJob.setSalaryMin((String)cloudPosition.get("salary_min"));
        enterpriseJob.setSpotlights((String)cloudPosition.get("spotlights"));
        enterpriseJob.setWorkExperience((String)cloudPosition.get("work_experience_name"));
        enterpriseJob.setStatus((String)cloudPosition.get("status"));
        enterpriseJob.setCloudPositionId((String)cloudPosition.get("id"));
        if (enterpriseMatch !=null){
            enterpriseJob.preUpdate();
            cloudEnterprisePositionMapper.updateByCloudId(enterpriseJob);
            //企业同步记录
            enterpriseMatch.setLastUpdateDate(DateUtils.parseDate((String)cloudPosition.get("update_time")));
            enterpriseMatch.setPushResult("1");
            enterpriseMatchMapper.update(enterpriseMatch);
        }else {
            enterpriseJob.preInsert();
            cloudEnterprisePositionMapper.insert(enterpriseJob);
            EnterpriseMatch enterpriseMatch1 = new EnterpriseMatch();
            enterpriseMatch1.setBussId((String)cloudPosition.get("id"));
            enterpriseMatch1.setBussType(EnterpriseMatch.TYPE_POSITION);
            enterpriseMatch1.setPushResult("1");
            enterpriseMatch1.setLastUpdateDate(DateUtils.parseDate(cloudPosition.get("update_time")));
            enterpriseMatch1.preInsert();
            enterpriseMatchMapper.insert(enterpriseMatch1);
        }
    }


    /**
     *  获取云平台该企业下的产品数据，并保存到本地
     * @param enterprise
     */
    public void findEnterpriseProduct(CloudEnterprise enterprise){
        if (enterprise!=null && StringUtils.isNotBlank(enterprise.getCloudId())){
            Map<String,Object> map  = CloudPlatformUtils.get(Global.cloud_server_url+"/enterprise_product?del_flag=0&status=2&enterpriseId="+enterprise.getCloudId(),0);
            try {
                if (map!=null && !map.isEmpty() && map.get("code")!=null && ((Integer)map.get("code")) ==200){
                    List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get("data");
                    if (!mapList.isEmpty()){
                        productMatch(mapList,enterprise);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  获取云平台该企业下的团队成员数据，并保存到本地
     * @param enterprise
     */
    public void findEnterpriseTeam(CloudEnterprise enterprise){
        if (enterprise!=null && StringUtils.isNotBlank(enterprise.getCloudId())) {
            Map<String,Object> map = CloudPlatformUtils.get(Global.cloud_server_url + "/enterprise_team?del_flag=0&enterpriseId=" + enterprise.getCloudId(),0);
            try {
                if (map!=null && !map.isEmpty() && map.get("code")!=null && ((Integer)map.get("code")) ==200){
                    List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get("data");
                    if (!mapList.isEmpty()){
//                        teamMatch(mapList,enterprise);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  获取云平台该企业下的招聘职位数据，并保存到本地
     * @param enterprise
     */
    public void findEnterprisePosition(CloudEnterprise enterprise){
        if (enterprise!=null && StringUtils.isNotBlank(enterprise.getCloudId())) {
            Map<String,Object> map = CloudPlatformUtils.get(Global.cloud_server_url+"/enterprise_position?del_flag=0&status=20&enterpriseId="+enterprise.getCloudId(),0);
            try {
                if (map !=null && !map.isEmpty() && map.get("code")!=null && ((Integer)map.get("code")) ==200){
                    List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get("data");
                    if (!mapList.isEmpty()){
                        positionMatch(mapList,enterprise);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
