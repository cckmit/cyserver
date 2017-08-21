package com.cy.core.alumnicard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.alumnicard.dao.AlumniCardExtMapper;
import com.cy.core.alumnicard.entity.AlumniCardExt;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.SystemUtil;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.alumnicard.dao.AlumniCardMapper;
import com.cy.core.alumnicard.entity.AlumniCard;
import com.cy.core.alumnicard.entity.AlumniCardStatistical;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;

@Service("alumniCardService")
public class AlumniCardServiceImpl implements AlumniCardService{
	private static final Logger logger = Logger.getLogger(AlumniCardServiceImpl.class);

	@Autowired
	private AlumniCardMapper alumniCardMapper;

	@Autowired
	private AlumniCardExtMapper alumniCardExtMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	/**
	 * 存储
	 * 
	 * @param alumniCard
	 * @return true，成功；false，失败；
	 * 
	 */

	public boolean save(AlumniCard alumniCard) {
		alumniCard.preInsert();
		return alumniCardMapper.save(alumniCard);
	}

	/**
	 * 更新
	 * 
	 * @param alumniCard
	 * @return true，成功；false，失败；
	 * 
	 */

	public boolean update(AlumniCard alumniCard) {
		return alumniCardMapper.update(alumniCard);
	}

	/**
	 * 获取总条数
	 * 
	 * @param map
	 * @return long
	 */

	public long count(Map<String, Object> map) {
		return alumniCardMapper.count(map);
	}
	
	/**
	 * 所有信息列表(带分页)
	 * 
	 * @param map
	 * @return DataGrid<AlumniCard>
	 */

	public DataGrid<AlumniCard> dataGrid(Map<String, Object> map) {
		DataGrid<AlumniCard> dataGrid = new DataGrid<AlumniCard>();
		long total = alumniCardMapper.count(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<AlumniCard> list = alumniCardMapper.list(map);
		if(list != null && list.size() > 0){
			for(int i=0 ; i < list.size(); i++){
				list.get(i).setCardExtList(alumniCardExtMapper.selectList(list.get(i).getId()));
			}
		}
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
		List<String> list = new ArrayList<>();
		for (String id : array)
		{
			list.add(id);
		}
		
		alumniCardMapper.delete(list);
	}

	/**
	 * 批量通过
	 * 
	 * @param ids
	 */

	public void checkToPass(String ids) {
		String[] array = ids.split(",");
		List<String> list = new ArrayList<>();
		for (String id : array)
		{
			list.add(id);
		}
		
		alumniCardMapper.checkToPass(list);
	}

	/**
	 * 批量未通过
	 * 
	 * @param ids
	 */

	public void checkToNotPass(String ids) {
		// TODO Auto-generated method stub
		String[] array = ids.split(",");
		List<String> list = new ArrayList<>();
		for (String id : array)
		{
			list.add(id);
		}
		
		alumniCardMapper.checkToNotPass(list);
	}

	/**
	 * 获取详情
	 * 
	 * @param
	 * @return List<AlumniCard>
	 */

	public AlumniCard selectById(String id) {
		AlumniCard item = alumniCardMapper.selectById(id);
		if(item != null && StringUtils.isNotBlank(item.getId())){
			item.setCardExtList(alumniCardExtMapper.selectList(id));
		}
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

	public void saveAlumniCard(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}

			AlumniCard alumniCard = JSON.parseObject(content, AlumniCard.class);

			if (alumniCard.getCardExtList() == null || alumniCard.getCardExtList().size() <= 0) {
				message.setMsg("请至少填写一条学历信息");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isNotBlank(alumniCard.getAccountNum())){
				Map<String, Object> map = new HashMap<>();
				map.put("accountNum", alumniCard.getAccountNum());
				map.put("isNoLimit", "1");
				List<AlumniCard> alumniCardList = alumniCardMapper.list(map);
				if(alumniCardList!= null  && alumniCardList.size() > 0){
					alumniCard.setId(alumniCardList.get(0).getId());
				}
			}
			if(StringUtils.isNotBlank(alumniCard.getId())){
				alumniCard.preUpdate();
				alumniCard.setStatus(0);
                alumniCardMapper.update(alumniCard);
            }else{
				alumniCard.preInsert();
				alumniCard.setCardNumber(SystemUtil.getOrderNo());
				alumniCard.setApplyTime(TimeZoneUtils.getFormatDate());
                alumniCardMapper.save(alumniCard);
            }

            alumniCardExtMapper.delete(alumniCard.getId());
			for (AlumniCardExt ace : alumniCard.getCardExtList()) {
				ace.setAlumniCardId(alumniCard.getId());
				ace.preInsert();
				alumniCardExtMapper.save(ace);
			}
			message.init(true , "创建成功",alumniCard.getId());
		}catch (Exception e) {
            message.init(false , "创建失败",null);
            throw new RuntimeException(e);
		}

	}

