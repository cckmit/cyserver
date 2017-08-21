package com.cy.core.news.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class News implements Serializable{

	private static final long serialVersionUID = 1L;

	private long newsId;
	private String title;
	private String content;
	private String pic;
	private String introduction;
	private Date createTime;
	private String type;
	private String channelName;
	private String newsUrl;//新闻网址
	private String [] types;
	private String typeStr;
	private long category;
	private long pCategory;
	private String categoryName;
	private String pCategoryName;//父栏目名
	private String topnews;
	private long currentRow = 0;//当前行数
	private int incremental = 10;//每次拉取数据的增量
	private String fDateTime;
	private String createDate;
    private String updateDate;

	private String clickRate;	//点击量

	private String createBy;
	private String updateBy;
	private String approveDeptId ;	// 新闻审核部门编号
	private String approveDeptName; //审核部门名

	private String uDeptId;		//当前用户的deptId

	private String channel;		//渠道

	private int source;			//来源

	private String mainName;	//新闻来源即组织类型

	private long categoryWechat;	//lixun
	private String categoryWechatName;	//lixun
	private String status;	//lixun
	private String opinions;	//lixun
	private String channelIds;	//lixun 栏目列表
	private String channels;	//lixun 栏目名称列表
	private long categoryWeb;
	private long pCategoryWeb;
	private String categoryWebName;
	private String pCategoryWebName;//父栏目名
	private String topnewsWeb;
	private String code;
	
	private String cityName;	//新增所在地
	private String channelId ;	// 新闻栏目

	private List<News> newsList; //相關新聞列表

	/**--1.总会新闻，2.地方新闻--**/
	private int origin;
	private int originP;
	private int originWeb;
	private int originWebP;
	
	private String json_news_url;
	
	private String dept_id;		//所属组织编号
	private String dept_name;	//所属组织名称
	
	private String profession;//行业群
	private String professionStr;
	private String alumniid;//校友会群
	private String alumniidStr;
	private String picUrl;//图片相对路径
	private String pic_xd;
	private String pic_min;	//小图
	private String newNewsUrl;//新闻网址相对路径
	private String newsUrl_xd;
	private String remarks;
	private String delFlag;
	private String categoryType;
	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", title=" + title + ", content="
				+ content + ", pic=" + pic + ", introduction=" + introduction
				+ ", createTime=" + createTime + ", type=" + type
				+ ", channelName=" + channelName + ", newsUrl=" + newsUrl
				+ ", types=" + Arrays.toString(types) + ", typeStr=" + typeStr
				+ ", category=" + category + ", categoryName=" + categoryName
				+ ", topnews=" + topnews + ", currentRow=" + currentRow
				+ ", pCategory=" + pCategory 
				+ ", incremental=" + incremental + "]";
	}

	
	
	
	public String getfDateTime() {
		return fDateTime;
	}

	public void setfDateTime(String fDateTime) {
		this.fDateTime = fDateTime;
	}

	public int getIncremental() {
		return incremental;
	}

	public void setIncremental(int incremental) {
		this.incremental = incremental;
	}

	public long getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTopnews() {
		return topnews;
	}

	public void setTopnews(String topnews) {
		this.topnews = topnews;
	}

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public long getNewsId()
	{
		return newsId;
	}

	public void setNewsId(long newsId)
	{
		this.newsId = newsId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}



	public String getIntroduction()
	{
		return introduction;
	}

	public void setIntroduction(String introduction)
	{
		this.introduction = introduction;
	}

	public Date getCreateTime()
	{
		try {
			if(StringUtils.isNotBlank(createDate)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				createTime = sdf.parse(createDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createTime ;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPCategoryName() {
		return pCategoryName;
	}
	public void setPCategoryName(String categoryName) {
		pCategoryName = categoryName;
	}
	public long getPCategory() {
		return pCategory;
	}
	public void setPCategory(long category) {
		pCategory = category;
	}
	public String getFDateTime() {
		return fDateTime;
	}
	public void setFDateTime(String dateTime) {
		fDateTime = dateTime;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}


	public void setCategoryWeb(long categoryWeb) {
		this.categoryWeb = categoryWeb;
	}

	public long getCategoryWeb() {
		return categoryWeb;
	}

	public void setPCategoryWeb(long pCategoryWeb) {
		this.pCategoryWeb = pCategoryWeb;
	}

	public long getPCategoryWeb() {
		return pCategoryWeb;
	}

	public void setCategoryWebName(String categoryWebName) {
		this.categoryWebName = categoryWebName;
	}

	public String getCategoryWebName() {
		return categoryWebName;
	}

	public void setPCategoryWebName(String pCategoryWebName) {
		this.pCategoryWebName = pCategoryWebName;
	}

	public String getPCategoryWebName() {
		return pCategoryWebName;
	}

	public void setTopnewsWeb(String topnewsWeb) {
		this.topnewsWeb = topnewsWeb;
	}

	public String getTopnewsWeb() {
		return topnewsWeb;
	}

	public void setOriginWeb(int originWeb) {
		this.originWeb = originWeb;
	}

	public int getOriginWeb() {
		return originWeb;
	}

	public void setOriginP(int originP) {
		this.originP = originP;
	}
	
	public int getOriginP() {
		return originP;
	}

	public void setOriginWebP(int originWebP) {
		this.originWebP = originWebP;
	}

	public int getOriginWebP() {
		return originWebP;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_name() {
		return dept_name;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getAlumniid() {
		return alumniid;
	}

	public void setAlumniid(String alumniid) {
		this.alumniid = alumniid;
	}

	public String getProfessionStr() {
		return professionStr;
	}

	public void setProfessionStr(String professionStr) {
		this.professionStr = professionStr;
	}

	public String getAlumniidStr() {
		return alumniidStr;
	}
	public void setAlumniidStr(String alumniidStr) {
		this.alumniidStr = alumniidStr;
	}
	public long getCategoryWechat() {
		return categoryWechat;
	}

	public void setCategoryWechat(long categoryWechat) {
		this.categoryWechat = categoryWechat;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategoryWechatName() {
		return categoryWechatName;
	}

	public void setCategoryWechatName(String categoryWechatName) {
		this.categoryWechatName = categoryWechatName;
	}
	public String getOpinions() {
		return opinions;
	}

	public void setOpinions(String opinions) {
		this.opinions = opinions;
	}

	public String getApproveDeptId() {
		return approveDeptId;
	}

	public void setApproveDeptId(String approveDeptId) {
		this.approveDeptId = approveDeptId;
	}

	public String getApproveDeptName() {
		return approveDeptName;
	}

	public void setApproveDeptName(String approveDeptName) {
		this.approveDeptName = approveDeptName;
	}

	public String getuDeptId() {
		return uDeptId;
	}

	public void setuDeptId(String uDeptId) {
		this.uDeptId = uDeptId;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMainName() {
		return mainName;
	}

	public void setMainName(String mainName) {
		this.mainName = mainName;
	}

	public String getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getClickRate() {
		return clickRate;
	}

	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
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
				pic=picUrl;
			}
		}
		return pic;
	}

	public void setPic(String pic)
	{
		this.pic = pic;
	}

	/**
	 * 网址相对路径
	 * @return
	 */
	public String getNewsUrl() {
		if(StringUtils.isBlank(newsUrl) && StringUtils.isNotBlank(newNewsUrl) && StringUtils.isNotBlank(Global.cy_server_url)) {
			if(newNewsUrl.indexOf("http") < 0) {
				newsUrl = Global.cy_server_url + newNewsUrl ;
			}else{
				newsUrl = newNewsUrl;
			}
		}
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public String getNewNewsUrl() {
		if(StringUtils.isBlank(newNewsUrl) && StringUtils.isNotBlank(newsUrl) && StringUtils.isNotBlank(Global.cy_server_url)) {
			if(newsUrl.indexOf(Global.cy_server_url) == 0) {
				newNewsUrl = newsUrl.substring(Global.cy_server_url.length()) ;
			}else{
				newNewsUrl = newsUrl;
			}
		}
		return newNewsUrl;
	}

	public void setNewNewsUrl(String newNewsUrl) {
		this.newNewsUrl = newNewsUrl;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		if(StringUtils.isBlank(delFlag)){
			delFlag = "0";
		}
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getPic_xd() {
		if(StringUtils.isBlank(pic_xd) && StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(pic.indexOf(Global.URL_DOMAIN) == 0) {
				pic_xd = pic.substring(Global.URL_DOMAIN.length()) ;
			}else{
				pic_xd=pic;
			}
		}
		return pic_xd;
	}

	public void setPic_xd(String pic_xd) {
		this.pic_xd = pic_xd;
	}


	public String getNewsUrl_xd() {
		if(StringUtils.isBlank(newsUrl_xd) && StringUtils.isNotBlank(newsUrl) && StringUtils.isNotBlank(Global.cy_server_url)) {
			if(newsUrl.indexOf(Global.cy_server_url) == 0) {
				newsUrl_xd = newsUrl.substring(Global.cy_server_url.length()) ;
			}else{
				newsUrl_xd = newsUrl;
			}
		}
		return newsUrl_xd;
	}
	public void setNewsUrl_xd(String newsUrl_xd) {
		this.newsUrl_xd = newsUrl_xd;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPic_min() {
		if(StringUtils.isNotBlank(picUrl)) {
			if(picUrl.indexOf("_MIN") > 0){
				pic_min = picUrl;
			}else{
				pic_min = picUrl.substring(0, picUrl.lastIndexOf("."))+"_MIN"+picUrl.substring(picUrl.lastIndexOf("."));
			}

		}else{
			pic_min = pic;
		}
		return pic_min;
	}

	public void setPic_min(String pic_min) {
		this.pic_min = pic_min;
	}
}
