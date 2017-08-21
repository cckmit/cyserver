package com.cy.core.region.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: Province</p>
 * <p>Description: 省实体类</p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午12:13:58
 */
public class Province implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键ID */
	private Integer id;
	/** 省名称 */
	private String provinceName;
	/** 排序 */
	private Integer orderId;
	/** 国家ID */
	private Integer countryId;
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
	/** 城市List */
	private List<City> cities;

	private String provincialCapital ;	// 省会
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
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
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public String getProvincialCapital() {
		return provincialCapital;
	}

	public void setProvincialCapital(String provincialCapital) {
		this.provincialCapital = provincialCapital;
	}
}