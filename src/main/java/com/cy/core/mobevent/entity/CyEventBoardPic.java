package com.cy.core.mobevent.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class CyEventBoardPic implements Serializable{

	/**
	 * 花絮图片
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;//花絮图片编号
	
	private long boardId;//花絮编号
	
	private String pic;//花絮图片地址
	
	private String thumbnail;//花絮缩略图地址
	
	private String xemanhdep;//花絮高清图地址
	
	private String picStr;//花絮图片说明

	private String picUrl;
	
	

	public String getPicStr() {
		return picStr;
	}

	public void setPicStr(String picStr) {
		this.picStr = picStr;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getPic() {
		if(StringUtils.isBlank(pic) && StringUtils.isNotBlank(picUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picUrl.indexOf("http") < 0) {
				pic = Global.URL_DOMAIN + picUrl ;
			}else{
				pic=picUrl;
			}
		}
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}

	public String getXemanhdep() {
		return xemanhdep;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setXemanhdep(String xemanhdep) {
		this.xemanhdep = xemanhdep;
	}

	public String getPicUrl() {
		if(StringUtils.isBlank(picUrl) && StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(pic.indexOf(Global.URL_DOMAIN) == 0) {
				picUrl = pic.substring(Global.URL_DOMAIN.length()) ;
			}else{
				picUrl=pic;
			}
		}
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyEventBoardPic [id=");
		builder.append(id);
		builder.append(", boardId=");
		builder.append(boardId);
		builder.append(", pic=");
		builder.append(pic);
		builder.append(", picUrl=");
		builder.append(picUrl);
		builder.append(", picStr=");
		builder.append(picStr);
		builder.append("]");
		return builder.toString();
	}

	

	
	
}
