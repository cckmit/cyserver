package com.cy.core.chatGroup.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.chatDeptGroup.dao.ChatDeptGroupMapper;
import com.cy.core.chatDeptGroup.entity.ChatDeptGroup;
import com.cy.core.chatGroup.dao.ChatGroupMapper;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroupUser.dao.ChatGroupUserMapper;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.system.Global;
import com.cy.util.DateUtils;
import com.cy.util.PairUtil;
import com.cy.util.UserUtils;
import com.cy.util.easemob.comm.utils.EasemobUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("chatGroupService")
public class ChatGroupServiceImpl implements ChatGroupService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());


    private final static long MEMBER_SIZE_INIT = 300 ;      // 群组成员最大值初始值
    private final static long INCREASED_RATE = 100 ;        // 群组成员最大值增长基数

	@Autowired
    private ChatGroupMapper chatGroupMapper;

    @Autowired
    private UserProfileMapper userProfileMapper ;

    @Autowired
    private ChatGroupUserMapper chatGroupUserMapper ;

    @Autowired
    private UserInfoMapper userInfoMapper ;

    @Autowired
    private DeptMapper deptMapper ;

    @Autowired
    private ChatDeptGroupMapper chatDeptGroupMapper ;

    public List<ChatGroup> findList(ChatGroup chatGroup) {
        List<ChatGroup> list = chatGroupMapper.query(chatGroup);
        return list ;
    }

    public DataGrid<ChatGroup> dataGrid(ChatGroup chatGroup) {
        DataGrid<ChatGroup> dataGrid = new DataGrid<ChatGroup>();
        long total = chatGroupMapper.count(chatGroup);
        dataGrid.setTotal(total);
        int start = (Integer.valueOf(chatGroup.getPage()) - 1) * Integer.valueOf(chatGroup.getRows());
        chatGroup.setStart(String.valueOf(start));
        List<ChatGroup> list = chatGroupMapper.query(chatGroup);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public ChatGroup getById(String id) {
        return chatGroupMapper.getById(id);
    }

    public void update(ChatGroup chatGroup) {
        if (chatGroup == null)
            throw new IllegalArgumentException("chatGroup cannot be null!");
        chatGroup.preUpdate();
        chatGroupMapper.update(chatGroup);
    }

    /**
     * 创建群组
     * @param chatGroup
     */
    public void insert(ChatGroup chatGroup) {
        if (chatGroup == null)
            throw new IllegalArgumentException("chatGroup cannot be null!");

        // 创建群
        if(StringUtils.isBlank(chatGroup.getId())){

            // 指定群组默认管理员账号
            if (StringUtils.isBlank(chatGroup.getUserId())) {
                chatGroup.setUserId("init");
            }
            // 群名称默认值
            if (StringUtils.isBlank(chatGroup.getName())) {
                chatGroup.setName("未命名群" + DateUtils.getDateTime());
            }
            // 群介绍默认值
            if (StringUtils.isBlank(chatGroup.getIntroduction())) {
                chatGroup.setIntroduction("本群创建于" + DateUtils.getDate());
            }
            // 群类型（0：普通群；1：系统群）
            if (StringUtils.isBlank(chatGroup.getType())) {
                chatGroup.setType("0");
            }
            chatGroup.preInsert();

            // 环信创建群
            chatGroup.setMaxusers(String.valueOf(MEMBER_SIZE_INIT));

            String easemobGroupId = EasemobUtils.createNewGroupSingle(chatGroup.getId(),chatGroup.getId(),false,false,MEMBER_SIZE_INIT,null) ;

            if(StringUtils.isNotBlank(easemobGroupId)) {
                chatGroup.setEasemobGroupId(easemobGroupId) ;
                chatGroup.setTotal("0");
                chatGroupMapper.insert(chatGroup);

                // 如群组有管理员,则将管理员加入成员列表
                if(!"init".equals(chatGroup.getUserId())) {
                    addMemberToGroup(chatGroup.getId(),chatGroup.getUserId());
                }
            }
        }

    }

    /**
     * 添加成员到群组中
     * @param groupId
     * @param members
     */
    public PairUtil<Boolean,String> addMemberToGroup(String groupId ,String members) {
        PairUtil<Boolean,String> pairUtil ;
        Boolean success = false ;
        String msg = "" ;
        if(StringUtils.isNotBlank(groupId) || StringUtils.isNotBlank(members)) {
            ChatGroup chatGroup = getById(groupId) ;

            if(chatGroup == null) {
                msg = "groupId("+groupId+") 对应的群组不存在" ;
                logger.warn(msg);
                return new PairUtil<Boolean,String>(success,msg);
            }
            if(StringUtils.isBlank(chatGroup.getEasemobGroupId())) {
                EasemobUtils.createNewGroupSingle(chatGroup.getId(),chatGroup.getId(),false ,false ,MEMBER_SIZE_INIT ,null) ;
            }
            Long total = Long.valueOf(chatGroup.getTotal()) ;
            Long maxusers = Long.valueOf(chatGroup.getMaxusers()) ;
            String[] memberIds = members.split(",") ;
            if((total + memberIds.length) >= maxusers) {
                maxusers += INCREASED_RATE ;
                EasemobUtils.changeChatGroups(chatGroup.getEasemobGroupId(),null ,null ,maxusers) ;
            }

            ChatGroupUser groupUser = new ChatGroupUser() ;
            groupUser.setGroupId(groupId);
            for(String memberId : memberIds) {
                UserProfile userProfile = userProfileMapper.selectByAccountNum(memberId) ;
                if(userProfile != null) {
                    groupUser.setUserId(memberId);
                    List<ChatGroupUser> list = chatGroupUserMapper.query(groupUser);
                    if (list == null || list.isEmpty()) {
                        groupUser.preInsert();
                        chatGroupUserMapper.insertMemeber(groupUser);

                        // 检测环信用户是否存在,不如存在注册用户
                        EasemobUtils.existsUserAndCreate(userProfile.getAccountNum() ,userProfile.getPassword(),null);
                        EasemobUtils.addUserToGroup(chatGroup.getEasemobGroupId(), memberId);
                        total++;
                    } else {
                        logger.warn("用户" + memberId + "已经在群组" + groupId + "中");
                        msg += "用户" + memberId + "已经在群组" + groupId + "中;\n";
                        return new PairUtil<Boolean,String>(success,msg);
                    }
                } else {
                    logger.warn("用户" + memberId + "不存在;\n");
                    msg += "用户" + memberId + "不存在;\n";
                    return new PairUtil<Boolean,String>(success,msg);

                }
            }
            chatGroup.setTotal(String.valueOf(total));
            chatGroup.setMaxusers(String.valueOf(maxusers));
            chatGroup.preUpdate();

            chatGroupMapper.update(chatGroup);
            success = true ;
            msg = StringUtils.isNotBlank(msg) ? msg : "添加成功" ;
            return new PairUtil<Boolean,String>(success,msg);

        } else {
            msg = "groupId/memberId 为空" ;
            logger.warn(msg);
            return new PairUtil<Boolean,String>(success,msg);
        }
    }
    /**
     * 删除成员从群组中(单个)
     * @param groupId
     * @param memberId
     */
    public PairUtil<Boolean,String> removeMemberFromGroup(String groupId , String memberId) {
        PairUtil<Boolean,String> pairUtil ;
        Boolean success = false ;
        String msg = null ;
        if(StringUtils.isNotBlank(groupId) || StringUtils.isNotBlank(memberId)) {
            ChatGroup chatGroup = getById(groupId) ;

            if(chatGroup == null) {
                msg = "groupId 对应的群组不存在" ;
                logger.warn(msg);
                return new PairUtil<Boolean,String>(success,msg);
            }
            if(StringUtils.isBlank(chatGroup.getEasemobGroupId())) {
                EasemobUtils.createNewGroupSingle(chatGroup.getId(),chatGroup.getId(),false ,false ,MEMBER_SIZE_INIT ,null) ;
            }
            Long total = Long.valueOf(chatGroup.getTotal()) ;

            ChatGroupUser groupUser = new ChatGroupUser() ;
            groupUser.setGroupId(groupId);
            groupUser.setUserId(memberId);
            List<ChatGroupUser> list = chatGroupUserMapper.query(groupUser) ;
            if(list != null && !list.isEmpty()) {
                for(ChatGroupUser temp : list) {
                    temp.preUpdate();
                    chatGroupUserMapper.delete(temp);
                    EasemobUtils.deleteUserFromGroup(groupId,memberId) ;
                }
                total--;
            } else {
                msg = "用户"+memberId + "不在在群组" + groupId + "中" ;
                logger.warn(msg);
                return new PairUtil<Boolean,String>(success,msg);
            }
            chatGroup.setTotal(String.valueOf(total));
            chatGroup.preUpdate();
            chatGroupMapper.update(chatGroup);
            success = true ;
            msg = StringUtils.isNotBlank(msg) ? msg : "删除成功" ;
            return new PairUtil<Boolean,String>(success,msg);
        } else {
            msg = "groupId/memberId 为空" ;
            logger.warn(msg);
            return new PairUtil<Boolean,String>(success,msg);
        }
    }

    /**
     * 删除成员从群组中(多个)
     * @param groupId
     * @param members
     */
    public PairUtil<Boolean,String> removeMultiMemberFromGroup(String groupId ,String members) {
        PairUtil<Boolean,String> pairUtil ;
        Boolean success = false ;
        String msg = null ;
        if(StringUtils.isNotBlank(groupId) || StringUtils.isNotBlank(members)) {
            ChatGroup chatGroup = getById(groupId) ;

            if(chatGroup == null) {
                msg = "groupId 对应的群组不存在" ;
                logger.warn(msg);
                return new PairUtil<Boolean,String>(success,msg);
            }
            if(StringUtils.isBlank(chatGroup.getEasemobGroupId())) {
                EasemobUtils.createNewGroupSingle(chatGroup.getId(),chatGroup.getId(),false ,false ,MEMBER_SIZE_INIT ,null) ;
            }
            Long total = Long.valueOf(chatGroup.getTotal()) ;
            Long maxusers = Long.valueOf(chatGroup.getMaxusers()) ;
            String[] memberIds = members.split(",") ;

            ChatGroupUser groupUser = new ChatGroupUser() ;
            groupUser.setGroupId(groupId);
            for(String memberId : memberIds) {
                groupUser.setUserId(memberId);
                List<ChatGroupUser> list = chatGroupUserMapper.query(groupUser) ;
                if(list != null && !list.isEmpty()) {
                    for(ChatGroupUser temp : list) {
                        temp.preUpdate();
                        chatGroupUserMapper.delete(temp);
                        EasemobUtils.deleteUserFromGroup(groupId,memberId) ;
                    }
                    total -- ;
                } else {
                    logger.warn("用户"+memberId + "不在在群组" + groupId + "中");
                    msg += "用户"+memberId + "不在在群组" + groupId + "中;\\n" ;
                }
            }
            chatGroup.setTotal(String.valueOf(total));
            chatGroup.preUpdate();
            chatGroupMapper.update(chatGroup);
            success = true ;
            msg = StringUtils.isNotBlank(msg) ? msg : "删除成功" ;
            return new PairUtil<Boolean,String>(success,msg);
        } else {
            msg = "groupId/memberId 为空" ;
            logger.warn(msg);
            return new PairUtil<Boolean,String>(success,msg);
        }
    }

    /**
     * 根据ID 集合删除 联系人信息
     */
    public void deleteByIdList(String ids) {
    	String[] array = ids.split(",");
		List<String> list = Arrays.asList(array);
		chatGroupMapper.deleteByIdList(list);
    }

    /**
     * 检测群组下是否有成员,如没有则删除群组
     * @param groupId
     */
    public void checkAndDeleteEmptyGroup(String groupId) {
        ChatGroup group = chatGroupMapper.queryChatGroupById(groupId) ;

        if(group != null) {
            ChatGroupUser groupUser = new ChatGroupUser();
            groupUser.setGroupId(groupId);
            long count = 0 ;
            if ("1".equals(group.getType())) {
//                groupUserList = chatGroupUserMapper.queryClassGroupUserForApp(groupUser);

                // 如是班级群组,需删除班级群组中间表
                ChatDeptGroup deptGroup = new ChatDeptGroup() ;
                deptGroup.setGroupId(groupId);
                List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup) ;

                if(deptGroupList != null && !deptGroupList.isEmpty()) {
                    String deptId = deptGroupList.get(0).getDeptId() ;
                    for(int i = 1 ; i < deptGroupList.size() ; i++) {
                        deptGroupList.get(i).preUpdate();
                        chatDeptGroupMapper.delete(deptGroupList.get(i));
                    }
                    Map<String,Object> map = Maps.newHashMap() ;
                    map.put("regflag","1") ;
                    map.put("deptId1",deptId) ;
                    count = userInfoMapper.countByDeptId(map) ;
                    if(count <= 0) {
                        deptGroup = new ChatDeptGroup() ;
                        deptGroup.setDeptId(deptId);
                        deptGroupList = chatDeptGroupMapper.query(deptGroup) ;
                        if(deptGroupList != null && !deptGroupList.isEmpty()) {
                            for(ChatDeptGroup temp : deptGroupList) {
                                temp.preUpdate();
                                chatDeptGroupMapper.delete(temp);
                            }
                        }
                    }

                }

            } else {
                count = chatGroupUserMapper.count(groupUser) ;
            }
            if(count <= 0) {
                // 删除环信群组
                EasemobUtils.deleteChatGroups(group.getEasemobGroupId()) ;
                delete(groupId);
            }
        }

    }

    /**
     * 根据ID 删除群组信息
     */
    public void delete(String id) {
    	ChatGroup chatGroup = new ChatGroup() ;
    	chatGroup.setId(id) ;
        chatGroup.preUpdate();
    	chatGroupMapper.delete(chatGroup);
    }

    /**
     * 比较学习经历和群组列表
     * @param userId : (为userProfile 表中的accountNum)
     */
    public void checkClassesAndGroup(String userId) {
        List<Map<String, Object>> pathList = userInfoMapper.getLearningExp(userId);

        ChatGroup group = new ChatGroup() ;
        group.setMemberUserId(userId);
        group.setType("1");
        List<Map<String,Object>> groupList = chatGroupMapper.queryGroupInterface(group) ;

        List<String> classIdList = Lists.newArrayList() ; // 班级群组中没有的学习经历班级编号
        List<String> groupIdList = Lists.newArrayList() ; // 学习经历中没有的班级群组编号

        for (Map<String,Object> temp: pathList ) {
            if(temp != null && temp.get("classId") != null) {
                classIdList.add((String)temp.get("classId"));
            }
        }
        for (Map<String,Object> temp: groupList ) {
            if(temp != null && temp.get("groupId") != null) {
                groupIdList.add((String)temp.get("groupId"));
            }
        }

        if (pathList != null && groupList != null) {
            if(pathList != null && !classIdList.isEmpty()) {
                for(int i = 0 ; i < pathList.size() ; i++) {
                    if(groupList != null && !groupList.isEmpty()) {
                        for (Map<String,Object> groupTemp: groupList) {
                            if(((String)(pathList.get(i).get("classId"))).equals((String)groupTemp.get("deptId"))) {
                                classIdList.remove((String)pathList.get(i).get("classId")) ;
                                groupIdList.remove((String)groupTemp.get("groupId")) ;
                            }
                        }
                    }
                }
            }
        }

        if(classIdList != null && !classIdList.isEmpty()) {
            for (String classId : classIdList) {
                insertClassGroupByClasses(classId,userId);
            }
        }

        if(groupIdList != null && !groupIdList.isEmpty()) {
            for (String groupId : groupIdList) {
                removeClassGroupByClasses(groupId,userId);
            }
        }


    }

    /**
     * 用户学习经历班级对应的班级群组不存在,需要创建
     */
    public void insertClassGroupByClasses(String classId ,String userId) {
        // 1. 根据班级编号找到对应的群组编号
        ChatDeptGroup deptGroup = new ChatDeptGroup() ;
        deptGroup.setDeptId(classId);
        List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup);

        String groupId = null ;
        if(deptGroupList == null || deptGroupList.isEmpty()) {
            // 1.1 如果班级群组不存在,创建班级群组和环信群组
            Dept dept = deptMapper.selectOne(classId) ;
            ChatGroup group = new ChatGroup() ;
            group.setName(dept.getDeptName());
            group.setIntroduction(dept.getFullName());
            group.setType("1");
            insert(group);
            groupId = group.getId() ;
            deptGroup.preInsert();
            deptGroup.setGroupId(groupId);
            chatDeptGroupMapper.insert(deptGroup);
        } else {
            deptGroup = deptGroupList.get(0) ;
        }

        // 2. 根据班级群组,该用户班级成员数据
        addMemberToGroup(deptGroup.getGroupId(),userId) ;
    }

    public void removeClassGroupByClasses(String groupId ,String userId) {
        removeMemberFromGroup(groupId,userId);
    }

    /**
     * 初始化创建班级群组
     * @param classId 班级编号
     */
    public void initInsertClassGroup(String classId) {
        ChatDeptGroup deptGroup = new ChatDeptGroup() ;
        deptGroup.setDeptId(classId);
        List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup) ;
        String groupId = null ;
        if(deptGroupList != null && !deptGroupList.isEmpty()) {
            groupId = deptGroupList.get(0).getGroupId() ;
        }
        if(StringUtils.isBlank(groupId)) {
            // 1.1 如果班级群组不存在,创建班级群组和环信群组
            Dept dept = deptMapper.selectOne(classId);
            ChatGroup group = new ChatGroup();
            group.setName(dept.getDeptName());
            group.setIntroduction(dept.getFullName());
            group.setType("1");
            insert(group);
            groupId = group.getId();
            deptGroup.preInsert();
            deptGroup.setGroupId(groupId);
            chatDeptGroupMapper.insert(deptGroup);
        }
        initInsertClassMembers(classId,groupId);
    }

    /**
     * 初始化班级成员
     * @param classId
     * @param groupId
     */
    public void initInsertClassMembers(String classId ,String groupId) {
        String memberIds = userInfoMapper.getUserInfosByClassId(classId) ;
        if(StringUtils.isNotBlank(memberIds)) {
            addMemberToGroup(groupId, memberIds);
        }
    }


    /**
     * 修复图片问题
     * @param groupUserList
     * @return
     */
    public List<Map<String, String>> fixPic(List<Map<String,String>> groupUserList){
        if(groupUserList != null && groupUserList.size()>0){
            for(int i =0; i < groupUserList.size(); i++){
                if(StringUtils.isNotBlank(groupUserList.get(i).get("pictureUrl"))){
                    String picUrl = groupUserList.get(i).get("pictureUrl");
                    String picUrlRT = picUrl;
                    String pictureUrl_xd=picUrl;
                    String picture_xd=picUrl;
                    if(StringUtils.isNotBlank(picUrl) && picUrl.indexOf("http") < 0){
                        picUrl = Global.URL_DOMAIN + picUrl ;
                    }
                    groupUserList.get(i).put("pictureUrl", picUrl);
                    groupUserList.get(i).put("pictureRT",picUrlRT);
                    groupUserList.get(i).put("pictureUrl_xd",pictureUrl_xd);
                    groupUserList.get(i).put("picture_xd",picture_xd);
                }
                if(StringUtils.isNotBlank(groupUserList.get(i).get("picture"))){
                    String picUrl = groupUserList.get(i).get("picture");
                    String picUrlRT = picUrl;
                    String pictureUrl_xd=picUrl;
                    String picture_xd=picUrl;
                    if(StringUtils.isNotBlank(picUrl) && picUrl.indexOf("http") < 0){
                        picUrl = Global.URL_DOMAIN + picUrl ;
                    }
                    groupUserList.get(i).put("picture", picUrl);
                    groupUserList.get(i).put("pictureRT",picUrlRT);
                    groupUserList.get(i).put("pictureUrl_xd",pictureUrl_xd);
                    groupUserList.get(i).put("picture_xd",picture_xd);
                }
            }
        }
        return groupUserList;
    }

    /***********************************************************************
     *
     * 【联系人】相关API（以下区域）
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
     * 查询用户参与的群组列表
     * @param message
     * @param content
     */
    public void findChatGroupList(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);

        if (map == null || StringUtils.isBlank(map.get("userId"))) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        String userId = map.get("userId") ;
        ChatGroup group = new ChatGroup() ;
        group.setMemberUserId(userId);

        checkClassesAndGroup(userId) ;
        // 2. 获得用户所在群组
        List<Map<String,Object>> groupList = chatGroupMapper.queryGroupInterface(group) ;

        StringBuffer classBuffer = new StringBuffer();
        String clazz = StringUtils.isNotBlank(classBuffer) ? classBuffer.deleteCharAt(classBuffer.length() - 1).toString() : "处理成功";
        message.setMsg(clazz);
        message.setSuccess(true);
        message.setObj(groupList);
        return;
    }

    /**
     * 查询群组详情
     * @param message
     * @param content
     */
    public void findChatGroup(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);

        if (map == null || StringUtils.isBlank(map.get("userId"))) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (map == null || StringUtils.isBlank(map.get("groupId"))) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }

        String userId = map.get("userId") ;
        String groupId = map.get("groupId") ;
        ChatGroup group = new ChatGroup() ;
        group.setId(groupId);
        List<Map<String,Object>> groupList = chatGroupMapper.queryGroupInterface(group) ;
        Map<String,Object> groupMap = null ;
        if(groupList != null && !groupList.isEmpty() ) {
            groupMap = groupList.get(0) ;
            ChatGroupUser groupUser = new ChatGroupUser() ;
            groupUser.setGroupId((String) groupMap.get("groupId"));

            groupUser.setUserId(userId);
            List<Map<String,String>> groupUserList = Lists.newArrayList() ;
            if("1".equals(groupMap.get("type"))) {
                groupUserList = fixPic(chatGroupUserMapper.queryClassGroupUserForApp(groupUser));

            } else {
                groupMap.put("type","0") ;
                groupUserList = fixPic(chatGroupUserMapper.queryGroupUserForApp(groupUser));
            }

            groupMap.put("contacts",groupUserList) ;
        } else {
            message.setMsg("群组不存在");
            message.setSuccess(false);
            return;
        }

        StringBuffer classBuffer = new StringBuffer();
        String clazz = StringUtils.isNotBlank(classBuffer) ? classBuffer.deleteCharAt(classBuffer.length() - 1).toString() : "查询成功";
        message.setMsg(clazz);
        message.setSuccess(true);
        message.setObj(groupMap);
        return;
    }

    /**
     * 创建群组
     * @param message
     * @param content
     */
    public void saveChatGroup(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String userId = map.get("userId") ;
        String groupName = map.get("groupName") ;
        String members = map.get("members") ;
        String picture = map.get("picture") ;
        String introduction = map.get("introduction") ;

        if (map == null || map.isEmpty()) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(groupName)) {
            message.setMsg("未传入群组名称");
            message.setSuccess(false);
            return;
        }

        // 创建群组
        ChatGroup group = new ChatGroup() ;
        group.setUserId(userId);
        group.setName(groupName);
        group.setIntroduction(introduction);
        group.setPhoto(picture);

        insert(group);

        // 添加群组成员
        if (StringUtils.isNotBlank(members)) {
            addMemberToGroup(group.getId(),members);
        }

        String groupId = group.getId() ;
        group = new ChatGroup() ;
        group.setId(groupId);
        List<Map<String,Object>> groupList = chatGroupMapper.queryGroupInterface(group) ;
        Map<String,Object> groupMap = null ;
        if(groupList != null && !groupList.isEmpty() ) {
            groupMap = groupList.get(0) ;
            ChatGroupUser groupUser = new ChatGroupUser() ;
            groupUser.setGroupId((String) groupMap.get("groupId"));

            List<Map<String,String>> groupUserList = chatGroupUserMapper.queryGroupUserInterface(groupUser) ;
            groupMap.put("contacts",groupUserList) ;

            //加入群推送
            PushUtils.pushGroupJoinRequest(userId,groupId,members);
            message.setMsg("添加成功");
            message.setSuccess(true);
            message.setObj(groupMap);
        }

        return;
    }
    /**
     * 添加成员
     * @param message
     * @param content
     */
    public void addMemberToGroup(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String userId = map.get("userId") ;
        String groupId = map.get("groupId") ;
        String memberId = map.get("memberId") ;
//        String applicantFlag = map.get("applicantFlag") ;

        if (map == null || map.isEmpty()) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (!UserUtils.isExistsByUserProfile(userId)) {
            message.setMsg("该用户不存在");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(groupId)) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(memberId)) {
            message.setMsg("未传入成员编号");
            message.setSuccess(false);
            return;
        }
        if (!UserUtils.isExistsByUserProfile(memberId)) {
            message.setMsg("该成员不存在");
            message.setSuccess(false);
            return;
        }
