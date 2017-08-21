package com.cy.core.dept.action;

import com.cy.core.deptInfo.service.DeptInfoService;
import com.cy.core.role.entity.Role;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.core.userbaseinfo.entity.UserBaseInfo;
import com.cy.core.userbaseinfo.service.UserBaseInfoService;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.common.utils.easyui.TreeStringUtil;

@Namespace("/dept")
@Action(value = "deptAction")
public class DeptAction extends AdminBaseAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DeptAction.class);

    @Autowired
    private DeptService deptService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    private String classId;    // 班级ID

    private Dept dept;

    private String deptId;

    private String deptCurrIds;    // 多个deptId 组合的字符串,已","隔开

    private String isCurrent;        // 是否现有

    private String userId;          // 校友编号
    private String userIds;          // 校友编号(多个userId 组合的字符串,已","隔开)
    private String isClassAdmin;    // 是否班级管理员(0:不是;1:是)

    private String sourceBelongId;    // 源归属机构
    private String goalBelongId;    // 目标归属机构

    private String url;



    /* lixun 获取未匹配标志 */
    private long noMatch = 0;   //现有树的未匹配标志
    public long getNoMatch() {
        return noMatch;
    }
    public void setNoMatch(long noMatch) {
        this.noMatch = noMatch;
    }
    /* lixun */

    private Map<String,String> queryMap = Maps.newHashMap() ;   //查询条件Map

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeptCurrIds() {
        return deptCurrIds;
    }

    public void setDeptCurrIds(String deptCurrIds) {
        this.deptCurrIds = deptCurrIds;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getSourceBelongId() {
        return sourceBelongId;
    }

    public void setSourceBelongId(String sourceBelongId) {
        this.sourceBelongId = sourceBelongId;
    }

    public String getGoalBelongId() {
        return goalBelongId;
    }

    public void setGoalBelongId(String goalBelongId) {
        this.goalBelongId = goalBelongId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getIsClassAdmin() {
        return isClassAdmin;
    }

    public void setIsClassAdmin(String isClassAdmin) {
        this.isClassAdmin = isClassAdmin;
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    public void getDeptGrid() {

        Map<String, Object> map = new HashedMap();

        // 分页插叙条件
        map.put("page", page);
        map.put("rows", rows);

        // 表单查询条件
        if (dept != null) {
            map.put("belongDeptId", dept.getBelongDeptId());
            map.put("isCurrent", dept.getIsCurrent());
            map.put("hasBelong", dept.getHasBelong());
            map.put("deptPid", dept.getDeptPid());

        }

        // 根据查询条件查询，遍历设置图标
        List<Dept> deptList = new ArrayList<>();

        List<Dept> deptsFromUser = getUser().getDepts();
        map.put("list", deptsFromUser);

//        deptList = deptService.dataGrid(map);

        super.writeJson(deptService.dataGrid(map));
    }

    public void getDeptTreeGrid() {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Dept> deptList = new ArrayList<>();

        List<Dept> deptsFromUser = getUser().getDepts();
        map.put("list", deptsFromUser);

        //有条件查询
        if (dept != null) {
            map.put("belongDeptId", dept.getBelongDeptId());
            map.put("isCurrent", dept.getIsCurrent());
            map.put("hasBelong", dept.getHasBelong());
//            map.put("deptName",dept.getDeptName());
            map.put("schoolName",dept.getSchoolName());
            map.put("departName",dept.getDepartName());
            map.put("grade",dept.getGrade());
            map.put("classes",dept.getClasses());
        }

        deptList = deptService.findDeptTreeBySearchCondition(map);
        //add by jiangling
        List<Dept> deptListMitIcons = new ArrayList<Dept>();
        for( Dept dept: deptList){
            switch (dept.getDeptId().length()) {
                case 6: dept.setIconCls("ext-icon-house");
                    break;
                case 10: dept.setIconCls("ext-icon-department");
                    break;
                case 14: dept.setIconCls("ext-icon-backmenu");
                    break;
                case 16: dept.setIconCls("ext-icon-charity");
                    break;
                default: dept.setIconCls("ext-icon-cross");
            }

            deptListMitIcons.add(dept);
        }

        super.writeJson(deptListMitIcons);

    }

    /**
     * 查询历史沿革与院系归属院系列表数据
     */
    public void getDeptGridByAttribution() {
        Map<String, Object> map = new HashedMap();
        map.put("page", page);
        map.put("rows", rows);
        map.put("limit", "query");

        if (dept != null) {
            map.put("belongDeptId", dept.getBelongDeptId());
            map.put("isCurrent", dept.getIsCurrent());
            map.put("hasBelong", dept.getHasBelong());

        }
        if (queryMap != null) {
            map.put("schoolName", queryMap.get("schoolName"));
            map.put("collegeName", queryMap.get("collegeName"));
        }
        super.writeJson(deptService.queryDeptByAttribution(map));
    }

    /**
     * 设置现有学院
     */
    public void setCurrentDept() {

        Message message = new Message();
        try {
            // 检查当前结构下是否存在学生,如果存在不允许删除
            if (StringUtils.isBlank(deptCurrIds)) {
                message.setMsg("未选择需要设置现有的学院");
                message.setSuccess(false);
            } else {
                Map<String, String> map = new HashedMap();
                map.put("deptIds", deptCurrIds);
                map.put("isCurrent", isCurrent);
                deptService.setCurrentDept(map);

                // 取消现有时,去除该学院下的归属机构
                if (!"1".equals(isCurrent)) {
                    map.clear();
                    map.put("deptIds", deptCurrIds);
                    map.put("belongDeptId", "");
                    deptService.setBelongDept(map);
                }
                message.setMsg("设置成功");
                message.setSuccess(true);
            }
        } catch (Exception e) {
            message.setMsg("设置失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void getDeptTree() {
        List<Dept> list = deptService.selectAll1(getUser().getDepts());
        List<TreeString> treeList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();
        if (getUser().getDepts() != null && getUser().getDepts().size() > 0) {
            for (Dept dept : list) {
                for (Dept dept2 : getUser().getDepts()) {
                    if (dept.getDeptId().equals(dept2.getDeptId())) {
                        TreeString node = new TreeString();
                        node.setId(dept.getDeptId());
                        if (dept.getParentId().equals("0")) {
                            node.setState("open");
                        } else {
                            node.setState("closed");
                        }
                        node.setPid(dept.getParentId());
                        node.setText(dept.getDeptName());
                        Map<String, Object> attributes = new HashMap<String, Object>();
                        attributes.put("level", dept.getLevel());
                        attributes.put("fullName", dept.getFullName());
                        node.setAttributes(attributes);
                        rootTrees.add(node);
                    }
                }
            }
        } else {
            for (Dept dept : list) {
                if (dept.getParentId().equals("0")) {
                    TreeString node = new TreeString();
                    node.setId(dept.getDeptId());
                    node.setState("open");
                    node.setPid(dept.getParentId());
                    node.setText(dept.getDeptName());
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("level", dept.getLevel());
                    attributes.put("fullName", dept.getFullName());
                    node.setAttributes(attributes);
                    rootTrees.add(node);
                }
            }
        }
        if (list != null && list.size() > 0) {
            for (Dept dept : list) {
                TreeString node = new TreeString();
                node.setId(dept.getDeptId());
                node.setPid(dept.getParentId());
                node.setText(dept.getDeptName());
                if (dept.getParentId().equals("0")) {
                    node.setState("open");
                } else {
                    node.setState("closed");
                }
                Map<String, Object> attributes = new HashMap<String, Object>();
                attributes.put("level", dept.getLevel());
                attributes.put("aliasName", dept.getAliasName());
                node.setAttributes(attributes);
                treeList.add(node);
            }
        }
        TreeStringUtil.parseTreeString(rootTrees, treeList);
        super.writeJson(rootTrees);
    }

    /**
     * 获取现有树
     */
    public void getCurrDeptTree() {
        long time = System.currentTimeMillis() ;
        /* lixun 添加参数分会未匹配学院标志*/
        List<Dept> list = deptService.findCurrDeptTree();
        List<TreeString> treeList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();
        if (getUser().getDepts() != null && getUser().getDepts().size() > 0) {
            for (Dept dept : list) {
                for (Dept dept2 : getUser().getDepts()) {
                    if (dept.getDeptId().equals(dept2.getDeptId())) {
                        TreeString node = new TreeString();
                        node.setId(dept.getDeptId());
                        if (dept.getParentId().equals("0")) {
                            node.setState("open");
                        } else {
                            node.setState("closed");
                        }
                        node.setPid(dept.getParentId());
                        node.setText(dept.getDeptName());
                        Map<String, Object> attributes = new HashMap<String, Object>();
                        attributes.put("level", dept.getLevel());
                        attributes.put("fullName", dept.getFullName());
                        node.setAttributes(attributes);
                        rootTrees.add(node);
                    }
                }
            }
        } else {
            for (Dept dept : list) {
                if (dept.getParentId().equals("0")) {
                    TreeString node = new TreeString();
                    node.setId(dept.getDeptId());
                    node.setState("open");
                    node.setPid(dept.getParentId());
                    node.setText(dept.getDeptName());
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("level", dept.getLevel());
                    attributes.put("fullName", dept.getFullName());
                    node.setAttributes(attributes);
                    rootTrees.add(node);
                }
            }
        }
        if (list != null && list.size() > 0) {
            for (Dept dept : list) {
                TreeString node = new TreeString();
                node.setId(dept.getDeptId());
                node.setPid(dept.getParentId());
                node.setText(dept.getDeptName());
                if (dept.getParentId().equals("0")) {
                    node.setState("open");
                } else {
                    node.setState("closed");
                }
                Map<String, Object> attributes = new HashMap<String, Object>();
                attributes.put("level", dept.getLevel());
                attributes.put("aliasName", dept.getAliasName());
                node.setAttributes(attributes);
                treeList.add(node);
            }
        }
        TreeStringUtil.parseTreeString(rootTrees, treeList);
        System.out.println("-----> 用时: " + (System.currentTimeMillis() - time) ) ;
        super.writeJson(rootTrees);
    }

    /**
     *院系归属树型结构图
     */
    public void findbofreDeptTree() {
        long time = System.currentTimeMillis() ;
        Dept depttj=new Dept();
        depttj.setStatus("20");
        depttj.setIsCurrent("1");
        depttj.setBelongDeptId("1");
        List<Dept> list = deptService.findbofreDeptTree(depttj);
        List<TreeString> treeList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();
        if (list.size()>0) {
            for (Dept dept : list) {
                if ("1".equals(dept.getBelongDeptId())) {
                    TreeString node = new TreeString();
                    node.setId(dept.getDeptId());
                    node.setState("open");
                    node.setPid(dept.getBelongDeptId());
                    String str = dept.getFullName();
                    String[] strs = str.split(",");
                    String deText;
                    deText = strs[0] + "—" + strs[1];
                    node.setText(deText);
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("level", dept.getLevel());
                    attributes.put("fullName", dept.getFullName());
                    attributes.put("belongDeptName", dept.getBelongDeptName());
                    attributes.put("isCurrent", dept.getIsCurrent());
                    node.setAttributes(attributes);
                    rootTrees.add(node);
                }
            }
            for (Dept dept : list) {
                TreeString node = new TreeString();
                node.setId(dept.getDeptId());
                node.setState("open");
                node.setPid(dept.getBelongDeptId());
                String str = dept.getFullName();
                String[] strs = str.split(",");
                String deText;
                deText = strs[0] + "—" + strs[1];
                node.setText(deText);
                Map<String, Object> attributes = new HashMap<String, Object>();
                attributes.put("level", dept.getLevel());
                attributes.put("fullName", dept.getFullName());
                attributes.put("belongDeptName", dept.getBelongDeptName());
                attributes.put("isCurrent", dept.getIsCurrent());
                node.setAttributes(attributes);
                treeList.add(node);
            }
        }
        TreeStringUtil.parseTreeString(rootTrees, treeList);

        depttj.setStatus("20");
        depttj.setIsCurrent("0");
        depttj.setBelongDeptId("0");
        List<Dept> list2 = deptService.findbofreDeptTree(depttj);
        List<TreeString> treeList2 = new ArrayList<TreeString>();
        List<TreeString> rootTrees2 = new ArrayList<TreeString>();
        if (list2.size()>0) {
            for (Dept dept : list2) {
                if ("1".equals(dept.getBelongDeptId())) {
                    TreeString node = new TreeString();
                    node.setId(dept.getDeptId());
                    node.setState("open");
                    node.setPid(dept.getBelongDeptId());
                    String str = dept.getFullName();
                    String[] strs = str.split(",");
                    String deText;
                    deText = strs[0] + "—" + strs[1];
                    node.setText(deText);
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("level", dept.getLevel());
                    attributes.put("fullName", dept.getFullName());
                    attributes.put("belongDeptName", dept.getBelongDeptName());
                    attributes.put("isCurrent", dept.getIsCurrent());
                    node.setAttributes(attributes);
                    rootTrees2.add(node);
                }
            }
            for (Dept dept : list2) {
                TreeString node = new TreeString();
                node.setId(dept.getDeptId());
                node.setState("open");
                node.setPid(dept.getBelongDeptId());
                String str = dept.getFullName();
                String[] strs = str.split(",");
                String deText;
                deText = strs[0] + "—" + strs[1];
                node.setText(deText);
                Map<String, Object> attributes = new HashMap<String, Object>();
                attributes.put("level", dept.getLevel());
                attributes.put("fullName", dept.getFullName());
                attributes.put("belongDeptName", dept.getBelongDeptName());
                attributes.put("isCurrent", dept.getIsCurrent());
                node.setAttributes(attributes);
                treeList2.add(node);
            }
        }
        TreeStringUtil.parseTreeString(rootTrees2, treeList2);
         List<TreeString> rootTrees3 = new ArrayList<TreeString>();

        TreeString node1 = new TreeString();
        node1.setId("-1");
        node1.setPid("-1");
        node1.setState("open");
        node1.setText("有归属学院");
        node1.setChildren(rootTrees);
        rootTrees3.add(node1);

        TreeString node2 = new TreeString();
        node2.setId("-2");
        node2.setPid("-2");
        node2.setState("open");
        node2.setText("无归属学院");
        node2.setChildren(rootTrees2);
        rootTrees3.add(node2);

        super.writeJson(rootTrees3);
    }

    /**
     * 设置归属机构
     */
    public void setBelongDept() {

        Message message = new Message();
        try {
            // 检查同一机构下的子机构是否存在同名的情况
            Map<String, String> map = new HashMap<String, String>();
            if (dept != null && (StringUtils.isNotBlank(dept.getDeptId()) || StringUtils.isNotBlank(dept.getDeptIds())) && StringUtils.isNotBlank(dept.getBelongDeptId())) {
                map.put("belongDeptId", dept.getBelongDeptId());
                map.put("deptId", dept.getDeptId());
                map.put("deptIds", dept.getDeptIds());
                deptService.setBelongDept(map);
                message.setMsg("设置成功");
                message.setSuccess(true);
            } else {
                message.setMsg("设置失败,机构编号或归属编号为空");
                message.setSuccess(false);
            }
        } catch (Exception e) {
            message.setMsg("设置失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    /**
     * 转移归属机构
     */
    public void shiftFromSourceBelongToGoalBelong() {

        Message message = new Message();
        try {
            Map<String, String> map = new HashMap<String, String>();
            if (StringUtils.isNotBlank(sourceBelongId) && StringUtils.isNotBlank(goalBelongId)) {
                map.put("sourceBelongId", sourceBelongId);
                map.put("goalBelongId", goalBelongId);
                deptService.shiftFromSourceBelongToGoalBelong(map);
                message.setMsg("设置成功");
                message.setSuccess(true);
            } else {
                message.setMsg("设置失败,目标归属机构或源归属机构不能为空");
                message.setSuccess(false);
            }
        } catch (Exception e) {
            message.setMsg("设置失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void doNotNeedSecurity_getDeptTreeForUser() {
        List<Dept> list = deptService.findCurrDeptTree();
        List<String> list_tmp = deptService.getDepts(getUser().getUserId());
        List<Dept> list2 = deptService.selectAll(list_tmp);
        List<TreeString> allList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();

        for (Dept dept : list) {
            for (Dept dept2 : list2) {
                if (dept.getDeptId().equals(dept2.getDeptId())) {
                    dept.setCheckable(true);
                }
            }
        }
        for (Dept dept : list) {
            if (dept.getParentId().equals("0")) {
                TreeString node = new TreeString();
                node.setId(dept.getDeptId());
                node.setState("open");
                node.setPid(dept.getParentId());
                node.setText(dept.getDeptName());
                node.setCheckable(dept.isCheckable());
                Map<String, Object> attributes = new HashMap<String, Object>();
                attributes.put("level", dept.getLevel());
                attributes.put("fullName", dept.getFullName());
                node.setAttributes(attributes);
                rootTrees.add(node);
            }
        }
        if (list != null && list.size() > 0) {
            for (Dept dept : list) {
                TreeString node = new TreeString();
                node.setId(dept.getDeptId());
                node.setState("open");
                node.setPid(dept.getParentId());
                node.setText(dept.getDeptName());
                node.setCheckable(dept.isCheckable());
                Map<String, Object> attributes = new HashMap<String, Object>();
                attributes.put("level", dept.getLevel());
                attributes.put("fullName", dept.getFullName());
                node.setAttributes(attributes);
                allList.add(node);
            }
        }
        TreeStringUtil.parseTreeString(rootTrees, allList);
        super.writeJson(rootTrees);
    }

    public void doNotNeedSecurity_dataGrid() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", rows);
        map.put("page", page);
        if (dept != null) {
            map.put("fullName", dept.getFullName());
        }
        super.writeJson(deptService.dateGridForUser(map));
    }

    public void insert() {
        Message message = new Message();
        try {
            // 检查同一机构下的子机构是否存在同名的情况
            if (dept.getParentId() != null && !"".equals(dept.getParentId())) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("parentId", dept.getParentId());
                map.put("deptName", dept.getDeptName());
                // 检查是否含有重名的机构
                Dept checkDept = deptService.selectByNameAndParentId(map);
                if (checkDept == null) {
                    deptService.insert(dept);
                    message.setMsg("新增成功");
                    message.setSuccess(true);
                } else {
                    message.setMsg("新增失败,名称重复");
                    message.setSuccess(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg("新增失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void update() {
        Message message = new Message();
        try {
            // 检查同一机构下的子机构是否存在同名的情况
            if (dept.getParentId() != null && !"".equals(dept.getParentId())) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("parentId", dept.getParentId());
                map.put("deptName", dept.getDeptName());
                map.put("deptId", dept.getDeptId());
                map.put("sort", dept.getSort());
                // 检查是否含有重名的机构
                Dept checkDept = deptService.selectByNameAndParentId(map);
                if (checkDept == null) {
                    deptService.update(dept);
                    message.setMsg("修改成功");
                    message.setSuccess(true);
                } else {
                    message.setMsg("修改失败,名称重复");
                    message.setSuccess(false);
                }
            }
        } catch (Exception e) {
            message.setMsg("修改失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void updateBelong() {
        Message message = new Message();
        try {
            deptService.updateBelong(dept);
            message.setMsg("修改成功");
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("修改失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void insertAlias() {
        Message message = new Message();
        try {
            // 检查同一机构下的子机构是否存在同名的情况
            if (dept.getParentId() != null && !"".equals(dept.getParentId())) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("parentId", dept.getParentId());
                map.put("deptName", dept.getDeptName());
                // 检查是否含有重名的机构
                Dept checkDept = deptService.selectByNameAndParentId(map);
                if (checkDept == null) {
                    deptService.insertAlias(dept);
                    message.setMsg("新增成功");
                    message.setSuccess(true);
                } else {
                    message.setMsg("新增失败,名称重复");
                    message.setSuccess(false);
                }
            }
        } catch (Exception e) {
            message.setMsg("新增失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void doNotNeedSecurity_getDeptAlias() {
        super.writeJson(deptService.getSchool());
    }

    public void doNotNeedSecurity_getDeptAlias1() {
        List<Dept> list = deptService.getDepart1();
        List<Dept> dept2list = new ArrayList<Dept>();
        for (Dept dept : list) {
            if (dept.getDeptId().substring(9, 10).equals("0")) {
                dept2list.add(dept);
            }
        }
        super.writeJson(dept2list);
    }

    public void delete() {
        Message message = new Message();
        try {
            // 检查当前结构下是否存在学生,如果存在不允许删除
            List<UserInfo> list = userInfoService.selectUserByClassId(deptId);
            List<UserBaseInfo> baseList = userBaseInfoService.selectUserByClassId(deptId);
            if (list != null && list.size() > 0 && baseList != null && baseList.size() > 0) {
                message.setMsg("删除失败,该机构下存在学生,请先删除该机构下的学生，再删除此机构");
                message.setSuccess(false);
            } else {
                deptService.delete(deptId);
                message.setMsg("删除成功");
                message.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg("删除失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void importData() {
        Message message = new Message();
        try {
            String result = deptService.importData(url, getUser());
            message.setMsg(result);
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("导入失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void doNotNeedSecurity_getSchool() {
        super.writeJson(deptService.getSchool());
    }

    /**
     * --查询学校信息--
     **/
    public void doNotNeedSecurity_getDepart() {
        /*lixun 更改角色判断*/
        //if (getUser().getRole().getSystemAdmin() == 1) {   //原来的
        List<Role> roles = getUser().getRoles();
        boolean isAdmin = false;
        boolean isZonghui = false;
        if (roles != null) {
            for (Role r : roles) {
                if (r.getSystemAdmin() == 1) {
                    isAdmin = true;
                    break;
                }
//                if (r.getRoleName().equals("校友总会")){
//                    isZonghui = true;
//                    break;
//                }
                // 系统管理员和总会管理员学院筛选不做权限判断
                if (r.getRoleId() == 6 || r.getRoleId() == 14){
                    isZonghui = true;
                    break;
                }
            }
        }
        if ( isAdmin || isZonghui) {
        /*lixun end*/
            super.writeJson(deptService.getSchool());
        } else {
            //List<Dept> depts = getUser().getDepts();  //lixun 去掉
            List<Dept> depts = deptService.selectDeptByUserId( getUser().getUserId() );    //lixun
            List<Dept> deptList = new ArrayList<Dept>();
            List<String> deptIds = new ArrayList<String>();
            if (depts != null && depts.size() > 0) {
                for (Dept dept : depts) {
                    if (dept.getDeptId().length() == 6) {
                        deptList.add(dept);
                    } else {
                        if (!deptIds.contains(dept.getDeptId().substring(0, 6))) {
                            deptIds.add(dept.getDeptId().substring(0, 6));
                        }
                    }
                }
                if (deptIds.size() > 0) {
                    deptList = deptService.selectByDeptIds(deptIds);
                }
            }
            super.writeJson(deptList);
        }
    }

    /**
     * --查询学校信息(权限和登录都不拦截,提供给外部通用)--
     **/
    public void doNotNeedSessionAndSecurity_getDepart() {
        super.writeJson(deptService.getSchool());
    }

    /**
     * --联动下拉框(学院或年级或班级)--
     **/
    public void doNotNeedSecurity_getByParentId() {
        /*lixun 更改角色判断*/
        //if (getUser().getRole().getSystemAdmin() == 1) {   //原来的
        List<Role> roles = getUser().getRoles();
        boolean isAdmin = false;
        boolean isZonghui = false;
        if (roles != null) {
            for (Role r : roles) {
                if (r.getSystemAdmin() == 1) {
                    isAdmin = true;
                    break;
                }
                // 系统管理员和总会管理员学院筛选不做权限判断
                if (r.getRoleId() == 6 || r.getRoleId() == 14){
                    isZonghui = true;
                    break;
                }
            }
        }
        if ( isAdmin || isZonghui ) {
        /*lixun end*/
            super.writeJson(deptService.getByParentId(deptId));
        } else {
            if (deptId.length() == 6) {
                //List<Dept> depts = getUser().getDepts();  //lixun 去掉
                List<Dept> depts = deptService.selectDeptByUserId( getUser().getUserId() );    //lixun
                List<String> list = new ArrayList<String>();
                boolean f = false;
                if (depts != null && depts.size() > 0) {
                    for (Dept dept : depts) {
                        if (dept.getDeptId().length() == 6) {
                            f = true;
                            break;
                        } else {
                            list.add(dept.getDeptId());
                        }
                    }
                } else {
                    list.add(" ");
                }
                if (f) {
                    super.writeJson(deptService.getByParentId(deptId));
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("deptId", deptId);
                    map.put("list", list);
                    super.writeJson(deptService.getByParentIdAndDeptIds(map));
                }
            } else {
                super.writeJson(deptService.getByParentId(deptId));
            }
        }
    }

    /**
     * --联动下拉框(权限和登录都不拦截,提供给外部通用)--
     **/
    public void doNotNeedSessionAndSecurity_getByParentId() {
        super.writeJson(deptService.getByParentId(deptId));
    }

    public void doNotNeedSecurity_getById() {
        super.writeJson(deptService.getById(dept.getDeptId()));
    }

    public void getByAliasName() {
        super.writeJson(deptService.getByAliasName(dept.getDeptId()));
    }

    public void doNotNeedSecurity_getBelong() {
        super.writeJson(deptService.getBelong(deptId));
    }

    public void doNotNeedSecurity_getDeptTreeForView() {
        List<Dept> list = deptService.getByAliasName(deptId).getDepts();
        List<TreeString> allList = new ArrayList<TreeString>();
        if (list != null && list.size() > 0) {
            for (Dept dept : list) {
                if (dept.getDeptId().length() == deptId.length() && !dept.getDeptId().equals(deptId)) {
                    TreeString node = new TreeString();
                    node.setId(dept.getDeptId());
                    node.setState("open");
                    node.setPid(dept.getParentId());
                    node.setText(dept.getDeptName());
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("level", dept.getLevel());
                    attributes.put("fullName", dept.getFullName());
                    node.setAttributes(attributes);
                    allList.add(node);
                }
            }
        }
        super.writeJson(allList);
    }

    public void doNotNeedSecurity_getUserDepts() {
        super.writeJson(getUser().getDepts());
    }

    /**
     * 班级列表
     */
    public void dataGridClass() {
        long userId = getUser().getUserId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("level", "4");
        map.put("userId",userId);
        if (queryMap != null) {
            map.put("schoolName", queryMap.get("schoolName"));
            map.put("collegeName", queryMap.get("collegeName"));
            map.put("gradeName", queryMap.get("gradeName"));
            map.put("className", queryMap.get("className"));
            map.put("adminNames", queryMap.get("adminNames"));

            map.put("schoolId", queryMap.get("schoolId"));
            map.put("collegeId", queryMap.get("collegeId"));
            map.put("gradeId", queryMap.get("gradeId"));
            map.put("classId", queryMap.get("classId"));
        }
        if( deptService.selectAluByUser(userId) == 1){
            super.writeJson(deptService.selectClassList(map));
        }else{
            super.writeJson(deptService.selectClassListByUser(map));
        }

    }



    /**
     * 获取班级学生列表
     */
    public void findClassStudents() {
        List<UserInfo> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(classId)) {
            list = userInfoService.selectUserByClassId(classId);
        }
        super.writeJson(list);
    }

    /**
     * 设置班级管理员
     */
    public void updateClassAdmin() {
        Message message = new Message();
        try {
            // 检查同一机构下的子机构是否存在同名的情况
            if ((StringUtils.isNotBlank(userId) || StringUtils.isNotBlank(userIds)) && StringUtils.isNotBlank(isClassAdmin)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("userId", userId);
                map.put("userIds", userIds);
                map.put("isClassAdmin", isClassAdmin);
                userInfoService.updateClassAdmin(map);
                message.setMsg("设置成功");
                message.setSuccess(true);
            }
        } catch (Exception e) {
            message.setMsg("设置失败");
            message.setSuccess(false);
            logger.error(e, e);
        }
        super.writeJson(message);
    }

    public void doNotNeedSessionAndSecurity_getDeptByAlumni()
    {
        Map<String,String> mp = new HashMap<String,String>();
        mp.put( "alumniName", deptService.selectDeptByAlumniId( getUser().getDeptId() ) );
        mp.put( "alumniId", String.valueOf( getUser().getDeptId() ) );
        if( getUser().getDeptId() == 1 )
            mp.put( "isNewTypeOpen", "0" );
        else
            mp.put( "isNewTypeOpen", "1" );
        super.writeJson( mp  );
    }

}