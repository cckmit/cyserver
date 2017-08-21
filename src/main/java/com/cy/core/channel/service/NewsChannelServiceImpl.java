package com.cy.core.channel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.channel.dao.NewsChannelMapper;
import com.cy.core.channel.entity.NewsChannel;
import com.cy.core.dict.entity.Dict;
import com.cy.util.jms.Channel;

@Service("newsChannelService")
public class NewsChannelServiceImpl implements NewsChannelService
{

	@Autowired
	private NewsChannelMapper newsChannelMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;

	public DataGrid<NewsChannel> dataGrid(Map<String, Object> map)
	{
		DataGrid<NewsChannel> dataGrid = new DataGrid<NewsChannel>();
		long count = newsChannelMapper.countNewsChannel(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<NewsChannel> list = newsChannelMapper.selectNewsChannelList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public long countNewsChannel(Map<String, Object> map) {
		return newsChannelMapper.countNewsChannel(map);
	}
	
	public void save(NewsChannel newsChannel)
	{
		if(newsChannel.getChannelIcon() !=null)
			newsChannel.setChannelIcon(newsChannel.getChannelIcon().replace(Global.URL_DOMAIN, ""));
		newsChannelMapper.save(newsChannel);
	}

	public void delete(String ids)
	{
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		newsChannelMapper.delete(list);
	}

	public NewsChannel selectById(long channelId) {
		NewsChannel newsChannel = newsChannelMapper.selectById(channelId);
		if(newsChannel!=null && StringUtils.isNotBlank(newsChannel.getChannelIcon()))
			newsChannel.setChannelIcon(Global.URL_DOMAIN + newsChannel.getChannelIcon());

		return newsChannel;
	}

	public void update(NewsChannel newsChannel)
	{
		if(newsChannel.getChannelIcon() !=null)
			newsChannel.setChannelIcon(newsChannel.getChannelIcon().replace(Global.URL_DOMAIN, ""));
		newsChannelMapper.update(newsChannel);
	}

	public List<NewsChannel> selectAll()
	{
		return newsChannelMapper.selectAll();
	}

	public List<NewsChannel> selectNewsChannelList(Map<String, Object> map) {
		return newsChannelMapper.selectNewsChannelList(map);
	}

	
	/**
	 * 得到所有的标签的列表
	 * 
	 * @return List<NewsChannel>；
	 * 
	 */
	public List<NewsChannel> getALLLabelList() {
		
		ArrayList<NewsChannel> labelList = new ArrayList<NewsChannel>();
		
		
		
		ArrayList<Dict> dictList = (ArrayList<Dict>) newsChannelMapper.getALLLabel();
		
		for(Dict dict : dictList)
		{
			NewsChannel newsChannel = new NewsChannel();
			newsChannel.setChannelName(dict.getDictName());
			newsChannel.setChannelRemark(dict.getDictName());
			
			labelList.add(newsChannel);
		}
		
		return labelList;
	}	
	
	
	/**
	 * 得到所有的标签的组合字符串
	 * 
	 * @return Channel；
	 * 
	 */
	public Channel getALLLabelCombinationStr() {
		String separator = ",";
		
		Channel channel = new Channel();
		
		ArrayList<Dict> dictList = (ArrayList<Dict>) newsChannelMapper.getALLLabel();
		
		StringBuilder labelStr = new StringBuilder();
		
		for(Dict dict : dictList)
		{
			labelStr.append(dict.getDictName());
			labelStr.append(separator);
		}
		
		channel.setChannelId("阅读兴趣");
		channel.setChannelName("阅读兴趣");
		channel.setChannelRemark(labelStr.substring(0, labelStr.length() - 1));

		return channel;
	}




	/**
	 * 修改我的订阅
	 */
	public void updateMyNewsChannel(Message message,String content){
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		String id =(String) map.get("channelId");
		String accountNum = (String)map.get("accountNum");

		if(StringUtils.isBlank(accountNum)&& accountNum == null){
			message.setMsg("账号不能为空");
			message.setSuccess(false);
			return;
		}
		List<NewsChannel> newsChannels = newsChannelMapper.selectAll();
		String[] split = id.split(",");
		boolean c = false;
		for (String news:split){
			for (NewsChannel newsChannel:newsChannels){
				c = news.equals(String.valueOf(newsChannel.getChannelId()));
				if(c){
					break;
				}
			}
			if (!c){
				message.setMsg("修改失败，您修改的有频道不存在");
				message.setSuccess(false);
				return;
			}
		}
		map.put("channelId",id);
		newsChannelMapper.updateMyChannel(map);
		message.setMsg("修改订阅列表成功!");
		message.setSuccess(true);
	}
	/**
	 * 	 标识 = 0 查找所有
	 *   标识 = 1 查找我的订阅
	 *   标识 = 2 查询其他订阅
	 */

	public void findMyNesChannle(Message message,String content){
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		//用户ID
		String accountNum =(String) map.get("accountNum");
		String flag = (String)map.get("flag");

		if(StringUtils.isBlank(accountNum)){
			message.setMsg("账号不为空");
			message.setSuccess(false);
			return;
		}

		UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
		if(userProfile == null){
			message.setMsg("账号不存在");
			message.setSuccess(false);
			return;
		}

		if("0".equals(flag)){
			List<NewsChannel> all = new ArrayList<>();
			if(StringUtils.isNotBlank(userProfile.getChannels())){
				String[] channels = userProfile.getChannels().split(",");
				map.put("channelId", channels);
				List<NewsChannel> myChannels = newsChannelMapper.selectMyChannel(map);
				if(myChannels != null && myChannels.size()>0){
					for (NewsChannel channel:myChannels){
						channel.setSubscribe("1");
						all.add(channel);
					}
				}
				List<NewsChannel> otherChannels = newsChannelMapper.selectOther(map);
				if(otherChannels != null && otherChannels.size()>0){
					for (NewsChannel channel:otherChannels){
						channel.setSubscribe("0");
						all.add(channel);
					}
				}
			}else{
				List<NewsChannel> otherChannels = newsChannelMapper.selectOther(map);
				if(otherChannels != null && otherChannels.size()>0){
					for (NewsChannel channel:otherChannels){
						channel.setSubscribe("0");
						all.add(channel);
					}
				}
			}
			message.init(true,"查询全部成功", all);

		}else if("1".equals(flag)){
			if(StringUtils.isBlank(userProfile.getChannels())){
				message.init(false,"您还没有订阅频道",null);
			}else{
				String[] channels = userProfile.getChannels().split(",");
				map.put("channelId", channels);
				List<NewsChannel> list = newsChannelMapper.selectMyChannel(map);
				if(list != null && list.size()>0){
					message.init(false,"您还没有订阅频道",null);
				}else{
					message.init(true, "获取订阅频道成功",list);
				}

			}
		}else if("2".equals(flag)){
			if(StringUtils.isNotBlank(userProfile.getChannels())){
				String[] channels = userProfile.getChannels().split(",");
				map.put("channelId", channels);
			}
			message.init(true, "查询成功", newsChannelMapper.selectOther(map));
		}else{
			message.init(false, "非法的flag参数", null);
		}
	}
}