//        if (StringUtils.isBlank(applicantFlag)) {
//            message.setMsg("未传入申请者标识");
//            message.setSuccess(false);
//            return;
//        }

        ChatGroup group = chatGroupMapper.getById(groupId) ;
        if (group == null) {
            message.init(false ,"当前群组不存在" ,null);
            return;
        }
        if(!userId.equals(group.getUserId())) {
            message.init(false ,"当前用户不是群组管理员,不能添加成员" ,null);
            return;
        }


        // 添加成员
        ChatGroupUser groupUser = new ChatGroupUser() ;
        groupUser.setUserId(userId);
        groupUser.setMemberId(memberId);
        PairUtil<Boolean ,String > pair = addMemberToGroup(groupId,memberId);

        if (pair.getOne()==true) {
            //加入群推送
            PushUtils.pushGroupJoinRequest(userId, groupId, memberId);
        }
//        message.setMsg("添加成功");
//        message.setSuccess(true);
        message.init(pair.getOne(),pair.getTwo(),null);
        return;
    }
    /**
     * 删除成员
     * @param message
     * @param content
     */
    public void removeMemberFromGroup(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String userId = map.get("userId") ;
        String groupId = map.get("groupId") ;
        String memberId = map.get("memberId") ;
//        String applicantFlag = map.get("applicantFlag") ;

        if (map == null || map.isEmpty()) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (UserUtils.isExistsByUserProfile(userId)) {
            message.setMsg("该用户不存在");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(groupId)) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(memberId)) {
            message.setMsg("未传入成员编号");
            message.setSuccess(false);
            return;
        }
        if (UserUtils.isExistsByUserProfile(memberId)) {
            message.setMsg("该成员不存在");
            message.setSuccess(false);
            return;
        }
