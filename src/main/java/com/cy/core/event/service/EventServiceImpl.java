package com.cy.core.event.service;


import java.text.DateFormat;
import java.util.*;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Maps;
import com.cy.base.entity.Message;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.service.AlumniService;
import com.cy.core.association.dao.AssociationMapper;
import com.cy.core.association.dao.AssociationMemberMapper;
import com.cy.core.association.entity.Association;
import com.cy.core.association.entity.AssociationMember;
import com.cy.core.association.service.AssociationMemberService;
import com.cy.core.association.service.AssociationService;
import com.cy.core.chatGroup.dao.ChatGroupMapper;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dicttype.entity.DictType;
import com.cy.core.event.entity.*;
import com.cy.core.mobevent.dao.MobEventMapper;
import com.cy.core.mobevent.entity.CyEvent;
import com.cy.core.mobevent.entity.CyEventSign;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.Global;
import com.cy.util.Collections3;
import com.cy.util.DateUtils;
import com.cy.util.UserUtils;
import com.cy.util.easemob.comm.utils.EasemobUtils;
import com.cy.util.image.QrCodeUtils;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mysql.fabric.xmlrpc.base.Data;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import org.apache.poi.ss.formula.functions.Even;
import org.apache.struts2.ServletActionContext;
import org.apache.xmlbeans.impl.common.ValidatorListener;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.cy.base.entity.DataGrid;
import com.cy.core.event.dao.EventMapper;

import javax.servlet.http.HttpServletRequest;

@Service("eventService")
public class EventServiceImpl implements EventService {
    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private MobEventMapper mobEventMapper;

    @Autowired
    private AssociationService associationService;

    @Autowired
    private AssociationMemberService associationMemberService;

    @Autowired
    private AssociationMapper associationMapper;

    @Autowired
    private AssociationMemberMapper associationMemberMapper;

    @Autowired
    private AlumniService alumniService;

    @Autowired
    private ChatGroupMapper chatGroupMapper;


    public DataGrid<Event> dataGrid(Map<String, Object> map) {
        DataGrid<Event> dataGrid = new DataGrid<Event>();
        long total = eventMapper.count(map);
        dataGrid.setTotal(total);
        
        int pg=Integer.valueOf(map.get("page").toString())-1;
		int rws=Integer.valueOf(map.get("rows").toString());
		
		int start = pg*rws;		
		
		map.put("start", start);
		map.put("rows", rws);
		
        List<Event> list = eventMapper.query(map);
        fillEventList(list);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public Event getById(String id) {
        Event event = eventMapper.getById(id);
        fillEvent(event);
        if(event != null && StringUtils.isNotBlank(event.getContent())) {
            event.setContent(EditorUtils.changeSrcFromRelativeToAbsolute(event.getContent()));
        }
       /* String qrCodeUrl = event.getQrCodeUrl();
        try {
            if (StringUtils.isBlank(qrCodeUrl) && qrCodeUrl == null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("commend", "409");
                jsonObject.put("eventId", event.getId());
                String jsonStr = jsonObject.toJSONString();
                String value = "http://www.cymobi.com?qr=" + jsonStr;
                String qrCodeByValue = QrCodeUtils.createQrCodeByValue(value);
                event.setQrCodeUrl(qrCodeByValue);
            }
        }catch (Exception e){
          e.printStackTrace();
        }
        event.preUpdate();
        eventMapper.update(event);*/
        return event;
    }


    public void save(Event event) {
        if (event == null)
            throw new IllegalArgumentException("event cannot be null!");
        if(StringUtils.isNotBlank(event.getContent())) {
            event.setContent(EditorUtils.edictorContent(event.getContent()));
        }

        event.preInsert();
       /* JSONObject jsonObject = new JSONObject() ;
        jsonObject.put("commend","409") ;
        jsonObject.put("eventId",event.getId()) ;
        String jsonStr = jsonObject.toJSONString() ;
        try {
            String value = "http://www.cymobi.com?qr="+ jsonStr ;
            String url = QrCodeUtils.createQrCodeByValue(value) ;
            if(StringUtils.isNotBlank(url)) {
                event.setQrCodeUrl(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        eventMapper.add(event);
    }


    public void update(Event event) {
        if (event == null)
            throw new IllegalArgumentException("event cannot be null!");
        if(StringUtils.isNotBlank(event.getContent())) {
            event.setContent(EditorUtils.edictorContent(event.getContent()));
        }

        eventMapper.update(event);
        eventMapper.resetNotification(event);
    }


    public void delete(String ids) {
        String[] array = ids.split(",");
        List<String> list = new ArrayList<String>();
        for (String id : array)
        {
            list.add(id);
            //删除返校计划同时删除群
            // 根据group_id查询该群组
            Event event = eventMapper.getById(id);
            ChatGroup group = chatGroupMapper.queryChatGroupById(event.getGroupId());
            Map<String, String> map = new HashMap<String, String>();
            map.put("groupId",event.getGroupId());
            chatGroupMapper.updateDelFlag(event.getGroupId());
            chatGroupMapper.updateGroupUserDelFlag(map);
            if (group != null) {
                EasemobUtils.deleteChatGroups(group.getEasemobGroupId());
            }
        }
        eventMapper.delete(list);
        eventMapper.deleteBussAuthority(list);
    }

    public void audit(Event event) {
        if (event == null)
            throw new IllegalArgumentException("event cannot be null!");

        eventMapper.audit(event);

    }


    public DataGrid<SignUserProfile> dataGridForSignUser(Map<String, Object> map) {
        DataGrid<SignUserProfile> dataGrid = new DataGrid<SignUserProfile>();
        long total = eventMapper.countSignUser(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<SignUserProfile> list = eventMapper.querySignUser(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public DataGrid<EventBoard> dataGridForEventBoard(Map<String, Object> map) {
        DataGrid<EventBoard> dataGrid = new DataGrid<EventBoard>();
        long total = eventMapper.countEventBoard(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<EventBoard> list = eventMapper.queryEventBoard(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public DataGrid<EventBoardComment> dataGridForEventBoardComment(Map<String, Object> map) {
        DataGrid<EventBoardComment> dataGrid = new DataGrid<EventBoardComment>();
        long total = eventMapper.countEventBoardComment(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<EventBoardComment> list = eventMapper.queryEventBoardComment(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public DataGrid<BoardComplaint> dataGridForComplaint(Map<String, Object> map) {
        DataGrid<BoardComplaint> dataGrid = new DataGrid<BoardComplaint>();
        long total = eventMapper.countComplaint(map);
        dataGrid.setTotal(total);
                
        int pg=Integer.valueOf(map.get("page").toString())-1;
        int rows=Integer.valueOf(map.get("rows").toString());
        int start =pg * rows;     
        
        //int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows"); 
        
        map.put("start", start); 
        map.put("rows", rows); 
        
        List<BoardComplaint> list = eventMapper.queryComplaint(map);
        
        dataGrid.setRows(list);
        
        return dataGrid;
        
    }

   

    public int handleBoardStatus(Map<String, Object> map) {
      return  eventMapper.handleBoardStatus(map);
    }


    public DataGrid<Complaint> dataGridForEventBoardComplaint(Map<String, Object> map) {
        DataGrid<Complaint> dataGrid = new DataGrid<Complaint>();
        long total = eventMapper.countEventBoardComplaint(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Complaint> list = eventMapper.queryEventBoardComplaint(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public void undoDelete(String id) {
        eventMapper.undoDelete(id);
    }

    public List<String> getPicByBoardId(long boardId) {
        return eventMapper.getPicByBoardId(boardId);
    }
    
    public String getCommentByBoardId(long boardId) {
        return eventMapper.getCommentByBoardId(boardId);
    }
    

    public String getRegionOfUser(long userId) {
        return eventMapper.getRegionOfUser(userId);
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


    /******鹏飞 方法 start******/

    /******鹏飞 方法 end******/


    /***********************************************************************
     *
     * 【校友活动】相关API（以下区域）
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
     * 查询校友活动列表
     * @param message
     * @param content
     */
    public void findEventList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String) map.get("accountNum");
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimni", "1");
        }

