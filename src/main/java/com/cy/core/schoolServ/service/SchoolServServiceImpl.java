package com.cy.core.schoolServ.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.base.entity.Message;
import com.cy.common.utils.TimeZoneUtils;

import com.cy.core.chatGroup.dao.ChatGroupMapper;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.operation.dao.CommentMapper;
import com.cy.core.operation.entity.Comment;
import com.cy.core.schoolServ.entity.*;
import com.cy.core.share.dao.FileMapper;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.Global;
import com.cy.util.DateUtils;
import com.cy.util.WebUtil;
import com.cy.util.easemob.comm.utils.EasemobUtils;
import com.cy.util.file.DefaultFileUpload;
import com.cy.util.file.FileResult;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.schoolServ.dao.SchoolServMapper;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import static org.apache.struts2.ServletActionContext.getResponse;

@Service("schoolServService")
public class SchoolServServiceImpl implements SchoolServService {

    @Autowired
    private SchoolServMapper schoolServiceMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ChatGroupMapper chatGroupMapper;
    @Autowired
    private FileMapper fileMapper;

    /**
     * 存储
     *
     * @param //SchoolServ schoolService
     * @return true，成功；false，失败；
     */
    @Override
    public boolean save(SchoolServ schoolService) {
        // TODO Auto-generated method stub
        return schoolServiceMapper.save(schoolService);
    }


    /**
     * 更新
     *
     * @param //SchoolServ schoolService
     * @return true，成功；false，失败；
     */
    @Override
    public boolean update(SchoolServ schoolService) {
        // TODO Auto-generated method stub
        return schoolServiceMapper.update(schoolService);
    }


    /**
     * 获取总条数
     *
     * @param //Map<String, Object> map
     * @return long
     */
    @Override
    public long count(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return schoolServiceMapper.count(map);
    }


    /**
     * 获取列表
     *
     * @param //Map<String, Object> map
     * @return List<SchoolService>
     */
    @Override
    public List<SchoolServ> list(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return schoolServiceMapper.list(map);
    }


    /**
     * 所有信息列表(带分页)
     *
     * @param //Map<String, Object> map
     * @return DataGrid<SchoolService>
     */

