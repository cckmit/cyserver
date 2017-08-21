package com.cy.core.notify.utils;

import com.cy.base.entity.Message;
import com.cy.common.utils.SpringContextHolder;
import com.cy.common.utils.StringUtils;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.association.dao.AssociationMapper;
import com.cy.core.association.dao.AssociationMemberMapper;
import com.cy.core.association.entity.Association;
import com.cy.core.association.entity.AssociationMember;
import com.cy.core.channel.dao.NewsChannelMapper;
import com.cy.core.channel.entity.NewsChannel;
import com.cy.core.chatContacts.entity.ChatContacts;
import com.cy.core.chatGroup.dao.ChatGroupMapper;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroupUser.dao.ChatGroupUserMapper;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.news.dao.NewsMapper;
import com.cy.core.news.entity.News;
import com.cy.core.notify.entity.Notify;
import com.cy.core.notify.entity.NotifyRecord;
import com.cy.core.notify.service.NotifyService;
import com.cy.core.user.service.UserService;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送工具类
 * @author kent
 * @version 2015-09-14
 */
public class PushUtils {

    // 推送类型
    public final static String PUSH_TYPE_NEWS_FEEDS                   = "news_feeds" ; // 新闻订阅
    public final static String PUSH_TYPE_CLASSMATE_AUTHENTICATION     = "classmate_authentication" ; // 同班同学班级认证
    public final static String PUSH_TYPE_EXIT_AUTHENTICATION          = "exit_authentication" ; // 同班同学退出班级认证
    public final static String PUSH_TYPE_REMOVE_GROUP_ALL             = "remove_group_all" ; // 通知同班校友成员被踢出
    public final static String PUSH_TYPE_ALUMNI_JOIN_ALL              = "alumni_join_all" ; // 分会下成员加入
    public final static String PUSH_TYPE_GROUP_JOIN_ALL               = "group_join_all" ; // 群组成员加入
    public final static String PUSH_TYPE_FRIEND_ADD_REQUEST           = "friend_add_request" ; // 好友添加申请
    public final static String PUSH_TYPE_FRIEND_ADD_AGREE             = "friend_add_agree" ; // 同意好友添加申请
    public final static String PUSH_TYPE_CLASS_AUTHENTICATION        = "class_authentication" ; // 校友班级认证
    public final static String PUSH_TYPE_CLASS_AUTHENTICATION_PERSIONAL = "class_authentication_persional";
    public final static String PUSH_TYPE_CLASS_AUTHENTICATION_REQ    = "class_authentication_req" ; // 自定义班级认证申请
    public final static String PUSH_TYPE_CLASS_AUTHENTICATION_HANDLE = "class_authentication_handle" ; // 自定义班级认证申请处理通知
    public final static String PUSH_TYPE_REMOVE_GROUP_PERSONAL        = "remove_group_personal" ; // 踢出班级
    public final static String PUSH_TYPE_ALUMNI_JOIN_AGREE            = "alumni_join_agree" ; // 同意分会添加申请
    public final static String PUSH_TYPE_ALUMNI_JOIN_DISAGREE         = "alumni_join_disagree" ; // 不同意分会添加申请
    public final static String PUSH_TYPE_DONATION_SUCCESS             = "donation_success" ; // 捐赠成功
    public final static String PUSH_TYPE_GROUP_JOIN_REQUEST           = "group_join_request" ; // 自建群邀请加入请求
    public final static String PUSH_TYPE_EXIT_GROUP_ALL               = "exit_group_all" ; // 通知群成员被踢出
    public final static String PUSH_TYPE_EXIT_GROUP_ONE               = "exit_group_one" ; // 通知被踢成员
    public final static String PUSH_TYPE_OTHER                        = "other" ; // 自定义推送内容
    public final static String PUSH_TYPE_INSERT_EVEVT                ="insert_event";//创建活动推送
    public final static String PUSH_TYPE_ASSOCIATION_EVEVT                ="association_event";//创建活动推送
    public final static String PUSH_TYPE_OFFICAL_INSERT_EVEVT                ="offical_insert_event";//创建活动推送


    //邀请加入社团通知
    public final static String PUSH_TYPE_JOIN_ASSOCIATION = "join_association"; //邀请加入社团

    // 下单通知
    public final static String PUSH_ORDER_MESSAGE_TO_USER = "订单号为@orderNo,下单成功；敬请等待发货！" ;
    public final static String PUSH_ORDER_MESSAGE_TO_STORE = "您有新的订单，请注意查看，尽快发货！" ;


    // 发货通知
    public final static String PUSH_DELIVER_GOODS_MESSAGE_TO_USER = "您的订单号为@orderNo的订单已发货，请注意验收" ;

    // 验收通知
    public final static String PUSH_GOODS_RECEIPT_MESSAGE_TO_USER = "通知用户：您的订单号为@orderNo已签收，祝你本次购物愉快，欢迎下次购物！" ;
    public final static String PUSH_GOODS_RECEIPT_MESSAGE_TO_STORE = "订单号为@orderNo的订单已被签收，订单金额已经打入您账户中，请注意查看" ;

    // 评价通知
    public final static String PUSH_COMMENT_MESSAGE_TO_STORE = "订单号为@orderNo的订单已被评价，请注意查看" ;

    private static UserService userService = SpringContextHolder.getBean("userService");			        // 系统用户
    private static UserProfileService userProfileService = SpringContextHolder.getBean("userProfileService");		// 手机用户
    private static NotifyService notifyService = SpringContextHolder.getBean("notifyService");		        // 通知


    /**
     * 推送消息
     * @param userId    推送用户
     * @param message   消息
     * @param type      推送类型
     */
    public static void pushMessage(String userId ,String message ,String type ) {

    }

