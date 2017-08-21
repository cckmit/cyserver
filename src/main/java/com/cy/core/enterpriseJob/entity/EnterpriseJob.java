package com.cy.core.enterpriseJob.entity;

import com.cy.base.entity.DataEntity;

import java.util.Date;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 招聘岗位</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
public class EnterpriseJob extends DataEntity<EnterpriseJob> {

    private static final long serialVersionUID = 1L;

    private String enterpriseId; //校企编号
    private String name;        //岗位名称
    private String city;        //招聘城市
    private String longitude;   //经度
    private String latitude;   //纬度
    private String locationDesc;//位置描述
    private String experienceMax;      //经验上限(0为不限)
    private String experienceMin;      //经验下限(0为不限)
    private String salaryMax;        //薪水上限(0为不限)
    private String salaryMin;    //薪水下限(0为不限)
    private String education;     //学历要求(0为不限)
    private String recruitersNum;     //招聘人数(0为不限)
    private String description;     //职位描述
    private String demand;     //职位要求
    private String status;     //状态(10:开启；20关闭；)
    private String auditStatus;     //审核状态(0通过；1不通过；默认0)
    private String auditor;     //审核人编号
    private String enterpriseName;  // 企业名称
    private String type;  // 岗位类型
    private String enterpriseLogo;  // 企业logo
    private String typeName;        //类型名称
    private String auditOpinion;        //审核意见
    private String characteristic;  // 工作性质（全职、兼职、实习)
    private String department    ;  // 所属部门
    private Date endTime       ;  // 招聘截止日期
    private String spotlight     ;  // 职位亮点
    private String cloudJobId    ;  // 云平台职位编号

    private String isCloud;      //条件查询（1：表示只查云平台同步过来的数据）


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEnterpriseLogo() {
        return enterpriseLogo;
    }

    public void setEnterpriseLogo(String enterpriseLogo) {
        this.enterpriseLogo = enterpriseLogo;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getExperienceMax() {
        return experienceMax;
    }

    public void setExperienceMax(String experienceMax) {
        this.experienceMax = experienceMax;
    }

    public String getExperienceMin() {
        return experienceMin;
    }

    public void setExperienceMin(String experienceMin) {
        this.experienceMin = experienceMin;
    }

    public String getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(String salaryMax) {
        this.salaryMax = salaryMax;
    }

    public String getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(String salaryMin) {
        this.salaryMin = salaryMin;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRecruitersNum() {
        return recruitersNum;
    }

    public void setRecruitersNum(String recruitersNum) {
        this.recruitersNum = recruitersNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSpotlight() {
        return spotlight;
    }

    public void setSpotlight(String spotlight) {
        this.spotlight = spotlight;
    }

    public String getCloudJobId() {
        return cloudJobId;
    }

    public void setCloudJobId(String cloudJobId) {
        this.cloudJobId = cloudJobId;
    }

    public String getIsCloud() {
        return isCloud;
    }

    public void setIsCloud(String isCloud) {
        this.isCloud = isCloud;
    }
}
