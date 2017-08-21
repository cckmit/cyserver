package com.cy.core.industry.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.industry.entity.Industry;

/**
 * <p>Title: IndustryMapper</p>
 * <p>Description: </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月12日 上午11:27:10
 */
public interface IndustryMapper {
	/**
	 * 查询行业节点下所有子行业节点
	 * 
	 * @param parentCode
	 * @return
	 */
	List<Industry> selectByParentCode(String parentCode);
	
	/**
	 * 
	 * @param map
	 * @return 查询行业记录数量
	 */
	long countIndustry(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 * @return 查询行业集合
	 */
	List<Industry> selectIndustryList(Map<String, Object> map);

	/**
	 *
	 * @param map
	 * @return 查询行业level
	 */

	List<Industry> findIndustryList(Map<String, Object> map);

	/**
	 * 
	 * @param industry
	 * 			保存行业记录
	 */
	void save(Industry industry);

	/**
	 * 
	 * @param industry
	 * 			修改行业记录
	 */
	void update(Industry industry);

	/**
	 * 
	 * @param code
	 * 			通过code删除行业记录
	 */			
	void delete(String code);

	/**
	 * 
	 * @param industry
	 * @return 
	 * 			通过行业code查询记录数量
	 */
	int countByIndustryCode(Industry industry);

	/**
	 * 
	 * @param industry
	 * @return
	 * 			通过行业value查询记录数量
	 */
	int countByIndustryName(Industry industry);

	/**
	 * 
	 * @param code
	 * @return
	 * 			通过code查询行业记录
	 */
	Industry selectIndustryByCode(String code);


	/**
	 * add by jiangling
	 * @param code
	 * @return
	 * 通过父节点查询那条行业记录
     */
	Industry selectIndustryByParentCode(String code);

	/**
	 * add by jiangling
	 * @param map
	 * @return
	 * 通过页面的查询条件来查询树形结构的行业记录
     */
	List<Industry> selectIndustryTrees(Map<String,Object> map);

	/**
	 * author chensheng
	 * @param lub
	 * 批量保存数据
	 */
	void multisave( List<Industry> lub );
}