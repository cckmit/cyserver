package com.cy.core.mobserv.service;

import java.util.List;
import java.util.Map;

import com.cy.core.mobserv.entity.CyContact;
import com.cy.core.mobserv.entity.CyServ;
import com.cy.core.mobserv.entity.CyServComment;
import com.cy.core.mobserv.entity.CyServComplaint;
import com.cy.core.mobserv.entity.CyServFavorite;
import com.cy.core.mobserv.entity.CyServPic;
import com.cy.core.mobserv.entity.CyServPraise;



public interface MobServService {
	
	/**
	 * 插入帮帮忙表
	 * 
	 * @param CyServ cyServ
	 * 
	 */
	public void insertServ(CyServ cyServ);
	
	/**
	 * 插入帮帮忙图片表
	 * 
	 * @param CyServPic cyServPic
	 * 
	 */
	public void insertServPic(CyServPic cyServPic);
	
	
	

	
	
	
	
	/**
	 * 获得帮帮忙列表
	 * 
	 * @param CyServ cyServ
	 * @return List<CyServ>
	 * 
	 */
	public List<CyServ> getServList(CyServ cyServ);
	
	
	/**
	 * 通过指定评论对象中的参数得到评论对象的列表(带楼主)
	 * 
	 * @param CyServComment servComment
	 * @return List<CyServComment>
	 * 
	 */
	public Map<String, Object> getServCommentListWithServ(CyServ cyServ, CyServComment cyServComment);
	
	
	/**
	 * 通过指定评论对象中的参数得到评论对象的列表(不带楼主)
	 * 
	 * @param CyServComment servComment
	 * @return List<CyServComment>
	 * 
	 */
	public Map<String, Object> getServCommentList(CyServ cyServ, CyServComment cyServComment);
	
	
	/**
	 * 获得帮帮忙列表总数
	 * 
	 * @param CyServ cyServ
	 * @return long
	 * 
	 */
	public long countServ(CyServ cyServ);
	
	
	/**
	 * 获得帮帮忙对象
	 * 
	 * @param CyServ cyServ
	 * @return CyServ cyServ
	 * 
	 */
	public CyServ getServ(CyServ cyServ);
	
	
	
	/**
	 * 通过ID获得帮帮忙对象
	 * 
	 * @param CyServ cyServ
	 * @return CyServ cyServ
	 * 
	 */
	public CyServ getServById(CyServ cyServ);
	
	
	/**
	 * 获得帮帮忙图片列表
	 * 
	 * @param CyServPic cyServPic
	 * @return List<CyServPic>
	 * 
	 */
	public List<CyServPic> getServPicList(CyServPic cyServPic);
	
	
	/**
	 * 获得帮帮忙总列表中的图片
	 * 
	 * @param CyServ cyServ
	 * @return List<CyServPic>
	 * 
	 */
	public List<CyServPic> getServPicListForServDetail(CyServ cyServ);
	
	
	/**
	 * 获得评论列表
	 * 
	 * @param CyServComment cyServComment
	 * @return List<CyServComment>
	 * 
	 */
	public List<CyServComment> getServCommentList(CyServComment cyServComment);
	
	
	/**
	 * 获得评论总数
	 * 
	 * @param CyServComment cyServComment
	 * @return long
	 * 
	 */
	public long countServComment(CyServComment cyServComment);
	
	
	/**
	 * 获得举报总数
	 * 
	 * @param CyServComplaint cyServComplaint
	 * @return long
	 * 
	 */
	public long countServComplaint(CyServComplaint cyServComplaint);
	
	/**
	 * 获得收藏总数
	 * 
	 * @param CyServFavorite cyServFavorite
	 * @return long
	 * 
	 */
	public long countServFavorite(CyServFavorite cyServFavorite);
	
	
	/**
	 * 获得点赞总数
	 * 
	 * @param CyServPraise cyServPraise
	 * @return long
	 * 
	 */
	public long countServPraise(CyServPraise cyServPraise);
	
	
	/**
	 * 添加举报
	 * 
	 * @param CyServComplaint cyServComplaint
	 * 
	 */
	public void insertServComplaint(CyServComplaint cyServComplaint);
	
	
	/**
	 * 添加评论
	 * 
	 * @param CyServComment cyServComment
	 * 
	 */
	public void insertServComment(CyServComment cyServComment);
	
	
	/**
	 * 删除评论
	 * 
	 * @param CyServComment cyServComment
	 * 
	 */
	public void deleteServComment(CyServComment cyServComment);
	
	
	/**
	 * 通过点赞对象中的参数点赞
	 * 
	 * @param CyServPraise cyServPraise
	 * 
	 */
	public void servPraise(CyServPraise cyServPraise);
	
	
	/**
	 * 通过收藏对象中的参数收藏
	 * 
	 * @param CyServFavorite cyServFavorite
	 * 
	 */
	public void servFavorite(CyServFavorite cyServFavorite);
	
	
	/**
	 * 添加赞
	 * 
	 * @param CyServPraise cyServPraise
	 * 
	 */
	public void insertServPraise(CyServPraise cyServPraise);
	
	
	/**
	 * 删除赞
	 * 
	 * @param CyServPraise cyServPraise
	 * 
	 */
	public void deleteServPraise(CyServPraise cyServPraise);
	
	
	/**
	 * 删除帮帮忙
	 * 
	 * @param CyServ cyServ
	 * 
	 */
	public void deleteServ(CyServ cyServ);
	

	/**
	 * 插入联系XXX表
	 * 
	 * @param CyContact cyContact
	 * 
	 */
	public void insertContact(CyContact cyContact);
	
	
	/**
	 * 获得联系XXX列表
	 * 
	 * @param CyContact cyContact
	 * @return List<CyContact>
	 * 
	 */
	public List<CyContact> getContactList(CyContact cyContact);

	public long getContactListCount(CyContact cyContact);
	/**
	 * 获得省会城市列表
	 * 
	 * @return List<Map<String,String>>
	 * 
	 */
	public List<Map<String,String>> getMobProvinceCapital();
	

	/**
	 * 获得省列表
	 * 
	 * @return List<Map<String,String>>
	 * 
	 */
	public List<Map<String,String>> getMobProvince();
	
	/**
	 * 获得省通过省获取市列表
	 * 
	 * @param Map<String, Object> requestMap
	 * @return List<Map<String,String>>
	 * 
	 */
	public List<Map<String,String>> getMobCapitalFromProvince(Map<String, Object> requestMap);
	
	/**
	 * 获得省列表
	 * 
	 * @return List<Map<String,String>>
	 * 
	 */
	public List<Map<String,String>> getMobProvinceAndId();
	
	
}
