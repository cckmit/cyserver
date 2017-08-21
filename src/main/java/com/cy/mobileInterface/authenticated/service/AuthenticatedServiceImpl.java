package com.cy.mobileInterface.authenticated.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.alumni.dao.AlumniWaitRegistersMapper;
import com.cy.core.alumni.entity.AlumniWaitRegisters;
import com.cy.core.chatDeptGroup.dao.ChatDeptGroupMapper;
import com.cy.core.chatDeptGroup.entity.ChatDeptGroup;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.deptInfo.dao.DeptInfoMapper;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.core.notify.utils.PushUtils;
import com.cy.mobileInterface.alumni.entity.JoinAlumni;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.mobileLocal.dao.MobileLocalMapper;
import com.cy.core.mobileLocal.entity.MobileLocal;
import com.cy.core.userProfile.dao.GroupInfoMapper;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.mobileInterface.authenticated.entity.Authenticated;
import com.cy.system.TigaseUtils;

import static sun.misc.MessageUtils.where;

@Service("authenticatedService")
public class AuthenticatedServiceImpl implements AuthenticatedService {

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private GroupInfoMapper groupInfoMapper;

	@Autowired
	private MobileLocalMapper mobileLocalMapper;

	@Autowired
	private AlumniMapper alumniMapper;

	@Autowired
	private ChatDeptGroupMapper chatDeptGroupMapper ;

	@Autowired
	private ChatGroupService chatGroupService ;

	@Autowired
	private AlumniWaitRegistersMapper alumniWaitRegistersMapper;

	@Autowired
	private DeptInfoMapper deptInfoMapper;

