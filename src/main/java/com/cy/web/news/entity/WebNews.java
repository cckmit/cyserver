package com.cy.web.news.entity;

import java.io.Serializable;

public class WebNews implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private long newsId;//新闻ID
	private String title;//新闻标题
	private String pic;//新闻封面图片地址
	private String content;//新闻内容
	private String introduction;//新闻简介
	private String createTime;//创建时间
	private String createTimeFomat;//格式化的创建时间
	private String channelName;//频道名称
	private String type;//新闻标签
	private String newsUrl;//新闻URL
	private int category;//新闻手机类别
	private String topnews;//推荐新闻
	private String cityName;//新闻地域
	private int categoryWeb;//新闻网站类别
	
	private int origin;//类别（1：总会， 2：地方）
	
	private int pagination;// 当前页
	
	private long currentRow;//起始行
	private long incremental;// 每页显示记录数
	
	private String paging;//分页字符串
	
	private String actionUrl;
	
	public long getNewsId() {
		return newsId;
	}
	public String getTitle() {
		return title;
	}
	public String getPic() {
		return pic;
	}
	public String getContent() {
		return content;
	}
	public String getIntroduction() {
		return introduction;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getChannelName() {
		return channelName;
	}
	public String getType() {
		return type;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public int getCategory() {
		return category;
	}
	public String getTopnews() {
		return topnews;
	}
	public String getCityName() {
		return cityName;
	}
	public int getCategoryWeb() {
		return categoryWeb;
	}
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public void setTopnews(String topnews) {
		this.topnews = topnews;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setCategoryWeb(int categoryWeb) {
		this.categoryWeb = categoryWeb;
	}
	public String getCreateTimeFomat() {
		return createTimeFomat;
	}
	public void setCreateTimeFomat(String createTimeFomat) {
		this.createTimeFomat = createTimeFomat;
	}
	
	
	public String getPaging() {
		return paging;
	}
	public void setPaging(String paging) {
		this.paging = paging;
	}
	public int getPagination() {
		return pagination;
	}
	public void setPagination(int pagination) {
		this.pagination = pagination;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public long getCurrentRow() {
		return currentRow;
	}
	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}
	public long getIncremental() {
		return incremental;
	}
	public void setIncremental(long incremental) {
		this.incremental = incremental;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	
	
}
