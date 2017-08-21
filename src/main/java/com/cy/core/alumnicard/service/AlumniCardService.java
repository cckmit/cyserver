package com.cy.core.alumnicard.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.alumnicard.entity.AlumniCard;
import com.cy.core.alumnicard.entity.AlumniCardStatistical;


public interface AlumniCardService {
	
	/**
	 * 存储
	 * 
	 * @param alumniCard
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean save(AlumniCard alumniCard);
	
	
	/**
	 * 更新
	 * 
	 * @param alumniCard
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean update(AlumniCard alumniCard);
	
	/**
	 * 获取总条数
	 * 
	 * @param map
	 * @return long
	 */
	long count(Map<String, Object> map);	
	
	/**
	 * 所有信息列表(带分页)
	 * 
	 * @param map
	 * @return DataGrid<AlumniCard>
	 */
	DataGrid<AlumniCard> dataGrid(Map<String, Object> map);
	
	/**
	 * 删除
	 * 
	 * @param ids
	 */
	void delete(String ids);
	

	/**
	 * 批量通过
	 * 
	 * @param ids
	 */
	void checkToPass(String ids);
	
	
	/**
	 * 批量未通过
	 * 
	 * @param ids
	 */
	void checkToNotPass(String ids);
	
	
	/**
	 * 获取详情
	 * 
	 * @param
	 * @return List<AlumniCard>
	 */
	AlumniCard selectById(String id);


	/**
	 * 申请修改校友卡接口
	 * @param message
	 * @param content
	 */

	void saveAlumniCard(Message message, String content);

	/**
	 * 查看校友卡接口
	 * @param message
	 * @param content
	 */

	void findAlumniCard(Message message, String content);


	/**
	 * 查看个人的一部分信息
	 * @param message
	 * @param content
	 */
	void findUserInfoForCard(Message message, String content);
}
