package com.cy.core.systemsetting.service;

import java.util.List;

import com.cy.base.entity.Message;
import com.cy.core.systemsetting.entity.SystemSetting;

public interface SystemSettingService {
	List<SystemSetting> selectAll();

	SystemSetting selectById(long systemId);

	void update(SystemSetting systemSetting);

	void insert(SystemSetting systemSetting);



	void findStatement(Message message, String content);

}
