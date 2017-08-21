package com.cy.core.region.service;

import java.util.List;

import com.cy.core.region.entity.Area;

/**
 * <p>Title: AreaService</p>
 * <p>Description:处理区的Service </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午3:26:15
 */
public interface AreaService {
	/**
	 * 根据城市ID查询所有县(区)
	 * 
	 * @return
	 */
	List<Area> selectByCityId(int cityId);
	
	/**
	 * 
	 * @param industry
	 * 			保存区记录
	 */
	void save(Area area);

	/**
	 * 
	 * @param industry
	 * 			修改区记录
	 */
	void update(Area area);

	/**
	 * 
	 * @param id
	 * 			删除区记录
	 */			
	void delete(Area area);
	
	/**
	 * 
	 * @param area
	 * @return
	 * 			通过县区名称查询记录数量
	 */
	int countByAreaName(Area area);
}