    /**
     * 1. 下单通知
     * 1.1 通知用户：订单号为xxxx,下单成功；敬请等待发货！
     * 1.2 通知商家：您有新的订单，请注意查看，尽快发货
     */
//    public static void pushOrderMessage(String orderId) {
//        MjbOrder order = orderService.get(orderId) ;
//        if(order == null) {
//            return ;
//        }
//
//        String userId = order.getUserId() ;
//        String storeId = order.getStoreId() ;
//        String orderNo = order.getOrderNo() ;
//        User storeUser = storeService.getUserByStoreId(storeId) ;
//
//        List<NotifyRecord> userList = Lists.newArrayList() ;
//        List<NotifyRecord> storeList = Lists.newArrayList() ;
//
//        String userMessage = PUSH_ORDER_MESSAGE_TO_USER.replace("@orderNo",orderNo) ;
//        String storeMessage = PUSH_ORDER_MESSAGE_TO_STORE.replace("@orderNo",orderNo) ;
//
//        // 用户通知消息
//        Notify userNotify = new Notify() ;
//        userNotify.setContent(userMessage);
//        userNotify.setTitle("订单下单成功");
//        userNotify.setWay("3");
//        userNotify.setStatus("1");
//
//        NotifyRecord shortMsg = new NotifyRecord() ;
//        shortMsg.setUserId(userId);
//        shortMsg.setId(IdGen.uuid());
//        shortMsg.setWay("1");
//        shortMsg.setReadFlag("1");
//        shortMsg.setReadDate(new Date());
//        shortMsg.setUser(new User(userId));
//        shortMsg.setReadFlag("0");
//        shortMsg.setNotify(userNotify);
//        userList.add(shortMsg);
//
//        NotifyRecord appMsg = new NotifyRecord() ;
//        appMsg.setUserId(userId);
//        appMsg.setId(IdGen.uuid());
//        appMsg.setWay("0");
//        appMsg.setUser(new User(userId));
//        appMsg.setReadFlag("0");
//        appMsg.setNotify(userNotify);
//        userList.add(appMsg);
//        userNotify.setNotifyRecordList(userList);
//
//        // 商铺通知消息
//        Notify storeNotify = new Notify() ;
//        storeNotify.setContent(storeMessage);
//        storeNotify.setTitle("新的订单");
//        storeNotify.setWay("3");
//        storeNotify.setStatus("1");
//
//
//        NotifyRecord storeMsg = new NotifyRecord() ;
//        storeMsg.setUserId(storeUser.getId());
//        storeMsg.setId(IdGen.uuid());
//        storeMsg.setWay("1");
//        storeMsg.setUser(storeUser);
//        storeMsg.setReadFlag("0");
//        storeMsg.setNotify(storeNotify);
//        storeList.add(storeMsg);
//        storeNotify.setNotifyRecordList(storeList);
//
//        notifyService.save(userNotify);
//        notifyService.save(storeNotify);

//    }


    /****************************************** 刘振 start ******************************************/

    // 下单通知
    public final static String PUSH_REMOVE_GROUP_PERSONAL = "十分抱歉,您不是@class的校友, 已被解除班级认证,如有什么问题可以联系班级管理员!" ;
    private static DeptMapper deptMapper = SpringContextHolder.getBean("deptMapper");		// 院系
    /**
     * 1. 校友被剔除群组
     * 1.1 通知用户：十分抱歉,您不是@class的校友, 已被解除班级认证,如有什么问题可以联系班级管理员!
     *
     * 此推送为个推(推送给个人)
     */
    public static void pushRemoveGroupPersonal(String userId ,String baseInfoId ,String bussId) {

        System.out.println("----------------测试");
        String msg = PUSH_REMOVE_GROUP_PERSONAL ;
        if(StringUtils.isBlank(baseInfoId) || baseInfoId.length() <= 16) return ;
        String deptId = baseInfoId.substring(0,16) ;
        Dept dept = deptMapper.getById(deptId) ;
        if(dept == null ) return ;
        msg = msg.replace("@class",dept.getFullName()) ;
        Notify notify = new Notify() ;
        notify.preInsert();
        notify.setType(PUSH_TYPE_REMOVE_GROUP_PERSONAL);
        notify.setChannel("20") ;
        notify.setWay("10");
        notify.setTitle("解除班级认证");
        notify.setContent(msg);
        notify.setBussId(bussId);
        notify.setMarkings(userId);

        NotifyRecord notifyRecord = new NotifyRecord() ;
        notifyRecord.preInsert();
        notifyRecord.setNotifyId(notify.getId());
        notifyRecord.setChannel("20");
        notifyRecord.setUserId(userId);
        notifyRecord.setMarking(userId);
        notifyRecord.setStatus("20");
        notifyRecord.setReadFlag("0");
        List<NotifyRecord> list = Lists.newArrayList() ;
        list.add(notifyRecord);
        notify.setNotifyRecordList(list);

        notifyService.insert(notify);

    }

    /****************************************** 刘振 end ********************************************/

