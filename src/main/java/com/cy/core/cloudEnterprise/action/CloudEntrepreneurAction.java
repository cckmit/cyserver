package com.cy.core.cloudEnterprise.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.cloudEnterprise.entity.CloudEntrepreneur;
import com.cy.core.cloudEnterprise.service.CloudEntrepreneurService;
import com.cy.core.userinfo.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业家
 *
 * @author niu
 * @create 2017-08-11 上午 10:56
 **/
@Namespace("/cloudEnterprise")
@Action(value = "cloudEntrepreneurAction")
public class CloudEntrepreneurAction extends AdminBaseAction{
    private static final Logger logger = Logger.getLogger(CloudEnterpriseAction.class);
    @Resource
    private CloudEntrepreneurService cloudEntrepreneurService;

    private CloudEntrepreneur cloudEntrepreneur;
    private String statuses; //状态集合以“，”隔开
    private String userId; //校友Id

    public CloudEntrepreneur getCloudEntrepreneur() {
        return cloudEntrepreneur;
    }

    public void setCloudEntrepreneur(CloudEntrepreneur cloudEntrepreneur) {
        this.cloudEntrepreneur = cloudEntrepreneur;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            if(cloudEntrepreneur !=null){
                map.put("teamName",cloudEntrepreneur.getTeamName());
                map.put("college",cloudEntrepreneur.getCollege());
                map.put("telephone",cloudEntrepreneur.getTelephone());
                map.put("enterpriseName",cloudEntrepreneur.getEnterpriseName());
                map.put("status",cloudEntrepreneur.getStatus());
            }
            if (StringUtils.isNotBlank(statuses)){
                map.put("statuses",statuses);
            }
            map.put("rows", rows);
            map.put("page", page);
            DataGrid<CloudEntrepreneur> data = cloudEntrepreneurService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }
    /**
     * 方法 updateStatus 的功能描述：更新企业家状态
     * @createAuthor niu
     * @createDate 2017-08-11 17:20:32
     * @param
     * @return
     * @throw
     *
    */
    public void updateStatus(){
        Message message = new Message();
        try {
            cloudEntrepreneurService.update(cloudEntrepreneur);
            message.setMsg("审核成功！");
            message.setSuccess(true);

        }catch (Exception e){
            logger.error(e,e);
            message.setMsg("审核失败！");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    /**
     * 方法 audit 的功能描述：企业家审核
     * @createAuthor niu
     * @createDate 2017-08-12 15:24:11
     * @param
     * @return void
     * @throw
     */
    public void audit(){
//        if (StringUtils.isNotBlank(userId)){
//           cloudEntrepreneurService.audit(userId,cloudEntrepreneur);
//        }
        Message message = new Message();
        try {
            if (cloudEntrepreneur != null && StringUtils.isNotBlank(cloudEntrepreneur.getId())) {
                CloudEntrepreneur entrepreneur = cloudEntrepreneurService.getById(cloudEntrepreneur.getId()) ;
                entrepreneur.setRelationUserInfoId(cloudEntrepreneur.getRelationUserInfoId());
                entrepreneur.setStatus(cloudEntrepreneur.getStatus());
                entrepreneur.setOpinion(cloudEntrepreneur.getOpinion());
                cloudEntrepreneurService.auditEntrepreneur(entrepreneur);

                message.setMsg("审核成功！");
                message.setSuccess(true);
            } else {
                message.setMsg("审核失败！");
                message.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e,e);
            message.setMsg("失败！");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    /**
     * 方法 relieveEntrepreneur 的功能描述：将企业家从正式校友或名誉校友中剔除
     * @createAuthor niu
     * @createDate 2017-08-12 15:24:11
     * @param
     * @return void
     * @throw
     */
    public void relieveEntrepreneur(){
//        if (StringUtils.isNotBlank(userId)){
//           cloudEntrepreneurService.audit(userId,cloudEntrepreneur);
//        }
        Message message = new Message();
        try {
            if (cloudEntrepreneur != null && StringUtils.isNotBlank(cloudEntrepreneur.getId())) {
                CloudEntrepreneur entrepreneur = cloudEntrepreneurService.getById(cloudEntrepreneur.getId()) ;
                entrepreneur.setStatus("15");
                cloudEntrepreneurService.relieveEntrepreneur(entrepreneur);

                message.setMsg("剔除成功！");
                message.setSuccess(true);
            } else {
                message.setMsg("剔除失败！");
                message.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e,e);
            message.setMsg("剔除失败！");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    /**
     * 方法 delete 的功能描述：删除企业家
     * @createAuthor niu
     * @createDate 2017-08-11 18:14:21
     * @param
     * @return
     * @throw
     *
    */
    public void delete(){
        Message message = new Message();
        try {
            if (cloudEntrepreneur!=null && StringUtils.isNotBlank(cloudEntrepreneur.getId())){
                Integer integer = cloudEntrepreneurService.delete(cloudEntrepreneur.getId());
                if (integer>0) {
                    message.setMsg("成功！");
                    message.setSuccess(true);
                }else {
                    message.setMsg("失败！");
                    message.setSuccess(false);
                }
            }else {
                message.setMsg("失败！");
                message.setSuccess(false);
            }
        }catch (Exception e){
            logger.error(e,e);
            message.setMsg("失败！");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
}
