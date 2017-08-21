package com.cy.core.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.system.SecretUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserRole;
import com.cy.core.user.service.UserService;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.service.UserProfileService;

@Namespace("/user")
@Action("userAction")
public class UserAction extends AdminBaseAction
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserAction.class);

	private User user; //系统用户表

	private UserProfile userProfile; //校友用户表
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserProfileService userProfileService;

	/*qpf 2016-7-15*/
	private long isAlumni = 0;	//分会管理员入口
	private int mainType = 0;	//增加分会类型
	private String aluid;
	public String getAluid() {
		return aluid;
	}
	public void setAluid(String Id) {
		this.aluid = Id;
	}
	public long getIsAlumni() {
		return isAlumni;
	}
	public void setIsAlumni(long isAlumni) {
		this.isAlumni = isAlumni;
	}
	public long getMainType() {
		return mainType;
	}
	public void setMainType(int mainType) {
		this.mainType = mainType;
	}
	public void dataGrid()
	{
		String userAccount = getRequest().getParameter("userAccount");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		map.put( "aid", aluid );	//qpf 2016-7-15 加
		map.put("userAccount", userAccount);
		super.writeJson(userService.dataGrid(map));
	}

	public void save()
	{
		Message message = new Message();
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userAccount", user.getUserAccount());
			map.put("userId", user.getUserId());
			/* lixun 校友组织页面访问,设置默认权限和角色 */
			if( isAlumni == 1 )
			{
				//暂时直接用role的ID来添加,之后再优化。2:分会,14:总会,15:学院分会,16:地方行业分会
				String sIds = "";
				switch ( mainType )
				{
					case 99 : sIds = ",14";break;
					case 1 : sIds = ",16";break;
					case 2 :
					case 3 : sIds = ",15";break;
					case 0 : message.setMsg("该校友会是虚拟组织,不能添加管理员"); message.setSuccess(false); break;
					default: message.setMsg("新增失败"); message.setSuccess(false); break;
				}
				user.setRoleIds( "2" + sIds );
			}
			long count = userService.countByUserAccount(map);
			if (count > 0)
			{
				message.setMsg("用户帐号已被占用");
				message.setSuccess(false);
			} else
			{
				
				userService.save(user,ids,isAlumni);		
				
				//新增用户同时，在校友表同时创建账号，如手机号已存在校友表，则不创建，默认对方为校友会组织管理员
				
		     //---2017-8-9------------by sky 增加校友会管理员同时同步账号到校友用户表，可以用此账号APP登陆使用管理功能----------------------------
				userProfile=new UserProfile();
				userProfile.setName(user.getUserName());
				userProfile.setPhoneNum(user.getTelephone());	
				userProfile.setPassword(user.getTelephone()); //默认密码为手机号码			
				userProfile.setAlumni_id(user.getDeptId()); //从属校友会组织				
				userProfile.setPosition("99999999");//特殊标识，区分校友和管理员				
				userProfileService.savecheck(userProfile);	//如果手机号已存在校友表，不插入			
				userProfileService.updateposition(userProfile);  //更新校友，设为APP管理员
				
		     //---------------------------------------------------------------------------------------------------------------------
				
				
				message.setMsg("新增成功");
				message.setSuccess(true);
				
			}
		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update()
	{
		Message message = new Message();
		try
		{
			/* lixun 校友组织页面访问,设置默认权限和角色 */
			if( isAlumni == 1 )
			{
				//暂时直接用role的ID来添加,之后再优化。2:分会,14:总会,15:学院分会,16:地方行业分会
				String sIds = "";
				switch ( mainType )
				{
					case 99 : sIds = ",14";break;
					case 1 : sIds = ",16";break;
					case 2 :
					case 3 : sIds = ",15";break;
					case 0 : message.setMsg("该校友会是虚拟组织,不能添加管理员"); message.setSuccess(false); break;
					default: message.setMsg("新增失败"); message.setSuccess(false); break;
				}
				user.setRoleIds( "2" + sIds );
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getUserId());
			map.put("telephone", user.getTelephone());
			/*if(!user.getTelephone().isEmpty() && user.getTelephone() != null && user.getTelephone() != "" && userService.countByTelphone(map) > 0){
				message.setMsg("该手机号已被绑定");
				message.setSuccess(false);
			}else{*/
				userService.update( user, ids, isAlumni );
				message.setMsg("修改成功");
				message.setSuccess(true);
			//}
		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getUserByUserId()
	{
		user = userService.selectByUserId(String.valueOf(user.getUserId()));
		super.writeJson(user);
	}
	
	public void doNotNeedSecurity_getUserByUserId()
	{
		user = userService.selectByUserId(String.valueOf(user.getUserId()));
		super.writeJson(user);
	}

	public void doNotNeedSecurity_resetPassword()
	{
		Message message = new Message();
		try {
			user = userService.selectByUserId(String.valueOf(user.getUserId()));
			user.setUserPassword(SecretUtil.encryptToSHA("111111"));
			userService.update(user);
			message.setMsg("密码重置成功");
			message.setSuccess(false);
		}catch (Exception e){
			logger.error(e, e);
			message.setMsg("重置失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete()
	{
		Message message = new Message();
		try
		{
			userService.delete(id);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void grant(){
		Message message = new Message();
		try
		{
			userService.updateGrant(ids, id);
			message.setMsg("修改角色成功");
			message.setSuccess(true);
		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("修改角色失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	
	/**
	 * 通过userId查询所有角色的ID
	 */
	public void doNotNeedSecurity_getUserRoleIdsByUserId()
	{
		List<UserRole> userRoles = userService.getUserRoleIdsByUserId(user.getUserId());
		StringBuffer sb = new StringBuffer();
		for (UserRole userRole : userRoles) {
			sb.append(userRole.getRoleId()).append(",");
		}
		if(sb.lastIndexOf(",")>0){
			super.writeJson(sb.substring(0, sb.length()-1).toString());
		}
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

}
