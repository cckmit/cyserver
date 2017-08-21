package com.cy.system;

/**
 * 系统属性文件配置实体类
 * @author Administrator
 *
 */
public class ConfigProperties {
	
	
	private String disk_path;
	private String linux_url_domain;
	private String win_url_domain;
	private String sign;
	private String schoolSign;
	private String deptNo;
//	private String tigase_server_domain;
//	private String tigase_admin_account;
//	private String tigase_admin_passwd;
//	private String tigase_server_ip;
//	private int tigase_server_port;
//	private String tigase_db_ip;
//	private int tigase_db_port;
//	private String tigase_db_user;
//	private String tigase_db_password;
//	private int tigase_db_conn_size;
//	private String tigase_db_name;
	private String activemq_server_ip;
	private int activemq_server_port;
	private String activemq_connection_admin_user;
	private String activemq_connection_admin_password;
	private String activemq_server_general_channel_name;
	private String client_release_checkcode;
	private String phone_secret_key;
	private String authenticated_secret_key;
	private String FACE_FILE_DIR;
	private String REGULAR_CHANNEL_1;
	private String REGULAR_CHANNEL_2;
	private String REGULAR_CHANNEL_3;
	private String DOWNLOAD_APP_URL;
	private String smtpHost;
	private String smtpPort;
	private String email_account;
	private String email_password;
	private String login_smscode_check;
	private String web_homepage_api_url;
	private String cy_server_url;

	// 极光账号信息(通用版)
//	private String jpush_app_key ;
//	private String jpush_master_secret ;
//	private Boolean jpush_ios_apns ;
	// 极光账号信息(专用版)
	private String special_jpush_app_key ;
	private String special_jpush_master_secret ;
	private Boolean special_jpush_ios_apns ;

	// 短信充值鏈接
	private String sms_cloud_order_url;
	// 校友会网站地址
	private String school_web_url;

	// 微信访问接口地址
	private String wechat_api_url;
	// 微信网页回调地址
	private String wechat_api_callback_path;

	private String founUrl;

	private String isRichTextConvert;	//富文本是否转换 Lixun 2017.5.5

	private String oauth_server_url; //云平台授权地址
	private String cloud_server_url;//云平台接口访问地址
	private String cloud_file_url;//云平台文件访问地址
	//云平台客户端id
	private String client_id;
	//云平台客户端密钥
	private String client_secret;
	//云平台账号
	private String cloud_user_name;
	//云平台密码
	private String cloud_user_password;

	private String jwt_secret;	//token加密字符串
	private String jwt_ttl;		//token 有效时间	
	private String jwt_refresh_interval;	//refreshtoken,重复获取间隔时间	
	private String jwt_refresh_ttl;  //refreshtoken token有效时间
	

	public String getIsRichTextConvert() {
		return isRichTextConvert;
	}

	public void setIsRichTextConvert(String isRichTextConvert) {
		this.isRichTextConvert = isRichTextConvert;
	}

