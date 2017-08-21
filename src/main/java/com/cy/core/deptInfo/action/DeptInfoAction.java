package com.cy.core.deptInfo.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.service.AlumniService;
import com.cy.core.alumni.service.AlumniServiceImpl;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.core.deptInfo.service.DeptInfoService;
import com.cy.common.utils.easyui.TreeStringUtil;
import com.cy.core.user.entity.User;
import com.cy.util.PairUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangling on 7/14/16.
 */
@Namespace("/deptInfo")
@Action(value = "deptInfoAction")
public class DeptInfoAction extends AdminBaseAction{

    private static final Logger logger = Logger.getLogger(DeptInfoAction.class);

    @Autowired
    private DeptInfoService deptInfoService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private AlumniService alumniService ;

    private PairUtil<String,String> deptIdPair ;

    private String oldDeptId ;
    private String newDeptId ;

    public void getDeptTreeGrid() {
        List<DeptInfo> deptInfoList = getUser().getDeptInfos() ;
        //1.查出登录用户所管理的所有机构:如果是院系管理员,将只查出院系机构
        List<DeptInfo> list = deptInfoService.selectAll(deptInfoList);
        super.writeJson(list);
    }

    /**
     * 删除选中的节点
     */
    public void delete() {
        Message message = new Message(); // Msg, Success, error
        try {
            //检查当前机构下是否存在子机构,如果存在子机构,请先删除子机构


        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
            logger.error(e,e);
        }
        super.writeJson(message);
    }

    /*lixun 2016-7-25 根据用户ID查询数据权限内的全部机构树*/
    public void getDeptAttrTreeByUser()
    {
        long userId = getUser().getUserId();
        List<TreeString> treeList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();
        List<DeptInfo> arDepts = deptInfoService.selectAlterDeptTreeByUser( userId );
        for ( DeptInfo ard : arDepts)
        {
            TreeString node = new TreeString();
            node.setId( ard.getDeptId() );
            if ( ard.getLevel() > 1 )
                node.setState("closed");
            else
                node.setState("open");
            node.setPid( ard.getParentId() );
            node.setText( ard.getDeptName() );
            Map<String, Object> attributes2 = new HashMap<String, Object>();
            attributes2.put("level", ard.getLevel() );
            attributes2.put("fullName", ard.getFullName() );
            node.setAttributes(attributes2);
            treeList.add(node);
            //
            if( ard.getDeptId().length() == 6 )
                rootTrees.add( node );
        }
        TreeStringUtil.parseTreeString(rootTrees, treeList);
        super.writeJson(rootTrees);
    }
    /*lixun*/

    /**
     * 通过用户所在组织，如果是院系组织获取该院及归属与该院系的班级
     */
    public void getDeptAttrTreeByUserDept(){
        List<TreeString> treeList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();

        User user = getUser();
        List<Dept> list = Lists.newArrayList() ;
        if(user != null){
            String academyId = null ;
            PairUtil<String,Alumni> pair = alumniService.getCurrAlumniTypeAndParentCollegeAlumni(String.valueOf(user.getDeptId())) ;
            if(pair != null && pair.getTwo() != null) {
                academyId = deptInfoService.getAcademyId(pair.getTwo().getAlumniId());
            }
            Map<String,String> map = Maps.newHashMap() ;

            if(StringUtils.isNotBlank(academyId)){
                map.put("currDeptId",academyId) ;
            }
            List<Dept> arDepts = deptService.queryDeptAndBelongDept(map);
            for ( Dept ard : arDepts)
            {
                int level = 0 ;
                if(StringUtils.isNotBlank(ard.getDeptId())) {
                    switch(ard.getDeptId().length()) {
                        case 6:
                            level = 1 ;
                            ard.setIconCls("ext-icon-house");
                            break;
                        case 10:
                            level = 2 ;
                            ard.setIconCls("ext-icon-department");
                            break;
                        case 14:
                            level = 3 ;
                            ard.setIconCls("ext-icon-backmenu");
                            break;
                        case 16:
                            level = 4 ;
                            ard.setIconCls("ext-icon-charity");
                            break;
                        default:
                            ard.setIconCls("ext-icon-cross");
                    }
                }

                TreeString node = new TreeString();
                node.setId( ard.getDeptId() );
                if ( level > 1 )
                    node.setState("closed");
                else
                    node.setState("open");
                node.setIconCls(ard.getIconCls());
                node.setPid( ard.getParentId() );
                node.setText( ard.getDeptName() );
                Map<String, Object> attributes2 = new HashMap<String, Object>();
                attributes2.put("level", ++level);
                attributes2.put("fullName", ard.getFullName() );
                node.setAttributes(attributes2);
                treeList.add(node);
                //
//                if( ard.getDeptId().length() == 6 )
//                    rootTrees.add( node );
            }
        }

        TreeString node = new TreeString();
        node.setState("open");
        node.setText("校友数据");
        node.setId("0");
        node.setPid("-1");
        Map<String, Object> attributes2 = new HashMap<String, Object>();
        attributes2.put("level", 1);
        attributes2.put("fullName","校友数据");
        node.setAttributes(attributes2);
        treeList.add(0,node);
        rootTrees.add(node);
        TreeStringUtil.parseTreeString(rootTrees, treeList);
        super.writeJson(rootTrees);
    }


    /**
     * 判断两班级中相同的数据
     */
    public void isSameOfTwoDept() throws Exception{
        try {
            super.writeJson(deptInfoService.isSameOfTwoDept(oldDeptId , newDeptId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 迁移班级下校友数据到另一个班级下
     * @return
     */
    public void moveUserInfoFromDeptToOtherDept() throws Exception {

        Message message = new Message();
        try {
            String code = deptInfoService.moveUserInfoFromDeptToOtherDept(oldDeptId , newDeptId) ;
            // 0：迁移成功；5: 未传需要迁移数据的班级或数据迁移到的班级；10：需要迁移数据的班级不存在；20：数据迁移到的班级不存在；30：需要迁移数据的班级下没有校友数据；40：两班级具有相同校友不能迁移；99：其他错误
            PairUtil<String,String> result = new PairUtil<String,String>() ;
            if ("0".equals(code)) {
                message.init(true,"迁移成功",null); ;
            } else if ("5".equals(code)) {
                message.init(false,"未传需要迁移数据的班级或数据迁移到的班级",null) ;
            } else if ("10".equals(code)) {
                message.init(false,"需要迁移数据的班级不存在「源班级」",null) ;
            } else if ("20".equals(code)) {
                message.init(false,"数据迁移到的班级不存在「目标班级」",null) ;
            } else if ("30".equals(code)) {
                message.init(false,"需要迁移数据的班级下没有校友数据",null) ;
            } else if ("40".equals(code)) {
                message.init(false,"两班级具有相同校友不能迁移",null) ;
            } else if ("99".equals(code)) {
                message.init(false,"其他错误",null) ;
            }

        } catch (Exception e) {
            e.printStackTrace();
            message.init(false,"其他错误E",null);
        }

        super.writeJson(message);
    }

    public PairUtil<String, String> getDeptIdPair() {
        return deptIdPair == null ? new PairUtil<String,String>() : deptIdPair ;
    }

    public void setDeptIdPair(PairUtil<String, String> deptIdPair) {
        this.deptIdPair = deptIdPair;
    }

    public String getOldDeptId() {
        return oldDeptId;
    }

    public void setOldDeptId(String oldDeptId) {
        this.oldDeptId = oldDeptId;
    }

    public String getNewDeptId() {
        return newDeptId;
    }

    public void setNewDeptId(String newDeptId) {
        this.newDeptId = newDeptId;
    }
}

