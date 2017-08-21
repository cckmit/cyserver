package com.cy.core.mobevent.action;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;


import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;

import sun.misc.BASE64Decoder;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.mobevent.entity.CyEvent;
import com.cy.core.mobevent.entity.CyEventBoard;
import com.cy.core.mobevent.entity.CyEventBoardComment;
import com.cy.core.mobevent.entity.CyEventBoardComplaint;
import com.cy.core.mobevent.entity.CyEventBoardPic;
import com.cy.core.mobevent.entity.CyEventBoardPraise;
import com.cy.core.mobevent.entity.CyEventSign;
import com.cy.core.mobevent.service.MobEventService;
import com.cy.system.Global;
import com.cy.util.WebUtil;



@Namespace("/mobile/event")
@Action(value = "mobEventAction")
public class MobEventAction extends AdminBaseAction
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobEventAction.class);

	@Autowired
	private MobEventService service;
 
	
	private CyEvent eventData;
	
	private CyEventSign eventSignData;
	
	private CyEventBoard eventBoard;
	
	private CyEventBoardPic eventBoardPic;
	
	private CyEventBoardComment eventBoardComment;
	
	private CyEventBoardComplaint eventBoardComplaint;
	
	private CyEventBoardPraise eventBoardPraise;
	
	private String jsonStr;
	
	private File upload;
	
	private File[] uploadArr;
	
	/**--小图尺寸--**/
	private static final String MIN_IMG_SIZE = "100*80"; 
	
	/**--大图尺寸--**/
	private static final String MAX_IMG_SIZE = "320*200";
	
	public void doNotNeedSessionAndSecurity_getEventBoardList()
	{	
		String userInfoId = eventBoard.getUserInfoId();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("countEventBoard", service.countEventBoard(eventBoard));
		eventBoard.setUserInfoId(userInfoId);
		response.put("eventBoardList", service.getEventBoardList(eventBoard));
		super.writeJson(response);
	}
	
	
	public void doNotNeedSessionAndSecurity_getEventBoardCommentList()
	{	
		if(eventBoardComment == null)
		{
			eventBoardComment = new CyEventBoardComment();
			eventBoardComment.setBoardId(eventBoard.getId());
		}
		else
		{
			eventBoardComment.setBoardId(eventBoard.getId());
		}
		
		super.writeJson(service.getEventBoardCommentListWithEventBoard(eventBoard, eventBoardComment));
	}
	
	
	public void doNotNeedSessionAndSecurity_eventBoardPraise()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			service.eventBoardPraise(eventBoardPraise);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		super.writeJson(response);
	}
	
	
	public void doNotNeedSessionAndSecurity_eventBoardComment()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			service.insertEventBoardComment(eventBoardComment);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		super.writeJson(response);
	}
	
	
	
	public void doNotNeedSessionAndSecurity_eventBoardComplaint()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			long complaintCount = service.countEventBoardComplaint(eventBoardComplaint);
			
			if(complaintCount > 0)
			{
				response.put("status", false);
			}
			else
			{
				service.insertEventBoardComplaint(eventBoardComplaint);
				response.put("status", true);
			}
			
			
			
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		super.writeJson(response);
	}
	
	
	
	public void doNotNeedSessionAndSecurity_deleteEventBoardComment()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			service.deleteEventBoardComment(eventBoardComment);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		super.writeJson(response);
	}
	
	
	public void doNotNeedSessionAndSecurity_deleteEventBoard()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			service.deleteEventBoard(eventBoard);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		super.writeJson(response);
	}
	
	
	public void doNotNeedSessionAndSecurity_deleteEvent()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		try
		{
			service.deleteEvent(eventData);
			response.put("status", true);
		}
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		super.writeJson(response);
	}
	
	
	public void doNotNeedSessionAndSecurity_getEventDetail()
	{
		String eventId = eventSignData.getEventId();
		String userInfoId = eventSignData.getUserInfoId();
		
		//logger.info("eventId=" + eventId);
		//logger.info("userInfoId=" + userInfoId);
		
		super.writeJson(service.getEventDetail(eventId, userInfoId));
	}
	
	
	public void doNotNeedSessionAndSecurity_getEventContent()
	{
		String eventId = eventData.getId();
		//String userInfoId = eventSignData.getUserInfoId();
		
		CyEvent cyEvent = service.getEventById(eventId);
		
		String eventContent = "";
		
		Map<String, String> map = new HashMap<String, String>();
		
		if(cyEvent != null && WebUtil.isEmpty(cyEvent.getContent()) == false)
		{
			eventContent = cyEvent.getContent();
			
		}
		
		map.put("eventContent", WebUtil.encodeUTF8(eventContent));
		
		
		super.writeJson(map);
	}
	
	
	public void doNotNeedSessionAndSecurity_getEventSignList()
	{
		String eventId = eventSignData.getEventId();
//		String userInfoId = eventSignData.getUserInfoId();
		
		//logger.info("eventId=" + eventId);
		//logger.info("userInfoId=" + userInfoId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eventId", eventId);
		//map.put("userInfoId", userInfoId);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("lists", service.getEventSignList(map));
		super.writeJson(response);
	}
	
	
	public void doNotNeedSessionAndSecurity_pullEventSignList()
	{
		logger.info("eventSignData=" + eventSignData);
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("cyEvent", service.getEventById(eventSignData.getEventId()));
		response.put("lists", service.pullEventSignList(eventSignData));
		super.writeJson(response);
	}
	
	public void doNotNeedSessionAndSecurity_getDicts()
	{
		
		String dictTypeName = getRequest().getParameter("dictTypeName")==null?"":getRequest().getParameter("dictTypeName");
		
		//logger.info("dictTypeName=" + dictTypeName);
		
		try {
			dictTypeName = URLDecoder.decode(dictTypeName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			dictTypeName = "";
			
		}
		
		//logger.info("dictTypeName=" + dictTypeName);
		
		super.writeJson(service.getDicts(dictTypeName));
	}
	
	public void doNotNeedSessionAndSecurity_getNumOfNotifyForMyEvents()
	{
		
		String userInfoId = getRequest().getParameter("userInfoId")==null?"":getRequest().getParameter("userInfoId");
		
		//logger.info("userInfoId=" + userInfoId);
		
		
		super.writeJson(service.getNumOfNotifyForMyEvents(userInfoId));
	}
	
	public void doNotNeedSessionAndSecurity_countEventSign()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		
		long countEventSign = service.countEventSign(eventSignData);
		
		String eventId = eventSignData.getEventId();
		CyEvent event = service.getEventById(eventId);
		
		if(		event != null && 
				eventSignData != null && 
				!WebUtil.isEmpty(eventSignData.getUserInfoId()) && 
				!WebUtil.isEmpty(event.getUserInfoId()) && 
				eventSignData.getUserInfoId().equals(event.getUserInfoId()))
		{
			countEventSign = countEventSign + 1;
		}
		
		
		
		
		if(event != null)
		{
			try
			{
				Date smdate = new Date();
				Date bdate = event.getStartTime();
				
				if(WebUtil.daysBetween(smdate, bdate) > 0)
				{
					countEventSign = -1;
				}
			}
			catch(Exception e)
			{
				logger.error("", e);
				countEventSign = -1;
			}
			
		}
		
		
		
		
		response.put("countEventSign", countEventSign);
		super.writeJson(response);
	}
	
	public void doNotNeedSessionAndSecurity_updateEventSign()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		try
		{
			String eventId = eventSignData.getEventId();
			String userInfoId = eventSignData.getUserInfoId();
			
			
			
			//logger.info("eventId=" + eventId);
			//logger.info("userInfoId=" + userInfoId);

			CyEventSign eventSign = new CyEventSign();
			eventSign.setEventId(eventId);
			eventSign.setUserInfoId(userInfoId);
			
			
			long signCount = service.countEventSign(eventSign);
			
			if(signCount > 0)
			{
				eventSign.setViewNotification(1);
				
				service.updateEventSign(eventSign);
				
				response.put("status", true);
			}
			else
			{
				response.put("status", false);
			}
		} 
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		
		
		
		super.writeJson(response);
	}
	
	public void doNotNeedSessionAndSecurity_eventSign()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		try
		{
			String eventId = eventSignData.getEventId();
			String userInfoId = eventSignData.getUserInfoId();
			
			
			
			//logger.info("eventId=" + eventId);
			//logger.info("userInfoId=" + userInfoId);

			CyEventSign eventSign = new CyEventSign();
			eventSign.setEventId(eventId);
			eventSign.setUserInfoId(userInfoId);
			if(eventData == null)
			{
				eventData = new CyEvent();
			}
			eventData.setId(eventId);
			
			long signCount = service.verifyEventSignInCode(eventData);
			
			if(signCount > 0)
			{
				eventSign.setIsSignIn(1);
				
				service.updateEventSign(eventSign);
				
				response.put("status", true);
			}
			else
			{
				response.put("status", false);
			}
		} 
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}
		
		
		
		
		super.writeJson(response);
	}
	
	
	
	public void doNotNeedSessionAndSecurity_eventJoin()
	{
		Map<String, Object> response = new HashMap<String, Object>();
		try
		{
			String eventId = eventSignData.getEventId();
			String userInfoId = eventSignData.getUserInfoId();

			//logger.info("eventId=" + eventId);
			//logger.info("userInfoId=" + userInfoId);

			CyEventSign eventSign = new CyEventSign();
			eventSign.setEventId(eventId);
			eventSign.setUserInfoId(userInfoId);
			
			
			long signCount = service.countEventSign(eventSign);
			
			eventSign.setUserInfoId(null);
			long currentSignCount = service.countEventSign(eventSign);
			
			CyEvent event = service.getEventById(eventId);
			
			long signMaxCount = 0;
			
			if(event != null)
			{
				signMaxCount = event.getMaxPeople();
			}
			
			if(signCount == 0 && (currentSignCount < signMaxCount || signMaxCount == 0))
			{
				eventSign.setUserInfoId(userInfoId);
				eventSign.setSignupTime(new Date());
				eventSign.setViewNotification(1);

				service.insertEventSign(eventSign);
				
				response.put("status", true);
			}
			else
			{
				response.put("status", false);
			}
		} 
		catch (Exception e)
		{
			logger.error(e, e);
			response.put("status", false);
		}

		super.writeJson(response);
	}
	
	
	public void doNotNeedSessionAndSecurity_pullEventList()	{		
		
//		String accountNum = getRequest().getParameter("accountNum")==null?"":getRequest().getParameter("accountNum");
		
//		eventData.setUserInfoId(accountNum);
		

		Map<String, Object> response = new HashMap<String, Object>();
		
		List<CyEvent> tmpList = null;
		
		try{
			
			if((eventData.getRegion()==null || eventData.getRegion().equals("")) && eventData.getType()==5){
				String regionStr = service.getRegionFromAlumniByAccountNum(eventData);
				
				eventData.setRegion(regionStr);	
			}
			
			logger.info("eventData=" + eventData);
			
			tmpList = service.listMobEvens(eventData);
			
			if(tmpList != null){
				
				String basePath = getRequest().getScheme() + "://" +
						 getRequest().getServerName() + ":" +
						 getRequest().getServerPort() + getRequest().getContextPath();
				
				
				Date tmpdate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE MM/dd HH:mm",Locale.CHINA);
				
				for (CyEvent cyEvent : tmpList) {
//					logger.info("cyEvent=" + cyEvent.toString());
					if(cyEvent.getStartTime() != null){
						cyEvent.setStartTimeStr(sdf.format(cyEvent.getStartTime()).replaceFirst("星期", "周"));
					}else{
						cyEvent.setStartTimeStr("");
					}
					
					if(cyEvent.getAuditStatus()==0){
						cyEvent.setEventStatus(0);//未审核
						
					}
//					else if(cyEvent.getAuditStatus()==1){
//						cyEvent.setEventStatus(1);//已审核
//						
//					}
					else if(cyEvent.getAuditStatus()==2){
						cyEvent.setEventStatus(2);//审核未通过
						
					}else if(cyEvent.getStatus()==1){
						cyEvent.setEventStatus(3);//活动已取消
						
					}else if(tmpdate.before(cyEvent.getStartTime()) && tmpdate.before(cyEvent.getSignupStartTime()) ){
						cyEvent.setEventStatus(4);//活动未开始
						
					}else if( tmpdate.after(cyEvent.getStartTime()) && tmpdate.before(cyEvent.getEndTime()) ){
						cyEvent.setEventStatus(5);//活动进行中
						
					}else if( tmpdate.after(cyEvent.getEndTime()) ){
						cyEvent.setEventStatus(6);//活动已截至
						
					}else if(tmpdate.after(cyEvent.getSignupStartTime()) && tmpdate.before(cyEvent.getSignupEndTime())){
						cyEvent.setEventStatus(7);//报名进行中
						
					}else if(tmpdate.after(cyEvent.getSignupEndTime())){
						cyEvent.setEventStatus(8);//报名已截至
						
					}else{
						cyEvent.setEventStatus(-1);
					}						
					
				
					cyEvent.setLink("eventDetail.jsp?eventId="+cyEvent.getId()+"&userInfoId="+eventData.getUserInfoId());
				
					if(cyEvent.getPic() == null || cyEvent.getPic().equals("")){
						cyEvent.setPic(basePath + "/mobile/img/nopic.png");
					}
				}
			}
			
			response.put("lists", tmpList);
			
			if(eventData.getCurrentRow()==0){
				response.put("countNum", service.listMobEvensNum(eventData));
			}
		}catch(Exception e){
			logger.error(e, e);
			Message message = new Message();
			message.setMsg(e.toString());
			message.setSuccess(false);
			response.put("lists", message);
			super.writeJson(response);
		}
		
		super.writeJson(response);
	}
	
	public void doNotNeedSessionAndSecurity_saveMobEvent() {
		
		boolean isWlight = false;
		
		String tmpPicStr = "";
		
		try{
			isWlight = Boolean.parseBoolean(getRequest().getParameter("isWlight"));
		}catch(Exception e){
			isWlight = false;
		}
		
		
		Message message = new Message();
		try {
			
			String picFileName = getRequest().getParameter("pic");
			
			if(!isWlight){
				if (WebUtil.isEmpty(jsonStr)) {
					message.setMsg("jsonStr is empty!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}

				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				if (jsonObject == null) {
					message.setMsg("jsonStr格式错误!");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}
				
//				tmpPicStr = eventData.getPic();
				
				eventData = (CyEvent)JSONObject.toBean(jsonObject, CyEvent.class);
				eventData.setPic("");
				
				picFileName = eventData.getLink();
				
				String pattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				
				eventData.setStartTime(sdf.parse(eventData.getStartTimeStr()));
				eventData.setEndTime(sdf.parse(eventData.getEndTimeStr()));
				eventData.setSignupStartTime(sdf.parse(eventData.getSignupStartTimeStr()));
				eventData.setSignupEndTime(sdf.parse(eventData.getSignupEndTimeStr()));
				
//				if(tmpPicStr != null && !tmpPicStr.equals("")){
//					tmpPicStr = URLDecoder.decode(tmpPicStr,"UTF-8");
//					eventData.setPic(tmpPicStr);
//				}
			}
			
			
			eventData.setCreateTime(new Date());
			
			logger.info("eventData="+eventData.toString());
			
			HashMap<String,String> fileMap = checkFileFolderAndCreateIfNotExsit();
			
			String savePath = fileMap.get("savePath");
			String saveUrl = fileMap.get("saveUrl");
			
			
			if(picFileName != null && eventData.getPic() != null && picFileName.lastIndexOf(".") != -1){
				
				picFileName = "mob" + eventData.getCreateTime().getTime() + new Random().nextInt(1000) + picFileName.substring(picFileName.lastIndexOf("."));
				
				if(!isWlight){
					
					if(GenerateImageForMob(upload, savePath + picFileName)){
						
						logger.info("Success pic=" + savePath + picFileName + " and pic url="+ saveUrl + picFileName);
						
						eventData.setPic(saveUrl + picFileName);
						
					}else{
						logger.info("Failure pic=" + savePath + picFileName + " and pic url="+ saveUrl + picFileName);
						eventData.setPic("");
					}
					
				}else{
					tmpPicStr = eventData.getPic().substring(eventData.getPic().indexOf("base64,")+7);
					
					if(GenerateImage(tmpPicStr, savePath + picFileName)){
						
						logger.info("Success pic=" + savePath + picFileName + " and pic url="+ saveUrl + picFileName);
						
						eventData.setPic(saveUrl + picFileName);
						
					}else{
						logger.info("Failure pic=" + savePath + picFileName + " and pic url="+ saveUrl + picFileName);
						eventData.setPic("");
					}
				}
				
				
				
			}else{
				logger.info("Failure pic=" + savePath + picFileName + " and pic url="+ saveUrl + picFileName);
				eventData.setPic("");
			}
			
			//type=9 个人
			eventData.setType(9);
			
			if(eventData.getNeedSignIn()==1){
				eventData.setSignInCode(generateSignInCode());
			}
			
			service.saveMobEvent(eventData);
			
			message.setMsg("活动提交成功,请等待审批");
			message.setSuccess(true);
			super.writeJson(message);
			return;

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("创建活动失败");
			message.setSuccess(false);
			logger.info("eventData="+eventData.toString());
			super.writeJson(message);
			return;
		}
		
	}
	
	public void doNotNeedSessionAndSecurity_saveMobEventBoard() {
		Message message = new Message();
		
		try {
		
			if (WebUtil.isEmpty(jsonStr)) {
				message.setMsg("jsonStr is empty!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}
	
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			if (jsonObject == null) {
				message.setMsg("jsonStr格式错误!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}
			
			eventBoard = (CyEventBoard)JSONObject.toBean(jsonObject, CyEventBoard.class);
			
			eventBoard.setCreateTime(new Date());
			
			service.saveMobEventBoard(eventBoard);
			
			message.setMsg("创建活动花絮成功");
			message.setSuccess(true);
			message.setReturnId(String.valueOf(eventBoard.getId()));
			super.writeJson(message);
			return;
		}catch(Exception e){
			logger.error(e, e);
			message.setMsg("创建活动花絮失败");
			message.setSuccess(false);
			logger.info("eventBoard="+eventBoard.toString());
			super.writeJson(message);
			return;
		}
		
	}
	
	public void doNotNeedSessionAndSecurity_saveMobEventBoardPic() {
		Message message = new Message();
		
		boolean isSuc = false;
		
		String tmpMsg = "提示信息";
		
		try{
		
			if (WebUtil.isEmpty(jsonStr)) {
				message.setMsg("jsonStr is empty!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}
	
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			if (jsonObject == null) {
				message.setMsg("jsonStr格式错误!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}
			
			eventBoardPic = (CyEventBoardPic)JSONObject.toBean(jsonObject, CyEventBoardPic.class);
			logger.info("eventBoardPic="+eventBoardPic.toString());
			String[] picFileName = eventBoardPic.getPicStr().split(",");
			eventBoardPic.setPicStr("");
			
			Date tmpDate = new Date();
			
			HashMap<String,String> fileMap = checkFileFolderAndCreateIfNotExsit();
			
			String savePath = fileMap.get("savePath");
			String saveUrl = fileMap.get("saveUrl");
			
			
			for (int i = 0; i < picFileName.length; i++) {
				
				logger.info("picname="+picFileName[i]);
				
				if(picFileName[i] != null && picFileName[i].lastIndexOf(".") != -1){
					
					picFileName[i] = "mob" + tmpDate.getTime() + new Random().nextInt(1000) + picFileName[i].substring(picFileName[i].lastIndexOf("."));
					
					if(i < uploadArr.length){
						if(GenerateImageForMob(uploadArr[i], savePath + picFileName[i])){
							
							logger.info("Success pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
							
							eventBoardPic.setPic(saveUrl + picFileName[i]);
							
						}else{
							logger.info("Failure pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
							tmpMsg += ";花絮图片上传失败:"+picFileName[i];
//							message.setSuccess(false);
							continue;
						}
					}else{
						logger.info("Failure pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
						tmpMsg += ";找不到对应的花絮图片对象:"+picFileName[i];
//						message.setSuccess(false);
						continue;
					}
					
					
					
				}else{
					tmpMsg += ";花絮图片文件名不正确:"+picFileName[i];
//					message.setSuccess(false);
					continue;
				}
				
				
				service.saveMobEventBoardPic(eventBoardPic);
				tmpMsg += ";花絮图片上传成功:"+picFileName[i];
//				message.setSuccess(true);
				isSuc = true;
			}
						
			
		}catch(Exception e){
			logger.error(e, e);
			message.setMsg("发生错误,上传花絮图片失败");
			message.setSuccess(false);
			logger.info("eventBoardPic="+eventBoardPic.toString());
			super.writeJson(message);
			return;
		}
		
		if(!isSuc){
			tmpMsg = "上传花絮图片失败";
		}
		
		message.setMsg(tmpMsg);
		message.setSuccess(isSuc);
		
		super.writeJson(message);
		return;
		
	}
	
	public void doNotNeedSessionAndSecurity_saveMobEventBoardAndPic() {
		
		Message message = new Message();
		
		Date tmpDate = new Date();
		
		boolean isSuc = false;
		
		String tmpPicName = "";
		
		int numOfPic = 0;
		
		JSONObject jsonObjectPut = new JSONObject();
		
		try {
		
			if (WebUtil.isEmpty(jsonStr)) {
				message.setMsg("jsonStr is empty!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}
	
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			if (jsonObject == null) {
				message.setMsg("jsonStr格式错误!");
				message.setSuccess(false);
				super.writeJson(message);
				return;
			}
			
			eventBoard = (CyEventBoard)JSONObject.toBean(jsonObject, CyEventBoard.class);
			
			eventBoard.setCreateTime(tmpDate);
			
			service.saveMobEventBoard(eventBoard);
			
			isSuc = true;
			
			jsonObjectPut.put("提示", "创建活动花絮成功");
			
			
//			message.setReturnId(String.valueOf(eventBoard.getId()));
			
			
			eventBoardPic = (CyEventBoardPic)JSONObject.toBean(jsonObject, CyEventBoardPic.class);
			logger.info("eventBoardPic="+eventBoardPic.toString());
			String[] picFileName = eventBoardPic.getPicStr().split(",");
			eventBoardPic.setPicStr("");
			
			eventBoardPic.setBoardId(eventBoard.getId());
			
			HashMap<String,String> fileMap = checkFileFolderAndCreateIfNotExsit();
			
			String savePath = fileMap.get("savePath");
			String saveUrl = fileMap.get("saveUrl");
			
			jsonObjectPut.put("图片数量", picFileName.length);
			
			for (int i = 0; i < picFileName.length; i++) {
				tmpPicName = picFileName[i];
				logger.info("picname="+picFileName[i]);
				
				if(picFileName[i] != null && picFileName[i].lastIndexOf(".") != -1){
					
					picFileName[i] = "mob" + tmpDate.getTime() + new Random().nextInt(1000) + picFileName[i].substring(picFileName[i].lastIndexOf("."));
					
					if(i < uploadArr.length){
						if(GenerateImageForMob(uploadArr[i], savePath + picFileName[i])){
							
							logger.info("Success pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
							
							eventBoardPic.setPic(saveUrl + picFileName[i]);
							
						}else{
							logger.info("Failure pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
							jsonObjectPut.put("图片"+i, "花絮图片上传失败="+tmpPicName);
							
							continue;
						}
					}else{
						logger.info("Failure pic=" + savePath + picFileName[i] + " and pic url="+ saveUrl + picFileName[i]);
						jsonObjectPut.put("图片"+i, "找不到对应的花絮图片对象="+tmpPicName);
			
						continue;
					}
					
					
					
				}else{
					jsonObjectPut.put("图片"+i, "花絮图片文件名不正确="+tmpPicName);
					
					continue;
				}
				
				
				service.saveMobEventBoardPic(eventBoardPic);
				jsonObjectPut.put("图片"+i, "花絮图片上传成功="+tmpPicName);
				numOfPic += 1;
			}

			jsonObjectPut.put("成功上传", numOfPic);
			
			message.setMsg(jsonObjectPut.toString());
			message.setSuccess(isSuc);
			super.writeJson(message);
			return;
			
		}catch(Exception e){
			logger.error(e, e);
			jsonObjectPut.put("提示", "创建活动花絮失败");
			message.setMsg(jsonObjectPut.toString());
			message.setSuccess(false);
			logger.info("eventBoard="+eventBoard.toString());
			super.writeJson(message);
			return;
		}
		
	}
	
	public static boolean GenerateImage(String imgStr, String imgFilePath) {
		if (imgStr == null) // 图像数据为空  
		return false; 
		
		//logger.info("base64="+imgStr);
		
		BASE64Decoder decoder = new BASE64Decoder();  
		
		try { 
			
			
			// Base64解码  
			byte[] bytes = decoder.decodeBuffer(imgStr);  
//			for (int i = 0; i < bytes.length; ++i) {
//				if (bytes[i] < 0) {// 调整异常数据  
//					bytes[i] += 256;  
//				}
//			}
			
			OutputStream out = new FileOutputStream(imgFilePath);  
			out.write(bytes);  
			out.flush();  
			out.close();  
			
			String fileExt = imgFilePath.substring(imgFilePath.lastIndexOf("."));
			String fileUrl = imgFilePath.substring(0, imgFilePath.lastIndexOf("."));
			//小图图
			WebUtil.getThumb(MIN_IMG_SIZE, true, imgFilePath, fileUrl+"_MIN"+fileExt);
			//高清图
			WebUtil.getThumb(MAX_IMG_SIZE,true, imgFilePath, fileUrl+"_MAX"+fileExt);
			
			return true;
              
		} catch (Exception e) {  
			return false;  
		} 
		
	}  
	
	public boolean GenerateImageForMob(File tmpUpload, String imgFilePath) {
		if (tmpUpload == null) // 图像数据为空  
		return false; 
		
		File uploadedFile = new File(imgFilePath);
		
		try {
			FileUtil.copyFile(tmpUpload, uploadedFile);
			
			String fileExt = imgFilePath.substring(imgFilePath.lastIndexOf("."));
			String fileUrl = imgFilePath.substring(0, imgFilePath.lastIndexOf("."));
			//小图图
			WebUtil.getThumb(MIN_IMG_SIZE, true, imgFilePath, fileUrl+"_MIN"+fileExt);
			//高清图
			WebUtil.getThumb(MAX_IMG_SIZE,true, imgFilePath, fileUrl+"_MAX"+fileExt);
			
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	public HashMap<String,String> checkFileFolderAndCreateIfNotExsit(){
		
		HashMap<String,String> hm = new HashMap<String,String>();
		
		// 文件保存目录路径
		String savePath = Global.DISK_PATH;

		// 文件保存目录URL
		String saveUrl = Global.URL_DOMAIN;
		
		String dirName = "image";
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists())
		{
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists())
		{
			dirFile.mkdirs();
		}
		
		hm.put("savePath", savePath);
		hm.put("saveUrl", saveUrl);
		
		return hm;
	}
	
	private String generateSignInCode() {
    	Random r = new Random(); 
    	int x = r.nextInt(9999); 
    	String code = String.format("%04d", x);
    	return code;
    }
	
	
	public File[] getUploadArr() {
		return uploadArr;
	}


	public void setUploadArr(File[] uploadArr) {
		this.uploadArr = uploadArr;
	}


	public File getUpload() {
		return upload;
	}


	public void setUpload(File upload) {
		this.upload = upload;
	}


	public CyEventBoard getEventBoard() {
		return eventBoard;
	}


	public void setEventBoard(CyEventBoard eventBoard) {
		this.eventBoard = eventBoard;
	}


	public CyEventBoardPic getEventBoardPic() {
		return eventBoardPic;
	}


	public void setEventBoardPic(CyEventBoardPic eventBoardPic) {
		this.eventBoardPic = eventBoardPic;
	}


	public CyEvent getEventData() {
		return eventData;
	}

	public CyEventSign getEventSignData() {
		return eventSignData;
	}

	public void setEventData(CyEvent eventData) {
		this.eventData = eventData;
	}

	public void setEventSignData(CyEventSign eventSignData) {
		this.eventSignData = eventSignData;
	}
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}


	public CyEventBoardComment getEventBoardComment() {
		return eventBoardComment;
	}


	public CyEventBoardComplaint getEventBoardComplaint() {
		return eventBoardComplaint;
	}


	public void setEventBoardComment(CyEventBoardComment eventBoardComment) {
		this.eventBoardComment = eventBoardComment;
	}


	public void setEventBoardComplaint(CyEventBoardComplaint eventBoardComplaint) {
		this.eventBoardComplaint = eventBoardComplaint;
	}


	public CyEventBoardPraise getEventBoardPraise() {
		return eventBoardPraise;
	}


	public void setEventBoardPraise(CyEventBoardPraise eventBoardPraise) {
		this.eventBoardPraise = eventBoardPraise;
	}

}
