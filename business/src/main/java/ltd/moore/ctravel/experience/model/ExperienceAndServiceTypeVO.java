package ltd.moore.ctravel.experience.model;

import java.io.Serializable;

/**
 * ExperienceAndServiceType对象
 * @author caicai
 * @version 1.0
 */

public class ExperienceAndServiceTypeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 *服务类型ID
	 */
	private String serviceTypeId;
	
	/*
	 *体验ID
	 */
	private String experienceId;
	
	public ExperienceAndServiceTypeVO(){}
	
	public String getServiceTypeId() {
		return  serviceTypeId;
	}

	public void setServiceTypeId(String  serviceTypeId) {
		 this.serviceTypeId = serviceTypeId;
	}
	
	public String getExperienceId() {
		return  experienceId;
	}

	public void setExperienceId(String  experienceId) {
		 this.experienceId = experienceId;
	}
	
}