package com.cy.core.userbaseinfo.action;

import com.cy.common.utils.CacheUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.userbaseinfo.entity.UserBaseInfo;
import com.cy.core.userbaseinfo.service.UserBaseInfoService;

@Namespace("/userBaseInfo")
@Action(value = "userBaseInfoAction")
public class UserBaseInfoAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserBaseInfoAction.class);

	@Autowired
	private UserBaseInfoService userInfoService;

	private UserBaseInfo userInfo;

	private String deptId;

	private String schoolId;
	private String departId;
	private String gradeId;
	private String classId;

	private String url;

	private int isInput;
	private String country;
	private String province;
	private String city;
	private String area;





	public void dataGrid() {
		dataGridUserBaseInfo();
	}
	
	public void dataGridx() {
		dataGridUserBaseInfo();
	}
	
	public void dataGridUserBaseInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (sort.equals("userName")) {
			sort = "name_pinyin";
		}
		map.put("sort", sort);
		map.put("order", order);
		map.put("deptList", getUser().getDepts());
		if (classId != null && !classId.equals("")) {
			map.put("deptId1", classId);
		} else if (gradeId != null && !gradeId.equals("")) {
			map.put("deptId1", gradeId);
		} else if (departId != null && !departId.equals("")) {
			map.put("deptId1", departId);
		} else if (schoolId != null && !schoolId.equals("")) {
			map.put("deptId1", schoolId);
		}
		if (userInfo != null) {
			map.put("telId", userInfo.getTelId());
			map.put("userName", userInfo.getUserName());
			map.put("studentType", userInfo.getStudentType());
			map.put("studentnumber", userInfo.getStudentnumber());
			// map.put("residentialArea", userInfo.getResidentialArea());
			map.put("workUnit", userInfo.getWorkUnit());
			map.put("mailingAddress", userInfo.getMailingAddress());
			map.put("majorId", userInfo.getMajorId());
		}
		String residentialArea = "";
