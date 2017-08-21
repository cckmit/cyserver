package com.cy.core.schoolServ.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.schoolServ.entity.*;

public interface SchoolServMapper {
	
	
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
	 * 删除
	 * 
	 * @param  list
	 */
	void delete(List<Long> list);
	
	/**
	 * 获取详情
	 * 
	 * @param id
	 * @return SchoolService
	 */
	SchoolServ selectById(long id);
	
	/**
	 * 获取服务列表
	 * 
	 * @return List<SchoolService>
	 */
	List<SchoolServ> getServiceList();

	boolean saveFxjh(Fxjh fxjh);

	//3.0返校计划
	void saveFxjhNew(Fxjh fxjh);
	List<Fxjh> findFxjhListNew(Map<String, Object> map);
	long findFxjhListCountNew(Map<String, Object> map);

	/**
	 * 获取返校计划列表
	 *
	 * @return List<SchoolService>
	 */

	List<Fxjh> findFxjhList(Map<String, Object> map);
	/**
	 * 获取详情
	 *
	 * @param
	 * @return
	 */
	Fxjh selectByFxjhId(String id);

	Fxjh selectByFxjhIdAndUserId(Map<String,String> map);

	Fxjh selectByFxjhIdAndUserIdNew(Map<String,String> map);
	BackSchoolSign isSignInOrNot(Map<String,String> map);
	void updateBackSchoolSign(Map<String,String> map);
	void insertBackSchoolSign(Map<String,String> map);
	String selectFxjhServicesById(Map<String,String> map);

	//查出总数
	long countFxjh(Map<String, Object> map);

	void updateFxjh(Fxjh fxjh);

	/**
	 * 报名返校计划
	 * @param backSchoolSign
	 */
	void saveBackSchoolSign(BackSchoolSign backSchoolSign);

	/**
	 * 查询返校计划人数
	 * @param
	 * @return
	 */
	long getUserCount(Map<String, Object> map);

	/**
	 * 查询用户在返校计划列表
	 * @param
	 * @return
	 */
	List<BackSchoolSign> findBackSchoolSignList(Map<String, Object> map);

	/**
	 * 查询用户参与、收藏数量
	 * @param
	 * @return
	 */
	Map<String, String> findUserBackSchoolCount(String userId);

	/**
	 * 删除返校报名
	 */
	void deleteBackSchoolSign(String id);

	/**
	 * 返校计划群ID更新
	 */
	void updateFxjhGroupId(Map<String, String> map);

	//保存返校计划花絮
	public void saveBackschoolBoard(BackschoolBoard backschoolBoard);

	//插入花絮图片
	public void saveBackschoolBoardPic(BackschoolBoardPic backschoolBoardPic);

	//获取返校计划花絮列表
	List<BackschoolBoard> findBackchoolBoardList(String backschoolId);

	//获取返校计划花絮列表
	Long findBackchoolBoardListCount(String backschoolId);

	//通过指定返校计划花絮对象中的参数得到返校计划花絮的对象
	public BackschoolBoard getbackschoolBoard(BackschoolBoard backschoolBoard);

	//通过指定返校计划花絮点赞对象中的参数得到返校计划花絮点赞数
	public int countBackschoolPraise(BackschoolBoardPraise backschoolBoardPraise);

	//通过指定返校计划花絮图片对象中的参数得到返校计划花絮图片列表
	List<BackschoolBoardPic> getBackschoolBoardPicList(BackschoolBoardPic backschoolBoardPic);

	//获取返校计划花絮列表
	List<BackschoolBoard> findBackchoolBoardList(Map<String, Object> map);

	//获取返校计划花絮列表数量
	Long findBackchoolBoardListCount(Map<String, Object> map);

	//通过返校计划花絮评论对象中的参数删除返校计划花絮评论
	public void deleteBachschoolBoardComment(BackschoolBoardComment backschoolBoardComment);

	//插入返校计划花絮评论对象中的参数到返校计划花絮评论表
	public void insertBackSchoolBoardComment(BackschoolBoardComment backschoolBoardComment);

	//插入返校计划花絮点赞对象中的参数到返校计划花絮点赞表
	public void insertBackschoolBoardPraise(BackschoolBoardPraise backschoolBoardPraise);

	//通过返校计划花絮点赞对象中的参数删除返校计划花絮点赞
	public void deleteBackschoolBoardPraise(BackschoolBoardPraise backschoolBoardPraise);

	//插入返校计划花絮举报对象中的参数到返校计划花絮举报表
	public void insertBackschoolBoardComplaint(BackschoolBoardComplaint backschoolBoardComplaint);

	//通过返校花絮对象中的参数删除返校计划花絮
	public void deleteBcakschoolBoard(BackschoolBoard backschoolBoard);

	//通过返校计划花絮点赞对象中的参数获取点赞id
	public String boardPraiseYesOrNotByUserId(BackschoolBoard backschoolBoard);

	//通过返校计划花絮对象中的参数获取花絮信息
	public String isOwnBoard(BackschoolBoard backschoolBoard);

	//通过返校计划花絮对象中的参数获取花絮id
	public String getBoardId(BackschoolBoard backschoolBoard);

	List<BackschoolBoardComment> getBackschoolBoardCommentList(String boardId);
}
