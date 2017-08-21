package com.cy.core.alumni.action;


import com.cy.base.entity.DataGrid;
import com.cy.core.association.entity.Association;
import com.cy.core.event.service.EventService;
import com.cy.core.user.service.UserService;
import com.google.common.collect.Maps;
import com.cy.core.association.service.AssociationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.service.AlumniService;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.common.utils.easyui.TreeStringUtil;

@Namespace("/alumni")
@Action(value = "alumniAction")
public class AlumniAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AlumniAction.class);

	@Autowired
	private AlumniService alumniService;

	@Autowired
	private DeptService deptService ;

	@Autowired
	private UserService userService ;

	@Autowired
	private AssociationService associationService;

	@Autowired
	private EventService eventService;

	private Alumni alumni;

	private String xueyuanId ;	//学院编号

	private String withOldAlumni; //包含非现有学院分会

	private String withoutOldAlumni;	// 不包含非现有学院

	public String getXueyuanId() {
		return xueyuanId;
	}

	public void setXueyuanId(String xueyuanId) {
		this.xueyuanId = xueyuanId;
	}

	/*lixun 2016-7-13*/
	private String aluid;
	public String getAluid() {
		return aluid;
	}
	public void setAluid(String Id) {
		this.aluid = Id;
	}

	/*qpf 2016-07-17*/
	private Long almainType;
	public Long getAlmainType() {return almainType; }
	public void setAlmainType(Long almainType) {this.almainType = almainType; }
	/*qpf 2016-07-22*/
	private String userAccount;
	public String getUserAccount() {return userAccount; }
	public void setUserAccount(String userAccount) {this.userAccount = userAccount; }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getWithOldAlumni() {
		return withOldAlumni;
	}

	public void setWithOldAlumni(String withOldAlumni) {
		this.withOldAlumni = withOldAlumni;
	}

	private String userName;

	@Autowired
	DeptService ds;

