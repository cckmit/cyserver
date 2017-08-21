package com.cy.core.region.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: Country</p>
 * <p>Description: 国家实体类 </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午12:08:09
 */
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主键ID */
	private Integer id;
	/** 国家名称 */
	private String countryName;
	/** 排序 */
	private Integer orderId;
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
	/** 省份List */
	private List<Province> provinces;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
	public List<Province> getProvinces() {
		return provinces;
	}
	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}

}