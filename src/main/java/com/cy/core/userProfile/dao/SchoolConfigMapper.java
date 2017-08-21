package com.cy.core.userProfile.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.userProfile.entity.SchoolConfigEntity;

public interface SchoolConfigMapper {
	List<SchoolConfigEntity> selectByBaseId(List<String> baseIdList);

	List<SchoolConfigEntity> selectAllSupportedSchools();
	
	long countSchoolConfigEntity(Map<String, Object> map);

	int save(SchoolConfigEntity schoolConfigEntity);

	void deleteByBaseId(List<String> baseIdList);

	void update(SchoolConfigEntity schoolConfigEntity);
}
