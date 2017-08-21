package com.cy.core.clientrelease.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cy.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.clientrelease.dao.ClientMapper;
import com.cy.core.clientrelease.entity.Client;
import com.cy.core.clientrelease.entity.ClientModel;
import com.cy.core.clientrelease.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	private ClientMapper clientMapper;

	public ClientMapper getClientMapper() {
		return clientMapper;
	}

	@Autowired
	public void setClientMapper(ClientMapper clientMapper) {
		this.clientMapper = clientMapper;
	}

	public DataGrid<ClientModel> dataGridClient(ClientModel clientModel) {
		DataGrid<ClientModel> dataGrid = new DataGrid<ClientModel>();
		long total = clientMapper.countClient(clientModel);
		dataGrid.setTotal(total);
		int start = (clientModel.getPage() - 1) * clientModel.getRows();
		int end = clientModel.getRows();
		clientModel.setStart(start);
		clientModel.setEnd(end);
		List<Client> list = clientMapper.selectClient(clientModel);
		List<ClientModel> modelList = new ArrayList<ClientModel>();
		for (Client client : list) {
			ClientModel clientModel2 = new ClientModel();
			BeanUtils.copyProperties(client, clientModel2);
			modelList.add(clientModel2);
		}
		dataGrid.setRows(modelList);
		return dataGrid;
	}

	public int addClient(ClientModel clientModel) {
		clientModel.setCreateTime(new Date());
		return clientMapper.addClient(clientModel);
	}

	public ClientModel selectById(int id) {
		Client client = clientMapper.selectById(id);
		ClientModel clientModel = new ClientModel();
		BeanUtils.copyProperties(client, clientModel);
		return clientModel;
	}

	public int updateClient(ClientModel clientModel) {
		return clientMapper.updateClient(clientModel);
	}

	public List<Client> selectClient(ClientModel clientModel) {
		return clientMapper.selectClient(clientModel);
	}

	@Override
	public Client selectNewOne() {
		return clientMapper.selectNewOne();
	}

	/**
	 * 查询当前最新或正式版本下的APP信息
	 * @return
	 */
	public Client selectNewAppVersion() {
		return clientMapper.selectNewAppVersion() ;
	}
	/**
	 * 方法updateDownloadsAddOne 的功能描述：更新版本下载量
	 * @createAuthor niu
	 * @createDate 2017-04-05 16:12:56
	 * @param client
	 * @return void
	 * @throw
	 *
	 */
	public void updateDownloadsAddOne(Client client){

		if (client !=null){
			ClientModel clientModel = new ClientModel();
			clientModel.setId(client.getId());
			String downloads = client.getDownloads();
			if (StringUtils.isBlank(downloads)){
				downloads = "0";
			}
			clientModel.setDownloads(String.valueOf(Long.parseLong(downloads)+1));
			clientMapper.updateClient(clientModel);
		}
	}
	/**
	 * 方法countAndroidDownloads 的功能描述：获取每个版本的下载量，计算安卓的总下载量
	 * @createAuthor niu
	 * @createDate 2017-04-12 14:20:45
	 * @param
	 * @return java.lang.Integer
	 * @throw
	 *
	 */
	public long countAndroidDownloads(){
		long count =0;
		List<Client> clientList = clientMapper.selectClient(new ClientModel());
		if (clientList!=null && !clientList.isEmpty()){
			for (Client client :clientList){
				if (StringUtils.isNotBlank(client.getDownloads())){
					count += Long.parseLong(client.getDownloads());
				}
			}
		}
		return count;
	}
}
