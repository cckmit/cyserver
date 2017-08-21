package com.cy.core.activity.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.activity.entity.Activity;
import com.cy.core.activity.service.ActivityService;
import com.cy.system.WebUtil;

@Namespace("/activity")
@Action(value = "activityAction")
public class ActivityAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(ActivityAction.class);

    private Activity activity;

    @Autowired
    private ActivityService activityService;

    public void save() {
        Message message = new Message();
        try {
            activityService.save(activity);
            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    public void getList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        if(activity != null){
            if(StringUtils.isNotBlank(activity.getContactPerson())){
                map.put("contactPerson", activity.getContactPerson());
            }
            if(StringUtils.isNotBlank(activity.getContactPhone())){
                map.put("contactPhone", activity.getContactPhone());
            }
            if(StringUtils.isNotBlank(activity.getStatus())){
                map.put("status", activity.getStatus());
            }
        }
        super.writeJson(activityService.dataGrid(map));
    }

    public void getById() {
        super.writeJson(activityService.getById(activity.getId()));
    }

    public void update() {
        Message message = new Message();
        try {
            activityService.update(activity);
            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    public void delete() {
        Message message = new Message();
        try {
            activityService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

   /* public void doNotNeedSessionAndSecurity_getSignPeople(){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("id", activity.getId() );
        super.writeJson(activityService.getBackSchoolSign(map));
    }*/

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}
