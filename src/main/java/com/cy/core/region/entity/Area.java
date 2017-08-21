package com.cy.core.region.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: Area</p>
 * <p>Description: 区实体类 </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午12:15:58
 */
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 主键ID */
	private Integer id;
	/** 城市ID */
	private Integer cityId;
	/** 区名称 */
	private String areaName;
	/** 邮政编码 */
	private String postCode;
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

}