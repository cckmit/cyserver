package com.cy.core.sms.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.AsynTreeNode;
import com.cy.base.entity.DataGrid;
import com.cy.core.sms.entity.MsgSend;

public interface MsgSendService
{

	/**
	 * 通过手机号和批次号更新发送记录表
	 * 
	 * @param msgSend
	 * @return
	 */
	int updateByPM(MsgSend msgSend);
	
	/**
	 * 状态报告更新
	 * 
	 * @param msgSend
	 */
	void updateStatusReport(MsgSend msgSend);
	
	/**
	 * 短信已推送至第三方状态更新
	 * 
	 * @param msgSend
	 */
	void updateStatus(MsgSend msgSend);

	/**
	 * 获取比所传ID大的短信
	 * 
	 * @param msgId
	 * @return
	 */
	List<MsgSend> selectTopMsgId(int msgId);

	/**
	 * 异步加载通讯录节点
	 * @param deptId
	 * @return
	 */
	List<AsynTreeNode> getAddrBookNode(String deptId, String level, String poolId);

	/**
	 * 短消息入库
	 * @param msgSendModel
	 * @return
	 */
	void insertMsgSend(MsgSend msgSend);

	/**
	 * 短信发件箱列表
	 * @param msgSendModel
	 * @return
	 */
	DataGrid<MsgSend> dataGridOutBox(Map<String, Object> map);

	/**
	 * 查看状态报告
	 * @param messagegroup
	 * @return
	 */
	Map<String, List<MsgSend>> viewReport(String messagegroup);

	MsgSend selectByMGAndTel(Map<String, Object> map);

	List<MsgSend> selectByDate(Map<String, Object> map);

	int deleteByDate(Map<String, Object> map);

	/**
	 * 短信验证码
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
//	int sendSmsCode(String mobile, String content, String code);
	
	/**
	 * 短信验证码新接口
	 * 
	 * @param mobile
	 * @param content
	 * @param code
	 * @return
	 */
	int sendRegisterCode(String mobile, String content, String code);
	
	/**
	 * app下载邀请短信
	 * @param mobile
	 * @param content
	 * @return
	 */
//	int sendSmsAppInvite(String mobile, String content);
}