//        if (StringUtils.isBlank(applicantFlag)) {
//            message.setMsg("未传入申请者标识");
//            message.setSuccess(false);
//            return;
//        }

        ChatGroup group = chatGroupMapper.getById(groupId) ;
        if (group != null) {
            message.init(false ,"当前群组不存在" ,null);
            return;
        }
        if(!userId.equals(group.getUserId())) {
            message.init(false ,"当前用户不是群组管理员,不能删除成员" ,null);
            return;
        }

        // 删除成员
        PairUtil<Boolean,String> pair = removeMemberFromGroup(groupId,memberId);
        message.init(pair.getOne(),pair.getTwo(),null);
        return;
    }

    /**
     * add by jiangling
     * 根据群组id删除群组
     * @param message
     * @param content
     */
    public void removeChatGroup(Message message, String content) {
        if(StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        ChatGroup chatGroup = JSON.parseObject(content,ChatGroup.class);

        if(chatGroup == null || StringUtils.isBlank(chatGroup.getGroupId())) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }
        if(chatGroup == null || StringUtils.isBlank(chatGroup.getUserId())) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }

        //1. 根据content.group_id查询该群组
        ChatGroup group = chatGroupMapper.queryChatGroupById(chatGroup.getGroupId());

        if(group == null){
            message.setMsg("不存在该群组,或已被删除!");
            message.setSuccess(false);
            return;
        }


        //2. 如果查询出来的userId= content.user_id,则表示这是群主
        //  只有群主才可以删除该群
        if(group != null && group.getUserId().equals(chatGroup.getUserId())) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("groupId",chatGroup.getGroupId());

            chatGroupMapper.updateDelFlag(chatGroup.getGroupId());
            chatGroupMapper.updateGroupUserDelFlag(map);
            EasemobUtils.deleteChatGroups(group.getEasemobGroupId());
            message.setMsg("删除成功!");
            message.setSuccess(true);
            return;
        }

    }


    /**
     * 修改群组信息【只可管理员或群主修改】
     */
    public void updateChatGroup(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String userId = map.get("userId") ;
        String groupId = map.get("groupId") ;
        String groupName = map.get("groupName") ;
        String picture = map.get("picture") ;
        String introduction = map.get("introduction") ;

        if (map == null || map.isEmpty()) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(groupId)) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }

        ChatGroup group = chatGroupMapper.getById(groupId) ;
        if(group == null ) {
            message.setMsg("群组不存在");
            message.setSuccess(false);
            return;
        }

        Map<String,String> queryMap = Maps.newHashMap() ;
        queryMap.put("groupId",groupId) ;
        queryMap.put("userId",userId) ;
        String isAdmin = chatGroupMapper.isGroupAdmin(queryMap) ;
        if(StringUtils.isBlank(isAdmin) || "0".equals(isAdmin)) {
            message.setMsg("你当前不是群主或管理员,不能修改群信息");
            message.setSuccess(false);
            return;
        }

        group.setName(groupName);
        group.setIntroduction(introduction);
        group.setPhoto(picture);

        update(group);

        message.setMsg("修改成功");
        message.setSuccess(true);
        return;
    }

    /**
     * 成员退群
     */
    public void quitChatGroup(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String userId = map.get("userId") ;
        String groupId = map.get("groupId") ;

        if (map == null || map.isEmpty()) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(groupId)) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }

        ChatGroup group = chatGroupMapper.getById(groupId) ;
        if(group == null ) {
            message.setMsg("群组不存在");
            message.setSuccess(false);
            return;
        }

        ChatGroupUser groupUser = new ChatGroupUser() ;
        groupUser.setUserId(userId);
        groupUser.setGroupId(groupId);
        //List<ChatGroupUser> list = chatGroupUserMapper.query(groupUser) ;
        ChatGroupUser chatGroupUser = chatGroupUserMapper.queryGroupUser(groupUser);

        if(chatGroupUser == null) {
            message.setMsg("您不是群成员,不需退出群组");
            message.setSuccess(false);
            return;
        }

        /*for(ChatGroupUser temp : list) {
            // 移除群组用户
            chatGroupUserMapper.delete(temp);
        }*/
        Map<String, String > map2 = new HashMap<String, String>();

        map2.put("userId", userId);
        map2.put("groupId", groupId);

        chatGroupMapper.updateGroupUserDelFlag(map2);

        // 将用户从环信群组中移除
        EasemobUtils.deleteUserFromGroup(group.getEasemobGroupId(),userId) ;

        message.setMsg("退群成功");
        message.setSuccess(true);
        return;
    }

    /**
     * 加入群组
     */
    public void requestInsertChatGroup(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String userId = map.get("userId") ;
        String groupId = map.get("groupId") ;

        if (map == null || map.isEmpty()) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(groupId)) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }

        ChatGroup group = chatGroupMapper.getById(groupId) ;
        if(group == null ) {
            message.setMsg("群组不存在");
            message.setSuccess(false);
            return;
        }

        PairUtil<Boolean ,String > pair = addMemberToGroup(groupId,userId) ;

        message.setMsg(pair.getTwo());
        message.setSuccess(pair.getOne());

        return;
    }

    /**
     * 添加多个成员到群组
     * @auther jiangling
     * @date 2016-08-06
     * @param message
     * @param content
     */
    @Override
    public void addMemberListToGroup(Message message, String content) {
        // 判断conten的内容是否为空
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        // 将JsonStr(content)--> map 格式Map<String,String> map = JSON.parseObject(content, Map.class);
        Map<String,String> map = JSON.parseObject(content, Map.class);
        if (map == null || map.isEmpty()) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }

        // 从map中取到 userId(群组的管理员), groupId(组Id), members(多个成员memberId)
        String userId = map.get("userId") ;
        String groupId = map.get("groupId") ;
        String members = map.get("members") ;

        // 验证其是否有效
        if (StringUtils.isBlank(userId)) {
            message.setMsg("未传入群组管理员编号");
            message.setSuccess(false);
            return;
        }
        if (!UserUtils.isExistsByUserProfile(userId)) {
            message.setMsg("该群组管理员不存在");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(groupId)) {
            message.setMsg("未传入群组编号");
            message.setSuccess(false);
            return;
        }

        //根据groupId 判断该组是否存在
        ChatGroup group = chatGroupMapper.getById(groupId) ;
        if (group == null) {
            message.init(false ,"当前群组不存在" ,null);
            return;
        }
        // 判断是否为群组管理员,只有管理员才可以添加这些成员
        if(!userId.equals(group.getUserId())) {
            message.init(false ,"当前用户不是群组管理员,不能添加成员" ,null);
            return;
        }
        // 创建组,将members加入到group
        PairUtil<Boolean ,String > pair = addMemberToGroup(groupId,members);

        //添加成功,返回message给用户
        message.init(pair.getOne(),pair.getTwo(),null);
        return;

    }





}
