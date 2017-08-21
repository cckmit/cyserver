package com.cy.core.chatRecord.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.chatRecord.dao.ChatRecordMapper;
import com.cy.core.chatRecord.entity.ChatRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("chatRecordService")
public class ChatRecordServiceImpl implements ChatRecordService {
	@Autowired
    private ChatRecordMapper chatRecordMapper;

    public List<ChatRecord> findList(ChatRecord chatRecord) {
        List<ChatRecord> list = chatRecordMapper.query(chatRecord);
        return list ;
    }

    public DataGrid<ChatRecord> dataGrid(ChatRecord chatRecord) {
        DataGrid<ChatRecord> dataGrid = new DataGrid<ChatRecord>();
        long total = chatRecordMapper.count(chatRecord);
        dataGrid.setTotal(total);
        int start = (Integer.valueOf(chatRecord.getPage()) - 1) * Integer.valueOf(chatRecord.getRows());
        chatRecord.setStart(String.valueOf(start));
        List<ChatRecord> list = chatRecordMapper.query(chatRecord);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public ChatRecord getById(String id) {
        return chatRecordMapper.getById(id);
    }

    public void update(ChatRecord chatRecord) {
        if (chatRecord == null)
            throw new IllegalArgumentException("chatRecord cannot be null!");

        chatRecordMapper.update(chatRecord);
    }

    /**
     * 根据ID 集合删除 联系人信息
     */
    public void deleteByIdList(String ids) {
    	String[] array = ids.split(",");
		List<String> list = Arrays.asList(array);
		chatRecordMapper.deleteByIdList(list);
    }
    
    /**
     * 根据ID 删除联系人信息
     */
    public void delete(String id) {
    	ChatRecord chatRecord = new ChatRecord() ;
    	chatRecord.setId(id) ;
        chatRecord.preUpdate();
    	chatRecordMapper.delete(chatRecord);
    }

}
