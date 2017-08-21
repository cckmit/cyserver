package com.cy.core.region.service;

import java.util.List;

import com.cy.system.GetDictionaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.region.dao.AreaMapper;
import com.cy.core.region.entity.Area;

@Service("areaService")
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;

	@Override
	public List<Area> selectByCityId(int cityId) {
		return areaMapper.selectByCityId(cityId);
	}

	@Override
	public void save(Area area) {
		areaMapper.save(area);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void update(Area area) {
		areaMapper.update(area);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void delete(Area area) {
		areaMapper.delete(area);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public int countByAreaName(Area area) {
		return areaMapper.countByAreaName(area);
	}

}