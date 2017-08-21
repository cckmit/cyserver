package com.cy.core.logger.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.logger.dao.LoggerMapper;
import com.cy.core.logger.entity.Logger;
import com.cy.system.Global;
import com.cy.util.mail.SimpleMail;
import com.cy.util.mail.SimpleMailSender;

@Service("loggerService")
public class LoggerServiceImpl implements LoggerService {
	@Autowired
	private LoggerMapper loggerMapper;

	private final String from = "cy199cn@163.com";
	private final String password = "vqmsdmgziatqjjez";
	private final String to = "307585689@qq.com";

	@Override
	public void sendLogger2Mail() {
		try {
			List<Logger> list = loggerMapper.selectNotSend();
			if (list != null && list.size() > 0) {
				String content = "";
				for (Logger logger : list) {
					String stamp = "stamp:" + DateFormat.getDateTimeInstance().format(logger.getStamp()) + "<br/>";
					String thread = "thread:" + logger.getThread() + "<br/>";
					String infolevel = "infolevel:" + logger.getInfolevel() + "<br/>";
					String classes = "classes:" + logger.getClasses() + "<br/>";
					String messages = "messages:" + logger.getMessages() + "<br/>";
					String split = "-----------------------------------------------------------------------------------" + "<br/><br/>";
					content += stamp + thread + infolevel + classes + messages + split;
				}
				List<String> recipients = new ArrayList<String>();
				recipients.add(to);
				SimpleMail mail = new SimpleMail();
				mail.setSubject(Global.schoolSign + "服务器日志");
				mail.setContent(content);
				SimpleMailSender mailSender = new SimpleMailSender(from, password);
				mailSender.send(recipients, null, null, mail, null);
				for (Logger logger : list) {
					loggerMapper.updateSend(logger.getLoggerId());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