//	private String country;
	private String province;
	private String city;
	private String area;



    private String presidentName;



	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		map.put( "aid", aluid );		//lixun 2016-7-13 加
		map.put( "deptid", getUser().getDeptId() );
		if (alumni != null) {
			map.put("alumniName", alumni.getAlumniName());
		}
		if("1".equals(withoutOldAlumni)){
			map.put("withoutOldAlumni", withoutOldAlumni);
		}
		/*lixun 2016-7-13*/
		DataGrid<Alumni> dg = alumniService.dataGrid(map);
		List<Alumni> lalu = dg.getRows();

		for( Alumni alu:lalu )
		{
			//1.调用函数查询管理员
			//2.拼接字符串
			List<String> lAdmin = alumniService.selectAdmin( alu.getAlumniId() );

			if( lAdmin.size() > 0 )
			{
				String ss = new String( lAdmin.get( 0 ) );
				int nSize = lAdmin.size();
				for( int i = 1; i < nSize; ++i )
				{
					ss = ss + "," + lAdmin.get(i);
				}
				alu.setAdmin( ss );
			}
		}
		super.writeJson( dg );
		/*end lixun */
	}
	public void dataGridAdmin() {
		Map<String, Object> mapAdmin = new HashMap<String, Object>();
		mapAdmin.put("page", page);
		mapAdmin.put("rows", rows);
		mapAdmin.put( "aid", aluid );
		if(userAccount!=null){
			mapAdmin.put("uAcount", userAccount);
		}
		if(userName!=null){
			mapAdmin.put("uName", userName);
		}
		mapAdmin.put( "deptid", getUser().getDeptId() );	//lixun
		super.writeJson( alumniService.dataGridAdmin(mapAdmin) );
	}

	public void insert() {
		Message message = new Message();
		try {
			Alumni checkAlumni = alumniService.selectByAlumniName(alumni);
			if (checkAlumni == null) {
				alumni.setCreateTime(new Date());
				alumni.setCreateById(getUser().getUserId());
				String region = "";
//				if (country != null && country.length() > 0) {
//					region += country;
//				}
				if (province != null && province.length() > 0) {
					region += province;
				}
				if (city != null && city.length() > 0) {
					region += " " + city;
				}
				if (area != null && area.length() > 0) {
					region += " " + area;
				}
				if (region != null && region.length() > 0) {
					alumni.setRegion(region);
				}
				alumniService.insert(alumni);
				message.setMsg("新增成功");
				message.setSuccess(true);
			} else {
				message.setMsg("新增失败,组织名称重复。");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			alumniService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getByAlumniId() {
		super.writeJson(alumniService.getByAlumniId(alumni.getAlumniId()));
	}

	public void doNotNeedSecurity_getByAlumniId() {
		super.writeJson(alumniService.getByAlumniId(alumni.getAlumniId()));
	}

	public void update() {
		Message message = new Message();
		try {
			Alumni checkAlumni = alumniService.selectByAlumniName(alumni);
			if (checkAlumni == null) {
				String region = "";
//				if (country != null && country.length() > 0) {
//					region += country;
//				}
				if (province != null && province.length() > 0) {
					region += province;
				}
				if (city != null && city.length() > 0) {
					region += " " + city;
				}
				if (area != null && area.length() > 0) {
					region += " " + area;
				}
				if (region != null && region.length() > 0) {
					alumni.setRegion(region);
				}

				alumniService.update(alumni);
				message.setMsg("修改成功");
				message.setSuccess(true);
			} else {
				message.setMsg("修改失败,组织名称重复。");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void doNotNeedSecurity_getAlumni2ComboBox() {
		super.writeJson(alumniService.selectAll( "地" ) );
	}

	public Alumni getAlumni() {
		return alumni;
	}

	public void setAlumni(Alumni alumni) {
		this.alumni = alumni;
	}

//	public String getCountry() {
//		return country;
//	}
//
//	public void setCountry(String country) {
//		this.country = country;
//	}


	public String getWithoutOldAlumni() {
		return withoutOldAlumni;
	}

	public void setWithoutOldAlumni(String withoutOldAlumni) {
		this.withoutOldAlumni = withoutOldAlumni;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	/*2016-7-12 lixun 分会树形展示*/
	
	public void getAlumniTree() {
		List<TreeString> treeList = new ArrayList<TreeString>();
		List<TreeString> rootTrees = new ArrayList<TreeString>();
		//添加根节点
		//DeptMapper deptMapper = new DeptMapper();

//		List<Dept> lRootDept = ds.selectAlumniDept();
		/*
		TreeString tsRoot = new TreeString();
		tsRoot.setId( "0" );
		tsRoot.setState( "open" );
		tsRoot.setPid( "-1" );
		tsRoot.setText( lRootDept.get( 0 ).getDeptName() ); // 窗友大学
        tsRoot.setIconCls("ext-icon-role"); // 总会节点,jiangling

		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put( "mainType", 0);
		attributes.put( "level", 1 );
		attributes.put( "fullName", "1." );
		attributes.put( "userAlumniId", getUser().getDeptId() );
		tsRoot.setAttributes( attributes );
		rootTrees.add( tsRoot );
		*/

		//添加节点树
		long nDeptId = getUser().getDeptId();
		List<Alumni> rootList = alumniService.selectRootTree( getUser().getDeptId() );
		long nXueYuan = alumniService.getXueYuanFenhui();	//lixun 学院分会的ID
		long nDiQu = alumniService.getDiQuFenhui();			//地区分会的ID
		long nHangYe = alumniService.getHangYeFenhui();		//行业分会的ID
		for( Alumni alu : rootList )
		{
			TreeString node = new TreeString();
			Long oAlumniId = alu.getAlumniId();	//lixun
			node.setId( oAlumniId.toString() );

            // 添加图标, add by jiangling
            if ("99".equals(alu.getMainType())) {
                node.setIconCls("ext-icon-role");
            }
//            if ("0".equals(alu.getMainType())) {
//                node.setIconCls("ext-icon-role");
//            }
            if ("1".equals(alu.getMainType())) {
                node.setIconCls("ext-icon-charity");
            }
            if ("2".equals(alu.getMainType())) {
                node.setIconCls("ext-icon-thaw");
            }
            if ("3".equals(alu.getMainType())) {
                node.setIconCls("ext-icon-groups");
            }


			if( alu.getMainType().equals( "0" ) )
				node.setState( "open" );
			else
				node.setState( "close" );
			node.setPid( alu.getParentId().toString() );
			node.setText( alu.getAlumniName() );

			Map<String, Object> attributes2 = new HashMap<String, Object>();
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
            // add by jiangling
            if( alu.getAlumniId() == 1 )
                rootTrees.add( node );
            else{
				if(!"1".equals(withOldAlumni) &&"1".equals(alu.getMainType()) && StringUtils.isBlank(alu.getXueyuanId())){
					continue;
				}
				treeList.add( node );
			}

		}
		TreeStringUtil.parseTreeString( rootTrees, treeList );
		//System.out.println( rootTrees.toString() );
		
		super.writeJson( rootTrees );

	}
	/*分会下拉框*/
	public void getAlumniTreeDrop() {
		List<TreeString> treeList = new ArrayList<TreeString>();
		List<TreeString> rootTrees = new ArrayList<TreeString>();

		//查询
		long nXueYuan = getUser().getDeptId();
		List<Alumni> rootList = alumniService.selectDropTree( nXueYuan );
		//添加节点树
		for( Alumni alu : rootList )
		{
			TreeString node = new TreeString();
			Long oAlumniId = alu.getAlumniId();
			node.setId( oAlumniId.toString() );
			node.setState( "open" );
			node.setPid( alu.getParentId().toString() );
			node.setText( alu.getAlumniName() );
			Map<String, Object> attributes2 = new HashMap<String, Object>();
			attributes2.put( "mainType", alu.getMainType());
			attributes2.put( "level", 2 );
			attributes2.put( "fullName", alu.getSequence() );
			node.setAttributes( attributes2 );
			if( oAlumniId == nXueYuan )	//根节点
				rootTrees.add( node );
			else
				treeList.add( node );
		}
		TreeStringUtil.parseTreeString( rootTrees, treeList );

		super.writeJson( rootTrees );
	}
	
	
	/*end lixun*/


	/* start liuzhen */

	/**
	 * 查询院系组织列表
     */
	public void findCollege() {
		Map<String,Object> map = Maps.newHashMap() ;
		Long pid = alumniService.getXueYuanFenhui() ;
		map.put("pid",pid) ;

		List<Alumni> list = alumniService.queryList(map) ;
		super.writeJson( list );
	}

	/**
	 * 指定学院组织
     */
	public void designatedManagementOrganization() {
		Message message = new Message();
		try {
			if(alumni != null && alumni.getAlumniId() != 0  && StringUtils.isNotBlank(alumni.getXueyuanId())) {
				alumniService.update(alumni);
				message.init(true,"指定成功",null);
			} else {
				message.init(false, "学院分会或学院编号为空" , null);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**
	 * 创建学院组织并绑定
     */
	public void insertAndBinding() {
		Message message = new Message();
		try {
			if(StringUtils.isNotBlank(xueyuanId)) {
				String[] deptIds = xueyuanId.split(",") ;
				for(String deptId : deptIds ){

					// 根据学院编号查出学院信息
					Dept dept = deptService.getById(deptId) ;
					if(dept != null) {
						String alumniName = dept.getDeptName() + "分会" ;

						Map<String ,Object> map = Maps.newHashMap() ;
						map.put("alumniName",alumniName) ;
						// 1. 判断学院名称是否存在
						List<Alumni> list = alumniService.queryList(map) ;
						if(list != null && !list.isEmpty()) {
							// 2. 如果存在,修改
							Alumni temp = new Alumni() ;
							temp.setAlumniId(list.get(0).getAlumniId());
							temp.setXueyuanId(deptId);
							alumniService.update(temp);

							// 设置院系组织
							userService.modifyUserPermission(deptId,String.valueOf(temp.getAlumniId()),"1");
						} else {
							// 3. 如果没有,添加
							Alumni temp = new Alumni() ;
							temp.setAlumniName(alumniName);
							Long pid = alumniService.getXueYuanFenhui() ;
							temp.setParentId(pid);
							temp.setMainType("1");
							temp.setIsUsed("2");
							temp.setXueyuanId(deptId);
							alumniService.insert(temp);
							// 设置院系组织
							userService.modifyUserPermission(deptId,String.valueOf(temp.getAlumniId()),"1");
						}

					}
				}
				message.init(true, "创建学院组织并绑定成功" , null);
			} else {
				message.init(false, "学院编号为空" , null);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	/**
	 * 解绑绑定
     */
	public void unbinding() {
		Message message = new Message();
		try {
			if(StringUtils.isNotBlank(xueyuanId)) {
				String[] deptIds = xueyuanId.split(",") ;
				for(String deptId : deptIds ) {

					Map<String,Object> map = Maps.newHashMap() ;
					map.put("xueyuanId",deptId) ;
					// 根据学院编号查出学院信息
					List<Alumni> list = alumniService.queryList(map) ;
					if(list != null && !list.isEmpty()) {
						for(Alumni alumni : list) {
							userService.modifyUserPermission(String.valueOf(alumni.getXueyuanId()),String.valueOf(alumni.getAlumniId()),"0");
						}
					}
				}
				// 根据学院编号查出学院信息
//				Dept dept = deptService.getById(xueyuanId) ;
				// 解除学院与分会的绑定
				alumniService.unbinding(xueyuanId);

				message.init(true, "解除学院与学院分会的绑定成功" , null);
			} else {
				message.init(false, "学院编号为空" , null);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	/* end liuzhen */


}
