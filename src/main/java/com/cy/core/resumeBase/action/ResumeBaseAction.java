package com.cy.core.resumeBase.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.resumeBase.entity.ResumeBase;
import com.cy.core.resumeBase.service.ResumeBaseService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
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
@Namespace("/resumeBase")
@Action(value = "resumeBaseAction")
public class ResumeBaseAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(ResumeBaseAction.class);

    private ResumeBase resumeBase;
    @Autowired
    private ResumeBaseService resumeBaseService;

    private String resumeBaseId;

    //得到列表并且分页
    public void dataGraid() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", rows);
        map.put("page", page);
        if(resumeBase != null){
            if(StringUtils.isNotBlank(resumeBase.getName())){
                map.put("name", resumeBase.getName());
            }
            if(StringUtils.isNotBlank(resumeBase.getTelephone())){
                map.put("telephone", resumeBase.getTelephone());
            }
            if(StringUtils.isNotBlank(resumeBase.getCity())){
                map.put("city", resumeBase.getCity());
            }

        }
        DataGrid<ResumeBase> data = resumeBaseService.dataGrid(map);
        super.writeJson(data);

    }
    //新增企业信息
    public void save(){
        Message message=new Message();
        try {
            resumeBaseService.save(resumeBase);
            message.setMsg("新增成功");
            message.setSuccess(true);

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("新增失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //修改企业信息
    public void update(){
        Message message=new Message();
        try {
            resumeBaseService.update(resumeBase);
            message.setMsg("修改成功");
            message.setSuccess(true);

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //查看企业详情
    public void getById(){
        ResumeBase resumeBase=resumeBaseService.getById(resumeBaseId);
        super.writeJson(resumeBase);


    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            resumeBaseService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    public ResumeBase getResumeBase() {
        return resumeBase;
    }

    public void setResumeBase(ResumeBase resumeBase) {
        this.resumeBase = resumeBase;
    }

    public ResumeBaseService getResumeBaseService() {
        return resumeBaseService;
    }

    public void setResumeBaseService(ResumeBaseService resumeBaseService) {
        this.resumeBaseService = resumeBaseService;
    }

    public String getResumeBaseId() {
        return resumeBaseId;
    }

    public void setResumeBaseId(String resumeBaseId) {
        this.resumeBaseId = resumeBaseId;
    }
}
