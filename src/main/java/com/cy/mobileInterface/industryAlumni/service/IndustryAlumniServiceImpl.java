package com.cy.mobileInterface.industryAlumni.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.Message;
import com.cy.core.industryAlumni.dao.IndustryAlumniMapper;
import com.cy.core.industryAlumni.entity.IndustryAlumni;

@Service("industryalumniService")
public class IndustryAlumniServiceImpl implements IndustryAlumniService {

	@Autowired
	private IndustryAlumniMapper industryAlumniMapper;

	@Override
	public void selectAll(Message message) {
		List<IndustryAlumni> list = industryAlumniMapper.selectAll();
		if (list != null && list.size() > 0) {
			message.setMsg("查询成功!");
			message.setObj(list);
			message.setSuccess(true);
			return;
		} else {
			message.setMsg("没有结果!");
			message.setSuccess(false);
			return;
		}
	}

}