	public String getDisk_path() {
		return disk_path;
	}
	public void setDisk_path(String disk_path) {
		this.disk_path = disk_path;
	}
	public String getLinux_url_domain() {
		return linux_url_domain;
	}
	public void setLinux_url_domain(String linux_url_domain) {
		this.linux_url_domain = linux_url_domain;
	}
	public String getWin_url_domain() {
		return win_url_domain;
	}
	public void setWin_url_domain(String win_url_domain) {
		this.win_url_domain = win_url_domain;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSchoolSign() {
		return schoolSign;
	}
	public void setSchoolSign(String schoolSign) {
		this.schoolSign = schoolSign;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
//	public String getTigase_admin_account() {
//		return tigase_admin_account;
//	}
//	public void setTigase_admin_account(String tigase_admin_account) {
//		this.tigase_admin_account = tigase_admin_account;
//	}
//	public String getTigase_admin_passwd() {
//		return tigase_admin_passwd;
//	}
//	public void setTigase_admin_passwd(String tigase_admin_passwd) {
//		this.tigase_admin_passwd = tigase_admin_passwd;
//	}
//	public String getTigase_server_ip() {
//		return tigase_server_ip;
//	}
//	public void setTigase_server_ip(String tigase_server_ip) {
//		this.tigase_server_ip = tigase_server_ip;
//	}
//	public int getTigase_server_port() {
//		return tigase_server_port;
//	}
//	public void setTigase_server_port(int tigase_server_port) {
//		this.tigase_server_port = tigase_server_port;
//	}
//	public String getTigase_db_ip() {
//		return tigase_db_ip;
//	}
//	public void setTigase_db_ip(String tigase_db_ip) {
//		this.tigase_db_ip = tigase_db_ip;
//	}
//	public int getTigase_db_port() {
//		return tigase_db_port;
//	}
//	public void setTigase_db_port(int tigase_db_port) {
//		this.tigase_db_port = tigase_db_port;
//	}
//	public String getTigase_db_user() {
//		return tigase_db_user;
//	}
//	public void setTigase_db_user(String tigase_db_user) {
//		this.tigase_db_user = tigase_db_user;
//	}
//	public String getTigase_db_password() {
//		return tigase_db_password;
//	}
//	public void setTigase_db_password(String tigase_db_password) {
//		this.tigase_db_password = tigase_db_password;
//	}
//	public int getTigase_db_conn_size() {
//		return tigase_db_conn_size;
//	}
//	public void setTigase_db_conn_size(int tigase_db_conn_size) {
//		this.tigase_db_conn_size = tigase_db_conn_size;
//	}
//	public String getTigase_db_name() {
//		return tigase_db_name;
//	}
//	public void setTigase_db_name(String tigase_db_name) {
//		this.tigase_db_name = tigase_db_name;
//	}
//	public String getTigase_server_domain() {
//		return tigase_server_domain;
//	}
//	public void setTigase_server_domain(String tigase_server_domain) {
//		this.tigase_server_domain = tigase_server_domain;
//	}
	public String getActivemq_server_ip() {
		return activemq_server_ip;
	}
	public void setActivemq_server_ip(String activemq_server_ip) {
		this.activemq_server_ip = activemq_server_ip;
	}
	public int getActivemq_server_port() {
		return activemq_server_port;
	}
	public void setActivemq_server_port(int activemq_server_port) {
		this.activemq_server_port = activemq_server_port;
	}
	public String getActivemq_connection_admin_user() {
		return activemq_connection_admin_user;
	}
	public void setActivemq_connection_admin_user(
			String activemq_connection_admin_user) {
		this.activemq_connection_admin_user = activemq_connection_admin_user;
	}
	public String getActivemq_connection_admin_password() {
		return activemq_connection_admin_password;
	}
	public void setActivemq_connection_admin_password(
			String activemq_connection_admin_password) {
		this.activemq_connection_admin_password = activemq_connection_admin_password;
	}
	public String getActivemq_server_general_channel_name() {
		return activemq_server_general_channel_name;
	}
	public void setActivemq_server_general_channel_name(
			String activemq_server_general_channel_name) {
		this.activemq_server_general_channel_name = activemq_server_general_channel_name;
	}
	public String getClient_release_checkcode() {
		return client_release_checkcode;
	}
	public void setClient_release_checkcode(String client_release_checkcode) {
		this.client_release_checkcode = client_release_checkcode;
	}
	public String getPhone_secret_key() {
		return phone_secret_key;
	}
	public void setPhone_secret_key(String phone_secret_key) {
		this.phone_secret_key = phone_secret_key;
	}
	public String getAuthenticated_secret_key() {
		return authenticated_secret_key;
	}
	public void setAuthenticated_secret_key(String authenticated_secret_key) {
		this.authenticated_secret_key = authenticated_secret_key;
	}
	public String getFACE_FILE_DIR() {
		return FACE_FILE_DIR;
	}
	public void setFACE_FILE_DIR(String face_file_dir) {
		FACE_FILE_DIR = face_file_dir;
	}
	public String getREGULAR_CHANNEL_1() {
		return REGULAR_CHANNEL_1;
	}
	public void setREGULAR_CHANNEL_1(String regular_channel_1) {
		REGULAR_CHANNEL_1 = regular_channel_1;
	}
	public String getREGULAR_CHANNEL_2() {
		return REGULAR_CHANNEL_2;
	}
	public void setREGULAR_CHANNEL_2(String regular_channel_2) {
		REGULAR_CHANNEL_2 = regular_channel_2;
	}
	public String getREGULAR_CHANNEL_3() {
		return REGULAR_CHANNEL_3;
	}
	public void setREGULAR_CHANNEL_3(String regular_channel_3) {
		REGULAR_CHANNEL_3 = regular_channel_3;
	}
	public String getDOWNLOAD_APP_URL() {
		return DOWNLOAD_APP_URL;
	}
	public void setDOWNLOAD_APP_URL(String download_app_url) {
		DOWNLOAD_APP_URL = download_app_url;
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
	
	public String getLogin_smscode_check() {
		return login_smscode_check;
	}
	public void setLogin_smscode_check(String login_smscode_check) {
		this.login_smscode_check = login_smscode_check;
	}
	
	public String getWeb_homepage_api_url() {
		return web_homepage_api_url;
	}
	public void setWeb_homepage_api_url(String web_homepage_api_url) {
		this.web_homepage_api_url = web_homepage_api_url;
	}

//	public String getJpush_app_key() {
//		return jpush_app_key;
//	}
//
//	public void setJpush_app_key(String jpush_app_key) {
//		this.jpush_app_key = jpush_app_key;
//	}
//
//	public String getJpush_master_secret() {
//		return jpush_master_secret;
//	}
//
//	public void setJpush_master_secret(String jpush_master_secret) {
//		this.jpush_master_secret = jpush_master_secret;
//	}
//
//	public Boolean getJpush_ios_apns() {
//		return jpush_ios_apns;
//	}
//
//	public void setJpush_ios_apns(Boolean jpush_ios_apns) {
//		this.jpush_ios_apns = jpush_ios_apns;
//	}

	public String getSpecial_jpush_app_key() {
		return special_jpush_app_key;
	}

	public void setSpecial_jpush_app_key(String special_jpush_app_key) {
		this.special_jpush_app_key = special_jpush_app_key;
	}

	public String getSpecial_jpush_master_secret() {
		return special_jpush_master_secret;
	}

	public void setSpecial_jpush_master_secret(String special_jpush_master_secret) {
		this.special_jpush_master_secret = special_jpush_master_secret;
	}

	public Boolean getSpecial_jpush_ios_apns() {
		return special_jpush_ios_apns;
	}

	public void setSpecial_jpush_ios_apns(Boolean special_jpush_ios_apns) {
		this.special_jpush_ios_apns = special_jpush_ios_apns;
	}

	public String getSms_cloud_order_url() {
		return sms_cloud_order_url;
	}

	public void setSms_cloud_order_url(String sms_cloud_order_url) {
		this.sms_cloud_order_url = sms_cloud_order_url;
	}

	public String getSchool_web_url() {
		return school_web_url;
	}

	public void setSchool_web_url(String school_web_url) {
		this.school_web_url = school_web_url;
	}


	public String getCy_server_url() {
		return cy_server_url;
	}

	public void setCy_server_url(String cy_server_url) {
		this.cy_server_url = cy_server_url;
	}

	public String getWechat_api_url() {
		return wechat_api_url;
	}

	public void setWechat_api_url(String wechat_api_url) {
		this.wechat_api_url = wechat_api_url;
	}

	public String getWechat_api_callback_path() {
		return wechat_api_callback_path;
	}

	public void setWechat_api_callback_path(String wechat_api_callback_path) {
		this.wechat_api_callback_path = wechat_api_callback_path;
	}

	public String getFounUrl() {
		return founUrl;
	}

	public void setFounUrl(String founUrl) {
		this.founUrl = founUrl;
	}

	public String getOauth_server_url() {
		return oauth_server_url;
	}

	public void setOauth_server_url(String oauth_server_url) {
		this.oauth_server_url = oauth_server_url;
	}

	public String getCloud_server_url() {
		return cloud_server_url;
	}

	public void setCloud_server_url(String cloud_server_url) {
		this.cloud_server_url = cloud_server_url;
	}

	public String getCloud_file_url() {
		return cloud_file_url;
	}

	public void setCloud_file_url(String cloud_file_url) {
		this.cloud_file_url = cloud_file_url;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getCloud_user_password() {
		return cloud_user_password;
	}

	public void setCloud_user_password(String cloud_user_password) {
		this.cloud_user_password = cloud_user_password;
	}

	public String getCloud_user_name() {
		return cloud_user_name;
	}

	public void setCloud_user_name(String cloud_user_name) {
		this.cloud_user_name = cloud_user_name;
	}

	
	public String getJwt_secret() {
		return jwt_secret;
	}

	public void setJwt_secret(String jwt_secret) {
		this.jwt_secret = jwt_secret;
	}

	public String getJwt_ttl() {
		return jwt_ttl;
	}

	public void setJwt_ttl(String jwt_ttl) {
		this.jwt_ttl = jwt_ttl;
	}

	public String getJwt_refresh_interval() {
		return jwt_refresh_interval;
	}

	public void setJwt_refresh_interval(String jwt_refresh_interval) {
		this.jwt_refresh_interval = jwt_refresh_interval;
	}

	public String getJwt_refresh_ttl() {
		return jwt_refresh_ttl;
	}

	public void setJwt_refresh_ttl(String jwt_refresh_ttl) {
		this.jwt_refresh_ttl = jwt_refresh_ttl;
	}

	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		buffer.append("\""+"disk_path"+"\""+":"+"\""+disk_path+"\",");
		buffer.append("\""+"linux_url_domain"+"\""+":"+"\""+linux_url_domain+"\",");
		buffer.append("\""+"win_url_domain"+"\""+":"+"\""+win_url_domain+"\",");
		buffer.append("\""+"sign"+"\""+":"+"\""+sign+"\",");
		buffer.append("\""+"schoolSign"+"\""+":"+"\""+schoolSign +"\",");
		buffer.append("\""+"deptNo"+"\""+":"+"\""+deptNo+"\",");
//		buffer.append("\""+"tigase_admin_account"+"\""+":"+"\""+tigase_admin_account+"\",");
//		buffer.append("\""+"tigase_admin_passwd"+"\""+":"+"\""+tigase_admin_passwd+"\",");
//		buffer.append("\""+"tigase_server_ip"+"\""+":"+"\""+tigase_server_ip+"\",");
//		buffer.append("\""+"tigase_server_port"+"\""+":"+"\""+tigase_server_port+"\",");
//		buffer.append("\""+"tigase_server_domain"+"\""+":"+"\""+tigase_server_domain+"\",");
//		buffer.append("\""+"tigase_db_ip"+"\""+":"+"\""+tigase_db_ip+"\",");
//		buffer.append("\""+"tigase_db_port"+"\""+":"+"\""+tigase_db_port+"\",");
//		buffer.append("\""+"tigase_db_user"+"\""+":"+"\""+tigase_db_user+"\",");
//		buffer.append("\""+"tigase_db_password"+"\""+":"+"\""+tigase_db_password+"\",");
//		buffer.append("\""+"tigase_db_conn_size"+"\""+":"+"\""+tigase_db_conn_size+"\",");
//		buffer.append("\""+"tigase_db_name"+"\""+":"+"\""+tigase_db_name+"\",");
		buffer.append("\""+"activemq_server_ip"+"\""+":"+"\""+activemq_server_ip+"\",");
		buffer.append("\""+"activemq_server_port"+"\""+":"+"\""+activemq_server_port+"\",");
		buffer.append("\""+"activemq_connection_admin_user"+"\""+":"+"\""+activemq_connection_admin_user+"\",");
		buffer.append("\""+"activemq_connection_admin_password"+"\""+":"+"\""+activemq_connection_admin_password+"\",");
		buffer.append("\""+"activemq_server_general_channel_name"+"\""+":"+"\""+activemq_server_general_channel_name+"\",");
		buffer.append("\""+"client_release_checkcode"+"\""+":"+"\""+client_release_checkcode+"\",");
		buffer.append("\""+"phone_secret_key"+"\""+":"+"\""+phone_secret_key+"\",");
		buffer.append("\""+"authenticated_secret_key"+"\""+":"+"\""+authenticated_secret_key+"\",");
		buffer.append("\""+"FACE_FILE_DIR"+"\""+":"+"\""+FACE_FILE_DIR+"\",");
		buffer.append("\""+"REGULAR_CHANNEL_1"+"\""+":"+"\""+REGULAR_CHANNEL_1+"\",");
		buffer.append("\""+"REGULAR_CHANNEL_2"+"\""+":"+"\""+REGULAR_CHANNEL_2+"\",");
		buffer.append("\""+"REGULAR_CHANNEL_3"+"\""+":"+"\""+REGULAR_CHANNEL_3+"\",");
		buffer.append("\""+"DOWNLOAD_APP_URL"+"\""+":"+"\""+DOWNLOAD_APP_URL+"\",");
		buffer.append("\""+"smtpHost"+"\""+":"+"\""+smtpHost+"\",");
		buffer.append("\""+"smtpPort"+"\""+":"+"\""+smtpPort+"\",");
		buffer.append("\""+"email_account"+"\""+":"+"\""+email_account+"\",");
		buffer.append("\""+"email_password"+"\""+":"+"\""+email_password+"\",");
		buffer.append("\""+"login_smscode_check"+"\""+":"+"\""+login_smscode_check+"\",");
		buffer.append("\""+"web_homepage_api_url"+"\""+":"+"\""+web_homepage_api_url+"\",");
		buffer.append("\""+"SMS_CLOUD_ORDER_URL"+"\""+":"+"\""+sms_cloud_order_url+"\",");
		buffer.append("\""+"cy_server_url"+"\""+":"+"\""+cy_server_url+"\",");
		buffer.append("\""+"wechat_api_url"+"\""+":"+"\""+wechat_api_url+"\",");
		buffer.append("\""+"wechat_api_callback_path"+"\""+":"+"\""+wechat_api_callback_path+"\",");
		buffer.append("\""+"founUrl"+"\""+":"+"\""+founUrl+"\",");
		buffer.append("\""+"isRichTextConvert"+"\""+":"+"\""+isRichTextConvert+"\",");
//		buffer.append("\""+"client_id"+"\""+":"+"\""+client_id+"\",");
//		buffer.append("\""+"client_secret"+"\""+":"+"\""+client_secret+"\",");
//		buffer.append("\""+"cloud_user_name"+"\""+":"+"\""+cloud_user_name+"\",");
//		buffer.append("\""+"cloud_user_password"+"\""+":"+"\""+cloud_user_password+"\",");
		buffer.append("\""+"oauth_server_url"+"\""+":"+"\""+oauth_server_url+"\",");
		buffer.append("\""+"cloud_server_url"+"\""+":"+"\""+cloud_server_url+"\",");
		buffer.append("\""+"cloud_file_url"+"\""+":"+"\""+cloud_file_url+"\",");
		buffer.append("\""+"jwt_secret"+"\""+":"+"\""+jwt_secret+"\",");
		buffer.append("\""+"jwt_refresh_interval"+"\""+":"+"\""+jwt_refresh_interval+"\",");
		buffer.append("\""+"jwt_refresh_ttl"+"\""+":"+"\""+jwt_refresh_ttl+"\",");
		buffer.append("\""+"jwt_ttl"+"\""+":"+"\""+jwt_ttl+"\"");
		buffer.append("}");
		return buffer.toString();
	}
	
}
