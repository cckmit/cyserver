package com.cy.core.region.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: Region</p>
 * <p>Description: Region类  </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月13日 下午4:38:31
 */
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 区域编号( 国家:3+国家id; 省份:2+省份id; 城市: 1+城市id;区县: 区县id)
	private String name;//区域名称
	private String pid;//区域父编号
	private String orderId;//排序
	private String areaCode;//城市区号
	private String postCode;//邮编(只针对区县)
	private String level;//层级(1-国家; 2-省份; 3-城市; 4-区县)

	private String state;	//lixun

	/**
	 * 获得原始表编号
	 * @return
     */
	public String getOldId() {

		String oldId = id.substring(id.indexOf(".")+1);
		return  oldId;
	}

	public static void main(String[] args) {
		String id1 = "3.12" ;
		String id2 = "2.23" ;
		String id3 = "1.35" ;
		String id4 = "46" ;

		System.out.println("id1 . index : " + id1.indexOf(".")) ;
		System.out.println("id2 . index : " + id2.indexOf(".")) ;
		System.out.println("id3 . index : " + id3.indexOf(".")) ;
		System.out.println("id4 . index : " + id4.indexOf(".")) ;


		String oldId1 = id1.substring(id1.indexOf(".")+1);
		String oldId2 = id2.substring(id2.indexOf(".")+1);
		String oldId3 = id3.substring(id3.indexOf(".")+1);
		String oldId4 = id4.substring(id4.indexOf(".")+1);

		System.out.println("oldId1 -> " + oldId1);
		System.out.println("oldId2 -> " + oldId2);
		System.out.println("oldId3 -> " + oldId3);
		System.out.println("oldId4 -> " + oldId4);


		String id = "1" ;
		String oldId ;
		if(id.indexOf("3.") == 0 || id.indexOf("2.") == 0 || id.indexOf("1.") == 0 ) {
			oldId = id.substring(2) ;
		} else {
			oldId = id ;
		}
		System.out.println("oldId -> "+oldId) ;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderid) {
		this.orderId = orderid;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}