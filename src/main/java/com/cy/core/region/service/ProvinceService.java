package com.cy.core.region.service;

import java.util.List;

import com.cy.core.region.entity.Province;

/**
 * <p>Title: ProvinceService</p>
 * <p>Description: 处理省份的Service </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午3:28:04
 */
public interface ProvinceService {
	/**
	 * 根据国家ID查询所有省份
	 * 
	 * @return
	 */
	List<Province> selectByCountryId(int countryId);
	
	/**
	 * 
	 * @param industry
	 * 			保存省份记录
	 */
	void save(Province province);

	/**
	 * 
	 * @param industry
	 * 			修改省份记录
	 */
	void update(Province province);

	/**
	 * 
	 * @param id
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
