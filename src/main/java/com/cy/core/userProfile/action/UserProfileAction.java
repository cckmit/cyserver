package com.cy.core.userProfile.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.token.Constant;
import com.cy.common.utils.token.JwtUtil;
import com.cy.core.actActivity.entity.ActActivity;
import com.cy.core.actActivity.service.ActActivityService;
import com.cy.core.activity.service.ActivityService;
import com.cy.core.activityApplicant.service.ActivityApplicantService;
import com.cy.core.activityPrize.service.ActivityPrizeService;
import com.cy.core.activityWinning.service.ActivityWinningService;
import com.cy.core.alipay.service.WechatPayService;
import com.cy.core.alumniAssociation.service.AlumniAssociationService;
import com.cy.core.alumnicard.service.AlumniCardService;
import com.cy.core.association.service.AssociationMemberService;
import com.cy.core.association.service.AssociationService;
import com.cy.core.backschoolOnlineSign.service.BackschoolOnlineSignService;
import com.cy.core.campuscard.service.CampusCardService;
import com.cy.core.channel.service.NewsChannelService;
import com.cy.core.chatContacts.service.ChatContactsService;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.chatGroupUser.service.ChatGroupUserService;
import com.cy.core.classHandle.service.ClassHandleService;
import com.cy.core.dataMining.service.DataMiningService;
import com.cy.core.dept.service.DeptService;
import com.cy.core.deptInfo.service.DeptInfoService;
import com.cy.core.dicttype.service.DictTypeService;
import com.cy.core.discover.service.DiscoverService;
import com.cy.core.donation.service.DonationService;
import com.cy.core.electronicBook.service.ElectronicBookService;
import com.cy.core.enterprise.service.EnterpriseProductService;
import com.cy.core.enterprise.service.EnterpriseService;
import com.cy.core.enterprise.service.EnterpriseTeamService;
import com.cy.core.enterpriseJob.service.EnterpriseJobService;
import com.cy.core.event.service.EventService;
import com.cy.core.express.service.ExpressService;
import com.cy.core.industry.service.IndustryService;
import com.cy.core.inviteSms.service.InviteSmsService;
import com.cy.core.live.service.LiveService;
import com.cy.core.messageboard.service.MessageBoardService;
import com.cy.core.mobevent.service.MobEventService;
import com.cy.core.news.service.MobNewsTypeService;
import com.cy.core.news.service.NewsService;
import com.cy.core.notify.service.NotifyService;
import com.cy.core.operation.service.CommentService;
import com.cy.core.operation.service.FavoriteService;
import com.cy.core.project.service.ProjectService;
import com.cy.core.region.service.RegionService;
import com.cy.core.report.service.ReportService;
import com.cy.core.resumeBase.entity.ResumeBase;
import com.cy.core.resumeBase.service.ResumeBaseService;
import com.cy.core.schoolServ.service.SchoolServService;
import com.cy.core.serviceNewsType.service.ServiceNewsTypeService;
import com.cy.core.share.service.ShareService;
import com.cy.core.systemsetting.service.SystemSettingService;
import com.cy.core.user.service.UserAuthService;
import com.cy.core.user.service.UserService;
import com.cy.core.userCollection.service.UserCollectionService;
import com.cy.core.userProfile.service.AppService;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.core.weiXin.service.WeiXinMenuService;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.core.weiXin.service.WeiXinUserService;
import com.cy.mobileInterface.activity.service.mActivityService;
import com.cy.mobileInterface.alumni.service.AlumniService;
import com.cy.mobileInterface.around.service.AroundService;
import com.cy.mobileInterface.authenticated.service.AuthenticatedService;
import com.cy.mobileInterface.baseinfo.service.BaseInfoService;
import com.cy.mobileInterface.complain.service.mComplainService;
import com.cy.mobileInterface.contact.service.mContactService;
import com.cy.mobileInterface.datamining.service.DataminingService;
import com.cy.mobileInterface.department.service.DepartmentService;
import com.cy.mobileInterface.group.service.GroupService;
import com.cy.mobileInterface.industryAlumni.service.IndustryAlumniService;
import com.cy.mobileInterface.member.service.mMemberService;
import com.cy.mobileInterface.multipleFileUpload.service.MultipleFileUploadService;
import com.cy.mobileInterface.password.service.PassWordService;
import com.cy.mobileInterface.qrCode.service.QrCodeService;
import com.cy.mobileInterface.register.service.RegisterService;
import com.cy.mobileInterface.registerCode.service.RegisterCodeService;
import com.cy.mobileInterface.util.service.mUtilService;
import com.cy.util.WebUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Namespace("/userProfile")
@Action(value = "userProfileAction", results = { @Result(name = "qrcode", location = "/qrcode/user_info.jsp") })
public class UserProfileAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserProfileAction.class);

	public static final String USER_PROFILE_UPLOAD_FILE = "1"; // 文件上传

	public static final String USER_PROFILE_GET_USER_BASE_INFO_ID_LIST = "2"; // 根据姓名获取班级和基础id

	public static final String USER_PROFILE_GET_REGISTER_CODE = "3"; // 获取短信验证码

	public static final String USER_PROFILE_REGISTER = "4";// 提交注册信息

	// 5号接口与23号接口合并
	public static final String USER_PROFILE_UPDATE_USER_PROFILE = "5"; // 用户信息更改

	public static final String USER_PROFILE_IMAGE_FILE_UPLOAD = "6";// 用户头像上传接口

	// 7号接口待定

	public static final String USER_PROFILE_GET_CLASSMATES_INFO_LIST = "8";// 获取某个班级的所有名单接口

	// 9号接口与22号接口合并
	public static final String USER_PROFILE_SEARCH_FOR_USERS = "9"; // 搜索用户

	public static final String USER_PROFILE_GET_USER_SELF_PROFILE = "10"; // 手机登陆接口

	public static final String USER_PROFILE_GET_FRIEND_PROFILE = "11"; // 根据账号和密码，好友的账号，手机号或者基础id，获取用户好友的基本信息

	public static final String USER_PROFILE_UPDATE_GROUP_INFO = "12"; // 修改群表信息

	public static final String USER_PROFILE_GET_GROUP_INFO = "13"; // 根据账号、密码、群名获取群成员账号信息

	public static final String USER_PROFILE_UPDATE_CLASSMATE_TEL = "14"; // 数据挖掘更新班级同学手机号，并发送注册邀请短信

	public static final String USER_PROFILE_AUTHENTICATED = "15"; // 用户认证

	public static final String USER_PROFILE_CHANGE_PASSWORD = "16"; // 修改密码

	//17号接口未使用了
	public static final String USER_PROFILE_GET_GROUP_MEMBERS_INFO = "17";// 批量获取群组非好友成员的信息

	public static final String USER_PROFILE_GET_CLASS_NAME = "18";// 根据班级id获取班级名称

	public static final String USER_PROFILE_SEARCH_FOR_USERS_NEW = "22";// 根据姓名和其它信息搜索用户

	public static final String USER_PROFILE_UPDATE_USER_PROFILE_NEW = "23"; // 用户个人资料修改

	public static final String GET_ALL_LINK_OF_CATEGORYS = "24" ;		// 获取全部栏目

	//25号接口没有被手机端使用
	public static final String USER_QR_CODE_ADDRESS = "25"; // 为用户生成二维码的地址

	public static final String GET_NEARBY_USER = "26"; // 查询附近的校友

	public static final String GET_USER_BY_ACCOUNT_NUM = "27"; // 根据accountNum查询用户信息

	public static final String GET_ALNMNI = "28"; // 查询所有的校友会

	public static final String GET_USER_BY_GROUP = "29"; // 根据群ID返回群内所有用户信息

	public static final String SAVE_BACKSCHOOL_BOARD="31";//保存返校计划花絮

	public static final String FIND_BACKSCHOOL_BOARD_LIST="32";//获取返校计划花絮列表

	public static final String FIND_BACKSCHOOL_BOARD="33";//获取返校计划花絮详情

	public static final String SAVE_BACKSCHOOL_BOARD_COMMENT_OR_PRAISE="34";//保存返校计划花絮评价或点赞

	public static final String SAVE_BACKSCHOOL_BOARD_COMPLAINT="35";//保存返校计划花絮举报

	public static final String REMOVE_BACKSCHOOL_BOARD="36";//删除返校计划花絮

	//public static final String GET_INDUSTRY = "30"; // 查询所有行业校友群

	//QPF 16-08-04

	public static final String CHANGE_USER_PHONE_NUM = "201"; 	//改绑手机号

	public static final String USER_PROFILE_AUTHENTICATED_BY_STU_NUM = "202";	// 通过学号进行认证

	public static final String USER_PROFILE_AUTHENTICATED_BY_CARD= "203";	//通过身份证号进行认证

	public static final String INVITE_OTHERS = "204";	//发送邀请码

	//16-08-05

	public static final String USER_PROFILE_AUTHENTICATED_BY_INVITE_CODE = "205";	//邀请码认认证接口

	//16-08-07

	public static final String CANCLE_USER_AUTHENTICATION = "206";	//取消认证接口

	//16-08-14

	public static final String JOIN_ALUMNIS = "207";	//批量加入地方、行业分会

	public static final String JOIN_ALUMNI = "208";		//单个加入地方、行业分会

	public static final String LEFT_ALUMNIS = "209";		//退出分会

	//16-08-17

	public static final String CLASS_ADMIN_EDIT_CLASSMATES = "210";	//班级管理员编辑本班同学

	public static final String LIST_OF_DEPTS = "211";		//学校、学院、年级、班级列表

	//16-08-18
	public static final String ADD_USER_INFO = "212";		//手动输入学校、学院、年级、班级接口

	//16-08-19
	public static final String ADD_PERSONAL_EVENT = "213";	//添加个人活动接口

	//16-08-20
	public static final String SHOW_EVENT = "214";		//活动详情接口+用户列表

	//16-08-22
	public static final String JOIN_EVENT = "215";			//加入活动接口

	public static final String REMOVE_EVENT = "216";			//删除活动接口

	public static final String GET_INFO_OF_SIGNER = "217";		//获取活动报名人信息接口

	public static final String GET_AUTHENTICATED_NAME = "218";	//获取认证所需的姓名

	public static final String SHOW_EVENT_MEMBERS = "219";		//获取活动报名人列表接口

	public static final String FEED_BACK = "220";		//反馈接口

	//16-08-25
	public static final String FIND_NEWS_LIST_FOR_MOBILE = "221";	//查询新闻列表接口

	public static final String SAVE_EVENT_BOARD = "222";	//活动花絮创建接口

	public static final String SAVE_COMMENT_OR_PRAISE = "223";	//活动花絮评价／点赞接口

	public static final String SAVE_COMPLAINT = "224";		//活动花絮反馈接口

	public static final String FIND_EVENT_BOARD_LIST = "225";	//活动花絮列表接口

	public static final String FIND_EVENT_BOARD = "226";	//花絮详情接口

	//16-08-26
	public static final String FIND_MOB_NEWS = "227";	//新闻详情

	public static final String FIND_PROJECT = "228";	//捐赠项目详情接口

	public static final String FINT_MY_DONATION = "229";	//我的捐赠项目详情

	public static final String SAVE_DONATION_ORDER = "230";	//捐赠订单下单

	public static final String DONATION_PAY_FEED_BACK = "231";	//捐赠订单支付回调接口

	//16-09-02
	public static final String FIND_EVENT_TYPE = "232";		//活动类型列表

	//16-09-07
	public static final String REMOVE_EVENT_BOARD = "233";	//刪除活動花絮接口

	//16-09-12
	public static final String FIND_SERVICE_NEWS_TYPE = "234";	//获取服务新闻栏目列表

	//16-09-13
	public static final String FIND_WEB_NEWS_TYPE = "235";	//获取Web新闻栏目列表

	public static final String FIND_WEB_NEWS_LIST = "236";	//获取Web新闻列表

	public static final String FIND_WEB_NEWS = "237";	//获取Web新闻详情

	//16-09-19
	public static final String FIND_SERVICE_NEWS = "238";	//获取服务新闻列表

	//16-09-26
	public static final String FIND_NEWS_TYPE = "239";		//获取新闻栏目列表

	//16-10-09
	public static final String ALUMNI_INFO = "240";		//获取分会详情

	public static final String FIND_FRIENDS_LIST = "241";	//获取我的好友列表

	//16-10-11
	public static final String FIND_CLASS_MATES = "242";		//获取某班所有同学列表信息

	//16-10-17
	public static final String FIND_CHANNEL_FOR_WEB = "243"; //获取新闻频道列表

	//16-10-21
	public static final String CANCEL_JOINED_EVENT = "244";	//取消報名接口

	//16-10-24
	public static final String FIND_REGION_LIST = "245"; //获取地区列表

	public static final String FIND_INDUSTRY_LIST = "246";	//获取行业列表

	//16-10-25
	public static final String FIND_GRADE_MATES = "247";		//同级生

	//16-11-11
	public static final String FIND_NEWS_LIST_NEW = "248";	//新的获取新闻列表接口

	//16-11-16
	public static final String SET_NOTIFY_PUSH_READ = "249";	//批量設置已讀

	//16-11-26
	public static final String CLASS_ADMIN_ADD_CLASSMATES = "250";	//班級管理員添加班級成員

	//16-12-14
	public static final String API_FOR_ASSOCIATION = "251";			//加入退出等社团相关接口

	//16-12-14
	public static final String FIND_ASSOCIATION_LIST = "252";			//社团列表

	//16-12-17
	public static final String FIND_NEW_AUTH_MEMBER = "253";		//查询最新认证校友接口

	public static final String ADD_ASSOCIATION_EVENT = "254";		//创建社团活动接口

	//16-12-27
	public static final String BACK_SCHOOL_COMMENT = "255";			//评论、点赞返校计划

	public static final String BACK_SCHOOL_COMMENT_LIST = "256";	//评论列表接口

	public static final String BACK_SCHOOL_SIGN_LIST = "257";		//返校计划报名列表

	public static final String BACK_SCHOOL_COUNT_FOR_USER = "258";	//查询用户参与的和收藏的

	//16-12-30
	public static final String ASSOCIATION_INFO = "259";			//社团详情

	//17-01-03
	public static final String DROP_BACK_SCHOOL = "260";			//删除返校计划

	//17-01-04
	public static final String FIND_ASSOCIATION_MEMBER = "261";		//查询社团成员

	//17-01-07
	public static final String FIND_ENTERPRISE_LIST = "262";		//校友企业列表

	public static final String FIND_ENTERPRISE_INFO = "263";		//校友企业详情

	public static final String FIND_ENTERPRISE_PRODUCTS_LIST = "264";	//校友产品列表

	public static final String FIND_ENTERPRISE_PRODUCTS_INFO = "265";	//校友产品详情



	//17-01-08
	public static final String FIND_DICT_TYPE_LIST = "266";		//字典类型列表

	public static final String FIND_DICT_LIST = "267";			//字典列表

	public static final String UPDATE_DONATION_CONFIRM = "268";		//确认发货接口

	//17-02-14
	public static final String FIND_STATEMENT = "269";				//查询声明接口

	public static final String FIND_PROJECT_COST = "270";			//查询项目进度

	//17-02-05
	public static final String FIND_PROJECT_INFO = "271";			//捐赠项目详情


	//17-02-25
	public static final String SAVE_ALUMNI_CARD = "272";			//保存校友卡信息接口

	public static final String FIND_ALUMNI_CARD_INFO = "273";		//查看校友卡信息接口

	public static final String SAVE_CAMPUS_CARD = "274";			//保存商户卡接口

	//17-02-27
	public static final String FIND_USER_INFO_FOR_CARD = "275";

	//17-03-06
	public static final String FIND_USER_CHECK_LIST = "276";		//查询用户审核列表

	//17-03-09
	public static final String GET_WECHAT_AUTH_URL = "277";

	//17-03-26
	public static final String REGIST_AND_BIND_WECHAT = "278";

	//17-04-08
	public static final String SEND_ACTIVE_EMAIL ="279";

	//17-04-15
	public static final String CANCLE_DONATION = "280";

	//17-04-19
	public static final String GET_WECHAT_PIC = "281";
	//QPF END


	//17-06-06
	public static final String OPERATE_RESUME_EXTENDS = "282";

	public static final String FIND_MY_RESUME = "283";
	
	
	//17-07-19  获取活动评论接口
	public static final String GET_EVENT_COMMENT="284";
	//17-07-24  获取活动评论增删接口
	public static final String GET_EVENT_COMMENT_ADD_DEL="285";
	//17-07-24  获取活动评论总数
	public static final String GET_EVENT_COMMENT_COUNT="286";
	//17-07-24  获取活动花蕠总数
	public static final String GET_EVENT_BOARD_COUNT="287";
		
	/**********************环信消息*************************/

	public static final String FIND_CONTACTS_LIST = "41"; 	// 查询好友列表接口
	public static final String SEARCH_USER_PROFILE = "42"; 	// 搜索好友接口
	public static final String ADD_CONTACTS = "43"; 		// 添加联系人接口(申请)
	public static final String REMOVE_CONTACTS = "44"; 		// 删除联系人接口
	public static final String FIND_USER = "45"; 			// 查询用户详情接口
	public static final String FIND_GROUP_LIST = "54"; 		// 查询群组列表接口
	public static final String FIND_GROUP = "49"; 			// 查询群组详情接口
	public static final String CREATE_GROUP = "46"; 		// 创建群组接口
	public static final String UPDATE_GROUP = "55"; 		// 修改群组接口
	public static final String REQUEST_INSERT_CHATGROUP = "56"; // 加入群组接口(二维码扫描加入)
	public static final String QUIT_CHAT_GROUP = "57"; 		// 退出群组接口
	public static final String ADD_MEMBER_TO_GROUP = "53"; 	// 添加成员到群组中接口
	//add by jiangling
	public static final String REMOVE_CHAT_GROUP = "47"; 	//删除群组
	public static final String REMOVE_CHAT_GROUP_USER = "50"; //删除群组成员接口

	/**********************活动消息*************************/
	public static final String FIND_EVENT_LIST = "61"; 		// 查询活动列表接口

	public static final String FIND_DONATION_LIST = "72" ;		// 获取我的捐赠列表
	public static final String FIND_DONATION_NEW_LIST = "73" ;	// 获取最新捐赠列表
	/**********************爱心捐赠*************************/
	public static final String FIND_PROJECT_LIST = "71" ;		// 获取捐赠项目列表

	/**********************爱心捐赠*************************/
	public static final String FIND_SCHOOL_SERV_LIST = "81" ;		// 获取获取服务列表

	/**********************返校计划*************************/
	public static final String SAVE_FXJH_NEW = "74" ;		// 3.0创建返校计划
	public static final String FIND_FXJH_LIST_NEW = "75" ;		// 3.0返校计划列表
	public static final String FIND_FXJH_NEW = "76" ;		// 3.0返校计划详情
	public static final String FIND_FXJH_SIGN = "77" ;		// 3.0返校计划签到
	public static final String FIND_FXJH_SERVICES = "78" ;		// 3.0返校计划提供的服务列表
	public static final String UPDATE_FXJH_GROUPID = "79" ;    // 3.0返校计划群ID更新
	public static final String UPDATE_EVENT_GROUPID = "80" ;    // 3.0活动群ID更新
	public static final String SAVE_FXJH = "82" ;		// 创建返校计划GET_FXJH_LIST

	/**********************返校计划*************************/
	public static final String FIND_FXJH_LIST = "83" ;		// 返校计划列表

	/**********************返校计划*************************/
	public static final String FIND_FXJH = "84" ;		// 返校计划详情

	/**wurencheng 朋友圈接口*/
	public static final String DISCOVER_PUBLISH_MESSAGE = "90";//朋友圈发布消息接口
	public static final String DISCOVER_SHOW_LIST = "91";//朋友圈展示消息接口
	public static final String DISCOVER_COMMENT_OR_PRAISE = "92";//朋友圈评论点赞接口
	public static final String DISCOVER_CONTENT_DELETE = "93";//朋友圈删除接口
	public static final String DISCOVER_UPLOAD_WALL_PHOTO = "94";//朋友圈上传背景图片
	public static final String DISCOVER_GET_WALL_PHOTO = "95";//朋友圈获取背景图片
	public static final String DISCOVER_GET_NEWS_LIST = "96";//朋友圈获取新闻接口
	public static final String DISCOVER_GET_RECRUITMENT_LIST = "97";//朋友圈获取招聘接口
	public static final String DISCOVER_REPLY_COMMENT = "98";//回复接口
	public static final String DISCOVER_SHOW_PERSONAL_INFO = "99";//朋友圈展示个人详情接口
	public static final String DISCOVER_CONTENT_COMPLAINT = "86";//朋友圈举报接口
	public static final String DISCOVER_PUSH_MESSAGE_DELETE = "87";//朋友圈消息推送清空接口
	public static final String DISCOVER_GET_PUSH_MESSAGE = "88";//获取朋友圈消息推送接口
	public static final String DISCOVER_PUSH_MESSAGE_IS_READ = "89";//设置朋友圈消息推送已读接口


	public String address = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort()
			+ getRequest().getContextPath() + "/" + "userProfile/userProfileAction!doNotNeedSessionAndSecurity_getUserInfoByQrCode.action";


	/**********************jiangling*************************/
	//add by jiangling
	public static final String USER_PROFILE_GET_ClASSES = "101";// 查询用户的学习路径(院系+班级)

	public static final String GET_PHONE = "102"; //挖掘手机号接口

	public static final String ADD_MEMBERLIST_TO_GROUP = "103"; //添加多个成员到群组中接口

	/**lixun 新接口*/
	public static final String USER_UNAUTHORIZED_ClASSES = "301";	//未认证班级
	public static final String USER_GET_USER_ALUMNI = "302";	//获取加入的分会
	public static final String USER_APPLY_ALUMNI = "303";	//申请加入分会
	//郭亚斌创建的接口 时间：2016-8-20
	public static final String USER_JOIN_ALUMNI = "401";//某个用户所加入的组织
	//郭亚斌创建的接口
	public static final String USER_NOTJOIN_ALUMNI = "402";//某个用户所没有加入的组织
	//吕鹏接口
	public static final String FIND_NEWSCHANNEL= "403";//查找订阅

	public static final String UPDATE_MY_NEWSCHANNEL="404";
	//查找所有推送
	public static final String FIND_JPUSH="405";
	//查找我的推送
	public  static final String FIND_MY_PUSH="406";
	//查找已读推送
	public static final String FIND_MY_READ_PUSH="407";
	public static final String NO_READ_PUSH_COUNT ="408";
	//查询是否签到
	public static final String FIND_EVENT_ISSING="409";
	//系统推送删除接口
	public static final String DELETE_SYSTEM_PUSH="410";

	//杨牛牛接口
	public  static  final String USER_CHANGE_EASEMOB_PASSWORD = "500";
	//热门班级列表
	public  static  final String HOT_CLASS = "501";

	//值年返校计划-报名
	public  static  final String ADD_BACK_SCHOOL_SIGN = "502";
	//值年返校计划-报名二维码
	public  static  final String ADD_BACK_SCHOOL_SIGN_QR = "503";
	//分享详情
	public static final String FIND_SHARE_SETTING = "504";
	//公众号详情
	public static final String FIND_WEIXIN_ACCOUNT_SETTING = "505";

	//我的收藏列表
	public static final String FIND_MY_FAVORITE_LIST = "506";

	//收藏或取消收藏
	public static final String SAVE_OR_REMOVE_FAVORITE = "507";

	//获取微信菜单button
	public  static  final String FIND_WEIXIN_MENU_BUTTONS = "508";

	//抽奖活动报名接口
	public  static final String ACTIVITY_APPLICANT ="510";

	//抽奖活动详情接口
	public static final String ACTIVITY_DETAIL ="511";

	//报名人列表接口
	public static  final String APPLICANT_LIST ="512";

	//中奖接口
	public static final String ACTIVITY_WINNING ="513";

	//中奖人列表
	public static final String WINNING_LIST ="514";

	//报名人详情
	public static final String APPLICANT_DETAIL ="515";