    /****************************************** 亓鹏飞 start ******************************************/
    private static UserInfoMapper userInfoMapper = SpringContextHolder.getBean("userInfoMapper");		// 人
    private static AssociationMapper associationMapper = SpringContextHolder.getBean("associationMapper");  //社团
    private static AssociationMemberMapper associationMemberMapper = SpringContextHolder.getBean("associationMemberMapper");      //社团成员
    /**
     * 同班同学多推
     */
    public final static String PUSH_REMOVE_GROUP_ALL = "经管理员审核,@name不是@class的校友, 已被解除班级认证";
    public final static String PUSH_EXIT_AUTHENTICATION = "@name退出了@class!" ;
    public final static String PUSH_CLASSMATE_AUTHENTICATION = "@name已加入@class" ;
    public final static String PUSH_CLASS_AUTHENTICATION = "@name加入了@class";
    public static void pushGroupAll( String baseInfoId,String bussId,String userId, int type ){
        String msg;
        if(type == 0){
            msg = PUSH_REMOVE_GROUP_ALL;
        }else if(type == 1){
            msg = PUSH_EXIT_AUTHENTICATION;
        }else if(type == 2){
            msg = PUSH_CLASSMATE_AUTHENTICATION;
        }else{
            msg = PUSH_CLASS_AUTHENTICATION;
        }

        if(StringUtils.isBlank(baseInfoId) || baseInfoId.length() <= 16) return ;
        String deptId = baseInfoId.substring(0,16) ;
        Dept dept = deptMapper.getById(deptId) ;
        if(dept == null ) return ;
        msg = msg.replace("@class",dept.getFullName()) ;
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(baseInfoId);
        if(userInfo == null) return ;
        msg = msg.replace("@name", userInfo.getUserName());

        String tag = "class_"+ deptId;
        Notify notify = new Notify() ;
        notify.preInsert();
        notify.setChannel("10") ;
        notify.setWay("10");
        if(type == 0 ){
            notify.setType(PUSH_TYPE_REMOVE_GROUP_ALL);
            notify.setTitle("同学被解除班级认证");
            notify.setBussId(bussId);
        }else if(type == 1) {
            notify.setType(PUSH_TYPE_EXIT_AUTHENTICATION);
            notify.setTitle("同学退出班级认证");
            notify.setBussId(userId);
        }else if( type == 2){
            notify.setType(PUSH_TYPE_CLASSMATE_AUTHENTICATION);
            notify.setTitle("同学通过班级认证");
            notify.setBussId(userId);
        }else{
            notify.setType(PUSH_TYPE_CLASS_AUTHENTICATION);
            notify.setTitle("自定义同学通过班级认证");
            notify.setBussId(userId);
        }
        notify.setContent(msg);
        notify.setMarkings(tag);
        Map<String, String> map = new HashMap<>();
        map.put("classId",deptId);
        Map<String, String> groupInfo = deptMapper.selectChatGroupByClassId(deptId);
        if(groupInfo == null) return;
        map.put("groupId", groupInfo.get("groupId"));
        map.put("groupEasemobId", groupInfo.get("groupEasemobId"));
        map.put("groupName", dept.getDepartName());
        map.put("accountNum", userId);
        notify.setExtrasMap(map);

        List<UserInfo>  userInfoList = userInfoMapper.selectClassmates(baseInfoId);
        List<NotifyRecord> list = Lists.newArrayList();

        if(userInfoList == null || userInfoList.size() <= 0) return;
        for(UserInfo ui:userInfoList){
            if(StringUtils.isBlank(ui.getAccountNum())) continue;
            NotifyRecord notifyRecord = new NotifyRecord() ;
            notifyRecord.preInsert();
            notifyRecord.setNotifyId(notify.getId());
            notifyRecord.setChannel("10");
            notifyRecord.setUserId(ui.getAccountNum());
            notifyRecord.setMarking(tag);
            notifyRecord.setStatus("20");
            notifyRecord.setReadFlag("0");
            list.add(notifyRecord);
        }

        notify.setNotifyRecordList(list);
        notifyService.insert(notify);
    }

    /**
     * 个推
     * @param userId
     * @param baseInfoId
     * @param bussId
     * @param type
     */
    public static String PUSH_CLASS_AUTHENTICATION_HANDLE = "您的自定义@class认证";
    public static String PUSH_CLASS_AUTHENTICATION_PERSIONAL = "您已通过@class班级认证";
    public final static String PUSH_ASSOCIATION_EVEVT = "@associationName的社员,@userName创建了@eventTitle的社团活动，欢迎大家前来参加。";
    public static void pushGroupPersonal(String userId ,String baseInfoId ,String bussId, int type) {
        String msg;
        if(type == 0){
            msg = PUSH_CLASS_AUTHENTICATION_PERSIONAL;
        }else{
            msg = PUSH_CLASS_AUTHENTICATION_HANDLE ;
        }
        if(StringUtils.isBlank(baseInfoId) || baseInfoId.length() <= 16) return ;
        String deptId = baseInfoId.substring(0,16) ;
        Dept dept = deptMapper.getById(deptId) ;
        if(dept == null ) return ;

        msg = msg.replace("@class",dept.getFullName()) ;
        Notify notify = new Notify() ;
        notify.preInsert();
        if(type==0){
            notify.setType(PUSH_TYPE_CLASS_AUTHENTICATION_PERSIONAL);
            notify.setTitle("通过班级认证");
        }else{
            notify.setType(PUSH_TYPE_CLASS_AUTHENTICATION_HANDLE);
            if(type == 1){
                notify.setTitle("自定义班级认证成功");
                msg += "成功";
            }else{
                notify.setTitle("自定义班级认证失败");
                msg += "失败";
            }
        }
        notify.setChannel("20") ;
        notify.setWay("10");
        notify.setContent(msg);
        notify.setBussId(bussId);
        notify.setMarkings(userId);
        Map<String, String> map = new HashMap<>();
        map.put("classId",deptId);
        Map<String, String> groupInfo = deptMapper.selectChatGroupByClassId(deptId);
        if(groupInfo == null) return;
        map.put("groupId", groupInfo.get("groupId"));
        map.put("groupEasemobId", groupInfo.get("groupEasemobId"));
        map.put("groupName", dept.getDepartName());
        map.put("accountNum", userId);
        notify.setExtrasMap(map);

        NotifyRecord notifyRecord = new NotifyRecord() ;
        notifyRecord.preInsert();
        notifyRecord.setNotifyId(notify.getId());
        notifyRecord.setChannel("20");
        notifyRecord.setUserId(userId);
        notifyRecord.setMarking(userId);
        notifyRecord.setStatus("20");
        notifyRecord.setReadFlag("0");
        List<NotifyRecord> list = Lists.newArrayList() ;
        list.add(notifyRecord);
        notify.setNotifyRecordList(list);

        notifyService.insert(notify);

    }

