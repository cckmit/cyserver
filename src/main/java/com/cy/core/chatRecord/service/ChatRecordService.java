package com.cy.core.chatRecord.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.chatRecord.entity.ChatRecord;

public interface ChatRecordService {
	
	DataGrid<ChatRecord> dataGrid(ChatRecord chatRecord);

	ChatRecord getById(String id);

    void delete(String id);

	/**
	 * 根据ID 集合删除 联系人信息
	 */
	public void deleteByIdList(String ids) ;
}
