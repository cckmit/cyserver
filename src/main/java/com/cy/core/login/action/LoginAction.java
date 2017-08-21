package com.cy.core.login.action;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import com.cy.common.utils.CacheUtils;
import com.cy.core.log.util.LogUtils;
import com.cy.core.news.service.NewsService;
import com.cy.util.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.Tree;
import com.cy.core.login.service.LoginService;
import com.cy.core.resource.entity.Resource;
import com.cy.core.resource.service.ResourceService;
import com.cy.core.role.entity.Role;
import com.cy.core.role.service.RoleService;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserRole;
import com.cy.core.user.service.UserService;
import com.cy.system.Global;
import com.cy.system.SecretUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Namespace("/login")
@Action("loginAction")
public class LoginAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoginAction.class);

	@Autowired
	private Producer captchaProducer;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private NewsService newsService;

	private String userAccount;

	private String userPassword;

	private String userTelephone ;

	private String validCode;

	private String smscode;

	private String date;	//用户主机时间

	private String agent;  //用户操作系统与浏览器版本

	private String userName; //用户姓名

	private String userId; //用户ID

	private boolean isValidateCodeLogin ;

	/**
	 * 登录
	 */
	public void doNotNeedSessionAndSecurity_login() {
		Message message = new Message();
		try {
			String code = (String) getRequest().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (code == null) {
				message.setMsg("请刷新当前页面");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}
			if (isValidateCodeLogin(this.userAccount, false, false) && !code.equalsIgnoreCase(validCode) && !validCode.equals("1")) {
				isValidateCodeLogin = isValidateCodeLogin(this.userAccount, true, false) ;
				message.setMsg("验证码错误");
				message.setObj(isValidateCodeLogin);
				message.setSuccess(false);
			} else {
				/**
				 *获取用户时不区分系统，并且仅获取用户基本信息
				 * 更改人：Kent
				 * 更改时间：2016-07-25 12:11
				 */
				//User selectUser = userService.selectByUserAccount(this.userAccount);
				//查询用户基本信息
				User selectUser = userService.selectByUserAccountNoSys(this.userAccount);
				if (selectUser != null) {
					//设置用户角色信息
					String roleIds = "";
					String roleNames = "";
					for (Role r : selectUser.getRoles()) {
						roleIds += r.getRoleId() + ",";
						roleNames += r.getRoleName() + ",";
					}
					if (selectUser.getRoles().size() > 0) {
						roleIds = roleIds.substring(0, roleIds.length() - 1);
						roleNames = roleNames.substring(0, roleNames.length() - 1);
					}
					selectUser.setRoleIds(roleIds);
					selectUser.setRoleNames(roleNames);

					List<Resource> menus = new ArrayList<Resource>();
					List<Resource> grantTreeList = new ArrayList<Resource>();
					// 校友会角色授权时使用该list
					List<Resource> xgrantTreeList = new ArrayList<Resource>();
					if (selectUser == null) {
						isValidateCodeLogin = isValidateCodeLogin(this.userAccount, true, false) ;
						message.setObj(isValidateCodeLogin);
						message.setMsg("帐号错误");
						message.setSuccess(false);
					} else if (StringUtils.isBlank(selectUser.getIsFirstLogin()) || "0".equals(selectUser.getIsFirstLogin().trim())) {
						message.init(false, "当前为首次登陆,请修改密码并绑定手机号", selectUser.getUserId(), "201");
					} else if (StringUtils.isBlank(selectUser.getTelephone())) {
						message.init(false, "绑定手机号不存在，请绑定手机号", selectUser.getUserId(), "201");
					} else {
						if (!selectUser.getUserPassword().equals(SecretUtil.encryptToSHA(this.userPassword))) {
							isValidateCodeLogin = isValidateCodeLogin(this.userAccount, true, false) ;
							message.setObj(isValidateCodeLogin);
							message.setMsg("密码错误");
							message.setSuccess(false);
						} else {
							String checkFlg = Global.login_smscode_check;

							if (checkFlg.equals("1") && !validCode.equals("1")) {
								message.setSuccess(true);
								// 短信发送
								loginService.sendSMSCodeForWebLogin(message, selectUser);

								//loginService.sendSMSRemindForWebLogin(message, selectUser, this.agent);
							} else {
								//更新最后一次登陆时间
								selectUser.setLastLoginTime((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date()));
								userService.update(selectUser);

								if (selectUser.getRoles().size() > 0) {
									menus = resourceService.getMenuByRoles(selectUser.getRoles());
									grantTreeList = resourceService.getResourcesByRoles(selectUser.getRoles());
									//xgrantTreeList = grantTreeList;
								}
								List<Tree> treeList = Lists.newArrayList();
								List<Tree> tree = Lists.newArrayList();
								if (menus != null && menus.size() > 0) {
									for (Resource resource : menus) {
										if (resource.getType().equals("菜单")) {
											Tree node = new Tree();
											node.setId(resource.getId());
											node.setPid(resource.getPid());
											node.setText(resource.getMenuName());
											node.setIconCls(resource.getIconCls());
											Map<String, String> attributes = new HashMap<String, String>();
											attributes.put("url", resource.getUrl());
											node.setAttributes(attributes);
											treeList.add(node);
										}
									}
									resourceService.parseTree(tree, treeList);
								}
								//getSession().put("xgrantTreeList", xgrantTreeList);
								getSession().put("grantTreeList", grantTreeList);
								getSession().put("menus", menus);
								getSession().put("menuTree", tree);
								getSession().put("user", selectUser);
								message.setSuccess(true);
								message.setReturnId("21");
								HttpServletRequest request = ServletActionContext.getRequest();
								LogUtils.saveLog(request,"登录");

								// 登录成功，清楚错误登录计数器
								isValidateCodeLogin = isValidateCodeLogin(this.userAccount, false, true) ;
							}
							// 用户拥有的角色
						/*Role hasRole = null;
						List<UserRole> hasRoles = selectUser.getUserRoles();

						if (hasRoles.size() != 0) {
							if (hasRoles.size() == 1) {	// 只有一种角色，按原来的程序执行
								hasRole = hasRoles.get(0).getRole();
								if (hasRole.getSystemAdmin() == 1) {
									selectUser.setRole(hasRole);
									Map<String, Object> map = GetDictionaryInfo.dictionaryInfoMap;
									@SuppressWarnings("unchecked")
									List<Resource> allList = (List<Resource>) map.get("resources");
									for (Resource resource : allList) {
										// 后台菜单
										if (resource.getType().equals("菜单") && resource.getFlag() == 0) {
											menus.add(resource);
										}
										if (resource.getFlag() == 0) {
											grantTreeList.add(resource);
										} else {
											xgrantTreeList.add(resource);
										}
									}
								} else {
									List<Role> roles2 = roleService.getMenu(hasRole.getRoleId());
									// 拥有的菜单
									for (Role role : roles2) {
										List<Resource> resources = role.getList();
										for (Resource resource : resources) {
											if (resource.getType().equals("菜单")) {
												menus.add(resource);
											}
											grantTreeList.add(resource);
										}
									}
								}
							} else {
								List<Role> roles3 = roleService.getMenuByUserRole(hasRoles);
								// 拥有的菜单
								for (Role role : roles3) {
									List<Resource> resources = role.getList();
									for (Resource resource : resources) {
										if (resource.getType().equals("菜单")) {
											menus.add(resource);
										}
										grantTreeList.add(resource);
									}
								}
							}
							removeDuplicate(menus);
							removeDuplicate(grantTreeList);
							getSession().put("xgrantTreeList", xgrantTreeList);
							getSession().put("grantTreeList", grantTreeList);
							getSession().put("menus", menus);
							getSession().put("user", selectUser);
							message.setSuccess(true);
							message.setReturnId("21");
						}*/
						}
					}
				} else {
					isValidateCodeLogin = isValidateCodeLogin(this.userAccount, true, false) ;
					message.init(false, "用户不存在!", isValidateCodeLogin);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.writeJson(message);
	}

	public void doNotNeedSessionAndSecurity_loginx() {
		Message message = new Message();
		String code = (String) getRequest().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (code == null) {
			message.setMsg("请刷新当前页面");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}
		if (!code.equalsIgnoreCase(validCode)) {
			message.setMsg("验证码错误");
			message.setSuccess(false);
		} else {
			User selectUser = userService.selectByUserAccount(this.userAccount);
			List<Resource> menus = new ArrayList<Resource>();
			List<Resource> grantTreeList = new ArrayList<Resource>();
			if (selectUser == null) {
				message.setMsg("帐号错误");
				message.setSuccess(false);
			} else {
				if (!selectUser.getUserPassword().equals(SecretUtil.encryptToSHA(this.userPassword))) {
					message.setMsg("密码错误");
					message.setSuccess(false);
				} else {
					// 用户拥有的角色
					Role hasRole = null;
					List<UserRole> hasRoles = selectUser.getUserRoles();
					if (hasRoles.size() == 1) {	// 只有一种角色，按原来的程序执行
						hasRole = hasRoles.get(0).getRole();
						if (hasRole.getSystemAdmin() == 1) {
							//Map<String, Object> map = GetDictionaryInfo.dictionaryInfoMap;
							@SuppressWarnings("unchecked")
							List<Resource> allList = (List<Resource>) CacheUtils.get("resources");
							for (Resource resource : allList) {
								// 后台菜单
								if (resource.getType().equals("菜单") && resource.getFlag() == 0) {
									menus.add(resource);
								}
								if (resource.getFlag() == 0) {
									grantTreeList.add(resource);
								}
							}
						} else {
							List<Role> roles2 = roleService.getMenu(hasRole.getRoleId());
							// 拥有的菜单
							for (Role role : roles2) {
								List<Resource> resources = role.getList();
								for (Resource resource : resources) {
									if ("菜单".equals(resource.getType())) {
										menus.add(resource);
									}
									grantTreeList.add(resource);
								}
							}
						}
						removeDuplicate(menus);
						removeDuplicate(grantTreeList);
						getSession().put("grantTreeList", grantTreeList);
						getSession().put("menus", menus);
						getSession().put("user", selectUser);
						message.setSuccess(true);
						HttpServletRequest request = ServletActionContext.getRequest();
						LogUtils.saveLog(request,"登录");
					}
				}
			}
		}
		super.writeJson(message);
	}

	/**
	 * 退出
	 */
	public void doNotNeedSessionAndSecurity_logout() {
		Message message = new Message();
		getSession().clear();
		message.setSuccess(true);
		super.writeJson(message);
	}

	/**
	 * 修改密码
	 */
	public void doNotNeedSecurity_updateCurrentPwd() {
		Message message = new Message();
		try {
			String pwd = getRequest().getParameter("pwd");
			User user = (User) getSession().get("user");
			user.setUserPassword(SecretUtil.encryptToSHA(pwd));
			userService.updatePassword(user);
			message.setMsg("密码修改成功!");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("密码修改失败!");
			message.setSuccess(false);
		}
		super.writeJson(message);

	}

	/**
	 * 发送短信验证码
	 */
	public void doNotNeedSessionAndSecurity_sendSmscode() {
		Message message = new Message();
		User selectUser = userService.selectByUserAccount(this.userAccount);
		if (selectUser == null) {
			message.setMsg("帐号错误");
			message.setSuccess(false);
		} else {
			if (!selectUser.getUserPassword().equals(SecretUtil.encryptToSHA(this.userPassword))) {
				message.setMsg("密码错误");
				message.setSuccess(false);
			} else {
				message.setSuccess(true);
				// 短信发送
				loginService.sendSMSCodeForWebLogin(message, selectUser);
			}
		}
		super.writeJson(message);
	}

	/**
	 * 获取短信验证码
	 * 2016-07-29
	 * QiPengfei
	 */
	public void doNotNeedSessionAndSecurity_getSmscode() {
		Message message = new Message();
		if(this.userTelephone != null){
			if(isMobile(this.userTelephone)){
				message.setSuccess(true);
				message.setMsg("验证码发送成功");
				// 短信发送
				loginService.getSMSCodeByTelephone(message, this.userTelephone);
			}else{
				message.setMsg("手机号格式不对");
				message.setSuccess(false);
			}
		}else{
			message.setMsg("请输入手机号");
			message.setSuccess(false);
		}

		super.writeJson(message);
	}

	/**
	 *手机号正则验证
	 * 2016-07-29
	 * QiPengfei
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	public void doNotNeedSessionAndSecurity_loginGo() {
		Message message = new Message();
		User selectUser = userService.selectByUserAccount(this.userAccount);
		if (selectUser == null) {
			message.setMsg("帐号错误");
			message.setSuccess(false);
		} else {
			if (!selectUser.getUserPassword().equals(SecretUtil.encryptToSHA(this.userPassword))) {
				message.setMsg("密码错误");
				message.setSuccess(false);
			} else {
				//验证手机验证码的正确性
				boolean checkRs = loginService.checkSMSCode(message, selectUser.getTelephone(), this.smscode);
				if(checkRs){
					//更新最后一次登陆时间
					selectUser.setLastLoginTime((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date()));
					userService.update(selectUser);
					//设置用户角色信息
					String roleIds="";
					String roleNames="";
					for(Role r : selectUser.getRoles()){
						roleIds+=r.getRoleId()+",";
						roleNames+=r.getRoleName()+",";
					}
					if(selectUser.getRoles().size()>0){
						roleIds=roleIds.substring(0,roleIds.length()-1);
						roleNames=roleNames.substring(0,roleNames.length()-1);
					}
					selectUser.setRoleIds(roleIds);
					selectUser.setRoleNames(roleNames);

					List<Resource> menus = new ArrayList<Resource>();
					List<Resource> grantTreeList = new ArrayList<Resource>();
					// 校友会角色授权时使用该list
					List<Resource> xgrantTreeList = new ArrayList<Resource>();
					if (selectUser.getRoles().size()>0){
						menus = resourceService.getMenuByRoles(selectUser.getRoles());
						grantTreeList = resourceService.getResourcesByRoles(selectUser.getRoles());
						//xgrantTreeList = grantTreeList;
					}
					//getSession().put("xgrantTreeList", xgrantTreeList);
					/*getSession().put("grantTreeList", grantTreeList);
					getSession().put("menus", menus);
					getSession().put("user", selectUser);
					message.setSuccess(true);
					message.setReturnId("21");*/
					List<Tree> treeList = Lists.newArrayList();
					List<Tree> tree = Lists.newArrayList();
					if (menus != null && menus.size() > 0) {
						for (Resource resource : menus) {
							if (resource.getType().equals("菜单")) {
								Tree node = new Tree();
								node.setId(resource.getId());
								node.setPid(resource.getPid());
								node.setText(resource.getMenuName());
								node.setIconCls(resource.getIconCls());
								Map<String, String> attributes = new HashMap<String, String>();
								attributes.put("url", resource.getUrl());
								node.setAttributes(attributes);
								treeList.add(node);
							}
						}
						resourceService.parseTree(tree, treeList);
					}
					getSession().put("grantTreeList", grantTreeList);
					getSession().put("menus", menus);
					getSession().put("menuTree",tree);
					getSession().put("user", selectUser);
					message.setSuccess(true);
					message.setReturnId("21");
					HttpServletRequest request = ServletActionContext.getRequest();
					LogUtils.saveLog(request,"登录");
					/*List<Resource> menus = new ArrayList<Resource>();
					List<Resource> grantTreeList = new ArrayList<Resource>();
					// 校友会角色授权时使用该list
					List<Resource> xgrantTreeList = new ArrayList<Resource>();


					// 用户拥有的角色
					Role hasRole = selectUser.getRole();
					if (hasRole != null) {
						if (hasRole.getSystemAdmin() == 1) {
//							Map<String, Object> map = GetDictionaryInfo.dictionaryInfoMap;
							@SuppressWarnings("unchecked")
							List<Resource> allList = (List<Resource>) CacheUtils.get("resources");
							for (Resource resource : allList) {
								// 后台菜单
								if (resource.getType().equals("菜单") && resource.getFlag() == 0) {
									menus.add(resource);
								}
								if (resource.getFlag() == 0) {
									grantTreeList.add(resource);
								}
							}
						} else {
							List<Role> roles2 = roleService.getMenu(hasRole.getRoleId());
							// 拥有的菜单
							for (Role role : roles2) {
								List<Resource> resources = role.getList();
								for (Resource resource : resources) {
									if ("菜单".equals(resource.getType())) {
										menus.add(resource);
									}
									grantTreeList.add(resource);
								}
							}
						}
						getSession().put("grantTreeList", grantTreeList);
						getSession().put("menus", menus);
						getSession().put("user", selectUser);
						message.setSuccess(true);
					}*/
				}else{
					message.setSuccess(false);
				}

			}
		}
		super.writeJson(message);
	}

	/**
	 * 验证码
	 */
	public void doNotNeedSessionAndSecurity_captchaImage() {
		try {
			getResponse().setDateHeader("Expires", 0);
			getResponse().setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			getResponse().addHeader("Cache-Control", "post-check=0, pre-check=0");
			getResponse().setHeader("Pragma", "no-cache");
			getResponse().setContentType("image/jpeg");

			String capText = captchaProducer.createText();

			getRequest().getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

			BufferedImage bi = captchaProducer.createImage(capText);
			ServletOutputStream out = getResponse().getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-30
	 * 描述：初始化后台主菜单
	 */
	public void doNotNeedSecurity_initMainMenu(){
		// 获取session中的菜单
		@SuppressWarnings("unchecked")
		List<Tree> trees = (List<Tree>) getSession().get("menuTree");
		super.writeJson(trees);
	}
	/**
	 * 初始化后台菜单
	 *
	 * @throws Exception
	 */
	public void doNotNeedSecurity_initMenu() {
		// 获取session中的菜单
		@SuppressWarnings("unchecked")
		List<Resource> menus = (List<Resource>) getSession().get("menus");
		if (menus != null && menus.size() > 0) {
			List<Tree> treeList = new ArrayList<Tree>();
			List<Tree> tree = new ArrayList<Tree>();
			for (Resource resource : menus) {
				if (resource.getType().equals("菜单")) {
					Tree node = new Tree();
					node.setId(resource.getId());
					node.setPid(resource.getPid());
					node.setText(resource.getMenuName());
					node.setIconCls(resource.getIconCls());
					Map<String, String> attributes = new HashMap<String, String>();
					attributes.put("url", resource.getUrl());
					node.setAttributes(attributes);
					treeList.add(node);
				}
			}
			resourceService.parseTree(tree, treeList);

			super.writeJson(tree);
		} else {
			List<Tree> tree = new ArrayList<Tree>();
			super.writeJson(tree);
		}
	}

	public void doNotNeedSecurity_checkRealUser() {
		String rskey = "rs";
		String errorVal = "1";
		Map<String,String> rs = new HashMap<String,String>(1);

		User user = (User) getSession().get("user");
		if(user == null){
			rs.put(rskey, errorVal);
			super.writeJson(rs);
			return;
		}

		String sessionUserAccount = user.getUserAccount();
		if(sessionUserAccount == null || sessionUserAccount.trim().length() <= 0
				|| this.userAccount == null || this.userAccount.trim().length() <= 0){
			rs.put(rskey, errorVal);
			super.writeJson(rs);
			return;
		}

		if(!sessionUserAccount.trim().equals(this.userAccount.trim())){
			rs.put(rskey, errorVal);
			super.writeJson(rs);
			return;
		}

		rs.put(rskey, "0");
		super.writeJson(rs);
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getSmscode() {
		return smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 *
	 * @param list
	 * 通过resource中的ID比对，把重复项去除
	 */
	public void removeDuplicate(List<Resource> list){
		for(int i = 0 ; i < list.size() - 1; i ++){
			for(int j = list.size() - 1; j > i; j --){
				if(list.get(j).getId()==list.get(i).getId()){
					list.remove(j);
				}
			}
		}
	}


	/**
	 * 绑定手机号并修改初始密码
     */
	public void doNotNeedSessionAndSecurity_binding() {
		Message message = new Message();
		if(StringUtils.isBlank(this.userName)) {
			message.init(false,"用户姓名不能为空",null,"101");
		} else if(StringUtils.isBlank(this.userPassword)) {
			message.init(false,"密码不能为空",null,"102");
		} else if(StringUtils.isBlank(this.userTelephone)) {
			message.init(false,"手机号不能为空",null,"102");
		} else if(StringUtils.isBlank(this.smscode)) {
			message.init(false,"验证码不能空",null,"103");
		} else {
			User selectUser = userService.selectByUserId(this.userId);
			if (selectUser == null) {
				message.setMsg("帐号错误");
				message.setSuccess(false);
			} else {

				//验证手机验证码的正确性
				boolean checkRs = loginService.checkSMSCode(message, this.userTelephone, this.smscode);
				if (checkRs) {
					Map<String,Object> map = Maps.newHashMap() ;
					map.put("telephone",this.userTelephone) ;
					map.put("userId",this.userId) ;
					/*List<User> list = userService.queryUserList(map) ;
					if(list != null && !list.isEmpty()) {
						message.init(false,"该手机号已绑定用户,不能重复绑定",null,"104");
					} else {*/
						// 修改用户信息
						selectUser.setUserName(this.userName);
						selectUser.setTelephone(this.userTelephone);
						selectUser.setUserPassword(SecretUtil.encryptToSHA(this.userPassword));
						selectUser.setIsFirstLogin("1");
						userService.update(selectUser);
						message.setMsg("绑定成功,请重新登陆");
						message.setSuccess(true);
					//}

				} else {
					message.setMsg("验证码无效");
					message.setSuccess(false);
				}

			/*if (!selectUser.getUserPassword().equals(SecretUtil.encryptToSHA(this.userPassword))) {
				message.setMsg("密码错误");
				message.setSuccess(false);
			} else {

			}*/
			}
		}
		super.writeJson(message);
	}


	/**
	 * 通过手机号登录
	 */
	public void doNotNeedSessionAndSecurity_loginByTelephone() {
		Message message = new Message();

		if(StringUtils.isBlank(this.userTelephone)) {
			message.init(false,"手机号不能为空",null,"102");
		} else if(StringUtils.isBlank(this.smscode)) {
			message.init(false,"验证码不能空",null,"103");
		} else {
			Map<String,Object> map = Maps.newHashMap() ;
			map.put("telephone",this.userTelephone) ;
			List<User> list = userService.queryUserList(map) ;
			if(list != null && !list.isEmpty()) {

				//验证手机验证码的正确性
				boolean checkRs = loginService.checkSMSCode(message, this.userTelephone, this.smscode);
				if (checkRs) {
					if(list.size()==1) {
						List<User> list2 =userService.queryChoiceUserList(map) ;
						User selectUser = list2.get(0);
						if (StringUtils.isBlank(selectUser.getIsFirstLogin()) || "0".equals(selectUser.getIsFirstLogin().trim())) {
							message.init(false, "当前为首次登陆,请修改密码并绑定手机号", "firstLogin", "201");
						} else {
							//更新最后一次登陆时间
							selectUser.setLastLoginTime((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date()));
							userService.update(selectUser);
							//设置用户角色信息
							String roleIds = "";
							String roleNames = "";
							for (Role r : selectUser.getRoles()) {
								roleIds += r.getRoleId() + ",";
								roleNames += r.getRoleName() + ",";
							}
							if (selectUser.getRoles().size() > 0) {
								roleIds = roleIds.substring(0, roleIds.length() - 1);
								roleNames = roleNames.substring(0, roleNames.length() - 1);
							}
							selectUser.setRoleIds(roleIds);
							selectUser.setRoleNames(roleNames);

							List<Resource> menus = new ArrayList<Resource>();
							List<Resource> grantTreeList = new ArrayList<Resource>();
							// 校友会角色授权时使用该list
							List<Resource> xgrantTreeList = new ArrayList<Resource>();
							if (selectUser.getRoles().size() > 0) {
								menus = resourceService.getMenuByRoles(selectUser.getRoles());
								grantTreeList = resourceService.getResourcesByRoles(selectUser.getRoles());
								//xgrantTreeList = grantTreeList;
							}
							//getSession().put("xgrantTreeList", xgrantTreeList);
					/*getSession().put("grantTreeList", grantTreeList);
					getSession().put("menus", menus);
					getSession().put("user", selectUser);
					message.setSuccess(true);
					message.setReturnId("21");*/
							List<Tree> treeList = Lists.newArrayList();
							List<Tree> tree = Lists.newArrayList();
							if (menus != null && menus.size() > 0) {
								for (Resource resource : menus) {
									if (resource.getType().equals("菜单")) {
										Tree node = new Tree();
										node.setId(resource.getId());
										node.setPid(resource.getPid());
										node.setText(resource.getMenuName());
										node.setIconCls(resource.getIconCls());
										Map<String, String> attributes = new HashMap<String, String>();
										attributes.put("url", resource.getUrl());
										node.setAttributes(attributes);
										treeList.add(node);
									}
								}
								resourceService.parseTree(tree, treeList);
							}
							getSession().put("grantTreeList", grantTreeList);
							getSession().put("menus", menus);
							getSession().put("menuTree", tree);
							getSession().put("user", selectUser);
							message.setSuccess(true);
							message.setReturnId("21");
						}
					}else{
						super.writeJson(list);
					}

				} else {
					message.setSuccess(false);
				}

			} else {
				message.init(false ,"该手机号未绑定账号" ,null);
			}
		}
		super.writeJson(message);
	}

	/**
	 * 通过手机号登录—选择要登录的账号
	 */
	public void doNotNeedSessionAndSecurity_loginByUserId() {
			Message message = new Message();
			Map<String,Object> map = Maps.newHashMap() ;
			map.put("userId",this.userId) ;
			List<User> list = userService.queryChoiceUserList(map) ;
			if(list != null && !list.isEmpty()) {
				User selectUser = list.get(0);
						if (StringUtils.isBlank(selectUser.getIsFirstLogin()) || "0".equals(selectUser.getIsFirstLogin().trim())) {
							message.init(false, "当前为首次登陆,请修改密码并绑定手机号", "firstLogin", "201");
						} else {
							//更新最后一次登陆时间
							selectUser.setLastLoginTime((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date()));
							userService.update(selectUser);
							//设置用户角色信息
							String roleIds = "";
							String roleNames = "";
							for (Role r : selectUser.getRoles()) {
								roleIds += r.getRoleId() + ",";
								roleNames += r.getRoleName() + ",";
							}
							if (selectUser.getRoles().size() > 0) {
								roleIds = roleIds.substring(0, roleIds.length() - 1);
								roleNames = roleNames.substring(0, roleNames.length() - 1);
							}
							selectUser.setRoleIds(roleIds);
							selectUser.setRoleNames(roleNames);

							List<Resource> menus = new ArrayList<Resource>();
							List<Resource> grantTreeList = new ArrayList<Resource>();
							// 校友会角色授权时使用该list
							List<Resource> xgrantTreeList = new ArrayList<Resource>();
							if (selectUser.getRoles().size() > 0) {
								menus = resourceService.getMenuByRoles(selectUser.getRoles());
								grantTreeList = resourceService.getResourcesByRoles(selectUser.getRoles());
								//xgrantTreeList = grantTreeList;
							}
							List<Tree> treeList = Lists.newArrayList();
							List<Tree> tree = Lists.newArrayList();
							if (menus != null && menus.size() > 0) {
								for (Resource resource : menus) {
									if (resource.getType().equals("菜单")) {
										Tree node = new Tree();
										node.setId(resource.getId());
										node.setPid(resource.getPid());
										node.setText(resource.getMenuName());
										node.setIconCls(resource.getIconCls());
										Map<String, String> attributes = new HashMap<String, String>();
										attributes.put("url", resource.getUrl());
										node.setAttributes(attributes);
										treeList.add(node);
									}
								}
								resourceService.parseTree(tree, treeList);
							}
							getSession().put("grantTreeList", grantTreeList);
							getSession().put("menus", menus);
							getSession().put("menuTree", tree);
							getSession().put("user", selectUser);
							message.setSuccess(true);
							message.setReturnId("21");
						}
					}
		super.writeJson(message);
	}

	public void doNotNeedSessionAndSecurity_msgNum(){

		User user = UserUtils.getUser();
		if(user != null && user.getDeptId() != 0){
			Map<String, Object> map = loginService.toDoList(user);
			super.writeJson(map);
		}else{
			super.writeJson(0);
		}

	}

	public boolean isValidateCodeLogin() {
		return isValidateCodeLogin;
	}

	public void setValidateCodeLogin(boolean validateCodeLogin) {
		isValidateCodeLogin = validateCodeLogin;
	}

	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
}
