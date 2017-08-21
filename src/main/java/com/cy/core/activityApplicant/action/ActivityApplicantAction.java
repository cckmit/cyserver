package com.cy.core.activityApplicant.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityApplicant.entity.ActivityApplicant;
import com.cy.core.activityApplicant.service.ActivityApplicantService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动报名人Action
 *
 * @author niu
 * @create 2017-06-06 上午 10:28
 **/
@Namespace("/activityApplicant")
@Action(value = "activityApplicantAction")
public class ActivityApplicantAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(ActivityApplicantAction.class);

    private ActivityApplicant activityApplicant;

    @Autowired
    private ActivityApplicantService activityApplicantService;
    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            if(activityApplicant !=null){
                map.put("name",activityApplicant.getName());
                map.put("telephone",activityApplicant.getTelephone());
            }
            if(StringUtils.isNotEmpty(activityApplicant.getActivityId())){
                map.put("activityId",activityApplicant.getActivityId());
            }
            map.put("rows", rows);
            map.put("page", page);
            DataGrid<ActivityApplicant> data = activityApplicantService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);
        }
    }


    public void save() {
        Message message = new Message();
        try {
           activityApplicantService.save(activityApplicant);
            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }


    public ActivityApplicant getActivityApplicant() {
        return activityApplicant;
    }

    public void setActivityApplicant(ActivityApplicant activityApplicant) {
        this.activityApplicant = activityApplicant;
    }
}
