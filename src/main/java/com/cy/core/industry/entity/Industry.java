package com.cy.core.industry.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: Industry</p>
 * <p>Description: 行业实体类</p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月12日 上午10:54:20
 */
public class Industry implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private int id;
	/** 行业代码 */
	private String code;
	/** 行业名称 */
	private String value;
	/** 排序 */
	private int orderNo;
	/** 父CODE */
	private String parentCode;
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



	private String state;	//lixun 打开状态
	public String getState() {	//lixun 打开状态
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	/** add by jiangling*/
	/** 行业序列号=该行业的上级sequence+该行业的本级代码*/
	private String sequence;

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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