package com.cy.core.alumniAssociation.entity;

import com.cy.core.project.entity.ProjectCost;
import com.cy.core.schoolServ.entity.SchoolServ;
import com.cy.core.user.entity.User;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserService implements Serializable {
	private static final long serialVersionUID = 1L;

	private String accountNum;
	private String unNormalServiceId;
	private String normalServiceId;
	private List<SchoolServ> normalService;
	private List<SchoolServ> unNormalService;


	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getNormalServiceId() {
		return normalServiceId;
	}

	public void setNormalServiceId(String normalServiceId) {
		this.normalServiceId = normalServiceId;
	}

	public String getUnNormalServiceId() {
		return unNormalServiceId;
	}

	public void setUnNormalServiceId(String unNormalServiceId) {
		this.unNormalServiceId = unNormalServiceId;
	}

	public List<SchoolServ> getNormalService() {
		return normalService;
	}

	public void setNormalService(List<SchoolServ> normalService) {
		this.normalService = normalService;
	}

	public List<SchoolServ> getUnNormalService() {
		return unNormalService;
	}

	public void setUnNormalService(List<SchoolServ> unNormalService) {
		this.unNormalService = unNormalService;
	}
}
