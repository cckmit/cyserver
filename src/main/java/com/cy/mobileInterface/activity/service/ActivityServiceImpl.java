package com.cy.mobileInterface.activity.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.event.entity.Event;
import com.cy.core.event.service.EventService;


@Service("mactivityService")
public class ActivityServiceImpl implements mActivityService {

    @Autowired
    private EventService eventService;
    
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
		
	
        map.put("alumniId", aluid);		        
        //map.put("type","5"); // 活动性质： 0=官方，5=校友会 ，9=个人	    
        //map.put("auditStatus", "0");// 审核状态（0=未审核，1=通过，2=不通过）
        map.put("status", "0"); //活动状态（0=正常， 1=取消， 2=删除）	        

    	DataGrid<Event> dataGrid =eventService.dataGrid(map);
	      
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

	@Override
	public void delete(Message message, String content) {
		// TODO Auto-generated method stub
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);		 
		
		String ids = (String)map.get("ids"); 
		if (StringUtils.isBlank(ids)) {
			message.setMsg("未传入ids");
			message.setSuccess(false);
			return;
		}  		
		
        try {
        	eventService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {   
            message.setMsg("删除失败");
            message.setSuccess(false);
         }
        
        return;
	}

	@Override
	public void update(Message message, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Message message, String content) {
		// TODO Auto-generated method stub
		
	}

}