    @Override
    public DataGrid<SchoolServ> dataGrid(Map<String, Object> map) {
        DataGrid<SchoolServ> dataGrid = new DataGrid<SchoolServ>();
        long total = schoolServiceMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<SchoolServ> list = schoolServiceMapper.list(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    /**
     * 删除
     *
     * @param //String ids
     */
    @Override
    public void delete(String ids) {
        String[] array = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (String id : array) {
            list.add(Long.parseLong(id));
        }

        schoolServiceMapper.delete(list);
    }


    /**
     * 获取详情
     *
     * @param //map
     * @return List<SchoolService>
     */
    @Override
    public SchoolServ selectById(long id) {
        /*News news = newsMapper.selectByIdNew(activityId);

		*//*if(news !=null && StringUtils.isNotBlank(news.getPic()) ){
            news.setPic(Global.URL_DOMAIN + news.getPic());
		}*/
        // TODO Auto-generated method stub
        return schoolServiceMapper.selectById(id);
    }


    /**
     * 获取服务列表
     *
     * @return List<SchoolService>
     */
    @Override
    public List<SchoolServ> getServiceList() {
        // TODO Auto-generated method stub
        return schoolServiceMapper.getServiceList();
    }

    public boolean saveFxjh(Fxjh fxjh) {
        fxjh.setClassinfo(fxjh.getClassinfo().replaceAll("\\s*", ""));
        String[] array = fxjh.getClassinfo().split(",");
        String classInfo = array[0];
        for (String ci : array) {
            if (ci.length() > classInfo.length()) {
                classInfo = ci;
            }
        }
        fxjh.setClassinfo(classInfo);
        fxjh.preInsert();
        fxjh.setType("1");
        fxjh.setStatus("20");
        return schoolServiceMapper.saveFxjh(fxjh);
    }


    //*********************************返校计划******************************************//

    /**
     * 所有信息列表(带分页)
     *
     * @param //Map<String, Object> map
     * @return DataGrid<SchoolService>
     */

    @Override
    public DataGrid<Fxjh> dataGridFxjh(Map<String, Object> map) {
        DataGrid<Fxjh> dataGridFxjh = new DataGrid<Fxjh>();
        long total = schoolServiceMapper.countFxjh(map);
        dataGridFxjh.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        //List<Fxjh> list = schoolServiceMapper.findFxjhList(map);
        List<Fxjh> list = schoolServiceMapper.findFxjhListNew(map);
        dataGridFxjh.setRows(list);
        return dataGridFxjh;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    /**
     * 获取返校计划列表
     *
     * @return List<SchoolService>
     */
    //@Override
    public List<Fxjh> getFxjhList(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return schoolServiceMapper.findFxjhList(map);
    }

    /**
     * 获取返校计划详情
     *
     * @param //map
     * @return List<SchoolService>
     */
    //@Override
    public Fxjh selectByFxjhId(String id) {
        /*News news = newsMapper.selectByIdNew(activityId);

		*//*if(news !=null && StringUtils.isNotBlank(news.getPic()) ){
            news.setPic(Global.URL_DOMAIN + news.getPic());
		}*/
        // TODO Auto-generated method stub
        return schoolServiceMapper.selectByFxjhId(id);
    }

    /**
     * 查询报名列表
     *
     * @param map
     * @return
     */
    public DataGrid<BackSchoolSign> getBackSchoolSign(Map<String, Object> map){
        DataGrid<BackSchoolSign> dataGrid = new DataGrid<>();
        long total = schoolServiceMapper.getUserCount(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<BackSchoolSign> list = schoolServiceMapper.findBackSchoolSignList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public void updateFxjh(Fxjh fxjh) {
        if (fxjh == null)
            throw new IllegalArgumentException("event cannot be null!");
        if (StringUtils.isNotBlank(fxjh.getClassinfo())) {
            fxjh.setClassinfo(fxjh.getClassinfo().replaceAll("\\s*", ""));
            String[] array = fxjh.getClassinfo().split(",");
            String classInfo = array[0];
            for (String ci : array) {
                if (ci.length() > classInfo.length()) {
                    classInfo = ci;
                }
            }
            fxjh.setClassinfo(classInfo);
        }
        fxjh.preUpdate();
        schoolServiceMapper.updateFxjh(fxjh);

    }

    public void deleteFxjh(String ids) {
        String[] array = ids.split(",");
        for (String id : array) {
            Fxjh fxjh = selectByFxjhId(id);
            fxjh.preUpdate();
            fxjh.setDelFlag("1");
            schoolServiceMapper.updateFxjh(fxjh);
            //删除返校计划同时删除群
            // 根据group_id查询该群组
            ChatGroup group = chatGroupMapper.queryChatGroupById(fxjh.getGroupId());
            Map<String, String> map = new HashMap<String, String>();
            map.put("groupId",fxjh.getGroupId());
            chatGroupMapper.updateDelFlag(fxjh.getGroupId());
            chatGroupMapper.updateGroupUserDelFlag(map);
            if (group != null) {
                EasemobUtils.deleteChatGroups(group.getEasemobGroupId());
            }
        }
    }

    /**
     * 获取服务列表接口
     *
     * @param message
     * @param content
     */
    public void findSchoolServList(Message message, String content) {
        if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);

        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        map.put("provideService", "1");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimni", "1");
        }
        if (StringUtils.isBlank((String) map.get("isAuditScreen")) || !((String) map.get("isAuditScreen")).equals("1")) {
            map.put("isAuditScreen", "");
        }
        List<SchoolServ> list = schoolServiceMapper.list(map);

        message.init(true, "查询成功", list, null);

    }

    /***********************************************************************
     *
     * 【返校计划】相关API（以下区域）
     *
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     *
     ***********************************************************************/
    /**
     * 创建返校计划接口
     */
    public void saveFxjh(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Fxjh fxjh = JSON.parseObject(content, Fxjh.class);

            if (StringUtils.isBlank(fxjh.getTopic())) {
                message.setMsg("组织主题不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getNumber())) {
                message.setMsg("计划人数不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getTime())) {
                message.setMsg("返校时间不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getEndTime())) {
                message.setMsg("返校计划结束时间不能为空");
                message.setSuccess(false);
                return;
            }

            Date start = DateUtils.parseDate(fxjh.getTime());
            Date end = DateUtils.parseDate(fxjh.getEndTime());

            if (start == null || end == null) {
                message.init(false, "非法的时间格式", null);
                return;
            }
            if (start.getTime() < TimeZoneUtils.getDate().getTime()) {
                message.setMsg("返校时间不能早于当前时间");
                message.setSuccess(false);
                return;
            }

            if (start.getTime() > end.getTime()) {
                message.setMsg("开始时间不能晚于结束时间");
                message.setSuccess(false);
                return;
            }

            if (StringUtils.isBlank(fxjh.getClassinfo())) {
                message.setMsg("班级信息不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getUserId())) {
                message.setMsg("用户编号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getOther())) {
                message.setMsg("描述不能为空");
                message.setSuccess(false);
                return;
            }

            UserProfile userProfile = userProfileMapper.selectByAccountNum(fxjh.getUserId());
            if (userProfile == null) {
                message.init(false, "用户不存在", null);
                return;
            }

            fxjh.setClassinfo(fxjh.getClassinfo().replaceAll("\\s*", ""));
            if (fxjh.getClassinfo().length() > 16) {
                fxjh.setClassinfo(fxjh.getClassinfo().substring(0, 16));
            }
            fxjh.setStatus("10");
            fxjh.setType("0");
            fxjh.setContent(fxjh.getOther());
            if (StringUtils.isBlank(fxjh.getId())) {
                fxjh.preInsert();
                schoolServiceMapper.saveFxjh(fxjh);
                //创建者自动报名
                BackSchoolSign backSchoolSign = new BackSchoolSign();
                backSchoolSign.setAccountNum(fxjh.getUserId());
                backSchoolSign.setBackSchoolId(fxjh.getId());
                backSchoolSign.setSignUpTime(TimeZoneUtils.getFormatDate());
                backSchoolSign.setViewNotification("0");
                schoolServiceMapper.saveBackSchoolSign(backSchoolSign);
            } else {
                Fxjh tmp = schoolServiceMapper.selectByFxjhId(fxjh.getId());
                if (tmp != null) {
                    fxjh.preUpdate();
                    schoolServiceMapper.updateFxjh(fxjh);
                } else {
                    message.init(false, "该计划不存在", null);
                }
            }

            message.setMsg("已提交计划申请");
            message.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3.0创建返校计划接口
     */
    public void saveFxjhNew(Message message, String content, File[] upload, String[] uploadFileName) throws FileNotFoundException, IOException {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Fxjh fxjh = JSON.parseObject(content, Fxjh.class);

            if (StringUtils.isBlank(fxjh.getTopic())) {
                message.setMsg("组织主题不能为空");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(fxjh.getPlace())){
                message.setMsg("活动地点不能为空");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(fxjh.getName())){
                message.setMsg("发起人不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getNumber())) {
                message.setMsg("计划人数不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getTime())) {
                message.setMsg("返校开始时间不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getEndTime())) {
                message.setMsg("返校结束时间不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getSignupStartTime())) {
                message.setMsg("返校报名开始时间不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getSignupEndTime())) {
                message.setMsg("返校报名结束时间不能为空");
                message.setSuccess(false);
                return;
            }

            Date startSign = DateUtils.parseDate(fxjh.getSignupStartTime());
            Date endSign = DateUtils.parseDate(fxjh.getSignupEndTime());
            Date start = DateUtils.parseDate(fxjh.getTime());
            Date end = DateUtils.parseDate(fxjh.getEndTime());

            if(!(new Date().getTime()<=startSign.getTime())){
                message.init(false, "确保报名开始时间晚于当前时间",null);
                return;
            }
            if(!(startSign.getTime()<endSign.getTime())){
                message.init(false, "确保报名结束时间晚于报名开始时间", null);
                return;
            }
            if(!(endSign.getTime() < start.getTime())){
                message.init(false, "确保活动开始时间晚于报名结束时间",null);
                return;
            }
            if(!(start.getTime() < end.getTime())){
                message.init(false, "确保活动结束时间晚于活动开始时间", null);
                return;
            }

            if (StringUtils.isBlank(fxjh.getClassinfo())) {
                message.setMsg("班级信息不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getUserId())) {
                message.setMsg("用户编号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(fxjh.getOther())) {
                message.setMsg("描述不能为空");
                message.setSuccess(false);
                return;
            }

            //海报
            DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
            defaultFileUpload.setFileDir("backSchool/" + fxjh.getUserId());
            defaultFileUpload.setMaxSize(1048576 * 5);
            defaultFileUpload.setCheckSuffix(true);
            defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
            StringBuffer urlsb = new StringBuffer();
            if(upload != null) {
                for (int i = 0; i < upload.length; i++) {
                    FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
                    urlsb.append("/" + fileResult.getFileUrl().substring(Global.URL_DOMAIN.length()));
                    urlsb.append("|");
                }
            }
            if (urlsb.length() > 0) {
                urlsb.deleteCharAt(urlsb.length() - 1);
                fxjh.setPoster(urlsb.toString());
            }

            UserProfile userProfile = userProfileMapper.selectByAccountNum(fxjh.getUserId());
            if (userProfile == null) {
                message.init(false, "用户不存在", null);
                return;
            }

            //收费
            if(StringUtils.isBlank(fxjh.getCost()) || fxjh.getCost().equals("0")){
                fxjh.setIsFree("0");
                fxjh.setCost("0");
            }else{
                fxjh.setIsFree("1");
            }
            //生成签到码
            if("1".equals(fxjh.getNeedSignIn())) {
                fxjh.setSignInCode(getSignCode());
            }

            fxjh.setClassinfo(fxjh.getClassinfo().replaceAll("\\s*", ""));
            if (fxjh.getClassinfo().length() > 16) {
                fxjh.setClassinfo(fxjh.getClassinfo().substring(0, 16));
            }
            fxjh.setStatus("10");
            fxjh.setType("0");
            fxjh.setContent(fxjh.getOther());
            //if (StringUtils.isBlank(fxjh.getId())) {
                fxjh.preInsert();
                schoolServiceMapper.saveFxjhNew(fxjh);
                //创建者自动报名
                BackSchoolSign backSchoolSign = new BackSchoolSign();
                backSchoolSign.setAccountNum(fxjh.getUserId());
                backSchoolSign.setBackSchoolId(fxjh.getId());
                backSchoolSign.setSignUpTime(TimeZoneUtils.getFormatDate());
                backSchoolSign.setViewNotification("0");
                schoolServiceMapper.saveBackSchoolSign(backSchoolSign);
            //} else {
                //Fxjh tmp = schoolServiceMapper.selectByFxjhId(fxjh.getId());
                //if (tmp != null) {
                    //fxjh.preUpdate();
                    //schoolServiceMapper.updateFxjh(fxjh);
                //} else {
                    //message.init(false, "该计划不存在", null);
                //}
            //}
            message.setObj(fxjh);
            message.setMsg("已提交计划申请");
            message.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取签到码
     * @return
     */
    public String getSignCode(){
        Random r = new Random();
        int x = r.nextInt(9999);
        String code = String.format("%04d", x);
        return code;
    }

    /**
     * 删除返校计划
     *
     * @param message
     * @param content
     */
    public void dropFxjh(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String backSchoolId = map.get("backSchoolId");
            String userId = map.get("userId");

            if (StringUtils.isBlank(backSchoolId)) {
                message.setMsg("用户ID不能为空");
                message.setSuccess(false);
                return;
            }

            UserProfile userProfile = userProfileMapper.selectByAccountNum(userId);
            if (userProfile == null) {
                message.init(false, "用户不存在", null);
                return;
            }

            if (StringUtils.isBlank(userId)) {
                message.setMsg("返校计划ID不能为空");
                message.setSuccess(false);
                return;
            }

            Fxjh fxjh1 = selectByFxjhId(backSchoolId);
            if (fxjh1 == null) {
                message.init(false, "不存在的返校计划", null);
                return;
            }

            if (!userId.equals(fxjh1.getUserId())) {
                message.init(false, "该计划不是您创建的", null);
                return;
            }

            fxjh1.setStatus("50");
            updateFxjh(fxjh1);
//            Map<String,Object> map1 = new HashMap<>();
//            map1.put("backSchoolId",fxjh1.getId());
//            map1.put("isNotLimit","1");
//            List<BackSchoolSign> backSchoolSignList = schoolServiceMapper.findBackSchoolSignList(map1);
//            String accountNums = "";
//            if (backSchoolSignList != null && backSchoolSignList.size()>0 && backSchoolSignList.get(0)!= null){
//                for (BackSchoolSign backSchoolSign: backSchoolSignList){
//                    if (StringUtils.isNotBlank(backSchoolSign.getAccountNum())){
//                        accountNums = accountNums +backSchoolSign.getAccountNum() +",";
//                    }
//                }
//            }
//            Map<String,Object> objectMap = new HashMap<>();
//            objectMap.put("msgType","10");
//            objectMap.put("accountNums",accountNums);
//            objectMap.put("content",userProfile.getName()+"已取消返校计划["+fxjh1.getTopic()+"]！\r\n请点击下面链接查看：\r\n"+Global.cy_server_url+"/mobile/services/backSchool/detail_plan.html?categoryId="+fxjh1.getId());
//            WechatPushUtils.pushWechat(objectMap);

            message.init(true, "取消成功", null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取返校计划列表接口
     *
     * @param message
     * @param content
     */
    public void findFxjhList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);

        if ("1".equals(map.get("type")) || "2".equals(map.get("type")) || "3".equals(map.get("type"))) {
            if (StringUtils.isBlank((String) map.get("userId"))) {
                message.setMsg("用户Id为空");
                message.setSuccess(false);
                return;
            } else {
                UserProfile userProfile = userProfileMapper.selectByAccountNum((String) map.get("userId"));

                if (userProfile == null) {
                    message.init(false, "用户不存在", null);
                    return;
                }
            }
        }else {

        }


        if (StringUtils.isBlank((String) map.get("type"))) {
            message.setMsg("查询类型为空");
            message.setSuccess(false);
            return;
        }
        if ("0".equals(map.get("type"))) {
            map.put("userId", "");
            map.put("status", "20");
        } else if ("1".equals(map.get("type"))) {
            //查询当前用户创建的
        } else if ("2".equals(map.get("type"))) {
            //查询当前用户参与的
            map.put("currentUserId", map.get("userId"));
            map.put("userId", "");
            map.put("status", "20");
        } else if ("3".equals(map.get("type"))) {
            map.put("favoriteUserId", map.get("userId"));
            map.put("userId", "");
            map.put("status", "20");
        } else {
            message.init(false, "非法的查询类型", null);
            return;
        }

        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimit", "1");
        }
        List<Fxjh> list = schoolServiceMapper.findFxjhList(map);


        message.init(true, "查询成功", list);

    }

    /**
     * 3.0获取返校计划列表接口
     *
     * @param message
     * @param content
     */
    public void findFxjhListNew(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);

        if ("1".equals(map.get("type")) || "2".equals(map.get("type")) || "3".equals(map.get("type"))) {
            if (StringUtils.isBlank((String) map.get("userId"))) {
                message.setMsg("用户Id为空");
                message.setSuccess(false);
                return;
            } else {
                UserProfile userProfile = userProfileMapper.selectByAccountNum((String) map.get("userId"));

                if (userProfile == null) {
                    message.init(false, "用户不存在", null);
                    return;
                }
            }
        }else {

        }

        if (StringUtils.isBlank((String) map.get("type"))) {
            message.setMsg("查询类型为空");
            message.setSuccess(false);
            return;
        }
        if ("0".equals(map.get("type"))) {
            map.put("userId", "");
            map.put("status", "20");
        } else if ("1".equals(map.get("type"))) {
            //查询当前用户创建的
        } else if ("2".equals(map.get("type"))) {
            //查询当前用户参与的
            map.put("currentUserId", map.get("userId"));
            map.put("userId", "");
            map.put("status", "20");
        } else if ("3".equals(map.get("type"))) {
            map.put("favoriteUserId", map.get("userId"));
            map.put("userId", "");
            map.put("status", "20");
        } else {
            message.init(false, "非法的查询类型", null);
            return;
        }
        //模糊查询值年返校活动
        map.put("topic", map.get("topic"));
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimit", "1");
        }
        DataGrid<Fxjh> dataGrid = new DataGrid<Fxjh>();
        List<Fxjh> list = schoolServiceMapper.findFxjhListNew(map);
        long total = schoolServiceMapper.findFxjhListCountNew(map);
        dataGrid.setRows(list);
        dataGrid.setTotal(total);
        message.init(true ,"查询成功",dataGrid,null);

    }

    /**
     * 获取返校计划详情接口
     *
     * @param message
     * @param content
     */
    public void getFxjhById(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        if (StringUtils.isBlank(map.get("id"))) {
            message.setMsg("计划Id为空");
            message.setSuccess(false);
            return;
        }

        Fxjh fxjh = schoolServiceMapper.selectByFxjhIdAndUserId(map);


        if (fxjh != null) {

            if (StringUtils.isNotBlank(map.get("userId"))) {
                // 当前用户是否拥有者
                if (map.get("userId").equals(fxjh.getUserId())) {
                    fxjh.setIsOwner("1");
                } else {
                    fxjh.setIsOwner("0");
                }
                // 当前用户是否点赞
                Map<String, Object> tmp = new HashMap<>();
                tmp.put("userId", map.get("userId"));
                tmp.put("type", "30");
                tmp.put("bussId", fxjh.getId());
                tmp.put("isNoLimit", "1");
                List<Comment> list = commentMapper.getCommentList(tmp);
                tmp.put("type", "10");
                long countComment = commentMapper.count(tmp);
                fxjh.setUserCommentCount(String.valueOf(countComment));
                if (list != null && list.size() > 0) {
                    fxjh.setHasPraised("1");
                } else {
                    fxjh.setHasPraised("0");
                }
            }


            Map<String, Object> mapTmp = new HashMap<>();
            mapTmp.put("backSchoolId", fxjh.getId());
            mapTmp.put("isNotLimit", "1");
            List<BackSchoolSign> fullList = schoolServiceMapper.findBackSchoolSignList(mapTmp);
            List<BackSchoolSign> signList = new ArrayList<>();

            for (BackSchoolSign bss : fullList) {
                if ("1".equals(bss.getIsOwner())) {
                    signList.add(0, bss);
                } else {
                    signList.add(bss);
                }
            }
            if (signList.size() > 5) {
                List<BackSchoolSign> tmp = new ArrayList<>();
                int count = 0;
                for (BackSchoolSign bss : signList) {
                    tmp.add(bss);
                    count++;
                    if (count == 5)
                        break;
                }
                signList = tmp;
            }
            fxjh.setSignList(signList);
            message.init(true, "查询成功", fxjh);
        } else {
            message.init(false, "未查询到此计划", null);
        }

    }

    /**
     * 3.0获取返校计划详情接口
     *
     * @param message
     * @param content
     */
    public void getFxjhNewById(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        if (StringUtils.isBlank(map.get("id"))) {
            message.setMsg("计划Id为空");
            message.setSuccess(false);
            return;
        }

        UserProfile userProfile = userProfileMapper.selectByAccountNum(map.get("userId"));
        if(userProfile==null){
            message.setMsg("用戶ID不存在");
            message.setSuccess(false);
            return;
        }

        Fxjh fxjh = schoolServiceMapper.selectByFxjhIdAndUserIdNew(map);

        if (fxjh != null) {

            if (StringUtils.isNotBlank(map.get("userId"))) {
                // 当前用户是否拥有者
                if (map.get("userId").equals(fxjh.getUserId())) {
                    fxjh.setIsOwner("1");
                } else {
                    fxjh.setIsOwner("0");
                }

                Map<String, Object> tmp = new HashMap<>();
                tmp.put("userId", map.get("userId"));
                tmp.put("type", "30");
                tmp.put("bussId", fxjh.getId());
                tmp.put("isNoLimit", "1");
                //List<Comment> list = commentMapper.getCommentList(tmp);
                tmp.put("type", "10");
                long countComment = commentMapper.count(tmp);
                fxjh.setUserCommentCount(String.valueOf(countComment));
            }

            //获取5个报名人员列表
            Map<String, Object> mapTmp = new HashMap<>();
            mapTmp.put("backSchoolId", fxjh.getId());
            String page = "1";
            String rows = "5";
            if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
                int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
                mapTmp.put("start", start);
                mapTmp.put("rows", Integer.valueOf(rows));
            } else {
                mapTmp.put("isNotLimit", "1");
            }
            List<BackSchoolSign> signList = schoolServiceMapper.findBackSchoolSignList(mapTmp);
            fxjh.setSignList(signList);

            //花絮条数
            Map<String, Object> map1 = new HashMap<>();
            map1.put("backschoolId", fxjh.getId());
            //返回总数
            Long boardCount = schoolServiceMapper.findBackchoolBoardListCount(map1);
            fxjh.setBoardCount(boardCount);
            message.init(true, "查询成功", fxjh);
        } else {
            message.init(false, "未查询到此计划", null);
        }

    }

    /**
     * 3.0返校计划签到
     */
    public void findFxjhSign(Message message ,String content){
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String accountNum = map.get("userId");
        String id = map.get("id");
        if(StringUtils.isBlank(accountNum)){
            message.setMsg("请传入用户ID");
            message.setSuccess(false);
            return;
        }
        if(StringUtils.isBlank(id)){
            message.setMsg("请传入返校计划Id");
            message.setSuccess(false);
            return;
        }
        map.put("id",id);
        Fxjh fxjh = schoolServiceMapper.selectByFxjhIdAndUserIdNew(map);
        if(fxjh == null){
            message.setMsg("返校计划活动不存在");
            message.setSuccess(false);
            return;
        }

        if(fxjh.getEventStatus().equals("50")){
            message.setMsg("报名尚未开始，不能签到");
            message.setSuccess(false);
            return;
        }else if(fxjh.getEventStatus().equals("70")){
            message.setMsg("活动已经结束");
            message.setSuccess(false);
            return;
        }

        UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
        if(userProfile==null){
            message.setMsg("用戶ID不存在");
            message.setSuccess(false);
            return;
        }
        if(StringUtils.isBlank(userProfile.getClasses())){
            message.init(false, "用户没有学习经历", null);
            return;
        }
        String[] classes = userProfile.getBaseInfoId().split(",");
        Boolean isInClasses = false;
        for(String cl:classes){
            if(cl.contains(fxjh.getClassinfo())){
                isInClasses = true;
                break;
            }
        }
        if(!isInClasses){
            message.init(false, "您不属于该班级", null);
            return;
        }
        //判断当前用户是否签到
        Map<String,String> signMap = new HashMap<>();
        signMap.put("id",id);
        signMap.put("accountNum",accountNum);
        BackSchoolSign backSchoolSign = schoolServiceMapper.isSignInOrNot(signMap);
        if (backSchoolSign != null) {
            if (("1").equals(backSchoolSign.getIsSignIn())) {
                message.setMsg("已签到，无需重复签到");
                message.setSuccess(false);
                return;
            }
            //用户签到
            schoolServiceMapper.updateBackSchoolSign(signMap);
        } else {
            //用户签到并报名
            schoolServiceMapper.insertBackSchoolSign(signMap);
        }
        message.setSuccess(true);
        message.setMsg("签到成功");

    }

    /**
     * 3.0返校计划提供的服务
     */
    public void findFxjhServices(Message message ,String content){
        Map<String, String> map = JSON.parseObject(content, Map.class);
        //返校计划ID
        String id = map.get("id");
        if(StringUtils.isBlank(id)){
            message.setMsg("请传入返校计划Id");
            message.setSuccess(false);
            return;
        }
        map.put("id",id);
        String fxjhServices = schoolServiceMapper.selectFxjhServicesById(map);

        message.setObj(fxjhServices);
        message.setSuccess(true);
        message.setMsg("提供的服务查询成功");

    }

    /**
     * 3.0返校计划群ID更新
     */
    public void updateFxjhGroupId(Message message ,String content){
        Map<String, String> map = JSON.parseObject(content, Map.class);
        //返校计划ID
        String id = map.get("id");
        String groupId = map.get("groupId");
        if(StringUtils.isBlank(id)){
            message.setMsg("请传入返校计划Id");
            message.setSuccess(false);
            return;
        }
        if(StringUtils.isBlank(groupId)){
            message.setMsg("请传入返校群ID");
            message.setSuccess(false);
            return;
        }
        map.put("id",id);
        map.put("groupId",groupId);
        schoolServiceMapper.updateFxjhGroupId(map);

        message.setSuccess(true);
        message.setMsg("返校计划群ID更新成功");

    }

    /**
     * 返校计划报名
     *
     * @param message
     * @param content
     */
    public void addBackSchoolSign(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            BackSchoolSign backSchoolSign = JSON.parseObject(content, BackSchoolSign.class);

            if (StringUtils.isBlank(backSchoolSign.getBackSchoolId())) {
                message.setMsg("返校计划为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(backSchoolSign.getAccountNum())) {
                message.setMsg("报名用户为空");
                message.setSuccess(false);
                return;
            }

            UserProfile userProfile = userProfileMapper.selectByAccountNum(backSchoolSign.getAccountNum());
            if (userProfile == null) {
                message.init(false, "用户不存在", null);
                return;
            }
            Fxjh fxjh = schoolServiceMapper.selectByFxjhId(backSchoolSign.getBackSchoolId());

            if(fxjh == null){
                message.init(false, "返校计划不存在", null);
                return;
            }
            if(StringUtils.isBlank(userProfile.getClasses())){
                message.init(false, "用户没有学习经历", null);
                return;
            }
            String[] classes = userProfile.getBaseInfoId().split(",");
            Boolean isInClasses = false;
            for(String cl:classes){
                if(cl.contains(fxjh.getClassinfo())){
                    isInClasses = true;
                    break;
                }
            }
            if(!isInClasses){
                message.init(false, "您不属于该班级", null);
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("backSchoolId", backSchoolSign.getBackSchoolId());
            map.put("accountNum", backSchoolSign.getAccountNum());
            map.put("isNotLimit", "1");
            List<BackSchoolSign> backSchoolSignList = schoolServiceMapper.findBackSchoolSignList(map);
            if (backSchoolSignList != null && backSchoolSignList.size() > 0) {
                BackSchoolSign backSchoolSign1 = backSchoolSignList.get(0);
                schoolServiceMapper.deleteBackSchoolSign(backSchoolSign1.getId());
                message.init(true, "取消报名成功", null);
                return;
            }

            if (StringUtils.isNotBlank(fxjh.getTime())) {
                if (DateUtils.parseDate(fxjh.getTime()).getTime() < TimeZoneUtils.getDate().getTime()) {
                    message.init(false, "返校计划报名已结束", null);
                    return;
                }
            } else {
                message.init(false, "该返校计划的返校时间为空", null);
                return;
            }
            if (StringUtils.isNotBlank(fxjh.getNumber())) {
                long num = schoolServiceMapper.getUserCount(map);
                if (!"0".equals(fxjh.getNumber()) && num >= Long.parseLong(fxjh.getNumber())) {
                    message.init(false, "该返校计划人数已满", null);
                    return;
                }
            } else {
                message.init(false, "该返校计划的人数为空", null);
                return;
            }

            backSchoolSign.setSignUpTime(TimeZoneUtils.getFormatDate());
            backSchoolSign.setViewNotification("0");
            schoolServiceMapper.saveBackSchoolSign(backSchoolSign);
            message.init(true, "报名成功", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 返校计划报名二维码 ，现在无用
     */

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public void addBackSchoolSignQR(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.init(false, "未传入参数", null);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String backSchooolId = map.get("backSchooolId");
            if (StringUtils.isBlank(backSchooolId)) {
                message.init(false, "返校计划编号为空", null);
                return;
            }

            String qrUrl = "http://www.cymobi.com";
            getResponse().setDateHeader("Expires", 0);
            getResponse().setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            getResponse().addHeader("Cache-Control", "post-check=0, pre-check=0");
            getResponse().setHeader("Pragma", "no-cache");
            getResponse().setContentType("image/gif");

            if (StringUtils.isBlank(backSchooolId)) {

                qrUrl += "?err=返校计划id为空";
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("command", "502");
                jsonObject.put("backSchooolId", backSchooolId);
                String jsonStr = jsonObject.toJSONString();
                qrUrl += "?qr=" + jsonStr;
            }

            System.out.println("--------------> qrUrl : " + qrUrl);

            int width = 300;
            int height = 300;
            //二维码的图片格式
            String format = "gif";
            Hashtable hints = new Hashtable();
            //内容所使用编码
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl,
                    BarcodeFormat.QR_CODE, width, height, hints);
            //生成二维码
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            ServletOutputStream out = getResponse().getOutputStream();
            ImageIO.write(image, format, out);
            out.flush();
            out.close();
            // message.init(true,"二维码生成成功",null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 报名人列表接口
     *
     * @param message
     * @param content
     */
    public void findBackSchoolSignList(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.init(false, "未传入参数", null);
                return;
            }
            Map<String, Object> map = JSON.parseObject(content, Map.class);
            String backSchoolId = (String) map.get("backSchoolId");
            String page = (String) map.get("page");
            String rows = (String) map.get("rows");
            if (StringUtils.isBlank(backSchoolId)) {
                message.init(false, "返校计划编号为空", null);
                return;
            }
            if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
                int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
                map.put("start", start);
                map.put("rows", Integer.valueOf(rows));
            } else {
                map.put("isNotLimit", "1");
            }
            List<BackSchoolSign> list = schoolServiceMapper.findBackSchoolSignList(map);
            message.init(true, "查询报名列表成功", list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 返校计划计数
     *
     * @param message
     * @param content
     */
    public void findUsersBackSchoolCount(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.init(false, "未传入参数", null);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String userId = map.get("userId");
            if (StringUtils.isBlank(userId)) {
                message.init(false, "未传入用户Id", null);
                return;
            }
            UserProfile userProfile = userProfileMapper.selectByAccountNum(userId);
            if (userProfile == null) {
                message.init(false, "不存在的用户", null);
                return;
            }
            Map<String, String> resultMap = schoolServiceMapper.findUserBackSchoolCount(userId);

            if (StringUtils.isNotBlank(resultMap.get("picture")) && resultMap.get("picture").indexOf("http") < 0) {
                resultMap.put("picture_xd", resultMap.get("picture"));
            }
            if (StringUtils.isNotBlank(resultMap.get("picture")) && resultMap.get("picture").indexOf("http") < 0) {
                resultMap.put("picture", Global.URL_DOMAIN + resultMap.get("picture"));
            }
            message.init(true, "查询成功", resultMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWebFxjhGroupId(Map<String,String> map) {
        schoolServiceMapper.updateFxjhGroupId(map);
    }

    //保存返校计划花絮
    public void saveBackSchoolBoard(Message message, String content) {
        if (StringUtils.isBlank(content)) { //判断客户端传来的参数是否为空或为0或为空白符构成
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
        }
        try {
            BackschoolBoard backschoolBoard = JSON.parseObject(content, BackschoolBoard.class);//将客户端参数序列化
            if (StringUtils.isBlank(backschoolBoard.getBackschoolId())) {
                message.setMsg("计划id不能为空");
                message.setSuccess(false);
            }
            if (StringUtils.isBlank(backschoolBoard.getUserInfoId())) {//判断花絮的用户id是否为空
                message.setMsg("用户id不能为空");
                message.setSuccess(false);
            }
            if (StringUtils.isBlank(backschoolBoard.getComment())) {//判断花絮的内容是否为空
                message.setMsg("花絮内容不能为空");
                message.setSuccess(false);
                return;
            }

            backschoolBoard.setCreateTime(new Date());//设置返校计划花絮创建时间
            backschoolBoard.setStatus(0);//设置返校计划花絮状态
            //保存花絮内容简介
            schoolServiceMapper.saveBackschoolBoard(backschoolBoard);

            //YS 2017/7/18
            //通过花絮对象中的参数获取当前花絮的id
            String id = schoolServiceMapper.getBoardId(backschoolBoard);

            //YS 2017/7/14
            //保存返校计划花絮图片
            for (BackschoolBoardPic Pic : backschoolBoard.getBackschoolBoardPicList()) {//循环遍历返校计划活动花絮图片列表
                BackschoolBoardPic backschoolBoardPic = new BackschoolBoardPic();//创建返校计划花絮图片对象
                backschoolBoardPic.setBoardId(Long.parseLong(id));//通过花絮对象id参数设置返校计划花絮id 7/18修改
                backschoolBoardPic.setPic(Pic.getPic());//设置花絮图片
                schoolServiceMapper.saveBackschoolBoardPic(backschoolBoardPic);//保存返校计划花絮图片
            }
            //------------------
            message.setMsg("插入花絮成功！");
            message.setSuccess(true);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取返校计划花絮列表
    public void findBackschoolBoardList(Message message, String content) {
        if (StringUtils.isBlank(content)) {//判断客户端传来参数是否为空
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);//将客户端传来的参数序列化

        String backschoolId = (String) map.get("backschoolId");//获取返校计划id

        //YS 2017/7/18
        map.put("backschoolId", backschoolId);
        //返回总数
        Long total = schoolServiceMapper.findBackchoolBoardListCount(map);//通过返校计划对象的参数获取返校计划活动花絮列表的数量
        //YS 2017/7/18
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");

        if (StringUtils.isBlank(backschoolId)) {//判断返校计划id是否为空
            message.setMsg("活动id不能为空");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNoLimit", "1");
        }
        List<BackschoolBoard> list = schoolServiceMapper.findBackchoolBoardList(map);//通过返校计划对象的参数获取返校计划活动花絮列表
        message.setMsg("获取花絮列表成功");
        message.setSuccess(true);
        Map<String, Object> listMap = new HashMap<>();
        listMap.put("detailList", list);
        listMap.put("total", total);
        //YS 2017/7/13
        message.setObj(listMap);
        return;
    }

    //获取返校计划花絮详情
    public void findBackschoolBoard(Message message, String content) {
        if (StringUtils.isBlank(content)) {//判断客户端传来的参数是否为空
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);//将客户端传来的参数序列化
        String boardId = (String) map.get("boardId");//获取花絮id
        String userId = (String) map.get("userId");//获取用户id
        if (StringUtils.isBlank(boardId)) {//判断花絮id是否为空
            message.setMsg("未传入花絮id");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(userId)) {//判断用户id是否为空
            message.setMsg("未传入用户id");
            message.setSuccess(false);
            return;
        }

        BackschoolBoard backschoolBoard = new BackschoolBoard();//新建返校计划花絮对象
        backschoolBoard.setId(Long.parseLong(boardId));//设置花絮id
        backschoolBoard.setUserInfoId(userId);//设置用户id
        BackschoolBoard backBoard = schoolServiceMapper.getbackschoolBoard(backschoolBoard);//通过返校计划花絮对象的参数来获取返校计划花絮对象

        if (backBoard == null) { //判断该花絮是否存在
            message.setMsg("该花絮不存在");
            message.setSuccess(false);
            return;
        }

        //YS 2017/7/17 代码修改

        BackschoolBoardPraise backschoolBoardPraise = new BackschoolBoardPraise();//创建返校计划花絮点赞对象

        backschoolBoardPraise.setBoardId((backBoard.getId() + "")); //设置返校计划花絮点赞id

        backschoolBoardPraise.setUserId(backBoard.getUserInfoId());//设置返校计划花絮用户id

        backschoolBoardPraise.setCreateTimeStr(WebUtil.pastTime(backBoard.getCreateTime()));//获取用户点赞创建时间

        backBoard.setPraiseNum(schoolServiceMapper.countBackschoolPraise(backschoolBoardPraise));//设置返校计划花絮点赞数量

        String userPic = backBoard.getUserAvatar();//获取用户头像

        if (WebUtil.isNumeric(userPic)) {
            backBoard.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));//设置创建花絮的用户头像
        }
        //

        BackschoolBoardPic backschoolBoardPic = new BackschoolBoardPic();//创建返校计划花絮图片对象

        backschoolBoardPic.setBoardId(Long.parseLong(boardId));//设置花絮id

        //YS 2017/7/15 通过返校计划花絮对象参数获取用户点赞id
        String userPraiseId = schoolServiceMapper.boardPraiseYesOrNotByUserId(backschoolBoard);
        if (StringUtils.isBlank(userPraiseId)) {
            backBoard.setPraiseOrNot("0");//0表示没有点赞(设置状态码)
        } else {
            backBoard.setPraiseOrNot("1");//1表示点过赞
        }
        //------------

        //YS 2017/7/17
        String isOwner = schoolServiceMapper.isOwnBoard(backschoolBoard);
        if (StringUtils.isBlank(isOwner)) {
            backBoard.setIsOwner("0");//表示不是自己创建的；
        } else {
            backBoard.setIsOwner("1");//表示是自己创建的；
        }
        //------------

        backBoard.setBackschoolBoardPicList(schoolServiceMapper.getBackschoolBoardPicList(backschoolBoardPic));//设置返校计划花絮图片

        List<BackschoolBoardComment> list = schoolServiceMapper.getBackschoolBoardCommentList(boardId);//获取返校计划花絮评论列表

        backBoard.setBackschoolBoardCommentList(list);//设置返校计划花絮评论列表

        message.setMsg("获取花絮详情成功");
        message.setSuccess(true);
        message.setObj(backBoard);
        return;
    }

    //保存返校计划花絮评论或点赞
    public void saveCommentOrPraise(Message message, String content) {
        if (StringUtils.isBlank(content)) { //判断客户端传来的参数是否为空或为0或由空白符构成
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }
        try {
            Map<String, String> map = JSON.parseObject(content, Map.class); //将客户端传来的参数序列化

            String boardId = map.get("boardId");
            String userId = map.get("userId");
            String action = map.get("action");
            String comment = map.get("comment");

            if (StringUtils.isBlank(boardId)) {
                message.setMsg("花絮编号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(userId)) {
                message.setMsg("评论人账号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(action)) {
                message.setMsg("操作类型不能为空");
                message.setSuccess(false);
                return;
            }

            if (action.equals("0") || action.equals("1")) {

                BackschoolBoardComment backschoolBoardComment = new BackschoolBoardComment();
                backschoolBoardComment.setBoardId(Long.parseLong(boardId));
                backschoolBoardComment.setUserInfoId(userId);
                backschoolBoardComment.setComment(comment);

                if (action.equals("0")) {
                    if (StringUtils.isBlank(comment)) {
                        message.setMsg("没有填写评论内容");
                        message.setSuccess(false);
                        return;
                    }

                    schoolServiceMapper.insertBackSchoolBoardComment(backschoolBoardComment);
                    message.setMsg("评论成功");
                    message.setSuccess(true);
                    return;
                } else {
                    schoolServiceMapper.deleteBachschoolBoardComment(backschoolBoardComment);
                    message.setMsg("删除评论成功");
                    message.setSuccess(true);
                    return;
                }

            } else if (action.equals("2") || action.equals("3")) {
                BackschoolBoardPraise backschoolBoardPraise = new BackschoolBoardPraise();

                backschoolBoardPraise.setBoardId(boardId);//设置花絮id
                backschoolBoardPraise.setUserId(userId);//设置用户id

                if (action.equals("2")) {
                    schoolServiceMapper.insertBackschoolBoardPraise(backschoolBoardPraise);
                    message.setMsg("点赞成功");
                    message.setSuccess(true);
                    return;
                } else {
                    schoolServiceMapper.deleteBackschoolBoardPraise(backschoolBoardPraise);
                    message.setMsg("取消点赞成功");
                    message.setSuccess(true);
                    return;
                }
            } else {
                message.setMsg("未知的操作类型");
                message.setSuccess(false);
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //保存返校计划花絮举报
    public void saveBackschoolBoardComplaint(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }

        try {
            BackschoolBoardComplaint backschoolBoardComplaint = JSON.parseObject(content, BackschoolBoardComplaint.class);//将客户端传来的参数序列化

            if (StringUtils.isBlank(String.valueOf(backschoolBoardComplaint.getBussId()))) {
                message.setMsg("举报业务编号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(String.valueOf(backschoolBoardComplaint.getBoardId()))) {
                message.setMsg("举报花絮编号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(String.valueOf(backschoolBoardComplaint.getBussType()))) {
                message.setMsg("举报业务类型不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(String.valueOf(backschoolBoardComplaint.getUserInfoId()))) {
                message.setMsg("举报人编号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(backschoolBoardComplaint.getReason())) {
                message.setMsg("请填写举报原因");
                message.setSuccess(false);
                return;
            }

            schoolServiceMapper.insertBackschoolBoardComplaint(backschoolBoardComplaint);//插入返校计划花絮举报

            if (backschoolBoardComplaint.getImageList() != null && !backschoolBoardComplaint.getImageList().isEmpty()) {
                for (String s : backschoolBoardComplaint.getImageList()) { //遍历举报图片
                    com.cy.core.share.entity.File file = new com.cy.core.share.entity.File();         //创建文件对象
                    file.setFileGroup(String.valueOf(backschoolBoardComplaint.getId())); //保存设置举报id
                    if ("20".equals(backschoolBoardComplaint.getBussType())) { //确定举报业务类型
                        file.setBussType("70");
                    } else {
                        file.setBussType("80");
                    }
                    file.setPicUrl(s);
                    file.preInsert();
                    fileMapper.insert(file);
                }
            }
            message.setMsg("举报已成功提交");
            message.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //删除返校计划花絮
    public void deleteBackschoolBoard(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> map = JSON.parseObject(content, Map.class);//将客户端传来的参数序列化
        String userId = (String) map.get("userId");
        String boardId = (String) map.get("boardId");
        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入用户ID");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(boardId)) {
            message.setMsg("未传入花絮ID");
            message.setSuccess(false);
            return;
        }

        BackschoolBoard backschoolBoard = new BackschoolBoard();//创建返校计划花絮对象
        backschoolBoard.setUserInfoId(userId);//设置用户id
        backschoolBoard.setId(Long.parseLong(boardId));//设置花絮id
        BackschoolBoard backBoard = schoolServiceMapper.getbackschoolBoard(backschoolBoard);

        if (backBoard == null) {
            message.setMsg("該花絮不存在，或已被刪除");
            message.setSuccess(false);
            return;
        }

        if (!backBoard.getUserInfoId().equals(userId)) {
            message.setMsg("不能刪除非本人創建的花絮");
            message.setSuccess(false);
            return;
        }

        schoolServiceMapper.deleteBcakschoolBoard(backschoolBoard);
        message.setMsg("成功刪除花絮");
        message.setSuccess(true);
    }
}
