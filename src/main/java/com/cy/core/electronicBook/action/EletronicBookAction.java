package com.cy.core.electronicBook.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.activity.entity.Activity;
import com.cy.core.activity.service.ActivityService;
import com.cy.core.electronicBook.entity.ElectronicBook;
import com.cy.core.electronicBook.service.ElectronicBookService;
import com.cy.core.user.entity.User;
import com.cy.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Namespace("/electronicBook")
@Action(value = "electronicBookAction")
public class EletronicBookAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(EletronicBookAction.class);

    private ElectronicBook electronicBook;

    @Autowired
    private ElectronicBookService electronicBookService;

    public void save() {
        Message message = new Message();
        try {
            User user = UserUtils.getUser();
            electronicBook.setUploadOrganization(String.valueOf(getUser().getDeptId()));
            electronicBookService.save(electronicBook);
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
        if(electronicBook != null){
            if(StringUtils.isNotBlank(electronicBook.getName())){
                map.put("name", electronicBook.getName());
            }
            if(StringUtils.isNotBlank(electronicBook.getAuthor())){
                map.put("author", electronicBook.getAuthor());
            }
            if(StringUtils.isNotBlank(electronicBook.getVersion())){
                map.put("version", electronicBook.getVersion());
            }
        }
        super.writeJson(electronicBookService.dataGrid(map));
    }

    public void getById() {
        super.writeJson(electronicBookService.getById(electronicBook.getId()));
    }

    public void update() {
        Message message = new Message();
        try {
            User user = UserUtils.getUser();
            electronicBook.setUploadOrganization(String.valueOf(getUser().getDeptId()));
            electronicBookService.update(electronicBook);
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
            electronicBookService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    public ElectronicBook getElectronicBook() {
        return electronicBook;
    }

    public void setElectronicBook(ElectronicBook electronicBook) {
        this.electronicBook = electronicBook;
    }

    public ElectronicBookService getElectronicBookService() {
        return electronicBookService;
    }

    public void setElectronicBookService(ElectronicBookService electronicBookService) {
        this.electronicBookService = electronicBookService;
    }
}
