package com.cy.mobileInterface.alumni.service;

import com.cy.base.entity.Message;

import java.util.List;
import java.util.Map;

public interface  AlumniService {

	/**
	 * 单个加入分会接口
	 */
	void joinAlumni(Message message, String content);

	/**
	 * 批量加入分会接口
	 */
	void joinAlumnis(Message message, String content);

	/**
	 * 退出出分会接口
	 */
	void leftAlumnis(Message message, String content);

	/**
	 * 用户所加入的分会  郭亚斌
	 */
	void getUserJoinedAlumni(Message message, String content);

	/**
	 * 查询满足条件的分会
	 * @param message
	 * @param content
	 */
	void selectAlumni(Message message ,String content) ;

	/**
	 * 获取分会列表
	 * @param map
	 * @return
	 */
	List<Map<String, String>> selectAlumni(Map<String,String> map) ;

	/**
	 * 获取分会详情
	 */
	void getAlumniInfo(Message message, String content);
	
	void getAlumniTreeByWeb(Message message, String content);

	long getXueYuanFenhui();

	long getDiQuFenhui();

	long getHangYeFenhui();
	
	void alumniMemebers(Message message, String content);

	/**
	 * 查询满足条件的推荐分会(地方，行业)
	 * @param message
	 * @param content
	 */
	void getCommendAlumniList(Message message ,String content);
}
