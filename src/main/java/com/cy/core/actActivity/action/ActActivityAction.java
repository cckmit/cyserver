package com.cy.core.actActivity.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.actActivity.entity.ActActivity;
import com.cy.core.actActivity.service.ActActivityService;
import com.cy.util.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽奖活动Action
 *
 * @author niu
 * @create 2017-06-06 上午 10:20
 **/
@Namespace("/actActivity")
@Action(value = "actActivityAction")
public class ActActivityAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(ActActivityAction.class);

    private ActActivity actActivity;


    @Autowired
    private ActActivityService actActivityService;

    private String actActivityId;

    private String orderFlag;

    private String musics;

    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("page", page);
            map.put("orderFlag", orderFlag);

            if(actActivity != null){
                if(StringUtils.isNotBlank(actActivity.getName())){
                    map.put("keywords", actActivity.getName());
                }
                /*if(actActivity.getStartTime() != null){
                    map.put("timeStart", DateUtils.formatDateTime(actActivity.getStartTime()));
                }
                if(actActivity.getEndTime() != null){
                    map.put("timeEnd", DateUtils.formatDateTime(actActivity.getEndTime()));
                }
                if(actActivity.getSignUpStartTime() != null){
                    map.put("signTimeStart", DateUtils.formatDateTime(actActivity.getSignUpStartTime()));
                }
                if(actActivity.getSignUpEndTime() != null){
                    map.put("signTimeEnd", DateUtils.formatDateTime(actActivity.getSignUpEndTime()));
                }*/
            }
            DataGrid<ActActivity> data = actActivityService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }

    public void doNotNeedSessionAndSecurity_findList(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<ActActivity> actActivityList = actActivityService.findList(map);
        super.writeJson(actActivityList);
    }

    //新增企业信息
    public void save(){
        Message message=new Message();
        try {

            actActivityService.save(actActivity,musics);
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
            actActivityService.update(actActivity,musics);
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
            ActActivity activity=actActivityService.getById(actActivityId);
            super.writeJson(activity);
        }catch (Exception e){
            logger.error(e,e);
        }

    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            actActivityService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    public ActActivity getActActivity() {
        return actActivity;
    }

    public void setActActivity(ActActivity actActivity) {
        this.actActivity = actActivity;
    }

    public ActActivityService getActActivityService() {
        return actActivityService;
    }

    public void setActActivityService(ActActivityService actActivityService) {
        this.actActivityService = actActivityService;
    }

    public String getActActivityId() {
        return actActivityId;
    }

    public void setActActivityId(String actActivityId) {
        this.actActivityId = actActivityId;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getMusics() {
        return musics;
    }

    public void setMusics(String musics) {
        this.musics = musics;
    }
}
