package com.cy.core.roster.entity;

/**
 * 黑白名单
 * @author Administrator
 *
 */
public class Roster {
	
	/**--主键--**/
	private int id;
	/**--引用的id可以是userId,或者机构id--**/
	private String ref_id;
	/**--引用字典表的id--**/
	private int dict_id;
	/**--加入时间--**/
	private String create_time;
	/**--所属于--**/
	private String dict_name;
	/**--类型(1.黑名单,2.白名单)--**/
	private int type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRef_id() {
		return ref_id;
	}
	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}
	public int getDict_id() {
		return dict_id;
	}
	public void setDict_id(int dict_id) {
		this.dict_id = dict_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
