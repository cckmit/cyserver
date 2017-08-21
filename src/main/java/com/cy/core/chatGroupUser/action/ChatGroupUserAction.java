package com.cy.core.chatGroupUser.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;
import com.cy.core.chatGroupUser.service.ChatGroupUserService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/chatGroupUser")
@Action(value = "chatGroupUserAction")
public class ChatGroupUserAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(ChatGroupUserAction.class);

    private ChatGroupUser chatGroupUser;

    @Autowired
    private ChatGroupUserService chatGroupUserService;

    public void getList() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("page", page);
//        map.put("rows", rows);
        chatGroupUser.setPage(String.valueOf(page)) ;
        chatGroupUser.setRows(String.valueOf(rows)) ;
        super.writeJson(chatGroupUserService.dataGrid(chatGroupUser));
    }

    public void getById() {
        super.writeJson(chatGroupUserService.getById(chatGroupUser.getId()));
    }


    public void delete() {
        Message message = new Message();
        try {
        	chatGroupUserService.deleteByIdList(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

	public ChatGroupUser getChatGroupUser() {
		return chatGroupUser;
	}

	public void setChatGroupUser(ChatGroupUser chatGroupUser) {
		this.chatGroupUser = chatGroupUser;
	}

    
}
