package com.cy.core.region.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cy.core.region.entity.Region;
import com.cy.core.region.service.RegionService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.region.entity.Country;
import com.cy.core.region.service.CountryService;

@Namespace("/country")
@Action(value = "countryAction")
public class CountryAction extends AdminBaseAction {
	
	private static final Logger logger = Logger.getLogger(CountryAction.class);

	//add by jiangling
	@Autowired
	private RegionService regionService;

	@Autowired
	private CountryService countryService;

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	private Country country;
	private Region region;

//	public String getSid() {
//		return sid;
//	}
//
//	public void setSid(String sid) {
//		this.sid = sid;
//	}
//
//	private String sid;
	
	public void doNotNeedSecurity_getCountry2ComboBox() {
		List<Country> list = countryService.selectAll();
		super.writeJson(list);
	}
	
	public void save() {
		Message message = new Message();
		try {
			if(country==null){
				message.setMsg("传参为空");
				message.setSuccess(false);
				return;
			}
			int count = countryService.countByCountryName(country);
			if (count > 0) {
				message.setMsg("国家名称已被占用");
				message.setSuccess(false);
			} else {
				country.setCreateuser(getUser().getUserId()+"");
				country.setCreatetime(new Date());
				country.setDelstate("0");
				countryService.save(country);
				message.setMsg("新增成功");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			int count = countryService.countByCountryName(country);
			if (count > 0) {
				message.setMsg("国家名称已被占用");
				message.setSuccess(false);
			} else {
				country.setUpdateuser(getUser().getUserId()+"");
				country.setUpdatetime(new Date());
				countryService.update(country);
				message.setMsg("修改成功");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			List<Country> countries = new ArrayList<Country>();
			countries.add(country);
			for (Country city : countries) {
				countryService.delete(city);
			}
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	

	
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
}
