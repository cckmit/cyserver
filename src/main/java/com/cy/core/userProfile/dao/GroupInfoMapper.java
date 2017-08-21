package com.cy.core.userProfile.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.userProfile.entity.GroupInfoEntity;

public interface GroupInfoMapper {
	List<GroupInfoEntity> selectByGroupId(List<String> groupIdList);

	long countGroupInfoEntity(Map<String, Object> map);

	int save(GroupInfoEntity groupInfoEntity);

	void deleteByGroupId(List<String> groupIdList);

	public void deleteByCreatorAccount(String createrAccount);

	void update(GroupInfoEntity groupInfoEntity);
	
	/**--查询该用户所在的所有群信息--**/
	List<GroupInfoEntity> selectGroupByAccountNum(String accountNum);
	
	/**--根据群ID查询群的详细--**/
	GroupInfoEntity selectGroupByGroupId(String groupId);
	
	void deleteById(String groupId);
	
	void updateMembersAccount(GroupInfoEntity groupInfoEntity);
	
	void updateAdminsAccount(GroupInfoEntity groupInfoEntity);
	
}
