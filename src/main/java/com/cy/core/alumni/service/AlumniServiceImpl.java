package com.cy.core.alumni.service;

import java.util.*;

import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.alumni.dao.AlumniWaitRegistersMapper;
import com.cy.core.alumni.entity.AlumniWaitRegisters;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.mobileInterface.alumni.entity.JoinAlumni;
import com.cy.util.PairUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.dept.entity.Dept;

@Service("alumniService")
public class AlumniServiceImpl implements AlumniService {

	@Autowired
	private AlumniMapper alumniMapper;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private AlumniWaitRegistersMapper alumniWaitRegistersMapper;

	@Override
	public DataGrid<Alumni> dataGrid(Map<String, Object> map) {
		DataGrid<Alumni> dataGrid = new DataGrid<Alumni>();
		long total = alumniMapper.countAlumni(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Alumni> list = alumniMapper.selectList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	@Override
	public void insert(Alumni alumni) {
		List<Alumni> lalu = alumniMapper.selectByParent( alumni.getParentId() );
		if( lalu.size() > 1 )
		{
			alumni.setLevel( lalu.get(0).getLevel() + 1 );
			alumni.setAlumniId( lalu.get(1).getAlumniId() );
			alumni.setSequence( lalu.get(0).getSequence() + "." + alumni.getAlumniId() );
			if( lalu.get(0).getIsUsed() == null )
				lalu.get(0).setIsUsed( "2" );
			alumniMapper.insert(alumni);
		}

	}

	@Override
	public Alumni selectByAlumniName(Alumni alumni) {
		return alumniMapper.selectByAlumniName(alumni);
	}

	@Override
	public void delete(String ids) {
		if (ids != null && ids.length() > 0) {
			String[] idArray = ids.split(",");
			List<Long> list = new ArrayList<Long>();
			for (String id : idArray) {
				list.add(Long.parseLong(id));
			}
			alumniMapper.delete(list);
		}
	}

	@Override
	public Alumni getByAlumniId(long alumniId) {
		return alumniMapper.getByAlumniId(alumniId);
	}

	@Override
	public void update(Alumni alumni) {
		alumniMapper.update(alumni);
		//学院分会改变
		if( alumni.getAlumniId() == Alumni.g_lXueYuanFenHui )
			alumniMapper.selectXueYuanFenHui();
	}

	@Override
	public List<Alumni> selectAll( String leixing ) {
		Map<String,String> map = Maps.newHashMap() ;
		map.put("leixing",leixing) ;
		return alumniMapper.selectAll( map );
	}

	@Override
	public List<Alumni> selectTree(List<Dept> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alumni> selectRootTree( long id) {
		// TODO Auto-generated method stub
		return alumniMapper.selectAlumniDeptTree( id );
	}

	@Override
	public List<Alumni> selectDropTree( long id ) {
		// TODO Auto-generated method stub
		return alumniMapper.selectAlumniDeptTreeDrop( id );	//selectAlumniDeptTreeDrop
	}

	@Override
	public List<String> selectAdmin( Long id )
	{
		return alumniMapper.selectAdmin( id );
	}

	@Override
	public DataGrid<Alumni> dataGridAdmin(Map<String, Object> map) {
		DataGrid<Alumni> dataGridAdmin = new DataGrid<Alumni>();
		long total = alumniMapper.countAdminList(map);
		dataGridAdmin.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Alumni> list = alumniMapper.selectAdminList(map);
		dataGridAdmin.setRows(list);
		return dataGridAdmin;
	}
	@Override
	public long selectRole( long id )
	{
		return alumniMapper.selectRole( id );
	}
	@Override
	public List<Long> selectAlumniIdByUserId( long id )
	{
		return alumniMapper.selectAlumniIdByUserId( id );
	}

	/**
	 * 查询满足条件的校友组织列表
	 * @param map
	 * @return
	 * @auther 刘振
     */
	public List<Alumni> queryList(Map<String,Object> map) {
		List<Alumni> list = alumniMapper.queryList(map);
		return list ;
	}


	/**
	 * 去除学院与学院分会的绑定
	 * @param xueyuanId
	 * @return
	 */
	public void unbinding(String xueyuanId) {
		alumniMapper.unbinding(xueyuanId);
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


	/**
	 * 获取当前组织类型和父级院系分会
	 * @return
     */
	public PairUtil<String,Alumni> getCurrAlumniTypeAndParentCollegeAlumni(String alumniId) {
		PairUtil<String,Alumni> pairUtil = new PairUtil<String,Alumni>() ;

		Alumni alumni = alumniMapper.getByAlumniId(Long.valueOf(alumniId)) ;
		if(alumni == null) {
			return null ;
		}

		pairUtil.setOne(alumni.getMainId());

		Map<String,Object> map = Maps.newHashMap() ;
		map.put("alumniChildId",alumniId) ;
		map.put("main","1") ;
		List<Alumni> list = queryList(map) ;
		if(list != null && !list.isEmpty()) {
			pairUtil.setTwo(list.get(0));
		}

		return pairUtil;
	}


	/**
	 * 创建用户分会中间表信息
	 * @param map
	 */
	public void saveUserAlumni(Map<String,String> map) {
		if(map != null && StringUtils.isNotBlank(map.get("accountNum")) && StringUtils.isNotBlank(map.get("alumniId"))) {
			Map<String, String> temp = Maps.newHashMap();
			temp.put("accountNum", map.get("accountNum"));
			temp.put("alumniId", map.get("alumniId"));
			alumniMapper.deleteUserAlumni(temp);

			map.put("delFlag", "0");
			alumniMapper.saveUserAlumni(map);
		}
	}

	/**
	 * 一键入会
	 */
	public Message oneKeyJoin(String alumniId, String userId){
		Message message = new Message();
		UserInfo userInfo = userInfoService.selectByUserId(userId);
		if(userInfo != null){
			if(StringUtils.isNotBlank(userInfo.getAccountNum())){
				// 如果注册，强行拉入坑
				String accountNum = userInfo.getAccountNum();
				Map<String, String> map = new HashMap<String, String>();
				map.put("accountNum",accountNum);
				map.put("alumniId",alumniId);
				map.put("delFlag", "0");
				map.put("status","20");
				JoinAlumni checkStatus = alumniMapper.selectUserAlumni(map);
				if(checkStatus != null){
					alumniMapper.updateUserAlumni(map);
				}else{
					map.put("joinTime", (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
					alumniMapper.saveUserAlumni(map);
				}
				message.init(true, "校友"+userInfo.getUserName()+"成功加入本会", null);
			}else{
				// 如果未注册，先存着，待时机成熟，再拖入坑中
				AlumniWaitRegisters alumniWaitRegisters = new AlumniWaitRegisters();
				alumniWaitRegisters.setAlumniId(alumniId);
				alumniWaitRegisters.setUserId(userId);
				List<AlumniWaitRegisters> list = alumniWaitRegistersMapper.selectList(alumniWaitRegisters);
				if(list != null && list.size() > 0){
					alumniWaitRegisters = list.get(0);
					if("1".equals(alumniWaitRegisters.getIsWorked())){
						alumniWaitRegisters.setIsWorked("0");
						alumniWaitRegisters.preUpdate();
						alumniWaitRegistersMapper.update(alumniWaitRegisters);
					}
				}else{
					alumniWaitRegisters.setIsWorked("0");
					alumniWaitRegisters.preInsert();
					alumniWaitRegistersMapper.save(alumniWaitRegisters);
				}
				message.init(true, "校友"+userInfo.getUserName()+"未注册或未认证，待校友认证完毕，将自动加入本会", null);
			}
		}else{
			message.init(false, "不存在的校友数据", null);
		}
		return message;
	}

	/**
	 * 剔除成员
	 * @param alumniId
	 * @param userId
	 * @return
	 */
	public Message kickOutAlumni(String alumniId, String userId, String accountNum){
		Message message = new Message();
		Map<String, String> map = new HashMap<>();
		map.put("alumniId",alumniId);
		if(StringUtils.isBlank(accountNum)){
			UserInfo userInfo = userInfoService.selectByUserId(userId);
			if(userInfo != null){
				if(StringUtils.isNotBlank(userInfo.getAccountNum())){
					map.put("userId", userInfo.getAccountNum());
					alumniMapper.deleteByUserIdAndAlumniId(map);
					message.init(true, "成功剔除该成员", null);
				}else{
					//待认证的校友数据
					AlumniWaitRegisters alumniWaitRegisters = new AlumniWaitRegisters();
					alumniWaitRegisters.setAlumniId(alumniId);
					alumniWaitRegisters.setUserId(userId);
					alumniWaitRegistersMapper.delete(alumniWaitRegisters);
					message.init(true, "成功剔除该成员", null);
				}
			}else{
				// 校友不存在
				message.init(false, "校友不存在", null);
			}
		}else{
			map.put("userId", accountNum);
			alumniMapper.deleteByUserIdAndAlumniId(map);
			message.init(true, "成功剔除该成员", null);
		}

		return message;
	}

	/**
	 * 创建用户分会中间表信息
	 * @param map
	 */
	public void updateUserAlumni(Map<String,String> map) {
		alumniMapper.updateUserAlumni(map);
	}
	/**
	 * 查询某用户参与的分会：地方，行业，学院
	 * @param
	 */
	public List<Alumni> findAlumniListFromAccountNum(String userId){
		return alumniMapper.findAlumniListFromAccountNum(userId);
	}
}
