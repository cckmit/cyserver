package com.cy.core.activityMusic.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityMusic.entity.ActivityMusic;
import com.cy.core.activityMusic.service.ActivityMusicService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽奖活动音乐Action
 *
 * @author niu
 * @create 2017-06-06 上午 10:35
 **/
@Namespace("/activityMusic")
@Action(value = "activityMusicAction")
public class ActivityMusicAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(ActivityMusicAction.class);

    private ActivityMusic activityMusic;

    @Autowired
    private ActivityMusicService activityMusicService;

    private String activityMusicId;

    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("page", page);

            if(activityMusic != null){
                if(StringUtils.isNotBlank(activityMusic.getActivityId())){
                    map.put("activityId", activityMusic.getActivityId());
                }
                if(StringUtils.isNotBlank(activityMusic.getActivityName())){
                    map.put("activityName", activityMusic.getActivityName());
                }
                if(StringUtils.isNotBlank(activityMusic.getType())){
                    map.put("type", activityMusic.getType());
                }
                if(StringUtils.isNotBlank(activityMusic.getIsRepeatPlay())){
                    map.put("isRepeatPlay", activityMusic.getIsRepeatPlay());
                }
            }
            DataGrid<ActivityMusic> data = activityMusicService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }
    //新增企业信息
    public void save(){
        Message message=new Message();
        try {

            activityMusicService.save(activityMusic);
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
            activityMusicService.update(activityMusic);
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
            ActivityMusic activityMusic=activityMusicService.getById(activityMusicId);
            super.writeJson(activityMusic);
        }catch (Exception e){
            logger.error(e,e);
        }

    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            activityMusicService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    public ActivityMusic getActivityMusic() {
        return activityMusic;
    }

    public void setActivityMusic(ActivityMusic activityMusic) {
        this.activityMusic = activityMusic;
    }

    public ActivityMusicService getActivityMusicService() {
        return activityMusicService;
    }

    public void setActivityMusicService(ActivityMusicService activityMusicService) {
        this.activityMusicService = activityMusicService;
    }

    public String getActivityMusicId() {
        return activityMusicId;
    }

    public void setActivityMusicId(String activityMusicId) {
        this.activityMusicId = activityMusicId;
    }
}
