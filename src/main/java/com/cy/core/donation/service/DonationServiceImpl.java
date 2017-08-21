package com.cy.core.donation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.JsonUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.common.utils.alipay.entity.AlipayResult;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dict.utils.DictUtils;
import com.cy.core.donation.entity.*;
import com.cy.core.donation.utils.Certificate;
import com.cy.core.project.dao.ProjectMapper;
import com.cy.core.project.entity.Project;
import com.cy.core.weiXin.dao.WeiXinUserMapper;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinUserService;
import com.cy.smscloud.http.FounHttpUtils;
import com.cy.smscloud.utils.FounMd5Utils;
import com.cy.system.Global;
import com.cy.util.DateUtils;
import com.cy.util.PairUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cy.smscloud.exception.NetServiceException;

import com.cy.base.entity.DataGrid;
import com.cy.core.donation.dao.DonationMapper;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.SystemUtil;

@Service("donationService")
public class DonationServiceImpl implements DonationService {
	private static final Logger logger = Logger.getLogger(DonationServiceImpl.class);

	@Autowired
	private DonationMapper donationMapper;


	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private WeiXinUserService weiXinUserService;

	public void save(Donation donation) {
		if (donation.getDonationTime()==null){
			donation.setDonationTime(TimeZoneUtils.getDate());
		}

		// if (donation.getUserId() != null && donation.getUserId().length() >
		// 0) {
		// UserInfo userInfo =
		// userInfoMapper.selectByUserId(donation.getUserId());
		// donation.setX_school(userInfo.getSchoolName());
		// donation.setX_depart(userInfo.getDepartName());
		// donation.setX_grade(userInfo.getGradeName());
		// donation.setX_clazz(userInfo.getClassName());
		// donation.setX_major(userInfo.getMajorName());
		// donation.setX_name(userInfo.getUserName());
		// donation.setX_sex(userInfo.getSex());
		// } else {
		// if (donation.getFlag() == 1) {
		// donation.setX_grade(donation.getX_grade() + "级");
		// }
		// }

		if (donation.getUserId() != null && donation.getUserId().length() > 0) {
			donation.setFlag(1);
		}

		donationMapper.save(donation);
	}

	public void update(Donation donation) {

		logger.info("-------> donation : " + donation.toString());
		if(donation.getConfirmStatus() >= 30){
			donation.setPayStatus(1);
			donation.setPayTime(TimeZoneUtils.getDate());
		}
		donationMapper.update(donation);


		//生成证书并保存
		if(donation.getConfirmStatus() >= 30){
			try{
				Donation donationTmp = selectById(donation.getDonationId());
				if(StringUtils.isBlank(donationTmp.getCertificatePicUrl())){
					createCertificate(donation.getDonationId());
				}
			}catch (Exception e) {
				logger.error(e, e);
			}
		}
	}

	public void delete(String ids) {
		List<Long> list = new ArrayList<Long>();
		String[] idArray = ids.split(",");
		if (idArray != null) {
			for (String id : idArray) {
				list.add(Long.parseLong(id));
			}
		}
		donationMapper.delete(list);
	}

	public Donation selectById(long id) {
		return donationMapper.selectById(id);
	}

