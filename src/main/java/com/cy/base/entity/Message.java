package com.cy.base.entity;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;
	private String returnId;
	private boolean success;
	private Object obj;

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public void init(boolean success ,String msg ,Object obj) {
		this.success = success ;
		this.msg = msg ;
		this.obj = obj ;
	}
	public void init(boolean success ,String msg ,Object obj ,String returnId) {
		this.success = success ;
		this.msg = msg ;
		this.obj = obj ;
		this.returnId = returnId ;
	}

	@Override
	public String toString() {
		return "Message [msg=" + msg + ", success=" + success + ", obj=" + obj + "]";
	}

}
