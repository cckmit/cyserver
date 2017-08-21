package com.cy.mobileInterface.contact.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.contact.dao.ContactMapper;
import com.cy.core.contact.entity.Contact;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.util.WebUtil;

@Service("mcontactService")
public class ContactServiceImpl  implements mContactService {
	private static final Logger logger = Logger.getLogger(ContactServiceImpl.class);
	
	@Autowired
	private ContactMapper contactMapper;

	
	//查询留言管理
    public void QueryList(Message message, String content) {
    	
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
        String checkpage=map.get("checkpage").toString();//1-未回复，2-已回复                
        int pg=Integer.valueOf(map.get("page").toString())-1;
        int rows=Integer.valueOf(map.get("rows").toString());
               
 
        int start =pg * rows;   
        map.put("start", start);
        map.put("rows", rows);
        
        map.put("category", "2");
        map.put("alumniId", aluid);
        map.put("checkPage", checkpage);        
               
        DataGrid<Contact> dt = new DataGrid<Contact>();
        long total = contactMapper.count(map);
        dt.setTotal(total);
                
        List<Contact>  list=contactMapper.querylist(map);  
        
      
        if(list == null)
		{
			message.setMsg("记录为空");
			message.setSuccess(false);
			return;
		}
		else {
			dt.setRows(list);
			message.setMsg("success");
			message.setSuccess(true);
			message.setObj(dt);
			return;
		}

       
    }
    
    
    public void getById(Message message, String content) {

    	if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);	
		
		String id = (String)map.get("id"); //留言ID
		if (StringUtils.isBlank(id)) {
			message.setMsg("未传入留言ID");
			message.setSuccess(false);
			return;
		}    		       
		
         if(WebUtil.isNumeric(id))  
         {
	           Contact ct= contactMapper.getByleaveId(Long.valueOf(id));
	       
			   if(ct == null)
				{
					message.setMsg("记录为空");
					message.setSuccess(false);
					return;
				}
				else {
					message.setMsg("success");
					message.setSuccess(true);
					message.setObj(ct);
					return;
				}
		   
         }
         else {
        		message.setMsg("ID为非法字符");
				message.setSuccess(false);
				return;
			}
         
        
    }


    public void reply(Message message, String content) {
    	
    	if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);	
		
		String id = (String)map.get("id"); //留言ID
		
		if (StringUtils.isBlank(id)) {
			message.setMsg("未传入留言ID");
			message.setSuccess(false);
			return;
		}    		       
		
		
		Contact ct=new Contact();
		
        if(WebUtil.isNumeric(id))  
        {
        	
			String replyUserId = (String)map.get("replayuserid");
			String replyContent = (String)map.get("replaycontent");
			
	        if (replyContent == null)
	        {
	    		message.setMsg("回复内容为空");
				message.setSuccess(false);
				return;
			}else
			{
			  
		  	   ct.setId(Long.valueOf(id));
			   ct.setReplyUserId(Long.valueOf(replyUserId));
			   ct.setReplyContent(replyContent);
			  				
	           int t=contactMapper.reply(ct);
	           if(t == 0)
				{
					message.setMsg("更新失败");
					message.setSuccess(false);
					return;
				}
				else {
					message.setMsg(String.valueOf(t));
					message.setSuccess(true);						
					return;
				}   
			}
        }
	    else {
			message.setMsg("ID为非法字符");
			message.setSuccess(false);
			return;
		}
	    
    
    }


    public void delete(String ids) {
    	String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		contactMapper.delete(list);
    }
    
}
