package com.cy.web.news.entity;

import java.io.Serializable;
import java.util.List;

public class WebNewsType implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long parentId;
	private String name; //名称
	private int type; //(1.新闻类别,2.链接，3.单页面)
	private String url;
	private int orderNum; //排序编号,越大越靠前
	private int origin; //类别（1：总会， 2：地方）
	private String cityName;
	private String dispCityName;
	
	private int isMain;//是否上首页（0：不上首页， 1：上首页）
	
	/**--是否上导航--**/
	private int isNavigation;
	
	private List<WebNewsType> webNewsType;
	
	
	
	public int getIsNavigation() {
		return isNavigation;
	}
	public void setIsNavigation(int isNavigation) {
		this.isNavigation = isNavigation;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	public List<WebNewsType> getWebNewsType() {
		return webNewsType;
	}
	public void setWebNewsType(List<WebNewsType> webNewsType) {
		this.webNewsType = webNewsType;
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getDispCityName() {
		return dispCityName;
	}
	public void setDispCityName(String dispCityName) {
		this.dispCityName = dispCityName;
	}
	
	public int getIsMain() {
		return isMain;
	}
	public void setIsMain(int isMain) {
		this.isMain = isMain;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WebNewsType [id=");
		builder.append(id);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", url=");
		builder.append(url);
		builder.append(", orderNum=");
		builder.append(orderNum);
		builder.append(", origin=");
		builder.append(origin);
		builder.append(", cityName=");
		builder.append(cityName);
		builder.append(", dispCityName=");
		builder.append(dispCityName);
		builder.append(", isMain=");
		builder.append(isMain);
		builder.append(", isNavigation=");
		builder.append(isNavigation);
		builder.append(", webNewsType=");
		builder.append(webNewsType);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
	
	
}
