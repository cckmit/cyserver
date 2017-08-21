package com.cy.core.resource.action;

import com.cy.common.utils.CacheUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.Tree;
import com.cy.core.resource.entity.Resource;
import com.cy.core.resource.service.ResourceService;

@Namespace("/resource")
@Action(value = "resourceAction")
public class ResourceAction extends AdminBaseAction {
	private static final Logger logger = Logger.getLogger(ResourceAction.class);

	@Autowired
	private ResourceService resourceService;

	private Resource resource;

	private int passPid;
	
	private String url;

	/**
	 * 新增后台菜单，编辑后台菜单时，初始化上级资源
	 */
	public void doNotNeedSecurity_initMenu() {
		//@SuppressWarnings("unchecked")
		//Map<String, Object> map = (Map<String, Object>) ServletActionContext.getServletContext().getAttribute("dictionaryInfoMap");
		@SuppressWarnings("unchecked")
		List<Resource> allList = (List<Resource>) CacheUtils.get("resources");
		List<Tree> treeList = new ArrayList<Tree>();
		List<Tree> tree = new ArrayList<Tree>();
		for (Resource resource : allList) {
			if (resource.getType().equals("菜单") && resource.getFlag() == 0) {
				Tree node = new Tree();
				node.setId(resource.getId());
				node.setPid(resource.getPid());
				node.setText(resource.getName());
				node.setIconCls(resource.getIconCls());
				Map<String, String> attributes = new HashMap<String, String>();
				attributes.put("url", resource.getUrl());
				node.setAttributes(attributes);
				treeList.add(node);
			}
		}
		resourceService.parseTree(tree, treeList);
		super.writeJson(tree);
	}
	
	public void doNotNeedSecurity_initMenuForAdd() {
		//@SuppressWarnings("unchecked")
		//Map<String, Object> map = (Map<String, Object>) ServletActionContext.getServletContext().getAttribute("dictionaryInfoMap");
		@SuppressWarnings("unchecked")
		List<Resource> allList = (List<Resource>) CacheUtils.get("resources");
		List<Tree> treeList = new ArrayList<Tree>();
		List<Tree> tree = new ArrayList<Tree>();
		for (Resource resource : allList) {
			if (resource.getType().equals("菜单")) {
				Tree node = new Tree();
				node.setId(resource.getId());
				node.setPid(resource.getPid());
				node.setText(resource.getMenuName());
				node.setIconCls(resource.getIconCls());
				Map<String, String> attributes = new HashMap<String, String>();
				attributes.put("url", resource.getUrl());
				node.setAttributes(attributes);
				treeList.add(node);
			}
		}
		resourceService.parseTree(tree, treeList);
		super.writeJson(tree);
	}

	/**
	 * 后台菜单列表
	 */
	public void backTreeGrid() {
		//@SuppressWarnings("unchecked")
		//Map<String, Object> map = (Map<String, Object>) ServletActionContext.getServletContext().getAttribute("dictionaryInfoMap");
		@SuppressWarnings("unchecked")
		List<Resource> list = (List<Resource>) CacheUtils.get("resources");
		super.writeJson(list);
	}

