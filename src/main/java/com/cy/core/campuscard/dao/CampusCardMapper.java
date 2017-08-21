package com.cy.core.campuscard.dao;

import java.util.List;
import java.util.Map;


import com.cy.base.entity.DataGrid;
import com.cy.core.campuscard.entity.CampusCard;
import com.cy.core.campuscard.entity.CampusCardStatistical;

public interface CampusCardMapper {

	
	/**
	 * 存储
	 * 
	 * @param CampusCardService campusCard
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean save(CampusCard campusCard);
	
	
	/**
	 * 更新
	 * 
	 * @param CampusCardService campusCard 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean update(CampusCard campusCard);
	
	/**
	 * 获取总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */
	long count(Map<String, Object> map);
	
	/**
	 * 获取列表
	 * 
	 * @param Map<String, Object> map
	 * @return List<CampusCard>
	 */
	List<CampusCard> list(Map<String, Object> map);
	
	/**
	 * 删除
	 * 
	 * @param List<Long> list
	 */
	void delete(List<Long> list);
	
	
	/**
	 * 逻辑删除
	 * 
	 * @param List<Long> list
	 */
	void deletion(List<Long> list);
	
	
	/**
	 * 批量通过
	 * 
	 * @param List<Long> list
	 */
	void checkToPass(List<Long> list);
	
	
	/**
	 * 批量未通过
	 * 
	 * @param List<Long> list
	 */
	void checkToNotPass(List<Long> list);
	
	
	/**
	 * 获取详情
	 * 
	 * @param map
	 * @return List<CampusCard>
	 */
	CampusCard selectById(long id);
	
	
	
	
	
	/**
	 * 统计总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */
	long statisticalCount(Map<String, Object> map);
	
	/**
	 * 统计列表
	 * 
	 * @param Map<String, Object> map
	 * @return List<CampusCardStatistical>
	 */
	List<CampusCardStatistical> statisticalList(Map<String, Object> map);
	
	
	/**
	 * 统计信息列表(带分页)
	 * 
	 * @param Map<String, Object> map
	 * @return DataGrid<CampusCardStatistical>
	 */
	DataGrid<CampusCardStatistical> statisticalDataGrid(Map<String, Object> map);

}
