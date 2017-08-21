package com.cy.core.backschoolOnlineSign.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.backschoolOnlineSign.entity.BackschoolOnlineSign;
import com.cy.core.backschoolOnlineSign.service.BackschoolOnlineSignService;
import freemarker.template.utility.StringUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 返校在线报名功能
 * 
 * @author Administrator
 * 
 */
@Namespace("/backschoolOnlineSign")
@Action(value = "backschoolOnlineSignAction")
public class BackschoolOnlineSignAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BackschoolOnlineSignAction.class);

	private BackschoolOnlineSign backschoolOnlineSign;

	@Autowired
	private BackschoolOnlineSignService backschoolOnlineSignService;

	public void save() {
		Message message = new Message();
		try {
			backschoolOnlineSignService.save(backschoolOnlineSign);
			message.setMsg("保存成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getList() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("rows", rows);
			if (backschoolOnlineSign != null) {
				if (StringUtils.isNotBlank(backschoolOnlineSign.getName())) {
					map.put("name", backschoolOnlineSign.getName());
				}
				if (backschoolOnlineSign.getStatus() > 0) {
					map.put("status", backschoolOnlineSign.getStatus());
				}
			}
			super.writeJson(backschoolOnlineSignService.dataGrid(map));
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	public void doNotNeedSecurity_getById() {
		super.writeJson(backschoolOnlineSignService.selectById(backschoolOnlineSign.getId()));
	}

	public void update() {
		Message message = new Message();
		try {
			backschoolOnlineSignService.update(backschoolOnlineSign);
			message.setMsg("保存成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			backschoolOnlineSignService.deletion(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public BackschoolOnlineSign getBackschoolOnlineSign() {
		return backschoolOnlineSign;
	}

	public void setBackschoolOnlineSign(BackschoolOnlineSign backschoolOnlineSign) {
		this.backschoolOnlineSign = backschoolOnlineSign;
	}

	public BackschoolOnlineSignService getBackschoolOnlineSignService() {
		return backschoolOnlineSignService;
	}

	public void setBackschoolOnlineSignService(BackschoolOnlineSignService backschoolOnlineSignService) {
		this.backschoolOnlineSignService = backschoolOnlineSignService;
	}
}
