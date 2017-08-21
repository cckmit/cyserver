package com.cy.core.notify.service;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.jpush.JpushClientUtil;
import com.cy.common.utils.request.RequestContent;
import com.cy.common.utils.request.RequestUtil;
import com.cy.core.clientrelease.entity.Json;
import com.cy.core.notify.dao.NotifyMapper;
import com.cy.core.notify.dao.NotifyRecordMapper;
import com.cy.core.notify.entity.Notify;
import com.cy.core.notify.entity.NotifyRecord;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.system.Global;
import com.cy.util.DateUtils;
import com.cy.util.PairUtil;
import com.cy.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("notifyService")
public class NotifyServiceImpl implements NotifyService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public final static String NEWS_FEEDS               ="news_feeds" ;                //新闻订阅；
    public final static String CLASSMATE_AUTHENTICATION ="classmate_authentication" ;  //同班同学班级认证；
    public final static String EXIT_AUTHENTICATION      ="exit_authentication" ;       //同班同学退出班级认证；
    public final static String FRIEND_ADD_REQUEST       ="friend_add_request" ;        //好友添加申请；
    public final static String FRIEND_ADD_AGREE         ="friend_add_agree" ;          //同意好友添加申请；
    public final static String REMOVE_GROUP_ALL         ="remove_group_all" ;          //通知同班校友成员被踢出；
    public final static String REMOVE_GROUP_PERSONAL    ="remove_group_personal" ;     //踢出班级；
    public final static String ALUMNI_JOIN_AGREE        ="alumni_join_agree" ;         //同意分会添加申请；
    public final static String ALUMNI_JOIN_DISAGREE     ="alumni_join_disagree" ;      //不同意分会添加申请；
    public final static String ALUMNI_JOIN_ALL          ="alumni_join_all" ;           //分会下成员加入；
    public final static String DONATION_SUCCESS         ="donation_success" ;          //捐赠成功；
    public final static String GROUP_JOIN_REQUEST       ="group_join_request" ;        //自建群邀请加入请求；
    public final static String OTHER                    ="other" ;                     //自定义推送内容；


    @Autowired
    private NotifyMapper notifyMapper;
    @Autowired
    private UserProfileMapper userMapper;
    @Autowired
    private NotifyRecordMapper notifyRecordMapper ;
    @Autowired
    private UserProfileMapper userProfileMapper;


    public List<Notify> findList(Notify notify) {
        List<Notify> list = notifyMapper.query(notify);
        return list ;
    }

    public DataGrid<Notify> dataGrid(Notify notify) {
        DataGrid<Notify> dataGrid = new DataGrid<Notify>();
        long total = notifyMapper.count(notify);
        dataGrid.setTotal(total);
        int start = (Integer.valueOf(notify.getPage()) - 1) * Integer.valueOf(notify.getRows());

        notify.setStart(String.valueOf(start));
        List<Notify> list = notifyMapper.query(notify);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public Notify getById(String id) {
        return notifyMapper.getById(id);
    }

    public void update(Notify notify) {
        if (notify == null)
            throw new IllegalArgumentException("notify cannot be null!");
        notify.preUpdate();
        notifyMapper.update(notify);
    }

    /**
     * 分页查询推送人
     */
    public DataGrid<UserProfile> dataGridUser(NotifyRecord notifyRecord){
        DataGrid<UserProfile> dataGrid = new DataGrid<UserProfile>();
        long total = notifyRecordMapper.count(notifyRecord);
        dataGrid.setTotal(total);
        int start = (Integer.valueOf(notifyRecord.getPage()) - 1) * Integer.valueOf(notifyRecord.getRows());
        notifyRecord.setStart(String.valueOf(start));
        List<NotifyRecord> list = notifyRecordMapper.query(notifyRecord);
        List<UserProfile> userList = new ArrayList<>();
        for (NotifyRecord notifyRecord1:list){
            UserProfile userProfile = userMapper.selectById(notifyRecord1.getUserId());
            String phoneNum = userProfile.getPhoneNum();

            userProfile.setPhoneNum(phoneNum.replaceAll(phoneNum.substring(3,7),"****"));
            userProfile.setIsRead(notifyRecord1.getReadFlag());
            userProfile.setDate(notifyRecord1.getReadDate());
            userList.add(userProfile);
        }
        dataGrid.setRows(userList);
        return dataGrid;
    }
    /**
     * 创建推送内容信息
     * @param notify
     */
    public void insert(Notify notify) {

        try {
//            notify.preInsert();
            notifyMapper.insert(notify);
            sendNotify(notify);
            // 更新发送接受人记录
            notifyRecordMapper.deleteByNotifyId(notify.getId());
            if (notify.getNotifyRecordList().size() > 0) {
                notifyRecordMapper.insertAll(notify.getNotifyRecordList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ID 删除推送内容信息
     */
    public void delete(String id) {
        Notify notify = new Notify() ;
        notify.setId(id) ;
        notify.preUpdate();
        notifyMapper.delete(notify);
    }
    /**
     * 根据ID 集合删除 推送内容信息
     */
    public void deleteByIdList(String ids) {
        String[] array = ids.split(",");
        List<String> list = Arrays.asList(array);
        notifyMapper.deleteByIdList(list);
    }

    /**
     * 推送所有通知信息
     * @param notify
     */
    @Transactional(readOnly = false)
    public void sendNotify(final Notify notify) throws InterruptedException {

        new RequestUtil(new RequestContent(){
            public void doSomeThing() throws Exception {
                String tempWay = notify.getWay() ;
//                if(StringUtils.isBlank(tempWay) || "40".equals(tempWay)) {
//                    tempWay = notifyRecord.getNotify() != null ? notifyRecord.getNotify().getWay() : "10";
//                }
                boolean success = false ;
                if(StringUtils.isNotBlank(tempWay)) {
                    if ("10".equals(tempWay)) {
                        success = sendFromJpush(notify) ;
                    }
                    /*switch (tempWay) {
                        case "10":
                            // 通过极光推送APP消息
                            success = sendFromJpush(notify) ;
                            break ;
                        case "20":
                            // 通过短信平台推送短信消息
//                                success = sendRecordFromShortMessage(notifyRecord,"3") ;
//								if(success) {
//									notifyRecord.setReadFlag("1");
//									notifyRecord.setReadDate(new Date());
//								}
                            break ;
                    }*/
                }
                if(success){
                    Notify notifyNew = notify ;
                    notifyNew.preUpdate();
                    notifyNew.setStatus("1");
                    notifyMapper.update(notifyNew);
                }

            }
            public void onSuccess(){
                System.out.println("消息推送成功！");
            }
        }).run();

    }
    /**
     * 推送所有通知信息
     * @param way	10：推送APP；20：推送短信；30：不做推送; 40：不确定推送方式
     * @param list
     */
    @Transactional(readOnly = false)
    public void sendNotifyRecords(final String way ,final List<NotifyRecord> list) throws InterruptedException {

        for (int i = 0 ; i < list.size() ; i++) {
            final int j = i ;
            new RequestUtil(new RequestContent(){
                public void doSomeThing() throws Exception {
                    NotifyRecord notifyRecord = list.get(j) ;
                    String tempWay = way ;
                    if(StringUtils.isBlank(tempWay) || "40".equals(tempWay)) {
                        tempWay = notifyRecord.getNotify() != null ? notifyRecord.getNotify().getWay() : "10";
                    }
                    boolean success = false ;
                    if(StringUtils.isNotBlank(tempWay)) {
                        switch (tempWay) {
                            case "0":
                                // 通过极光推送APP消息
                                success = sendRecordFromJpush(notifyRecord) ;
                                break ;
                            case "1":
                                // 通过短信平台推送短信消息
//                                success = sendRecordFromShortMessage(notifyRecord,"3") ;
//								if(success) {
//									notifyRecord.setReadFlag("1");
//									notifyRecord.setReadDate(new Date());
//								}
                                break ;
                        }
                    }
//					if(StringUtils.isNotBlank(notifyRecord.getId())) {
//						notifyRecordDao.update(notifyRecord);
//					}

                }
                public void onSuccess(){
                    System.out.println("消息推送成功！");
                }
            }).run();


        }
    }
//
//    /**
//     * 通知短信信息推送给手机
//     */
//    public boolean sendRecordFromShortMessage(NotifyRecord notifyRecord ,String type) {
//        boolean success = false ;
//        type = StringUtils.isNotBlank(type)?type:"2" ;
//        if(notifyRecord != null && StringUtils.isNotBlank(notifyRecord.getUserId())) {
//            // 获取用户手机号
//            User user = UserUtils.get(notifyRecord.getUserId()) ;
//
//            if(user != null && StringUtils.isNotBlank(user.getMobile())) {
//                String mobile = user.getMobile() ;
//                if(StringUtils.isNotBlank(mobile) && notifyRecord.getNotify() != null) {
//                    if (StringUtils.isBlank(notifyRecord.getNotify().getContent()) && StringUtils.isNotBlank(notifyRecord.getNotify().getId())) {
//
//                        Notify notify = dao.get(notifyRecord.getNotify()) ;
//                        if(notify != null) {
//                            notifyRecord.setNotify(notify);
//                        }
//                    }
//                    if(StringUtils.isNotBlank(notifyRecord.getNotify().getContent())) {
//                        // 发送短信通知
//                        PairUtil<Boolean,Sms> pair = smsService.sendSms(mobile, notifyRecord.getNotify().getContent(),type) ;
//                        success = pair.getOne() ;
//                    }
//                }
//            }
//        }
//        return success ;
//    }
//
    public boolean sendPush(String appKey ,String masterSecret ,boolean iosApns ,Notify notify) {
        JpushClientUtil jpushClientUtil = new JpushClientUtil(appKey,masterSecret,iosApns) ;
        String[] markings = notify.getMarkings().split(",") ;

        Map map = notify.getExtrasMap() ;
        if(map == null) map = new HashMap() ;
        map.put("notifyId",notify.getId()) ;
        map.put("bussId",notify.getBussId()) ;
        map.put("type",notify.getType()) ;
        notify.setExtrasMap(map);
        notifyMapper.update(notify);
        boolean success = false ;
        switch (notify.getChannel()) {
            case "10":  // 通过标签 tag 推送
                success = jpushClientUtil.sendPushByTag(notify.getTitle(),notify.getContent() ,markings ,map) ;
                break ;
            case "20":  // 通过别名 alias 推送
                success = jpushClientUtil.sendPushByAlias(notify.getTitle(),notify.getContent() ,markings ,map) ;
                break ;
        }
        return success ;
    }

    /**
     * 通过极光将通知信息推送给手机APP中
     */
    public boolean sendFromJpush(Notify notify) {
        boolean success = false ;

        if (notify != null && StringUtils.isNotBlank(notify.getChannel()) && StringUtils.isNotBlank(notify.getMarkings())) {
            if(StringUtils.isNotBlank(notify.getContent())) {
//                if(StringUtils.isNotBlank(Global.JPUSH_APP_KEY) && StringUtils.isNotBlank(Global.JPUSH_MASTER_SECRET)) {
//                    sendPush(Global.JPUSH_APP_KEY, Global.JPUSH_MASTER_SECRET, Global.JPUSH_IOS_APNS, notify);
//                }
                if(StringUtils.isNotBlank(Global.SPECIAL_JPUSH_APP_KEY) && StringUtils.isNotBlank(Global.SPECIAL_JPUSH_MASTER_SECRET)) {
                    sendPush(Global.SPECIAL_JPUSH_APP_KEY, Global.SPECIAL_JPUSH_MASTER_SECRET, Global.SPECIAL_JPUSH_IOS_APNS, notify);
                }
            }
        }

        return success ;
    }
    /**
     * 通过极光将通知信息推送给手机APP中
     */
    public boolean sendRecordFromJpush(NotifyRecord notifyRecord) {
        boolean success = false ;

        if (notifyRecord != null && StringUtils.isNotBlank(notifyRecord.getChannel()) && StringUtils.isNotBlank(notifyRecord.getMarking())) {
            if(notifyRecord.getNotify() != null) {
                if (StringUtils.isBlank(notifyRecord.getNotify().getContent()) && StringUtils.isNotBlank(notifyRecord.getNotify().getId())) {

                    Notify notify = notifyMapper.getById(notifyRecord.getNotify().getId()) ;
                    if(notify != null) {
                        notifyRecord.setNotify(notify);
                    }
                }
                if(StringUtils.isNotBlank(notifyRecord.getNotify().getContent())) {
                    Map map = notifyRecord.getNotify().getExtrasMap() ;
                    if(map == null) map = Maps.newHashMap() ;
                    map.put("notifyId",notifyRecord.getNotify().getId()) ;
                    map.put("bussId",notifyRecord.getNotify().getBussId()) ;
                    map.put("type",notifyRecord.getNotify().getType()) ;
                    JpushClientUtil jpushClientUtil = new JpushClientUtil(Global.SPECIAL_JPUSH_APP_KEY,Global.SPECIAL_JPUSH_MASTER_SECRET,Global.SPECIAL_JPUSH_IOS_APNS) ;
                    switch (notifyRecord.getChannel()) {
                        case "10":  // 通过标签 tag 推送
                            success = jpushClientUtil.sendPushByTag(notifyRecord.getNotify().getTitle(),notifyRecord.getNotify().getContent() ,new String[]{notifyRecord.getMarking()} ,map) ;
                            break ;
                        case "20":  // 通过别名 alias 推送
                            success = jpushClientUtil.sendPushByAlias(notifyRecord.getNotify().getTitle(),notifyRecord.getNotify().getContent() ,new String[]{notifyRecord.getMarking()} ,map) ;
                            break ;
                    }
                    notifyRecord.getNotify().setExtrasMap(map) ;
                }
            }
        }

        return success ;
    }

    /***********************************************************************
     *
     * 【推送】相关API（以下区域）
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
     * 查询所有推送接口
     */
    public void findNotify(Message message, String content){
        Map<String ,String> map = JSON.parseObject(content, Map.class);
        String accountNum = map.get("accountNum");
        String readFlag = map.get("readFlag");  // 阅读标识:未传:所有;0:未读;1:已读;
        String type = map.get("type") ;
        String page = map.get("page");
        String rows = map.get("rows");

        if (StringUtils.isBlank(accountNum) || accountNum==null){
            message.setMsg("账号为空");
            message.setSuccess(false);
            return;
        }
        /*if (StringUtils.isBlank(page) || StringUtils.isBlank(rows)){
            message.setMsg("請傳入分頁參數");
            message.setSuccess(false);
            return;
        }*/

        Map<String , String> searchMap = new HashMap<>();
        searchMap.put("userId", accountNum);
        searchMap.put("readFlag", readFlag);
        searchMap.put("type", type);

        DataGrid<Notify> dataGrid = new DataGrid<Notify>();
        if(StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            long start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            searchMap.put("start", String.valueOf(start));
            searchMap.put("rows", rows);
        } else {
            searchMap.put("isNotLimit","1") ;
        }

        /*
        //已读
        NotifyRecord notifyRecord = new NotifyRecord();
        notifyRecord.setUserId(accountNum);
        long total = notifyRecordMapper.count(notifyRecord);
        notifyRecord.setPage(page);
        notifyRecord.setRows(rows);
        notifyRecord.setStatus("20");
        notifyRecord.setStart(String.valueOf(start));
        dataGrid.setTotal(total);

        List<NotifyRecord> read = notifyRecordMapper.query(notifyRecord);
        List<Notify> notifyList = new ArrayList<>();
        for (NotifyRecord readNotify:read){
            Notify notify = notifyMapper.getById(readNotify.getNotifyId());
            notify.setStatus(readNotify.getReadFlag());
            notifyList.add(notify);
        }
        */


        long total = notifyMapper.countPersonalNotifies(searchMap);
        dataGrid.setTotal(total);

        List<Notify> notifyList = notifyMapper.selectPersonalNotifies(searchMap);
        dataGrid.setRows(notifyList);
        message.setMsg("查询所有");
        message.setObj(dataGrid);
        message.setSuccess(true);
    }

    /**
     * 查询我的推送详情
     * @param message
     * @param content
     */
    public void findMynotify(Message message , String content){
        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String)map.get("userId");
        String notifyID = (String)map.get("notifyId");
        if (StringUtils.isBlank(accountNum) || accountNum==null){
            message.setMsg("账号为空");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(notifyID)&&notifyID==null){
            message.setMsg("您没有选中推送");
            message.setSuccess(false);
            return;
        }
        map.put("status" ,"20");
        map.put("notifyId",notifyID);
        map.put("userId", accountNum);
        NotifyRecord notifyRecord = notifyRecordMapper.getByIdAndUserId(map);
        if(notifyRecord == null){
            message.setMsg("沒有查到該推送信息");
            message.setSuccess(false);
            return;
        }
        String notifyId = notifyRecord.getNotifyId();
        Notify byId = notifyMapper.getById(notifyId);

        notifyRecord.setReadDate(DateUtils.formatDateTime(new Date()));
        notifyRecord.setReadFlag("1");
        notifyRecord.preUpdate();
        notifyRecordMapper.update(notifyRecord);
        message.setMsg("推送详情");
        message.setObj(byId);
        message.setSuccess(true);
    }

    /**
     * 设置已读
     * @param message
     * @param content
     */
    public void setRead(Message message,String content){
        if(StringUtils.isBlank(content)) {
            message.setMsg("未传任何参数");
            message.setSuccess(false);
            return;
        }
        NotifyRecord notifyRecord = JSON.parseObject(content, NotifyRecord.class);
        if(notifyRecord == null) {
            message.setMsg("未传任何参数");
            message.setSuccess(false);
            return;
        }
//        String notifyId = map.get("notifyId");
        if (StringUtils.isBlank(notifyRecord.getUserId())){
            message.setMsg("账号为空");
            message.setSuccess(false);
            return;
        }
        try {
            notifyRecord.setIsNotLimit("1");
            List<NotifyRecord> list = notifyRecordMapper.query(notifyRecord) ;
            if (list == null || list.isEmpty()) {
                message.setMsg("您不属于推送");
                message.setSuccess(false);
                return;
            }
            notifyRecord = list.get(0) ;

            notifyRecord.setReadFlag("1");
            notifyRecord.setReadDate(DateUtils.formatDateTime(new Date()));
            notifyRecordMapper.update(notifyRecord);
            message.setMsg("设置已读成功");
            message.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            message.setMsg("您不属于推送");
            message.setSuccess(false);
        }
    }
    /**
     * 获取未读数量  0未读
     */
    public void noReadCount(Message message,String content){
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String accountNum = map.get("userId");
        String type = map.get("type");
        if (StringUtils.isBlank(accountNum)){
            message.setMsg("账号为空");
            message.setSuccess(false);
            return;
        }

        Map< String , String > searchMap = new HashMap<>();
        searchMap.put("userId", accountNum);
        searchMap.put("type", type);
        searchMap.put("readFlag", "0");
        long count = notifyMapper.countPersonalNotifies(searchMap);
//        NotifyRecord notifyRecord = new NotifyRecord();
//        notifyRecord.setUserId(accountNum);
//        notifyRecord.setReadFlag("0");
//        Long count = notifyRecordMapper.count(notifyRecord);
        message.setMsg("未读数量");
        message.setSuccess(true);
        message.setObj(count);
    }

    /**
     * 批量設置已讀
     */
    public void setAllReaded(Message message, String content)
    {
        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String)map.get("userId");
        String notifyIds = (String)map.get("notifyIds");
        if (StringUtils.isBlank(accountNum) || accountNum == null)
        {
            message.setMsg("账号为空");
            message.setSuccess(false);
            return;
        }
        if(StringUtils.isNotBlank(notifyIds))
        {
            String tmp[] = notifyIds.split(",");
            if( tmp.length > 0)
                map.put("notifyIds", tmp);
        }
        notifyRecordMapper.setAllReaded(map);
        message.setMsg("操作成功");
        message.setSuccess(true);
    }

    /**
     * 系统推送删除
     */
    public void deleteNotify(Message message, String content)
    {
        Map<String,Object> map = JSON.parseObject(content, Map.class);
        String accountNum = (String)map.get("userId");
        String notifyId = (String)map.get("notifyId");
        if (StringUtils.isBlank(accountNum) || accountNum == null)
        {
            message.setMsg("账号不能为空");
            message.setSuccess(false);
            return;
        }
        map.put("accountNum", accountNum);
        map.put("notifyId", notifyId);
        notifyRecordMapper.deleteSystemNotify(map);
        message.setMsg("系统通知删除成功");
        message.setSuccess(true);
    }


}
