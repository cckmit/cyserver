package com.cy.core.log.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.log.entity.Log;
import com.cy.core.log.service.LogService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cha0res on 12/20/16.
 */
@Namespace("/log")
@Action(value = "logAction")
public class LogAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(LogAction.class);
    @Autowired
    LogService logService;

    private Log log;

    public void dataGrid(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", rows);
        map.put("page", page);
        if(log != null){
            if(StringUtils.isNotBlank(log.getTitle())){
                map.put("title", log.getTitle());
            }
            if(StringUtils.isNotBlank(log.getType())){
                map.put("type", log.getType());
            }
            if(StringUtils.isNotBlank(log.getUserAccount())){
                map.put("userAccount", log.getUserAccount());
            }
            if(StringUtils.isNotBlank(log.getUserName())){
                map.put("userName", log.getUserName());
            }
            if(StringUtils.isNotBlank(log.getBeginDate())){
                map.put("beginDate", log.getBeginDate());
            }
            if(StringUtils.isNotBlank(log.getEndDate())){
                map.put("endDate", log.getEndDate());
            }
        }
        super.writeJson(logService.dataGrid(map));
    }


    public void getById(){
        super.writeJson(logService.getById(log.getId()));
    }

    public void delete(){
        Message message = new Message();
        try{
            logService.delete(ids);
            message.init(true,"删除成功",null);
        }catch (Exception e){
            logger.error(e,e);
            message.init(false,"删除失败",null);
        }
        super.writeJson(message);
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
}
