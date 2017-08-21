package com.cy.core.userProfile.service;

import net.sf.json.JSONObject;
import com.cy.base.entity.Message;
import com.cy.core.userProfile.entity.UserProfile;


public interface AppService{
	
	
	/**
	 * 12号接口
	 * 修改群表信息，手机端自己处理， 如果上传空串则删除记录，如果不传此字段则不做改变，全删全建
	 * 0创建，1删除群组，2添加普通成员，3删除普通成员，4添加管理员，5删除管理员，6普通成员提升为管理员 7管理员降级为普通成员，8修改群自有信息
	 * 其中content里面的 accountNum,password,groupId,type,createrAccount,groupName必填
	 * 
	 * @param content
	 * @param message
	 */
	public void updateGroupInfo(JSONObject content, Message message) throws Exception;
	
	/**--创建默认班级,认证之后做的事情--**/
	public void createInitClass(UserProfile userProfile) throws Exception;
	
	/**--手机端获取登录信息，原10号接口,根据帐号密码获取自己的基本信息--**/
	public void selectAppLogin(JSONObject content, Message message);
	
	/**--根据群id查询群内用户信息--**/
	public void selectUserByGroupId(JSONObject content, Message message);
	
	/**--根据baseInfo查询用户信息--**/
	public void selectUserByBaseInfoId(JSONObject content, Message message);

	/**--根据校友手机号查询查询用户信息--**/
	public void selectByPhoneNum(Message message, String content);
	
	/**--根据校友ID，更新状态--**/
	public void UpdateProfileStatus(Message message, String content) ;
}
