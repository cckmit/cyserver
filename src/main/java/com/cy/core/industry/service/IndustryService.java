package com.cy.core.industry.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.industry.entity.Industry;
import com.cy.core.user.entity.User;

/**
 * <p>Title: IndustryService</p>
 * <p>Description: 行业Service类 </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月12日 上午11:31:33
 */
public interface IndustryService {
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
	 * @return 查询行业集合
	 */
	List<Industry> selectIndustryList(Map<String, Object> map);
	
	/**
	 * 
	 * @param industry
	 * 			保存行业记录
	 */
	void save(Industry industry);

	/**
	 * 
	 * @param id
	 * 			通过code删除行业记录
	 */			
	void delete(String code);

	/**
	 * 
	 * @param code
	 * @return 
	 * 			通过行业code查询记录数量
	 */
	int countByIndustryCode(Industry industry);

	/**
	 * 
	 * @param value
	 * @return
	 * 			通过行业value查询记录数量
	 */
	int countByIndustryValue(Industry industry);

	void parseTree(List<TreeString> tree,List<TreeString> allList);
	
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
	 * 通过code parentCode 来查询一条Industry类型的数据
     */
	Industry selectIndustryByParentCode(String code);

	/**
	 * add by jiangling
	 * @param map
	 * @return
	 * 通过页面的查询条件来查询树形结构的行业记录
     */
	List<Industry> selectIndustryTrees(Map<String, Object> map);

	/**
	 * 获取行业列表接口
	 * @param message
	 * @param content
     */
	void findIndustryList(Message message, String content);

	/**
	 * author chensheng
	 * 导入行业xsl
	 * @param url
	 * @param user
	 * @return
	 */
	String importDataBeforeThread( String url, User user );
}
