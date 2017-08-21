package com.cy.util.easemob.comm.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

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
import com.cy.util.easemob.comm.body.GroupOwnerTransferBody;
import com.cy.util.easemob.comm.body.IMUserBody;
import com.cy.util.easemob.comm.body.IMUsersBody;
import com.cy.util.easemob.comm.body.MessageBody;
import com.cy.util.easemob.comm.body.ModifyChatGroupBody;
import com.cy.util.easemob.comm.body.ModifyNicknameBody;
import com.cy.util.easemob.comm.body.ResetPasswordBody;
import com.cy.util.easemob.comm.body.UserNamesBody;
import com.cy.util.easemob.comm.wrapper.BodyWrapper;
import com.cy.util.easemob.comm.wrapper.ResponseWrapper;
import com.fasterxml.jackson.databind.node.ContainerNode;

/**
 * 
 * <p>Title: EasemobBaseUtils</p>
 * <p>Description: 环信基础工具类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-12 下午3:18:40
 */
public class EasemobBaseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EasemobBaseUtils.class);

    private static final String MSG_REQUEST_SUCCESS = "请求处理成功" ;
    private static final String MSG_REQUEST_FAIL = "请求处理失败" ;
    private static final String MSG_CREATE_USER_FAIL = "环信用户注册失败" ;
    private static final String MSG_CREATE_USER_FAIL_EXISTS = "环信用户已存在" ;
    private static final String MSG_CREATE_USER_FAIL_ILLEGAL_ARGUMENT = "环信用户名不合法" ;
    private static final String MSG_ERR_SERVICE_RESOURCE_NOT_FOUND = "URL指定的资源不存在" ;

    private static EasemobRestAPIFactory factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
	
    private static IMUserAPI user = (IMUserAPI)factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
    private static ChatMessageAPI chat = (ChatMessageAPI)factory.newInstance(EasemobRestAPIFactory.MESSAGE_CLASS);
    private static FileAPI file = (FileAPI)factory.newInstance(EasemobRestAPIFactory.FILE_CLASS);
    private static SendMessageAPI message = (SendMessageAPI)factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
    private static ChatGroupAPI chatgroup = (ChatGroupAPI)factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
    private static ChatRoomAPI chatroom = (ChatRoomAPI)factory.newInstance(EasemobRestAPIFactory.CHATROOM_CLASS);
    
    
    public static EasemobRestAPIFactory getFactory() {
        return factory;
    }
    
