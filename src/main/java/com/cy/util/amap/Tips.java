package com.cy.util.amap;

public class Tips {
	
	//查询字段（必填）
	private String keywords;
	
	//POI分类（需要定位才能获得应该为手机端应用）
	private String type;
	
	//坐标
	private String location;
	
	//搜索城市（默认全国）
	private String city;
	
	//是否仅返回指定城市数据
	private Boolean citylimit;
	
	//返回的数据类型(all/poi/bus/busline)
	private String datatype;
	
	//数字签名
	private String sig;
	
	//返回数据格式类型（json,xml）
	private String output;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getCitylimit() {
		return citylimit;
	}

	public void setCitylimit(Boolean citylimit) {
		this.citylimit = citylimit;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getParams() {
		return "&keywords=" + this.getKeywords() + "&type=" + this.getType()
				+ "&location=" + this.getLocation() + "&city=" + this.getCity()
				+ "&citylimit=" + this.getCitylimit() + "&datatype="
				+ this.getDatatype() + "&sig=" + this.getSig() + "&output="
				+ this.getOutput();
	}

}
