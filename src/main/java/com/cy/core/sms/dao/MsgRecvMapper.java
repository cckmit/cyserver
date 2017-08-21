package com.cy.core.sms.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.sms.entity.MsgRecv;

public interface MsgRecvMapper {
	/**
	 * 保存上行短消息
	 * 
	 * @param msgRecv
	 * @return
	 */
	int insertMsgRecv(MsgRecv msgRecv);

	/**
	 * 上行总条数
	 * 
	 * @param map
	 * @return
	 */
	long countMsgRecv(Map<String, Object> map);

	/**
	 * 上行记录
	 * 
	 * @param map
	 * @return
	 */
	List<MsgRecv> selectMsgRecv(Map<String, Object> map);

	List<MsgRecv> selectByDate(Map<String, Object> map);

	int deleteByDate(Map<String, Object> map);

}
