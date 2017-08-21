package com.cy.core.schoolServ.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class SchoolServ implements Serializable {

	/**
	 * 学校服务配置实体类
	 * 
	 * 
	 */
	
	private static final long serialVersionUID = -8460453140427597892L;
	
	
	private long id;//自增长ID
	private String serviceName;//服务名称
	private String systemService;//是否系统服务，0否，1是
	private String provideService;//是否提供服务，0否，1是
	private String serviceUrl;//服务的URL
	private String serviceUrlUrl;	//存储的服务路径
	private String serviceUrl_xd;	//存储的服务路径
	private String servicePic;//服务的缩略图
	private String servicePicUrl;	//存储的路径
	private String servicePic_xd;//服务的缩略图
	private String createBy;//服务创建者
	private String createDate;//创建日期
	private String deptId;	//当前用户所属组织

	private String sort ;	// 排序

	private String group_name;//分组标识
	private String group_sort;//分组排序
	private String is_audit_screen; //是否审核屏蔽
	private String need_authentication;//查看服務是否需要認證

	public String getIs_audit_screen() {
		return is_audit_screen;
	}

	public void setIs_audit_screen(String is_audit_screen) {
		this.is_audit_screen = is_audit_screen;
	}

	public String getGroup_sort() {
		return group_sort;
	}

	public void setGroup_sort(String group_sort) {
		this.group_sort = group_sort;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public long getId() {
		return id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public String getSystemService() {
		return systemService;
	}
	public String getProvideService() {
		return provideService;
	}
	public String getServiceUrl() {
		if(StringUtils.isBlank(serviceUrl) && StringUtils.isNotBlank(serviceUrlUrl) && StringUtils.isNotBlank(Global.cy_server_url)) {
			if(serviceUrlUrl.indexOf("http") < 0) {
				serviceUrl = Global.cy_server_url + serviceUrlUrl ;
			}else{
				serviceUrl = serviceUrlUrl;
			}
		}
		return serviceUrl;
	}
	public String getServicePic() {
		if(StringUtils.isBlank(servicePic) && StringUtils.isNotBlank(servicePicUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(servicePicUrl.indexOf("http") < 0) {
				servicePic = Global.URL_DOMAIN + servicePicUrl ;
			}else{
				servicePic=servicePicUrl;
			}
		}
		return servicePic;
	}
	public String getCreateBy() {
		return createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public void setSystemService(String systemService) {
		this.systemService = systemService;
	}
	public void setProvideService(String provideService) {
		this.provideService = provideService;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public void setServicePic(String servicePic) {
		this.servicePic = servicePic;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getServicePicUrl() {
		if(StringUtils.isBlank(servicePicUrl) && StringUtils.isNotBlank(servicePic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(servicePic.indexOf(Global.URL_DOMAIN) == 0) {
				servicePicUrl = servicePic.substring(Global.URL_DOMAIN.length()) ;
			}else{
				servicePicUrl=servicePic;
			}
		}
		return servicePicUrl;
	}

	public String getServiceUrlUrl() {
		if(StringUtils.isBlank(serviceUrlUrl) && StringUtils.isNotBlank(serviceUrl) && StringUtils.isNotBlank(Global.cy_server_url)) {
			if(serviceUrl.indexOf(Global.cy_server_url) == 0) {
				serviceUrlUrl = serviceUrl.substring(Global.cy_server_url.length()) ;
			}else{
				serviceUrlUrl = serviceUrl;
			}
		}
		return serviceUrlUrl;
	}

	public void setServiceUrlUrl(String serviceUrlUrl) {
		this.serviceUrlUrl = serviceUrlUrl;
	}

	public void setServicePicUrl(String servicePicUrl) {
		this.servicePicUrl = servicePicUrl;
	}

	public String getNeed_authentication() {
		return need_authentication;
	}

	public void setNeed_authentication(String need_authentication) {
		this.need_authentication = need_authentication;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SchoolService [id=<");
		builder.append(id);
		builder.append(">, serviceName=<");
		builder.append(serviceName);
		builder.append(">, systemService=<");
		builder.append(systemService);
		builder.append(">, provideService=<");
		builder.append(provideService);
		builder.append(">, serviceUrl=<");
		builder.append(serviceUrl);
		builder.append(">, servicePic=<");
		builder.append(servicePic);
		builder.append(">, createBy=<");
		builder.append(createBy);
		builder.append(">, createDate=<");
		builder.append(createDate);
		builder.append("]");
		return builder.toString();
	}

	public String getServiceUrl_xd() {
		if(StringUtils.isBlank(serviceUrl_xd) && StringUtils.isNotBlank(serviceUrl) && StringUtils.isNotBlank(Global.cy_server_url)) {
			if(serviceUrl.indexOf(Global.cy_server_url) == 0) {
				serviceUrl_xd = serviceUrl.substring(Global.cy_server_url.length()) ;
			}else{
				serviceUrl_xd = serviceUrl;
			}
		}
		return serviceUrl_xd;
	}

	public void setServiceUrl_xd(String serviceUrl_xd) {
		this.serviceUrl_xd = serviceUrl_xd;
	}

	public String getServicePic_xd() {
		if(StringUtils.isBlank(servicePic_xd) && StringUtils.isNotBlank(servicePic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(servicePic.indexOf(Global.URL_DOMAIN) == 0) {
				servicePic_xd = servicePic.substring(Global.URL_DOMAIN.length()) ;
			}else{
				servicePic_xd=servicePic;
			}
		}
		return servicePic_xd;
	}

	public void setServicePic_xd(String servicePic_xd) {
		this.servicePic_xd = servicePic_xd;
	}
}
