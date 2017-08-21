package com.cy.common.utils.easyui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cy.base.entity.TreeString;
import com.google.common.collect.Lists;
import org.apache.commons.collections.list.TransformedList;
import org.apache.commons.lang.StringUtils;

public class TreeStringUtil {
	public static void parseTreeString(List<TreeString> rootTreeStrings, List<TreeString> allList) {
		for (TreeString TreeString : rootTreeStrings) {
			getChildren(TreeString, allList);
		}
	}
	
	private static void getChildren(TreeString TreeString, List<TreeString> allList) {
		List<TreeString> children = getChild(TreeString.getId(), allList);
		if (children != null && children.size() > 0) {
			TreeString.setChildren(children);
			for (TreeString TreeString2 : children) {
				getChildren(TreeString2, allList);
			}
		} else {
			TreeString.setState("open");
		}
	}

	/**
	 * 删除使用,获取删除节点的所有子节点
	 * 
	 * @param id
	 * @param allList
	 * @param deptIdList
	 */
	public static void getChildren(String id, List<TreeString> allList, List<String> deptIdList) {
		List<TreeString> children = getChild(id, allList);
		if (children != null && children.size() > 0) {
			for (TreeString TreeString2 : children) {
				deptIdList.add(TreeString2.getId());
				getChildren(TreeString2.getId(), allList, deptIdList);
			}
		}
	}

	public static List<TreeString> getChild(String id, List<TreeString> allList) {
		List<TreeString> children = new ArrayList<TreeString>();
		if (allList != null && allList.size() > 0) {
			for (TreeString TreeString : allList) {
				if (TreeString.getPid().equals(id)) {
					children.add(TreeString);
				}
			}
		}
		return children;
	}

	public static List<TreeString> findByTreeString(TreeString treeString,List<TreeString> allList){
		List<TreeString> children = new ArrayList<TreeString>();
		if (allList != null && allList.size() > 0) {
			for (TreeString t : allList) {
				Boolean flag1 = StringUtils.isBlank(treeString.getId())||t.getId().equals(treeString.getId().trim());
				Boolean flag2 = StringUtils.isBlank(treeString.getPid())||t.getPid().equals(treeString.getPid().trim());
				Boolean flag3 = StringUtils.isBlank(treeString.getText())||t.getText().indexOf(treeString.getText().trim())!=-1;

				if(flag1&&flag2&&flag3){
					children.add(t);
				}
			}
		}
		return children;
	}

	public static TreeString getParent(TreeString treeString,List<TreeString> allList){
		TreeString parent = new TreeString();
		if(treeString.getPid().equals("-1")){
			return parent;
		}else{
			TreeString treeQuery = new TreeString();
			treeQuery.setId(treeString.getPid());
			parent = findByTreeString(treeQuery,allList).get(0);
			return parent;
		}
	}
	public static List<TreeString> getParents(TreeString treeString,List<TreeString> allList){
		List<TreeString> parents = Lists.newArrayList();
		for(TreeString treeTemp=treeString;!treeTemp.getPid().equals("-1");){
			treeTemp=getParent(treeTemp,allList);
			parents.add(treeTemp);
		}
		return parents;
	}
	public static List<TreeString> findTreeWithParent(TreeString treeString,List<TreeString> allList){
		List<TreeString> list = Lists.newArrayList();
		List<TreeString> queryList = Lists.newArrayList();
		queryList.addAll(findByTreeString(treeString, allList));
		List<TreeString> parentList = Lists.newArrayList();
		for(TreeString t:queryList){
			parentList.addAll(getParents(t,allList));
		}
		queryList.addAll(parentList);
		list = mergeTreeList(queryList);
		return list;
	}
	public static List<TreeString> mergeTreeList(List<TreeString> allList){
		List<TreeString> list = Lists.newArrayList();
		for(TreeString t:allList){
			if(!isInList(list,t)){
				list.add(t);
			}
		}
		return list;
	}

	public static Boolean isInList(List<TreeString> list,TreeString tree){
		for(TreeString t:list){
			if(t.getId().equals(tree.getId())){
				return true;
			}
		}
		return false;
	}
}
