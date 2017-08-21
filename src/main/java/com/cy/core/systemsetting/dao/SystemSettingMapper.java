package com.cy.core.systemsetting.dao;

import java.util.List;

import com.cy.core.systemsetting.entity.SystemSetting;

public interface SystemSettingMapper {
	List<SystemSetting> selectAll();

	SystemSetting selectById(long systemId);
	
	void update(SystemSetting systemSetting);
	
	void insert(SystemSetting systemSetting);

}