    /**
     * 社团活动群推
     * @param associationId
     * @param title
     * @param eventUrl
     * @param bussId
     * @param accountNum
     */
    public static void pushAssociationEvent(String associationId,String title ,String eventUrl,String bussId,String accountNum){

        String msg = PUSH_ASSOCIATION_EVEVT;
        Association association = associationMapper.getAssociationById(associationId);
        //判断此社团是否存在
        if(association == null ){
            return ;    //该社团不存在
        }

        //得到这个社团下所有的成员
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("associationId", associationId);
        searchMap.put("status", "20");
        List<AssociationMember> members = associationMemberMapper.selectAssociationMember(searchMap);

        //判断此社团下是否有成员
        if(members == null || members.size() < 0 ){
            return ;    //该社团下没有成员
        }

        //通过用户账号得到创建此活动的用户
        UserProfile eventCreatuserProfile=userProfileMapper.selectByAccountNum(accountNum);
        //判断用户是否存在
        if(eventCreatuserProfile == null){
            return ;    //查不到此用户
        }
        String name = eventCreatuserProfile.getName();
        msg = msg.replace("@associationName",association.getName());
        msg = msg.replace("@userName",name) ;
        msg = msg.replace("@eventTitle",title);

        String tag="association_"+associationId;
        Map<String,String> map =new HashMap<String,String>();
        Notify notify = new Notify() ;
        notify.preInsert();
        map.put("eventUrl",eventUrl);
        notify.setExtrasMap(map);
        notify.setType(PUSH_TYPE_ASSOCIATION_EVEVT);
        notify.setChannel("10") ;
        notify.setWay("10");
        notify.setTitle("最新社团活动信息的推送");
        notify.setContent(msg);
        notify.setBussId(bussId);
        notify.setMarkings(tag);

        List<NotifyRecord> list = Lists.newArrayList() ;

        for (AssociationMember am:members ) {
            String userId=am.getAccountNum();
            NotifyRecord notifyRecord = new NotifyRecord() ;
            notifyRecord.preInsert();
            notifyRecord.setNotifyId(notify.getId());
            notifyRecord.setChannel("10");
            notifyRecord.setUserId(userId);
            notifyRecord.setMarking(tag);
            notifyRecord.setStatus("20");
            notifyRecord.setReadFlag("0");
            list.add(notifyRecord);
        }
        notify.setNotifyRecordList(list);
        notifyService.insert(notify);

    }


    /****************************************** 亓鹏飞 end ********************************************/

    /****************************************** 吕鹏 start ******************************************/


    public final static String PUSH_NEWS_FEEDS="@news";
    private static NewsChannelMapper newsChannelMapper = SpringContextHolder.getBean("newsChannelMapper");
    private static UserProfileMapper userProfileMapper = SpringContextHolder.getBean("userProfileMapper");
    private static NewsMapper newsMapper = SpringContextHolder.getBean("newsMapper");
    public static void pushTypeNewsFeed(List<Long> newsId){

        for (Long id: newsId) {
            String msg = PUSH_NEWS_FEEDS;
            News news = newsMapper.selectById(id);
            if (news== null) return;
            Map<String,Object> channel = new HashMap<>();
            channel.put("channelName" ,news.getChannelName());
            NewsChannel newsChannel = newsChannelMapper.selectByChannelName(channel);
            if(newsChannel == null ) return ;
            msg = msg.replace("@news",news.getTitle()) ;
            Notify notify = new Notify() ;
            notify.preInsert();
            notify.setType(PUSH_TYPE_NEWS_FEEDS);
            notify.setChannel("10") ;
            notify.setWay("10");
            notify.setTitle("新闻订阅");
            notify.setContent(msg);
            Map<String ,Object> map = new HashMap<>();
            map.put("channelName",newsChannel.getChannelName());
            map.put("newsName",news.getTitle());
            map.put("newsUrl",news.getNewsUrl());
            notify.setExtrasMap(map);
            notify.setBussId("newsId");
            String tag = "news_"+newsChannel.getChannelId();
            notify.setMarkings(tag);

            List<NotifyRecord> list = Lists.newArrayList() ;
            List<UserProfile> userProfiles = userProfileMapper.selectAll();
            for (UserProfile userProfile:userProfiles){
                if(userProfile.getChannels()==null) {continue;}
                if (userProfile.getChannels().contains(""+newsChannel.getChannelName()) || userProfile.getChannels().contains(""+newsChannel.getChannelId())){
                    NotifyRecord notifyRecord = new NotifyRecord() ;
                    notifyRecord.setUserId(userProfile.getAccountNum());
                    notifyRecord.preInsert();
                    notifyRecord.setNotifyId(notify.getId());
                    notifyRecord.setChannel("10");
                    notifyRecord.setMarking(tag);
                    notifyRecord.setStatus("20");
                    notifyRecord.setReadFlag("0");
                    list.add(notifyRecord);
                }
            }
            notify.setNotifyRecordList(list);
            notifyService.insert(notify);
        }
    }


