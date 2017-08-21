package com.cy.core.activityPrize.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.activityPrize.entity.ActivityPrize;
import com.cy.core.activityPrize.service.ActivityPrizeService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽奖奖品action
 *
 * @author niu
 * @create 2017-06-06 上午 10:39
 **/
@Namespace("/activityPrize")
@Action(value = "activityPrizeAction")
public class ActivityPrizeAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(ActivityPrizeAction.class);

    @Autowired
    private ActivityPrizeService activityPrizeService;

    private ActivityPrize activityPrize;

    public ActivityPrize getActivityPrize() {
        return activityPrize;
    }

    public void setActivityPrize(ActivityPrize activityPrize) {
        this.activityPrize = activityPrize;
    }


    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            if(activityPrize !=null){
                map.put("name",activityPrize.getName());
                map.put("prizeName",activityPrize.getPrizeName());
                String a = activityPrize.getNum();
                activityPrize.setSurplusNum(a);
            }
            if(StringUtils.isNotEmpty(activityPrize.getActivityId())){
                map.put("activityId",activityPrize.getActivityId());
            }
            map.put("rows", rows);
            map.put("page", page);
            DataGrid<ActivityPrize> data = activityPrizeService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);
        }
    }

    public void save() {
        Message message = new Message();
        try {
            if(activityPrize !=null && StringUtils.isBlank(activityPrize.getSurplusNum())){
                activityPrize.setSurplusNum(activityPrize.getNum());
            }
            activityPrizeService.save(activityPrize);
            message.setMsg("保存成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("保存失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    public void getById(){
        super.writeJson(activityPrizeService.getById(activityPrize.getId()));
    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            activityPrizeService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }



}
