package com.cy.core.userinfo.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.alumni.entity.UserAlumni;
import com.cy.core.analysis.action.AnalysisAction;
import com.cy.core.chatDeptGroup.dao.ChatDeptGroupMapper;
import com.cy.core.chatDeptGroup.entity.ChatDeptGroup;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.clientrelease.service.ClientService;
import com.cy.core.cloudEnterprise.entity.CloudEntrepreneur;
import com.cy.core.dataMining.dao.DataMiningMapper;
import com.cy.core.dataMining.entity.DataMining;
import com.cy.core.dept.service.DeptService;
import com.cy.core.deptInfo.dao.DeptInfoMapper;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.core.mobileLocal.service.MobileLocalService;
import com.cy.core.news.entity.News;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.share.entity.Share;
import com.cy.core.share.service.ShareService;
import com.cy.core.userProfile.dao.GroupInfoMapper;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.core.weiXin.service.WeiXinUserService;
import com.cy.mobileInterface.authenticated.service.AuthenticatedService;
import com.cy.mobileInterface.group.entity.GroupInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JSONArray;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dicttype.entity.DictType;
import com.cy.core.major.dao.MajorMapper;
import com.cy.core.major.entity.Major;
import com.cy.core.major.entity.MajorDept;
import com.cy.core.mobileLocal.dao.MobileLocalMapper;
import com.cy.core.mobileLocal.dao.MobileScratchMapper;
import com.cy.core.mobileLocal.entity.MobileLocal;
import com.cy.core.mobileLocal.entity.MobileScratch;
import com.cy.core.sms.dao.MsgSendMapper;
import com.cy.core.sms.entity.MsgSend;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userbaseinfo.dao.UserBaseInfoMapper;
import com.cy.core.userbaseinfo.entity.UserBaseInfo;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.system.ClassPathResource;
import com.cy.system.ExcelUtil;
import com.cy.system.GetDictionaryInfo;
import com.cy.system.Global;
import com.cy.system.IdUtil;
import com.cy.system.PinYinUtils;
import com.cy.system.UUID;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;

	@Autowired
	private MajorMapper majorMapper;

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private DeptService deptService ;

	@Autowired
	private AlumniMapper alumniMapper;

	@Autowired
	private MobileLocalMapper mobileLocalMapper;

	@Autowired
	private MobileScratchMapper mobileScratchMapper;

	@Autowired
	private MsgSendMapper msgSendMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;

	//add by jiangling
	@Autowired
	private DataMiningMapper dataMiningMapper;

	@Autowired
	private GroupInfoMapper groupInfoMapper;

	@Autowired
	private ChatGroupService chatGroupService;

	@Autowired
	private ChatDeptGroupMapper chatDeptGroupMapper ;

	@Autowired
	private MobileLocalService mobileLocalService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ShareService shareService;

	@Autowired
	private DeptInfoMapper deptInfoMapper;

	@Autowired
	private AuthenticatedService authenticatedService;

	@Autowired
	private WeiXinUserService weiXinUserService;


	/** start liuzhen **/

	/**
	 * 设置班级管理员
	 * @param map
	 */
	public void updateClassAdmin(Map<String,String> map) {
		if(map != null && (StringUtils.isNotBlank(map.get("userId")) || StringUtils.isNotBlank(map.get("userIds")))) {
			userInfoMapper.updateClassAdmin(map);
			userBaseInfoMapper.updateClassAdmin(map);

		}
	}

	/** end liuzhen **/

	public DataGrid<UserInfo> selectByDeptId(Map<String, Object> map) {
		DataGrid<UserInfo> dataGrid = new DataGrid<UserInfo>();
		/*lixun 2016-8-3 总会和学院分会展示所有的人*/

		long total = userInfoMapper.countByDeptIdNew(map);

		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<UserInfo> list = userInfoMapper.selectByDeptIdNew(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public DataGrid<UserProfile> selectByDeptIdFormUserProfile(Map<String, Object> map){
		DataGrid<UserProfile> dataGrid = new DataGrid<UserProfile>();
		long total = userProfileMapper.countByDeptFormUserProfile(map);
		dataGrid.setTotal(total);
		
		
		int pg=Integer.valueOf(map.get("page").toString())-1;
		int rws=Integer.valueOf(map.get("rows").toString());
		
		int start = pg*rws;		
		
		map.put("start", start);
		map.put("rows", rws);
		
		List<UserProfile> list = userProfileMapper.selectByDeptFormUserProfile(map);
		for(UserProfile up:list){
			if(up.getClasses()!=null && up.getClasses()!= ""){
				up.setClasses(up.getClasses().replaceAll("_", "<br>"));
			}
		}
		
		dataGrid.setRows(list);
		return dataGrid;
		
	}

    /*APP调用,按分会组织查询校友*/
	public DataGrid<UserProfile> selectByDeptFormAlumni(Map<String, Object> map){
		DataGrid<UserProfile> dataGrid = new DataGrid<UserProfile>();
		long total = userProfileMapper.countByDeptFormAlumni(map);
		System.out.println("total="+total);
		dataGrid.setTotal(total);		
		
		
		
		int pg=Integer.valueOf(map.get("page").toString())-1;
		int rws=Integer.valueOf(map.get("rows").toString());
		
		int start = pg*rws;		
		
		map.put("start", start);
		map.put("rows", rws);
		
		List<UserProfile> list = userProfileMapper.selectByDeptFormAlumni(map);
		for(UserProfile up:list){
			if(up.getClasses()!=null && up.getClasses()!= ""){
				up.setClasses(up.getClasses().replaceAll("_", "<br>"));
			}
			if(up.getGroupName()!=null&&up.getGroupName()!=""&&up.getGroupName().indexOf(",")>0){
				up.setGroupName(up.getGroupName().substring(0, up.getGroupName().indexOf(",")));
			}

		}
		
		dataGrid.setRows(list);
		return dataGrid;
		
	}
	
	public void checkInitiate(Map<String, String> map){
		userProfileMapper.checkInitiate(map);
	}

	//单人处理
	public int updateProfileStatus(Map<String, Object> map){
		return userProfileMapper.updatealumnistatus(map);
	}
	
	//批量处理
	public int updateProfileStatusBatch(Map<String, Object> map){
		return userProfileMapper.updatealumnistatusbatch(map);
	}
		
	public DataGrid<UserInfo> selectByDeptIdForAlumni(Map<String, Object> map) {
		DataGrid<UserInfo> dataGrid = new DataGrid<UserInfo>();
		String sMain = (String)map.get("aluid");
		long mainType = 0;
		if( sMain != null )
			mainType = userInfoMapper.selectMainInAlumni( Long.valueOf( sMain ) );
		if( mainType == 99 || mainType == 1 )	//总会和校友分会
			map.put( "zonghui", String.valueOf( mainType ) );
		/*lixun*/
		long total = userInfoMapper.countByDeptIdForAlumni(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<UserInfo> list = userInfoMapper.selectByDeptIdForAlumni(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public boolean delete(String ids) {
		try {
			boolean canDelete = true;
			String[] array = ids.split(",");
			List<String> list = new ArrayList<String>();
			for (String id : array) {
				list.add(id);
			}
			List<UserInfo> userInfoList = userInfoMapper.selectByIds(list);
			for (UserInfo info : userInfoList) {
				if (info.getCheckFlag() != null && "1".equals(info.getCheckFlag())) {
					canDelete = false;
					break;
				}
			}
			if (canDelete) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String id : array) {
					UserProfile userProfile = userProfileMapper.selectByBaseInfoId(id);
					if (userProfile != null) {
//						String baseInfoId = "";
//						String groupName = "";
//						if (userProfile.getBaseInfoId() != null && userProfile.getBaseInfoId().length() > 0) {
//							String[] idArray = userProfile.getBaseInfoId().split(",");
//							for (String baseId : idArray) {
//								if (!baseId.equals(id)) {
//									baseInfoId += baseId + ",";
//								}
//							}
//							if (baseInfoId.length() > 0) {
//								baseInfoId = baseInfoId.substring(0, baseInfoId.length() - 1);
//							}
//						}
//						if (userProfile.getGroupName() != null && userProfile.getGroupName().length() > 0) {
//							String[] groupArray = userProfile.getGroupName().split(",");
//							for (String group : groupArray) {
//								if (!group.equals(id.substring(0, 16))) {
//									groupName += group + ",";
//								}
//							}
//							if (groupName.length() > 0) {
//								groupName = groupName.substring(0, groupName.length() - 1);
//							}
//						}
//						map.put("baseInfoId", baseInfoId);
//						map.put("accountNum", userProfile.getAccountNum());
//						map.put("groupName", groupName);
//						userProfileMapper.clearBaseInfoId(map);

						Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
						if(baseMap == null) {
							baseMap = Maps.newHashMap() ;
						}
						baseMap.put("accountNum", userProfile.getAccountNum()) ;
						userProfileMapper.clearBaseInfoId(baseMap);
					}
				}
				userInfoMapper.delete(list);
			}
			return canDelete;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void save(UserInfo userInfo, User user, int isInput) {
		// 更新归属地
		if(StringUtils.isNotBlank(userInfo.getTelId()) && userInfo.getTelId().length() == 11){
			MobileLocal mobileLocal = mobileLocalMapper.selectByMobileNumber(userInfo.getTelId().substring(0, 7));
			if (mobileLocal != null) {
				userInfo.setMobileLocal(mobileLocal.getMobileArea());
			}
		}
		try {
			if (isInput == 0 && userInfo.getClassId() != null && !userInfo.getClassId().equals("")) {
				List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(userInfo.getClassId());
				List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(userInfo.getClassId());
				Set<String> set = new HashSet<String>();
				for (UserBaseInfo UserBaseInfo2 : list) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				for (UserInfo UserBaseInfo2 : ulist) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				String uId = IdUtil.getUserId(set);
				String userId = userInfo.getClassId() + uId;
				userInfo.setUserId(userId);
				userInfo.setNamePinyin(PinYinUtils.getQuanPin(userInfo.getUserName()));
				userInfo.setCreateTime(new Date());
				userInfo.setCheckFlag(StringUtils.isNotBlank(userInfo.getCheckFlag()) ? userInfo.getCheckFlag() : "0");
				userInfoMapper.save(userInfo);
			} else if (isInput == 1 && userInfo.getSchoolName() != null && !userInfo.getSchoolName().equals("") && userInfo.getDepartName() != null
					&& !userInfo.getDepartName().equals("") && userInfo.getGradeName() != null && !userInfo.getGradeName().equals("")
					&& userInfo.getClassName() != null && !userInfo.getClassName().equals("")) {
				Map<String, Object> map = new HashMap<String, Object>();
				Dept depart = null;
				if (user.getDepts() == null || user.getDepts().size() == 0) {
					// 查找学校
					map.put("parentId", "0");
					map.put("deptName", userInfo.getSchoolName());

					// 学校
					Dept school = deptMapper.selectByNameAndParentId(map);
					if (school == null) {
						// 取所有学校名
						school = new Dept();
						List<Dept> list = deptMapper.getSchool();
						Set<String> set = new HashSet<String>();
						for (Dept dept2 : list) {
							set.add(dept2.getDeptId().substring(5, 6));
						}
						String extend = IdUtil.getExtend(set);
						if (extend.equals("")) {
							throw new RuntimeException("学校曾用名数据量已超过最大限制(35个)");
						}
						String deptId = list.get(0).getDeptId().substring(0, 5) + extend;
						school.setDeptId(deptId);
						school.setCreateTime(new Date());
						school.setFullName(userInfo.getSchoolName());
						school.setDeptName(userInfo.getSchoolName());
						school.setLevel(4);
						school.setParentId("0");
						school.setStatus("20");
						deptMapper.insert(school);
					}

					// 院系
					map.put("parentId", school.getDeptId());
					map.put("deptName", userInfo.getDepartName());
					depart = deptMapper.selectByNameAndParentId(map);
					if (depart == null) {
						depart = new Dept();
						List<Dept> list = deptMapper.selectByDeptId(school.getDeptId());
						Set<String> set = new HashSet<String>();
						for (Dept dept2 : list) {
							if (dept2.getDeptId() != null && dept2.getDeptId().length() == 10) {
								set.add(dept2.getDeptId().substring(6, 9));
							}
						}
						String departId = IdUtil.getDepart(set);
						if (departId.equals("")) {
							throw new RuntimeException("院系已超过最大限制(999个)");
						}
						String deptId = school.getDeptId() + departId + "0";
						depart.setDeptId(deptId);
						depart.setCreateTime(new Date());
						depart.setFullName(school.getFullName() + "," + userInfo.getDepartName());
						depart.setDeptName(userInfo.getDepartName());
						depart.setLevel(4);
						depart.setParentId(school.getDeptId());
						depart.setStatus("20");
						deptMapper.insert(depart);
					}
				} else {
					List<Dept> hasDeptList = user.getDepts();
					Set<String> schoolSet = new HashSet<String>();
					Set<String> departSet = new HashSet<String>();
					for (Dept dept : hasDeptList) {
						if (dept.getDeptId().length() == 6) {
							schoolSet.add(dept.getDeptId());
						} else {
							departSet.add(dept.getDeptId());
						}
					}

					map.put("parentId", "0");
					map.put("deptName", userInfo.getSchoolName());

					// 学校
					Dept school = deptMapper.selectByNameAndParentId(map);
					if (school == null) {
						throw new RuntimeException("无权限添加学校曾用名");
					}

					// 院系
					map.put("parentId", school.getDeptId());
					map.put("deptName", userInfo.getDepartName());
					depart = deptMapper.selectByNameAndParentId(map);
					if (departSet.size() == 0) {
						if (schoolSet.size() > 0 && !schoolSet.contains(school.getDeptId())) {
							throw new RuntimeException("当前用户无权限添加学校曾用名");
						}
						// 院系
						map.put("parentId", school.getDeptId());
						map.put("deptName", userInfo.getDepartName());
						depart = deptMapper.selectByNameAndParentId(map);
						if (depart == null) {
							depart = new Dept();
							List<Dept> list = deptMapper.selectByDeptId(school.getDeptId());
							Set<String> set = new HashSet<String>();
							for (Dept dept2 : list) {
								if (dept2.getDeptId() != null && dept2.getDeptId().length() == 10) {
									set.add(dept2.getDeptId().substring(6, 9));
								}
							}
							String departId = IdUtil.getDepart(set);
							if (departId.equals("")) {
								throw new RuntimeException("院系数据异常，院系已超过1000个");
							}
							String deptId = school.getDeptId() + departId + "0";
							depart.setDeptId(deptId);
							depart.setCreateTime(new Date());
							depart.setFullName(school.getFullName() + "," + userInfo.getDepartName());
							depart.setDeptName(userInfo.getDepartName());
							depart.setLevel(4);
							depart.setParentId(school.getDeptId());
							depart.setStatus("20");
							deptMapper.insert(depart);
						}
					} else {
						map.put("parentId", school.getDeptId());
						map.put("deptName", userInfo.getDepartName());
						depart = deptMapper.selectByNameAndParentId(map);
						if (depart == null) {
							throw new RuntimeException("院系名称找不到，当前用户无权限添加院系名称");
						}
						if (departSet.size() > 0 && !departSet.contains(depart.getDeptId())) {
							throw new RuntimeException("无权限向该院系内添加数据");
						}
					}
				}
				// 专业
				Major major = majorMapper.getByName(userInfo.getMajorName());
				if (major == null) {
					major = new Major();
					major.setMajorName(userInfo.getMajorName());
					majorMapper.addMajor(major);
				}
				// 获取major和dept的关系
				Map<String, Object> majorAndDeptMap = new HashMap<String, Object>();
				majorAndDeptMap.put("deptId", depart.getDeptId());
				majorAndDeptMap.put("majorId", major.getMajorId());
				List<MajorDept> majorDepts = majorMapper.getMajorAndDept(majorAndDeptMap);
				if (majorDepts != null && majorDepts.size() > 0) {
					// 此处代码是为了去掉中间表的冗余数据，之前由于没有考虑去重，导致中间表大量重复数据
					if (majorDepts.size() > 1) {
						List<String> deptList = new ArrayList<String>();
						deptList.add(depart.getDeptId());
						majorMapper.deleteMajorByDeptId(deptList);
						majorMapper.addMajorDept(majorAndDeptMap);
					}
				} else {
					majorMapper.addMajorDept(majorAndDeptMap);
				}

				// 年级
				map.put("parentId", depart.getDeptId());
				map.put("deptName", userInfo.getGradeName() + "级");
				Dept grade = deptMapper.selectByNameAndParentId(map);
				if (grade == null) {
					grade = new Dept();
					String deptId = depart.getDeptId() + userInfo.getGradeName();
					grade.setDeptId(deptId);
					grade.setCreateTime(new Date());
					grade.setFullName(depart.getFullName() + "," + userInfo.getGradeName() + "级");
					grade.setDeptName(userInfo.getGradeName() + "级");
					grade.setLevel(4);
					grade.setParentId(depart.getDeptId());
					grade.setStatus("20");
					deptMapper.insert(grade);
				}

				// 班级
				map.put("parentId", grade.getDeptId());
				map.put("deptName", userInfo.getClassName());
				Dept class1 = deptMapper.selectByNameAndParentId(map);
				if (class1 == null) {
					class1 = new Dept();
					List<Dept> list = deptMapper.selectByDeptId(grade.getDeptId());
					Set<String> set = new HashSet<String>();
					for (Dept dept2 : list) {
						if (dept2.getDeptId() != null && dept2.getDeptId().length() == 16) {
							set.add(dept2.getDeptId().substring(14, 16));
						}
					}
					String classId = IdUtil.getClassId(set);
					if (classId.equals("")) {
						throw new RuntimeException("班级数据异常，同年级班级已超过100个");
					}
					String deptId = grade.getDeptId() + classId;
					class1.setDeptId(deptId);
					class1.setCreateTime(new Date());
					String fullName = grade.getFullName() + "," + userInfo.getClassName();
					class1.setFullName(fullName);
					class1.setLevel(4);
					class1.setParentId(grade.getDeptId());
					class1.setDeptName(userInfo.getClassName());
					class1.setStatus("20");
					deptMapper.insert(class1);
				}

				List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(class1.getDeptId());
				List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(class1.getDeptId());
				Set<String> set = new HashSet<String>();
				for (UserBaseInfo UserBaseInfo2 : list) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				for (UserInfo UserBaseInfo2 : ulist) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				String uId = IdUtil.getUserId(set);
				if (uId.length() == 0) {
					throw new RuntimeException("同一班级人数超过最大限制(999人)");
				}
				String userId = class1.getDeptId() + uId;
				userInfo.setUserId(userId);
				userInfo.setMajorId(major.getMajorId());
				userInfo.setNamePinyin(PinYinUtils.getQuanPin(userInfo.getUserName()));
				userInfo.setCreateTime(new Date());
				userInfo.setCheckFlag(StringUtils.isNotBlank(userInfo.getCheckFlag()) ? userInfo.getCheckFlag() : "0");
				userInfoMapper.save(userInfo);
			}
		} catch (Exception e) {
			logger.error(e, e);
			throw new RuntimeException(e);
		}
	}

	public UserInfo selectByUserId(String userId) {
		UserInfo userInfo = userInfoMapper.selectByUserId(userId);
		if (userInfo !=null ){
			if(StringUtils.isNotBlank(userInfo.getTelId())&& userInfo.getTelId().length()>7){
				MobileLocal mobileLocal = mobileLocalService.selectByMobileNumber(userInfo.getTelId().substring(0,7));
				if (mobileLocal !=null ){
					userInfo.setAttributionInquiry(mobileLocal.getMobileArea());
				}
			}
			if(StringUtils.isNotBlank(userInfo.getUserName())){
				Map<String, Object> map = new HashMap<>();
				map.put("userName", userInfo.getUserName());
				if(StringUtils.isNotBlank(userInfo.getSex())){
					map.put("sex", userInfo.getSex());
				}
				userInfo.setSameNameCount(userInfoMapper.countSameName(map));
			}
			if(userInfo.getAccountNum() != null && userInfo.getAccountNum().length() > 0 ){
				UserProfile userProfile = userProfileMapper.selectByAccountNum(userInfo.getAccountNum());
				if(userProfile == null){
					userInfo.setAccountNum("0");
					userInfoMapper.update(userInfo);
					userInfo.setAccountNum("");
				}
			}
		}
		return userInfo;
	}

	@SuppressWarnings("unchecked")
	public String importData(String url, User user) {
		int rownumber = 0;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;
			url = savePath + url.replace(saveUrl, "");
			File file = new File(url);
			List<Object[]> list = ExcelUtil.parseExcel(file);
			if (list.size() > 50000) {
				throw new RuntimeException("一次只能导入2万条数据");
			}
			List<Object[]> errorList = new ArrayList<Object[]>();
			// 整理数据开始
			List<Dept> allDeptList = deptMapper.selectAll2();
			Map<String, Dept> deptNameAndParentIdMap = new ConcurrentHashMap<String, Dept>();
			Map<String, Set<String>> ParentIdAndExtendIdMap = new ConcurrentHashMap<String, Set<String>>();
			String schoolId = "";
			if (allDeptList != null && allDeptList.size() > 0) {
				for (Dept dept2 : allDeptList) {
					Set<String> set = ParentIdAndExtendIdMap.get(dept2.getParentId());
					if (set == null) {
						set = new HashSet<String>();
					}
					if (dept2.getDeptId().length() == 6) {
						set.add(dept2.getDeptId().substring(5, 6));
						schoolId = dept2.getDeptId().substring(0, 5);
					}
					if (dept2.getDeptId().length() == 10) {
						set.add(dept2.getDeptId().substring(6, 9));
					}
					if (dept2.getDeptId().length() == 16) {
						set.add(dept2.getDeptId().substring(14, 16));
					}
					ParentIdAndExtendIdMap.put(dept2.getParentId(), set);
					// 班级
					String deptName = dept2.getDeptName();
					String parentId = dept2.getParentId();
					deptNameAndParentIdMap.put(deptName + " " + parentId, dept2);

				}
			}

			Map<String, Major> majorMap = new ConcurrentHashMap<String, Major>();

			List<Major> majorList = majorMapper.selectAll();
			if (majorList != null && majorList.size() > 0) {
				for (Major major : majorList) {
					majorMap.put(major.getMajorName(), major);
				}
			}

			Map<String, MajorDept> majorDeptMap = new ConcurrentHashMap<String, MajorDept>();
			List<MajorDept> majorDeptList = majorMapper.selectMajorAndDeptAll();
			if (majorDeptList != null && majorDeptList.size() > 0) {
				for (MajorDept majorDept : majorDeptList) {
					majorDeptMap.put(majorDept.getMajorId() + " " + majorDept.getDeptId(), majorDept);
				}
			}

			List<UserBaseInfo> userInfoList = userBaseInfoMapper.selectAll();
			List<UserInfo> uList = userInfoMapper.selectAll();
			// 每个班级下的所有学生，key为班级编号，value为学生集合
			Map<String, List<UserInfo>> classStudentNameMap = new ConcurrentHashMap<String, List<UserInfo>>();
			Map<String, List<UserBaseInfo>> classBaseStudentNameMap = new ConcurrentHashMap<String, List<UserBaseInfo>>();
			// 班级编号与学生扩展位的对应关系,key为学生编号，value为一个班中，所有学生的扩展位
			Map<String, Set<String>> classStudentIdMap = new ConcurrentHashMap<String, Set<String>>();
			// 所填数据包含数据字典，与数据字典校验
			List<DictType> dictTypes = (List<DictType>) CacheUtils.get("dicts");
			List<String> studentTypeList = new ArrayList<String>();
			List<String> programLenthList = new ArrayList<String>();
			List<String> cardTypeList = new ArrayList<String>();
			for (DictType dictType : dictTypes) {
				if (dictType.getDictTypeName().equals("学历")) {
					for (Dict dict : dictType.getList()) {
						studentTypeList.add(dict.getDictName());
					}
				}
				if (dictType.getDictTypeName().equals("学制")) {
					for (Dict dict : dictType.getList()) {
						programLenthList.add(dict.getDictName());
					}
				}
				if (dictType.getDictTypeName().equals("证件类型")) {
					for (Dict dict : dictType.getList()) {
						cardTypeList.add(dict.getDictName());
					}
				}
			}
			if (userInfoList != null && userInfoList.size() > 0) {
				for (UserBaseInfo userBaseInfo : userInfoList) {
					Set<String> set = null;
					List<UserBaseInfo> userList = null;
					if(userBaseInfo != null && StringUtils.isNotBlank(userBaseInfo.getUserId()) && userBaseInfo.getUserId().length() >= 19) {
						String classId = userBaseInfo.getUserId().substring(0, 16);
						if (classStudentIdMap.containsKey(classId)) {
							set = classStudentIdMap.get(classId);
							set.add(userBaseInfo.getUserId().substring(16, 19));
						} else {
							set = new HashSet<String>();
							set.add(userBaseInfo.getUserId().substring(16, 19));
						}
						classStudentIdMap.put(classId, set);

						if (classBaseStudentNameMap.containsKey(classId)) {
							userList = classBaseStudentNameMap.get(classId);
							userList.add(userBaseInfo);
						} else {
							userList = new ArrayList<UserBaseInfo>();
							userList.add(userBaseInfo);
						}
						classBaseStudentNameMap.put(classId, userList);
					}
				}
			}
			if (uList != null && uList.size() > 0) {
				for (UserInfo userInfo : uList) {
					Set<String> set = null;
					List<UserInfo> userList = null;
					if(userInfo != null && StringUtils.isNotBlank(userInfo.getUserId()) && userInfo.getUserId().length() >= 19) {
						String classId = userInfo.getUserId().substring(0, 16);
						if (classStudentIdMap.containsKey(classId)) {
							set = classStudentIdMap.get(classId);
							set.add(userInfo.getUserId().substring(16, 19));
						} else {
							set = new HashSet<String>();
							set.add(userInfo.getUserId().substring(16, 19));
						}
						classStudentIdMap.put(classId, set);

						if (classStudentNameMap.containsKey(classId)) {
							userList = classStudentNameMap.get(classId);
							userList.add(userInfo);
						} else {
							userList = new ArrayList<UserInfo>();
							userList.add(userInfo);
						}
						classStudentNameMap.put(classId, userList);
					}
				}
			}

			List<Dept> hasDeptList = user.getDepts();
			Set<String> schoolSet = new HashSet<String>();
			Set<String> departSet = new HashSet<String>();
			if (hasDeptList != null && hasDeptList.size() > 0) {
				for (Dept dept : hasDeptList) {
					if (dept.getDeptId().length() == 6) {
						schoolSet.add(dept.getDeptId());
					} else {
						departSet.add(dept.getDeptId());
					}
				}
			}

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					rownumber = i;
					String studentNumber = ((String) list.get(i)[0]).trim();
					String userName = ((String) list.get(i)[1]).trim();
					String aliasName = ((String) list.get(i)[2]).trim();
					String schoolName = ((String) list.get(i)[3]).trim();
					String departName = ((String) list.get(i)[4]).trim();
					String gradeName = ((String) list.get(i)[5]).trim();
					String className = ((String) list.get(i)[6]).trim();
					String majorName = ((String) list.get(i)[7]).trim();
					String cardType = ((String) list.get(i)[8]).trim();
					String card = ((String) list.get(i)[9]).trim();
					String birthday = ((String) list.get(i)[10]).trim();
					String nation = ((String) list.get(i)[11]).trim();
					String nationality = ((String) list.get(i)[12]).trim();
					String sex = ((String) list.get(i)[13]).trim();
					String status = ((String) list.get(i)[14]).trim();
					String resourceArea = ((String) list.get(i)[15]).trim();
					String email = ((String) list.get(i)[16]).trim();
					String residentialArea = ((String) list.get(i)[17]).trim();
					String residentialTel = ((String) list.get(i)[18]).trim();
					String telId = ((String) list.get(i)[19]).trim();
					String entranceTime = ((String) list.get(i)[20]).trim();
					String programLength = ((String) list.get(i)[21]).trim();
					String studentType = ((String) list.get(i)[22]).trim();

					//Lixun 2017-5-12 添加 毕业时间、单位地址、职位
					String graduateTime = "";
					String workAddress = "";
					String position = "";
					// 刘利然 2017-6-3 添加 工作单位、QQ，注意看顺序
					String workUnit = "";
					String qq = "";
					//chenshneg 2017-6-15添加行业信息
					String industryType = "";

					String remarks = "";

					if( list.get(i).length >= 23 )
					{
						graduateTime = ((String) list.get(i)[23]).trim();
						workUnit = ((String) list.get(i)[24]).trim();
						workAddress = ((String) list.get(i)[25]).trim();
						position = ((String) list.get(i)[26]).trim();
						qq = ((String) list.get(i)[27]).trim();
						/*chenshneg 2-17-6-15添加*/
						industryType = ((String)list.get(i)[28]).trim();
						remarks = ((String)list.get(i)[29]).trim();
					}

					if (i == 0) {
						// 第一行为excle表头
						Object[] head = new Object[list.get(i).length + 1];
						for (int j = 0; j < head.length; j++) {
							if (j != head.length - 1) {
								head[j] = list.get(i)[j];
							} else {
								head[j] = "失败原因";
							}
						}
						errorList.add(head);
					} else {
						if (userName == null || "".equals(userName.trim())) {
							// 姓名为空
							Object[] content = new Object[list.get(i).length + 1];
							for (int j = 0; j < content.length; j++) {
								if (j != content.length - 1) {
									content[j] = list.get(i)[j];
								} else {
									content[j] = "姓名为空";
								}
							}
							errorList.add(content);
							continue;
						} else if (schoolName == null || "".equals(schoolName.trim())) {
							Object[] content = new Object[list.get(i).length + 1];
							for (int j = 0; j < content.length; j++) {
								if (j != content.length - 1) {
									content[j] = list.get(i)[j];
								} else {
									content[j] = "学校为空";
								}
							}
							errorList.add(content);
							continue;
						} else if (departName == null || "".equals(departName.trim())) {
							Object[] content = new Object[list.get(i).length + 1];
							for (int j = 0; j < content.length; j++) {
								if (j != content.length - 1) {
									content[j] = list.get(i)[j];
								} else {
									content[j] = "院系为空";
								}
							}
							errorList.add(content);
							continue;
						} else if (gradeName == null || "".equals(gradeName.trim())) {
							// 班级为空
							Object[] content = new Object[list.get(i).length + 1];
							for (int j = 0; j < content.length; j++) {
								if (j != content.length - 1) {
									content[j] = list.get(i)[j];
								} else {
									content[j] = "年级为空";
								}
							}
							errorList.add(content);
							continue;
						} else if (className == null || "".equals(className.trim())) {
							// 班级为空
							Object[] content = new Object[list.get(i).length + 1];
							for (int j = 0; j < content.length; j++) {
								if (j != content.length - 1) {
									content[j] = list.get(i)[j];
								} else {
									content[j] = "班级为空";
								}
							}
							errorList.add(content);
							continue;
						} else if (!ClassPathResource.isNumeric(gradeName)) {
							// 班级为空
							Object[] content = new Object[list.get(i).length + 1];
							for (int j = 0; j < content.length; j++) {
								if (j != content.length - 1) {
									content[j] = list.get(i)[j];
								} else {
									content[j] = "年级数据有误";
								}
							}
							errorList.add(content);
							continue;
						} else {
							Dept depart = null;
							if (user.getDepts() == null || user.getDepts().size() == 0) {
								// 学校
								Dept school = deptNameAndParentIdMap.get(schoolName + " " + "0");
								if (school == null) {
									school = new Dept();
									Set<String> set = ParentIdAndExtendIdMap.get("0");
									if (set == null) {
										set = new HashSet<String>();
									}
									String extend = IdUtil.getExtend(set);
									if (extend.length() == 0) {
										throw new RuntimeException("学校曾用名数已超过最大限制(35个)");
									}
									String deptId = schoolId + extend;
									school.setDeptId(deptId);
									school.setCreateTime(new Date());
									school.setFullName(schoolName);
									school.setDeptName(schoolName);
									school.setLevel(4);
									school.setParentId("0");
									school.setStatus("20");
									deptMapper.insert(school);
									deptNameAndParentIdMap.put(schoolName + " " + "0", school);
									set.add(extend);
									ParentIdAndExtendIdMap.put("0", set);
								}
								// 院系
								depart = deptNameAndParentIdMap.get(departName + " " + school.getDeptId());
								if (depart == null) {
									depart = new Dept();
									Set<String> set = ParentIdAndExtendIdMap.get(school.getDeptId());
									if (set == null) {
										set = new HashSet<String>();
									}
									String departId = IdUtil.getDepart(set);
									if (departId.equals("")) {
										throw new RuntimeException("院系已超过最大限制(999个)");
									}
									String deptId = school.getDeptId() + departId + "0";
									depart.setDeptId(deptId);
									depart.setCreateTime(new Date());
									depart.setFullName(school.getFullName() + "," + departName);
									depart.setDeptName(departName);
									depart.setLevel(4);
									depart.setParentId(school.getDeptId());
									depart.setStatus("20");
									deptMapper.insert(depart);
									deptNameAndParentIdMap.put(departName + " " + school.getDeptId(), depart);
									set.add(departId);
									ParentIdAndExtendIdMap.put(school.getDeptId(), set);
								}

							} else {

								Dept school = deptNameAndParentIdMap.get(schoolName + " " + "0");
								if (school == null) {
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "当前用户无权限添加学校曾用名";
										}
									}
									errorList.add(content);
									continue;
								}
								if (departSet.size() == 0) {
									if (schoolSet.size() > 0 && !schoolSet.contains(school.getDeptId())) {
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "无权限向该学校内导入数据";
											}
										}
										errorList.add(content);
										continue;
									}
									depart = deptNameAndParentIdMap.get(departName + " " + school.getDeptId());
									if (depart == null) {
										depart = new Dept();
										Set<String> set = ParentIdAndExtendIdMap.get(school.getDeptId());
										if (set == null) {
											set = new HashSet<String>();
										}
										String departId = IdUtil.getDepart(set);
										if (departId.equals("")) {
											throw new RuntimeException("院系数据异常，院系已超过1000个");
										}
										String deptId = school.getDeptId() + departId + "0";
										depart.setDeptId(deptId);
										depart.setCreateTime(new Date());
										depart.setFullName(school.getFullName() + "," + departName);
										depart.setDeptName(departName);
										depart.setLevel(4);
										depart.setParentId(school.getDeptId());
										depart.setStatus("20");
										deptMapper.insert(depart);
										deptNameAndParentIdMap.put(departName + " " + school.getDeptId(), depart);
										set.add(departId);
										ParentIdAndExtendIdMap.put(school.getDeptId(), set);
									}

								} else {
									depart = deptNameAndParentIdMap.get(departName + " " + school.getDeptId());
									if (depart == null) {
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "院系名称找不到，当前用户无权限添加院系名称";
											}
										}
										errorList.add(content);
										continue;
									}
									if (departSet.size() > 0 && !departSet.contains(depart.getDeptId())) {
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "无权限向该院系内导入数据";
											}
										}
										errorList.add(content);
										continue;
									}
								}
							}

							Major major = null;
							if (majorName != null && !majorName.equals("")) {
								major = majorMap.get(majorName);
								if (major == null) {
									major = new Major();
									major.setMajorName(majorName);
									majorMapper.addMajor(major);
									majorMap.put(majorName, major);
								}
								// 获取major和dept的关系,此处待优化
								MajorDept majorDept = majorDeptMap.get(major.getMajorId() + " " + depart.getDeptId());
								if (majorDept == null) {
									majorDept = new MajorDept();
									Map<String, Object> majorAndDeptMap = new HashMap<String, Object>();
									majorAndDeptMap.put("majorId", major.getMajorId());
									majorAndDeptMap.put("deptId", depart.getDeptId());
									majorMapper.addMajorDept(majorAndDeptMap);
									majorDept.setDeptId(depart.getDeptId());
									majorDept.setMajorId(major.getMajorId());
									majorDeptMap.put(major.getMajorId() + " " + depart.getDeptId(), majorDept);
								}
							}

							// 年级
							Dept grade = deptNameAndParentIdMap.get(gradeName + "级" + " " + depart.getDeptId());
							if (grade == null) {
								grade = new Dept();
								String deptId = depart.getDeptId() + gradeName;
								grade.setDeptId(deptId);
								grade.setCreateTime(new Date());
								grade.setFullName(depart.getFullName() + "," + gradeName + "级");
								grade.setDeptName(gradeName + "级");
								grade.setLevel(4);
								grade.setParentId(depart.getDeptId());
								grade.setStatus("20");
								deptMapper.insert(grade);
								deptNameAndParentIdMap.put(gradeName + "级" + " " + depart.getDeptId(), grade);
							}

							// 班级
							Dept class1 = deptNameAndParentIdMap.get(className + " " + grade.getDeptId());
							if (class1 == null) {
								class1 = new Dept();
								Set<String> set = ParentIdAndExtendIdMap.get(grade.getDeptId());
								if (set == null) {
									set = new HashSet<String>();
								}
								String classId = IdUtil.getClassId(set);
								if (classId.equals("")) {
									throw new RuntimeException("班级数据异常，同年级班级已超过100个");
								}
								String deptId = grade.getDeptId() + classId;
								class1.setDeptId(deptId);
								class1.setCreateTime(new Date());
								String fullName = grade.getFullName() + "," + className;
								class1.setFullName(fullName);
								class1.setLevel(4);
								class1.setParentId(grade.getDeptId());
								class1.setDeptName(className);
								class1.setStatus("20");
								deptMapper.insert(class1);
								deptNameAndParentIdMap.put(className + " " + grade.getDeptId(), class1);
								set.add(classId);
								ParentIdAndExtendIdMap.put(grade.getDeptId(), set);
							}
							UserInfo userInfo = new UserInfo();
							List<UserInfo> userList = classStudentNameMap.get(class1.getDeptId());
							List<UserBaseInfo> userBaseList = classBaseStudentNameMap.get(class1.getDeptId());
							Set<String> set = classStudentIdMap.get(class1.getDeptId());
							if (userList == null) {
								userList = new ArrayList<UserInfo>();
							}
							if (userBaseList == null) {
								userBaseList = new ArrayList<UserBaseInfo>();
							}
							if (set == null) {
								set = new HashSet<String>();
							}
							boolean hasIn = false;
							boolean hasBaseIn = false;
							boolean isStudentIdDuplicate = false;	//学号是否重复
							for (UserInfo userInfo2 : userList) {
								String birth = "";
								if (userInfo2.getBirthday() != null) {
									birth = dateFormat.format(userInfo2.getBirthday());
								}
								String StudentId = userInfo.getStudentnumber();
								if( StudentId == null ) StudentId = "";
								if( !studentNumber.equals( "" ) && studentNumber.equals( StudentId ) )
								{
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "班内学号重复";
										}
									}
									errorList.add(content);
									isStudentIdDuplicate = true;
									break;
								}
								if (userName.equals(userInfo2.getUserName()) && className.equals(userInfo2.getClassName()) && birthday.equals(birth)) {
									hasIn = true;
									break;
								}
							}


							for (UserBaseInfo userBaseInfo : userBaseList) {
								String birth = "";
								if (userBaseInfo.getBirthday() != null) {
									birth = dateFormat.format(userBaseInfo.getBirthday());
								}
								String StudentId = userInfo.getStudentnumber();
								if( StudentId == null ) StudentId = "";
								if( !isStudentIdDuplicate && !studentNumber.equals( "" ) && studentNumber.equals( StudentId ) )
								{
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "班内学号重复";
										}
									}
									errorList.add(content);
									isStudentIdDuplicate = true;
									break;
								}
								if (userName.equals(userBaseInfo.getUserName()) && className.equals(userBaseInfo.getClassName()) && birthday.equals(birth)) {
									hasBaseIn = true;
									break;
								}
							}
							if( isStudentIdDuplicate )	//学号重复
								continue;

							if (!hasBaseIn && !hasIn) {
								String uId = IdUtil.getUserId(set);
								if (uId.equals("")) {
									throw new RuntimeException("同一班级学生数量超过最大限制(999人)");
								}
								userInfo.setUserId(class1.getDeptId() + uId);
								userInfo.setUserName(userName);
								userInfo.setAliasname(aliasName);
								userInfo.setNamePinyin(PinYinUtils.getQuanPin(userName));
								userInfo.setCreateTime(new Date());
								userInfo.setStudentnumber(studentNumber);
								try {
									SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
									if (entranceTime != null && !"".equals(entranceTime)) {
										Date date = fmt1.parse(entranceTime);
										userInfo.setEntranceTime(date);
									}
									if( graduateTime != null && !"".equals(graduateTime) )
									{
										Date date = fmt1.parse(graduateTime);
										userInfo.setGraduationTime( date );
									}
								} catch (Exception e) {
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "入学/毕业日期无法转换";
										}
									}
									errorList.add(content);
									continue;
								}
								if (major != null) {
									userInfo.setMajorId(major.getMajorId());
								}
								userInfo.setSex(sex);
								userInfo.setNation(nation);
								userInfo.setNationality(nationality);
								userInfo.setStudentType(studentType);
								if (studentTypeList.contains(studentType)) {
									userInfo.setStudentType(studentType);
								} else {
									userInfo.setStudentType("");
								}
								if (programLenthList.contains(programLength)) {
									userInfo.setProgramLength(programLength);
								} else {
									userInfo.setProgramLength("");
								}
								if (cardTypeList.contains(cardType)) {
									userInfo.setCardType(cardType);
								} else {
									userInfo.setCardType("");
								}
								userInfo.setCard(card);
								userInfo.setEmail(email);
								userInfo.setResidentialArea(residentialArea);
								userInfo.setResidentialTel(residentialTel);
								userInfo.setTelId(telId);
								userInfo.setResourceArea(resourceArea);
								userInfo.setStatus(status);
								userInfo.setClassName(className);
								/*Lixun 2-17-5-12 添加3个字段*/
								userInfo.setWorkAddress( workAddress );
								userInfo.setPosition( position );
								/*Lixun*/
								/* llr 2-17-6-3 添加2个字段*/
								userInfo.setWorkUnit(workUnit);
								userInfo.setQq(qq);
								/*chensheng 2-17-6-15*/
								userInfo.setIndustryType(industryType);
								/*杨牛牛 2017-6-15*/
								userInfo.setRemarks(remarks);
								/*llr*/
								try {
									SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
									if (birthday != null && !"".equals(birthday)) {
										Date date = fmt.parse(birthday);
										userInfo.setBirthday(date);
									}
								} catch (Exception e) {
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "生日数据无法转换";
										}
									}
									errorList.add(content);
									continue;
								}
								userInfo.setCheckFlag("0");
								userInfoMapper.save(userInfo);
								// 保存成功后刷新classStudentNameMap，classStudentIdMap
								userList.add(userInfo);
								set.add(uId);
								classStudentNameMap.put(class1.getDeptId(), userList);
								classStudentIdMap.put(class1.getDeptId(), set);
							}
							if (hasIn) {
								Object[] content = new Object[list.get(i).length + 1];
								for (int j = 0; j < content.length; j++) {
									if (j != content.length - 1) {
										content[j] = list.get(i)[j];
									} else {
										content[j] = "校友库中含有相同记录的数据";
									}
								}
								errorList.add(content);
								continue;
							}
							if (hasBaseIn) {
								Object[] content = new Object[list.get(i).length + 1];
								for (int j = 0; j < content.length; j++) {
									if (j != content.length - 1) {
										content[j] = list.get(i)[j];
									} else {
										content[j] = "基础库中含有相同记录的数据";
									}
								}
								errorList.add(content);
								continue;
							}
						}
					}
				}
			}
			System.gc();
			return ExcelUtil.exportData(errorList);
		} catch (Exception e) {
			String str = "";
			if (rownumber > 0) {
				str = "第" + rownumber + "行数据导致数据导入失败";
			} else {
				str = e.getMessage();
			}
			throw new RuntimeException(str, e);
		}
	}

	public List<UserInfo> selectAllUserList() {
		return userInfoMapper.selectUserToGetTel();
	}

	/** --查询所有有手机号的用户并且分页-- **/
	public List<UserInfo> selectUserToGetTelPage(Map<String, Object> map) {
		return userInfoMapper.selectUserToGetTelPage(map);
	}

	public List<UserInfo> selectUserByClassIdAndName(String userName, String classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("classId", classId);
		return userInfoMapper.selectUserByClassIdAndName(map);
	}

	public void updateUserAccountNum(UserInfo userInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userInfo.getUserId());
		map.put("accountNum", userInfo.getAccountNum());
		userInfoMapper.updateUserAccountNum(map);
	}

	public UserInfo selectAllProByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userInfoMapper.selectAllProByUserId(map);
	}

	public List<UserInfo> selectUserByClassId(String classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", classId);
		return userInfoMapper.selectAllUserByClassId(map);
	}

	public UserInfo selectUserInfoByGmidAndName(Map<String, Object> map) {
		return userInfoMapper.selectUserInfo(map);
	}

	public int updateUserTelId(Map<String, Object> map, UserInfo userinfo) {
		if (userinfo == null) {
			return -8;
		}

		String submitTelId = map.get("telId").toString();
		String telId = userinfo.getTelId();
		// System.out.println("-------------------------------------------------------");
		// System.out.println("基础数据表中的电话号码是"+telId);
		// System.out.println("提交的电话号码是"+submitTelId);
		// System.out.println("-------------------------------------------------------");
		if (telId == null || "".equals(telId)) {
			return userInfoMapper.updateTelId(map);
		} else if (!submitTelId.equals(telId)) {
			if (userinfo.getUseTime() == null) {
				if (map.get("useTime") != null) {
					return userInfoMapper.updateTelId(map);
				} else {
					// 返回1代表不做更新处理
					return -8;
				}
			} else if (userinfo.getUseTime().before((Date) map.get("useTime"))) {
				return userInfoMapper.updateTelId(map);
			}
		}
		return -8;
	}

	public boolean selectUserInClass(String classId, List<String> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("classId", classId);
		long count = userInfoMapper.selectUserInClass(map);
		if (count == list.size()) {
			return true;
		}
		return false;
	}

	public List<UserInfo> selectByUserName(String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		return userInfoMapper.selectByUserName(map);
	}

	public List<UserInfo> selectCard(List<String> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return userInfoMapper.selectCard(map);
	}

	public void update(UserInfo userInfo, User user, int isInput) {
		// 更新归属地
		if(StringUtils.isNotBlank(userInfo.getTelId()) && userInfo.getTelId().length() == 11){
			MobileLocal mobileLocal = mobileLocalMapper.selectByMobileNumber(userInfo.getTelId().substring(0, 7));
			if (mobileLocal != null) {
				userInfo.setMobileLocal(mobileLocal.getMobileArea());
				userInfoMapper.updateMobileLocal(userInfo);
			}
		}
		if ("1".equals(userInfo.getCheckFlag())) {
			if(StringUtils.isNotBlank(userInfo.getUserName())){
				userInfo.setNamePinyin(PinYinUtils.getQuanPin(userInfo.getUserName()));
			}
			userInfoMapper.updateOthers(userInfo);
			UserBaseInfo userBaseInfo = new UserBaseInfo();
			BeanUtils.copyProperties(userInfo, userBaseInfo);
			userBaseInfoMapper.updateOthers(userBaseInfo);
		} else {
			if (isInput == 0 && userInfo.getUserId().substring(0, 16).equals(userInfo.getClassId())) {
				userInfoMapper.update(userInfo);
			} else if (isInput == 0 && userInfo.getClassId() != null && !userInfo.getClassId().equals("")) {
				// 机构ID更改
				List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(userInfo.getClassId());
				List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(userInfo.getClassId());
				Set<String> set = new HashSet<String>();
				for (UserBaseInfo UserBaseInfo2 : list) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				for (UserInfo UserBaseInfo2 : ulist) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				String uId = IdUtil.getUserId(set);
				if (uId.length() == 0) {
					throw new RuntimeException("班级人数超过最大限制(999人)");
				}
				String userId = userInfo.getClassId() + uId;
				userInfo.setNewUserId(userId);
				userInfoMapper.update(userInfo);
				UserProfile userProfile = userProfileMapper.selectByBaseInfoId(userInfo.getUserId());
				String baseInfoId = "";
				String groupName = "";
				if (userProfile != null) {
					String[] idArray = userProfile.getBaseInfoId().split(",");
					for (String baseId : idArray) {
						if (baseId.equals(userInfo.getUserId())) {
							baseInfoId += userId + ",";
						} else {
							baseInfoId += baseId + ",";
						}
					}
					if (baseInfoId.length() > 0) {
						baseInfoId = baseInfoId.substring(0, baseInfoId.length() - 1);
					}
					String[] groupArray = userProfile.getGroupName().split(",");
					for (String group : groupArray) {
						if (group.equals(userInfo.getUserId().substring(0, 16))) {
							groupName += userId.substring(0, 16) + ",";
						} else {
							groupName += group + ",";
						}
					}
					if (groupName.length() > 0) {
						groupName = groupName.substring(0, groupName.length() - 1);
					}
				}
				UserProfile newUserProfile = new UserProfile();
				newUserProfile.setName(userInfo.getUserName());
				if (userInfo.getSex() != null && userInfo.getSex().equals("男")) {
					newUserProfile.setSex("0");
				} else {
					newUserProfile.setSex("1");
				}
				newUserProfile.setBaseInfoId(userInfo.getUserId());
				newUserProfile.setNewBaseInfoId(baseInfoId);
				newUserProfile.setGroupName(groupName);
				userProfileMapper.updateBase(newUserProfile);
			} else if (isInput == 1 && userInfo.getSchoolName() != null && !userInfo.getSchoolName().equals("") && userInfo.getDepartName() != null
					&& !userInfo.getDepartName().equals("") && userInfo.getGradeName() != null && !userInfo.getGradeName().equals("")
					&& userInfo.getClassName() != null && !userInfo.getClassName().equals("")) {
				Map<String, Object> map = new HashMap<String, Object>();
				Dept depart = null;
				if (user.getDepts() == null || user.getDepts().size() == 0) {
					// 查找学校
					map.put("parentId", "0");
					map.put("deptName", userInfo.getSchoolName());

					// 学校
					Dept school = deptMapper.selectByNameAndParentId(map);
					if (school == null) {
						// 取所有学校名
						school = new Dept();
						List<Dept> list = deptMapper.getSchool();
						Set<String> set = new HashSet<String>();
						for (Dept dept2 : list) {
							set.add(dept2.getDeptId().substring(5, 6));
						}
						String extend = IdUtil.getExtend(set);
						if (extend.equals("")) {
							throw new RuntimeException("学校曾用名数据量已超过最大限制(35个)");
						}
						String deptId = list.get(0).getDeptId().substring(0, 5) + extend;
						school.setDeptId(deptId);
						school.setCreateTime(new Date());
						school.setFullName(userInfo.getSchoolName());
						school.setDeptName(userInfo.getSchoolName());
						school.setLevel(4);
						school.setParentId("0");
						school.setStatus("20");
						deptMapper.insert(school);
					}

					// 院系
					map.put("parentId", school.getDeptId());
					map.put("deptName", userInfo.getDepartName());
					depart = deptMapper.selectByNameAndParentId(map);
					if (depart == null) {
						depart = new Dept();
						List<Dept> list = deptMapper.selectByDeptId(school.getDeptId());
						Set<String> set = new HashSet<String>();
						for (Dept dept2 : list) {
							if (dept2.getDeptId() != null && dept2.getDeptId().length() == 10) {
								set.add(dept2.getDeptId().substring(6, 9));
							}
						}
						String departId = IdUtil.getDepart(set);
						if (departId.equals("")) {
							throw new RuntimeException("院系已超过最大限制(999个)");
						}
						String deptId = school.getDeptId() + departId + "0";
						depart.setDeptId(deptId);
						depart.setCreateTime(new Date());
						depart.setFullName(school.getFullName() + "," + userInfo.getDepartName());
						depart.setDeptName(userInfo.getDepartName());
						depart.setLevel(4);
						depart.setParentId(school.getDeptId());
						depart.setStatus("20");
						deptMapper.insert(depart);
					}

				} else {
					List<Dept> hasDeptList = user.getDepts();
					Set<String> schoolSet = new HashSet<String>();
					Set<String> departSet = new HashSet<String>();
					for (Dept dept : hasDeptList) {
						if (dept.getDeptId().length() == 6) {
							schoolSet.add(dept.getDeptId());
						} else {
							departSet.add(dept.getDeptId());
						}
					}

					map.put("parentId", "0");
					map.put("deptName", userInfo.getSchoolName());

					// 学校
					Dept school = deptMapper.selectByNameAndParentId(map);
					if (school == null) {
						throw new RuntimeException("无权限添加学校曾用名");
					}

					if (departSet.size() == 0) {
						if (schoolSet.size() > 0 && !schoolSet.contains(school.getDeptId())) {
							throw new RuntimeException("当前用户无权限添加学校曾用名");
						}
						// 院系
						map.put("parentId", school.getDeptId());
						map.put("deptName", userInfo.getDepartName());
						depart = deptMapper.selectByNameAndParentId(map);
						if (depart == null) {
							depart = new Dept();
							List<Dept> list = deptMapper.selectByDeptId(school.getDeptId());
							Set<String> set = new HashSet<String>();
							for (Dept dept2 : list) {
								if (dept2.getDeptId() != null && dept2.getDeptId().length() == 10) {
									set.add(dept2.getDeptId().substring(6, 9));
								}
							}
							String departId = IdUtil.getDepart(set);
							if (departId.equals("")) {
								throw new RuntimeException("院系数据异常，院系已超过1000个");
							}
							String deptId = school.getDeptId() + departId + "0";
							depart.setDeptId(deptId);
							depart.setCreateTime(new Date());
							depart.setFullName(school.getFullName() + "," + userInfo.getDepartName());
							depart.setDeptName(userInfo.getDepartName());
							depart.setLevel(4);
							depart.setParentId(school.getDeptId());
							depart.setStatus("20");
							deptMapper.insert(depart);
						} else {
							map.put("parentId", school.getDeptId());
							map.put("deptName", userInfo.getDepartName());
							depart = deptMapper.selectByNameAndParentId(map);
							if (depart == null) {
								throw new RuntimeException("院系名称找不到，当前用户无权限添加院系名称");
							}
							if (departSet.size() > 0 && !departSet.contains(depart.getDeptId())) {
								throw new RuntimeException("无权限向该院系内添加数据");
							}
						}
					}
				}

				// 专业
				Major major = majorMapper.getByName(userInfo.getMajorName());
				if (major == null) {
					major = new Major();
					major.setMajorName(userInfo.getMajorName());
					majorMapper.addMajor(major);
				}
				// 获取major和dept的关系
				Map<String, Object> majorAndDeptMap = new HashMap<String, Object>();
				majorAndDeptMap.put("deptId", depart.getDeptId());
				majorAndDeptMap.put("majorId", major.getMajorId());
				List<MajorDept> majorDepts = majorMapper.getMajorAndDept(majorAndDeptMap);
				if (majorDepts != null && majorDepts.size() > 0) {
					// 此处代码是为了去掉中间表的冗余数据，之前由于没有考虑去重，导致中间表大量重复数据
					if (majorDepts.size() > 1) {
						List<String> deptList = new ArrayList<String>();
						deptList.add(depart.getDeptId());
						majorMapper.deleteMajorByDeptId(deptList);
						majorMapper.addMajorDept(majorAndDeptMap);
					}
				} else {
					majorMapper.addMajorDept(majorAndDeptMap);
				}

				// 年级
				map.put("parentId", depart.getDeptId());
				map.put("deptName", userInfo.getGradeName() + "级");
				Dept grade = deptMapper.selectByNameAndParentId(map);
				if (grade == null) {
					grade = new Dept();
					String deptId = depart.getDeptId() + userInfo.getGradeName();
					grade.setDeptId(deptId);
					grade.setCreateTime(new Date());
					grade.setFullName(depart.getFullName() + "," + userInfo.getGradeName() + "级");
					grade.setDeptName(userInfo.getGradeName() + "级");
					grade.setLevel(4);
					grade.setParentId(depart.getDeptId());
					grade.setStatus("20");
					deptMapper.insert(grade);
				}

				// 班级
				map.put("parentId", grade.getDeptId());
				map.put("deptName", userInfo.getClassName());
				Dept class1 = deptMapper.selectByNameAndParentId(map);
				if (class1 == null) {
					class1 = new Dept();
					List<Dept> list = deptMapper.selectByDeptId(grade.getDeptId());
					Set<String> set = new HashSet<String>();
					for (Dept dept2 : list) {
						if (dept2.getDeptId() != null && dept2.getDeptId().length() == 16) {
							set.add(dept2.getDeptId().substring(14, 16));
						}
					}
					String classId = IdUtil.getClassId(set);
					if (classId.equals("")) {
						throw new RuntimeException("班级数据异常，同年级班级已超过100个");
					}
					String deptId = grade.getDeptId() + classId;
					class1.setDeptId(deptId);
					class1.setCreateTime(new Date());
					String fullName = grade.getFullName() + "," + userInfo.getClassName();
					class1.setFullName(fullName);
					class1.setLevel(4);
					class1.setParentId(grade.getDeptId());
					class1.setDeptName(userInfo.getClassName());
					class1.setStatus("20");
					deptMapper.insert(class1);
				}

				List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(class1.getDeptId());
				List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(class1.getDeptId());
				Set<String> set = new HashSet<String>();
				for (UserBaseInfo UserBaseInfo2 : list) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				for (UserInfo UserBaseInfo2 : ulist) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				String uId = IdUtil.getUserId(set);
				if (uId.length() == 0) {
					throw new RuntimeException("同一班级人数超过最大限制(999人)");
				}
				String userId = class1.getDeptId() + uId;
				userInfo.setNewUserId(userId);
				userInfo.setMajorId(major.getMajorId());
				userInfoMapper.update(userInfo);
				UserProfile userProfile = userProfileMapper.selectByBaseInfoId(userInfo.getUserId());
				String baseInfoId = "";
				String groupName = "";
				if (userProfile != null) {
					String[] idArray = userProfile.getBaseInfoId().split(",");
					for (String baseId : idArray) {
						if (baseId.equals(userInfo.getUserId())) {
							baseInfoId += userId + ",";
						} else {
							baseInfoId += baseId + ",";
						}
					}
					if (baseInfoId.length() > 0) {
						baseInfoId = baseInfoId.substring(0, baseInfoId.length() - 1);
					}
					String[] groupArray = userProfile.getGroupName().split(",");
					for (String group : groupArray) {
						if (group.equals(userInfo.getUserId().substring(0, 16))) {
							groupName += userId.substring(0, 16) + ",";
						} else {
							groupName += group + ",";
						}
					}
					if (groupName.length() > 0) {
						groupName = groupName.substring(0, groupName.length() - 1);
					}
				}
				UserProfile newUserProfile = new UserProfile();
				newUserProfile.setName(userInfo.getUserName());
				if (userInfo.getSex() != null && userInfo.getSex().equals("男")) {
					newUserProfile.setSex("0");
				} else {
					newUserProfile.setSex("1");
				}
				newUserProfile.setBaseInfoId(userInfo.getUserId());
				newUserProfile.setNewBaseInfoId(baseInfoId);
				newUserProfile.setGroupName(groupName);
				userProfileMapper.updateBase(newUserProfile);
			}
		}

		//同步到被修改所对应的用户
		UserInfo u = userInfoMapper.selectByUserId(userInfo.getUserId());
		if(u!= null && u.getAccountNum() != null && u.getAccountNum().length() > 0){
			UserProfile userProfile = userProfileMapper.selectByAccountNum(u.getAccountNum());
			if(userProfile != null){
				userProfile.setName(userInfo.getUserName());
				if("男".equals(userInfo.getSex())){
					userProfile.setSex("0");
				}else if("女".equals(userInfo.getSex())){
					userProfile.setSex("1");
				}
				userProfileMapper.update(userProfile);
			}
		}
	}

	public void update(UserInfo userInfo) {
		this.userInfoMapper.update(userInfo);
	}

	public DataGrid<UserInfo> dataGridFor(Map<String, Object> map) {
		DataGrid<UserInfo> dataGrid = new DataGrid<UserInfo>();
		long total = userInfoMapper.countFor(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<UserInfo> list = userInfoMapper.selectListFor(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	@Override
	public void saveFromBase(Map<String, Object> map) {
		try {
			List<UserBaseInfo> list = userBaseInfoMapper.selectByDeptIdForImportAll(map);
			for (UserBaseInfo baseInfo : list) {
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(baseInfo, userInfo);
				userInfo.setCheckFlag("1");
				userInfoMapper.save(userInfo);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateIdea(UserInfo userInfo) {
		try {
			//2016-08-18 QPF
			UserInfo userInfoTmp = userInfoMapper.selectByUserId(userInfo.getUserId());
			UserProfile userProfile = userProfileMapper.selectByAccountNum(userInfoTmp.getAccountNum());
			if ("1".equals(userInfo.getCheckFlag())) {

				//判断是否来自于App端的数据
				if(userInfoTmp.getIsFromApp() != null && userInfoTmp.getIsFromApp() == 1){
					userInfo.setUseTime(TimeZoneUtils.getDate());
					//学院、院系、年级、班级状态改为20
					Dept dept = new Dept();
					dept.setStatus("20");
					dept.setDeptId(userInfoTmp.getSchoolId());
					deptMapper.updateStatus(dept);

					dept.setDeptId(userInfoTmp.getDepartId());
					deptMapper.updateStatus(dept);

					dept.setDeptId(userInfoTmp.getGradeId());
					deptMapper.updateStatus(dept);

					dept.setDeptId(userInfoTmp.getClassId());
					deptMapper.updateStatus(dept);


					if (userProfile != null) {
						ChatDeptGroup deptGroup = new ChatDeptGroup() ;
						deptGroup.setDeptId(userInfo.getUserId().substring(0, 16));

						List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup) ;
						if(deptGroupList == null || deptGroupList.isEmpty()) {
							ChatGroup group = new ChatGroup();
							group.setName(userInfoTmp.getClassName());
							group.setIntroduction(userInfoTmp.getSchoolName() + "," + userInfoTmp.getDepartName() + "," + userInfoTmp.getGradeName() + "," + userInfoTmp.getClassName());
							group.setType("1");
							chatGroupService.insert(group);
							deptGroup.preInsert();
							deptGroup.setGroupId(group.getId());
							chatDeptGroupMapper.insert(deptGroup);
						}else{
							deptGroup = deptGroupList.get(0) ;
						}

						//添加到群组中
						chatGroupService.addMemberToGroup(deptGroup.getGroupId(), userProfile.getAccountNum());

						userProfile.setAuthenticated("1");

						String baseInfoId = userProfile.getBaseInfoId() == null ?"": userProfile.getBaseInfoId();
						String groupName = userProfile.getGroupName()==null?"": userProfile.getGroupName();
						String classes = userProfile.getClasses()==null?"":userProfile.getClasses();

						baseInfoId += (StringUtils.isNotBlank(baseInfoId) ? "," : "") + userInfoTmp.getUserId();
						groupName += (StringUtils.isNotBlank(groupName) ? "," : "") + userInfoTmp.getFullName();
						classes += (StringUtils.isNotBlank(classes) ? "_" : "") + userInfoTmp.getClassId();
						userProfile.setBaseInfoId(baseInfoId);
						userProfile.setGroupName(groupName);
						userProfile.setClasses(classes);
						//更新群组信息
						userProfileMapper.update(userProfile);
						try {
							PushUtils.pushGroupAll(userInfoTmp.getUserId(),userInfoTmp.getUserId(),userProfile.getAccountNum(),  3);
							PushUtils.pushGroupPersonal(userProfile.getAccountNum(), userInfoTmp.getUserId(),userInfoTmp.getUserId(), 1);
						}catch (Exception e ) {
							e.printStackTrace();
						}
					}

				}
				UserBaseInfo baseInfo = new UserBaseInfo();
				BeanUtils.copyProperties(userInfoTmp, baseInfo);
				userBaseInfoMapper.save(baseInfo);
			}else{
				//判断是否来自于App端的数据
				if(userInfoTmp.getIsFromApp() != null &&  userInfoTmp.getIsFromApp() == 1) {
					//学院、院系、年级、班级状态改为20

					Dept dept = new Dept();

					dept.setStatus("30");
					Dept tmp = deptMapper.getById(userInfoTmp.getSchoolId());

					if(!tmp.getStatus().equals("20")){
						dept.setDeptId(userInfoTmp.getSchoolId());
						deptMapper.updateStatus(dept);
					}

					tmp = deptMapper.getById(userInfoTmp.getDepartId());
					if(!tmp.getStatus().equals("20")) {
						dept.setDeptId(userInfoTmp.getDepartId());
						deptMapper.updateStatus(dept);
					}

					tmp = deptMapper.getById(userInfoTmp.getGradeId());
					if(!tmp.getStatus().equals("20")){
						dept.setDeptId(userInfoTmp.getGradeId());
						deptMapper.updateStatus(dept);
					}

					tmp = deptMapper.getById(userInfoTmp.getClassId());
					if(!tmp.getStatus().equals("20")){
						dept.setDeptId(userInfoTmp.getClassId());
						deptMapper.updateStatus(dept);
					}
					try {
						PushUtils.pushGroupAll(userInfoTmp.getUserId(),userInfoTmp.getUserId(),userProfile.getAccountNum(),  2);
					}catch (Exception e ) {
						e.printStackTrace();
					}

				}
			}
			userInfoMapper.updateIdea(userInfo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean deleteAll(Map<String, Object> map) {
		boolean canDelete = true;
		List<UserInfo> list = userInfoMapper.selectByDeptIdAll(map);
		for (UserInfo info : list) {
			if (info.getCheckFlag() != null && "1".equals(info.getCheckFlag())) {
				canDelete = false;
				break;
			}
		}
		if (canDelete) {
			if (list != null && list.size() > 0) {
				for (UserInfo userInfo : list) {
					UserProfile userProfile = userProfileMapper.selectByBaseInfoId(userInfo.getUserId());
					if (userProfile != null) {
//						String baseInfoId = "";
//						String groupName = "";
//						if (userProfile.getBaseInfoId() != null && userProfile.getBaseInfoId().length() > 0) {
//							String[] idArray = userProfile.getBaseInfoId().split(",");
//							for (String baseId : idArray) {
//								if (!baseId.equals(userInfo.getUserId())) {
//									baseInfoId += baseId + ",";
//								}
//							}
//							if (baseInfoId.length() > 0) {
//								baseInfoId = baseInfoId.substring(0, baseInfoId.length() - 1);
//							}
//						}
//						if (userProfile.getGroupName() != null && userProfile.getGroupName().length() > 0) {
//							String[] groupArray = userProfile.getGroupName().split(",");
//							for (String group : groupArray) {
//								if (!group.equals(userInfo.getUserId().substring(0, 16))) {
//									groupName += group + ",";
//								}
//							}
//							if (groupName.length() > 0) {
//								groupName = groupName.substring(0, groupName.length() - 1);
//							}
//						}
//						map.put("baseInfoId", baseInfoId);
//						map.put("accountNum", userProfile.getAccountNum());
//						map.put("groupName", groupName);
//						userProfileMapper.clearBaseInfoId(map);


						Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
						if(baseMap == null) {
							baseMap = Maps.newHashMap() ;
						}
						baseMap.put("accountNum", userProfile.getAccountNum()) ;
						userProfileMapper.clearBaseInfoId(baseMap);
					}
				}
			}
			userInfoMapper.deleteByDeptIdAll(map);
		}
		return canDelete;
	}

	/** --校友管理导出excel-- **/
	@Override
	public String export(Map<String, Object> map) throws IOException {
		List<Object[]> objects = new ArrayList<Object[]>();
		Object[] o = new Object[36];
		o[0] = "学号";
		o[1] = "姓名";
		o[2] = "曾用名";
		o[3] = "学校";
		o[4] = "院系";
		o[5] = "年级";
		o[6] = "班级";
		o[7] = "专业";
		o[8] = "证件类型";
		o[9] = "证件号码";
		o[10] = "出生年月";
		o[11] = "民族";
		o[12] = "国籍";
		o[13] = "性别";
		o[14] = "状态";
		o[15] = "籍贯";
		o[16] = "邮箱";
		o[17] = "家庭地址";
		o[18] = "固定电话";
		o[19] = "手机号码";
		o[20] = "入学日期";
		o[21] = "学制";
		o[22] = "学历";
		// 以下为新增
		o[23] = "政治面貌";
		o[24] = "毕业时间";
		o[25] = "QQ";
		o[26] = "微博";
		o[27] = "个人网站";
		o[28] = "通讯地址";
		o[29] = "工作单位";
		o[30] = "职务";
		o[31] = "所在行业";
		o[32] = "企业性质";
		o[33] = "单位电话";
		o[34] = "单位地址";
		o[35] = "备注";
		objects.add(o);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		List<UserInfo> list = userInfoMapper.selectByDeptIdForExport(map);
		if (list != null && list.size() > 0) {
			for (UserInfo userInfo : list) {
				Object[] o1 = new Object[36];
				o1[0] = userInfo.getStudentnumber();
				o1[1] = userInfo.getUserName();
				o1[2] = userInfo.getAliasname();
				o1[3] = userInfo.getSchoolName();
				o1[4] = userInfo.getDepartName();
				if(userInfo.getGradeName() != null){
					o1[5] = userInfo.getGradeName().substring(0, userInfo.getGradeName().length() - 1);
				}else{
					o1[5] = "";
				}
				o1[6] = userInfo.getClassName();
				o1[7] = userInfo.getMajorName();
				o1[8] = "";
				o1[9] = "";
				if (userInfo.getBirthday() != null) {
					o1[10] = dateFormat.format(userInfo.getBirthday());
				} else {
					o1[10] = "";
				}
				o1[11] = userInfo.getNation();
				o1[12] = userInfo.getNationality();
				o1[13] = userInfo.getSex();
				o1[14] = userInfo.getStatus();
				o1[15] = userInfo.getResourceArea();
				o1[16] = userInfo.getEmail();
				o1[17] = userInfo.getResidentialArea();
				o1[18] = userInfo.getResidentialTel();
				o1[19] = userInfo.getTelId();
				if (userInfo.getEntranceTime() != null) {
					o1[20] = dateFormat.format(userInfo.getEntranceTime());
				} else {
					o1[20] = "";
				}
				o1[21] = userInfo.getProgramLength();
				o1[22] = userInfo.getStudentType();
				o1[23] = userInfo.getPolitical();
				o1[24] = userInfo.getGraduationTime();
				o1[25] = userInfo.getQq();
				o1[26] = userInfo.getWeibo();
				o1[27] = userInfo.getPersonalWebsite();
				o1[28] = userInfo.getMailingAddress();
				o1[29] = userInfo.getWorkUnit();
				o1[30] = userInfo.getPosition();
				o1[31] = userInfo.getIndustryType();
				o1[32] = userInfo.getEnterprise();
				o1[33] = userInfo.getWorkTel();
				o1[34] = userInfo.getWorkAddress();
				o1[35] = userInfo.getRemarks();

				objects.add(o1);
			}
		}
		return ExcelUtil.exportData(objects);
	}

	@Override
	public List<UserInfo> getUserInfoByUserIdForAlumni(String userId) {
		String[] s = userId.split(",");
		List<String> list = new ArrayList<String>();
		for (String str : s) {
			list.add(str);
		}
		return userInfoMapper.getUserInfoByUserIdForAlumni(list);
	}

	public List<UserInfo> selectByAccountNum(Map<String, Object> map) {
		return userInfoMapper.selectByAccountNum(map);
	}

	@Override
	public void updateMobileLocal() {
		try {
			List<UserInfo> list = userInfoMapper.selectMobileLocalIsNull();
			if (list != null && list.size() > 0) {
				for (UserInfo userInfo : list) {
					if (userInfo.getTelId().length() == 11) {
						String mobileNumber = userInfo.getTelId().substring(0, 7);
						MobileLocal mobileLocalObj = mobileLocalMapper.selectByMobileNumber(mobileNumber);
						if (mobileLocalObj != null) {
							// 找到归属地,删掉暂存表的数据
							mobileScratchMapper.delete(mobileNumber);
							// 更新用户表
							userInfo.setMobileLocal(mobileLocalObj.getMobileArea());
							userInfoMapper.updateMobileLocal(userInfo);
						} else {
							// 找不到归属地入暂存表
							MobileScratch mobileScratch = mobileScratchMapper.selectByMobileNumber(mobileNumber);
							if (mobileScratch == null) {
								mobileScratch = new MobileScratch();
								mobileScratch.setMobileNumber(mobileNumber);
								mobileScratchMapper.insert(mobileScratch);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sendBirthdaySms() {
		try {
			List<UserInfo> list = userInfoMapper.selectBirthday();
			if (list != null && list.size() > 0 && Global.smsBirthdayTemplate != null && Global.smsBirthdayTemplate.length() > 0) {
				for (UserInfo userInfo : list) {
					MsgSend msgSend = new MsgSend();
					String content = Global.smsBirthdayTemplate;
					content = content.replace("${0}", userInfo.getUserName());
					if (Global.sign != null && Global.sign.length() > 0) {
//						content = "【" + Global.sign + "】" + content;
						if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
							content += "（" + Global.sign + "）";

						} else {
							content = "【" + Global.sign + "】" + content;
						}
					}
					msgSend.setContent(content);
					msgSend.setMessagegroup(UUID.getMsgGroup());
					msgSend.setTelphone(userInfo.getTelId());
					msgSend.setStatues(9);
					msgSend.setSendtime(new Date());
					msgSend.setMsgType(2);
					int countNumber = 0;
					if (content.length() % 67 == 0) {
						countNumber = content.length() / 67;
					} else {
						countNumber = content.length() / 67 + 1;
					}
					msgSend.setCountNumber(countNumber);
					msgSendMapper.insertMsg(msgSend);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserInfo> selectByAccountNum2FullName(String accountNum) {
		return userInfoMapper.selectByAccountNum2FullName(accountNum);
	}

	@Override
	public List<UserInfo> selectUserInfoByName(String name) {
		return userInfoMapper.selectUserInfoByName(name);
	}

	@Override
	public void updateFromUserProfile() {
		List<UserProfile> list = userProfileMapper.selectAll();
		for (UserProfile profile : list) {
			UserInfo userInfo = new UserInfo();
			userInfo.setAccountNum(profile.getAccountNum());
			userInfo.setResidentialArea(profile.getAddress());
			userInfo.setHobbies(profile.getHobby());
			userInfo.setEmail(profile.getEmail());
			userInfo.setAlumniId(profile.getAlumni_id());
			userInfo.setPosition(profile.getPosition());
			userInfo.setWorkUnit(profile.getWorkUtil());
			userInfo.setIndustryType(profile.getProfession());
			userInfo.setTelId(profile.getPhoneNum());
			userInfo.setPicUrl(profile.getPicture());
			userInfoMapper.updateFromUserProfile(userInfo);
		}
	}

	@Override
	public void updateTwoWay() {
		// 查询号码不为空的用户
		List<UserInfo> list = userInfoMapper.selectByMobile();
		for (UserInfo userInfo : list) {
			UserBaseInfo userBaseInfo = new UserBaseInfo();
			BeanUtils.copyProperties(userInfo, userBaseInfo);
			userBaseInfoMapper.updateMobile(userBaseInfo);
		}

		List<UserBaseInfo> list1 = userBaseInfoMapper.selectByMobile();
		for (UserBaseInfo userBaseInfo : list1) {
			UserInfo userInfo = new UserInfo();
			BeanUtils.copyProperties(userBaseInfo, userInfo);
			userInfoMapper.updateMobile(userInfo);
		}
	}

	@Override
	public Map<String, Long> countEveryThing(){
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("",userInfoMapper.countByAccountNum());
		map.put("",userInfoMapper.countByCheckFlag());
		map.put("",userInfoMapper.countByNoCheckFlag());
		return map;
	}

	/**
	 * 获得潜在用户的信息(被挖掘人的手机号)
	 * @auther jiangling
	 * @param userId
	 * @return
     */
	@Override
	public List<DataMining> getPotentialUser(String userId) {
		return dataMiningMapper.getPotentialUser(userId);
	}


	/**
	 * 取消认证
	 * @param userId
     */
	@Override
	public void cancleAuth(String userId,  Message message){
		try {
			//通过userId找到accountNum
			String accountNum = userInfoMapper.selectAccountNumByUserId(userId);
			if(accountNum == null){
				message.setMsg("账户不存在");
				message.setSuccess(false);
				return;
			}

			//删除user_info与userbase_info下的accountNum
			userInfoMapper.deleteAccountNumByUserId(userId);
			userInfoMapper.deleteAccountNumByUserIdBase(userId);

			//获取userprofile
			UserProfile userProfile = userProfileMapper.selectById(accountNum);

			if(userProfile == null){
				message.setMsg("账户不存在");
				message.setSuccess(false);
				return;
			}

			String baseInfoIds = userProfile.getBaseInfoId();

			if(baseInfoIds == null || baseInfoIds == ""){
				message.setMsg("该用户学习经历无班级");
				message.setSuccess(false);
				return;
			}

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

				// 如果是一键认证，而且唯一认证数据，则删除该用户
				if("1".equals(userProfile.getIsOneKeyAuth())){
					userProfileMapper.deleteByAccountNum(userProfile.getAccountNum());
				}
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
							baseInfoIds += ("," + baseId);
							groupNames += ("," + baseId.substring(0, 16));
							classes += ("_" + userInfoMapper.selectClassNameByUserId(baseId));
						}
						count++;
					}else{
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
			deptGroup.setDeptId(userId.substring(0, 16));
			List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup);
			deptGroup = deptGroupList.get(0);
			chatGroupService.removeMemberFromGroup(deptGroup.getGroupId(), userProfile.getAccountNum());
			try {
				PushUtils.pushGroupAll(userId, userId, accountNum, 0);
			}catch (Exception e ) {
				e.printStackTrace();
			}
			message.setMsg("已取消认证");
			message.setSuccess(true);
		}catch (Exception e) {
			logger.error(e, e);
			message.setMsg("取消认证失败");
			message.setSuccess(false);
		}
	}

	public void addUserInfo(Message message,String content){
		try{
			UserInfo userInfo = JSON.parseObject(content, UserInfo.class);
			if(userInfo == null){
				message.setMsg("无任何参数!");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isBlank(userInfo.getSchoolId()) && StringUtils.isBlank(userInfo.getSchoolName())){
				message.setMsg("学校名为空!");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isBlank(userInfo.getDepartId()) && StringUtils.isBlank(userInfo.getDepartName())){
				message.setMsg("学院名为空!");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isBlank(userInfo.getGradeId()) && StringUtils.isBlank(userInfo.getGradeName())){
				message.setMsg("年级名为空!");
				message.setSuccess(false);
				return;
			}

			try {
				int inGrade = Integer.parseInt(userInfo.getGradeName());

				if(userInfo.getGradeName().length()!=4){
					message.setMsg("年级编号必须是4位!");
					message.setSuccess(false);
					return;
				}

				int toYear = Calendar.getInstance().get(Calendar.YEAR);

				if(inGrade > toYear){
					message.setMsg("所填年级晚于当前年份!");
					message.setSuccess(false);
					return;
				}

			} catch (NumberFormatException e) {
				message.setMsg("非法的年级号!");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isBlank(userInfo.getClassId()) && StringUtils.isBlank(userInfo.getClassName())){
				message.setMsg("班级名为空!");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(userInfo.getAccountNum())){
				message.setMsg("账号为空!");
				message.setSuccess(false);
				return;
			}
			UserProfile userProfile = userProfileMapper.selectByAccountNum(userInfo.getAccountNum());
			if(userProfile == null ){
				message.setMsg("不存在此用户!");
				message.setSuccess(false);
				return;
			}

			Map<String, Object> searchMap = new HashMap<>();

			Map<String, Object> map = new HashMap<String, Object>();
			Dept depart = null;
			// 查找学校
			map.put("parentId", "0");
			map.put("deptName", userInfo.getSchoolName());

			// 学校
			Dept school = deptMapper.selectByNameAndParentId(map);
			if (school == null) {
				school = new Dept();

				searchMap.put("level", "1");
				List<Dept> list = deptMapper.selectAllDept(searchMap);
				Set<String> set = new HashSet<String>();
				for (Dept dept2 : list) {
					set.add(dept2.getDeptId().substring(5, 6));
				}
				String extend = IdUtil.getExtend(set);

				if (extend.equals("")) {
					throw new RuntimeException("学校曾用名数据量已超过最大限制(35个)");
				}
				String deptId = list.get(0).getDeptId().substring(0, 5) + extend;
				school.setDeptId(deptId);
				school.setCreateTime(new Date());
				school.setFullName(userInfo.getSchoolName());
				school.setDeptName(userInfo.getSchoolName());
				school.setLevel(1);
				school.setParentId("0");
				school.setStatus("10");
				deptMapper.insert(school);
			}else if("30".equals(school.getStatus())){
				school.setStatus("10");
				deptMapper.updateStatus(school);
			}
			// 院系
			map.put("parentId", school.getDeptId());
			map.put("deptName", userInfo.getDepartName());
			depart = deptMapper.selectByNameAndParentId(map);
			if (depart == null) {
				depart = new Dept();
				searchMap.put("deptPid", school.getDeptId());
				searchMap.put("level", "2");
				List<Dept> list = deptMapper.selectAllDept(searchMap);
				Set<String> set = new HashSet<String>();
				for (Dept dept2 : list) {
					if (dept2.getDeptId() != null && dept2.getDeptId().length() == 10) {
						set.add(dept2.getDeptId().substring(6, 9));
					}
				}
				String departId = IdUtil.getDepart(set);
				if (departId.equals("")) {
					throw new RuntimeException("院系已超过最大限制(999个)");
				}
				String deptId = school.getDeptId() + departId + "0";
				depart.setDeptId(deptId);
				depart.setCreateTime(new Date());
				depart.setFullName(school.getFullName() + "," + userInfo.getDepartName());
				depart.setDeptName(userInfo.getDepartName());
				depart.setLevel(2);
				depart.setParentId(school.getDeptId());
				depart.setStatus("10");
				deptMapper.insert(depart);
			}else if("30".equals(depart.getStatus())){
				depart.setStatus("10");
				deptMapper.updateStatus(depart);
			}
			// 年级
			map.put("parentId", depart.getDeptId());
			map.put("deptName", userInfo.getGradeName() + "级");
			Dept grade = deptMapper.selectByNameAndParentId(map);
			if (grade == null) {
				grade = new Dept();
				String deptId = depart.getDeptId() + userInfo.getGradeName();
				grade.setDeptId(deptId);
				grade.setCreateTime(new Date());
				grade.setFullName(depart.getFullName() + "," + userInfo.getGradeName() + "级");
				grade.setDeptName(userInfo.getGradeName() + "级");
				grade.setLevel(3);
				grade.setParentId(depart.getDeptId());
				grade.setStatus("10");
				deptMapper.insert(grade);
			}else if("30".equals(grade.getStatus())){
				grade.setStatus("10");
				deptMapper.updateStatus(grade);
			}

			// 班级
			map.put("parentId", grade.getDeptId());
			map.put("deptName", userInfo.getClassName());
			Dept class1 = deptMapper.selectByNameAndParentId(map);
			if (class1 == null) {
				class1 = new Dept();
				searchMap.put("deptPid", grade.getDeptId());
				searchMap.put("level", "4");
				List<Dept> list = deptMapper.selectAllDept(searchMap);
				Set<String> set = new HashSet<String>();
				for (Dept dept2 : list) {
					if (dept2.getDeptId() != null && dept2.getDeptId().length() == 16) {
						set.add(dept2.getDeptId().substring(14, 16));
					}
				}
				String classId = IdUtil.getClassId(set);
				if (classId.equals("")) {
					throw new RuntimeException("班级数据异常，同年级班级已超过100个");
				}
				String deptId = grade.getDeptId() + classId;
				class1.setDeptId(deptId);
				class1.setCreateTime(new Date());
				String fullName = grade.getFullName() + "," + userInfo.getClassName();
				class1.setFullName(fullName);
				class1.setLevel(4);
				class1.setParentId(grade.getDeptId());
				class1.setDeptName(userInfo.getClassName());
				class1.setStatus("10");
				deptMapper.insert(class1);
			}else if("30".equals(class1.getStatus())){
				class1.setStatus("10");
				deptMapper.updateStatus(class1);
			}

			String userId = null ;

			//查看该申请是否已经提交过
			Map<String, Object> tmp = new HashMap<>();
			tmp.put("accountNum", userProfile.getAccountNum());
			List<UserInfo> listTmp = userInfoMapper.selectByAccountNum(tmp);
			for(UserInfo ui:listTmp){
				if(ui.getUserId().substring(0,16).equals(class1.getDeptId())){
					if("1".equals(ui.getCheckFlag())){
						message.init(false,"已经存在于该班级", null);
						return;
					}else if("0".equals(ui.getCheckFlag())){
						message.init(false, "请勿重复提交申请", null);
						return;
					}
				}
			}

			// 判断校友数据是否已存在
			Map<String,Object> qMap = Maps.newHashMap() ;
			qMap.put("userNameFull",userProfile.getName()) ;
			qMap.put("deptId1",class1.getDeptId()) ;
			if(StringUtils.isNotBlank(userProfile.getSex())){
				if("0".equals(userProfile.getSex())){
					qMap.put("sex", "男");
				}else if("1".equals(userProfile.getSex())){
					qMap.put("sex", "女");
				}
			}
			List<UserInfo> tempList = userInfoMapper.selectUserToGetTelPage(qMap) ;

//			boolean isInsert = false ;
			if(tempList != null && !tempList.isEmpty()) {
				message.init(false, "已有匹配校友数据，或已申请加入该班级", null);
				return;
			} else {
				// 生成新的校友信息编号
				List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(class1.getDeptId());
				List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(class1.getDeptId());
				Set<String> set = new HashSet<>();
				for (UserBaseInfo UserBaseInfo2 : list) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				for (UserInfo UserBaseInfo2 : ulist) {
					if (UserBaseInfo2.getUserId() != null) {
						set.add(UserBaseInfo2.getUserId().substring(16, 19));
					}
				}
				String uId = IdUtil.getUserId(set);
				if (uId.length() == 0) {
					throw new RuntimeException("同一班级人数超过最大限制(999人)");
				}
				userId = class1.getDeptId() + uId;
//				isInsert = true ;
			}

			userInfo.setUserName(userProfile.getName());
			userInfo.setTelId(userProfile.getPhoneNum());
			userInfo.setNamePinyin(PinYinUtils.getQuanPin(userInfo.getUserName()));
			userInfo.setCreateTime(new Date());
			if(StringUtils.isBlank(userProfile.getSex())){
				userInfo.setSex("");
			}else if(userProfile.getSex().equals("0")){
				userInfo.setSex("男");
			}else if(userProfile.getSex().equals("1")){
				userInfo.setSex("女");
			}
			userInfo.setEmail(userProfile.getEmail());
			userInfo.setPosition(userProfile.getAddress());
			userInfo.setPicUrl(userProfile.getPicture());
			userInfo.setCheckFlag("0");
			userInfo.setAccountNum(userProfile.getAccountNum());
			userInfo.setIsFromApp(1);
//			if(isInsert) {
				//通过userprofile的信息填充userInfo的信息
				userInfo.setUserId(userId);
				userInfoMapper.save(userInfo);
//			} else {
//				userInfoMapper.update(userInfo);
//			}

			message.setMsg("请求成功");
			message.setSuccess(true);
		}catch (Exception e){
			logger.error(e, e);
			message.setMsg("请求失败");
			message.setSuccess(false);
		}
	}


	/**
	 * 校友数据统计分析
	 */
	@Override
	public List<Map<String,String>> countAnalysisUserInfo(Map<String,String> map) {
		map = map == null ? Maps.<String, String>newHashMap() : map ;
		String groupType = map.get("groupType") ;
		String deptId = map.get("deptId") ;
		List<Map<String, String>> list = Lists.newArrayList() ;
		list = userInfoMapper.countAnalysisUserInfo(map) ;
		if(list != null) {
			for (Map<String,String> analysisMap : list) {
				Map<String,String> tempMap = Maps.newHashMap() ;
				if(StringUtils.isNotBlank(groupType)) {
					switch (groupType) {
						case "5" :
							tempMap.put("majorId",analysisMap.get("majorId")) ;
							break ;
						case "1" :
							tempMap.put("deptId",analysisMap.get("schoolId")) ;
							break ;
						case "2" :
							tempMap.put("deptId",analysisMap.get("collegeId")) ;
							break ;
						case "3" :
							tempMap.put("deptId",analysisMap.get("gradeId")) ;
							break ;
						case "4" :
							tempMap.put("deptId",analysisMap.get("classId")) ;
							break ;
						default:
							tempMap.put("deptId",deptId) ;
					}
				}
				analysisMap.putAll(userInfoSummaryAnalysis(tempMap));
			}
		}
		return list ;
	}

	@Override
	public DataGrid<Map<String,String>> countAnalysisUserInfoDataGrid(Map<String, String> map) {
		DataGrid<Map<String,String>> dataGrid = new DataGrid<Map<String,String>>();
		map = map == null ? Maps.<String, String>newHashMap() : map ;

        String groupType = map.get("groupType") ;
//        map.put("groupType",groupType) ;
		if(StringUtils.isNotBlank(map.get("page")) && StringUtils.isNotBlank(map.get("rows"))) {
			long total = userInfoMapper.countByCountAnalysisUserInfo(map);
			dataGrid.setTotal(total);
			int start = (Integer.valueOf( map.get("page")) - 1) * Integer.valueOf( map.get("rows"));
			map.put("start", String.valueOf(start));
			map.put("isLimit", "1");
		}

		String deptId = map.get("deptId") ;
		List<Map<String, String>> list = Lists.newArrayList() ;
		list = userInfoMapper.countAnalysisUserInfo(map) ;
		if(list != null) {
			for (Map<String,String> analysisMap : list) {
				Map<String,String> tempMap = Maps.newHashMap() ;
                switch (groupType) {
                    case "5" :
                        tempMap.put("majorId",analysisMap.get("majorId")) ;
                        break ;
                    case "1" :
                        tempMap.put("deptId",analysisMap.get("schoolId")) ;
                        break ;
                    case "2" :
                        tempMap.put("deptId",analysisMap.get("collegeId")) ;
                        break ;
                    case "3" :
                        tempMap.put("deptId",analysisMap.get("gradeId")) ;
                        break ;
                    case "4" :
                        tempMap.put("deptId",analysisMap.get("classId")) ;
                        break ;
                    default:
                        tempMap.put("deptId",deptId) ;
                }
				analysisMap.putAll(userInfoSummaryAnalysis(tempMap));
			}
		}
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**
	 * 校友数据汇总
	 */
	@Override
	public List<Map<String, String>> userInfoSummary(Map<String, String> map) {
		List<Map<String, String>> list = Lists.newArrayList() ;

        String cacheKey = null ;
		if(map == null) {
		    cacheKey = "all" ;
			map = new HashMap<String, String>();
		}
		if (StringUtils.isNotBlank(cacheKey)) {
		    list = (List<Map<String,String>>) CacheUtils.get(AnalysisAction.ANALYSIS_CACHE_USERINFOSUMMARY,cacheKey) ;
        }
        if(list == null || list.isEmpty()) {
            list = Lists.newArrayList() ;
            // 1. 获取正式校友数
            Map<String, String> map1 = new HashMap<String, String>();
            map1.put("name", "正式校友数");
			long num = userInfoMapper.countUserInfo(map) ;
            map1.put("count", String.valueOf(num));
            list.add(map1);

            // 2. 获取已被认证校友数
            Map<String, String> map2 = new HashMap<String, String>();
            map.put("isRegistered", "1");
            map2.put("name", "已被认证校友数");
            map2.put("count", String.valueOf(userInfoMapper.countUserInfo(map)));
            list.add(map2);
            // 3. 获取被挖掘的校友数
            Map<String, String> map3 = new HashMap<String, String>();
            map.clear();
            map.put("isMining", "1");
            map3.put("name", "被挖掘的校友数");
            map3.put("count", String.valueOf(userInfoMapper.countUserInfo(map)));
            list.add(map3);

            if (StringUtils.isNotBlank(cacheKey)) {
                CacheUtils.put(AnalysisAction.ANALYSIS_CACHE_USERINFOSUMMARY,cacheKey,list) ;
            }
        }
		return list ;
	}

	/**
	 * 校友数据汇总
	 */
	@Override
	public Map<String, String> userInfoSummaryAnalysis(Map<String, String> map) {
		Map<String, String> resultMap = Maps.newHashMap() ;
		if(map == null) {
			map = new HashMap<String, String>();
		}
		// 1. 获取正式校友数
		resultMap.put("checkFlagCount",String.valueOf(userInfoMapper.countUserInfo(map))) ;

		// 2. 获取已被认证校友数
		map.put("isRegistered","1") ;
		resultMap.put("authCount",String.valueOf(userInfoMapper.countUserInfo(map))) ;
		// 3. 获取被挖掘的校友数
		map.put("isRegistered",null) ;
		map.put("isMining","1") ;
		resultMap.put("miningCount",String.valueOf(userInfoMapper.countUserInfo(map))) ;
		return resultMap ;
	}

	/**
	 * 获取院系用户报表图
	 */
	public List<Map<String, Object>> chartOfDeptUser() {
		List<Map<String, Object>> list = (List<Map<String,Object>>) CacheUtils.get(AnalysisAction.ANALYSIS_CACHE_CHARTOFDEPTUSERCHART) ;
		if(list == null || list.isEmpty()) {
			list = Lists.newArrayList() ;
			Map<String, String> map = Maps.newHashMap();
			map.put("isLimit", "1");
			map.put("start", "0");
			map.put("rows", "7");
			map.put("groupType", "2");
			List<Map<String, String>> queryList = userInfoMapper.countAnalysisUserInfo(map);
			if (queryList == null) {
				return null;
			}
			Map<String, Object> otherMap = Maps.newHashMap();
			otherMap.put("name", "其他");
			otherMap.put("fullName", "其他");
			map = userInfoSummaryAnalysis(null);
			long otherTotal = 0;
			if (!map.isEmpty()) {
				otherTotal = Long.valueOf(map.get("checkFlagCount"));
				if (!queryList.isEmpty()) {
					for (Map<String, String> temp : queryList) {
						Map<String, Object> chartMap = Maps.newHashMap();
						Long value = Long.valueOf(temp.get("total"));
						chartMap.put("name", temp.get("collegeName"));
						chartMap.put("value", value);
						chartMap.put("fullName", temp.get("schoolName") + temp.get("collegeName"));
						list.add(chartMap);
						otherTotal -= value;
					}
				}
			}
			if (otherTotal > 0) {
				otherMap.put("value", otherTotal);
				list.add(otherMap);
			}
			CacheUtils.put(AnalysisAction.ANALYSIS_CACHE_CHARTOFDEPTUSERCHART,list);
		}
		return list ;
	}

	/**
	 * 获取被认证校友报表图
	 */
	public List<Map<String, Object>> chartOfRegistered() {
		List<Map<String, Object>> list = (List<Map<String,Object>>) CacheUtils.get(AnalysisAction.ANALYSIS_CACHE_CHARTOFREGISTERED) ;
		if(list == null || list.isEmpty()) {
			list = Lists.newArrayList() ;
			Map<String,String> map = userInfoSummaryAnalysis(null) ;
			if(!map.isEmpty()) {
				long total = Long.valueOf(map.get("checkFlagCount")) ;
				long authTotal = Long.valueOf(map.get("authCount")) ;
				Map<String,Object> authMap = Maps.newHashMap() ;
				authMap.put("name","已被认证校友") ;
				authMap.put("value",authTotal) ;
				list.add(authMap);
				Map<String,Object> authNotMap = Maps.newHashMap() ;
				authNotMap.put("name","未被认证校友") ;
				authNotMap.put("value",total - authTotal) ;
				list.add(authNotMap);

			}
			CacheUtils.put(AnalysisAction.ANALYSIS_CACHE_CHARTOFREGISTERED,list);
		}
		return list ;
	}
	/**
	 * 获取挖掘校友报表图
	 */
	public List<Map<String, String>> chartOfMining() {
		List<Map<String, String>> list = (List<Map<String,String>>) CacheUtils.get(AnalysisAction.ANALYSIS_CACHE_CHARTOFMINING) ;
		if(list == null || list.isEmpty()) {
			list = Lists.newArrayList() ;
			Map<String, String> map = new HashMap<String, String>();
			map.put("isMining","1") ;
			for(int i = 11;i >= 0 ;i--) {
				Map<String,String> temp = Maps.newHashMap() ;

				Calendar calendar=Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH) - i);//让日期加1
				Date date =calendar.getTime();
				String month = new SimpleDateFormat("yyyy-MM").format(date) ;

				map.put("month",month) ;
				temp.put("name",month) ;
				temp.put("value",String.valueOf(userInfoMapper.countUserInfo(map))) ;
				list.add(temp);
			}
			CacheUtils.put(AnalysisAction.ANALYSIS_CACHE_CHARTOFMINING,list);
		}
//        resultMap.put("miningCount",String.valueOf(userInfoMapper.countUserInfo(map))) ;
		return list ;
	}

	/**
	 * 同级生
	 * @param message
	 * @param content
	 */
	public void findGradeMates(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map<String, String> map = JSON.parseObject(content, Map.class);
		String classPathStr = map.get("classPathStr");
		String level = map.get("level");
		String isAuth = map.get("isAuth");
		String[] studyPath = classPathStr.split(",");

		if(StringUtils.isBlank(classPathStr) || studyPath.length == 0){
			message.setMsg("请传入至少一个学习经历基础ID");
			message.setSuccess(false);
			return;
		}
		if(StringUtils.isBlank(level)){
			message.setMsg("请传入查询等级");
			message.setSuccess(false);
			return;
		}

		UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(studyPath[0]);
		if(userInfo == null || userInfo.getAccountNum() == null || StringUtils.isBlank(userInfo.getAccountNum())){
			message.setMsg("未認證的學習經歷編號");
			message.setSuccess(false);
			return;
		}
		String accountNum = userInfo.getAccountNum();
		if(isAuth == null || StringUtils.isBlank(isAuth)){
			isAuth = "1";
		}

		String st[] = classPathStr.split(",");
		List<String> list = new ArrayList<>();
		for(String tmp:st){
			tmp =  tmp.replaceAll("\\s*", "");
			list.add(tmp);
		}

		Map<String, Object> mapTmp = new HashMap<>();
		mapTmp.put("level", level );
		mapTmp.put("list", list);
		mapTmp.put("isAuth", isAuth);
		mapTmp.put("accountNum", accountNum);
		List<Map<String, String>> listResult = userInfoMapper.findGradeMates(mapTmp);
		message.setMsg("成功获取同级生");
		message.setSuccess(true);
		message.setObj(listResult);
	}

	/**
	 * 查询新加入校友列表
	 * @param content
	 * @param message
	 */
	public void showNewsAuth(Message message, String content){
		try{
			DataGrid<UserInfo> dataGrid = new DataGrid<>();
			Map<String, Object> map = new HashMap<>();
			if (StringUtils.isNotBlank(content)) {
				Map<String, String> tmpMap = JSON.parseObject(content, Map.class);
				if (StringUtils.isNotBlank(tmpMap.get("page")) && StringUtils.isNotBlank(tmpMap.get("rows"))) {
					int start = (Integer.valueOf(tmpMap.get("page")) - 1) * Integer.valueOf(tmpMap.get("rows"));
					map.put("start", start);
					map.put("rows", Integer.valueOf(tmpMap.get("rows")));
				}else{
					map.put("isNoLimit", "1");
				}
			}else{
				map.put("isNoLimit", "1");
			}
			long total = userInfoMapper.countAuthMember(map);
			dataGrid.setTotal(total);
			List<UserInfo> list = userInfoMapper.selectAuthMember(map);
			dataGrid.setRows(list);
			message.init(true,"查询成功", dataGrid);
		}catch (Exception e){
			logger.error(e, e);
			message.setMsg("查询失败");
			message.setSuccess(false);
		}
	}

	/**
	 * 查询校友审核列表
	 * @param message
	 * @param content
	 */
	public void userCheckList(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map<String, Object> map = JSON.parseObject(content, Map.class);

		String accountNum = (String)map.get("accountNum");
		String userId = (String)map.get("userId");

		if(StringUtils.isBlank(accountNum) && StringUtils.isBlank(userId)){
			message.init(false, "用户ID为空", null);
			return;
		}
		if(StringUtils.isNotBlank(userId)){
			map.put("isNoLimit", "1");
			List<Map<String, String>> list = userInfoMapper.userCheckList(map);
			if(list == null || list.size() <= 0 ){
				message.init(false,"没有找到该记录", null );
			}else{
				message.init(true, "查询成功", list.get(0));
			}
		}else{
			if (StringUtils.isNotBlank((String)map.get("page")) && StringUtils.isNotBlank((String)map.get("rows"))) {
				int start = (Integer.valueOf((String)map.get("page")) - 1) * Integer.valueOf((String)map.get("rows"));
				map.put("start", start);
				map.put("rows", Integer.valueOf((String)map.get("rows")));
			}else{
				map.put("isNoLimit", "1");
			}

			String checkFlag = (String)map.get("checkFlag");
			if(StringUtils.isNotBlank(checkFlag)&&!"0".equals(checkFlag)&&!"1".equals(checkFlag)&&!"2".equals(checkFlag)){
				message.init(false, "非法的审核状态类型", null);
				return;
			}

			List<Map<String, String>> list = userInfoMapper.userCheckList(map);

			if(list == null || list.size() <= 0 ){
				message.init(false, "没有数据可以展示咯", null);
			}else{
				long total = userInfoMapper.userCheckCount(map);
				DataGrid<Map<String, String>> dataGrid = new DataGrid<>();
				dataGrid.setTotal(total);
				dataGrid.setRows(list);
				message.init(true, "查询成功", dataGrid);
			}

		}
	}


	/**
	 * 获取省份地区校友分布
	 */
	public List<Map<String,String>> findUserInfoMap(Map<String,String> map) {
        List<Map<String,String>> list = null ;
        String cacheKey = null ;
	    if(map == null){
	        cacheKey = "all" ;
        }
        if(StringUtils.isNotBlank(cacheKey) ) {
            list = (List<Map<String, String>>) CacheUtils.get(AnalysisAction.ANALYSIS_CACHE_CHARTOFUSERINFOMAP, cacheKey);
        }
        if(list == null || list.isEmpty()) {
            list = userInfoMapper.findUserInfoMap(map);
            if(StringUtils.isNotBlank(cacheKey) ) {
                CacheUtils.put(AnalysisAction.ANALYSIS_CACHE_CHARTOFUSERINFOMAP, cacheKey, list);
            }
        }

        return list ;
	}

	/**
	 * 获取省份校友会分布
	 */
	public List<Map<String,String>> findAlumniCountMap(Map<String,String> map) {
		return userInfoMapper.findAlumniCountMap(map) ;
	}

	public UserAlumni selectAlumniStatusById(String id){
		return userProfileMapper.selectAlumniStatusById(id);
	}

	/**
	 * 获取省份地区校友分布 接口
	 */
	public void chartOfAlumniCountMap(Message message, String content) {
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,String> analysisMap = JSON.parseObject(content, Map.class);
		List<Map<String, String>> list = findAlumniCountMap(analysisMap) ;
		message.init(true,"查询成功",list);
	}

	public void chartOfUserInfoMap(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,String> analysisMap = JSON.parseObject(content, Map.class);
		List<Map<String,String>> list = userInfoMapper.findUserInfoMap(analysisMap);
		message.init(true,"查询成功",list);
	}


	public static void main(String[] args) {

		int num = 429 ;
		int length = String.valueOf(num).length() ;
		System.out.println(length);
        int base = (int)Math.pow(10,length-1);
        System.out.println(num / base);

		int count = num / base ;
		if(count > 0 ) {
			if(num%base > 0 ) {
				count ++ ;
			}
		}
		System.out.println(count * base / 5);
	}

	public boolean checkAccountInClass(UserInfo userInfo){
		boolean result = false;

		if(userInfoMapper.countAccountInClass(userInfo) > 0)
			result = true;

		return result;
	}


	public Map<String,Object> usingStatistics(){
		Map<String,Object> map = Maps.newHashMap();
		//android 下载量
		long countAndroidDownloads = clientService.countAndroidDownloads();
		//iOS 下载量
		String iOSDownloads = "0";
		List<Share>  shareList =shareService.findList(new Share());
		if (shareList!=null && !shareList.isEmpty()){
			Share share = shareList.get(0);
			if (share!=null && StringUtils.isNotBlank(share.getIosDownloads())){
				iOSDownloads = share.getIosDownloads();
			}
		}

		//注册用户数
		long countUserProfile =0;
		Map<String,Object> mapUserProfile = Maps.newHashMap();
		countUserProfile = userProfileMapper.countUserProfile(mapUserProfile);

		//认证用户数
		Map<String,String> mapUser = Maps.newHashMap();
		mapUser.put("isRegistered","1");
		long countAuthenticateUser = userInfoMapper.countUserInfo(mapUser);

		//挖掘校友数
		long countUserInfoMining = userInfoMapper.countUserInfoMining();

		//挖掘注册用户数
		long countUserInfoRegister = userInfoMapper.countUserInfoRegister();

		//挖掘认证用户数
		long countUserInfoAuthenticate = userInfoMapper.countUserInfoAuthenticate();

		//校友会公众号关注人数
		Map<String,Object> map1 = Maps.newHashMap();
		map1.put("accountType","10");
		long alumniWechatAccount = weiXinUserService.countWechatAccount(map1);

		//基金会公众号关注人数
		Map<String,Object> map2 = Maps.newHashMap();
		map2.put("accountType","20");
		long foundationWechatAccount = weiXinUserService.countWechatAccount(map2);

		//微信关注人数
        long countWechatUser =alumniWechatAccount +foundationWechatAccount;


		map.put("countAndroidDownloads",countAndroidDownloads);
		map.put("iOSDownloads",iOSDownloads);
		map.put("countUserProfile",countUserProfile);
		map.put("countAuthenticateUser",countAuthenticateUser);
		map.put("countUserInfoMining",countUserInfoMining);
		map.put("countUserInfoRegister",countUserInfoRegister);
		map.put("countUserInfoAuthenticate",countUserInfoAuthenticate);
		map.put("alumniWechatAccount",alumniWechatAccount);
		map.put("foundationWechatAccount",foundationWechatAccount);
		map.put("countWechatUser",countWechatUser);
		return map;
	}

	/**
	 * 方法countUserInfoMining 的功能描述：挖掘用户数
	 * @createAuthor niu
	 * @createDate 2017-04-12 09:11:05
	 * @param
	 * @return java.lang.String
	 * @throw
	 *
	 */
	public long countUserInfoMining(){

		return userInfoMapper.countUserInfoMining();
	}

	/**
	 * 方法countUserInfoRegister 的功能描述：挖掘注册用户数
	 * @createAuthor niu
	 * @createDate 2017-04-12 09:11:26
	 * @param
	 * @return java.lang.String
	 * @throw
	 *
	 */
	public long countUserInfoRegister(){
		return userInfoMapper.countUserInfoRegister();
	}

	/**
	 * 方法countUserInfoAuthenticate 的功能描述：挖掘认证用户数
	 * @createAuthor niu
	 * @createDate 2017-04-12 09:11:39
	 * @param
	 * @return java.lang.String
	 * @throw
	 *
	 */
	public long countUserInfoAuthenticate(){
		return userInfoMapper.countUserInfoAuthenticate();
	}
//	public List<Map<String,String>> findUserInfoMapChatUser(Map<String,String> map){
//		List<Map<String, String>> list = userInfoMapper.findUserInfoMapUser(map);
//		String jsonMessage = list.get(0).get("value");
//		System.out.print(jsonMessage);
//		JSONArray jsonArray = JSONArray.fromObject(jsonMessage);
//		List<Map<String, String>> list12 = JSONArray.toList(jsonArray, Map.class);// 过时方法
//		return list12 ;
//	}
//	public List<Map<String,String>> finduserInfoSummary(Map<String,String> map){
//		List<Map<String, String>> list1 = userInfoMapper.findUserInfoMapUser(map);
//		String jsonMessage = list1.get(0).get("context");
//		System.out.print(jsonMessage);
//		JSONArray jsonArray = JSONArray.fromObject(jsonMessage);
//		List<Map<String, String>> list12 = JSONArray.toList(jsonArray, Map.class);// 过时方法
//		return list12 ;
//	}


	public DataGrid<UserInfo> selectSameNameList(Map<String, Object> map){
		DataGrid<UserInfo> dataGrid = new DataGrid<>();
		long total = userInfoMapper.countSameName(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		map.put("isLimit", "1");
		List<UserInfo> list = userInfoMapper.selectSameNameList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}


	/**
	 * 迁移某校友到某班级
	 * @return
	 */
	public Map<String, Object> moveData(Map<String, String> map){
		int code = 0;
		Map<String, Object> resultMap = new HashMap<>();
		String focus = map.get("focus");
		String userId = map.get("userId");
		String newDeptId = map.get("newDeptId");

		// focus，就检测是否有类似数据，如果有，就返回
		if("0".equals(focus)){
			resultMap = sameInfo(userId, newDeptId);
			code = (int)resultMap.get("code");
			if(code != 0){
				return resultMap;
			}
		}
		//查询旧用户信息
		UserInfo userInfo = userInfoMapper.selectByUserId(userId);
		//旧班级ID
		String oldClassId = userInfo.getUserId().substring(0, 16);
		//查询新班级的信息
		DeptInfo deptInfo = deptInfoMapper.findById(newDeptId);



		// 生成新的校友信息编号
		List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(newDeptId);
		List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(newDeptId);
		Set<String> set = new HashSet<>();
		for (UserBaseInfo baseTmp : list) {
			if (baseTmp.getUserId() != null) {
				set.add(baseTmp.getUserId().substring(16, 19));
			}
		}
		for (UserInfo uTmp : ulist) {
			if (uTmp.getUserId() != null) {
				set.add(uTmp.getUserId().substring(16, 19));
			}
		}
		String uId = IdUtil.getUserId(set);
		if (uId.length() == 0) {
			System.out.println("同一班级人数超过最大限制(999人)");
			code = 999;
			resultMap.put("code", code);
			return resultMap;
		}
		//设置新的userId，保存
		String newUserId = newDeptId + uId;
		userInfo.setUserId(newUserId);
		userInfoMapper.save(userInfo);
		//保存一份到userBaseInfo表
		UserBaseInfo baseInfo = new UserBaseInfo();
		BeanUtils.copyProperties(userInfo, baseInfo);
		userBaseInfoMapper.save(baseInfo);

		//删除校友的数据
		userInfoMapper.deleteById(userId);
		userBaseInfoMapper.deleteById(userId);

		// 如果已经认证
		if(StringUtils.isNotBlank(userInfo.getAccountNum())){
			//认证用户ID
			String accountNum = userInfo.getAccountNum();
			UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
			if(userProfile != null){
				// 更新用户认证信息
				Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
				if (baseMap == null) {
					baseMap = Maps.newHashMap();
				}
				baseMap.put("accountNum", userProfile.getAccountNum());
				userProfileMapper.clearBaseInfoId(baseMap);


				//把用户移除旧群组
				Map<String, Object> tmp = new HashMap<String, Object>();
				map.put("accountNum", accountNum);
				map.put("class", oldClassId);
				userInfoMapper.deleteGroupUser(tmp);
				//从环信删除该用户
				ChatDeptGroup oldDeptGroup = new ChatDeptGroup();
				oldDeptGroup.setDeptId(oldClassId);
				List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(oldDeptGroup);
				oldDeptGroup = deptGroupList.get(0);
				chatGroupService.removeMemberFromGroup(oldDeptGroup.getGroupId(), accountNum);


				// 验证班级环信群组是否存在,如不存在创建
				ChatDeptGroup newDeptGroup = new ChatDeptGroup() ;
				newDeptGroup.setDeptId(newDeptId);
				List<ChatDeptGroup> deptGroupListNew = chatDeptGroupMapper.query(newDeptGroup) ;
				if(deptGroupListNew == null || deptGroupListNew.isEmpty()) {
					ChatGroup group = new ChatGroup() ;
					group.setName(deptInfo.getDeptName());
					group.setIntroduction(deptInfo.getFullName());
					group.setType("1");
					chatGroupService.insert(group);
					String groupId = group.getId();
					newDeptGroup.preInsert();
					newDeptGroup.setGroupId(groupId);
					chatDeptGroupMapper.insert(newDeptGroup);
				} else {
					newDeptGroup = deptGroupList.get(0) ;
				}

				// 将该用户添加到群组中
				chatGroupService.addMemberToGroup(newDeptGroup.getGroupId(),accountNum);
			}
		}
		resultMap.put("code", code);
		return resultMap;
	}

	/**
	 * 检查新班级是否有重复数据
	 * @param userId
	 * @param newDeptId
	 * @return
	 */
	private Map<String, Object> sameInfo(String userId, String newDeptId){
		int code = 0;
		List<UserInfo> resultList = new ArrayList<>();
		UserInfo userInfo = userInfoMapper.selectByUserId(userId);
		DeptInfo deptInfo = deptInfoMapper.findById(newDeptId);
		if(userInfo != null && deptInfo != null){
			// 获取正式校友数据
			List<UserInfo> list = userInfoMapper.selectUserByClassIdLess(newDeptId);
			if(list != null && list.size() > 0){
				for(UserInfo u:list){
					if(userInfo.getUserName().equals(u.getUserName())){
						String text = "相同姓名";
						if(StringUtils.isNotBlank(userInfo.getSex()) && userInfo.getSex().equals(u.getSex()))
							text += ",相同性别";
						else if(StringUtils.isNotBlank(userInfo.getTelId()) && userInfo.getTelId().equals(u.getTelId()))
							text += ",相同手机号";
						else if(StringUtils.isNotBlank(userInfo.getEmail()) && userInfo.getEmail().equals(u.getEmail()))
							text += ",相同邮箱";
						else if(StringUtils.isNotBlank(userInfo.getCard()) && userInfo.getCard().equals(u.getCard()))
							text += ",相同身份证号";
						else if(StringUtils.isNotBlank(userInfo.getStudentnumber()) && userInfo.getStudentnumber().equals(u.getStudentnumber()))
							text += ",相同学号";
						else if(StringUtils.isNotBlank(userInfo.getAccountNum()) && userInfo.getAccountNum().equals(u.getAccountNum())){
							text += ",已被同一个用户认证";
						}
						u.setRemarks(text);
						resultList.add(u);
						if(code != 2){
							code = 1;
						}
					}else if(StringUtils.isNotBlank(userInfo.getAccountNum()) && userInfo.getAccountNum().equals(u.getAccountNum())){
						code = 2;
						u.setRemarks("已被同一个用户认证");
						resultList.add(u);
					}
				}
			}
		}else{
			code = -1;
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("code", code);
		resultMap.put("list", resultList);
		return resultMap;
	}

	/**
	 * 一键认证
	 * @param userId
	 * @return
	 */
	public Message oneKeyAuth(String userId){
		Message message = new Message();
		UserInfo userInfo = selectByUserId(userId);
		if(userInfo != null){
			if(StringUtils.isNotBlank(userInfo.getAccountNum())){
				message.init(false, userInfo.getUserName()+"校友已认证", null);
			}else{
				if(!"1".equals(userInfo.getCheckFlag())){
					message.init(false, userInfo.getUserName()+"非正式校友数据", null);
				}else{
					if(StringUtils.isBlank(userInfo.getTelId()) && StringUtils.isBlank(userInfo.getEmail())){
						message.init(false, userInfo.getUserName()+"校友手机邮箱都不存在，无法一键认证", null);
					}else{
						Map<String, String> map = new HashMap<>();
						if(StringUtils.isNotBlank(userInfo.getTelId()) ){
							map.put("phoneNum", userInfo.getTelId());
						}

						if(StringUtils.isNotBlank(userInfo.getEmail())){
							map.put("phoneNum", userInfo.getTelId());
						}

						UserProfile userProfile = new UserProfile();
						List<UserProfile> upList = userProfileMapper.selectByPhoneNumOrEmail(map);
						if(upList != null && upList.size()>0){
							userProfile = upList.get(0);
							String baseInfoId = userProfile.getBaseInfoId() == null ?"": userProfile.getBaseInfoId();
							String groupName = userProfile.getGroupName()==null?"": userProfile.getGroupName();
							String classes = userProfile.getClasses()==null?"":userProfile.getClasses();

							baseInfoId += StringUtils.isNotBlank(baseInfoId)?("," + userInfo.getUserId()): userInfo.getUserId();
							classes += StringUtils.isNotBlank(classes)?("," + userInfo.getClassId()): userInfo.getClassId();
							groupName += StringUtils.isNotBlank(groupName)? ("_" + userInfo.getFullName()):userInfo.getFullName();

							userProfile.setBaseInfoId(baseInfoId);
							userProfile.setGroupName(groupName);
							userProfile.setClasses(classes);
							userProfileMapper.update(userProfile);

							if("1".equals(userProfile.getIsOneKeyAuth()))
								userInfo.setIsOneKeyAuth("1");
							else
								userInfo.setIsOneKeyAuth("0");

							// 同步到userInfo表
							userInfo.setTelId(userProfile.getPhoneNum());
							userInfo.setUseTime(TimeZoneUtils.getDate());
							userInfo.setAccountNum(userProfile.getAccountNum());
							userInfo.setPicUrl(userProfile.getPictureUrl());
							userInfoMapper.updateAuthen2User(userInfo);
						}else{
							//没有就创建账号
							userProfile.setName(userInfo.getUserName());
							userProfile.setPhoneNum(userInfo.getTelId());
							userProfile.setEmail(userInfo.getEmail());
							if("男".equals(userInfo.getSex()))
								userProfile.setSex("0");
							else if("女".equals(userInfo.getSex()))
								userProfile.setSex("1");
							userProfile.setWorkUtil(userInfo.getWorkUnit());
							userProfile.setPosition(userInfo.getPosition());
							try{
								if(userInfo.getBirthday() != null && !"".equals(userInfo.getBirthday())){
									userProfile.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(userInfo.getBirthday()));
								}
							}catch (Exception e){
								System.out.println("---------->生日日期转换失败");
								throw new RuntimeException(e);
							}
							userProfile.setAddress(userInfo.getMailingAddress());
							userProfile.setAuthenticated("1");
							userProfile.setBaseInfoId(userInfo.getUserId());
							userProfile.setClasses(userInfo.getClassId());
							userProfile.setGroupName(userInfo.getFullName());
							userProfile.setIsOneKeyAuth("1");
							if(StringUtils.isNotBlank(userProfile.getPhoneNum())){
								if(userProfile.getPhoneNum().length() > 6){
									userProfile.setPassword(userProfile.getPhoneNum().substring(0, 6));
								}else{
									userProfile.setPassword(userProfile.getPhoneNum());
								}
							}else{
								userProfile.setPassword(userInfo.getNamePinyin());
							}
							userProfileMapper.save(userProfile);

							userInfo.setIsOneKeyAuth("1");
							userInfo.setAccountNum(userProfile.getAccountNum());
							userInfoMapper.updateAuthen2User(userInfo);
						}

						//一键入会的话也会顺带加进去
						authenticatedService.autoJoinAlumni(userId, userProfile.getAccountNum());

						// 验证班级环信群组是否存在,如不存在创建
						ChatDeptGroup deptGroup = new ChatDeptGroup() ;
						deptGroup.setDeptId(userInfo.getClassId());
						List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup) ;
						if(deptGroupList == null || deptGroupList.isEmpty()) {
							ChatGroup group = new ChatGroup() ;
							group.setName(userInfo.getClassName());
							group.setIntroduction(userInfo.getSchoolName() + "," + userInfo.getDepartName() + ","  + userInfo.getGradeName() + ","  + userInfo.getClassName());
							group.setType("1");
							chatGroupService.insert(group);
							deptGroup.preInsert();
							deptGroup.setGroupId(group.getId());
							chatDeptGroupMapper.insert(deptGroup);
						} else {
							deptGroup = deptGroupList.get(0) ;
						}

						// 将认证对象添加到群组中
						chatGroupService.addMemberToGroup(deptGroup.getGroupId(),userProfile.getAccountNum()) ;
						message.init(true, "操作成功", null);
					}
				}
			}
		}else{
			message.init(false, "不存在的校友", null);
		}
		System.out.println("message---------------------->"+message.toString());
		return message;
	}
	/**
	 * 一键认证
	 * @param userId
	 * @return
	 */
	public Message oneKeyAuth(String userId,String phoneNum){
		Message message = new Message();
		UserInfo userInfo = selectByUserId(userId);
		if(userInfo != null){
			if(StringUtils.isNotBlank(userInfo.getAccountNum())){
				message.init(false, userInfo.getUserName()+"校友已认证", null);
			}else{
				if(!"1".equals(userInfo.getCheckFlag())){
					message.init(false, userInfo.getUserName()+"非正式校友数据", null);
				}else{
					if(StringUtils.isBlank(phoneNum)){
						message.init(false, userInfo.getUserName()+"校友手机邮箱都不存在，无法一键认证", null);
					}else{
						userInfo.setTelId(phoneNum);
						Map<String, String> map = new HashMap<>();
						if(StringUtils.isNotBlank(userInfo.getTelId()) ){
							map.put("phoneNum", userInfo.getTelId());
						}

						if(StringUtils.isNotBlank(userInfo.getEmail())){
							map.put("phoneNum", userInfo.getTelId());
						}

						UserProfile userProfile = new UserProfile();
						List<UserProfile> upList = userProfileMapper.selectByPhoneNumOrEmail(map);
						if(upList != null && upList.size()>0){
							userProfile = upList.get(0);
							String baseInfoId = userProfile.getBaseInfoId() == null ?"": userProfile.getBaseInfoId();
							String groupName = userProfile.getGroupName()==null?"": userProfile.getGroupName();
							String classes = userProfile.getClasses()==null?"":userProfile.getClasses();

							baseInfoId += StringUtils.isNotBlank(baseInfoId)?("," + userInfo.getUserId()): userInfo.getUserId();
							classes += StringUtils.isNotBlank(classes)?("," + userInfo.getClassId()): userInfo.getClassId();
							groupName += StringUtils.isNotBlank(groupName)? ("_" + userInfo.getFullName()):userInfo.getFullName();

							userProfile.setBaseInfoId(baseInfoId);
							userProfile.setGroupName(groupName);
							userProfile.setClasses(classes);
							userProfileMapper.update(userProfile);

							if("1".equals(userProfile.getIsOneKeyAuth()))
								userInfo.setIsOneKeyAuth("1");
							else
								userInfo.setIsOneKeyAuth("0");

							// 同步到userInfo表
							userInfo.setTelId(userProfile.getPhoneNum());
							userInfo.setUseTime(TimeZoneUtils.getDate());
							userInfo.setAccountNum(userProfile.getAccountNum());
							userInfo.setPicUrl(userProfile.getPictureUrl());
							userInfoMapper.updateAuthen2User(userInfo);
						}else{
							//没有就创建账号
							userProfile.setName(userInfo.getUserName());
							userProfile.setPhoneNum(userInfo.getTelId());
							userProfile.setEmail(userInfo.getEmail());
							if("男".equals(userInfo.getSex()))
								userProfile.setSex("0");
							else if("女".equals(userInfo.getSex()))
								userProfile.setSex("1");
							userProfile.setWorkUtil(userInfo.getWorkUnit());
							userProfile.setPosition(userInfo.getPosition());
							try{
								if(userInfo.getBirthday() != null && !"".equals(userInfo.getBirthday())){
									userProfile.setBirthday(new SimpleDateFormat("yyyy-MM-dd").format(userInfo.getBirthday()));
								}
							}catch (Exception e){
								System.out.println("---------->生日日期转换失败");
								throw new RuntimeException(e);
							}
							userProfile.setAddress(userInfo.getMailingAddress());
							userProfile.setAuthenticated("1");
							userProfile.setBaseInfoId(userInfo.getUserId());
							userProfile.setClasses(userInfo.getClassId());
							userProfile.setGroupName(userInfo.getFullName());
							userProfile.setIsOneKeyAuth("1");
							if(StringUtils.isNotBlank(userProfile.getPhoneNum())){
								if(userProfile.getPhoneNum().length() > 6){
									userProfile.setPassword(userProfile.getPhoneNum().substring(0, 6));
								}else{
									userProfile.setPassword(userProfile.getPhoneNum());
								}
							}else{
								userProfile.setPassword(userInfo.getNamePinyin());
							}
							userProfileMapper.save(userProfile);

							userInfo.setIsOneKeyAuth("1");
							userInfo.setAccountNum(userProfile.getAccountNum());
							userInfoMapper.updateAuthen2User(userInfo);
						}

						//一键入会的话也会顺带加进去
						authenticatedService.autoJoinAlumni(userId, userProfile.getAccountNum());

						// 验证班级环信群组是否存在,如不存在创建
						ChatDeptGroup deptGroup = new ChatDeptGroup() ;
						deptGroup.setDeptId(userInfo.getClassId());
						List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup) ;
						if(deptGroupList == null || deptGroupList.isEmpty()) {
							ChatGroup group = new ChatGroup() ;
							group.setName(userInfo.getClassName());
							group.setIntroduction(userInfo.getSchoolName() + "," + userInfo.getDepartName() + ","  + userInfo.getGradeName() + ","  + userInfo.getClassName());
							group.setType("1");
							chatGroupService.insert(group);
							deptGroup.preInsert();
							deptGroup.setGroupId(group.getId());
							chatDeptGroupMapper.insert(deptGroup);
						} else {
							deptGroup = deptGroupList.get(0) ;
						}

						// 将认证对象添加到群组中
						chatGroupService.addMemberToGroup(deptGroup.getGroupId(),userProfile.getAccountNum()) ;
						message.init(true, "操作成功", null);
					}
				}
			}
		}else{
			message.init(false, "不存在的校友", null);
		}
		System.out.println("message---------------------->"+message.toString());
		return message;
	}

	/**
	 * 獲取同班同學
	 * @param userId
	 * @return
	 */
	public List<Map<String, String>> findClassMates(String userId){
		UserInfo currentUser = userInfoMapper.selectByUserId(userId);

		List<UserInfo> userInfoList = userInfoMapper.selectClassmates(userId);
		boolean currentIsAdmin = false;
		if(StringUtils.isNotBlank(currentUser.getIsClassAdmin()) && currentUser.getIsClassAdmin().equals("1")){
			currentIsAdmin = true;
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (userInfoList != null && userInfoList.size() > 0) {
			List<String> listTmp = new ArrayList<>();
			for(UserInfo us:userInfoList){
				if(StringUtils.isNotBlank(us.getAccountNum()))
					listTmp.add(us.getAccountNum());
			}
			Map<String , Object> mapTmp = new HashMap<>();
			mapTmp.put("list", listTmp);
			mapTmp.put("accountNum", currentUser.getAccountNum());
			List<Map<String, String>> friendsStatusList = userInfoMapper.selectFriendsStatusInClassmates(mapTmp);


			List<Map<String, String>> list2 = new ArrayList<>();
			for (int i = 0; i < userInfoList.size(); ++i){
				UserInfo userInfo = userInfoList.get(i);
				Map<String, String> tmp = new HashMap<>();

				tmp.put("userId", userInfo.getUserId());
				tmp.put("sex", userInfo.getSex());
				tmp.put("userName", userInfo.getUserName());

				if(StringUtils.isNotBlank(userInfo.getTelId())){
					if(currentIsAdmin)
						tmp.put("phoneNum", userInfo.getTelId());
					else if(userInfo.getTelId().length() >  4)
						tmp.put("phoneNum", "*******"+userInfo.getTelId().substring( userInfo.getTelId().length() - 4, userInfo.getTelId().length() ));
				}

				tmp.put("pictureRT", userInfo.getPicUrl());
				if(StringUtils.isNotBlank(userInfo.getPicUrl()) && StringUtils.isNotBlank(Global.URL_DOMAIN) && userInfo.getPicUrl().indexOf("http") < 0){
					tmp.put("picture", Global.URL_DOMAIN + userInfo.getPicUrl());
				}else{
					tmp.put("picture", userInfo.getPicUrl());
				}
				tmp.put("picture_xd", userInfo.getPicUrl_xd());
				tmp.put("accountNum", userInfo.getAccountNum());
				tmp.put("isClassAdmin", userInfo.getIsClassAdmin());

				if(userId.equals(userInfo.getUserId()))
					tmp.put("isCurrent", "1");

				if(StringUtils.isNotBlank(userInfo.getAccountNum())){
					tmp.put("accountNum", userInfo.getAccountNum());
					//查詢好友狀態
					for(Map<String, String> fs:friendsStatusList){
						if(userInfo.getAccountNum().equals(fs.get("friend_id"))){
							tmp.put("friendStatus",fs.get("status"));
							tmp.put("appFlag",fs.get("applicant_flag"));
						}
					}
					list.add(tmp);
				}else{
					list2.add(tmp);
				}
			}
			list.addAll(list2);
		}
		return list;
	}


	//根据手机号查询用户并做一些提示和警告
	public List<UserInfo> selectUserByTelePhone(String phoneNumber){
		return userInfoMapper.selectUserByTelePhone(phoneNumber);
	}

}