        //判斷是否查詢与我相关活动
        if (map.get("flag") != null && ("2".equals((String)map.get("flag")) || "3".equals((String)map.get("flag")))) {
            if(map.get("currUserId") == null || StringUtils.isBlank((String)map.get("currUserId")) ) {
                message.init(false,"查询我的活动或我参与的活动时,用户编号不能为空",null);
                return ;
            }
        } else {
            map.put("auditStatus", '1');
            if (map.get("eventStatusScope") == null || StringUtils.isBlank((String) map.get("eventStatusScope"))) {
                map.put("eventStatusScope", "50,60,70,80,90");
            }
        }

        List<Event> list = new ArrayList<>();
        List<Association> associationList = Lists.newArrayList();
        if (StringUtils.isNotBlank(accountNum)){
            //获取用户加入的社团
            Map<String, Object> map2 = new HashMap<>();
            map2.put("userAccount", accountNum);
            map2.put("inType","20");
            map2.put("isNoLimit", "1");
            associationList = associationMapper.selectAssociation(map2);

        }
        //社团活动
        if("99".equals(map.get("type")) && StringUtils.isBlank((String)map.get("associationId"))){

            if(associationList != null && associationList.size() > 0){
                for(Association as:associationList){
                    map.put("associationId", as.getId());
                    list.addAll(eventMapper.query(map));
                    fillEventList(list);
                }
            }else{
                message.init(false, "未加入社团",null);
                return;
            }
        }else{
            /**
             * accountNum 存在 查询范围(总会、社团、个人所属分会)
             * accountNum 不存在 查询范围(总会、社团公开)
            */
            String alumniIds = "1";
            if (StringUtils.isNotBlank(accountNum)){
                //获取用户加入的分会
                List<Alumni> alumniList = alumniService.findAlumniListFromAccountNum(accountNum);
                if (alumniList !=null && !alumniList.isEmpty()){
                    alumniIds += ","+ Collections3.extractToString(alumniList,"alumniId",",");
                }
                if (associationList !=null && !associationList.isEmpty()){
                    alumniIds += ","+ Collections3.extractToString(associationList,"id",",");
                }
            }
            map.put("alumniIds",alumniIds);
            list.addAll(eventMapper.query(map));
            fillEventList(list);
        }


