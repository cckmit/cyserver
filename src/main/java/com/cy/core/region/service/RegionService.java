package com.cy.core.region.service;

import java.util.List;

import com.cy.base.entity.Message;
import com.cy.core.region.entity.Region;


public interface RegionService {

	/**
	 * add by jiangling
	 * @param region
	 * @return
	 * 无条件和有条件查询,查询地区
     */
	List<Region> selectRegionList( String region );

	/**
	 * add by jiangling
	 * @return
	 * 查询所以得国家
     */
	List<Region> selectCountryList( );

	/**
	 * add by jiangling
	 * @param id
	 * @return
	 * 查询地区
     */
	Region selectRegionById(String id);

	void update(Region region);

	/**
	 * 获取地区列表接口
	 */
	void findRegionList(Message message, String content);
}
