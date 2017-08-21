package com.cy.core.region.service;

import java.util.List;

import com.cy.core.region.entity.Country;

/**
 * <p>Title: CountryService</p>
 * <p>Description: 处理国家的Service </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午3:27:32
 */
public interface CountryService {
	/**
	 * 查询所有国家
	 * @return
	 */
	List<Country> selectAll();
	
	/**
	 * 
	 * @param country
	 * 			保存国家记录
	 */
	void save(Country country);

	/**
	 * 
	 * @param country
	 * 			修改国家记录
	 */
	void update(Country country);

	/**
	 * 
	 * @param country
	 * 			删除国家记录
	 */			
	void delete(Country country);
	
	/**
	 * 
	 * @param country
	 * @return
	 * 			通过国家名称查询记录数量
	 */
	int countByCountryName(Country country);

	/**
	 *
	 * @param country
	 * @return
	 * 			通过国家Id查询记录
	 */
}