        for(int i =0; i<list.size(); i++){
            if(StringUtils.isNotBlank(accountNum)){
                CyEventSign eventSign = new CyEventSign();
                eventSign.setEventId(list.get(i).getId());
                eventSign.setUserInfoId(accountNum);

                long signCount = mobEventMapper.countEventSign(eventSign);

                if(signCount > 0){
                    list.get(i).setIsJoined("1");
                }else{
                    list.get(i).setIsJoined("0");
                }
            }else{
                list.get(i).setIsJoined("0");
            }
        }



        DataGrid<Event> dataGrid = new DataGrid<Event>();
        long total = eventMapper.count(map);
        dataGrid.setTotal(total);
        dataGrid.setRows(list);

        message.init(true ,"查询成功",dataGrid,null);
    }

    /******鹏飞 接口 start******/

    /**
     * 个人活动接口
     */
    public void savePersonalEvent(Message message, String content){
        try{
            if(StringUtils.isBlank(content)){
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Event event = JSON.parseObject(content, Event.class);

            if(StringUtils.isBlank(event.getUserInfoId())){
                message.setMsg("账号不能为空");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(event.getTitle())){
                message.setMsg("标题不能为空");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(event.getPlace())){
                message.setMsg("活动地点不能为空");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(event.getContent())){
                message.setMsg("活动简介不能为空");
                message.setSuccess(false);
                return;
            }
            if(event.getSignupStartTime() == null){
                message.setMsg("报名开始日期不能为空");
                message.setSuccess(false);
                return;
            }else if(event.getSignupStartTime().length() > 19){
                event.setSignupStartTime(event.getSignupStartTime().substring(0, 19));
            }
            if(event.getSignupEndTime() == null){
                message.setMsg("报名截止日期不能为空");
                message.setSuccess(false);
                return;
            }else if(event.getSignupEndTime().length() > 19){
                event.setSignupEndTime(event.getSignupEndTime().substring(0, 19));
            }
            if(event.getStartTime() == null){
                message.setMsg("活动开始日期不能为空");
                message.setSuccess(false);
                return;
            }else if(event.getStartTime().length() > 19){
                event.setStartTime(event.getStartTime().substring(0, 19));
            }
            if(event.getEndTime() == null){
                message.setMsg("活动结束日期不能为空");
                message.setSuccess(false);
                return;
            }else if(event.getEndTime().length() > 19){
                event.setEndTime(event.getEndTime().substring(0, 19));
            }


            Date startSign = DateUtils.parseDate(event.getSignupStartTime());
            Date endSign = DateUtils.parseDate(event.getSignupEndTime());
            Date start = DateUtils.parseDate(event.getStartTime());
            Date end = DateUtils.parseDate(event.getEndTime());

            if(!(new Date().getTime()<=startSign.getTime())){
                message.init(false, "确保报名开始时间晚于当前时间",null);
                return;
            }
            if(!(startSign.getTime()<endSign.getTime())){
                message.init(false, "确保报名结束时间晚于报名开始时间",null);
                return;
            }
            if(!(endSign.getTime() < start.getTime())){
                message.init(false, "确保活动开始时间晚于报名结束时间",null);
                return;
            }
            if(!(start.getTime() < end.getTime())){
                message.init(false, "确保活动结束时间晚于活动开始时间",null);
                return;
            }


            event.preInsert();

            //收费
            if(StringUtils.isBlank(event.getCost()) || event.getCost().equals("0")){
                event.setIsFree("0");
                event.setCost("0");
            }else{
                event.setIsFree("1");
            }
            //是否审核个人活动（0；否，1：是）

            if("1".equals(Global.is_audit_personal_activity)){
                event.setAuditStatus(0); // 默认就是0(待审核)
            }else{
                event.setAuditStatus(1);
            }

            //设置为个人活动类型
            event.setType(9);
            //设置活动状态
            event.setStatus(0);
            //生成签到码
            if(event.isNeedSignIn()) {
                event.setSignInCode(getSignCode());
            }


            BussAuthority bussAuthority = new BussAuthority();
            bussAuthority.preInsert();
            //活动对谁可见
            if(StringUtils.isNotBlank(event.getPerAlumniId()) && !event.getPerAlumniId().equals("1") && !event.getPerAlumniId().equals("0")){
                bussAuthority.setAlumniId(event.getPerAlumniId());
                event.setDept_id(event.getPerAlumniId());
            }else{
                bussAuthority.setAlumniId("1");
                event.setDept_id("1");
            }

            eventMapper.add(event);
            String eventId = event.getId();
            //创建者自动报名
            CyEventSign eventSign = new CyEventSign();
            eventSign.setEventId(eventId);
            eventSign.setUserInfoId(event.getUserInfoId());
            eventSign.setSignupTime(new Date());
            eventSign.setViewNotification(1);
            mobEventMapper.insertEventSign(eventSign);
            try {
                //String eventid=event.getId();
                String title=event.getTitle();
                String alumniId= event.getDept_id();
                String accountNum=event.getUserInfoId();
                HttpServletRequest request = ServletActionContext.getRequest();;
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
                String eventUrl = basePath +"mobile/services/activity/"+ "detail.html?eventId=" + eventId+"&accountNum="+accountNum + "&sourceFlag=0";
                //  System.out.println(eventUrl+"=====================================");
                System.out.println(PushUtils.PUSH_INSERT_EVEVT);
                PushUtils.pushInsertEvent(alumniId,title,eventUrl,eventId,accountNum);

            } catch (Exception e ) {
                e.printStackTrace();
            }
            bussAuthority.setBussId(eventId);
            bussAuthority.setBussType("10");
            bussAuthority.setStatus("10");
            eventMapper.saveBussAuthority(bussAuthority);
            message.setObj(event);
            message.setMsg("已提交活动申请");
            message.setSuccess(true);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建社团活动
     * @param message
     * @param content
     */
    public void saveAssociationEvent(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Event event = JSON.parseObject(content, Event.class);
            if(StringUtils.isBlank(event.getUserInfoId())){
                message.init(false, "账号不能为空", null);
                return;
            }
            if(StringUtils.isBlank(event.getAssociationId())){
                message.init(false, "社团ID不能为空", null);
                return;
            }

            Association association = associationService.getAssociationById(event.getAssociationId());

            if(association == null){
                message.init(false, "社团不存在", null);
                return;
            }

            AssociationMember associationMember = associationMemberService.getByAccountNum(event.getUserInfoId(), event.getAssociationId());

            if(associationMember == null || !event.getAssociationId().equals(associationMember.getAssociationId())){
                message.init(false, "您不存在于该社团中", null);
                return;
            }

            if(!"1".equals(associationMember.getPosition()) || !"2".equals(associationMember.getPosition())){
                message.init(false, "不是社长不能创建社团活动", null);
                return;
            }

            if(StringUtils.isBlank(event.getTitle())){
                message.init(false, "标题不能为空", null);
                return;
            }
            if(StringUtils.isBlank(event.getPlace())){
                message.init(false, "活动地点不能为空", null);
                return;
            }
            if(StringUtils.isBlank(event.getContent())){
                message.init(false, "活动简介不能为空", null);
                return;
            }
            if(event.getSignupStartTime() == null){
                message.init(false, "报名开始日期不能为空", null);
                return;
            }else if(event.getSignupStartTime().length() > 19){
                event.setSignupStartTime(event.getSignupStartTime().substring(0, 19));
            }
            if(event.getSignupEndTime() == null){
                message.init(false, "报名截止日期不能为空", null);
                return;
            }else if(event.getSignupEndTime().length() > 19){
                event.setSignupEndTime(event.getSignupEndTime().substring(0, 19));
            }
            if(event.getStartTime() == null){
                message.init(false, "活动开始日期不能为空", null);
                return;
            }else if(event.getStartTime().length() > 19){
                event.setStartTime(event.getStartTime().substring(0, 19));
            }
            if(event.getEndTime() == null){
                message.init(false, "活动结束日期不能为空", null);
                return;
            }else if(event.getEndTime().length() > 19){
                event.setEndTime(event.getEndTime().substring(0, 19));
            }


            Date startSign = DateUtils.parseDate(event.getSignupStartTime());
            Date endSign = DateUtils.parseDate(event.getSignupEndTime());
            Date start = DateUtils.parseDate(event.getStartTime());
            Date end = DateUtils.parseDate(event.getEndTime());

            if(!(new Date().getTime()<=startSign.getTime()&&startSign.getTime()<endSign.getTime() && endSign.getTime() <= start.getTime() && start.getTime() < end.getTime())){
                message.init(false, "确保活动时间晚于报名时间、结束时间晚于开始时间",null);
                return;
            }
            event.preInsert();

            //收费
            if(StringUtils.isBlank(event.getCost()) || event.getCost().equals("0")){
                event.setIsFree("0");
                event.setCost("0");
            }else{
                event.setIsFree("1");
            }

            //设置为社团活动类型
            event.setType(99);
            //设置为待审核状态
            event.setAuditStatus(1);
            //设置活动状态
            event.setStatus(0);
            //生成签到码
            if(event.isNeedSignIn()) {
                event.setSignInCode(getSignCode());
            }

            //将社团ID存到deptId中
            event.setDept_id(event.getAssociationId());
            eventMapper.add(event);
            String eventId = event.getId();
            try {
                String title=event.getTitle();
                String associationId= event.getDept_id();
                String accountNum=event.getUserInfoId();
                HttpServletRequest request = ServletActionContext.getRequest();;
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
                String eventUrl = basePath +"mobile/services/activity/"+ "detail.html?eventId=" + eventId+"&accountNum="+accountNum + "&sourceFlag=0";
                PushUtils.pushAssociationEvent(associationId,title,eventUrl,eventId,accountNum);

            } catch (Exception e ) {
                e.printStackTrace();
            }
            message.init(true, "成功创建社团活动", null);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 活动详情接口
     */
    public void showEventInfo(Message message, String content){
        try{
            if(StringUtils.isBlank(content)){
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            
            Map<String, String> map = JSON.parseObject(content, Map.class);
            if(StringUtils.isBlank(map.get("eventId"))){
                message.setMsg("活动Id为空");
                message.setSuccess(false);
                return;
            }

            /*if(StringUtils.isBlank(map.get("userId"))){
                message.setMsg("用户Id为空");
                message.setSuccess(false);
                return;
            }*/
            Event event = eventMapper.getById(map.get("eventId"));
            fillEvent(event);
            if(event == null){
                message.setMsg("活动不存在");
                message.setSuccess(false);
                return;
            }

            int pageView = 0;
            if(StringUtils.isNotBlank(event.getPageView())){
                pageView = Integer.parseInt(event.getPageView());
            }
            pageView+=1;
            event.setPageView(String.valueOf(pageView));
           /* String qrCodeUrl = event.getQrCodeUrl();
            if (StringUtils.isBlank(qrCodeUrl)&& qrCodeUrl==null){
                JSONObject jsonObject = new JSONObject() ;
                jsonObject.put("commend","409") ;
                jsonObject.put("eventId",event.getId()) ;
                String jsonStr = jsonObject.toJSONString() ;
                String value = "http://www.cymobi.com?qr="+ jsonStr ;
                String qrCodeByValue = QrCodeUtils.createQrCodeByValue(value);
                event.setQrCodeUrl(qrCodeByValue);
            }*/
            event.preUpdate();
            eventMapper.update(event);
            Map<String,Object> memberMap = Maps.newHashMap() ;
            memberMap.put("eventId",map.get("eventId")) ;
            memberMap.put("isLimit","1") ;
            memberMap.put("start",0) ;
            memberMap.put("rows",4) ;
            List<Map<String, Object>> members =  eventMapper.getMembers(memberMap);

            Map<String, Object> eventInfo = new HashMap<>();

            eventInfo.put("isJoin", 0);
            eventInfo.put("isOwner",false);
            //未登录用户
            if("1".equals(event.getNeedAuth())){
                eventInfo.put("needAuthButNot", true);
            }else{
                eventInfo.put("needAuthButNot", false);
            }

            if(StringUtils.isNotBlank(map.get("userId")))
            {
                UserProfile userProfile = userProfileMapper.selectByAccountNum(map.get("userId"));

                if(userProfile == null){
                    message.setMsg("当前用户不存在");
                    message.setSuccess(false);
                    return;
                }
                
                for(int i = 0; i < members.size(); i++){
                    if(String.valueOf(members.get(i).get("accountNum")).equals(map.get("userId"))){
                        String isSignIn = String.valueOf(members.get(i).get("isSignIn"));
                        if (isSignIn.equals("0")){
                            eventInfo.put("isJoin", 1);
                        }
                        if (isSignIn.equals("1")){
                            eventInfo.put("isJoin",3);
                        }
                        break;
                    }
                }
                
                if(StringUtils.isNotBlank(event.getUserInfoId()) && event.getUserInfoId().equals(map.get("userId"))){
                    eventInfo.put("isOwner",true);
                }
                
                if(StringUtils.isNotBlank(userProfile.getBaseInfoId())){
                    eventInfo.put("needAuthButNot", false);
                }
                
            }


            String signupStartTime = event.getSignupStartTime();
            String signupEndTime = event.getSignupEndTime();


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            long newTime = TimeZoneUtils.getDate().getTime();

            long start = format.parse(signupStartTime).getTime();
            long end = format.parse(signupEndTime).getTime();

            if (newTime>start && end>newTime){
                eventInfo.put("applyState",1);
            }else if (newTime>end){
                eventInfo.put("applyState",2);
            }else if (start>newTime){
                eventInfo.put("applyState",0);
            }

            long s = format.parse(event.getStartTime()).getTime();
            long e = format.parse(event.getEndTime()).getTime();

            if (newTime>s && e>newTime){
                eventInfo.put("eventState",1);
            }else if (newTime>e){
                eventInfo.put("eventState",2);
            }else if (s>newTime){
                eventInfo.put("eventState",0);
            }

            eventInfo.put("id", event.getId());
            eventInfo.put("title",event.getTitle());
            eventInfo.put("content", event.getContent());
            eventInfo.put("place", event.getPlace());
            eventInfo.put("type", event.getType());
            eventInfo.put("associationName", event.getAssociationName());
            eventInfo.put("associationId", event.getAssociationId());
            eventInfo.put("signupStartTime", event.getSignupStartTime());
            eventInfo.put("signupEndTime", event.getSignupEndTime());
            eventInfo.put("startTime", event.getStartTime());
            eventInfo.put("endTime",event.getEndTime());
            eventInfo.put("signCode",event.getSignInCode());
            eventInfo.put("maxPeople", event.getMaxPeople());
            eventInfo.put("minPeople", event.getMinPeople());
            eventInfo.put("pic",event.getPic());
            eventInfo.put("picUrl",event.getPicUrl());
            eventInfo.put("pic_xd",event.getPic_xd());
            eventInfo.put("signupNum",event.getSignupNum());
            eventInfo.put("members", members);
            eventInfo.put("userProfile", event.getUserProfile());
            eventInfo.put("user", event.getUser());
            eventInfo.put("eventStatus",event.getEventStatus());
            eventInfo.put("nowStatus",event.getNowStatus());
            eventInfo.put("deptId",event.getDept_id());
            eventInfo.put("pageView",event.getPageView());
            eventInfo.put("signCode", event.getSignInCode() );
            eventInfo.put("alumniName", event.getAlumniName());
            eventInfo.put("needAuth", event.getNeedAuth());
            eventInfo.put("cost", event.getCost());
            eventInfo.put("costMemo", event.getCostMemo());
            eventInfo.put("groupId", event.getGroupId());
            eventInfo.put("groupEasemobId", event.getGroupEasemobId());
            eventInfo.put("groupTitle", event.getGroupTitle());
            
            message.setMsg("获取详情成功");
            message.setSuccess(true);
            message.setObj(eventInfo);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    /**
     * 获取活动报名人列表接口
     */
    public void showEventMembers(Message message, String content){
        try{
            if(StringUtils.isBlank(content)){
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            if(StringUtils.isBlank(map.get("eventId"))){
                message.setMsg("活动Id为空");
                message.setSuccess(false);
                return;
            }
            Map<String,Object> memberMap = Maps.newHashMap() ;
            memberMap.put("eventId",map.get("eventId")) ;
            memberMap.put("isLimit","1") ;
            memberMap.put("start",0) ;
            memberMap.put("rows",4) ;
            List<Map<String, Object>> members =  eventMapper.getMembers(memberMap);
            for(int i = 0; i < members.size(); i++){
                String pic = (String)members.get(i).get("picture");
                String picture_xd = (String)members.get(i).get("picture");
                if(StringUtils.isNotBlank(pic) && pic.indexOf("http") < 0 ){
                    pic = Global.URL_DOMAIN + pic;
                }
                if(StringUtils.isNotBlank(picture_xd) && pic.indexOf("http") < 0 ){
                    picture_xd = Global.URL_DOMAIN + picture_xd;
                }else{
                    picture_xd=pic;
                }
                members.get(i).put("picture", pic);
                members.get(i).put("picture_xd", picture_xd);
            }
            message.setMsg("获取报名了列表成功");
            message.setSuccess(true);
            message.setObj(members);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取活动类型列表接口
     */
    public void showEventTypeList(Message message, String content){
        List<DictType> list = (List<DictType>) CacheUtils.get("dicts");
        List<Dict> dictList = new ArrayList<Dict>();
        if(list!=null&&list.size()>0){
            for (DictType dictType : list) {
                if (dictType.getDictTypeName().equals("活动类别")) {
                    dictList = dictType.getList();
                }
            }
            if(dictList!=null && list.size()>0){
                message.setMsg("获取活动类型成功");
                message.setSuccess(true);
                message.setObj(dictList);
            }else {
                message.setMsg("活动类型列表空");
                message.setSuccess(false);
            }
        }else{
            message.setMsg("数据字典空");
            message.setSuccess(false);
        }


    }

    /**
     * 加入活动接口
     */
    public void joinEvent(Message message, String content){
        try{
            if(StringUtils.isBlank(content)){
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, String>  map = JSON.parseObject(content, Map.class);
            String userId = map.get("userId");
            String eventId = map.get("eventId");
            if(StringUtils.isBlank(userId)){
                message.setMsg("请传入用户ID");
                message.setSuccess(false);
                return;
            }
            if(StringUtils.isBlank(eventId)){
                message.setMsg("请传入活动Id");
                message.setSuccess(false);
                return;
            }

            Event tmp = getById(eventId);
            if(tmp.getType() == 99){
                //社团活动需要验证是否社团成员
                Map<String, Object> map1 = new HashMap<>();
                map1.put("associationId", tmp.getDept_id());
                map1.put("status", "20");
                map1.put("isNoLimit", "1");
                List<AssociationMember> listMember =associationMemberMapper.selectAssociationMember(map1);

                Boolean isIn = false;
                for(AssociationMember am:listMember){
                    if(am.getAccountNum().equals(userId)){
                        isIn = true;
                        break;
                    }
                }
                if(!isIn){
                    message.init(false,"您不在该社团中", null);
                    return;
                }
            }else{
                if(1 == tmp.getType()){
                   //分会的限制
                }
            }

            CyEventSign eventSign = new CyEventSign();
            eventSign.setEventId(eventId);
            eventSign.setUserInfoId(userId);


            long signCount = mobEventMapper.countEventSign(eventSign);

            if(signCount > 0){
                message.setMsg("您已参与该活动请不要重复报名");
                message.setSuccess(false);
                return;
            }

            eventSign.setUserInfoId(null);

            long currentSignCount = mobEventMapper.countEventSign(eventSign);

            CyEvent event = mobEventMapper.getEventById(eventId);

            long signMaxCount = 0;

            if(event != null)
            {
                signMaxCount = event.getMaxPeople();
            }

            if(signCount == 0 && (currentSignCount < signMaxCount || signMaxCount == 0))
            {
                eventSign.setUserInfoId(userId);
                eventSign.setSignupTime(new Date());
                eventSign.setViewNotification(1);

                mobEventMapper.insertEventSign(eventSign);
                message.setMsg("报名成功");
                message.setSuccess(true);
            }else{
                message.setMsg("报名失败，人员数量已达上限");
                message.setSuccess(true);
            }


        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除活动接口
     */
    public void removeEvent(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String userId = map.get("userId");
            String eventId = map.get("eventId");

            if (StringUtils.isBlank(userId)) {
                message.setMsg("请传入用户ID");
                message.setSuccess(false);
                return;
            }

            if (StringUtils.isBlank(eventId)) {
                message.setMsg("请传入活动Id");
                message.setSuccess(false);
                return;
            }


            Event event = eventMapper.getById(eventId);
            fillEvent(event);
            if(event==null){
                message.setMsg("活动不存在，或已被删除");
                message.setSuccess(false);
                return;
            }

            if(!event.getUserInfoId().equals(userId)){
                message.setMsg("非创建者无法删除该活动");
                message.setSuccess(false);
                return;
            }

            map.put("delFlag","1");
            eventMapper.removeEvent(map);
            message.setMsg("成功删除活动");
            message.setSuccess(true);


        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取活动报名人信息接口
     */
    public void getInfoOfSigner(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }

            Map<String, String> map = JSON.parseObject(content, Map.class);
            String userId = map.get("userId");
            String eventId = map.get("eventId");

            if (StringUtils.isBlank(userId)) {
                message.setMsg("请传入用户ID");
                message.setSuccess(false);
                return;
            }

            if (StringUtils.isBlank(eventId)) {
                message.setMsg("请传入活动Id");
                message.setSuccess(false);
                return;
            }

            CyEventSign eventSign = new CyEventSign();
            eventSign.setEventId(eventId);
            eventSign.setUserInfoId(userId);


            long signCount = mobEventMapper.countEventSign(eventSign);

            if(signCount == 0 ){
                message.setMsg("当前用户不在该活动中");
                message.setSuccess(false);
                return;
            }

            UserProfile userInfo = userProfileMapper.selectByAccountNum(userId);

            Map<String, String> map2 = new HashMap<String, String>();

            if(userInfo != null) {
                map2.put("accountNum", StringUtils.isBlank(userInfo.getAccountNum()) ? "" : userInfo.getAccountNum());// 用户编号
                map2.put("userName", StringUtils.isBlank(userInfo.getName()) ? "" : userInfo.getName());// 姓名
                map2.put("sex", StringUtils.isBlank(userInfo.getSex()) ? "" : userInfo.getSex());// 性别
                map2.put("profession", StringUtils.isBlank(userInfo.getProfession()) ? "" : userInfo.getProfession());// 行业
                map2.put("address", StringUtils.isBlank(userInfo.getAddress()) ? "" : userInfo.getAddress());// 地点
                map2.put("hobby", StringUtils.isBlank(userInfo.getHobby()) ? "" : userInfo.getHobby());// 兴趣
                map2.put("picture", StringUtils.isBlank(userInfo.getPicture()) ? "" : userInfo.getPicture()); // 用户图片
                map2.put("pictureUrl",StringUtils.isBlank(userInfo.getPictureUrl())?"":userInfo.getPictureUrl());
                map2.put("baseInfoId", userInfo.getBaseInfoId());
                map2.put("workUtil", StringUtils.isBlank(userInfo.getWorkUtil()) ? "" : userInfo.getWorkUtil());
                map2.put("departName", StringUtils.isBlank(userInfo.getClasses()) ? "" : userInfo.getClasses());
            }

            if (map2 == null || map2.isEmpty()) {
                message.setMsg("查不到此用户信息!");
                message.setSuccess(false);
                return;
            } else {
                message.setMsg("查询成功!");
                message.setSuccess(true);
                message.setObj(map2);
                return;
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 退出活动
     * @param message
     * @param content
     */
    public void cancelJoinedEvent(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }

            Map<String, String>  map = JSON.parseObject(content, Map.class);
            String userId = map.get("userId");
            String eventId = map.get("eventId");

            if(StringUtils.isBlank(userId)){
                message.setMsg("请传入用户ID");
                message.setSuccess(false);
                return;
            }

            if(StringUtils.isBlank(eventId)){
                message.setMsg("请传入活动Id");
                message.setSuccess(false);
                return;
            }

            CyEventSign eventSign = new CyEventSign();
            eventSign.setEventId(eventId);
            eventSign.setUserInfoId(userId);


            long signCount = mobEventMapper.countEventSign(eventSign);

            if(signCount <= 0){
                message.setMsg("您不未报名此活动，无需退出");
                message.setSuccess(false);
                return;
            }

            mobEventMapper.cancelEventSign(eventSign);
            message.setMsg("成功退出活动");
            message.setSuccess(true);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /******鹏飞 接口 end******/

    /**
     * 查询是否签到
     */
    public void setIsSing(Message message ,String content){
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String accountNum = map.get("accountNum");
        String eventId = map.get("eventId");
        if(StringUtils.isBlank(accountNum)){
            message.setMsg("请传入用户ID");
            message.setSuccess(false);
            return;
        }
        if(StringUtils.isBlank(eventId)){
            message.setMsg("请传入活动Id");
            message.setSuccess(false);
            return;
        }

        Event event = eventMapper.getById(eventId);
        if(event == null){
            message.setMsg("活动不存在");
            message.setSuccess(false);
            return;
        }

        if(event.getEventStatus() == 80){
            message.setMsg("活动尚未开始，不能签到");
            message.setSuccess(false);
            return;
        }else if(event.getEventStatus() == 70){
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
        Map<String,Object> memberMap = Maps.newHashMap() ;
        memberMap.put("eventId",map.get("eventId")) ;
        memberMap.put("isLimit","1") ;
        memberMap.put("start",0) ;
        memberMap.put("rows",4) ;
        List<Map<String, Object>> members =  eventMapper.getMembers(memberMap);

        boolean isIn = false;
        if( members != null && members.size() > 0){
            for (Map<String, Object> member : members) {
                String accountNum1 = String.valueOf(member.get("accountNum"));
                if (accountNum1.equals(accountNum)) {
                    //设置签到
                    String isSignIn = String.valueOf(member.get("isSignIn"));
                    if(StringUtils.isNotBlank(isSignIn) && isSignIn.equals("1")){
                        message.setMsg("已签到，无需重复签到");
                        message.setSuccess(false);
                        return;
                    }
                    isIn = true;
                    Map<String, String> setSignMap = new HashMap<>();
                    setSignMap.put("accountNum", accountNum);
                    setSignMap.put("eventId", eventId);
                    eventMapper.setIsSignIn(setSignMap);
                }
            }
        }
        if(!isIn){
            if(event.getMaxPeople() > 0 && event.getMaxPeople() <= event.getSignupNum()){
                message.setMsg("已达活动人员上限");
                message.setSuccess(false);
                return;
            }
            Map<String, Object> signMap = new HashedMap();
            signMap.put("userId", accountNum);
            signMap.put("eventId",eventId);
            signMap.put("viewNotification", 1 );
            signMap.put("isSignIn", 1 );
            Date dateTmp = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            signMap.put("signinTime", sdf.format(dateTmp));
            eventMapper.saveSigner(signMap);
        }
        //String userInfoId=event.getUserInfoId();
        HttpServletRequest request = ServletActionContext.getRequest();;
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String eventUrl = (StringUtils.isNotBlank(Global.cy_server_url)?Global.cy_server_url:basePath) +"mobile/services/activity/"+ "detail.html?eventId=" + eventId+"&accountNum="+accountNum + "&sourceFlag=0";
        message.setObj(eventUrl);
        message.setSuccess(true);
        message.setMsg("签到成功");

    }

    /**
     * 填充活动列表信息
     * @param list
     */
    public void fillEventList(List<Event> list) {
        if (list != null && !list.isEmpty()) {
            for (Event event : list) {
                fillEvent(event);
            }
        }
    }
    /**
     * 填充活动信息
     * @param event
     */
    public void fillEvent(Event event) {
        event.setSignupNum(getMemberCount(event.getId(),null));
        event.setSignInNum(getMemberCount(event.getId(),"1"));
        event.setReportCount(getReportCount(event.getId(),null));
        event.setHandlCount(getReportCount(event.getId(),"10"));
    }

    /**
     * 获取活动报名人数和签到人数
     */
    public int getMemberCount(String eventId,String isSignIn) {
        Map<String,String> map = Maps.newHashMap() ;
        map.put("eventId",eventId) ;
        if (StringUtils.isNotBlank(isSignIn)) map.put("isSignIn",isSignIn) ;
        return eventMapper.getMemberCount(map) ;
    }


    /**
     * 获取活动举报数和举报处理数
     */
    public String getReportCount(String eventId ,String handleStatus) {
        Map<String,String> map = Maps.newHashMap() ;
        map.put("eventId",eventId) ;
        if (StringUtils.isNotBlank(handleStatus)) map.put("handleStatus",handleStatus) ;
        return eventMapper.getReportCount(map) ;

    }

    /**
     * 后台创建活动的群ID更新
     */
    public void updateEventGroupId(Map<String,String> map) {
        eventMapper.updateEventGroupId(map);
    }

    /**
     * 手机创建活动的群ID更新
     */
    public void updateMobEventGroupId(Message message ,String content){
        Map<String, String> map = JSON.parseObject(content, Map.class);
        //返校计划ID
        String id = map.get("id");
        String groupId = map.get("groupId");
        if(StringUtils.isBlank(id)){
            message.setMsg("请传入活动Id");
            message.setSuccess(false);
            return;
        }
        if(StringUtils.isBlank(groupId)){
            message.setMsg("请传入活动群ID");
            message.setSuccess(false);
            return;
        }
        map.put("id",id);
        map.put("groupId",groupId);
        eventMapper.updateEventGroupId(map);

        message.setSuccess(true);
        message.setMsg("活动群ID更新成功");

    }
}
