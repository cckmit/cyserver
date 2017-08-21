package com.cy.core.docount.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;

import com.cy.core.docount.entity.DoCount;
import com.cy.core.docount.service.DoCountService;

@Namespace("/docount")
@Action(value = "doCountAction")
public class DoCountAction extends AdminBaseAction {
	private static final Logger logger = Logger.getLogger(DoCountAction.class);

	@Autowired
	private DoCountService doCountService;
	
	private DoCount formData;
	

	public void getList() {
		String countType = "0";
		Map<String, Object> map = new HashMap<String, Object>();
		

		map.put("page", page);
		map.put("rows", rows);
		
		//System.out.println("test:"+(formData != null));
		
		if (formData != null)
		{
			if(formData.getLocation() != null && formData.getLocation().length() > 0)
			{
				map.put("location", formData.getLocation());
//				System.out.println("test:"+formData.getLocation());
			}
			
			if(formData.getBackground() != null && formData.getBackground().length() > 0)
			{
				map.put("background", formData.getBackground());
			}
			
			if(formData.getDepartment() != null && formData.getDepartment().length() > 0)
			{
				map.put("department", formData.getDepartment());
			}
			
			if(formData.getJoinYear() != null && formData.getJoinYear().length() > 0)
			{
				map.put("joinYear", formData.getJoinYear());
			}
			
			if(formData.getSpecialty() != null && formData.getSpecialty().length() > 0)
			{
				map.put("specialty", formData.getSpecialty());
			}
			
			if(formData.getSex() != null && formData.getSex().length() > 0)
			{
				map.put("sex", formData.getSex());
			}
			if(formData.getBirthDate() != null && formData.getBirthDate().length() > 0)
			{
				map.put("birthDate", formData.getBirthDate());
			}
			
			if(formData.getSchool() != null && formData.getSchool().length() > 0)
			{
				map.put("school", formData.getSchool());
			}
			
			map.put("majorId", formData.getMajorId());
			
			if(formData.getCountType() != null && formData.getCountType().length() > 0)
			{
				countType = formData.getCountType();
			}
			
			
		}
		

		//choose to count class
		if (countType.equals("1")) {

			super.writeJson(doCountService.countClassByDept(map));
		
		//choose to count college
		} else if (countType.equals("2")) {

			super.writeJson(doCountService.countCollegeByDept(map));
		} else {
			super.writeJson(doCountService.countCollegeByDept(map));
		}
		// else{
		// message.setSuccess(true);
		// message.setMsg("successful");
		// super.writeJson(message);
		// }

	}
	
	public void doNotNeedSessionAndSecurity_getAllProvince(){
//		List<DoCount> list = new ArrayList();
//		DoCount defaultDo = new DoCount();
//		
//		defaultDo.setProvinceId("");
//		defaultDo.setProvinceName("    ");
		
//		list = doCountService.getAllProvince();
//		list.add(0, defaultDo);
		super.writeJson(doCountService.getAllProvince());
	}
	
	public void doNotNeedSessionAndSecurity_getAllCollege(){
		
		super.writeJson(doCountService.getAllCollege());
		
	}
	
	public void doNotNeedSessionAndSecurity_getAllAge(){
		
		super.writeJson(doCountService.getAllAge());
		
	}

	public void doNotNeedSessionAndSecurity_getAllClass(){
		
		super.writeJson(doCountService.getAllClass());
		
	}
	
	public void doNotNeedSessionAndSecurity_getAllBirthDate(){
		
		super.writeJson(doCountService.getAllBirthDate());
		
	}
	
	public DoCount getFormData() {
		return formData;
	}

	public void setFormData(DoCount formData) {
		this.formData = formData;
	}
}
