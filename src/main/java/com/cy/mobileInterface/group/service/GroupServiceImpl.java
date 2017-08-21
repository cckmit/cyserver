package com.cy.mobileInterface.group.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.userProfile.dao.GroupInfoMapper;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.mobileInterface.group.entity.Group;
import com.cy.mobileInterface.group.entity.GroupInfo;
import com.cy.mobileInterface.group.entity.GroupMembers;
import com.cy.system.TigaseUtils;
import com.cy.util.WebUtil;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private GroupInfoMapper groupInfoMapper;

	@Override
	public void getGroupMembersInfo(Message message, String content) {
		GroupMembers groupMembers = JSON.parseObject(content, GroupMembers.class);

		if (WebUtil.isEmpty(groupMembers.getAccountNum()) || WebUtil.isEmpty(groupMembers.getPassword()) || WebUtil.isEmpty(groupMembers.getAccountNums())) { // 协议检查
			message.setMsg("账号,密码，联系人帐号列表不能为空!");
			message.setSuccess(false);
			return;
		}
		// 自己
		UserProfile userProfileOwner = userProfileMapper.selectByAccountNum(groupMembers.getAccountNum());
		if (userProfileOwner == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		if (!groupMembers.getPassword().equals(userProfileOwner.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}
		List<Long> list = new ArrayList<Long>();
		String[] accountNumArray = groupMembers.getAccountNums().split(",");
		for (String accountNum : accountNumArray) {
			list.add(Long.parseLong(accountNum));
		}

		List<Map<String, String>> userProfileSearchEntityList = new ArrayList<Map<String, String>>();
		if (list.size() > 0) {
			List<UserProfile> userProfiles = userProfileMapper.selectByAccountNums(list);
			for (UserProfile userProfile : userProfiles) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", userProfile.getName());
				map.put("sex", userProfile.getSex());
				map.put("picture", userProfile.getPicture());
				map.put("accountNum", userProfile.getAccountNum());
				userProfileSearchEntityList.add(map);
			}
		}

		if (userProfileSearchEntityList.size() > 0) {
			message.setMsg("查询成功!");
			message.setObj(userProfileSearchEntityList);
			message.setSuccess(true);
		} else {
			message.setMsg("没有联系人信息!");
			message.setSuccess(false);
		}
	}

	@Override
	public void getGroupInfo(Message message, String content) {
		GroupInfo groupInfo = JSON.parseObject(content, GroupInfo.class);
		if (WebUtil.isEmpty(groupInfo.getAccountNum()) || WebUtil.isEmpty(groupInfo.getPassword()) || WebUtil.isEmpty(groupInfo.getGroupId())) {// 协议检查
			message.setMsg("数据格式错误,帐号,密码,群id不能为空!");
			message.setSuccess(false);
			return;
		}
		// 查询UserProfile
		UserProfile userProfile = userProfileMapper.selectByAccountNum(groupInfo.getAccountNum());
		if (userProfile == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		// 核对密码
		if (!groupInfo.getPassword().equals(userProfile.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}

		// 去重
		Set<String> groupIdSet = new HashSet<String>();
		List<String> groupIdList = new ArrayList<String>();
		String groupIds[] = groupInfo.getGroupId().split(",");
		for (int i = 0; i < groupIds.length; ++i) {
			if (!groupIdSet.contains(groupIds[i])) {
				groupIdSet.add(groupIds[i]);
				groupIdList.add(groupIds[i]);
			}
		}

		List<GroupInfoEntity> groupInfoEntityList = groupInfoMapper.selectByGroupId(groupIdList);
		if (groupInfoEntityList == null || groupInfoEntityList.size() < 1) {
			message.setMsg("没有此群!");
			message.setSuccess(false);
			return;
		}
		message.setMsg("查询成功!");
		message.setObj(groupInfoEntityList);
		message.setSuccess(true);
	}

	/*
	 * 12号接口 修改群表信息，手机端自己处理， 如果上传空串则删除记录，如果不传此字段则不做改变，全删全建
	 * 0创建，1删除群组，2添加普通成员，3删除普通成员，4添加管理员，5删除管理员，6普通成员提升为管理员 7管理员降级为普通成员，8修改群自有信息
	 * 其中content里面的 accountNum,password,groupId,type,createrAccount,groupName必填
	 */
	@Override
	public void updateGroupInfo(Message message, String content) {
		try {
			Group group = JSON.parseObject(content, Group.class);
			if (WebUtil.isEmpty(group.getAccountNum()) || WebUtil.isEmpty(group.getPassword())) {// 协议检查
				message.setMsg("账号密码不能为空!");
				message.setSuccess(false);
				return;
			}
			if (WebUtil.isEmpty(group.getType()) || WebUtil.isEmpty(group.getCreaterAccount()) || WebUtil.isEmpty(group.getGroupName())) {
				message.setMsg("groupId,type,createrAccount,groupName不能为空!");
				message.setSuccess(false);
				return;
			}
			// 查询用户信息userProfile
			UserProfile userProfile = userProfileMapper.selectByAccountNum(group.getAccountNum());

			if (userProfile == null) {
				message.setMsg("查询不到此账号!");
				message.setSuccess(false);
				return;
			}
			if (!userProfile.getPassword().equals(group.getPassword())) { // 核对密码
				message.setMsg("密码错误!");
				message.setSuccess(false);
			}

			// 其它选填字段
			String description = group.getDescription();
			String subject = group.getSubject();
			String membersAccount = group.getMembersAccount();
			String adminsAccount = group.getAdminsAccount();
			String type = group.getType();
			String groupId = group.getGroupId();
			GroupInfoEntity groupInfoEntity = null;
			// 建群
			if (type.equals("0")) {
				// 时间戳形式,现在这个id不由手机生成#######################
				groupId = group.getAccountNum() + "-" + new Date().getTime();
				// 创建群组
				groupInfoEntity = new GroupInfoEntity();
				groupInfoEntity.setGroupId(groupId);
				groupInfoEntity.setGroupName(group.getGroupName());
				groupInfoEntity.setDescription(description);
				groupInfoEntity.setSubject(subject);
				groupInfoEntity.setCreaterAccount(group.getCreaterAccount());
				groupInfoEntity.setAdminsAccount(adminsAccount);
				groupInfoEntity.setMembersAccount(membersAccount);

				GroupInfoEntity entity = groupInfoMapper.selectGroupByGroupId(groupId);
				// 查询该群是否存在
				if (entity != null) {
					message.setMsg("服务器已经存在此群");
					message.setSuccess(false);
					return;
				} else {
					// 创建群组
					groupInfoMapper.save(groupInfoEntity);
					// 同步修改各个成员的groupName信息
					List<String> accountList = generateListFromString(adminsAccount, membersAccount);
					// 取出所有人的group信息
					List<UserProfile> userProfileList = userProfileMapper.selectGroupInfoByAccountNumList(accountList);
					for (UserProfile userProfile2 : userProfileList) {
						String groupIds = handleId(userProfile2.getGroupName(), groupId, "add");
						userProfile2.setGroupName(groupIds);
						userProfileMapper.updateGroup(userProfile2);
					}
					// tigase上创建群组节点
					TigaseUtils tigaseUtils = TigaseUtils.getInstance();
					// 如果tigase发生异常，web回滚
					tigaseUtils.createGroupNod(groupId, group.getAccountNum());
					message.setMsg("创建成功");
					message.setObj(groupId);
					message.setSuccess(true);
					return;
				}
			} else if (type.equals("1")) {
				// 删除群
				if (WebUtil.isEmpty(group.getGroupId())) {
					message.setMsg("groupId不能为空!");
					message.setSuccess(false);
					return;
				}
				TigaseUtils tigaseUtils = TigaseUtils.getInstance();
				tigaseUtils.deleteGroupNod(groupId, group.getAccountNum());
				groupInfoMapper.deleteById(groupId);
				// 同步修改各个成员的groupName信息
				List<String> accountList = generateListFromString(adminsAccount, membersAccount);
				// 取出所有人的group信息
				List<UserProfile> userProfileList = userProfileMapper.selectGroupInfoByAccountNumList(accountList);
				for (UserProfile userProfile2 : userProfileList) {
					String groupIds = handleId(userProfile2.getGroupName(), groupId, "delete");
					userProfile2.setGroupName(groupIds);
					userProfileMapper.updateGroup(userProfile2);
				}
				message.setMsg("删除成功!");
				message.setSuccess(true);
				return;
			} else if (type.equals("2")) {
				// 添加普通成员
				if (WebUtil.isEmpty(group.getGroupId())) {
					message.setMsg("groupId不能为空!");
					message.setSuccess(false);
					return;
				}
				if (WebUtil.isEmpty(membersAccount)) {
					message.setMsg("成员列表为空!");
					message.setSuccess(false);
					return;
				}
				groupInfoEntity = groupInfoMapper.selectGroupByGroupId(groupId);
				if (groupInfoEntity != null) {
					List<String> accountList = generateListFromString(groupInfoEntity.getMembersAccount(), membersAccount);
					List<String> accountList1 = generateListFromString(null, membersAccount);
					String membersAccounts = generateStringFromList(accountList);
					groupInfoEntity.setMembersAccount(membersAccounts);
					groupInfoMapper.updateMembersAccount(groupInfoEntity);
					List<UserProfile> userProfileList = userProfileMapper.selectGroupInfoByAccountNumList(accountList1);
					for (UserProfile userProfile2 : userProfileList) {
						String groupIds = handleId(userProfile2.getGroupName(), groupId, "add");
						userProfile2.setGroupName(groupIds);
						userProfileMapper.updateGroup(userProfile2);
					}
					message.setMsg("普通成员添加成功!");
					message.setSuccess(true);
					return;
				} else {
					message.setMsg("群不存在!");
					message.setSuccess(false);
					return;
				}
			} else if (type.equals("3")) {
				// 删除普通成员
				if (WebUtil.isEmpty(group.getGroupId())) {
					message.setMsg("groupId不能为空!");
					message.setSuccess(false);
					return;
				}
				if (WebUtil.isEmpty(membersAccount)) {
					message.setMsg("成员列表为空!");
					message.setSuccess(false);
					return;
				}
				groupInfoEntity = groupInfoMapper.selectGroupByGroupId(groupId);
				if (groupInfoEntity != null) {
					// 删除普通成员一次只能删一个
					String newMembers = handleId(groupInfoEntity.getMembersAccount(), membersAccount, "delete");
					groupInfoEntity.setMembersAccount(newMembers);
					groupInfoMapper.updateMembersAccount(groupInfoEntity);
					List<String> accountList = generateListFromString(null, membersAccount);
					List<UserProfile> userProfileList = userProfileMapper.selectGroupInfoByAccountNumList(accountList);
					for (UserProfile userProfile2 : userProfileList) {
						String groupIds = handleId(userProfile2.getGroupName(), groupId, "delete");
						userProfile2.setGroupName(groupIds);
						userProfileMapper.updateGroup(userProfile2);
					}
					message.setMsg("普通成员删除成功!");
					message.setSuccess(true);
					return;
				} else {
					message.setMsg("群不存在!");
					message.setSuccess(false);
					return;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 压缩字段合并出账号列表
	 * 
	 * @param adminsAccount
	 * @param membersAccount
	 * @return
	 */
	private List<String> generateListFromString(String adminsAccount, String membersAccount) {
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
	}

	private String generateStringFromList(List<String> membersAccountsList) {
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
	}

	private String handleId(String ids, String id, String operation) {
		StringBuffer sb = new StringBuffer();
		if (operation.equals("add")) {
			if (ids != null && ids.length() > 0) {
				if (ids.indexOf(id) == -1) {
					sb.append(ids);
					sb.append(",");
					sb.append(id);
				} else {
					sb.append(ids);
				}
			} else {
				sb.append(id);
			}
		} else {
			if (ids != null && ids.length() > 0) {
				String[] array = ids.split(",");
				for (String s : array) {
					if (!s.equals(id)) {
						sb.append(s);
						sb.append(",");
					}
				}
				if (sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1);
				}
			}
		}
		return sb.toString();
	}

	@Override
	public void selectUserByGroupId(Message message, String content) {
		GroupInfo groupInfo = JSON.parseObject(content, GroupInfo.class);
		if (WebUtil.isEmpty(groupInfo.getAccountNum()) || WebUtil.isEmpty(groupInfo.getPassword()) || WebUtil.isEmpty(groupInfo.getGroupId())) { // 协议检查
			message.setMsg("数据格式错误,帐号,密码,groupId不能为空!");
			message.setSuccess(false);
			return;
		}

		// 核对帐号和密码
		UserProfile userProfileOwner = userProfileMapper.selectByAccountNum(groupInfo.getAccountNum());
		if (userProfileOwner == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		// 核对密码
		if (!groupInfo.getPassword().equals(userProfileOwner.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}

		List<UserProfile> userList = userProfileMapper.getUserProfileByGroupId(groupInfo.getGroupId());
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>(); // 使用list<Map>封装数据
		if (!WebUtil.isEmpty(userList)) {
			for (UserProfile user : userList) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("picture", WebUtil.isNull(user.getPicture()) ? "" : user.getPicture());
				userMap.put("accountNum", WebUtil.isNull(user.getAccountNum()) ? "" : user.getAccountNum());
				userMap.put("name", WebUtil.isNull(user.getName()) ? "" : user.getName());
				userMap.put("address", WebUtil.isNull(user.getAddress()) ? "" : user.getAddress());
				userMap.put("alumni_id", WebUtil.isNull(String.valueOf(user.getAlumni_id())) ? "" : user.getAlumni_id());
				userMap.put("profession", WebUtil.isNull(user.getProfession()) ? "" : user.getProfession());
				userMap.put("hobby", WebUtil.isNull(user.getHobby()) ? "" : user.getHobby());
				userMap.put("workUtil", WebUtil.isNull(user.getWorkUtil()) ? "" : user.getWorkUtil());
				userMap.put("position", WebUtil.isNull(user.getPosition()) ? "" : user.getPosition());
				userMap.put("email", WebUtil.isNull(user.getEmail()) ? "" : user.getEmail());
				userMap.put("sign", WebUtil.isNull(user.getSign()) ? "" : user.getSign());
				userMap.put("baseInfoId", WebUtil.isNull(user.getBaseInfoId()) ? "" : user.getBaseInfoId());
				listMap.add(userMap);
			}
			message.setMsg("查询成功!");
			message.setObj(listMap);
			message.setSuccess(true);
		} else {
			message.setMsg("没有数据!");
			message.setSuccess(false);
		}
	}

}
