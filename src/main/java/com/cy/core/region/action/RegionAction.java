package com.cy.core.region.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.common.utils.CacheUtils;
import com.cy.core.region.entity.*;
import com.cy.common.utils.easyui.TreeStringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.core.region.service.RegionService;

@Namespace("/region")
@Action(value = "regionAction")
public class RegionAction extends AdminBaseAction {
	private static final Logger logger = Logger.getLogger(CountryAction.class);

	@Autowired
	private RegionService regionService;
	
	private Region region;
	private Country country;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	private Province province;
	private City city;
	private Area area;


	//查询条件
	private String strRegion;

	public String getStrRegion() {
		return strRegion;
	}
	public void setStrRegion(String strRegion) {
		this.strRegion = strRegion;
	}

	public String getSid() {
		return sId;
	}

	public void setSid(String sId) {
		this.sId = sId;
	}

	private String sId;

	private String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	//list
	public void dataGrid() {
		List<TreeString> treeList = (List<TreeString>)CacheUtils.get("regionDicts");
		List<TreeString> tree = new ArrayList<TreeString>();

		if(StringUtils.isBlank(strRegion)){
			List<TreeString> treeTemp = TreeStringUtil.getChild(pid,treeList);
			for(TreeString t : treeTemp){
				List<TreeString> treeChild = TreeStringUtil.getChild(t.getId(),treeList);
				if(treeChild.size()>0){
					t.setState("closed");
				}
				tree.add(t);
			}
		}else{
			TreeString treeTemp = new TreeString();
			treeTemp.setText(strRegion);
			tree=TreeStringUtil.findTreeWithParent(treeTemp,treeList);
		}
		super.writeJson(tree);

	}

//	//编辑
//	public void editRegion()
//	{
//		view();
//	}
//	//查看
//	public void viewRegion()
//	{
//		view();
//	}


	public void view() {
		Message message = new Message();
		try {
			Region r = regionService.selectRegionById( sId );
			message.setObj(r);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("数据查询失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	//update
	public void update() {
		Message message = new Message();
		try {
			Region r = regionService.selectRegionById( sId );
			if (r.getName().equals(region.getName()) && r.getId().equals(region.getId())) {
				message.setMsg("地区名称已被占用");
				message.setSuccess(false);
			} else {
				//jiangling
//				String oldId = region.getOldId();
				regionService.update(region);
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

	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}

}