package com.cy.core.systemsetting.action;

import java.util.List;

import com.cy.system.Global;
import org.apache.log4j.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.systemsetting.entity.SystemSetting;
import com.cy.core.systemsetting.service.SystemSettingService;
import com.cy.system.GetDictionaryInfo;

@Namespace("/systemSetting")
@Action("systemSettingAction")
public class SystemSettingAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SystemSettingAction.class);

	@Autowired
	private SystemSettingService systemSettingService;

	private SystemSetting systemSetting;

	public void initSystemSetting() {
		List<SystemSetting> list = systemSettingService.selectAll();
		if (list != null && list.size() > 0) {
			systemSetting = list.get(0);
		} else {
			systemSetting = new SystemSetting();
		}
		//System.out.println(Global.web_homepage_api_url);
		super.writeJson(systemSetting);
	}

	public void save() {
		Message message = new Message();
		try {

			List<SystemSetting> list = systemSettingService.selectAll();
			if (list != null && list.size() > 0) {
				systemSetting.setSystemId(list.get(0).getSystemId());
			}

			if (systemSetting.getSystemId() != 0) {
				systemSettingService.update(systemSetting);
			} else {
				systemSettingService.insert(systemSetting);
			}
			GetDictionaryInfo.getInstance().initSystem();
			message.setMsg("保存成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public SystemSetting getSystemSetting() {
		return systemSetting;
	}

	public void setSystemSetting(SystemSetting systemSetting) {
		this.systemSetting = systemSetting;
	}

}
