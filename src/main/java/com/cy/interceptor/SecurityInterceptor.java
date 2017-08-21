package com.cy.interceptor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cy.common.utils.CacheUtils;
import com.cy.core.resource.entity.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.cy.core.role.entity.Role;
import com.cy.core.user.entity.User;
import com.cy.util.WebUtil;

/**
 * 权限拦截器
 * 
 */
@SuppressWarnings("all")
public class SecurityInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);

	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {

		ActionContext ctx = actionInvocation.getInvocationContext();
		String actionName = ctx.getName();
		if (actionName.equals("messageBoardAction") || actionName.equals("servAction")||actionName.equals("contactAction")) {
			return actionInvocation.invoke();
		}
		User user = (User) ActionContext.getContext().getSession().get("user");

		Map<String, Object> map = (Map<String, Object>) CacheUtils.get("authority");
		Map<String, Resource> menuUrlMap = (Map<String, Resource>) CacheUtils.get("menuUrl");
		String servletPath = ServletActionContext.getRequest().getServletPath();

		servletPath = StringUtils.substringBeforeLast(servletPath, ".");// 去掉后面的后缀
																		// *.action之类的
		logger.info("进入权限拦截器->访问的资源为：[" + servletPath + "]");

		// 是否通过
		boolean isPass = false;

//		if (user.getRole().getSystemAdmin() == 1) {
		/*if(user.getUserRoles().size()==1){
			if (user.getUserRoles().get(0).getRole().getSystemAdmin() == 1) {
				// 管理员用户system登录
				return actionInvocation.invoke();
			} else {
				// 普通管理员用户登录
				List<Role> roles = (List<Role>) map.get(servletPath);
				if (roles != null && roles.size() > 0) {
					logger.info("资源需要的角色" + roles.toString());
					logger.info("当前用户拥有的角色" + user.getUserRoles().get(0).getRole().toString());
					for (Role role2 : roles) {
						if (user.getUserRoles().get(0).getRole().getRoleName().equals(role2.getRoleName())) {
							isPass = true;
							break;
						}
					}// end for2
				}
			}
		} else {*/
			// 普通管理员用户登录
			List<Role> roles = (List<Role>) map.get(servletPath);
			for (int i = 0; i < user.getUserRoles().size(); i++) {
				boolean flag = false;
				if (roles != null && roles.size() > 0) {
					logger.info("资源需要的角色" + roles.toString());
					logger.info("当前用户拥有的角色" + user.getUserRoles().get(i).getRole().toString());
					for (Role role2 : roles) {
						if (user.getUserRoles().get(i).getRole().getRoleId()==role2.getRoleId()) {
							isPass = true;
							flag = true;
							break;
						}
					}// end for2
					if(flag){
						break;
					}
				}
			}
			
//		}
		
		if (isPass) {
			return actionInvocation.invoke();
		} else {
			String errMsg = "您没有访问此功能的权限！请联系管理员给你赋予相应权限。";
			logger.info(errMsg);
			ServletActionContext.getRequest().setAttribute("msg", errMsg);
			return "noSecurity";
		}
	}

}
