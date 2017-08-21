package com.cy.core.mobserv.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;












import com.cy.core.mobserv.dao.MobServMapper;
import com.cy.core.mobserv.entity.CyContact;
import com.cy.core.mobserv.entity.CyServ;
import com.cy.core.mobserv.entity.CyServComment;
import com.cy.core.mobserv.entity.CyServComplaint;
import com.cy.core.mobserv.entity.CyServFavorite;
import com.cy.core.mobserv.entity.CyServPic;
import com.cy.core.mobserv.entity.CyServPraise;
import com.cy.util.WebUtil;



@Service("mobServService")
public class MobServServiceImpl implements MobServService {

	@Autowired
	private MobServMapper mobServMapper;

	@Override
	public void insertServ(CyServ cyServ) {
		
		mobServMapper.insertServ(cyServ);
		
	}

	@Override
	public void insertServPic(CyServPic cyServPic) {
		
		mobServMapper.insertServPic(cyServPic);
		
	}
	
	
	
	/**
	 * 获得帮帮忙列表
	 * 
	 * @param CyServ cyServ
	 * @return List<CyServ>
	 * 
	 */
	@Override
	public List<CyServ> getServList(CyServ cyServ) {
		// TODO Auto-generated method stub
		
		long accountNum = cyServ.getAccountNum();
		
		
		if(cyServ.getIsWhat() != 2 && cyServ.getIsWhat() != 3 )
		{
			cyServ.setAccountNum(0);
		}
		
		
		
		List<CyServ> list = mobServMapper.getServList(cyServ);
		
		for(CyServ serv : list)
		{
			if(serv != null)
			{
				CyServPraise servPraise = new CyServPraise();
				servPraise.setServiceId(serv.getId());
				//servPraise.setServiceId(accountNum);
				serv.setPraiseNum(mobServMapper.countServPraise(servPraise));
				
				CyServPraise countPraise = new CyServPraise();
				countPraise.setServiceId(serv.getId());
				countPraise.setAccountNum(accountNum);
				long pariseNum = mobServMapper.countServPraise(countPraise);
				
				if(pariseNum > 0)
				{
					serv.setParise(true);
				}
				else
				{
					serv.setParise(false);
				}
				
				
				CyServFavorite countFavorite = new CyServFavorite();
				countFavorite.setServiceId(serv.getId());
				countFavorite.setAccountNum(accountNum);
				long favoriteNum = mobServMapper.countServFavorite(countFavorite);
				
				if(favoriteNum > 0)
				{
					serv.setFavorite(true);
				}
				else
				{
					serv.setFavorite(false);
				}
				
				String userPic = serv.getUserAvatar();
				
				long serviceId = serv.getId();
				CyServPic cyServPic = new CyServPic();cyServPic.setServiceId(serviceId);
				
				serv.setCyServPicList(getServPicList(cyServPic));
				serv.setCreateTimeStr(WebUtil.formatDateByPattern(serv.getCreateTime(), "yyyy年MM月dd日 HH:mm"));
				
				if(WebUtil.isNumeric(userPic))
				{
					serv.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
			
			
		}
		
		
		return list;
	
	}
	
	
	/**
	 * 通过指定评论对象中的参数得到评论对象的列表(带楼主)
	 * 
	 * @param CyServComment servComment
	 * @return List<CyServComment>
	 * 
	 */
	@Override
	public Map<String, Object> getServCommentListWithServ(CyServ cyServ, CyServComment cyServComment) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		long accountNum = cyServ.getAccountNum();
		cyServComment.setCurrentRow(cyServ.getCurrentRow());
		
		if(cyServ.getIsWhat() != 2 && cyServ.getIsWhat() != 3 )
		{
			cyServ.setAccountNum(0);
		}

		CyServ serv = mobServMapper.getServ(cyServ);
		
		if(serv != null)
		{
			CyServPraise servPraise = new CyServPraise();
			servPraise.setServiceId(cyServ.getId());
			servPraise.setAccountNum(accountNum);
			serv.setPraiseNum(mobServMapper.countServPraise(servPraise));
			
			CyServPraise countServPraise = new CyServPraise();
			countServPraise.setServiceId(cyServ.getId());
			//countServPraise.setAccountNum(accountNum);
			long pariseNum = mobServMapper.countServPraise(countServPraise);
			
			if(pariseNum > 0)
			{
				serv.setParise(true);
			}
			else
			{
				serv.setParise(false);
			}

			CyServFavorite countFavorite = new CyServFavorite();
			countFavorite.setServiceId(serv.getId());
			countFavorite.setAccountNum(accountNum);
			long favoriteNum = mobServMapper.countServFavorite(countFavorite);
			
			if(favoriteNum > 0)
			{
				serv.setFavorite(true);
			}
			else
			{
				serv.setFavorite(false);
			}
			
			if(serv != null)
			{
				String userPic = serv.getUserAvatar();
				
				long serviceId = serv.getId();
				CyServPic cyServPic = new CyServPic();cyServPic.setServiceId(serviceId);
				
				serv.setCyServPicList(getServPicList(cyServPic));
				serv.setCreateTimeStr(WebUtil.pastTime(serv.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					serv.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
			
		}
		
		List<CyServComment> list = mobServMapper.getServCommentList(cyServComment);
		
		for(CyServComment servComment : list)
		{
			if(servComment != null)
			{
				String userPic = servComment.getUserAvatar();
				
				servComment.setCreateTimeStr(WebUtil.pastTime(servComment.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					servComment.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
		}
		
		map.put("serv", serv);
		map.put("commentList", list);
		
		
		return map;
	}
	
	
	/**
	 * 通过指定评论对象中的参数得到评论对象的列表(不带楼主)
	 * 
	 * @param CyServComment servComment
	 * @return List<CyServComment>
	 * 
	 */
	@Override
	public Map<String, Object> getServCommentList(CyServ cyServ, CyServComment cyServComment) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();

		List<CyServComment> list = mobServMapper.getServCommentList(cyServComment);
		
		for(CyServComment servComment : list)
		{
			if(servComment != null)
			{
				String userPic = servComment.getUserAvatar();
				
				servComment.setCreateTimeStr(WebUtil.pastTime(servComment.getCreateTime()));
				
				if(WebUtil.isNumeric(userPic))
				{
					servComment.setUserAvatar(WebUtil.getIcon(Integer.parseInt(userPic)));
				}
			}
		}
		
		map.put("commentList", list);
		
		
		return map;
	}

	
	/**
	 * 获得帮帮忙列表总数
	 * 
	 * @param CyServ cyServ
	 * @return long
	 * 
	 */
	@Override
	public long countServ(CyServ cyServ)
	{
		if(cyServ.getIsWhat() != 2 && cyServ.getIsWhat() != 3 )
		{
			cyServ.setAccountNum(0);
		}
		// TODO Auto-generated method stub
		return mobServMapper.countServ(cyServ);
	}

	/**
	 * 获得帮帮忙对象
	 * 
	 * @param CyServ cyServ
	 * @return CyServ cyServ
	 * 
	 */
	@Override
	public CyServ getServ(CyServ cyServ) {
		// TODO Auto-generated method stub
		return mobServMapper.getServ(cyServ);
	}

	/**
	 * 通过ID获得帮帮忙对象
	 * 
	 * @param CyServ cyServ
	 * @return CyServ cyServ
	 * 
	 */
	@Override
	public CyServ getServById(CyServ cyServ) {
		// TODO Auto-generated method stub
		return mobServMapper.getServById(cyServ);
	}

	/**
	 * 获得帮帮忙图片列表
	 * 
	 * @param CyServPic cyServPic
	 * @return List<CyServPic>
	 * 
	 */
	@Override
	public List<CyServPic> getServPicList(CyServPic cyServPic) {
		// TODO Auto-generated method stub
		return mobServMapper.getServPicList(cyServPic);
	}

	/**
	 * 获得帮帮忙总列表中的图片
	 * 
	 * @param CyServ cyServ
	 * @return List<CyServPic>
	 * 
	 */
	@Override
	public List<CyServPic> getServPicListForServDetail(CyServ cyServ) {
		// TODO Auto-generated method stub
		return mobServMapper.getServPicListForServDetail(cyServ);
	}

	
	/**
	 * 获得评论列表
	 * 
	 * @param CyServComment cyServComment
	 * @return List<CyServComment>
	 * 
	 */
	@Override
	public List<CyServComment> getServCommentList(CyServComment cyServComment) {
		// TODO Auto-generated method stub
		return mobServMapper.getServCommentList(cyServComment);
	}

	/**
	 * 获得评论总数
	 * 
	 * @param CyServComment cyServComment
	 * @return long
	 * 
	 */
	@Override
	public long countServComment(CyServComment cyServComment) {
		// TODO Auto-generated method stub
		return mobServMapper.countServComment(cyServComment);
	}

	/**
	 * 获得举报总数
	 * 
	 * @param CyServComplaint cyServComplaint
	 * @return long
	 * 
	 */
	@Override
	public long countServComplaint(CyServComplaint cyServComplaint) {
		// TODO Auto-generated method stub
		return mobServMapper.countServComplaint(cyServComplaint);
	}
	
	
	/**
	 * 获得收藏总数
	 * 
	 * @param CyServFavorite cyServFavorite
	 * @return long
	 * 
	 */
	@Override
	public long countServFavorite(CyServFavorite cyServFavorite) {
		// TODO Auto-generated method stub
		return mobServMapper.countServFavorite(cyServFavorite);
	}

	/**
	 * 获得点赞总数
	 * 
	 * @param CyServPraise cyServPraise
	 * @return long
	 * 
	 */
	@Override
	public long countServPraise(CyServPraise cyServPraise) {
		// TODO Auto-generated method stub
		return mobServMapper.countServPraise(cyServPraise);
	}

	
	/**
	 * 添加举报
	 * 
	 * @param CyServComplaint cyServComplaint
	 * 
	 */
	@Override
	public void insertServComplaint(CyServComplaint cyServComplaint) {
		// TODO Auto-generated method stub
		mobServMapper.insertServComplaint(cyServComplaint);
	}

	/**
	 * 添加评论
	 * 
	 * @param CyServComment cyServComment
	 * 
	 */
	@Override
	public void insertServComment(CyServComment cyServComment) {
		// TODO Auto-generated method stub
		mobServMapper.insertServComment(cyServComment);
	}

	/**
	 * 删除评论
	 * 
	 * @param CyServComment cyServComment
	 * 
	 */
	@Override
	public void deleteServComment(CyServComment cyServComment) {
		// TODO Auto-generated method stub
		mobServMapper.deleteServComment(cyServComment);
	}
	
	
	/**
	 * 通过点赞对象中的参数点赞
	 * 
	 * @param CyServPraise cyServPraise
	 * 
	 */
	@Override
	public void servPraise(CyServPraise cyServPraise) {
		// TODO Auto-generated method stub
		
		long praiseNum = mobServMapper.countServPraise(cyServPraise);
		
		if(praiseNum > 0)
		{
			mobServMapper.deleteServPraise(cyServPraise);
		}
		else
		{
			mobServMapper.insertServPraise(cyServPraise);
		}
		
	}
	
	/**
	 * 通过收藏对象中的参数收藏
	 * 
	 * @param CyServFavorite cyServFavorite
	 * 
	 */
	@Override
	public void servFavorite(CyServFavorite cyServFavorite)
	{
		// TODO Auto-generated method stub
		
		long favoriteNum = mobServMapper.countServFavorite(cyServFavorite);
		
		if(favoriteNum > 0)
		{
			mobServMapper.deleteServFavorite(cyServFavorite);
		}
		else
		{
			mobServMapper.insertServFavorite(cyServFavorite);
		}
		
	}
	

	/**
	 * 添加赞
	 * 
	 * @param CyServPraise cyServPraise
	 * 
	 */
	@Override
	public void insertServPraise(CyServPraise cyServPraise) {
		// TODO Auto-generated method stub
		mobServMapper.insertServPraise(cyServPraise);
	}
	

	/**
	 * 删除赞
	 * 
	 * @param CyServPraise cyServPraise
	 * 
	 */
	@Override
	public void deleteServPraise(CyServPraise cyServPraise) {
		// TODO Auto-generated method stub
		mobServMapper.deleteServPraise(cyServPraise);
	}

	/**
	 * 删除帮帮忙
	 * 
	 * @param CyServ cyServ
	 * 
	 */
	@Override
	public void deleteServ(CyServ cyServ) {
		// TODO Auto-generated method stub
		mobServMapper.deleteServ(cyServ);
	}

	/**
	 * 插入联系XXX表
	 * 
	 * @param CyContact cyContact
	 * 
	 */
	@Override
	public void insertContact(CyContact cyContact) {
		// TODO Auto-generated method stub
		mobServMapper.insertContact(cyContact);
		
	}

	/**
	 * 获得联系XXX列表
	 * 
	 * @param CyContact cyContact
	 * @return List<CyContact>
	 * 
	 */
	@Override
	public List<CyContact> getContactList(CyContact cyContact) {
		// TODO Auto-generated method stub
		return mobServMapper.getContactList(cyContact);
	}
	@Override
	public long getContactListCount(CyContact cyContact) {
		// TODO Auto-generated method stub
		return mobServMapper.getContactListCount(cyContact);
	}
	@Override
	public List<Map<String, String>> getMobProvinceCapital() {
		return mobServMapper.getMobProvinceCapital();
	}

	@Override
	public List<Map<String, String>> getMobProvince() {
		return mobServMapper.getMobProvince();
	}

	@Override
	public List<Map<String, String>> getMobCapitalFromProvince(
			Map<String, Object> requestMap) {
		return mobServMapper.getMobCapitalFromProvince(requestMap);
	}

	@Override
	public List<Map<String, String>> getMobProvinceAndId() {
		return mobServMapper.getMobProvinceAndId();
	}

	


}
