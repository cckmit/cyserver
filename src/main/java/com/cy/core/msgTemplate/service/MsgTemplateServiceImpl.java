package com.cy.core.msgTemplate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.smscloud.entity.Header;
import com.cy.smscloud.http.HttpClientBase;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.msgTemplate.dao.MsgTemplateMapper;
import com.cy.core.msgTemplate.entity.MsgTemplate;

@Service("msgTemplateService")
public class MsgTemplateServiceImpl implements MsgTemplateService {

	@Autowired
	private MsgTemplateMapper msgTemplateMapper;
	/*@Autowired
	private SmsCloudHttpUtils smsCloudHttpUtils;

	public SmsCloudHttpUtils getSmsCloudHttpUtils() {
		return smsCloudHttpUtils;
	}

	public void setSmsCloudHttpUtils(SmsCloudHttpUtils smsCloudHttpUtils) {
		this.smsCloudHttpUtils = smsCloudHttpUtils;
	}
*/

	public void save(MsgTemplate msgTemplate) {

		msgTemplateMapper.save(msgTemplate);
	}

	public void update(MsgTemplate msgTemplate) {
		msgTemplateMapper.update(msgTemplate);

	}

	public void delete(String ids) {
		if (ids != null && !"".equals(ids)) {
			String[] idArray = ids.split(",");
			List<Long> list = new ArrayList<Long>();
			for (String id : idArray) {
				list.add(Long.parseLong(id));
			}
			msgTemplateMapper.delete(list);
		}
	}

	public MsgTemplate selectById(long msgTemplateId) {
		return msgTemplateMapper.selectById(msgTemplateId);
	}

	public List<MsgTemplate> selectAll() {
		return msgTemplateMapper.selectAll();
	}
	
	public List<MsgTemplate> selectList(Map<String, Object> map) {
		return msgTemplateMapper.findList(map);
	}

	public DataGrid<MsgTemplate> dataGrid(Map<String, Object> map) {
		DataGrid<MsgTemplate> dataGrid = new DataGrid<MsgTemplate>();
		long count = msgTemplateMapper.count(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<MsgTemplate> list = msgTemplateMapper.selectList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public MsgTemplate selectByTitle(MsgTemplate msgTemplate) {
		return msgTemplateMapper.selectByTitle(msgTemplate);
	}

}
