package com.cy.core.region.service;

import java.util.List;

import com.cy.system.GetDictionaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.region.dao.ProvinceMapper;
import com.cy.core.region.entity.Province;

@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {

	@Autowired
	private ProvinceMapper provinceMapper;

	@Override
	public List<Province> selectByCountryId(int countryId) {
		return provinceMapper.selectByCountryId(countryId);
	}

	@Override
	public void save(Province province) {
		provinceMapper.save(province);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void update(Province province) {
		provinceMapper.update(province);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void delete(Province province) {
		provinceMapper.delete(province);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public int countByProvinceName(Province province) {
		return provinceMapper.countByProvinceName(province);
	}

	@Override
	public Province selectByProvinceId(Province province) {
		return provinceMapper.selectByProvinceId(province);
	}

}