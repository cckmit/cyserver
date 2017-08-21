package com.cy.web.userinfo.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.WebBaseAction;
import com.cy.core.userinfo.service.UserInfoService;

@Namespace("/webUserInfo")
@Action(value = "webUserInfoAction")
public class UserInfoAction extends WebBaseAction {
	@Autowired
	private UserInfoService userInfoService;

	private String name;

	public void selectUserInfoByName() {
		super.writeJson(userInfoService.selectUserInfoByName(name));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
