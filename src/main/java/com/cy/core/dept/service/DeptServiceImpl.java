package com.cy.core.dept.service;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.cloudEnterprise.entity.CloudEntrepreneur;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.TreeString;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.major.dao.MajorMapper;
import com.cy.core.major.entity.Major;
import com.cy.core.user.entity.User;
import com.cy.system.ClassPathResource;
import com.cy.system.ExcelUtil;
import com.cy.system.Global;
import com.cy.system.IdUtil;
import com.cy.common.utils.easyui.TreeStringUtil;

@Service("deptService")
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private MajorMapper majorMapper;

	@Autowired
	private UserInfoMapper  userInfoMapper;

	public List<String> getDepts(long userId){
		return deptMapper.getDepts(userId);
	}


	public List<Dept> selectAll(List<String> list) {
		return deptMapper.selectAll(list);
	}

	/**
	 * 查询历史沿革与院系归属的院系列表
	 * @param map
	 * @return
	 */
	public DataGrid<Map<String,String>> queryDeptByAttribution(Map<String,Object> map) {
		DataGrid<Map<String,String>> dataGrid = new DataGrid<Map<String,String>>();
		long count = deptMapper.countByAttribution(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Map<String,String>> list = deptMapper.queryDeptByAttribution(map);
		dataGrid.setRows(list);
		return dataGrid ;
	}
    /**
     * 设置现有学院
     */
    public void setCurrentDept(Map<String,String> map) {
        if(map != null && !map.isEmpty() && StringUtils.isNotBlank(map.get("deptIds"))) {
            deptMapper.setCurrent(map);
        }
    }

    /**
     * 设置归属学院
     */
    public void setBelongDept(Map<String,String> map) {
        if(map != null && !map.isEmpty() && (StringUtils.isNotBlank(map.get("deptId"))) || StringUtils.isNotBlank(map.get("deptIds"))) {
            deptMapper.setBelongDept(map);
        }
    }

    /**
     * 转移归属学院
     */
    public void shiftFromSourceBelongToGoalBelong(Map<String,String> map) {
        deptMapper.shiftFromSourceBelongToGoalBelong(map);
    }

    /**
     * 查询现有机构数
     * @return
     */
    public List<Dept> findCurrDeptTree( ) {
    	Map<String, Object> map = Maps.newHashMap() ;
		map.put("status","20") ;
		map.put("isCurrent","1") ;
		map.put("isNotLimit","1") ;
		List<Dept> list = deptMapper.selectDeptList(map) ;
		map.clear();
		map.put("deptList",list) ;
		list = deptMapper.findCurrDeptTree(map) ;
        return list ;
    }

	/**
	 * 查询之前机构数
	 * @return
	 */
	public List<Dept> findbofreDeptTree(Dept dept) {
		Map<String, Object> map = Maps.newHashMap() ;
		map.put("status",dept.getStatus()) ;
		map.put("isCurrent",dept.getIsCurrent()) ;
		map.put("hasBelong",dept.getBelongDeptId()) ;
		List<Dept> list = deptMapper.selectTreeList(map) ;
		map.clear();
		map.put("deptList",list) ;
		list = deptMapper.findtreeDeptTree(map) ;
		return list ;
	}

	public List<Dept> selectEvery(){return deptMapper.selectEvery(); }

	public void insert(Dept dept) {
		try {
			if ("0".equals(dept.getParentId())) {
				dept.setCreateTime(new Date());
				dept.setFullName(dept.getDeptName());
				dept.setStatus("20");
				deptMapper.insert(dept);
			}
			if (dept.getParentId().length() == 14) {
				List<Dept> list = deptMapper.selectByDeptId(dept.getParentId());
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
				String deptId = dept.getParentId() + classId;
				dept.setDeptId(deptId);
				dept.setCreateTime(new Date());
				Dept grade = deptMapper.selectOne(dept.getParentId());
				String fullName = grade.getFullName() + "," + dept.getDeptName();
				dept.setFullName(fullName);
				dept.setStatus("20");
				deptMapper.insert(dept);
			}

//			if (dept.getParentId().length() == 6 && dept.getLevel() == 4 && dept.getDepartId() == null || "".equals(dept.getDepartId())) {
			if (dept.getParentId().length() == 6 && dept.getDepartId() == null || "".equals(dept.getDepartId())) {
				List<Dept> list = deptMapper.selectByDeptId(dept.getParentId());
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
				String deptId = dept.getParentId() + departId + "0";
				dept.setDeptId(deptId);
				dept.setCreateTime(new Date());
				Dept school = deptMapper.selectOne(dept.getParentId());
				String fullName = school.getFullName() + "," + dept.getDeptName();
				dept.setFullName(fullName);
				dept.setStatus("20");
				deptMapper.insert(dept);
			}

//			if (dept.getParentId().length() == 6 && dept.getLevel() == 4 && (dept.getDepartId() != null && !dept.getDepartId().equals(""))) {
			if (dept.getParentId().length() == 6 && (dept.getDepartId() != null && !dept.getDepartId().equals(""))) {
				Set<String> set = new HashSet<String>();
				List<Dept> list = deptMapper.getDepart2(dept.getParentId());
				for (Dept dept2 : list) {
					if (dept2.getDeptId().substring(6, 9).equals(dept.getDepartId().substring(6, 9))) {
						set.add(dept2.getDeptId().substring(9, 10));
					}
				}
				String extend = IdUtil.getExtend(set);
				if (extend.equals("")) {
					throw new RuntimeException("曾用名异常，曾用名数据量已超过35个");
				}
				String deptId = dept.getParentId() + dept.getDepartId().substring(6, 9) + extend;
				dept.setDeptId(deptId);
				dept.setCreateTime(new Date());
				Dept school = deptMapper.selectOne(dept.getParentId());
				String fullName = school.getFullName() + "," + dept.getDeptName();
				dept.setFullName(fullName);
				dept.setStatus("20");
				deptMapper.insert(dept);
			}

			if (dept.getParentId().length() == 10) {
				String deptId = dept.getParentId() + dept.getDeptName();
				dept.setDeptId(deptId);
				dept.setCreateTime(new Date());
				Dept depart = deptMapper.selectOne(dept.getParentId());
				dept.setFullName(depart.getFullName() + "," + dept.getDeptName() + "级");
				dept.setDeptName(dept.getDeptName() + "级");
				dept.setStatus("20");
				deptMapper.insert(dept);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insertAlias(Dept dept) {
		if (dept.getDeptId().length() == 6) {
			List<Dept> list = deptMapper.getSchool();
			Set<String> set = new HashSet<String>();
			for (Dept dept2 : list) {
				set.add(dept2.getDeptId().substring(5, 6));
			}
			String extend = IdUtil.getExtend(set);
			if (extend.equals("")) {
				throw new RuntimeException("曾用名异常，曾用名数据量已超过35个");
			}
			String deptId = dept.getDeptId().substring(0, 5) + extend;
			dept.setAliasName(dept.getDeptId());
			dept.setDeptId(deptId);
			dept.setCreateTime(new Date());
			dept.setFullName(dept.getDeptName());
			dept.setStatus("20");
			deptMapper.insert(dept);
		}
		if (dept.getDeptId().length() == 10) {
			Dept school = deptMapper.selectOne(dept.getSchoolId());
			List<Dept> list = deptMapper.getDepart2(dept.getSchoolId());
			Set<String> set = new HashSet<String>();
			for (Dept dept2 : list) {
				if (dept2.getDeptId().substring(6, 9).equals(dept.getDeptId().substring(6, 9))) {
					set.add(dept2.getDeptId().substring(9, 10));
				}
			}
			String extend = IdUtil.getExtend(set);
			if (extend.equals("")) {
				throw new RuntimeException("曾用名异常，曾用名数据量已超过35个");
			}
			String deptId = dept.getSchoolId() + dept.getDeptId().substring(6, 9) + extend;
			dept.setDeptId(deptId);
			dept.setCreateTime(new Date());
			dept.setParentId(dept.getSchoolId());
			dept.setFullName(school.getFullName() + "," + dept.getDeptName());
			dept.setStatus("20");
			deptMapper.insert(dept);
		}
	}

	public Dept checkDeptId(String deptId) {
		return deptMapper.checkDeptId(deptId);
	}

	public void delete(String deptId) {
		try {
			List<Dept> list = deptMapper.selectAll2();
			List<TreeString> allList = new ArrayList<TreeString>();
			if (list != null && list.size() > 0) {
				for (Dept dept : list) {
					TreeString node = new TreeString();
					node.setId(dept.getDeptId());
					node.setPid(dept.getParentId());
					node.setText(dept.getDeptName());
					Map<String, Integer> attributes = new HashMap<String, Integer>();
					attributes.put("level", dept.getLevel());
					node.setAttributes(attributes);
					allList.add(node);
				}
			}
			List<String> deptIdList = new ArrayList<String>();
			deptIdList.add(deptId);
			TreeStringUtil.getChildren(deptId, allList, deptIdList);
			deptMapper.delete(deptIdList);
			// 删除与专业的关系表
			majorMapper.deleteMajorByDeptId(deptIdList);
			// 清空归属关系
			for (String aliasName : deptIdList) {
				deptMapper.updateAliasName(aliasName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public String importData(String url, User user) {
		try {
			// 文件保存目录路径
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;
			url = savePath + url.replace(saveUrl, "");
			File file = new File(url);
			List<Object[]> list = ExcelUtil.parseExcel(file);
			List<Object[]> errorList = new ArrayList<Object[]>();
			// 数据整理开始
			Map<String, Dept> deptNameAndParentIdMap = new ConcurrentHashMap<String, Dept>();
			Map<String, Set<String>> parentIdMap = new ConcurrentHashMap<String, Set<String>>();
			List<Dept> allDeptList = deptMapper.selectAll2();
			String schoolId = "";
			if (allDeptList != null && allDeptList.size() > 0) {
				for (Dept dept2 : allDeptList) {
					String deptNameAndParentId = dept2.getDeptName() + "," + dept2.getParentId();
					if (deptNameAndParentIdMap.keySet().contains(deptNameAndParentId)) {
						throw new RuntimeException("同一机构下，发现重名的子机构");
					} else {
						deptNameAndParentIdMap.put(deptNameAndParentId, dept2);
					}
					String parentId = dept2.getParentId();
					Set<String> deptIdSet = null;
					// 学校
					if (dept2.getDeptId().length() == 6) {
						if (parentIdMap.keySet().contains(parentId)) {
							deptIdSet = parentIdMap.get(parentId);
							deptIdSet.add(dept2.getDeptId().substring(5, 6));
						} else {
							deptIdSet = new HashSet<String>();
							deptIdSet.add(dept2.getDeptId().substring(5, 6));
						}
						parentIdMap.put(parentId, deptIdSet);
						schoolId = dept2.getDeptId().substring(0, 5);
					}
					// 院系
					if (dept2.getDeptId().length() == 10) {
						if (parentIdMap.keySet().contains(parentId)) {
							deptIdSet = parentIdMap.get(parentId);
							deptIdSet.add(dept2.getDeptId().substring(6, 9));
						} else {
							deptIdSet = new HashSet<String>();
							deptIdSet.add(dept2.getDeptId().substring(6, 9));
						}
						parentIdMap.put(parentId, deptIdSet);
					}
					// 班级
					if (dept2.getDeptId().length() == 16) {
						if (parentIdMap.keySet().contains(parentId)) {
							deptIdSet = parentIdMap.get(parentId);
							deptIdSet.add(dept2.getDeptId().substring(14, 16));
						} else {
							deptIdSet = new HashSet<String>();
							deptIdSet.add(dept2.getDeptId().substring(14, 16));
						}
						parentIdMap.put(parentId, deptIdSet);
					}
				}
			}
			// 专业和院系的关系初始化
			Map<String, Major> majorMap = new ConcurrentHashMap<String, Major>();
			Set<String> majorSet = new HashSet<String>();
			List<Major> majorList = majorMapper.selectAll();
			if (majorList != null && majorList.size() > 0) {
				for (Major major : majorList) {
					majorMap.put(major.getMajorName(), major);
					List<Dept> mDeptList = major.getDeptList();
					if (mDeptList != null && mDeptList.size() > 0) {
						for (Dept dept2 : mDeptList) {
							majorSet.add(dept2.getDeptId() + "," + major.getMajorId());
						}
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
			// 数据整理结束
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String schoolName = ((String) list.get(i)[0]).trim();
					String departName = ((String) list.get(i)[1]).trim();
					String majorName = ((String) list.get(i)[2]).trim();
					String gradeName = ((String) list.get(i)[3]).trim();
					String className = ((String) list.get(i)[4]).trim();
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
						if (user.getDepts() == null || user.getDepts().size() == 0) {
							// 学校
							Dept school = deptNameAndParentIdMap.get(schoolName + "," + "0");
							if (school == null) {
								school = new Dept();
								Set<String> set = parentIdMap.get("0");
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
								allDeptList.add(school);
								deptNameAndParentIdMap.put(schoolName + "," + "0", school);
								set.add(extend);
								parentIdMap.put("0", set);
							}
							Dept depart = deptNameAndParentIdMap.get(departName + "," + school.getDeptId());
							if (depart == null) {
								depart = new Dept();
								String parentId = school.getDeptId();
								Set<String> set = parentIdMap.get(parentId);
								if (set == null) {
									set = new HashSet<String>();
								}
								String departId = IdUtil.getDepart(set);
								if (departId.equals("")) {
									throw new RuntimeException("院系数据异常，院系已超过1000个");
								}
								String deptId = parentId + departId + "0";
								depart.setDeptId(deptId);
								depart.setParentId(parentId);
								depart.setCreateTime(new Date());
								depart.setFullName(school.getFullName() + "," + departName);
								depart.setDeptName(departName);
								depart.setLevel(school.getLevel());
								depart.setStatus("20");
								deptMapper.insert(depart);
								// 院系新增后，更新deptNameAndParentIdMap，allDeptList，parentIdMap
								allDeptList.add(depart);
								deptNameAndParentIdMap.put(departName + "," + school.getDeptId(), depart);
								set.add(departId);
								parentIdMap.put(parentId, set);
							}

							// 专业
							if (majorName != null && !majorName.equals("")) {
								Major major = majorMap.get(majorName);
								if (major == null) {
									major = new Major();
									major.setMajorName(majorName);
									majorMapper.addMajor(major);
									majorMap.put(majorName, major);
								}
								if (!majorSet.contains(depart.getDeptId() + "," + major.getMajorId())) {
									// 增加专业和院系的关系
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("majorId", major.getMajorId());
									map.put("deptId", depart.getDeptId());
									majorMapper.addMajorDept(map);
									majorSet.add(depart.getDeptId() + "," + major.getMajorId());
								}
							}

							Dept grade = deptNameAndParentIdMap.get(gradeName + "级" + "," + depart.getDeptId());
							if (grade == null) {
								grade = new Dept();
								String parentId = depart.getDeptId();
								String deptId = "";
								String fullName = "";
								deptId = depart.getDeptId() + gradeName;
								fullName = depart.getFullName() + "," + gradeName + "级";
								grade.setDeptId(deptId);
								grade.setDeptName(gradeName + "级");
								grade.setParentId(parentId);
								grade.setCreateTime(new Date());
								grade.setFullName(fullName);
								grade.setLevel(school.getLevel());
								grade.setStatus("20");
								deptMapper.insert(grade);
								// 年级新增后，更新deptNameAndParentIdMap，allDeptList
								allDeptList.add(grade);
								deptNameAndParentIdMap.put(gradeName + "级" + "," + depart.getDeptId(), grade);
							}

							Dept class1 = deptNameAndParentIdMap.get(className + "," + grade.getDeptId());
							if (class1 == null) {
								class1 = new Dept();
								String parentId = grade.getDeptId();
								Set<String> set = parentIdMap.get(parentId);
								if (set == null) {
									set = new HashSet<String>();
								}
								String classId = IdUtil.getClassId(set);
								if (classId.equals("")) {
									throw new RuntimeException("班级数据异常，同年级班级已超过100个");
								}
								String deptId = parentId + classId;
								class1.setDeptId(deptId);
								class1.setParentId(parentId);
								class1.setCreateTime(new Date());
								class1.setDeptName(className);
								class1.setFullName(grade.getFullName() + "," + className);
								class1.setLevel(school.getLevel());
								class1.setStatus("20");
								deptMapper.insert(class1);
								// 班级新增后，更新deptNameAndParentIdMap，allDeptList，parentIdMap
								allDeptList.add(class1);
								deptNameAndParentIdMap.put(className + "," + grade.getDeptId(), class1);
								set.add(classId);
								parentIdMap.put(parentId, set);
							} else {
								// 班级名重复
								Object[] content = new Object[list.get(i).length + 1];
								for (int j = 0; j < content.length; j++) {
									if (j != content.length - 1) {
										content[j] = list.get(i)[j];
									} else {
										content[j] = "班级名重复";
									}
								}
								errorList.add(content);
								continue;
							}
						} else {
							// 学校
							Dept school = deptNameAndParentIdMap.get(schoolName + "," + "0");
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
							Dept depart = null;
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
								depart = deptNameAndParentIdMap.get(departName + "," + school.getDeptId());
								if (depart == null) {
									depart = new Dept();
									String parentId = school.getDeptId();
									Set<String> set = parentIdMap.get(parentId);
									if (set == null) {
										set = new HashSet<String>();
									}
									String departId = IdUtil.getDepart(set);
									if (departId.equals("")) {
										throw new RuntimeException("院系数据异常，院系已超过1000个");
									}
									String deptId = parentId + departId + "0";
									depart.setDeptId(deptId);
									depart.setParentId(parentId);
									depart.setCreateTime(new Date());
									depart.setFullName(school.getFullName() + "," + departName);
									depart.setDeptName(departName);
									depart.setLevel(school.getLevel());
									depart.setStatus("20");
									deptMapper.insert(depart);
									// 院系新增后，更新deptNameAndParentIdMap，allDeptList，parentIdMap
									allDeptList.add(depart);
									deptNameAndParentIdMap.put(departName + "," + school.getDeptId(), depart);
									set.add(departId);
									parentIdMap.put(parentId, set);
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

							// 专业
							if (majorName != null && !majorName.equals("")) {
								Major major = majorMap.get(majorName);
								if (major == null) {
									major = new Major();
									major.setMajorName(majorName);
									majorMapper.addMajor(major);
									majorMap.put(majorName, major);
								}
								if (!majorSet.contains(depart.getDeptId() + "," + major.getMajorId())) {
									// 增加专业和院系的关系
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("majorId", major.getMajorId());
									map.put("deptId", depart.getDeptId());
									majorMapper.addMajorDept(map);
									majorSet.add(depart.getDeptId() + "," + major.getMajorId());
								}
							}

							Dept grade = deptNameAndParentIdMap.get(gradeName + "级" + "," + depart.getDeptId());
							if (grade == null) {
								grade = new Dept();
								String parentId = depart.getDeptId();
								String deptId = "";
								String fullName = "";
								deptId = depart.getDeptId() + gradeName;
								fullName = depart.getFullName() + "," + gradeName + "级";
								grade.setDeptId(deptId);
								grade.setDeptName(gradeName + "级");
								grade.setParentId(parentId);
								grade.setCreateTime(new Date());
								grade.setFullName(fullName);
								grade.setLevel(school.getLevel());
								grade.setStatus("20");
								deptMapper.insert(grade);
								// 年级新增后，更新deptNameAndParentIdMap，allDeptList
								allDeptList.add(grade);
								deptNameAndParentIdMap.put(gradeName + "级" + "," + depart.getDeptId(), grade);
							}

							Dept class1 = deptNameAndParentIdMap.get(className + "," + grade.getDeptId());
							if (class1 == null) {
								class1 = new Dept();
								String parentId = grade.getDeptId();
								Set<String> set = parentIdMap.get(parentId);
								if (set == null) {
									set = new HashSet<String>();
								}
								String classId = IdUtil.getClassId(set);
								if (classId.equals("")) {
									throw new RuntimeException("班级数据异常，同年级班级已超过100个");
								}
								String deptId = parentId + classId;
								class1.setDeptId(deptId);
								class1.setParentId(parentId);
								class1.setCreateTime(new Date());
								class1.setDeptName(className);
								class1.setFullName(grade.getFullName() + "," + className);
								class1.setLevel(school.getLevel());
								class1.setStatus("20");
								deptMapper.insert(class1);
								// 班级新增后，更新deptNameAndParentIdMap，allDeptList，parentIdMap
								allDeptList.add(class1);
								deptNameAndParentIdMap.put(className + "," + grade.getDeptId(), class1);
								set.add(classId);
								parentIdMap.put(parentId, set);
							} else {
								// 班级名重复
								Object[] content = new Object[list.get(i).length + 1];
								for (int j = 0; j < content.length; j++) {
									if (j != content.length - 1) {
										content[j] = list.get(i)[j];
									} else {
										content[j] = "班级名重复";
									}
								}
								errorList.add(content);
								continue;
							}
						}
					}
				}
			}
			return ExcelUtil.exportData(errorList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Dept> getSchool() {
		return deptMapper.getSchool();
	}

	public List<Dept> selectAllClass(String deptId) {
		return deptMapper.selectAllClass(deptId);
	}

	public DataGrid<Dept> dateGridForUser(Map<String, Object> map) {
		DataGrid<Dept> dataGrid = new DataGrid<Dept>();
		long count = deptMapper.countDept(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Dept> list = deptMapper.selectDeptList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public Dept selectByNameAndParentId(Map<String, Object> map) {
		return deptMapper.selectByNameAndParentId(map);
	}

	public List<Dept> getDepart() {
		return deptMapper.getDepart();
	}

	public List<Dept> getDepart1() {
		return deptMapper.getDepart1();
	}

	public List<Dept> getByParentId(String deptId) {
		return deptMapper.getByParentId(deptId);
	}

	@Override
	public Dept getById(String deptId) {
		return deptMapper.getById(deptId);
	}

	@Override
	public void update(Dept dept) {
		try {
			if (!dept.getDeptName().equals(dept.getResourceName())||!dept.getSort().equals(dept.getResourceSort())) {
				deptMapper.update(dept);
				deptMapper.updateFullName(dept);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Dept> getBelong(String deptId) {
		if (deptId.length() == 6) {
			return deptMapper.getBelong1(deptId);
		} else {
			return deptMapper.getBelong2(deptId);
		}

	}

	@Override
	public void updateBelong(Dept dept) {
		deptMapper.updateBelong(dept);
	}

	@Override
	public Dept getByAliasName(String deptId) {
		return deptMapper.getByAliasName(deptId);
	}

	@Override
	public List<Dept> selectByDeptIds(List<String> list) {
		return deptMapper.selectByDeptIds(list);
	}

	@Override
	public List<Dept> getByParentIdAndDeptIds(Map<String, Object> map) {
		return deptMapper.getByParentIdAndDeptIds(map);
	}

	@Override
	public List<Dept> selectAll1(List<Dept> list) {
		return deptMapper.selectAll1(list);
	}

	@Override
	public List<Dept> selectDepart(List<Dept> list) {
		return deptMapper.selectDepart(list);
	}
	
	@Override
	public List<Dept> selectAlumniDept() {	//lixun
		return deptMapper.selectAlumniDeptTree();
	}

	/**
	 * add by jiangling
	 * @param map
	 * @return
     */
	public List<Dept> findDeptTreeBySearchCondition(Map<String,Object> map) {
//		String str = map.get("schoolName").toString();
//		if("schoolName".equals(str)) {
//
//		}
		return  deptMapper.findDeptTreeBySearchCondition(map);
	}


	/**
	 * 查询院系列表
	 * @param map
	 * @return
     */
	public DataGrid<Dept> dataGrid(Map<String, Object> map) {
		DataGrid<Dept> dataGrid = new DataGrid<Dept>();
		long count = deptMapper.countDept(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Dept> list = deptMapper.selectDeptList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**
	 * add liuzhen
	 * 查询班级列表
	 * @param map
	 * @return
	 */
	public DataGrid<Map<String,String>> selectClassList(Map<String, Object> map) {
		DataGrid<Map<String,String>> dataGrid = new DataGrid<Map<String,String>>();
		long count = deptMapper.countClassList(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Map<String,String>> list = deptMapper.selectClassList(map);
		if( list!= null ){
			for(int i = 0 ; i < list.size() ;i++) {
				Map<String,String> adminMap = deptMapper.selectAdminByClass(list.get(i).get("deptId")) ;
				if (adminMap != null && !adminMap.isEmpty()) {
					list.get(i).put("adminIds", adminMap.get("adminIds"));
					list.get(i).put("adminNames", adminMap.get("adminNames"));
				}
			}
		}
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**
	 * add qpf
	 * 通过用户数据权限查询班级列表
	 * @param map
	 * @return
	 */
	public DataGrid<Map<String,String>> selectClassListByUser(Map<String, Object> map) {
		DataGrid<Map<String,String>> dataGrid = new DataGrid<Map<String,String>>();
		long count = deptMapper.countClassListByUser(map);
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Map<String,String>> list = deptMapper.selectClassListByUser(map);
		if( list!= null ){
			for(int i = 0 ; i < list.size() ;i++) {
				Map<String,String> adminMap = deptMapper.selectAdminByClass(list.get(i).get("deptId")) ;
				if(adminMap != null && StringUtils.isNotBlank(adminMap.get("adminIds"))) {
					list.get(i).put("adminIds", adminMap.get("adminIds"));
					list.get(i).put("adminNames", adminMap.get("adminNames"));
				}
			}
		}
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**
	 * 通过用户获取其所在组织
	 * @param userId
	 * @return
     */
	public long selectAluByUser( long userId){
		return deptMapper.selectAluByUser( userId );
	}

	@Override
	public List<Dept> selectDeptByUserId( long userid )
	{
		return deptMapper.selectDeptByUserId( userid );
	}
	@Override
	public String selectDeptByAlumniId( long deptid )
	{
		return deptMapper.selectDeptByAlumniId( deptid );
	}

	/**
	 * 获取当前学院和归属于当前学院的学院列表
	 * @param map
	 * @return
     */
	public List<Dept> queryDeptAndBelongDept(Map<String,String> map) {
		List<Dept> list = deptMapper.queryCurrAndBelongDept(map) ;
		return list ;
	}
	/**
	 * 获取当前学院和归属于当前学院的学院编号字符串(多个编号以","隔开)
	 * @param map
	 * @return
     */
	public String getDeptAndBelongDeptIdsByCurrDeptId(Map<String,String> map) {
		return deptMapper.getDeptAndBelongDeptIdsByCurrDeptId(map) ;
	}

	/**
	 * 根据班级上级机构查询热门班级列表
	 */
	public void findDeptListPage(Message message, String content ){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			Map<String, String> map = JSON.parseObject(content,Map.class);
			String deptId = map.get("deptId");
			String start = (String) map.get("start");
			String rows = (String) map.get("rows");
			if (StringUtils.isBlank(deptId)){
				message.setMsg("请输入班级上级机构编号");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isBlank(start) || StringUtils.isBlank(rows)){
				map.put("isNotLimit","1");
			}else {
				map.put("isNotLimit","0");
			}
			List<Map<String,Object>> deptList = deptMapper.findDeptListPage(map);
			for (Map<String,Object> deptMap:deptList){
				String id = (String) deptMap.get("deptId");
				if (StringUtils.isBlank(id)){
					message.setMsg("班级为空");
					message.setSuccess(false);
					return;
				}
				Map<String,Object> map1 = new HashedMap();
				map1.put("classId",id);
				map1.put("isClassAdmin","1");
				List<UserInfo> userInfoList = userInfoMapper.selectAllUserByClassId(map1);
				if (userInfoList !=null && userInfoList.size()>0) {
					List<Object>list = new ArrayList<Object>();
					for (UserInfo userInfo :userInfoList){
						Map<String,String> userMap = new HashedMap();
						userMap.put("name",userInfo.getUserName());
						userMap.put("telNum",userInfo.getTelId());
						userMap.put("sex",userInfo.getSex());
						list.add(userMap);
					}
					deptMap.put("classAdminList", list);
				}else {
					deptMap.put("classAdminList", "");
				}
			}
			message.setMsg("查询成功!");
			message.setObj(deptList);
			message.setSuccess(true);
			return;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 检测企业家班级是否存在，如不存在则创建企业家班级
	 * @return
     */
	public Dept checkAndCreateEntrepreneurDept() {
		Dept dept = new Dept() ;

		try {
			Map<String, String> params = Maps.newHashMap();
			params.put("level", "4");
			params.put("deptFullName", "校友企业家");
			// 检测校友企业家班级是否已存在
			List<Dept> list = deptMapper.selectList(params);
			if (list != null && !list.isEmpty()) {
				dept = list.get(0);
				if (list.size() > 1) {
					for (int i = 1; i < list.size(); i++) {
						delete(dept.getDeptId());
					}
				}
			} else {
				params.put("level", "2");
				params.put("deptFullName", "校友企业");
				// 校友企业家班级不存在，检测校友企业院系是否存在
				List<Dept> collegeList = deptMapper.selectList(params);
				Dept college = new Dept();
				if (collegeList != null && !collegeList.isEmpty()) {
					college = collegeList.get(0);
				} else {
					// 校友企业学院不存在，创建学院
					college.setDeptName("校友企业");
					college.setParentId(ResourceBundle.getBundle("config").getString("deptNo"));
					college.setLevel(2);
					insert(college);
				}
				Dept grade = deptMapper.getById(college.getDeptId() + "2017") ;
				if (grade == null ) {
					grade = new Dept() ;
					// 校友企业学院不存在，创建学院
					grade.setDeptName("2017");
					grade.setParentId(college.getDeptId());
					grade.setLevel(3);
					insert(grade);
				}
				dept.setLevel(4);
				dept.setDeptName("校友企业家");
				dept.setParentId(grade.getDeptId());
				insert(dept);
			}

			// 创建「企业家」班级群组
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept ;
	}

}
