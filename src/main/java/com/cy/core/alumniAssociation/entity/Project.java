package com.cy.core.alumniAssociation.entity;

import com.cy.core.project.entity.ProjectCost;
import com.cy.core.user.entity.User;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	private long projectId;
	private String projectName;
	private double donationMoney;
	private String remark;
	private Date createTime;
	private long createId;
	private int seq;
	private String introduction;
	private String content;
	private String projectPic;
	private String projectPicUrl;
	private String projectPic_xd;
	private double target;
	private String endTime;
	private String hasTarget;
	private String hasEndTime;
	private String startTime;
	private String rateOfProgress;
	private String countPeople;
	private String rateOfTime;
	private String timeStatus;
	private String status;
	private String costMoney;
	private String restMoney;
	private String isCommand;	//是否推荐项目, 0：否, 1：是

	private String organization;

	private String projectType; //捐赠项目类型
	/*private String [] projectTypes;
	private String projectTypeStr;*/

	private String evm;

	private String donateUrl;//爱心捐赠项目详情url

	private String donateUrl_xd;//爱心捐赠项目详情相对url

	private User user;
    private String founProject;
	private String isFoun;

	public String getIsFoun() {
		return isFoun;
	}

	public void setIsFoun(String isFoun) {
		this.isFoun = isFoun;
	}

	public String getFounProject() {
		return founProject;
	}

	public void setFounProject(String founProject) {
		this.founProject = founProject;
	}

	private List<ProjectCost> projectProgress;

	private String currentDonate;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public double getDonationMoney() {
		return donationMoney;
	}

	public void setDonationMoney(double donationMoney) {
		this.donationMoney = donationMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getCreateId() {
		return createId;
	}

	public void setCreateId(long createId) {
		this.createId = createId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProjectPic() {
		if(StringUtils.isBlank(projectPic) && StringUtils.isNotBlank(projectPicUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(projectPicUrl.indexOf("http") < 0) {
				projectPic = Global.URL_DOMAIN + projectPicUrl ;
			}else{
				projectPic=projectPicUrl;
			}

		}
		return projectPic;
	}

	public void setProjectPic(String projectPic) {
		this.projectPic = projectPic;
	}

	public String getProjectPicUrl() {
		if(StringUtils.isBlank(projectPicUrl) && StringUtils.isNotBlank(projectPic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(projectPic.indexOf(Global.URL_DOMAIN) == 0) {
				projectPicUrl = projectPic.substring(Global.URL_DOMAIN.length()) ;
			}else{
				projectPicUrl=projectPic;
			}
		}
		return projectPicUrl;
	}

	public void setProjectPicUrl(String projectPicUrl) {
		this.projectPicUrl = projectPicUrl;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public String getEndTime() {
		if("0".equals(hasEndTime)){
			endTime = null;
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHasEndTime() {
		return hasEndTime;
	}

	public void setHasEndTime(String hasEndTime) {
		this.hasEndTime = hasEndTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRateOfProgress() {
		if("1".equals(hasTarget)&&target > 0 && donationMoney>0){
			rateOfProgress = String.format("%.2f",donationMoney/target*100);
		}else{
			rateOfProgress = "0.0";
		}
		return rateOfProgress;
	}

	public void setRateOfProgress(String rateOfProgress) {
		this.rateOfProgress = rateOfProgress;
	}

	public String getHasTarget() {
		return hasTarget;
	}

	public void setHasTarget(String hasTarget) {
		this.hasTarget = hasTarget;
	}

	public String getCountPeople() {
		return countPeople;
	}

	public void setCountPeople(String countPeople) {
		this.countPeople = countPeople;
	}

	public String getRateOfTime() {
		if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(rateOfTime)){
			rateOfTime = com.cy.common.utils.StringUtils.formateRate(rateOfTime);
		}
		return rateOfTime;
	}

	public void setRateOfTime(String rateOfTime) {
		this.rateOfTime = rateOfTime;
	}

	public String getProjectPic_xd() {
		if(StringUtils.isBlank(projectPic_xd) && StringUtils.isNotBlank(projectPicUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(projectPicUrl.indexOf("http") == 0) {
				projectPic_xd = projectPicUrl.substring(Global.URL_DOMAIN.length()) ;
			}else{
				projectPic_xd=projectPicUrl;
			}
		}
		return projectPic_xd;
	}

	public void setProjectPic_xd(String projectPic_xd) {
		this.projectPic_xd = projectPic_xd;
	}

	public String getTimeStatus() {
		return timeStatus;
	}

	public void setTimeStatus(String timeStatus) {
		this.timeStatus = timeStatus;
	}

	public String getStatus() {
		if(StringUtils.isBlank(status)){
			status = "0";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCostMoney() {
		if(StringUtils.isBlank(costMoney)){
			costMoney = "0";
		}
		return costMoney;
	}

	public void setCostMoney(String costMoney) {
		this.costMoney = costMoney;
	}

	public String getRestMoney() {
		if(donationMoney > 0){
			if(StringUtils.isNotBlank(costMoney)){
				double cost = Double.parseDouble(costMoney);
				restMoney = String.valueOf(donationMoney-cost);
			}else{
				restMoney = String.valueOf(donationMoney);
			}
		}else{
			restMoney = "0";
		}
		return restMoney;
	}

	public void setRestMoney(String restMoney) {
		this.restMoney = restMoney;
	}

	public List<ProjectCost> getProjectProgress() {
		return projectProgress;
	}

	public void setProjectProgress(List<ProjectCost> projectProgress) {
		this.projectProgress = projectProgress;
	}
	
	public String getOrganization() {
		if(StringUtils.isNotBlank(Global.FOUNDATION_NAME)){
			organization = Global.FOUNDATION_NAME;
		}else if(StringUtils.isNotBlank(Global.schoolSign)){
			organization = Global.schoolSign;
		}else{
			organization = "";
		}
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getProjectType() {
		if (StringUtils.isNotBlank(projectType)){
			projectType = projectType.replaceAll(" ", "");
		}
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}


	public String getEvm() {
		return evm;
	}

	public void setEvm(String evm) {
		this.evm = evm;
	}

	public String getCurrentDonate() {
		if(StringUtils.isBlank(currentDonate)){
			currentDonate = "0";
		}
		return currentDonate;
	}

	public void setCurrentDonate(String currentDonate) {
		this.currentDonate = currentDonate;
	}

	public String getIsCommand() {
		return isCommand;
	}

	public void setIsCommand(String isCommand) {
		this.isCommand = isCommand;
	}

	public String getDonateUrl() {
		return donateUrl;
	}

	public void setDonateUrl(String donateUrl) {
		this.donateUrl = donateUrl;
	}

	public String getDonateUrl_xd() {
		return donateUrl_xd;
	}

	public void setDonateUrl_xd(String donateUrl_xd) {
		this.donateUrl_xd = donateUrl_xd;
	}
}
