package com.cy.core.activityWinning.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityWinning.entity.ActivityWinning;
import com.cy.core.activityWinning.service.ActivityWinningService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hp on 2017/6/7.
 */
@Namespace("/activityWinning")
@Action(value = "activityWinningAction")
public class ActivityWinningAction extends AdminBaseAction{

    private static final Logger logger = Logger.getLogger(ActivityWinningAction.class);

    private ActivityWinning activityWinning;


    @Autowired
    private ActivityWinningService activityWinningService;

    private String activityWinningId;

    private String activityId;

    private String keywords;


    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("page", page);
            if(StringUtils.isNotEmpty(activityId)){
                map.put("activityId",activityId);
            }
            if(StringUtils.isNotEmpty(keywords)){
                map.put("keywords",keywords);
            }
            DataGrid<ActivityWinning> data = activityWinningService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }

    public void doNotNeedSessionAndSecurity_findList(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<ActivityWinning> actActivityList = activityWinningService.findList(map);
        super.writeJson(actActivityList);
    }

    //新增企业信息
    public void save(){
        Message message=new Message();
        try {

            activityWinningService.save(activityWinning);
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
            activityWinningService.update(activityWinning);
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
        Message message=new Message();
        try{
            ActivityWinning activityWinning=activityWinningService.getById(activityWinningId);
            super.writeJson(activityWinning);
        }catch (Exception e){
            logger.error(e,e);
        }

    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            activityWinningService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    public ActivityWinning getActivityWinning() {
        return activityWinning;
    }

    public void setActivityWinning(ActivityWinning activityWinning) {
        this.activityWinning = activityWinning;
    }

    public ActivityWinningService getActivityWinningService() {
        return activityWinningService;
    }

    public void setActivityWinningService(ActivityWinningService activityWinningService) {
        this.activityWinningService = activityWinningService;
    }

    public String getActivityWinningId() {
        return activityWinningId;
    }

    public void setActivityWinningId(String activityWinningId) {
        this.activityWinningId = activityWinningId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