////////////////////////////////////////////////////////用户////////////////////////////////////////////////////////////////////////////

    /**
     * 注册IM用户[单个]
     * <p/>
     * 给指定AppKey创建一个新的用户
     *
     * @param dataNode
     * @return
     */
    public static ResponseWrapper createNewIMUserSingle(String userName, String password, String nickName) {
    	BodyWrapper userBody = new IMUserBody(userName, password, nickName);
    	ResponseWrapper response = (ResponseWrapper) user.createNewIMUserSingle(userBody);
    	return response ;
    }

    /**
     * 注册IM用户[批量]
     * <p/>
     * 给指定AppKey创建一批用户
     *
     * @param dataArrayNode
     * @return
     */
    public static ResponseWrapper createNewIMUserBatch(List<IMUserBody> users) {
		BodyWrapper usersBody = new IMUsersBody(users);
		ResponseWrapper response = (ResponseWrapper) user.createNewIMUserBatch(usersBody);
        return response;
    }

    /**
     * 获取IM用户
     *
     * @param userName 用户主键：username或者uuid
     * @return
     */
    public static ResponseWrapper getIMUsersByUserName(String userName) {
		ResponseWrapper response = (ResponseWrapper) user.getIMUsersByUserName(userName) ;
		return response ;
    }

    /**
     * 删除IM用户[单个]
     * <p/>
     * 删除指定AppKey下IM单个用户
     *
     * @param userName
     * @return
     */
    public static ResponseWrapper deleteIMUserByUserName(String userName) {
    	ResponseWrapper response = (ResponseWrapper) user.deleteIMUserByUserName(userName) ;
    	return response ;
    }

    /**
     * 删除IM用户[批量]
     * <p/>
     * 批量指定AppKey下删除IM用户
     *
     * @param limit
     * @param queryStr
     * @return
     */
    public static ResponseWrapper deleteIMUserByUsernameBatch(Long limit,
                                                         String queryStr) {
    	ResponseWrapper response = (ResponseWrapper) user.deleteIMUserBatch(limit);
    	return response ;
    }

    /**
     * 重置IM用户密码 提供管理员token
     *
     * @param userName
     * @param dataObjectNode
     * @return
     */
    public static ResponseWrapper modifyIMUserPasswordWithAdminToken(
            String userName, ResetPasswordBody passwordBody) {
    	ResponseWrapper response = (ResponseWrapper) user.modifyIMUserPasswordWithAdminToken(userName, passwordBody) ;
    	return response ;
    }
    
    /**
     * 修改IM用户昵称 提供管理员token
     *
     * @param userName
     * @param dataObjectNode
     * @return
     */
    public static ResponseWrapper modifyIMUserPasswordWithAdminToken(
    		String userName,ModifyNicknameBody  nicknameBody) {
    	ResponseWrapper response = (ResponseWrapper) user.modifyIMUserNickNameWithAdminToken(userName, nicknameBody);
    	return response ;
    }

    /**
     * 添加好友[单个]
     *
     * @param ownerUserName
     * @param friendUserName
     * @return
     */
    public static ResponseWrapper addFriendSingle(String ownerUserName,
                                             String friendUserName) {
    	ResponseWrapper response = (ResponseWrapper) user.addFriendSingle(ownerUserName, friendUserName) ;
    	return response ;
    }

    /**
     * 解除好友关系
     *
     * @param ownerUserName
     * @param friendUserName
     * @return
     */
    public static ResponseWrapper deleteFriendSingle(String ownerUserName,
                                                String friendUserName) {
    	ResponseWrapper response = (ResponseWrapper) user.deleteFriendSingle(ownerUserName, friendUserName) ;
    	return response ;
    }

    /**
     * 查看好友
     *
     * @param ownerUserName
     * @return
     */
    public static ResponseWrapper getFriends(String ownerUserName) {
    	ResponseWrapper response = (ResponseWrapper) user.getFriends(ownerUserName) ;
    	return response ;
    }

    /**
     * IM用户登录
     *
     * @param ownerUserName
     * @param password
     * @return
     */
    public static ResponseWrapper imUserLogin(String ownerUserName, String password) {
//    	ResponseWrapper response = (ResponseWrapper) user.(ownerUserName) ;
    	ResponseWrapper response = new ResponseWrapper() ;
    	return response ;
    }

//    /**
//     * 指定前缀和数量生成用户基本数据
//     *
//     * @param usernamePrefix
//     * @param number
//     * @return
//     */
//    private static ArrayNode genericArrayNode(String usernamePrefix, Long number) {
//        ArrayNode arrayNode = factory.arrayNode();
//        for (int i = 0; i < number; i++) {
//            ObjectNode userNode = factory.objectNode();
//            userNode.put("username", usernamePrefix + "_" + i);
//            userNode.put("password", Constants.DEFAULT_PASSWORD);
//
//            arrayNode.add(userNode);
//        }
//
//        return arrayNode;
//    }

