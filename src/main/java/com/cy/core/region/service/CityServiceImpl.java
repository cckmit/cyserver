package com.cy.core.region.service;

import java.util.List;

import com.cy.system.GetDictionaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.region.dao.CityMapper;
import com.cy.core.region.entity.City;

@Service("cityService")
public class CityServiceImpl implements CityService {

	@Autowired
	private CityMapper cityMapper;

	@Override
	public List<City> selectByProvinceId(int provinceId) {
		return cityMapper.selectByProvinceId(provinceId);
	}

	@Override
	public void save(City city) {
		cityMapper.save(city);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void update(City city) {
		cityMapper.update(city);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void delete(City city) {
		cityMapper.delete(city);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public int countByCityName(City city) {
		return cityMapper.countByCityName(city);
	}

}