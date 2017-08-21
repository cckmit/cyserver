package com.cy.util.easemob.comm.utils;

import com.cy.util.easemob.comm.body.ResetPasswordBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.util.easemob.api.ChatGroupAPI;
import com.cy.util.easemob.api.ChatMessageAPI;
import com.cy.util.easemob.api.ChatRoomAPI;
import com.cy.util.easemob.api.FileAPI;
import com.cy.util.easemob.api.IMUserAPI;
import com.cy.util.easemob.api.SendMessageAPI;
import com.cy.util.easemob.comm.ClientContext;
import com.cy.util.easemob.comm.EasemobRestAPIFactory;
import com.cy.util.easemob.comm.body.ChatGroupBody;
import com.cy.util.easemob.comm.wrapper.ResponseWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * <p>Title: EasemobUtils</p>
 * <p>Description: 环信工具类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-12 下午3:18:09
 */
public class EasemobUtils extends EasemobBaseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasemobUtils.class);

    private final static String GROUP_OWER_INIT = "init" ;  // 环信群组初始管理员账号
////////////////////////////////////////////////////////用户////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
//    	LOGGER.debug(createNewGroupSingle("测试3组", "这是一个测试三组", false, true, 100L, "User101", null) );
        LOGGER.debug(resetIMUserPasswordWithAdminToken("User101","111111").toString());
    }

    /**
     * 判断用户名是否存在
     * @param username
     * @return true：存在；false：不存在
     */
    public static Boolean existsUser(String username) {
        boolean exists = false ;
        ResponseWrapper response = getIMUsersByUserName(username);
        if(response.getResponseStatus() == 200) {
        	exists = true ;
        }
        return exists ;
    }


    /**
     * 判断用户名是否存在,如不存在则注册
     * @param username
     * @return true：存在；false：不存在
     */
    public static void existsUserAndCreate(String username ,String password ,String nickname) {
        boolean exists = false ;
        ResponseWrapper response = getIMUsersByUserName(username);
        if(response.getResponseStatus() != 200) {
        	createNewIMUserSingle(username,password,nickname) ;
        }
    }




    /**
     * 注册IM用户[单个]
     * <p/>
     * 给指定AppKey创建一个新的用户
     * <p/>
     * LiuZhen - 2015-11-06
     * @param username
     * @param password
     * @return
     */
    public static PairUtil<Boolean,String> createNewIMUser(String username ,String password, String nickName) {
        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
        boolean success = false ;
        String msg = null ;

        ResponseWrapper response = createNewIMUserSingle(username,password,null) ;

        if(response.getResponseStatus() != 200) {
            success = true;
        }
        msg = response.toString() ; ;

        result.setOne(success);
        result.setTwo(msg);
        return result ;
    }
//
//    /**
//     * 删除IM用户[单个]
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param username 用户名
//     * @return one : true 成功；false : 失败；<p/> two : 错误消息
//     */
//    public static PairUtil<Boolean ,String> deleteIMUser(String username) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//        ObjectNode deleteIMUserByUserNameNode = deleteIMUserByUserName(username);
//
//        PairUtil<String,String> pair = analysisResult(deleteIMUserByUserNameNode) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }
//
//    /**
//     * 用户登录，并获取登录用户对象
//     * @param username
//     * @param password
//     * @return
//     */
//    public static EasemobUser loginIMUser(String username ,String password ) {
//        EasemobUser user = new EasemobUser() ;
//        ObjectNode imUserLoginNode = imUserLogin(username, password);
//        if (null != imUserLoginNode) {
//            LOGGER.info("获取IM用户[主键查询]: " + imUserLoginNode.toString());
//            PairUtil<String,String> pair = analysisResult(imUserLoginNode) ;
//
//            String statusCode = pair.getOne() ;
//            if("200".equals(statusCode.trim())) {
//                JsonNode userJson = imUserLoginNode.get("user") ;
//                if(userJson != null) {
//                    if (userJson.has("uuid")) {
//                        String userStr = userJson.toString() ;
//                        userStr = userStr.replace("uuid","id") ;
//                        System.out.println(userStr);
//                        JSONObject json = JSONObject.fromObject(userStr) ;
//
//                        user = (EasemobUser) JSONObject.toBean(json,EasemobUser.class) ;
//                        ;
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
//                        Date date = new Date() ;
//                        date.setTime(Long.valueOf(user.getCreated()));
//                        user.setCreateDate(sdf.format(date));
//
//                        date.setTime(Long.valueOf(user.getModified()));
//                        user.setUpdateDate(sdf.format(date));
//                    }
//                }
//            }
//        }
//        return user ;
//    }
//
//
    /**
     * 重置IM用户密码 提供管理员token
     *
     * @param userName
     * @param password
     * @return
     */
    public static PairUtil<Boolean,String> resetIMUserPasswordWithAdminToken(
            String userName, String password) {
        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
        boolean success = false ;
        String msg = null ;
        ResetPasswordBody resetPasswordBody = new ResetPasswordBody(password) ;

        ResponseWrapper response = modifyIMUserPasswordWithAdminToken(userName,resetPasswordBody) ;

        if(response.getResponseStatus() == 200) {
            success = true ;
        }
        msg = response.toString() ;
        result.setOne(success);
        result.setTwo(msg);
        return result ;
    }