////////////////////////////////////////////////////////群组管理////////////////////////////////////////////////////////////////////////////
    /**
     * 获取APP中所有的群组ID
     *
     * @return
     */
    public static ResponseWrapper getChatgroups(Long limit, String cursor) {
    	ResponseWrapper response = (ResponseWrapper) chatgroup.getChatGroups(limit, cursor) ;
    	return response ;
    }

    /**
     * 获取一个或者多个群组的详情
     *
     * @return
     */
    public static ResponseWrapper getGroupDetailsByChatgroupid(String[] chatgroupIDs) {
    	ResponseWrapper response = (ResponseWrapper) chatgroup.getChatGroupDetails(chatgroupIDs);
    	return response ;
    }

    /**
     * 创建群组
     */
    public static ResponseWrapper creatChatGroups(ChatGroupBody groupBody) {
    	ResponseWrapper response = (ResponseWrapper) chatgroup.createChatGroup(groupBody);
    	return response ;
    }

    /**
     * 修改群组
     */
    public static ResponseWrapper changeChatGroups(String chatgroupid,String groupName, String desc, Long maxUsers) {

        ModifyChatGroupBody modifyChatGroupBody = new ModifyChatGroupBody(groupName, desc, maxUsers) ;
        ResponseWrapper response = (ResponseWrapper) chatgroup.modifyChatGroup(chatgroupid, modifyChatGroupBody) ;
        return response ;
    }

    /**
     * 删除群组
     */
    public static ResponseWrapper deleteChatGroups(String chatgroupid) {
        ResponseWrapper response = (ResponseWrapper) chatgroup.deleteChatGroup(chatgroupid) ;
        return response ;
    }

    /**
     * 获取群组中的所有成员
     */
    public static ResponseWrapper getAllMemberssByGroupId(String chatgroupid) {
        ResponseWrapper response = (ResponseWrapper) chatgroup.getChatGroupUsers(chatgroupid) ;
        return response ;
    }

    /**
     * 在群组中添加一个人
     */
    public static ResponseWrapper addUserToGroup(String chatgroupid, String userName) {

        ResponseWrapper response = (ResponseWrapper) chatgroup.addSingleUserToChatGroup(chatgroupid,userName) ;
        return response ;
    }

    /**
     * 在群组中添加一个或多个成员
     */
    public static ResponseWrapper addUsersToGroup(String chatgroupid, String[] users) {
    	UserNamesBody userNamesBody = new UserNamesBody(users) ;
        ResponseWrapper response = (ResponseWrapper) chatgroup.addBatchUsersToChatGroup(chatgroupid,userNamesBody) ;
        return response ;
    }

    /**
     * 在群组中减少一个人
     */
    public static ResponseWrapper deleteUserFromGroup(String chatgroupid, String userName) {
        ResponseWrapper response = (ResponseWrapper) chatgroup.removeSingleUserFromChatGroup(chatgroupid,userName) ;
        return response ;
    }

    /**
     * 删除群组中一个或多个成员
     * 多个用户用逗号隔开如“user1,user2,user3”
     */
    public static ResponseWrapper deleteUsersFromGroup(String chatgroupid, String users) {
    	String[] userIds = users.split(",") ;
        ResponseWrapper response = (ResponseWrapper) chatgroup.removeBatchUsersFromChatGroup(chatgroupid,userIds) ;
        return response ;
    }

    /**
     * 获取一个用户参与的所有群组
     *
     * @param username
     * @return
     */
    public static ResponseWrapper getJoinedChatgroupsForIMUser(String userName) {
        ResponseWrapper response = (ResponseWrapper) user.getIMUserAllChatGroups(userName) ;
        return response ;
    }
    
    /**
     * 群组转让
     *
     * @param newowner
     * @return
     */
    public static ResponseWrapper transferChatGroupOwner(String groupId ,String newowner) {
    	GroupOwnerTransferBody groupOwnerTransferBody = new GroupOwnerTransferBody(newowner) ;
    	ResponseWrapper response = (ResponseWrapper) chatgroup.transferChatGroupOwner(groupId, groupOwnerTransferBody) ;
    	return response ;
    }


////////////////////////////////////////////////////////发送消息////////////////////////////////////////////////////////////////////////////
    /**
     * 检测用户是否在线
     *
     * @param targetUserName
     * @return
     */
    public static ResponseWrapper getUserStatus(String targetUserName) {
        ResponseWrapper response = (ResponseWrapper) user.getIMUserStatus(targetUserName) ;
        return response ;
    }

    /**
     * 检测用户是否在线
     * <p/>
     * LiuZhen - 2015-11-06
     * @param targetUserName
     * @return
     */
    public static boolean getUserIsOnline(String targetUserName) {
        boolean isOnline = false ;

        ResponseWrapper response = getUserStatus(targetUserName) ;
        if(response.getResponseStatus() == 200) {
        	isOnline = true ;
        }

        return isOnline ;
    }

    /**
     * 发送消息
     *
     * @param targetType 消息投递者类型：users 用户, chatgroups 群组
     * @param target     接收者ID 必须是数组,数组元素为用户ID或者群组ID
     * @param from       发送者
     * @param ext        消息内容
     * @return 请求响应
     */
    public static ResponseWrapper sendMessages(String targetType, String[] targets, String from, Map<String, String> ext) {
    	MessageBody messageBody = new MessageBody(targetType ,targets ,from ,ext) {
			
			@Override
			public ContainerNode<?> getBody() {
		        return getMsgBody();
			}
		};
        ResponseWrapper response = (ResponseWrapper) message.sendMessage(messageBody);
        return response ;
    }



