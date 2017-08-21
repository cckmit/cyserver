package com.cy.mobileInterface.around.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cy.core.chatContacts.service.ChatContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.mobileInterface.around.entity.Around;
import com.cy.util.WebUtil;

@Service("aroundService")
public class AroundServiceImpl implements AroundService {

	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private ChatContactsService chatContactsService;

	@Override
	public void getNearbyUser(Message message, String content) {
		Around around = JSON.parseObject(content, Around.class);
		if (WebUtil.isEmpty(around.getAccountNum())) {// 协议检查
			message.setMsg("数据格式错误,accountNum不能为空!");
			message.setSuccess(false);
			return;
		}
		if (WebUtil.isEmpty(around.getPassword())) {
			message.setMsg("数据格式错误,密码不能为空!");
			message.setSuccess(false);
			return;
		}
		if (around.getMu_longitud() == 0.0) {
			message.setMsg("数据格式错误,GPS精度不能为空!");
			message.setSuccess(false);
			return;
		}
		if (around.getMu_latitude() == 0.0) {
			message.setMsg("数据格式错误,GPS纬度不能为空!");
			message.setSuccess(false);
			return;
		}

		// 查询当前用户自己的信息
		UserProfile userProfile = userProfileMapper.selectByAccountNum(around.getAccountNum());
		if (userProfile == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		if (!around.getPassword().equals(userProfile.getPassword())) {
			message.setMsg("密码不正确!");
			message.setSuccess(false);
			return;
		}

		userProfile.setMu_longitud(around.getMu_longitud());
		userProfile.setMu_latitude(around.getMu_latitude());
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		userProfile.setGps_time(stamp);
		// 用户当前使用附近人功能 将当前用户的GPS坐标更新
		userProfileMapper.updateGps(userProfile);
		if(around.getRadius()==0){
			around.setRadius(20000);
		}
		double[] aroundArray = WebUtil.getAround(around.getMu_longitud(), around.getMu_latitude(), around.getRadius());
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("accountNum", around.getAccountNum());
		queryMap.put("mu_longitud", around.getMu_longitud());
		queryMap.put("mu_latitude", around.getMu_latitude());
		queryMap.put("min_longitud", aroundArray[0]);
		queryMap.put("max_longitud", aroundArray[1]);
		queryMap.put("min_latitude", aroundArray[2]);
		queryMap.put("max_latitude", aroundArray[3]);
		List<UserProfile> list = userProfileMapper.selectNearPeople(queryMap);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		// 分别计算用户距离手机的距离
		if (list != null && list.size() != 0) {
			for (UserProfile userProfile2 : list) {
				Map<String, String> entity = new HashMap<String, String>();
				double targetLon = userProfile2.getMu_longitud();
				double targetLat = userProfile2.getMu_latitude();
				double distance = WebUtil.distance(around.getMu_longitud(), around.getMu_latitude(), targetLon, targetLat);
				entity.put("distance", String.valueOf((int) distance));
				entity.put("name", WebUtil.isEmpty(userProfile2.getName()) ? "" : userProfile2.getName());// 姓名
				entity.put("sex", WebUtil.isEmpty(userProfile2.getSex()) ? "" : userProfile2.getSex());// 性别
				entity.put("profession", WebUtil.isEmpty(userProfile2.getProfession()) ? "" : userProfile2.getProfession());// 行业
				entity.put("address", WebUtil.isEmpty(userProfile2.getAddress()) ? "" : userProfile2.getAddress());// 地点
				entity.put("hobby", WebUtil.isEmpty(userProfile2.getHobby()) ? "" : userProfile2.getHobby());// 兴趣
				entity.put("picture", WebUtil.isEmpty(userProfile2.getPicture()) ? "" : userProfile2.getPicture()); // 用户图片
				entity.put("picture_xd", WebUtil.isEmpty(userProfile2.getPicture_xd()) ? "" : userProfile2.getPicture_xd());
				entity.put("baseInfoId", userProfile2.getBaseInfoId());
				entity.put("accountNum", userProfile2.getAccountNum());
				entity.put("workUtil", WebUtil.isEmpty(userProfile2.getWorkUtil()) ? "" : userProfile2.getWorkUtil());
				entity.put("departName", WebUtil.isEmpty(userProfile2.getGroupName()) ? "" : userProfile2.getGroupName());
				entity.put("isFriend", chatContactsService.isFriendNew(userProfile.getAccountNum(),userProfile2.getAccountNum()));     // 用户与当前用户是否是好友关系(0:不是;1:是)
				entity.put("pictureRT", WebUtil.isEmpty(userProfile2.getPictureUrl())?"":userProfile2.getPictureUrl());
				mapList.add(entity);
			}
		}
		message.setMsg("查询成功!");
		message.setObj(mapList);
		message.setSuccess(true);
	}

}
