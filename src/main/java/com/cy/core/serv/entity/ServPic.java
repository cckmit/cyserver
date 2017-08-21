package com.cy.core.serv.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class ServPic implements Serializable {

	private static final long serialVersionUID = -6743923486800079634L;
	private long id;
	private long serviceId;
	private String pic;
	private String picUrl;//图片相对路径

	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}


	/**
	 * 图片相对路径
	 * @return
	 */


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

	public String getPic()
	{
		if(StringUtils.isBlank(pic) && StringUtils.isNotBlank(picUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picUrl.indexOf("http") < 0) {
				pic = Global.URL_DOMAIN + picUrl ;
			}else{
				pic = picUrl;
			}
		}
		return pic;
	}
}
