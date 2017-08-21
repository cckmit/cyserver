package com.cy.core.clientrelease.dao;

import java.util.List;


import com.cy.core.clientrelease.entity.Client;
import com.cy.core.clientrelease.entity.ClientModel;

public interface ClientMapper {
	/**
	 * @param clientModel
	 * @return
	 */
	long countClient(ClientModel clientModel);
	
	/**
	 * @param clientModel
	 * @return
	 */
	List<Client> selectClient(ClientModel clientModel);
	
	/**
	 * @param clientModel
	 * @return
	 */
	int addClient(ClientModel clientModel);
	
	/**
	 * @param clientModel
	 * @return
	 */
	int updateClient(ClientModel clientModel);

	/**
	 * @param id
	 * @return
	 */
	Client selectById(int id);
	
	Client selectNewOne();

	/**
	 * 查询当前最新或正式版本下的APP信息
	 * @return
     */
	Client selectNewAppVersion();
}