////////////////////////////////////////////////////////聊天记录////////////////////////////////////////////////////////////////////////////
//    /**
//     * 根据时间获取聊天记录
//     * @return
//     */
//    public static String getChatMessagesByTime(String timeMillis) {
//        String result = null;
//        String currentTimestamp = String.valueOf(System.currentTimeMillis());
////        String senvenDayAgo = String.valueOf(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
//        String senvenDayAgo = timeMillis;
//        ObjectNode queryStrNode = getFactory().objectNode();
//        queryStrNode.put("from","5202f41a15af413bad637ed5b08eb7a0") ;
//        queryStrNode.put("to","f06ebec81a1a496e9ad95991fbbecc7f") ;
//        queryStrNode.put("ql", "select * where  timestamp > " + senvenDayAgo + " and timestamp < " + currentTimestamp);
//        ObjectNode messages = getChatMessages(queryStrNode);
//        System.out.println("--------> time message" + messages);
//        return result ;
//    }
//
//    /**
//     * 根据分页获取聊天记录
//     * @return
//     */
//    public static String getChatMessagesByLimit(String pageSize) {
//        String result = null;
//        ObjectNode queryStrNode = getFactory().objectNode();
//        if(StringUtils.isNotBlank(pageSize)) {
//            queryStrNode.put("limit", pageSize);
//        }
//        ObjectNode messages = getChatMessages(queryStrNode);
//        return result ;
//    }


////////////////////////////////////////////////////////群组////////////////////////////////////////////////////////////////////////////
    /**
     * 创建群组[单个]
     * <p/>
     * 给指定AppKey创建一个新的用户
     * <p/>
     * LiuZhen - 2015-11-06
     * @param groupName 群组名称
     * @param desc       群组描述
     * @param approval  加入公开群是否需要批准, 默认值是false（加如公开群不需要群主批准）, 此属性为必选的，私有群必须为true
     * @param isPublic        是否是公开群, 此属性为必须的
     * @param maxUsers  群组成员最大数(包括群主), 值为数值类型,默认值200,此属性为可选的
     * @param owner     群主的username， 例如：{“owner”: “13800138001”}
     * @return
     */
    public static String createNewGroupSingle(String groupName
            ,String desc ,boolean approval ,boolean isPublic ,Long maxUsers ,String owner
            ,String[] members) {
        ChatGroupBody groupBody = new ChatGroupBody(groupName, desc, isPublic, maxUsers, approval, owner, members) ;
        ResponseWrapper response = creatChatGroups(groupBody);
        System.out.println("--------------------> 环信创建群组："+response) ;
        JsonNode dataJson = ((JsonNode)response.getResponseBody()).get("data") ;
        if(dataJson != null) {
            String groupId = dataJson.get("groupid").toString() ;
            if(groupId != null && !"".equals(groupId.trim())) {
                groupId = groupId.trim().replaceAll("\"","") ;
            }
            return groupId ;
        }
        return null ;
    }

    /**
     * 创建群组[单个]
     * <p/>
     * 给指定AppKey创建一个新的用户
     * <p/>
     * LiuZhen - 2015-11-06
     * @param groupName 群组名称
     * @param desc       群组描述
     * @param approval  加入公开群是否需要批准, 默认值是false（加如公开群不需要群主批准）, 此属性为必选的，私有群必须为true
     * @param isPublic        是否是公开群, 此属性为必须的
     * @param maxUsers  群组成员最大数(包括群主), 值为数值类型,默认值200,此属性为可选的
     * @return
     */
    public static String createNewGroupSingle(String groupName
            ,String desc ,boolean approval ,boolean isPublic ,Long maxUsers ,String[] members) {
        boolean initOwerIsExists = existsUser(GROUP_OWER_INIT) ;
        if (!initOwerIsExists) {
            createNewIMUserSingle(GROUP_OWER_INIT,"111111",GROUP_OWER_INIT) ;
        }
        ChatGroupBody groupBody = new ChatGroupBody(groupName, desc, isPublic, maxUsers, approval, GROUP_OWER_INIT, members) ;
        ResponseWrapper response = creatChatGroups(groupBody);
        System.out.println("--------------------> 环信创建群组："+response) ;
        JsonNode dataJson = ((JsonNode)response.getResponseBody()).get("data") ;
        if(dataJson != null) {
            String groupId = dataJson.get("groupid").toString() ;
            if(groupId != null && !"".equals(groupId.trim())) {
                groupId = groupId.trim().replaceAll("\"","") ;
            }
            return groupId ;
        }
        return null ;
    }

