package com.cy.core.sms.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.sms.entity.MsgSend;

public interface MsgSendMapper {
	/**
	 * 后台保存短消息
	 * 
	 * @param msgSend
	 * @return
	 */
	int insertMsg(MsgSend msgSend);

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
	
	void updateStatus(MsgSend msgSend);

	/**
	 * 获取比所传ID大的短信
	 * 
	 * @param msgId
	 * @return
	 */
	List<MsgSend> selectTopMsgId(int msgId);

	/**
	 * 前台短消息入库
	 * 
	 * @param msgSendModel
	 * @return
	 */
	int insertMsgSend(MsgSend msgSend);

	/**
	 * 获取短信发件箱列表
	 * 
	 * @param msgSendModel
	 * @return
	 */
	List<MsgSend> selectOutBox(Map<String, Object> map);

	/**
	 * 短信发件箱总记录条数
	 * 
	 * @param msgSendModel
	 * @return
	 */
	long countOutBox(Map<String, Object> map);

	/**
	 * 获取每批短信
	 * 
	 * @param messagegroup
	 * @return
	 */
	List<MsgSend> selectByMsgGroup(String messagegroup);

	MsgSend selectByMGAndTel(Map<String, Object> map);

	List<MsgSend> selectByDate(Map<String, Object> map);

	int deleteByDate(Map<String, Object> map);
}
