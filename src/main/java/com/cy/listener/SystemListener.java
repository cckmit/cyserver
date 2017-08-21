package com.cy.listener;

import java.util.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.alibaba.druid.filter.config.ConfigTools;
import com.cy.system.ConfigProperties;
import com.cy.system.DESPlus;
import com.cy.system.Global;
import com.cy.system.InitDB;
import com.cy.util.WebUtil;

public class SystemListener implements ServletContextListener {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SystemListener.class);

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

	/** --容器初始化-- **/
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("config");
			String os = System.getProperty("os.name");
			// 无法从配置中心得到参数，读本地
			setProperties2(servletContextEvent, os, bundle);
			// web数据库初始化
			String jdbc_url = bundle.getString("jdbc_url");
			String jdbc_username = bundle.getString("jdbc_username");
			String jdbc_password = ConfigTools.decrypt(bundle.getString("jdbc_password"));
			InitDB.initDB(jdbc_url, jdbc_username, jdbc_password);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/** --设置属性文件的内容,从本地读取-- **/
	public void setProperties2(ServletContextEvent servletContextEvent, String os, ResourceBundle bundles) throws Exception {
		// 以下为属性文件的字段
		ConfigProperties config = new ConfigProperties();
		config.setDisk_path(bundles.getString("disk_path"));
		config.setLinux_url_domain(bundles.getString("linux_url_domain"));
		config.setWin_url_domain(bundles.getString("win_url_domain"));
		config.setSchoolSign(bundles.getString("schoolSign"));
		config.setDeptNo(bundles.getString("deptNo"));
		config.setActivemq_server_ip(bundles.getString("activemq_server_ip"));
		config.setActivemq_server_port(WebUtil.toInt(bundles.getString("activemq_server_port")));
		config.setActivemq_connection_admin_user(bundles.getString("activemq_connection_admin_user"));
		config.setActivemq_connection_admin_password(bundles.getString("activemq_connection_admin_password"));
		config.setActivemq_server_general_channel_name(bundles.getString("activemq_server_general_channel_name"));
//		config.setTigase_server_domain(bundles.getString("tigase_server_domain"));
//		config.setTigase_db_ip(bundles.getString("tigase_db_ip"));
//		config.setTigase_db_port(WebUtil.toInt(bundles.getString("tigase_db_port")));
//		config.setTigase_db_user(bundles.getString("tigase_db_user"));
//		config.setTigase_db_password(bundles.getString("tigase_db_password"));
//		config.setTigase_db_conn_size(WebUtil.toInt(bundles.getString("tigase_db_conn_size")));
//		config.setTigase_db_name(bundles.getString("tigase_db_name"));
		config.setClient_release_checkcode(bundles.getString("client_release_checkcode"));
		config.setAuthenticated_secret_key(bundles.getString("authenticated_secret_key"));
		config.setPhone_secret_key(bundles.getString("phone_secret_key"));
		config.setREGULAR_CHANNEL_1(bundles.getString("REGULAR_CHANNEL_1"));
		config.setREGULAR_CHANNEL_2(bundles.getString("REGULAR_CHANNEL_2"));
		config.setREGULAR_CHANNEL_3(bundles.getString("REGULAR_CHANNEL_3"));
		config.setLogin_smscode_check(bundles.getString("login_smscode_check"));
		config.setWeb_homepage_api_url(bundles.getString("web_homepage_api_url"));
		config.setCy_server_url(bundles.getString("cy_server_url"));
		// 校友会网站地址
		config.setSchool_web_url(bundles.getString("school_web_url"));
		// 微信接口访问
		config.setWechat_api_url(bundles.getString("wechat_api_url"));
		// 微信网页回调地址
		config.setWechat_api_callback_path(bundles.getString("wechat_api_callback_path"));
		boolean founUrl1 = bundles.containsKey("founUrl");
		if (founUrl1){
			config.setFounUrl(bundles.getString("founUrl"));
		}
		//富文本配置 lixun 2017.5.5
		if( bundles.containsKey( "rich_text_convert" ) )
		{
			config.setIsRichTextConvert( bundles.getString( "rich_text_convert" ) );
		}else{
			config.setIsRichTextConvert("0");
		}

//		config.setClient_id(bundles.getString("client_id"));
//		config.setClient_secret(bundles.getString("client_secret"));
//		config.setCloud_user_name(bundles.getString("cloud_user_name"));
//		config.setCloud_user_password(bundles.getString("cloud_user_password"));
		config.setOauth_server_url(bundles.getString("oauth_server_url"));
		config.setCloud_server_url(bundles.getString("cloud_server_url"));
		config.setCloud_file_url(bundles.getString("cloud_file_url"));
		// 极光KEY(通用版)
//		config.setJpush_app_key(bundles.getString("JPUSH_APP_KEY"));
//		config.setJpush_master_secret(bundles.getString("JPUSH_MASTER_SECRET"));
//		if(StringUtils.isNotBlank(bundles.getString("JPUSH_IOS_APNS")) ){
//			config.setJpush_ios_apns(Boolean.valueOf(bundles.getString("JPUSH_IOS_APNS").trim()));
//		} else {
//			config.setJpush_ios_apns(false);
//		}
		// 极光KEY(专用版)
		config.setSpecial_jpush_app_key(bundles.getString("SPECIAL_JPUSH_APP_KEY"));
		config.setSpecial_jpush_master_secret(bundles.getString("SPECIAL_JPUSH_MASTER_SECRET"));
		if(StringUtils.isNotBlank(bundles.getString("SPECIAL_JPUSH_IOS_APNS")) ){
			config.setSpecial_jpush_ios_apns(Boolean.valueOf(bundles.getString("SPECIAL_JPUSH_IOS_APNS").trim()));
		} else {
			config.setSpecial_jpush_ios_apns(false);
		}

		config.setSms_cloud_order_url(bundles.getString("SMS_CLOUD_ORDER_URL"));
		
		//token相关设置
		config.setJwt_secret(bundles.getString("jwt_secret"));
		config.setJwt_ttl(bundles.getString("jwt_ttl"));
		config.setJwt_refresh_ttl(bundles.getString("jwt_refresh_ttl"));
		config.setJwt_refresh_interval(bundles.getString("jwt_refresh_interval"));
			
		// 密码数据解密
		this.setGlobalValue(servletContextEvent, os, config);
		logger.info("##############################加载本地项目的属性文件成功##############################");
	}

	/** --Global赋值,密码数据解密-- **/
	private void setGlobalValue(ServletContextEvent servletContextEvent, String os, ConfigProperties config) throws Exception {
		// 密码数据解密
		DESPlus dESPlus = DESPlus.getInstance();
		Global.schoolSign = config.getSchoolSign();
		Global.deptNo = config.getDeptNo();
		Global.activemq_server_ip = config.getActivemq_server_ip();
		Global.activemq_server_port = config.getActivemq_server_port();
		Global.activemq_connection_admin_user = config.getActivemq_connection_admin_user();
		Global.activemq_connection_admin_password = dESPlus.decrypt(config.getActivemq_connection_admin_password());
		Global.activemq_server_general_channel_name = config.getActivemq_server_general_channel_name();
//		Global.tigase_server_domain = config.getTigase_server_domain();
//		Global.tigase_db_ip = config.getTigase_db_ip();
//		Global.tigase_db_port = config.getTigase_db_port();
//		Global.tigase_db_user = config.getTigase_db_user();
//		Global.tigase_db_password = ConfigTools.decrypt(config.getTigase_db_password());
//		Global.tigase_db_conn_size = config.getTigase_db_conn_size();
//		Global.tigase_db_name = config.getTigase_db_name();
		Global.client_release_checkcode = config.getClient_release_checkcode();
		Global.authenticated_secret_key = config.getAuthenticated_secret_key();
		Global.phone_secret_key = config.getPhone_secret_key();
		Global.REGULAR_CHANNEL_1 = config.getREGULAR_CHANNEL_1();
		Global.REGULAR_CHANNEL_2 = config.getREGULAR_CHANNEL_2();
		Global.REGULAR_CHANNEL_3 = config.getREGULAR_CHANNEL_3();
		Global.login_smscode_check = config.getLogin_smscode_check();
		Global.web_homepage_api_url = config.getWeb_homepage_api_url();
		Global.cy_server_url = config.getCy_server_url();

		// 极光账号(通用版)
//		Global.JPUSH_APP_KEY = config.getJpush_app_key() ;
//		Global.JPUSH_MASTER_SECRET = config.getJpush_master_secret() ;
//		Global.JPUSH_IOS_APNS = config.getJpush_ios_apns() ;

		// 极光账号(专用版)
		Global.SPECIAL_JPUSH_APP_KEY = config.getSpecial_jpush_app_key() ;
		Global.SPECIAL_JPUSH_MASTER_SECRET = config.getSpecial_jpush_master_secret() ;
		Global.SPECIAL_JPUSH_IOS_APNS = config.getSpecial_jpush_ios_apns() ;

		Global.SMS_CLOUD_ORDER_URL = config.getSms_cloud_order_url();

		// 校友会网站地址
		Global.school_web_url = config.getSchool_web_url();

		// 微信访问接口
		Global.wechat_api_url = config.getWechat_api_url();

		// 微信网页回调地址
		 Global.wechat_api_callback_path = config.getWechat_api_callback_path();


		 Global.founUrl = config.getFounUrl();

		//富文本是否转换 lixun 2017.5.5
		Global.IS_RICH_TEXT_CONVERT = Integer.valueOf( config.getIsRichTextConvert() );

//		Global.client_id = config.getClient_id();

//		Global.client_secret = config.getClient_secret();

//		Global.cloud_user_name = config.getCloud_user_name();

//		Global.cloud_user_password = config.getCloud_user_password();

		Global.oauth_server_url = config.getOauth_server_url();
		Global.cloud_server_url = config.getCloud_server_url();
		Global.cloud_file_url = config.getCloud_file_url();
		
		Global.jwt_secret=config.getJwt_secret();
		Global.jwt_ttl=config.getJwt_ttl();
		Global.jwt_refresh_ttl=config.getJwt_refresh_ttl();
		Global.jwt_refresh_interval=config.getJwt_refresh_interval();

		if (os.startsWith("win") || os.startsWith("Win")) {
//			Global.DISK_PATH = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf("bin")) + "webapps/";
			Global.DISK_PATH = config.getDisk_path();
			Global.URL_DOMAIN = config.getWin_url_domain();
			servletContextEvent.getServletContext().setAttribute("URL_DOMAIN", config.getWin_url_domain());
		}
		if (os.startsWith("linux") || os.startsWith("Linux") || os.startsWith("Mac OS X")) {
			Global.DISK_PATH = config.getDisk_path();
			Global.URL_DOMAIN = config.getLinux_url_domain();
			servletContextEvent.getServletContext().setAttribute("URL_DOMAIN", config.getLinux_url_domain());
		}
		servletContextEvent.getServletContext().setAttribute("schoolSign", config.getSchoolSign());
	}
}
