package com.cy.core.majormng.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.majormng.entity.MajorMng;
import com.cy.core.majormng.service.MajorMngService;

@Namespace("/majormng")
@Action(value = "majorMngAction")
public class MajorMngAction extends AdminBaseAction {
	private static final Logger logger = Logger.getLogger(MajorMngAction.class);

	@Autowired
	private MajorMngService mmService;
	
	private MajorMng formData;
	

	public void getList() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (formData != null){
			if(formData.getSpecialtyName() != null && formData.getSpecialtyName().length() > 0){
				map.put("specialtyName", formData.getSpecialtyName());
			}
		}
		

		map.put("page", page);
		map.put("rows", rows);

		super.writeJson(mmService.dataGrid(map));

	}
	
	public void delete() {
        Message message = new Message();
        try {
        	mmService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
	
	
	/**
	 * 专业收集（APP端使用）
	 * 
	 * 
	 */
	public void doNotNeedSessionAndSecurity_specialtyCollectionForApp() 
	{
		
		Message message = new Message();
		try 
		{
			mmService.specialtyCollectionForApp(formData);
			message.setMsg("操作成功");
            message.setSuccess(true);
		} 
		catch (Exception e) 
		{
			logger.error(e, e);
            message.setMsg("操作失败");
            message.setSuccess(false);
		}
		super.writeJson(message);
	}
	
	
	public MajorMng getFormData() {
		return formData;
	}

	public void setFormData(MajorMng formData) {
		this.formData = formData;
	}
}
