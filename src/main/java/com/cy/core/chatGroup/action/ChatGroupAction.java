package com.cy.core.chatGroup.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/chatGroup")
@Action(value = "chatGroupAction")
public class ChatGroupAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(ChatGroupAction.class);

    private ChatGroup chatGroup;

    @Autowired
    private ChatGroupService chatGroupService;

    public void getList() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("page", page);
//        map.put("rows", rows);
        chatGroup.setPage(String.valueOf(page)) ;
        chatGroup.setRows(String.valueOf(rows)) ;
        super.writeJson(chatGroupService.dataGrid(chatGroup));
    }

    public void getById() {
        super.writeJson(chatGroupService.getById(chatGroup.getId()));
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

	public ChatGroup getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

    
}
