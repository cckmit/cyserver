package com.cy.core.notify.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.notify.entity.Notify;
import com.cy.core.notify.entity.NotifyRecord;
import com.cy.core.notify.service.NotifyService;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.core.userinfo.entity.UserInfo;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 推送Action类
 */
@Namespace("/notify")
@Action(value = "notifyAction", results = {
       @Result(name = "viewNotify", location = "/page/admin/notify/notifyDeta.jsp")
    })
public class NotifyAction extends AdminBaseAction{

    private static final Logger logger = Logger.getLogger(NotifyAction.class);

    @Autowired
    private NotifyService notifyService;
    @Autowired
    private UserProfileService userProfileService;


    private Notify notify;

    private String notifyId ;


    public Notify getNotify() {
        return notify;
    }

    public void setNotify(Notify notify) {
        this.notify = notify;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    /**
     * 分页
     */
    public void dataGrid() {
        Notify notify1 = new Notify();
        notify1.setRows(String.valueOf(rows));
        notify1.setPage(String.valueOf(page));
        super.writeJson(notifyService.dataGrid(notify1));
    }

    public String getById(){
        notify=notifyService.getById(String.valueOf(notifyId));
        return "viewNotify";
    }
    public String view(){
        notify=notifyService.getById(String.valueOf(notifyId));
        return "view";
    }
    /**
     * 修改
     */
    public void  update(){
        Message message = new Message();
        try {
            notifyService.update(notify);
            message.setMsg("修改成功");
            message.setSuccess(true);
        }catch (Exception e) {
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    /**
     * 删除
     */
    public void delete(){
        Message message = new Message();
        try{
        String[] array=ids.split(",");
        for (String id : array) {
            notifyService.delete(id);
            message.setMsg("删除成功");
            message.setSuccess(true);
         }
        }catch(Exception e){
            logger.error(e,e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    public void dataGridUser(){
        NotifyRecord notifyRecord = new NotifyRecord();
        notifyRecord.setNotifyId(notifyId);
        notifyRecord.setRows(String.valueOf(rows));
        notifyRecord.setPage(String.valueOf(page));

        super.writeJson(notifyService.dataGridUser(notifyRecord));

    }

}
