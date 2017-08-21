package com.cy.core.logger.dao;

import java.util.List;

import com.cy.core.logger.entity.Logger;

public interface LoggerMapper {
	List<Logger> selectNotSend();
	
	void updateSend(long loggerId);
}
