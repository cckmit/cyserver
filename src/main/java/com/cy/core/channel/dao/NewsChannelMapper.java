package com.cy.core.channel.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.channel.entity.NewsChannel;
import com.cy.core.dict.entity.Dict;
import com.cy.core.userProfile.entity.UserProfile;

public interface NewsChannelMapper
{
	long countNewsChannel(Map<String, Object> map);

	List<NewsChannel> selectNewsChannelList(Map<String, Object> map);

	List<NewsChannel> selectAll();

	void save(NewsChannel newsChannel);

	void delete(List<Long> list);

	NewsChannel selectById(long channelId);

	void update(NewsChannel newsChannel);
	
	/**
	 * 得到所有的标签
	 * 
	 * @return Dict；
	 * 
	 */
	List<Dict> getALLLabel();


	/**
	 * 修改我的订阅
	 */
	void updateMyChannel(Map<String, Object> map);

	/**
	 * 查找我的订阅
	 * @param myChannels
	 * @return
     */
	List<NewsChannel> selectMyChannel(Map<String , Object> myChannels);

	/**
	 * 查找其他订阅
	 * @param myChannels
	 * @return
     */
	List<NewsChannel> selectOther(Map<String , Object> myChannels);
	NewsChannel selectByChannelName(Map<String , Object> map);
}
