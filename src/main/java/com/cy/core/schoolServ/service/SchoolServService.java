package com.cy.core.schoolServ.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.schoolServ.entity.BackSchoolSign;
import com.cy.core.schoolServ.entity.Fxjh;
import com.cy.core.schoolServ.entity.SchoolServ;
import com.sun.tools.doclets.internal.toolkit.Content;

public interface SchoolServService {

	
	
	/**
	 * 存储
	 * 
	 * @param  schoolService
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean save(SchoolServ schoolService);
	
	
	/**
	 * 更新
	 * 
	 * @param  schoolService
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean update(SchoolServ schoolService);
	
	/**
	 * 获取总条数
	 * 
	 * @param  map
	 * @return long
	 */
	long count(Map<String, Object> map);
	
	/**
	 * 获取列表
	 * 
	 * @param  map
	 * @return List<SchoolService>
	 */
	List<SchoolServ> list(Map<String, Object> map);
	
	/**
	 * 所有信息列表(带分页)
	 * 
	 * @param  map
	 * @return DataGrid<SchoolService>
	 */
	DataGrid<SchoolServ> dataGrid(Map<String, Object> map);
	
	/**
	 * 删除
	 * 
	 * @param  ids
	 */
	void delete(String ids);
	
	/**
	 * 获取详情
	 * 
	 * @param id
	 * @return List<SchoolService>
	 */
	SchoolServ selectById(long id);
	
	
	/**
	 * 获取服务列表
	 * 
	 * @return List<SchoolService>
	 */
	List<SchoolServ> getServiceList();

	/**
	 * 获取活动列表接口
	 * @param message
	 * @param content
	 */
	void findSchoolServList(Message message, String content) ;

	boolean saveFxjh(Fxjh fxjh);

	void saveFxjh(Message message, String content);

	void saveFxjhNew(Message message, String content, File[] upload, String[] uploadFileName)throws FileNotFoundException, IOException;

	void dropFxjh(Message message, String content);


//*********************返校计划******************************************************//
	/**
	 * 获取返校计划列表接口
	 *
	 * @return List<Fxjh>
	 */
	List<Fxjh> getFxjhList( Map<String, Object> map );
	void findFxjhList(Message message, String content);
	void findFxjhListNew(Message message, String content);
	void findFxjhSign(Message message, String content);
	void findFxjhServices(Message message, String content);
	void updateFxjhGroupId(Message message, String content);
	/**
	 * 获取详情接口
	 *
	 * @param
	 * @return List<Fxjh>
	 */
	Fxjh selectByFxjhId(String id);
	void getFxjhById(Message message, String content);
	void getFxjhNewById(Message message, String content);

	DataGrid<BackSchoolSign> getBackSchoolSign(Map<String, Object> map);

	DataGrid<Fxjh> dataGridFxjh(Map<String, Object> map);

	/**
	 * 获取总数
	 * @param map
	 * @return
	 */
	Long findCount(Map<String,Object> map) ;
	void updateFxjh(Fxjh fxjh);

	void deleteFxjh(String ids);
	/**
	 * 返校计划报名
	 * @param message
	 * @param content
	 */
	void addBackSchoolSign(Message message, String content);

	void addBackSchoolSignQR(Message message,String content);

	/**
	 * 返校计划报名列表接口
	 * @param message
	 * @param content
	 */
	void findBackSchoolSignList(Message message, String content);

	/**
	 * 查询用户收藏的返校计划、和参与的返校计划
	 * @param message
	 * @param content
	 */
	void findUsersBackSchoolCount(Message message, String content);

	void updateWebFxjhGroupId(Map<String, String> map);

	public void saveBackSchoolBoard(Message message, String content);//返校计划花絮创建接口

	public void findBackschoolBoardList(Message message, String content);//获取返校计划花絮列表

	public void findBackschoolBoard(Message message, String content);//获取返校计划花絮详情

	public void saveCommentOrPraise(Message message, String content);//保存返校计划花絮评论或点赞

	public void saveBackschoolBoardComplaint(Message message, String content);//保存返校计划花絮举报

	public void deleteBackschoolBoard(Message message, String content);//删除返校计划花絮

}
