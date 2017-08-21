package com.cy.core.notify.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Title: Notify</p>
 * <p>Description: 通知推送内容实体类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-10-27
 */
public class Notify extends DataEntity<Notify>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//--------------数据库字段---------------//
	private String type        ;   // 推送类型（news_feeds：新闻订阅；classmate_authentication：同班同学班级认证；exit_authentication：同班同学退出班级认证；friend_add_request：好友添加申请；friend_add_agree：同意好友添加申请；remove_group_al
	private String title       ;   // 标题
	private String content     ;   // 通知内容
	private String bussId     ;   // 通知相关业务编号
	private String way         ;   // 推送方式：10：app；20：短信；30：不推送；40：不确定推送方式
	private String status      ;   // 发送状态（10：未发送；20：已发送）
	private String channel     ; // 推送方式（10：通过标签tag 推送；20：通过别名alias 推送；30：通过手机号推送）
	private String markings     ; // 接收推送标示(对个标识以","隔开)
	private String extras ;			//推送信息json 字符串
	private Map extrasMap ;			// 推送信息
	//标签
	private String state;
	private List<String> userName;

	private String readFlag ;	// 已读标识(0:未读;1:已读)

	public List<String> getUserName() {
		return userName;
	}

	public void setUserName(List<String> userName) {
		this.userName = userName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	//--------------查询关联字段---------------//
	private List<NotifyRecord> notifyRecordList ;	// 通知记录列表
//	private String groupId		;	// 群组编号(=id)
//	private String userName		;	// 用户名称

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBussId() {
		return bussId;
	}

	public void setBussId(String bussId) {
		this.bussId = bussId;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMarkings() {
		return markings;
	}

	public void setMarkings(String markings) {
		this.markings = markings;
	}

	public List<NotifyRecord> getNotifyRecordList() {
		return notifyRecordList;
	}

	public void setNotifyRecordList(List<NotifyRecord> notifyRecordList) {
		this.notifyRecordList = notifyRecordList;
	}

	public Map getExtrasMap(){
		if(extrasMap == null && StringUtils.isNotBlank(extras)) {
			try {
				extrasMap = JsonUtils.json2pojo(extras,Map.class) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return extrasMap;
	}

	public void setExtrasMap(Map extrasMap) {
		this.extrasMap = extrasMap;
	}

	public String getExtras() {
		if(StringUtils.isBlank(extras) && extrasMap != null && !extrasMap.isEmpty()) {
			try {
				extras = JsonUtils.obj2json(extrasMap) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	@Override
	public String toString() {
		return "Notify{" +
				"type='" + type + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", bussId='" + bussId + '\'' +
				", way='" + way + '\'' +
				", status='" + status + '\'' +
				", channel='" + channel + '\'' +
				", markings='" + markings + '\'' +
				", extras=" + extras +
				", notifyRecordList=" + notifyRecordList +
				'}';
	}

}