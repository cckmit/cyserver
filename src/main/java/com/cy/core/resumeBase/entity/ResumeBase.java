package com.cy.core.resumeBase.entity;

import com.cy.base.entity.DataEntity;

import java.util.List;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 简历基本信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
public class ResumeBase extends DataEntity<ResumeBase> {
    private static final long serialVersionUID = 1L;

    private String accountNum;        //手机用户
    private String name;        //姓名
    private String sex;        //性别
    private String headPic;   //头像
    private String birthday;   //出生日期
    private String experience;      //工作经验   /年
    private String city;        //所在城市
    private String placeOrigin;        //籍贯
    private String telephone;     //手机号码
    private String email;     //电子邮箱
    private String hobby;     //爱好
    private String selComment;     //爱好
    private List<ResumeEducation> resumeEducations;
    private List<ResumeWorkExperience> resumeWorkExperiences;
    private List<ResumeProjectExperience> resumeProjectExperiences;
    private List<ResumeSkill> resumeSkills;
    private List<ResumeRewardAtSchool> resumeRewardAtSchools;
    private List<ResumeCertificate> resumeCertificates;


    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlaceOrigin() {
        return placeOrigin;
    }

    public void setPlaceOrigin(String placeOrigin) {
        this.placeOrigin = placeOrigin;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ResumeEducation> getResumeEducations() {
        return resumeEducations;
    }

    public void setResumeEducations(List<ResumeEducation> resumeEducations) {
        this.resumeEducations = resumeEducations;
    }

    public List<ResumeWorkExperience> getResumeWorkExperiences() {
        return resumeWorkExperiences;
    }

    public void setResumeWorkExperiences(List<ResumeWorkExperience> resumeWorkExperiences) {
        this.resumeWorkExperiences = resumeWorkExperiences;
    }

    public List<ResumeProjectExperience> getResumeProjectExperiences() {
        return resumeProjectExperiences;
    }

    public void setResumeProjectExperiences(List<ResumeProjectExperience> resumeProjectExperiences) {
        this.resumeProjectExperiences = resumeProjectExperiences;
    }

    public List<ResumeSkill> getResumeSkills() {
        return resumeSkills;
    }

    public void setResumeSkills(List<ResumeSkill> resumeSkills) {
        this.resumeSkills = resumeSkills;
    }

    public List<ResumeRewardAtSchool> getResumeRewardAtSchools() {
        return resumeRewardAtSchools;
    }

    public void setResumeRewardAtSchools(List<ResumeRewardAtSchool> resumeRewardAtSchools) {
        this.resumeRewardAtSchools = resumeRewardAtSchools;
    }

    public List<ResumeCertificate> getResumeCertificates() {
        return resumeCertificates;
    }

    public void setResumeCertificates(List<ResumeCertificate> resumeCertificates) {
        this.resumeCertificates = resumeCertificates;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSelComment() {
        return selComment;
    }

    public void setSelComment(String selComment) {
        this.selComment = selComment;
    }
}