    /****************************************** 吕鹏 end ********************************************/

    /****************************************** 王傲辉 start ******************************************/
    // 同意加入分会
    public final static String PUSH_ALUMNI_JOIN_AGREE = "恭喜您加入@alumniName" ;
    public final static String PUSH_ALUMNI_JOIN_DISAGREE = "@alumniName拒绝了您的加入，如有什么问题可以联系管理员!" ;
    private static AlumniMapper alumniMapper = SpringContextHolder.getBean("alumniMapper");

    public static void pushAgreeJoinMessage(String accountNum ,String flag,String alumniId ,String bussId){


        // 同意分会添加申请
        // 1.1新建一个通知new notify()，通知内容是“有新成员加入”。
        String msg = "" ;
        String notifyType = "";
        String notifyTitle="";
        if(flag.equals("20") ){
            msg=PUSH_ALUMNI_JOIN_AGREE;
            notifyType = PUSH_TYPE_ALUMNI_JOIN_AGREE;
            notifyTitle = "分会同意添加申请";
        }else if(flag.equals("30") ){
            msg=PUSH_ALUMNI_JOIN_DISAGREE;
            notifyType = PUSH_TYPE_ALUMNI_JOIN_DISAGREE;
            notifyTitle = "分会拒绝添加申请";
        }
        Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(alumniId));
        String main = alumni.getMainId();
        Map<String ,String> map  = new HashMap<String, String>();
        map.put("main",main);
        map.put("alumniId" ,alumniId);
        map.put("accountNum",accountNum);
        msg = msg.replace("@alumniName",alumni.getAlumniName()) ;
        Notify notify = new Notify() ;
        notify.preInsert();
        notify.setType(notifyType);
        notify.setChannel("20") ;//送方式（10：通过标签tag 推送；20：通过别名alias 推送；30：通过手机号推送）
        notify.setWay("10");// 推送方式：10：app；20：短信；30：不推送；40：不确定推送方式
        notify.setTitle(notifyTitle);
        notify.setContent(msg);// 通知内容
        notify.setBussId(bussId);// 通知相关业务编号
        notify.setMarkings(accountNum);// 接收推送标示(对个标识以","隔开)
        notify.setExtrasMap(map);
        // 1.2将该通知发送给所有分会成员（遍历），每送一次生成一条通知记录new NotifyRecord()
        NotifyRecord notifyRecord = new NotifyRecord() ;
        notifyRecord.preInsert();
        notifyRecord.setNotifyId(notify.getId());
        notifyRecord.setChannel("20");
        notifyRecord.setUserId(accountNum);// 接收用户编号
        notifyRecord.setMarking(accountNum);
        notifyRecord.setStatus("20");// 发送状态（10：未发送；20：已发送）
        notifyRecord.setReadFlag("0");

        List<NotifyRecord> list = Lists.newArrayList() ;
        list.add(notifyRecord);
        notify.setNotifyRecordList(list);

