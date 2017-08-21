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
import com.cy.core.region.entity.Province;
import com.cy.core.region.service.ProvinceService;

@Namespace("/province")
@Action(value = "provinceAction")
public class ProvinceAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(ProvinceAction.class);
	
	private int countryId;

	@Autowired
	private ProvinceService provinceService;
	
	private Province province;

	public void doNotNeedSecurity_getProvince2ComboBox() {
		super.writeJson(provinceService.selectByCountryId(countryId));
	}
	
	public void doNotNeedSessionAndSecurity_getProvince2ComboBox() {
		super.writeJson(provinceService.selectByCountryId(countryId));
	}

	public void save() {
		Message message = new Message();
		try {
			int count = provinceService.countByProvinceName(province);
			if (count > 0) {
				message.setMsg("省份名称已被占用");
				message.setSuccess(false);
			} else {
				province.setCreateuser(getUser().getUserId()+"");
				province.setCreatetime(new Date());
				province.setDelstate("0");
				provinceService.save(province);
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
			int count = provinceService.countByProvinceName(province);
			if (count > 0) {
				message.setMsg("省份名称已被占用");
				message.setSuccess(false);
			} else {
				province.setUpdateuser(getUser().getUserId()+"");
				province.setUpdatetime(new Date());
				provinceService.update(province);
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
			List<Province> provinces = new ArrayList<Province>();
			provinces.add(province);
			for (Province province : provinces) {
				provinceService.delete(province);
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
		Message message = new Message();
		try {
			Province p = provinceService.selectByProvinceId(province);
			message.setObj(p);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("数据查询失败");
			message.setSuccess(false);
		}
		super.writeJson(message);

	}
	
	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
