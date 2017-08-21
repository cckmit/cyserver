package com.cy.core.resumeBase.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.resumeBase.dao.ResumeBaseMapper;
import com.cy.core.resumeBase.entity.*;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 简历基本信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
@Service("ResumeBaseService")
public class ResumeBaseServiceImpl implements ResumeBaseService {

    @Autowired
    private ResumeBaseMapper resumeBaseMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;


    @Override
    public List<ResumeBase> findList(Map<String, Object> map) {
        List<ResumeBase> list = resumeBaseMapper.selectResumeBase(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    @Override
    public DataGrid<ResumeBase> dataGrid(Map<String, Object> map) {
        DataGrid<ResumeBase> dataGrid = new DataGrid<ResumeBase>();
        long total = resumeBaseMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ResumeBase> list = resumeBaseMapper.selectResumeBase(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public List<ResumeBase> resumeBaseList(){
        Map<String, Object> map = new HashMap<>();
        map.put("isNoLimit", "1");
        List<ResumeBase> list = resumeBaseMapper.selectResumeBase(map);
        return list;
    }

    @Override
    public ResumeBase save(ResumeBase resumeBase) {
        resumeBase.preInsert();
        resumeBaseMapper.insert(resumeBase);
        return resumeBase;
    }
    @Override
    public ResumeBase update(ResumeBase resumeBase) {
        resumeBase.preUpdate();
        resumeBaseMapper.update(resumeBase);
        return resumeBase;
    }

    @Override
    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        resumeBaseMapper.delete(list);
    }

    public ResumeBase getById(String id) {
        ResumeBase resumeBase = resumeBaseMapper.getById(id) ;
        resumeBase.setResumeEducations(resumeBaseMapper.selectResumeEducation(id));
        resumeBase.setResumeWorkExperiences(resumeBaseMapper.selectResumeWorkExperience(id));
        resumeBase.setResumeProjectExperiences(resumeBaseMapper.selectResumeProjectExperience(id));
        resumeBase.setResumeSkills(resumeBaseMapper.selectResumeSkill(id));
        resumeBase.setResumeRewardAtSchools(resumeBaseMapper.selectResumeRewardAtSchool(id));
        resumeBase.setResumeCertificates(resumeBaseMapper.selectResumeCertificate(id));
        return resumeBase;
    }


    /**
     * 创建/修改简历基本信息接口
     */
    public void saveResumeBase(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            ResumeBase resumeBase = JSON.parseObject(content, ResumeBase.class);

            if (StringUtils.isBlank(resumeBase.getAccountNum())){
                message.init(false, "账号不能为空",null);
                return;
            }

            /*if (StringUtils.isBlank(resumeBase.getBirthday())) {
                message.setMsg("出生日期不能为空");
                message.setSuccess(false);
                return;
            }*/
            /*if (StringUtils.isBlank(resumeBase.getSex())) {
                message.setMsg("性别不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(resumeBase.getCity())) {
                message.setMsg("所在城市不能为空");
                message.setSuccess(false);
                return;
            }

            if (StringUtils.isBlank(resumeBase.getExperience())) {
                message.setMsg("工作经验不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(resumeBase.getTelephone())) {
                message.setMsg("手机号不能为空");
                message.setSuccess(false);
                return;
            }
            if (StringUtils.isBlank(resumeBase.getEmail())) {
                message.setMsg("邮箱不能为空");
                message.setSuccess(false);
                return;
            }*/

            if (StringUtils.isBlank(resumeBase.getId())) {
                resumeBase.preInsert();
                resumeBaseMapper.insert(resumeBase);

            } else {
                ResumeBase tmp = resumeBaseMapper.getById(resumeBase.getId());
                if (tmp != null) {
                    resumeBase.preUpdate();
                    resumeBaseMapper.update(resumeBase);
                } else {
                    message.init(false, "该信息不存在", null);
                }
            }

            message.setMsg("创建简历信息成功");
            message.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询简历信息的接口
     * @param message
     * @param content
     */
    public void getResumeByAccount(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, Object> map = JSON.parseObject(content, Map.class);
            String accountNum = (String)map.get("accountNum");
            if(StringUtils.isBlank(accountNum)){
                message.init(false, "请传入用户ID", null);
            }
            map.put("isNoLimit", "1");
            List<ResumeBase> list = resumeBaseMapper.selectResumeBase(map);
            if(list != null && list.size() > 0){
                ResumeBase rb = list.get(0);
                rb.setResumeEducations(resumeBaseMapper.selectResumeEducation(rb.getId()));
                rb.setResumeWorkExperiences(resumeBaseMapper.selectResumeWorkExperience(rb.getId()));
                rb.setResumeProjectExperiences(resumeBaseMapper.selectResumeProjectExperience(rb.getId()));
                rb.setResumeSkills(resumeBaseMapper.selectResumeSkill(rb.getId()));
                rb.setResumeRewardAtSchools(resumeBaseMapper.selectResumeRewardAtSchool(rb.getId()));
                rb.setResumeCertificates(resumeBaseMapper.selectResumeCertificate(rb.getId()));
                message.init(true, "获取成功", rb);
            }else{
                message.init(true, "您还没有创建简历", null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除简历基本信息接口
     *
     * @param message
     * @param content
     */
    public void deleteResumeBase(Message message, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, String> map = JSON.parseObject(content, Map.class);
            String resumeBaseId = map.get("id");
            String userId = map.get("userId");

            if (StringUtils.isBlank(userId)) {
                message.setMsg("请传入用户ID");
                message.setSuccess(false);
                return;
            }

            if (StringUtils.isBlank(resumeBaseId)) {
                message.setMsg("请传入简历信息id");
                message.setSuccess(false);
                return;
            }

            ResumeBase resumeBase = getById(resumeBaseId);
            if (resumeBase == null) {
                message.init(false, "不存在的简历信息", null);
                return;
            }

            List<String> list = new ArrayList<>();
            list.add(resumeBaseId);
            resumeBaseMapper.delete(list);

            message.init(true, "删除成功", null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 简历拓展类型的增删改
     * @param message
     * @param content
     */
    public void operateResumeExtends(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }
            Map<String, Object> map = JSON.parseObject(content, Map.class);
            int type = (int)map.get("type");
            int action = (int) map.get("action");
            Object ob = map.get("data");
            String data = JSON.toJSONString(ob);

            switch (type){
                case 1 :    // 教育经历
                    ResumeEducation re = JSON.parseObject(data, ResumeEducation.class);
                    switch (action){
                        case 1 :
                            if(StringUtils.isBlank(re.getResumeBaseId()))
                                message.init(false, "请传入简历ID", null);
                            else{
                                ResumeBase rb = resumeBaseMapper.getById(re.getResumeBaseId());
                                if(rb == null){
                                    message.init(false, "简历不存在", null);
                                }else{
                                    re.preInsert();
                                    resumeBaseMapper.saveResumeEducation(re);
                                    message.init(true, "创建成功", null);
                                }
                            }
                            break;
                        case 2 :
                            if(StringUtils.isBlank(re.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                re.preUpdate();
                                resumeBaseMapper.updateResumeEducation(re);
                                message.init(true, "修改成功", null);
                            }
                            break;
                        case 3 :
                            if(StringUtils.isBlank(re.getId()))
                                message.init(false, "请传入查询ID", null);
                            else{
                                ResumeEducation rer = resumeBaseMapper.getResumeEducationById(re.getId());
                                if(rer == null)
                                    message.init(false, "不存在的ID", null);
                                else
                                    message.init(true, "查询成功", rer);
                            }
                            break;
                        case 4 :
                            if(StringUtils.isBlank(re.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                re.preUpdate();
                                re.setDelFlag("1");
                                resumeBaseMapper.updateResumeEducation(re);
                                message.init(true, "删除成功", null);
                            }
                            break;
                        default:
                            message.init(false, "非法操作类型", null);
                    }
                    break;
                case 2 :    // 工作经历
                    ResumeWorkExperience rwe = JSON.parseObject(data, ResumeWorkExperience.class);
                    switch (action){
                        case 1 :
                            if(StringUtils.isBlank(rwe.getResumeBaseId()))
                                message.init(false, "请传入简历ID", null);
                            else {
                                ResumeBase rb = resumeBaseMapper.getById(rwe.getResumeBaseId());
                                if(rb == null)
                                    message.init(false, "简历不存在", null);
                                else{
                                    rwe.preInsert();
                                    resumeBaseMapper.saveResumeWorkExperience(rwe);
                                    message.init(true, "创建成功", null);
                                }
                            }
                            break;
                        case 2 :
                            if(StringUtils.isBlank(rwe.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rwe.preUpdate();
                                resumeBaseMapper.updateResumeWorkExperience(rwe);
                                message.init(true, "保存成功", null);
                            }
                            break;
                        case 3 :
                            if(StringUtils.isBlank(rwe.getId()))
                                message.init(false, "请传入查询ID", null);
                            else{
                                ResumeWorkExperience rwer = resumeBaseMapper.getResumeWorkExperienceById(rwe.getId());
                                if(rwer == null)
                                    message.init(false, "不存在的ID", null);
                                else
                                    message.init(true, "查询成功", rwer);
                            }
                            break;
                        case 4 :
                            if(StringUtils.isBlank(rwe.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rwe.preUpdate();
                                rwe.setDelFlag("1");
                                resumeBaseMapper.updateResumeWorkExperience(rwe);
                                message.init(true, "删除成功", null);
                            }
                            break;
                        default:
                            message.init(false, "非法操作类型", null);
                    }
                    break;
                case 3 :    // 项目经验
                    ResumeProjectExperience rpe = JSON.parseObject(data, ResumeProjectExperience.class);
                    switch (action){
                        case 1 :
                            if(StringUtils.isBlank(rpe.getResumeBaseId()))
                                message.init(false, "请传入简历ID", null);
                            else {
                                ResumeBase rb = resumeBaseMapper.getById(rpe.getResumeBaseId());
                                if(rb == null)
                                    message.init(false, "简历不存在", null);
                                else{
                                    rpe.preInsert();
                                    resumeBaseMapper.saveResumeProjectExperience(rpe);
                                    message.init(true, "创建成功", null);
                                }
                            }
                            break;
                        case 2 :
                            if(StringUtils.isBlank(rpe.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rpe.preUpdate();
                                resumeBaseMapper.updateResumeProjectExperience(rpe);
                                message.init(true, "保存成功", null);
                            }
                            break;
                        case 3 :
                            if(StringUtils.isBlank(rpe.getId()))
                                message.init(false, "请传入查询ID", null);
                            else{
                                ResumeProjectExperience rper = resumeBaseMapper.getResumeProjectExperienceById(rpe.getId());
                                if(rper == null)
                                    message.init(false, "不存在的ID", null);
                                else
                                    message.init(true, "查询成功", rper);
                            }
                            break;
                        case 4 :
                            if(StringUtils.isBlank(rpe.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rpe.preUpdate();
                                rpe.setDelFlag("1");
                                resumeBaseMapper.updateResumeProjectExperience(rpe);
                                message.init(true, "删除成功", null);
                            }
                            break;
                        default:
                            message.init(false, "非法操作类型", null);
                    }
                    break;
                case 4 :    // 专业技能
                    ResumeSkill rs = JSON.parseObject(data, ResumeSkill.class);
                    switch (action){
                        case 1 :
                            if(StringUtils.isBlank(rs.getResumeBaseId()))
                                message.init(false, "请传入简历ID", null);
                            else {
                                ResumeBase rb = resumeBaseMapper.getById(rs.getResumeBaseId());
                                if(rb == null)
                                    message.init(false, "简历不存在", null);
                                else{
                                    rs.preInsert();
                                    resumeBaseMapper.saveResumeSkill(rs);
                                    message.init(true, "创建成功", null);
                                }
                            }
                            break;
                        case 2 :
                            if(StringUtils.isBlank(rs.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rs.preUpdate();
                                resumeBaseMapper.updateResumeSkill(rs);
                                message.init(true, "保存成功", null);
                            }
                            break;
                        case 3 :
                            if(StringUtils.isBlank(rs.getId()))
                                message.init(false, "请传入查询ID", null);
                            else{
                                ResumeSkill rsr = resumeBaseMapper.getResumeSkillById(rs.getId());
                                if(rsr == null)
                                    message.init(false, "不存在的ID", null);
                                else
                                    message.init(true, "查询成功", rsr);
                            }
                            break;
                        case 4 :
                            if(StringUtils.isBlank(rs.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rs.preUpdate();
                                rs.setDelFlag("1");
                                resumeBaseMapper.updateResumeSkill(rs);
                                message.init(true, "删除成功", null);
                            }
                            break;
                        default:
                            message.init(false, "非法操作类型", null);
                    }
                    break;
                case 5 :    // 在校奖励情况
                    ResumeRewardAtSchool rras = JSON.parseObject(data, ResumeRewardAtSchool.class);
                    switch (action){
                        case 1 :
                            if(StringUtils.isBlank(rras.getResumeBaseId()))
                                message.init(false, "请传入简历ID", null);
                            else {
                                ResumeBase rb = resumeBaseMapper.getById(rras.getResumeBaseId());
                                if(rb == null)
                                    message.init(false, "简历不存在", null);
                                else{
                                    rras.preInsert();
                                    resumeBaseMapper.saveResumeRewardAtSchool(rras);
                                    message.init(true, "创建成功", null);
                                }
                            }
                            break;
                        case 2 :
                            if(StringUtils.isBlank(rras.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rras.preUpdate();
                                resumeBaseMapper.updateResumeRewardAtSchool(rras);
                                message.init(true, "保存成功", null);
                            }
                            break;
                        case 3 :
                            if(StringUtils.isBlank(rras.getId()))
                                message.init(false, "请传入查询ID", null);
                            else{
                                ResumeRewardAtSchool rrasr = resumeBaseMapper.getResumeRewardAtSchoolById(rras.getId());
                                if(rrasr == null)
                                    message.init(false, "不存在的ID", null);
                                else
                                    message.init(true, "查询成功", rrasr);
                            }
                            break;
                        case 4 :
                            if(StringUtils.isBlank(rras.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rras.preUpdate();
                                rras.setDelFlag("1");
                                resumeBaseMapper.updateResumeRewardAtSchool(rras);
                                message.init(true, "删除成功", null);
                            }
                            break;
                        default:
                            message.init(false, "非法操作类型", null);
                    }
                    break;
                case 6 :    // 证书
                    ResumeCertificate rc = JSON.parseObject(data, ResumeCertificate.class);
                    switch (action){
                        case 1 :
                            if(StringUtils.isBlank(rc.getResumeBaseId()))
                                message.init(false, "请传入简历ID", null);
                            else {
                                ResumeBase rb = resumeBaseMapper.getById(rc.getResumeBaseId());
                                if(rb == null)
                                    message.init(false, "简历不存在", null);
                                else{
                                    rc.preInsert();
                                    resumeBaseMapper.saveResumeCertificate(rc);
                                    message.init(true, "创建成功", null);
                                }
                            }
                            break;
                        case 2 :
                            if(StringUtils.isBlank(rc.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rc.preUpdate();
                                resumeBaseMapper.updateResumeCertificate(rc);
                                message.init(true, "保存成功", null);
                            }
                            break;
                        case 3 :
                            if(StringUtils.isBlank(rc.getId()))
                                message.init(false, "请传入查询ID", null);
                            else{
                                ResumeCertificate rcr = resumeBaseMapper.getResumeCertificateById(rc.getId());
                                if(rcr == null)
                                    message.init(false, "不存在的ID", null);
                                else
                                    message.init(true, "查询成功", rcr);
                            }
                            break;
                        case 4 :
                            if(StringUtils.isBlank(rc.getId()))
                                message.init(false, "保存操作请传入ID", null);
                            else{
                                rc.preUpdate();
                                rc.setDelFlag("1");
                                resumeBaseMapper.updateResumeCertificate(rc);
                                message.init(true, "删除成功", null);
                            }
                            break;
                        default:
                            message.init(false, "非法操作类型", null);
                    }
                    break;
                default:
                    message.init(false, "非法拓展类型", null);
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