	@Override
	public void updateAuthenticated(Message message, String content) {
		try {
			Authenticated authenticated = JSON.parseObject(content, Authenticated.class);
			if (authenticated.getBaseInfoId() == null || authenticated.getBaseInfoId().size() == 0) {
				message.setMsg("baseInfoId为空!");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isNotBlank(authenticated.getAccountNum())){
				if(authenticated.getClassmates() == null || authenticated.getClassmates().size() < 1){
					message.setMsg("请提供本班同学的姓名!!");
					message.setSuccess(false);
					return;
				}
			}else{
				if (authenticated.getClassmates() == null || authenticated.getClassmates().size() != 3) {
					message.setMsg("请提供3个本班同学的姓名!!");
					message.setSuccess(false);
					return;
				}
			}

			UserProfile userProfile;
			if(StringUtils.isNotBlank(authenticated.getAccountNum())){
				userProfile = userProfileMapper.selectByAccountNum(authenticated.getAccountNum());
				if(userProfile == null){
					message.init(false, "用户ID不存在", null);
					return;
				}

			}else if(StringUtils.isNotBlank(authenticated.getPhoneNum()) && StringUtils.isNotBlank(authenticated.getPassword())){
				userProfile = userProfileMapper.selectByPhoneNum(authenticated.getPhoneNum());
				if (userProfile == null) {
					message.setMsg("账号/密码错误!");
					message.setSuccess(false);
					return;
				}
				// 核对密码
				if (!authenticated.getPassword().equals(userProfile.getPassword())) {
					message.setMsg("账号/密码错误!");
					message.setSuccess(false);
					return;
				}
			}else{
				message.init(false, "请提供账号/手机号密码", null);
				return;
			}


			int isLock = isLocked(userProfile);

			if(isLock == 2){
				message.setMsg("您的认证通道已被锁定，请"+(24-(new Date().getTime() - userProfile.getAuthErrTime().getTime())/(60*60*1000))+"小时后再试!");
				message.setSuccess(false);
				return;
			}

			//超过24小时或距离上次认证超过10分钟归零
			if(isLock == 3 || isLock == 4) {
				userProfile.setAuthErrNum(0);
			}

			// 身份甄别
			String classId = authenticated.getBaseInfoId().get(0).substring(0, 16);// 截取班级编号
			List<UserInfo> userInfoList = userInfoMapper.selectUserByClassIdLess(classId);
			long count = 0;
			UserInfo userInfo = null;
			for (UserInfo user : userInfoList) {
				for (String name : authenticated.getClassmates()) {
					if (name.equals(user.getUserName())) {
						count++;
						break;
					}
				}
				if (user.getUserId().equals(authenticated.getBaseInfoId().get(0))) {
					userInfo = user;
				}
			}

			if(StringUtils.isNotBlank(authenticated.getAccountNum())){
				if (count < 1 || userInfo == null){
					message.setMsg("认证失败！请确保输入了正确的同学姓名");
					message.setSuccess(false);
					return;
				}
			}else{
				if (count < 3 || userInfo == null) {
					if(isLock != 2){
						updateAuthErr(userProfile);
					}
					if(userProfile.getAuthErrNum() >= 3){
						message.setMsg("您在10分钟内连续认证错误3次，该功能将锁定24小时!");
						message.setSuccess(false);
						return;
					}
					message.setMsg("认证失败！请确保选择的同班同学无误！（还剩"+(3-userProfile.getAuthErrNum())+"次机会）");
					message.setSuccess(false);
					return;
				}
			}


			if (userInfo.getAccountNum() != null && userInfo.getAccountNum().length() != 0) {
				message.setMsg("该用户已经认证过!");
				message.setSuccess(false);
				return;
			}

			Map<String,Object> returnMap = authSuccess(userProfile, userInfo);
			//更新用户信息
			if((int)returnMap.get("code") == 0){
				message.setObj(returnMap);
				message.setMsg("恭喜你，认证成功!");
				message.setSuccess(true);
			}else{
				message.setMsg("信息保存失败");
				message.setSuccess(false);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过学号进行认证
	 * @param message
	 * @param content
     */
	@Override
	public void updateAuthenticatedByStuNum(Message message, String content){
		try {
			Authenticated authenticated = JSON.parseObject(content, Authenticated.class);
			if (authenticated.getBaseInfoIdx() == null || authenticated.getBaseInfoIdx().length() == 0) {
				message.setMsg("baseInfoId为空!");
				message.setSuccess(false);
				return;
			}

			if (authenticated.getStudentnumber() == null) {
				message.setMsg("请填写学号!!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfile = userProfileMapper.selectByPhoneNum(authenticated.getPhoneNum());
			if (userProfile == null) {
				message.setMsg("账号错误!");
				message.setSuccess(false);
				return;
			}
			// 核对密码
			if (!authenticated.getPassword().equals(userProfile.getPassword())) {
				message.setMsg("密码错误!");
				message.setSuccess(false);
				return;
			}

			/*int isLock = isLocked(userProfile);

			if(isLock == 2){
				message.setMsg("您的认证通道已被锁定，请"+(24-(new Date().getTime() - userProfile.getAuthErrTime().getTime())/(60*60*1000))+"小时后再试!");
				message.setSuccess(false);
				return;
			}

			//超过24小时或距离上次认证超过10分钟归零
			if(isLock == 3 || isLock == 4) {
				userProfile.setAuthErrNum(0);
			}*/

			// 身份甄别
			List<UserInfo> userInfoList = userInfoMapper.selectUserByStudentNum(authenticated.getStudentnumber());
			UserInfo userInfo = null;
			for (UserInfo user : userInfoList) {
				if (user.getUserId().equals(authenticated.getBaseInfoIdx())) {
					userInfo = user;
				}
			}

			if (userInfo == null) {
				/*updateAuthErr(userProfile);
				if(userProfile.getAuthErrNum() >= 3){
					message.setMsg("您在10分钟内连续认证错误3次，该功能将锁定24小时!");
					message.setSuccess(false);
					return;
				}
				message.setMsg("认证失败!请确保填写的学号无误!（还剩"+(3-userProfile.getAuthErrNum())+"次机会）");*/
				message.setMsg("认证失败!请确保填写的学号无误!");
				message.setSuccess(false);
				return;
			}
			if (userInfo.getAccountNum() != null && userInfo.getAccountNum().length() != 0) {
				message.setMsg("该用户已经认证过!");
				message.setSuccess(false);
				return;
			}

			Map<String,Object> returnMap = authSuccess(userProfile, userInfo);
			//更新用户信息
			if((int)returnMap.get("code") == 0){
				message.setObj(returnMap);
				message.setMsg("恭喜你，认证成功!");
				message.setSuccess(true);
			}else{
				message.setMsg("信息保存失败");
				message.setSuccess(false);
			}
						return;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过身份证进行认证
	 * @param message
	 * @param content
	 */
	public void updateAuthenticatedByCard(Message message, String content){
		try {
			Authenticated authenticated = JSON.parseObject(content, Authenticated.class);
			if (authenticated.getBaseInfoIdx() == null || authenticated.getBaseInfoIdx().length() == 0) {
				message.setMsg("baseInfoId为空!");
				message.setSuccess(false);
				return;
			}

			if (authenticated.getCard() == null) {
				message.setMsg("请填写身份证号!!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfile = userProfileMapper.selectByPhoneNum(authenticated.getPhoneNum());
			if (userProfile == null) {
				message.setMsg("账号错误!");
				message.setSuccess(false);
				return;
			}
			// 核对密码
			if (!authenticated.getPassword().equals(userProfile.getPassword())) {
				message.setMsg("密码错误!");
				message.setSuccess(false);
				return;
			}

			/*int isLock = isLocked(userProfile);

			if(isLock == 2){
				message.setMsg("您的认证通道已被锁定，请"+(24-(new Date().getTime() - userProfile.getAuthErrTime().getTime())/(60*60*1000))+"小时后再试!");
				message.setSuccess(false);
				return;
			}

			//超过24小时或距离上次认证超过10分钟归零
			if(isLock == 3 || isLock == 4) {
				userProfile.setAuthErrNum(0);
			}*/

			// 身份甄别
			List<UserInfo> userInfoList = userInfoMapper.selectUserByCard(authenticated.getCard());
			UserInfo userInfo = null;
			for (UserInfo user : userInfoList) {
				if (user.getUserId().equals(authenticated.getBaseInfoIdx())) {
					userInfo = user;
				}
			}

			if (userInfo == null) {
				/*updateAuthErr(userProfile);
				if(userProfile.getAuthErrNum() >= 3){
					message.setMsg("您在10分钟内连续认证错误3次，该功能将锁定24小时!");
					message.setSuccess(false);
					return;
				}
				message.setMsg("认证失败!请确认您的身份证号无误!（还剩"+(3-userProfile.getAuthErrNum())+"次机会）");*/
				message.setMsg("认证失败!请确认您的身份证号无误!");
				message.setSuccess(false);
				return;
			}
			if (userInfo.getAccountNum() != null && userInfo.getAccountNum().length() != 0) {
				message.setMsg("该用户已经认证过!");
				message.setSuccess(false);
				return;
			}

			Map<String,Object> returnMap = authSuccess(userProfile, userInfo);
			//更新用户信息
			if((int)returnMap.get("code") == 0){
				message.setObj(returnMap);
				message.setMsg("恭喜你，认证成功!");
				message.setSuccess(true);
			}else{
				message.setMsg("信息保存失败");
				message.setSuccess(false);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过邀请码进行认证
	 * @param message
	 * @param content
	 */

	public void updateAuthenticatedByInviteCode(Message message, String content){
		try {
			Authenticated authenticated = JSON.parseObject(content, Authenticated.class);
			if (authenticated.getBaseInfoIdx() == null || authenticated.getBaseInfoIdx().length() == 0) {
				message.setMsg("baseInfoId为空!");
				message.setSuccess(false);
				return;
			}

			if (authenticated.getInvitecode() == null) {
				message.setMsg("请填写邀请码!!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfile = userProfileMapper.selectByPhoneNum(authenticated.getPhoneNum());
			if (userProfile == null) {
				message.setMsg("账号错误!");
				message.setSuccess(false);
				return;
			}
			// 核对密码
			if (!authenticated.getPassword().equals(userProfile.getPassword())) {
				message.setMsg("密码错误!");
				message.setSuccess(false);
				return;
			}

			/*int isLock = isLocked(userProfile);

			if(isLock == 2){
				message.setMsg("您的认证通道已被锁定，请"+(24-(new Date().getTime() - userProfile.getAuthErrTime().getTime())/(60*60*1000))+"小时后再试!");
				message.setSuccess(false);
				return;
			}

			//超过24小时或距离上次认证超过10分钟归零
			if(isLock == 3 || isLock == 4) {
				userProfile.setAuthErrNum(0);
			}*/

			// 身份甄别
			List<UserInfo> userInfoList = userInfoMapper.selectUserByInviteCode(authenticated.getInvitecode().toUpperCase());

			UserInfo userInfo = null;

			if(userInfoList == null || userInfoList.isEmpty()) {
				/*updateAuthErr(userProfile);
				if(userProfile.getAuthErrNum() >= 3){
					message.setMsg("您在10分钟内连续认证错误3次，该功能将锁定24小时!");
					message.setSuccess(false);
					return;
				}
				message.setMsg("认证失败!请确认您的邀请码无误!（还剩"+(3-userProfile.getAuthErrNum())+"次机会）");*/
				message.setMsg("认证失败!请确认您的邀请码无误!");
				message.setSuccess(false);
				return;
			}
			if (userInfoList.get(0).getUserId().equals(authenticated.getBaseInfoIdx())) {
				userInfo = userInfoList.get(0);
			}

			if (userInfo == null) {
				updateAuthErr(userProfile);
				message.setMsg("找不到认证用户信息!");
				message.setSuccess(false);
				return;
			}
			if (userInfo.getAccountNum() != null && userInfo.getAccountNum().length() != 0) {
				message.setMsg("该用户已经认证过!");
				message.setSuccess(false);
				return;
			}
			Map<String,Object> returnMap = authSuccess(userProfile, userInfo);
			//更新用户信息
			if((int)returnMap.get("code") == 0){
				message.setObj(returnMap);
				message.setMsg("恭喜你，认证成功!");
				message.setSuccess(true);
			}else{
				message.setMsg("信息保存失败");
				message.setSuccess(false);
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 手机端取消认证接口
	 * @param message
	 * @param content
	 */
	@Override
	public void cancleUserAuthentication(Message message, String content){
		try {
			Authenticated authenticated = JSON.parseObject(content, Authenticated.class);

			if (authenticated.getBaseInfoIdx() == null || authenticated.getBaseInfoIdx().length() == 0) {
				message.setMsg("baseInfoId为空!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfile;
			if(StringUtils.isNotBlank(authenticated.getAccountNum())){
				userProfile = userProfileMapper.selectByAccountNum(authenticated.getAccountNum());
				if (userProfile == null) {
					message.setMsg("账号不存在!");
					message.setSuccess(false);
					return;
				}
			}else if(StringUtils.isNotBlank(authenticated.getPhoneNum()) && StringUtils.isNotBlank(authenticated.getPassword())){
				userProfile = userProfileMapper.selectByPhoneNum(authenticated.getPhoneNum());
				if (userProfile == null) {
					message.setMsg("账号不存在!");
					message.setSuccess(false);
					return;
				}
				// 核对密码
				if (!authenticated.getPassword().equals(userProfile.getPassword())) {
					message.setMsg("密码错误!");
					message.setSuccess(false);
					return;
				}
			}else{
				message.setMsg("请提供账号或手机号!");
				message.setSuccess(false);
				return;
			}

			String userId = authenticated.getBaseInfoIdx();
			//通过userId找到accountNum
			String accountNum = userInfoMapper.selectAccountNumByUserId(userId);
			if(StringUtils.isBlank(accountNum)) {
				message.setMsg("用户对应校友不正确!");
				message.setSuccess(false);
				return;
			}

			//删除user_info与userbase_info下的accountNum
			userInfoMapper.deleteAccountNumByUserId(userId);
			userInfoMapper.deleteAccountNumByUserIdBase(userId);

			String baseInfoIds = userProfile.getBaseInfoId();
			if(StringUtils.isNotBlank(baseInfoIds)) {
				String[] baseInfoId = baseInfoIds.split(",");

				if (baseInfoId.length == 1) {
					//如果baseInfoId唯一值，authenticated字段置为0，并且清理baseInfoId、groupName与classes
					userProfile.setAuthenticated("0");
					userProfile.setBaseInfoId("");
					userProfile.setGroupName("");
					userProfile.setClasses("");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("accountNum", accountNum);
					map.put("class", baseInfoIds.substring(0, 16));
					userInfoMapper.deleteGroupUser(map);
				} else if (baseInfoId.length > 1) {
					//如果baseInfoId不唯一，截取baseInfoId，利用剩余的baseInfoId更新groupName与classes
					baseInfoIds = "";
					String groupNames = "";
					String classes = "";
					int count = 0;
					for (String baseId : baseInfoId) {
						if (!baseId.equals(userId)) {
							if (count == 0) {
								baseInfoIds = baseId;
								groupNames = baseId.substring(0, 16);
								classes = userInfoMapper.selectClassNameByUserId(baseId);
							} else {
								baseInfoIds += StringUtils.isNotBlank(baseInfoIds)?("," + baseId): baseId;
								groupNames += StringUtils.isNotBlank(groupNames)?("," + baseId.substring(0, 16)): groupNames;
								classes += StringUtils.isNotBlank(classes)? ("_" + userInfoMapper.selectClassNameByUserId(baseId)):classes;
							}
							count++;
						} else {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("accountNum", accountNum);
							map.put("class", baseId.substring(0, 16));
							userInfoMapper.deleteGroupUser(map);
						}
					}
					userProfile.setBaseInfoId(baseInfoIds);
					userProfile.setGroupName(groupNames);
					userProfile.setClasses(classes);
				}
				userProfileMapper.update(userProfile);
				//从环信删除该用户
				ChatDeptGroup deptGroup = new ChatDeptGroup();
				deptGroup.setDeptId(authenticated.getBaseInfoIdx().substring(0, 16));
				List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup);
				deptGroup = deptGroupList.get(0);
				chatGroupService.removeMemberFromGroup(deptGroup.getGroupId(), userProfile.getAccountNum());
			}
			try {
				PushUtils.pushGroupAll(userId, userId, accountNum, 1);
			}catch (Exception e ) {
				e.printStackTrace();
			}
//			// 更新用户认证信息
//			Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
//			if(baseMap == null) {
//				baseMap = Maps.newHashMap() ;
//			}
//			baseMap.put("accountNum", userProfile.getAccountNum()) ;
//			userProfileMapper.clearBaseInfoId(baseMap);

			message.setMsg("已取消认证");
			message.setSuccess(true);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取认证所需的姓名
	 * @param message
	 * @param content
	 */
	@Override
	public void getAuthenticatedName(Message message, String content){
		try{
			if(StringUtils.isBlank(content) || content == null){
				message.setMsg("没有任何请求参数");
				message.setSuccess(false);
				return;
			}

			Map<String, Object> map= JSON.parseObject(content, Map.class);

			String strStudyPathId = (String) map.get("strStudyPathId");

			if(StringUtils.isBlank(strStudyPathId) || strStudyPathId ==null){
				message.setMsg("strStudyPathId为空");
				message.setSuccess(false);
				return;
			}

			UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(strStudyPathId);

			if(userInfo == null){
				message.setMsg("无法查询到此校友信息");
				message.setSuccess(false);
				return;
			}

			List<String> list = new ArrayList<String>();

			//随机获取同班除自己以外的3名同学且跟自己不重名的同学
			List<String> classmates = userInfoMapper.getClassMates(strStudyPathId);

			if(classmates == null || classmates.size()<3){
				message.setMsg("该班级没有足够的同学用于此认证方式");
				message.setSuccess(false);
				return;
			}

			StringBuffer dummyNameWhere = new StringBuffer() ;
			dummyNameWhere.append("WHERE ") ;
			int i = 0 ;
			for (String className : classmates) {
				if(i != 0 ) {
					dummyNameWhere.append(" or ") ;
				}
				dummyNameWhere.append("name != '").append(className).append("' ") ;
				i++ ;
			}

			//将5名同班同学加入
			list.addAll(classmates);
			map.put("dummyNameWhere",dummyNameWhere.toString()) ;
			map.put("limit",  9-classmates.size());
			List<String> dummyList = userInfoMapper.findDummyName6(map) ;
			list.addAll(dummyList) ;

//			while (true){
//				String othersName = getRandomName();
//				if(!list.contains(othersName)){
//					list.add(othersName);
//				}
//
//				if(list.size()==9) break;
//			}

//			//随机获取其他班级的20名同学
//			List<String> others = new ArrayList<>();
//
//			int count = 0;
//			while (true){
//				others = userInfoMapper.getOtherClassStudents(strStudyPathId);
//				others.removeAll(classmates);
//				if(others.size()>=6) break;
//				count ++;
//
//				if(count >10){
//					message.setMsg("总体人数不足以用于验证");
//					message.setSuccess(false);
//					return;
//				}
//			}
//
//			for(int i = 0; i < 6; i++){
//				list.add(others.get(i));
//			}

			//打乱顺序
			Collections.shuffle(list);

			message.setMsg("获取成功");
			message.setObj(list);
			message.setSuccess(true);

		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 是否锁定
	 * @param userProfile
	 * @return
     */
	private int isLocked(UserProfile userProfile){

		if(userProfile.getAuthErrTime() == null || userProfile.getAuthErrNum() == 0){
			return 0;
		}

		long timeDifference = new Date().getTime()-userProfile.getAuthErrTime().getTime();

		//如果错误次数达到3次且离上次错误时间少于24小时
		if(userProfile.getAuthErrNum() >=3 && timeDifference <= 24*60*60*1000 ){
			return 2;
		}else if(userProfile.getAuthErrNum() >=3 && timeDifference >= 24*60*60*1000){
			return 3;
		}else if(userProfile.getAuthErrNum() > 0 && timeDifference <= 10*60*1000){
			return 1;
		}else if(userProfile.getAuthErrNum() > 0 && timeDifference >= 10*60*1000){
			return 4;
		} else{
			return 0;
		}
	}


	/**
	 * 认证成功更新信息
	 */
	private void updateAuthSuccss(UserProfile userProfile){

		userProfile.setAuthErrNum(0);
		userProfileMapper.updateAuthErr(userProfile);
	}

	/**
	 * 认证错误更新信息
	 */
	private void updateAuthErr(UserProfile userProfile){

		userProfile.setAuthErrNum(userProfile.getAuthErrNum()+1);

		userProfileMapper.updateAuthErr(userProfile);
	}


	/**
	 * 自动加入分会For一键入会
	 * @param userId
	 * @param accountNum
	 */
	public void autoJoinAlumni(String userId, String accountNum){
		AlumniWaitRegisters awr = new AlumniWaitRegisters();
		awr.setUserId(userId);
		awr.setIsWorked("0");
		List<AlumniWaitRegisters> list = alumniWaitRegistersMapper.selectList(awr);
		if(list != null && list.size() > 0){
			for(AlumniWaitRegisters tmp: list){
				Map<String, String> map = new HashMap<>();
				map.put("accountNum",accountNum);
				map.put("alumniId",tmp.getAlumniId());
				map.put("delFlag", "0");
				map.put("status","20");
				JoinAlumni checkStatus = alumniMapper.selectUserAlumni(map);
				if(checkStatus != null){
					alumniMapper.updateUserAlumni(map);
				}else{
					map.put("joinTime", (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
					alumniMapper.saveUserAlumni(map);
				}
				tmp.setIsWorked("1");
				alumniWaitRegistersMapper.update(tmp);
			}
		}
	}

	/**
	 * 认证成功后所执行的操作
	 * @param userProfile
	 * @param userInfo
	 * @return
	 */
	public Map<String,Object> authSuccess(UserProfile userProfile, UserInfo userInfo){
		int code = 1;
		Map<String,Object> returnMap = new HashMap<>();
		try{
			updateAuthSuccss(userProfile);
			if(StringUtils.isNotBlank(userProfile.getPhoneNum()) && userProfile.getPhoneNum().length() > 7) {
				// 获取电话号码归属地
				MobileLocal mobileLocal = mobileLocalMapper.selectByMobileNumber(userProfile.getPhoneNum().substring(0, 7));
				if (mobileLocal != null) {
					String local = mobileLocal.getMobileArea();
					String[] array = local.split(" ");
					Map<String, Object> alumniMap = new HashMap<String, Object>();
					if (array.length == 2) {
						alumniMap.put("region1", array[0]);
						alumniMap.put("region2", array[1]);
					} else {
						alumniMap.put("region1", array[0]);
					}
					// 获取校友会
					List<Alumni> list = alumniMapper.selectByRegion(alumniMap);
					// 随机选择一个校友会
					if (list != null && list.size() > 0) {
						userProfile.setAlumni_id(list.get(0).getAlumniId());
					}
				}
			}

			// 更新userprofile表
			if (userInfo.getSex() != null && userInfo.getSex().equals("男")) {
				userProfile.setSex("0");
			} else if(userInfo.getSex() != null && userInfo.getSex().equals("女")) {
				userProfile.setSex("1");
			}else {
				userProfile.setSex("");
			}
			userProfile.setPicture(StringUtils.isNotBlank(userProfile.getPicture())? userProfile.getPicture() : "");
			userProfile.setAuthenticated("1");

			String baseInfoId = userProfile.getBaseInfoId() == null ?"": userProfile.getBaseInfoId();
			String groupName = userProfile.getGroupName()==null?"": userProfile.getGroupName();
			String classes = userProfile.getClasses()==null?"":userProfile.getClasses();

			baseInfoId += StringUtils.isNotBlank(baseInfoId)?("," + userInfo.getUserId()): userInfo.getUserId();
			classes += StringUtils.isNotBlank(classes)?("," + userInfo.getUserId().substring(0, 16)): userInfo.getUserId().substring(0, 16);
			groupName += StringUtils.isNotBlank(groupName)? ("_" + userInfo.getFullName()):userInfo.getFullName();

			userProfile.setBaseInfoId(baseInfoId);
			userProfile.setGroupName(groupName);
			userProfile.setClasses(classes);
			userProfileMapper.update(userProfile);
			// 同步到userinfo表
			userInfo.setTelId(userProfile.getPhoneNum());
			userInfo.setUseTime(TimeZoneUtils.getDate());
			userInfo.setAccountNum(userProfile.getAccountNum());
			userInfo.setAlumniId(userProfile.getAlumni_id());
			userInfo.setPicUrl(userProfile.getPictureUrl());
			userInfoMapper.updateAuthen2User(userInfo);

			// 验证班级环信群组是否存在,如不存在创建
			ChatDeptGroup deptGroup = new ChatDeptGroup() ;
			deptGroup.setDeptId(userInfo.getUserId().substring(0, 16));

			List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup) ;
			String groupId = null ;
			if(deptGroupList == null || deptGroupList.isEmpty()) {
				ChatGroup group = new ChatGroup() ;
				group.setName(userInfo.getClassName());
				group.setIntroduction(userInfo.getSchoolName() + "," + userInfo.getDepartName() + ","  + userInfo.getGradeName() + ","  + userInfo.getClassName());
				group.setType("1");
				chatGroupService.insert(group);
				groupId = group.getId() ;
				deptGroup.preInsert();
				deptGroup.setGroupId(groupId);
				chatDeptGroupMapper.insert(deptGroup);
			} else {
				deptGroup = deptGroupList.get(0) ;
			}
			// 将认证对象添加到群组中
			chatGroupService.addMemberToGroup(deptGroup.getGroupId(),userProfile.getAccountNum()) ;

			try {
				PushUtils.pushGroupAll(userInfo.getUserId(),userInfo.getUserId(),userProfile.getAccountNum(),  2);
			}catch (Exception e ) {
				e.printStackTrace();
			}
			// 自动加入一键设置的入会
			autoJoinAlumni(userInfo.getUserId(), userProfile.getAccountNum());
			// 更新用户认证信息
//			Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
//			if(baseMap == null) {
//				baseMap = Maps.newHashMap() ;
//			}
//			baseMap.put("accountNum", userProfile.getAccountNum()) ;
//			userProfileMapper.clearBaseInfoId(baseMap);
			//获取主要是第几位，卡号、班级信息、用户信息
			UserProfile userProfileNew =userProfileMapper.selectByAccountNum(userProfile.getAccountNum());
				String baseInfoIdSub = userInfo.getUserId().substring(0, 14);
				//获取认证的同学校同年级的个数
			long count = userProfileMapper.countAuthenticatedUserProfile(baseInfoIdSub);
			DeptInfo deptInfo = deptInfoMapper.findById(userInfo.getUserId().substring(0, 16));
			String name = userProfileNew.getName();
			String sex = userProfileNew.getSex();
			String[] message = deptInfo.getFullName().split(",");
			String alumni = message[0];
			String academy = message[1];
			String grade = message[2];
			String className = message[3];
			returnMap.put("code", 0);
			returnMap.put("authNumber", count);
			returnMap.put("name", name);
			returnMap.put("sex", sex);
			returnMap.put("alumni", alumni);
			returnMap.put("academy", academy);
			returnMap.put("grade", grade);
			returnMap.put("className", className);
			code = 0;
		}catch (Exception e){
			code = -1;
			returnMap.put("code",-1);
			throw new RuntimeException(e);
		}
		return returnMap;
	}

	/**
	 * 獲取隨機姓名
	 * @return
     */
	private String getRandomName(){
		return getLastName()+getFirstName();
	}

	/**
	 * 獲取隨機名
	 * @return
     */
	private String getFirstName(){
		Random random=new Random();
		String[] firstName = {"致远","俊驰","雨泽","烨磊","晟睿","文昊","修洁","黎昕","远航","旭尧","鸿涛","伟祺","荣轩","越泽","浩宇","瑾瑜",
				"皓轩","擎宇","志泽","子轩","睿渊","弘文","哲瀚","雨泽","楷瑞","建辉","晋鹏","天磊","绍辉","泽洋","鑫磊","鹏煊","昊强","伟宸",
				"博超","君浩","子骞","鹏涛","炎彬","鹤轩","越彬","风华","靖琪 ","明辉","伟诚","明轩","健柏","修杰","志泽","弘文",
				"懿轩","烨伟","苑博","伟泽","熠彤","鸿煊","博涛","烨霖","烨","正豪","昊然","明杰","立诚","立轩","立辉","峻熙","弘文",
				"熠彤","鸿煊","烨霖","哲瀚","鑫鹏","昊天","思聪","展鹏","志强","炫明","雪松","思源","智渊","思淼","晓啸","天宇","浩然","文轩",
				"鹭洋","振家","晓博","昊焱","金鑫","锦程","嘉熙","鹏飞","子默","思远","浩轩","语堂","聪健","梦琪","之桃","慕青",
				"尔岚","初夏","沛菡","傲珊","曼文","乐菱","惜文","香寒","新柔","语蓉","海安","夜蓉","涵柏","水桃","醉蓝","语琴","从彤","傲晴","语兰",
				"又菱","碧彤","元霜","怜梦","紫寒","妙彤","曼易","南莲","紫翠","雨寒","易烟","如萱","若南","寻真","晓亦","向珊","慕灵","以蕊","映易",
				"雪柳","海云","凝天","沛珊","寒云","冰旋","宛儿","绿真","晓霜","碧凡","夏菡","曼香","若烟","半梦","雅绿","冰蓝","灵槐","平安","书翠",
				"翠风","代云","梦曼","梦柏","醉易","访旋","亦玉","凌萱","怀亦","笑蓝","靖柏","夜蕾","冰夏","梦松","书雪","乐枫",
				"念薇","靖雁","从寒","觅波","静曼","凡旋","以亦","念露","芷蕾","千兰","新波","新蕾","雁玉","冷卉","紫山","千琴","傲芙","盼山",
				"怀蝶","冰兰","山柏","翠萱","问旋","白易","问筠","如霜","半芹","丹珍","冰彤","亦寒","之瑶","冰露","尔珍","谷雪","乐萱","涵菡","海莲",
				"傲蕾","青槐","易梦","惜雪","宛海","之柔","夏青","亦瑶","妙菡","紫蓝","幻柏","元风","冰枫","访蕊","芷蕊","凡蕾","凡柔","安蕾","天荷",
				"含玉","书兰","雅琴","书瑶","从安","夏槐","念芹","代曼","秋翠","白晴","海露","代荷","含玉","书蕾","听白","灵雁","雪青",
				"乐瑶","含烟","涵双","平蝶","雅蕊","傲之","灵薇","含蕾","从梦","从蓉","初丹","听兰","听蓉","语芙","夏彤","凌瑶","忆翠","幻灵","怜菡",
				"紫南","依珊","妙竹","访烟","怜蕾","映寒","友绿","冰萍","惜霜","凌香","芷蕾","雁卉","迎梦","元柏","代萱","紫真","千青","凌寒","紫安",
				"寒安","怀蕊","秋荷","涵雁","以山","凡梅","盼曼","翠彤","谷冬","冷安","千萍","冰烟","雅阳","友绿","南松","诗云","飞风","寄灵","书芹",
				"幼蓉","以蓝","笑寒","忆寒","秋烟","芷巧","水香","醉波","夜山","芷卉","向彤","小玉","幼南","凡梦","尔曼","念波","迎松",
				"青寒","涵蕾","碧菡","映秋","盼烟","忆山","以寒","寒香","小凡","代亦","梦露","映波","友蕊","寄凡","怜蕾","雁枫","水绿","曼荷",
				"笑珊","寒珊","谷南","慕儿","夏岚","友儿","小萱","紫青","妙菱","冬寒","曼柔","语蝶","青筠","夜安","觅海","问安","晓槐","雅山","访云",
				"翠容","寒凡","以菱","冬云","含玉","访枫","含卉","夜白","冷安","灵竹","醉薇","元珊","幻波","盼夏","元瑶","迎曼","水云","访琴",
				"谷波","笑白","妙海","紫霜","凌旋","怜寒","凡松","翠安","凌雪","绮菱","代云","香薇","冬灵","凌珍","沛文","紫槐",
				"幻柏","采文","雪旋","盼海","映梦","安雁","映容","凝阳","访风","天亦","小霜","雪萍","半雪","山柳","谷雪","靖易","白薇","梦菡",
				"飞绿","如波","又晴","友易","香菱","冬亦","问雁","海冬","秋灵","凝芙","念烟","白山","从灵","尔芙","迎蓉","念寒","翠绿","翠芙","靖儿",
				"妙柏","千凝","小珍","妙旋","雪枫","夏菡","绮琴","雨双","听枫","觅荷","凡之","晓凡","雅彤","孤风","从安","绮彤","之玉","雨珍","幻丝",
				"代梅","青亦","元菱","海瑶","飞槐","听露","梦岚","幻竹","谷云","忆霜","水瑶","慕晴","秋双","雨真","觅珍","丹雪","元枫","思天","如松",
				"妙晴","谷秋","妙松","晓夏","宛筠","碧琴","盼兰","小夏","安容","青曼","千儿","寻双","涵瑶","冷梅","秋柔","思菱","醉波","醉柳","以寒",
				"迎夏","向雪","以丹","依凝","如柏","雁菱","凝竹","宛白","初柔","南蕾","书萱","梦槐","南琴","绿海","沛儿","晓瑶","凝蝶","紫雪","念双",
				"念真","曼寒","凡霜","飞雪","雪兰","雅霜","从蓉","冷雪","靖巧","觅翠","凡白","乐蓉","迎波","丹烟","梦旋","书双","念桃","夜天",
				"安筠","觅柔","初南","秋蝶","千易","安露","诗蕊","山雁","友菱","香露","晓兰","白卉","语山","冷珍","秋翠","夏柳","如之","忆南","书易",
				"翠桃","寄瑶","如曼","问柳","幻桃","又菡","醉蝶","亦绿","诗珊","听芹","新之","易巧","念云","晓灵","静枫","夏蓉","如南","幼丝","秋白",
				"冰安","秋白","南风","醉山","初彤","凝海","紫文","凌晴","雅琴","傲安","傲之","初蝶","代芹","诗霜","碧灵","诗柳","夏柳","采白","慕梅",
				"乐安","冬菱","紫安","宛凝","雨雪","易真","安荷","静竹","代柔","丹秋","绮梅","依白","凝荷","幼珊","忆彤","凌青","之桃","芷荷","听荷",
				"代玉","念珍","梦菲","夜春","千秋","白秋","谷菱","飞松","初瑶","惜灵","梦易","新瑶","碧曼","友瑶","雨兰","夜柳","芷珍","含芙",
				"夜云","依萱","凝雁","以莲","安南","幼晴","尔琴","飞阳","白凡","沛萍","雪瑶","向卉","采文","乐珍","寒荷","觅双","白桃","安卉","迎曼",
				"盼雁","乐松","涵山","问枫","以柳","含海","翠曼","忆梅","涵柳","海蓝","晓曼","代珊","忆丹","静芙","绮兰","梦安","千雁","凝珍",
				"梦容","冷雁","飞柏","翠琴","寄真","秋荷","代珊","初雪","雅柏","怜容","如风","南露","紫易","冰凡","海雪","语蓉","碧玉",
				"语风","凝梦","从雪","白枫","傲云","白梅","念露","慕凝","雅柔","盼柳","从霜","怀柔","怜晴","夜蓉","若菱","芷文",
				"南晴","梦寒","初翠","灵波","问夏","惜海","亦旋","沛芹","幼萱","白凝","初露","迎海","绮玉","凌香","寻芹","映真","含雁",
				"寒松","寻雪","青烟","问蕊","灵阳","雪巧","丹萱","凡双","孤萍","紫菱","寻凝","傲柏","傲儿","友容","灵枫","曼凝","若蕊",
				"思枫","水卉","问梅","念寒","诗双","翠霜","夜香","寒蕾","凡阳","冷玉","平彤","语薇","幻珊","紫夏","凌波","芷蝶","丹南","之双","凡波",
				"思雁","白莲","如容","采柳","沛岚","惜儿","夜玉","水儿","半凡","语海","听莲","幻枫","念柏","冰珍","思山","凝蕊","天玉","思萱",
				"向梦","笑南","夏旋","之槐","元灵","以彤","采萱","巧曼","绿兰","平蓝","问萍","绿蓉","靖柏","迎蕾","碧曼","思卉","白柏","妙菡","怜阳",
				"雨柏","雁菡","梦之","又莲","乐荷","凝琴","书南","白梦","初瑶","平露","含巧","慕蕊","半莲","醉卉","青雪","雅旋",
				"巧荷","飞丹","若灵","诗兰","青梦","海菡","灵槐","忆秋","寒凝","凝芙","绮山","尔蓉","尔冬","映萱","白筠","冰双",
				"访彤","绿柏","夏云","笑翠","晓灵","含双","盼波","以云","怜翠","雁风","之卉","平松","问儿","绿柳","如蓉","曼容","天晴","丹琴","惜天",
				"寻琴","依瑶","涵易","忆灵","从波","依柔","问兰","山晴","怜珊","之云","飞双","傲白","沛春","雨南","梦之","笑阳","代容","友琴","雁梅",
				"友桃","从露","语柔","傲玉","觅夏","晓蓝","新晴","雨莲","凝旋","绿旋","幻香","觅双","冷亦","忆雪","友卉","幻翠","靖柔","寻菱","丹翠",
				"安阳","雅寒","惜筠","尔安","雁易","飞瑶","夏兰","沛蓝","静丹","山芙","笑晴","新烟","笑旋","雁兰","凌翠","秋莲","书桃","傲松","语儿",
				"映菡","初曼","听云","初夏","雅香","语雪","初珍","白安","冰薇","诗槐","冷玉","冰巧","之槐","夏寒","诗筠","新梅","白曼","安波","从阳",
				"含桃","曼卉","笑萍","晓露","寻菡","沛白","平灵","水彤","安彤","涵易","乐巧","依风","紫南","易蓉","紫萍","惜萱","诗蕾","寻绿",
				"诗双","寻云","孤丹","谷蓝","山灵","友梅","从云","盼旋","幼旋","尔蓝","沛山","觅松","冰香","依玉","冰之","妙梦",
				"以冬","曼青","冷菱","雪曼","安白","千亦","凌蝶","又夏","南烟","靖易","沛凝","翠梅","书文","雪卉","乐儿","安青","初蝶","寄灵",
				"惜寒","雨竹","冬莲","绮南","翠柏","平凡","亦玉","孤兰","秋珊","新筠","半芹","夏瑶","念文","涵蕾","雁凡","谷兰","灵凡","凝云",
				"曼云","丹彤","南霜","夜梦","从筠","雁芙","语蝶","依波","晓旋","念之","盼芙","曼安","采珊","初柳","曼安","南珍","妙芙","语柳",
				"含莲","晓筠","夏山","尔容","念梦","傲南","问薇","雨灵","凝安","冰海","初珍","宛菡","冬卉","盼晴","冷荷","寄翠","幻梅","如凡","语梦",
				"易梦","雁荷","代芙","醉易","夏烟","依秋","依波","紫萱","涵易","忆之","幻巧","水风","安寒","白亦","怜雪","听南","念蕾","梦竹","千凡",
				"寄琴","采波","元冬","思菱","平卉","笑柳","雪卉","谷梦","绿蝶","飞荷","平安","孤晴","芷荷","曼冬","尔槐","以旋","绿蕊","初夏",
				"怜南","千山","雨安","水风","寄柔","幼枫","凡桃","新儿","夏波","雨琴","静槐","元槐","映阳","飞薇","小凝","映寒","傲菡","谷蕊","笑槐",
				"飞兰","笑卉","迎荷","元冬","书竹","半烟","绮波","小之","觅露","夜雪","寒梦","尔风","白梅","雨旋","芷珊","山彤","尔柳","沛柔","灵萱",
				"沛凝","白容","乐蓉","映安","依云","映冬","凡雁","梦秋","醉柳","梦凡","若云","元容","怀蕾","灵寒","天薇","白风","访波","亦凝","易绿",
				"夜南","曼凡","亦巧","青易","冰真","白萱","友安","诗翠","雪珍","海之","小蕊","又琴","香彤","语梦","惜蕊","迎彤","沛白","雁山","易蓉",
				"雪晴","诗珊","冰绿","半梅","笑容","沛凝","念瑶","如冬","向真","从蓉","亦云","向雁","尔蝶","冬易","丹亦","夏山","醉香","盼夏","孤菱",
				"安莲","问凝","冬萱","晓山","雁蓉","梦蕊","山菡","南莲","飞双","思萱","怀梦","雨梅","冷霜","向松","迎梅","听双","山蝶",
				"夜梅","醉冬","雨筠","平文","青文","半蕾","幼菱","寻梅","含之","香之","含蕊","亦玉","靖荷","碧萱","寒云","向南","书雁","怀薇","思菱",
				"忆文","若山","向秋","凡白","绮烟","从蕾","天曼","又亦","依琴","曼彤","沛槐","又槐","元绿","安珊","夏之","易槐","宛亦","白翠","丹云",
				"问寒","易文","傲易","青旋","思真","妙之","半双","若翠","初兰","怀曼","惜萍","初之","幻儿","千风","天蓉","雅青","寄文","代天",
				"惜珊","向薇","冬灵","惜芹","凌青","谷芹","雁桃","映雁","书兰","寄风","访烟","绮晴","傲柔","寄容","以珊","紫雪","芷容","书琴","寻桃",
				"涵阳","怀寒","易云","采蓝","代秋","惜梦","尔烟","谷槐","怀莲","涵菱","水蓝","访冬","冬卉","安双","冰岚","香薇","语芹",
				"静珊","幻露","静柏","小翠","雁卉","访文","凌文","芷云","思柔","巧凡","慕山","依云","千柳","从凝","安梦","香旋","映天",
				"安柏","平萱","以筠","忆曼","新竹","绮露","觅儿","碧蓉","白竹","飞兰","曼雁","雁露","凝冬","含灵","初阳","海秋","冰双","绿兰","盼易",
				"思松","梦山","友灵","绿竹","灵安","凌柏","秋柔","又蓝","尔竹","青枫","问芙","语海","灵珊","凝丹","小蕾","迎夏","水之","飞珍",
				"冰夏","亦竹","飞莲","海白","元蝶","芷天","怀绿","尔容","元芹","若云","寒烟","听筠","采梦","凝莲","元彤","觅山","代桃","冷之","盼秋",
				"秋寒","慕蕊","海亦","初晴","巧蕊","听安","芷雪","以松","梦槐","寒梅","香岚","寄柔","映冬","孤容","晓蕾","安萱","听枫","夜绿","雪莲",
				"从丹","碧蓉","绮琴","雨文","幼荷","青柏","初蓝","忆安","盼晴","寻冬","雪珊","梦寒","迎南","如彤","采枫","若雁","翠阳","沛容","幻翠",
				"山兰","芷波","雪瑶","寄云","慕卉","冷松","涵梅","书白","雁卉","宛秋","傲旋","新之","凡儿","夏真","静枫","乐双","白玉","问玉",
				"寄松","丹蝶","元瑶","冰蝶","访曼","代灵","芷烟","白易","尔阳","怜烟","平卉","丹寒","访梦","绿凝","冰菱","语蕊","思烟","忆枫","映菱",
				"凌兰","曼岚","若枫","傲薇","凡灵","乐蕊","秋灵","谷槐","觅云"};
		int tmp = (int)(Math.random()*100);
		int index;
		if(tmp < 50){
			index = random.nextInt(107);
		}else{
			index = random.nextInt(firstName.length-109)+108;
		}
		String first = firstName[index]; //獲取隨機名
		return first;
	}

	/**
	 * 獲取隨機姓
	 * @return
     */
	private String getLastName(){
		Random random=new Random();
		/* 598 百家姓 */
		String[] lastName= {"赵","孙","李","周","吴","郑","王","陈","张","卫","蒋","沈","韩","杨","朱","秦","许","何","吕","曹",
				"施","孔","曹","严","华","金","魏","陶","冯","姜","戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎",
				"鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤","滕","殷",
				"罗","毕","郝","邬","钱","安","常","乐","于","时","傅","皮","卞","齐","康","伍","余","元","卜","顾","孟","平","黄","和",
				"穆","萧","尹","姚","邵","湛","汪","祁","毛","褚","禹","狄","米","贝","明","臧","计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒",
				"屈","项","祝","董","梁","杜","阮","蓝","闵","席","季","麻","强","贾","尤","路","娄","危","江","童","颜","郭","梅","盛","林","刁","钟",
				"徐","邱","骆","高","夏","蔡","田","樊","胡","凌","霍","虞","万","支","柯","昝","管","卢","莫","经","房","裘","缪","干","解","应",
				"宗","丁","宣","贲","邓","郁","单","杭","洪","包","诸","左","石","崔","吉","钮","龚","程","嵇","邢","滑","裴","陆","荣","翁","荀",
				"羊","于","惠","甄","曲","家","封","芮","羿","储","靳","汲","邴","糜","松","井","段","富","巫","乌","焦","巴","弓","牧","隗","山",
				"谷","车","侯","宓","蓬","全","郗","班","仰","秋","仲","伊","宫","宁","仇","栾","暴","甘","钭","厉","戎","祖","武","符","刘","景",
				"詹","束","龙","叶","幸","司","韶","郜","黎","蓟","溥","印","宿","白","怀","蒲","邰","从","鄂","索","咸","籍","赖","卓","蔺","屠",
				"蒙","池","乔","阴","郁","胥","能","苍","双","闻","莘","党","翟","谭","贡","劳","逄","姬","申","扶","堵","冉","宰","郦","雍","却",
				"璩","桑","桂","濮","牛","寿","通","边","扈","燕","冀","浦","尚","农","温","别","庄","晏","柴","瞿","阎","充","慕","连","茹","习",
				"宦","艾","鱼","容","向","古","易","慎","戈","廖","庾","终","暨","居","衡","步","都","耿","满","弘","匡","国","文","寇","广","禄",
				"阙","东","欧","殳","沃","利","蔚","越","夔","隆","师","巩","厍","聂","晁","勾","敖","融","冷","訾","辛","阚","那","简","饶","空",
				"曾","毋","沙","乜","养","鞠","须","丰","巢","关","蒯","相","查","后","荆","红","游","郏","竺","权","逯","盖","益","桓","公","仉",
				"督","岳","帅","缑","亢","况","郈","有","琴","归","海","晋","楚","闫","法","汝","鄢","涂","钦","商","牟","佘","佴","伯","赏","墨",
				"哈","谯","篁","年","爱","阳","佟","言","福","南","火","铁","迟","漆","官","冼","真","展","繁","檀","祭","密","敬","揭","舜","楼",
				"疏","冒","浑","挚","胶","随","高","皋","原","种","练","弥","仓","眭","蹇","覃","阿","门","恽","来","綦","召","仪","风","介","巨",
				"木","京","狐","郇","虎","枚","抗","达","杞","苌","折","麦","庆","过","竹","端","鲜","皇","亓","老","是","秘","畅","邝","还","宾",
				"闾","辜","纵","侴","万俟","司马","上官","欧阳","夏侯","诸葛","闻人","东方","赫连","皇甫","羊舌","尉迟","公羊","澹台","公冶","宗正",
				"濮阳","淳于","单于","太叔","申屠","公孙","仲孙","轩辕","令狐","钟离","宇文","长孙","慕容","鲜于","闾丘","司徒","司空","兀官","司寇",
				"南门","呼延","子车","颛孙","端木","巫马","公西","漆雕","车正","壤驷","公良","拓跋","夹谷","宰父","谷梁","段干","百里","东郭","微生",
				"梁丘","左丘","东门","西门","南宫","第五","公仪","公乘","太史","仲长","叔孙","屈突","尔朱","东乡","相里","胡母","司城","张廖","雍门",
				"毋丘","贺兰","綦毋","屋庐","独孤","南郭","北宫","王孙"};

		//設定姓氏出現的概率
		int tmp = (int)(Math.random()*100);
		int index;
		if(tmp < 90){
			index = random.nextInt(19);
		}else if(tmp < 95){
			index = random.nextInt(69)+20;
		}else{
			index = random.nextInt(lastName.length-91)+90;
		}

		String last = lastName[index]; //获得一个随机的姓氏

		return last;
	}
}