	/**
	 * 角色授权，整个授权功能树
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 *
	 */
	public void doNotNeedSecurity_getGrantTree() {
		@SuppressWarnings("unchecked")
		List<Resource> allList = resourceService.selectAll();
		//List<Resource> allList = (List<Resource>) getSession().get("grantTreeList");
		List<Tree> treeList = new ArrayList<Tree>();
		List<Tree> tree = new ArrayList<Tree>();
		for (Resource resource : allList) {
			Tree node = new Tree();
			node.setId(resource.getId());
			node.setPid(resource.getPid());
			node.setText(resource.getMenuName());
			node.setIconCls(resource.getIconCls());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());
			node.setAttributes(attributes);
			treeList.add(node);
		}
		resourceService.parseTree(tree, treeList);
		super.writeJson(tree);
	}
	/**
	 * 角色授权，整个授权菜单树
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 */
	public void doNotNeedSecurity_getMenuTree() {
		@SuppressWarnings("unchecked")
		//获取全部菜单列表
		List<Resource> menus = resourceService.selectAllMenu();
		List<Tree> treeList = new ArrayList<Tree>();
		List<Tree> tree = new ArrayList<Tree>();
		for (Resource resource : menus) {
			Tree node = new Tree();
			node.setId(resource.getId());
			node.setPid(resource.getPid());
			node.setText(resource.getMenuName());
			node.setIconCls(resource.getIconCls());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());
			node.setAttributes(attributes);
			treeList.add(node);
		}
		resourceService.parseTree(tree, treeList);
		super.writeJson(tree);
	}
	
	public void doNotNeedSecurity_getxGrantTree() {
		@SuppressWarnings("unchecked")
		List<Resource> allList = (List<Resource>) getSession().get("xgrantTreeList");
		List<Tree> treeList = new ArrayList<Tree>();
		List<Tree> tree = new ArrayList<Tree>();
		for (Resource resource : allList) {
			Tree node = new Tree();
			node.setId(resource.getId());
			node.setPid(resource.getPid());
			node.setText(resource.getMenuName());
			node.setIconCls(resource.getIconCls());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());
			node.setAttributes(attributes);
			treeList.add(node);
		}
		resourceService.parseTree(tree, treeList);
		super.writeJson(tree);
	}

	public void save() {

		Message message = new Message();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (resource.getName() != null && !"".equals(resource.getName())) {
				map.put("name", resource.getName());
			}
			if (resource.getUrl() != null && !"".equals(resource.getUrl())) {
				map.put("url", resource.getUrl());
			}
			Resource resourceCheck = resourceService.selectByNameOrUrl(map);
			if (resourceCheck == null) {
				resourceService.save(resource);
				message.setMsg("保存成功");
				message.setSuccess(true);
			} else {
				message.setMsg("保存失败,资源权限存在或者资源名称存在");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);

	}

	public void update() {
		Message message = new Message();
		try {
			if (resource.getPid() == resource.getId()) {
				message.setMsg("保存失败,不能选择自身作为父节点!");
				message.setSuccess(false);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				if (resource.getName() != null && !"".equals(resource.getName())) {
					map.put("name", resource.getName());
				}
				if (resource.getUrl() != null && !"".equals(resource.getUrl())) {
					map.put("url", resource.getUrl());
				}
				map.put("id", resource.getId());
				Resource resourceCheck = resourceService.selectByNameOrUrl(map);
				if (resourceCheck == null) {
					resourceService.update(resource);
					message.setMsg("保存成功");
					message.setSuccess(true);
				} else {
					message.setMsg("保存失败,资源权限存在或者资源名称存在");
					message.setSuccess(false);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	public void exportData() {
		Message message = new Message();
		try {
	
			String result = resourceService.export();
			message.setMsg(result);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("导出失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void importData() {
		Message message = new Message();
		try {
			resourceService.importData(url);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("导入失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	/**
	 * 删除菜单
	 */
	public void delete() {
		Message message = new Message();
		try {
			//检查当前菜单下是否存在子菜单,如果存在不允许删除
			int count = resourceService.getCountById(id);
			System.out.println("count= "+count);
			if (count > 0) {
				message.setMsg("删除失败,该菜单下存在子菜单或功能,请先删除该菜单下的子菜单，再删除此菜单");
				message.setSuccess(false);
			} else {
				resourceService.delete(id);
				message.setMsg("删除成功");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**
	 * 查看菜单
	 */
	public void getById() {
		super.writeJson(resourceService.getById(id));
	}

	public void doNotNeedSecurity_getById() {
		super.writeJson(resourceService.getById(id));
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public int getPassPid() {
		return passPid;
	}

	public void setPassPid(int passPid) {
		this.passPid = passPid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
