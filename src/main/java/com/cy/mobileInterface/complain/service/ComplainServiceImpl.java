package com.cy.mobileInterface.complain.service;

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
import com.cy.core.contact.entity.Contact;
import com.cy.core.event.entity.BoardComplaint;
import com.cy.core.event.service.EventService;
import com.cy.mobileInterface.contact.service.ContactServiceImpl;

@Service("mcomplainService")
public class ComplainServiceImpl implements mComplainService {
	private static final Logger logger = Logger.getLogger(ComplainServiceImpl.class);

	
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
			
		//（0=正常，1=投诉处理-花絮正常，2=投诉处理-花絮违规，3=用户自己删除，4=管理员删除）
        String status=map.get("status").toString();//1-未回复，2-已回复     
      //  if(status == null) 已处理数据;
   
        map.put("dept_id", aluid);
        map.put("status", status);                  	    	
        DataGrid<BoardComplaint> dt=eventService.dataGridForComplaint(map);     
        
      
        if(dt.getTotal()==0)
		{
			message.setMsg("记录为空");
			message.setSuccess(false);
			return;
		}
		else {	
			message.setMsg("success");
			message.setSuccess(true);
			message.setObj(dt);
			return;
		}

		
	}

	@Override
	public void getById(Message message, String content) {
		// TODO Auto-generated method stub
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);		
		String boardId = (String)map.get("boardid");  //花蕠ID
		if (StringUtils.isBlank(boardId)) {
			message.setMsg("未传入花蕠ID");
			message.setSuccess(false);
			return;
		}    	
      
		List<String> list=eventService.getPicByBoardId(Long.valueOf(boardId));
		String comment=eventService.getCommentByBoardId(Long.valueOf(boardId));
		
        if(list.size()==0)
		{
			message.setMsg("记录为空");
			message.setSuccess(false);
			return;
		}
		else {	
			message.setMsg(comment);			
			message.setSuccess(true);
			message.setObj(list);
			return;
		}

        
	}

	@Override
	public void delete(Message message, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Message message, String content) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);	
		
		String boardId = (String)map.get("boardid"); //花蕠ID
		if (StringUtils.isBlank(boardId)) {
			message.setMsg("未传入花蕠ID");
			message.setSuccess(false);
			return;
		}    	
      
		
		String handleStatus = (String)map.get("handlestatus"); //处理状态
		map.put("handleStatus", handleStatus);
		map.put("boardId", boardId);
		
		int t=eventService.handleBoardStatus(map);
	
        if(t==0)
		{
			message.setMsg("未处理任何数据");
			message.setSuccess(false);
			return;
		}
		else {	
			message.setMsg("success");
			message.setSuccess(true);
			message.setObj(t);
			return;
		}
	}
	
	
	
}
