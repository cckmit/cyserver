package com.cy.mobileInterface.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.alumni.service.AlumniService;
import com.cy.core.contact.entity.Contact;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.service.UserInfoService;

@Service("mmerberService")
public class MemberServiceImpl implements mMemberService{

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private AlumniService alumniService ;
	
	
	//成员列表
	@Override
	public void QueryList(Message message, String content) {
		// TODO Auto-generated method stub
		
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);		
		String aluid = (String)map.get("aluid"); //校友组织ID
		if (StringUtils.isBlank(aluid)) {
			message.setMsg("未传入校友组织ID");
			message.setSuccess(false);
			return;
		}    		  
		
		String page = (String)map.get("page"); 
		if (StringUtils.isBlank(page)) {
			message.setMsg("未传入页码");
			message.setSuccess(false);
			return;
		}   
		
		String rows = (String)map.get("rows"); 
		if (StringUtils.isBlank(rows)) {
			message.setMsg("未传入行数");
			message.setSuccess(false);
			return;
		}   
		
		
		map.put("userAlStatus", 20);//已审核用户
	
    	DataGrid<UserProfile> dataGrid =userInfoService.selectByDeptFormAlumni(map);
      
        if(dataGrid.getTotal() ==0)
		{
			message.setMsg("记录为空");
			message.setSuccess(false);
			return;
		}
		else {
			message.setMsg("success");
			message.setSuccess(true);
			message.setObj(dataGrid);
			return;
		}
	
	}

	@Override
	public void getById(Message message, String content) {
		// TODO Auto-generated method stub
		
	}

	//删除成员
	@Override
	public void mDelete(Message message, String content) {
		// TODO Auto-generated method stub

		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);		
		String aluid = (String)map.get("aluid"); //校友组织ID
		if (StringUtils.isBlank(aluid)) {
			message.setMsg("未传入校友组织ID");
			message.setSuccess(false);
			return;
		}    
		
		String accountNum = (String)map.get("accountNum"); //校友组织ID
		if (StringUtils.isBlank(accountNum)) {
			message.setMsg("未传入校友ID");
			message.setSuccess(false);
			return;
		}    
		
		Message msg =alumniService.kickOutAlumni(aluid, "", accountNum);
		
		message.setMsg(msg.getMsg());
		message.setSuccess(msg.isSuccess());
		return;
	}

	@Override
	public void mUupdate(Message message, String content) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mClean(Message message, String content) {
		// TODO Auto-generated method stub
		
		
	}
	

	@Override
	public void QueryChat(Message message, String content) {
		// TODO Auto-generated method stub
		
	}

}
