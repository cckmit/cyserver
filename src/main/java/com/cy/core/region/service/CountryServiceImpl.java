package com.cy.core.region.service;

import java.util.List;

import com.cy.system.GetDictionaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.region.dao.CountryMapper;
import com.cy.core.region.entity.Country;

@Service("countryService")
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryMapper countryMapper;

	@Override
	public List<Country> selectAll() {
		return countryMapper.selectAll();
	}

	@Override
	public void save(Country country) {
		countryMapper.save(country);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void update(Country country) {
		countryMapper.update(country);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public void delete(Country country) {
		countryMapper.delete(country);
		GetDictionaryInfo.getInstance().loadRegionDict();
	}

	@Override
	public int countByCountryName(Country country) {
		return countryMapper.countByCountryName(country);
	}

//    @Override
//    public Country selectByCountryId(Country country) {
//        return countryMapper.selectByCountryId(country);
//    }


/*	public Country selectByCountryId(Country country) {
		return countryMapper.selectByCountryId(country);
	}*/


}
