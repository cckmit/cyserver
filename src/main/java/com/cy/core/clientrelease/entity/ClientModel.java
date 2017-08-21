package com.cy.core.clientrelease.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class ClientModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String version;
	private String url;
	private String urller;   //相对路径
	private String downloads; //下载量
	private Date createTime;
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private int start;
	private int end;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrller() {
		if(StringUtils.isBlank(urller) && StringUtils.isNotBlank(url) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(url.indexOf(Global.URL_DOMAIN) == 0) {
				urller = url.substring(Global.URL_DOMAIN.length()) ;
			}else{
				urller = url;
			}
		}
		return urller;
	}

	public void setUrller(String urller) {
		this.urller = urller;
	}
	public String getUrl() {
		if(StringUtils.isBlank(url) && StringUtils.isNotBlank(urller) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(urller.indexOf("http") < 0) {
				url = Global.URL_DOMAIN + urller ;
			}else{
				url = urller;
			}
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getDownloads() {
		return downloads;
	}

	public void setDownloads(String downloads) {
		this.downloads = downloads;
	}
}
