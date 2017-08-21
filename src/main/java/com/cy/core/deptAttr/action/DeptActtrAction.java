package com.cy.core.deptAttr.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.TreeString;
import com.cy.core.dept.service.DeptService;
import com.cy.core.deptAttr.entity.DeptAttr;
import com.cy.core.deptAttr.service.DeptAttrService;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.common.utils.easyui.TreeStringUtil;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cha0res on 2016/7/25.
 */
@Namespace("/deptAttr")
@Action(value="deptAttrAction")
public class DeptActtrAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(DeptActtrAction.class);

    @Autowired
    private DeptAttrService deptAttrService;

    private DeptAttr deptAttr;

    @Autowired
    DeptService ds;

/*
    public void getDeptAttrTree() {
        long nDeptId = getUser().getDeptId();

        List<DeptAttr> list = deptAttrService.selectRootTree(nDeptId);
        List<TreeString> treeList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();
        //添加根节点

        TreeString tsRoot = new TreeString();
        tsRoot.setId("0");
        tsRoot.setState("open");
        tsRoot.setPid("-1");
        tsRoot.setText(list.get(0).getAlumniName());
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("mainType", 0);
        attributes.put("level", 1);
        attributes.put("fullName", "1.");
        tsRoot.setAttributes(attributes);
        rootTrees.add(tsRoot);

        //添加节点树
        List<DeptAttr> rootList = deptAttrService.selectRootTree(nDeptId);
        for (DeptAttr alu : rootList) {
            TreeString node = new TreeString();
            Long oAlumniId = alu.getAlumniId();
            node.setId(oAlumniId.toString());
            if (alu.getMainType().equals("0"))
                node.setState("open");
            else
                node.setState("close");
            node.setPid(alu.getParentId().toString());
            node.setText(alu.getAlumniName());
            Map<String, Object> attributes2 = new HashMap<String, Object>();
            attributes2.put("mainType", alu.getMainType());
            attributes2.put("level", 2);
            attributes2.put("fullName", alu.getSequence());
            if (alu.getXueyuanId() != null && !alu.getXueyuanId().equals(""))
                attributes2.put("academyid", alu.getXueyuanId());
            node.setAttributes(attributes2);
            treeList.add(node);
        }
        TreeStringUtil.parseTreeString(rootTrees, treeList);

        super.writeJson(rootTrees);
    }*/

    /*lixun 2016-7-25 根据用户ID查询数据权限内的全部机构树*/
    public void getDeptAttrTreeByUser()
    {
        long userId = getUser().getUserId();
        List<TreeString> treeList = new ArrayList<TreeString>();
        List<TreeString> rootTrees = new ArrayList<TreeString>();
        List<DeptInfo> arDepts = deptAttrService.selectAlterDeptTreeByUser( userId );
        for ( DeptInfo ard : arDepts)
        {
            TreeString node = new TreeString();
            node.setId( ard.getDeptId() );
            if ( ard.getLevel() > 2 )
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
        }
        TreeStringUtil.parseTreeString(rootTrees, treeList);
        super.writeJson(rootTrees);
    }
    /*lixun*/
}
