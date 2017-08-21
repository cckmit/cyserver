package com.cy.core.dept.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.dept.entity.Dept;

public interface DeptMapper {


	List<Dept> findtreeDeptTree( Map<String, Object> map ) ;
	List<Dept> selectTreeList(Map<String, Object> map);
	List<String> getDepts(long userId);

	List<Dept> selectAll(List<String> list);

	List<Dept> selectEvery();
	
	List<Dept> selectDepart(List<Dept> list);
	
	List<Dept> selectAll1(List<Dept> list);
	
	List<Dept> getCurRootDept( );	//lixun 2016-7-12 获取当前根部门
	
	List<Dept> selectAll2();

	void insert(Dept dept);

	void multiinsert( List<Dept> ldept );	//Lixun 2017-5-12 优化导入效率

	Dept checkDeptId(String deptId);

	Dept selectOne(String deptId);

	List<Dept> selectByDeptId(String deptId);

	void delete(List<String> list);

	List<Dept> selectByName(String deptName);

	Dept selectByNameAndParentId(Map<String, Object> map);

	List<Dept> getSchool();

	List<Dept> selectAllDept(Map<String, Object> map);

	List<Dept> selectAllClass(String deptId);

	/**
	 * 获取院系信息
	 * @param map
	 * @return
     */
	List<Dept> selectList(Map<String, String> map);

	long countDept(Map<String, Object> map);

	/**
	 * 获取院系列表信息
	 * @param map
	 * @return
     */
	List<Dept> selectDeptList(Map<String, Object> map);

	List<Dept> getDepart();

	List<Dept> getDepart1();

	List<Dept> getDepart2(String parentId);

	List<Dept> getByParentId(String deptId);

	Dept getById(String deptId);

	void update(Dept dept);
	
	void updateBelong(Dept dept);
	
	void updateAliasName(String aliasName);

	void updateFullName(Dept dept);
	
	List<Dept> getBelong1(String deptId);
	
	List<Dept> getBelong2(String deptId);
	
	Dept getByAliasName(String deptId);
	
	List<Dept> selectByDeptIds(List<String> list);
	
	List<Dept> getByParentIdAndDeptIds(Map<String, Object> map);
	
	List<Dept> selectAlumniDeptTree();	//lixun 2016-7-12

	/**
	 * 查询历史沿革与院系归属的院系列表
	 * @param map
	 * @return
     */
	List<Map<String,String>> queryDeptByAttribution(Map<String,Object> map) ;
	/**
	 * 查询历史沿革与院系归属的院系列表
	 * @param map
	 * @return
     */
	long countByAttribution(Map<String,Object> map) ;
	/**
	 * 设置现有机构
	 * @param map
     */
	void setCurrent(Map<String,String> map) ;
	/**
	 * 设置归属机构
	 * @param map
     */
	void setBelongDept(Map<String,String> map) ;

	/**
	 * 转移归属机构
	 * @param map
     */
	void shiftFromSourceBelongToGoalBelong(Map<String,String> map) ;

	/**
	 * 查询现有机构数
	 * @return
     */
	List<Dept> findCurrDeptTree( Map<String, Object> map ) ;

	/**
	 * add jiangling
	 * 根据查询条件查询树型结构的DeptInfo
	 * @param map
	 * @return
     */
	List<Dept> findDeptTreeBySearchCondition(Map<String, Object> map);

	/**
	 * add liuzhen
	 * 查询班级列表
	 * @param map
	 * @return
     */
	List<Map<String,String>> selectClassList(Map<String, Object> map);

	/**
	 * 通过用户权限查询班级列表
	 * @param map
	 * @return
     */
	List<Map<String,String>> selectClassListByUser (Map<String, Object> map);
	long countClassListByUser(Map<String, Object> map);


	/**
	 * 查询所属组织机构Id
	 */
	long selectAluByUser (long userId);

	/**
	 * 查询班级列表条数
	 * @param map
	 * @return
	 */
	long countClassList(Map<String,Object> map) ;

	/**
	 * 根据手机用户找到他所属的现有院系
	 * @param accountNum
	 * @return
     */
	List<Dept> queryCurrDeptByAccountNum( String accountNum );

	/**
	 * add lixun
	 * 根据userid查询权限内的部门
	 * @param
	 * @return
	 */
	List<Dept> selectDeptByUserId( long userid );

	String selectDeptByAlumniId( long deptid );	//lixun

	/**
	 * 获取当前学院和归属于该学院的学院的所有上级学校、下级年级班级
	 * @param map
	 * @return
     */
	List<Dept> queryCurrAndBelongDept(Map<String,String> map) ;
	/**
	 * 获取当前学院和归属于该学院的学院的所有上级学校、下级年级班级
	 * @param map
	 * @return
     */
	String getDeptAndBelongDeptIdsByCurrDeptId(Map<String,String> map) ;

	/**
	 * 更新机构状态
	 * @param dept
     */
	void updateStatus(Dept dept);

	/**
	 * 根据班级编号查出班级管理员
	 * @param classId
	 * @return
     */
	Map<String,String> selectAdminByClass(String classId) ;

	/**
	 * 根据班级ID查询聊天群组信息
	 * @param classId
	 * @return
	 */
	Map<String, String> selectChatGroupByClassId(String classId);

	/**
	 * 根据班级上级机构查询热门班级列表
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> findDeptListPage (Map<String,String> map);
}
