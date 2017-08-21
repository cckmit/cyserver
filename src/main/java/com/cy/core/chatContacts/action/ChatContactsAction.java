package com.cy.core.chatContacts.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.chatContacts.entity.ChatContacts;
import com.cy.core.chatContacts.service.ChatContactsService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/chatContacts")
@Action(value = "chatGroupAction")
public class ChatContactsAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(ChatContactsAction.class);

    private ChatContacts chatContacts;

    @Autowired
    private ChatContactsService chatGroupService;

    public void getList() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("page", page);
//        map.put("rows", rows);
        chatContacts.setPage(String.valueOf(page)) ;
        chatContacts.setRows(String.valueOf(rows)) ;
        super.writeJson(chatGroupService.dataGrid(chatContacts));
    }

    public void getById() {
        super.writeJson(chatGroupService.getById(chatContacts.getId()));
    }


    public void delete() {
        Message message = new Message();
        try {
        	chatGroupService.deleteByIdList(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

	public ChatContacts getChatContacts() {
		return chatContacts;
	}

	public void setChatContacts(ChatContacts chatContacts) {
		this.chatContacts = chatContacts;
	}

    
}
