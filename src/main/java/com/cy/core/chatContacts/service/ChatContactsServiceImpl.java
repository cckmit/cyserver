package com.cy.core.chatContacts.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.sortandgroup.SortByPinyinUtils;
import com.cy.core.chatContacts.dao.ChatContactsMapper;
import com.cy.core.chatContacts.entity.ChatContacts;
import com.cy.core.chatGroup.dao.ChatGroupMapper;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.chatGroupUser.dao.ChatGroupUserMapper;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;
import com.cy.core.chatGroupUser.service.ChatGroupUserService;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.region.dao.CityMapper;
import com.cy.core.region.entity.City;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.mobileInterface.baseinfo.entity.BaseInfo;
import com.cy.system.PinYinUtils;
import com.cy.util.PinYin2Abbreviation;
import com.cy.util.UserUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service("chatContactsService")
public class ChatContactsServiceImpl implements ChatContactsService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ChatContactsMapper chatContactsMapper;

    @Autowired
    private ChatGroupMapper chatGroupMapper ;

    @Autowired
    private ChatGroupUserMapper chatGroupUserMapper ;

    @Autowired
    private UserProfileMapper userProfileMapper ;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private CityMapper cityMapper ;

    @Autowired
    private ChatGroupService chatGroupService ;

    public List<ChatContacts> findList(ChatContacts chatContacts) {
        List<ChatContacts> list = chatContactsMapper.query(chatContacts);
        return list ;
    }

    public DataGrid<ChatContacts> dataGrid(ChatContacts chatContacts) {
        DataGrid<ChatContacts> dataGrid = new DataGrid<ChatContacts>();
        long total = chatContactsMapper.count(chatContacts);
        dataGrid.setTotal(total);
        int start = (Integer.valueOf(chatContacts.getPage()) - 1) * Integer.valueOf(chatContacts.getRows());
        chatContacts.setStart(String.valueOf(start));
        List<ChatContacts> list = chatContactsMapper.query(chatContacts);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public ChatContacts getById(String id) {
        return chatContactsMapper.getById(id);
    }

    public void insert(ChatContacts chatContacts) {
        if (chatContacts == null)
            throw new IllegalArgumentException("contact cannot be null!");
        chatContacts.preInsert();
        chatContactsMapper.insert(chatContacts);
    }

    public void update(ChatContacts chatContacts) {
        if (chatContacts == null)
            throw new IllegalArgumentException("contact cannot be null!");
        chatContacts.preUpdate();
        chatContactsMapper.update(chatContacts);
    }

    /**
     * 根据ID 集合删除 联系人信息
     */
    public void deleteByIdList(String ids) {
    	String[] array = ids.split(",");
		List<String> list = Arrays.asList(array);
		chatContactsMapper.deleteByIdList(list);
    }
    
    /**
     * 根据ID 删除联系人信息
     */
    public void delete(String id,String updateBy) {
    	ChatContacts chatContacts = new ChatContacts() ;
    	chatContacts.setId(id) ;
        chatContacts.preUpdate();
    	chatContactsMapper.delete(chatContacts);
    }

    /**
     * 添加好友到通讯录
     * @param contacts
     * @return
     */
    public String saveContacts(ChatContacts contacts) {
        String code = null ;        // 0:添加成功;1:好友已存在通讯录中;2:用户不存在

        ChatContacts temp = new ChatContacts() ;
        temp.setUserId(contacts.getUserId());
        temp.setFriendId(contacts.getFriendId());

        List<ChatContacts> list = findList(temp) ;
        if(list != null && !list.isEmpty() && list.size() > 0) {
            // 删除多余的通讯录
            for(int i = 1 ; i < list.size() ;i++) {
                delete(list.get(i).getId(),temp.getUserId());
            }
            if(contacts.getStatus().equals(list.get(0).getStatus())) {
                if(contacts.getStatus().equals("1")){
                    logger.warn("好友已存在通讯录中");
                    code = "1";
                }else{
                    if(list.get(0).getApplicantFlag().equals("1")){
                        logger.warn("已发送请求，等待对方接受");
                        code = "2";
                    }else{
                        logger.warn("该用户已向您发起好友请求，请接受");
                        code = "3";
                    }
                }
            }else {
                contacts.setId(list.get(0).getId());
            }
        }
        if(StringUtils.isBlank(code)) {
            UserProfile friend = userProfileMapper.selectByAccountNum(contacts.getFriendId());
            if (friend == null) {
                logger.warn("好友不存在");
                code = "4";
            } else {
                contacts.setAlias(friend.getName());
                contacts.setLetter(PinYin2Abbreviation.getFirstLetter(friend.getName()));
                if(StringUtils.isNotBlank(contacts.getId())) {
                    update(contacts);
                }else {
                    insert(contacts);
                }
                code = "0" ;
            }
        }
        return code ;
    }

    /**
     * 判断两个用户是否是好友
     * @param userId    用户编号
     * @param contactId 联系人编号
     * @return
     */
    public boolean isFriend(String userId ,String contactId) {
        boolean success = false ;

        ChatContacts contacts = new ChatContacts() ;
        contacts.setUserId(userId);
        contacts.setFriendId(contactId);

        List<ChatContacts> list = findList(contacts) ;
        if(list != null && !list.isEmpty()) {
            success = true ;
        }
        return success ;
    }

    /**
     * 判断是否正式好友新
     * @param userId
     * @param contactId
     * @return
     */
    public String isFriendNew(String userId ,String contactId) {
        String status = "0" ;                       //非好友

        ChatContacts contacts = new ChatContacts() ;
        contacts.setUserId(userId);
        contacts.setFriendId(contactId);
        contacts.setStatus("1");
        List<ChatContacts> list = findList(contacts) ;
        if(list != null && !list.isEmpty()) {
            status = "1";                           //正式好友狀態
        }else{
            contacts.setStatus("0");
            contacts.setApplicantFlag("1");
            list = findList(contacts) ;
            if(list != null && !list.isEmpty()){
                status = "2";                       //已發送好友請求，等待對方審核
            }else{
                contacts.setStatus("0");
                contacts.setApplicantFlag("0");
                list = findList(contacts) ;
                if(list != null && !list.isEmpty()){
                    status = "3";                   //等待當前用戶通過好友請求
                }
            }
        }

        return status ;
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
     * 查询联系人列表
     * @param message
     * @param content
     */
    public void findContactsList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        ChatContacts chatContacts = JSON.parseObject(content, ChatContacts.class);

        if (chatContacts == null || StringUtils.isBlank(chatContacts.getUserId())) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }

        // 1. 获得用户普通好友
        List<Map<String,String>> list = chatGroupService.fixPic(chatContactsMapper.queryContactsInterface(chatContacts));

        List<Map<String,Object>> resultList = Lists.newArrayList() ;
        Map<String,Object> contactMap = new HashMap<String,Object>() ;
        contactMap.put("groupName","我的好友") ;
        if(list != null && !list.isEmpty()) {
            contactMap.put("memberNum",String.valueOf(list.size())) ;
            contactMap.put("contacts",list) ;
        }else{
            contactMap.put("memberNum",0);
            contactMap.put("contacts",list);
        }
        resultList.add(contactMap);

        // 2. 获得用户所在群组
        ChatGroup group = new ChatGroup() ;
        group.setMemberUserId(chatContacts.getUserId());
        chatGroupService.checkClassesAndGroup(chatContacts.getUserId());
        List<Map<String,Object>> groupList = chatGroupMapper.queryGroupInterface(group) ;

        // 3. 获取每个群组中的用户
        if(groupList != null && groupList.size()>0) {
            for (Map<String,Object> temp : groupList) {
                ChatGroupUser groupUser = new ChatGroupUser() ;
                groupUser.setGroupId((String) temp.get("groupId"));
                groupUser.setUserId(chatContacts.getUserId());

                List<Map<String,String>> groupUserList = Lists.newArrayList() ;
                if("1".equals(temp.get("type"))) {
                    groupUserList = chatGroupService.fixPic(chatGroupUserMapper.queryClassGroupUserForApp(groupUser));
                } else {
                    temp.put("type","0") ;
                    groupUserList = chatGroupService.fixPic(chatGroupUserMapper.queryGroupUserForApp(groupUser));
                }
                temp.put("contacts",groupUserList) ;
            }

            resultList.addAll(groupList);
        }

        StringBuffer classBuffer = new StringBuffer();
        String clazz = StringUtils.isNotBlank(classBuffer) ? classBuffer.deleteCharAt(classBuffer.length() - 1).toString() : "处理成功";
        message.setMsg(clazz);
        message.setSuccess(true);
        message.setObj(resultList);
        return;
    }

    /**
     * 添加联系人
     * @param message
     * @param content
     */
    public void saveContacts(Message message, String content) {

        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        ChatContacts chatContacts = JSON.parseObject(content, ChatContacts.class);

        if (chatContacts == null ) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(chatContacts.getUserId())) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(chatContacts.getContactId())) {
            message.setMsg("未传添加好友用户编号编号");
            message.setSuccess(false);
            return;
        }
        chatContacts.setFriendId(chatContacts.getContactId());
        if (StringUtils.isBlank(chatContacts.getApplicantFlag())) {
            message.setMsg("未传申请者标识");
            message.setSuccess(false);
            return;
        }


        UserProfile userProfile = userProfileMapper.selectByAccountNum(chatContacts.getUserId());
        if(userProfile == null){
            message.setMsg("无效的用户ID");
            message.setSuccess(false);
            return;
        }
        UserProfile userProfileF = userProfileMapper.selectByAccountNum(chatContacts.getFriendId());
        if(userProfileF == null){
            message.setMsg("无效的好友ID");
            message.setSuccess(false);
            return;
        }


        chatContacts.setType("0");
        if(StringUtils.isBlank(chatContacts.getStatus())) {
            chatContacts.setStatus(("1".equals(chatContacts.getApplicantFlag()))?"0":"1");
        }

        ChatContacts otherContacts = new ChatContacts() ;
        otherContacts.setUserId(chatContacts.getFriendId());
        otherContacts.setFriendId(chatContacts.getUserId());
        otherContacts.setType(chatContacts.getType());
        otherContacts.setRemarks(chatContacts.getRemarks());


        if(chatContacts.getApplicantFlag().equals("1")){

        String code = isFriendNew(chatContacts.getUserId(), chatContacts.getContactId());
        if(code != null){
            if("1".equals(code)){
                message.setMsg("好友已存在您通讯录中");
                message.setSuccess(false);
                return;
            }else if("2".equals(code)){
                message.setMsg("已发送请求，等待对方接受");
                message.setSuccess(false);
                return;
            }else if("3".equals(code)){
                message.setMsg("该用户已向您发起好友请求，请接受");
                message.setSuccess(false);
                return;
            }
        }


            chatContacts.setStatus("0");
            chatContacts.setAlias(userProfileF.getName());
            chatContacts.setLetter(PinYin2Abbreviation.getFirstLetter(userProfileF.getName()));
            insert(chatContacts);

            otherContacts.setApplicantFlag("0");
            otherContacts.setStatus("0");
            otherContacts.setAlias(userProfile.getName());
            otherContacts.setLetter(PinYin2Abbreviation.getFirstLetter(userProfile.getName()));
            insert(otherContacts);

        }else{
            chatContacts.setApplicantFlag("");
            chatContacts.setStatus("");
            chatContacts.setDelFlag("");
            List<ChatContacts> list = findList(chatContacts);
            if(list != null && !list.isEmpty() && list.size() > 0){
                chatContacts = list.get(0);
                if(chatContacts.getStatus().equals("1")){
                    message.setMsg("已同意过该好友请求，不要重复操作");
                    message.setSuccess(false);
                    return;
                }

                List<ChatContacts> list2 = findList(otherContacts);
                if(list2 != null && !list2.isEmpty() && list2.size() > 0){
                    chatContacts.setStatus("1");
                    update(chatContacts);
                    otherContacts = list2.get(0);
                    otherContacts.setStatus("1");
                    update(otherContacts);
                }else{
                    message.setMsg("对方未发起好友请求");
                    message.setSuccess(false);
                    return;
                }
            }else{
                message.setMsg("对方未发起好友请求");
                message.setSuccess(false);
                return;
            }

        }


 /*       // 在本人通讯录中添加好友
        String code = saveContacts(chatContacts) ;
        if(code != null ) {
            switch (code) {
                case "1":
                    message.setMsg("好友已存在您通讯录中");
                    message.setSuccess(false);
                    return;
                case "2":
                    message.setMsg("已发送请求，等待对方接受");
                    message.setSuccess(false);
                    return;
                case "3":
                    message.setMsg("该用户已向您发起好友请求，请接受");
                    message.setSuccess(false);
                    return;
                case "4":
                    message.setMsg("好友用户不存在");
                    message.setSuccess(false);
                    return;
            }
        }

        // 在对方通讯录中添加好友
        ChatContacts otherContacts = new ChatContacts() ;
        otherContacts.setUserId(chatContacts.getFriendId());
        otherContacts.setFriendId(chatContacts.getUserId());
        otherContacts.setStatus(chatContacts.getStatus());
        otherContacts.setType(chatContacts.getType());
        otherContacts.setApplicantFlag(("1").equals(chatContacts.getApplicantFlag()) ? "0":"1");
        otherContacts.setRemarks(chatContacts.getRemarks());
        code = saveContacts(otherContacts) ;
        if(StringUtils.isNotBlank(code) && "2".equals(code)) {
            message.setMsg("用户不存在");
            message.setSuccess(false);
            return;
        }
*/
        PushUtils.pushFriendAddRequestOrAgree(otherContacts);
        message.setMsg("请求成功");
        message.setSuccess(true);

    }


    /**
     * 解除好友关系
     * @return
     */
    public void removeContacts(Message message, String content) {
        ChatContacts chatContacts = JSON.parseObject(content, ChatContacts.class);

        if(chatContacts == null) {
            logger.info("对象不存在");
            message.setMsg("对象不存在");
            message.setSuccess(false);
            return;
        }

        if(StringUtils.isBlank(chatContacts.getUserId())) {
            logger.warn("用户编号为空");
            message.setMsg("用户编号为空");
            message.setSuccess(false);
            return;
        }
        chatContacts.setFriendId(chatContacts.getContactId());
        if(StringUtils.isBlank(chatContacts.getFriendId())) {
            logger.warn("好友编号为空");
            message.setMsg("好友编号为空");
            message.setSuccess(false);
            return;
        }

        List<ChatContacts> list = findList(chatContacts) ;
        if(list == null || list.isEmpty()) {
            message.setMsg("非好友，不要乱删");
            message.setSuccess(false);
            return;
        }
        chatContactsMapper.theRealDelete(chatContacts);
/*        // 解除好友在本人通讯录中的显示
        List<ChatContacts> list = findList(chatContacts) ;
        if(list != null && !list.isEmpty()) {
            for(ChatContacts temp : list) {
                delete(temp.getId(),chatContacts.getUserId());
            }
        }
        // 解除本人在好友通讯录中的显示
        ChatContacts otherContacts = new ChatContacts();
        otherContacts.setUserId(chatContacts.getFriendId());
        otherContacts.setFriendId(chatContacts.getUserId());

        list = findList(otherContacts) ;
        if(list != null && !list.isEmpty()) {
            for(ChatContacts temp : list) {
                delete(temp.getId(),chatContacts.getUserId());
            }
        }*/

        String clazz = "删除成功";
        message.setMsg(clazz);
        message.setSuccess(true);
        return;
    }


    /**
     * 搜索好友(满足条件)
     * @param message
     * @param content
     */
    public void searchUserProfile(Message message, String content) {
        BaseInfo baseInfo = JSON.parseObject(content, BaseInfo.class);
        Map<String, Object> map = new HashMap<String, Object>();
        if(baseInfo.getName()!=null) {
            if(StringUtils.isNumeric(baseInfo.getName())){
                map.put("phoneNum", baseInfo.getName());
            }
            else if (isChinese(baseInfo.getName())) {
                map.put("name", baseInfo.getName());
            } else {
                List<String> accountList = userInfoMapper.findAccountByPinyin(baseInfo.getName().toLowerCase());
                if (accountList != null && accountList.size() > 0) {
                    map.put("accountList", accountList);
                } else {
                    message.setMsg("找不到结果集!");
                    message.setSuccess(false);
                    return;
                }
            }
        }
        if (baseInfo.getEntranceTime()!= null && baseInfo.getEntranceTime().length() > 0) {
            String[] birthdayArray =baseInfo.getEntranceTime().split(",");
            map.put("entranceTimeStart", birthdayArray[0]);
            map.put("entranceTimeEnd", birthdayArray[1]);
        }
        if (baseInfo.getSex() != null && baseInfo.getSex().length() > 0) {
            map.put("sex", baseInfo.getSex());
        }
        if (baseInfo.getProfession() != null && baseInfo.getProfession().length() > 0) {
            map.put("profession", baseInfo.getProfession());
        }
        if (baseInfo.getAddress() != null && baseInfo.getAddress().length() > 0) {
            map.put("address", baseInfo.getAddress());
        }
        if (baseInfo.getProfessionId() != null && baseInfo.getProfessionId().length() > 0) {
            map.put("profession", baseInfo.getProfessionId());
        }
        City city = cityMapper.selectByCityCode(baseInfo.getCityId());
        if (city != null && city.getCityName() !=null) {
            map.put("address", city.getCityName());
        }
        List<UserProfile> userInfos = userProfileMapper.selectAlumni(map);

        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (UserProfile userProfile2 : userInfos) {
            Map<String, String> entityMap = new HashMap<String, String>();
            entityMap.put("userId", StringUtils.isBlank(userProfile2.getAccountNum()) ? "" : userProfile2.getAccountNum());// 用户编号
            entityMap.put("userName", StringUtils.isBlank(userProfile2.getName()) ? "" : userProfile2.getName());// 姓名
            entityMap.put("sex", StringUtils.isBlank(userProfile2.getSex()) ? "" : userProfile2.getSex());// 性别
            entityMap.put("profession", StringUtils.isBlank(userProfile2.getProfession()) ? "" : userProfile2.getProfession());// 行业
            entityMap.put("address", StringUtils.isBlank(userProfile2.getAddress()) ? "" : userProfile2.getAddress());// 地点
            entityMap.put("hobby", StringUtils.isBlank(userProfile2.getHobby()) ? "" : userProfile2.getHobby());// 兴趣
            entityMap.put("pictureUrl", StringUtils.isBlank(userProfile2.getPicture()) ? "" : userProfile2.getPicture()); // 用户图片
            entityMap.put("baseInfoId", userProfile2.getBaseInfoId());
            entityMap.put("workUtil", StringUtils.isBlank(userProfile2.getWorkUtil()) ? "" : userProfile2.getWorkUtil());
            entityMap.put("departName", StringUtils.isBlank(userProfile2.getGroupName()) ? "" : userProfile2.getGroupName());
            entityMap.put("isFriend", isFriendNew(baseInfo.getCurrUserId(),userProfile2.getAccountNum()));     // 用户与当前用户是否是好友关系(0:不是;1:是)
            entityMap.put("pictureRT", StringUtils.isBlank(userProfile2.getPictureUrl())?"":userProfile2.getPictureUrl());
            entityMap.put("pictureUrl_xd", StringUtils.isBlank(userProfile2.getPicture_xd())?"":userProfile2.getPicture_xd());
            mapList.add(entityMap);
        }

        if (mapList == null || mapList.size() == 0) {
            message.setMsg("找不到结果集!");
            message.setSuccess(false);
            return;
        } else {
            message.setMsg("查询成功!");
            message.setSuccess(true);
            message.setObj(mapList);
            return;
        }
    }

    /**
     * 查询好友详情/用户详情
     * @param message
     * @param content
     */
    public void findUserProfile(Message message, String content) {
        BaseInfo baseInfo = JSON.parseObject(content, BaseInfo.class);

        if (StringUtils.isBlank(baseInfo.getCurrUserId()) || StringUtils.isBlank(baseInfo.getUserId())) {// 非空检查
            message.setMsg("当前用户编号、查看用户信息的用户编号不能为空");
            message.setSuccess(false);
            return;
        }

        UserProfile userProfile = userProfileMapper.selectByAccountNum(baseInfo.getCurrUserId());
        if (userProfile == null) {
            message.setMsg("查询不到此账号!");
            message.setSuccess(false);
            return;
        }

        UserProfile userInfo = userProfileMapper.selectByAccountNum(baseInfo.getUserId());

        Map<String, String> entityMap = new HashMap<String, String>();
        if(userInfo != null) {
            entityMap.put("userId", StringUtils.isBlank(userInfo.getAccountNum()) ? "" : userInfo.getAccountNum());// 用户编号
            entityMap.put("accountNum", StringUtils.isBlank(userInfo.getAccountNum()) ? "" : userInfo.getAccountNum());// 用户编号
            entityMap.put("userName", StringUtils.isBlank(userInfo.getName()) ? "" : userInfo.getName());// 姓名
            entityMap.put("sex", StringUtils.isBlank(userInfo.getSex()) ? "" : userInfo.getSex());// 性别
            entityMap.put("profession", StringUtils.isBlank(userInfo.getProfession()) ? "" : userInfo.getProfession());// 行业
            entityMap.put("address", StringUtils.isBlank(userInfo.getAddress()) ? "" : userInfo.getAddress());// 地点
            entityMap.put("hobby", StringUtils.isBlank(userInfo.getHobby()) ? "" : userInfo.getHobby());// 兴趣
            entityMap.put("pictureUrl", StringUtils.isBlank(userInfo.getPicture()) ? "" : userInfo.getPicture()); // 用户图片
            entityMap.put("pictureUrl_xd", StringUtils.isBlank(userInfo.getPicture_xd()) ? "" : userInfo.getPicture_xd());
            entityMap.put("baseInfoId", userInfo.getBaseInfoId());
            entityMap.put("workUtil", StringUtils.isBlank(userInfo.getWorkUtil()) ? "" : userInfo.getWorkUtil());
            entityMap.put("departName", StringUtils.isBlank(userInfo.getGroupName()) ? "" : userInfo.getGroupName());
            entityMap.put("isFriend", isFriendNew(baseInfo.getCurrUserId(),userInfo.getAccountNum()));     // 用户与当前用户是否是好友关系(0:不是;1:是)
            entityMap.put("phoneNum", StringUtils.isBlank(userInfo.getPhoneNum())?"":userInfo.getPhoneNum());
        }

        if (entityMap == null || entityMap.isEmpty()) {
            message.setMsg("找不到结果集!");
            message.setSuccess(false);
            return;
        } else {
            message.setMsg("查询成功!");
            message.setSuccess(true);
            message.setObj(entityMap);
            return;
        }
    }

    /**
     * 查询我的好友列表
     * @param message
     * @param content
     */
    public void findFriendsList(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);

        if (map == null || StringUtils.isBlank(map.get("userId"))) {
            message.setMsg("未传入用户编号");
            message.setSuccess(false);
            return;
        }

        List<Map<String,String>> list = chatGroupService.fixPic(chatContactsMapper.findContactsInfo(map));

        if("1".equals(map.get("orderByPinYin"))){
            //Map<String, Object> listGroup = SortByPinyinUtils.sortAndGroupByPinyin(list);
            List<Map<String,String>> listSort = SortByPinyinUtils.sortByPinyin(list);
            message.init(true, "获取排序后列表成功", listSort);
            return;
        }
        message.setObj(list);
        message.setMsg("获取好友列表成功");
        message.setSuccess(true);
    }

    /**
     * 查询我的推荐好友
     * @param message
     * @param content
     */
    public void getCommendFriendList(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //当前页
        String page = (String) map.get("page");
        //一页几行
        String rows = (String) map.get("rows");
        //用户ID
        String userId = (String) map.get("userId");
        if (map == null || StringUtils.isBlank(userId)){
            message.setMsg("未传入用户编号");
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
        //用户关系表中查询userId列表
        List<UserProfile> list = chatContactsMapper.queryUserList(map);
        List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
        //循环好友列表获取和每个好友的共同好友
        for(int i=0;i<list.size();i++) {
            map.put("maybeFriendId", list.get(i).getAccountNum());
            //获取共同好友
            List<String> commendList = chatContactsMapper.getCommendFriendList(map);
            if(commendList.size()!=0) {
                Map<String, Object> commendMap = new HashMap<String, Object>();
                //通过accountNum查询用户信息
                UserProfile userProfile = userProfileMapper.selectByAccountNum(list.get(i).getAccountNum());
                //commendMap.put("userId", map.get("userId"));
                commendMap.put("maybeFriendId", list.get(i).getAccountNum());
                commendMap.put("maybeFriendName",userProfile.getName());
                commendMap.put("maybeFriendPicture", userProfile.getPicture());
                commendMap.put("maybeFriendSign", userProfile.getSign());
                commendMap.put("maybeFriendSex", userProfile.getSex());
                commendMap.put("commendList", commendList);
                commendMap.put("commendCount", commendList.size());
                returnList.add(commendMap);
            }
        }
        //sort(returnList);
        message.setObj(returnList);
        message.setMsg("获取推荐好友成功");
        message.setSuccess(true);
    }

    private static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }
    private static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
}
