package ltd.moore.ctravel.experience.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * ServiceTime对象
 * @author caicai
 * @version 1.0
 */

public class ServiceTimeVO implements Serializable {
	/*
	 *服务时间ID
	 */
	private String serviceTimeId;
	
	/*
	 *体验ID
	 */
	private String experienceId;
	
	/*
	 *服务日期
	 */
	private Date serviceDate;
	
	/*
	 *服务开始时间
	 */
	private Timestamp   startTime;
	
	/*
	 *服务结束时间
	 */
	private Timestamp endTime;
	
	public ServiceTimeVO(){}
	
	public String getServiceTimeId() {
		return  serviceTimeId;
	}

	public void setServiceTimeId(String  serviceTimeId) {
		 this.serviceTimeId = serviceTimeId;
	}
	
	public String getExperienceId() {
		return  experienceId;
	}

	public void setExperienceId(String  experienceId) {
		 this.experienceId = experienceId;
	}
	
	public Date getServiceDate() {
		return  serviceDate;
	}

	public void setServiceDate(Date  serviceDate) {
		 this.serviceDate = serviceDate;
	}
	
	public Timestamp  getStartTime() {
		return  startTime;
	}

	public void setStartTime( Timestamp  startTime) {
		 this.startTime = startTime;
	}
	
	public Timestamp  getEndTime() {
		return  endTime;
	}

	public void setEndTime(Timestamp   endTime) {
		 this.endTime = endTime;
	}
	
}