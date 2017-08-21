package com.cy.mobileInterface.util.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.contact.dao.ContactMapper;
import com.cy.core.event.dao.EventMapper;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;

@Service("mutilService")
public class UtilServiceImpl implements mUtilService {

	
	@Autowired
    private EventMapper eventMapper;
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	
	@Autowired
	private ContactMapper contactMapper;

	
	@Override
	public void CountNewInfo(Message message, String content) {
		// TODO Auto-generated method stub
	  
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, Object> map = JSON.parseObject(content, Map.class);		
		
		String aluid = (String)map.get("aluid"); //校友组织ID
		if (StringUtils.isBlank(aluid)) {
			message.setMsg("未传入校友会组织ID");
			message.setSuccess(false);
			return;
		}
		
		
		String modelkey = (String)map.get("modelkey"); //校友组织ID
		if (StringUtils.isBlank(modelkey)) {
			message.setMsg("未传模块ID");
			message.setSuccess(false);
			return;
		}    		  
      
		Map<String, Object> maptotal = new HashMap<String, Object>();
		
		String[] arrkey=modelkey.split(",");
		
	   for (String key :arrkey) 
		{			 
		        System.out.println(key);
		        
		        switch(key){
		        case "APP_MEMBER_AUDIT":  //人员审核		        
		    	    maptotal.put("APP_MEMBER_AUDIT", appMemberAudit(aluid)) ;		          
		            break;
		        case "APP_MESSAGE_HANDLE":  //留言处理
		        	maptotal.put("APP_MESSAGE_HANDLE",appMessageHandle(aluid)) ;
		            break;
		        case "APP_COMPLAIN_HANDLE":
		        	maptotal.put("APP_COMPLAIN_HANDLE",appComplainHandle(aluid)) ;		            
		            break;
		        default:
		           break;
		        }	     
	     }
		
		message.setMsg("success");
		message.setSuccess(true);
		message.setObj(maptotal);
		return;
	}

	private String appMemberAudit(String aluid)
	{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("aluid", aluid);
		
		map.put("userAlStatus", "10");
		//申请状态（5:邀请加入； 10：待审核；20：审核通过；30：审核失败；40：拒绝加入）	
		long total = userProfileMapper.countByDeptFormAlumni(map);
		
		return String.valueOf(total);
	}
	
	private String appMessageHandle(String aluid)
	{
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alumniId", aluid);		
	     map.put("checkPage", 1);     
	   //申请状态（1:未回复； 2：已回复；）	
		 long total = contactMapper.count(map);	
		
		return String.valueOf(total);
	}
	
	private String appComplainHandle(String aluid)
	{		
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("dept_id", aluid);
        map.put("status", 1);  //1-未回复，2-已回复       
	    long total = eventMapper.countComplaint(map);		
	    
		return String.valueOf(total);
	}
	
}