////////////////////////////////////////////////////////REST API Demo : 图片语音文件上传、下载 Jersey2.9实现////////////////////////////////////////////////////////////////////////////
    /**
     * 图片/语音文件上传
     *
     * @param uploadFile
     */
    public static ResponseWrapper mediaUpload(File uploadFile) {
        ResponseWrapper response = (ResponseWrapper) file.uploadFile(uploadFile) ;
        return response ;
    }

    /**
     * 图片语音文件下载
     *
     * @param fileUUID    文件在DB的UUID
     * @param shareSecret 文件在DB中保存的shareSecret
     * @param localPath   下载后文件存放地址
     * @param isThumbnail 是否下载缩略图 true:缩略图 false:非缩略图
     * @return
     */
    public static ResponseWrapper mediaDownload(String fileUUID, String shareSecret, File localPath, Boolean isThumbnail) {
        ResponseWrapper response = (ResponseWrapper) file.downloadFile(fileUUID, shareSecret, isThumbnail) ;
        return response ;
    }

////////////////////////////////////////////////////////聊天记录////////////////////////////////////////////////////////////////////////////
//    /**
//     * 获取聊天消息
//     *
//     * @param queryStrNode
//     */
//    public static ResponseWrapper getChatMessages(ObjectNode queryStrNode) {
//        ResponseWrapper response = (ResponseWrapper) file.downloadFile(fileUUID, shareSecret, isThumbnail) ;
//        return response ;
//    }


////////////////////////////////////////////////////////解析结果////////////////////////////////////////////////////////////////////////////
//    /**
//     * 解析错误消息
//     * @param errNode
//     * @return
//     */
//    public static String analysisErrorMsg(ObjectNode errNode) {
//        String msg = null ;
//        List<String> errorList = errNode.findValuesAsText("error") ;
//
//        if(errorList != null && errorList.size() > 0) {
//            String err = errorList.get(0) ;
//            if(StringUtils.isNotBlank(err)) {
//            	if("illegal_argument".equals(err.trim())) {
//                    msg = MSG_CREATE_USER_FAIL_ILLEGAL_ARGUMENT ;
//            	} else if ("duplicate_unique_property_exists".equals(err.trim())) {
//                    msg = MSG_CREATE_USER_FAIL_EXISTS ;
//            	} else {
//                    msg = MSG_CREATE_USER_FAIL ;
//            	}
//            } else {
//                msg = MSG_CREATE_USER_FAIL ;
//            }
//        } else {
//            msg = MSG_CREATE_USER_FAIL ;
//        }
//        return msg ;
//    }
//
//    /**
//     * 解析结果
//     * @param resultNode
//     * @return
//     */
//    public static PairUtil<String,String> analysisResult(ObjectNode resultNode) {
//        String msg = null ;
//        PairUtil<String,String> resultPair = new PairUtil<String ,String>() ;
//        String statusCode = resultNode.get("statusCode").toString() ;
//
//        if(StringUtils.isNotBlank(statusCode) && !"200".equals(statusCode.trim())) {
//            msg = analysisErrorMsg(resultNode) ;
//        } else if(StringUtils.isNotBlank(statusCode) && "200".equals(statusCode.trim()) ) {
//            msg = MSG_REQUEST_SUCCESS ;
//        } else {
//            msg = MSG_REQUEST_FAIL ;
//        }
//
//        resultPair.setOne(statusCode);
//        resultPair.setTwo(msg);
//        return resultPair ;
//    }

}
