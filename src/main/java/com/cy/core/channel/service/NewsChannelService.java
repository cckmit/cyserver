package com.cy.core.channel.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.channel.entity.NewsChannel;
import com.cy.util.jms.Channel;


public interface NewsChannelService
{
	DataGrid<NewsChannel> dataGrid(Map<String, Object> map);
	
	long countNewsChannel(Map<String, Object> map);
	
	List<NewsChannel> selectNewsChannelList(Map<String, Object> map);
	
	List<NewsChannel> selectAll();
	
	void save(NewsChannel newsChannel);
	
	void delete(String ids);

	NewsChannel selectById(long channelId);

	void update(NewsChannel newsChannel);
	
	
	/**
	 * 得到所有的标签
	 * 
	 * @return List<NewsChannel>；
	 * 
	 */
	List<NewsChannel> getALLLabelList();
	
	/**
	 * 得到所有的标签的组合字符串
	 * 
	 * @return Channel；
	 * 
	 */
	Channel getALLLabelCombinationStr();
	/**
	 * 查找订阅for mobile
	 */
	void findMyNesChannle(Message message,String content);
	/**
	 * 修改我的订阅
	 */
	void updateMyNewsChannel(Message message, String content);

}
