package com.cy.core.campuscard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.alumnicard.entity.AlumniCard;
import com.cy.core.alumnicard.entity.AlumniCardExt;
import com.cy.system.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.campuscard.dao.CampusCardMapper;
import com.cy.core.campuscard.entity.CampusCard;
import com.cy.core.campuscard.entity.CampusCardStatistical;

@Service("campusCardService")
public class CampusCardServiceImpl implements CampusCardService{

	@Autowired
	private CampusCardMapper campusCardMapper;
	
	/**
	 * 存储
	 * 
	 * @param CampusCardService campusCard
	 * @return true，成功；false，失败；
	 * 
	 */

	public boolean save(CampusCard campusCard) {
		return campusCardMapper.save(campusCard);
	}

	/**
	 * 更新
	 * 
	 * @param CampusCardService campusCard 
	 * @return true，成功；false，失败；
	 * 
	 */

	public boolean update(CampusCard campusCard) {
		// TODO Auto-generated method stub
		return campusCardMapper.update(campusCard);
	}

	/**
	 * 获取总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */

	public long count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return campusCardMapper.count(map);
	}

	/**
	 * 获取列表
	 * 
	 * @param Map<String, Object> map
	 * @return List<CampusCard>
	 */

	public List<CampusCard> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return campusCardMapper.list(map);
	}

	/**
	 * 所有信息列表(带分页)
	 * 
	 * @param Map<String, Object> map
	 * @return DataGrid<CampusCard>
	 */

	public DataGrid<CampusCard> dataGrid(Map<String, Object> map) {
		DataGrid<CampusCard> dataGrid = new DataGrid<CampusCard>();
		long total = campusCardMapper.count(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<CampusCard> list = campusCardMapper.list(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**
	 * 删除
	 * 
	 * @param String ids
	 */

	public void delete(String ids) {
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		
		campusCardMapper.delete(list);
	}

	/**
	 * 逻辑删除
	 * 
	 * @param String ids
	 */

	public void deletion(String ids) {
		// TODO Auto-generated method stub
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		
		campusCardMapper.deletion(list);
	}

	/**
	 * 批量通过
	 * 
	 * @param String ids
	 */

	public void checkToPass(String ids) {
		// TODO Auto-generated method stub
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		
		campusCardMapper.checkToPass(list);
	}

	/**
	 * 批量未通过
	 * 
	 * @param String ids
	 */

	public void checkToNotPass(String ids) {
		// TODO Auto-generated method stub
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		
		campusCardMapper.checkToNotPass(list);
	}

	/**
	 * 获取详情
	 * 
	 * @param map
	 * @return List<CampusCard>
	 */

	public CampusCard selectById(long id) {
		// TODO Auto-generated method stub
		return campusCardMapper.selectById(id);
	}

	
	
	/**
	 * 统计总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */

	public long statisticalCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return campusCardMapper.statisticalCount(map);
	}

	
	/**
	 * 统计信息列表(带分页)
	 * 
	 * @param Map<String, Object> map
	 * @return DataGrid<CampusCardStatistical>
	 */

	public DataGrid<CampusCardStatistical> statisticalDataGrid(Map<String, Object> map) {
		DataGrid<CampusCardStatistical> dataGrid = new DataGrid<CampusCardStatistical>();
		long total = campusCardMapper.statisticalCount(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<CampusCardStatistical> list = campusCardMapper.statisticalList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	/***********************************************************************
	 *
	 * 【商户卡】相关API（以下区域）
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

	public void saveCampusCard(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}

			CampusCard campusCard = JSON.parseObject(content, CampusCard.class);

			if(StringUtils.isBlank(campusCard.getBusinessLicenseNo())){
				message.setMsg("请提供营业执照编号");
				message.setSuccess(false);
				return;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessLicenseNo", campusCard.getBusinessLicenseNo());

			long count = campusCardMapper.count(map);

			if(count > 0){
				message.init(true , "已注册的营业执照编号",null);
				return;
			}
			campusCard.setCardNumber(SystemUtil.getOrderNo());
			campusCardMapper.save(campusCard);
			message.init(true , "创建成功",null);
		}catch (Exception e) {
			message.init(false , "创建失败",null);
			throw new RuntimeException(e);
		}
	}
}
