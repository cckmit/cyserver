package com.cy.util.amap;

public class BusParams {

	//出发点（经度，纬度必填）
	private String origin;
	
	//目的地（经度，纬度必填）
	private String destination;
	
	//城市（如果是跨城出行为起点）
	private String city;
	
	//城市为跨城出行的终点
	private String cityd;
	
	//返回结果详略(base:返回基本信息,all:返回全部信息)
	private String extensions;
	
	//公交换成策略（0：快捷，1：经济，2：少换乘，3：少步行，4：不乘地铁）
	private String strategy;
	
	//是否计算夜班车(0：不计算，1计算)
	private String nightflag;
	
	//出发日期
	private String date;
	
	//出发时间
	private String time;
	
	//数字签名
	private String sig;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityd() {
		return cityd;
	}

	public void setCityd(String cityd) {
		this.cityd = cityd;
	}

	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getNightflag() {
		return nightflag;
	}

	public void setNightflag(String nightflag) {
		this.nightflag = nightflag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}
	
	public String getParams(){
		return "&origin="+this.getOrigin()+"&destination="+this.getDestination()+"&city="+this.getCity()+"&cityd="+this.getCityd()+"&extensions="+this.getExtensions()+"&strategy="+this.getStrategy()+"&nightflag="+this.getNightflag()+"&date="+this.getDate()+"&time="+this.getTime()+"&sig="+this.getSig();
	}
}