//    /**
//     * 修改群组[单个]
//     * <p/>
//     * 给指定AppKey创建一个新的用户
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param groupname
//     * @param desc
//     * @param maxusers
//     * @return
//     */
//    public static PairUtil<Boolean ,String>  changeGroup(String groupId ,String groupname ,String desc ,Long maxUsers) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//
//        ResponseWrapper response = changeChatGroups(groupId, groupname, desc, maxUsers);
//        System.out.println("--------------------> 环信修改群组："+response) ;
//        PairUtil<String,String> pair = analysisResult(response) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }
//
//    /**
//     * 删除群组[单个]
//     * <p/>
//     * 给指定AppKey创建一个新的用户
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param groupId
//     * @return
//     */
//    public static PairUtil<Boolean ,String>  deleteGroup(String groupId ) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//
//        ObjectNode deleteChatGroupNode = deleteChatGroups(groupId);
//        System.out.println("--------------------> 环信删除群组："+deleteChatGroupNode) ;
//        PairUtil<String,String> pair = analysisResult(deleteChatGroupNode) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }
//    /**
//     * 判断群组是否存在[单个]
//     * <p/>
//     * 给指定AppKey创建一个新的用户
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param groupId
//     * @return
//     */
//    public static PairUtil<Boolean ,String>  isExistsGroup(String groupId ) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//
//        String[] chatgroupIDs = {groupId};
//        ObjectNode chatGroupNode = getGroupDetailsByChatgroupid(chatgroupIDs);
//
//        System.out.println("--------------------> 获取环信群组："+chatGroupNode) ;
//        PairUtil<String,String> pair = analysisResult(chatGroupNode) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }
//
//    /**
//     * 群组加人[单个]
//     * <p/>
//     * 给指定AppKey创建一个新的用户
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param groupId
//     * @return
//     */
//    public static PairUtil<Boolean ,String> addMemberToGroup(String groupId ,String member) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//
//        ObjectNode addMemberToGroup = addUserToGroup(groupId,member);
//
//        System.out.println("--------------------> 环信群组添加成员："+addMemberToGroup) ;
//        PairUtil<String,String> pair = analysisResult(addMemberToGroup) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }
//
//    /**
//     * 群组加人[多个]
//     * <p/>
//     * 给指定AppKey创建一个新的用户
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param groupId
//     * @return
//     */
//    public static PairUtil<Boolean ,String> addMembersToGroup(String groupId ,String[] members) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//
//        ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
//        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
//        if(members != null) {
//            for (String member : members) {
//                arrayNode.add(member);
//            }
//        }
//        dataObjectNode.put("usernames", arrayNode);
//
//        ObjectNode addMembersToGroup = addUsersToGroup(groupId,dataObjectNode);
//
//        System.out.println("--------------------> 环信群组添加成员："+addMembersToGroup) ;
//        PairUtil<String,String> pair = analysisResult(addMembersToGroup) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }
//    /**
//     * 删除群组中的一个成员[单个]
//     * <p/>
//     * 给指定AppKey创建一个新的用户
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param groupId
//     * @return
//     */
//    public static PairUtil<Boolean ,String> deleteMemberFromGroup(String groupId ,String member) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//
//        ObjectNode deleteUserFromGroup = deleteUserFromGroup(groupId, member);
//
//        System.out.println("--------------------> 删除环信群组中成员："+deleteUserFromGroup) ;
//        PairUtil<String,String> pair = analysisResult(deleteUserFromGroup) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }
//
//    /**
//     * 删除群组中的一个成员[多个]
//     * <p/>
//     * 给指定AppKey创建一个新的用户
//     * <p/>
//     * LiuZhen - 2015-11-06
//     * @param groupId
//     * @return
//     */
//    public static PairUtil<Boolean ,String> deleteMembersFromGroup(String groupId ,String[] members) {
//        PairUtil<Boolean,String> result = new PairUtil<Boolean ,String>() ;
//        boolean success = false ;
//        String msg = null ;
//        String users = "" ;
//        if(members != null) {
//            for (int i = 0 ; i < members.length ; i++) {
//                users += members[i] ;
//                if(i < members.length - 1) {
//                    users += ",";
//                }
//            }
//        }
//
//        ObjectNode deleteUsersFromGroup = deleteUsersFromGroup(groupId, users);
//
//        System.out.println("--------------------> 环信群组删除成员："+deleteUsersFromGroup) ;
//        PairUtil<String,String> pair = analysisResult(deleteUsersFromGroup) ;
//
//        String statusCode = pair.getOne() ;
//        msg = pair.getTwo() ;
//        if("200".equals(statusCode.trim())) {
//            success = true;
//        }
//
//        result.setOne(success);
//        result.setTwo(msg);
//        return result ;
//    }

}
