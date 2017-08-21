package com.cy.mobileInterface.qrCode.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.Maps;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.mobileInterface.qrCode.entity.QrCode;
import com.cy.util.WebUtil;

@Service("qrCodeService")
public class QrCodeServiceImpl implements QrCodeService {

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Override
	public void getUserInfoByAccountNum(Message message, String content) {
		QrCode qrCode = JSON.parseObject(content, QrCode.class);
		if (WebUtil.isEmpty(qrCode.getAccountNum())) {// 协议检查
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return;
		}

		UserProfile userProfile = userProfileMapper.selectByAccountNum(qrCode.getAccountNum());


		if(userProfile == null){
			message.setMsg("不存在此用户");
			message.setSuccess(false);
			return;
		}

		if(StringUtils.isNotBlank(userProfile.getAccountNum())){
			// 更新用户认证信息
			Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
			if(baseMap == null) {
				baseMap = Maps.newHashMap() ;
			}
			baseMap.put("accountNum", qrCode.getAccountNum());
			userProfileMapper.clearBaseInfoId(baseMap);
		}else{
			message.setMsg("用户账号不存在");
			message.setSuccess(false);
			return;
		}

		Map<String, String> entityMap = new HashMap<String, String>();
		entityMap.put("name", WebUtil.isEmpty(userProfile.getName()) ? "" : userProfile.getName());// 姓名
		entityMap.put("sex", WebUtil.isEmpty(userProfile.getSex()) ? "" : userProfile.getSex());// 性别
		entityMap.put("profession", WebUtil.isEmpty(userProfile.getProfession()) ? "" : userProfile.getProfession());// 行业
		entityMap.put("address", WebUtil.isEmpty(userProfile.getAddress()) ? "" : userProfile.getAddress());// 地点
		entityMap.put("hobby", WebUtil.isEmpty(userProfile.getHobby()) ? "" : userProfile.getHobby());// 兴趣
		entityMap.put("picture", WebUtil.isEmpty(userProfile.getPicture()) ? "" : userProfile.getPicture()); // 用户图片
		entityMap.put("picture_xd", WebUtil.isEmpty(userProfile.getPicture_xd()) ? "" : userProfile.getPicture_xd()); // 用户头像相对路径图片
		entityMap.put("baseInfoId", userProfile.getBaseInfoId());
		entityMap.put("accountNum", userProfile.getAccountNum());
		entityMap.put("workUtil", WebUtil.isEmpty(userProfile.getWorkUtil()) ? "" : userProfile.getWorkUtil());
		entityMap.put("departName", WebUtil.isEmpty(userProfile.getGroupName()) ? "" : userProfile.getGroupName());
		entityMap.put("birthday", WebUtil.isEmpty(userProfile.getBirthday()) ? "" : userProfile.getBirthday());
		entityMap.put("position", WebUtil.isEmpty(userProfile.getPosition()) ? "" : userProfile.getPosition() );
		entityMap.put("email", WebUtil.isEmpty(userProfile.getEmail()) ? "" : userProfile.getEmail() );
		entityMap.put("sign", WebUtil.isEmpty(userProfile.getSign()) ? "" : userProfile.getSign()  );
		entityMap.put("isChangedSex", WebUtil.isEmpty(userProfile.getIsChangedSex()) ? "" : userProfile.getIsChangedSex());
		entityMap.put("phoneNum",WebUtil.isEmpty(userProfile.getPhoneNum())? "" :userProfile.getPhoneNum()); //手机号
		entityMap.put("pictureRT", WebUtil.isEmpty(userProfile.getPictureUrl())?"":userProfile.getPictureUrl());
		entityMap.put("isActivate", userProfile.getIsActivated());


		if (entityMap.size() > 0) {
			message.setObj(entityMap);
			message.setSuccess(true);
			message.setMsg("查询成功!");
		} else {
			message.setSuccess(false);
			message.setMsg("找不到该用户!");
		}
	}

	@Override
	public void getUserQRCodeAddress(Message message, String content, String address) {
		QrCode qrCode = JSON.parseObject(content, QrCode.class);
		if (WebUtil.isEmpty(qrCode.getAccountNum())) {// 协议检查
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return;
		}
		String sign = WebUtil.getBase64(qrCode.getAccountNum());
		JSONObject json = new JSONObject();
		json.put("address", address + "?sign=" + sign);
		message.setObj(json);
		message.setSuccess(true);
	}

}
