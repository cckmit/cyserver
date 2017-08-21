package com.cy.base.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class AsynTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private boolean isParent;
	private String telphone;
	private String plevel;
	private int typeId;
	private String fullName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSONField(name = "isParent")
	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getPlevel() {
		return plevel;
	}

	public void setPlevel(String plevel) {
		this.plevel = plevel;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
