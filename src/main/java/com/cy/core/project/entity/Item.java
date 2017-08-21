package com.cy.core.project.entity;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private String donateItemUrl;
	private String projectName;
	private String projectPic;
	private String projectPicUrl;
	private String projectId;
	private String introduction;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDonateItemUrl() {
		return donateItemUrl;
	}

	public void setDonateItemUrl(String donateItemUrl) {
		this.donateItemUrl = donateItemUrl;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectPic() {
		return projectPic;
	}

	public void setProjectPic(String projectPic) {
		this.projectPic = projectPic;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getProjectPicUrl() {
		return projectPicUrl;
	}

	public void setProjectPicUrl(String projectPicUrl) {
		this.projectPicUrl = projectPicUrl;
	}
}
