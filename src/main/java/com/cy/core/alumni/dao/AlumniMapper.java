package com.cy.core.alumni.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.alumni.entity.Alumni;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.mobileInterface.alumni.entity.JoinAlumni;

public interface AlumniMapper {
	/**
	 * 校友会组织列表记录总条数
	 * 
	 * @param map
	 * @return
	 */
	long countAlumni(Map<String, Object> map);

	/**
	 * 校友会组织列表
	 * 
	 * @param map
	 * @return
	 */
	List<Alumni> selectList(Map<String, Object> map);
	
	/**
	 * 新增校友组织
	 * 
	 * @param alumni
	 */
	void insert(Alumni alumni);
	
	/**
	 * 根据校友组织名称查询
	 * 
	 * @param alumni
	 * @return
	 */
	Alumni selectByAlumniName(Alumni alumni);
	
	/**
	 * 删除
	 * 
	 * @param list
	 */
	void delete(List<Long> list);
	
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
    List<Alumni> selectAll( Map<String,String> map);	//lixun
    
    /**
     * 根据地域获取校友会
     * 
     * @param map
     * @return
     */

    List<Alumni> selectByRegion(Map<String, Object> map);
    
    
    /*lixun */
	List<Long> selectAlumniIdByUserId( long id );
    List<Alumni> selectAlumniDeptTree( long id );
    List<Alumni> selectAlumniDeptTreeDrop( long id );
    List<Alumni> selectByParent( Long sid );
	List<String> selectAdmin( Long id );
	long selectXueYuanFenHui();	//学院分会ID
    /*lixun end*/
	long selectDiQuFenHui(); //地区分会ID
	long selectHangYeFenHui(); //行业分会ID
	List<Alumni> selectAdminList(Map<String, Object> map);
	long countAdminList(Map<String, Object> map);

	long selectRole( long userid );

	
	//获取组织分会结构图
    List<Alumni> selectAlumniAllTree();
    

	/* start liuzhen */

	/**
	 * 查询列表
	 * @param map
	 * @return
     */
	List<Alumni> queryList(Map<String,Object> map) ;

	/**
	 * 去除学院与学院分会的绑定
	 * @param xueyuanId
	 * @return
     */
	void unbinding(String xueyuanId) ;

	/**
	 * 查询满足条件的校友组织编号(多个编号以","隔开)
	 * @param map
	 * @return
     */
	String queryAlumniIds(Map<String,Object> map) ;

	/* end liuzhen */



	//通过用户ID和分会Id
	JoinAlumni selectUserAlumni(Map<String,String> map);

	//加入分会
	void saveUserAlumni(Map<String,String> map);

	//清除用户已存的分会关联
	void clearByAccountNum(String accountNum);

	//退出分会
	void updateUserAlumni(Map<String,String> map);

	/**
	 * 删除校友手机用户与分会中间表数据
	 */
	void deleteUserAlumni(Map<String,String> map);

	/**
	 * 获取成员列表
	 * @param map
	 * @return
     */
	List<Map<String,String>> queryMembers(Map<String,String> map) ;

	//得到某个用户所参加的组织  郭亚斌
	List<Map<String,String>> getUserJoinedAlumni(Map<String,String> map);
    //得到某个用户所没有参加过的组织 郭亚斌
	List<Map<String,String>> getUserNotJoinedAlumni(Map<String,String> map);

	/**
	 * 查询分会列表
	 * @param map
	 * @return
     */
	List<Map<String,String>> selectAlumniList(Map<String,String> map);

	/**
	 * 查询用户所在的分会组织
	 * @return
	 */
	public List<Alumni> findAlumniListFromAccountNum(String userId);

	/**
	 * 获取分户成员数
	 * @param map
	 * @return
     */
	Integer getAlumniMemberCount(Map<String,String> map) ;


	/**
	 * 删除成员
	 * @param map
	 */
	void deleteByUserIdAndAlumniId(Map<String,String> map);

	/**
	 * 查询满足条件的推荐分会(地方，行业)
	 * @param map
	 * @return
	 */
	List<Alumni>  queryAlumniListByCondition(Map<String,Object> map);

	/**
	 * 根据行业分会代号查询行业分会产业
	 * @param code
	 * @return
	 */
	 String queryProfessionByCode(String code);

}
