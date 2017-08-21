package ltd.moore.ctravel.experience.model;


import java.io.Serializable;

/**
 * ServiceType对象
 * @author caicai
 * @version 1.0
 */

public class ServiceTypeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 *服务类型ID
	 */
	private String serviceTypeId;
	
	/*
	 *服务名称
	 */
	private String serviceName;
	
	public ServiceTypeVO(){}
	
	public String getServiceTypeId() {
		return  serviceTypeId;
	}

	public void setServiceTypeId(String  serviceTypeId) {
		 this.serviceTypeId = serviceTypeId;
	}
	
	public String getServiceName() {
		return  serviceName;
	}

	public void setServiceName(String  serviceName) {
		 this.serviceName = serviceName;
	}
	
}