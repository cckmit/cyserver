package com.cy.core.mobserv.entity;

import java.io.Serializable;


public class CyServComment extends CyServExtension implements Serializable{

	/**
	 * 
	 * 帮帮忙评论表
	 */
	private static final long serialVersionUID = 1L;

	
	private String content; //'评论内容',
	
	private int type; //性质（0=官方 ，5=校友会，9=个人）

	private int status; //'状态（0=正常，1=用户自己删除，2=管理员删除）',
	
	
	public String getContent() {
		return content;
	}
	
	public int getStatus() {
		return status;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyServComment [content=<");
		builder.append(content);
		builder.append(">, status=<");
		builder.append(status);
		builder.append(">, type=<");
		builder.append(type);
		builder.append(">, getUserName()=<");
		builder.append(getUserName());
		builder.append(">, getUserAvatar()=<");
		builder.append(getUserAvatar());
		builder.append(">, getUserTel()=<");
		builder.append(getUserTel());
		builder.append(">, getUserSex()=<");
		builder.append(getUserSex());
		builder.append(">, getCurrentRow()=<");
		builder.append(getCurrentRow());
		builder.append(">, getIncremental()=<");
		builder.append(getIncremental());
		builder.append(">, getId()=<");
		builder.append(getId());
		builder.append(">, getServiceId()=<");
		builder.append(getServiceId());
		builder.append(">, getAccountNum()=<");
		builder.append(getAccountNum());
		builder.append(">, getCreateTime()=<");
		builder.append(getCreateTime());
		builder.append(">]");
		return builder.toString();
	}

	
	
	
}
