package com.cy.core.systemsetting.service;

import java.rmi.MarshalException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.system.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.systemsetting.dao.SystemSettingMapper;
import com.cy.core.systemsetting.entity.SystemSetting;
import com.cy.system.GetDictionaryInfo;

@Service("systemSettingService")
public class SystemSettingServiceImpl implements SystemSettingService {

	@Autowired
	private SystemSettingMapper systemSettingMapper;

	@Override
	public List<SystemSetting> selectAll() {
		return systemSettingMapper.selectAll();
	}

	@Override
	public SystemSetting selectById(long systemId) {
		SystemSetting systemSetting = systemSettingMapper.selectById(systemId);

		if(systemSetting !=null && StringUtils.isNotBlank(systemSetting.getSchoolLogo()) ){
			systemSetting.setSchoolLogo(Global.URL_DOMAIN + systemSetting.getSchoolLogo());
		}
		return systemSetting;
	}

	@Override
	public void update(SystemSetting systemSetting) {
		if(systemSetting.getSchoolLogo() != null ){
			systemSetting.setSchoolLogo(systemSetting.getSchoolLogo().replace(Global.URL_DOMAIN, ""));
		}
		if(systemSetting.getFoundationSignet() != null){
			systemSetting.setFoundationSignet(systemSetting.getFoundationSignet().replace(Global.URL_DOMAIN, ""));
		}
		if(systemSetting.getFoundationLogo() != null){
			systemSetting.setFoundationLogo(systemSetting.getFoundationLogo().replace(Global.URL_DOMAIN, ""));
		}
		systemSettingMapper.update(systemSetting);
		GetDictionaryInfo.getInstance().initSystem();
	}

	@Override
	public void insert(SystemSetting systemSetting) {
		if(systemSetting.getSchoolLogo() != null ){
			systemSetting.setSchoolLogo(systemSetting.getSchoolLogo().replace(Global.URL_DOMAIN, ""));
		}
		if(systemSetting.getFoundationSignet() != null){
			systemSetting.setFoundationSignet(systemSetting.getFoundationSignet().replace(Global.URL_DOMAIN, ""));
		}
		systemSettingMapper.insert(systemSetting);
		GetDictionaryInfo.getInstance().initSystem();
	}

	/**
	 *
	 * 查询声明文本的接口
	 *
	 */
	public void findStatement(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, String> map = JSON.parseObject(content, Map.class);
		String type = map.get("type");

		List<SystemSetting> list = systemSettingMapper.selectAll();

		if(list == null || list.size() < 0){
			message.setMsg("未查询到系统设置参数");
			message.setSuccess(false);
			return;
		}

		if(StringUtils.isBlank(type)){
			message.setMsg("请提供查询的声明类型");
			message.setSuccess(false);
			return;
		}

		String text;

		if("1".equals(type)){
			text = list.get(0).getSignUpText();
		}else if("2".equals(type)){
			text = list.get(0).getDonateText();
		}else{
			message.init(false,"未知的声明类型", null);
			return;
		}

		if(StringUtils.isBlank(text)){
			message.init(false,"声明内容不存在哦",null);
			return;
		}

		message.setObj(text);
		message.setSuccess(true);
		message.setMsg("查询成功");

	}
}
