package com.cy.core.clientrelease.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String version;
	private String url;
	private Date createTime;
	private String urller;   //相对路径
	private String downloads; //下载量

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



	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getDownloads() {
		return downloads;
	}

	public void setDownloads(String downloads) {
		this.downloads = downloads;
	}
}
