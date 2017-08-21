package com.cy.core.systemsetting.entity;

import java.io.Serializable;

public class SystemSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	private long systemId;
	private String smtpHost;
	private String smtpPort;
	private String email_account;
	private String email_password;
	private String download_app_url;
	//	private String partner;
//	private String seller_email;
	private String key;
	private String private_key;
	private String wap_public_key;
	private String notify_url;
	private String return_url;
	private String wap_notify_url;
	private String wap_return_url;
	private String wap_merchant_url;
	private String exter_invoke_ip;
	private String smsAccount;
	private String smsPassword;
	private String smsUrl;
	private String sendType;
	private String smsCodeTemplate;
	private String smsVisitTemplate;
	private String smsBirthdayTemplate;

	private String web_homepage_api_url;
	private String is_new_type_open;

	private String schoolLogo ;		// 学校LOGO
	/*private String schoolLogoUrl;   // 学校LOGO相对路径*/

	/*************基金会信息*****************/
	private String foundationName;
	private String foundationSignet;
	private String foundationLogo;

	/**************支付宝信息****************/
	private String sellerEmail 	 	;	// 商户账号
	private String appId 			;	// 支付宝应用APPID
	private String partner 			;	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	private String inputCharset 	;	// 参数编码字符集
	private String alipayPublicKey 	;	// 支付宝公钥
	private String appPublicKey 	;	// 应用公钥
	private String appPrivateKey 	;	// 应用私钥
	private String serverUrl 	 	;	// 支付网管
	private String paymentType ;     //支付类型(1:商品购买【即时到账】；4：捐赠【公益即使捐赠】)

	/***************声明文本****************/
	private String signUpText;
	private String donateText;


	/***************基金会邮件配置***********************/
	private String foundationSmtpHost;
	private String foundationSmtpPort;
	private String foundationEmailAccount;
	private String foundationEmailPassword;

	/***************云平台设置***********************/
	private String clientId;
	private String clientSecret;
	private String cloudUserName;
	private String cloudUserPassword;




	/*************活动设置*****************/
	private String is_audit_personal_activity;

	public long getSystemId() {
		return systemId;
	}

	public void setSystemId(long systemId) {
		this.systemId = systemId;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getEmail_account() {
		return email_account;
	}

	public void setEmail_account(String email_account) {
		this.email_account = email_account;
	}

	public String getEmail_password() {
		return email_password;
	}

	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}

	public String getDownload_app_url() {
		return download_app_url;
	}

	public void setDownload_app_url(String download_app_url) {
		this.download_app_url = download_app_url;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getExter_invoke_ip() {
		return exter_invoke_ip;
	}

	public void setExter_invoke_ip(String exter_invoke_ip) {
		this.exter_invoke_ip = exter_invoke_ip;
	}

	public String getSmsAccount() {
		return smsAccount;
	}

	public void setSmsAccount(String smsAccount) {
		this.smsAccount = smsAccount;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getSmsCodeTemplate() {
		return smsCodeTemplate;
	}

	public void setSmsCodeTemplate(String smsCodeTemplate) {
		this.smsCodeTemplate = smsCodeTemplate;
	}

	public String getSmsVisitTemplate() {
		return smsVisitTemplate;
	}

	public void setSmsVisitTemplate(String smsVisitTemplate) {
		this.smsVisitTemplate = smsVisitTemplate;
	}

	public String getsmsBirthdayTemplate() {
		return smsBirthdayTemplate;
	}

	public void setsmsBirthdayTemplate(String smsBirthdayTemplate) {
		this.smsBirthdayTemplate = smsBirthdayTemplate;
	}

	public String getPrivate_key() {
		return private_key;
	}

	public void setPrivate_key(String private_key) {
		this.private_key = private_key;
	}

	public String getWap_notify_url() {
		return wap_notify_url;
	}

	public void setWap_notify_url(String wap_notify_url) {
		this.wap_notify_url = wap_notify_url;
	}

	public String getWap_return_url() {
		return wap_return_url;
	}

	public void setWap_return_url(String wap_return_url) {
		this.wap_return_url = wap_return_url;
	}

	public String getWap_merchant_url() {
		return wap_merchant_url;
	}

	public void setWap_merchant_url(String wap_merchant_url) {
		this.wap_merchant_url = wap_merchant_url;
	}

	public String getWap_public_key() {
		return wap_public_key;
	}

	public void setWap_public_key(String wap_public_key) {
		this.wap_public_key = wap_public_key;
	}

	public String getWeb_homepage_api_url() {
		return web_homepage_api_url;
	}

	public void setWeb_homepage_api_url(String web_homepage_api_url) {
		this.web_homepage_api_url = web_homepage_api_url;
	}

	public String getIs_new_type_open() {
		return is_new_type_open;
	}

	public void setIs_new_type_open(String is_new_type_open) {
		this.is_new_type_open = is_new_type_open;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getAppPublicKey() {
		return appPublicKey;
	}

	public void setAppPublicKey(String appPublicKey) {
		this.appPublicKey = appPublicKey;
	}

	public String getAppPrivateKey() {
		return appPrivateKey;
	}

	public void setAppPrivateKey(String appPrivateKey) {
		this.appPrivateKey = appPrivateKey;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}


	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}

	/*public String getSchoolLogoUrl() {
		return schoolLogoUrl;
	}

	public void setSchoolLogoUrl(String schoolLogoUrl) {
		this.schoolLogoUrl = schoolLogoUrl;
	}*/

	public String getSignUpText() {
		return signUpText;
	}

	public void setSignUpText(String signUpText) {
		this.signUpText = signUpText;
	}

	public String getDonateText() {
		return donateText;
	}

	public void setDonateText(String donateText) {
		this.donateText = donateText;
	}

	public String getFoundationName() {
		return foundationName;
	}

	public void setFoundationName(String foundationName) {
		this.foundationName = foundationName;
	}

	public String getFoundationSignet() {
		return foundationSignet;
	}

	public void setFoundationSignet(String foundationSignet) {
		this.foundationSignet = foundationSignet;
	}

	public String getFoundationLogo() {
		return foundationLogo;
	}

	public void setFoundationLogo(String foundationLogo) {
		this.foundationLogo = foundationLogo;
	}

	public String getFoundationSmtpHost() {
		return foundationSmtpHost;
	}

	public void setFoundationSmtpHost(String foundationSmtpHost) {
		this.foundationSmtpHost = foundationSmtpHost;
	}

	public String getFoundationSmtpPort() {
		return foundationSmtpPort;
	}

	public void setFoundationSmtpPort(String foundationSmtpPort) {
		this.foundationSmtpPort = foundationSmtpPort;
	}

	public String getFoundationEmailAccount() {
		return foundationEmailAccount;
	}

	public void setFoundationEmailAccount(String foundationEmailAccount) {
		this.foundationEmailAccount = foundationEmailAccount;
	}

	public String getFoundationEmailPassword() {
		return foundationEmailPassword;
	}

	public void setFoundationEmailPassword(String foundationEmailPassword) {
		this.foundationEmailPassword = foundationEmailPassword;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getIs_audit_personal_activity() {
		return is_audit_personal_activity;
	}

	public void setIs_audit_personal_activity(String is_audit_personal_activity) {
		this.is_audit_personal_activity = is_audit_personal_activity;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCloudUserName() {
		return cloudUserName;
	}

	public void setCloudUserName(String cloudUserName) {
		this.cloudUserName = cloudUserName;
	}

	public String getCloudUserPassword() {
		return cloudUserPassword;
	}

	public void setCloudUserPassword(String cloudUserPassword) {
		this.cloudUserPassword = cloudUserPassword;
	}
}
