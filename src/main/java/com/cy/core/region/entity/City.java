package com.cy.core.region.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: City</p>
 * <p>Description: 城市实体类 </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午12:13:36
 */
public class City implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键ID */
	private Integer id;
	/** 省ID */
	private Integer provinceId;
	/** 城市名称 */
	private String cityName;
	/** 城市编号 */
	private String areaCode;
	/** 0:普通城市，1:省会城市，2热门城市  */
	private String type;
	/** 级别 */
	private String level;
	/** 创建人ID */
	private String createuser;
	/** 创建时间 */
	private Date createtime;
	/** 修改人ID */
	private String updateuser;
	/** 修改时间 */
	private Date updatetime;
	/** 删除标记，默认是0，表示未删除，1表示删除。 */
	private String delstate;
	/** 区域List */
	private List<Area> areas;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getDelstate() {
		return delstate;
	}
	public void setDelstate(String delstate) {
		this.delstate = delstate;
	}
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
}