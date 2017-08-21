package com.cy.tag;

import java.util.List;
import java.util.Objects;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cy.core.role.service.RoleService;
import com.cy.system.SpringManager;
import org.apache.log4j.Logger;

import com.cy.core.role.entity.Role;
import com.cy.core.user.entity.UserRole;
import com.cy.system.GetDictionaryInfo;

public class AuthorityTag extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AuthorityTag.class);

	private static final long serialVersionUID = 1L;

	private List<UserRole> userRoles;
	private String authorizationCode;

	@Override
	public int doStartTag() throws JspException {
		boolean flag = false;
		if (userRoles.size() != 0) {
			/*if (userRoles.size() == 1) {
				if (userRoles.get(0).getRole().getSystemAdmin() == 1) {
					flag = true;
				} else {
					@SuppressWarnings("unchecked")
					List<Role> list = (List<Role>) GetDictionaryInfo.authorityMap.get(authorizationCode);
					if (list != null && list.size() > 0) {
						logger.info("授权码拥有的角色" + list.toString());
						logger.info("当前角色" + userRoles.get(0).getRole().toString());
						for (Role role : list) {
							if (role.getRoleName().equals(this.userRoles.get(0).getRole().getRoleName())) {
								flag = true;
								break;
							}
						}
					}
				}
			} else {*/
				@SuppressWarnings("unchecked")
				//List<Role> list = (List<Role>) GetDictionaryInfo.authorityMap.get(authorizationCode);

				List<Role> list = SpringManager.getBean("roleService", RoleService.class).getRolesByAction(authorizationCode);
				if (list != null && list.size() > 0) {
					logger.info("授权码拥有的角色" + list.toString());
					//logger.info("当前角色" + userRoles.get(0).getRole().toString());
					for (Role role : list) {
						boolean flag2 = false;
						for (int i = 0; i < userRoles.size(); i++) {
							if (role.getRoleId()==this.userRoles.get(i).getRole().getRoleId()) {
								flag = true;
								flag2 = true;
								break;
							}
						}
						if (flag2) {
							break;
						}
					}
				}
//			}
		}
		if (flag) {
			return EVAL_BODY_INCLUDE;
		} else {
			return super.doStartTag();
		}
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

}
