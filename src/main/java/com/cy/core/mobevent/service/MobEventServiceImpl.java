package com.cy.core.mobevent.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.core.event.entity.EventBoard;
import com.cy.core.share.dao.FileMapper;
import com.cy.core.share.entity.File;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.dict.entity.Dict;
import com.cy.core.mobevent.dao.MobEventMapper;
import com.cy.base.entity.Message;
import com.cy.core.mobevent.entity.CyEvent;
import com.cy.core.mobevent.entity.CyEventBoard;
import com.cy.core.mobevent.entity.CyEventBoardComment;
import com.cy.core.mobevent.entity.CyEventBoardComplaint;
import com.cy.core.mobevent.entity.CyEventBoardPic;
import com.cy.core.mobevent.entity.CyEventBoardPraise;
import com.cy.core.mobevent.entity.CyEventComment;
import com.cy.core.mobevent.entity.CyEventSign;
import com.cy.util.WebUtil;


@Service("mobEventService")
public class MobEventServiceImpl implements MobEventService {

	@Autowired
	private MobEventMapper mobEventMapper;

	@Autowired
	private FileMapper fileMapper;
	
	/**
	 * 通过活动ID和用户ID得到活动详情
	 *
	 * @return Map<String, Object>
	 * 
	 */
	@Override
	public Map<String, Object> getEventDetail(String id, String userId) {
		// TODO Auto-generated method stub
		
		CyEvent cyEvent = getEventById(id);
		
		List<CyEventBoardPic> eventBoardPicList = null; 
		if(cyEvent == null)
		{
			return null;
		}
		else
		{
			cyEvent.setStartShortTime(WebUtil.formatDateByPattern(cyEvent.getStartTime(), WebUtil.MDHM));
			cyEvent.setEndShortTime(WebUtil.formatDateByPattern(cyEvent.getEndTime(), WebUtil.MDHM));
			cyEvent.setSignupStartShortTime(WebUtil.formatDateByPattern(cyEvent.getSignupStartTime(), WebUtil.MDHM));
			cyEvent.setSignupEndShortTime(WebUtil.formatDateByPattern(cyEvent.getSignupEndTime(), WebUtil.MDHM));
			
			CyEventBoard eventBoard = new CyEventBoard();eventBoard.setEventId(cyEvent.getId());
			eventBoardPicList = getEventBoardPicListForEventDetail(eventBoard);
		}
		
		CyEventSign eventSign = new CyEventSign();
		eventSign.setUserInfoId(userId);
		eventSign.setEventId(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", "5");
		//map.put("userInfoId", userId);
		map.put("eventId", id);
		
		Map<String, Object> eventDetail = new HashMap<String, Object>();
		String userPic = cyEvent.getUserAvatar();
		
		if(WebUtil.isNumeric(userPic))
		{
			cyEvent.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
		}
		
		
		eventDetail.put("cyEvent", cyEvent);
		eventDetail.put("eventBoardPicList", eventBoardPicList);
		eventDetail.put("eventSign", getEventSign(eventSign));eventSign.setUserInfoId(null);
		eventDetail.put("countEventSign", countEventSign(eventSign));
		eventDetail.put("eventSignList", getEventSignList(map));
		
		return eventDetail;
	}
	
	
	/**
	 * 通过活动ID得到活动对象
	 *
	 * @return CyEvent
	 * 
	 */
	@Override
	public CyEvent getEventById(String id) {
		// TODO Auto-generated method stub
		CyEvent cyEvent = mobEventMapper.getEventById(id);
		
		if(cyEvent != null)
		{
			String userPic = cyEvent.getUserAvatar();
			
			if(WebUtil.isNumeric(userPic))
			{
				cyEvent.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
			}
		}
		
		
		
		return cyEvent;
	}

	/**
	 * 通过指定活动签到报名对象中的参数得到活动签到报名对象
	 *
	 * @return CyEventSign
	 * 
	 */
	@Override
	public CyEventSign getEventSign(CyEventSign eventSign) {
		// TODO Auto-generated method stub
		CyEventSign cyEventSign = mobEventMapper.getEventSign(eventSign);
		
		if(cyEventSign != null)
		{
			String userPic = cyEventSign.getUserAvatar();
			
			if(WebUtil.isNumeric(userPic))
			{
				cyEventSign.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
			}
		}
		
		
		
		return cyEventSign;
	}
	
	/**
	 * 通过指定活动签到报名对象中的参数得到活动签到报名对象的列表
	 * @return CyEventSign
	 * 
	 */
	@Override
	public List<CyEventSign> getEventSignList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<CyEventSign> list = mobEventMapper.getEventSignList(map);
		
		for(CyEventSign cyEventSigns : list)
		{
			if(cyEventSigns != null)
			{
				String userPic = cyEventSigns.getUserAvatar();
				
				if(WebUtil.isNumeric(userPic))
				{
					cyEventSigns.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
			
			
		}
		
		return list;
	}
	
	/**
	 * 通过指定活动签到报名对象中的参数得到活动签到报名对象的列表（手机列表专用）
	 *
	 * @return CyEventSign
	 * 
	 */
	@Override
	public List<CyEventSign> pullEventSignList(CyEventSign cyEventSign) {
		// TODO Auto-generated method stub
		List<CyEventSign> list = mobEventMapper.pullEventSignList(cyEventSign);
		
		for(CyEventSign cyEventSigns : list)
		{
			if(cyEventSigns != null)
			{
				String userPic = cyEventSigns.getUserAvatar();
				
				if(WebUtil.isNumeric(userPic))
				{
					cyEventSigns.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
			
			
		}
		
		return list;

	}


	/**
	 * 通过指定活动签到报名对象中的参数得到相应的统计数量
	 *
	 * @return long
	 * 
	 */
	@Override
	public long countEventSign(CyEventSign eventSign) {
		// TODO Auto-generated method stub
		return mobEventMapper.countEventSign(eventSign);
	}

	/**
	 * 验证签到码
	 *
	 * @return CyEvent
	 * 
	 */
	@Override
	public long verifyEventSignInCode(CyEvent event) {
		// TODO Auto-generated method stub
		return mobEventMapper.verifyEventSignInCode(event);
	}

	/**
	 * 更新报名签到表
	 * 
	 */
	@Override
	public void updateEventSign(CyEventSign eventSign) {
		// TODO Auto-generated method stub
		mobEventMapper.updateEventSign(eventSign);
	}

	/**
	 * 插入报名签到表
	 *
	 * 
	 */
	@Override
	public void insertEventSign(CyEventSign eventSign) {
		// TODO Auto-generated method stub
		mobEventMapper.insertEventSign(eventSign);
	}

	
	/**
	 * 插入活动表
	 *
	 * 
	 */
	@Override
	public void saveMobEvent(CyEvent cyEevent) {
		// TODO Auto-generated method stub
		mobEventMapper.saveMobEvent(cyEevent);
	}

	
	/**
	 * 查询活动列表
	 *
	 * 
	 */
	@Override
	public List<CyEvent> listMobEvens(CyEvent cyEevent) {
		// TODO Auto-generated method stub
		return mobEventMapper.listMobEvens(cyEevent);
	}


	/**
	 * 查询活动类型列表
	 *
	 * 
	 */
	public List<Dict> getDicts(String dictTypeName){
		return mobEventMapper.getDicts(dictTypeName);
	}
	
	/**
	 * 查询我的所有活动通知数量
	 *
	 * 
	 */
	public CyEvent getNumOfNotifyForMyEvents(String userInfoId){
		return mobEventMapper.getNumOfNotifyForMyEvents(userInfoId);
	}


	/**
	 * 插入花絮表
	 * 
	 */
	@Override
	public void saveMobEventBoard(CyEventBoard eventBoard) {
		mobEventMapper.saveMobEventBoard(eventBoard);
		
	}

	
	/**
	 * 插入花絮图片表
	 * 
	 */
	@Override
	public void saveMobEventBoardPic(CyEventBoardPic eventBoardPic) {
		mobEventMapper.saveMobEventBoardPic(eventBoardPic);
		
	}
	
	

	/**
	 * 通过指定活动花絮对象中的参数得到活动花絮对象的列表
	 * @return List<CyEventBoard>
	 * 
	 */
	@Override
	public List<CyEventBoard> getEventBoardList(CyEventBoard eventBoard) {
		// TODO Auto-generated method stub
		
		String userInfoId = eventBoard.getUserInfoId();
		eventBoard.setUserInfoId(null);
		
		List<CyEventBoard> list = mobEventMapper.getEventBoardList(eventBoard);
		
		for(CyEventBoard cyEventBoard : list)
		{
			if(cyEventBoard != null)
			{
				CyEventBoardPraise eventBoardPraise = new CyEventBoardPraise();
				eventBoardPraise.setBoardId(cyEventBoard.getId());
				cyEventBoard.setPraiseNum(mobEventMapper.countEventBoardPraise(eventBoardPraise));
				
				CyEventBoardPraise countEventBoardPraise = new CyEventBoardPraise();
				countEventBoardPraise.setBoardId(cyEventBoard.getId());
				countEventBoardPraise.setUserInfoId(userInfoId);
				long pariseNum = mobEventMapper.countEventBoardPraise(countEventBoardPraise);
				
				if(pariseNum > 0)
				{
					cyEventBoard.setParise(true);
				}
				else
				{
					cyEventBoard.setParise(false);
				}
				
				String userPic = cyEventBoard.getUserAvatar();
				
				long boardId = cyEventBoard.getId();
				CyEventBoardPic cyEventBoardPic = new CyEventBoardPic();cyEventBoardPic.setBoardId(boardId);
				
				cyEventBoard.setCyEventBoardPicList(getEventBoardPicList(cyEventBoardPic));
				cyEventBoard.setCreateTimeStr(WebUtil.pastTime(cyEventBoard.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					cyEventBoard.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
			
			
		}
		
		
		return list;
	}
	
	
	/**
	 * 通过指定活动花絮对象中的参数得到活动花絮总数
	 *
	 */
	@Override
	public long countEventBoard(CyEventBoard eventBoard)
	{
		//String userInfoId = eventBoard.getUserInfoId();
		eventBoard.setUserInfoId(null);
		return mobEventMapper.countEventBoard(eventBoard);
	}
	
	/**
	 * 通过指定活动对象中的参数得到活动评论总数
	 *
	 */
	@Override
	public long countEventComment(CyEventComment eventComment)
	{
		//String userInfoId = eventBoard.getUserInfoId();
		eventComment.setUserInfoId(null);
		return mobEventMapper.countEventComment(eventComment);
	}

	

	/**
	 * 通过指定活动花絮对象中的参数得到活动花絮的对象
	 */
	@Override
	public CyEventBoard getEventBoard(CyEventBoard eventBoard) {
		// TODO Auto-generated method stub
		CyEventBoard cyEventBoard  = mobEventMapper.getEventBoard(eventBoard);
		if(cyEventBoard != null)
		{
			CyEventBoardPraise eventBoardPraise = new CyEventBoardPraise();
			eventBoardPraise.setBoardId(cyEventBoard.getId());
			cyEventBoard.setPraiseNum(mobEventMapper.countEventBoardPraise(eventBoardPraise));
			
			String userPic = cyEventBoard.getUserAvatar();
			
			cyEventBoard.setCreateTimeStr(WebUtil.pastTime(cyEventBoard.getCreateTime()));
			
			if(WebUtil.isNumeric(userPic))
			{
				cyEventBoard.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
			}
		}
		
		
		return cyEventBoard;
	}

	/**
	 * 通过指定活动花絮图片对象中的参数得到活动花絮图片对象的列表
	 * @return List<CyEventBoardPic>
	 * 
	 */
	@Override
	public List<CyEventBoardPic> getEventBoardPicList(
			CyEventBoardPic eventBoardPic) {
		// TODO Auto-generated method stub
		
		List<CyEventBoardPic> eventBoardPicList = mobEventMapper.getEventBoardPicList(eventBoardPic);
		
		for(CyEventBoardPic eventBoardPics : eventBoardPicList)
		{
			String pic = eventBoardPics.getPic();
			
			if(!WebUtil.isEmpty(pic))
			{
				pic = pic.replaceAll("_MIN", "").replaceFirst("_MAX", "");
				
				String thumbnail = pic;
				
				String xemanhdep = pic;
				
				thumbnail = WebUtil.getPictureByType(pic, "MIN");
				xemanhdep = WebUtil.getPictureByType(pic, "MAX");
				
				eventBoardPics.setPic(pic);
				eventBoardPics.setThumbnail(thumbnail);
				eventBoardPics.setXemanhdep(xemanhdep);
			}
			
		}
		
		return eventBoardPicList;
	}
	
	
	/**
	 * 得到每个用户发布的活动花絮中的最新的一张的活动花絮图片对象的列表
	 *
	 */
	@Override
	public List<CyEventBoardPic> getEventBoardPicListForEventDetail(CyEventBoard eventBoard){
		// TODO Auto-generated method stub
		List<CyEventBoardPic> eventBoardPicList = mobEventMapper.getEventBoardPicListForEventDetail(eventBoard);
		
		for(CyEventBoardPic eventBoardPic : eventBoardPicList)
		{
			String pic = eventBoardPic.getPic();
			
			if(!WebUtil.isEmpty(pic))
			{
				pic = pic.replaceAll("_MIN", "").replaceFirst("_MAX", "");
				
				String thumbnail = pic;
				
				String xemanhdep = pic;
				
				thumbnail = WebUtil.getPictureByType(pic, "MIN");
				xemanhdep = WebUtil.getPictureByType(pic, "MAX");
				
				eventBoardPic.setPic(pic);
				eventBoardPic.setThumbnail(thumbnail);
				eventBoardPic.setXemanhdep(xemanhdep);
			}
			
		}
		
		return eventBoardPicList;
	}

	/**
	 * 通过指定活动花絮评论对象中的参数得到活动花絮评论对象的列表
	 *
	 */
	@Override
	public List<CyEventBoardComment> getEventBoardCommentList(
			CyEventBoardComment eventBoardComment) {
		// TODO Auto-generated method stub
		List<CyEventBoardComment> list = mobEventMapper.getEventBoardCommentList(eventBoardComment);
		
		for(CyEventBoardComment cyEventBoardComment : list)
		{
			if(cyEventBoardComment != null)
			{
				String userPic = cyEventBoardComment.getUserAvatar();
				
				cyEventBoardComment.setCreateTimeStr(WebUtil.pastTime(cyEventBoardComment.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					cyEventBoardComment.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
			
			
		}
		
		
		return list;
	}
	
	
	/**
	 * 通过指定活动对象中的参数得到活动评论对象的列表
	 *
	 */
	@Override
	public List<CyEventComment> getEventCommentList(CyEvent event) {
		// TODO Auto-generated method stub
		List<CyEventComment> list = mobEventMapper.getEventCommentList(event);
		
		for(CyEventComment cyEventComment : list)
		{
			if(cyEventComment != null)
			{
				String userPic = cyEventComment.getUserAvatar();
				
				cyEventComment.setCreateTimeStr(WebUtil.pastTime(cyEventComment.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					cyEventComment.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
						
		}
		
		
		return list;
	}
	
	
	/**
	 * 通过指定活动花絮评论对象中的参数得到活动花絮评论对象的列表(带楼主)
	 *
	 */
	@Override
	public Map<String, Object> getEventBoardCommentListWithEventBoard(CyEventBoard eventBoard, CyEventBoardComment eventBoardComment) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String userInfoId = eventBoard.getUserInfoId();
		eventBoard.setUserInfoId(null);

		CyEventBoard cyEventBoard = mobEventMapper.getEventBoard(eventBoard);
		
		if(cyEventBoard != null)
		{
			CyEventBoardPraise eventBoardPraise = new CyEventBoardPraise();
			eventBoardPraise.setBoardId(cyEventBoard.getId());
			cyEventBoard.setPraiseNum(mobEventMapper.countEventBoardPraise(eventBoardPraise));
			
			CyEventBoardPraise countEventBoardPraise = new CyEventBoardPraise();
			countEventBoardPraise.setBoardId(cyEventBoard.getId());
			countEventBoardPraise.setUserInfoId(userInfoId);
			long pariseNum = mobEventMapper.countEventBoardPraise(countEventBoardPraise);
			
			if(pariseNum > 0)
			{
				cyEventBoard.setParise(true);
			}
			else
			{
				cyEventBoard.setParise(false);
			}
			
			if(cyEventBoard != null)
			{
				String userPic = cyEventBoard.getUserAvatar();
				
				long boardId = cyEventBoard.getId();
				CyEventBoardPic cyEventBoardPic = new CyEventBoardPic();cyEventBoardPic.setBoardId(boardId);
				
				cyEventBoard.setCyEventBoardPicList(getEventBoardPicList(cyEventBoardPic));
				cyEventBoard.setCreateTimeStr(WebUtil.pastTime(cyEventBoard.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					cyEventBoard.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
			
		}
		
		List<CyEventBoardComment> list = mobEventMapper.getEventBoardCommentList(eventBoardComment);
		
		for(CyEventBoardComment cyEventBoardComment : list)
		{
			if(cyEventBoardComment != null)
			{
				String userPic = cyEventBoardComment.getUserAvatar();
				
				cyEventBoardComment.setCreateTimeStr(WebUtil.pastTime(cyEventBoardComment.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					cyEventBoardComment.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
		}
		
		map.put("eventBoard", cyEventBoard);
		map.put("commentList", list);
		
		
		return map;
	}

	
	
	
	
	/**
	 * 通过指定活动花絮举报对象中的参数得到活动花絮举报数
	 *
	 */
	@Override
	public long countEventBoardComplaint(
			CyEventBoardComplaint eventBoardComplaint) {
		// TODO Auto-generated method stub
		return mobEventMapper.countEventBoardComplaint(eventBoardComplaint);
	}
	
	/**
	 * 通过指定活动花絮点赞对象中的参数得到活动花絮点赞数
	 * @return long
	 */
	@Override
	public long countEventBoardPraise(
			CyEventBoardPraise eventBoardPraise) {
		// TODO Auto-generated method stub
		return mobEventMapper.countEventBoardPraise(eventBoardPraise);
	}
	
	
	/**
	 * 通过指定活动花絮评论对象中的参数得到活动花絮评论数
	 * @return long
	 */
	@Override
	public long countEventBoardComment(
			CyEventBoardComment eventBoardComment) {
		// TODO Auto-generated method stub
		return mobEventMapper.countEventBoardComment(eventBoardComment);
	}
	

	/**
	 * 插入活动花絮举报对象中的参数到活动花絮举报表
	 *
	 */
	@Override
	public void insertEventBoardComplaint(
			CyEventBoardComplaint eventBoardComplaint) {
		// TODO Auto-generated method stub
		mobEventMapper.insertEventBoardComplaint(eventBoardComplaint);
	}


	/**
	 * 插入活动花絮点赞对象中的参数到活动花絮点赞表
	 *
	 */
	public void insertEventBoardPraise(CyEventBoardPraise eventBoardPraise) {
		// TODO Auto-generated method stub
		mobEventMapper.insertEventBoardPraise(eventBoardPraise);
	}


	/**
	 * 通过活动花絮点赞对象中的参数删除活动花絮点赞
	 *
	 */
	public void deleteEventBoardPraise(CyEventBoardPraise eventBoardPraise) {
		// TODO Auto-generated method stub
		mobEventMapper.deleteEventBoardPraise(eventBoardPraise);
	}


	/**
	 * 通过活动花絮点赞对象中的参数删除活动花絮点赞
	 *
	 */
	@Override
	public void eventBoardPraise(CyEventBoardPraise eventBoardPraise) {
		// TODO Auto-generated method stub
		
		long praiseNum = mobEventMapper.countEventBoardPraise(eventBoardPraise);
		
		if(praiseNum > 0)
		{
			mobEventMapper.deleteEventBoardPraise(eventBoardPraise);
		}
		else
		{
			mobEventMapper.insertEventBoardPraise(eventBoardPraise);
		}
		
	}


	/**
	 * 插入活动花絮评论对象中的参数到活动花絮评论表
	 *
	 */
	@Override
	public void insertEventBoardComment(CyEventBoardComment eventBoardComment) {
		// TODO Auto-generated method stub
		mobEventMapper.insertEventBoardComment(eventBoardComment);
	}


	/**
	 * 通过活动花絮评论对象中的参数删除活动花絮评论
	 *
	 */
	@Override
	public void deleteEventBoardComment(CyEventBoardComment eventBoardComment) {
		// TODO Auto-generated method stub
		mobEventMapper.deleteEventBoardComment(eventBoardComment);
	}


	/**
	 * 通过活动花絮对象中的参数删除活动花絮
	 *
	 */
	@Override
	public void deleteEventBoard(CyEventBoard eventBoard) {
		// TODO Auto-generated method stub
		mobEventMapper.deleteEventBoard(eventBoard);
	}


	@Override
	public long listMobEvensNum(CyEvent cyEevent) {
		// TODO Auto-generated method stub
		return mobEventMapper.listMobEvensNum(cyEevent);
	}


	/**
	 * 通过活动对象中的参数删除活动
	 *
	 */
	@Override
	public void deleteEvent(CyEvent event) {
		// TODO Auto-generated method stub
		mobEventMapper.deleteEvent(event);
	}


	@Override
	public String getRegionFromAlumniByAccountNum(CyEvent cyEvent) {
		
		String returnStr = "";
		String pointStr = " ";
    	
		CyEvent tmpEvent = mobEventMapper.getRegionFromAlumniByAccountNum(cyEvent);
		
		if(tmpEvent != null){
			
			if(tmpEvent.getRegion()!=null && !tmpEvent.getRegion().equals("")){
				
				if(tmpEvent.getRegion().contains(pointStr)){
					returnStr = tmpEvent.getRegion().substring(tmpEvent.getRegion().lastIndexOf(pointStr)+1, tmpEvent.getRegion().length());
				}else{
					returnStr = tmpEvent.getRegion();
				}
				 
				
			}else if(tmpEvent.getPlace()!=null && !tmpEvent.getPlace().equals("")){
				
				if(tmpEvent.getPlace().contains(pointStr)){
					returnStr = tmpEvent.getPlace().substring(tmpEvent.getPlace().lastIndexOf(pointStr)+1, tmpEvent.getPlace().length());
				}else{
					returnStr = tmpEvent.getPlace();
				}
				
			}
		}
		
    	return returnStr;
		
	}

	/***********************************************************************
	 *
	 * 【活动花絮】相关API（以下区域）
	 *
	 * 注意事项：
	 * 1、方法名-格式要求
	 * 创建方法：save[Name]
	 * 撤销方法：remove[Name]
	 * 查询分页列表方法：find[Name]ListPage
	 * 查询列表方法：find[Name]List
	 * 查询详细方法：find[Name]
	 *
	 ***********************************************************************/

	/**
	 * 活动花絮创建接口
	 */
	public void saveEventBoard(Message message, String content){
		if(StringUtils.isBlank(content)){
			message.setMsg("请求数据不能为空");
			message.setSuccess(false);
			return;
		}
		try{
			CyEventBoard cyEventBoard = JSON.parseObject(content, CyEventBoard.class);
			if(StringUtils.isBlank(cyEventBoard.getEventId())){
				message.setMsg("活动Id不能为空");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isBlank(String.valueOf(cyEventBoard.getUserInfoId()))){
				message.setMsg("用户Id不能为空");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isBlank(cyEventBoard.getComment())){
				message.setMsg("花絮内容不能为空");
				message.setSuccess(false);
				return;
			}

			cyEventBoard.setCreateTime(new Date());
			cyEventBoard.setStatus(0);
			//保存花絮内容简介
			mobEventMapper.saveMobEventBoard(cyEventBoard);
			//保存花絮图片
			for(CyEventBoardPic cyPic:cyEventBoard.getCyEventBoardPicList()){
				CyEventBoardPic cyEventBoardPic = new CyEventBoardPic();
				cyEventBoardPic.setBoardId(cyEventBoard.getId());
				cyEventBoardPic.setPic(cyPic.getPic());
				mobEventMapper.saveMobEventBoardPic(cyEventBoardPic);
			}
			message.setMsg("创建花絮成功");
			message.setSuccess(true);
			return;

		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * 活动评价／点赞接口
	 */
	public void saveEventCommentOrPraise(Message message, String content){
		if(StringUtils.isBlank(content)){
			message.setMsg("请求数据不能为空");
			message.setSuccess(false);
			return;
		}
		try{
			Map<String, String> map = JSON.parseObject(content, Map.class);

			String eventId = map.get("eventId");
			String userId = map.get("userId");
			String action = map.get("action");
			String comment = map.get("comment");

			if( StringUtils.isBlank(eventId) ){
				message.setMsg("花絮编号不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(userId)){
				message.setMsg("评论人账号不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(action)){
				message.setMsg("操作类型不能为空");
				message.setSuccess(false);
				return;
			}

			//评论
			if(action.equals("0") || action.equals("1")){

				CyEventComment cyEventComment = new CyEventComment();
				cyEventComment.setEventId(eventId);
				cyEventComment.setUserInfoId(userId);
				cyEventComment.setComment(comment);

				//0-添加评论
				if(action.equals("0")){
					if(StringUtils.isBlank(comment)){
						message.setMsg("没有填写评论内容");
						message.setSuccess(false);
						return;
					}

					mobEventMapper.insertEventComment(cyEventComment);
					message.setMsg("评论成功");
					message.setSuccess(true);
					return;
				}else{
					//1-删除评论
					mobEventMapper.deleteEventComment(cyEventComment);
					message.setMsg("删除评论成功");
					message.setSuccess(true);
					return;
				}                  
			}
			//点赞
			/*
			else if(action.equals("2") || action.equals("3")){
				CyEventBoardPraise cyEventBoardPraise = new CyEventBoardPraise();

				cyEventBoardPraise.setBoardId(Long.parseLong(eventId));
				cyEventBoardPraise.setUserInfoId(userId);

				if(action.equals("2")){
					mobEventMapper.insertEventBoardPraise(cyEventBoardPraise);
					message.setMsg("点赞成功");
					message.setSuccess(true);
					return;
				}else {
					mobEventMapper.deleteEventBoardPraise(cyEventBoardPraise);
					message.setMsg("取消点赞成功");
					message.setSuccess(true);
					return;
				}
				}
				*/
			else{
				message.setMsg("未知的操作类型");
				message.setSuccess(false);
				return;
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 活动评论统计
	 */
	public void CountCommentForEventid(Message message, String content){
		if(StringUtils.isBlank(content)){
			message.setMsg("请求数据不能为空");
			message.setSuccess(false);
			return;
		}
		try{
			Map<String, String> map = JSON.parseObject(content, Map.class);

			String eventId = map.get("eventId");
			//String userId = map.get("userId");		

			if( StringUtils.isBlank(eventId) ){
				message.setMsg("活动编号不能为空");
				message.setSuccess(false);
				return;
			}						

			CyEventComment cyEventComment = new CyEventComment();
			cyEventComment.setEventId(eventId);
			//cyEventComment.setUserInfoId(userId);
					
		    long eventcommentcount= mobEventMapper.countEventComment(cyEventComment);
			
		    message.setMsg("评论总数");
		    message.setObj(eventcommentcount);
			message.setSuccess(true);
	
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 活动花絮统计
	 */
	public void CountBoardForBoardid(Message message, String content){
		if(StringUtils.isBlank(content)){
			message.setMsg("请求数据不能为空");
			message.setSuccess(false);
			return;
		}
		
		try{
			Map<String, String> map = JSON.parseObject(content, Map.class);

			String eventId = map.get("eventId");
			//String userId = map.get("userId");		

		
			if( StringUtils.isBlank(eventId) ){
				message.setMsg("活动编号不能为空");
				message.setSuccess(false);
				return;
			}
		
			CyEventBoard cyEventBoard = new CyEventBoard();
			cyEventBoard.setEventId(eventId);
			//cyEventBoard.setUserInfoId(userId);
			long boardcount=mobEventMapper.countEventBoard(cyEventBoard);
			
			message.setMsg("花絮总数");
			message.setObj(boardcount);
			message.setSuccess(true);	
			
		   }catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 活动花絮评价／点赞接口
	 */
	public void saveCommentOrPraise(Message message, String content){
		if(StringUtils.isBlank(content)){
			message.setMsg("请求数据不能为空");
			message.setSuccess(false);
			return;
		}
		try{
			Map<String, String> map = JSON.parseObject(content, Map.class);

			String boardId = map.get("boardId");
			String userId = map.get("userId");
			String action = map.get("action");
			String comment = map.get("comment");

			if( StringUtils.isBlank(boardId) ){
				message.setMsg("花絮编号不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(userId)){
				message.setMsg("评论人账号不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(action)){
				message.setMsg("操作类型不能为空");
				message.setSuccess(false);
				return;
			}

			if(action.equals("0") || action.equals("1")){

				CyEventBoardComment cyEventBoardComment = new CyEventBoardComment();
				cyEventBoardComment.setBoardId(Long.parseLong(boardId));
				cyEventBoardComment.setUserInfoId(userId);
				cyEventBoardComment.setComment(comment);

				if(action.equals("0")){
					if(StringUtils.isBlank(comment)){
						message.setMsg("没有填写评论内容");
						message.setSuccess(false);
						return;
					}

					mobEventMapper.insertEventBoardComment(cyEventBoardComment);
					message.setMsg("评论成功");
					message.setSuccess(true);
					return;
				}else{
					mobEventMapper.deleteEventBoardComment(cyEventBoardComment);
					message.setMsg("删除评论成功");
					message.setSuccess(true);
					return;
				}

			}else if(action.equals("2") || action.equals("3")){
				CyEventBoardPraise cyEventBoardPraise = new CyEventBoardPraise();

				cyEventBoardPraise.setBoardId(Long.parseLong(boardId));
				cyEventBoardPraise.setUserInfoId(userId);

				if(action.equals("2")){
					mobEventMapper.insertEventBoardPraise(cyEventBoardPraise);
					message.setMsg("点赞成功");
					message.setSuccess(true);
					return;
				}else {
					mobEventMapper.deleteEventBoardPraise(cyEventBoardPraise);
					message.setMsg("取消点赞成功");
					message.setSuccess(true);
					return;
				}
			}else{
				message.setMsg("未知的操作类型");
				message.setSuccess(false);
				return;
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 活动花絮反馈接口
	 */
	public void saveComplaint(Message message, String content){
		if(StringUtils.isBlank(content)){
			message.setMsg("请求数据不能为空");
			message.setSuccess(false);
			return;
		}
		try{
			CyEventBoardComplaint cyEventBoardComplaint = JSON.parseObject(content, CyEventBoardComplaint.class);
			/*if(StringUtils.isBlank(String.valueOf(cyEventBoardComplaint.getBoardId()))){
				message.setMsg("花絮编号不能为空");
				message.setSuccess(false);
				return;
			}*/
			if(StringUtils.isBlank(String.valueOf(cyEventBoardComplaint.getBussId()))){
				message.setMsg("举报业务编号不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(String.valueOf(cyEventBoardComplaint.getBussType()))){
				message.setMsg("举报业务类型不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(cyEventBoardComplaint.getUserInfoId())){
				message.setMsg("举报人编号不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(cyEventBoardComplaint.getReason())){
				message.setMsg("请填写举报原因");
				message.setSuccess(false);
				return;
			}

			mobEventMapper.insertEventBoardComplaint(cyEventBoardComplaint);


			if (cyEventBoardComplaint.getImageList()!=null && !cyEventBoardComplaint.getImageList().isEmpty()){
				for (String s:cyEventBoardComplaint.getImageList()){
					File file = new File();
					file.setFileGroup(String.valueOf(cyEventBoardComplaint.getId()));
					//如果
					if ("10".equals(cyEventBoardComplaint.getBussType())){
						file.setBussType("50");
					}else {
						file.setBussType("60");
					}
					file.setPicUrl(s);
					file.preInsert();
					fileMapper.insert(file);
				}
			}

			message.setMsg("举报已成功提交");
			message.setSuccess(true);

		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 活动花絮列表接口
	 */
	public void findEventBoardList(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map< String, String > map = JSON.parseObject(content, Map.class);

		String eventId = map.get("eventId");

		if(StringUtils.isBlank(eventId)){
			message.setMsg("活动Id不能为空");
			message.setSuccess(false);
			return;
		}

		List<EventBoard> list = mobEventMapper.findEventBoardList(eventId);
		message.setMsg("获取花絮列表成功");
		message.setSuccess(true);
		message.setObj(list);
	}

	/**
	 * 活动花絮详情接口
	 */
	public void findEventBoard(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		String boardId = (String)map.get("boardId");

		if (StringUtils.isBlank(boardId)) {
			message.setMsg("未传入花絮ID");
			message.setSuccess(false);
			return;
		}


		CyEventBoard eventBoard = new CyEventBoard();
		eventBoard.setId(Long.parseLong(boardId));
		CyEventBoard cyEventBoard  = mobEventMapper.getEventBoard(eventBoard);

		if(cyEventBoard == null)
		{
			message.setMsg("不存在此花絮");
			message.setSuccess(false);
			return;
		}

		Long count=mobEventMapper.getPraise(map);

		if(count>0){
			cyEventBoard.setParise(true);
		}else{
			cyEventBoard.setParise(false);
		}


		CyEventBoardPraise eventBoardPraise = new CyEventBoardPraise();

		eventBoardPraise.setBoardId(cyEventBoard.getId());

		cyEventBoard.setPraiseNum(mobEventMapper.countEventBoardPraise(eventBoardPraise));

		String userPic = cyEventBoard.getUserAvatar();

		cyEventBoard.setCreateTimeStr(WebUtil.pastTime(cyEventBoard.getCreateTime()));

		if(WebUtil.isNumeric(userPic))
		{
			cyEventBoard.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
		}

		CyEventBoardPic cyEventBoardPic = new CyEventBoardPic();
		cyEventBoardPic.setBoardId(Long.parseLong(boardId));

		cyEventBoard.setCyEventBoardPicList(mobEventMapper.getEventBoardPicList(cyEventBoardPic));

		List<CyEventBoardComment> list = mobEventMapper.getMobEventBoardCommentList(boardId);

		cyEventBoard.setCyEventBoardCommentList(list);

		message.setMsg("花絮详情成功");
		message.setSuccess(true);
		message.setObj(cyEventBoard);
		return;

	}

	/**
	 * 活动评论详情接口
	 */
	public void findEventComment(Message message, String content){
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		
		String eventId = (String)map.get("eventId");

		if (StringUtils.isBlank(eventId)) {
			message.setMsg("未传入活动ID");
			message.setSuccess(false);
			return;
		}


		CyEvent event = new CyEvent();
		 event.setId(eventId);
		CyEvent cyEvent= mobEventMapper.getEventById(eventId);//.getEvent(event);

		if(cyEvent == null)
		{
			message.setMsg("不存在此活动");
			message.setSuccess(false);
			return;
		}

		
		List<CyEventComment> cyEventComment= mobEventMapper.getEventCommentList(event);
		message.setMsg("活动评论详情成功");
		message.setSuccess(true);
		message.setObj(cyEventComment);
		return;
	}
	
	
	/**
	 * 刪除活動花絮接口
	 */
	public void removeEventBoard(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map<String, Object> map = JSON.parseObject(content, Map.class);
		String userId = (String)map.get("userId");
		String boardId = (String)map.get("boardId");
		if (StringUtils.isBlank(userId)) {
			message.setMsg("未传入用户ID");
			message.setSuccess(false);
			return;
		}
		if (StringUtils.isBlank(boardId)) {
			message.setMsg("未传入花絮ID");
			message.setSuccess(false);
			return;
		}

		CyEventBoard eventBoard = new CyEventBoard();
		eventBoard.setId(Long.parseLong(boardId));
		CyEventBoard cyEventBoard  = mobEventMapper.getEventBoard(eventBoard);

		if(cyEventBoard == null){
			message.setMsg("該花絮不存在，或已被刪除");
			message.setSuccess(false);
			return;
		}

		if(!cyEventBoard.getUserInfoId().equals(userId)){
			message.setMsg("不能刪除非本人創建的花絮");
			message.setSuccess(false);
			return;
		}

		mobEventMapper.deleteEventBoard(cyEventBoard);
		message.setMsg("成功刪除花絮");
		message.setSuccess(true);
	}

}
