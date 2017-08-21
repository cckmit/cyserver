package com.cy.core.alumnicard.dao;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.alumnicard.entity.AlumniCard;
import com.cy.core.alumnicard.entity.AlumniCardStatistical;


public interface AlumniCardMapper {

	/**
	 * 存储
	 *
	 * @param AlumniCardService alumniCard
	 * @return true，成功；false，失败；
	 *
	 */
	boolean save(AlumniCard alumniCard);


	/**
	 * 更新
	 *
	 * @param AlumniCardService alumniCard
	 * @return true，成功；false，失败；
	 *
	 */
	boolean update(AlumniCard alumniCard);

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
	 * @return List<AlumniCard>
	 */
	List<AlumniCard> list(Map<String, Object> map);


	/**
	 * 删除
	 *
	 * @param List<Long> list
	 */
	void delete(List<String> list);

	/**
	 * 批量通过
	 *
	 * @param List<Long> list
	 */
	void checkToPass(List<String> list);


	/**
	 * 批量未通过
	 *
	 * @param List<Long> list
	 */
	void checkToNotPass(List<String> list);


	/**
	 * 获取详情
	 *
	 * @param map
	 * @return List<AlumniCard>
	 */
	AlumniCard selectById(String id);

	AlumniCard selectById(long id);



	/**
	 * 获取所有城市
	 *
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>>getNationalOfCity();








	/**
	 * 统计总条数
	 *
	 * @param Map<String, Object> map
	 * @return long
	 */

}
