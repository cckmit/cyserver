package com.cy.core.project.service;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.dict.entity.Dict;
import com.cy.core.donation.dao.DonationMapper;
import com.cy.core.donation.entity.CountPeopleAndMoney;
import com.cy.core.donation.entity.DonateDataGrid;
import com.cy.core.project.dao.ProjectCostMapper;
import com.cy.core.project.entity.ProjectCost;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.core.weiXin.service.WeiXinUserService;
import com.cy.system.Global;
import com.cy.util.DateUtils;
import com.cy.util.PairUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.project.dao.ProjectMapper;
import com.cy.core.project.entity.Item;
import com.cy.core.project.entity.Project;
import com.cy.core.project.entity.ProjectItem;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ProjectCostMapper projectCostMapper;
	@Autowired
	private WeiXinAccountService weiXinAccountService;
	@Autowired
	private WeiXinUserService weiXinUserService;
	@Autowired
	private DonationMapper donationMapper;

	private Logger logger = Logger.getLogger(ProjectServiceImpl.class);

	public void save(Project project) {
		if("1".equals(project.getStatus())){
			project.setStartTime(TimeZoneUtils.getFormatDate());
		}
		projectMapper.save(project);
	}

	public void update(Project project) {
		if("1".equals(project.getStatus())){
			project.setStartTime(TimeZoneUtils.getFormatDate());
		}
		projectMapper.update(project);
	}

