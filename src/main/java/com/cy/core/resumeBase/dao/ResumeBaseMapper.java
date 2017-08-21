package com.cy.core.resumeBase.dao;

import com.cy.core.resumeBase.entity.*;

import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 简历基本信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
public interface ResumeBaseMapper {

    //查出总数
    long count(Map<String, Object> map);
    //查出列表
    List<ResumeBase> selectResumeBase(Map<String, Object> map);
    //根据ID查询单个的详细信息
    ResumeBase getById(String id);

    //添加一条
    void insert(ResumeBase resumeBase);
    //修改
    void update(ResumeBase resumeBase);

    void delete(List<String> list);

    // 查询教育经历
    List<ResumeEducation> selectResumeEducation(String resumeBaseId);

    ResumeEducation getResumeEducationById(String id);

    // 新增教育经历
    void saveResumeEducation(ResumeEducation resumeEducation);

    // 修改/删除教育经历
    void updateResumeEducation(ResumeEducation resumeEducation);

    // 查询工作／实习经历
    List<ResumeWorkExperience> selectResumeWorkExperience(String resumeBaseId);

    ResumeWorkExperience getResumeWorkExperienceById(String id);

    // 新增工作实习经历
    void saveResumeWorkExperience(ResumeWorkExperience resumeWorkExperience);

    // 修改/删除工作实习经历
    void updateResumeWorkExperience(ResumeWorkExperience resumeWorkExperience);

    // 查询项目经历
    List<ResumeProjectExperience> selectResumeProjectExperience(String resumeBaseId);

    ResumeProjectExperience getResumeProjectExperienceById(String id);

    // 新增项目经历
    void saveResumeProjectExperience(ResumeProjectExperience resumeProjectExperience);

    // 修改／删除项目经历
    void updateResumeProjectExperience(ResumeProjectExperience resumeProjectExperience);

    // 查询专业技能
    List<ResumeSkill> selectResumeSkill(String resumeBaseId);

    ResumeSkill getResumeSkillById(String id);

    // 新增专业技能
    void saveResumeSkill(ResumeSkill resumeSkill);

    // 修改/删除专业技能
    void updateResumeSkill(ResumeSkill resumeSkill);

    // 查询在校奖励情况
    List<ResumeRewardAtSchool> selectResumeRewardAtSchool(String resumeBaseId);

    ResumeRewardAtSchool getResumeRewardAtSchoolById(String id);

    // 新增在校奖励
    void saveResumeRewardAtSchool(ResumeRewardAtSchool resumeRewardAtSchool);

    // 修改/删除在校奖励
    void updateResumeRewardAtSchool(ResumeRewardAtSchool resumeRewardAtSchool);

    // 查询证书
    List<ResumeCertificate> selectResumeCertificate(String resumeBaseId);

    ResumeCertificate getResumeCertificateById(String id);

    // 新增证书
    void saveResumeCertificate(ResumeCertificate resumeCertificate);

    // 修改/删除新增证书
    void updateResumeCertificate(ResumeCertificate resumeCertificate);
}
