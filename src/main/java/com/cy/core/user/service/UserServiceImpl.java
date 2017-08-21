package com.cy.core.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.role.entity.Role;
import com.cy.core.user.dao.UserDeptMapper;
import com.cy.core.user.dao.UserMapper;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserDept;
import com.cy.core.user.entity.UserRole;
import com.cy.system.SecretUtil;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserDeptMapper userDeptMapper;

	public DataGrid<User> dataGrid(Map<String, Object> map) {
		DataGrid<User> dataGrid = new DataGrid<User>();
		long count = userMapper.countUser(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<User> list = userMapper.selectUserListx(map);
		for (int i = 0; i < list.size(); i++) {
			StringBuffer sb = new StringBuffer();
			List<UserRole> userRoles = list.get(i).getUserRoles();
			for (UserRole userRole : userRoles) {
				if(userRole.getRole() != null){
					sb.append(userRole.getRole().getRoleName()).append(",");
				} else {
					Role role = new Role();
					UserRole uRole = new UserRole();
					uRole.setRole(role);
					List<UserRole> roles = new ArrayList<UserRole>();
					roles.add(uRole);
					list.get(i).setUserRoles(roles);
				}
			}
			/*if(sb.length()>0){
				list.get(i).setRoleNames(sb.substring(0, sb.length()-1).toString());
			} else {
				list.get(i).setRoleNames("");
			}*/
		}
		dataGrid.setRows(list);
		return dataGrid;
	}

	public void save(User user, String ids, long type ) {
		try {
			/*if (user.getFlag() == 0) {
				user.setDeptId(0);
			}*/
			user.setUserPassword(SecretUtil.encryptToSHA(user.getUserPassword()));
			/*用户个数*/
			Map<String, Object> map = new HashMap<String, Object>();
			long nCnt = userMapper.countUser( map );
			if( nCnt == 1 ) //默认总会
			{
				user.setDeptId( 1 );
				user.setRoleIds( "2,14" );
			}
			userMapper.save(user);
			User u = null;	// 取出刚插入的记录，后面要用到userId
			// lixun 校友组织页面进入,默认权限
			if( type == 1 ) {
				saveUserDept( user.getUserId() );
			}
			else if( StringUtils.isNotBlank(ids) ) {
				String[] idArray = ids.split(",");
				for (String id : idArray) {
					UserDept userDept = new UserDept();
					userDept.setDeptId(id);
					userDept.setUserId(user.getUserId());
					userDeptMapper.save(userDept);
				}
			}
			//lixun
			u = userMapper.selectByUserAccount(user.getUserAccount());

			//删除该用户ID关联的角色
			userMapper.deleteUserAndRoleByUserId(u.getUserId());
			//处理web后台系统用户与角色的关联关系
			if (!user.getRoleIds().isEmpty()) {
				String[] idArray1 = user.getRoleIds().split(",");
				for (String roleId : idArray1) {
					UserRole userRole = new UserRole();
					userRole.setUserId(u.getUserId());
					userRole.setRoleId(Integer.parseInt(roleId.trim()));
					userMapper.insertUserAndRole(userRole);
				}
			}

			/*if (user.getFlag() == 0) {
				// lixun 校友组织页面进入,默认权限
				if( type == 1 )
					ids = userMapper.selectDeptIdByAlumniId( user.getDeptId());	//获取默认部门
				if( StringUtils.isNotBlank(ids) ) {
					String[] idArray = ids.split(",");
					for (String id : idArray) {
						UserDept userDept = new UserDept();
						userDept.setDeptId(id);
						userDept.setUserId(user.getUserId());
						userDeptMapper.save(userDept);
					}
				}
				//lixun
				u = userMapper.selectByUserAccount(user.getUserAccount());
				//处理web后台系统用户与角色的关联关系
				if (!user.getRoleIds().isEmpty()) {
					String[] idArray1 = user.getRoleIds().split(",");
					for (String roleId : idArray1) {
						UserRole userRole = new UserRole();
						userRole.setUserId(u.getUserId());
						userRole.setRoleId(Integer.parseInt(roleId.trim()));
						userMapper.insertUserAndRole(userRole);
					}
				}
			}
			if (user.getFlag() == 1) {
				u = userMapper.selectByUserAccountx(user.getUserAccount());
				//处理校友会系统用户与角色的关联关系
				if (!user.getAlumniRoleId().isEmpty()) {
					UserRole userRole = new UserRole();
					userRole.setUserId(u.getUserId());
					userRole.setRoleId(Integer.parseInt(user.getAlumniRoleId()));
					userMapper.insertUserAndRole(userRole);
				}
			}*/
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public long countByUserAccount(Map<String, Object> map) {
		return userMapper.countByUserAccount(map);
	}

	public User selectByUserId(String userId) {
		return userMapper.selectByUserId(String.valueOf(userId));
	}
	
	public void selectByTelephone(Message message, String content)
	{
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		
		String telephone = (String)map.get("telephone");

		if (StringUtils.isBlank(telephone)) {
			message.setMsg("未传入用户手机号");
			message.setSuccess(false);
			return;
		}
		
		
		List<User> uesr=userMapper.selectByTelephoneForApp(telephone);
		
	    if(uesr == null){
	        message.setMsg("手机号不存在,非管理员用户");
            message.setSuccess(false);
            return;
	    }
	    else{
		    message.setMsg("success");
			message.setSuccess(true);		
			message.setObj(uesr);
			return;
	    }
		
	}	

	public void update(User user, String ids, long type ) {
		try {
			userMapper.update(user);
			//删除旧的角色关联
			userMapper.deleteUserAndRoleByUserId(user.getUserId());
			if (user.getRoleIds() != null){
				//添加新的角色关联
				String[] idArray1 = user.getRoleIds().split(",");
				for (String roleId : idArray1) {
					UserRole userRole = new UserRole();
					userRole.setUserId(user.getUserId());
					userRole.setRoleId(Integer.parseInt(roleId.trim()));
					userMapper.insertUserAndRole(userRole);
				}
			}
			/*if(user.getFlag() == 0){
                if (user.getRoleIds() == null){
                    userMapper.deleteUserAndRoleByUserId(user.getUserId());
                }else{
                    //删除旧的角色关联
					userMapper.deleteUserAndRoleByUserId(user.getUserId());
                    //添加新的角色关联
					String[] idArray1 = user.getRoleIds().split(",");
					for (String roleId : idArray1) {
						UserRole userRole = new UserRole();
						userRole.setUserId(user.getUserId());
						userRole.setRoleId(Integer.parseInt(roleId.trim()));
						userMapper.insertUserAndRole(userRole);
					}
				}
			}
			if (user.getFlag() == 1) {
				//处理校友会系统用户与角色的关联关系
				if (!user.getAlumniRoleId().isEmpty()) {
					UserRole userRole = new UserRole();
					userRole.setUserId(user.getUserId());
					userRole.setRoleId(Integer.parseInt(user.getAlumniRoleId()));
					userMapper.insertUserAndRole(userRole);
				}
			}*/
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public long countByTelphone(Map<String, Object> map){
		return userMapper.countByTelphone(map);
	}


	/**
	 * 修改用户信息
	 * @param user
	 */
	public void update(User user) {
		if(user != null) {
			userMapper.update(user);
		}
	}

	public void delete(long userId) {
		try {
			userDeptMapper.deleteByUserId(userId);
			userMapper.deleteUserAndRoleByUserId(userId);
			userMapper.delete(userId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User selectRole(long userId) {
		return userMapper.selectRole(userId);
	}

	public void updateGrant(String ids, long id) {
		try {
			String[] idAttr = ids.split(",");
			for (String idStr : idAttr) {
				if (idStr != null && !"".equals(idStr)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", id);
					map.put("roleId", Long.parseLong(idStr));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User selectByUserAccount(String userAccount) {
		return userMapper.selectByUserAccount(userAccount);
	}

	public void updatePassword(User user) {
		userMapper.updatePassword(user);
	}


	public List<UserRole> getUserRoleIdsByUserId(long userId) {
		return userMapper.getUserRoleIdsByUserId(userId);
	}

	public User selectByUserAccountNoSys(String userAccount) {
		return userMapper.selectByUserAccountNoSys(userAccount);
	}

	/**
	 * 修改学院分会管理员数据权限
	 * @param deptId 学院编号
	 * @param aluId	 学院分会编号
	 * @param type	 现有标识(0:取消现有;1:设置现有)
	 */
	@Override
	public void modifyUserPermission( String deptId, String aluId, String type )
	{
		if( type.equals("1") )	//新增
		{
			Map<String,String> map = new HashMap<String,String>();
			map.put( "deptId", deptId );
			map.put( "aluId", aluId );
			userDeptMapper.addUserPermission( map );
		}
		else if( type.equals("0") )	//删除
		{
			userDeptMapper.delUserPermission( deptId );
		}
	}

	@Override
	public long saveUserDept( long userid )	//lixun
	{
		String sAcademyid = userMapper.selectAcademyid( userid );	//查询所属部门
		Map<String,String> mp = new HashMap<String,String>();
		if( StringUtils.isNotBlank( sAcademyid ) ) {

			mp.put( "type", "1" );
			mp.put( "deptid", sAcademyid  );
		}
		else
		{
			mp.put( "type", "2" );
		}
		mp.put( "userid",  String.valueOf( userid )  );

		userMapper.saveUserDept( mp );	//储存
		return 0;
	}


	/**
	 * 查询满足条件的用户列表
	 * @param map
	 * @return
	 */
	public List<User> queryUserList(Map<String,Object> map) {
		return userMapper.queryUserList(map) ;
	}
	public List<User> queryChoiceUserList(Map<String,Object> map) {
		return userMapper.queryChoiceUserList(map) ;
	}

	/**
	 * 通过Id查询用户
	 */
	public User selectByUserIdx(String userId){ return userMapper.selectByUserId(userId); }
}
