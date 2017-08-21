package com.cy.core.sms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.sms.dao.MsgRecvMapper;
import com.cy.core.sms.entity.MsgRecv;

@Service("msgRecvService")
public class MsgRecvServiceImpl implements MsgRecvService {

	@Autowired
	private MsgRecvMapper msgRecvMapper;

	public int insertMsg(MsgRecv msgRecv) {
		return msgRecvMapper.insertMsgRecv(msgRecv);
	}

	public DataGrid<MsgRecv> dataGridMsgRecv(MsgRecv MsgRecv) {
		DataGrid<MsgRecv> dataGrid = new DataGrid<MsgRecv>();
		// Map<String , Object> map = new HashMap<String, Object>();
		// map.put("messagegroup", msgRecvModel.getMessagegroup());
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(msgRecvModel.getRecvtime());
		// calendar.add(Calendar.DAY_OF_YEAR, 2);
		// Date endtime = calendar.getTime();
		// map.put("starttime", msgRecvModel.getRecvtime());
		// map.put("endtime", endtime);
		// int start=(msgRecvModel.getPage()-1)*msgRecvModel.getRows();
		// int end = msgRecvModel.getPage()*msgRecvModel.getRows();
		// map.put("start",start);
		// map.put("end",end);
		// long total = msgRecvMapper.countMsgRecv(map);
		// dataGrid.setTotal(total);
		// List<MsgRecv> list = msgRecvMapper.selectMsgRecv(map);
		// List<MsgRecvModel> recvList = new ArrayList<MsgRecvModel>();
		// for(MsgRecv msgRecv:list){
		// MsgRecvModel msgRecvModel2 = new MsgRecvModel();
		// BeanUtils.copyProperties(msgRecv, msgRecvModel2);
		// recvList.add(msgRecvModel2);
		// }
		// dataGrid.setRows(recvList);

		return dataGrid;
	}

	public List<MsgRecv> selectByDate(Map<String, Object> map) {
		return msgRecvMapper.selectByDate(map);
	}

	public int deleteByDate(Map<String, Object> map) {
		return msgRecvMapper.deleteByDate(map);
	}

}
