package com.cy.mobileInterface.alumni.service;

import java.util.*;

import com.cy.core.mobileLocal.dao.MobileLocalMapper;
import com.cy.core.mobileLocal.entity.MobileLocal;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.mobileInterface.alumni.entity.JoinAlumni;
import com.cy.system.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.common.utils.easyui.TreeStringUtil;
import com.cy.common.utils.token.Constant;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.mobevent.entity.CyEvent;
import com.cy.core.mobevent.entity.CyEventComment;

@Service("malumniService")
public class AlumniServiceImpl implements AlumniService {

	@Autowired
	private AlumniMapper alumniMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private MobileLocalMapper mobileLocalMapper;
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 查询满足条件的分会
	 * @param message
	 * @param content
     */
	public void selectAlumni(Message message ,String content) {
		try {
			if (StringUtils.isBlank(content)) {
				message.setMsg("未传入参数");
				message.setSuccess(false);
				return;
			}
			Map<String, String> map = JSON.parseObject(content, Map.class);

			String type = map.get("type");        // -1:查询所有分会(不区分地区、行业)

			if (StringUtils.isBlank(type)) {
				message.setMsg("未传访问类型");
				message.setSuccess(false);
				return;
			}

			String userId = map.get("userId");    // 查询用户编号
			String isJoin = map.get("isJoin");    // 是否加入标识(0:未加入;1:已加入)
			String status = map.get("status");    // 状态(5:邀请加入;10:申请加入;20:正式会员;30:申通不通过)
			if ((StringUtils.isNotBlank(status) || StringUtils.isNotBlank(isJoin)) && StringUtils.isBlank(userId)) {
				message.init(false, "根据加入标识或状态查询分会列表时,当前用户编号不能为空", null);
				return;
			}
			if(StringUtils.isNotBlank(userId)) {
				map.put("currUserId",userId) ;
			}

			List<Map<String, String>> list = Lists.newArrayList() ;
			if ("-1".equals(type)) {
				map.put("type","1") ;
				List<Map<String, String>> collegeList = alumniMapper.selectAlumniList(map);
				if(collegeList != null) {
					list.addAll(collegeList) ;
				}
				map.put("type","2") ;
				List<Map<String, String>> areaList = alumniMapper.selectAlumniList(map);
				if(areaList != null) {
					list.addAll(areaList) ;
				}
				map.put("type","3") ;
				List<Map<String, String>> industryList = alumniMapper.selectAlumniList(map);
				if(industryList != null) {
					list.addAll(industryList) ;
				}
			} else {
				list = alumniMapper.selectAlumniList(map);
			}
			message.init(true, "查询成功", list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void joinAlumni(Message message, String content){
		try{
			JoinAlumni joinAlumni = JSON.parseObject(content, JoinAlumni.class);

			if(StringUtils.isBlank(joinAlumni.getAccountNum()) || StringUtils.isBlank(joinAlumni.getAlumniId())){
				message.setMsg("用户ID,分会ID不能为空!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfile = userProfileMapper.selectByAccountNum(joinAlumni.getAccountNum());
			if (userProfile == null) {
				message.setMsg("查询不到此账号!");
				message.setSuccess(false);
				return;
			}

			Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(joinAlumni.getAlumniId()));
			if(alumni == null){
				message.setMsg("查询不到此分会!");
				message.setSuccess(false);
				return;
			}

			if(StringUtils.isNotBlank(joinAlumni.getStatus()) && !"40".equals(joinAlumni.getStatus()) && !"20".equals(joinAlumni.getStatus())){
				message.init(false, "非法的状态参数", null);
				return;
			}


			if(!alumni.getMainId().equals("2") && !alumni.getMainId().equals("3")){
				if(alumni.getMainId().equals("1")){
					message.setMsg("学院分会不可加入!");
				}else{
					message.setMsg("您正在申请加入不存在的分会!");
				}
				message.setSuccess(false);
				return;
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNum",joinAlumni.getAccountNum());
			map.put("alumniId",joinAlumni.getAlumniId());
			map.put("joinTime", (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
			map.put("delFlag", "0");

			if(StringUtils.isBlank(joinAlumni.getStatus())){
				map.put("status","10");
			}else{
				map.put("status",joinAlumni.getStatus());
			}

			JoinAlumni checkStatus = alumniMapper.selectUserAlumni(map);
			if(checkStatus != null){
				if(!"10".equals(map.get("status")) && !"5".equals(checkStatus.getStatus())){
					message.init(false, "您未被邀请，或已处理邀请，不可执行此操作", null);
					return;
				}
				if("1".equals(checkStatus.getDelFlag())){
					alumniMapper.updateUserAlumni(map);
					message.setMsg("欢迎重新回到分会!");
					message.setSuccess(true);
					return;
				}else if("10".equals(checkStatus.getDelFlag()) || "20".equals(checkStatus.getDelFlag())){
					message.setMsg("已加入该分会，不可重复申请!");
					message.setSuccess(false);
					return;
				}else{
					// 审核不通过，重复申请
					alumniMapper.updateUserAlumni(map);
					if("10".equals(map.get("status"))){
						message.setMsg("申请成功!");
					}else if("20".equals(map.get("status"))){
						message.setMsg("欢迎加入!");
					}else if("40".equals(map.get("status"))){
						message.setMsg("残忍拒绝!");
					}
					message.setSuccess(true);
					return;
				}
			}else if(!"10".equals(map.get("status"))){
				message.init(false, "您未被邀请，不可执行此操作", null);
				return;
			}


			alumniMapper.saveUserAlumni(map);
			message.setMsg("申请成功!");
			message.setSuccess(true);

		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void joinAlumnis(Message message, String content){
		try{
			JoinAlumni joinAlumni = JSON.parseObject(content, JoinAlumni.class);

			if(StringUtils.isBlank(joinAlumni.getAccountNum()) || StringUtils.isBlank(joinAlumni.getAlumniIds())){
				message.setMsg("账号，分会ID不能为空!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfile = userProfileMapper.selectByAccountNum(joinAlumni.getAccountNum());
			if (userProfile == null) {
				message.setMsg("查询不到此账号!");
				message.setSuccess(false);
				return;
			}

			String[] alumniIds = joinAlumni.getAlumniIds().split(",");
			for(String aid:alumniIds){
				Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(aid));
				if(!alumni.getMainId().equals("2") && !alumni.getMainId().equals("3")){
					if(alumni.getMainId().equals("1")){
						message.setMsg("学院分会不可加入!");
					}else{
						message.setMsg("您正在加入分会中存在异次元分会，我们还无法抵达!");
					}
					message.setSuccess(false);
					return;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("accountNum",joinAlumni.getAccountNum());
				map.put("alumniId",aid);
				JoinAlumni checkStatus = alumniMapper.selectUserAlumni(map);
				if(checkStatus != null && !"1".equals(checkStatus.getDelFlag()) &&("10".equals(checkStatus.getStatus()) || "20".equals(checkStatus.getStatus()))){
					message.setMsg("已加入分会，请不要重复申请!");
					message.setSuccess(false);
					return;
				}
			}

			//清除该用户已加入的分会
			//alumniMapper.clearByAccountNum(joinAlumni.getAccountNum());
			//更新用户所加入分会
			for(String aid:alumniIds){
				Map<String, String> map = new HashMap<String, String>();
				map.put("accountNum",joinAlumni.getAccountNum());
				map.put("alumniId",aid);
				map.put("status","10");
				map.put("joinTime", (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				map.put("delFlag", "0");
				JoinAlumni checkStatus = alumniMapper.selectUserAlumni(map);
				if(checkStatus != null){
					alumniMapper.updateUserAlumni(map);
				}else{
					alumniMapper.saveUserAlumni(map);
				}
			}
			message.setMsg("成功加入所选分会!");
			message.setSuccess(true);

		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void leftAlumnis(Message message, String content){
		try{
			JoinAlumni joinAlumni = JSON.parseObject(content, JoinAlumni.class);
			if(StringUtils.isBlank(joinAlumni.getAccountNum()) || StringUtils.isBlank(joinAlumni.getAlumniIds())){
				message.setMsg("账号,密码，分会ID不能为空!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfile = userProfileMapper.selectByAccountNum(joinAlumni.getAccountNum());
			if (userProfile == null) {
				message.setMsg("查询不到此账号!");
				message.setSuccess(false);
				return;
			}

			String[] alumniIds = joinAlumni.getAlumniIds().split(",");
			for(String aid:alumniIds){
				Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(aid));
				if(!alumni.getMainId().equals("2") && !alumni.getMainId().equals("3")){
					if(alumni.getMainId().equals("1")){
						message.setMsg("不可退出学院分会!");
					}else{
						message.setMsg("你正在尝试退出不存在分会!");
					}
					message.setSuccess(false);
					return;
				}
			}



			for(String aid:alumniIds){
				Map<String, String> map = new HashMap<String, String>();
				map.put("accountNum",joinAlumni.getAccountNum());
				map.put("alumniId",aid);

				if(alumniMapper.selectUserAlumni(map) == null){
					message.setMsg("你无法退出未加入的分会!");
					message.setSuccess(false);
					return;
				}
				map.put("leaveTime", (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
				map.put("delFlag", "1");
				alumniMapper.updateUserAlumni(map);
			}
			message.setMsg("成功退出所选分会!");
			message.setSuccess(true);

		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//郭亚斌 得到用户所参加的组织
	public void getUserJoinedAlumni(Message message, String content){
		Map<String, String> map = JSON.parseObject(content,Map.class);

		String userId = map.get("userId");
		String type=map.get("type");
		String alumniType=map.get("alumniType");
		String status=map.get("status");

		if(userId == null && userId== ""){
			message.setMsg("用户ID不能为空");
			message.setSuccess(false);
			return;
		}

		if(StringUtils.isBlank(alumniType)) {
			message.setMsg("未传分会类型");
			message.setSuccess(false);
			return;
		}

		switch (alumniType)
		{
			case "2" : map.put("leixing", "地");break;
			case "3" : map.put("leixing", "行业");break;
		}
		List<Alumni> list = alumniMapper.selectAll( map );



		List<Map<String,String>>  list1= new ArrayList<Map<String,String>>();
		if(type.equals("0")){

			list1=alumniMapper.getUserJoinedAlumni(map);

			}else if(type.equals("1")){

			list1=alumniMapper.getUserNotJoinedAlumni(map);
		}



		for(Map lt:list1){
			lt.put("id", String.valueOf(lt.get("id")) );
			lt.put("memberCount", String.valueOf(lt.get("memberCount")) );
          }



		message.setObj(list1);
		message.setSuccess(true);
		message.setMsg("请求成功");


	}
	/*public void getUserNotJoinedAlumni(Message message, String content){

		Map<String, String> map = JSON.parseObject(content,Map.class);
		String userId = map.get("userId");
		if(userId == null && userId== ""){
			message.setMsg("用户ID不能为空");
			message.setSuccess(false);
			return;
		}
		List<Map<String,Object>> list = alumniMapper.getUserNotJoinedAlumni(Long.parseLong(userId));

		for(Map lt:list){
			lt.put("id", String.valueOf(lt.get("id")) );
			lt.put("memberCount", String.valueOf(lt.get("memberCount")) );
		}


		message.setObj(list);
		message.setSuccess(true);
		message.setMsg("请求成功");


	}*/

	/**
	 * 获取分会列表
	 * @param map
	 * @return
     */
	public List<Map<String, String>> selectAlumni(Map<String,String> map) {
		List<Map<String, String>> list = alumniMapper.selectAlumniList(map);
		if(list != null && !list.isEmpty()) {
			// 获取分会成员数
			for(Map<String,String> temp : list) {
				Map<String,String> tempMap = Maps.newHashMap() ;
				tempMap.put("alumniId",temp.get("id")) ;
				tempMap.put("status","20") ;
				temp.put("memberCount",String.valueOf(alumniMapper.getAlumniMemberCount(tempMap))) ;
			}
		}
		return list ;
	}

	/**
	 * 获取分会详情
	 */
	public void getAlumniInfo(Message message, String content){
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, String> map = JSON.parseObject(content,Map.class);
		String alumniId = map.get("alumniId");
		if(StringUtils.isBlank(alumniId)){
			message.setMsg("分会ID不能为空");
			message.setSuccess(false);
			return;
		}

		Alumni alumni = alumniMapper.getByAlumniId(Long.parseLong(alumniId));

		message.setObj(alumni);
		message.setMsg("获取分会详情成功");
		message.setSuccess(true);
	}
	
	
	/*
	lixun 获取学院
   */
	@Override
	public long getXueYuanFenhui()
	{
		return Alumni.g_lXueYuanFenHui == 0 ? Alumni.g_lXueYuanFenHui = alumniMapper.selectXueYuanFenHui() : Alumni.g_lXueYuanFenHui;
	}
	
	@Override
	public long getDiQuFenhui()
	{
		return Alumni.g_lDiQuFenHui == 0 ? Alumni.g_lDiQuFenHui = alumniMapper.selectDiQuFenHui() : Alumni.g_lDiQuFenHui;
	}
	
	@Override
	public long getHangYeFenhui()
	{
		return Alumni.g_lHangYeFenHui == 0 ? Alumni.g_lHangYeFenHui = alumniMapper.selectHangYeFenHui() : Alumni.g_lHangYeFenHui;
	}

	/*2016-7-12 lixun 分会树形展示*/
	

	/**
	 * 组织成员列表
	 */
	public void alumniMemebers(Message message, String content) {		
		
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		
		
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		
		String aluid = (String)map.get("aluid"); //校友组织ID
		if (StringUtils.isBlank(aluid)) {
			message.setMsg("未传入校友会组织ID");
			message.setSuccess(false);
			return;
		}
		
		String page = (String)map.get("page"); //页码
		String rows = (String)map.get("rows"); //行数
		 //申请状态（5:邀请加入； 10：待审核；20：审核通过；30：审核失败；40：拒绝加入）
		String useraistatus= (String)map.get("userAlStatus"); 
		
		System.out.println("aluid="+aluid);
		System.out.println("page="+page);
		System.out.println("rows="+rows);
		System.out.println("userAlStatus="+useraistatus);		
		System.out.println("================================================");		
		
		DataGrid<UserProfile> dt=userInfoService.selectByDeptFormAlumni(map);
		
		if(dt == null)
		{
			message.setMsg("记录为空");
			message.setSuccess(false);
			return;
		}
		else {
			message.setMsg("success");
			message.setSuccess(true);
			message.setObj(dt);
			return;
		}

	}
	
	
	/*2017-8-7 sky 分会树形展示*/	
	 public void getAlumniTreeByWeb(Message message, String conten)
	 {
		
		List<TreeString> treeList = new ArrayList<TreeString>();
		List<TreeString> rootTrees = new ArrayList<TreeString>();

		//添加节点树
	//	long nDeptId = getUser().getDeptId();
		List<Alumni> rootList =alumniMapper.selectAlumniAllTree();
		//long nXueYuan = getXueYuanFenhui();	//lixun 学院分会的ID
		//long nDiQu = getDiQuFenhui();			//地区分会的ID
		//long nHangYe = getHangYeFenhui();		//行业分会的ID
		
		Map<String, Object> attributes2 = new HashMap<String, Object>();
		
		
		for( Alumni alu : rootList )
		{
			attributes2.clear();
			
			TreeString node = new TreeString();
			Long oAlumniId = alu.getAlumniId();	//lixun
			node.setId( oAlumniId.toString() );

			if( alu.getMainType().equals( "0" ) )
				node.setState( "open" );
			else
				node.setState( "close" );
			
			node.setPid( alu.getParentId().toString() );
			
			node.setText( alu.getAlumniName() );
					
	/*
			if( oAlumniId == nXueYuan )	//lixun
			{
				attributes2.put("mainType", "1");
				attributes2.put( "xueyuanfenhui", "1" );
			}else if( oAlumniId == nDiQu )
				attributes2.put("mainType", "2");
			else if( oAlumniId == nHangYe )
				attributes2.put("mainType", "3");
			else
				attributes2.put( "mainType", alu.getMainType());
			
			
			attributes2.put( "level", alu.getLevel() );
			attributes2.put( "fullName", alu.getSequence() );
			
			if( alu.getXueyuanId() != null && !alu.getXueyuanId().equals("") )
				attributes2.put( "academyid", alu.getXueyuanId() );
			
			
		  node.setAttributes( attributes2 );
 */
			
          if(alu.getMainType().equals("0"))
              rootTrees.add( node );
          else
		    treeList.add( node );
			          

		}
		
		TreeStringUtil.parseTreeString( rootTrees, treeList );
		//System.out.println( rootTrees.toString() );
	
		message.setMsg("Get tree success.");
		message.setSuccess(true);		
		message.setObj(rootTrees);
		return;

	}

	/**
	 * 查询满足条件的分会
	 *
	 * @param message
	 * @param content
	 */
	public void getCommendAlumniList(Message message, String content) {
		if (StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		//用户ID
		String accountNum = (String) map.get("accountNum");
		if (StringUtils.isBlank(accountNum)) {
			message.setMsg("请输入用户ID");
			message.setSuccess(false);
			return;
		}
		UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNum", accountNum);
		List<Alumni> alumniList = new ArrayList<Alumni>();
		//获取推荐地方分会
		if (userProfile != null && userProfile.getPhoneNum() != null) {
			MobileLocal mobileLocal = mobileLocalMapper.selectByMobileNumber(userProfile.getPhoneNum().substring(0, 7));
			String[] mobileAreaArray = mobileLocal.getMobileArea().split(" ");
			String mobileArea = "";
			if (mobileAreaArray != null) {
				if (mobileAreaArray.length == 1) {
					mobileArea = mobileAreaArray[0];
				} else if (mobileAreaArray.length == 2) {
					mobileArea = mobileAreaArray[1].replace("市", "");
				}
			}
			//通过手机归属地查询地方分会
			paramMap.put("type", "1");
			paramMap.put("mobileArea", mobileArea);
			//通过条件查询出推荐分会
			if (paramMap.get("mobileArea") != null) {
				List<Alumni> alumniListArea = alumniMapper.queryAlumniListByCondition(paramMap);
				alumniList.addAll(alumniListArea);
			}
		}
		//获取推荐行业分会
		if (userProfile != null && userProfile.getProfession() != null) {

			//通过从事行业查询行业分会 type=2
			paramMap.put("type", "2");
			//通过条件查询出推荐分会
			String code = "";
			List<Alumni> alumniListProf = alumniMapper.queryAlumniListByCondition(paramMap);
			for (Alumni prof :alumniListProf) {
				if (StringUtils.isNotBlank(prof.getIndustry())) {
					//最大产业code
					code = prof.getIndustry().substring(0,1);
					//根据行业分会代号查询行业分会产业
					String profession = alumniMapper.queryProfessionByCode(code);
					if (userProfile.getProfession().equals(profession)) {
						alumniList.add(prof);
					}
				}
			}
		}
		message.setObj(alumniList);
		message.setMsg("获取分会详情成功");
		message.setSuccess(true);
	}
}
