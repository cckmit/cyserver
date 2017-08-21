package ltd.moore.ctravel.experience.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Experience对象
 * @author caicai
 * @version 1.0
 */

public class ExperienceVO implements Serializable {
	
	/*
	 *体验ID
	 */
	private String experienceId;
	/*
	 *客户ID
	 */
	private String customerId;
	/*
	 *体验明细ID
	 */
	private String experienceDetailId;
	
	/*
	 *货币类型
	 */
	private String currencyType;
	
	/*
	 *价格
	 */
	private String price;
	
	/*
	 *激活标志符
	 */
	private int enabled;

	/*
     *创建时间
     */
	private Timestamp createTime;
	/*
	 *修改时间
	 */
	private Timestamp updateTime;
	public ExperienceVO(){}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getExperienceId() {
		return  experienceId;
	}

	public void setExperienceId(String  experienceId) {
		 this.experienceId = experienceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getExperienceDetailId() {
		return  experienceDetailId;
	}

	public void setExperienceDetailId(String  experienceDetailId) {
		 this.experienceDetailId = experienceDetailId;
	}
	
	public String getCurrencyType() {
		return  currencyType;
	}

	public void setCurrencyType(String  currencyType) {
		 this.currencyType = currencyType;
	}
	
	public String getPrice() {
		return  price;
	}

	public void setPrice(String  price) {
		 this.price = price;
	}
	
	public int getEnabled() {
		return  enabled;
	}

	public void setEnabled(int  enabled) {
		 this.enabled = enabled;
	}
	
}