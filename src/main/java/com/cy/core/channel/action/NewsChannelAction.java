package com.cy.core.channel.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.channel.entity.NewsChannel;
import com.cy.core.channel.service.NewsChannelService;
import com.cy.util.jms.Channel;
import com.cy.util.jms.MsgPushServer;

@Namespace("/newsChannel")
@Action(value = "newsChannelAction", results = {
		@Result(name = "initNewsChannelUpdate", location = "/page/admin/newsChannel/editNewsChannel.jsp"),
		@Result(name = "viewNewsChannel", location = "/page/admin/newsChannel/viewNewsChannel.jsp") })
public class NewsChannelAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(NewsChannelAction.class);

	private NewsChannel newsChannel;

	@Autowired
	private NewsChannelService newsChannelService;

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (newsChannel != null) {
			map.put("channelName", newsChannel.getChannelName());
		}
		super.writeJson(newsChannelService.dataGrid(map));
	}

	public void save() {
		Message message = new Message();
		try {
			String channelName = newsChannel.getChannelName();
			if (!channelName.equals("")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("channelName", channelName);
				if (newsChannelService.countNewsChannel(map) == 0) {
					newsChannelService.save(newsChannel);
					message.setMsg("保存成功");
					message.setSuccess(true);
					super.writeJson(message);
					return;
				}
			}
			message.setMsg("频道名称重复，请重新输入!");
			message.setSuccess(false);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/*
	 * 把频道信息推送给前端
	 */
	public void send() {
		Message message = new Message();
		try {
			MsgPushServer server = MsgPushServer.getInstance();
			List<Channel> channelList = new ArrayList<Channel>();

			List<NewsChannel> allList = newsChannelService.selectAll();
			if (allList != null && allList.size() > 0) {
				for (NewsChannel dict : allList) {
					Channel channel = new Channel();
					channel.setChannelId2(dict.getChannelId());
					channel.setChannelId(dict.getChannelName());
					channel.setChannelName(dict.getChannelName());
					channel.setChannelIcon(dict.getChannelIcon());
					channel.setChannelRemark(dict.getChannelRemark());
					channelList.add(channel);
				}
			}

			channelList.add(newsChannelService.getALLLabelCombinationStr());

			server.broadcastChannelUpdateMessage(channelList);

			message.setSuccess(true);
			message.setMsg("发送成功");
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("发送失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			newsChannelService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			newsChannelService.update(newsChannel);
			message.setMsg("修改成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void doNotNeedSecurity_initType() {
		List<NewsChannel> listNewsChannel = newsChannelService.selectAll();
		super.writeJson(listNewsChannel);
	}

	public void doNotNeedSessionAndSecurity_getAllChannels() {
		List<NewsChannel> listNewsChannel = newsChannelService.selectAll();
		NewsChannel newsChannel = new NewsChannel();
		Channel channel = newsChannelService.getALLLabelCombinationStr();
		newsChannel.setChannelId(channel.getChannelId2());
		newsChannel.setChannelIcon(channel.getChannelIcon());
		newsChannel.setChannelName(channel.getChannelName());
		newsChannel.setChannelRemark(channel.getChannelRemark());
		listNewsChannel.add(newsChannel);
		super.writeJson(listNewsChannel);
	}

	public String doNotNeedSessionAndSecurity_initNewsChannelUpdate() {
		newsChannel = newsChannelService.selectById(id);
		return "initNewsChannelUpdate";
	}

	public String getById() {
		newsChannel = newsChannelService.selectById(id);
		return "viewNewsChannel";
	}

	public NewsChannel getNewsChannel() {
		return newsChannel;
	}

	public void setNewsChannel(NewsChannel newsChannel) {
		this.newsChannel = newsChannel;
	}

	/**
	 * 得到所有的标签
	 * 
	 */
	public void doNotNeedSecurity_getALLLabelList() {
		List<NewsChannel> labels = newsChannelService.getALLLabelList();
		logger.info(JSON.toJSONString(labels));
		super.writeJson(labels);
	}

}