//	public void updateCommand(Project project) {
//		if("0".equals(project.getIsCommand())){
//			project.setIsCommand("ture");
//		}//isRmv.equals("true")
//		projectMapper.updateCommand(project);
//	}

	public int updateCommand(String ids) {
		String[] array = ids.split(",");
		List<String> list = new ArrayList<>();
		for (String id : array) {
			list.add(id);
		}
		projectMapper.changeIsCommand(list);
		return 0;
	}


	public int updateNotCommand(String ids) {
		String[] array = ids.split(",");
		List<String> list = new ArrayList<>();
		for (String id : array) {
			list.add(id);
		}
		projectMapper.changeIsNotCommand(list);
		return 0;
	}

	public void delete(String ids) {
		List<Long> list = new ArrayList<Long>();
		String[] idArray = ids.split(",");
		if (idArray != null) {
			for (String id : idArray) {
				list.add(Long.parseLong(id));
			}
		}
		projectMapper.delete(list);
	}

	public DataGrid<Project> dataGrid(Map<String, Object> map) {
		DataGrid<Project> dataGrid = new DataGrid<Project>();
		long total = projectMapper.countProject(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Project> list = projectMapper.selectList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public Project selectById(long id) {
		Project project = projectMapper.selectById(id);

		return project;
	}

//	@Override
//	public Project selectById1(String id) {
//		Project project = projectMapper.selectById1(id);
//		return project;
//	}

	public Project selectByProjectName(String projectName) {
		return projectMapper.selectByProjectName(projectName);
	}

	public Project selectByProjectNameAndProjectId(Project project) {
		return projectMapper.selectByProjectNameAndProjectId(project);
	}

	public List<Project> selectAll() {
		return projectMapper.selectAll();
	}

	@Override
	public List<Project> selectTop6() {
		return projectMapper.selectTop6();
	}

	@Override
	public List<Project> selectMore(int page, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		int start = (page - 1) * rows;
		map.put("start", start);
		return projectMapper.selectMore(map);
	}

	@Override
	public long selectTotalCount() {
		return projectMapper.selectTotalCount();
	}

	@Override
	public ProjectItem listAll(int page, int rows, String accountNum) {
		long totalCount = projectMapper.selectTotalCount();
		ProjectItem projectItem = new ProjectItem();
		projectItem.setCountDonateItem(totalCount);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		int start = (page - 1) * rows;
		map.put("start", start);
		List<Project> list = projectMapper.selectMore(map);
		List<Item> items = new ArrayList<Item>();
		for (Project project : list) {
			Item item = new Item();
			String proPic = project.getProjectPic().substring(0, project.getProjectPic().lastIndexOf(".")) + "_MIN"
					+ project.getProjectPic().substring(project.getProjectPic().lastIndexOf("."));
			item.setProjectPic(proPic);
			item.setProjectName(project.getProjectName());
			item.setIntroduction(project.getIntroduction());
			item.setDonateItemUrl("../../project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=" + project.getProjectId() + "&accountNum="
					+ accountNum);
			items.add(item);
		}
		projectItem.setDonateItemList(items);
		return projectItem;
	}

	public DataGrid<ProjectCost> dataGridCost(Map<String, Object> map) {
		DataGrid<ProjectCost> dataGrid = new DataGrid<>();
		long total = projectCostMapper.countProjectCost(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<ProjectCost> list = projectCostMapper.selectList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public ProjectCost selectCostById(String id){
		return projectCostMapper.selectById(id);
	}

	public void saveCost(ProjectCost projectCost){
		projectCost.preInsert();
		projectCostMapper.save(projectCost);
	}

	public void updateCost(ProjectCost projectCost){
		projectCost.preUpdate();
		projectCostMapper.update(projectCost);
	}

	public void deleteCost(String ids){
		List<String> list = new ArrayList<>();
		String[] idArray = ids.split(",");
		if (idArray != null) {
			for (String id : idArray) {
				list.add(id);
			}
		}
		projectCostMapper.delete(list);
	}


	/***********************************************************************
	 *
	 * 【爱心捐赠】相关API（以下区域）
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

	/**
	 * 查询捐赠项目列表
	 * @param message
	 * @param content
	 */
	public void findProjectList(Message message, String content) {
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String page = (String) map.get("page");
		String rows = (String) map.get("rows");
		String timeStatus = (String) map.get("timeStatus");
		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
			map.put("start", start);
			map.put("rows", Integer.valueOf(rows));
		} else {
			map.put("isNotLimit", "1");
		}
		String accountNum = (String)map.get("accountNum") ;

		map.put("status", "1");

/*		DataGrid<Item> dataGrid = new DataGrid<Item>();

		long totalCount = projectMapper.selectTotalCount();
		ProjectItem projectItem = new ProjectItem();
		projectItem.setCountDonateItem(totalCount);
		List<Project> list = projectMapper.selectMore(map);
		List<Item> items = new ArrayList<Item>();
		for (Project project : list) {
			Item item = new Item();
//			String proPic = project.getProjectPic().substring(0, project.getProjectPic().lastIndexOf(".")) + "_MIN"
//					+ project.getProjectPic().substring(project.getProjectPic().lastIndexOf("."));
			item.setProjectPic(project.getProjectPic());
			item.setProjectPicUrl(project.getProjectPicUrl());
			item.setProjectId(String.valueOf(project.getProjectId()));
			item.setProjectName(project.getProjectName());
			item.setIntroduction(project.getIntroduction());
			item.setDonateItemUrl("../../project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=" + project.getProjectId() + "&accountNum="
					+ accountNum);
			items.add(item);
		}*/
		DonateDataGrid<Project> dataGrid = new DonateDataGrid<>();
		long total = projectMapper.countProject(map);
		dataGrid.setTotal(total);
		List<Project> list = projectMapper.selectList(map);

		if("1".equals(timeStatus)){
			dataGrid.setUnderWay(String.valueOf(total));
			map.put("timeStatus", "2");
			long other = projectMapper.countProject(map);
			dataGrid.setComplete(String.valueOf(other));
		}else if("2".equals(timeStatus)){
			dataGrid.setComplete(String.valueOf(total));
			map.put("timeStatus", "1");
			long other = projectMapper.countProject(map);
			dataGrid.setUnderWay(String.valueOf(other));
		}else{
			map.put("timeStatus", "1");
			long underWay = projectMapper.countProject(map);
			dataGrid.setUnderWay(String.valueOf(underWay));
			map.put("timeStatus", "2");
			long complete = projectMapper.countProject(map);
			dataGrid.setComplete(String.valueOf(complete));
		}

		dataGrid.setRows(list);

		message.init(true ,"查询成功",dataGrid,null);
	}


	/**
	 * 查询项目详情接口
	 * @param message
	 * @param content
	 */
	public void findProject(Message message, String content) {
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String id = (String)map.get("id");

		Project project = projectMapper.selectById(Long.parseLong(id));

		message.init(true,"查询成功", project);
	}
	/**
	 * 新的查询项目详情接口
	 * @param message
	 * @param content
	 */
	public void findProjectInfo(Message message, String content) {
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String id = (String)map.get("id");

		Project project = projectMapper.selectById(Long.parseLong(id));
		map.put("projectId", id);
		map.put("isNoLimit", "1");
		List<ProjectCost> projectProgress = projectCostMapper.selectList(map);
		project.setProjectProgress(projectProgress);
		String evm = "";
		Map<String,Object> tmp = new HashMap<String,Object>();
		tmp.put("accountType","20");
		List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(tmp);
		if(weiXinAccountList != null && weiXinAccountList.size()>0 && org.apache.commons.lang.StringUtils.isNotBlank(weiXinAccountList.get(0).getCodeImage())){
			evm = weiXinAccountList.get(0).getCodeImage();
		}else{
			tmp.put("accountType","10");
			weiXinAccountList = weiXinAccountService.getList(tmp);
			if(weiXinAccountList != null && weiXinAccountList.size()>0 && org.apache.commons.lang.StringUtils.isNotBlank(weiXinAccountList.get(0).getCodeImage())){
				evm = weiXinAccountList.get(0).getCodeImage();
			}
		}
		project.setEvm(evm);


		//如果有用户账号的话顺带查询用户在该项目捐赠的金额
		String accountNum = (String)map.get("accountNum");
		String openId = (String)map.get("openId");
		String appId = (String)map.get("appId");

		if(StringUtils.isNotBlank(accountNum) || (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(appId))){

			Map<String, Object> findeUserDonateMap = new HashMap<>();
			if(StringUtils.isNotBlank(accountNum)){
				findeUserDonateMap.put("accountNum", accountNum);
			}
			if(StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(appId)){
				WeiXinUser weiXinUser = weiXinUserService.saveUserInfoByOpenId(openId, appId);
				if(weiXinUser != null){
					findeUserDonateMap.put("weixinUserId", weiXinUser.getId());
				}
			}
			findeUserDonateMap.put("projectId", id);
			CountPeopleAndMoney countPeopleAndMoney = donationMapper.countDonationMoneyAndPeople(findeUserDonateMap);
			if(Double.parseDouble(countPeopleAndMoney.getTotalMoney()) > 0){
				project.setCurrentDonate(countPeopleAndMoney.getTotalMoney());
			}
		}

		message.init(true,"查询成功", project);
	}
	/**
	 * 项目进度列表
	 * @param message
	 * @param content
	 */
	public void findProjectCost(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String page = (String) map.get("page");
		String rows = (String) map.get("rows");
		String projectId = (String) map.get("projectId");

		if(StringUtils.isBlank(projectId)){
			message.setMsg("未传入项目ID");
			message.setSuccess(false);
			return;
		}
		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
			map.put("start", start);
			map.put("rows", Integer.valueOf(rows));
		} else {
			map.put("isNoLimit", "1");
		}

		List<ProjectCost> list = projectCostMapper.selectList(map);
		if(list != null && list.size()>0){
			message.init(true, "查询成功",list);
		}else{
			message.init(false, "没有查到任何信息",null);
		}
	}

	/**
	 * 方法getALLDonateType 的功能描述：获取所有的捐赠项目类型
	 * @createAuthor niu
	 * @createDate 2017-04-01 10:59:19
	 * @param
	 * @return java.util.List<java.lang.String>
	 * @throw
	 *
	 */
	public List<PairUtil<String,String>> getALLDonateType(){
		 List<Dict> dictList =  projectMapper.getALLDonateType();
		 List<PairUtil<String,String>> pairUtilList = Lists.newArrayList();

		 if (dictList !=null &&!dictList.isEmpty()) {
			 for (Dict d : dictList) {
				 PairUtil<String,String> pairUtil = new PairUtil();
				 pairUtil.setOne(d.getDictName());
				 pairUtil.setTwo(d.getDictName());
				 pairUtilList.add(pairUtil);

			 }
		 }
		return pairUtilList;
	}


	//保存基金会项目接口
	public void saveFounProject(Message message, String content){
		try{
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			Map<String,Object> map = JSON.parseObject(content, Map.class);
			String founProject = (String) map.get("founProject");//基金会项目id
			if (StringUtils.isBlank(founProject)) {
				message.setMsg("未传入项目id参数");
				message.setSuccess(false);
				return;
			}
			String target = (String) map.get("target");//总额
			if (StringUtils.isBlank(target)) {
				message.setMsg("未传入总额参数");
				message.setSuccess(false);
				return;
			}
			String projectType = (String) map.get("projectType");//基金类型
			if (StringUtils.isBlank(projectType)) {
				message.setMsg("未传入基金类型参数");
				message.setSuccess(false);
				return;
			}
			String projectName = (String) map.get("projectName");//项目名
			if (StringUtils.isBlank(projectName)) {
				message.setMsg("未传入项目名参数");
				message.setSuccess(false);
				return;
			}
			String proContent = (String) map.get("proContent");//项目内容
			if (StringUtils.isBlank(proContent)) {
				message.setMsg("未传入项目内容参数");
				message.setSuccess(false);
				return;
			}
			String introduction = (String) map.get("introduction");//项目简介
			/*if (StringUtils.isBlank(introduction)) {
				message.setMsg("未传入项目简介参数");
				message.setSuccess(false);
				return;
			}*/
			String createDate = (String) map.get("createDate");//创建时间
			if (createDate ==null && createDate.equals("")) {
				message.setMsg("未传入项目时间参数");
				message.setSuccess(false);
				return;
			}
			String isSync = (String) map.get("isSync");//状态参数
			if (StringUtils.isBlank(isSync)) {
				message.setMsg("未传入是否同步参数");
				message.setSuccess(false);
				return;
			}

			String imageUrls = (String) map.get("imageUrls");
			String imageUrl =null;
			if (StringUtils.isNotBlank(imageUrls)){
				String[] filesArray = imageUrls.split("\\|");
				if(filesArray.length>0){
					String url = filesArray[1];
					url = "http://127.0.0.1:8080/"+ url;
					imageUrl = EditorUtils.downloadImage(url);

				}
			}

			Project project = new Project();
			//设置基金会项目中项目
			project.setIsFoun("1");
			project.setStartTime(createDate);
			project.setCreateId(35);
			project.setStatus(isSync);
			project.setContent(proContent);
			project.setProjectName(projectName);
			project.setProjectType(projectType);
			project.setSeq(1);
			project.setHasEndTime("0");
			project.setTarget(Double.valueOf(target));
			project.setHasTarget("1");
			project.setFounProject(founProject);
			project.setIntroduction(introduction);
			project.setIsFoun("1");
			project.setProjectPic(imageUrl);
			projectMapper.save(project);
			message.setMsg("新增成功");
			message.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			message.setMsg("新增失败");
			message.setSuccess(false);
		}

	}
	//修改基金会项目
	public void updateFounProject(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			Map<String,Object> map = JSON.parseObject(content, Map.class);
			String founProject = (String) map.get("founProject");//基金会项目id
			if (StringUtils.isBlank(founProject)){
				message.setMsg("未传入项目编号");
				message.setSuccess(false);
				return;
			}
			String target = (String) map.get("target");//总额
			if (StringUtils.isBlank(target)) {
				message.setMsg("未传入总额参数");
				message.setSuccess(false);
				return;
			}
			String projectType = (String) map.get("projectType");//基金类型
			if (StringUtils.isBlank(projectType)) {
				message.setMsg("未传入基金类型参数");
				message.setSuccess(false);
				return;
			}
			String projectName = (String) map.get("projectName");//项目名
			if (StringUtils.isBlank(projectName)) {
				message.setMsg("未传入项目名参数");
				message.setSuccess(false);
				return;
			}
			String proContent = (String) map.get("proContent");//项目内容
			if (StringUtils.isBlank(proContent)) {
				message.setMsg("未传入项目内容参数");
				message.setSuccess(false);
				return;
			}
			String introduction = (String) map.get("introduction");//项目简介
			/*if (StringUtils.isBlank(introduction)) {
				message.setMsg("未传入项目简介参数");
				message.setSuccess(false);
				return;
			}*/
			String createDate = (String) map.get("createDate");//创建时间
			if (StringUtils.isBlank(createDate)) {
				message.setMsg("未传入项目时间参数");
				message.setSuccess(false);
				return;
			}

			String isSync = (String) map.get("isSync");//状态参数
			if (StringUtils.isBlank(isSync)) {
				message.setMsg("未传入是否同步参数");
				message.setSuccess(false);
				return;
			}
			String isEnd = (String) map.get("isEnd");//状态参数
			if (StringUtils.isBlank(isEnd)) {
				message.setMsg("未传入是否完结状态");
				message.setSuccess(false);
				return;
			}

			String imageUrls = (String) map.get("imageUrls");
			String imageUrl =null;
			if (StringUtils.isNotBlank(imageUrls)){
				String[] filesArray = imageUrls.split("\\|");
				if(filesArray.length>0){
					String url = filesArray[1];
					logger.info("图片地址：=="+url);
					url = "http://127.0.0.1:8080/"+url;
					logger.info("图片请求地址：=="+url);
					imageUrl = EditorUtils.downloadImage(url);
					logger.info("下载后图片地址：=="+imageUrl);



				}
			}


			Project project = new Project();
			project.setIsFoun("1");
			project.setStatus(isSync);
			project.setCreateId(35);
			project.setContent(proContent);
			project.setProjectName(projectName);
			project.setProjectType(projectType);
			project.setSeq(1);
			if("0".equals(isEnd)){
				project.setHasEndTime("0");
				project.setStartTime(createDate);
			}else {
				project.setHasEndTime("1");
				project.setEndTime(createDate);
			}
			project.setTarget(Double.valueOf(target));
			project.setHasTarget("1");
			project.setFounProject(founProject);
			project.setIntroduction(introduction);
			project.setIsFoun("1");
			project.setProjectPic(imageUrl);
			Project projectTemp = projectMapper.selectByFounProject(founProject);
			if (projectTemp !=null){
				projectMapper.updateByFounProject(project);
			}else {
				projectMapper.save(project);
			}
			message.setMsg("修改成功");
			message.setSuccess(true);
		} catch (Exception e) {
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
	}


	// 删除基金会项目
	public void deleteByFounProject(Message message, String content){
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			Map<String,Object> map = JSON.parseObject(content, Map.class);
			String founProject = (String) map.get("founProject");//基金会项目id
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			projectMapper.deleteFounProject(founProject);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
	}
}
