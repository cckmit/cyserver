package com.cy.core.userProfile.entity;
import java.util.*;
import java.io.Serializable;

public class GroupInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * groupId 群索引id
	 * description 群描述
	 * subject 群主题
	 * groupName 群名称
	 * createrAccount 群创建者账号
	 * adminsAccount 群管理员账号列表
	 * membersAccount 成员账号列表
	 */
    private String groupId;
    private String description;
    private String subject;
    private String groupName;
    private String createrAccount;
    private String membersAccount;
    private String adminsAccount;
    
    /**--群内用户--**/
    private List userList;
   
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCreaterAccount() {
		return createrAccount;
	}
	public void setCreaterAccount(String createrAccount) {
		this.createrAccount = createrAccount;
	}
	public String getMembersAccount() {
		return membersAccount;
	}
	public void setMembersAccount(String membersAccount) {
		this.membersAccount = membersAccount;
	}
	public String getAdminsAccount() {
		return adminsAccount;
	}
	public void setAdminsAccount(String adminsAccount) {
		this.adminsAccount = adminsAccount;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List getUserList() {
		return userList;
	}
	public void setUserList(List userList) {
		this.userList = userList;
	}
	@Override
	public String toString() {
		return "GroupInfoEntity [groupId=" + groupId + ", description=" + description + ", subject=" + subject + ", groupName=" + groupName
				+ ", createrAccount=" + createrAccount + ", membersAccount=" + membersAccount + ", adminsAccount=" + adminsAccount + ", userList=" + userList
				+ "]";
	}
	
	
	
}
