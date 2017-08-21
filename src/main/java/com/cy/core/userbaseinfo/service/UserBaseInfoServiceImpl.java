package com.cy.core.userbaseinfo.service;

import com.cy.base.entity.DataGrid;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dicttype.entity.DictType;
import com.cy.core.major.dao.MajorMapper;
import com.cy.core.major.entity.Major;
import com.cy.core.major.entity.MajorDept;
import com.cy.core.mobileLocal.entity.MobileLocal;
import com.cy.core.mobileLocal.service.MobileLocalService;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userbaseinfo.dao.UserBaseInfoMapper;
import com.cy.core.userbaseinfo.entity.UserBaseInfo;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.system.*;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl implements UserBaseInfoService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserBaseInfoServiceImpl.class);

	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private MajorMapper majorMapper;

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private MobileLocalService mobileLocalService;




	/** start liuzhen **/

	/**
	 * 设置班级管理员
	 * @param map
	 */
	public void updateClassAdmin(Map<String,String> map) {
		userBaseInfoMapper.updateClassAdmin(map);
	}

	/** end liuzhen **/

	public DataGrid<UserBaseInfo> selectByDeptId(Map<String, Object> map) {
		DataGrid<UserBaseInfo> dataGrid = new DataGrid<UserBaseInfo>();
		long total = userBaseInfoMapper.countByDeptId(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<UserBaseInfo> list = userBaseInfoMapper.selectByDeptId(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public void delete(String ids) {
		try {
			String[] array = ids.split(",");
			List<String> list = new ArrayList<String>();
			Map<String, String> map = new HashMap<String, String>();
			for (String id : array) {
				list.add(id);
				// 将userprofile表的关联字段清掉
				UserProfile userProfile = userProfileMapper.selectByBaseInfoId(id);
				if (userProfile != null) {
//					String baseInfoId = "";
//					String groupName = "";
//					if (userProfile.getBaseInfoId() != null && userProfile.getBaseInfoId().length() > 0) {
//						String[] idArray = userProfile.getBaseInfoId().split(",");
//						for (String baseId : idArray) {
//							if (!baseId.equals(id)) {
//								baseInfoId += baseId + ",";
//							}
//						}
//						if (baseInfoId.length() > 0) {
//							baseInfoId = baseInfoId.substring(0, baseInfoId.length() - 1);
//						}
//					}
//					if (userProfile.getGroupName() != null && userProfile.getGroupName().length() > 0) {
//						String[] groupArray = userProfile.getGroupName().split(",");
//						for (String group : groupArray) {
//							if (!group.equals(id.substring(0, 16))) {
//								groupName += group + ",";
//							}
//						}
//						if (groupName.length() > 0) {
//							groupName = groupName.substring(0, groupName.length() - 1);
//						}
//					}
//					map.put("baseInfoId", baseInfoId);
//					map.put("accountNum", userProfile.getAccountNum());
//					map.put("groupName", groupName);
//					userProfileMapper.clearBaseInfoId(map);


					Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
					if(baseMap == null) {
						baseMap = Maps.newHashMap() ;
					}
					baseMap.put("accountNum", userProfile.getAccountNum()) ;
					userProfileMapper.clearBaseInfoId(baseMap);
				}
			}
			userBaseInfoMapper.delete(list);
			userInfoMapper.delete(list);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void save(UserBaseInfo UserBaseInfo, User user, int isInput) {
		try {
			if (isInput == 0 && UserBaseInfo.getClassId() != null && !UserBaseInfo.getClassId().equals("")) {
				List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(UserBaseInfo.getClassId());
				List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(UserBaseInfo.getClassId());
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
					throw new RuntimeException("同一班级人数超过最大限制(999人)。");
				}
				String userId = UserBaseInfo.getClassId() + uId;
				UserBaseInfo.setUserId(userId);
				UserBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(UserBaseInfo.getUserName()));
				UserBaseInfo.setCreateTime(new Date());
				userBaseInfoMapper.save(UserBaseInfo);
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(UserBaseInfo, userInfo);
				userInfo.setCheckFlag("1");
				userInfoMapper.save(userInfo);
			} else if (isInput == 1 && UserBaseInfo.getSchoolName() != null && !UserBaseInfo.getSchoolName().equals("") && UserBaseInfo.getDepartName() != null
					&& !UserBaseInfo.getDepartName().equals("") && UserBaseInfo.getGradeName() != null && !UserBaseInfo.getGradeName().equals("")
					&& UserBaseInfo.getClassName() != null && !UserBaseInfo.getClassName().equals("")) {
				Map<String, Object> map = new HashMap<String, Object>();
				Dept depart = null;
				if (user.getDepts() == null || user.getDepts().size() == 0) {
					// 查找学校
					map.put("parentId", "0");
					map.put("deptName", UserBaseInfo.getSchoolName());

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
						school.setFullName(UserBaseInfo.getSchoolName());
						school.setDeptName(UserBaseInfo.getSchoolName());
						school.setLevel(4);
						school.setParentId("0");
						school.setStatus("20");
						deptMapper.insert(school);
					}

					// 院系
					map.put("parentId", school.getDeptId());
					map.put("deptName", UserBaseInfo.getDepartName());
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
						depart.setFullName(school.getFullName() + "," + UserBaseInfo.getDepartName());
						depart.setDeptName(UserBaseInfo.getDepartName());
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
						} else if (dept.getDeptId().length() == 10) {
							departSet.add(dept.getDeptId());
						}
					}

					map.put("parentId", "0");
					map.put("deptName", UserBaseInfo.getSchoolName());

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
						map.put("deptName", UserBaseInfo.getDepartName());
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
							depart.setFullName(school.getFullName() + "," + UserBaseInfo.getDepartName());
							depart.setDeptName(UserBaseInfo.getDepartName());
							depart.setLevel(4);
							depart.setParentId(school.getDeptId());
							depart.setStatus("20");
							deptMapper.insert(depart);
						}
					} else {
						map.put("parentId", school.getDeptId());
						map.put("deptName", UserBaseInfo.getDepartName());
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
				Major major = majorMapper.getByName(UserBaseInfo.getMajorName());
				if (major == null) {
					major = new Major();
					major.setMajorName(UserBaseInfo.getMajorName());
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
				map.put("deptName", UserBaseInfo.getGradeName() + "级");
				Dept grade = deptMapper.selectByNameAndParentId(map);
				if (grade == null) {
					grade = new Dept();
					String deptId = depart.getDeptId() + UserBaseInfo.getGradeName();
					grade.setDeptId(deptId);
					grade.setCreateTime(new Date());
					grade.setFullName(depart.getFullName() + "," + UserBaseInfo.getGradeName() + "级");
					grade.setDeptName(UserBaseInfo.getGradeName() + "级");
					grade.setLevel(4);
					grade.setParentId(depart.getDeptId());
					grade.setStatus("20");
					deptMapper.insert(grade);
				}

				// 班级
				map.put("parentId", grade.getDeptId());
				map.put("deptName", UserBaseInfo.getClassName());
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
					String fullName = grade.getFullName() + "," + UserBaseInfo.getClassName();
					class1.setFullName(fullName);
					class1.setLevel(4);
					class1.setParentId(grade.getDeptId());
					class1.setDeptName(UserBaseInfo.getClassName());
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
				UserBaseInfo.setUserId(userId);
				UserBaseInfo.setMajorId(major.getMajorId());
				UserBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(UserBaseInfo.getUserName()));
				UserBaseInfo.setCreateTime(new Date());
				userBaseInfoMapper.save(UserBaseInfo);
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(UserBaseInfo, userInfo);
				userInfo.setCheckFlag("1");
				userInfoMapper.save(userInfo);
			}
		} catch (Exception e) {
			logger.error(e, e);
			throw new RuntimeException("");
		}
	}

	public UserBaseInfo selectByUserId(String userId) {
		UserBaseInfo userBaseInfo = userBaseInfoMapper.selectByUserId(userId);
		if (userBaseInfo !=null && StringUtils.isNotBlank(userBaseInfo.getTelId())&& userBaseInfo.getTelId().length()>7){
			MobileLocal mobileLocal = mobileLocalService.selectByMobileNumber(userBaseInfo.getTelId().substring(0,7));
			if (mobileLocal !=null ){
				userBaseInfo.setAttributionInquiry(mobileLocal.getMobileArea());
			}
		}
		UserInfo u = userInfoMapper.selectByUserId(userBaseInfo.getUserId());
		if (u != null && u.getAccountNum() != null && u.getAccountNum().length() > 0){
			UserProfile userProfile = userProfileMapper.selectByAccountNum(u.getAccountNum());
			if(userProfile == null){
				u.setAccountNum("");
				userInfoMapper.update(u);
			}
			userBaseInfo.setAccountNum(u.getAccountNum());
		}

		return userBaseInfo;
	}

	public String importData(String url, User user) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;
			url = savePath + url.replace(saveUrl, "");
			File file = new File(url);
			List<Object[]> list = ExcelUtil.parseExcel(file);
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

			List<UserBaseInfo> UserBaseInfoList = userBaseInfoMapper.selectAll();
			List<UserInfo> uList = userInfoMapper.selectAll();
			// 每个班级下的所有学生，key为班级编号，value为学生集合
			Map<String, List<UserInfo>> classStudentNameMap = new ConcurrentHashMap<String, List<UserInfo>>();
			Map<String, List<UserBaseInfo>> classBaseStudentNameMap = new ConcurrentHashMap<String, List<UserBaseInfo>>();
			// 班级编号与学生扩展位的对应关系,key为学生编号，value为一个班中，所有学生的扩展位
			Map<String, Set<String>> classStudentIdMap = new ConcurrentHashMap<String, Set<String>>();
			// 所填数据包含数据字典，与数据字典校验
			@SuppressWarnings("unchecked")
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

			if (UserBaseInfoList != null && UserBaseInfoList.size() > 0) {
				for (UserBaseInfo UserBaseInfo : UserBaseInfoList) {
					Set<String> set = null;
					List<UserBaseInfo> userList = null;
					String classId = UserBaseInfo.getUserId().substring(0, 16);
					if (classStudentIdMap.containsKey(classId)) {
						set = classStudentIdMap.get(classId);
						set.add(UserBaseInfo.getUserId().substring(16, 19));
					} else {
						set = new HashSet<String>();
						set.add(UserBaseInfo.getUserId().substring(16, 19));
					}
					classStudentIdMap.put(classId, set);

					if (classBaseStudentNameMap.containsKey(classId)) {
						userList = classBaseStudentNameMap.get(classId);
						userList.add(UserBaseInfo);
					} else {
						userList = new ArrayList<UserBaseInfo>();
						userList.add(UserBaseInfo);
					}
					classBaseStudentNameMap.put(classId, userList);
				}
			}
			if (uList != null && uList.size() > 0) {
				for (UserInfo userInfo : uList) {
					Set<String> set = null;
					List<UserInfo> userList = null;
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
						} else {
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
								Dept depart = deptNameAndParentIdMap.get(departName + " " + school.getDeptId());
								if (depart == null) {
									depart = new Dept();
									Set<String> set = ParentIdAndExtendIdMap.get(school.getDeptId());
									if (set == null) {
										set = new HashSet<String>();
									}
									String departId = IdUtil.getDepart(set);
									if (departId.equals("")) {

										//throw new RuntimeException("院系数据异常，院系已超过1000个");
										/*Lixun 2017-5-11 改造出错信息*/
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "院系数据异常，院系已超过1000个";
											}
										}
										errorList.add(content);
										continue;
										/*Lixun*/
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
										//throw new RuntimeException("班级数据异常，同年级班级已超过100个");
										/*Lixun 2017-5-11 改造出错信息*/
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "班级数据异常，同年级班级已超过100个";
											}
										}
										errorList.add(content);
										continue;
										/*Lixun*/
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

								UserBaseInfo userBaseInfo = new UserBaseInfo();

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
								for (UserInfo userInfo : userList) {
									String birth = "";
									String entranceDate = "";
									String sType = "";
									String pLength = "";
									String cType = "";
									if (userInfo.getBirthday() != null) {
										birth = dateFormat.format(userInfo.getBirthday());
									}
									if (userInfo.getEntranceTime() != null) {
										entranceDate = dateFormat.format(userInfo.getEntranceTime());
									}
									if (studentTypeList.contains(studentType)) {
										sType = studentType;
									}
									if (programLenthList.contains(programLength)) {
										pLength = programLength;
									}
									if (cardTypeList.contains(cardType)) {
										cType = cardType;
									}
									if (studentNumber.equals(userInfo.getStudentnumber()) && userName.equals(userInfo.getUserName())
											&& aliasName.equals(userInfo.getAliasname()) && className.equals(userInfo.getClassName())
											&& majorName.equals(userInfo.getMajorName()) && cType.equals(userInfo.getCardType())
											&& card.equals(userInfo.getCard()) && birthday.equals(birth) && nation.equals(userInfo.getNation())
											&& nationality.equals(userInfo.getNationality()) && sex.equals(userInfo.getSex())
											&& status.equals(userInfo.getStatus()) && resourceArea.equals(userInfo.getResourceArea())
											&& email.equals(userInfo.getEmail()) && residentialArea.equals(userInfo.getResidentialArea())
											&& residentialTel.equals(userInfo.getResidentialTel()) && telId.equals(userInfo.getTelId())
											&& entranceTime.equals(entranceDate) && pLength.equals(userInfo.getProgramLength())
											&& sType.equals(userInfo.getStudentType())) {
										hasIn = true;
										break;
									}
								}

								for (UserBaseInfo userInfo : userBaseList) {
									String birth = "";
									String entranceDate = "";
									String sType = "";
									String pLength = "";
									String cType = "";
									if (userInfo.getBirthday() != null) {
										birth = dateFormat.format(userInfo.getBirthday());
									}
									if (userInfo.getEntranceTime() != null) {
										entranceDate = dateFormat.format(userInfo.getEntranceTime());
									}
									if (studentTypeList.contains(studentType)) {
										sType = studentType;
									}
									if (programLenthList.contains(programLength)) {
										pLength = programLength;
									}
									if (cardTypeList.contains(cardType)) {
										cType = cardType;
									}
									if (studentNumber.equals(userInfo.getStudentnumber()) && userName.equals(userInfo.getUserName())
											&& aliasName.equals(userInfo.getAliasname()) && className.equals(userInfo.getClassName())
											&& majorName.equals(userInfo.getMajorName()) && cType.equals(userInfo.getCardType())
											&& card.equals(userInfo.getCard()) && birthday.equals(birth) && nation.equals(userInfo.getNation())
											&& nationality.equals(userInfo.getNationality()) && sex.equals(userInfo.getSex())
											&& status.equals(userInfo.getStatus()) && resourceArea.equals(userInfo.getResourceArea())
											&& email.equals(userInfo.getEmail()) && residentialArea.equals(userInfo.getResidentialArea())
											&& residentialTel.equals(userInfo.getResidentialTel()) && telId.equals(userInfo.getTelId())
											&& entranceTime.equals(entranceDate) && pLength.equals(userInfo.getProgramLength())
											&& sType.equals(userInfo.getStudentType())) {
										hasBaseIn = true;
										break;
									}
								}

								if (!hasBaseIn && !hasIn) {
									String uId = IdUtil.getUserId(set);
									if (uId.equals("")) {
										//throw new RuntimeException("学生数据异常，同一班级下，学生数量超过1000位");
										/*Lixun 2017-5-11 改造出错信息*/
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "学生数据异常，同一班级下，学生数量超过999位";
											}
										}
										errorList.add(content);
										continue;
										/*Lixun*/
									}
									userBaseInfo.setUserId(class1.getDeptId() + uId);
									userBaseInfo.setUserName(userName);
									userBaseInfo.setAliasname(aliasName);
									userBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(userName));
									userBaseInfo.setCreateTime(new Date());
									userBaseInfo.setStudentnumber(studentNumber);
									try {
										SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
										if (entranceTime != null && !"".equals(entranceTime)) {
											Date date = fmt1.parse(entranceTime);
											userBaseInfo.setEntranceTime(date);
										}
									} catch (Exception e) {
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "入学日期数据无法转换";
											}
										}
										errorList.add(content);
										continue;
									}
									if (major != null) {
										userBaseInfo.setMajorId(major.getMajorId());
									}
									userBaseInfo.setSex(sex);
									userBaseInfo.setNation(nation);
									userBaseInfo.setNationality(nationality);
									if (studentTypeList.contains(studentType)) {
										userBaseInfo.setStudentType(studentType);
									} else {
										userBaseInfo.setStudentType("");
									}
									if (programLenthList.contains(programLength)) {
										userBaseInfo.setProgramLength(programLength);
									} else {
										userBaseInfo.setProgramLength("");
									}
									if (cardTypeList.contains(cardType)) {
										userBaseInfo.setCardType(cardType);
									} else {
										userBaseInfo.setCardType("");
									}
									userBaseInfo.setCard(card);
									userBaseInfo.setEmail(email);
									userBaseInfo.setResidentialArea(residentialArea);
									userBaseInfo.setResidentialTel(residentialTel);
									userBaseInfo.setTelId(telId);
									userBaseInfo.setResourceArea(resourceArea);
									userBaseInfo.setStatus(status);
									try {
										SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
										if (birthday != null && !"".equals(birthday)) {
											Date date = fmt.parse(birthday);
											userBaseInfo.setBirthday(date);
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
									userBaseInfoMapper.save(userBaseInfo);
									// 保存成功后刷新classStudentNameMap，classStudentIdMap
									userBaseList.add(userBaseInfo);
									set.add(uId);
									classBaseStudentNameMap.put(class1.getDeptId(), userBaseList);
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

							} else {

								Dept school = deptNameAndParentIdMap.get(schoolName + " " + "0");
								if (school == null) {
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "非权限范围内的学校名称";
										}
									}
									errorList.add(content);
									continue;
								}
								// 院系
								Dept depart = deptNameAndParentIdMap.get(departName + " " + school.getDeptId());
								if (depart == null) {
									depart = new Dept();
									Set<String> set = ParentIdAndExtendIdMap.get(school.getDeptId());
									if (set == null) {
										set = new HashSet<String>();
									}
									String departId = IdUtil.getDepart(set);
									if (departId.equals("")) {
										//throw new RuntimeException("院系数据异常，院系已超过1000个");
										/*Lixun 2017-5-11 改造出错信息*/
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "院系数据异常，院系已超过1000个";
											}
										}
										errorList.add(content);
										continue;
										/*Lixun*/
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

								if (schoolSet.size() > 0 && !schoolSet.contains(depart.getDeptId().substring(0, 6))) {
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "非权限范围内的院系名称";
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
											content[j] = "非权限范围内的院系名称";
										}
									}
									errorList.add(content);
									continue;
								}

								// 专业
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
										majorAndDeptMap.put("deptId", depart.getDeptId());
										majorAndDeptMap.put("majorId", major.getMajorId());
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
										//throw new RuntimeException("班级数据异常，同年级班级已超过100个");
										/*Lixun 2017-5-11 改造出错信息*/
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "班级数据异常，同年级班级已超过100个";
											}
										}
										errorList.add(content);
										continue;
										/*Lixun*/
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

								UserBaseInfo userBaseInfo = new UserBaseInfo();

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
								for (UserInfo userInfo : userList) {
									String birth = "";
									String entranceDate = "";
									String sType = "";
									String pLength = "";
									String cType = "";
									if (userInfo.getBirthday() != null) {
										birth = dateFormat.format(userInfo.getBirthday());
									}
									if (userInfo.getEntranceTime() != null) {
										entranceDate = dateFormat.format(userInfo.getEntranceTime());
									}
									if (studentTypeList.contains(studentType)) {
										sType = studentType;
									}
									if (programLenthList.contains(programLength)) {
										pLength = programLength;
									}
									if (cardTypeList.contains(cardType)) {
										cType = cardType;
									}
									if (studentNumber.equals(userInfo.getStudentnumber()) && userName.equals(userInfo.getUserName())
											&& aliasName.equals(userInfo.getAliasname()) && className.equals(userInfo.getClassName())
											&& majorName.equals(userInfo.getMajorName()) && cType.equals(userInfo.getCardType())
											&& card.equals(userInfo.getCard()) && birthday.equals(birth) && nation.equals(userInfo.getNation())
											&& nationality.equals(userInfo.getNationality()) && sex.equals(userInfo.getSex())
											&& status.equals(userInfo.getStatus()) && resourceArea.equals(userInfo.getResourceArea())
											&& email.equals(userInfo.getEmail()) && residentialArea.equals(userInfo.getResidentialArea())
											&& residentialTel.equals(userInfo.getResidentialTel()) && telId.equals(userInfo.getTelId())
											&& entranceTime.equals(entranceDate) && pLength.equals(userInfo.getProgramLength())
											&& sType.equals(userInfo.getStudentType())) {
										hasIn = true;
										break;
									}
								}

								for (UserBaseInfo userInfo : userBaseList) {
									String birth = "";
									String entranceDate = "";
									String sType = "";
									String pLength = "";
									String cType = "";
									if (userInfo.getBirthday() != null) {
										birth = dateFormat.format(userInfo.getBirthday());
									}
									if (userInfo.getEntranceTime() != null) {
										entranceDate = dateFormat.format(userInfo.getEntranceTime());
									}
									if (studentTypeList.contains(studentType)) {
										sType = studentType;
									}
									if (programLenthList.contains(programLength)) {
										pLength = programLength;
									}
									if (cardTypeList.contains(cardType)) {
										cType = cardType;
									}
									if (studentNumber.equals(userInfo.getStudentnumber()) && userName.equals(userInfo.getUserName())
											&& aliasName.equals(userInfo.getAliasname()) && className.equals(userInfo.getClassName())
											&& majorName.equals(userInfo.getMajorName()) && cType.equals(userInfo.getCardType())
											&& card.equals(userInfo.getCard()) && birthday.equals(birth) && nation.equals(userInfo.getNation())
											&& nationality.equals(userInfo.getNationality()) && sex.equals(userInfo.getSex())
											&& status.equals(userInfo.getStatus()) && resourceArea.equals(userInfo.getResourceArea())
											&& email.equals(userInfo.getEmail()) && residentialArea.equals(userInfo.getResidentialArea())
											&& residentialTel.equals(userInfo.getResidentialTel()) && telId.equals(userInfo.getTelId())
											&& entranceTime.equals(entranceDate) && pLength.equals(userInfo.getProgramLength())
											&& sType.equals(userInfo.getStudentType())) {
										hasBaseIn = true;
										break;
									}
								}

								if (!hasBaseIn && !hasIn) {
									String uId = IdUtil.getUserId(set);
									if (uId.equals("")) {
										//throw new RuntimeException("学生数据异常，同一班级下，学生数量超过1000位");
										/*Lixun 2017-5-11 改造出错信息*/
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "学生数据异常，同一班级下，学生数量超过999位";
											}
										}
										errorList.add(content);
										continue;
										/*Lixun*/
									}
									userBaseInfo.setUserId(class1.getDeptId() + uId);
									userBaseInfo.setUserName(userName);
									userBaseInfo.setAliasname(aliasName);
									userBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(userName));
									userBaseInfo.setCreateTime(new Date());
									userBaseInfo.setStudentnumber(studentNumber);
									try {
										SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
										if (entranceTime != null && !"".equals(entranceTime)) {
											Date date = fmt1.parse(entranceTime);
											userBaseInfo.setEntranceTime(date);
										}
									} catch (Exception e) {
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "入学日期数据无法转换";
											}
										}
										errorList.add(content);
										continue;
									}
									if (major != null) {
										userBaseInfo.setMajorId(major.getMajorId());
									}
									userBaseInfo.setSex(sex);
									userBaseInfo.setNation(nation);
									userBaseInfo.setNationality(nationality);
									if (studentTypeList.contains(studentType)) {
										userBaseInfo.setStudentType(studentType);
									} else {
										userBaseInfo.setStudentType("");
									}
									if (programLenthList.contains(programLength)) {
										userBaseInfo.setProgramLength(programLength);
									} else {
										userBaseInfo.setProgramLength("");
									}
									if (cardTypeList.contains(cardType)) {
										userBaseInfo.setCardType(cardType);
									} else {
										userBaseInfo.setCardType("");
									}
									userBaseInfo.setCard(card);
									userBaseInfo.setEmail(email);
									userBaseInfo.setResidentialArea(residentialArea);
									userBaseInfo.setResidentialTel(residentialTel);
									userBaseInfo.setTelId(telId);
									userBaseInfo.setResourceArea(resourceArea);
									userBaseInfo.setStatus(status);
									try {
										SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
										if (birthday != null && !"".equals(birthday)) {
											Date date = fmt.parse(birthday);
											userBaseInfo.setBirthday(date);
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
									userBaseInfoMapper.save(userBaseInfo);
									// 保存成功后刷新classStudentNameMap，classStudentIdMap
									userBaseList.add(userBaseInfo);
									set.add(uId);
									classBaseStudentNameMap.put(class1.getDeptId(), userBaseList);
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
			}
			System.gc();
			return ExcelUtil.exportData(errorList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<UserBaseInfo> selectAllUserList() {
		return userBaseInfoMapper.selectUserToGetTel();
	}

	public List<UserBaseInfo> selectUserByClassIdAndName(String userName, String classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("classId", classId);
		return userBaseInfoMapper.selectUserByClassIdAndName(map);
	}

	public void updateUserAccountNum(UserBaseInfo UserBaseInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", UserBaseInfo.getUserId());
		map.put("accountNum", UserBaseInfo.getAccountNum());
		userBaseInfoMapper.updateUserAccountNum(map);
	}

	public UserBaseInfo selectAllProByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userBaseInfoMapper.selectAllProByUserId(map);
	}

	public List<UserBaseInfo> selectUserByClassId(String classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", classId);
		return userBaseInfoMapper.selectAllUserByClassId(map);
	}

	public UserBaseInfo selectUserBaseInfoByGmidAndName(Map<String, Object> map) {
		return userBaseInfoMapper.selectUserInfo(map);
	}

	public int updateUserTelId(Map<String, Object> map, UserBaseInfo UserBaseInfo) {
		if (UserBaseInfo == null) {
			return -8;
		}

		String submitTelId = map.get("telId").toString();
		String telId = UserBaseInfo.getTelId();
		// System.out.println("-------------------------------------------------------");
		// System.out.println("基础数据表中的电话号码是"+telId);
		// System.out.println("提交的电话号码是"+submitTelId);
		// System.out.println("-------------------------------------------------------");
		if (telId == null || "".equals(telId)) {
			return userBaseInfoMapper.updateTelId(map);
		} else if (!submitTelId.equals(telId)) {
			if (UserBaseInfo.getUseTime() == null) {
				if (map.get("useTime") != null) {
					return userBaseInfoMapper.updateTelId(map);
				} else {
					// 返回1代表不做更新处理
					return -8;
				}
			} else if (UserBaseInfo.getUseTime().before((Date) map.get("useTime"))) {
				return userBaseInfoMapper.updateTelId(map);
			}
		}
		return -8;
	}

	public boolean selectUserInClass(String classId, List<String> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("classId", classId);
		long count = userBaseInfoMapper.selectUserInClass(map);
		if (count == list.size()) {
			return true;
		}
		return false;
	}

	public List<UserBaseInfo> selectByUserName(String userName, String deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		return userBaseInfoMapper.selectByUserName(map);
	}

	public List<UserBaseInfo> selectCard(List<String> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return userBaseInfoMapper.selectCard(map);
	}

	public void update(UserBaseInfo userBaseInfo, User user, int isInput) {
		try {
			UserInfo u = userInfoMapper.selectByUserId(userBaseInfo.getUserId());
			if (u != null){
				//如果认证过了，会同步到用户数据
				if (u.getAccountNum() != null && u.getAccountNum().length() > 0) {
					UserProfile userProfile = userProfileMapper.selectByAccountNum(u.getAccountNum());
					if(userProfile != null){
						userProfile.setName(userBaseInfo.getUserName());
						if("男".equals(userBaseInfo.getSex())){
							userProfile.setSex("0");
						}else if("女".equals(userBaseInfo.getSex())){
							userProfile.setSex("1");
						}
						userProfileMapper.update(userProfile);
					}else{
						u.setAccountNum("");
						userInfoMapper.update(u);
					}
				}
				// 更新归属地
				if(StringUtils.isNotBlank(userBaseInfo.getTelId()) && userBaseInfo.getTelId().length() == 11){
					MobileLocal mobileLocal = mobileLocalService.selectByMobileNumber(userBaseInfo.getTelId().substring(0, 7));
					if (mobileLocal != null) {
						u.setMobileLocal(mobileLocal.getMobileArea());
						userInfoMapper.updateMobileLocal(u);
					}
				}
			}


			if (isInput == 0 && userBaseInfo.getUserId().substring(0, 16).equals(userBaseInfo.getClassId())) {
				userBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(userBaseInfo.getUserName()));
				userBaseInfoMapper.update(userBaseInfo);
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(userBaseInfo, userInfo);
				userInfoMapper.update(userInfo);
				UserProfile userProfile = new UserProfile();
				userProfile.setName(userBaseInfo.getUserName());
				if (userBaseInfo.getSex() != null && userBaseInfo.getSex().equals("男")) {
					userProfile.setSex("0");
				} else {
					userProfile.setSex("1");
				}
				userProfile.setBaseInfoId(userBaseInfo.getUserId());
				userProfileMapper.updateBase(userProfile);
			} else if (isInput == 0 && userBaseInfo.getClassId() != null && !userBaseInfo.getClassId().equals("")) {
				// 机构ID更改
				List<UserBaseInfo> list = userBaseInfoMapper.selectAllByDeptId(userBaseInfo.getClassId());
				List<UserInfo> ulist = userInfoMapper.selectAllByDeptId(userBaseInfo.getClassId());
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
				String userId = userBaseInfo.getClassId() + uId;
				userBaseInfo.setNewUserId(userId);
				userBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(userBaseInfo.getUserName()));
				userBaseInfoMapper.update(userBaseInfo);
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(userBaseInfo, userInfo);
				userInfoMapper.update(userInfo);
				UserProfile userProfile = userProfileMapper.selectByBaseInfoId(userBaseInfo.getUserId());
				String baseInfoId = "";
				String groupName = "";
				if (userProfile != null) {
					String[] idArray = userProfile.getBaseInfoId().split(",");
					for (String baseId : idArray) {
						if (baseId.equals(userBaseInfo.getUserId())) {
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
						if (group.equals(userBaseInfo.getUserId().substring(0, 16))) {
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
				newUserProfile.setName(userBaseInfo.getUserName());
				if (userBaseInfo.getSex() != null && userBaseInfo.getSex().equals("男")) {
					newUserProfile.setSex("0");
				} else {
					newUserProfile.setSex("1");
				}
				newUserProfile.setBaseInfoId(userBaseInfo.getUserId());
				newUserProfile.setNewBaseInfoId(baseInfoId);
				newUserProfile.setGroupName(groupName);
				userProfileMapper.updateBase(newUserProfile);
			} else if (isInput == 1 && userBaseInfo.getSchoolName() != null && !userBaseInfo.getSchoolName().equals("") && userBaseInfo.getDepartName() != null
					&& !userBaseInfo.getDepartName().equals("") && userBaseInfo.getGradeName() != null && !userBaseInfo.getGradeName().equals("")
					&& userBaseInfo.getClassName() != null && !userBaseInfo.getClassName().equals("")) {
				// 手动输入
				Map<String, Object> map = new HashMap<String, Object>();
				Dept depart = null;
				if (user.getDepts() == null || user.getDepts().size() == 0) {
					// 查找学校
					map.put("parentId", "0");
					map.put("deptName", userBaseInfo.getSchoolName());

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
						school.setFullName(userBaseInfo.getSchoolName());
						school.setDeptName(userBaseInfo.getSchoolName());
						school.setLevel(4);
						school.setParentId("0");
						school.setStatus("20");
						deptMapper.insert(school);
					}

					// 院系
					map.put("parentId", school.getDeptId());
					map.put("deptName", userBaseInfo.getDepartName());
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
						depart.setFullName(school.getFullName() + "," + userBaseInfo.getDepartName());
						depart.setDeptName(userBaseInfo.getDepartName());
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
					map.put("deptName", userBaseInfo.getSchoolName());

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
						map.put("deptName", userBaseInfo.getDepartName());
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
							depart.setFullName(school.getFullName() + "," + userBaseInfo.getDepartName());
							depart.setDeptName(userBaseInfo.getDepartName());
							depart.setLevel(4);
							depart.setParentId(school.getDeptId());
							depart.setStatus("20");
							deptMapper.insert(depart);
						}
					} else {
						map.put("parentId", school.getDeptId());
						map.put("deptName", userBaseInfo.getDepartName());
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
				Major major = majorMapper.getByName(userBaseInfo.getMajorName());
				if (major == null) {
					major = new Major();
					major.setMajorName(userBaseInfo.getMajorName());
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
				map.put("deptName", userBaseInfo.getGradeName() + "级");
				Dept grade = deptMapper.selectByNameAndParentId(map);
				if (grade == null) {
					grade = new Dept();
					String deptId = depart.getDeptId() + userBaseInfo.getGradeName();
					grade.setDeptId(deptId);
					grade.setCreateTime(new Date());
					grade.setFullName(depart.getFullName() + "," + userBaseInfo.getGradeName() + "级");
					grade.setDeptName(userBaseInfo.getGradeName() + "级");
					grade.setLevel(4);
					grade.setParentId(depart.getDeptId());
					grade.setStatus("20");
					deptMapper.insert(grade);
				}

				// 班级
				map.put("parentId", grade.getDeptId());
				map.put("deptName", userBaseInfo.getClassName());
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
					String fullName = grade.getFullName() + "," + userBaseInfo.getClassName();
					class1.setFullName(fullName);
					class1.setLevel(4);
					class1.setParentId(grade.getDeptId());
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
				userBaseInfo.setNewUserId(userId);
				userBaseInfo.setMajorId(major.getMajorId());
				userBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(userBaseInfo.getUserName()));
				userBaseInfoMapper.update(userBaseInfo);
				UserInfo userInfo = new UserInfo();
				BeanUtils.copyProperties(userBaseInfo, userInfo);
				userInfoMapper.update(userInfo);
				UserProfile userProfile = userProfileMapper.selectByBaseInfoId(userBaseInfo.getUserId());
				String baseInfoId = "";
				String groupName = "";
				if (userProfile != null) {
					String[] idArray = userProfile.getBaseInfoId().split(",");
					for (String baseId : idArray) {
						if (baseId.equals(userBaseInfo.getUserId())) {
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
						if (group.equals(userBaseInfo.getUserId().substring(0, 16))) {
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
				newUserProfile.setName(userBaseInfo.getUserName());
				if (userBaseInfo.getSex() != null && userBaseInfo.getSex().equals("男")) {
					newUserProfile.setSex("0");
				} else {
					newUserProfile.setSex("1");
				}
				newUserProfile.setBaseInfoId(userBaseInfo.getUserId());
				newUserProfile.setNewBaseInfoId(baseInfoId);
				newUserProfile.setGroupName(groupName);
				userProfileMapper.updateBase(newUserProfile);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public DataGrid<UserBaseInfo> dataGridFor(Map<String, Object> map) {
		DataGrid<UserBaseInfo> dataGrid = new DataGrid<UserBaseInfo>();
		long total = userBaseInfoMapper.countFor(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<UserBaseInfo> list = userBaseInfoMapper.selectListFor(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	@Override
	public void deleteAll(Map<String, Object> map) {
		try {
			List<UserBaseInfo> list = userBaseInfoMapper.selectByDeptIdAll(map);
			if (list != null && list.size() > 0) {
				for (UserBaseInfo userBaseInfo : list) {
					UserProfile userProfile = userProfileMapper.selectByBaseInfoId(userBaseInfo.getUserId());
					if (userProfile != null) {
//						String baseInfoId = "";
//						String groupName = "";
//						if (userProfile.getBaseInfoId() != null && userProfile.getBaseInfoId().length() > 0) {
//							String[] idArray = userProfile.getBaseInfoId().split(",");
//							for (String baseId : idArray) {
//								if (!baseId.equals(userBaseInfo.getUserId())) {
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
//								if (!group.equals(userBaseInfo.getUserId().substring(0, 16))) {
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


						Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
						if(baseMap == null) {
							baseMap = Maps.newHashMap() ;
						}
						baseMap.put("accountNum", userProfile.getAccountNum()) ;
						userProfileMapper.clearBaseInfoId(baseMap);
					}
				}
			}
			userBaseInfoMapper.deleteByDeptIdAll(map);
			userInfoMapper.deleteByDeptIdAll(map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

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
		o[10] = "生日";
		o[11] = "民族";
		o[12] = "国籍";
		o[13] = "性别";
		o[14] = "状态";
		o[15] = "籍贯";
		o[16] = "邮箱";
		o[17] = "所在地址";
		o[18] = "固定电话";
		o[19] = "手机号码";
		o[20] = "入学时间";
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
		List<UserBaseInfo> list = userBaseInfoMapper.selectByDeptIdForExport(map);
		if (list != null && list.size() > 0) {
			for (UserBaseInfo userBaseInfo : list) {
				Object[] o1 = new Object[36];
				o1[0] = userBaseInfo.getStudentnumber();
				o1[1] = userBaseInfo.getUserName();
				o1[2] = userBaseInfo.getAliasname();
				o1[3] = userBaseInfo.getSchoolName();
				o1[4] = userBaseInfo.getDepartName();
				o1[5] = userBaseInfo.getGradeName().substring(0, userBaseInfo.getGradeName().length() - 1);
				o1[6] = userBaseInfo.getClassName();
				o1[7] = userBaseInfo.getMajorName();
				o1[8] = userBaseInfo.getCardType();
				o1[9] = userBaseInfo.getCard();
				if (userBaseInfo.getBirthday() != null) {
					o1[10] = dateFormat.format(userBaseInfo.getBirthday());
				} else {
					o1[10] = "";
				}
				o1[11] = userBaseInfo.getNation();
				o1[12] = userBaseInfo.getNationality();
				o1[13] = userBaseInfo.getSex();
				o1[14] = userBaseInfo.getStatus();
				o1[15] = userBaseInfo.getResourceArea();
				o1[16] = userBaseInfo.getEmail();
				o1[17] = userBaseInfo.getResidentialArea();
				o1[18] = userBaseInfo.getResidentialTel();
				o1[19] = userBaseInfo.getTelId();
				if (userBaseInfo.getEntranceTime() != null) {
					o1[20] = dateFormat.format(userBaseInfo.getEntranceTime());
				} else {
					o1[20] = "";
				}
				o1[21] = userBaseInfo.getProgramLength();
				o1[22] = userBaseInfo.getStudentType();

				o1[23] = userBaseInfo.getPolitical();
				o1[24] = userBaseInfo.getGraduationTime();
				o1[25] = userBaseInfo.getQq();
				o1[26] = userBaseInfo.getWeibo();
				o1[27] = userBaseInfo.getPersonalWebsite();
				o1[28] = userBaseInfo.getMailingAddress();
				o1[29] = userBaseInfo.getWorkUnit();
				o1[30] = userBaseInfo.getPosition();
				o1[31] = userBaseInfo.getIndustryType();
				o1[32] = userBaseInfo.getEnterprise();
				o1[33] = userBaseInfo.getWorkTel();
				o1[34] = userBaseInfo.getWorkAddress();
				o1[35] = userBaseInfo.getRemarks();

				objects.add(o1);
			}
		}
		return ExcelUtil.exportData(objects);
	}

	@Override
	public DataGrid<UserBaseInfo> selectByDeptIdForImport(Map<String, Object> map) {
		DataGrid<UserBaseInfo> dataGrid = new DataGrid<UserBaseInfo>();
		long total = userBaseInfoMapper.countByDeptIdForImport(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<UserBaseInfo> list = userBaseInfoMapper.selectByDeptIdForImport(map);
		dataGrid.setRows(list);
		return dataGrid;
	}


	@Override
	public String importDataBeforeThread( String url, User user )
	{
		final String sUrl = url;
		final User sUser = user;

		/*Lixun 2017-5-11 正在导入的状态*/
		if ( (Integer)CacheUtils.get( "isImport") > 0  )
			return "正在导入...";

		/*Lixun*/

		new Thread() {
			@Override
			public void run() {

				String re = importDataNew( sUrl, sUser );
			}
		}.start();

		return "";
	}


	public String importDataNew(String url, User user ) {

		/*Lixun 2017-5-11 正在导入的状态*/
		if ( (Integer)CacheUtils.get( "isImport") > 0 )
			return "正在导入...";

		CacheUtils.put( "isImport", 1 );
		/*Lixun*/





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
			CacheUtils.put( "nImportTotal", list.size() ) ;	//Lixun 2017-5-11
			if (list.size() > 50000) {
				throw new RuntimeException("一次只能导入5万条数据");
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

			List<UserBaseInfo> UserBaseInfoList = userBaseInfoMapper.selectAll();
			List<UserInfo> uList = userInfoMapper.selectAll();
			// 每个班级下的所有学生，key为班级编号，value为学生集合
			Map<String, List<UserInfo>> classStudentNameMap = new ConcurrentHashMap<String, List<UserInfo>>();
			Map<String, List<UserBaseInfo>> classBaseStudentNameMap = new ConcurrentHashMap<String, List<UserBaseInfo>>();
			// 班级编号与学生扩展位的对应关系,key为学生编号，value为一个班中，所有学生的扩展位
			Map<String, Set<String>> classStudentIdMap = new ConcurrentHashMap<String, Set<String>>();
			// 所填数据包含数据字典，与数据字典校验
			@SuppressWarnings("unchecked")
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

			if (UserBaseInfoList != null && UserBaseInfoList.size() > 0) {
				for (UserBaseInfo UserBaseInfo : UserBaseInfoList) {
					Set<String> set = null;
					List<UserBaseInfo> userList = null;
					String classId = UserBaseInfo.getUserId().substring(0, 16);
					if (classStudentIdMap.containsKey(classId)) {
						set = classStudentIdMap.get(classId);
						set.add(UserBaseInfo.getUserId().substring(16, 19));
					} else {
						set = new HashSet<String>();
						set.add(UserBaseInfo.getUserId().substring(16, 19));
					}
					classStudentIdMap.put(classId, set);

					if (classBaseStudentNameMap.containsKey(classId)) {
						userList = classBaseStudentNameMap.get(classId);
						userList.add(UserBaseInfo);
					} else {
						userList = new ArrayList<UserBaseInfo>();
						userList.add(UserBaseInfo);
					}
					classBaseStudentNameMap.put(classId, userList);
				}
			}
			if (uList != null && uList.size() > 0) {
				for (UserInfo userInfo : uList) {
					Set<String> set = null;
					List<UserInfo> userList = null;
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

			List<Dept> hasDeptList = user.getDepts();
			Set<String> schoolSet = new HashSet<String>();
			Set<String> departSet = new HashSet<String>();
			if (hasDeptList != null && hasDeptList.size() > 0) {
				for (Dept dept : hasDeptList) {
					if (dept.getDeptId().length() == 6) {
						schoolSet.add(dept.getDeptId());
					} else if (dept.getDeptId().length() == 10) {
						departSet.add(dept.getDeptId());
					}
				}
			}

			CacheUtils.put( "isImport", 2 );	//导入状态
			if (list != null && list.size() > 0) {
				// 第一行为excel表头
				Object[] head = new Object[list.get(0).length + 1];
				for (int j = 0; j < head.length; j++) {
					if (j != head.length - 1) {
						head[j] = list.get(0)[j];
					} else {
						head[j] = "失败原因";
					}
				}
				errorList.add(head);

				/*Lixun 2017-5-12优化批量导入效率*/
				List<UserBaseInfo> lub = new ArrayList<UserBaseInfo>();		//baseinfo缓存list
				List<UserInfo> lui = new ArrayList<UserInfo>();			//userinfo缓存list
				List<Dept> ldept = new ArrayList<Dept>();	//学院、班级缓存list
				List<String> lmajor = new ArrayList<String>();	//专业缓存list
				List< Map<String, Object> > lMajorDept = new ArrayList< Map<String, Object> >();	//专业部门缓存list

				int nCur = 0;	//baseinfo总数

				/*Lixun*/

				for (int i = 1; i < list.size(); i++) {
					rownumber = i;
					CacheUtils.put( "nImportNow", i );	//Lixun 导入状态 2017-5-11
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
					//chensheng 2017-6-15
					String industryType = "";
					//杨牛牛 2017-6-18
					String remarks = "";

					if( list.get(i).length > 23 )
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
					} else if (gradeName == null || gradeName.length() != 4 ) {
						// 班级为空
						Object[] content = new Object[list.get(i).length + 1];
						for (int j = 0; j < content.length; j++) {
							if (j != content.length - 1) {
								content[j] = list.get(i)[j];
							} else {
								content[j] = "年级不能为空且必须是4位数字";
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
								if (extend.equals("") || extend.equals("34")) {
									//throw new RuntimeException("学校曾用名数已超过最大限制(35个)");
									/*Lixun 2017-5-11 改造出错信息*/
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "学校曾用名数已超过最大限制(33个)";
										}
									}
									errorList.add(content);
									continue;
									/*Lixun*/
								}
								String deptId = schoolId + extend;
								school.setDeptId(deptId);
								school.setCreateTime(new Date());
								school.setFullName(schoolName);
								school.setDeptName(schoolName);
								school.setLevel(1);
								school.setParentId("0");
								school.setStatus("20");
								//deptMapper.insert(school);
								//Lixun
								ldept.add( school );
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
								if (departId.equals("") || departId.equals("999") ) {
									//throw new RuntimeException("院系已超过最大限制(999个)");
									/*Lixun 2017-5-11 改造出错信息*/
									Object[] content = new Object[list.get(i).length + 1];
									for (int j = 0; j < content.length; j++) {
										if (j != content.length - 1) {
											content[j] = list.get(i)[j];
										} else {
											content[j] = "院系已超过最大限制(998个)";
										}
									}
									errorList.add(content);
									continue;
									/*Lixun*/
								}
								String deptId = school.getDeptId() + departId + "0";
								depart.setDeptId(deptId);
								depart.setCreateTime(new Date());
								depart.setFullName(school.getFullName() + "," + departName);
								depart.setDeptName(departName);
								depart.setLevel(2);
								depart.setParentId(school.getDeptId());
								depart.setStatus("20");
								//deptMapper.insert(depart);
								//Lixun
								ldept.add( depart );
								deptNameAndParentIdMap.put(departName + " " + school.getDeptId(), depart);
								set.add(departId);
								ParentIdAndExtendIdMap.put(school.getDeptId(), set);
							}
						} else {
							// 找不到学校，无权限添加学校
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
							// 学校管理员
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
								// 院系
								depart = deptNameAndParentIdMap.get(departName + " " + school.getDeptId());
								if (depart == null) {
									depart = new Dept();
									Set<String> set = ParentIdAndExtendIdMap.get(school.getDeptId());
									if (set == null) {
										set = new HashSet<String>();
									}
									String departId = IdUtil.getDepart(set);
									if (departId.equals("") || departId.equals("998")) {
										//throw new RuntimeException("院系已超过最大限制(999个)");
										/*Lixun 2017-5-11 改造出错信息*/
										Object[] content = new Object[list.get(i).length + 1];
										for (int j = 0; j < content.length; j++) {
											if (j != content.length - 1) {
												content[j] = list.get(i)[j];
											} else {
												content[j] = "院系已超过最大限制(998个)";
											}
										}
										errorList.add(content);
										continue;
										/*Lixun*/
									}
									String deptId = school.getDeptId() + departId + "0";
									depart.setDeptId(deptId);
									depart.setCreateTime(new Date());
									depart.setFullName(school.getFullName() + "," + departName);
									depart.setDeptName(departName);
									depart.setLevel(2);
									depart.setParentId(school.getDeptId());
									depart.setStatus("20");
									//deptMapper.insert(depart);
									//Lixun
									ldept.add( depart );
									deptNameAndParentIdMap.put(departName + " " + school.getDeptId(), depart);
									set.add(departId);
									ParentIdAndExtendIdMap.put(school.getDeptId(), set);
								}
							} else {
								// 院系管理员
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
						// 专业
						Major major = null;
						if (majorName != null && !majorName.equals("")) {
							major = majorMap.get(majorName);
							if (major == null) {
								major = new Major();
								major.setMajorName(majorName);
								//majorMapper.addMajor(major);
								//Lixun
								lmajor.add( majorName );
								majorMap.put(majorName, major);
							}
							// 获取major和dept的关系,此处待优化
							MajorDept majorDept = majorDeptMap.get(major.getMajorId() + " " + depart.getDeptId());
							if (majorDept == null) {
								majorDept = new MajorDept();

								Map<String, Object> majorAndDeptMap = new HashMap<String, Object>();
								majorAndDeptMap.put("deptId", depart.getDeptId());
								majorAndDeptMap.put("majorId", major.getMajorId());
								//majorMapper.addMajorDept(majorAndDeptMap);
								//Lixun

								lMajorDept.add( majorAndDeptMap );
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
							grade.setLevel(3);
							grade.setParentId(depart.getDeptId());
							grade.setStatus("20");
							//deptMapper.insert(grade);
							//Lixun
							ldept.add( grade );
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
							if (classId.equals("") || classId.equals("99")) {
								//throw new RuntimeException("班级数据异常，同年级班级已超过100个");
								/*Lixun 2017-5-11 改造出错信息*/
								Object[] content = new Object[list.get(i).length + 1];
								for (int j = 0; j < content.length; j++) {
									if (j != content.length - 1) {
										content[j] = list.get(i)[j];
									} else {
										content[j] = "班级数据异常，同年级班级已超过99个";
									}
								}
								errorList.add(content);
								continue;
								/*Lixun*/
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
							//deptMapper.insert(class1);
							//Lixun
							ldept.add( class1 );
							deptNameAndParentIdMap.put(className + " " + grade.getDeptId(), class1);
							set.add(classId);
							ParentIdAndExtendIdMap.put(grade.getDeptId(), set);
						}

						UserBaseInfo userBaseInfo = new UserBaseInfo();
						UserInfo userInfoObj = new UserInfo();

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
						//Lixun 之前这一块的判断存在效率问题，可以考虑改造

						for (UserInfo userInfo : userList) {
							String birth = "";
							if (userInfo.getBirthday() != null) {
								birth = dateFormat.format(userInfo.getBirthday());
							}
							/*Lixun 2017-5-11 学号、联系电话、身份证的重复数据判断*/
							String phoneNum = userInfo.getTelId();
							String StudentId = userInfo.getStudentnumber();
							String IdentifyId = userInfo.getCard();
							if( phoneNum == null ) phoneNum = "";
							if( StudentId == null ) StudentId = "";
							if( IdentifyId == null ) IdentifyId = "";
							/*Lixun*/

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
								hasIn = true;
								break;
							}

							if (userName.equals(userInfo.getUserName()) && className.equals(userInfo.getClassName()) /*&& birthday.equals(birth)*/
								&& studentNumber.equals( StudentId ) && telId.equals( phoneNum ) && card.equals( IdentifyId )	) {	//Lixun 2017-5-11
								hasIn = true;
								break;
							}
						}

						for (UserBaseInfo userInfo : userBaseList) {
							String birth = "";
							if (userInfo.getBirthday() != null) {
								birth = dateFormat.format(userInfo.getBirthday());
							}
							/*Lixun 2017-5-11 学号、联系电话、身份证的重复数据判断*/
							String phoneNum = userInfo.getTelId();
							String StudentId = userInfo.getStudentnumber();
							String IdentifyId = userInfo.getCard();
							if( phoneNum == null ) phoneNum = "";
							if( StudentId == null ) StudentId = "";
							if( IdentifyId == null ) IdentifyId = "";
							/*Lixun*/

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

							if (userName.equals(userInfo.getUserName()) && className.equals(userInfo.getClassName()) /*&& birthday.equals(birth)*/
									&& studentNumber.equals( StudentId ) && telId.equals( phoneNum ) && card.equals( IdentifyId ) ) { //Lixun 2017-5-11
								hasBaseIn = true;
								break;
							}
						}
						if( isStudentIdDuplicate )	//学号重复
							continue;

						if (!hasBaseIn && !hasIn) {
							String uId = IdUtil.getUserId(set);

							if ( uId.equals("") || uId.equals("999") ) {
								//throw new RuntimeException("同一班级学生数量超过最大限制(999人)");
								/*Lixun 2017-5-11 改造出错信息*/
								Object[] content = new Object[list.get(i).length + 1];
								for (int j = 0; j < content.length; j++) {
									if (j != content.length - 1) {
										content[j] = list.get(i)[j];
									} else {
										content[j] = "同一班级学生数量超过最大限制(998人)";
									}
								}
								errorList.add(content);
								continue;
								/*Lixun*/
							}
							userBaseInfo.setUserId(class1.getDeptId() + uId);
							userInfoObj.setUserId(class1.getDeptId() + uId);
							userBaseInfo.setUserName(userName);
							userInfoObj.setUserName(userName);
							userBaseInfo.setAliasname(aliasName);
							userInfoObj.setAliasname(aliasName);
							userBaseInfo.setNamePinyin(PinYinUtils.getQuanPin(userName));
							userInfoObj.setNamePinyin(PinYinUtils.getQuanPin(userName));
							userBaseInfo.setCreateTime(new Date());
							userInfoObj.setCreateTime(new Date());
							userBaseInfo.setStudentnumber(studentNumber);
							userInfoObj.setStudentnumber(studentNumber);
							try {
								SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
								if (entranceTime != null && !"".equals(entranceTime)) {
									Date date = fmt1.parse(entranceTime);
									userBaseInfo.setEntranceTime(date);
								}
								if( graduateTime != null && !"".equals(graduateTime) )
								{
									Date date = fmt1.parse(graduateTime);
									userBaseInfo.setGraduationTime( date );
									userInfoObj.setGraduationTime( date );
								}
							} catch (Exception e) {
								Object[] content = new Object[list.get(i).length + 1];
								for (int j = 0; j < content.length; j++) {
									if (j != content.length - 1) {
										content[j] = list.get(i)[j];
									} else {
										content[j] = "入学/毕业日期数据无法转换";
									}
								}
								errorList.add(content);
								continue;
							}
							if (major != null) {
								userBaseInfo.setMajorId(major.getMajorId());
								userInfoObj.setMajorId(major.getMajorId());
							}
							userBaseInfo.setSex(sex);
							userInfoObj.setSex(sex);
							userBaseInfo.setNation(nation);
							userInfoObj.setNation(nation);
							userBaseInfo.setNationality(nationality);
							userInfoObj.setNationality(nationality);
							if (studentTypeList.contains(studentType)) {
								userBaseInfo.setStudentType(studentType);
								userInfoObj.setStudentType(studentType);
							} else {
								userBaseInfo.setStudentType("");
								userInfoObj.setStudentType("");
							}
							if (programLenthList.contains(programLength)) {
								userBaseInfo.setProgramLength(programLength);
								userInfoObj.setProgramLength(programLength);
							} else {
								userBaseInfo.setProgramLength("");
								userInfoObj.setProgramLength("");
							}
							if (cardTypeList.contains(cardType)) {
								userBaseInfo.setCardType(cardType);
								userInfoObj.setCardType(cardType);
							} else {
								userBaseInfo.setCardType("");
								userInfoObj.setCardType("");
							}
							userBaseInfo.setCard(card);
							userInfoObj.setCard(card);
							userBaseInfo.setEmail(email);
							userInfoObj.setEmail(email);
							userBaseInfo.setResidentialArea(residentialArea);
							userInfoObj.setResidentialArea(residentialArea);
							userBaseInfo.setResidentialTel(residentialTel);
							userInfoObj.setResidentialTel(residentialTel);
							userBaseInfo.setTelId(telId);
							userInfoObj.setTelId(telId);
							userBaseInfo.setResourceArea(resourceArea);
							userInfoObj.setResourceArea(resourceArea);
							userBaseInfo.setStatus(status);
							userInfoObj.setStatus(status);

							/*Lixun 2-17-5-12 添加3个字段*/
							userBaseInfo.setWorkAddress( workAddress );
							userInfoObj.setWorkAddress( workAddress );
							userBaseInfo.setPosition( position );
							userInfoObj.setPosition( position );
							/*Lixun*/

							/* llr 2-17-6-3 添加2个字段*/
							userBaseInfo.setWorkUnit(workUnit);
							userBaseInfo.setQq(qq);
							userInfoObj.setWorkUnit(workUnit);
							userInfoObj.setQq(qq);
							/* chensheng 2-17-6-15 添加字段*/
							userBaseInfo.setIndustryType(industryType);
							userInfoObj.setIndustryType(industryType);

							/*杨牛牛 2017-6-18*/
							userBaseInfo.setRemarks(remarks);
							userInfoObj.setRemarks(remarks);

							/*llr*/

							userBaseInfo.setClassName(className);
							userInfoObj.setClassName(className);
							try {
								SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
								if (birthday != null && !"".equals(birthday)) {
									Date date = fmt.parse(birthday);
									userBaseInfo.setBirthday(date);
									userInfoObj.setBirthday(date);
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
							userInfoObj.setCheckFlag("1");
							/*
							userBaseInfoMapper.save(userBaseInfo);
							// 保存成功后刷新classStudentNameMap，classStudentIdMap
							userInfoMapper.save(userInfoObj);
							*/
							/*Lixun 2-17-5-12改造存校友数据效率问题*/
							lub.add( userBaseInfo );
							lui.add( userInfoObj );
							++nCur;
							if( nCur == 1000 || i == list.size()-1 )
							{
								if( ldept.size() > 0 )
								{
									deptMapper.multiinsert(ldept);
									ldept = new ArrayList<Dept>();
								}
								if( lmajor.size() > 0 )
								{
									majorMapper.multiaddMajor( lmajor );
									lmajor = new ArrayList<String>();
								}
								if( lMajorDept.size() > 0 )
								{
									majorMapper.multiaddMajorDept( lMajorDept );
									lMajorDept = new ArrayList< Map<String, Object> >();
								}

								userBaseInfoMapper.multisave( lub );
								userInfoMapper.multisave( lui );
								nCur = 0;
								lub = new ArrayList<UserBaseInfo>();
								lui = new ArrayList<UserInfo>();
							}
							/*Lixun*/
							userBaseList.add(userBaseInfo);
							uList.add(userInfoObj);
							set.add(uId);
							classBaseStudentNameMap.put(class1.getDeptId(), userBaseList);
							classStudentNameMap.put(class1.getDeptId(), uList);
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
				//Lixun 2017-5-12 处理最后一批保存
				if( nCur > 0 )
				{
					if( ldept.size() > 0 )
						deptMapper.multiinsert(ldept);
					if( lmajor.size() > 0 )
						majorMapper.multiaddMajor( lmajor );
					if( lMajorDept.size() > 0 )
						majorMapper.multiaddMajorDept( lMajorDept );
					userBaseInfoMapper.multisave( lub );
					userInfoMapper.multisave( lui );
				}
			}
			System.gc();

			CacheUtils.put( "sImportResult", ExcelUtil.exportData(errorList) );
			CacheUtils.put( "isImport", 0 );
			return "";
		} catch (Exception e) {
			String str = "";
			if (rownumber > 0) {
				str = "第" + rownumber + "行数据导致数据导入失败(" + e.getMessage() + ")";
			} else {
				str = e.getMessage();
			}
			CacheUtils.put( "isImport", 0 );
			CacheUtils.put( "sImportResult", "1" + e.getMessage() );
			throw new RuntimeException(str, e);
		}
	}

}