//	public static final String FIND_WEIXIN_MENU

	//通过公众号AppId获取所对应的菜单
	public static final String FIND_WEIXIN_MENU = "600";
	//创建微信用户信息接口
	public static final String SAVE_WEIXIN_USER = "601";
	public static final String FIND_WEIXIN_USER = "602";
	//解除绑定微信用户接口
	public static final String REMOVE_WEIXIN_USER = "603";

	public static final String FIND_WEIXIN_USER_AUTHOR = "604";// 通过code获取微信用户账户信息
	//创建/修改返校聚会接口
	public static final String SAVE_ACTIVITY = "605";
	//获取返校聚会列表接口
	public static final String FIND_ACTIVITY_LIST = "606";

	//删除返校聚会列表接口
	public static final String DELETE_ACTIVITY = "607";
	//创建返校在线报名接口
	public static final String SAVE_ONLINE_SIGN = "608";
	//查询返校在线报名接口
	public static final String GET_ONLINE_SIGN = "609";
	//获取返校聚会详情接口
	public static final String GET_ACTIVITY_BYID = "610";
	//报名人列表接口
	public static final String GET_SIGN_LIST = "611";
	//获取省份地区校友会分布
	public static final String FIND_ALUMNI_COUNT ="612";
    //获取省份地区校友分布接口
	public static final String FIND_USERINFO_COUNT ="613";

	//获取电子书刊列表接口
	public static final String FIND_ELECTRONIC_BOOK_LIST = "614";


	//保存基金项目
	public static final String   SAVE_FOUN_PROJECT ="615";
	//修改基金项目
	public static final String   UPDATE_FOUN_PROJECT ="616";
	//删除基金项目
	public static final String   DELETE_FOUN_PROJECT ="617";
	//保存基金收入记录
	public static final String   SAVE_FOUN_INCOME ="618";
	//修改基金收入记录
	public static final String   UPDATE_FOUN_INCOME ="619";
	//删除基金收入记录
	public static final String   DELETE_FOUN_INCOME ="620";

	public static final String FIND_ENTERPRISE_TEAM_LIST = "621";	//校友企业成员列表

	public static final String FIND_ENTERPRISE_JOB_LIST = "622";	//校友企业招聘岗位列表

	public static final String FIND_ENTERPRISE_Job = "623";	//校友企业招聘岗位详情

	public static final String SAVE_RESUME_BASE = "624";	//创建/修改简历基本信息接口

	public static final String DELETE_RESUME_BASE = "625";	//创建/修改简历基本信息接口

	//直播间修改接口
	public static final String LIVE_ROOM_UPDATE = "797";
	//直播间话题修改接口
	public static final String LIVE_TOPIC_UPDATE = "798";
	//判断是否已经创建直播间接口
	public static final String LIVE_ROOM_CREATE_OR_NOT = "799";
	//直播间接口
	public static final String LIVE_ROOM_CREATE = "800";
	//上传语音接口
	public static final String LIVE_ROOM_UPLOAD_VOICE = "801";
	//直播间创建话题接口
	public static final String LIVE_TOPIC_CREATE = "802";
	//直播间展示接口
	public static final String LIVE_ROOM_LIST_SHOW = "803";
	//个人直播间展示接口
	public static final String LIVE_ROOM_SHOW = "804";
	//直播间关注接口
	public static final String LIVE_ROOM_ATTENTION = "805";
	//直播间话题详情接口
	public static final String LIVE_TOPIC_INFO = "806";
	//直播间留言板接口
	public static final String LIVE_ROOM_COMMENT = "807";
	//直播间留言板展示接口
	public static final String LIVE_ROOM_COMMENT_LIST = "808";
	//直播间回复留言接口
	public static final String LIVE_ROOM_REPLY = "809";
	//直播间留言详情接口
	public static final String LIVE_ROOM_COMMENT_INFO = "810";
	//我关注直播间列表接口
	public static final String LIVE_ROOM_ATTENTION_LIST = "811";
	//参与话题接口
	public static final String LIVE_TOPIC_ATTENTION = "812";
	//我参与话题列表接口
	public static final String LIVE_TOPIC_ATTENTION_LIST = "813";
	//结束直播接口
	public static final String LIVE_TOPIC_END = "814";
	//直播内容保存接口
	public static final String LIVE_ROOM_CONTENT = "815";
	//直播内容获取接口
	public static final String GET_LIVE_ROOM_CONTENT = "817";
	//表白墙发布接口
	public static final String EXPRESS_CONTENT_SEND = "790";
	//表白墙获取接口
	public static final String GET_EXPRESS_CONTENT_LIST = "791";
	//表白墙评论与点赞接口
	public static final String EXPRESS_COMMENT_OR_PRAISE = "792";
	//表白墙详情获取接口
	public static final String GET_EXPRESS_INFO = "793";
	//惠校友动态栏目获取
	public static final String GET_SERVICE_COLUMNS = "794";
	//惠校友动态栏目变更
	public static final String UPDATE_SERVICE_COLUMNS = "795";
	//惠校友首页信息获取
	public static final String GET_TOP_FAVOUR_ALUMNI_INFO = "796";
	//根据分会组织ID获取新闻列表接口
	public static final String GET_NEWS_BY_ALUMNI_ID = "780";
	//根据分会组织ID获取活动列表接口
	public static final String GET_EVENTS_BY_ALUMNI_ID = "781";
	//根据分会组织ID获取成员列表
	public static final String GET_MEMBER_LIST_BY_ALUMNI_ID = "782";
	//手机APP微信支付统一下单
	public static final String GET_APP_PAY_DATA = "783";
	//统计网站的访问量
	public static final String GET_WEB_CLICK_NUM = "784";
	//校友会首页信息获取
	public static final String GET_TOP_ALUMNI_INFO = "816";

	//举报信息的添加
	public static final String REPORT_ACTIVITY_OR_HUAXU = "818";

	//举报信息的查看
	public static final String VIEW_REPORT = "819";

	//查询所有的企业并且有按照距离的远近排序：近的在前，远的在后
	public static final String SELECT_ENTERPRISES_ORDER_BY_DISTENCE_DESC = "820";

    //token过期重新申请
	public static final String TOKEN_REFRESH = "821";

	//用户信息收集
	public static final String SAVE_USER_COLLECTION = "900";
	public static final String FIND_FRIENDS_COMMEND_LIST = "1000";	//获取推荐好友
	//推荐地方分会，行业分会
	public static final String FIND_ALUMNI_COMMEND_LIST = "1001";

    //校友会组织结构
	public static final String ALUMNI_TREE_MENU="1010"; 

	//APPManager 校友组织管理员权限
	public static final String CHECK_ACCUNTNUM_ISADMIN="1011";
	
	//APPManager 校友组织员成员
	public static final String ALUMNI_LIST="1012";
	
	//APPManager 校友组织员成员详情
	public static final String ALUMNI_USERPROFILE_DETAIL="1013";
	
	//APPManager 更新校友状态
	public static final String UPDATE_ALUMNI_STATUS="1014";
		
	//APPManager 查询本分会组织留言
	public static final String VIEW_ALUMNI_CONTACT="1015";
		
	//APPManager 查询本分会组织留言详情
	public static final String VIEW_ALUMNI_CONTACT_DETAIL="1016";
		
	//APPManager 查询本分会组织留言，留言回复
	public static final String VIEW_ALUMNI_CONTACT_REPLAY="1017";
		
	public static final String ALUMNI_MEMBER_CHAT="1018"; //组织聊天室
	public static final String ALUMNI_MEMBER_LIST="1019"; //组织成员列表
	public static final String ALUMNI_MEMBER_CLEAN="1020"; //聊天室踢出成员
	public static final String ALUMNI_MEMBER_DELETE="1021"; //组织删除成员
	public static final String ALUMNI_COMPLAIN_LIST="1022"; //投诉清单
	public static final String ALUMNI_COMPLAIN_DETAIL="1023"; //投诉详情
	public static final String ALUMNI_COMPLAIN_HANDLER="1024"; //投诉处理
	public static final String ALUMNI_ACTIVITY_LIST="1025"; //本组织活动列表
    public static final String ALUMNI_ACTIVITY_ADD="1026"; //新增活动
	public static final String ALUMNI_ACTIVITY_DELETE="1027";  //删除活动
	public static final String APP_UTIL_FUNCTION="1028";  //删除活动
	
	@Autowired
	private MultipleFileUploadService multipleFileUploadService;

	@Autowired
	private RegisterCodeService registerCodeService;

	@Autowired
	private RegisterService mregisterService;

	@Autowired
	private BaseInfoService baseInfoService;

	@Autowired
	private AlumniService malumniService;

	@Autowired
	private IndustryAlumniService industryalumniService;

	@Autowired
	private QrCodeService qrCodeService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private SystemSettingService systemSettingService;

	@Autowired
	private IndustryService industryService;

	@Autowired
	private AroundService aroundService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private DataminingService dataminingService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DeptInfoService deptInfoService;

	@Autowired
	private PassWordService passWordService;

	@Autowired
	private AuthenticatedService authenticatedService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private ChatContactsService chatContactsService ;

	@Autowired
	private ChatGroupService chatGroupService;

	@Autowired
	private ChatGroupUserService chatGroupUserService;

	@Autowired
	private EventService eventService;

	@Autowired
	private InviteSmsService inviteSmsService;

	@Autowired
	private ClassHandleService classHandleService;

	@Autowired
	private MobNewsTypeService mobNewsTypeService ;

	@Autowired
	private MobEventService mobEventService;

	@Autowired
	private NewsService newsService;

	@Autowired
	private MessageBoardService messageBoardService;

	@Autowired
	private ServiceNewsTypeService serviceNewsTypeService;

	//jiangling
	@Autowired
	private DataMiningService dataMiningService;

	@Autowired
	private NotifyService notifyService;
	@Autowired
	private DonationService donationService ;

	@Autowired
	private AlumniCardService alumniCardService;

	@Autowired
	private CampusCardService campusCardService;

	@Autowired
	private ProjectService projectService ;
	@Autowired
	private SchoolServService schoolServService ;
	@Autowired
	private NewsChannelService newsChannelService;
	@Autowired
	private AssociationMemberService associationMemberService;
	@Autowired
	private AssociationService associationService;

	@Autowired
	private ShareService shareService;

	@Autowired
	private WeiXinAccountService weiXinAccountService;

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private WeiXinMenuService weiXinMenuService;

	@Autowired
	private WeiXinUserService weiXinUserService;

	@Autowired
	private EnterpriseService enterpriseService;

	@Autowired
	private EnterpriseProductService enterpriseProductService;

	@Autowired
	private EnterpriseTeamService enterpriseTeamService;

	@Autowired
	private EnterpriseJobService enterpriseJobService;

	@Autowired
	private ResumeBaseService resumeBaseService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private BackschoolOnlineSignService backschoolOnlineSignService;

	@Autowired
	private DictTypeService dictTypeService;

	@Autowired
	private ElectronicBookService electronicBookService;

	@Autowired
	private DiscoverService discoverService;

	@Autowired
	private LiveService liveService;

	@Autowired
	private AlumniAssociationService alumniAssociationService;

	@Autowired
	private WechatPayService wechatPayService;

	@Autowired
	private ExpressService expressService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ActivityApplicantService activityApplicantService;

	@Autowired
	private ActActivityService actActivityService;

	@Autowired
	private ActivityWinningService activityWinningService;

	@Autowired
	private UserAuthService  userAuthService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserCollectionService userCollectionService;
	
	@Autowired
	private UserService  userService;  
	
	@Autowired
	private AppService  appService;  
	
	@Autowired
	private mContactService  mcontactService;  
	
	@Autowired
	private mMemberService  mmemberService;
		
	@Autowired
	private mComplainService mcomplainService;
		
	@Autowired
	private mActivityService mactivityService;
	
	@Autowired
	private mUtilService mutilService;
	
	private String jsonStr;
	private File[] upload;
	private String[] uploadFileBase64;
	private String[] uploadFileName;

	
	/**
	 * 手机访问接口
	 *
	 */
	public void doNotNeedSessionAndSecurity_userProfileHandler() {
		logger.info(jsonStr);
		Message message = new Message();
		String token=null;
		
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		  if (jsonObject == null) {
			  message.setMsg("jsonStr is null");
			  message.setSuccess(false);
			  super.writeJson(message);
			  return;
		  }	
		  
		  
try {				
		 String command = jsonObject.getString("command");		
		
		//端口号不能为空
		if (command == null) {							
			message.setMsg("jsonStr is error");
			message.setSuccess(false);
			super.writeJson(message);
			return;			
		 }	
		
		 //String usagent= getUserAgent();  //浏览器信息
		 token=request.getHeader("authorization");//header带token方法		    
		 String content = jsonObject.getString("content");
		 String reftoken = jsonObject.getString("token"); //参数带TOKEN方法
		 
		   //如查参数含token，且header中token为空，用参数
		 if ((reftoken!=null&&reftoken.length()>0)&&(token==null||token.length()<=0))    token=reftoken;  
		
		 
		 
		//---------header传token验证--访问锁-------------------------10手机登陆端口不检测-----------------		
		   	
			//System.out.println("authorization="+token);
			//System.out.println("getUserAgent="+t);						
			//System.out.println("command="+command);		
	    /*
		   if ((token == null||token.length()<=0)&&!command.equals("10"))
			 {							
				message.setMsg("token is null，非法访问");
				message.setSuccess(false);
				message.setReturnId(String.valueOf(Constant.RESCODE_EXCEPTION)); //1002
				super.writeJson(message);
				return;			
			 }				 
		*/ 
		//----------------------------------------------------------------------------------------
			
		
	    //-------------------参数验证token------------------------------------------------------------
	    
	    //10，821方法内含验证，除此之外所有端口在此验证TOKEN有效性
	    if (token!=null&&token.length()>0&&!command.equals("10")&&!command.equals("821"))
		{
			    long togo=userAuthService.CheckToken(token);
		        
		        if (togo!=Constant.RESCODE_SUCCESS)
		        {
		        	message.setMsg("token is invalid!");
		        	message.setReturnId(String.valueOf(togo)); //返回错误码
				    message.setSuccess(false);
				    super.writeJson(message);
				    return;
		        }		        
		}
	  //------------------------------------------------------------------------------------
	    		
		
        //821 token刷新程序，不带content内容,非821接口需检测content
		if (command !="821"&& content == null) {				
			message.setMsg("jsonStr is error");
			message.setSuccess(false);
			super.writeJson(message);
			return;				
		  }
			
			if (command.equals(USER_PROFILE_UPLOAD_FILE)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64,uploadFileName) ;
				}
				multipleFileUploadService.addFile(message, content, upload, uploadFileName);
			} else if (command.equals(USER_PROFILE_GET_USER_BASE_INFO_ID_LIST)) {
				baseInfoService.getBaseInfoIdByName(message, content);
			} else if (command.equals(USER_PROFILE_GET_REGISTER_CODE)) {
				System.out.println("准备调用短信") ;
				registerCodeService.sendRegisterCode(message, content);
			} else if (command.equals(USER_PROFILE_REGISTER)) {
				mregisterService.register(message, content);
			} else if (command.equals(USER_PROFILE_UPDATE_USER_PROFILE)) {
				baseInfoService.updateProfile(message, content);
			} else if (command.equals(USER_PROFILE_IMAGE_FILE_UPLOAD)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64,uploadFileName) ;
				}
				baseInfoService.uploadPhoto(message, content, upload, uploadFileName);
			} else if (command.equals(USER_PROFILE_GET_CLASSMATES_INFO_LIST)) {
				baseInfoService.getClassmates(message, content);
			} else if (command.equals(USER_PROFILE_SEARCH_FOR_USERS)) {
				baseInfoService.searchUserInfo(message, content);
			} else if (command.equals(USER_PROFILE_GET_USER_SELF_PROFILE)) {
				if (token==null||token.length()<=0)
				     baseInfoService.selectAppLogin(message, content); //APP 普通登陆方法
				else
				     baseInfoService.selectAppLogin(message, content,token); // token header登陆方法
			} else if (command.equals(USER_PROFILE_GET_FRIEND_PROFILE)) {
				baseInfoService.getFriendProfile(message, content);
			} else if (command.equals(USER_PROFILE_UPDATE_GROUP_INFO)) {
				groupService.updateGroupInfo(message, content);
			} else if (command.equals(USER_PROFILE_GET_GROUP_INFO)) {
				groupService.getGroupInfo(message, content);
			} else if (command.equals(USER_PROFILE_UPDATE_CLASSMATE_TEL)) {
				dataminingService.updateClassmateTel(message, content);
			} else if (command.equals(USER_PROFILE_AUTHENTICATED)) {
				authenticatedService.updateAuthenticated(message, content);
			} else if (command.equals(USER_PROFILE_CHANGE_PASSWORD)) {
				passWordService.updatePassWord(message, content);
			} else if (command.equals(USER_PROFILE_GET_GROUP_MEMBERS_INFO)) {
				groupService.getGroupMembersInfo(message, content);
			} else if (command.equals(USER_PROFILE_GET_CLASS_NAME)) {
				departmentService.getDepartName(message, content);
			} else if (command.equals(USER_PROFILE_SEARCH_FOR_USERS_NEW)) {
				baseInfoService.searchUserInfo(message, content);
			} else if (command.equals(USER_PROFILE_UPDATE_USER_PROFILE_NEW)) {
				baseInfoService.updateProfile(message, content);
			} else if (command.equals(USER_QR_CODE_ADDRESS)) {
				qrCodeService.getUserQRCodeAddress(message, content, address);
			} else if (command.equals(GET_NEARBY_USER)) {
				aroundService.getNearbyUser(message, content);
			} else if (command.equals(GET_USER_BY_ACCOUNT_NUM)) {
				qrCodeService.getUserInfoByAccountNum(message, content);
			} else if (command.equals(GET_ALNMNI)) {
				//malumniService.selectAllAlumni(message, 2);	//lixun
//				malumniService.selectAllAlumni(message,content);	//16-08-19

				malumniService.selectAlumni(message,content) ;

			} else if (command.equals(GET_USER_BY_GROUP)) {
				groupService.selectUserByGroupId(message, content);
			}else if(command.equals(SAVE_BACKSCHOOL_BOARD)){//保存返校计划花絮
				schoolServService.saveBackSchoolBoard(message,content);
			}else if(command.equals(FIND_BACKSCHOOL_BOARD_LIST)){//获取返校计划花絮列表
				schoolServService.findBackschoolBoardList(message, content);
			}else if(command.equals(FIND_BACKSCHOOL_BOARD)){//获取返校计划花絮详情
				schoolServService.findBackschoolBoard(message, content);
			}else if(command.equals(SAVE_BACKSCHOOL_BOARD_COMMENT_OR_PRAISE)){//保存返校计划花絮评论或点赞
				schoolServService.saveCommentOrPraise(message, content);
			}else if(command.equals(SAVE_BACKSCHOOL_BOARD_COMPLAINT)){//保存返校计划花絮举报
				schoolServService.saveBackschoolBoardComplaint(message,content);
			}else if(command.equals(REMOVE_BACKSCHOOL_BOARD)){//删除返校计划花絮
				schoolServService.deleteBackschoolBoard(message, content);
				//朋友圈
				//发布接口
			} else if (command.equals(DISCOVER_PUBLISH_MESSAGE)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64,uploadFileName);
				}
				discoverService.save(message, content, upload, uploadFileName);
				//展示接口
			}else if (command.equals(DISCOVER_SHOW_LIST)) {
				discoverService.showAllList(message, content);
				//评论和点赞接口
			} else if (command.equals(DISCOVER_COMMENT_OR_PRAISE)) {
				discoverService.saveDiscoverCommentOrPraise(message, content);
				//删除接口
			} else if (command.equals(DISCOVER_CONTENT_DELETE)) {
				discoverService.deleteDiscoverContent(message, content);
				//朋友圈背景图片上传接口
			}else if (command.equals(DISCOVER_UPLOAD_WALL_PHOTO)){
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64, uploadFileName) ;
				}
				discoverService.uploadDiscoverWallPhoto(message, content, upload, uploadFileName);
				//朋友圈获取图片上传接口
			} else if (command.equals(DISCOVER_GET_WALL_PHOTO)) {
				discoverService.getDiscoverWallPhoto(message, content);
				//朋友圈获取新闻接口
			} else if (command.equals(DISCOVER_GET_NEWS_LIST)) {
				discoverService.getDiscoverNewsList(message, content);
				//朋友圈获取招聘接口
			} else if (command.equals(DISCOVER_GET_RECRUITMENT_LIST)) {
				discoverService.getDiscoverRecruitmentList(message, content);
				//回复评论接口
			} else if (command.equals(DISCOVER_REPLY_COMMENT)) {
				discoverService.saveDiscoverReply(message, content);
				//朋友圈展示个人详情接口
			} else if (command.equals(DISCOVER_SHOW_PERSONAL_INFO)) {
				discoverService.getDiscoverPersonalInfo(message, content);
				//朋友圈举报功能
			} else if (command.equals(DISCOVER_CONTENT_COMPLAINT)) {
				discoverService.discoverContentComplaint(message, content);
				//获取朋友圈消息推送接口
			} else if (command.equals(DISCOVER_GET_PUSH_MESSAGE)) {
				discoverService.getDiscoverGetPushMsg(message, content);
				//设置朋友圈消息推送已读接口
			} else if (command.equals(DISCOVER_PUSH_MESSAGE_IS_READ)) {
				discoverService.discoverGetPushMsgIsRead(message, content);
				//朋友圈消息推送清空接口
			} else if (command.equals(DISCOVER_PUSH_MESSAGE_DELETE)) {
				discoverService.discoverGetPushMsgDelete(message, content);
				//直播间修改接口
			} else if (command.equals(LIVE_ROOM_UPDATE)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64, uploadFileName) ;
				}
				liveService.liveRoomUpdate(message, content, upload, uploadFileName);
				//直播间话题修改接口
			} else if (command.equals(LIVE_TOPIC_UPDATE)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64, uploadFileName) ;
				}
				liveService.liveTopicUpdate(message, content, upload, uploadFileName);
				//判断是否已经创建直播间接口
			} else if (command.equals(LIVE_ROOM_CREATE_OR_NOT)) {
				liveService.liveRoomCreateOrNot(message, content);
				//直播间创建接口
			} else if (command.equals(LIVE_ROOM_CREATE)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64, uploadFileName) ;
				}
				liveService.liveRoomCreate(message, content, upload, uploadFileName);
				//直播间语音上传接口
			} else if (command.equals(LIVE_ROOM_UPLOAD_VOICE)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64, uploadFileName) ;
				}
				liveService.liveRoomUploadVoice(message, content, upload, uploadFileName);
				//直播间话题创建接口
			} else if (command.equals(LIVE_TOPIC_CREATE)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64, uploadFileName) ;
				}
				liveService.liveTopicCreate(message, content, upload, uploadFileName);
				//直播间展示接口
			} else if (command.equals(LIVE_ROOM_LIST_SHOW)) {
				liveService.liveRoomListShow(message, content);
				//个人直播间展示接口
			} else if (command.equals(LIVE_ROOM_SHOW)) {
				liveService.liveRoomShow(message, content);
				//直播间关注接口
			} else if (command.equals(LIVE_ROOM_ATTENTION)) {
				liveService.liveRoomAttention(message, content);
				//直播间话题详情接口
			} else if (command.equals(LIVE_TOPIC_INFO)) {
				liveService.liveTopicInfo(message, content);
				//直播间留言板接口
			} else if (command.equals(LIVE_ROOM_COMMENT)) {
				liveService.liveRoomComment(message, content);
				//直播间留言板列表接口
			} else if (command.equals(LIVE_ROOM_COMMENT_LIST)) {
				liveService.liveRoomCommentList(message, content);
				//直播间回复留言接口
			} else if (command.equals(LIVE_ROOM_REPLY)) {
				//直播间留言详情接口
				liveService.liveRoomReply(message, content);
			} else if (command.equals(LIVE_ROOM_COMMENT_INFO)) {
				liveService.liveRoomCommentInfo(message, content);
				//我关注直播间列表接口
			} else if (command.equals(LIVE_ROOM_ATTENTION_LIST)) {
				liveService.liveRoomAttentionList(message, content);
				//参与话题接口
			} else if (command.equals(LIVE_TOPIC_ATTENTION)) {
				liveService.liveTopicAttention(message, content);
				//我参与话题列表接口
			} else if (command.equals(LIVE_TOPIC_ATTENTION_LIST)) {
				liveService.liveTopicAttentionList(message, content);
				//结束直播接口
			} else if (command.equals(LIVE_TOPIC_END)) {
				liveService.liveTopicEnd(message, content);
				//直播内容保存接口
			} else if (command.equals(LIVE_ROOM_CONTENT)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64,uploadFileName) ;
				}
				liveService.saveLiveRoomContent(message, content, upload, uploadFileName);
				//直播内容获取接口
			} else if (command.equals(GET_LIVE_ROOM_CONTENT)) {
				liveService.getLiveRoomContent(message, content);
				//校友会首页信息
			} else if (command.equals(GET_TOP_ALUMNI_INFO)) {
				alumniAssociationService.getTopAlumniInfo(message, content);
				//惠校友首页信息
			} else if (command.equals(GET_TOP_FAVOUR_ALUMNI_INFO)) {
				alumniAssociationService.getTopFavourAlumniInfo(message, content);
				//惠校友动态栏目获取
			} else if (command.equals(GET_SERVICE_COLUMNS)) {
				alumniAssociationService.getServiceColumns(message, content);
				//惠校友动态栏目变更
			} else if (command.equals(UPDATE_SERVICE_COLUMNS)) {
				alumniAssociationService.updateServiceColumns(message, content);
				//表白墙发布接口
			} else if (command.equals(EXPRESS_CONTENT_SEND)) {
				expressService.sendExpressContent(message, content);
				//表白墙获取接口
			} else if (command.equals(GET_EXPRESS_CONTENT_LIST)) {
				expressService.getExpressContentList(message, content);
				//表白墙评论或者点赞接口
			}else if (command.equals(EXPRESS_COMMENT_OR_PRAISE)) {
				expressService.saveExpressCommentOrPraise(message, content);
				//表白墙详情获取接口
			}else if (command.equals(GET_EXPRESS_INFO)) {
				expressService.getExpressInfo(message, content);
				//根据分会组织ID获取新闻列表接口
			} else if (command.equals(GET_NEWS_BY_ALUMNI_ID)) {
				alumniAssociationService.getNewsByAlumniId(message, content);
				//根据分会组织ID获取活动列表接口
			} else if (command.equals(GET_EVENTS_BY_ALUMNI_ID)) {
				alumniAssociationService.getEventsByAlumniId(message, content);
				//根据分会组织ID获取成员列表
			} else if (command.equals(GET_MEMBER_LIST_BY_ALUMNI_ID)) {
				alumniAssociationService.getMemberListByAlumniId(message, content);
				//手机APP微信支付统一下单
			} else if (command.equals(GET_APP_PAY_DATA)) {
				wechatPayService.getAppPayDate(message, content);
			} else if (command.equals(GET_WEB_CLICK_NUM)) {
				alumniAssociationService.getWebClickNum(message, content);
			}else if (command.equals(SAVE_USER_COLLECTION)){  //用户信息收集接口
				userCollectionService.saveUserCollection(message, content);
			} else if (command.equals(REPORT_ACTIVITY_OR_HUAXU)){
				reportService.report(message, content);
			} else if (command.equals(VIEW_REPORT)){
				reportService.viewReport(message, content);
			}else if(command.equals(SELECT_ENTERPRISES_ORDER_BY_DISTENCE_DESC)) {
				enterpriseService.findEnterpriseListAndOrderByDistenceDesc(message, content);
			}
			/*else if(command.equals(GET_INDUSTRY)){
				malumniService.selectAllAlumni(message, 3);	//lixun
			}*/
			else if (command.equals(CHANGE_USER_PHONE_NUM)){
				baseInfoService.resetUserPhoneNum(message, content);
			}else if(command.equals(USER_PROFILE_AUTHENTICATED_BY_STU_NUM)) {
				authenticatedService.updateAuthenticatedByStuNum(message, content);
			}else if(command.equals(USER_PROFILE_AUTHENTICATED_BY_CARD)){
				authenticatedService.updateAuthenticatedByCard(message, content);
			} else if (command.equals(INVITE_OTHERS)){
				inviteSmsService.sendInviteSms(message, content);
			}else if(command.equals(USER_PROFILE_AUTHENTICATED_BY_INVITE_CODE)) {
				authenticatedService.updateAuthenticatedByInviteCode(message, content);
			}else if(command.equals(CANCLE_USER_AUTHENTICATION)){
				authenticatedService.cancleUserAuthentication(message, content);
			} else if (command.equals(JOIN_ALUMNI)) {
				malumniService.joinAlumni(message, content);
			} else if (command.equals(JOIN_ALUMNIS)){
				malumniService.joinAlumnis(message, content);
			} else if (command.equals(LEFT_ALUMNIS)) {
				malumniService.leftAlumnis(message, content);
			}else if(command.equals(CLASS_ADMIN_EDIT_CLASSMATES)){
				classHandleService.classAdminEditClassMates(message, content);
			} else if (command.equals(LIST_OF_DEPTS)){
				deptInfoService.selectDepts(message, content);
			} else if (command.equals(ADD_USER_INFO)){
				userInfoService.addUserInfo(message, content);
			}else if(command.equals(FIND_ALUMNI_COUNT)) {
				userInfoService.chartOfAlumniCountMap(message, content);
			}else if(command.equals(FIND_USERINFO_COUNT)){
				userInfoService.chartOfUserInfoMap(message, content);
			} else if (command.equals(ADD_PERSONAL_EVENT)){
				eventService.savePersonalEvent(message, content);
			} else if (command.equals(SHOW_EVENT)) {
				eventService.showEventInfo(message, content);
			} else if (command.equals(JOIN_EVENT)) {
				eventService.joinEvent(message, content);
			} else if (command.equals(REMOVE_EVENT)){
				eventService.removeEvent(message, content);
			}else if (command.equals(GET_INFO_OF_SIGNER)) {
				eventService.getInfoOfSigner(message, content);
			}else if(command.equals(GET_AUTHENTICATED_NAME)){
				authenticatedService.getAuthenticatedName(message, content);
			}else if (command.equals(SHOW_EVENT_MEMBERS)){
				eventService.showEventMembers(message, content);
			} else if (command.equals(FEED_BACK)){
				messageBoardService.saveFeedBack(message, content);
			} else if(command.equals(FIND_NEWS_LIST_FOR_MOBILE)){
				newsService.findNewsListForMobile(message, content);
			} else if (command.equals(SAVE_EVENT_BOARD)){
				mobEventService.saveEventBoard(message, content);
			}else if(command.equals(SAVE_COMMENT_OR_PRAISE)){
				mobEventService.saveCommentOrPraise(message, content);
			}else if(command.equals(GET_EVENT_COMMENT_ADD_DEL)){//活动评论接口
				mobEventService.saveEventCommentOrPraise(message, content);
			} else if(command.equals(GET_EVENT_COMMENT_COUNT)){//活动评论统计
				mobEventService.CountCommentForEventid(message, content);	
			}else if(command.equals(GET_EVENT_BOARD_COUNT)){//活动花絮统计
				mobEventService.CountBoardForBoardid(message, content);					
			}else if (command.equals(SAVE_COMPLAINT)) {
				mobEventService.saveComplaint(message, content);
			}else if(command.equals(FIND_EVENT_BOARD_LIST)) {
				mobEventService.findEventBoardList(message, content);
			}else if (command.equals(FIND_EVENT_BOARD)) {
				mobEventService.findEventBoard(message, content);
			} else if (command.equals(FIND_MOB_NEWS)) {
				newsService.findNewsForMobile(message, content);
			} else if (command.equals(FIND_PROJECT)){
				projectService.findProject(message, content);
			}else if (command.equals(FINT_MY_DONATION)){
				donationService.findMyDonation(message, content);
			}else if (command.equals(SAVE_DONATION_ORDER)){
				donationService.saveDonation(message, content);
			}else if(command.equals(DONATION_PAY_FEED_BACK)){
				donationService.updatePayStatus(message, content);
			}else if (command.equals(FIND_EVENT_TYPE)) {
				eventService.showEventTypeList(message, content);
			}else if(command.equals(REMOVE_EVENT_BOARD)) {
				mobEventService.removeEventBoard(message, content);
			}else if(command.equals(FIND_SERVICE_NEWS_TYPE)) {
				serviceNewsTypeService.findServiceNewsTypeList(message, content);
			}else if(command.equals(FIND_WEB_NEWS_TYPE)){
				newsService.findNewsTypeListForWeb(message, content);
			} else if (command.equals(FIND_WEB_NEWS_LIST)) {
				newsService.findNewsListForWeb(message, content);
			} else if (command.equals(FIND_WEB_NEWS)){
				newsService.findNewsForWeb(message, content);
			}else if(command.equals(FIND_SERVICE_NEWS)){
				serviceNewsTypeService.findServiceNewsList(message, content);
			} else if (command.equals(FIND_NEWS_TYPE)){
				mobNewsTypeService.findNewsType(message, content);
			} else if (command.equals(ALUMNI_INFO)){
				malumniService.getAlumniInfo(message, content);
			}else if(command.equals(FIND_FRIENDS_LIST)) {
				chatContactsService.findFriendsList(message, content);
			}else if (command.equals(FIND_CLASS_MATES)) {
				baseInfoService.findClassmates(message, content);
			}else if(command.equals(FIND_CHANNEL_FOR_WEB)){
				mobNewsTypeService.findNewsChannelList(message, content);
			}else if (command.equals(CANCEL_JOINED_EVENT)){
				eventService.cancelJoinedEvent(message, content);
			}else if (command.equals(FIND_REGION_LIST)){
				regionService.findRegionList(message, content);
			}else if(command.equals(FIND_INDUSTRY_LIST)) {
				industryService.findIndustryList(message, content);
			} else if (command.equals(FIND_GRADE_MATES)) {
				userInfoService.findGradeMates(message, content);
			}else if (command.equals(FIND_NEWS_LIST_NEW)) {
				//
			} else if (command.equals(CLASS_ADMIN_ADD_CLASSMATES)) {
				classHandleService.classAdminAddClassmates(message, content);
			}else if(command.equals(API_FOR_ASSOCIATION)) {
				associationMemberService.apiForAssociation(message, content);
			}else if(command.equals(FIND_ASSOCIATION_LIST)) {
				associationService.findAssociationList(message, content);
			}else if (command.equals(FIND_NEW_AUTH_MEMBER)){
				userInfoService.showNewsAuth(message, content);
			}else if(command.equals(ADD_ASSOCIATION_EVENT)){
				eventService.saveAssociationEvent(message, content);
			} else if (command.equals(BACK_SCHOOL_COMMENT)) {
				commentService.saveComment(message, content);
			} else if (command.equals(BACK_SCHOOL_COMMENT_LIST)){
				commentService.findCommentList(message, content);
			} else if(command.equals(BACK_SCHOOL_SIGN_LIST)) {
				schoolServService.findBackSchoolSignList(message, content);
			} else if(command.equals(BACK_SCHOOL_COUNT_FOR_USER)) {
				schoolServService.findUsersBackSchoolCount(message, content);
			}else if(command.equals(ASSOCIATION_INFO)) {
				associationService.findAssociationInfo(message, content);
			}else if (command.equals(DROP_BACK_SCHOOL)) {
				schoolServService.dropFxjh(message, content);
			}else if(command.equals(FIND_ASSOCIATION_MEMBER)) {
				associationMemberService.findAssociationMember(message, content);
			}else if(command.equals(FIND_ENTERPRISE_LIST)){
				enterpriseService.findEnterpriseList(message, content);
			} else if(command.equals(FIND_ENTERPRISE_INFO)) {
				enterpriseService.findEnterprise(message, content);
			}else if (command.equals(FIND_ENTERPRISE_PRODUCTS_LIST)) {
				enterpriseProductService.findEnterpriseProductList(message, content);
			}else if(command.equals(FIND_ENTERPRISE_PRODUCTS_INFO)) {
				enterpriseProductService.findEnterpriseProduct(message, content);
			}else if (command.equals(FIND_DICT_TYPE_LIST)){
				dictTypeService.findDictTypeList(message, content);
			} else if (command.equals(FIND_DICT_LIST)){
				dictTypeService.findDictList(message, content);
			}else if(command.equals(UPDATE_DONATION_CONFIRM)){
				donationService.updateDonationStatus(message, content);
			}else if (command.equals(FIND_STATEMENT)){
				systemSettingService.findStatement(message, content);
			}else if (command.equals(FIND_PROJECT_COST)){
				projectService.findProjectCost(message, content);
			}else if (command.equals(FIND_PROJECT_INFO)){
				projectService.findProjectInfo(message, content);
			}else if (command.equals(SAVE_ALUMNI_CARD)){
				alumniCardService.saveAlumniCard(message, content);
			}else if(command.equals(FIND_ALUMNI_CARD_INFO)) {
				alumniCardService.findAlumniCard(message, content);
			}else if (command.equals(SAVE_CAMPUS_CARD)) {
				campusCardService.saveCampusCard(message, content);
			}else if(command.equals(FIND_USER_INFO_FOR_CARD)){
				alumniCardService.findUserInfoForCard(message, content);
			}else if (command.equals(FIND_USER_CHECK_LIST)){
				userInfoService.userCheckList(message, content);
			}else if (command.equals(GET_WECHAT_AUTH_URL)){
				weiXinMenuService.getAuthUrl(message, content);
			}else if(command.equals(REGIST_AND_BIND_WECHAT)){
				mregisterService.registAndBindWechat(message, content);
			}else if (command.equals(SEND_ACTIVE_EMAIL)){
				mregisterService.sendActiveEmail(message, content);
			}else if (command.equals(CANCLE_DONATION)){
				donationService.cancleDonate(message, content);
			}else if (command.equals(GET_WECHAT_PIC)) {
				weiXinUserService.saveWeiXinPic(message, content);
			}else if(command.equals(OPERATE_RESUME_EXTENDS)){
				resumeBaseService.operateResumeExtends(message, content);
			}else if (command.equals(FIND_MY_RESUME)){
				resumeBaseService.getResumeByAccount(message, content);
			}else if (command.equals(SET_NOTIFY_PUSH_READ)){
				notifyService.setAllReaded(message, content);
			}else if(command.equals(FIND_CONTACTS_LIST)){
				chatContactsService.findContactsList(message, content);
			}else if(command.equals(SEARCH_USER_PROFILE)) {
				chatContactsService.searchUserProfile(message, content);
			}else if (command.equals(FIND_USER)){
				chatContactsService.findUserProfile(message, content);
			}else if (command.equals(FIND_GROUP_LIST)) {
				chatGroupService.findChatGroupList(message, content);
			} else if (command.equals(FIND_GROUP)) {
				chatGroupService.findChatGroup(message, content);
			}else if (command.equals(ADD_CONTACTS)) {
				chatContactsService.saveContacts(message, content);
			}else if (command.equals(REMOVE_CONTACTS)){
				chatContactsService.removeContacts(message, content);
			} else if (command.equals(CREATE_GROUP)){
				chatGroupService.saveChatGroup(message, content);
			}else if (command.equals(UPDATE_GROUP)){
				chatGroupService.updateChatGroup(message, content);
			}else if (command.equals(FIND_PROJECT_LIST)) {
				projectService.findProjectList(message, content);
			}else if(command.equals(FIND_DONATION_LIST)) {
				donationService.findDonationList(message, content);
			}else if(command.equals(FIND_DONATION_NEW_LIST)) {
				donationService.findDonationNewList(message, content);
			}else if(command.equals(REQUEST_INSERT_CHATGROUP)) {
				chatGroupService.requestInsertChatGroup(message, content);
			}else if (command.equals(QUIT_CHAT_GROUP)){
				chatGroupService.quitChatGroup(message, content);
			} else if (command.equals(FIND_SCHOOL_SERV_LIST)){
				schoolServService.findSchoolServList(message, content);
			} else if (command.equals(SAVE_FXJH)) {
				schoolServService.saveFxjh(message, content);
			} else if (command.equals(SAVE_FXJH_NEW)) {
				if(upload == null && uploadFileBase64 != null) {
					upload = multipleFileUploadService.changeFromBase64ToFile(uploadFileBase64, uploadFileName);
				}
				schoolServService.saveFxjhNew(message, content, upload, uploadFileName);
			} else if (command.equals(FIND_FXJH_LIST)) {
				schoolServService.findFxjhList(message, content);
			} else if (command.equals(FIND_FXJH_LIST_NEW)) {
				schoolServService.findFxjhListNew(message, content);
			} else if (command.equals(FIND_FXJH)){
				schoolServService.getFxjhById(message, content);
			} else if (command.equals(FIND_FXJH_NEW)){
				schoolServService.getFxjhNewById(message, content);
			} else if (command.equals(FIND_FXJH_SIGN)){
				schoolServService.findFxjhSign(message, content);
			} else if (command.equals(FIND_FXJH_SERVICES)){
				schoolServService.findFxjhServices(message, content);
			} else if (command.equals(UPDATE_FXJH_GROUPID)){
				schoolServService.updateFxjhGroupId(message, content);
			} else if (command.equals(UPDATE_EVENT_GROUPID)){
				eventService.updateMobEventGroupId(message, content);
			}else if(command.equals(ADD_MEMBER_TO_GROUP)){
				chatGroupService.addMemberToGroup(message, content);
			}else if(command.equals(GET_ALL_LINK_OF_CATEGORYS)){    // 获取全部栏目接口 24
				mobNewsTypeService.getAllLinkOfCategorys(message, content);
				// add by jiangling
			} else if (command.equals(REMOVE_CHAT_GROUP)) {
				chatGroupService.removeChatGroup(message, content);
			}                // add by jiangling
			else if (command.equals(REMOVE_CHAT_GROUP_USER)) {
				chatGroupUserService.removeChatGroupUser(message, content);
			} else if (command.equals(FIND_EVENT_LIST)) {
				eventService.findEventList(message, content);
			}// add by jiangling 查询用户学习经历
			else if(command.equals(USER_PROFILE_GET_ClASSES)) {
				baseInfoService.getClasses(message, content);
			}//add by jiangling 挖掘手机号
			else if (command.equals(GET_PHONE)){
				dataMiningService.savePhone(message, content);
			}//add by jiangling 添加组织成员列表(添加多个组织成员)
			else if(command.equals(ADD_MEMBERLIST_TO_GROUP)) {
				chatGroupService.addMemberListToGroup(message, content);
			} else if ( command.equals( USER_UNAUTHORIZED_ClASSES ) )
			{
				baseInfoService.getUnauthorizedClass(message, content);    //未认证班级
			}
			else if (command.equals(USER_GET_USER_ALUMNI ) )
			{
				;	//获取加入的分会
			} else if (command.equals(USER_APPLY_ALUMNI)) {
				;    //申请加入分会
			} else if(command.equals(FIND_NEWSCHANNEL)) {
				//查找订阅
				newsChannelService.findMyNesChannle(message, content);
			} else if (command.equals(UPDATE_MY_NEWSCHANNEL)) {
				//修改我的订阅
				newsChannelService.updateMyNewsChannel(message, content);
			} else if (command.equals(FIND_JPUSH)) {
				notifyService.findNotify(message, content);
			} else if (command.equals(FIND_MY_PUSH)) {
				notifyService.findMynotify(message, content);
			} else if (command.equals(FIND_MY_READ_PUSH)) {
				notifyService.setRead(message, content);
			} else if (command.equals(NO_READ_PUSH_COUNT)) {
				notifyService.noReadCount(message, content);
			} else if (command.equals(FIND_EVENT_ISSING)) {
				eventService.setIsSing(message, content);
			}else if (command.equals(DELETE_SYSTEM_PUSH)) {
				notifyService.deleteNotify(message, content);
			} else if (command.equals(HOT_CLASS)) {
				deptService.findDeptListPage(message, content);
			} else if (command.equals(ADD_BACK_SCHOOL_SIGN)) {
				schoolServService.addBackSchoolSign(message, content);
			} else if (command.equals(ADD_BACK_SCHOOL_SIGN_QR)) {
				schoolServService.addBackSchoolSignQR(message, content);
			} else if (command.equals(FIND_SHARE_SETTING)){
				shareService.findShareSetting(message, content);
			} else if (command.equals(FIND_WEIXIN_ACCOUNT_SETTING)) {
				weiXinAccountService.findWeiXinAccountSetting(message, content);
			}else if (command.equals(FIND_MY_FAVORITE_LIST)) {
				favoriteService.findMyFavoriteList(message, content);
			}else if (command.equals(SAVE_OR_REMOVE_FAVORITE)) {
				favoriteService.saveOrCancelFavorite(message, content);
			}else if (command.equals(FIND_WEIXIN_MENU)) {
				weiXinMenuService.findWeiXinMenu(message, content);
			} else if (command.equals(FIND_WEIXIN_MENU_BUTTONS)) {
				weiXinMenuService.findWeiXinMenuButton(message, content);
			}else if (command.equals(ACTIVITY_APPLICANT)) {
				activityApplicantService.applicant(message, content);
			}else if (command.equals(APPLICANT_LIST)) {
				activityApplicantService.applicantList(message, content);
			}else if (command.equals(ACTIVITY_DETAIL)) {
				actActivityService.activityDetail(message, content);
			}else if (command.equals(ACTIVITY_WINNING)){
				activityWinningService.winning(message, content);
			}else if (command.equals(WINNING_LIST)) {
				activityWinningService.winningList(message, content);
			}else if (command.equals(APPLICANT_DETAIL)) {
				activityApplicantService.applicantDetail(message, content);
			}else if (command.equals(SAVE_WEIXIN_USER)) {
				weiXinUserService.saveWeiXinUser(message, content);
			}else if (command.equals(FIND_WEIXIN_USER)) {
				weiXinUserService.findWeiXinUser(message, content);
			}else if(command.equals(REMOVE_WEIXIN_USER)) {
				weiXinUserService.removeWeiXinUser(message, content);
			} else if (command.equals(FIND_WEIXIN_USER_AUTHOR)) {
				weiXinUserService.findWeiXinUserAuthor(message, content);
			} else if (command.equals(SAVE_ACTIVITY)) {
				activityService.saveActivity(message, content);
			} else if (command.equals(FIND_ACTIVITY_LIST)) {
				activityService.findActivityList(message, content);
			}
			else if (command.equals(DELETE_ACTIVITY)) {
				activityService.deleteActivity(message, content);
			} else if (command.equals(SAVE_ONLINE_SIGN)) {
				backschoolOnlineSignService.saveOnlineSign(message, content);
			} else if (command.equals(GET_ONLINE_SIGN)) {
				backschoolOnlineSignService.getOnlineSign(message, content);
			}
			else if (command.equals(GET_ACTIVITY_BYID)) {
				activityService.getActivityById(message, content);
			} else if (command.equals(GET_SIGN_LIST)){
				backschoolOnlineSignService.getSignList(message, content);
			}
			else if (command.equals(FIND_ELECTRONIC_BOOK_LIST)){
				electronicBookService.findElectronicBookList(message, content);
			}

			//修改环信密码
			else if(command.equals(USER_CHANGE_EASEMOB_PASSWORD)) {
				passWordService.updateEasemobPassword(message, content);
			}
			else if (command.equals(SAVE_FOUN_PROJECT)) {
				projectService.saveFounProject(message, content);
			} else if(command.equals(UPDATE_FOUN_PROJECT)) {
				projectService.updateFounProject(message, content);
			} else if (command.equals(DELETE_FOUN_PROJECT)) {
				projectService.deleteByFounProject(message, content);
			}
			else if (command.equals(SAVE_FOUN_INCOME)){
				donationService.saveFounIncome(message, content);
			} else if (command.equals(FIND_ENTERPRISE_TEAM_LIST)) {
				enterpriseTeamService.findEnterpriseTeamList(message, content);
			} else if(command.equals(FIND_ENTERPRISE_JOB_LIST)) {
				enterpriseJobService.findEnterpriseJobList(message, content);
			} else if(command.equals(FIND_ENTERPRISE_Job)) {
				enterpriseJobService.findEnterpriseJob(message, content);
			}
			else if(command.equals(SAVE_RESUME_BASE)){
				resumeBaseService.saveResumeBase(message, content);
			}
			else if(command.equals(DELETE_RESUME_BASE)){
				resumeBaseService.deleteResumeBase(message, content);
			}
			else if(command.equals(GET_EVENT_COMMENT)){  //评论读取接口
				mobEventService.findEventComment(message, content);
			}
			else if(command.equals(TOKEN_REFRESH)){  //token
				if (token==null||token.length()<=0)
					userAuthService.token_refresh(message, content);  //token带参传入			
				else				    
				    userAuthService.token_refresh(message, content,token); //token header传入
			}	
			else if (command.equals(ALUMNI_TREE_MENU)){
				malumniService.getAlumniTreeByWeb(message, content); //取分分组织结构
			}
			else if (command.equals(CHECK_ACCUNTNUM_ISADMIN)){
				userService.selectByTelephone(message, content); //APPManager 检测校友组织管理员权限
			}
			else if(command.equals(ALUMNI_LIST)){
				malumniService.alumniMemebers(message, content); //APPManager 获取校友列表，限定某个校友组织
			}
			else if(command.equals(FIND_FRIENDS_COMMEND_LIST)){  //获取推荐好友
				chatContactsService.getCommendFriendList(message, content);
            }
			else if(command.equals(FIND_ALUMNI_COMMEND_LIST)){ //获取推荐分会
				malumniService.getCommendAlumniList(message, content);
			}
            else if(command.equals(ALUMNI_USERPROFILE_DETAIL)){
				appService.selectByPhoneNum(message, content); //APPManager 获取某个校友信息
			}
			else if(command.equals(UPDATE_ALUMNI_STATUS)){
				appService.UpdateProfileStatus(message, content); // APPManager 更新校友状态
			}
			else if(command.equals(VIEW_ALUMNI_CONTACT)){
				mcontactService.QueryList(message, content); //APPManager 查询本分会组织留言
			}
			else if(command.equals(VIEW_ALUMNI_CONTACT_DETAIL)){
				mcontactService.getById(message, content); //APPManager 查询本分会组织留言详情
			}
			else if(command.equals(VIEW_ALUMNI_CONTACT_REPLAY)){
				mcontactService.reply(message, content);//APPManager 查询本分会组织留言，留言回复
			}			
			else if(command.equals(ALUMNI_MEMBER_CHAT)){
				mmemberService.QueryChat(message, content);//APPManager 聊天室成员
			}	
			else if(command.equals(ALUMNI_MEMBER_LIST)){
				mmemberService.QueryList(message, content);//APPManager组织成员列表
			}	
			else if(command.equals(ALUMNI_MEMBER_CLEAN)){
				mmemberService.mClean(message, content);// APPManager聊天室踢出成员
			}	
			else if(command.equals(ALUMNI_MEMBER_DELETE)){
				mmemberService.mDelete(message, content);// APPManager组织删除成员
			}
			else if(command.equals(ALUMNI_COMPLAIN_LIST)){
				mcomplainService.QueryList(message, content); //APPManager 投诉清单
			}
			else if(command.equals(ALUMNI_COMPLAIN_DETAIL)){
				mcomplainService.getById(message, content);//APPManager 投诉详情
			}
			else if(command.equals(ALUMNI_COMPLAIN_HANDLER)){
				mcomplainService.update(message, content);//APPManager 投诉处理
			}
			else if(command.equals(ALUMNI_ACTIVITY_LIST)){
				mactivityService.QueryList(message, content);//APPManager本组织活动列表
			}
			else if(command.equals(ALUMNI_ACTIVITY_ADD)){
				mactivityService.add(message, content);//APPManager新增活动
			}
			else if(command.equals(ALUMNI_ACTIVITY_DELETE)){
				mactivityService.delete(message, content);//APPManager 删除活动
			}	
			else if(command.equals(APP_UTIL_FUNCTION)){
				mutilService.CountNewInfo(message, content);//APP公司信息提醒功能
			}
	        else
			{
				message.setMsg("找不到命令");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("server exception");
			message.setObj(null);
			message.setSuccess(false);
		}

		logger.info(message);
		super.writeJson(message);

	}

	public String doNotNeedSessionAndSecurity_getUserInfoByQrCode()
			throws Exception {
		HttpServletRequest request = this.getRequest();
		String sign = request.getParameter("sign");
		String accountNum = WebUtil.getFromBase64(sign);
		List<UserInfo> list = userInfoService.selectByAccountNum2FullName(accountNum);
		request.setAttribute("item", list.get(0));
		request.setAttribute("list", list);
		return "qrcode";
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String[] getUploadFileBase64() {
		return uploadFileBase64;
	}

	public void setUploadFileBase64(String[] uploadFileBase64) {
		this.uploadFileBase64 = uploadFileBase64;
	}

	//get user agent
    private String getUserAgent() {
        return request.getHeader("user-agent");
    }

    //get request headers
    private Map<String, String> getHeadersInfo() {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
    
}
