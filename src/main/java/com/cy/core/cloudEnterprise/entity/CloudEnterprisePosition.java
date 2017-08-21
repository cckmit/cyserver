package com.cy.core.cloudEnterprise.entity;

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
public class CloudEnterprisePosition extends DataEntity<CloudEnterprisePosition> {

    private static final long serialVersionUID = 1L;

    private String enterpriseId; //校企编号
    private String name;        //岗位名称
    private String city;        //招聘城市
    private String longitude;   //经度
    private String latitude;   //纬度
    private String locationDesc;//位置描述
    private String workExperience;      //经验(0为不限)
    private String salaryMax;        //薪水上限(0为不限)
    private String salaryMin;    //薪水下限(0为不限)
    private String education;     //学历要求(0为不限)
    private String recruitNumber;     //招聘人数(0为不限)
    private String description;     //职位描述
    private String status;     //状态(10:开启；20关闭；)
    private String enterpriseName;  // 企业名称
    private String positionType;  // 岗位类型
    private String enterpriseLogo;  // 企业logo
    private String jobType;  // 工作性质（全职、兼职、实习)
    private String department    ;  // 所属部门
    private Date bussTime       ;  // 招聘截止日期
    private String spotlights     ;  // 职位亮点
    private String cloudPositionId    ;  //云平台职位编号
    private String address;               //工作地点


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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(String recruitNumber) {
        this.recruitNumber = recruitNumber;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Date getBussTime() {
        return bussTime;
    }

    public void setBussTime(Date bussTime) {
        this.bussTime = bussTime;
    }

    public String getSpotlights() {
        return spotlights;
    }

    public void setSpotlights(String spotlights) {
        this.spotlights = spotlights;
    }

    public String getCloudPositionId() {
        return cloudPositionId;
    }

    public void setCloudPositionId(String cloudPositionId) {
        this.cloudPositionId = cloudPositionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
