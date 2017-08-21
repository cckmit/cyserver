package com.cy.core.dept.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.dept.entity.Dept;
import com.cy.core.user.entity.User;

public interface DeptService {
	List<Dept> selectAll(List<String> list);


	/**
	 * 查询历史沿革与院系归属的院系列表
	 * @param map
	 * @return
	 */
	DataGrid<Map<String,String>> queryDeptByAttribution(Map<String,Object> map) ;

    /**
     * 设置现有学院
     */
    public void setCurrentDept(Map<String,String> map) ;
    /**
     * 设置归属学院
     */
    public void setBelongDept(Map<String,String> map) ;

    /**
     * 转移归属学院
     */
    public void shiftFromSourceBelongToGoalBelong(Map<String,String> map) ;

    /**
     * 查询现有机构数
     * @return
     */
    List<Dept> findCurrDeptTree() ;

    /*查询之前*/
	public List<Dept> findbofreDeptTree(Dept dept);


	List<Dept> selectEvery();

	List<Dept> selectDepart(List<Dept> list);
	
	List<Dept> selectAll1(List<Dept> list);
	
	void insert(Dept dept);
	
	void insertAlias(Dept dept);
	
	Dept checkDeptId(String deptId);
	
	void delete(String deptId);
	
	String importData(String url,User user);
	
	List<Dept> getSchool();
	
	List<Dept> getDepart();
	
	List<Dept> getDepart1();
	
	List<Dept> selectAllClass(String deptId);
	
	DataGrid<Dept> dateGridForUser(Map<String, Object> map);
	
	Dept selectByNameAndParentId(Map<String, Object> map);
	
	List<Dept> getByParentId(String deptId);
	
	List<Dept> getByParentIdAndDeptIds(Map<String, Object> map);
	
	Dept getById(String deptId);
	
	void update(Dept dept);
	
	void updateBelong(Dept dept);
	
	List<Dept> getBelong(String deptId);
	
	Dept getByAliasName(String deptId);
	
	List<Dept> selectByDeptIds(List<String> list);
	
	List<Dept> selectAlumniDept();//lixun

	List<String> getDepts(long userId);

	List<Dept> findDeptTreeBySearchCondition(Map<String,Object> map); // jiangling

	/**
	 * 院系列表
	 *
	 * @param map
	 * @return
	 */
	DataGrid<Dept> dataGrid(Map<String, Object> map);

	/**
	 * add liuzhen
	 * 查询班级列表
	 * @param map
	 * @return
	 */
	DataGrid<Map<String,String>> selectClassList(Map<String, Object> map);

	/**
	 * 通过用户数据权限查询班级列表
	 */
	DataGrid<Map<String,String>> selectClassListByUser(Map<String, Object> map);

	/**
	 * 查询所属组织机构Id
	 */
	long selectAluByUser (long userId);

	/**
	 * add lixun
	 * 查询部门表,根据userID获取权限范围内的部门
	 * @param userid
	 * @return
	 */
	List<Dept> selectDeptByUserId( long userid );
	String selectDeptByAlumniId( long deptid );

	/**
	 * 获取当前学院和归属于当前学院的学院列表
	 * @param map
	 * @return
	 */
	public List<Dept> queryDeptAndBelongDept(Map<String,String> map) ;

	/**
	 * 获取当前学院和归属于当前学院的学院编号字符串(多个编号以","隔开)
	 * @param map
	 * @return
	 */
	public String getDeptAndBelongDeptIdsByCurrDeptId(Map<String,String> map) ;

	/**
	 * 根据班级上级机构查询热门班级列表
	 * @param message
	 * @param content
	 * @return
	 */
	public void findDeptListPage(Message message,String content);


	/**
	 * 检测企业家班级是否存在，如不存在则创建企业家班级
	 * @return
	 */
	public Dept checkAndCreateEntrepreneurDept();
}
