package com.cy.core.region.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.region.entity.Region;


/**
 * <p>Title: RegionMapper</p>
 * <p>Description: 处理Region的Mapper</p>
 * 
 * @author jiangling
 * @Company 博视创诚
 *
 */
public interface RegionMapper {
	/**
	 * add by jiangling
	 * @param sRegion
	 * @return
	 * 			查询
	 */
	List<Region> selectRegion(String sRegion);
	List<Region> selectAll();
	List<Region> selectAllRegion( );
	List<Region> selectCountryList( );

	Region selectRegionById(String sId);

	void update1(Region sRegion);
	void update2(Region sRegion);
	void update3(Region sRegion);
	void update4(Region sRegion);

	List<Region> selectRegionList(Map<String, Object> map);


}
