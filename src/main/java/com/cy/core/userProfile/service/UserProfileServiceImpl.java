package com.cy.core.userProfile.service;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.userProfile.dao.GroupInfoMapper;
import com.cy.core.userProfile.dao.SchoolConfigMapper;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.userProfile.entity.SchoolConfigEntity;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.entity.UserProfileSearchEntity;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.system.TigaseUtils;
import com.cy.util.WebUtil;

@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(UserProfileServiceImpl.class);

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private GroupInfoMapper groupInfoMapper;

	@Autowired
	private SchoolConfigMapper schoolConfigMapper;

	public DataGrid<UserProfile> dataGrid(Map<String, Object> map) {
		DataGrid<UserProfile> dataGrid = new DataGrid<UserProfile>();
		long count = userProfileMapper.countUserProfile(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<UserProfile> list = userProfileMapper.selectUserProfileList(map);
		for(UserProfile up:list){
			String baseInfoId=up.getBaseInfoId();
			if(baseInfoId != null){
				String[] array = up.getBaseInfoId().split(",");
				up.setNumber(String.valueOf(array.length));
			}

		}
		dataGrid.setRows(list);
		return dataGrid;
	}

	public int save(UserProfile userProfile) {
		return userProfileMapper.save(userProfile);
	}

	//防手机号重复插入
	public int savecheck(UserProfile userProfile) {
		return userProfileMapper.savecheck(userProfile);
	}
	
	public int updateposition(UserProfile userProfile) {
		return userProfileMapper.updatePosition(userProfile);
	}
	
	// 待改动
	public void delete(String ids) {
		List<Long> list = new ArrayList<Long>();
		try {
			String[] idArray = ids.split(",");
			for (int j = 0; j < idArray.length; ++j) {
				list.add(Long.parseLong(idArray[j]));
				UserProfile userProfile = userProfileMapper.selectById(idArray[j]);
				if (userProfile == null) {
					continue;// 避免异常
				}
				String baseInfo = userProfile.getBaseInfoId();
				if (baseInfo != null) {
					String baseInfoArray[] = baseInfo.split(",");
					List<String> baseInfoIdList = new ArrayList<String>();
					for (int i = 0; i < baseInfoArray.length; ++i) {
						if (baseInfoArray[i] == null
								|| baseInfoArray[i].length() != 19) {
							continue;// 错误的基础id
						}
						baseInfoIdList.add(baseInfoArray[i]);
					}

					if (baseInfoIdList.size() > 0) {
						// 基础数据账号清零
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("list", baseInfoIdList);
						List<UserInfo> userInfoList = userInfoMapper.selectCard(map);
						for (int i = 0; i < userInfoList.size(); ++i) {
							UserInfo userInfo = userInfoList.get(i);
							map.put("userId", userInfo.getUserId());
							map.put("accountNum", "");
							userInfoMapper.updateUserAccountNum(map);
						}
					}
				}

				// 删除聊天服务器账号
				String accountNum = userProfile.getAccountNum();
				TigaseUtils tigaseUtils = TigaseUtils.getInstance();
				if (tigaseUtils.deleteAccount(accountNum)) {
					StringBuffer buf = new StringBuffer();
					buf.append("delete user : account=").append(
							userProfile.getAccountNum()).append(" name=")
							.append(userProfile.getName()).append(
									" baseInfoId=").append(
									userProfile.getBaseInfoId()).append(
									" phoneNum=").append(
									userProfile.getPhoneNum());
					logger.info(buf.toString());
				}

				// 删除其所创建的群组
				//groupInfoMapper.deleteByCreatorAccount(idArray[j]);
				//删除该用户所在的群里面MembersAccount
				//查询该用户所在的所有群信息
				List<GroupInfoEntity> groupList = this.selectGroupByAccountNum(accountNum);
				if(!WebUtil.isEmpty(groupList)){
					for(GroupInfoEntity group:groupList){
						//判断该用户是否是管理员
						String adminAccount = group.getAdminsAccount();
						if(accountNum.equals(adminAccount)&&group.getGroupId().indexOf(accountNum)!=-1){//用户是管理员用户,且该节点不为班级节点删除该群
							this.deleteGroupByGroupId(group.getGroupId(), accountNum);
						}else{								//用户不是管理员用户,移除membersAccount中的accountNum
							/*
							String[] membersAccounts = group.getAdminsAccount().split(",");
							List<String> accountNumList = new ArrayList<String>(Arrays.asList(membersAccounts));
							for(int i=0;i<accountNumList.size();i++){
								if(accountNum.equals(accountNumList.get(i))){
									accountNumList.remove(i);
									break;
								}
							}
							group.setMembersAccount(WebUtil.arrayToString(accountNumList));
							groupInfoMapper.update(group);
							*/
							//用户不是管理员用户,则将该用户移出群
							this.updateGroupRemoveUser(accountNum,group.getGroupId());
							
						}
						
					}		
				}	
			}
			//删除userProfile账户
			userProfileMapper.delete(list);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public UserProfile selectById(String id) {
		return userProfileMapper.selectById(id);
	}

	public void update(UserProfile userProfile) {
		userProfileMapper.update(userProfile);

	}

	public void updateNew(UserProfile userProfile, UserInfo info) {
		userProfileMapper.update(userProfile);
		// cy_userinfo表里面的数据一并更新
		userInfoMapper.updateByAccountNum(info);
	}

	public void updateAuthenticated(Map<String, Object> map) {
		userProfileMapper.updateAuthenticated(map);
	}

	public List<UserProfile> selectAll() {
		return userProfileMapper.selectAll();
	}

	public long countByUserAccount(Map<String, Object> map) {
		return userProfileMapper.countUserProfile(map);
	}

	public UserProfile selectByAccountNum(String accountNum) {
		return userProfileMapper.selectByAccountNum(accountNum);
	}

	public List<UserProfile> selectGroupInfoByAccountNumList(
			List<String> accountList) {
		return userProfileMapper.selectGroupInfoByAccountNumList(accountList);
	}

	public UserProfile selectByBaseInfoId(String baseInfoId) {
		return userProfileMapper.selectByBaseInfoId(baseInfoId);
	}

	// 需要改
	// public void deleteByAccountNum(Map<String, Object> map) {
	// userProfileMapper.deleteByAccountNum(map);
	// }

	public List<UserProfile> selectUserByName(Map<String, Object> map) {
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		return userProfileMapper.selectUserByName(map);
	}

	/** --新增模糊查询,查询UserProfile-- * */
	public List<UserProfile> selectUserByQuery(Map<String, Object> map) {
		int page = map.get("page") == null ? 1 : (Integer) map.get("page");
		int rows = map.get("rows") == null ? 1000 : (Integer) map.get("rows");
		int start = (page - 1) * rows;
		map.put("start", start);
		map.put("rows", rows);
		return userProfileMapper.selectUserByQuery(map);
	}

	public long countUserProfileByBaseInfoId(Map<String, Object> map) {
		return userProfileMapper.countUserProfileByBaseInfoId(map);
	}

	public long countByEmail(String email){
		return userProfileMapper.countByEmail(email);
	}

	public long countByPhoneNum(String phoneNum) {
		return userProfileMapper.countByPhoneNum(phoneNum);
	}

	public UserProfile selectByPhoneNum(String phoneNum) {
		return userProfileMapper.selectByPhoneNum(phoneNum);
	}

	public List<GroupInfoEntity> selectByGroupId(List<String> groupIdList) {
		return groupInfoMapper.selectByGroupId(groupIdList);
	}

	public int save(GroupInfoEntity groupInfoEntity) {
		return groupInfoMapper.save(groupInfoEntity);
	}

	public void update(GroupInfoEntity groupInfoEntity) {
		groupInfoMapper.update(groupInfoEntity);
	}

	public void deleteByGroupId(List<String> groupIdList) {
		groupInfoMapper.deleteByGroupId(groupIdList);
	}

	public long countGroupInfoEntity(Map<String, Object> map) {
		return groupInfoMapper.countGroupInfoEntity(map);
	}

	public void deleteByCreatorAccount(String createrAccount) {
		groupInfoMapper.deleteByCreatorAccount(createrAccount);
	}

	public List<SchoolConfigEntity> getByBaseId(List<String> baseIdList) {
		return schoolConfigMapper.selectByBaseId(baseIdList);
	}

	public long countSchoolConfigEntity(Map<String, Object> map) {
		return schoolConfigMapper.countSchoolConfigEntity(map);
	}

	public int save(SchoolConfigEntity schoolConfigEntity) {
		return schoolConfigMapper.save(schoolConfigEntity);
	}

	public void deleteByBaseId(List<String> baseIdList) {
		schoolConfigMapper.deleteByBaseId(baseIdList);
	}

	public void update(SchoolConfigEntity schoolConfigEntity) {
		schoolConfigMapper.update(schoolConfigEntity);
	}

	public List<SchoolConfigEntity> getAllSupportedSchools() {
		return schoolConfigMapper.selectAllSupportedSchools();
	}

	/** --模糊查询通用方法，map为传入的模糊查询条件--* */
	public List<Map<String, String>> getUserProfileSearchEntity(
			Map<String, Object> map) {
		// 查询userProfileList
		List<UserProfile> userProfileList = this.selectUserByQuery(map);

		if (!WebUtil.isEmpty(userProfileList)) {
			//组织accountNums查询userInfo的信息
			List<String> accountNums = new ArrayList<String>();
			
			for(UserProfile profile:userProfileList){
				accountNums.add(profile.getAccountNum());
			}
			List<UserInfo> userInfoList = this.userInfoMapper.selectByAccountNums(accountNums);
			List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
			for(UserProfile profile:userProfileList){
				
				// 封装学校院系年级班级等重新组织数据
				StringBuffer full_departName = new StringBuffer();
				for(int i=0;i<userInfoList.size();i++){
					UserInfo info = userInfoList.get(i);
					//拼接
					if(profile.getAccountNum().equals(info.getAccountNum())){
						full_departName.append(info.getFullName() + "_");
					}
				}
				//去掉最后面的"_"
				if(full_departName.length()>0 && full_departName.substring(full_departName.length()-1,full_departName.length()).equals("_")){
					full_departName = new StringBuffer(full_departName.substring(0, full_departName.length()-1));
				}
				Map<String, String> entityMap = new HashMap<String, String>();
				entityMap.put("accountNum", profile.getAccountNum());
				entityMap.put("baseInfoId", profile.getBaseInfoId());
				entityMap.put("phoneNum", profile.getPhoneNum());
				entityMap.put("departName", full_departName.toString());
				entityMap.put("channels", profile.getChannels());
				entityMap.put("intrestType", profile.getIntrestType());
				entityMap.put("name",WebUtil.isEmpty(profile.getName()) ? "": profile.getName());// 姓名
				entityMap.put("sex", WebUtil.isEmpty(profile.getSex()) ? "": profile.getSex());// 性别
				entityMap.put("workUtil", WebUtil.isEmpty(profile.getWorkUtil()) ? "" : profile.getWorkUtil());// 工作单位
				entityMap.put("profession", WebUtil.isEmpty(profile.getProfession()) ? "" : profile.getProfession());// 行业
				entityMap.put("address", WebUtil.isEmpty(profile.getAddress()) ? "" : profile.getAddress());// 地点
				entityMap.put("hobby",WebUtil.isEmpty(profile.getHobby()) ? "": profile.getHobby());// 兴趣
				entityMap.put("position", WebUtil.isEmpty(profile.getPosition()) ? "" : profile.getPosition());// 职务
				entityMap.put("picture", WebUtil.isEmpty(profile.getPicture()) ? "" : profile.getPicture()); // 用户图片
				entityMap.put("mu_longitud", profile.getMu_longitud() + "");
				entityMap.put("mu_latitude", profile.getMu_latitude() + "");
				entityMap.put("alumni_id", profile.getAlumni_id() + "");
				entityMap.put("email", profile.getEmail());
				// entityMap.put("password", userProfile.getPassword());
				mapList.add(entityMap);
			}
			return mapList;
		}
		return null;
	}
	
	/**--查询该用户所在的所有群信息--**/
	public List<GroupInfoEntity> selectGroupByAccountNum(String accountNum){
		return this.groupInfoMapper.selectGroupByAccountNum(accountNum);
	}
	
	/**--根据群的ID,查询群内所有的联系人详细信息--**/
	public List<UserProfile> getUserProfileByGroupId(String groupId){
		GroupInfoEntity group = this.groupInfoMapper.selectGroupByGroupId(groupId);
		if(group==null){
			return null;
		}
		
		String adminsAccount = group.getAdminsAccount();
		String createrAccount = group.getCreaterAccount();
		String membersAccount = group.getMembersAccount();
		//获取accountNum,使用Hash的键集合存放可以去掉重复的accountNum
		Map<String,String> map = new HashMap<String,String>();
		if(!WebUtil.isEmpty(adminsAccount)){
			String[] array = adminsAccount.split(",");
			for(String accountNum:array){
				map.put(accountNum,accountNum);
			}
		}
		if(!WebUtil.isEmpty(createrAccount)){
			String[] array = createrAccount.split(",");
			for(String accountNum:array){
				map.put(accountNum,accountNum);			
			}
		}
		if(!WebUtil.isEmpty(membersAccount)){
			String[] array = membersAccount.split(",");
			for(String accountNum:array){
				map.put(accountNum,accountNum);
			}
		}
		
		List<Long> accountNumList = new ArrayList<Long>();
		for(String accountNum:map.keySet()){
			accountNumList.add(WebUtil.toLong(accountNum));
		}
		
		List<UserProfile> list = new ArrayList<UserProfile>();
		list = this.userProfileMapper.selectByAccountNums(accountNumList);
		return list;
	}
	
	
	public GroupInfoEntity selectGroupByGroupId(String groupId){
		return this.groupInfoMapper.selectGroupByGroupId(groupId);
	}
	
	

	/**--将一个accountNum的用户加入到某个群里面--**/
	public void updateGroupAddUser(String accountNum,String groupId){
		//查询用户信息
		UserProfile profile = userProfileMapper.selectByAccountNum(accountNum);
		if(profile==null){
			return;
		}
		//查询当前群的信息
		GroupInfoEntity group = this.groupInfoMapper.selectGroupByGroupId(groupId);
		String membersAccount =group.getMembersAccount() ;
		if(WebUtil.isNull(membersAccount)){
			//第一个人加入到membersAccount里面
			group.setMembersAccount(accountNum);
		}else{
			//判断accounts数组内是否存在accountNum
			if( membersAccount.indexOf(accountNum)==-1){
				group.setMembersAccount(membersAccount+","+accountNum);
			}
		}
		groupInfoMapper.update(group);
		//用户的UserProfile里面groupName字段也同时更新
		String groupName = profile.getGroupName();
		if(WebUtil.isNull(groupName)){
			profile.setGroupName(groupId);
		}else{
			if(groupName.indexOf(groupId)==-1){
				profile.setGroupName(groupName+","+groupId);
			}
		}
		userProfileMapper.update(profile);	
	}
	
	/**--删除群操作,accountNum为删除人--**/
	public void deleteGroupByGroupId(String groupId,String accountNum) throws Exception{
		//查询该群所有的用户
		List<UserProfile> userList = this.getUserProfileByGroupId(groupId);
		//所有用户移除groupId
		for(UserProfile user:userList){
			String groupName = user.getGroupName();
			if(!WebUtil.isNull(groupName)){
				String groupIds[] = groupName.split(",");
				List<String> list =new ArrayList<String>(Arrays.asList(groupIds));
				for(int i=0;i<list.size();i++){
					if(groupId.equals(list.get(i))){
						list.remove(i);
						break;
					}
				}
				groupName = WebUtil.arrayToString(list);
				user.setGroupName(groupName);
				this.userProfileMapper.update(user);
			}
		}
		
		//删除该群
		List<String> idList = new ArrayList<String>();
		idList.add(groupId);
		this.groupInfoMapper.deleteByGroupId(idList);
		//在tigase删除
		// 在tigase上删除群组
		TigaseUtils tigaseUtils = TigaseUtils.getInstance();
		tigaseUtils.deleteGroupNod(groupId, accountNum);
		
		
		
	}
	
	/**--将一个accountNum的用户移除某个群,踢人或者退群操作--**/
	public void updateGroupRemoveUser(String accountNum,String groupId)  throws Exception{
		//查询当前用户信息
		UserProfile user = this.selectByAccountNum(accountNum);
		if(user==null){
			return;
		}
		//查询当前群的信息
		GroupInfoEntity group = this.groupInfoMapper.selectGroupByGroupId(groupId);
		String membersAccount =group.getMembersAccount() ;
		if(!WebUtil.isNull(membersAccount)&&membersAccount.indexOf(accountNum)!=-1&&user!=null){
			//membersAccount移除该用户
			String accountNums[] = membersAccount.split(",");
			List<String> list = new ArrayList<String>( Arrays.asList(accountNums));
			for(int i=0;i<list.size();i++){
				if(accountNum.equals(list.get(i))){
					list.remove(i);
					break;
				}
			}
			membersAccount = WebUtil.arrayToString(list);
			group.setMembersAccount(membersAccount);
			groupInfoMapper.update(group);
			//将用户的groupName移除
			String groupName = user.getGroupName();
			if(!WebUtil.isNull(groupName)&&groupName.indexOf(groupId)!=-1){
				String groupIds[] = groupName.split(",");
				List<String> groupIdList = new ArrayList<String>(Arrays.asList(groupIds));
				for(int i=0;i<groupIdList.size();i++){
					if(groupId.equals(groupIdList.get(i))){
						groupIdList.remove(i);
						break;
					}
				}
				groupName = WebUtil.arrayToString(groupIdList);
				user.setGroupName(groupName);
				this.userProfileMapper.update(user);
			}
			//如果退群者是管理员，则删除该群
			if(accountNum.equals(group.getAdminsAccount())){
				this.deleteGroupByGroupId(groupId, accountNum);
			}
			
			
		}
	}
	
	/**--创建群--*
	 * @throws Exception */
	public void insertGroup(String accountNum, GroupInfoEntity group) throws Exception{
		this.groupInfoMapper.save(group);
		//用户的groupName添加该群
		UserProfile profile = userProfileMapper.selectByAccountNum(accountNum);
		String groupName = profile.getGroupName();
		if(WebUtil.isNull(groupName)){
			profile.setGroupName(group.getGroupId());
		}else{
			if(groupName.indexOf(group.getGroupId())==-1){
				profile.setGroupName(groupName+","+group.getGroupId());
			}
		}
		userProfileMapper.update(profile);
		//tigase上创建
		TigaseUtils tigaseUtils = TigaseUtils.getInstance();
		// 如果tigase发生异常，web回滚
		tigaseUtils.createGroupNod(group.getGroupId(), group.getAdminsAccount());
	}
	
	public static void main(String[] args) {
		String s = "123,456,789";

	}

	/**
	 * 获取用户组织中间表信息
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> queryUserAlumni(Map<String,String> map) {
		return userProfileMapper.queryUserAlumni(map) ;
	}

	/**
	 * 变更手机用户认证信息
	 * @param accountNum
     */
	public void changeUserBaseInfo(String accountNum) {
		if(StringUtils.isNotBlank(accountNum)) {
			Map<String, String> map = userProfileMapper.selectBaseInfo(accountNum);
			if(map == null) {
				map = Maps.newHashMap() ;
			}
			map.put("accountNum",accountNum) ;
			userProfileMapper.clearBaseInfoId(map);
		}
	}
}