//		if (country != null && country.length() > 0) {
//			residentialArea += country;
//		}
		if (province != null && province.length() > 0) {
			residentialArea += province;
		}
		if (city != null && city.length() > 0) {
			residentialArea += " " + city;
		}
		if (area != null && area.length() > 0) {
			residentialArea += " " + area;
		}
		if (residentialArea.length() > 0) {
			map.put("residentialArea", residentialArea);
		}
		super.writeJson(userInfoService.selectByDeptId(map));
	}

	public void doNotNeedSecurity_dataGridFromBase() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		map.put("deptList", getUser().getDepts());
		if (classId != null && !classId.equals("")) {
			map.put("deptId1", classId);
		} else if (gradeId != null && !gradeId.equals("")) {
			map.put("deptId1", gradeId);
		} else if (departId != null && !departId.equals("")) {
			map.put("deptId1", departId);
		} else if (schoolId != null && !schoolId.equals("")) {
			map.put("deptId1", schoolId);
		}
		if (userInfo != null) {
			map.put("telId", userInfo.getTelId());
			map.put("userName", userInfo.getUserName());
			map.put("studentType", userInfo.getStudentType());
			map.put("studentnumber", userInfo.getStudentnumber());
			map.put("residentialArea", userInfo.getResidentialArea());
			map.put("workUnit", userInfo.getWorkUnit());
			map.put("mailingAddress", userInfo.getMailingAddress());
			map.put("majorId", userInfo.getMajorId());
		}
		super.writeJson(userInfoService.selectByDeptIdForImport(map));
	}

	public void delete() {
		Message message = new Message();
		try {
			userInfoService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void deleteAll() {
		Message message = new Message();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("deptList", getUser().getDepts());
			if (classId != null && !classId.equals("")) {
				map.put("deptId1", classId);
			} else if (gradeId != null && !gradeId.equals("")) {
				map.put("deptId1", gradeId);
			} else if (departId != null && !departId.equals("")) {
				map.put("deptId1", departId);
			} else if (schoolId != null && !schoolId.equals("")) {
				map.put("deptId1", schoolId);
			}
			if (userInfo != null) {
				map.put("telId", userInfo.getTelId());
				map.put("userName", userInfo.getUserName());
				map.put("studentType", userInfo.getStudentType());
				map.put("studentnumber", userInfo.getStudentnumber());
				// map.put("residentialArea", userInfo.getResidentialArea());
				map.put("workUnit", userInfo.getWorkUnit());
				map.put("mailingAddress", userInfo.getMailingAddress());
				map.put("majorId", userInfo.getMajorId());
			}
			String residentialArea = "";
//			if (country != null && country.length() > 0) {
//				residentialArea += country;
//			}
			if (province != null && province.length() > 0) {
				residentialArea += province;
			}
			if (city != null && city.length() > 0) {
				residentialArea += " " + city;
			}
			if (area != null && area.length() > 0) {
				residentialArea += " " + area;
			}
			if (residentialArea.length() > 0) {
				map.put("residentialArea", residentialArea);
			}
			userInfoService.deleteAll(map);
			message.setMsg("删除成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void save() {
		Message message = new Message();
		try {

			String residentialArea = "";
			String resourceArea = "";
			if (province != null && province.length() > 0) {
				residentialArea += province;
			}
			if (city != null && city.length() > 0) {
				residentialArea += " " + city;
			}
			if (area != null && area.length() > 0) {
				residentialArea += " " + area;
			}
			if (residentialArea != null && residentialArea.length() > 0) {
				userInfo.setResidentialArea(residentialArea);
			}

			userInfoService.save(userInfo, getUser(), isInput);
			message.setMsg("新增成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败!" + e.getMessage());
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			String residentialArea = "";
			String resourceArea = "";
			if (province != null && province.length() > 0) {
				residentialArea += province;
			}
			if (city != null && city.length() > 0) {
				residentialArea += " " + city;
			}
			if (area != null && area.length() > 0) {
				residentialArea += " " + area;
			}
			if (residentialArea != null && residentialArea.length() > 0) {
				userInfo.setResidentialArea(residentialArea);
			}
			userInfoService.update(userInfo, getUser(), isInput);
			message.setMsg("修改成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败!" + e.getMessage());
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void doNotNeedSecurity_getUserInfoByUserId() {
		super.writeJson(userInfoService.selectByUserId(userInfo.getUserId()));
	}

	public void getUserInfoByUserId() {
		super.writeJson(userInfoService.selectByUserId(userInfo.getUserId()));
	}

	public void doNotNeedSecurity_getAllUserList() {
		super.writeJson(userInfoService.selectAllUserList());
	}

	public void doNotNeedSecurity_dataGridFor() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (userInfo != null) {
			map.put("telId", userInfo.getTelId());
			map.put("userName", userInfo.getUserName());
		}
		super.writeJson(userInfoService.dataGridFor(map));
	}

	/*Lixun 2017-5-11 正在导入的状态*/
	public void importState()
	{
		Map<String, Object> map = new HashMap<String, Object>();

		if ( (Integer)CacheUtils.get( "isImport") == 2 )
		{	//正在导入数据
			map.put( "state", 2 );
			map.put( "total", (Integer)CacheUtils.get( "nImportTotal") -1);
			map.put( "now", (Integer)CacheUtils.get( "nImportNow") );
		}
		else if ( (Integer)CacheUtils.get( "isImport") == 1 )
		{	//正在整理数据
			map.put( "state", 1 );
			if(CacheUtils.get( "nImportTotal")!=null&&!CacheUtils.get( "nImportTotal").equals("")){
				map.put( "total", (Integer)CacheUtils.get( "nImportTotal") -1);
			}
			if(CacheUtils.get( "nImportNow")!=null && !CacheUtils.get( "nImportNow").equals("")){
				map.put( "now", (Integer)CacheUtils.get( "nImportNow") );
			}
		}
		else
		{	//未导入数据
			map.put( "state", 0 );
			map.put( "url", (String)CacheUtils.get( "sImportResult") );
			if( CacheUtils.get( "sImportResult" ) != null )
				CacheUtils.remove( "sImportResult" );
			if( CacheUtils.get( "isImport" ) != null )
				CacheUtils.remove( "isImport" );
			if( CacheUtils.get( "nImportTotal" ) != null )
				CacheUtils.remove( "nImportTotal" );
			if( CacheUtils.get( "nImportNow" ) != null )
				CacheUtils.remove( "nImportNow" );
		}

		super.writeJson(map);
	}
	/*Lixun*/

	public void importData() {
		Message message = new Message();
		if( CacheUtils.get( "isImport" ) == null )
			CacheUtils.put( "isImport", 0 );
		if( CacheUtils.get( "nImportTotal" ) == null )
			CacheUtils.put( "nImportTotal", 0 ) ;
		if( CacheUtils.get( "nImportNow" ) == null )
			CacheUtils.put( "nImportNow", 0 );
		if( CacheUtils.get( "sImportResult" ) == null )
			CacheUtils.put( "sImportResult", "" );
		try {
			String result = "";
			/*Lixun 2017-5-11 正在导入的状态*/
			if ( (Integer)CacheUtils.get( "isImport") > 0 )
				result = "importing";
			else
				result = userInfoService.importDataBeforeThread( url, getUser() );
			/*Lixun*/

			message.setMsg(result);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("导入失败," + e.getMessage());
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/** --毕业生管理导出excel-- **/
	public void exportData() {
		Message message = new Message();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("deptList", getUser().getDepts());
			if (classId != null && !classId.equals("")) {
				map.put("deptId1", classId);
			} else if (gradeId != null && !gradeId.equals("")) {
				map.put("deptId1", gradeId);
			} else if (departId != null && !departId.equals("")) {
				map.put("deptId1", departId);
			} else if (schoolId != null && !schoolId.equals("")) {
				map.put("deptId1", schoolId);
			}
			if (userInfo != null) {
				map.put("telId", userInfo.getTelId());
				map.put("userName", userInfo.getUserName());
				map.put("studentType", userInfo.getStudentType());
				map.put("studentnumber", userInfo.getStudentnumber());
				// map.put("residentialArea", userInfo.getResidentialArea());
				map.put("workUnit", userInfo.getWorkUnit());
				map.put("mailingAddress", userInfo.getMailingAddress());
				map.put("majorId", userInfo.getMajorId());
			}
			String residentialArea = "";
//			if (country != null && country.length() > 0) {
//				residentialArea += country;
//			}
			if (province != null && province.length() > 0) {
				residentialArea += province;
			}
			if (city != null && city.length() > 0) {
				residentialArea += " " + city;
			}
			if (area != null && area.length() > 0) {
				residentialArea += " " + area;
			}
			if (residentialArea.length() > 0) {
				map.put("residentialArea", residentialArea);
			}
			String result = userInfoService.export(map);
			message.setMsg(result);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("导出失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public UserBaseInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserBaseInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public int getIsInput() {
		return isInput;
	}

	public void setIsInput(int isInput) {
		this.isInput = isInput;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
