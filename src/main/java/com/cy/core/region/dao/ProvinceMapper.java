package com.cy.core.region.dao;

import java.util.List;

import com.cy.core.region.entity.Province;

/**
 * <p>Title: ProvinceMapper</p>
 * <p>Description: 处理省份的Mapper</p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午3:23:26
 */
public interface ProvinceMapper {
	/**
	 * 根据国家ID查询所有省份
	 * 
	 * @return
	 */
	List<Province> selectByCountryId(int countryId);
	
	/**
	 * 
	 * @param province
	 * 			保存省份记录
	 */
	void save(Province province);

	/**
	 * 
	 * @param province
	 * 			修改省份记录
	 */
	void update(Province province);

	/**
	 * 
	 * @param province
	 * 			删除省份记录
	 */			
	void delete(Province province);

	/**
	 * 
	 * @param province
	 * @return
	 * 			通过省份名称查询记录数量
	 */
	int countByProvinceName(Province province);

	/**
	 *
	 * @param province
	 * @return
	 * 			通过省份Id查询记录
	 */
	Province selectByProvinceId(Province province);
}
