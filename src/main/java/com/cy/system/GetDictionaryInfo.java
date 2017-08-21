package com.cy.system;

import com.cy.base.entity.TreeString;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.SpringContextHolder;
import com.cy.core.analysis.action.AnalysisAction;
import com.cy.core.analysis.utils.AnalysisUtils;
import com.cy.core.industry.dao.IndustryMapper;
import com.cy.core.industry.entity.Industry;
import com.cy.core.region.dao.RegionMapper;
import com.cy.core.region.entity.Region;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cy.alipay.config.AlipayConfig;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dicttype.dao.DictTypeMapper;
import com.cy.core.dicttype.entity.DictType;
import com.cy.core.resource.dao.ResourceMapper;
import com.cy.core.resource.entity.Resource;
import com.cy.core.role.dao.RoleMapper;
import com.cy.core.role.entity.Role;
import com.cy.core.systemsetting.dao.SystemSettingMapper;
import com.cy.core.systemsetting.entity.SystemSetting;
import com.cy.core.user.dao.UserMapper;
import com.cy.core.user.entity.User;
import com.cy.util.WebUtil;

@Component("getDictionaryInfo")
public class GetDictionaryInfo {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GetDictionaryInfo.class);

	@Autowired
	private ResourceMapper resourceMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DictTypeMapper dictTypeMapper;

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private SystemSettingMapper systemSettingMapper;

	@Autowired
	private IndustryMapper industryMapper;

	@Autowired
	private RegionMapper regionMapper;

	/*private static WebApplicationContext springContext;
	public static ServletContext servletContext;// 这个是为了取servletContext
	private static GetDictionaryInfo instance;*/

	/*public static Map<String, Object> dictionaryInfoMap = new ConcurrentHashMap<String, Object>();

	public static Map<String, Object> authorityMap = new ConcurrentHashMap<String, Object>();

	*//** --功能菜单和URL的映射-- **//*
	public static Map<String, Resource> menuUrlMap = new ConcurrentHashMap<String, Resource>();*/

	public static GetDictionaryInfo getInstance() {
		/*springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if (null == instance)
			instance = (GetDictionaryInfo) springContext.getBean("getDictionaryInfo");
		return instance;*/
		return SpringContextHolder.getBean("getDictionaryInfo");
	}

	public void getAllInfo() {
		loadAnalysis() ;
		loadDict();
		loadRegionDict();
		loadIndustryDict();
		loadResources();
		initDB();
		initSystem();
	}

	public void loadRegionDict() {
		// 设置时区
		System.setProperty("user.timezone","Asia/Shanghai");
		logger.info("##############################	开始加载区域字典数据##############################");
		logger.info("##############################开始从数据库中取数据##############################");
		List<TreeString> treeList = new ArrayList<TreeString>();
		List<Region> list = regionMapper.selectAll();

		for (Region r : list) {
			TreeString node = new TreeString();
			node.setId(r.getId());
			node.setPid(r.getPid());
			node.setText(r.getName());
			node.setState( r.getState() );	//lixun
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("level", r.getLevel());
			attributes.put("areaCode",r.getAreaCode());
			attributes.put("postCode",r.getPostCode());
			node.setAttributes(attributes);
			treeList.add(node);
		}
		CacheUtils.remove("regionDicts");
		CacheUtils.put("regionDicts", treeList);
		logger.info("##############################数据分别放进了缓存sysCache（regionDicts）##############################");

	}

	public void loadIndustryDict() {
		logger.info("##############################	开始加载行业字典数据##############################");
		logger.info("##############################开始从数据库中取数据##############################");
		List<TreeString> treeList = new ArrayList<TreeString>();
		List<Industry> industryList = industryMapper.selectIndustryList(new HashMap<String, Object>());

		for (Industry industry : industryList) {
			TreeString node = new TreeString();
			node.setId(industry.getCode());
			node.setPid(industry.getParentCode());
			node.setText(industry.getValue());
			node.setState( industry.getState() );	//lixun
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("level", industry.getLevel());
			attributes.put("sequence", industry.getSequence());
			node.setAttributes(attributes);
			treeList.add(node);
		}
		CacheUtils.remove("industryDicts");
		CacheUtils.put("industryDicts", treeList);
		logger.info("##############################数据分别放进了缓存sysCache（industryDicts）##############################");

	}

	public void loadDict() {
		logger.info("##############################	开始加载字典数据##############################");
		logger.info("##############################开始从数据库中取数据##############################");
		// 取出所有字典
		List<DictType> dictTypeList = dictTypeMapper.selectAll();
		CacheUtils.remove("dicts");
		CacheUtils.put("dicts", dictTypeList);
		logger.info("##############################数据分别放进了缓存sysCache（dicts）##############################");

	}

	/**
	 * 初始化统计分析缓存
     */
	public void loadAnalysis() {
		AnalysisUtils.init();
	}

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-28
	 * 描述：加载资源文件
	 */
	public void loadResources(){
		Map authority = Maps.newHashMap();;
		Map menuUrl=Maps.newHashMap();
		logger.info("##############################	开始加载资源数据##############################");
		logger.info("##############################开始从数据库中取数据##############################");
		List<Resource> resources = resourceMapper.selectAll();
		logger.info("##############################资源数据大小" + resources.size() + "##############################");
		if (resources != null && resources.size() > 0) {
			for (Resource resource : resources) {
				if (!WebUtil.isEmpty(resource.getUrl())) {
					authority.put(resource.getUrl().trim(), resource.getRoles());
					authority.put(resource.getName().trim(), resource.getRoles());
					menuUrl.put(resource.getUrl().trim(), resource);
				}
			}
		}
		logger.info("##############################权限MAP大小" + authority.size() + "##############################");
		logger.info("##############################数据从数据库中全部取出##############################");
		CacheUtils.remove("resources");
		CacheUtils.put("resources", resources);
		CacheUtils.remove("authority");
		CacheUtils.put("authority", authority);
		CacheUtils.remove("menuUrl");
		CacheUtils.put("menuUrl", menuUrl);
		logger.info("##############################数据分别放进了缓存sysCache（resources，authority，menuUrl）##############################");

	}

	public void initDB() {
		/**
		 * 修改人：Kent
		 * 修改时间：2016-07-26
		 */
		// 超级管理员角色是否存在
		/*Role role = roleMapper.selectSystemAdmin();
		if (role == null) {
			role = new Role();
			role.setRoleName("超级管理员");
			role.setSystemAdmin(1);
			role.setFlag(0);
			roleMapper.add(role);
		}
		// 超级管理员帐号是否存在
		User user = userMapper.selectAdminUser();
		if (user == null) {
			user = new User();
			user.setUserAccount("system");
			user.setUserName("超级管理员");
			user.setUserPassword(SecretUtil.encryptToSHA("cykjqwer"));
			user.setFlag(0);
			user.setRoleId(role.getRoleId());
			userMapper.save(user);
		}*/
		// 初始化学校名称
		String deptNo = Global.deptNo;
		String schoolSign = Global.schoolSign;
		String schoolName = "";
		if (!WebUtil.isEmpty(deptNo) && !WebUtil.isEmpty(schoolSign)) {
			schoolName = schoolSign.length() > 4 ? schoolSign.substring(0, schoolSign.length() - 4) : schoolSign;
			Dept dept = this.deptMapper.getById(Global.deptNo);
			if (dept == null) {
				dept = new Dept();
				dept.setDeptId(deptNo);
				dept.setDeptName(schoolName);
				dept.setFullName(schoolName);
				dept.setCreateTime(new Date());
				dept.setParentId("0");
				dept.setLevel(4);
				dept.setStatus("20");
				deptMapper.insert(dept);
			}
		}

	}

	public void initSystem() {
		List<SystemSetting> list = systemSettingMapper.selectAll();
		if (list != null && list.size() > 0) {
			SystemSetting systemSetting = list.get(0);
			Global.sign = systemSetting.getDownload_app_url();
			Global.smtpHost = systemSetting.getSmtpHost();
			Global.smtpPort = systemSetting.getSmtpPort();
			Global.email_account = systemSetting.getEmail_account();
			Global.email_password = systemSetting.getEmail_password();

			AlipayConfig.partner = systemSetting.getPartner();
			AlipayConfig.seller_email = systemSetting.getSellerEmail();
			AlipayConfig.key = systemSetting.getKey();
			AlipayConfig.notify_url = systemSetting.getNotify_url();
			AlipayConfig.return_url = systemSetting.getReturn_url();
			AlipayConfig.exter_invoke_ip = systemSetting.getExter_invoke_ip();
			com.cy.wapalipay.config.AlipayConfig.partner = systemSetting.getPartner();
			com.cy.wapalipay.config.AlipayConfig.seller_email = systemSetting.getSellerEmail();
			com.cy.wapalipay.config.AlipayConfig.key=systemSetting.getKey();
			com.cy.wapalipay.config.AlipayConfig.private_key=systemSetting.getAppPrivateKey();
			com.cy.wapalipay.config.AlipayConfig.ali_public_key=systemSetting.getAlipayPublicKey();
			com.cy.wapalipay.config.AlipayConfig.merchant_url=systemSetting.getWap_merchant_url();
			com.cy.wapalipay.config.AlipayConfig.notify_url=systemSetting.getWap_notify_url();
			com.cy.wapalipay.config.AlipayConfig.return_url=systemSetting.getWap_return_url();

			com.cy.common.utils.alipay.config.AlipayConfig.seller_email 	= systemSetting.getSellerEmail() ;
			com.cy.common.utils.alipay.config.AlipayConfig.app_id 			= systemSetting.getAppId() ;
			com.cy.common.utils.alipay.config.AlipayConfig.partner 			= systemSetting.getPartner() ;
//			com.cy.common.utils.alipay.config.AlipayConfig.input_charset 	= systemSetting.getInputCharset() ;
			com.cy.common.utils.alipay.config.AlipayConfig.alipay_public_key= systemSetting.getAlipayPublicKey() ;
			com.cy.common.utils.alipay.config.AlipayConfig.app_public_key 	= systemSetting.getAppPublicKey() ;
			com.cy.common.utils.alipay.config.AlipayConfig.app_private_key 	= systemSetting.getAppPrivateKey() ;
			com.cy.common.utils.alipay.config.AlipayConfig.payment_type 	= systemSetting.getPaymentType() ;
			com.cy.common.utils.alipay.config.AlipayConfig.serverUrl 		= systemSetting.getServerUrl() ;
			com.cy.common.utils.alipay.config.AlipayConfig.notify_url 		= systemSetting.getNotify_url() ;
			com.cy.common.utils.alipay.config.AlipayConfig.return_url 		= systemSetting.getReturn_url() ;
			com.cy.common.utils.alipay.config.AlipayConfig.exter_invoke_ip 	= systemSetting.getExter_invoke_ip() ;

			Global.userAccount = systemSetting.getSmsAccount();
			Global.password = systemSetting.getSmsPassword();
			Global.smsUrl = systemSetting.getSmsUrl();
			Global.sendType = systemSetting.getSendType();
			Global.smsBirthdayTemplate = systemSetting.getsmsBirthdayTemplate();
			Global.schoolLogo = systemSetting.getSchoolLogo();
			Global.smsCodeTemplate = systemSetting.getSmsCodeTemplate();
			Global.smsVisitTemplate = systemSetting.getSmsVisitTemplate();
//			Global.web_homepage_api_url = systemSetting.getWeb_homepage_api_url();
			Global.is_new_type_open = systemSetting.getIs_new_type_open();
			Global.FOUNDATION_NAME = systemSetting.getFoundationName();
			Global.FOUNDATION_SIGNET = systemSetting.getFoundationSignet();
			Global.FOUNDATION_LOGO = systemSetting.getFoundationLogo();
			Global.foundation_smtpHost = systemSetting.getFoundationSmtpHost();
			Global.foundation_smtpPort = systemSetting.getFoundationSmtpPort();
			Global.foundation_email_account = systemSetting.getFoundationEmailAccount();
			Global.foundation_email_password = systemSetting.getFoundationEmailPassword();
			Global.is_audit_personal_activity = systemSetting.getIs_audit_personal_activity();

			Global.client_id = systemSetting.getClientId();
			Global.client_secret = systemSetting.getClientSecret();
			Global.cloud_user_name = systemSetting.getCloudUserName();
			Global.cloud_user_password = systemSetting.getCloudUserPassword();

		}
	}

	/*public void reloadDictionaryInfoMap() {
		logger.info("##############################系统开始重新加载字典表##############################");
		dictionaryInfoMap.clear();
		authorityMap.clear();
		menuUrlMap.clear();
		logger.info("##############################字典表MAP,清空成功##############################");
		loadDict();
		logger.info("##############################重新加载字典表成功##############################");
	}

	public static Map<String, Object> getDictionaryInfoMap() {
		return dictionaryInfoMap;
	}

	public static void setDictionaryInfoMap(Map<String, Object> dictionaryInfoMap) {
		GetDictionaryInfo.dictionaryInfoMap = dictionaryInfoMap;
	}*/

}
