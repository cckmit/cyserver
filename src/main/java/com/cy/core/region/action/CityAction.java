package com.cy.core.region.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.region.entity.City;
import com.cy.core.region.service.CityService;

@Namespace("/city")
@Action(value = "cityAction")
public class CityAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(CityAction.class);
	
	private int provinceId;

	@Autowired
	private CityService cityService;
	
	private City city;

	public void doNotNeedSecurity_getCity2ComboBox() {
		super.writeJson(cityService.selectByProvinceId(provinceId));
	}
	
	public void doNotNeedSessionAndSecurity_getCity2ComboBox() {
		super.writeJson(cityService.selectByProvinceId(provinceId));
	}


	public void save() {
		Message message = new Message();
		try {
			int count = cityService.countByCityName(city);
			if (count > 0) {
				message.setMsg("城市名称已被占用");
				message.setSuccess(false);
			} else {
				city.setCreateuser(getUser().getUserId()+"");
				city.setCreatetime(new Date());
				city.setDelstate("0");
				cityService.save(city);
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
			int count = cityService.countByCityName(city);
			if (count > 0) {
				message.setMsg("城市名称已被占用");
				message.setSuccess(false);
			} else {
				city.setUpdateuser(getUser().getUserId()+"");
				city.setUpdatetime(new Date());
				cityService.update(city);
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
			List<City> citys = new ArrayList<City>();
			citys.add(city);
			for (City city : citys) {
				cityService.delete(city);
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
	
	public void view(){
	}
	
	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
