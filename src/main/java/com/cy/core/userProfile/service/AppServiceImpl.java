package com.cy.core.userProfile.service;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.system.TigaseUtils;
import com.cy.util.WebUtil;

/**
 * 给手机端提供的接口，封装在Service层
 * 
 * @author Administrator
 * 
 */
@Service("appService")
public class AppServiceImpl implements AppService {

	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private DeptService deptService;

	
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	/** ********************************************************************************************** */

	/**
	 * 修改群表信息，手机端自己处理， 如果上传空串则删除记录，如果不传此字段则不做改变，全删全建
	 * 0创建，1删除群组，2添加普通成员，3删除普通成员，4添加管理员，5删除管理员，6普通成员提升为管理员 7管理员降级为普通成员，8修改群自有信息
	 * 其中content里面的 accountNum,password,groupId,type,createrAccount,groupName必填
	 * 
	 * @param content
	 * @param message
	 */
	public void updateGroupInfo(JSONObject content, Message message) throws Exception {
		String accountNum = content.has("accountNum") ? content.getString("accountNum") : null;
		String password = content.has("password") ? content.getString("password") : null;
		if (WebUtil.isNull(accountNum) || WebUtil.isNull(password)) {// 协议检查
			message.setMsg("账号密码不能为空!");
			message.setSuccess(false);
			return;
		}
		UserProfile userProfile = userProfileService.selectByAccountNum(accountNum);
		if(userProfile==null||!password.equals(userProfile.getPassword())){
			message.setMsg("帐号或密码不正确!");
			message.setSuccess(false);
			return;
		}else{
			// 操作群的必填字段
			String groupId = content.has("groupId") ? content.getString("groupId") : null;
			String type = content.has("type") ? content.getString("type"): null;
			String createrAccount = content.has("createrAccount") ? content.getString("createrAccount") : null;
			String groupName = content.has("groupName") ? content.getString("groupName") : null;
			if (WebUtil.isNull(groupId) || WebUtil.isNull(type)|| WebUtil.isNull(createrAccount)|| WebUtil.isNull(groupName)) {
				message.setMsg("groupId,type,createrAccount,groupName不能为空!");
				message.setSuccess(false);
				return;
			}
			// 其它选填字段
			String description = content.has("description") ? content.getString("description") : null;
			String subject = content.has("subject") ? content.getString("subject") : null;
			String membersAccount = content.has("membersAccount")?content.getString("membersAccount") : null;
			String adminsAccount = content.has("adminsAccount")?content.getString("adminsAccount") : null;
			GroupInfoEntity groupInfoEntity = null;
			if (type.equals("0")) {
				// 服务器生成groupId accountNum +
				// 时间戳形式,现在这个id不由手机生成#######################
				groupId = accountNum + "-" + new Date().getTime();
				// 创建群组
				groupInfoEntity = new GroupInfoEntity();
				groupInfoEntity.setGroupId(groupId);
				groupInfoEntity.setGroupName(groupName);
				groupInfoEntity.setDescription(description);
				groupInfoEntity.setSubject(subject);
				groupInfoEntity.setCreaterAccount(createrAccount);
				groupInfoEntity.setAdminsAccount(adminsAccount);
				if(WebUtil.isNull(membersAccount)){
					groupInfoEntity.setMembersAccount(adminsAccount);
				}else{
					groupInfoEntity.setMembersAccount(membersAccount);
				}
				List<String> groupIdList = new ArrayList<String>();
				groupIdList.add(groupId);
				// 查询该群是否存在
				List<GroupInfoEntity> res = userProfileService.selectByGroupId(groupIdList);
				if (res != null && res.size() > 0) {
					message.setMsg("服务器已经存在此群");
					message.setSuccess(false);
					return;
				} else {
					//group表里面添加此群
					int result = userProfileService.save(groupInfoEntity);
					if (result > 0) {
						// 同步修改各个成员的groupName信息
						syncUpdateUserProfileGroupInfo(groupInfoEntity,adminsAccount, membersAccount, groupId, type);
						//userProfile的groupName添加该群的ID
						/*
						String userGroup = userProfile.getGroupName();
						if(WebUtil.isNull(userGroup)){
							userGroup = groupId;
						}else if(!WebUtil.isNull(userGroup)&& userGroup.indexOf(groupId)==-1 ){
							userGroup = userGroup + ","+groupId;
						}
						*/
						TigaseUtils tigaseUtils = TigaseUtils.getInstance();
						// 如果tigase发生异常，web回滚
						tigaseUtils.createGroupNod(groupId, accountNum);
						message.setMsg("创建成功");
						message.setObj(groupId);
						message.setSuccess(true);
						return;
					} else {
						message.setMsg("系统错误，groupInfoEntity创建失败");
						message.setSuccess(false);
						return;
					}
				}
			} else {
				// 取出群组记录
				List<String> groupIdList = new ArrayList<String>();
				groupIdList.add(groupId);
				List<GroupInfoEntity> groupInfoEntityList = userProfileService
						.selectByGroupId(groupIdList);
				if (groupInfoEntityList == null
						|| groupInfoEntityList.size() == 0) {
					message.setMsg("没有对应的群组记录!");
					message.setSuccess(true);
					return;
				}
				groupInfoEntity = groupInfoEntityList.get(0);
				if (type.equals("1")) {
					// 删除群组
					
					List<String> idList = new ArrayList<String>();
					idList.add(groupId);
					userProfileService.deleteByGroupId(idList);
					// 同步修改各个成员的groupName信息
					syncUpdateUserProfileGroupInfo(groupInfoEntity,adminsAccount, membersAccount, groupId, type);
					// 在tigase上删除群组
					TigaseUtils tigaseUtils = TigaseUtils.getInstance();
					tigaseUtils.deleteGroupNod(groupId, accountNum);
					message.setMsg("删除成功!");
					message.setSuccess(true);
					return;
				} else if (type.equals("2")) {
					// 添加普通成员 membersAccount内要添加的普通成员
					if (WebUtil.isNull(membersAccount)) {
						message.setMsg("成员列表为空!");
						message.setSuccess(true);
						return;
					}
					List<String> membersAccountsList = generateListFromString(
							groupInfoEntity.getMembersAccount(), membersAccount);
					String membersAccounts = generateStringFromList(membersAccountsList);
					groupInfoEntity.setMembersAccount(membersAccounts);
					userProfileService.update(groupInfoEntity);
					// 同步修改各个成员的groupName信息
					syncUpdateUserProfileGroupInfo(groupInfoEntity,
							adminsAccount, membersAccount, groupId, type);
				} else if (type.equals("3")) {
					// 删除普通成员 membersAccount内为要删除的普通成员
					if (membersAccount == null || membersAccount.equals("")) {
						message.setMsg("成员列表为空!");
						message.setSuccess(true);
						return;
					}
					String newMembers = removeGroupIdInArray(groupInfoEntity
							.getMembersAccount(), membersAccount);
					groupInfoEntity.setMembersAccount(newMembers);
					userProfileService.update(groupInfoEntity);

					// 同步修改各个成员的groupName信息
					syncUpdateUserProfileGroupInfo(groupInfoEntity,
							adminsAccount, membersAccount, groupId, type);
				} else if (type.equals("4")) {
					// 添加管理员 adminsAccount内为要添加的管理员
					if (adminsAccount == null || adminsAccount.equals("")) {
						message.setMsg("成员列表为空!");
						message.setSuccess(true);
						return;
					}
					List<String> adminsAccountList = generateListFromString(
							groupInfoEntity.getAdminsAccount(), adminsAccount);
					String adminsAccounts = generateStringFromList(adminsAccountList);
					groupInfoEntity.setAdminsAccount(adminsAccounts);
					userProfileService.update(groupInfoEntity);

					// 同步修改各个成员的groupName信息
					syncUpdateUserProfileGroupInfo(groupInfoEntity,
							adminsAccount, membersAccount, groupId, type);

				} else if (type.equals("5")) {
					// 删除管理员 adminsAccount内要删除的管理员
					if (adminsAccount == null || adminsAccount.equals("")) {
						message.setMsg("成员列表为空!");
						message.setSuccess(true);
						return;
					}
					String newMembers = removeGroupIdInArray(groupInfoEntity
							.getAdminsAccount(), adminsAccount);
					groupInfoEntity.setAdminsAccount(newMembers);
					userProfileService.update(groupInfoEntity);

					// 同步修改各个成员的groupName信息
					syncUpdateUserProfileGroupInfo(groupInfoEntity,
							adminsAccount, membersAccount, groupId, type);
				} else if (type.equals("6")) {
					// 普通成员提升为管理员 membersAccount内为要提升的普通成员
					if (membersAccount == null || membersAccount.equals("")) {
						message.setMsg("成员列表为空!");
						message.setSuccess(true);
						return;
					}
					// 普通成员删除
					String newMembers = removeGroupIdInArray(groupInfoEntity
							.getMembersAccount(), membersAccount);
					groupInfoEntity.setMembersAccount(newMembers);
					// 管理员添加
					List<String> membersAccountsList = generateListFromString(
							groupInfoEntity.getAdminsAccount(), membersAccount);
					String membersAccounts = generateStringFromList(membersAccountsList);
					groupInfoEntity.setAdminsAccount(membersAccounts);

					userProfileService.update(groupInfoEntity);
				} else if (type.equals("7")) {
					// 管理员降级为普通成员 adminsAccount内为要要降级的管理员
					if (adminsAccount == null || adminsAccount.equals("")) {
						message.setMsg("成员列表为空!");
						message.setSuccess(true);
						return;
					}
					// 管理员删除
					String newMembers = removeGroupIdInArray(groupInfoEntity
							.getAdminsAccount(), adminsAccount);
					groupInfoEntity.setAdminsAccount(newMembers);
					// 普通成员添加
					List<String> membersAccountsList = generateListFromString(
							groupInfoEntity.getMembersAccount(), adminsAccount);
					String membersAccounts = generateStringFromList(membersAccountsList);
					groupInfoEntity.setMembersAccount(membersAccounts);

					userProfileService.update(groupInfoEntity);
				} else if (type.equals("8")) {
					// 修改群自有信息，群名，描述，主题
					groupInfoEntity.setGroupName(groupName);
					groupInfoEntity.setDescription(description);
					groupInfoEntity.setSubject(subject);
					userProfileService.update(groupInfoEntity);
				}

				message.setMsg("修改成功!");
				message.setSuccess(true);
			}
		}
	}

	
	/**--创建默认班级,认证之后做的事情--**/
	public void createInitClass(UserProfile userProfile) throws Exception{
		String baseIds = userProfile.getBaseInfoId(); // 基础ID
		String accountNum = userProfile.getAccountNum();// accountNum
		if (!WebUtil.isNull(baseIds)) {
			String[] baseIdArray = baseIds.split(",");
			for(int i=0;i<baseIdArray.length;i++){
				String baseId = baseIdArray[i];
				String classId = baseId.substring(0, 16);
				// tigase节点创建成功后在将该用户加入到班级群
				String groupId = classId;
				// 查询groupInfo表是否存在该组
				GroupInfoEntity groupInfo = this.userProfileService.selectGroupByGroupId(groupId);
				//查询该班级部门是否存在
				Dept dept = this.deptService.getById(groupId);
				if (groupInfo ==null && dept != null) {
					//如果不存在该班级则添加班级,添加组,添加节点
					//班级群创建者直接是admin
					groupInfo = new GroupInfoEntity();
					groupInfo.setGroupName(dept.getDeptName());
					groupInfo.setAdminsAccount("admin");
					groupInfo.setCreaterAccount("admin");
					groupInfo.setGroupId(groupId);
					groupInfo.setMembersAccount(accountNum);
					groupInfo.setSubject("");
					groupInfo.setDescription("");
					this.userProfileService.save(groupInfo);
					//groupName设置
					String groupName = userProfile.getGroupName();
					if(WebUtil.isNull(groupName)){
						userProfile.setGroupName(groupId);
					}else{
						if(groupName.indexOf(groupId)==-1){
							userProfile.setGroupName(groupName+","+groupId);
						}
					}
					
					TigaseUtils tigaseUtils = TigaseUtils.getInstance();
					tigaseUtils.createGroupNod(classId,"admin");
					// 下面update
				}else if(groupInfo!=null&& dept != null){
					//如果存在该班级则添加用户
					this.userProfileService.updateGroupAddUser(accountNum, groupId);
				}
				
			}
		}
	}
	