        notifyService.insert(notify);



    }
    //群推
    public final static String PUSH_ALUMNI_JOIN_ALL = "@name加入@alumniName" ;
    public static void PushAlumniJoinAll(String accountNum ,String alumniId ,String bussId){
        String msg = PUSH_ALUMNI_JOIN_ALL;
        Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(alumniId));
        if(alumni ==null){
            return;
        }
        UserProfile findedUser=userProfileMapper.selectByAccountNum(accountNum);
        String name=findedUser.getName();
        List<UserProfile> userProfileList = userProfileMapper.getUsersByAlumniId(alumniId);
        if(userProfileList ==null) return;
         msg = msg.replace("@name",name);
        msg = msg.replace("@alumniName",alumni.getAlumniName()) ;
        Map<String ,String> map  = new HashMap<String, String>();
        String tag = "alumni_"+alumniId;
        String main = alumni.getMainId();
        map.put("main",main);
        Notify notify = new Notify() ;
        notify.preInsert();
        notify.setType(PUSH_TYPE_ALUMNI_JOIN_ALL);
        notify.setChannel("10") ;//送方式（10：通过标签tag 推送；20：通过别名alias 推送；30：通过手机号推送）
        notify.setWay("10");// 推送方式：10：app；20：短信；30：不推送；40：不确定推送方式
        notify.setTitle(name+"加入了分会");
        notify.setContent(msg);// 通知内容
        notify.setBussId(bussId);// 通知相关业务编号
        notify.setMarkings(tag);// 接收推送标示(对个标识以","隔开)
        notify.setExtrasMap(map);
        // 1.2将该通知发送给所有分会成员（遍历），每送一次生成一条通知记录new NotifyRecord()
        List<NotifyRecord> list = Lists.newArrayList();
        for(UserProfile users:userProfileList){
            String userId=users.getAccountNum();
            NotifyRecord notifyRecord = new NotifyRecord() ;
            notifyRecord.preInsert();
            notifyRecord.setNotifyId(notify.getId());
            notifyRecord.setChannel("10");
            notifyRecord.setUserId(userId);// 接收用户编号
            notifyRecord.setMarking(tag);
            notifyRecord.setStatus("20");// 发送状态（10：未发送；20：已发送）
            notifyRecord.setReadFlag("0");
            list.add(notifyRecord);
        }

        notify.setNotifyRecordList(list);

        notifyService.insert(notify);
        // 2.2将该通知发送给申请者，每送一次生成一条通知记录

    }

    /****************************************** 王傲辉 end ********************************************/

    /****************************************** 杨牛牛 start ******************************************/

    /*
    * 添加好友申请和同意申请
    * */

    public final static String PUSH_FRIEND_ADD_REQUEST = "您的校友@friend已向您发起好友请求，赶快去同意吧！";
    public final static String PUSH_FRIEND_ADD_AGREE = "您的校友@friend已经同意您的好友请求，赶快去聊天吧！";


    public static void pushFriendAddRequestOrAgree(ChatContacts chatContacts){
        System.out.println("------添加好友申请或验证推送----------");
        if (chatContacts == null) return;

        UserProfile userProfile= userProfileMapper.selectById(chatContacts.getFriendId());
        if (userProfile == null) return;
        String msg = "";
        Notify notify = new Notify();
        notify.preInsert();
        if (chatContacts.getStatus().equals("0")) {
            msg = PUSH_FRIEND_ADD_REQUEST ;
            notify.setType(PUSH_TYPE_FRIEND_ADD_REQUEST);
            notify.setTitle("好友添加申请");
        }
        else if (chatContacts.getStatus().equals("1")){
            msg = PUSH_FRIEND_ADD_AGREE ;
            notify.setType(PUSH_TYPE_FRIEND_ADD_AGREE);
            notify.setTitle("同意好友添加申请");

        }
        msg = msg.replace("@friend",userProfile.getName());
        notify.setChannel("20");
        notify.setWay("10");
        notify.setContent(msg);
        notify.setBussId(chatContacts.getId());
        notify.setMarkings(chatContacts.getUserId());
        Map<String ,Object> map = new HashMap<>();
        map.put("userId",userProfile.getAccountNum());
        notify.setExtrasMap(map);


        NotifyRecord notifyRecord = new NotifyRecord() ;
        notifyRecord.preInsert();
        notifyRecord.setNotifyId(notify.getId());
        notifyRecord.setChannel("20");
        notifyRecord.setUserId(chatContacts.getUserId());
        notifyRecord.setMarking(chatContacts.getUserId());
        notifyRecord.setStatus("10");
        notifyRecord.setReadFlag("0");
        List<NotifyRecord> list = Lists.newArrayList() ;
        list.add(notifyRecord);
        notify.setNotifyRecordList(list);

        notifyService.insert(notify);

    }

    /*
    * 自定义群邀请成员加入申请
    * */

    public final static String PUSH_GROUP_JOIN_REQUEST = "群管理员@administrator邀请您加入@group，赶快去聊天吧";
    //public final static String PUSH_GROUP_JOIN_AGREE = "您申请加入的@group群管理员@administrator已同意您加入，赶快去聊天吧";

    public final static ChatGroupMapper chatGroupMapper = SpringContextHolder.getBean("chatGroupMapper");

    public final static ChatGroupUserMapper chatGroupUserMapper = SpringContextHolder.getBean("chatGroupUserMapper");

    public static void  pushGroupJoinRequest(String userId,String groupId,String memberId){
        System.out.println("------加入群推送----------");

        UserProfile userProfile = userProfileMapper.selectByAccountNum(userId) ;
        if (userProfile == null) return;
        ChatGroup group =chatGroupMapper.getById(groupId);
        if (group == null) return;
        String[] memberIds = memberId.split(",") ;
        /*ChatGroupUser groupUser = new ChatGroupUser() ;
        groupUser.setGroupId(groupId);
        groupUser.setUserId(userId);
        List<Map<String,String>> groupUserList = Lists.newArrayList() ;
        groupUserList = chatGroupUserMapper.queryGroupUserForApp(groupUser);
        String members ="";
        if (groupUserList != null || groupUserList.size()>0){
            for (Map<String,String> gUser :groupUserList){
                if (!members.equals(""))
                    members +=",";
                members += gUser.get("userId");
            }
        }*/
       // for (String mId:memberIds){
           // UserProfile member = userProfileMapper.selectByAccountNum(mId) ;
            String msg = PUSH_GROUP_JOIN_REQUEST;
            msg = msg.replace("@administrator",userProfile.getName()) ;
            msg = msg.replace("@group",group.getName()) ;
            Notify notify = new Notify() ;
            notify.preInsert();
            notify.setType(PUSH_TYPE_GROUP_JOIN_REQUEST);
            notify.setChannel("20") ;
            notify.setWay("10");
            notify.setTitle("加入群成员");
            notify.setContent(msg);
            notify.setBussId(groupId);
            notify.setMarkings(memberId);
            Map<String ,Object> map = new HashMap<>();
            ChatGroup chatGroup = chatGroupMapper.getById(groupId) ;
            if(chatGroup == null) return;
            map.put("normalGroupEasemobId", chatGroup.getEasemobGroupId());
            map.put("normalGroupName", group.getName());
            map.put("normalGroupId",groupId);
            notify.setExtrasMap(map);

            List<NotifyRecord> list = Lists.newArrayList() ;
            if (memberIds !=null){
                for (String s:memberIds){
                    NotifyRecord notifyRecord = new NotifyRecord() ;
                    notifyRecord.preInsert();
                    notifyRecord.setNotifyId(notify.getId());
                    notifyRecord.setChannel("10");
                    notifyRecord.setUserId(s);
                    notifyRecord.setMarking(memberId);
                    notifyRecord.setStatus("10");
                    notifyRecord.setReadFlag("0");
                    list.add(notifyRecord);
                }
            }
            notify.setNotifyRecordList(list);
            notifyService.insert(notify);

       // }


    }


    public final static String PUSH_EXIT_GROUP_ALL = "您的校友@friend已被群管理员@administrator移出@group群！";
    public final static String PUSH_EXIT_GROUP_ONE = "您已被群管理员@administrator移出@group群！";
    public static void  pushExitGroupAll(String userId,String groupId,String memberId){
        System.out.println("------删除群成员推送----------");

        UserProfile userProfile = userProfileMapper.selectByAccountNum(userId) ;
        if (userProfile == null) return;
        ChatGroup group =chatGroupMapper.getById(groupId);
        if (group == null) return;

        String[] memberIds = memberId.split(",") ;
        for (String mId:memberIds){

            /*
            * 给被剔除者个推
            * */
            String msg = PUSH_EXIT_GROUP_ONE;
            msg = msg.replace("@administrator",userProfile.getName()) ;
            msg = msg.replace("@group",group.getName()) ;
            Notify notify = new Notify() ;
            notify.preInsert();
            notify.setType(PUSH_TYPE_EXIT_GROUP_ONE);
            notify.setChannel("20") ;
            notify.setWay("10");
            notify.setTitle("删除群成员");
            notify.setContent(msg);
            notify.setBussId(groupId);
            notify.setMarkings(mId);

            NotifyRecord notifyRecord = new NotifyRecord() ;
            notifyRecord.preInsert();
            notifyRecord.setNotifyId(notify.getId());
            notifyRecord.setChannel("20");
            notifyRecord.setUserId(mId);
            notifyRecord.setMarking(mId);
            notifyRecord.setStatus("10");
            notifyRecord.setReadFlag("0");
            List<NotifyRecord> list = Lists.newArrayList() ;
            list.add(notifyRecord);
            notify.setNotifyRecordList(list);

            notifyService.insert(notify);

            /*
            * 给群成员群推
            * */
            UserProfile member = userProfileMapper.selectByAccountNum(mId) ;
            String msgAll = PUSH_EXIT_GROUP_ALL;
            msgAll = msgAll.replace("@friend",member.getName()) ;
            msgAll = msgAll.replace("@administrator",userProfile.getName()) ;
            msgAll = msgAll.replace("@group",group.getName()) ;
            Notify notifyAll = new Notify() ;
            notifyAll.preInsert();
            notifyAll.setType(PUSH_TYPE_EXIT_GROUP_ALL);
            notifyAll.setChannel("10") ;
            notifyAll.setWay("10");
            notifyAll.setTitle("删除群成员");
            notifyAll.setContent(msgAll);
            notifyAll.setBussId(groupId);
            String tag = "group_"+groupId;
            notifyAll.setMarkings(tag);
            Map<String ,Object> map = new HashMap<>();
            map.put("groupId",groupId);
            notify.setExtrasMap(map);

            ChatGroupUser groupUser = new ChatGroupUser() ;
            groupUser.setGroupId(groupId);
            groupUser.setUserId(userId);
            List<NotifyRecord> listAll = Lists.newArrayList() ;
            List<Map<String,String>> groupUserList = Lists.newArrayList() ;
            groupUserList = chatGroupUserMapper.queryGroupUserForApp(groupUser);
            if (groupUserList != null || groupUserList.size()>0){
               for (Map<String,String> gUser :groupUserList){
                   String  uId = gUser.get("userId");
                   NotifyRecord notifyRecordAll = new NotifyRecord() ;
                   notifyRecordAll.preInsert();
                   notifyRecordAll.setNotifyId(notify.getId());
                   notifyRecordAll.setChannel("10");
                   notifyRecordAll.setUserId(uId);
                   notifyRecordAll.setMarking(tag);
                   notifyRecordAll.setStatus("10");
                   notifyRecordAll.setReadFlag("0");
                   listAll.add(notifyRecordAll);
               }
            }
            notify.setNotifyRecordList(listAll);
            notifyService.insert(notifyAll);

        }


    }

    //邀请加入社团推送
    public final static String PUSH_JOIN_ASSOCIATION = "@inviteAccountNum邀请您加入@associationId社团，丰富您的大学生活";
    /**
     *
     * association //社团实体
     * associationMember //被邀请者社团成员实体
     * associationMemberAdmin //邀请者社团成员实体
     * */
    public static void pushJoinAssociation(Association association, AssociationMember associationMember,String inviteAccountNum){
        if (association == null || StringUtils.isBlank(association.getId())){return;}
        if (associationMember == null || StringUtils.isBlank(associationMember.getId())){return;}
        if (StringUtils.isBlank(inviteAccountNum)){return;}

        UserProfile userProfile =  userProfileMapper.selectById(inviteAccountNum);

        if (userProfile == null){
            return;
        }

        String msg = PUSH_JOIN_ASSOCIATION ;
        Notify notify = new Notify();
        notify.preInsert();
        notify.setType(PUSH_TYPE_JOIN_ASSOCIATION);
        notify.setTitle("邀请加入社团");
        msg = msg.replace("@inviteAccountNum",userProfile.getName());
        msg = msg.replace("@associationId",association.getName());
        notify.setChannel("20");
        notify.setWay("10");
        notify.setContent(msg);
        notify.setBussId(associationMember.getId());
        notify.setMarkings(associationMember.getAccountNum());
        Map<String ,Object> map = new HashMap<>();
        map.put("userId",associationMember.getAccountNum());
        notify.setExtrasMap(map);


        NotifyRecord notifyRecord = new NotifyRecord() ;
        notifyRecord.preInsert();
        notifyRecord.setNotifyId(notify.getId());
        notifyRecord.setChannel("20");
        notifyRecord.setUserId(associationMember.getAccountNum());
        notifyRecord.setMarking(associationMember.getAccountNum());
        notifyRecord.setStatus("10");
        notifyRecord.setReadFlag("0");
        List<NotifyRecord> list = Lists.newArrayList() ;
        list.add(notifyRecord);
        notify.setNotifyRecordList(list);

        notifyService.insert(notify);
    }


    /****************************************** 杨牛牛 end ********************************************/

    /****************************************** 郭亚斌 start ******************************************/
    //活动推送
    public final static String PUSH_INSERT_EVEVT = "@userName创建了@eventTitle的活动，欢迎大家前来参加。";
    public final static String PUSH_OFFICAL_INSERT_EVEVT = "@userName创建了@eventTitle的活动，欢迎大家前来参加。";
    /**
     * 1. 某一个校友创建活动通知某一个范围内所有的校友
     * 1.1某个范围是指某一个分会下的所遇人
     *
     * 此推送为群推
     */
    public static void pushInsertEvent(String alumniId,String title ,String eventUrl,String bussId,String accountNum){

        String msg = PUSH_INSERT_EVEVT;
        if(StringUtils.isBlank(alumniId) ||("1").equals(alumniId)|| ("2").equals(alumniId)|| ("3").equals(alumniId)|| ("4").equals(alumniId) ) return ;
       //通过分会ID查询此分会
        Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(alumniId)) ;
       //判断此分会是否存在
        if(alumni == null ){
            Message message=new Message();
            message.setMsg("该分会不存在");
            message.setSuccess(false);
            return ;
        }
        //得到这个分会下所有的成员
        List<UserProfile>  userList=userProfileMapper.getUsersByAlumniId(alumniId);
        //判断此分会下是否有成员
        if(userList == null || userList.size() < 0 ){
            Message message=new Message();
            message.setMsg("该分会下没有成员");
            message.setSuccess(false);
            return ;
        }
        //通过用户账号得到创建此活动的用户
        UserProfile eventCreatuserProfile=userProfileMapper.selectByAccountNum(accountNum);
        //判断用户是否存在
        if(eventCreatuserProfile == null){
            Message message=new Message();
            message.setMsg("查不到此用户");
            message.setSuccess(false);
            return ;
        }
        String name = eventCreatuserProfile.getName();
        msg = msg.replace("@userName",name) ;
        msg = msg.replace("@eventTitle",title);

        String tag="alumni_"+alumniId;
        Map<String,String> map =new HashMap<String,String>();
        Notify notify = new Notify() ;
        notify.preInsert();
        map.put("eventUrl",eventUrl);
        notify.setExtrasMap(map);
        notify.setType(PUSH_TYPE_INSERT_EVEVT);
        notify.setChannel("10") ;
        notify.setWay("10");
        notify.setTitle("最新发布活动信息的推送");
        notify.setContent(msg);
        notify.setBussId(bussId);
        notify.setMarkings(tag);

        List<NotifyRecord> list = Lists.newArrayList() ;

        for (UserProfile users:userList ) {
            String userId=users.getAccountNum();
            NotifyRecord notifyRecord = new NotifyRecord() ;
            notifyRecord.preInsert();
            notifyRecord.setNotifyId(notify.getId());
            notifyRecord.setChannel("10");
            notifyRecord.setUserId(userId);
            notifyRecord.setMarking(tag);
            notifyRecord.setStatus("20");
            notifyRecord.setReadFlag("0");
            list.add(notifyRecord);
        }
        notify.setNotifyRecordList(list);
        notifyService.insert(notify);

    }


    /**
     * 1. 后台管理员创建官方活动通知这个管理员所管理分会下的所有人
     *
     * 此推送为群推
     */
    public static void pushOfficalInsertEvent(String alumniId,String title ,String eventUrl,String bussId,String accountNum){

        String msg = PUSH_OFFICAL_INSERT_EVEVT;
        if(StringUtils.isBlank(alumniId) ) return ;
        //通过分会ID查询此分会
        Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(alumniId)) ;
        //判断此分会是否存在
        if(alumni == null ){
            Message message=new Message();
            message.setMsg("该分会不存在");
            message.setSuccess(false);
            return ;
        }
        //得到这个分会下所有的成员
        List<UserProfile> userList;
        if(("1").equals(alumniId)|| ("2").equals(alumniId)|| ("3").equals(alumniId)|| ("4").equals(alumniId)){
           userList=userProfileMapper.getAllUsers();
        }else{
            userList=userProfileMapper.getUsersByAlumniId(alumniId);
        }
        //通过传来的alumniID查得是哪个官方的分会发布的活动：既得到分会的名称
        String name=alumni.getAlumniName();
        msg = msg.replace("@userName",name) ;
        msg = msg.replace("@eventTitle",title);

        String tag="alumni_"+alumniId;
        Map<String,String> map =new HashMap<String,String>();
        Notify notify = new Notify() ;
        notify.preInsert();
        map.put("eventUrl",eventUrl);
        notify.setExtrasMap(map);
        notify.setType(PUSH_TYPE_OFFICAL_INSERT_EVEVT);
        notify.setChannel("10") ;
        notify.setWay("10");
        notify.setTitle("最新发布活动信息的推送");
        notify.setContent(msg);
        notify.setBussId(bussId);
        notify.setMarkings(tag);

        List<NotifyRecord> list = Lists.newArrayList() ;

        for (UserProfile users:userList ) {
            String userId=users.getAccountNum();
            NotifyRecord notifyRecord = new NotifyRecord() ;
            notifyRecord.preInsert();
            notifyRecord.setNotifyId(notify.getId());
            notifyRecord.setChannel("10");
            notifyRecord.setUserId(userId);
            notifyRecord.setMarking(tag);
            notifyRecord.setStatus("20");
            notifyRecord.setReadFlag("0");
            list.add(notifyRecord);
        }
        notify.setNotifyRecordList(list);
        notifyService.insert(notify);

    }


    /****************************************** 郭亚斌 end ********************************************/
}
