package com.cy.core.userinfo.action;

import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.entity.UserAlumni;
import com.cy.core.alumni.service.AlumniService;
import com.cy.core.dataMining.entity.DataMining;
import com.cy.core.dept.service.DeptService;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.core.deptInfo.service.DeptInfoService;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.util.PairUtil;
import com.cy.util.UserUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFAnchor;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;

@Namespace("/userInfo")
@Action(value = "userInfoAction")
public class UserInfoAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserInfoAction.class);

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private DeptService deptService ;

	@Autowired
	private DeptInfoService deptInfoService ;

	@Autowired
	private AlumniService alumniService ;



	private UserInfo userInfo;

	private String userId;
	private String accountNum;

	private String deptId;

	private String schoolId;
	private String departId;
	private String gradeId;
	private String classId;

	private String region;

	private String url;

	private int isInput;

	private String country;
	private String province;
	private String city;
	private String area;

	private String resourceAreaProvince;
	private String	resourceAreaCity;



	private String birthday;
	private String entranceTime;
	private String regflag;

	private String userAlumniId;	//用户与分会关联ID
	private String status;			//审核状态

	/*lixun*/
	private String aluid;		//分会id
	private String isAlumni;	//分会查询标记
	private String userDeptId;	//查询部门ID

	private int checkPage;
	private int searchPage;		//来自搜索页面
	private String checkFlag;

	private String focus;		//是否强制迁移

	private String userNames;
	private String studentNumbers;
	private String telIds;

	private String phoneNumber;//用户的手机号

	@Autowired
	private DeptInfoService di;	//查询权限部门


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserDeptId() {
		return userDeptId;
	}

	public void setUserDeptId(String userDeptId) {
		this.userDeptId = userDeptId;
	}

	public String getAluid() {
		return aluid;
	}

	public void setAluid(String aluid) {
		this.aluid = aluid;
	}

	public String getIsAlumni() {
		return isAlumni;
	}

	public void setIsAlumni(String isAlumni) {
		this.isAlumni = isAlumni;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserAlumniId() {
		return userAlumniId;
	}

	public void setUserAlumniId(String userAlumniId) {
		this.userAlumniId = userAlumniId;
	}

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

	public int getCheckPage() {
		return checkPage;
	}

	public void setCheckPage(int checkPage) {
		this.checkPage = checkPage;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getStudentNumbers() {
		return studentNumbers;
	}

	public void setStudentNumbers(String studentNumbers) {
		this.studentNumbers = studentNumbers;
	}

	public String getTelIds() {
		return telIds;
	}

	public void setTelIds(String telIds) {
		this.telIds = telIds;
	}

	public void dataGridByDept()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (sort.equals("userName")) {
			sort = "name_pinyin";
		}
		map.put("sort", sort);
		map.put("order", order);
		if (classId != null && !classId.equals("")) {
			map.put("deptId1", classId);
		} else if (gradeId != null && !gradeId.equals("")) {
			map.put("deptId1", gradeId);
		} else if (departId != null && !departId.equals("")) {
			map.put("deptId1", departId);
		} else if (schoolId != null && !schoolId.equals("")) {
			map.put("deptId1", schoolId);
		}
		map.put("regflag", regflag);
		if (userInfo != null) {
			map.put("userName", userInfo.getUserName());
			map.put("studentType", userInfo.getStudentType());
			map.put("studentnumber", userInfo.getStudentnumber());
			map.put("workUnit", userInfo.getWorkUnit());
			map.put("mailingAddress", userInfo.getMailingAddress());
			map.put("majorId", userInfo.getMajorId());
			map.put("industryType", userInfo.getIndustryType());
		}
		String residentialArea = "";
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
		//lixun 部门
//		map.put( "deptId2", userDeptId );

		Map<String,String> deptMap = Maps.newHashMap() ;


		String academyId = null ;
		PairUtil<String,Alumni> pair = alumniService.getCurrAlumniTypeAndParentCollegeAlumni(String.valueOf(UserUtils.getUser().getDeptId())) ;
		if(pair != null && pair.getTwo() != null) {
			academyId = deptInfoService.getAcademyId(pair.getTwo().getAlumniId());
		}

//		String academyId = deptInfoService.getAcademyId(UserUtils.getUser().getDeptId());

		/*if(StringUtils.isNotBlank(academyId)){
			deptMap.put("currDeptId",academyId) ;
		}
		deptMap.put("deptId",userDeptId) ;
		*/
		map.put("deptId", userDeptId);
		if(StringUtils.isNotBlank(academyId)){
			map.put("currDeptId",academyId) ;
		}
		/*String hasPermissionDeptIds = deptService.getDeptAndBelongDeptIdsByCurrDeptId(deptMap) ;
		map.put("hasPermissionDeptIds",hasPermissionDeptIds) ;*/
		map.put("checkFlag","1");

		//lixun 地区
		if (pair != null && "2".equals(pair.getOne())) {
			String deptId = String.valueOf( getUser().getDeptId() );
			map.put( "alumniId", deptId );	//地区权限
			map.put( "currentAlumni", deptId );
			map.put( "userId", getUser().getUserId() );
		}

		super.writeJson(userInfoService.selectByDeptId(map));
	}

	/*lixun*/

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (sort.equals("userName")) {
			sort = "name_pinyin";
		}
		map.put("sort", sort);
		map.put("order", order);
		if( aluid != null )
			map.put( "deptId", getUser().getDeptId() );	//lixun

		if (classId != null && !classId.equals("")) {
			map.put("deptId1", classId);
		} else if (gradeId != null && !gradeId.equals("")) {
			map.put("deptId1", gradeId);
		} else if (departId != null && !departId.equals("")) {
			map.put("deptId1", departId);
		} else if (schoolId != null && !schoolId.equals("")) {
			map.put("deptId1", schoolId);
		}
		map.put("regflag", regflag);

		if(StringUtils.isNotBlank(userNames)){
			String[] names = userNames.split(",");
			String sqlStr = "(";
			for(int i = 0; i < names.length; i++){
				if(i > 0)
					sqlStr += "OR ";

				sqlStr += "user_name like concat('%','"+names[i].trim()+"','%') ";
			}
			sqlStr += ")";
			map.put("userNames", sqlStr);
		}

		if(StringUtils.isNotBlank(studentNumbers)){
			String[] sNumbers = studentNumbers.split(",");
			String sqlStr = "(";
			for(int i = 0; i < sNumbers.length; i++){
				if(i > 0)
					sqlStr += "OR ";

				sqlStr += "studentnumber like concat('%','"+sNumbers[i].trim()+"','%') ";
			}
			sqlStr += ")";
			map.put("studentNumbers", sqlStr);
		}
		if(StringUtils.isNotBlank(telIds)){
			String[] tIds = telIds.split(",");
			String sqlStr = "(";
			for(int i = 0; i < tIds.length; i++){
				if(i > 0)
					sqlStr += "OR ";
				sqlStr += "tel_id like concat('%','"+tIds[i].trim()+"','%') ";
			}
			sqlStr += ")";
			map.put("telIds", sqlStr);
		}

		if (userInfo != null) {
			map.put("userName", userInfo.getUserName());
			map.put("telId", userInfo.getTelId());
			map.put("studentnumber", userInfo.getStudentnumber());
			map.put("studentType", userInfo.getStudentType());
			// map.put("residentialArea", userInfo.getResidentialArea());
			map.put("workUnit", userInfo.getWorkUnit());
			map.put("mailingAddress", userInfo.getMailingAddress());
			map.put("majorId", userInfo.getMajorId());
			map.put("industryType", userInfo.getIndustryType());
			map.put("sex",userInfo.getSex());
		}

		String residentialArea = "";
		// if (country != null && country.length() > 0) {
		// residentialArea += country;
		// }
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

		if(checkPage == 1){
			map.put("checkPage", "1");
		}

		// 查询页面，地方行业分会要查看一下状态
		if(searchPage == 1){
			PairUtil<String,Alumni> pair = alumniService.getCurrAlumniTypeAndParentCollegeAlumni(String.valueOf(UserUtils.getUser().getDeptId())) ;
			if (pair != null && "2".equals(pair.getOne())) {
				String deptId = String.valueOf( getUser().getDeptId() );
				map.put( "currentAlumni", deptId );	//地区权限
			}
		}
		map.put("checkFlag", checkFlag);

		super.writeJson(userInfoService.selectByDeptId(map));

	}
	/**
	 * 汇总数据
	 */
	public void countEveryThing()
	{
		List<Map> list = new ArrayList<Map>();
		Map<String, Long> map = userInfoService.countEveryThing();
		for(Map.Entry<String,Long> entry : map.entrySet()){
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("Name",entry.getKey());
			map1.put("Value",String.valueOf(entry.getValue()));
			list.add(map1);
		}
		super.writeJson(list);
	}
	/**
	 * 后台校友总汇
	 */
	public void dataGridForAlumni() {
		alumni();
	}

	private void alumni() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);

		/*lixun 增加左树的分会ID,是否加入分会,部门ID*/
		map.put( "aluid", aluid );	//lixun
		map.put( "isAlumni", isAlumni );	//lixun
		if( aluid != null )
			map.put( "deptId", getUser().getDeptId() );	//lixun

		if (classId != null && !classId.equals("")) {
			map.put("deptId1", classId);
		} else if (gradeId != null && !gradeId.equals("")) {
			map.put("deptId1", gradeId);
		} else if (departId != null && !departId.equals("")) {
			map.put("deptId1", departId);
		} else if (schoolId != null && !schoolId.equals("")) {
			map.put("deptId1", schoolId);
		}

		map.put("deptList", getUser().getDepts());
		//System.out.println( getUser().getDepts().toString() );

		if (userInfo != null) {
			map.put("userName", userInfo.getUserName());
			map.put("studentType", userInfo.getStudentType());
			map.put("majorId", userInfo.getMajorId());
			map.put("birthday", userInfo.getBirthday());
			map.put("sex", userInfo.getSex());
			map.put("industryType", userInfo.getIndustryType());
			map.put("entranceTime", userInfo.getEntranceTime());
			map.put("alumniId", userInfo.getAlumniId());
		}

		if (entranceTime != null && entranceTime.length() > 0) {
			map.put("entranceTime", entranceTime);
		}

		if (birthday != null && birthday.length() > 0) {
			map.put("birthday", birthday);
		}

		if (getUser().getFlag() == 1) {
			map.put("alumniId", getUser().getDeptId());
		}

		super.writeJson(userInfoService.selectByDeptIdForAlumni(map));

	}


	/**
	 * 组织成员列表
	 */
	public void alumniMemebers() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);

		map.put( "aluid", userDeptId );

		if (userInfo != null) {
			map.put("userName", userInfo.getUserName());
			map.put("userAlStatus", userInfo.getUserAlStatus());
		}

		if(checkPage == 1){
			map.put("userAlStatus", "10");
		}

		map.put("currAlumniId", getUser().getDeptId());

		super.writeJson(userInfoService.selectByDeptIdFormUserProfile(map));

	}

	public void checkInitiate(){
		Message message = new Message();
		try{
			Map<String, String> map = new HashMap<String, String>();
			map.put("userAlumniId",userAlumniId);
			map.put("status",status);

			userInfoService.checkInitiate(map);

			//

			try{
				UserAlumni userAlumni = userInfoService.selectAlumniStatusById(userAlumniId);
				String userId = userAlumni.getUserId();
				String alumniId =userAlumni.getAlumniId();

				PushUtils.pushAgreeJoinMessage(userId,status,alumniId,userAlumniId);
				PushUtils.PushAlumniJoinAll(userId,alumniId,userAlumniId);
			}catch (Exception e ) {
				e.printStackTrace();
			}
			message.setMsg("操作成功");
			message.setSuccess(true);
		}catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**
	 * 取消认证
	 */
	public void doNotNeedSecurity_cancleAuth(){
		Message message = new Message();

		try{
			userInfoService.cancleAuth(userId, message);
			message.setMsg("已取消认证");
			message.setSuccess(true);
		}catch (Exception e) {
			logger.error(e, e);
			message.setMsg("取消认证失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**
	 * 校友会列表
	 */
	public void dataGridForAlumnix() {
		alumni();
	}

	public void delete() {
		Message message = new Message();
		try {
			boolean canDelete = userInfoService.delete(ids);
			if (canDelete) {
				message.setMsg("删除成功");
				message.setSuccess(true);
			} else {
				message.setMsg("删除失败,删除的记录中存在正式校友");
				message.setSuccess(false);
			}

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
				map.put("userName", userInfo.getUserName());
				map.put("studentType", userInfo.getStudentType());
				map.put("studentnumber", userInfo.getStudentnumber());
				// map.put("residentialArea", userInfo.getResidentialArea());
				map.put("workUnit", userInfo.getWorkUnit());
				map.put("mailingAddress", userInfo.getMailingAddress());
				map.put("majorId", userInfo.getMajorId());
				map.put("industryType", userInfo.getIndustryType());
			}

			String residentialArea = "";
			// if (country != null && country.length() > 0) {
			// residentialArea += country;
			// }
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
			boolean canDelete = userInfoService.deleteAll(map);
			if (canDelete) {
				message.setMsg("删除成功");
				message.setSuccess(true);
			} else {
				message.setMsg("删除失败,删除的记录中存在正式校友");
				message.setSuccess(false);
			}

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**
	 * 邀请加入分会
     */
	public void invite() {
		Message message = new Message();
		try {
			String alumniId = String.valueOf(getUser().getDeptId()) ;
			if(StringUtils.isNotBlank(accountNum) ) {
				Map<String,String> userAlumni = Maps.newHashMap() ;
				userAlumni.put("alumniId",alumniId) ;
				userAlumni.put("accountNum",accountNum) ;

                List<Map<String,String>> list = userProfileService.queryUserAlumni(userAlumni) ;
                if(list != null && !list.isEmpty() ) {
                    userAlumni = list.get(0) ;
                    if (StringUtils.isNotBlank(userAlumni.get("status")) && !"30".equals(userAlumni.get("status"))) {
                        message.init(false ,"该校友正处于申请加入或已加入分会状态中,不能邀请加入",null);
                    } else {
                        alumniService.updateUserAlumni(userAlumni);
                        message.init(true,"邀请成功",null);
                    }
                } else {
                    userAlumni.put("status","5") ;
                    alumniService.saveUserAlumni(userAlumni);
                    message.init(true,"邀请成功",null);
                }

            }

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败" + e.getMessage());
			message.setSuccess(false);
		}
		super.writeJson(message);
	}


	/**
	 * 一键入会
	 */
	public void doNotNeedSecurity_oneKeyJoin(){
		Message message = new Message();
		try {
			String alumniId = String.valueOf(getUser().getDeptId());
			if(StringUtils.isNotBlank(userId) ) {
				message = alumniService.oneKeyJoin(alumniId, userId);
			}else{
				message.setMsg("请选择要操作的校友数据");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败" + e.getMessage());
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**
	 * 踢除成员
	 */
	public void doNotNeedSecurity_kickOutAlumni(){
		Message message = new Message();
		try {
			String alumniId = String.valueOf(getUser().getDeptId());
			if(StringUtils.isNotBlank(userId) ) {
				message = alumniService.kickOutAlumni(alumniId, userId, "");
			}else if(StringUtils.isNotBlank(accountNum)){
				message = alumniService.kickOutAlumni(alumniId, "", accountNum);
			}else{
				message.setMsg("请选择要操作的校友");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败" + e.getMessage());
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


			if (resourceAreaProvince != null && resourceAreaProvince.length() > 0) {
				resourceArea += resourceAreaProvince;
			}
			if (resourceAreaCity != null && resourceAreaCity.length() > 0) {
				resourceArea += " " + resourceAreaCity;
			}

			if (resourceArea != null && resourceArea.length() > 0) {
				userInfo.setResourceArea(resourceArea);
			}
			userInfoService.save(userInfo, getUser(), isInput);
			message.setMsg("新增成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败" + e.getMessage());
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void saveFromBase() {
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
				map.put("residentialArea", userInfo.getResidentialArea());
				map.put("workUnit", userInfo.getWorkUnit());
				map.put("mailingAddress", userInfo.getMailingAddress());
				map.put("majorId", userInfo.getMajorId());
			}
			userInfoService.saveFromBase(map);
			message.setMsg("导入成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("导入失败");
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

			if (resourceAreaProvince != null && resourceAreaProvince.length() > 0) {
				resourceArea += resourceAreaProvince;
			}
			if (resourceAreaCity != null && resourceAreaCity.length() > 0) {
				resourceArea += " " + resourceAreaCity;
			}

			if (resourceArea != null && resourceArea.length() > 0) {
				userInfo.setResourceArea(resourceArea);
			}



			userInfoService.update(userInfo, getUser(), isInput);
			message.setMsg("修改成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败" + e.getMessage().replace("java.lang.RuntimeException: ", ""));
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void doNotNeedSecurity_selectUserByTelePhone(){
		Message message = new Message();
		try {

			if(phoneNumber != null && !phoneNumber.equals("")){
				List<UserInfo> userInfoList = userInfoService.selectUserByTelePhone(phoneNumber);
				String str="";
				if(userInfoList != null && userInfoList.size() > 0){
					for(UserInfo users:userInfoList){
						String schoolName= users.getSchoolName();
						String departName= users.getDepartName();
						String gradeName= users.getGradeName();
						String className= users.getClassName();
						String userName = users.getUserName();
						str+="[" + schoolName  + departName  + gradeName +className + "的" + userName + "]" + "、";
					}
					str = str.substring(0,str.length()-1);
					message.setMsg("此手机号已经被"+ str + "绑定过" +"," + "请先点击取消，仔细核实该用户信息，如无误，请单击确定");
					message.setSuccess(true);
			}

			}else{
				message.setMsg("直接新增");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败" + e.getMessage().replace("java.lang.RuntimeException: ", ""));
			message.setSuccess(false);
		}
		super.writeJson(message);




	}




	public void updateIdea() {
		Message message = new Message();
		try {
			UserInfo userInfoTmp = userInfoService.selectByUserId(userInfo.getUserId());

			if(StringUtils.isNotBlank(userInfoTmp.getAccountNum()) && "1".equals(userInfo.getCheckFlag())){
				boolean block = userInfoService.checkAccountInClass(userInfoTmp);

				if(block){
					message.setMsg("当前校友数据对应的注册帐号在该班级中已经有正式认证纪录，请点击不通过");
					message.setSuccess(false);
					super.writeJson(message);
					return;
				}
			}

			userInfoService.updateIdea(userInfo);
			message.setMsg("操作成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}


	/**
	 * 批量审核校友
	 */
	public void doNotNeedSecurity_updateIdea(){
		Message message = new Message();
		try{
			if (StringUtils.isNotBlank(ids)) {
				String[] ids = getIds().split(",");

				//先验证一下所有数据是否有问题
				for(String id: ids){
					UserInfo userInfoTmp = userInfoService.selectByUserId(id);
					if(StringUtils.isNotBlank(userInfoTmp.getAccountNum()) && ("1".equals(checkFlag))){
						boolean block = userInfoService.checkAccountInClass(userInfoTmp);
						if(block){
							message.init(false,userInfoTmp.getUserName()+"校友,对应的注册帐号在该班级中已经有正式认证纪录，请点击不通过", null);
							super.writeJson(message);
							return;
						}
					}
				}
				//然后再作处理
				for(String id: ids){
					UserInfo checkUser = new UserInfo();
					checkUser.setUserId(id);
					checkUser.setCheckFlag(String.valueOf(checkFlag));
					userInfoService.updateIdea(checkUser);
				}
				message.setMsg("操作成功");
				message.setSuccess(true);
			}else{
				message.setMsg("请选择要审核的校友");
				message.setSuccess(false);
			}
		}catch (Exception e){
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void doNotNeedSecurity_getUserInfoByUserId() {
		UserInfo user = userInfoService.selectByUserId(userInfo.getUserId()) ;
		user.setOldPhoneNumber(user.getTelId());
		List<DataMining> potentialUsers = userInfoService.getPotentialUser(userInfo.getUserId());  //jiangling, 得到被挖掘用户信息
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>> ();
		for(DataMining dm:potentialUsers) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("miningUserName", dm.getMiningUserName());
			map.put("phone",dm.getPhone());
			map.put("createDate",dm.getCreateDate());
			list.add(map);
		}
		user.setDataMiningList(list);
		super.writeJson(user);
	}

	public void getUserInfoByUserId() {
		UserInfo user = userInfoService.selectByUserId(userInfo.getUserId());
		List<DataMining> potentialUsers = userInfoService.getPotentialUser(userInfo.getUserId());  //jiangling, 得到被挖掘用户信息
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>> ();
		for(DataMining dm:potentialUsers) {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("miningUserName", dm.getMiningUserName());
			map.put("phone",dm.getPhone());
			map.put("createDate",dm.getCreateDate());
			list.add(map);
		}
		user.setDataMiningList(list);
		super.writeJson(user);
	}

	public void getUserInfoByUserIdForAlumni() {
		super.writeJson(userInfoService.getUserInfoByUserIdForAlumni(userInfo.getUserId()));
	}

	public void getUserInfoByUserIdForAlumnix() {
		super.writeJson(userInfoService.getUserInfoByUserIdForAlumni(userInfo.getUserId()));
	}

	public void doNotNeedSecurity_getAllUserList() {
		super.writeJson(userInfoService.selectAllUserList());
	}

	/** --通过ajax查询所有手机号不为空的用户，并且分页-- **/
	public void doNotNeedSecurity_getAllUserList_dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNotNull", true);
		map.put("emailNotNull", false);

		map.put("deptList", getUser().getDepts());

		if(getUser().getAlumni()!=null&&getUser().getAlumni().getAlumniId()!=0){
			map.put("alumniId", getUser().getAlumni().getAlumniId());
		}

		if(userInfo != null) {
			map.put("userIds", userInfo.getUserIds()) ;
		}

		super.writeJson(userInfoService.selectUserToGetTelPage(map));
	}


	/** --通过ajax查询所有手机号不为空的用户，并且分页【旧】-- **/
	public void doNotNeedSecurity_getAllUserList_dataGrid_Old() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNotNull", true);
		map.put("emailNotNull", false);
		map.put("deptList", getUser().getDepts());

		if(getUser().getAlumni()!=null&&getUser().getAlumni().getAlumniId()!=0){
			map.put("alumniId", getUser().getAlumni().getAlumniId());
		}
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
			map.put("industryType", userInfo.getIndustryType());
		}
		String residentialArea = "";
		// if (country != null && country.length() > 0) {
		// residentialArea += country;
		// }
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
		super.writeJson(userInfoService.selectUserToGetTelPage(map));
	}

	/** --通过ajax查询所有邮箱号不为空的用户，并且分页-- **/
	public void doNotNeedSecurity_getAllUserEmailList_dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		// phoneNotNull==true
		map.put("phoneNotNull", false);
		map.put("emailNotNull", true);

		map.put("deptList", getUser().getDepts());

		if(userInfo != null) {
			map.put("userIds", userInfo.getUserIds()) ;
		}
		super.writeJson(userInfoService.selectUserToGetTelPage(map));
	}

	/** --通过ajax查询所有邮箱号不为空的用户，并且分页-- **/
	public void doNotNeedSecurity_getAllUserEmailList_dataGrid_old() {
		Map<String, Object> map = new HashMap<String, Object>();
		// phoneNotNull==true
		map.put("phoneNotNull", false);
		map.put("emailNotNull", true);

		map.put("deptList", getUser().getDepts());
		if(getUser().getAlumni()!=null&&getUser().getAlumni().getAlumniId()!=0){
			map.put("alumniId", getUser().getAlumni().getAlumniId());
		}
		if (classId != null && !classId.equals("")) {
			map.put("deptId1", classId);
		} else if (gradeId != null && !gradeId.equals("")) {
			map.put("deptId1", gradeId);
		} else if (departId != null && !departId.equals("")) {
			map.put("deptId1", departId);
		} else if (schoolId != null && !schoolId.equals("")) {
			map.put("deptId1", schoolId);
		}

		map.put("regflag", regflag);

		if (userInfo != null) {
			map.put("telId", userInfo.getTelId());
			map.put("userName", userInfo.getUserName());
			map.put("studentType", userInfo.getStudentType());
			map.put("studentnumber", userInfo.getStudentnumber());
			// map.put("residentialArea", userInfo.getResidentialArea());
			map.put("workUnit", userInfo.getWorkUnit());
			map.put("mailingAddress", userInfo.getMailingAddress());
			map.put("majorId", userInfo.getMajorId());
			map.put("industryType", userInfo.getIndustryType());
		}

		String residentialArea = "";
		// if (country != null && country.length() > 0) {
		// residentialArea += country;
		// }
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
		super.writeJson(userInfoService.selectUserToGetTelPage(map));
	}


	public void doNotNeedSecurity_dataGridFor() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (userInfo != null) {
			map.put("telId", userInfo.getTelId());
			map.put("userName", userInfo.getUserName());
			map.put("userId",userInfo.getUserId());
			map.put("checkFlag",userInfo.getCheckFlag());
		}
		super.writeJson(userInfoService.dataGridFor(map));
	}

	public void doNotNeedSessionAndSecurity_isShowInfoData() {

	}

	public void doNotNeedSecurity_sameNameGrid(){
		UserInfo userInfo = userInfoService.selectByUserId(userId);
		DataGrid<UserInfo> dataGrid = new DataGrid<>();
		if(userInfo != null){
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			map.put("rows", rows);
			if(StringUtils.isNotBlank(deptId)){
				map.put("deptId", deptId);
			}
			if(StringUtils.isNotBlank(userInfo.getUserName())){
				map.put("userName", userInfo.getUserName());
				if(StringUtils.isNotBlank(userInfo.getSex())){
					map.put("sex", userInfo.getSex());
				}
				dataGrid = userInfoService.selectSameNameList(map);
			}else{
				dataGrid.setTotal(0);
			}
		}else{
			dataGrid.setTotal(0);
		}
		super.writeJson(dataGrid);
	}

	/** --外部查询用户信息-- **/
	public void doNotNeedSessionAndSecurity_dataGridFor() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (userInfo != null) {
			map.put("telId", userInfo.getTelId());
			map.put("userName", userInfo.getUserName());
		}

		DataGrid<UserInfo> data = userInfoService.dataGridFor(map);
		super.writeJson(data);
	}

	public void importData() {
		Message message = new Message();
		try {
			String result = userInfoService.importData(url, getUser());
			message.setMsg(result);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("导入失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/** --导出校友-- **/
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
				map.put("accountNum", userInfo.getAccountNum());
			}

			String residentialArea = "";
			// if (country != null && country.length() > 0) {
			// residentialArea += country;
			// }
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

	/**
	 * 方法usingStatistics 的功能描述：使用统计
	 * @createAuthor niu
	 * @createDate 2017-04-12 14:24:37
	 * @param
	 * @return void
	 * @throw
	 *
	 */
	public void usingStatistics(){
		Message message = new Message();
		Map<String,Object> map = Maps.newHashMap();
		map = userInfoService.usingStatistics();
		message.setObj(map);
		message.setSuccess(true);
		message.setMsg("成功");
		super.writeJson(message);
	}

	/**
	 * 迁移到其他班级
	 */
	public void doNotNeedSecurity_moveToOtherClass(){
		Message message = new Message();
		try{
			if(deptId.equals(userId.substring(0, 16))){
				message.init(false,"不要选择校友已在的班级", null);
			}else{
				Map<String, String> map = new HashMap<>();
				map.put("focus", focus);
				map.put("userId", userId);
				map.put("newDeptId", deptId);
				Map<String, Object> resultMap = userInfoService.moveData(map);
				message.init(true, "消息", resultMap);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.init(false,"迁移失败", null);
		}
		super.writeJson(message);
	}


	/**
	 * 查询可用于一键认证的校友数据
	 */
	public void doNotNeedSecurity_dataGridForOneKey(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (sort.equals("userName")) {
			sort = "name_pinyin";
		}
		map.put("sort", sort);
		map.put("order", order);
		map.put("regflag", "0");
		map.put("checkFlag","1");
		map.put("hasPhone","1");
		/*map.put( "deptId", getUser().getDeptId() );
		map.put( "deptId1", deptId );*/

		if(StringUtils.isNotBlank(userNames)){
			String[] names = userNames.split(",");
			String sqlStr = "(";
			for(int i = 0; i < names.length; i++){
				if(i > 0)
					sqlStr += "OR ";
				sqlStr += "user_name like concat('%','"+names[i].trim()+"','%') ";
			}
			sqlStr += ")";
			map.put("userNames", sqlStr);
		}

		if(StringUtils.isNotBlank(studentNumbers)){
			String[] sNumbers = studentNumbers.split(",");
			String sqlStr = "(";
			for(int i = 0; i < sNumbers.length; i++){
				if(i > 0)
					sqlStr += "OR ";

				sqlStr += "studentnumber like concat('%','"+sNumbers[i].trim()+"','%') ";
			}
			sqlStr += ")";
			map.put("studentNumbers", sqlStr);
		}
		if(StringUtils.isNotBlank(telIds)){
			String[] tIds = telIds.split(",");
			String sqlStr = "(";
			for(int i = 0; i < tIds.length; i++){
				if(i > 0)
					sqlStr += "OR ";
				sqlStr += "tel_id like concat('%','"+tIds[i].trim()+"','%') ";
			}
			sqlStr += ")";
			map.put("telIds", sqlStr);
		}
		super.writeJson(userInfoService.selectByDeptId(map));
	}

	/**
	 * 一键认证
	 */
	public void doNotNeedSecurity_oneKeyAuth(){
		Message message = new Message();
		try{
			if(StringUtils.isNotBlank(userId)){
				message = userInfoService.oneKeyAuth(userId);
			}else{
				message.init(false,"请选择要操作的校友", null);
			}
		}catch (Exception e){
			logger.error(e, e);
			message.init(false,"操作失败", null);
		}
		super.writeJson(message);
	}

	/**
	 * 批量一键认证
	 * @return
	 */
	public void doNotNeedSecurity_mOneKeyAuth(){
		Message message = new Message();
		try{
			if(StringUtils.isNotBlank(ids)){
				String[] userIds = ids.split(",");
				Boolean success = true;
				String msg = "";
				int errorNum = 0;
				for(String uId:userIds){
					Message tmp = userInfoService.oneKeyAuth(uId);
					if(!tmp.isSuccess()){
                        success = false;
					    if(errorNum > 0)
					        msg += ",";
					    msg += tmp.getMsg();
					    errorNum++;
                    }
				}

				if(!success){
                    msg = "("+errorNum+"个错误)"+msg;
                    message.init(false,msg, null);
                }else{
                    message.init(true,"操作成功", null);
                }
			}else{
				message.init(false,"请选择要操作的校友", null);
			}
		}catch (Exception e){
			logger.error(e, e);
			message.init(false,"操作失败", null);
		}
		super.writeJson(message);
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEntranceTime() {
		return entranceTime;
	}

	public void setEntranceTime(String entranceTime) {
		this.entranceTime = entranceTime;
	}

	public String getRegflag() {
		return regflag;
	}

	public void setRegflag(String regflag) {
		this.regflag = regflag;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

	public String getResourceAreaProvince() {
		return resourceAreaProvince;
	}

	public void setResourceAreaProvince(String resourceAreaProvince) {
		this.resourceAreaProvince = resourceAreaProvince;
	}

	public String getResourceAreaCity() {
		return resourceAreaCity;
	}

	public void setResourceAreaCity(String resourceAreaCity) {
		this.resourceAreaCity = resourceAreaCity;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getSearchPage() {
		return searchPage;
	}

	public void setSearchPage(int searchPage) {
		this.searchPage = searchPage;
	}
}
