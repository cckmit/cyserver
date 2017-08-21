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
import com.cy.core.region.entity.Area;
import com.cy.core.region.service.AreaService;

@Namespace("/area")
@Action(value = "areaAction")
public class AreaAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(AreaAction.class);
	
	private int cityId;

	@Autowired
	private AreaService areaService;

	private Area area;
	
	public void doNotNeedSecurity_getArea2ComboBox() {
		super.writeJson(areaService.selectByCityId(cityId));
	}

	public void save() {
		Message message = new Message();
		try {
			int count = areaService.countByAreaName(area);
			if (count > 0) {
				message.setMsg("县区名称已被占用");
				message.setSuccess(false);
			} else {
				area.setCreateuser(getUser().getUserId()+"");
				area.setCreatetime(new Date());
				area.setDelstate("0");
				areaService.save(area);
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
			int count = areaService.countByAreaName(area);
			if (count > 0) {
				message.setMsg("县区名称已被占用");
				message.setSuccess(false);
			} else {
				area.setUpdateuser(getUser().getUserId()+"");
				area.setUpdatetime(new Date());
				areaService.update(area);
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
			List<Area> areas = new ArrayList<Area>();
			areas.add(area);
			for (Area area : areas) {
				areaService.delete(area);
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
	
	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}