	public void findAlumniCard(Message message, String content){
        try {
            if (StringUtils.isBlank(content)) {
                message.setMsg("未传入参数");
                message.setSuccess(false);
                return;
            }

            Map<String, Object> map = JSON.parseObject(content, Map.class);
            String accountNum = (String)map.get("accountNum");
            String alumniCardId = (String) map.get("alumniCardId");

            if (StringUtils.isBlank(accountNum) && StringUtils.isBlank(alumniCardId)) {
                message.setMsg("请传入用户ID或卡片Id");
                message.setSuccess(false);
                return;
            }
            map.put("isNoLimit", "1");
			List<AlumniCard> list = alumniCardMapper.list(map);
			if(list == null || list.size()<=0){
				message.init(false , "未查询到校友卡信息",null);
				return;
			}
            AlumniCard alumniCard = list.get(0);
            alumniCard.setCardExtList(alumniCardExtMapper.selectList(alumniCard.getId()));
            message.init(true , "查询成功",alumniCard);
        }catch (Exception e) {
            message.init(false , "查询失败",null);
            throw new RuntimeException(e);
        }
    }

    public void findUserInfoForCard(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}

			Map<String, Object> map = JSON.parseObject(content, Map.class);
			String accountNum = (String)map.get("accountNum");

			if (StringUtils.isBlank(accountNum)) {
				message.setMsg("请传入用户ID");
				message.setSuccess(false);
				return;
			}
			UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
			if(userProfile == null || StringUtils.isBlank(userProfile.getBaseInfoId())){
				message.init(false, "用户不存在或没有学习经历", null);
				return;
			}
			AlumniCard alumniCard = new AlumniCard();
			alumniCard.setName(userProfile.getName());
			alumniCard.setPhone(userProfile.getPhoneNum());
			alumniCard.setBirthday(userProfile.getBirthday());
			alumniCard.setEmail(userProfile.getEmail());
			alumniCard.setAddress(userProfile.getAddress());
			alumniCard.setSex(userProfile.getSex());
			alumniCard.setWorkUnit(userProfile.getWorkUtil());
			alumniCard.setPosition(userProfile.getPosition());
			List<AlumniCardExt> extList = new ArrayList<>();
			String[] baseInfoIds = userProfile.getBaseInfoId().split(",");
			for(String bif:baseInfoIds){
				AlumniCardExt alumniCardExt = new AlumniCardExt();
				UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(bif);
				alumniCardExt.setClazz(userInfo.getClassName());
				alumniCardExt.setDegree(userInfo.getStudentType());
				alumniCardExt.setDepart(userInfo.getDepartName());
				alumniCardExt.setStartTime(String.valueOf(userInfo.getEntranceTime()));
				alumniCardExt.setEndTime(String.valueOf(userInfo.getGraduationTime()));
				alumniCardExt.setMajor(userInfo.getMajorName());

				extList.add(alumniCardExt);
			}

			alumniCard.setCardExtList(extList);

			message.init(true , "查询成功",alumniCard);
		}catch (Exception e) {
			message.init(false , "查询失败",null);
			throw new RuntimeException(e);
		}
	}

}
