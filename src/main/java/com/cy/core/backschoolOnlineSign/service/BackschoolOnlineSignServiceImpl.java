package com.cy.core.backschoolOnlineSign.service;


import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;

import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.alumnicard.dao.AlumniCardExtMapper;

import com.cy.core.alumnicard.entity.AlumniCard;
import com.cy.core.alumnicard.entity.AlumniCardExt;
import com.cy.core.backschoolOnlineSign.dao.BackschoolOnlineSignMapper;
import com.cy.core.backschoolOnlineSign.entity.BackschoolOnlineSign;
import com.cy.core.schoolServ.entity.BackSchoolSign;
import com.cy.core.userProfile.dao.UserProfileMapper;

import com.cy.system.SystemUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BackschoolOnlineSignService")
public class BackschoolOnlineSignServiceImpl implements BackschoolOnlineSignService{
	private static final Logger logger = Logger.getLogger(BackschoolOnlineSignServiceImpl.class);

	@Autowired
	private BackschoolOnlineSignMapper backschoolOnlineSignMapper;

	@Autowired
	private AlumniCardExtMapper alumniCardExtMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;
	/**
	 * 存储
	 *
	 * @param backschoolOnlineSign
	 * @return true，成功；false，失败；
	 *
	 */

	public boolean  save(BackschoolOnlineSign backschoolOnlineSign) {
		return backschoolOnlineSignMapper.save(backschoolOnlineSign);
	}

	/**
	 * 更新
	 *
	 * @param backschoolOnlineSign
	 * @return true，成功；false，失败；
	 *
	 */

	public boolean update(BackschoolOnlineSign backschoolOnlineSign) {
		return backschoolOnlineSignMapper.update(backschoolOnlineSign);
	}

	/**
	 * 获取总条数
	 *
	 * @param map
	 * @return long
	 */

	public long count(Map<String, Object> map) {
		return backschoolOnlineSignMapper.count(map);
	}

	/**
	 * 所有信息列表(带分页)
	 * @param map
	 * @return DataGrid<AlumniCard>
	 */

