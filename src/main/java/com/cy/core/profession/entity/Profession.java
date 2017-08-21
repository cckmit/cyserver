package com.cy.core.profession.entity;

import java.io.Serializable;

public class Profession implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String professioName;
	private String professioRemark;

	public String getProfessioName() {
		return professioName;
	}

	public void setProfessioName(String professioName) {
		this.professioName = professioName;
	}

	public String getProfessioRemark() {
		return professioRemark;
	}

	public void setProfessioRemark(String professioRemark) {
		this.professioRemark = professioRemark;
	}

	
	
}
