package com.cy.core.clientrelease.service;

import java.util.List;

import com.cy.base.entity.DataGrid;
import com.cy.core.clientrelease.entity.Client;
import com.cy.core.clientrelease.entity.ClientModel;

public interface ClientService {
	/**
	 * @param clientModel
	 * @return
	 */
	DataGrid<ClientModel> dataGridClient(ClientModel clientModel);
	
	/**
	 * @param clientModel
	 * @return
	 */
	int addClient(ClientModel clientModel);
	
	/**
	 * @param id
	 * @return
	 */
	ClientModel selectById(int id);
	
	/**
	 * @param clientModel
	 * @return
	 */
	int updateClient(ClientModel clientModel);
	
	/**
	 * @param clientModel
	 * @return
	 */
	List<Client> selectClient(ClientModel clientModel);
	
	Client selectNewOne();

	/**
	 * 查询当前最新或正式版本下的APP信息
	 * @return
	 */
	Client selectNewAppVersion();

	void updateDownloadsAddOne(Client client);

	long countAndroidDownloads();
}
