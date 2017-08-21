package com.cy.core.userProfile.entity;

import java.io.Serializable;

public class SchoolConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    private String baseId;//学校编号
    private String schoolName;//学校名称
    private String province;//学校所在的省份
    private String city;//学校所在的城市
    private String pushServer;//推送服务器的ip地址或者域名
    private String pushServerPort;//推送服务器的端口号
    private String pushServerAccount;//推送服务器的连接账号
    private String pushServerPassword;//推送服务器的连接密码
    private String webServer;//web服务器的ip地址或者域名
    private String webServerPort;//web服务器的端口
    private String fileServer;//文件服务器的ip地址或者域名
    private String fileServerPort;//文件服务器的端口
    private String chatServer;//聊天服务器的ip地址或者域名
    private String chatServerPort;//聊天服务器的端口
    private String chatServerDomain;//聊天服务器的domain，账号jid后缀
    private String codeSecretKey;//手机获取验证码需要提供的秘钥
    private String welcomePicture;//欢迎页面的图片地址
    private String telphone;//校友会联系电话
    
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPushServer() {
		return pushServer;
	}
	public void setPushServer(String pushServer) {
		this.pushServer = pushServer;
	}
	public String getPushServerPort() {
		return pushServerPort;
	}
	public void setPushServerPort(String pushServerPort) {
		this.pushServerPort = pushServerPort;
	}
	public String getPushServerAccount() {
		return pushServerAccount;
	}
	public void setPushServerAccount(String pushServerAccount) {
		this.pushServerAccount = pushServerAccount;
	}
	public String getPushServerPassword() {
		return pushServerPassword;
	}
	public void setPushServerPassword(String pushServerPassword) {
		this.pushServerPassword = pushServerPassword;
	}
	public String getWebServer() {
		return webServer;
	}
	public void setWebServer(String webServer) {
		this.webServer = webServer;
	}
	public String getWebServerPort() {
		return webServerPort;
	}
	public void setWebServerPort(String webServerPort) {
		this.webServerPort = webServerPort;
	}
	public String getFileServer() {
		return fileServer;
	}
	public void setFileServer(String fileServer) {
		this.fileServer = fileServer;
	}
	public String getFileServerPort() {
		return fileServerPort;
	}
	public void setFileServerPort(String fileServerPort) {
		this.fileServerPort = fileServerPort;
	}
	public String getChatServer() {
		return chatServer;
	}
	public void setChatServer(String chatServer) {
		this.chatServer = chatServer;
	}
	public String getChatServerPort() {
		return chatServerPort;
	}
	public void setChatServerPort(String chatServerPort) {
		this.chatServerPort = chatServerPort;
	}
	public String getChatServerDomain() {
		return chatServerDomain;
	}
	public void setChatServerDomain(String chatServerDomain) {
		this.chatServerDomain = chatServerDomain;
	}
	public String getCodeSecretKey() {
		return codeSecretKey;
	}
	public void setCodeSecretKey(String codeSecretKey) {
		this.codeSecretKey = codeSecretKey;
	}
	public String getWelcomePicture() {
		return welcomePicture;
	}
	public void setWelcomePicture(String welcomePicture) {
		this.welcomePicture = welcomePicture;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
}
