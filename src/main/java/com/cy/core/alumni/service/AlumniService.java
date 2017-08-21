package com.cy.core.alumni.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.dept.entity.Dept;
import com.cy.util.PairUtil;


public interface AlumniService {
	/**
	 * 校友会组织列表
	 * 
	 * @param map
	 * @return
	 */
	DataGrid<Alumni> dataGrid(Map<String, Object> map);
	/**
	 * 校友管理员列表
	 */
	DataGrid<Alumni> dataGridAdmin(Map<String, Object> map);

	/**
	 * 新增校友组织
	 * 
	 * @param alumni
	 */
	void insert(Alumni alumni);

	/**
	 * 批量删除校友组织
	 * 
	 * @param ids
	 */
	void delete(String ids);

	/**
	 * 根据校友组织名称查询
	 * 
	 * @param alumni
	 * @return
	 */
	Alumni selectByAlumniName(Alumni alumni);

	/**
	 * 根据主键查询对象
	 * 
	 * @param alumniId
	 * @return
	 */
	Alumni getByAlumniId(long alumniId);
	
	/**
	 * 修改校友组织
	 * 
	 * @param alumni
	 */
	void update(Alumni alumni);
	
	
    /**
     * 查询所有校友组织
     * 
     * @return
     */
    List<Alumni> selectAll( String leixing );
	List<Long> selectAlumniIdByUserId( long id );
    List<Alumni> selectTree(List<Dept> list);	//lixun 2016-7-12
    List<Alumni> selectRootTree( long id );	//lixun 2016-7-12
    List<Alumni> selectDropTree( long id );
	List<String> selectAdmin( Long id );
	long getXueYuanFenhui();
	long getDiQuFenhui();
	long getHangYeFenhui();
	long selectRole( long id );

	/**
	 * 查询满足条件的校友组织列表
	 * @param map
	 * @return
	 * @auther 刘振
	 */
	public List<Alumni> queryList(Map<String,Object> map) ;

	/**
	 * 去除学院与学院分会的绑定
	 * @param xueyuanId
	 * @return
	 */
	void unbinding(String xueyuanId) ;

	/**
	 * 获取当前组织类型和父级院系分会
	 * @return
	 */
	public PairUtil<String,Alumni> getCurrAlumniTypeAndParentCollegeAlumni(String alumniId) ;

	/**
	 * 创建用户分会中间表信息
	 * @param map
	 */
	public void saveUserAlumni(Map<String,String> map) ;


	/**
	 * 一键拉入分会
	 * @param alumniId
	 * @param userId
	 * @return
	 */
	Message oneKeyJoin(String alumniId, String userId);


	/**
	 * 剔除成员
	 * @param alumniId
	 * @param userId
	 * @param accountNum
	 * @return
	 */
	Message kickOutAlumni(String alumniId, String userId, String accountNum);

	/**
	 * 创建用户分会中间表信息
	 * @param map
	 */
	public void updateUserAlumni(Map<String,String> map) ;

	/**
	 * 查出某个用户所参加的各种分会：地方，行业，学院
	 * @param map
	 */
	public List<Alumni> findAlumniListFromAccountNum(String userId);


}