	public DataGrid<Donation> dataGrid(Map<String, Object> map) {
		DataGrid<Donation> dataGrid = new DataGrid<Donation>();
		long total = donationMapper.countDonation(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Donation> list = donationMapper.selectDonationList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public DataGrid<Donation> dataGridForCount(Map<String, Object> map) {
		DataGrid<Donation> dataGrid = new DataGrid<Donation>();
		long total = donationMapper.countDonationForCount(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Donation> list = donationMapper.selectDonationForCountList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	@Override
	public void updateFromShouXin(Donation donation) {
		donationMapper.updateFromShouXin(donation);
	}

	@Override
	public List<Donation> selectRandom50() {
		return donationMapper.selectRandom50();
	}

	@Override
	public List<Donation> selectByNameAndPhone(Donation donation) {
		return donationMapper.selectByNameAndPhone(donation);
	}

	@Override
	public int saveFromMobile(Donation donation) {
		int code = 0 ;	// 0: 保存成功;1:用户不存在;2:用户未认证
		// 根据accountNum获取用户信息
		UserProfile userProfile = userProfileMapper.selectById(String.valueOf(donation.getAccountNum()));
		if (userProfile != null) {
			donation.setDonationTime(new Date());
			if(StringUtils.isNotBlank(userProfile.getBaseInfoId())){
				donation.setFlag(1);
			}else{
				donation.setFlag(0);
			}

			donation.setOrderNo(SystemUtil.getOrderNo());
			donation.setPayStatus(0);
			donation.setX_email(userProfile.getEmail());
			donation.setX_address(userProfile.getAddress());
			donation.setX_position(userProfile.getPosition());
			donation.setX_name(userProfile.getName());
			donation.setX_phone(userProfile.getPhoneNum());
			donation.setX_workunit(userProfile.getWorkUtil());
//			donation.setUserId(userProfile.getAccountNum());
//			donation.setUserId(userProfile.getBaseInfoId());
//			Map<String, String> map = donationMapper.selectClassInfo(userProfile.getAccountNum());

			donation.setUserId(userProfile.getAccountNum());
			/*if (map == null || map.isEmpty()) {
				System.out.println("----------> 当前用户未认证!");
//				code = 2 ;
			} else {
				donation.setUserId(map.get("baseInfoId"));
				donation.setX_school(map.get("school"));
				donation.setX_depart(map.get("depart"));
				donation.setX_grade(map.get("grade"));
				donation.setX_clazz(map.get("clazz"));
				donation.setX_major(map.get("major"));
			}*/
			donationMapper.save(donation);
		}else if(donation.getAccountNum() == 0){
			donation.setDonationTime(TimeZoneUtils.getDate());
			donation.setOrderNo(SystemUtil.getOrderNo());
			donation.setPayStatus(0);

			donationMapper.save(donation);
		} else {
			code = 1 ;
		}
		return code ;
	}

	@Override
	public MyDonation listAll(Map<String, Object> map) {
		MyDonation myDonation = new MyDonation();
		long total = donationMapper.countDonationForMobile(map);
		myDonation.setCountDonateHistory(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<DonateHistory> donateHistories = new ArrayList<DonateHistory>();
		List<Donation> list = donationMapper.selectDonationForCountMobile(map);
		if (list != null) {
			for (Donation donation : list) {
				DonateHistory donateHistory = new DonateHistory();
				donateHistory.setConfirmTime(donation.getDonationTime());
				donateHistory.setMoney(donation.getMoney());
				donateHistory.setPayStatus(donation.getPayStatus());
				if (donation.getProject() != null) {
					donateHistory.setProjectName(donation.getProject().getProjectName());
				}
				donateHistory.setDonateUrl("../../donation/donationAction!doNotNeedSessionAndSecurity_getById.action?id=" + donation.getDonationId()
						+ "&accountNum=" + donation.getAccountNum());
				donateHistories.add(donateHistory);
			}
		}
		myDonation.setDonateHistoryList(donateHistories);
		return myDonation;
	}

	@Override
	public NewDonate listNew(Map<String, Object> map) {
		NewDonate newDonate = new NewDonate();
		long total = donationMapper.countDonationForMobileNew(map);
		newDonate.setCountDonateList(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<NewDonateItem> newDonateItems = new ArrayList<NewDonateItem>();
		List<Donation> list = donationMapper.selectDonationForCountMobileNew(map);
		if (list != null) {
			for (Donation donation : list) {
				NewDonateItem newDonateItem = new NewDonateItem();
				if (donation.getAnonymous() == 1) {
					newDonateItem.setX_name("匿名");
				} else {
					newDonateItem.setX_name(donation.getX_name());
				}
				newDonateItem.setMoney(donation.getMoney());
				if (donation.getProject() != null) {
					newDonateItem.setProjectName(donation.getProject().getProjectName());
				}
				newDonateItem.setDonateItemUrl("../../project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=" + donation.getProjectId()
						+ "&accountNum=" + map.get("accountNum"));
				newDonateItems.add(newDonateItem);
			}
		}
		newDonate.setNewDonateList(newDonateItems);
		return newDonate;
	}

	@Override
	public Donation selectByOrderNo(String orderNo) {
		Donation donation = donationMapper.selectByOrderNo(orderNo);
		donation.setProject(projectMapper.selectById(donation.getProjectId()));
		return donation;
	}


	/***********************************************************************
	 *
	 * 【爱心捐赠订单】相关API（以下区域）
	 *
	 * 注意事项：
	 * 1、方法名-格式要求
	 * 创建方法：save[Name]
	 * 撤销方法：remove[Name]
	 * 查询分页列表方法：find[Name]ListPage
	 * 查询列表方法：find[Name]List
	 * 查询详细方法：find[Name]
	 *
	 ***********************************************************************/

	/**
	 * 查询我的捐赠项目列表
	 * @param message
	 * @param content
	 */
	public void findDonationList(Message message, String content) {
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String page = (String) map.get("page");
		String rows = (String) map.get("rows");
		String accountNum = (String) map.get("accountNum");
//		String isNotDonated = (String) map.get("isNotDonated");
		String openId = (String) map.get("openId");
		String accountAppId = (String) map.get("accountAppId");


		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
			map.put("start", start);
			map.put("rows", Integer.valueOf(rows));
		} else {
			map.put("isNotLimni", "1");
		}

		if(StringUtils.isNotBlank(accountNum)){
			UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);

			if(userProfile == null){
				message.init(false,"用户ID不存在", null);
				return;
			}
			if(StringUtils.isNotBlank(userProfile.getBaseInfoId())){
				userProfile.setAuthenticated("1");
			}else{
				userProfile.setAuthenticated("0");
			}
			if(StringUtils.isNotBlank(openId)){
				WeiXinUser weiXinUser = weiXinUserService.saveUserInfoByOpenId(openId, accountAppId);
				if(weiXinUser != null){
					map.put("weixinUserId", weiXinUser.getId());
				}
			}

			long total = donationMapper.countDonationForMobile(map) ;
			List<DonateHistory> donateHistories = new ArrayList<>();
			List<Donation> list = donationMapper.selectDonationForCountMobile(map);
			DonateDataGrid<Donation> dataGrid = new DonateDataGrid<>();
			if (list != null) {
				for (Donation donation : list) {
					DonateHistory donateHistory = new DonateHistory();
					donateHistory.setConfirmTime(donation.getDonationTime());
					donateHistory.setMoney(donation.getMoney());
					donateHistory.setPayStatus(donation.getPayStatus());
					if (donation.getProject() != null) {
						donateHistory.setProjectName(donation.getProject().getProjectName());
					}
					donateHistory.setDonateUrl("../../donation/donationAction!doNotNeedSessionAndSecurity_getById.action?id=" + donation.getDonationId()
							+ "&accountNum=" + donation.getAccountNum());
					donateHistories.add(donateHistory);
				}
			}

				CountPeopleAndMoney countPeopleAndMoney = donationMapper.countDonationMoneyAndPeople(map);

				dataGrid.setTotalMoney(countPeopleAndMoney.getTotalMoney());
				dataGrid.setTotalPeople(countPeopleAndMoney.getTotalPeople());
				dataGrid.setUserName(userProfile.getName());
				dataGrid.setUserSex(userProfile.getSex());
				dataGrid.setUserTel(userProfile.getPhoneNum());
				dataGrid.setUserPic(userProfile.getPictureUrl());
				dataGrid.setIsActivate(userProfile.getIsActivated());
				if(StringUtils.isNotBlank(userProfile.getBaseInfoId())){
					dataGrid.setUserAuth("1");
				}else{
					dataGrid.setUserAuth("0");
				}
				dataGrid.setTotal(total);
				dataGrid.setRows(list);

				message.init(true ,"查询成功",dataGrid,null);
//			}
		}else if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(accountAppId)){
			WeiXinUser weiXinUser = weiXinUserService.saveUserInfoByOpenId(openId, accountAppId);
			if(weiXinUser != null){
				DonateDataGrid<Donation> dataGrid = new DonateDataGrid<>();
				dataGrid.setUserName(weiXinUser.getNickname());
				if("1".equals(weiXinUser.getSex())){
					dataGrid.setUserSex("0");
				}else if("2".equals(weiXinUser.getSex())){
					dataGrid.setUserSex("1");
				}
				dataGrid.setUserPic(weiXinUser.getHeadimgurl());
				dataGrid.setUserAuth("0");
				map.put("weixinUserId", weiXinUser.getId());
				map.put("accountNum", "");
				long total = donationMapper.countDonationForMobile(map);
				dataGrid.setTotal(total);
				List<Donation> totalList = donationMapper.selectDonationForCountMobile(map);
				if(totalList == null || totalList.size() <= 0){
					message.init(false, "未查到您的捐赠", dataGrid);
					return;
				}else{
					dataGrid.setRows(totalList);
					CountPeopleAndMoney countPeopleAndMoney = donationMapper.countDonationMoneyAndPeople(map);
					dataGrid.setTotalMoney(countPeopleAndMoney.getTotalMoney());
					dataGrid.setTotalPeople(countPeopleAndMoney.getTotalPeople());
				}

				message.init(true,"查询成功",dataGrid);
			}else{
				message.init(false,"不存在此openId",null);
			}
		}

	}


	/**
	 * 查询最新捐赠项目列表
	 * @param message
	 * @param content
	 */
	public void findDonationNewList(Message message, String content) {
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String page = (String) map.get("page");
		String rows = (String) map.get("rows");
		String accountNum = (String) map.get("accountNum");
		String openId = (String) map.get("openId");
		String accountAppId = (String) map.get("accountAppId");
		String money = (String) map.get("money");

		if(StringUtils.isNotBlank(money)){
			double aDouble =Double.parseDouble(money);
			map.put("money", aDouble);
		}

		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
			map.put("start", start);
			map.put("rows", Integer.valueOf(rows));
		} else {
			map.put("isNoLimit", "1");
		}
		WeiXinUser weiXinUser = null;
		if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(accountAppId)){
			weiXinUser = weiXinUserService.saveUserInfoByOpenId(openId, accountAppId);
			if(weiXinUser == null){
				message.init(false, "微信用户不存在", null);
				return;
			}
			map.put("weixinUserId", weiXinUser.getId());
			map.put("accountNum", "");
		}

		//默认获取基金会公众号和校友会公众号捐赠记录
//		map.put("payTypes","10,20");
//		map.put("donationType","10");

		DonateDataGrid<Donation> dataGrid = new DonateDataGrid<>();
		long total = donationMapper.countDonationForMobileNew(map);
		List<NewDonateItem> newDonateItems = new ArrayList<NewDonateItem>();
		List<Donation> list = donationMapper.selectDonationForCountMobileNew(map);
		if (list != null && list.size() >0 ) {
			for (int i = 0 ; i < list.size(); i++) {
				//如果不是查询用户自己的的捐赠记录，就需要把不公开的置空
				if(StringUtils.isBlank(accountNum) && StringUtils.isBlank(openId) && "0".equals(list.get(i).getMessageIsOpen())){
					list.get(i).setMessage("");
				}

				if("20".equals(list.get(i).getDonationType())){
					String goodsCount = list.get(i).getItemNum();
					//获取物品字典
					Map<String,String> stringMap = Maps.newHashMap();
					stringMap.put("dictTypeValue","29");
					List<Dict> dictList = DictUtils.findDictList(stringMap);

					if(dictList != null && dictList.size() > 0){
						for(Dict d:dictList){
							if(d.getDictValue().equals(list.get(i).getItemType())){
								goodsCount = d.getDictName() + goodsCount;
								break;
							}
						}
					}

					//获取物品单位字典
					stringMap.put("dictTypeValue","31");
					List<Dict> dicts = DictUtils.findDictList(stringMap);
					if(dicts != null && dicts.size() > 0){
						for(Dict d: dicts){
							if(d.getDictValue().equals(list.get(i).getItemType())){
								goodsCount += d.getDictName();
								break;
							}
						}
					}

					list.get(i).setItemNum(goodsCount);
				}else{
					list.get(i).setItemNum("￥" + list.get(i).getPayMoney());
				}

				NewDonateItem newDonateItem = new NewDonateItem();
				if (list.get(i).getAnonymous() == 1) {
					list.get(i).setX_name("匿名");
					list.get(i).setDonorName("匿名");
					newDonateItem.setX_name("匿名");
				} else {
					newDonateItem.setX_name(list.get(i).getX_name());
				}
				newDonateItem.setMoney(list.get(i).getMoney());
				if (list.get(i).getProject() != null) {
					newDonateItem.setProjectName(list.get(i).getProject().getProjectName());
				}
				newDonateItem.setDonateItemUrl("../../project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=" + list.get(0).getProjectId()
						+ "&accountNum=" + map.get("accountNum"));
				newDonateItems.add(newDonateItem);
			}
			dataGrid.setUserName(list.get(0).getX_name());
		}
		if(StringUtils.isNotBlank(accountNum)){
			UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
			if(userProfile != null){
				dataGrid.setUserName(userProfile.getName());
			}
		}else if(weiXinUser != null){
			dataGrid.setUserName(StringUtils.isNotBlank(weiXinUser.getNickname())?weiXinUser.getNickname():"");
		}

		CountPeopleAndMoney countPeopleAndMoney= donationMapper.countDonationMoneyAndPeople(map);


		dataGrid.setTotalMoney(countPeopleAndMoney.getTotalMoney());
		dataGrid.setTotalPeople(countPeopleAndMoney.getTotalPeople());
		dataGrid.setTotal(total);
		dataGrid.setRows(list);

		message.init(true ,"查询成功",dataGrid,null);
	}

	/**
	 * 查询捐赠详情接口
	 * @param message
	 * @param content
	 */
	public void findMyDonation(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String id = (String)map.get("id");
		String orderNo = (String) map.get("orderNo");
		Donation donation =null;
		if (StringUtils.isNotBlank(id)) {
			donation = donationMapper.selectById(Long.parseLong(id));
		}else {
			donation = donationMapper.selectByOrderNo(orderNo);
		}
		if(donation == null){
			message.init(false, "捐赠不存在", null);
			return;
		}
		if(donation.getConfirmStatus() >= 30 && StringUtils.isBlank(donation.getCertificatePicUrl())){
			try{
				createCertificate(donation.getDonationId());
				donation = donationMapper.selectById(Long.parseLong(id));
			}catch (Exception e) {
				logger.error(e, e);
			}
		}

		message.init(true ,"查询成功",donation);

	}

	/**
	 * 下单接口
	 * @param message
	 * @param content
	 */
	public void saveDonation(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Donation donation = JSON.parseObject(content, Donation.class);

		if(donation.getAccountNum() == null){
			donation.setAccountNum((long)0);
		}

		// 存儲微信用戶
		if(StringUtils.isNotBlank(donation.getOpenId()) && StringUtils.isNotBlank(donation.getAccountAppId())){
			WeiXinUser weiXinUser = weiXinUserService.saveUserInfoByOpenId(donation.getOpenId(), donation.getAccountAppId());
			donation.setWeixinUserId(weiXinUser.getId());
		}

		if(StringUtils.isBlank(String.valueOf(donation.getProjectId()))){
			message.setMsg("捐赠项目Id不能为空");
			message.setSuccess(false);
			return;
		}

		if(StringUtils.isBlank(donation.getDonationType())){
			donation.setDonationType("10");
		}

		if("10".equals(donation.getDonationType()) || StringUtils.isBlank(donation.getDonationType())){
			if(StringUtils.isBlank(String.valueOf(donation.getMoney()))){
				message.setMsg("请输入捐赠金额");
				message.setSuccess(false);
				return;
			}
		}

		if(StringUtils.isBlank(donation.getPayType())){
			// 入口类型，默认校友会
			donation.setPayType("10");
		}
		if(StringUtils.isBlank(donation.getPayMethod())){
			// 支付途径,默认手机
			donation.setPayMethod("10");
		}
		donation.setPayStatus(0);
		donation.setConfirmStatus(10);
		if(StringUtils.isBlank(donation.getDonorType())){
			donation.setDonorType("10");
		}
		//如果捐赠方为空，则将联系人置为捐赠方
		if (StringUtils.isBlank(donation.getDonorName())){
			donation.setDonorName(donation.getX_name());
		}
		if (StringUtils.isBlank(donation.getX_name())){
			donation.setX_name(donation.getDonorName());
		}
		int code = saveFromMobile(donation);
		if(code == 0) {
			long id = donation.getDonationId();

			message.init(true ,"下单成功",String.valueOf(id) );
		} else if (code == 1){
			message.init(false ,"用户不存在",null);
		} else if (code == 2){
			message.init(false ,"当前用户未认证,请先认证",null);
		}
	}

	/**
	 * 取消订单接口
	 * @param message
	 * @param content
	 */
	public void cancleDonate(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, String> map = JSON.parseObject(content, Map.class);
		String id = map.get("donationId");
		Donation donation = selectById(Integer.parseInt(id));
		if(donation == null){
			message.init(false, "未知的捐赠", null);
			return;
		}
		if("1".equals(donation.getPayStatus())){
			message.init(false, "无法删除已支付的捐赠", null);
			return;
		}

		delete(id);
		message.init(true, "成功取消该捐赠", null);

	}

	public void updatePayStatus(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Donation donation = JSON.parseObject(content, Donation.class);
		if(donation.getDonationId() <= 0 ){
			message.setMsg("请提供捐赠编号");
			message.setSuccess(false);
			return;
		}

		Donation tmp = selectById(donation.getDonationId());
		if(tmp == null){
			message.setMsg("未知的捐赠订单");
			message.setSuccess(false);
			return;
		}

		if("1".equals(tmp.getPayStatus())){
			message.init(false, "已支付的订单，请勿重新支付", null);
			return;
		}

		createCertificate(donation.getDonationId());

		donation.setPayTime(TimeZoneUtils.getDate());
		donation.setPayMode("1");
		donation.setPayStatus(1);
		donation.setPayTime(TimeZoneUtils.getDate());
		donation.setConfirmStatus(30);		//支付完成直接为已确认状态
		donation.setConfirmId(0);
		donation.setConfirmTime(TimeZoneUtils.getDate());
		donation.setPayMoney(tmp.getMoney());
		donationMapper.update(donation);
		message.init(true, "成功提交发货信息", null);
	}

	/**
	 * 确认发货接口
	 * @param message
	 * @param content
	 */
	public void updateDonationStatus(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Donation donation = JSON.parseObject(content, Donation.class);
		if(StringUtils.isBlank(donation.getOrderNo())){
			message.setMsg("请提供捐赠编号");
			message.setSuccess(false);
			return;
		}
		if(StringUtils.isBlank(donation.getUserId())){
			message.setMsg("请提供用户编号");
			message.setSuccess(false);
			return;
		}
		Donation tmp = selectByOrderNo(donation.getOrderNo());
		if(tmp == null){
			message.setMsg("未知的捐赠ID");
			message.setSuccess(false);
			return;
		}
		UserProfile userProfile = userProfileMapper.selectByAccountNum(donation.getUserId());
		if(userProfile == null){
			message.setMsg("用户ID不存在");
			message.setSuccess(false);
			return;
		}
		if(!donation.getUserId().equals(tmp.getUserId())){
			message.setMsg("您不是捐赠发起者");
			message.setSuccess(false);
			return;
		}
		if(StringUtils.isBlank(donation.getDonationCourier())){
			message.setMsg("请提供快递公司名称");
			message.setSuccess(false);
			return;
		}
		if(StringUtils.isBlank(donation.getDonationCourierNumber())){
			message.setMsg("请提供快递单号");
			message.setSuccess(false);
			return;
		}
		donation.setConfirmStatus(20);
		donationMapper.update(donation);
		message.init(true, "成功提交发货信息", null);
	}

	/**
	 * 支付宝订单支付回调接口
	 * @param alipayResult
	 */
	public PairUtil<Integer,String> donationPayFeedBack(AlipayResult alipayResult){
		PairUtil<Integer,String> resultPair = new PairUtil<Integer,String>() ;
		int code = 0 ;		// 0:支付成功;1:已支付,无需重新支付;2:订单不存在;3:其他错误
		String accountNum = null ;
		try {
			if (alipayResult != null) {

				Donation donation = new Donation();
//				donation.setOrderNo(alipayResult.getOut_trade_no());
				// 通过订单编号获取订单详情
				donation = selectByOrderNo(alipayResult.getOut_trade_no());

				if (donation != null) {
					accountNum = String.valueOf(donation.getAccountNum()) ;
					// 当订单存在,判断订单状态
					if (donation.getPayStatus() == 1) {
						code = 1;
					} else {
						if (AlipayResult.TRADE_STATUS_TRADE_FINISHED.equals(alipayResult.getTrade_status()) || AlipayResult.TRADE_STATUS_TRADE_SUCCESS.equals(alipayResult.getTrade_status())) {
							//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序

							//注意：
							//该种交易状态只在两种情况下出现
							//1、开通了普通即时到账，买家付款成功后。
							//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。

							//支付完成更新订单信息
							updateDonation(donation,alipayResult);


						} else {
							code = 3;
						}
					}
				} else {
					code = 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = 3 ;
		}
		resultPair.setOne(code);
		resultPair.setTwo(accountNum);
		return resultPair ;
	}
	/**
	 * 方法 updateDonation 的功能描述：支付完成更新订单信息
	 *                                 注意：该方法不许修改，其它地方也不许调用
	 * @createAuthor niu
	 * @createDate 2017-05-11 17:46:09
	 * @param
	 * @return
	 * @throw
	 *
	*/
	public void updateDonation(Donation donation,AlipayResult alipayResult) throws ParseException {
		if(StringUtils.isNotBlank(alipayResult.getTotal_amount())) {
			donation.setPayMoney(Double.valueOf(alipayResult.getTotal_amount()));
		} else if(StringUtils.isNotBlank(alipayResult.getTotal_fee())) {
			donation.setPayMoney(Double.valueOf(alipayResult.getTotal_fee()));
		}
		donation.setPayMode("10");//支付宝
		if(StringUtils.isNotBlank(alipayResult.getGmt_payment())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			donation.setPayTime(sdf.parse(alipayResult.getGmt_payment()));
		}
		donation.setPayStatus(1);
		donation.setConfirmStatus(30);
		donation.setConfirmId(0);
		donation.setConfirmTime(TimeZoneUtils.getDate());
		donation.setAlipayNumber(donation.getOrderNo());
		update(donation);

		if(StringUtils.isNotBlank(Global.founUrl)){
			Map<String , Object> map = new HashMap();
			map.put("projectIncome", String.valueOf(donation.getProject().getProjectId()));
			map.put("personLiable",donation.getX_name());
			map.put("personNum",donation.getX_phone());
			map.put("paymentCount",String.valueOf(donation.getMoney()));
			map.put("createDate", TimeZoneUtils.getFormatDate().substring(0,10) );
			map.put("incomeType", "支付宝");
			map.put("orderNum", donation.getOrderNo());
			FounHttpUtils.saveFounIncome(map);
		}
	}

    /**
     * 方法 wechatDonationPayBack 的功能描述：微信支付订单更新
     * @createAuthor niu
     * @createDate 2017-05-12 12:35:22
     * @param
     * @return
     * @throw
     *
    */

	public Donation wechatDonationPayBack(String orderNo){
		Donation donation = selectByOrderNo(orderNo);
		if (donation != null && donation.getPayStatus() != 1) {

			//支付状态： 1 已支付
			donation.setPayStatus(1);

			//支付方式（10:支付宝支付；20:微信支付）
			donation.setPayMode("20");
			//支付途径（10:手机APP；20:网站；30:微信）
			donation.setPayMethod("30");

			donation.setPayTime(new Date());

			donation.setPayMoney(donation.getMoney());
			donation.setConfirmStatus(30);
			donation.setConfirmId(0);
			donation.setConfirmTime(new Date());
			update(donation);
			if(StringUtils.isNotBlank(Global.founUrl)){
				Map<String, Object> map = new HashMap();
				Long projectId =donation.getProject().getProjectId();
				Project project= projectMapper.selectById(projectId);
				String projectIncome = project.getFounProject();
				if(!("").equals(projectIncome) && projectIncome != null){
					map.put("projectIncome",projectIncome);
				}
				map.put("personLiable", donation.getX_name());
				map.put("personNum", donation.getX_phone());
				map.put("paymentCount", String.valueOf(donation.getMoney()));
				map.put("createDate", TimeZoneUtils.getFormatDate().substring(0,10) );
				map.put("incomeType", "微信");
				map.put("orderNum", donation.getOrderNo());

				FounHttpUtils.saveFounIncome(map);
			}

			//生成证书
			//donationService.createCertificate(donation.getDonationId());
		}
		return donation;

	}

	public void updatePayType(String orderNo,String payType,String payMethod){
		if (StringUtils.isNotBlank(orderNo) && StringUtils.isNotBlank(payType)){
			Donation donation = new Donation();
			donation.setOrderNo(orderNo);
			donation.setPayType(payType);
			if (StringUtils.isNotBlank(payMethod)) {
				donation.setPayMethod(payMethod);
			}
			donationMapper.updatePayType(donation);
		}
	}

	public int createCertificate(long donationId){
		try{
			Donation donation  = selectById(donationId);
			if(donation == null){
				return 1;
			}

			String certificateImage =  Certificate.createCertificate(donation);
			donation.setCertificatePicUrl(certificateImage);

			donationMapper.update(donation);
			return 0;
		}catch (Exception e){
			logger.error(e, e);
			return -1;
		}

	}
	/*
		* 基金会保存收入记录接口
		* 基金会保存收入自动带入窗友收入记录信息
		* */
	public void saveFounIncome(Message message,String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		try {

			Map<String, Object> map1 = JsonUtils.json2map(content);
			String jsonMd5 = (String) map1.get("jsonMd5");
            String json = (String) map1.get("json");

            Map<String, Object> map = JsonUtils.json2map(json);

            String founMd5 = FounMd5Utils.getFounMd5(map);
			logger.info("---------> 加密后FounMd5  : " + founMd5);
			if (!jsonMd5.equals(founMd5)){
				message.setMsg("密钥不匹配");
				message.setSuccess(false);
				return;
			}
			String name = (String) map.get("name");
			if (StringUtils.isBlank(name)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			String donorName = (String) map.get("donorName");//组织名称
			if (StringUtils.isBlank(donorName)) {
				message.setMsg("未传入组织名称参数");
				message.setSuccess(false);
				return;
			}
			String paymoney = String.valueOf(map.get("payMoney")) ;//支付金额
			if (StringUtils.isBlank(paymoney)) {
				message.setMsg("未传入支付金额参数");
				message.setSuccess(false);
				return;
			}
			String founProject = (String) map.get("founProject"); //项目名称
			if (StringUtils.isBlank(founProject)) {
				message.setMsg("未传入项目名称参数");
				message.setSuccess(false);
				return;
			}
			String donorNum = (String) map.get("donorNum");//支付人电话号
			if (StringUtils.isBlank(donorNum) && donorNum.length()>10) {
				message.setMsg("未传入支付人电话号参数");
				message.setSuccess(false);
				return;
			}
			String createDate = (String) map.get("createDate");//支付时间
			if (StringUtils.isBlank(createDate)) {
				message.setMsg("未传入支付时间参数");
				message.setSuccess(false);
				return;

			}
			String donorType = (String)map.get("donorType");//支付人类型
			if (StringUtils.isBlank(donorType)) {
				message.setMsg("未传入支付人类型参数");
				message.setSuccess(false);
				return;
			}
			String incomeId = (String) map.get("incomeId");//基金会项目id
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			UserProfile userProfile = userProfileMapper.selectByPhoneNum(donorNum);
			if (userProfile ==null){
				userProfile = new UserProfile();
				userProfile.setName(name);
				userProfile.setPhoneNum(donorNum);
				userProfile.setPassword(donorNum.substring(5,11));
				userProfileMapper.save(userProfile);
			}
			Donation donation = new Donation();
			donation.setUserId(userProfile.getAccountNum());//捐赠人
			donation.setDonationType("10");//捐赠类型
			donation.setFlag(0);//捐赠人类型  社会人士
			donation.setNeedInvoice("20");//不需要发票
			donation.setDonationTime(DateUtils.parseDate(createDate));//捐赠时间
			donation.setConfirmTime(DateUtils.parseDate(createDate));//确认时间
			donation.setPayTime(DateUtils.parseDate(createDate));//支付时间
			donation.setPayMoney(Double.valueOf(paymoney));//支付金额
			donation.setMoney(Double.valueOf(paymoney));//捐赠金额
			donation.setPayMode("50");//捐赠方式 网银
			donation.setPayType("20");//捐赠渠道 基金会渠道
			donation.setPayStatus(1); //捐赠确认状态 已确认
			donation.setConfirmStatus(30);//捐赠确认人
			donation.setConfirmId(35);//确认者
			donation.setX_name(name);//捐赠人名
			donation.setX_phone(donorNum);
			donation.setFounProject(founProject);//项目id
			donation.setIncomeId(incomeId);//收入id
			String isShow = (String)map.get("isShow");//是否匿名
			if(!isShow.equals("") && ("0").equals(isShow)){
				donation.setAnonymous((short)0);
			}else{
				donation.setAnonymous((short)1);
			}
			Project project = projectMapper.selectByFounProject(founProject);
			logger.info("---------> 98hwfhweyr89w9rwefehrffewfdfasdfsdfsdfsdfsdfsdfsfddsf  : " + project.getProjectId());
			if(project != null ){
				donation.setProjectId(project.getProjectId());//项目Id
			}
			logger.info("---------> 98hwfhweyr89w9rwefehrfdsfsdfsdfsdfsfsdfsdfsfddfssdfsdfs : " + project.getProjectId());
			donationMapper.save(donation);
			message.setSuccess(true);
			message.setMsg("新增成功");
		} catch (Exception e) {
			message.setMsg("新增失败");
			message.setSuccess(false);
			e.printStackTrace();
		}
	}

	/*
		* 基金会修改收入记录接口
		* 基金会修改收入自动修改窗友收入记录信息
		* */
	public void updateFounIncome(Message message,String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		try {

			Map<String, Object> map1 = JsonUtils.json2map(content);
			String jsonMd5 = (String) map1.get("jsonMd5");
			String json = (String) map1.get("json");

			Map<String, Object> map = JsonUtils.json2map(json);

			String founMd5 = FounMd5Utils.getFounMd5(map);
			logger.info("---------> 加密后FounMd5  : " + founMd5);
			if (!jsonMd5.equals(founMd5)){
				message.setMsg("密钥不匹配");
				message.setSuccess(false);
				return;
			}
			String name = (String) map.get("name");
			if (StringUtils.isBlank(name)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			String donorName = (String) map.get("donorName");//组织名称
			if (StringUtils.isBlank(donorName)) {
				message.setMsg("未传入组织名称参数");
				message.setSuccess(false);
				return;
			}
			String paymoney = String.valueOf(map.get("payMoney")) ;//支付金额
			if (StringUtils.isBlank(paymoney)) {
				message.setMsg("未传入支付金额参数");
				message.setSuccess(false);
				return;
			}
			String founProject = (String) map.get("founProject"); //项目名称
			if (StringUtils.isBlank(founProject)) {
				message.setMsg("未传入项目名称参数");
				message.setSuccess(false);
				return;
			}
			String donorNum = (String) map.get("donorNum");//支付人电话号
			if (StringUtils.isBlank(donorNum) && donorNum.length()>10) {
				message.setMsg("未传入支付人电话号参数");
				message.setSuccess(false);
				return;
			}
			String createDate = (String) map.get("createDate");//支付时间
			if (StringUtils.isBlank(createDate)) {
				message.setMsg("未传入支付时间参数");
				message.setSuccess(false);
				return;

			}
			String donorType = (String)map.get("donorType");//支付人类型
			if (StringUtils.isBlank(donorType)) {
				message.setMsg("未传入支付人类型参数");
				message.setSuccess(false);
				return;
			}
			String incomeId = (String) map.get("incomeId");//基金会项目id
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			UserProfile userProfile = userProfileMapper.selectByPhoneNum(donorNum);
			if (userProfile ==null){
				userProfile = new UserProfile();
				userProfile.setName(name);
				userProfile.setPhoneNum(donorNum);
				userProfile.setPassword(donorNum.substring(5,11));
				userProfileMapper.save(userProfile);
			}
			Donation donation = new Donation();
			donation.setUserId(userProfile.getAccountNum());//捐赠人
			donation.setDonationType("10");//捐赠类型
			donation.setFlag(0);//捐赠人类型  社会人士
			donation.setNeedInvoice("20");//不需要发票
			donation.setDonationTime(DateUtils.parseDate(createDate));//捐赠时间
			donation.setConfirmTime(DateUtils.parseDate(createDate));//确认时间
			donation.setPayTime(DateUtils.parseDate(createDate));//支付时间
			donation.setPayMoney(Double.valueOf(paymoney));//支付金额
			donation.setMoney(Double.valueOf(paymoney));//捐赠金额
			donation.setPayMode("50");//捐赠方式 网银
			donation.setPayType("20");//捐赠渠道 基金会渠道
			donation.setPayStatus(1); //捐赠确认状态 已确认
			donation.setConfirmStatus(30);//捐赠确认人
			donation.setConfirmId(35);//确认者
			donation.setX_name(name);//捐赠人名
			donation.setX_phone(donorNum);
			donation.setFounProject(founProject);//项目id
			donation.setIncomeId(incomeId);//收入id
			String isShow = (String)map.get("isShow");//是否匿名
			if(!isShow.equals("") && ("0").equals(isShow)){
				donation.setAnonymous((short)0);
			}else{
				donation.setAnonymous((short)1);
			}
			Project project = projectMapper.selectByFounProject(founProject);
			logger.info("---------> 98hwfhweyr89w9rwefehrffewfdfasdfsdfsdfsdfsdfsdfsfddsf  : " + project.getProjectId());
			if(project != null ){
				donation.setProjectId(project.getProjectId());//项目Id
			}
			logger.info("---------> 98hwfhweyr89w9rwefehrfdsfsdfsdfsdfsfsdfsdfsfddfssdfsdfs : " + project.getProjectId());
			donationMapper.save(donation);
			message.setSuccess(true);
			message.setMsg("新增成功");
		} catch (Exception e) {
			message.setMsg("新增失败");
			message.setSuccess(false);
			e.printStackTrace();
		}








	}
	/*
           * 基金会删除收入记录接口
           * 基金会删除收入自动修改窗友收入记录信息
           * */
	public void deleteFounIncome(Message message,String content){

		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			Map<String,Object> map = JSON.parseObject(content, Map.class);
			String incomeId = (String) map.get("incomeId");//基金会项目id
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			donationMapper.deleteByIncomeId(incomeId);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			message.setMsg("删除失败");
			message.setSuccess(false);
		}







	}

}