	/**--手机端获取登录信息，原10号接口,根据帐号密码获取自己的基本信息--**/
	public void selectAppLogin(JSONObject content, Message message){
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)){		//协议检查
			message.setMsg("数据格式错误,帐号/手机号和密码不能为空!");
			message.setSuccess(false);
			return;
		}
		UserProfile userProfile = null;
		if(WebUtil.isMobileNO(accountNum)){//根据帐号或者手机号
			userProfile = userProfileService.selectByPhoneNum(accountNum);
		}else{
			userProfile = userProfileService.selectByAccountNum(accountNum);
		}
		if(userProfile==null){
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		//核对密码
		if(!password.equals(userProfile.getPassword())){
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}
		Map<String, String> entityMap = new HashMap<String, String>();
		
		entityMap.put("accountNum", userProfile.getAccountNum());
		entityMap.put("email", userProfile.getEmail());
		entityMap.put("phoneNum", userProfile.getPhoneNum());
		String channel = userProfile.getChannels();
		String intrestType = userProfile.getIntrestType();
		// channel和intrestType如果为空则设置初始值
		if (WebUtil.isNull(channel)) {
			channel = "母校新闻,总会快递";
		}
		if (WebUtil.isNull(intrestType)) {
			intrestType = "要闻,推荐";
		}
		entityMap.put("channels", channel);
		entityMap.put("intrestType", intrestType);
		entityMap.put("sign", userProfile.getSign());
		entityMap.put("authenticated", userProfile.getAuthenticated());
		entityMap.put("groupName", userProfile.getGroupName());
		entityMap.put("name", WebUtil.isNull(userProfile.getName()) ? "" : userProfile.getName());//姓名
		entityMap.put("sex", WebUtil.isNull(userProfile.getSex()) ? "" : userProfile.getSex());	//性别
		entityMap.put("workUtil", WebUtil.isNull(userProfile.getWorkUtil()) ? "" : userProfile.getWorkUtil());//工作单位
		entityMap.put("profession", WebUtil.isNull(userProfile.getProfession()) ? "": userProfile.getProfession());//行业
		entityMap.put("address", WebUtil.isNull(userProfile.getAddress()) ? "" : userProfile.getAddress());	//地点
		entityMap.put("hobby", WebUtil.isNull(userProfile.getHobby()) ? "" : userProfile.getHobby());	//兴趣
		entityMap.put("position", WebUtil.isNull(userProfile.getPosition()) ? "" : userProfile.getPosition());//职务
		entityMap.put("picture", WebUtil.isEmpty(userProfile.getPicture()) ? "" : userProfile.getPicture()); // 用户图片
		entityMap.put("alumni_id",userProfile.getAlumni_id()+"");
		
		String baseInfoId = userProfile.getBaseInfoId();
		//查询学校,院系，年级，班级，专业，查询user_info
		if(!WebUtil.isNull(baseInfoId)){
			
			//根据accountNum查询user_info得到的专业
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("accountNum", userProfile.getAccountNum());
			List<UserInfo> list = userInfoService.selectByAccountNum(queryMap);
			if(list!=null && list.size()!=0){
				StringBuffer buf = new StringBuffer();
				String baseInfo = "";
				for(int j=0;j< list.size();j++){
					String full_departName = "";
					UserInfo info = list.get(j);
					String schoolName = WebUtil.isNull(info.getSchoolName())?"null":info.getSchoolName() ;//学校
					String departName = WebUtil.isNull(info.getDepartName())?"null":info.getDepartName() ;//院系
					String gradeName = WebUtil.isNull(info.getGradeName())?"null":info.getGradeName();    //年级
					String className = WebUtil.isNull(info.getClassName())?"null":info.getClassName();    //班级
					if(j<list.size()-1){
						full_departName = schoolName+","+departName+","+gradeName+","+className+"_";
						baseInfo = baseInfo + info.getUserId() + ",";
					}else{
						full_departName = schoolName+","+departName+","+gradeName+","+className;
						baseInfo = baseInfo + info.getUserId();
					}
					buf.append(full_departName);
				}
				entityMap.put("baseInfoId",baseInfo);
				entityMap.put("departName",buf.toString());
			}
			//查询用户所在的群
			List<GroupInfoEntity> groupList = this.userProfileService.selectGroupByAccountNum(accountNum);
			//查询群内所有用户信息
			/*
			for(GroupInfoEntity group:groupList){
				List<UserProfile> userList = this.userProfileService.getUserProfileByGroupId(group.getGroupId());
				List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
				if(userList!=null && userList.size()!=0){
					for(UserProfile user:userList){
						Map<String,Object> userMap = new HashMap<String, Object>();
						userMap.put("accountNum", user.getAccountNum());
						userMap.put("name", user.getName());
						userMap.put("phoneNum", user.getPhoneNum());
						userMap.put("channels", user.getChannels());
						userMap.put("intrestType", user.getIntrestType());
						userMap.put("picture", user.getPicture());
						userMap.put("address", user.getAddress());
						userMap.put("sex", user.getSex());
						listMap.add(userMap);
					}	
				}
				group.setUserList(listMap);
			}
			*/
			JSONArray jsonArray = JSONArray.fromObject(groupList);
			entityMap.put("groupList", jsonArray.toString());	
		}
		message.setMsg("查询成功!");
		message.setObj(entityMap);
		message.setSuccess(true);
	}
	
	
	/**--根据群id查询群内用户信息--**/
	public void selectUserByGroupId(JSONObject content, Message message){
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		String groupId = content.has("groupId")?content.getString("groupId"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)||WebUtil.isNull(groupId)){		//协议检查
			message.setMsg("数据格式错误,帐号,密码,groupId不能为空!");
			message.setSuccess(false);
			return;
		}
		
		//核对帐号和密码
		boolean checkAccount = this.checkAccountAndPassword(accountNum, password);
		if(!checkAccount){
			message.setMsg("帐号或密码不正确!");
			message.setSuccess(false);
			return;
		}
		
		List<UserProfile> userList = this.userProfileService.getUserProfileByGroupId(groupId);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();		//使用list<Map>封装数据
		
		if(!WebUtil.isEmpty(userList)){
			for(UserProfile user:userList){
				Map<String,Object> userMap = new HashMap<String, Object>();
				userMap.put("picture", WebUtil.isNull(user.getPicture())?"":user.getPicture());
				userMap.put("accountNum", WebUtil.isNull(user.getAccountNum())?"":user.getAccountNum());
				userMap.put("name", WebUtil.isNull(user.getName())?"":user.getName());
				userMap.put("address", WebUtil.isNull(user.getAddress())?"":user.getAddress());
				userMap.put("alumni_id", WebUtil.isNull(String.valueOf(user.getAlumni_id()) )?"":user.getAlumni_id());
				userMap.put("profession", WebUtil.isNull(user.getProfession())?"":user.getProfession());
				userMap.put("hobby", WebUtil.isNull(user.getHobby())?"":user.getHobby());
				userMap.put("workUtil", WebUtil.isNull(user.getWorkUtil())?"":user.getWorkUtil());
				userMap.put("position", WebUtil.isNull(user.getPosition())?"":user.getPosition());
				userMap.put("email", WebUtil.isNull(user.getEmail())?"":user.getEmail());
				userMap.put("sign", WebUtil.isNull(user.getSign())?"":user.getSign());
				userMap.put("baseInfoId", WebUtil.isNull(user.getBaseInfoId())?"":user.getBaseInfoId());
				listMap.add(userMap);
			}
			message.setMsg("查询成功!");
			message.setObj(listMap);
			message.setSuccess(true);
		}else{
			message.setMsg("没有数据!");
			message.setSuccess(false);
		}
		
	}
	
	/**--根据baseInfo查询用户信息--**/
	public void selectUserByBaseInfoId(JSONObject content, Message message){
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		String baseInfoIds = content.has("baseInfoIds")?content.getString("baseInfoIds"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)||WebUtil.isNull(baseInfoIds)){		//协议检查
			message.setMsg("数据格式错误,帐号,密码,baseInfoIds不能为空!");
			message.setSuccess(false);
			return;
		}
		//核对帐号和密码
		boolean checkAccount = this.checkAccountAndPassword(accountNum, password);
		if(!checkAccount){
			message.setMsg("帐号或密码不正确!");
			message.setSuccess(false);
			return;
		}
		
		String[] array = baseInfoIds.split(",");

		List<Map<String ,Object>> listMap = new ArrayList<Map<String, Object>>();
		for(String str:array){
			UserProfile user = userProfileService.selectByBaseInfoId(str);
			if(user!=null){
				Map<String ,Object> userMap = new HashMap<String ,Object>();
				userMap.put("picture", WebUtil.isNull(user.getPicture())?"":user.getPicture());
				userMap.put("accountNum", WebUtil.isNull(user.getAccountNum())?"":user.getAccountNum());
				userMap.put("name", WebUtil.isNull(user.getName())?"":user.getName());
				userMap.put("address", WebUtil.isNull(user.getAddress())?"":user.getAddress());
				userMap.put("alumni_id", WebUtil.isNull(String.valueOf(user.getAlumni_id()) )?"":user.getAlumni_id());
				userMap.put("profession", WebUtil.isNull(user.getProfession())?"":user.getProfession());
				userMap.put("hobby", WebUtil.isNull(user.getHobby())?"":user.getHobby());
				userMap.put("workUtil", WebUtil.isNull(user.getWorkUtil())?"":user.getWorkUtil());
				userMap.put("position", WebUtil.isNull(user.getPosition())?"":user.getPosition());
				userMap.put("email", WebUtil.isNull(user.getEmail())?"":user.getEmail());
				userMap.put("sign", WebUtil.isNull(user.getSign())?"":user.getSign());
				userMap.put("baseInfoId", WebUtil.isNull(user.getBaseInfoId())?"":user.getBaseInfoId());
				listMap.add(userMap);
			}
		}
		if(WebUtil.isEmpty(listMap)){
			message.setMsg("没有数据!");
			//message.setObj(listMap);
			message.setSuccess(false);
		}else{
			message.setMsg("查询成功!");
			message.setObj(listMap);
			message.setSuccess(true);
		}
	}
	
	
	/**--同步修改--**/
	

	/**
	 * 同步修改各个用户的群组信息
	 * 
	 * @param groupInfoEntity
	 * @param adminsAccount
	 * @param membersAccount
	 * @param groupId
	 * @param type
	 */
	private void syncUpdateUserProfileGroupInfo(
			GroupInfoEntity groupInfoEntity, String adminsAccount,
			String membersAccount, String groupId, String type) {
		List<UserProfile> userProfileList = null;
		List<String> accountList = null;
		if (type.equals("0")) {
			// 创建群组
			// 取出所有的账号
			accountList = generateListFromString(adminsAccount, membersAccount);
			if (accountList == null || accountList.size() == 0) {
				return;
			}

			// 取出所有人的group信息
			userProfileList = userProfileService.selectGroupInfoByAccountNumList(accountList);
			for (int i = 0; i < userProfileList.size(); ++i) {
				// 增加 groupId
				String groupIds = userProfileList.get(i).getGroupName();
				List<String> adminsAccountList = generateListFromString(
						groupIds, groupId);
				String adminsAccounts = generateStringFromList(adminsAccountList);
				userProfileList.get(i).setGroupName(adminsAccounts);
			}
		} else if (type.equals("1")) {
			// 删除群组
			accountList = generateListFromString(groupInfoEntity
					.getAdminsAccount(), groupInfoEntity.getMembersAccount());

			if (accountList == null || accountList.size() == 0) {
				return;
			}
			// 取出所有人的group信息
			userProfileList = userProfileService
					.selectGroupInfoByAccountNumList(accountList);
			for (int i = 0; i < userProfileList.size(); ++i) {
				// 删除每一个人的groupId
				String groupIds = userProfileList.get(i).getGroupName();
				groupIds = removeGroupIdInArray(groupIds, groupId);
				userProfileList.get(i).setGroupName(groupIds);
			}
		} else if (type.equals("2")) {
			// 添加普通成员 membersAccount内要添加的普通成员
			accountList = generateListFromString(null, membersAccount);

			if (accountList == null || accountList.size() == 0) {
				return;
			}
			// 取出所有人的group信息
			userProfileList = userProfileService.selectGroupInfoByAccountNumList(accountList);
			for (int i = 0; i < userProfileList.size(); ++i) {
				// 添加每一个人的groupId
				String groupIds = userProfileList.get(i).getGroupName();
				List<String> adminsAccountList = generateListFromString(groupIds, groupId);
				String adminsAccounts = generateStringFromList(adminsAccountList);
				userProfileList.get(i).setGroupName(adminsAccounts);
			}
		} else if (type.equals("3")) {
			// 删除普通成员 membersAccount内为要删除的普通成员
			accountList = generateListFromString(null, membersAccount);

			if (accountList == null || accountList.size() == 0) {
				return;
			}
			// 取出所有人的group信息
			userProfileList = userProfileService
					.selectGroupInfoByAccountNumList(accountList);
			for (int i = 0; i < userProfileList.size(); ++i) {
				// 删除每一个人的groupId
				String groupIds = userProfileList.get(i).getGroupName();
				String newGroupIds = removeGroupIdInArray(groupIds, groupId);
				userProfileList.get(i).setGroupName(newGroupIds);
			}
		} else if (type.equals("4")) {
			// 添加管理员 adminsAccount内为要添加的管理员
			accountList = generateListFromString(null, adminsAccount);

			if (accountList == null || accountList.size() == 0) {
				return;
			}
			// 取出所有人的group信息
			userProfileList = userProfileService
					.selectGroupInfoByAccountNumList(accountList);
			for (int i = 0; i < userProfileList.size(); ++i) {
				// 添加每一个人的groupId
				String groupIds = userProfileList.get(i).getGroupName();
				List<String> adminsAccountList = generateListFromString(groupIds, groupId);
				String adminsAccounts = this.generateStringFromList(adminsAccountList);
				userProfileList.get(i).setGroupName(adminsAccounts);
			}
		} else if (type.equals("5")) {
			// 删除管理员 adminsAccount内要删除的管理员
			accountList = generateListFromString(null, adminsAccount);

			if (accountList == null || accountList.size() == 0) {
				return;
			}
			// 取出所有人的group信息
			userProfileList = userProfileService
					.selectGroupInfoByAccountNumList(accountList);
			for (int i = 0; i < userProfileList.size(); ++i) {
				// 删除每一个人的groupId
				String groupIds = userProfileList.get(i).getGroupName();
				String newGroupIds = removeGroupIdInArray(groupIds, groupId);
				userProfileList.get(i).setGroupName(newGroupIds);
			}
		} else if (type.equals("6")) {
			// 普通成员提升为管理员 membersAccount内为要提升的普通成员
		} else if (type.equals("7")) {
			// 管理员降级为普通成员 adminsAccount内为要要降级的管理员
		} else if (type.equals("8")) {
			// 修改群自有信息，群名，描述，主题
		}

		// 写回数据库 +++lqg+++ 待优化，一次性写入
		if (userProfileList != null && userProfileList.size() > 0) {
			// userProfileService.updateUserProfileGroupInfoList(userProfileList);
			for (int i = 0; i < userProfileList.size(); ++i) {
				userProfileService.update(userProfileList.get(i));
			}
		}
	}

	private String removeGroupIdInArray(String groupIds, String groupId) {
		try {
			if (WebUtil.isNull(groupIds)) {
				return groupIds;
			}
			if (WebUtil.isNull(groupId)) {
				return groupIds;
			}

			String groupIdArray[] = groupIds.split(",");
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < groupIdArray.length; ++i) {
				if (!groupIdArray[i].equals(groupId)) {
					buf.append(groupIdArray[i]).append(",");
				}
			}
			String temp = buf.toString();
			if (temp != null && !temp.equals("")) {
				temp = temp.substring(0, temp.lastIndexOf(","));
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return groupIds;
		}
	}
	
	
	
	
	
	/**--得到accountNum的集合--**/
	private List<String> getAccountNumList(String adminsAccount,String membersAccount){
		Map<String,String> map = new HashMap<String, String>();
		List<String> list = new ArrayList<String>();
		if(!WebUtil.isNull(adminsAccount)){
			String accountNums[] = adminsAccount.split(",");
			for(String accountNum:accountNums){
				map.put(accountNum, accountNum);
			}
		}
		if(!WebUtil.isNull(membersAccount)){
			String accountNums[] = membersAccount.split(",");
			for(String accountNum:accountNums){
				map.put(accountNum, accountNum);
			}
		}
		for(String key:map.keySet()){
			list.add(key);
		}
		return list;
	}
	
	
	
	

	/**
	 * 压缩字段合并出账号列表
	 * 
	 * @param adminsAccount
	 * @param membersAccount
	 * @return
	 */
	private List<String> generateListFromString(String adminsAccount,
			String membersAccount) {
		try {
			Set<String> accountSet = new HashSet<String>();
			List<String> accountList = new ArrayList<String>();
			if (adminsAccount != null && !adminsAccount.equals("")) {
				String adminsAccounts[] = adminsAccount.split(",");
				for (int i = 0; i < adminsAccounts.length; ++i) {
					if (!accountSet.contains(adminsAccounts[i])) {
						accountSet.add(adminsAccounts[i]);
						accountList.add(adminsAccounts[i]);
					}
				}
			}

			if (membersAccount != null && !membersAccount.equals("")) {
				String membersAccounts[] = membersAccount.split(",");
				for (int i = 0; i < membersAccounts.length; ++i) {
					if (!accountSet.contains(membersAccounts[i])) {
						accountSet.add(membersAccounts[i]);
						accountList.add(membersAccounts[i]);
					}
				}
			}

			return accountList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 将字符串添加进逗号隔开的字符串后面,不能重复
	 */
	private String appendNewUnit(String str, String unit) {
		try {
			if (str == null || str.equals("")) {
				return unit;
			}
			String array[] = str.split(",");
			if (array.length == 0) {
				return unit;
			}
			int i;
			for (i = 0; i < array.length; ++i) {
				if (unit.equals(array[i])) {
					break;
				}
			}
			if (i < array.length) {
				return str;
			}

			StringBuffer buf = new StringBuffer();
			buf.append(str).append(",").append(unit);
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}

	private String generateStringFromList(List<String> membersAccountsList) {
		try {
			int size = membersAccountsList.size();
			if (size == 0) {
				return "";
			}

			StringBuffer buf = new StringBuffer();
			buf.append(membersAccountsList.get(0));
			for (int i = 1; i < size; ++i) {
				buf.append(",").append(membersAccountsList.get(i));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**--核对用户的帐号和密码--**/
	public boolean checkAccountAndPassword(String accountNum,String password){
		UserProfile userProfile = userProfileService.selectByAccountNum(accountNum);
		if(userProfile==null){
			return false;
		}else if(!userProfile.getPassword().equals(password)){
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String s = "123,456,789";
		System.out.println(s.indexOf("1233"));
	}

	
	/**
	 * 组织成员查看详情，BY tel
	 */
	public void selectByPhoneNum(Message message, String content) {		
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		
		String tel = (String)map.get("tel"); //校友电话
		if (StringUtils.isBlank(tel)) {
			message.setMsg("未传入校友电话");
			message.setSuccess(false);
			return;
		}
		
		String aluid = (String)map.get("aluid"); 
		if (StringUtils.isBlank(aluid)) {
			message.setMsg("未传入校友组织ID");
			message.setSuccess(false);
			return;
		}
		Map<String, String> map2=new HashMap<String, String>();
		map2.put("aluid", aluid);
		map2.put("phoneNum", tel);
		
		UserProfile userprofile= userProfileMapper.selectByPhoneNumDetail(map2);		
		
		if(userprofile == null)
		{
			message.setMsg(tel+"记录为空");
			message.setSuccess(false);
			return;
		}
		else {
			String[] gp= userprofile.getGroupName().split("_");			
			userprofile.setGroupStud(gp);			
			message.setMsg("success");
			message.setSuccess(true);
			message.setObj(userprofile);
			return;
		}

	}
		
	/**
	 * 更校校友会成员状态
	 */
	public void UpdateProfileStatus(Message message, String content) {		
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
				
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		
		String userAlumniId = (String)map.get("almid"); //校友组织ID
		String userId = (String)map.get("userid"); //校友ID
		String status = (String)map.get("status"); //校友状态
	
		if (StringUtils.isBlank(userAlumniId)) {
			message.setMsg("未传入校友组织ID-almid");
			message.setSuccess(false);
			return;
		}
		
				
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("userId",userId);
		map2.put("userAlumniId",userAlumniId);
		map2.put("status",status);
		
		int t=0;		
		if (userId.indexOf(",")>0)
		{
		   String[] ids=userId.split(",");
		   map2.put("ids",ids);			
		   t=userInfoService.updateProfileStatusBatch(map2); //多用户更新
		}
		else
	       t=userInfoService.updateProfileStatus(map2);		//单用户更新
				
        if(t==0)
		{
			message.setMsg("操作失败");
			message.setSuccess(false);
			return;
		}
		else {
			message.setMsg("success");
			message.setSuccess(true);
			message.setObj(t);
			return;
		}

	}
	
}
