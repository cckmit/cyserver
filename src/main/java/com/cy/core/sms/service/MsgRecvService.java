package com.cy.core.sms.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.sms.entity.MsgRecv;

public interface MsgRecvService {
	/**
	 * 保存上行短消息
	 * 
	 * @param msgRecv
	 * @return
	 */
	int insertMsg(MsgRecv msgRecv);

	/**
	 * 短信回复查看
	 * 
	 * @param messagegroup
	 * @return
	 */
	DataGrid<MsgRecv> dataGridMsgRecv(MsgRecv msgRecv);

	List<MsgRecv> selectByDate(Map<String, Object> map);

	int deleteByDate(Map<String, Object> map);
}
