package com.cy.core.deptAttr.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Cha0res on 2016/7/25.
 */
public class DeptAttr implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long alumniId;
    private String alumniName;
    private String region;
    private String mainType;
    private String xueyuanId;
    private String industryCode;
    private Long parentId;
    private String delState;
    private String isUsed;
    private Long level;
    private String sequence;
    private String parent;
    private String admin;
    private String industry;
    private String hobby;
    private String introduction;
    private Integer status;
    private Long createById;
    private Long accountNum;
    private Date createTime;
    private Integer checkFlag;

    private Long memberCount;
    private String presidentName;
    private String telephone;
    private String address;
    private String email;

    public Long getAlumniId() {
        return alumniId;
    }

    public void setAlumniId(Long alumniId) {
        this.alumniId = alumniId;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateById() {
        return createById;
    }

    public void setCreateById(Long createById) {
        this.createById = createById;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }

    public String getPresidentName() {
        return presidentName;
    }

    public void setPresidentName(String presidentName) {
        this.presidentName = presidentName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	/*lixun 2016-7-12*/

    public String getMainType() {
        return mainType;
    }
    public void setMainType(String MainType) {
        mainType = MainType;
    }

    public String getXueyuanId() {
        return xueyuanId;
    }
    public void setXueyuanId(String XueyuanId) {
        xueyuanId = XueyuanId;
    }

    public String getIndustryCode() {
        return industryCode;
    }
    public void getIndustryCode(String IndustryCode) {
        industryCode = IndustryCode;
    }

    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long ParentId) {
        parentId = ParentId;
    }

    public String getDelState() {
        return delState;
    }
    public void getDelState(String DelState) {
        delState = DelState;
    }

    public String getIsUsed() {
        return isUsed;
    }
    public void setIsUsed(String IsUsed) {
        isUsed = IsUsed;
    }

    public Long getLevel() {
        return level;
    }
    public void setLevel(Long Level) {
        level = Level;
    }

    public String getSequence() {
        return sequence;
    }
    public void setSequence(String Sequence) {
        sequence = Sequence;
    }

    public String getParent() {
        return parent;
    }
    public void getParent(String Parent) {
        parent = Parent;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
	/*end lixun*/

}