	public DataGrid<BackschoolOnlineSign> dataGrid(Map<String, Object> map) {
		DataGrid<BackschoolOnlineSign> dataGrid = new DataGrid<BackschoolOnlineSign>();
		long total = backschoolOnlineSignMapper.count(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<BackschoolOnlineSign> list = backschoolOnlineSignMapper.list(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**
	 * 删除
	 *
	 * @param ids
	 */

	public void delete(String ids) {
		String[] array = ids.split(",");
		List<String> list = new ArrayList<String>();
		for (String id : array)
		{
			list.add(id);
		}

		backschoolOnlineSignMapper.delete(list);
	}

	/**
	 * 逻辑删除
	 *
	 * @param ids
	 */

	public void deletion(String ids) {
		// TODO Auto-generated method stub
		String[] array = ids.split(",");
		List<String> list = new ArrayList<String>();
		for (String id : array)
		{
			list.add(id);
		}

		backschoolOnlineSignMapper.deletion(list);
	}


	/**
	 * 获取详情
	 *
	 * @param
	 * @return List<AlumniCard>
	 */

	public BackschoolOnlineSign selectById(String id) {
		BackschoolOnlineSign item = backschoolOnlineSignMapper.selectById(id);
		return item;
	}



	/***********************************************************************
	 *
	 * 【校友卡】相关API（以下区域）
	 *
	 * 注意事项：
	 * 1、方法名-格式要求
	 * 创建方法：save[Name]
	 * 撤销方法：remove[Name]
	 * 查询分页列表方法：find[Name]ListPage
	 * 查询列表方法：find[Name]List
	 * 查询详细方法：find[Name]
	 *
	 ***********************************************************************/

	public void saveOnlineSign(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}

			BackschoolOnlineSign backschoolOnlineSign = JSON.parseObject(content, BackschoolOnlineSign.class);

			if (backschoolOnlineSign.getCardExtList() == null || backschoolOnlineSign.getCardExtList().size() <= 0) {
				message.setMsg("请至少填写一条学历信息");
				message.setSuccess(false);
				return;
			}
			backschoolOnlineSign.setStatus(10);
			if(StringUtils.isNotBlank(backschoolOnlineSign.getId())){
				backschoolOnlineSignMapper.update(backschoolOnlineSign);
			}else{
				/*if(StringUtils.isNotBlank(backschoolOnlineSign.getAccountNum())){
					Map<String, Object> map = new HashMap<>();
					map.put("isNoLimit", "1");
					map.put("accountNum", backschoolOnlineSign.getAccountNum());
					map.put("activityId", backschoolOnlineSign.getActivityId());
					long total = backschoolOnlineSignMapper.count(map);
					if(total > 0){
						message.init(false, "已报名", null);
						return;
					}
				}*/
				backschoolOnlineSign.preInsert();
				backschoolOnlineSign.setCardNumber(SystemUtil.getOrderNo());
				backschoolOnlineSignMapper.save(backschoolOnlineSign);
			}

			alumniCardExtMapper.deleteForSign(backschoolOnlineSign.getId());

			for (AlumniCardExt ace : backschoolOnlineSign.getCardExtList()) {
				ace.setSignCardId(backschoolOnlineSign.getId());
				ace.preInsert();
				alumniCardExtMapper.save(ace);
			}
			message.init(true , "创建成功", backschoolOnlineSign.getId());
		}catch (Exception e) {
			message.init(false , "创建失败",null);
			throw new RuntimeException(e);
		}

	}

	/**
	 *在线报名详情接口
	 *
	 */
	public void getOnlineSign(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}

			Map<String, Object> map = JSON.parseObject(content, Map.class);
			String id = (String)map.get("id");
			if (StringUtils.isBlank(id)) {
				message.setMsg("未传入用户编号");
				message.setSuccess(false);
				return;
			}

			BackschoolOnlineSign backschoolOnlineSign = backschoolOnlineSignMapper.selectById(id);
			if(backschoolOnlineSign == null)
			{
				message.init(false , "未查询到报名卡信息",null);
				return;
			}
			backschoolOnlineSign.setCardExtList(alumniCardExtMapper.selectListForSign(backschoolOnlineSign.getId()));
			message.init(true , "查询成功",backschoolOnlineSign);
		}catch (Exception e) {
			message.init(false , "查询失败",null);
			throw new RuntimeException(e);
		}
	}

	/**
	 *报名人列表接口
	 */

	public void getSignList(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}

			Map<String, Object> map = JSON.parseObject(content, Map.class);
			String page = (String) map.get("page");
			String rows = (String) map.get("rows");
			String activityId = (String)map.get("activityId");
			if (StringUtils.isBlank(activityId)) {
				message.setMsg("未传入聚会ID");
				message.setSuccess(false);
				return;
			}
			if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
				int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
				map.put("start", start);
				map.put("rows", Integer.valueOf(rows));
			}else{
				map.put("isNoLimit", "1");
			}
			List<BackschoolOnlineSign> list = backschoolOnlineSignMapper.list(map);
			if(list == null || list.size()<=0){
				message.init(false , "未查询到信息",null);
				return;
			}
			for (int i=0; i<list.size(); i++){
				list.get(i).setCardExtList(alumniCardExtMapper.selectListForSign(list.get(i).getId()));
			}

			long total = backschoolOnlineSignMapper.count(map);
			DataGrid<BackschoolOnlineSign> dataGrid = new DataGrid<>();
			dataGrid.setRows(list);
			dataGrid.setTotal(total);

			message.init(true , "查询成功",dataGrid);
		}catch (Exception e) {
			message.init(false , "查询失败",null);
			throw new RuntimeException(e);
		}
	}
}
