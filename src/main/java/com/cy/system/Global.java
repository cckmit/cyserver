package com.cy.system;

public class Global {
	/**
	 * 文件保存目录路径
	 */
	public static String DISK_PATH = "";

	/**
	 * 文件保存目录URL
	 */
	public static String URL_DOMAIN = "";

	/**
	 * 网关签名
	 */
	public static String sign = "";

	/**
	 * 各学校签名
	 */
	public static String schoolSign = "";

	/**
	 * 机构编号
	 */
	public static String deptNo = "";

//	/**
//	 * 管理员账户
//	 */
//	public static String tigase_admin_account = "";
//
//	/**
//	 * 管理员密码
//	 */
//	public static String tigase_admin_passwd = "";
//
//	/**
//	 * 聊天服务器ip地址
//	 */
//	public static String tigase_server_ip = "";
//
//	/**
//	 * 聊天服务器端口号
//	 */
//	public static int tigase_server_port = 5222;
//
//	/**
//	 * 聊天服务器domain
//	 */
//	public static String tigase_server_domain = "";
//
//	/**
//	 * 聊天服务器数据库ip
//	 */
//	public static String tigase_db_ip = "";
//
//	/**
//	 * 聊天服务器数据库port
//	 */
//	public static int tigase_db_port = 3306;
//
//	/**
//	 * 聊天服务器数据库用户
//	 */
//	public static String tigase_db_user = "";
//
//	/**
//	 * 聊天服务器数据库密码
//	 */
//	public static String tigase_db_password = "";
//
//	/**
//	 * 聊天服务器数据库连接池对象数量
//	 */
//	public static int tigase_db_conn_size = 5;
//
//	/**
//	 * 聊天服务器数据库名称
//	 */
//	public static String tigase_db_name = "";

	/**
	 * 推送服务器ip
	 */
	public static String activemq_server_ip = "";

	/**
	 * 推送服务器端口号
	 */
	public static int activemq_server_port = 0;

	/**
	 * 推送服务连接管理员
	 */
	public static String activemq_connection_admin_user = "";

	/**
	 * 推送服务连接管理员密码
	 */
	public static String activemq_connection_admin_password = "";

	/**
	 * 推送服务公用通道
	 */
	public static String activemq_server_general_channel_name = "";

	/**
	 * 手机端软件下载秘钥
	 */
	public static String client_release_checkcode = "";

	/**
	 * 注册码获取秘钥
	 */
	public static String phone_secret_key = "";

	/**
	 * 后台认证用户秘钥
	 */
	public static String authenticated_secret_key = "";

	/**
	 * 用户图像存储文件夹名
	 */
	public static String FACE_FILE_DIR = "";

	/**
	 * 用户默认监听通道
	 */
	public static String REGULAR_CHANNEL_1 = "";

	/**
	 * 用户默认监听通道
	 */
	public static String REGULAR_CHANNEL_2 = "";

	/**
	 * 用户默认监听通道
	 */
	public static String REGULAR_CHANNEL_3 = "";

	/**
	 * 短信开关
	 */
	public static boolean SMSON = false;

	/** --财大web平台的id-- **/
	public static String school_id = "000090";

	/**
	 * 邮箱服务器地址
	 */
	public static String smtpHost = "";

	/**
	 * 邮箱服务器端口
	 */
	public static String smtpPort = "";

	/**
	 * 邮箱帐号
	 */
	public static String email_account = "";

	/**
	 * 邮箱密码
	 */
	public static String email_password = "";

	/**
	 * 短信账号
	 */
	public static String userAccount = "";
	/**
	 * 短信密码
	 */
	public static String password = "";
	/**
	 * 短信url
	 */
	public static String smsUrl = "";

	/**
	 * 短信发送方式，HTTP,sdk,webservice
	 */
	public static String sendType = "";

	/**
	 * 验证码短信模板
	 */
	public static String smsCodeTemplate = "";

	/**
	 * 邀请短信模板
	 *
	 */
	public static String smsVisitTemplate = "";

	/**
	 * 生日祝福短信模板
	 *
	 */
	public static String smsBirthdayTemplate = "";

	public static String login_smscode_check = "";

	public static String web_homepage_api_url = "";


	/**
	 * 学校Logo
	 */
	public static String schoolLogo = "";

	/**
	 * 是否开通分会新闻专栏
	 */
	public static String is_new_type_open = "";

	// 极光账号信息(通用版)
//	public static String JPUSH_APP_KEY ;
//	public static String JPUSH_MASTER_SECRET ;
//	public static Boolean JPUSH_IOS_APNS ;
	// 极光账号信息(专用版)
	public static String SPECIAL_JPUSH_APP_KEY ;
	public static String SPECIAL_JPUSH_MASTER_SECRET ;
	public static Boolean SPECIAL_JPUSH_IOS_APNS ;

	/**
	 * 短信充值鏈接
	 */
	public static String SMS_CLOUD_ORDER_URL;

	/**
	 * 校友会网站地址
	 */
	public static String school_web_url;

	/**
	 * 网页链接地址
	 */
	public static String cy_server_url;

	// 微信访问接口地址
	public static String wechat_api_url;

	// 微信网页回调地址
	public static String wechat_api_callback_path;

	public static String FOUNDATION_NAME;

	public static String FOUNDATION_SIGNET;

	public static String FOUNDATION_LOGO;

	public static String founUrl;



	/**
	 * 基金会邮箱服务器地址
	 */
	public static String foundation_smtpHost = "";

	/**
	 * 基金会邮箱服务器端口
	 */
	public static String foundation_smtpPort = "";

	/**
	 * 基金会邮箱帐号
	 */
	public static String foundation_email_account = "";

	/**
	 * 基金会邮箱密码
	 */
	public static String foundation_email_password = "";

	/**
	 * 基金会邮箱密码
	 */
	public static String is_audit_personal_activity = "";


	/**
	 * 富文本是否转换Base64
	 * lixun 2017.5.5
	 */
	public static int IS_RICH_TEXT_CONVERT = 0;

	//云平台客户端id
	public static String client_id ;
	//云平台客户端密钥
	public static String client_secret ;
	//云平台授权地址
	public static String oauth_server_url ;
	//云平台接口访问地址
	public static String cloud_file_url;
	//云平台接口访问地址
	public static String cloud_server_url;
	//云平台帐号
	public static String cloud_user_name ;
	//云平台密码
	public static String cloud_user_password ;

	public static String jwt_secret;	//token加密字符串
	public static String jwt_ttl;		//token 有效时间	
	public static String jwt_refresh_interval;	//refreshtoken,重复获取间隔时间	
	public static String jwt_refresh_ttl;  //refreshtoken token有效时间
	
}
