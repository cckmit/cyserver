package com.cy.core.region.service;

import java.util.List;

import com.cy.core.region.entity.City;

/**
 * <p>Title: CityService</p>
 * <p>Description: 处理城市的Service</p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午3:27:01
 */
public interface CityService {
	/**
	 * 根据省份ID查询所有城市
	 * 
	 * @return
	 */
	List<City> selectByProvinceId(int provinceId);
	
	/**
	 * 
	 * @param industry
	 * 			保存城市记录
	 */
	void save(City city);

	/**
	 * 
	 * @param industry
	 * 			修改城市记录
	 */
	void update(City city);

	/**
	 * 
	 * @param id
	 * 			删除城市记录
	 */			
	void delete(City city);

	/**
	 * 
	 * @param city
	 * @return
	 * 			通过城市名称查询记录数量
	 */
	int countByCityName(City city);
}
