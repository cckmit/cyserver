package com.cy.core.chatRecord.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.chatRecord.entity.ChatRecord;
import com.cy.core.chatRecord.service.ChatRecordService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/chatRecord")
@Action(value = "chatRecordAction")
public class ChatRecordAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(ChatRecordAction.class);

    private ChatRecord chatRecord;

    @Autowired
    private ChatRecordService chatRecordService;

    public void getList() {
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("page", page);
//        map.put("rows", rows);
        chatRecord.setPage(String.valueOf(page)) ;
        chatRecord.setRows(String.valueOf(rows)) ;
        super.writeJson(chatRecordService.dataGrid(chatRecord));
    }

    public void getById() {
        super.writeJson(chatRecordService.getById(chatRecord.getId()));
    }


    public void delete() {
        Message message = new Message();
        try {
        	chatRecordService.deleteByIdList(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

	public ChatRecord getChatRecord() {
		return chatRecord;
	}

	public void setChatRecord(ChatRecord chatRecord) {
		this.chatRecord = chatRecord;
	}

    
}
